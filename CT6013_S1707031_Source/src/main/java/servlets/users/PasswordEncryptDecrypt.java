package servlets.users;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.apache.log4j.Logger;

public class PasswordEncryptDecrypt
{
	private PasswordEncryptDecrypt()
	{
		//Empty constructor
	}

	static final Logger LOG = Logger.getLogger(PasswordEncryptDecrypt.class);

	private static final String KEY = "randomPasswordKey123";

	public static String encryptPasswordToStore(String pword)
	{
		String hashedPassword = null;
		try
		{
			SecretKeySpec secretKeySpec=new SecretKeySpec(KEY.getBytes(),"Blowfish");
			Cipher cipher=Cipher.getInstance("Blowfish");
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
			byte[] encrypted=cipher.doFinal(pword.getBytes());
			hashedPassword =new String(encrypted);
		}
		catch (Exception e) {
			LOG.error("Error hashing password");
		}
		return hashedPassword;
	}

	public static String decryptPasswordFromDB(String pword)
	{
		String hashedPassword = null;
		try
		{
			SecretKeySpec secretKeySpec=new SecretKeySpec(KEY.getBytes(),"Blowfish");
			Cipher cipher=Cipher.getInstance("Blowfish");
			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
			byte[] decrypted=cipher.doFinal(pword.getBytes());
			hashedPassword=new String(decrypted);
		}
		catch (Exception e) {
			LOG.error("Error decrypting password");
		}
		return hashedPassword;
	}
}
