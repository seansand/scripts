//Wffl.groovy

public class Wffl
{
   private static final LinkedList pointsList = new LinkedList()

   static
   {
      //Properties props = System.getProperties();
      //props.put("http.proxyHost", "webproxy.int.westgroup.com");
      //props.put("http.proxyPort", "80");
      
	  // TEAM COUNT = 12
      //pointsList.add(0.5); pointsList.add(1.0); pointsList.add(1.5);
      //pointsList.add(2.0); pointsList.add(2.5); pointsList.add(3.0);
      //pointsList.add(3.5); pointsList.add(4.0); pointsList.add(4.5);
      //pointsList.add(5.0); pointsList.add(5.5); pointsList.add(7.5);  // was 9.0
	  
  	  // TEAM COUNT = 10
      pointsList.add(0.5); pointsList.add(1.0); pointsList.add(1.5);
      pointsList.add(2.0); pointsList.add(2.5); pointsList.add(3.0);
      pointsList.add(3.5); pointsList.add(4.0); pointsList.add(4.5);
                                                pointsList.add(6.5); 
                                                
  	  // TEAM COUNT = 11
      //pointsList.add(0.5); pointsList.add(1.0); pointsList.add(1.5);
      //pointsList.add(2.0); pointsList.add(2.5); pointsList.add(3.0);
      //pointsList.add(3.5); pointsList.add(4.0); pointsList.add(4.5);
      //pointsList.add(5.0);                      pointsList.add(7.0);                                                 
                                                
   }
   
   private static final String YEAR = "2020";
   private static final String MFL_NBR = "25686";
   private static int TEAM_COUNT;
   
   private static final String RESULTS_URL =
      "https://api.myfantasyleague.com/" + YEAR + "/export?TYPE=weeklyResults&L=" + MFL_NBR + "&W=#WEEKNBR#"
      //was   "http://www.myfantasyleague.com/" + YEAR + "/export?TYPE=weeklyResults&L=" + MFL_NBR + "&W=#WEEKNBR#"
   
   private static def teamMap = null;
   
   public static void main(String[] args)
   {
      TEAM_COUNT = pointsList.size()
      println()
      int weekNumber
      try
      {
         if (args.size() != 1)
         {
            def systemIn = new BufferedReader(new InputStreamReader(System.in))
            print("Enter week number: ");
            weekNumber = Integer.parseInt(systemIn.readLine());
         }
         else
         {
            weekNumber = Integer.parseInt(args[0])
         }
      }
      catch (Exception e)
      {
         e.printStackTrace();
         println("Invalid arguments for WFFL: $args (expecting week number)")
         println e.getMessage()
         return
      }
      
      initMap();
      
      (1..weekNumber).each()
      {
         println("---------")
         println(" WEEK $it")
         println("---------")
         getWeekResults(it)
         printEntryOrder()
         printStandings(it, weekNumber)
      }
   }   
   
   public static void printStandings(def curr, def weekNumber)
   {
      if (curr == weekNumber)
      {
         doPlayoffProjections(weekNumber);
      }
   
      def scoreList = []
      teamMap.values().each()
      {
         scoreList.add(new Score(it.getTotalSp(), it))
      }
      scoreList = scoreList.sort().reverse()
      println()
      
      if (curr == weekNumber)
      {
         println("Hit [Enter] to confirm STANDINGS: ")
         def systemIn = new BufferedReader(new InputStreamReader(System.in))
         systemIn.readLine()
      }
      
      scoreList.each()
      {
         println(it.getTeam().toString())
      }
      println()
   }


   static void showMap()   //temp, for debugging
   {
      teamMap.each()
      {
         k, v ->
         println("$k: ${v.getName()} ; ${v.getTotalSp()} ; ${v.getProjectedSp()} ; ${v.getInPlayoffs()} ")
      }
      println()
   }   
   
   static void doPlayoffProjections(int weekNumber)
   {
      println("\n-----------------------------")
      println("Now doing playoff projections")
      println("-----------------------------\n")
   
      Team.TRIALS.times()
      {
         teamMap.values().each()
         {
            it.setProjectedSp(it.getTotalSp());
         }
      
         (weekNumber..<17).each()
         {
            //Shuffle teams and assign each some projectedSp randomly.
            List shuffledTeams = new ArrayList(teamMap.keySet());
            Collections.shuffle(shuffledTeams);
            
            for (i in (0..<pointsList.size()))
            {
               Team currTeam = teamMap.get(shuffledTeams[i]);
               BigDecimal proj = currTeam.getProjectedSp();
               
               def addPoints = proj + pointsList[i];
               if (pointsList[i] > 3.25)
                  addPoints += 6.0;  // assume that top six all win
               
               currTeam.setProjectedSp(addPoints);
            }
         }
      
         // Sort by getProjectedSp().
      
         def scoreList = []
         teamMap.values().each()
         {
            scoreList.add(new Score(it.getProjectedSp(), it))
         }
         scoreList = scoreList.sort().reverse()

		   int X = TEAM_COUNT / 2 - 1
		 
         for (i in (0..X))   // TOP X teams depends on TEAM_COUNT...10 or 11 => 5, 12 => 6
         {
            int inPlayoffs = scoreList[i].getTeam().getInPlayoffs();
            scoreList[i].getTeam().setInPlayoffs(inPlayoffs + 1);
         }
      }
      
      printEntryOrder();

      // playoff percentage is now v.inPlayoffs / TRIALS * 100
      
   }
   
   
   // This is the number in URLs (e.g. ?L=24330&F=0001&O=07 for Team America)
   
   private static final String TEAM1 = "0001";
   private static final String TEAM2 = "0002";
   private static final String TEAM3 = "0003";
   private static final String TEAM4 = "0004";
   private static final String TEAM5 = "0005";
   private static final String TEAM6 = "0006";
   private static final String TEAM7 = "0007";
   private static final String TEAM8 = "0008";
   private static final String TEAM9 = "0009";
   private static final String TEAM10 = "0010";
   
   public static void printEntryOrder()
   {
      println()
      println("ENTER:")   //This should be the order that appears on the Standings Adjustment page
      println()                                        
      println(teamMap.get(TEAM1).toCurrentSpString()) 
      println(teamMap.get(TEAM2).toCurrentSpString()) 
      println(teamMap.get(TEAM3).toCurrentSpString()) 
      println(teamMap.get(TEAM4).toCurrentSpString()) 
      println(teamMap.get(TEAM5).toCurrentSpString()) 
      println(teamMap.get(TEAM6).toCurrentSpString()) 
      println(teamMap.get(TEAM7).toCurrentSpString()) 
      println(teamMap.get(TEAM8).toCurrentSpString()) 
      println(teamMap.get(TEAM9).toCurrentSpString()) 
      println(teamMap.get(TEAM10).toCurrentSpString()) 
   }
   public static void initMap()
   {
      String s
      teamMap = new TreeMap();  
      s = TEAM1; teamMap.put(s,  new Team(s, "CharlieHorse"))
      s = TEAM2; teamMap.put(s,  new Team(s, "Adam Schefter"))
      s = TEAM3; teamMap.put(s,  new Team(s, "CRUD"))
      s = TEAM4; teamMap.put(s,  new Team(s, "Fool Pitiers"))
      s = TEAM5; teamMap.put(s,  new Team(s, "Grace's Team"))
      s = TEAM6; teamMap.put(s,  new Team(s, "Zach's Team"))
      s = TEAM7; teamMap.put(s,  new Team(s, "K. Kamikaze"))
      s = TEAM8; teamMap.put(s,  new Team(s, "Hammer"))
      s = TEAM9; teamMap.put(s,  new Team(s, "Le'V. Prayer"))
      s = TEAM10;teamMap.put(s,  new Team(s, "S.D. Gloria"))
   }   
   public static void getWeekResults(int weekNumber)
   {
      // Clear current standing Points
      teamMap.values().each()
      {
         it.clearCurrentSp()
      }
      String urlString = RESULTS_URL.replace("#WEEKNBR#", weekNumber + "")
      
      URL u = new URL(urlString)
      
      //temp
      //println urlString
      //println u.getText()
      
      String urlText = u.getText()    //this is the real code
      
      //urlText = TestText.getText()  //use when testing only
      
      def weeklyResults =
         new XmlSlurper().parseText(urlText)
         
      //println u.getText()   //TODO SRS TEMP
         
      LinkedList scoresList = new LinkedList()
      def allMatchups = weeklyResults.matchup
      //println (allMatchups.size())
      allMatchups.each()
      {
         BigDecimal score0
         BigDecimal score1
      
         def twoFranchises = it.franchise
         try {
            score0 = new BigDecimal(twoFranchises[0].@score.text())
            score1 = new BigDecimal(twoFranchises[1].@score.text())
         }
         catch (NumberFormatException e) {
            println("Problem reading scores from $u, probably because score data is not there yet.")
            throw e;
         }
         String id0 = twoFranchises[0].@id.text()
         String id1 = twoFranchises[1].@id.text()
        
/*        
         println("id0 $id0")  // TODO SRS TEMP
         println("id1 $id1")
         teamMap.each{ k, v ->
            println("$k :: $v")
         }
*/         
         if (score0 > score1 && id0 != "AVG")
         {
            teamMap.get(id0).addSp(6.0)
         }
         else if (score1 > score0 && id1 != "AVG")
         {
            teamMap.get(id1).addSp(6.0)
         }
         else if (score1 == score0)
         {
            if (id0 != "AVG")
               teamMap.get(id0).addSp(3.0)
            if (id1 != "AVG")   
               teamMap.get(id1).addSp(3.0)
         }
         
         if (id0 != "AVG")
            scoresList.add(new Score(score0, teamMap.get(id0)))
            
         if (id1 != "AVG")   
            scoresList.add(new Score(score1, teamMap.get(id1)))
      }
      
      /* SRS Mean Machine Note **
      
      If using "Mean Machine", sum scoresList, take the average (divide by 11), then replace the zero score with the average (after sorting)
      
      2015 Note: This may never be necessary because the MyFL system has some facility for doing mean machine; try that first.
      
      2020 Note: I am indeed trying to accommodate the MyFL way of doing it.
      
      /**/
      
      
      scoresList.sort()
      //println "ScoresList = $scoresList"
      // Now add bonus points
      List currentGroup = new ArrayList()
      int currentPoints;
      List points = pointsList.clone() 
      def total = 0.0
      // Repeat this till scoresMap is empty
      while (scoresList.size() > 0)
      {
         currentPoints = scoresList.get(0).getScore()
         currentGroup.add(scoresList.remove())
         total += points.remove()
         while (scoresList.size() > 0 &&
                scoresList.get(0).getScore() == currentPoints)
         {
            currentGroup.add(scoresList.remove())
            total += points.remove()
         }
         // currentGroup is now correctly populated.
         currentGroup.each()
         {
            it.getTeam().addSp(total / currentGroup.size())
         }
         currentGroup.clear()
         total = 0.0
      }
      //println("teamMap = $teamMap")
   }  // end getWeekResults  
}
class Score implements Comparable
{
   BigDecimal score
   Team team
   Score(BigDecimal score, Team team)
   {
      this.score = score
      this.team = team
   }
   public int compareTo(Object anotherScore) throws ClassCastException
   {
      if (!(anotherScore instanceof Score))
         throw new ClassCastException("Score object expected.");
      BigDecimal diff = this.getScore() - anotherScore.getScore()
      if (diff > 0) return 1
      else if (diff < 0) return -1
      else return 0
   }
   public String toString()
   {
      score + "-" + team.toString()
   }
}
class Team
{
   public static int TRIALS = 200000;

   String id
   String name
   BigDecimal currentSp = 0.0
   BigDecimal totalSp = 0.0
   
   int inPlayoffs = 0;
   BigDecimal projectedSp = 0.0;
   
   Team(String id, String name)
   {
      this.id = id
      this.name = name
   }
   void clearCurrentSp()
   {
      this.currentSp = 0.0
   }
   void addSp(BigDecimal sp)
   {
      this.currentSp += sp
      this.totalSp += sp
   }
   public String toString()
   {
      String dispName
      String dispTotalSp
      String dispCurrentSp
      dispName = name;
      while (dispName.length() < 20)
         dispName += " "
      dispTotalSp = totalSp.toString()
      while (dispTotalSp.length() < 8)
         dispTotalSp += " "
      dispCurrentSp = currentSp.toString()
      while (dispCurrentSp.length() < 8)
         dispCurrentSp += " "

      String percentStr = getPercentage(inPlayoffs, TRIALS);
         
      return "$dispName$dispTotalSp+ $dispCurrentSp \t $percentStr"
   }
   public String toCurrentSpString()
   {
      String dispName
      String dispCurrentSp
      dispName = name;
      while (dispName.length() < 20)
         dispName += " "
      dispCurrentSp = currentSp.toString()
      while (dispCurrentSp.length() < 8)
         dispCurrentSp += " "
      
      String percentStr = getPercentage(inPlayoffs, TRIALS);
       
      return "$dispName+ $dispCurrentSp \t $percentStr" 
   }
   
   public static String getPercentage(def a, def b)
   {
      def quotient = (b == 0) ? 0 : a / b
      def roundNumberQuotient
      def round3DecimalsQuotient
      
      roundNumberQuotient = quotient * 100   
      roundNumberQuotient = Math.round(roundNumberQuotient)
      roundNumberQuotient = (roundNumberQuotient + '%     ').substring(0, 5)

      round3DecimalsQuotient = quotient * 100
      //round3DecimalsQuotient = Math.round(roundNumberQuotient)
      round3DecimalsQuotient = (round3DecimalsQuotient + '     ').substring(0, 5)

      //return roundNumberQuotient + "%  (" + a +")";
      
      return roundNumberQuotient + "(" + round3DecimalsQuotient + ") " + a;
   }

}

public class TestText
{
   public static String getText()
   {
      return '''
         <weeklyResults week="1">
           <matchup regularSeason="1">
             <franchise score="10" id="0005" spread="-0" isHome="0" result="L"></franchise>
             <franchise score="20" id="0006" spread="0" isHome="1" result="W"></franchise>
           </matchup>
           <matchup regularSeason="1">
             <franchise score="11" id="0007" spread="-0" isHome="0" result="L"></franchise>
             <franchise score="12" id="0008" spread="0" isHome="1" result="W"></franchise>
           </matchup>
           <matchup regularSeason="1">
             <franchise score="14" id="0009" spread="-0" isHome="0" result="T"></franchise>
             <franchise score="14" id="0010" spread="0" isHome="1" result="T"></franchise>
           </matchup>
           <matchup regularSeason="1">
             <franchise score="50" id="0001" spread="-0" isHome="0" result="W"></franchise>
             <franchise score="2"  id="0004" spread="0" isHome="1" result="L"></franchise>
           </matchup>
           <matchup regularSeason="1">
             <franchise score="3"  id="0002" spread="-0" isHome="0" result="L"></franchise>
             <franchise score="7"  id="0003" spread="0" isHome="1" result="W"></franchise>
           </matchup>
         </weeklyResults>
   '''
   }
}
