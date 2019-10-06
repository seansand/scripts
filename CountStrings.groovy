//CountStrings.groovy

List<File> files = 
[
   new File("C:\\groovy\\scripts\\GroovyUtils.groovy"),
   new File("E:\\groovy\\scripts\\GroovyUtils.groovy")
]

File f = files.find { it.exists() }
evaluate(f)

String str =  '''

Burch
Allen
Gross
Takhar
C. Sandquist
Heichert
Helps
Punnoose








Helps
Allen
Gross
Burch
S. Sandquist
Stephenson
Berry
Takhar








Helps
Gross
Burch
Punnoose
S. Sandquist
C. Sandquist
Takhar
Berry








S. Sandquist
Larson
Stephenson
Tschida
C. Sandquist
Heichert
Carolan
Punnoose








Helps
Gross
Stephenson
Tschida
Heichert
Takhar
Burch
Berry








S. Sandquist
Stephenson
Larson
Berry
C. Sandquist
Allen
Takhar
Carolan








Gross
Stephenson
Burch
Tschida
Heichert
Allen
Punnoose
Berry

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
   
   