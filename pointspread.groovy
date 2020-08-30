//Pointspread.groovy

final double POINT_CONSTANT = 13.86  // From Stern document

final int TRIALS = 499999  // Bigger = more accurate but slower

Random rng = new Random(1)

(0..250).step(5)
{
   double pointspread = it / 10
   int winCount = 0;
   int loseCount = 0;

   TRIALS.times()
   {
      double r = rng.nextGaussian()
      r *= POINT_CONSTANT

      if (pointspread + r >= 0.0)
      {
         ++winCount
      }
	  else
	  {
	     ++loseCount
	  }
   }

   println("$pointspread:\t${percentage(winCount/TRIALS)} / ${percentage(loseCount/TRIALS)}  ")
}

double percentage(double x) {
   double y = x * 1000
   y = Math.round(y)
   y = y/10
   return y
}





