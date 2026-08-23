//SyncSandbox.groovy

// Similar to BackupSilver, but just does a sync 
// (it doesn't copy files that are already present).
// This should run much faster than BackupSilver.
// Modified March 2022


public class SyncSandbox
{
   static int counter = 0
   static int copyCounter = 0
   
   static String TOP_LEVEL = "backup-PS2025"
   static String USER_NAME = "seans"

   public static void main(String[] args)
   {
		// List of Sync directories here.  Remote "from", then local "to".
			
      final String DL = "D:\\";  //DRIVE LETTER D FOR POWERSPEC
      //final String DL = "F:\\";  //DRIVE LETTER F FOR DSTORM
         
      Map map = new HashMap();
      
	  map.put("C:\\groovy\\scripts", 
        DL + "$TOP_LEVEL\\Applications\\groovy\\scripts");
	  
      map.put("U:\\Videos\\",
        DL + "$TOP_LEVEL\\Users\\$USER_NAME\\Videos"); 
      
      map.put("C:\\Users\\$USER_NAME\\Documents", 
        DL + "$TOP_LEVEL\\Users\\$USER_NAME\\Documents");

      map.put("G:\\", 
        DL + "$TOP_LEVEL\\GmailGoogleDrive");  
        
      map.put("X:\\", 
        DL + "$TOP_LEVEL\\Dropbox");
        
	  // TEMPORARILY DO NOT STORE ANYTHING FROM ONEDRIVE
	  // EVERYTHING IMPORTANT IN ONEDRIVE IS THE Y:\\Music DUPLICATED FROM GOOGLE DRIVE
	  // ONLY SAVE ONEDRIVE IF YOU WANT MUSIC SAVED IN TWO PLACES
      //map.put("Y:\\", 
        //DL + "$TOP_LEVEL\\OneDrive");		

	  //map.put("U:\\Music", 
      //  DL + "$TOP_LEVEL\\Users\\$USER_NAME\\Music");

	  //map.put("C:\\Applications\\",
      // DL + "$TOP_LEVEL\\Applications");

      //map.put("G:\\", 
      //  DL + "$TOP_LEVEL\\UwAlumniGoogleDrive");  

      //map.put("E:\\Music", 
      //  DL + "$TOP_LEVEL\\Users\\$USER_NAME\\E_Drive_Music");
        
      //map.put("C:\\Users\\$USER_NAME\\Music", 
      //  DL + "$TOP_LEVEL\\Users\\$USER_NAME\\Music");

      //map.put("Z:\\Archived Music", 
      //  DL + "$TOP_LEVEL\\Users\\$USER_NAME\\Music\\Amazon Music");

      //map.put("Z:\\My Send-to-Kindle Docs", 
      //  DL + "$TOP_LEVEL\\Amazon\\My Send-to-Kindle Docs");
      
      //map.put("C:\\Users\\$USER_NAME\\Old Camera Uploads", 
      //  DL + "$TOP_LEVEL\\Users\\$USER_NAME\\Old Camera Uploads");  
        
      //map.put("E:\\Videos\\",
      //  DL + "$TOP_LEVEL\\Videos"); 
      //map.put("Z:\\Videos", 
      //  DL + "$TOP_LEVEL\\Videos");
        
      //map.put("Z:\\Applications", 
      //  DL + "$TOP_LEVEL\\Amazon\\Applications");
        
      //map.put("Z:\\Documents", 
      //  DL + "$TOP_LEVEL\\Amazon\\Documents");
        
      //map.put("Z:\\Pictures", 
      //  DL + "$TOP_LEVEL\\Amazon\\Pictures");
        
      //map.put("Z:\\Pictures-Private", 
      //  DL + "$TOP_LEVEL\\Amazon\\Pictures-Private");
        
      //map.put("Z:\\Podcasts", 
      //  DL + "$TOP_LEVEL\\Amazon\\Podcasts");
        
      //map.put("Z:\\E-books",
      //  DL + "$TOP_LEVEL\\Amazon\\E-books");

 
		//////////////

		println()
		println("SyncSandbox.groovy (PS2025)")

		def systemIn = new BufferedReader(new InputStreamReader(System.in))

		println()
        println("Making sure MyPassport external hard drive is hooked up to drive " + DL);
		File test = new File(DL + TOP_LEVEL + "\\")
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


