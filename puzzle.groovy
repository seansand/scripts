
//println addDigits(23)
//println(reverseDigits(1234))
//println(addRepeats(112344))
//println(sumOfFactors(28))


for (i in (10000..100000))
{
   int tax = calculateTax(i)
   println(i + ", " + tax)
   if (tax == 0) 
      break;
}
println ("Done.");



int calculateTax(int income)
{
   List<Integer> line = new ArrayList<Integer>(21);

   line[1] = income;
   line[2] = addDigits(line[1]);
   line[3] = figureOutLine3(line[1]);
   if (line[3] == -1)
      return -1
   else 
      println("Found a valid line 3");
   line[4] = addDigits(line[3]);
   line[5] = reverseDigits(line[3]);
   line[6] = Math.max(line[3] - line[5], 0);
   line[7] = Math.max(line[5] - line[3], 0);
   line[8] = line[6] + line[7];
   line[9] = addRepeats(line[1]);
   line[10] = sumOfFactors(line[3]);
   line[11] = Math.max(line[5] - line[10], 0);
   line[12] = Math.max(line[10] - line[5], 0);
   line[13] = line[11] + line[12]
   line[14] = line[2] + line[8] + line[10] +
              line[11] + line[12] + line[13]
   line[15] = line[4] + line[9] + line[10]
   line[16] = addDigits(line[2]) + addDigits(line[4]) +  addDigits(line[14])
   line[17] = addDigits(line[15]);
   line[18] = Math.max(line[16] - line[17], 0);
   line[19] = Math.max(line[17] - line[16], 0);
   line[20] = line[8] + line[9] + line[13] + line[18] + line[19]
   return line[20];
}


int sumOfFactors(int val)
{
   int retVal = 0;
   def list = factorsOf(val);
   list.each()
   {
      retVal += it
   }
   return retVal;

}


static Set<Integer> factorsOf(int val) 
{
    boolean selfFactorFlag = true;

    Set<Integer> numArray = new TreeSet<Integer>();

    for (int i = 2; i <= Math.ceil(Math.sqrt(val)); i++) 
    {
        if (val % i == 0) 
        {
            numArray.add(i);
            numArray.add(val.intdiv(i));
        }
    }
    
    if (selfFactorFlag)
       numArray.add(val);
    
    return numArray
}


int addRepeats(int lineOne)
{
   String str = "" + lineOne;
   (0..9).each()
   {
      String digit = "" + it;
      str = str.replaceFirst(digit, "");   
   }
   if (str == "") 
      str = "0"
   
   return addDigits(new Integer(str))

}


int figureOutLine3(int lineOne)
{
   for (int p3 = lineOne + 1; p3 < lineOne + 45; ++p3)
   {
      int p4 = addDigits(p3);
      println (p3 + "  " + p4)    
    
      if (p3 == lineOne + p4)
         return p3;
   }
   return -1
}


int reverseDigits(int number)
{
   String numberStr = "" + number;

   String retVal = "";
   numberStr.each()
   {
      retVal = it + retVal;
   }
   new Integer(retVal);
}

int addDigits(int number)
{
   int retVal = 0;
   String numberStr = "" + number;
   numberStr.each()
   {
      retVal += Integer.parseInt(it)
   }
   retVal;
}

