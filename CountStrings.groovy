//CountStrings.groovy

evaluate(new File("E:\\groovy\\scripts\\GroovyUtils.groovy"))

String str =  '''


Gross
Hartley
Kennedy
Barthel
Stephenson
Amann
Mahlke
Tschida








Helps
Carolan
Allen
Berry
S. Sandquist
C. Sandquist
Hendrie
Tschida








Kennedy
Gross
Takhar
Burch
Carolan
Helps
Amann
Mahlke








Stephenson
Hartley
Takhar
Barthel
C. Sandquist
Hendrie
Heichert
Carolan








Heichert
Gross
Burch
Allen
S. Sandquist
C. Sandquist
Takhar
Berry








Barthel
Hartley
Kennedy
Helps
S. Sandquist
Carolan
Mahlke
Tschida








Takhar
Berry
Allen
Burch
Hendrie
Amann
Stephenson
Barthel




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
   
   