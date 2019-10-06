//nnfp538.groovy
String pickAndPercentage = '''

CHI 64
MIN 59
BAL 61
NYJ 55
KC  58
LAR 52
CLE 60
PHI 77
SEA 75
LAC 72
DET 51
DAL 74
TB  55
NE  68
NO  68
OAK 51

'''

String site1 = "http://projects.fivethirtyeight.com/2019-nfl-predictions"
String site2 = "http://seansand.appspot.com/nnfpsort.html"


class Matchup
{
   String team
   Integer percent
}
 
 
List matchupList = []
 
pickAndPercentage.eachLine()
{
   line ->
   
   line = line.replaceAll("\\s+", " ")
   
   if (line.trim() != "")
   {
      List token = line.split(" ")
	  
	  Integer i = new Integer(token[1])
	  if (i >= 50)
         matchupList.add(new Matchup(team: token[0], percent: i))
      else
	     matchupList.add(new Matchup(team: "~" + token[0], percent: (100-i)))
   }
  
   matchupList = matchupList.sort{a, b -> a.percent <=> b.percent}
}
 
println()
matchupList.eachWithIndex()
{
   matchup, i ->
 
   if (matchup.team.size() == 2)
      matchup.team = matchup.team + " "
   matchup.team = matchup.team.toUpperCase()
 
   println(matchup.team + " " + formatInt(1 + i) + "        " + matchup.percent)
}
println()
println(site1)
println(site2)
println()
 
String formatInt(Integer i)
{
   if (i >= 10) return "" + i
   else return " " + i
}