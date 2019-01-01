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
 
int IND = 2
int HOU = 1
int LAC = 1
int BAL = 2
int SEA = 1
int DAL = 2
int PHI = 1
int CHI = 2
int KCC = 3
int NEP = 2
int NOS = 2
int LAR = 3

assert IND + HOU + LAC + BAL + SEA + DAL + PHI + CHI + KCC + NEP + NOS + LAR == 22


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
   
   if (line.trim() != "" &&
       !line.contains("---") &&
       !line.contains("(I)") &&
       !line.contains("(S)") )
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
         case "IND": newscore = score * IND; break;
         case "HOU": newscore = score * HOU; break;
         case "LAC": newscore = score * LAC; break;
         case "BAL": newscore = score * BAL; break;
         case "SEA": newscore = score * SEA; break;
         case "DAL": newscore = score * DAL; break;
         case "PHI": newscore = score * PHI; break;
         case "CHI": newscore = score * CHI; break;
         case "KCC": newscore = score * KCC; break;
         case "NEP": newscore = score * NEP; break;
         case "NOS": newscore = score * NOS; break;
         case "LAR": newscore = score * LAR; break;
		 
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
      
      //score = new Integer(list[1]);
      score = 0
      score += new Integer(list[15].trim() ?: 0)  //week 13
      score += new Integer(list[16].trim() ?: 0)  //week 14
      score += new Integer(list[17].trim() ?: 0)  //week 15
      score += new Integer(list[18].trim() ?: 0)  //week 16
      
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
	  if (list[0].contains("IND")) return "NEP"
	  if (list[0].contains("HOU")) return "HOU"
	  if (list[0].contains("LAC")) return "LAC"
	  if (list[0].contains("BAL")) return "BAL"
	  if (list[0].contains("SEA")) return "SEA"
	  if (list[0].contains("DAL")) return "DAL"
	  if (list[0].contains("PHI")) return "PHI"
	  if (list[0].contains("CHI")) return "CHI"
	  if (list[0].contains("NEP")) return "NEP"
	  if (list[0].contains("NOS")) return "NOS"
     if (list[0].contains("LAR")) return "LAR"
	  if (list[0].contains("KCC")) return "KCC"
	  throw new RuntimeException("Unknown team");
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

