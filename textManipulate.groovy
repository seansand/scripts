
File f = new File("x:\\temp\\nightfall.txt")
File nf = new File("x:\\temp\\nightfall-new.txt")


def LS = System.getProperty ("line.separator");  

f.eachLine()
{
    nf.append("    " + it + LS + LS)

}
