package CommonClassReusables;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;



public class Email extends AGlobalComponents {

	

	/**
	* <h1>sendEmail</h1>
	* This is a Method to send Email
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	03-31-2020
	* @param   	ITestResult result
	* @return  	none
	*/
	
	public static void sendEmail(String emailMsg) {

//		try {
//			// sets SMTP server properties
//			Properties properties = new Properties();
//			properties.put("mail.smtp.host", mailHost);
//			properties.put("mail.smtp.port", mailPort);
//			properties.put("mail.smtp.auth", "true");
//			properties.put("mail.smtp.starttls.enable", "true");
//
//			// creates a new session with an authenticator
//			Authenticator auth = new Authenticator() {
//				public PasswordAuthentication getPasswordAuthentication() {
//					return new PasswordAuthentication(mailUsername, mailPassword);
//				}
//			};
//
//			Session session = Session.getInstance(properties, auth);
//
//			// creates a new e-mail message
//			Message msg2 = new MimeMessage(session);
//
//			msg2.setFrom(new InternetAddress(mailFrom));
//			InternetAddress[] toAddresses = { new InternetAddress(mailTo) };
//			msg2.setRecipients(Message.RecipientType.TO, toAddresses);
//			msg2.setSubject(mailSubject);
//			msg2.setSentDate(new Date());
//			// set plain text message
//			msg2.setContent(emailMsg, "text/html");
//
//			// sends the e-mail
//			Transport.send(msg2);
//		} catch (Exception e) {
//
//		}
//	}
	}
	
	 
	 
	 
	public static void sendMailWithReport(String[] args) {
	 
	    final String username = "testusername@gmail.com"; //change to your Gmail username
	    final String password = "testpassword"; //change to your Gmail password
	    final String from = "test.from.email@helloselenium.com"; //change to from email address
	    final String to = "test.to.email@helloselenium.com"; //change to to email address
	    final String cc = "test.cc.email@helloselenium.com"; //change to cc email address
	    final String bcc = "test.bcc.email@helloselenium.com"; //change to bcc email address
	    final String subject = "Test Email from Hello Selenium"; //change to your subject
	    final String msg = "Test Email from Hello Selenium to learn the automation of email message sending using Java Mail API from Gmail."; //change to your message
	 
	    Properties props = new Properties();
	    props.put("mail.smtp.auth", true);
	    props.put("mail.smtp.starttls.enable", true);
	    props.put("mail.smtp.host", "smtp.gmail.com");
	    props.put("mail.smtp.port", "587");
	 
	    Session session = Session.getInstance(props,
	            new javax.mail.Authenticator() {
	                protected PasswordAuthentication getPasswordAuthentication() {
	                    return new PasswordAuthentication(username, password);
	                }
	            });
	 
	    try {
	 
	        Message message = new MimeMessage(session);
	        message.setFrom(new InternetAddress(from));
	        message.setRecipients(Message.RecipientType.TO,
	                InternetAddress.parse(to));
	        //below code only requires if your want cc email address
	        message.setRecipients(Message.RecipientType.CC,
	                InternetAddress.parse(cc));
	        //below code only requires if your want bcc email address
	        message.setRecipients(Message.RecipientType.BCC,
	                InternetAddress.parse(bcc));
	        message.setSubject(subject);
	        message.setText(msg);
	 
	        MimeBodyPart messageBodyPart = new MimeBodyPart();
	 
	        Multipart multipart = new MimeMultipart();
	 
	        messageBodyPart = new MimeBodyPart();
	        String file1 = "drive:\\folder1\\folder2\\file.txt"; //change to your attachment filepath
	        String fileName1 = "file.txt"; //change to your attachment filename
	        DataSource source1 = new FileDataSource(file1);
	        messageBodyPart.setDataHandler(new DataHandler(source1));
	        messageBodyPart.setFileName(fileName1);
	        multipart.addBodyPart(messageBodyPart);
	 
	        message.setContent(multipart);
	 
	        System.out.println("Sending");
	 
	        Transport.send(message);
	 
	        System.out.println("Done");
	 
	    } catch (MessagingException e) {
	        e.printStackTrace();
	    }
	  
	}
	
	
	
	
	

}
