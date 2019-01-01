//PlayoffCheatSheetMakerAdvanced2018

/*
IND  IND 
HOU  KCC  KCC

LAC  BAL  
BAL  NEP  NEP  KCC

SEA  DAL    
DAL  LAR  LAR  LAR

PHI  CHI    
CHI  NOS  NOS

*/

List<Team> teams = [];
teams << new Team("IND", 2)
teams << new Team("HOU", 1)
teams << new Team("LAC", 1)
teams << new Team("BAL", 2)
teams << new Team("SEA", 1)
teams << new Team("DAL", 2)
teams << new Team("PHI", 1)
teams << new Team("CHI", 2)
teams << new Team("KCC", 3)
teams << new Team("NEP", 2)
teams << new Team("NOS", 2)
teams << new Team("LAR", 3)

println();
 
assert Team.totalWeight == 22
assert Team.map.size() == 12

String fString = /X:\Documents\WFFL\2018\playoffplayers-2018.csv/

File fIn = new File(fString)
File fOut = new File(fString + ".out.csv")
 
List qbList = [];
List rbList = [];
List wrList = [];
List pkList = [];
List dfList = [];
List all = [qbList, rbList, wrList, pkList, dfList]; 

 
fIn.eachLine()
{
   line ->
   
   if (line.trim() != "" &&         // ignore blank lines
       !line.contains("---") &&     // pos header lines
       !line.contains("(I)") &&     // ignore players on IR
       !line.contains("(S)") )      // ignore suspended players
   {
      List tokens = line.split(',');   // separator (preferably comma?)

      Guy guy = new Guy(tokens, line);
   
      if (guy.getPos().contains("QB"))
         qbList.add(guy);
      else if (guy.getPos().contains("RB"))
         rbList.add(guy);
      else if (guy.getPos().contains("PK"))
         pkList.add(guy);
      else if (guy.getPos().contains("Def"))
         dfList.add(guy);
      else
         wrList.add(guy);
   
      int guyScore = guy.getScore();
            
 	   String tm = guy.getTeam()
	   tm = tm.replaceAll('"', "")
	  
      int newWeightedScore = guyScore * Team.map.get(tm)  // retrieve weight from Team map
      
      guy.setScore(newWeightedScore)
   }

}


all.each()
{
   list ->
   
   list.sort();
   list = list.reverse();
   list.each()
   {
      guy ->
      println guy
   }
   println();
}


class Team {
   static Float totalWeight = 0
   static Map map = [:]
   
   String name
   Float weight
   
   Team(String n, Float w) 
   {
      this.name = n
      this.weight = w
      totalWeight += w
      map.put(n, w)
   }
   
}


class Guy implements Comparable
{
   List list
   int score
   String line
      
   Guy(List list, line)
   {
      this.list = list;
	   this.line = line;
      
      //score = new Integer(list[1]);             // can use either the total of all weeks
      
      score = 0                                   // or selected weeks (like four recent weeks & not 17)
      score += new Integer(list[15].trim() ?: 0)  // week 13
      score += new Integer(list[16].trim() ?: 0)  // week 14
      score += new Integer(list[17].trim() ?: 0)  // week 15
      score += new Integer(list[18].trim() ?: 0)  // week 16
      
   }
   
   public String getPos()
   {
	  if (list[0].contains("Def")) return "Def"
	  if (list[0].contains("QB")) return "QB"
	  if (list[0].contains("RB")) return "RB"
	  if (list[0].contains("WR")) return "WR"
	  if (list[0].contains("TE")) return "TE"
	  if (list[0].contains("PK")) return "PK"
	  throw new RuntimeException("Unknown Pos");
   }

   public String getTeam()
   {
     String teamNameFound = null
     Team.map.each { k, v ->
        if (this.list[0].contains(k)) {
           teamNameFound = k
        }
     }
     return teamNameFound
   }   
   
   public String toString()
   {
      String retVal = "";
      
	   return "${this.score}, ${this.line}"
	  
	  /*
	  
      int i = 0;
      list.each()
      {
         if (i++ != 3)
            retVal += it.trim() + ",";
         else
            retVal += score + ",";
      }
      return retVal.substring(0, retVal.size() - 1)
      */
	  
	  
      //return "$name, $team, $pos, $score, $meta";
   }
   
   public int compareTo(def other)
   {
      return Integer.compare(this.score, other.score);
   }

}

