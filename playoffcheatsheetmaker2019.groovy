//Playoffcheatsheetmaker.groovy

//String fString = "C:\\Users\\Sean\\Dropbox\\Documents\\WFFL\\2012-playoff-cheatsheet1.txt";

String fString = /C:\Users\Sean\Dropbox\Documents\WFFL\2019\2019-playoff-cheat-sheet.txt/

File fIn = new File(fString)
File fOut = new File(fString + ".out.csv")

final String LS = System.getProperty ("line.separator");  

//List teams = ["GBP", "NOS", "SFO", "DET", "NYG", "ATL",
//              "NEP", "BAL", "CIN", "PIT", "DEN", "HOU"]

List teams = ["GBP", "SEA", "PHI", "SFO", "MIN", "NOS",
              "NEP", "KCC", "HOU", "BUF", "TEN", "BAL"]

fOut.write("");

fIn.eachLine()
{
   line ->
   //println line
   
   boolean keep = false

   teams.each()
   {
     if (line.contains(it) || line.trim().size() == 0)
	  {
	      keep = true
         line = line.replaceAll(",", "");
         line = line.replace(it, ";" + it + ";");
         line = line.replaceAll("\\t", "; ");
	  }
   }


   if (keep && line.trim().size() == 0)
   {
      println("");
   }

   else if (keep)
   {
	   List tokens = line.split(';')
	  
      List sublist = tokens.subList(0, 13)
      sublist = sublist.collect { !it.contains("-") && !it.contains("FA") ? it : '' }
      
      String out = sublist.join(',');
      
      println out
      fOut.append(out + LS)
	 
   }

}



// OLD


boolean notOnlyNumbers(String str)
{
   return (str.replaceAll("1", "").replaceAll("2", "").replaceAll("3", "").replaceAll("4", "").replaceAll("5", "").
           replaceAll("6", "").replaceAll("7", "").replaceAll("8", "").replaceAll("9", "").replaceAll("0", "") != "")

}

String didntMakeThePlayoffs(String str)
{
   String retVal = str
   
   List replaceText = ["Team America", "Beware of Doug", "Brickermania", "Honey Bunches of Funchess", "All I Do is Quinn", "Soli Deo Gloria", "Free Agent - Add", "Free Agent"]

   replaceText.each()
   {
      if (retVal.contains(it))
      {
         retVal = retVal.replaceAll(it, "FA")
      }
   }
   
   return retVal

}

