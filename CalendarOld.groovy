/*************************************************************************
 *  Compilation:  javac Calendar.java
 *  Execution:    java Calendar M Y
 *
 *  This program takes the month M and year Y and prints a
 *  calendar for that month.
 *
 *  % java Calendar 7 2005
 *   July 2005
 *   S  M  T  W Th  F  S
 *                  1  2
 *   3  4  5  6  7  8  9 
 *  10 11 12 13 14 15 16
 *  17 18 19 20 21 22 23
 *  24 25 26 27 28 29 30
 *  31
 * 
 *************************************************************************/
 
public class Calendar {
 
   /**********************************************************************
    *  Given the month (M), day (D), and year (Y), return which day
    *  of the week it falls on according to the Gregorian calendar.
    *  For M use 1 for January, 2 for February, and so forth. Outputs
    *  0 for Sunday, 1 for Monday, and so forth.
    **********************************************************************/
    public static Integer day(int M, int D, int Y)
    {
        int y = Y - (14 - M).intdiv(12);
        int x = y + y.intdiv(4) - y.intdiv(100) + y.intdiv(400)
        int m = M + 12 * ((14 - M).intdiv(12)) - 2;
        int d = (D + x + (31*m).intdiv(12)) % 7;
    }
 
    // return true if the given year is a leap year
    public static boolean isLeapYear(int year) {
        if  ((year % 4 == 0) && (year % 100 != 0)) return true;
        if  (year % 400 == 0) return true;
        return false;
    }
 
    public static void main(String[] args)
    {
        println()
        int M
        int Y
        Date now = new Date()
 
        if (args.length <= 0)
        {
           M = now.getMonth() + 1
        }
        else
        {
           M = Integer.parseInt(args[0]);    // month (Jan = 1, Dec = 12)
        }
 
        if (args.length <= 1)
           Y = now.getYear() + 1900
        else
           Y = Integer.parseInt(args[1]);    // year
 
        // months[i] = name of month i
        def months = [
             "",                               // leave empty so that months[1] = "January"
             "January", "February", "March",
             "April", "May", "June",
             "July", "August", "September",
             "October", "November", "December"
        ]
 
        // days[i] = number of days in month i
        def days = [ 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 ]
 
        // check for leap year
        if (M == 2 && isLeapYear(Y)) days[M] = 29;
 
 
        // print calendar header
        println("   " + months[M] + " " + Y);
        println(" S  M Tu  W Th  F  S");
 
        // starting day
        int d = day(M, 1, Y);
 
        // print the calendar
        for (int i = 0; i < d; i++)
            print("   ");
        for (int i = 1; i <= days[M]; i++) {
            printf("%2d ", i);
            if (((i + d) % 7 == 0) || (i == days[M])) println();
        }
 
        println()
    }
}
