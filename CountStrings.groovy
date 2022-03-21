//CountStrings.groovy

List<File> files = 
[
   new File("C:\\groovy\\scripts\\GroovyUtils.groovy"),
   new File("E:\\groovy\\scripts\\GroovyUtils.groovy")
]

File f = files.find { it.exists() }
evaluate(f)

String str =  '''

Tschida
Burch
Miller
Berry
Takhar
C. Sandquist
S. Sandquist
Stephenson








Berry
Tschida
S. Sandquist
Medved
C. Sandquist
Takhar
Helps
Miller








Miller
Burch
Tschida
Helps
S. Sandquist
C. Sandquist
Berry
Stephenson








Medved
S. Sandquist
Miller
Stephenson
Helps
C. Sandquist
Takhar
Burch








Stephenson
S. Sandquist
Berry
Tschida
Burch
Helps
Takhar
C. Sandquist








Medved
Stephenson
Helps
Berry
Carolan
Burch
Takhar
Miller








S. Sandquist
Berry
Medved
Stephenson
Burch
Carolan
Miller
Takhar




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
   
   