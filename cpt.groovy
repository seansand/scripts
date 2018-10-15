//cpt.groovy

@Grapes(
@Grab(group='joda-time', module='joda-time', version='2.9.9')
)

import org.joda.time.LocalDate
import org.joda.time.Days

String dropboxRoot = [/C:\Users\Seans\Dropbox/,
                      /C:\Users\Sean\Dropbox/,
                      /C:\Users\U0038026\Dropbox/].find{new File(it).exists()}

File f = new File(dropboxRoot + /\Documents\cpt.txt/)

if (args.size() == 0)
{
   println f.text
   println()
   return
}

String input = args[0].toLowerCase()

LocalDate enteredDate

if (input.startsWith("t")) 
{
   print("today ")
   enteredDate = LocalDate.now()
}

else if (input.startsWith("y")) 
{
   print("yesterday ")
   enteredDate = LocalDate.now().minusDays(1)
}
else
{
   enteredDate = LocalDate.parse(input)
}

println enteredDate
println()

List<Integer> averageList = []
List dates = []
List lineArray = []
List newData = []
LocalDate lastDate = LocalDate.parse("2017-01-01")

f.eachLine { lineArray << it.trim() }

for (String line : lineArray)
{
   if (line == "")
      break

   Integer averageValue
   if (line.contains("(")) {
      String averageValue = line.find(/\([0-9][0-9]\)/)        
      if (averageValue)
         averageList << new Integer(averageValue)
   } 
  
   List lineTokens = line.split(" ")
   LocalDate thisDate = LocalDate.parse(lineTokens[0])
   int daysBetween = Days.daysBetween(thisDate, enteredDate).getDays()
   
   if (Math.abs(daysBetween) < 10)
   {
        int duration = Days.daysBetween(lastDate, enteredDate).getDays()
		newData << enteredDate.toString() + "  ($duration)"
		5.times() 
		{
		   enteredDate = enteredDate.plusDays(28)
		   newData << enteredDate.toString()	
		}
		break
   }
   else
   {
       newData << line
   }
   
   lastDate = thisDate
   
}

newData << ""
newData << "Average = ${(averageList.sum() / averageList.size())}"
newData << "To modify dates, enter 'today', 'yesterday' or 'YYYY-MM-DD' as a single argument."

f.write("")
newData.each
{
   println it
   f.append(it + System.getProperty('line.separator'))
}
