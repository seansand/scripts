(0..255).each()
{
  println it
  
  URL url = new URL("http://192.168.1." + it)
  
  
  try
  {
     def a = url.toString()
     def b = url.getText()
     println a
     println b
  }
  catch (Exception e)
  {
     println ("Fail: " + url.toString());
  }
  
}

