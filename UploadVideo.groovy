// UploadVideo.groovy 

import org.apache.commons.net.ftp.FTPClient; 
import org.apache.commons.net.ftp.FTP; 
 

      //Properties props = System.getProperties();
      //props.put("http.proxyHost", "webproxy.int.westgroup.com");
      //props.put("http.proxyPort", "80");
 


FTPClient ftpClient = new FTPClient(); 
ftpClient.connect("upload.comcast.net") 
ftpClient.login("********", "********") 
ftpClient.setFileType(FTP.ASCII_FILE_TYPE) 

String fileString = "C:\\Users\\Sean\\Dropbox\\Www\\videos\\index.html"

// send index.html 
def indexFile = new FileInputStream(new File(fileString)) 
 

print ("Uploading $fileString to \"\\videos\\index.html\", success = ")
println (ftpClient.storeFile("videos/index.html", indexFile)); 
indexFile.close() 
 


// Eventually other operations here ... 
ftpClient.disconnect(); 

println("Done");
 

