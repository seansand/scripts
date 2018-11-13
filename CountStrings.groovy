//CountStrings.groovy

evaluate(new File("C:\\groovy\\scripts\\GroovyUtils.groovy"))

String str =  '''

Helps
Gross
Allen
Burch
C. Sandquist
Mahlke
Hendrie
Carolan








S. Sandquist
Kennedy
Hartley
Takhar
Mahlke
Helps
Allen
Heichert








Stephenson
Gross
Tschida
Berry
(no tennis)














Tschida
Kennedy
Hartley
Berry
C. Sandquist
Mahlke
Heichert
Carolan








S. Sandquist
Kennedy
Helps
Takhar
Burch
Mahlke
Heichert
Allen








Takhar
Kennedy
Gross
Carolan
S. Sandquist
Stephenson
Hendrie
Berry








S. Sandquist
Tschida
Hartley
Stephenson
C. Sandquist
Takhar
Hendrie
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
   
   