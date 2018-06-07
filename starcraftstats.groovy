//StarcraftStats.groovy

File dir = new File("X:\\Starcraft\\Sc2gears-replays");

int TPwins = 0;
int TPtotal = 0;

int TTwins = 0;
int TTtotal = 0;

int TZwins = 0;
int TZtotal = 0;

int PPwins = 0;
int PPtotal = 0;

int PTwins = 0;
int PTtotal = 0;

int PZwins = 0;
int PZtotal = 0;

int ZZwins = 0;
int ZZtotal = 0;

int ZTwins = 0;
int ZTtotal = 0;

int ZPwins = 0;
int ZPtotal = 0;

int 2v2wins = 0;
int 2v2total = 0;

//List<String> files = new ArrayList<String>();

dir.eachFile()
{
   if (it.getName().contains("MD5_"))
   {
      String name = it.getName();
      
      /*
      
      if (name.contains("TPv") || 
          name.contains("PTv") ||
          name.contains("TTv") ||
          name.contains("ZZv") ||
          name.contains("PPv") ||
          name.contains("TZv") ||
          name.contains("ZTv") ||
          name.contains("PZv"))
       {
           // can't tell from the filename 
       }
      */
      
     
      //1v1
      
      if (name.contains(" TvT "))
      {
         TTtotal += 1;
         TTwins += isWin(name);
         
         //print(name + "  ");
         //println (isWin(name) + " " + TTwins + " " + TTtotal);
         
         
      }
      else if (name.contains(" TvZ "))
      {
         TZtotal += 1;
         TZwins += isWin(name);
      }
      else if (name.contains(" TvP "))
      {
         TPtotal += 1;
         TPwins += isWin(name);
      }
      else if (name.contains(" PvT "))
      {
         PTtotal += 1;
         PTwins += isWin(name);
      }
      else if (name.contains(" PvP "))
      {
         PPtotal += 1;
         PPwins += isWin(name);
      }
      else if (name.contains(" PvZ "))
      {
         PZtotal += 1;
         PZwins += isWin(name);
      }
      else if (name.contains(" ZvT "))
      {
         ZTtotal += 1;
         ZTwins += isWin(name);
      }
      else if (name.contains(" ZvP "))
      {
         ZPtotal += 1;
         ZPwins += isWin(name);
      }
      else if (name.contains(" ZvZ "))
      {
         ZZtotal += 1;
         ZZwins += isWin(name);
      }
      
   }
}


println()
println("TvT: $TTwins of $TTtotal.  ${getPercentage(TTwins, TTtotal)}");
println("TvP: $TPwins of $TPtotal.  ${getPercentage(TPwins, TPtotal)}");
println("TvZ: $TZwins of $TZtotal.  ${getPercentage(TZwins, TZtotal)}");
println("TvX: ${TZwins + TTwins + TPwins} of ${TZtotal + TTtotal + TPtotal}.  ${getPercentage(TZwins + TTwins + TPwins, TZtotal + TTtotal + TPtotal)}");
println()
println("PvP: $PPwins of $PPtotal.  ${getPercentage(PPwins, PPtotal)}");
println("PvT: $PTwins of $PTtotal.  ${getPercentage(PTwins, PTtotal)}");
println("PvZ: $PZwins of $PZtotal.  ${getPercentage(PZwins, PZtotal)}");
println("PvX: ${PZwins + PTwins + PPwins} of ${PZtotal + PTtotal + PPtotal}.  ${getPercentage(PZwins + PTwins + PPwins, PZtotal + PTtotal + PPtotal)}");
println()
println("ZvP: $ZPwins of $ZPtotal.  ${getPercentage(ZPwins, ZPtotal)}");
println("ZvT: $ZTwins of $ZTtotal.  ${getPercentage(ZTwins, ZTtotal)}");
println("ZvZ: $ZZwins of $ZZtotal.  ${getPercentage(ZZwins, ZZtotal)}");
println("ZvX: ${ZZwins + ZTwins + ZPwins} of ${ZZtotal + ZTtotal + ZPtotal}.  ${getPercentage(ZZwins + ZTwins + ZPwins, ZZtotal + ZTtotal + ZPtotal)}");


println()

println("All: ${ZZwins + ZTwins + ZPwins + PZwins + PTwins + PPwins + TZwins + TTwins + TPwins} of ${ZZtotal + ZTtotal + ZPtotal + PZtotal + PTtotal + PPtotal + TZtotal + TTtotal + TPtotal}.  ${getPercentage(ZZwins + ZTwins + ZPwins + PZwins + PTwins + PPwins + TZwins + TTwins + TPwins, PZtotal + PTtotal + PPtotal + TZtotal + TTtotal + TPtotal + ZZtotal + ZTtotal + ZPtotal)}");

println()


int isWin(String str)
{
   return (str.contains("Seansand") || str.contains("GroovyGuy")) ? 1 : 0;
}


public static String getPercentage(def a, def b)
{
   def quotient = (b == 0) ? 0 : a / b
   quotient *= 10000
   quotient = Math.round(quotient)
   quotient = quotient / 100
   return quotient + "%"
}


