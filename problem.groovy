//problem.groovy



assert (test(["....",
              ".oo.",
	          "...."], 2) == 0); println("** 1 pass ** ")

assert (test(["....",
              ".oo.",
	          "...."], 1) == 2); println("** 2 pass ** ")
			 
			 
assert (test(["o..o",
              "o.oo",
	          "oooo",
			  "o..o"], 3) == 3); println("** 3 pass ** ")

assert (test(["oo",
              "oo"], 3) == -1); println("** 4 pass ** ")

assert (test(["oooo",
              "oooo",
	          "o..o"], 1) == 3); println("** 5 pass ** ")

assert (test(["oo......",
              ".oo.....",
              ".o......",
              ".o......",
              ".o.oo...",
              ".o.oooo.",
              "...oooo.",
	          "...o.oo."], 8) == 4); println("** 6 pass ** ")

			  
println("tests all passed")


			
int test(List<String> rows, int wantCount)
{
    Map<Integer, List<List<String>>> mapOfGrids = new HashMap<Integer, List<List<String>>>();
    mapOfGrids.put(0, [rows])
    
    int currentLevel = 0
    
    int levelsToTry = 2 * (rows.size() + rows[0].size())
    
    while (currentLevel < levelsToTry)
    {
        if (testCurrentLevel(currentLevel, mapOfGrids, wantCount))
        {
           return currentLevel
        }
        ++currentLevel
    }
    
    return -1;  // no solution found
}


boolean testCurrentLevel(int currentLevel, Map<Integer, List<List<String>>> mapOfGrids, int wantCount)
{
   println ("testing level $currentLevel")

   List nextLevel = []
   mapOfGrids.put(currentLevel + 1, nextLevel)

   List<List<String>> gridsToTest = mapOfGrids.get(currentLevel)
   
   for (i in (0..<gridsToTest.size()))
   {
      int count = gridCount(gridsToTest[i])
   
      if (count == wantCount)
         return true
      
      if (count < wantCount)
         continue
      
      // a solution is still possible, generate the next level
      
      nextLevel.add(shiftUp(gridsToTest[i]))
      nextLevel.add(shiftDown(gridsToTest[i]))         
      nextLevel.add(shiftLeft(gridsToTest[i]))  
      nextLevel.add(shiftRight(gridsToTest[i])) 
   }

   return false
   
}


int gridCount(List<String> grid)
{
   int total = 0
   grid.each()
   {
      println it
      total += it.count("o")
   }
   
   println("count = $total\n")
   
   //def systemIn = new BufferedReader(new InputStreamReader(System.in))
   //systemIn?.readLine()
   
   return total
}



List<String> shiftDown(List<String> rows)
{
   def newRows = []

   def newRow = rows[0].replaceAll("o",".")
   newRows.add(newRow)
   
   for(i in (0..<rows.size() - 1))
   {
      newRow = rows[i]
	  newRows.add(newRow)
   }
   
   return newRows
}


List<String> shiftUp(List<String> rows)
{
   def newRows = []

   for(i in (1..<rows.size()))
   {
      String newRow = rows[i]
	  newRows.add(newRow)
   }
   def newRow = rows[0].replaceAll("o",".")
   newRows.add(newRow)
   
   return newRows
}

List<String> shiftLeft(List<String> rows)
{
   def newRows = []

   rows.each()
   {
      String newRow = it.substring(1, it.size()) + "."
	  newRows.add(newRow)
   }
   
   return newRows
}

List<String> shiftRight(List<String> rows)
{
   def newRows = []

   rows.each()
   {
      String newRow = "." + it.substring(0, it.size() -1)
	  newRows.add(newRow)
   }
   
   return newRows
}



			 
			 
