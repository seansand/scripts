//CopyFiles.groovy

//Works for both text and binary files.

List srcList = 
[
"C:\\work\\NSA-West\\com\\tlrg\\nsa\\west\\delivery\\util\\security\\AccessCategoryChecker.java",
"C:\\work\\NSA-West\\com\\tlrg\\nsa\\west\\delivery\\version2\\handler\\DeliveryInsertHandler.java",
"C:\\work\\NSA-West\\com\\tlrg\\nsa\\west\\delivery\\util\\DeliveryInsertRequestManager.java",
"C:\\work\\NSA-West\\com\\tlrg\\nsa\\west\\delivery\\version2\\handler\\DeliveryLegacyInsertHandler.java",
"C:\\work\\NSA-West\\com\\tlrg\\nsa\\west\\delivery\\util\\DeliveryUtil.java",
"C:\\work\\NSA-West\\com\\tlrg\\nsa\\west\\archive\\util\\legacy\\LegacyWestlawArchive.java",
"C:\\work\\NSA-West\\com\\tlrg\\nsa\\west\\delivery\\util\\summary\\SummaryInfoBuilder.java",

]

List destList = []
srcList.each()
{
   destList.add(it.replace("C:\\work\\NSA-West\\com\\tlrg\\nsa\\west",
                           "C:\\tfs\\Westlaw_Online_Services\\Development\\MiddleTier\\NSA-West\\src\\com\\tlrg\\nsa\\west"))
}

for (i in (0..<srcList.size()))
{
   copyFile(srcList[i], destList[i])
}


public static void copyFile (File srcFile, File destFile)
{
   copyFile(srcFile.getCanonicalPath(), destFile.getCanonicalPath())
}


public static void copyFile (String srcStr, String destStr)
{
   println(srcStr + " -> " + destStr)

   File src = new File(srcStr)
   File dest = new File(destStr).withOutputStream
   {
      out -> out.write src.readBytes()
   }

   /* THIS DID NOT WORK

   File src = new File(srcStr)
   File dest = new File(destStr)
   println(src.getCanonicalPath() + " -> " + dest.getCanonicalPath())

   String command = "cp ${src.getCanonicalPath()} ${dest.getCanonicalPath()}"
   command = "cmd /c " + command
   Process p = command.execute()
   */
}

