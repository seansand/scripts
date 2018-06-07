//AuctionToDropbox.groovy

def INTERVAL = 10;  //seconds

def SOURCE = "https://docs.google.com/spreadsheet/pub?key=0AsgpO7EOqcQGdGZ0SEtZLWR6MGowTnJRUFFfcWZGdGc&single=true&gid=0&output=html"
def LOCAL = "X:\\Public\\WFFL\\2012-from-googledocs.html"

while(true)
{
   URL u = new URL(SOURCE + "&" + System.currentTimeMillis())
   
   File f = new File(LOCAL)
   f.write(u.getText())
   print(".") 
	
   Thread.sleep(INTERVAL * 1000)
}