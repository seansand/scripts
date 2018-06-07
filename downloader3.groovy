//Downloader.groovy


def items1 = ["S01", "S02", "S03"]
//def items2 = ["M", "P"]

def items3a = ["0", "1", "2", "3", "4", "5", "6", "7", "8", "9"];

def items3b = ["00", "01", "02", "03", "04", "05", "06", "07", "08", "09"];

def items3c = []
(10..300).each() {items3c.add("" + it)}

def items3 = items3a + items3b + items3c

String TOPDIR = "c:\\temp\\podcasts\\trekwest5\\";



items1.each()
{
   i1 ->
   
   items2.each()
   {
      i2 ->
      
      items3.each()
      {
         i3 ->
         
         String fn = i1 + i2 + i3 + ".mp3"
         
         File f = new File(TOPDIR + fn);
         
         if (f.exists())
            println(f.toString() + " already exists");
         else
         {
            try
            {
               download("http://s3.amazonaws.com/TrekWest5/" + fn);
            }
            catch (Exception e)
            {
               println("Exception, didn't find $fn");
            } 
         }         
      }  
   }  
}





def download(String address)
{
    String TOPDIR = "c:\\temp\\podcasts\\trekwest5\\";

    println("Downloading $address");
    def file = new FileOutputStream(TOPDIR + "\\" + address.tokenize("/")[-1])
    def out = new BufferedOutputStream(file)
    out << new URL(address).openStream()
    out.close()
    println("Finished $address");
    
}