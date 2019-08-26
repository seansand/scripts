//RenameFiles.groovy

import java.util.regex.*

def systemIn = new BufferedReader(new InputStreamReader(System.in))
 
println()
 
String dirName = /C:\Users\Sean\Google Drive\eBooks\Games\Cthulhu/

File dir = new File(dirName) 
 
File latest = null
long latestTime = 0 
 
dir.eachFile()
{
   if (it.isFile() && it.getName().startsWith("Call of Cthulhu - "))
	{
	     println(it.getName())
       
        String newName = it.getCanonicalPath().replaceFirst("Call of Cthulhu - ", "");
		  new File(it.getCanonicalPath()).renameTo(new File(newName)) 
        println()
		  println((new File(newName)).getName())	
        println()
        Thread.sleep(500)		
       
	}
}
