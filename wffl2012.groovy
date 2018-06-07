

//Wffl.groovy
public class Wffl
{
   static
   {
      //Properties props = System.getProperties();
      //props.put("http.proxyHost", "webproxy.int.westgroup.com");
      //props.put("http.proxyPort", "80");
      pointsList.add(0.5); pointsList.add(1.0); pointsList.add(1.5);
      pointsList.add(2.0); pointsList.add(2.5); pointsList.add(3.0);
      pointsList.add(3.5); pointsList.add(4.0); pointsList.add(4.5);
      pointsList.add(5.0); pointsList.add(5.5); pointsList.add(7.5);  // was 9.0
   }
   
   private static final String RESULTS_URL =
      "http://football8.myfantasyleague.com/2012/export?TYPE=weeklyResults&L=33878&W=#WEEKNBR#"
   private static final LinkedList pointsList = new LinkedList()
   private static def teamMap = null;
   public static void main(String[] args)
   {
      println()
      int weekNumber
      try
      {
         if (args.size() != 1)
         {
            throw new IllegalArgumentException("Expecting 1 argument (week number).")
         }
         weekNumber = Integer.parseInt(args[0])
      }
      catch (Exception e)
      {
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
         printStandings()
      }
   }   
   public static void printStandings()
   {
      def scoreList = []
      teamMap.values().each()
      {
         scoreList.add(new Score(it.getTotalSp(), it))
      }
      scoreList = scoreList.sort().reverse()
      println()
      println("STANDINGS:")
      println()
      scoreList.each()
      {
         println(it.getTeam().toString())
      }
      println()
   }
   public static void printEntryOrder()
   {
      println()
      println("ENTER:")
      println()
      println(teamMap.get("0006").toCurrentSpString()) // Burrested Development     
      println(teamMap.get("0010").toCurrentSpString()) // Soli Deo Gloria
      println(teamMap.get("0004").toCurrentSpString()) // SPAM
      println(teamMap.get("0005").toCurrentSpString()) // Beware of Doug
      println(teamMap.get("0003").toCurrentSpString()) // CRUD                                                      
      println(teamMap.get("0002").toCurrentSpString()) // Spinal Tappers
      println(teamMap.get("0008").toCurrentSpString()) // Hammer
      println(teamMap.get("0007").toCurrentSpString()) // Kickass Kamikaze
      println(teamMap.get("0001").toCurrentSpString()) // Team America
      println(teamMap.get("0012").toCurrentSpString()) // Sean's
      println(teamMap.get("0009").toCurrentSpString()) // KOD's
      println(teamMap.get("0011").toCurrentSpString()) // FleaFlickers
 
   }
   public static void initMap()
   {
      String s
      teamMap = new TreeMap();
      s = "0001"; teamMap.put(s, new Team(s, "Team America"))
      s = "0002"; teamMap.put(s, new Team(s, "Spinal Tappers"))
      s = "0003"; teamMap.put(s, new Team(s, "CRUD"))
      s = "0004"; teamMap.put(s, new Team(s, "SPAM"))
      s = "0005"; teamMap.put(s, new Team(s, "Beware of Doug"))
      s = "0006"; teamMap.put(s, new Team(s, "Bur. Development"))
      s = "0007"; teamMap.put(s, new Team(s, "Kickass Kamikaze"))
      s = "0008"; teamMap.put(s, new Team(s, "Hammer"))
      s = "0009"; teamMap.put(s, new Team(s, "KOD's"))
      s = "0010"; teamMap.put(s, new Team(s, "Soli Deo Gloria"))
      s = "0011"; teamMap.put(s, new Team(s, "FleaFlickers"))
      s = "0012"; teamMap.put(s, new Team(s, "P. Knife Surprise"))
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
   String id
   String name
   BigDecimal currentSp = 0.0
   BigDecimal totalSp = 0.0
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
      "$dispName$dispTotalSp+ $dispCurrentSp"
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
      "$dispName+ $dispCurrentSp"
   }
}
