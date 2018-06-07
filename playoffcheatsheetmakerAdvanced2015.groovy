//PlayoffCheatSheetMakerAdvanced

/*
KCC    KCC
HOU    DEN    DEN

PIT    PIT    NEP   DEN
CIN    NEP

SEA    SEA    ARI   CAR 
MIN    ARI

GBP    GBP    CAR
WAS    CAR


 KCC x 2
 HOU x 1
 PIT x 2
 CIN x 1
 NEP x 2
 DEN x 3
 SEA x 2
 MIN x 1
 GBP x 2
 WAS x 1
 ARI x 2
 CAR x 3
 */

println();
 
int KCC = 2
int HOU = 1
int PIT = 2
int CIN = 1
int NEP = 2
int DEN = 3
int SEA = 2
int MIN = 1
int GBP = 2
int WAS = 1
int ARI = 2
int CAR = 3


String fString = /x:\documents\wffl\2015\real-cheatsheet-2.txt/

File fIn = new File(fString)
File fOut = new File(fString + ".out.txt")
 
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

      Guy guy = new Guy(tokens);
   
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
      
      switch (guy.getTeam())
      {
         case "KCC": newscore = score * KCC; break;
         case "HOU": newscore = score * HOU; break;
         case "PIT": newscore = score * PIT; break;
         case "CIN": newscore = score * CIN; break;
         case "NEP": newscore = score * NEP; break;
         case "DEN": newscore = score * DEN; break;
         case "SEA": newscore = score * SEA; break;
         case "MIN": newscore = score * MIN; break;
         case "GBP": newscore = score * GBP; break;
         case "WAS": newscore = score * WAS; break;
         case "ARI": newscore = score * ARI; break;
         case "CAR": newscore = score * CAR; break;
         default: throw new RuntimeException("Unknown team");
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
      
   Guy(List list)
   {
      this.list = list;
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
      
      int i = 0;
      list.each()
      {
         if (i++ != 3)
            retVal += it.trim() + ",";
         else
            retVal += score + ",";
      }
      return retVal.substring(0, retVal.size() - 1)
      
      //return "$name, $team, $pos, $score, $meta";
   }
   
   public int compareTo(def other)
   {
      return Integer.compare(this.score, other.score);
   }

}

