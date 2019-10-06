
/* This is the one to call (2016) */

import java.util.regex.*;

YEAR = "2020"

String excelFileIn = "x:\\Mapped\\WFFL\\${YEAR}.xlsx";
String htmlFile1 = "x:\\Mapped\\WFFL\\${YEAR}-orig.html";
String htmlFile2 = "x:\\Mapped\\WFFL\\${YEAR}.html";
String changesFileName = "x:\\Mapped\\WFFL\\${YEAR}-changes-MILLIS.txt";
changesFileName = changesFileName.replaceAll("MILLIS", "" + System.currentTimeMillis());
File changesFile = null;
Set<String> changesSet = new LinkedHashSet<String>();


Integer DELAY_SECONDS = 18

RECORD_CHANGES = true

while(true) 
{
    try 
    {

        println("converting $excelFileIn to $htmlFile1");
        org.apache.poi.ss.examples.html.ExcelToHtml.main(excelFileIn, htmlFile1)
        modifyHtml(htmlFile1, htmlFile2, changesSet)

        if (RECORD_CHANGES)
        {
           println("Changes file = $changesFileName");
           changesFile = new File(changesFileName);
           changesFile.write("");
           changesFile.append("CHRON LIST\n");
           changesSet.each{print("."); changesFile.append(it + "\n")}
           
           List<String> alphaList = new ArrayList<String>(changesSet);
           alphaList = alphaList.sort();
           changesFile.append("\nALPHA LIST\n");
           
           alphaList.each{changesFile.append(it + "\n")}
        }
    }
    catch (Exception e) 
    {
        e.printStackTrace()
        println("Continuing to loop...")
    }
    
    print((new Date()).toString())
    println(" . . . OK to Ctrl-C to quit");
    
    Thread.sleep(DELAY_SECONDS * 1000)
}


void modifyHtml(String inFileName, String outFileName, def changesSet)
{
   String replaceThis = "<head>";
   String replaceWith = '<head><meta http-equiv="refresh" content="20"/><TITLE>' + YEAR + ' WFFL Auction</TITLE>'

   File inFile = new File(inFileName)
   File outFile = new File(outFileName)
   
   String buffer = ""
   
   inFile.eachLine()
   {
      line ->
      if (line.trim() == replaceThis)
      {
         line = replaceWith
      }
      recordChanges(line, changesSet)      
      buffer += line + "\n"
   }
   outFile.write(buffer)
}

void recordChanges(String line, def changesSet)
{
   Pattern p = Pattern.compile('<td class=style_.*text-align: left;">(.*)</td>');

   if (RECORD_CHANGES)
   {
      Matcher m = p.matcher(line);
      if (m.find())
      {
         changesSet.add(m.group(1));
      }
   }
}

