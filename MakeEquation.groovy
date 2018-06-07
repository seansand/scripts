//MakeEquation.groovy

//Math.log = log to base e

println("---")

for (int i = 3600; i >= 0; i-= 60)
{
   makeEq(i);
}






void makeEq(double x)
{

   println x + " : " +   
   
   (3.77 * Math.log((x + 240)/100 + 1))

}


//   (6 +  2.18 * Math.log((x/100 + 1))

//   (6 +  0.96 * Math.log(x + 1))
