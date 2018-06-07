//yada.groovy

File sourceFile = new File(/.\ClipboardMethods.groovy/);
Class groovyClass = new GroovyClassLoader(getClass().getClassLoader()).parseClass(sourceFile);
def clipboardMethods = (GroovyObject) groovyClass.newInstance();

//String clipboard = clipboardMethods.getClipboard();

if (args.size() != 1)
{
	println "Yada expects exactly one String argument"
	return
}

String key = args[0].toLowerCase()

File yadaFile = new File("C:\\Users\\Sean\\Dropbox\\Applications\\Yadabyte\\YadabyteSubText_data.txt")

Map map = [:]

yadaFile.eachLine()
{
   String line = it.trim()
   
   def tokens = line.split("=")
   map.put(tokens[0].toLowerCase(), tokens[1])
}

if (!map.containsKey(key))
{
	println("[$key] not found.")
	return
}

String value = map.get(key)

clipboardMethods.setClipboard(value)
println ("Found [$key], set [$value] to clipboard.")

