package de.metro.im.core.testframework.config.reporting.mail;


import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.activation.MimetypesFileTypeMap;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mail {
	MailConfigurator config; 
	private static Logger log = LogManager.getLogger();
	public Mail() throws MailConfiguratorException {
		config = new MailConfigurator("config/mail.properties");
		log.info(config.getClass().getSimpleName() + " loaded.");
	}
	
	public void sendSimpleMail(String mailSubject, String mailBodyText, String toRecipient) throws MessagingException{
		if (!toRecipient.endsWith(config.getDomainRestriction()))	{
			throw new AddressException("Invalid email addresse provided. Recipient must be a member of " + config.getDomainRestriction() + " domain. ");
		}
		MimeMessage message = new MimeMessage(getSession());

			
			message.setFrom(new InternetAddress(config.getSenderAddress()));
			message.addRecipient(Message.RecipientType.CC, new InternetAddress(config.getAdminAddress()));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(toRecipient));

			message.setSubject(mailSubject);
			message.setText(mailBodyText);
	
			Transport transport = getSession().getTransport("smtp");
			transport.connect(getSession().getProperty("mail.smtp.host"), 
					getSession().getProperty("mail.smtp.user"), 
					getSession().getProperty("mail.smtp.password"));  
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			
			System.out.println("[MAIL] " + mailSubject + " (subject)" + " sent.");
			log.info("MAIL [ " + mailSubject + " ] sent.");
	}
	
	public void sendHTMLReport(MailableReport mailableReport){
		String logoFilePath = "./resource/ALiCE2.jpg";
//		String html = buildHTmlBody(mailSubject, reportContext);
		String html = mailableReport.getHtmlBody();
		MimeMessage message = new MimeMessage(getSession());
		Multipart multipart = new MimeMultipart();
	
		try {
		
			message.setFrom(new InternetAddress(config.getSenderAddress()));
			
			if (html.contains("Error during conversion!") || html.contains("FAILED CONFIGURATIONS")){
				message.addRecipient(Message.RecipientType.CC, new InternetAddress(config.getAdminAddress())); 
			}
			
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(config.getImInbox())); 
			
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(html, "text/html");
			multipart.addBodyPart(messageBodyPart);
			
			messageBodyPart = new MimeBodyPart();
			
			File logo = new File(logoFilePath);
			if (logo.isFile()){
				String mimetype= new MimetypesFileTypeMap().getContentType(logo);
			
				if(!mimetype.split("/")[0].equals("image")) {
					throw new Exception("The logo file you tried to attachs is not an image! Type is:" + mimetype);
				}
			
				DataSource logoFileSource = new FileDataSource(logoFilePath);
				messageBodyPart.setFileName("ALiCELogo.jepg");
				messageBodyPart.setDataHandler(new DataHandler(logoFileSource));
				multipart.addBodyPart(messageBodyPart);
				messageBodyPart.setHeader("Content-ID", "<image>");
			
			} else {
			
			System.out.println("Logo file was not found. Could not attach.");
			log.debug("Logo file was not found. Could not attach. " + logo.getPath() + " not found.");
			multipart.addBodyPart(messageBodyPart);
			//messageBodyPart.setHeader("Content-ID", "<image>"); //TODO kann hier vermutlich weggelassen werden, falls das logo nicht gefunden wird
			
			}
			
			message.setContent(multipart);
//			message.setSubject(mailSubject);
			message.setSubject(mailableReport.getMailSubject());
			
			Transport transport = getSession().getTransport("smtp");
			transport.connect(getSession().getProperty("mail.smtp.host"), getSession().getProperty("mail.smtp.user"), getSession().getProperty("mail.smtp.password"));			
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			
			System.out.println("[MAIL] Report sent.");
			log.info("Report sent. ");
		} catch (AddressException ae) {
			log.error(ae);
			ae.printStackTrace();
		} catch (MessagingException me) {
			log.error(me);
			me.printStackTrace();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		
}
	
	private Session getSession() {
		
	Authenticator authenticator = new Authenticator();
	
	Properties props = System.getProperties();
	
	props.put("mail.smtp.submitter", "true");
	props.put("mail.smtp.host", authenticator.getFirewall());
	props.put("mail.smtp.user", authenticator.getPassAuth().getUserName());
	props.put("mail.smtp.password", authenticator.getPassAuth().getPassword());
	props.put("mail.smtp.port", "...");
	props.put("mail.smtp.auth", "true");
	
	
	return Session.getDefaultInstance(props, authenticator);
	
	}
	
	private class Authenticator extends javax.mail.Authenticator{
		private PasswordAuthentication authentication;
		
		//hat keinen effekt auf zustellbarkeit
		public Authenticator() {
			String from = "...";
			String pass = "...";
			this.authentication = new PasswordAuthentication(from, pass);
		}
		
		protected PasswordAuthentication getPassAuth(){
			return this.authentication;
		}
		
		protected String getFirewall(){
			return "...";
		}
	}
	
	public void notifyAdmin(String mailSubject, String mailBodyText) throws MessagingException{
		sendSimpleMail(mailSubject, mailBodyText, config.getAdminAddress());
	}

	public void notifyIMCustomer(String mailSubject, String mailBodyText) throws MessagingException {
		sendSimpleMail(mailSubject, mailBodyText, config.getImInbox());
		
	}
	
}
