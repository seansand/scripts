//NnfpEmailer.groovy

import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;

public class NnfpEmailer
{
   private static String SEAN = "seansand@uwalumni.com"
   private static String RAJIV = "rajiv.maheswaran@gmail.com"


   public static void main(String[] args)
   {
      URL u = new URL("http://seansand.appspot.com/nnfpclassic.groovy")
      String uText = u.getText()
      
      uText = uText.replaceAll("<p>", '<P><FONT FACE = "Courier">') 
      uText = uText.replaceAll("</p>", "</FONT></P>") 

      sendEmail("NNFP results (http://seansand.appspot.com)", uText, "text/html", SEAN, SEAN)
      sendEmail("NNFP results (http://seansand.appspot.com)", uText, "text/html", RAJIV, SEAN)
      
      println ("Done.")
   }    

   public static void sendEmail(String subject, 
                                String content,
                                String contentType,
                                String toAddress,
                                String fromAddress)
   {
      // init sesssion
      /*
      Properties props = new Properties();
      props.put("mail.host", "smtp.comcast.net");
      props.put("mail.host.port", "465");
      props.setProperty("mail.user", "********"); 
      props.setProperty("mail.password", "********");
      Session session = Session.getDefaultInstance(props, null);
      */
      
      final String username = "******************";
		final String password = "*****************";
 
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
 
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
    
    
      // create a message
      Message msg = new MimeMessage(session);
    
      // set addresses
      msg.setFrom(new InternetAddress(fromAddress));
    
      InternetAddress[] addressTo = new InternetAddress[1];
      addressTo[0] = new InternetAddress(toAddress);
      msg.setRecipients(Message.RecipientType.TO, addressTo);
    
      // Setting the Subject
      msg.setSubject(subject);
    
      // build content
      
      msg.setContent(content, contentType ? contentType : "text/plain");  // default
      try
      {
         Transport.send(msg);
         println("Mail sent successfully.")
      }
      catch(Exception e)
      {
         println("Failed to send mail: " + e)
      }
   }
}
