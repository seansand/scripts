
Closure countClosure = 
{
   group ->

   if (null == delegate.get(group))
   {
      delegate.put(group, 1)
   }
   else
   {
      delegate.put(group, 1 + delegate.get(group))
   }
}  


HashMap.metaClass.count = countClosure

LinkedHashMap.metaClass.count = countClosure

TreeMap.metaClass.count = countClosure


File f = new File("c:\\temp\\text.txt")

def map = new HashMap<String, Integer>()

f.eachLine()
{
   line ->
   def tokens = line.split("\\s")
   
   tokens.each()
   {
      //println ("<$it>")
      map.count(it)
   }
   
}

println map