//Easter.groovy

Integer.metaClass.intDiv = 
{ 
   (int)(delegate / it)
}
 
(2012..2020).each()
{ 
   assert compute(it) == compute2(it)
}

/**
 * Uses expandoMetaClass and inserts method intDiv.
 */
String compute2(int y)
{
   int c = y.intDiv(100);
   int n = y - 19 * (y.intDiv(19));
   int k = (c - 17).intDiv(25);
   int i = c - c.intDiv(4) - (c - k).intDiv(3) + 19 * n + 15;
   i = i - 30 * (i.intDiv(30));
   i = i - (i.intDiv(28)) * (1 - (i.intDiv(28)) * (29.intDiv((i + 1)))
       * ((21 - n).intDiv(11)));
   int j = y + y.intDiv(4) + i + 2 - c + c.intDiv(4);
   j = j - 7 * (j.intDiv(7));
   int L = i - j;
   int m = 3 + (L + 40).intDiv(44);
   int d = L + 28 - 31 * (m.intDiv(4));

   String retVal = y + " " + m + "/" + d  // "year month/date"
   println retVal
   retVal
}




/*
 * Old version (has to cast int)
 */
String compute(int y)
{
   int c = (int)(y / 100)
   int n = y - 19 * (int)(y / 19)
   int k = (int)((c - 17) / 25)
   int i = c - (int)(c / 4) - (int)((c - k) / 3) + 19 * n + 15
   i = i - 30 * (int)(i / 30)
   i = i - (int)(i / 28) * (1 - (int)(i / 28) * (int)(29 / (i + 1))
       * (int)((21 - n) / 11))
   int j = y + (int)(y / 4) + i + 2 - c + (int)(c / 4)
   j = j - 7 * (int)(j / 7)
   int L = i - j;
   int m = 3 + (int)((L + 40) / 44)
   int d = L + 28 - 31 * (int)(m / 4)

   String retVal = y + " " + m + "/" + d  // "year month/date"
   // println retVal
   retVal
}
