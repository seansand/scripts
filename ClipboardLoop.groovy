
@Grapes(
    @Grab(group='com.fasterxml.jackson.core', module='jackson-databind', version='2.5.4')
)
import com.fasterxml.jackson.databind.ObjectMapper;

public final class ClipboardLoop {

  //public static String DIRNAME = "c:\\users\\u0038026\\JsonToolbarHack\\JsonStatus\\"
  public static String DIRNAME = "e:\\temp\\"
  public static File DIR = new File(DIRNAME)

  public static void main(String... args)
  {
      assert DIRNAME.endsWith("\\")
  
      ClipboardMethods cm = new ClipboardMethods();
	  String clipboardText = "~start~"

	  while (true) 
      {
		 Thread.sleep(500)
	     String newClipboardText = cm.getClipboard();
		
		 if (newClipboardText == null)
		    newClipboardText = ""
		
		 newClipboardText = newClipboardText.trim()
		 
		 if (clipboardText != newClipboardText) 
		 {
            clipboardText = newClipboardText
			
			if (clipboardText.startsWith("{") || clipboardText.startsWith("["))
			{
				changeName(isJsonValid(clipboardText) ? "VJ" : "IJ")
			}
			else 
			{
			    changeName("X")
			}
         }		 
      }	  
  }

  public static void changeName(String name)
  {
     //https://superuser.com/questions/89628/windows-7-display-date-using-small-icons
  
     // rename *.lnk in usersU0038026\JsonToolbarHack\JsonStatus to $name.lnk
  
     println name
	 File onlyFile = ClipboardLoop.DIR.listFiles()[0]
	 
	 println onlyFile.getCanonicalPath()
	 
	 boolean renameOp = onlyFile.renameTo(DIRNAME + name + ".lnk")
	 while (!renameOp) {
	    println renameOp
	    renameOp = onlyFile.renameTo(DIRNAME + name + ".lnk")
	 }
	 
	 
  }
  
  public static boolean checkClipboard()
  {  
	  ClipboardMethods cm = new ClipboardMethods();
	  String clipboardText = cm.getClipboard();
	  return isJsonValid(clipboardText)
  }
  
  public static boolean isJsonValid(String jsonInString ) {
    try {
       final ObjectMapper mapper = new ObjectMapper();
       mapper.readTree(jsonInString);
       return true;
    } catch (IOException e) {
       return false;
    }
  }
}

/*
assert !IsJsonValid.isJsonValid("blert")
assert !IsJsonValid.isJsonValid('{"foo":"b;}')
assert IsJsonValid.isJsonValid("[]")
assert IsJsonValid.isJsonValid("{}")
assert IsJsonValid.isJsonValid('{"foo":"bar"}')
*/