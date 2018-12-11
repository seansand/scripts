//Harmonic.groovy

BigDecimal sum = 0

int i = 1

100.times {

   sum += (1 / i)
   println("1/$i" + "  sum = " + sum)
   ++i
}

