// NflOdds.groovy 
// Updated 9/18/2013 to include game date, we were getting odds for games too far ahead of time.
// Does NNFP Picks



String.metaClass.safesubstring = 
{
   start, length ->
   try
   {
     return delegate.substring(start, length)
   }
   catch (Exception e)
   {
     return delegate.substring(start)
   }
}               
String.metaClass.safesubstring = 
{
   start ->
     return delegate.substring(Math.max(0, start))
} 

boolean allFlag = args.size() > 0 ? args?.getAt(0)?.toUpperCase()?.startsWith("ALL") : false;

Properties props = System.getProperties(); 
props.put("http.proxyHost", "webproxy.int.westgroup.com"); 
props.put("http.proxyPort", "80"); 
  

//def url = new URL("http://home.comcast.net/~seansand/nflodds.xml")
def url = new URL("http://lines.gamblerspalace.com/")

println()
println(url)

def xmlString = url.getText(); 
 
// Get rid of opening junk 
xmlString = xmlString.safesubstring(xmlString.indexOf("<?xml")) 
def matchupList = [] 

println()

def slurper = new XmlSlurper().parseText(xmlString) 
try
{
   def leagues = slurper.Leagues

   Set forceUnique = new HashSet()  //just in case a team is erroneously listed twice
   
   leagues.league.each() 
   { 
      league ->

      if (league.@IdSport.toString().trim() == "NFL" && league.@Description.toString().trim() == "NFL") 
      { 
         league.game.each() 
         { 
             game ->

             //println("Matchup: ${game.@vtm.toString()} @ ${game.@htm.toString()}, ${game.line.@hsprdt.toString()}")
             
			 
             if ((allFlag || gameIsWithinAWeek(game)) && (!forceUnique.contains(Matchup.teamName(game.@htm.toString()))))
             {
                forceUnique.add(Matchup.teamName(game.@htm.toString()))
                
                matchupList.add(new Matchup(game.@htm.toString(), 
                                            game.@vtm.toString(),
                                            game.line.@hsprdt.toString())) 
             }
         } 
      } 
   } 
   getValues(matchupList) 
   matchupList.each() 
   { 
       println it.toString() 
   } 
}
catch (Exception e)
{
   println xmlString
   println()
   e.printStackTrace()
}

println()
// Done. 


// Return true if the game is within the next seven days
boolean gameIsWithinAWeek(def game)
{
   def sdf = new java.text.SimpleDateFormat("yyyyMMdd"); 

	String gameDateString = game.@gmdt.toString();   // 20130926
	
	Date gameDate = sdf.parse(gameDateString);
   long gameDateLong = gameDate.getTime();
	Date nowDate = new Date();
   String dayString = nowDate.toString().substring(0, 3);
   
   long now = System.currentTimeMillis();
	
   switch(dayString)
   {
      case "Mon": return gameDateLong - now < 1000 * 60 * 60 * 24 * 1;
      case "Tue": return gameDateLong - now < 1000 * 60 * 60 * 24 * 7;
      case "Wed": return gameDateLong - now < 1000 * 60 * 60 * 24 * 6;
      case "Thu": return gameDateLong - now < 1000 * 60 * 60 * 24 * 5;
      case "Fri": return gameDateLong - now < 1000 * 60 * 60 * 24 * 4;
      case "Sat": return gameDateLong - now < 1000 * 60 * 60 * 24 * 3;
      case "Sun": return gameDateLong - now < 1000 * 60 * 60 * 24 * 2;
   }
   
   return null;
}


void getValues(List matchupList) 
{ 
   TreeMap map = new TreeMap() 
   matchupList.each() 
   { 
      while (map.containsKey(it.getSpread())) 
         it.setSpread(it.getSpread() + 0.01) 
      map.put(it.getSpread(), it) 
   } 
   counter = 1 
   map.each() 
   { 
       it.getValue().setValue(counter++) 
   } 
} 

class Matchup 
{ 
    String favorite = "" 
    String otherTeam = "" 
    String homeTeam = "" 
    String awayTeam = "" 
    String homeSpread = ""
    Boolean homeFavorite = true 
    Double spread = 0.0 
    Integer value = 0 
    Matchup (String home, String away, String homeSpreadIn) 
    { 
       homeTeam = teamName(home)
       awayTeam = teamName(away)
       homeSpread = homeSpreadIn

       homeFavorite = homeSpread.startsWith('-') 
       /*
       try 
       { 
          homeSpread = homeSpread.substring(0, homeSpread.lastIndexOf("-")) 
       } 
       catch (Exception e) 
       { 
          try
          {
             if (homeSpread.contains("+"))
                homeSpread = homeSpread.substring(0, homeSpread.lastIndexOf("+")) 
          }
          catch (Exception e2)
          {
             println ("OFF: $home $away")
             homeSpread = "0.0";
          }
       } 

       homeSpread = homeSpread.replaceAll("\\-", "").replaceAll("\\+", "") 
       */
       homeSpread = homeSpread.replaceAll("\\+", "")
       homeSpread = homeSpread.replaceAll("-", "")
       homeSpread = homeSpread.replaceAll("Â½", ".5") 
       homeSpread = homeSpread.replaceAll("n/a", "0") 

       if (homeSpread == "") 
          homeSpread = "0" 
       spread = new Double(homeSpread) 
       favorite = homeFavorite? teamName(home) : teamName(away) 
       otherTeam = homeFavorite? teamName(away) : teamName(home) 
    } 

    String toStringOld() 
    { 
        String valStr = value.toString() 
        if (valStr.size() == 1) 
           valStr = " " + valStr 
        "$favorite $valStr   (${homeFavorite? "vs" : "at"} $otherTeam) ${toString(spread)}" 
    } 

    String toString() 
    { 
        String valStr = value.toString() 
        if (valStr.size() == 1) 
           valStr = " " + valStr 

		def forecast = null   
		try
		{ 
           forecast = Forecast.homePercentage(new Double("${homeFavorite ? "-" : " "}$homeSpread"))
		}
		catch (Exception e)
		{
		   println(homeSpread.dump())
		   println(e)
		   println(homeSpread + " failed for some reason")
		   assert false
		}
		   
		String retVal = 
		   "$favorite $valStr   ($awayTeam @ $homeTeam) ${homeFavorite ? "-" : " "}$homeSpread  \t" 
        retVal += "  --- <$homeTeam> $forecast"		
		
	    return retVal		
    } 

    // Only works when the "½" character is supported 
    String toString(Double f) 
    { 
        String origString = f.toString() 
        String fString = origString.substring(0, origString.indexOf(".")) 
        //origString.contains(".5") ? fString + "½" : fString 
        return origString.contains(".5") ? fString + ".5" : fString + ".0" 
    } 
    static String teamName(String name) 
    { 
        name = name.toUpperCase() 
		
        if (name.contains("NFC")) return "NFC" 
        if (name.contains("AFC")) return "AFC" 
        if (name.contains("SEAT")) return "SEA" 
        if (name.contains("FRAN")) return "SF " 
        if (name.contains("ARIZ")) return "ARI" 
        if (name.contains("RAMS")) return "LAR" 
        if (name.contains("GREE")) return "GB " 
        if (name.contains("CHIC")) return "CHI" 
        if (name.contains("MINN")) return "MIN" 
        if (name.contains("DETR")) return "DET" 
        if (name.contains("ORLE")) return "NO " 
        if (name.contains("TAMP")) return "TB " 
        if (name.contains("CARO")) return "CAR" 
        if (name.contains("ATLA")) return "ATL" 
        if (name.contains("GIAN")) return "NYG" 
        if (name.contains("DALL")) return "DAL" 
        if (name.contains("WASH")) return "WAS" 
        if (name.contains("PHIL")) return "PHI" 
        if (name.contains("CHAR")) return "LAC" 
        if (name.contains("DENV")) return "DEN" 
        if (name.contains("OAKL")) return "OAK" 
        if (name.contains("KANS")) return "KC " 
        if (name.contains("BALT")) return "BAL" 
        if (name.contains("CLEV")) return "CLE" 
        if (name.contains("CINC")) return "CIN" 
        if (name.contains("PITT")) return "PIT" 
        if (name.contains("INDI")) return "IND" 
        if (name.contains("HOUS")) return "HOU" 
        if (name.contains("TENN")) return "TEN" 
        if (name.contains("JACK")) return "JAC" 
        if (name.contains("MIAM")) return "MIA" 
        if (name.contains("JETS")) return "NYJ" 
        if (name.contains("ENGL")) return "NE " 
        if (name.contains("BUFF")) return "BUF" 
		
		/*
        if (name.contains("NFC")) return "NFC" 
        if (name.contains("AFC")) return "AFC" 
        if (name.contains("SEA")) return "SEA" 
        if (name.contains("SFO")) return "SF " 
        if (name.contains("ARI")) return "ARI" 
        if (name.contains("LA")) return "LA " 
        if (name.contains("GB")) return "GB " 
        if (name.contains("MIN")) return "MIN" 
        if (name.contains("DET")) return "DET" 
        if (name.contains("NO ")) return "NO " 
        if (name.contains("TB ")) return "TB " 
        if (name.contains("CAR")) return "CAR" 
        if (name.contains("ATL")) return "ATL" 
        if (name.contains("GIA")) return "NYG" 
        if (name.contains("DAL")) return "DAL" 
        if (name.contains("WAS")) return "WAS" 
        if (name.contains("SDG")) return "SD "  //how many fucking times do i have to change this
        if (name.contains("DEN")) return "DEN" 
        if (name.contains("OAK")) return "OAK" 
        if (name.contains("KC ")) return "KC " 
        if (name.contains("CHI")) return "CHI"   // after KC
        if (name.contains("BAL")) return "BAL" 
        if (name.contains("CLE")) return "CLE" 
        if (name.contains("CIN")) return "CIN" 
        if (name.contains("PIT")) return "PIT" 
        if (name.contains("IND")) return "IND" 
        if (name.contains("HOU")) return "HOU" 
        if (name.contains("TEN")) return "TEN" 
        if (name.contains("JAX")) return "JAC" 
        if (name.contains("MIA")) return "MIA" 
        if (name.contains("PHI")) return "PHI"  // after MIA
        if (name.contains("JET")) return "NYJ" 
        if (name.contains("NE ")) return "NE " 
        if (name.contains("BUF")) return "BUF" 
		*/
        throw new IllegalStateException("Team name $name not found") 
		
    } 
} 


         
/***/
