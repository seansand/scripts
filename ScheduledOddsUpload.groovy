//ScheduledOddsUploadDropbox.groovy

/*
import org.apache.commons.net.ftp.FTPClient; 
import org.apache.commons.net.ftp.FTP; 



def remoteFileName = "/nflodds.xml"

*/

      Properties props = System.getProperties();
      //props.put("socksProxyHost", "internet");
      //props.put("socksProxyPort", "21");
      //props.put("http.proxyHost", "webproxy.int.westgroup.com");
      //props.put("http.proxyPort", "80");



def localFileName = "X:\\Public\\nflodds.xml"
CopyFiles.copyFile(localFileName, localFileName + ".previous.xml");

def localFile = new File(localFileName)

def urlString = "http://lines.gamblerspalace.com/"
def url = new URL(urlString)
String urlText = url.getText()

localFile.write(urlText)
