package mail;

import beans.UserBean;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.log4j.Logger;

public class Emailer
{
	static final Logger LOG = Logger.getLogger(Emailer.class);
	private static final String USERNAME = "s1707031uog@gmail.com";
	private static final String PASSWORD = "s1707031@";

	public Emailer()
	{
		//Empty Constructor
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
						return new PasswordAuthentication(USERNAME, PASSWORD);
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
