package mail;

import beans.UserBean;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.log4j.Logger;

/**
 * @author Denny-Jo
 * Emailer - Class Constructor will retrieve hidden properties for basic SMTP Connection.
 *
 * This Class is used for sending Users their 2FA Authorization Pin when they attempt a Login to the system.
 * */
public class Emailer
{
	static final Logger LOG = Logger.getLogger(Emailer.class);
	private String username;
	private String password;

	public Emailer()
	{
		Properties props = new Properties();
		try
		{
			InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("/config.properties");
			if(resourceAsStream != null)
			{
				props.load(resourceAsStream);
				username = props.getProperty("mailsender");
				password = props.getProperty("mailpword");
			}
			else
			{
				throw new FileNotFoundException("Could not find properties file for configuring smtp mail");
			}
		}
		catch (IOException e)
		{
			LOG.error(e);
		}
	}

	public void emailKeyToUser(UserBean bean, String pin)
	{
		if(bean != null && pin != null)
		{
			generateMail(pin, bean);
		}
	}

	private void generateMail(String pin, UserBean bean)
	{
		//Generate plain/text mail (no need for HTML)

		String from = "dennyjomiller98@gmail.com";
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					@Override
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});
		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(bean.getUsername()));
			message.setSubject("Your Authorization Pin");
			message.setText("Your credentials have recently been used to attempt login. \r\n Your 4 Digit Authentication Pin is: " + pin);

			Transport.send(message);
			LOG.debug("Sent message successfully");
		} catch (MessagingException e) {
			LOG.error(e);
		}
	}
}
