import java.util.regex.*

File f = new File(/e:\temp\jd-source-1.txt/);
File outfile = new File(/e:\temp\jd-source-out.txt/);


class Pair{
   int first
   String second 
}

String text = f.text //.substring(0, 1000)  //first 1000

def pattern = Pattern.compile(/([0-9]{4})\r\n/)
//def pattern = Pattern.compile(/Rednex/)

def m = pattern.matcher(text)

Map startEndPairs = [:]

while (m.find())
{
   startEndPairs.put(
      m.start(), 
      new Pair(first: m.end(), 
               second: m.group(1)))
}
/*
println text.substring(0, 1000)
*/

String newText = text.replaceAll(/([0-9]{4})\r\n/, 
  "wxyz  ")

newText = newText.replaceAll(/\r\n/, " ");

String LS = System.getProperty('line.separator');  
  
startEndPairs.each{k, v ->
   newText = newText.replaceFirst("wxyz  ",
                " " + v.second + LS);   
   
}

outfile.write(newText);




