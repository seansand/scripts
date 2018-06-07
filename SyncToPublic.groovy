//SyncToPublic.groovy


String wwwDir = "x:\\www\\"
String pubDir = "x:\\public\\www\\"

List<String> filesToSync = []

filesToSync.add("worklinks.html");
filesToSync.add("links.html");


filesToSync.each()
{
   copyFile(wwwDir + it, pubDir + it);
}

void copyFile (String srcStr, String destStr)
{
   println(srcStr + " -> " + destStr)

   File src = new File(srcStr)
   File dest = new File(destStr).withOutputStream
   {
      out -> out.write src.readBytes()
   }
}

void copyFile (File src, File dest)
{
   dest.withOutputStream
   {
      out -> out.write src.readBytes()
   }
}
