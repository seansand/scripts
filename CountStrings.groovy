//CountStrings.groovy

evaluate(new File("c:\\groovy\\scripts\\GroovyUtils.groovy"))

String str =  '''


Helps
Gross
Heichert
Nicol
C. Sandquist
Smith
Burch
Hoyt








Heichert
Takhar
Burch
Gross
Stephenson
Carolan
McGrew
Zweber








Carolan
Helps
Berry
Stephenson
C. Sandquist
Allen
Smith
Takhar








Stephenson
Helps
Berry
Nicol
S. Sandquist
Carolan
McGrew
Gangl








Nicol
Helps
Heichert
Smith
C. Sandquist
Allen
Hoyt
Gross








Hoyt
Burch
Heichert
Takhar
S. Sandquist
McGrew
Zweber
Stephenson








Gross
Allen
Burch
Smith
Zweber
Carolan
Berry
Gangl




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
   
   