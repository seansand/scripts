//CountStrings.groovy

List<File> files = 
[
   new File("C:\\groovy\\scripts\\GroovyUtils.groovy"),
   new File("E:\\groovy\\scripts\\GroovyUtils.groovy")
]

File f = files.find { it.exists() }
evaluate(f)

String str =  '''


(no tennis)
xxx
xxx
xxx
Burch
Gross
Ramesh
Allen








(no tennis)
xxx
xxx
xxx
Gross
Stephenson
Ramesh
Medved








S. Sandquist
Tschida
Berry
Stephenson
Ramesh
Burch
Gross
Allen








Helps
Tschida
Burch
Allen
S. Sandquist
Gross
Berry
Medved








Helps
Tschida
Berry
Medved
S. Sandquist
Heichert
Carolan
Stephenson








Helps
Tschida
Ramesh
Stephenson
Carolan
Burch
Allen
Berry








Helps
Stephenson
Ramesh
Gross
Medved
Heichert
Burch
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
   
   