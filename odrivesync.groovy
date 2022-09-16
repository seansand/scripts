//odrivesync.groovy

//U:\appdata\local\programs\python\python37\python U:\.odrive\bin\6382\cli\odrive.py sync "E:\odrive\Amazon Cloud Drive\Pictures\2017\2017-08 Marion-Chicago\IMG_20170820_155548.jpg.cloud"

// Note that this is currently coded to *not* recursively sync directories.

File ODRIVE_DIR = new File(/C:\Users\Sean\.odrive\bin/)
List files = ODRIVE_DIR.listFiles()

assert files.size() == 1  // should only be the one directory, whose name we need

// GLOBAL VARIABLE
// directory name in u:\.odrive\bin  (something like "6472")
ODRIVE_VER = files[0].getName()

def filesOrDirsToSync = 

  [
   
//   /  E:\odrive\Amazon Cloud Drive\Pictures\Pixel 4\Camera  /,


   
  ]

// Note this must be an odrive folder (drive E)


if (filesOrDirsToSync.size() == 0)
{
   ClipboardMethods cm = new ClipboardMethods();
   filesOrDirsToSync.add(cm.getClipboard())
   println()
   println("FROM CLIPBOARD: " + cm.getClipboard());
   println()
   Thread.sleep(500)
}


filesOrDirsToSync.each { dir -> dir = dir.trim() ; odriveSync(dir) ; println("-----") }

   
void odriveSync(def fileList)
{
   String pythonCmd = /U:\appdata\local\programs\python\python37\python/
   String odrivePy = /U:\.odrive\bin\$ODRIVE_VER\cli\odrive.py/

   Closure c;
   c = { String fileName ->
   
      boolean RECURSE_DIRS = true
      int PAUSE_MILLIS = 500
      
      File f = new File(fileName)
      
      //println(f.getCanonicalPath())
      //println("exists? ${f.exists()}")
      //println("isFile? ${f.isFile()}")
      //println("isDirectory? ${f.isDirectory()}")
      
      boolean isCloudFile = f.getName().endsWith(".cloud") || f.getName().endsWith(".cloudf")
      //println("isCloudFile? $isCloudFile")
      
      if (f.exists() && isCloudFile) {
         println()
         println("Syncing:")
         String execStr = "$pythonCmd $odrivePy sync "
         execStr += '"' + f.getCanonicalPath() + '"'
         println execStr
         Process proc0 = Runtime.getRuntime().exec(execStr);
         println("Waiting $PAUSE_MILLIS milliseconds.");
         Thread.sleep(PAUSE_MILLIS)
      }      
      
      if (f.exists() && f.isDirectory() && RECURSE_DIRS)
      {
         List<String> fList = f.list()
         List<String> dirFileNames = fList.collect{ f.getCanonicalPath() + "\\" + it }
         dirFileNames.each(c)
      }
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


