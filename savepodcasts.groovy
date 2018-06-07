File podcastDir = new File("C:\\Users\\Sean\\Music\\iTunes\\iTunes Media\\Podcasts\\")
File podcastTxt = new File("X:\\Documents\\podcasts-" + System.currentTimeMillis() + ".txt")

println("Writing to ${podcastTxt.getCanonicalPath()}")

podcastTxt.write("")

podcastDir.eachDir()
{
   println(it.getName())
   podcastTxt.append(it.getName() + System.getProperty('line.separator'))
}