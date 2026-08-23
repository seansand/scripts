// OceanRenamer.groovy

def inputPath = args ? args[0] : /G:\My Drive\E-books\Miscellaneous/

// Normalize to File object
File dir = new File(inputPath)

if (!dir.exists() || !dir.isDirectory()) {
    println "Invalid directory: $inputPath"
    System.exit(1)
}

dir.eachFile { file ->
    def name = file.name

    if (name.startsWith("_OceanofPDF.com_")) {

        // Remove the prefix
        def newName = name.replaceFirst("^_OceanofPDF\\.com_", "")

        // Convert all remaining underscores to spaces
        newName = newName.replaceAll("_", " ")

        File newFile = new File(dir, newName)

        println "Renaming:"
        println "  $name"
        println "    → $newName"

        file.renameTo(newFile)
    }
}