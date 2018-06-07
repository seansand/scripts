//PlayoffCheatSheetMakerAdvanced

/*
MIA    PIT 
PIT    KC     KC 

OAK    HOU    NEP   NEP
HOU    NEP

DET    SEA    ATL   DAL 
SEA    ATL 

NYG    GBP    DAL
GBP    DAL


 KCC x 2
 HOU x 2
 PIT x 2
 OAK x 1
 NEP x 3
 MIA x 1
 SEA x 2
 NYG x 1
 GBP x 2
 DAL x 3
 ATL x 2
 DET x 1
 */

println();
 
int KCC = 2
int HOU = 2
int PIT = 2
int OAK = 1
int NEP = 3
int MIA = 1
int SEA = 2
int NYG = 1
int GBP = 2
int DAL = 3
int ATL = 2
int DET = 1


String fString = /x:\documents\wffl\2016\cheatsheet-saved.csv/

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
   
   if (line.trim() != "")
   {
      List tokens = line.split(',');

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
   
      int score = guy.getScore();
      int newscore;
      
	  String tm = guy.getTeam()
	  tm = tm.replaceAll('"', "")
	  
      switch (tm)
      {
         case "KCC": newscore = score * KCC; break;
         case "HOU": newscore = score * HOU; break;
         case "PIT": newscore = score * PIT; break;
         case "OAK": newscore = score * OAK; break;
         case "NEP": newscore = score * NEP; break;
         case "MIA": newscore = score * MIA; break;
         case "SEA": newscore = score * SEA; break;
         case "DAL": newscore = score * DAL; break;
         case "GBP": newscore = score * GBP; break;
         case "DET": newscore = score * DET; break;
         case "ATL": newscore = score * ATL; break;
         case "NYG": newscore = score * NYG; break;
		 
         default: throw new RuntimeException("Unknown team: $tm");
      }
      
      guy.setScore(newscore);
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



class Guy implements Comparable
{
   List list
   int score
   String line
      
   Guy(List list, line)
   {
      this.list = list;
	  this.line = line;
      score = new Integer(list[3]);
   }
   
   public String getPos()
   {
      return list[2].trim();
   }

   public String getTeam()
   {
      return list[1].trim();
   }   
   
   public String toString()
   {
      String retVal = "";
      
	  return this.score + ", " + this.line
	  
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

