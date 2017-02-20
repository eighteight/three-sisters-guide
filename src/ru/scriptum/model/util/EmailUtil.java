/*
 * Scriptum Project
 */
package ru.scriptum.model.util;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import ru.scriptum.model.exception.CatalogException;
import ru.scriptum.spring.session.SpringContext;

//

/**
 * Utility class to send email.
 * 
 * @author <a href="mailto:dev@scriptum.ru">Developer</a>
 */
public class EmailUtil {
	// the logger for this class
	private static Log logger = LogFactory
			.getLog("ru.scriptum.view.util.EmailUtil");

	/**
	 * Send email to a list of recipients.
	 * 
	 * @param smtpHost
	 *            the SMTP email server address
	 * @param receiverAddress
	 *            the sender email address
	 * @param receiverName
	 *            the sender name
	 * @param recipients
	 *            a list of receipients email addresses
	 * @param sub
	 *            the subject of the email
	 * @param msg
	 *            the message content of the email
	 */
	public static void sendPlainEmail(String smtpHost, String smtpUsername,
			String smtpPassword, boolean smtpAuthRequired,
			String receiverAddress, String receiverName, String sendersAddress,
			String sendersName, String sub, String msg) throws CatalogException {
		if (smtpHost == null) {
			String errMsg = "Could not send email: smtp host address is null";

			logger.error(errMsg);
			throw new CatalogException(errMsg);
		}

		try {
			Properties props = System.getProperties();
			props.put("mail.smtp.host", smtpHost);
			props.put("mail.smtp.auth", Boolean.toString(smtpAuthRequired));
			props.put("mail.smtp.debug", "true");
			// props.put("mail.debug", "true");
			Authenticator auth = null;
			if (smtpAuthRequired) {
				auth = new SMTPAuthenticator(smtpUsername, smtpPassword);
			}
			Session session = Session.getDefaultInstance(props, auth);

			MimeMessage message = new MimeMessage(session);
			message.addHeader("Content-type", "text/plain");
			message.setSubject(sub);
			message.setFrom(new InternetAddress(sendersAddress, sendersName));
			message.addRecipients(Message.RecipientType.TO, receiverAddress);

			message.setText(msg);
			message.setSentDate(new Date());
			Transport.send(message);
		} catch (Exception e) {
			String errorMsg = "Could not send email";
			logger.error(errorMsg, e);

			throw new CatalogException("errorMsg", e);
		}
	}

	/**
	 * Send email to a list of recipients.
	 * @param properties 
	 * 
	 * @param smtpHost
	 *            the SMTP email server address
	 * @param receiverAddress
	 *            the sender email address
	 * @param receiverName
	 *            the sender name
	 * @param recipients
	 *            a list of receipients email addresses
	 * @param sub
	 *            the subject of the email
	 * @param confirmationUrl
	 *            the message content of the email
	 * @param confirmationUrl2 
	 */
	public static void sendHtmlEmail(JavaMailSender mailSender, String smtpHost, String smtpUsername,
			String smtpPassword, boolean smtpAuthRequired,
			String receiverAddress, String receiverName, String sendersAddress,
			String sendersName, String smtpPort, String sub, String confirmationUrl) throws CatalogException {
		if (smtpHost == null) {
			String errMsg = "Could not send email: smtp host address is null";
			logger.error(errMsg);
			throw new CatalogException(errMsg);
		}
		Session session = null;
		Properties props = null;
		MimeMessage message = null;
		try {
			props = System.getProperties();
			Authenticator auth = null;
			if (smtpAuthRequired) {
				auth = new SMTPAuthenticator(smtpUsername, smtpPassword);
			}
			session = Session.getDefaultInstance(props, auth);

			message = new MimeMessage(session);
			message.addHeader("Content-type", "text/plain");
			message.setSubject(sub);
			message.setFrom(new InternetAddress(sendersAddress, sendersName));
			message.addRecipients(Message.RecipientType.TO, receiverAddress);

			message.setText(confirmationUrl);
			message.setSentDate(new Date());
		} catch (Exception e) {
			String errorMsg = "Could not send email";
			logger.error(errorMsg, e);
			throw new CatalogException(errorMsg, e);
		}

		MimeMessageHelper helper;
		try {
			// use the true flag to indicate you need a multipart message
			helper = new MimeMessageHelper(message, true);
			helper.setTo(receiverAddress);

			String htmlMessage = "<html><body>";
			htmlMessage += "Dear "+receiverName+":<br>";
			htmlMessage += "Please confirm your registration <a href='"+confirmationUrl+"'>here</a><br>";
			htmlMessage += "Respectfully<br>";
			htmlMessage += "<img src='cid:identifier1234'></body></html>";
			helper.setText(htmlMessage, true);
			String path = getRealContextRoot()+"/images/logo-theatron.gif";
			FileSystemResource res = new FileSystemResource(new File(path));
			helper.addInline("identifier1234", res);

			mailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	public static String getRealContextRoot() {
		return SpringContext.getInstance().getServletContext().getRealPath(
				System.getProperty("file.separator"));
	}
}
