package encryption;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import org.apache.log4j.Logger;

public class PasswordHasher
{
	static final Logger LOG = Logger.getLogger(PasswordHasher.class);

	public PasswordHasher()
	{
		//Empty Constructor
	}

	public String encrypt(final String dataToEncrypt) {
		String ret = null;
		try
		{
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] digest = md5.digest(dataToEncrypt.getBytes());
			ret = Base64.getEncoder().encodeToString(digest);
		}
		catch (NoSuchAlgorithmException e)
		{
			LOG.error(e);
		}
		return ret;
	}
}
