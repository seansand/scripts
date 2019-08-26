//Math.groovy

// If a,b,c,d are positive integers with a sum of 63, what is the maximum value
// of ab+bc+cd

def rangea = (1..61)
def rangeb = (1..61)
def rangec = (1..61)
def ranged = (1..61)

int max = 0;
int result = 0;
String maxString = '';

for (int a in rangea) {
   for (int b in rangeb) {
      for (int c in rangec) {
         for (int d in ranged) {
         
            if (a + b + c + d == 63) {
            
               result = a*b + b*c + c*d
               max = Math.max(result, max)
               if (max == result) { 
                  maxString = "$a, $b, $c, $d: $a*$b + $b*$c + $c*$d == $result which is max so far."
                  println maxString
               }
               
            } // else do nothing
         
         }
      }
   }
}
