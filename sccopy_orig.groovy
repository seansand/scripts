//sccopy.groovy

//Works for both text and binary files.

//Copies the last 20 files

class Sccopy
{
	public static void main(String[] args)
	{
	  
		String sourceDirName = "C:\\Users\\Sean\\Documents\\StarCraft II\\Accounts\\69139895\\1-S2-1-2301117\\Replays\\Multiplayer\\"
		String destDirName = "C:\\Users\\Sean\\Dropbox\\Starcraft\\replays\\"

		AgeComparator<File> comparator = new AgeComparator<File>()
		
		java.util.PriorityQueue list = new java.util.PriorityQueue(20, comparator);


		File sourceDir = new File(sourceDirName)
		sourceDir.eachFile()
		{
		   //println it.getName()
		
		   if (it.getName().endsWith(".SC2Replay"))
		   {
		      //println it.getName()
		      list.add(it)
		   }
		}

		File curr = list.poll()
		
		int fileCount = 20;
				
		List reverseList = []
		
		while (curr != null && fileCount-- > 0)
		{
           reverseList.add(curr)
		   curr = list.poll()
		}

     	reverseList.reverse().each()
		{
		   copyFile(it.getCanonicalPath(), destDirName + it.getName())
		}
			
	}



	static void copyFile (String srcStr, String destStr)
	{

	   File destFile = new File(destStr)
	   if (!destFile.exists())
	   {
	   
		   File src = new File(srcStr)
		   File dest = new File(destStr).withOutputStream
		   {
			  out -> out.write src.readBytes()
		   }
		   
   	   	   println(destStr)
	   }
	}

	
	static class AgeComparator<File> implements Comparator<File>
	{
	   @Override
	   int compare(File f1, File f2)
	   {
		  if (f1.lastModified() > f2.lastModified())
		  {
			 return -1
		  }
		  else
		  {
			 return 1
		  }
	   }
	};

}
