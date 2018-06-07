nnfppicks.groovy


import org.apache.commons.net.ftp.FTPClient; 
import org.apache.commons.net.ftp.FTP; 

      //Properties props = System.getProperties();
      //props.put("http.proxyHost", "webproxy.int.westgroup.com");
      //props.put("http.proxyPort", "80");

	  
def sysin = new BufferedReader(new InputStreamReader(System.in)) 	  

def localFileName = "c:\\www\\picks.txt"
def remoteFileName = "/picks.txt"
def localFile = new File(localFileName)

print("Enter week number: ")
def weekNumber = sysin.readLine()

println()
println("Paste picks, then enter a lone dot.")

StringBuffer buf = new StringBuffer()

def line = sysin.readLine()

while (line.trim() != ".")
{
   buf.append(line + System.getProperty("line.separator"))
   line = sysin.readLine()
}

def ls = System.getProperty("line.separator") 

localFile.write("NNFP Week ($weekNumber)" + ls + ls)
localFile.append(buf.toString().trim())

FTPClient ftpClient = new FTPClient()
connect(ftpClient)
doTheUpload(ftpClient, localFile, remoteFileName)
ftpClient.disconnect()

println("Uploaded week $weekNumber to $remoteFileName.")

///

void connect(FTPClient ftpClient)
{
   ftpClient.connect("upload.comcast.net") 
   ftpClient.login("********", "********") 
   ftpClient.setFileType(FTP.ASCII_FILE_TYPE) 
}


void doTheUpload(FTPClient ftpClient, def localFile, def remoteFileName)
{
   def fis = new FileInputStream(localFile)
   println("Success == " + ftpClient.storeFile(remoteFileName, fis))
   fis.close()
}
