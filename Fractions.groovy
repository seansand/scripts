//Fractions.groovy

@Grab(group='org.apache.commons', module='commons-math3', version='3.0')
import org.apache.commons.math3.fraction.*


Fraction f = new Fraction(4, 5)
Fraction g = new Fraction(1, 2)

println f.add(g)

println("done")