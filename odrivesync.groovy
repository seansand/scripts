//odrivesync.groovy

//U:\appdata\local\programs\python\python37\python U:\.odrive\bin\6382\cli\odrive.py sync "E:\odrive\Amazon Cloud Drive\Pictures\2017\2017-08 Marion-Chicago\IMG_20170820_155548.jpg.cloud"


// Note that this is currently coded to *not* recursively sync directories.

ODRIVE_VER = "6472"  // GLOBAL VARIABLE, directory in u:\.odrive\bin

def filesOrDirsToSync = 
  ///E:\odrive\Amazon Cloud Drive\Pictures\2017\2017-09 Elizabeth (age 9.5)/
  //E:\odrive\Amazon Cloud Drive\Pictures\2008\2008-03 Elizabeth (Private)/
  //E:\odrive\Amazon Cloud Drive\Pictures\2017\2017-03 Elizabeth (age 9)/,
  //E:\odrive\Amazon Cloud Drive\Pictures\2017\2017-09 Minnesota State Capitol/,
  //E:\odrive\Amazon Cloud Drive\Pictures\2016\2016-03 Elizabeth (age 8)/,
  //E:\odrive\Amazon Cloud Drive\Pictures\2016\2016-08 Chicago/,
  //E:\odrive\Amazon Cloud Drive\Pictures\2016\2016-10 Elizabeth (age 8.5)/,
  //E:\odrive\Amazon Cloud Drive\Pictures\2015\2015-05 Elizabeth (age 7)/,
  //E:\odrive\Amazon Cloud Drive\Pictures\2015\2015-10 Elizabeth (age 7.5)/,
  [
   /  E:\odrive\Amazon Cloud Drive\Pictures\2000\2000 Misc  /.trim()
  ]


filesOrDirsToSync.each { odriveSync(it) ; println("-----") }

   
void odriveSync(def fileList)
{
   String pythonCmd = /U:\appdata\local\programs\python\python37\python/
   String odrivePy = /U:\.odrive\bin\$ODRIVE_VER\cli\odrive.py/

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


