// Synclinks.groovy

String dropboxCopy = /C:\Users\Seans\Dropbox\Www\links.html/
String googleCopy  = /G:\My Drive\WWW\links.html/
String gitHubCopy  = /G:\My Drive\Applications\seansand.github.io\links.html/

syncNewest([dropboxCopy, googleCopy, gitHubCopy])

// --- FUNCTIONS ---

public static void syncNewest(List<String> paths) {
    // Convert to File objects
    List<File> files = paths.collect { new File(it) }

    // Find the newest file
    File newest = files.max { it.lastModified() }

    println "Newest file: ${newest.getCanonicalPath()} (modified: ${new Date(newest.lastModified())})"

    // Copy newest into all others
    files.each { f ->
        if (f != newest) {
            copyFile(newest, f)
        }
    }
}

public static void copyFile(File srcFile, File destFile) {
    copyFile(srcFile.getCanonicalPath(), destFile.getCanonicalPath())
}

public static void copyFile(String srcStr, String destStr) {
    println(srcStr + " -> " + destStr)

    File src = new File(srcStr)
    File dest = new File(destStr).withOutputStream { out ->
        out.write(src.readBytes())
    }
}
