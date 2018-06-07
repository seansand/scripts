int TRIALS = 200000

Closure countClosure = 
{
   group ->

   if (null == delegate.get(group))
   {
      delegate.put(group, 1)
   }
   else
   {
      delegate.put(group, 100/TRIALS + delegate.get(group))        //This number * trials
   }
}  


HashMap.metaClass.count = countClosure

LinkedHashMap.metaClass.count = countClosure

TreeMap.metaClass.count = countClosure





Random r = new Random()

for (float n = 4; n < 20; n += 0.5)
{
   computeGaussians(n, TRIALS, r);
}

void computeGaussians(double gaussianMultiplier, int TRIALS, Random r)
{
   Map map = new LinkedHashMap();
   map.put(">35", 0)
   map.put(">28", 0)
   map.put(">11", 0)
   map.put(">7", 0)
   map.put(">3", 0) 

   
   TRIALS.times()
   {
      double a = r.nextGaussian() * gaussianMultiplier   

           if (a > 35.0) map.count(">35")
      else if (a > 28.0) map.count(">28")
      else if (a > 11.0) map.count(">11")
      else if (a > 7.0)  map.count(">7")
      else if (a > 3.0)  map.count(">3") 
   }

   print(gaussianMultiplier + "==   ");
   map.each() {
      k, v ->
   
      print("$k : ${Math.round(v * 10) / 10}%   ");
   }
   println();
   
}




