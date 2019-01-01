//CountStrings.groovy

List<File> files = 
[
   new File("C:\\groovy\\scripts\\GroovyUtils.groovy"),
   new File("E:\\groovy\\scripts\\GroovyUtils.groovy")
]

File f = files.find { it.exists() }
evaluate(f)

String str =  '''


Gross
Burch
Tschida
Allen
Stephenson
Punnoose
Berry
[open]








Kennedy
Gross
Tschida
Hartley
Burch
Carolan
Punnoose
Allen








Stephenson
Kennedy
Berry
Hartley
S. Sandquist
Mahlke
Heichert
Burch








S. Sandquist
Helps
Tschida
Hartley
C. Sandquist
Mahlke
Heichert
Allen








Carolan
Kennedy
Takhar
Gross
Helps
Mahlke
Heichert
Stephenson








S. Sandquist
Helps
Gross
Berry
C. Sandquist
Punnoose
Takhar
Carolan


'''

Map countMap = [:]

str = str.replaceAll(". ", ".")
str = str.replaceAll("\\s", " ")

def array = str.split(" ")

array.each()
{
   String s = it;
   s = s.replaceAll("[0-9]","")

   if (s.trim().size() > 1)
      
   {
      countMap.count(s)       
   }
}

countMap.each{ println it}
   
   