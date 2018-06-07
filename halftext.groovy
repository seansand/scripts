File inputFile = new File("c:\\users\\sean\\documents\\jwx.txt")
File outputFile = new File("c:\\users\\sean\\documents\\jwx2.txt")

//74 width

outputFile.write("");

int breakpoint = 31


inputFile.eachLine()
{
   line ->
   
   if (line.size() < breakpoint)
   {
      outputFile.append(line.trim() + "\n");
   }
   else
   {
      outputFile.append(line.substring(0, breakpoint));
      
      int i = breakpoint;
      for (; i < line.size(); ++i)
      {
         if (line.substring(i, i+1) == ' ' ||
             line.substring(i, i+1) == '\n')
             break;
         outputFile.append(line.substring(i, i+1));
      }      
      outputFile.append('\n');
      outputFile.append(line.substring(i).trim())
      outputFile.append('\n');
   }
}
