//RenameReplay.groovy

import java.util.regex.*

def systemIn = new BufferedReader(new InputStreamReader(System.in))
 
println()
 
String dirName = /C:\Users\Sean\Dropbox\Starcraft\Sc2gears-replays/

File dir = new File(dirName) 
 
File latest = null
long latestTime = 0 
 
dir.eachFile()
{
    if (it.lastModified() > latestTime)
	{
	    latestTime = it.lastModified()
		latest = it
	}
}

Pattern p = Pattern.compile("\\[.*\\]")
Matcher m = p.matcher(latest.getCanonicalPath())

if (m.find())
{
	
	println("LATEST REPLAY: ${new Date(latestTime)}  (Enter empty string to cancel.)\n")
	println(latest.getName().replaceFirst("MD5", "\nMD5"))
	println()
	print("ADD NOTE:  ")
	String input = systemIn.readLine()

	input = input.trim();
	
	if (input != "")
	{
        String newName = latest.getCanonicalPath().replaceFirst("\\[.*\\]", "[$input]");
		new File(latest.getCanonicalPath()).renameTo(new File(newName)) 
        println()
		println((new File(newName)).getName())	
        Thread.sleep(5000)		
	}

}
else
{
    println("Error, no brackets in ${latest.getName()}")
	Thread.sleep(5000)
}


