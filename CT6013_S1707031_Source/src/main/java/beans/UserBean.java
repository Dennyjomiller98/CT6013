package beans;

/**
 * @author Denny-Jo
 * UserBean JavaBean - Assists with storing DB data for a logged in User
 * */
public class UserBean
{
	private String fUsername;
	private String fPassword;
	private String fPin;
	private String fPinStart;
	private String fPinEnd;
	private String fRole;

	public UserBean()
	{
		//No-Args Constructor
	}

	public String getUsername()
	{
		return fUsername;
	}
	public void setUsername(String username)
	{
		fUsername = username;
	}

	public String getPword()
	{
		return fPassword;
	}
	public void setPword(String pword)
	{
		fPassword = pword;
	}

	public String getPin()
	{
		return fPin;
	}
	public void setPin(String pin)
	{
		fPin = pin;
	}

	public String getPinStart()
	{
		return fPinStart;
	}
	public void setPinStart(String date)
	{
		fPinStart = date;
	}

	public String getPinEnd()
	{
		return fPinEnd;
	}
	public void setPinEnd(String date)
	{
		fPinEnd = date;
	}

	public String getRole()
	{
		return fRole;
	}
	public void setRole(String role)
	{
		fRole = role;
	}
}
