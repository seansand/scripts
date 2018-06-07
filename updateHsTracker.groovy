//UpdateHsTracker.groovy

import java.nio.file.Files

File topdir = new File(/C:\Users\Sean\Dropbox\Applications/)

String HDT = "Hearthstone.Deck.Tracker"

TreeMap dirs = [:]

//Count number of directories that start with Hearthstone.Deck.Tracker

topdir.eachDir
{
   if (it.getName().startsWith(HDT))
   {
	  dirs.put(it.lastModified(), it)
   }
}

if (dirs.size() <= 1)
{
   println "Nothing to do."
   return
}
   
   
//If there is more than one, figure out the oldest.

Integer deletes = dirs.size() - 1

Boolean successFlag = false;

dirs.eachWithIndex()
{
   k, dir, index ->
   
   if (index < deletes)
   {
      print("Deleting $dir: ")
      println(dir.deleteDir())
   }
   else
   {
   
      Thread.sleep(2000)
	  println("Renaming $dir to $HDT")
	  while(!successFlag)
	  {
	     print(".")
		 String newFileDir = topdir.getCanonicalPath() 
		 File newName = new File(newFileDir + "\\" + HDT)
         successFlag = dir.renameTo(newName)
	  }
   
   
   }

}

println("")
println "Success = $successFlag"

