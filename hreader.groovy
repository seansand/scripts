//hreader.groovy

import groovy.json.JsonSlurper;
import groovy.transform.TupleConstructor;
import com.google.common.collect.ComparisonChain;

def systemIn = new BufferedReader(new InputStreamReader(System.in))

boolean done = false

while(!done)
{
   println()
   try
   {
      String READ_FILE_NAME = "x:\\documents\\games\\Hearthstone Decks\\0 - template.txt"
      String TARGET_DIRECTORY = "x:\\documents\\games\\Hearthstone Decks"

      List fullCardList = getFullCardList()
      List fastCardList = getFastCardList(fullCardList)
      List rawDeck = readTemplate(READ_FILE_NAME);
      check30(rawDeck);
      List indexes = lookup(rawDeck, fullCardList, fastCardList)
      writeDeck(rawDeck, fullCardList, indexes, TARGET_DIRECTORY);
      
      done = true;
   }
   catch (Error e)
   {
      println(e)
      //e.printStackTrace()
      println()
      print("[Enter] to try again, [Ctrl-C] to cancel. ");
      systemIn.readLine();   
   }
}
   
////

List lookup(List rawDeck, List fullCardList, List fastCardList)
{
   List indexVals = []

   rawDeck.each()
   {
      thisCard -> 
	 
  	   thisCard = thisCard.replaceAll("gvg", "");  // IV site format
	   thisCard = thisCard.replaceAll("naxxramascard", "");

	   thisCard = thisCard.replaceAll("1x", "");
	   thisCard = thisCard.replaceAll("x1", "");
      thisCard = thisCard.replaceAll("2x", "2");
      if (!thisCard.contains("hex"))
	      thisCard = thisCard.replaceAll("x2", "2");
      
      if (thisCard.endsWith("2"))
         thisCard = thisCard.substring(0, thisCard.size() - 1)
      
      List candidates = []
      
      int i = 0;
      for (String card : fastCardList)
      {
         if (card.contains(thisCard))
         {
            candidates.add(i)
         }
         ++i;      
      }

      // Special exceptions for unavoidable ambiguities
      
      if (candidates.size() == 2 && thisCard.startsWith("mind"))
      {
         candidates.remove(1);   // "Mind Control" and "Mind Control Tech"
      }

      if (candidates.size() == 2 && thisCard.startsWith("windfury"))
      {
         candidates.remove(0);   // "Windfury" and "windfury harpy"
      }

      if (candidates.size() == 2 && thisCard.startsWith("cogmaster"))
      {
         candidates.remove(1);   // "Cogmaster", "Cogmaster's Wrench"
      }

      
      // If a card starts with a string, use that one.  There might be more than 2 candidates.
      
      if (candidates.size() > 1)
      {
         int cardStartsWith = -1;

         def newCandidates = []
         
         for (int candidate : candidates)
         {
            if (fastCardList[candidate].startsWith(thisCard))
            {
               newCandidates.add(candidate)
            }
         }
         
         candidates = newCandidates;
         
      }         
      
      if (candidates.size() == 0)
      {
         println('Error, could not find a matching card for "' + thisCard + '".');
         assert false;
      }
      else if (candidates.size() > 1)
      {
         println('Error, ' + thisCard + ' is ambiguous, could match any of:');
         candidates.each()
         {
            println fullCardList[it].name         
         }
         assert false;
      }
      else
      {
         indexVals.add(candidates[0]);
      }
   }   

   return indexVals
}

void check30(List rawDeck)
{
   List endsWith2 = rawDeck.findAll(){it.endsWith("2")}
   int endsWith2Count = endsWith2.size();
   
   int deckSize = endsWith2Count + rawDeck.size();
   
   if (deckSize != 30)
   {
      def systemIn = new BufferedReader(new InputStreamReader(System.in))
      println("Warning, deck size should be 30 but is instead $deckSize.")  
      print("Hit [Enter] to continue, [Ctrl-C] to cancel. ");
      systemIn.read();
   }
}


void writeDeck(List rawDeck, List fullCardList, List indexes, String TARGET_DIRECTORY)
{
   List allCards = []
   List classCards = []
   List neutralCards = []
   String playerClass = "Unknown";
   
   for (i in (0..<rawDeck.size()))
   {
      int index = indexes[i]
      def item = fullCardList[index]
      String disp = item.cost + " " + item.name 
      
      if (rawDeck[i].endsWith("2"))
         disp += " (2)"
   
      if (item.rarity.equalsIgnoreCase("legendary"))
         disp += " (*)"

      if (item.playerClass == null)
      {
         neutralCards.add(new Pair(disp, item))     
      }
      else
      {
         playerClass = item.playerClass
         classCards.add(new Pair(disp, item))       
      }
         
      allCards.add(new Pair(disp, item))            
   }

   // SortClosure
   Closure sc = 
   {
      first, second ->
	  
	  ComparisonChain.start()
         .compare(first.fullCard.get("cost"), second.fullCard.get("cost"))  
         .compare(second.fullCard.get("type"), first.fullCard.get("type"))   //reverse
         .compare(first.fullCard.get("name"), second.fullCard.get("name"))
         .result();
	  
   }
   
   // write to file
   
   File outFile = getOutFile(TARGET_DIRECTORY, playerClass)
   
   outFile.append(playerClass.toUpperCase() + " CARDS \n");
   classCards.sort(sc).each() {outFile.append(it.disp + '\n')}
   outFile.append("\nNEUTRAL CARDS \n");
   neutralCards.sort(sc).each() {outFile.append(it.disp + '\n')}
   
   outFile.append("______________" + '\n\n')
   outFile.append("DISPLAYED LIST" + '\n')
   allCards.sort(sc).each() {outFile.append(it.disp + '\n')}
   
   println("Done. Wrote deck to " + outFile.getCanonicalPath());
   println();
   
   def cmd = 'explorer ' + outFile.getCanonicalPath()
   Process proc0 = Runtime.getRuntime().exec(cmd)
   
}

@TupleConstructor
class Pair
{
   String disp
   HashMap fullCard
}



File getOutFile(String TARGET_DIRECTORY, String hero)
{
   if (!TARGET_DIRECTORY.endsWith("\\"))
      TARGET_DIRECTORY += "\\";
   File dir = new File(TARGET_DIRECTORY + hero + "\\");
   assert dir.exists();
      
   def sdf = new java.text.SimpleDateFormat("yyyyMMdd");
   def now = new Date();
   def dateString = sdf.format(now)
   
   String outFileString = TARGET_DIRECTORY + hero + "\\" + dateString + " " + hero + ".txt";
   File outFile = new File(outFileString);
   if (outFile.exists())
   {
      String newOutFileString = outFileString;
      for (j in ('a'..'z'))
      {
         newOutFileString  = outFileString.replaceFirst(dateString, dateString + j)
         outFile = new File(newOutFileString);
         if (!outFile.exists())
            break;
      }      
   }
   
   return outFile
}


List readTemplate(String READ_FILE_NAME)
{
   File f = new File(READ_FILE_NAME)
   
   List deck = [];
      
   f.eachLine()
   {
      line ->

	   line = line.replaceAll("[^\\x00-\\x7F]", "");   //strip non-ascii
      line = line.trim();
	  
      if (line != "" && !line.contains("CARDS"))
      {
	  
	      if (line.startsWith("x2")) 
		   {
		      line = line.replaceAll("x2", "");
		      line = line + " 2";		 
		   }	 

		   if (line.startsWith("2x"))
    	   {
		      line = line.replaceAll("2x", "");
		      line = line + " 2";		 
		   }	 
		 
	      if (line.startsWith("1x"))
	      {
		      line = line.replaceAll("1x", "");
	      }	 
        
		   if (line.endsWith("1"))
		   {
		      line = line.substring(0, line.size() - 1);
		   }
		
         boolean twoLine = false;
         line = line.toLowerCase().trim();
         line = stripAnyInitialNumber(line); 
         twoLine = line.contains("2");
         
         line = line.replaceAll("'", "").replaceAll(" ", "").replaceAll("-", "")
                    .replaceAll("\\.", "").replaceAll(":", "").replaceAll("\\*", "")
                    .replaceAll("\\(.*?\\)", "").replaceAll("\\*", "").replaceAll("2", "")
         
         if (twoLine)
            line = line + "2";
         
         deck.add(line.trim());
        
      }
     
   }

   return deck
}

String stripAnyInitialNumber(String str)
{
   str = str.trim();
   if (str.startsWith("1") ||
       str.startsWith("2") ||
       str.startsWith("3") ||
       str.startsWith("4") ||
       str.startsWith("5") ||
       str.startsWith("6") ||
       str.startsWith("7") ||
       str.startsWith("8") ||
       str.startsWith("9") ||
       str.startsWith("0"))
       return stripAnyInitialNumber(str.substring(1))
    else
       return str
}


List getFastCardList(List fullList)
{
   List fastList = []
   
   fullList.each()
   {
      String name = it.name.toLowerCase();

      name = name.replaceAll("'", "").replaceAll(" ", "").replaceAll("-", "")
                 .replaceAll("\\.", "").replaceAll(":", "")
                
      fastList.add(name);
   }
   
   return fastList;

}

List getFullCardList()
{
   //String hCardFileName = "x:\\documents\\games\\hearthstonecards.json"
   //File hCardFile = new File(hCardFileName);

   //URL url = new URL("https://raw.githubusercontent.com/pdyck/hearthstone-db/master/cards/all-cards.json");

   URL url = new URL("http://hearthstonejson.com/json/AllSets.json");

   
   String hCardJson = url.getText();
   
   List allSets = [];
   allSets.add(new JsonSlurper().parseText(hCardJson)."Basic")
   allSets.add(new JsonSlurper().parseText(hCardJson)."Curse of Naxxramas")
   allSets.add(new JsonSlurper().parseText(hCardJson)."Classic")
   allSets.add(new JsonSlurper().parseText(hCardJson)."Goblins vs Gnomes")
   allSets.add(new JsonSlurper().parseText(hCardJson)."Promotion")
   allSets.add(new JsonSlurper().parseText(hCardJson)."Reward")
   //allSets.add(new JsonSlurper().parseText(hCardJson)."Credits")
   //allSets.add(new JsonSlurper().parseText(hCardJson)."Debug")
   //allSets.add(new JsonSlurper().parseText(hCardJson)."Missions")
   //allSets.add(new JsonSlurper().parseText(hCardJson)."System")

   List fullList = [];
   
   allSets.each()
   {
      set ->
      set.each()
      {
         fullList.add(it)
      }
   }
   
   fullList.findAll{it.collectible}
}



