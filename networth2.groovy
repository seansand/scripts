import java.time.YearMonth

import java.time.temporal.ChronoUnit

 

/**

* Calculates a value based on the exponential function:

* y = 416031.91 * e^(0.0107 * N)

* where N is the number of months elapsed since May 2010.

*/

def calculateExponentialValue() {

    // 1. Define Constants

    final double A = 416031.91 // The 'A' coefficient

    final double B = 0.0107    // The 'B' exponent coefficient

    final YearMonth START_DATE = YearMonth.of(2010, 5) // May 2010

 

    // 2. Get User Input

    println "Enter the month (1-12):"

    def monthInput = System.console()?.readLine() ?: new Scanner(System.in).nextLine()

    println "Enter the year (e.g., 2025):"

    def yearInput = System.console()?.readLine() ?: new Scanner(System.in).nextLine()

 

    try {

        int month = monthInput.toInteger()

        int year = yearInput.toInteger()

 

        // Input validation

        if (month < 1 || month > 12 || year < 2010) {

            println "\nError: Please enter a valid month (1-12) and a year 2010 or later."

            return

        }

 

        // 3. Calculate N (Number of months since May 2010)

        def targetDate = YearMonth.of(year, month)

 

        // ChronoUnit.MONTHS.between is an elegant way to get the difference in months

        // The cast to long is necessary for the calculation, but the result will fit in an int for N

        def N_long = ChronoUnit.MONTHS.between(START_DATE, targetDate)

        int N = N_long.toInteger()

 

        // 4. Calculate the Final Value (y)

        // Math.exp(x) calculates e^x

        double y = A * Math.exp(B * N)

 

        // 5. Output Result

        println "\n--- Results ---"

        println "Target Month/Year: ${targetDate}"

        println "Months Elapsed (N): ${N} months since ${START_DATE}"

        println "Formula Used: ${A} * e^(${B} * ${N})"

        println "Calculated Value: ${String.format('%.2f', y)}"

 

    } catch (NumberFormatException e) {

        println "\nError: Invalid input. Please enter valid numbers for month and year."

    } catch (Exception e) {

        println "\nAn unexpected error occurred: ${e.getMessage()}"

    }

}

 

// Execute the main function

calculateExponentialValue()