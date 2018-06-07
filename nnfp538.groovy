//nnfp538.groovy
String pickAndPercentage = '''

min 99
pit 98
phi 77
det 64
ind 56
ne 91
was 61
sea 75
mia 51
atl 61
bal 77
jac 50
kc 65
no 73
lac 71
lar 85

'''





class Matchup
{
   String team
   Integer percent
}
 
 
List matchupList = []
 
pickAndPercentage.eachLine()
{
   line ->
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
println('''
http://projects.fivethirtyeight.com/2017-nfl-predictions
'''.trim())
println(
'''
http://seansand.appspot.com/nnfpsort.html
'''.trim())
println()
 
String formatInt(Integer i)
{
   if (i >= 10) return "" + i
   else return " " + i
}