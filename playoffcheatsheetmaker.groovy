//Playoffcheatsheetmaker.groovy

//String fString = "C:\\Users\\Sean\\Dropbox\\Documents\\WFFL\\2012-playoff-cheatsheet1.txt";

String fString = /x:\documents\wffl\2017\stats-1.txt/

File fIn = new File(fString)
File fOut = new File(fString + ".out.txt")

final String LS = System.getProperty ("line.separator");  

//List teams = ["GBP", "NOS", "SFO", "DET", "NYG", "ATL",
//              "NEP", "BAL", "CIN", "PIT", "DEN", "HOU"]

List teams = ["GBP", "DET", "SEA", "NYG", "DAL", "ATL",
              "NEP", "KCC", "HOU", "PIT", "MIA", "OAK"]

fOut.write("");

fIn.eachLine()
{
   line ->
   //println line
   
   boolean keep = false

   teams.each()
   {
     if (line.contains(it) || line.contains("---"))
	  {
	     keep = true
         line = line.replace(it, "," + it + ",");
	  }
   }

   if (keep)
   {
	  List tokens = line.split(',')
	  
	  String modifiedLine = line
	  
	  if (!line.contains("---") && line.trim().size() > 0)
	  {
		 tokens[2] = tokens[2].replaceAll("\\s+", ",")	
		 
		 int lastComma = tokens[2].lastIndexOf(",")
		 
		 tokens[2] = tokens[2].substring(0, lastComma)
		 
		 tokens[2] = tokens[2].replaceAll(",Free,Agent,-,Add", "")
		 
		 tokens[2] = tokens[2].replaceAll("Robot,Sea,Monsters", "RSM")
		 tokens[2] = tokens[2].replaceAll("BoomShakaLaka", "BSL")
		 tokens[2] = tokens[2].replaceAll("Team,America", "TA")

		 tokens[2] = tokens[2].replaceAll("Kickass,Kamikaze", "")
		 tokens[2] = tokens[2].replaceAll("In,Goff,We,Trust", "")
		 tokens[2] = tokens[2].replaceAll("Hammer", "")
		 tokens[2] = tokens[2].replaceAll("Soli,Deo,Gloria", "")
		 tokens[2] = tokens[2].replaceAll("Beware,of,Doug", "")
	  
		 modifiedLine = tokens.join(",")

		 modifiedLine = modifiedLine.replaceAll("\\(R\\),", "")	
		 
		 
		 modifiedLine = modifiedLine.replaceAll(",," , ",")
		 
	  }
	  
	  
/*	  
	  tokens.remove(tokens.size() - 1);
      tokens.remove(0)
      tokens.remove(0)
      
      
      //18.times()   { tokens.remove(4);   }
      
      //def revisedTokens = tokens.findAll() {notOnlyNumbers(it)}
      
      def revisedTokens2 = tokens.collect() {didntMakeThePlayoffs(it)}      
   
      String modifiedLine = revisedTokens2.join(",");
   */
   
      fOut.append(modifiedLine + LS)
	  
	 
   }
   
   

}



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

