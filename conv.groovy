//conv.groovy

File sourceFile = new File(/.\ClipboardMethods.groovy/);
Class groovyClass = new GroovyClassLoader(getClass().getClassLoader()).parseClass(sourceFile);
def clipboardMethods = (GroovyObject) groovyClass.newInstance();

String clipboard = clipboardMethods.getClipboard();

String newString = "";

clipboard.eachLine()
{
   line ->

   boolean ignore = false;   
   if (line.contains("CARDS")) ignore = true;
   if (line.trim().size() == 0) ignore = true;
   if (line.contains("LIST")) ignore = true;
   
   String newLine = "";
   
   if (!ignore)
   {
      newLine = removeInitialDigits(line)
	  newLine = removeParens(newLine)
   
      newString += newLine + System.getProperty('line.separator');
   }

}

clipboardMethods.setClipboard(newString);
println newString
println("Conversion added to clipboard.");

String removeInitialDigits(String str)
{
   while (str.startsWith("0") ||
          str.startsWith("1") ||
          str.startsWith("2") ||
          str.startsWith("3") ||
          str.startsWith("4") ||
          str.startsWith("5") ||
          str.startsWith("6") ||
          str.startsWith("7") ||
          str.startsWith("8") ||
          str.startsWith("9") ||
          str.startsWith("0") ||
          str.startsWith(" "))
   str = str.substring(1);

   return str   
}

String removeParens(String str)
{
   if (str.contains("(*)"))
      str = str.replace("(*)", "")
   else if (str.contains("(2)"))
      str = "2 " + str.replace("(2)", "")
	  
   return str
}