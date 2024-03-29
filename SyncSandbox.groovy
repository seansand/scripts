//SyncSandbox.groovy

// Similar to BackupSilver, but just does a sync 
// (it doesn't copy files that are already present).
// This should run much faster than BackupSilver.
// Modified March 2022


public class SyncSandbox
{
   static int counter = 0
   static int copyCounter = 0

   public static void main(String[] args)
   {
		// List of Sync directories here.  Remote "from", then local "to".
			
      final String DL = "D:\\";  //DRIVE LETTER D FOR OLD DRIVE
      //final String DL = "F:\\";  //DRIVE LETTER F FOR MY PASSPORT
         
		Map map = new HashMap();
      
      
      map.put("C:\\Users\\Sean\\Documents", 
        DL + "backup-DS2017\\Users\\Sean\\Documents");

      //map.put("E:\\Music", 
      //  DL + "backup-DS2017\\Users\\Sean\\E_Drive_Music");
        
      //map.put("C:\\Users\\Sean\\Music", 
      //  DL + "backup-DS2017\\Users\\Sean\\Music");

      //map.put("Z:\\Archived Music", 
      //  DL + "backup-DS2017\\Users\\Sean\\Music\\Amazon Music");

      //map.put("Z:\\My Send-to-Kindle Docs", 
      //  DL + "backup-DS2017\\Amazon\\My Send-to-Kindle Docs");
      
      //map.put("C:\\Users\\Sean\\Old Camera Uploads", 
      //  DL + "backup-DS2017\\Users\\Sean\\Old Camera Uploads");  

      map.put("E:\\Applications\\",
        DL + "backup-DS2017\\Old Amazon\\Applications");
        
      //map.put("E:\\Videos\\",
      //  DL + "backup-DS2017\\Videos"); 
      //map.put("Z:\\Videos", 
      //  DL + "backup-DS2017\\Videos");

      map.put("G:\\", 
        DL + "backup-DS2017\\UwAlumniGoogleDrive");  
        
      map.put("H:\\", 
        DL + "backup-DS2017\\GmailGoogleDrive");  
        
      map.put("X:\\", 
        DL + "backup-DS2017\\Dropbox");
        
      map.put("Y:\\", 
        DL + "backup-DS2017\\OneDrive");		
        
      //map.put("Z:\\Applications", 
      //  DL + "backup-DS2017\\Amazon\\Applications");
        
      //map.put("Z:\\Documents", 
      //  DL + "backup-DS2017\\Amazon\\Documents");
        
      //map.put("Z:\\Pictures", 
      //  DL + "backup-DS2017\\Amazon\\Pictures");
        
      //map.put("Z:\\Pictures-Private", 
      //  DL + "backup-DS2017\\Amazon\\Pictures-Private");
        
      //map.put("Z:\\Podcasts", 
      //  DL + "backup-DS2017\\Amazon\\Podcasts");
        
      //map.put("Z:\\E-books",
      //  DL + "backup-DS2017\\Amazon\\E-books");

 
		//////////////

		println()
		println("SyncSandbox.groovy (DS2017)")

		def systemIn = new BufferedReader(new InputStreamReader(System.in))

		println()
        println("Making sure MyPassport external hard drive is hooked up to drive " + DL);
		File test = new File(DL + "backup-DS2017\\")
        assert test.exists() && test.isDirectory();
      
        println("Confirmed.");
        println("Press <Enter> to sync, <Ctrl-C> to cancel.");
		String line = systemIn.readLine();

		Date start = new Date();
		println()
		println("Sync started: " + start);

		map.each()
		{
		   copyWhereDiff(new File(it.getKey()), new File(it.getValue()))
		}

		Date end = new Date();
		println()
		println("Sync ended " + end);

		long duration = end.getTime() - start.getTime()
		duration = duration / (60 * 1000)

		println()
		println ("Done (success).  Duration: $duration minutes.")
		println("$copyCounter files copied.")
      
      
   
      
    }

    
   /*
    * Remote is "from".  Local is "to".
    */
	static void copyWhereDiff(File remote, File local)
	{
	   assert(remote.exists())
	   if (!local.exists())
	   {
		  local.mkdir()
	   }
	   
	   remote.eachFile()
	   {
		  if (++counter % 1000 == 0) 
			 print("(1K)")
		  File localFile = new File(local.getCanonicalPath() + "\\" + it.getName())
		  
		  if (it.isDirectory())
		  {
			 copyWhereDiff(it, localFile)
		  }
		  else  // is file
		  {
			 // Ignore Thumbs.db
			 
			  try
			  {
				 if (!it.getName().contains("Thumbs.db") &&
				     !it.getName().endsWith(".cloudf") &&
					  !it.getName().endsWith(".cloud") &&
				     (!localFile.exists() || localFile.size() != it.size()))
				 {
                if (it.getName().endsWith(".cloud"))
                   {
                      println("<${it.getName()}>")
                      assert false;
                   }
             
				   println()
				   print(!localFile.exists() ? "NEW FILE: " : "UNMATCHED FILE SIZE: ")
				   println(it.getCanonicalPath())
				   // copy file, overwrite = true
				   ++copyCounter
				   ( new AntBuilder ( ) ).copy ( file : it.getCanonicalPath() , tofile : localFile.getCanonicalPath() , overwrite:true)
				 }
			  }
			  catch (Exception e)
			  {
				 println(e.toString());
			  }
		  } 
	   }
	   
	   return;
	}
}


