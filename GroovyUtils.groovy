//GroovyUtils.java

import org.apache.log4j.*

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



String.metaClass.safesubstring = 
{
   start, length ->
   try
   {
     return delegate.substring(start, length)
   }
   catch (Exception e)
   {
     return delegate.substring(Math.max(0, start))
   }
}            

String.metaClass.safesubstring = 
{
   start ->
   return delegate.substring(Math.max(0, start))
}   

// Must be a cleaner way to do this.  You must do String.LS()

String.metaClass.'static'.LS = 
{
   return System.getProperty("line.separator");                         
}

String.metaClass.ls = 
{
   return System.getProperty("line.separator");                         
}


Integer.metaClass.toCommas = 
{
   def df = new java.text.DecimalFormat("#####0");
   return df.format(d);
}

BasicConfigurator.configure();
System.setProperty("com.trgr.at.deliverydata.onuds","true");
System.setProperty("HAProvider.load.file","C:\\edev\\udsweb\\resources\\HAProviderConfig.xml");
System.setProperty("HAProvider.env","dev"); 
System.setProperty("com.trgr.at.deliverydata.transportConfigFile", "C:\\work\\DeliveryData\\z-prop\\dev_transportconfig.xml");

// return value

System.getProperty("line.separator");


  
