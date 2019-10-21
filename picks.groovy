//Picks.groovy

	  
def sysin = new BufferedReader(new InputStreamReader(System.in)) 	  

def localFileName = "x:\\public\\picks.txt";
def errorFileName = "x:\\public\\picks-error.txt";

def localFile = new File(localFileName)
def errorFile = new File(errorFileName)

def weekNumber;

if (args.size() > 0)
   weekNumber = args[0]
else
{
   print("Enter week number: ")
   weekNumber = sysin.readLine()
}
 
println()
println("Paste picks, then enter a lone dot.")

StringBuffer buf = new StringBuffer()

def line = sysin.readLine()

while (line.trim() != ".")
{
   if (line.trim() != '')
   {
      line = line.replaceAll("JAX", "JAC")
      line = line.replaceAll("KCC", "KC ")
      line = line.replaceAll("NOS", "NO ")
      line = line.replaceAll("GBP", "GB ")
      line = line.replaceAll("SFO", "SF ")
      line = line.replaceAll("TBB", "TB ")
      line = line.replaceAll("NEP", "NE ")
      line = line.replaceAll("ARZ", "ARI")
      line = line.replaceAll("AZ",  "ARI")
      
      line = line.replaceAll("     ", "  ");  // comment out if a problem.

      buf.append(line + System.getProperty("line.separator"))
   }
   line = sysin.readLine()
}

if ("test" in args)
{
   buf = new StringBuffer(
      "ARI 1 BUF 1 ARI 1 \n OAK 2 OAK 2 NEP 2 \n SF 4 SF 4 SF 3 \n DET 3 MIN 3 NYG 7")
}

def ls = System.getProperty("line.separator") 

def errorMessage = validateBuffer(buf.toString().trim());
if (null == errorMessage || errorMessage == "")
{
   assert !("test" in args)
   
   localFile.write("NNFP Week ($weekNumber)" + ls + ls)
   localFile.append(buf.toString().trim())
}
else
{
   println errorMessage;
   errorFile.write("NNFP Week ($weekNumber)" + ls + ls)
   errorFile.append(errorMessage + ls + buf.toString().trim())
}

println("Done, copied file.")


String validateBuffer(String picks) 
{ 
   String errorMessage = ""; 
   
   List<Set> scores = [];
   
   boolean firstLine = true;
   
   picks.eachLine() 
   {  
      if (it.contains("SEAN") || it.contains("RYAN") || 
          it.contains("RICK") ||
          it.contains("RAJIV") || it.contains("---"))
      {
      }
      else
      {
      
         List tokens = it.split(); 
         List teams = [] 
         List values = [] 
         tokens.each() 
         { 
            def list = (teams.size() == values.size()) ? teams : values; 
            list.add(it) 
            
            if (firstLine && teams.size() == values.size())
            {
               scores.add(new TreeSet());
            }
         } 
         firstLine = false;
         
         Set teamSet = new HashSet(teams) 
          
         if (teamSet.size() > 2)     
            errorMessage += "A line has more than two teams: ${teams.toString()}\n"; 
            
         for (int i=0; i < values.size(); ++i)
         {
            scores[i].add(new Integer(values[i]));
         }
      }
   } 

   Integer scoreSum = scores[0].sum();
   Integer scoreSize = scores[0].size();
   
   for (int i = 1; i < scores.size(); ++i)
   {
      if (scoreSum != scores[i].sum())
         errorMessage += "Missing a value in column ${1+i}: ${scores[i]}\n";
      if (scoreSize != scores[i].size())
         errorMessage += "Duplicate value in column ${1+i}: ${scores[i]}\n";
   }
   
   return errorMessage; 
} 



