//CountStrings.groovy

List<File> files = 
[
   new File("C:\\groovy\\scripts\\GroovyUtils.groovy"),
   new File("E:\\groovy\\scripts\\GroovyUtils.groovy")
]

File f = files.find { it.exists() }
evaluate(f)

String str =  '''


Helps
Ramesh
Stephenson
Berry
C. Sandquist
Heichert
Medved
Takhar








Helps
Tschida
Gross
Larson
Medved
Carolan
Burch
Berry








Tschida
Stephenson
Ramesh
Larson
Takhar
Heichert
Allen
Burch








Gross
Tschida
Stephenson
Medved
(no tennis)














S. Sandquist
Carolan
Gross
Larson
C. Sandquist
Heichert
Allen
Burch








Carolan
Stephenson
Tschida
Larson
S. Sandquist
C. Sandquist
Allen
Takhar








S. Sandquist
Ramesh
Medved
Berry
Takhar
Heichert
Allen
Burch



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
   
   