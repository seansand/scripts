//PlayoffCheatSheetMakerAdvanced2017

/*
TEN    KCC 
KCC    NEP    NEP

BUF    JAC    PIT NEP
JAC    PIT

ATL    LAR    MIN   MIN
LAR    MIN 

CAR    NOS    PHI
NOS    PHI

*/

println();
 
int KCC = 2
int TEN = 1
int PIT = 2
int JAC = 2
int NEP = 3
int BUF = 1
int MIN = 3
int PHI = 2
int ATL = 1
int LAR = 2
int NOS = 2
int CAR = 1


String fString = /C:\Users\Sean\Dropbox\Documents\WFFL\2017\stats-4.csv/

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
      List tokens = line.split('\t');

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
         case "NEP": newscore = score * NEP; break;
         case "CAR": newscore = score * CAR; break;
         case "PIT": newscore = score * PIT; break;
         case "KCC": newscore = score * KCC; break;
         case "LAR": newscore = score * LAR; break;
         case "NOS": newscore = score * NOS; break;
         case "JAC": newscore = score * JAC; break;
         case "MIN": newscore = score * MIN; break;
         case "ATL": newscore = score * ATL; break;
         case "TEN": newscore = score * TEN; break;
         case "BUF": newscore = score * BUF; break;
         case "PHI": newscore = score * PHI; break;
		 
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
      score = new Integer(list[1]);
   }
   
   public String getPos()
   {
      //return list[2].trim();
	  
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
	  if (list[0].contains("NEP")) return "NEP"
	  if (list[0].contains("PIT")) return "PIT"
	  if (list[0].contains("LAR")) return "LAR"
	  if (list[0].contains("JAC")) return "JAC"
	  if (list[0].contains("BUF")) return "BUF"
	  if (list[0].contains("PHI")) return "PHI"
	  if (list[0].contains("CAR")) return "CAR"
	  if (list[0].contains("MIN")) return "MIN"
	  if (list[0].contains("TEN")) return "TEN"
	  if (list[0].contains("ATL")) return "ATL"
	  if (list[0].contains("NOS")) return "NOS"
	  if (list[0].contains("KCC")) return "KCC"
	  throw new RuntimeException("Unknown team");
   }   
   
   public String toString()
   {
      String retVal = "";
      
	  return this.score + "\t" + this.line
	  
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

