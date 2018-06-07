(0..255).each()
{
  URL url = new URL("http://192.168.1." + it)
  URL url2 = new URL("http://10.0.0." + it)

  Tester t = new Tester(url);  
  Tester t2 = new Tester(url2);
  t.start();
  t2.start();
  
}


class Tester extends Thread
{
    URL url;
    
    Tester(URL inU)
    {
       url = inU;
    }
    
    void run()
    {
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


}



