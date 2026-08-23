// deleteDuplicates.groovy
import java.security.MessageDigest

def inputPath = args ? args[0] : /G:\My Drive\E-books\Asimov/
File dir = new File(inputPath)

if (!dir.exists() || !dir.isDirectory()) {
    println "Invalid directory: $inputPath"
    System.exit(1)
}

// Hash helper
String hashFile(File f) {
    MessageDigest md = MessageDigest.getInstance("SHA-256")
    f.withInputStream { is ->
        byte[] buffer = new byte[8192]
        int read
        while ((read = is.read(buffer)) != -1) {
            md.update(buffer, 0, read)
        }
    }
    return md.digest().encodeHex().toString()
}

// Map: hash → list of files with that hash
def groups = [:].withDefault { [] }

dir.eachFile { file ->
    if (file.isFile()) {
        def h = hashFile(file)
        groups[h] << file
    }
}

// Process duplicates
groups.each { hash, files ->
    if (files.size() > 1) {
        println "\nDuplicate group (hash: $hash)"
        files.each { println "  ${it.name}" }

        // Keep the file with the shortest name
        def keeper = files.min { it.name.length() }
        println "Keeping: ${keeper.name}"

        // Delete the others
        files.findAll { it != keeper }.each { f ->
            println "Deleting: ${f.name}"
            f.delete()
        }
    }
}