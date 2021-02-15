//PlayoffCheatSheetMakerAdvanced2020

/*
NO   
CHI  

SEA  SEA
LAR  NO

WAS  TB   NO
TB   GB   GB   GB

BUF  PIT  BUF  KC
IND  BUF  KC

PIT  TEN  
CLE  KC

TEN  
BAL  


*/

List<Team> teams = [];
teams << new Team("NOS", 3)
teams << new Team("CHI", 1)
teams << new Team("SEA", 2)
teams << new Team("LAR", 1)
teams << new Team("WAS", 1)
teams << new Team("TBB", 2)
teams << new Team("BUF", 3)
teams << new Team("IND", 1)
teams << new Team("PIT", 2)
teams << new Team("CLE", 1)
teams << new Team("TEN", 2)
teams << new Team("BAL", 1)
teams << new Team("GBP", 3)
teams << new Team("KCC", 3)

println();
 
assert Team.totalWeight == 26  // was 22 (12-1 * 2)
assert Team.map.size() == 14  // was 12
assert Team.totalWeight == (Team.map.size() - 1) * 2

String fString = /X:\Documents\WFFL\2020\playoffplayers-2020-1.csv/

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
       !line.contains(",,,,,") &&   // CSV blank line
       !line.contains("---") &&     // pos header lines
       !line.contains("(I)") &&     // ignore players on IR
       !line.contains("(S)") )      // ignore suspended players
   {
      List tokens = line.split(',');   // separator (preferably comma?)

      Guy guy = new Guy(tokens, line);
   
      String tm = guy.getTeam()
      if (tm)                          // ignore if team is not found
      {
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
         
         tm = tm.replaceAll('"', "")
        
         int newWeightedScore = guyScore * Team.map.get(tm)  // retrieve weight from Team map
         
         guy.setScore(newWeightedScore)
      }
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
      
      //score = 0                                   // or selected weeks (like four recent weeks & not 17)
      
      score = new Integer(list[2].trim() ?: 0)
      
      /*
      score += new Integer(list[15].trim() ?: 0)  // week 13
      score += new Integer(list[16].trim() ?: 0)  // week 14
      score += new Integer(list[17].trim() ?: 0)  // week 15
      score += new Integer(list[18].trim() ?: 0)  // week 16
      */
      
   }
   
   public String getPos()
   {
	  if (list[1].contains("Def")) return "Def"
	  if (list[1].contains("QB")) return "QB"
	  if (list[1].contains("RB")) return "RB"
	  if (list[1].contains("WR")) return "WR"
	  if (list[1].contains("TE")) return "TE"
	  if (list[1].contains("PK")) return "PK"
	  throw new RuntimeException("Unknown Pos");
   }

   public String getTeam()
   {
     String teamNameFound = null
     Team.map.each { k, v ->
        if (this.list[1].contains(k)) {
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

