//odrivesync.groovy

//U:\appdata\local\programs\python\python37\python U:\.odrive\bin\6382\cli\odrive.py sync "E:\odrive\Amazon Cloud Drive\Pictures\2017\2017-08 Marion-Chicago\IMG_20170820_155548.jpg.cloud"


// Note that this is currently coded to *not* recursively sync directories.

String fileOrDirToSync = /E:\odrive\Amazon Cloud Drive\Pictures\2017\2017-09 Elizabeth (age 9.5)/


odriveSync(fileOrDirToSync)

   
void odriveSync(def fileList)
{
   String pythonCmd = /U:\appdata\local\programs\python\python37\python/
   String odrivePy = /U:\.odrive\bin\6382\cli\odrive.py/

   Closure c = { String fileName ->
   
      File f = new File(fileName)
      
      println(f.getCanonicalPath())
      println("exists? ${f.exists()}")
      println("isFile? ${f.isFile()}")
      println("isDirectory? ${f.isDirectory()}")
      
      boolean isCloudFile = f.getName().endsWith(".cloud") || f.getName().endsWith(".cloudf")
      println("isCloudFile? $isCloudFile")
      
      if (f.exists() && isCloudFile) {
         println("Syncing:")
         String execStr = "$pythonCmd $odrivePy sync "
         execStr += '"' + f.getCanonicalPath() + '"'
         println execStr
         Process proc0 = Runtime.getRuntime().exec(execStr);
      }      
         
      println()  
   }

   if (fileList instanceof String) {    // single file name
   
      File g = new File(fileList)
      if (g.exists() && g.isFile()) {
         c.call(fileList)
      }
      if (g.exists() && g.isDirectory()) {
         
         List<String> gList = g.list()
         List<String> dirFileNames = gList.collect{ g.getCanonicalPath() + "\\" + it }
         dirFileNames.each(c)
      }
      
   }
   else if (fileList instanceof List) {   // list of file names
      fileList.each(c)   
   }
  
}


