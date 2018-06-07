//Forecast.groovy

// From Stern document...translates point spread into odds of winning

public class Forecast
{
    public static void main(String[] args)
	{
	    println("-15: " + homePercentage(-15))
		println(" 15: " + homePercentage(15))
	
	}


    public static double homePercentage(double homePointSpread)
	{
		final int TRIALS = 200000  //was 1500000

		Random r = new Random()

		int wincount = 0

		TRIALS.times()
		{
		   double a = r.nextGaussian() * 13.86 + homePointSpread
		   
		   if (a < 0.0)
			  ++wincount   
		}

		//println "$wincount wins out of $TRIALS"
		//println wincount / TRIALS
		return Math.round(100 * wincount / TRIALS)
	}
}