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
   }
   
   private static final String YEAR = "2019";
   private static final String MFL_NBR = "25686";
   private static int TEAM_COUNT;
   
   private static final String RESULTS_URL =
      "http://www.myfantasyleague.com/" + YEAR + "/export?TYPE=weeklyResults&L=" + MFL_NBR + "&W=#WEEKNBR#"
   
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
		 
         for (i in (0..X))   //X == 5 or 6 depending on 10 or 12 teams
         {
            int inPlayoffs = scoreList[i].getTeam().getInPlayoffs();
            scoreList[i].getTeam().setInPlayoffs(inPlayoffs + 1);
         }
      }
      
      printEntryOrder();

      // playoff percentage is now v.inPlayoffs / TRIALS * 100
      
   }
   
   
   // This is the number in URLs (e.g. ?L=24330&F=0001&O=07 for Team America)
   
   private static final String TEAM_CHAR = "0001";
   private static final String TEAM_ADAM = "0002";
   private static final String TEAM_CRUD = "0003";
   private static final String TEAM_ZOMB = "0004";
   private static final String TEAM_GGRI = "0005";
   private static final String TEAM_BOOM = "0006";
   private static final String TEAM_KICK = "0007";
   private static final String TEAM_HAMM = "0008";
   private static final String TEAM_LEVE = "0009";
   private static final String TEAM_SDGL = "0010";
   
   public static void printEntryOrder()
   {
      println()
      println("ENTER:")   //This should be the order that appears on the Standings Adjustment page
      println()                                        
      println(teamMap.get(TEAM_CHAR).toCurrentSpString()) 
      println(teamMap.get(TEAM_ADAM).toCurrentSpString()) 
      println(teamMap.get(TEAM_CRUD).toCurrentSpString()) 
      println(teamMap.get(TEAM_ZOMB).toCurrentSpString()) 
      println(teamMap.get(TEAM_GGRI).toCurrentSpString()) 
      println(teamMap.get(TEAM_BOOM).toCurrentSpString()) 
      println(teamMap.get(TEAM_KICK).toCurrentSpString()) 
      println(teamMap.get(TEAM_HAMM).toCurrentSpString()) 
      println(teamMap.get(TEAM_LEVE).toCurrentSpString()) 
      println(teamMap.get(TEAM_SDGL).toCurrentSpString()) 
 
   }
   public static void initMap()
   {
      String s
      teamMap = new TreeMap();  
      s = TEAM_CHAR; teamMap.put(s, new Team(s, "CharlieHorse"))
      s = TEAM_ADAM; teamMap.put(s, new Team(s, "Adam Schefter"))
      s = TEAM_CRUD; teamMap.put(s, new Team(s, "CRUD"))
      s = TEAM_ZOMB; teamMap.put(s, new Team(s, "Zombie Pres."))
      s = TEAM_GGRI; teamMap.put(s, new Team(s, "G. Grinders"))
      s = TEAM_BOOM; teamMap.put(s, new Team(s, "BoomShakaLaka"))
      s = TEAM_KICK; teamMap.put(s, new Team(s, "K. Kamikaze"))
      s = TEAM_HAMM; teamMap.put(s, new Team(s, "Hammer"))
      s = TEAM_LEVE; teamMap.put(s, new Team(s, "Le'V. Prayer"))
      s = TEAM_SDGL; teamMap.put(s, new Team(s, "S.D. Gloria"))
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
      def weeklyResults =
         new XmlSlurper().parseText(u.getText())
         
      //println u.getText()   //TODO SRS TEMP
         
      LinkedList scoresList = new LinkedList()
      def allMatchups = weeklyResults.matchup
      //println (allMatchups.size())
      allMatchups.each()
      {
         def twoFranchises = it.franchise
         int score0 = Integer.parseInt(twoFranchises[0].@score.text())
         int score1 = Integer.parseInt(twoFranchises[1].@score.text())
         String id0 = twoFranchises[0].@id.text()
         String id1 = twoFranchises[1].@id.text()
        
/*        
         println("id0 $id0")  // TODO SRS TEMP
         println("id1 $id1")
         teamMap.each{ k, v ->
            println("$k :: $v")
         }
*/         
         if (score0 > score1)
         {
            teamMap.get(id0).addSp(6.0)
         }
         else if (score1 > score0)
         {
            teamMap.get(id1).addSp(6.0)
         }
         else if (score1 == score0)
         {
            teamMap.get(id0).addSp(3.0)
            teamMap.get(id1).addSp(3.0)
         }
         //println "$score0 $score1 <="
         scoresList.add(new Score(score0, teamMap.get(id0)))
         scoresList.add(new Score(score1, teamMap.get(id1)))
      }
      
      /* SRS Mean Machine Note **
      
      If using "Mean Machine", sum scoresList, take the average (divide by 11), then replace the zero score with the average (after sorting)
      
      2015 Note: This may never be necessary because the MyFL system has some facility for doing mean machine; try that first.
      
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
   public static int TRIALS = 50000;

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
      quotient *= 100   
      quotient = Math.round(quotient)
      //quotient = quotient / 100
      return quotient + "%  (" + a +")";
   }

}
