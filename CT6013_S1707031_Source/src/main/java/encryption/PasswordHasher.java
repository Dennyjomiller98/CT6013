package encryption;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.log4j.Logger;

/**
 * @author Denny-Jo
 * PasswordHasher is used to validate inputted Password (using MD5) against the DW User Login information
 * @implNote this is not the preferred method, but it's a simple 'Proof-Of-Concept' to show how User Security can be enhanced, rather than PlainText Password Storing
 * */
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
			StringBuilder hex = new StringBuilder();
			for (byte b : digest) {
				hex.append(String.format("%02x", b));
			}
			ret = hex.toString();
		}
		catch (NoSuchAlgorithmException e)
		{
			LOG.error(e);
		}
		return ret;
	}
}
