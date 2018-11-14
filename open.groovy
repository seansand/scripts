//Open.groovy

class Open //implements Runnable
{
   static final def TOPLEVEL = new File("c:\\groovy\\scripts\\")
   String fileName

   public static void main(String[] args)
   {
      if (args.length == 0)
      {
         println("No file name specified.")
         return
      }

      String fileName = args[0]
      
      boolean done = false
      
      if (!fileName.contains(".groovy"))
      {
         fileName += ".groovy"
      }
      
      if (!search(TOPLEVEL, fileName))
         println("Done.")

      /*
      def runnable = new Open(fileName);
      def t = new Thread(runnable);
      t.start();
      t.join(500);  // 1/2 seconds.
      println ("Done.")
      */
   }
          

   public Open(String fileName)
   {
      this.fileName = fileName
   }

   public void run()
   {
      search(TOPLEVEL, fileName)
   }
   
   ////
   
   static boolean search(File directory, String fileName)
   {
      def retval = false;

      directory.eachFile()
      {
         if (fileName.equalsIgnoreCase(it.getName()))
         {
            String execStr = '"C:\\Program Files (x86)\\Notepad++\\notepad++.exe"'
            println execStr + it.getAbsolutePath()
            Process proc0 = Runtime.getRuntime().exec(execStr + it.getAbsolutePath());
            retval = true;
         }
      }

      if (!retval)
      {
         directory.eachDir()
         {
            if (search(it, fileName))
            {
               return true
            }
         }
      }
      return retval;
   }
}


