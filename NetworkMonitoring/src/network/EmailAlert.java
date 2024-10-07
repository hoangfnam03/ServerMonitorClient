//package network;
//import java.util.Properties;
//import javax.mail.*;
//import javax.mail.internet.*;
//
//public class EmailAlert {
//    public static void sendAlert(String message) {
//        // Configure email settings
//        String to = "your-email@example.com";  // Replace with your email
//        String from = "alert@example.com";
//        String host = "smtp.example.com";      // Replace with your SMTP server
//
//        Properties properties = System.getProperties();
//        properties.setProperty("mail.smtp.host", host);
//
//        // Get the default Session object
//        Session session = Session.getDefaultInstance(properties);
//
//        try {
//            // Create a default MimeMessage object
//            MimeMessage msg = new MimeMessage(session);
//            
//            // Set From: header field
//            msg.setFrom(new InternetAddress(from));
//
//            // Set To: header field
//            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
//
//            // Set Subject: header field
//            msg.setSubject("Network Monitoring Alert");
//
//            // Now set the actual message
//            msg.setText(message);
//
//            // Send message
//            Transport.send(msg);
//            System.out.println("Alert sent successfully.");
//        } catch (MessagingException mex) {
//            mex.printStackTrace();
//        }
//    }
//}
