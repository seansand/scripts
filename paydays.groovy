//Paydays.groovy

Date date = new Date();



def format = new java.text.SimpleDateFormat("MM/dd/yyyy");


(26 * 14).times()
{
   
   //println daysSinceFirstPayDay(date)
   
   if (daysSinceFirstPayDay(date) % 14 == 0)
      println(format.format(date))

   date += 1
}



def daysSinceFirstPayDay(Date thisDate)
{
   //Date firstPayDay = new Date(1231518876423);  //(1/9/2009)
   Date firstPayDay = new Date(1231480800000)

   long millis = thisDate.getTime() - firstPayDay.getTime()

   Math.floor(millis / (1000 * 60 * 60 * 24))
}
