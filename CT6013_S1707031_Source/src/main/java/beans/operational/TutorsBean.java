package beans.operational;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;

public class TutorsBean
{
	static final Logger LOG = Logger.getLogger(TutorsBean.class);

	private String fTutorId;
	private String fEmail;
	private String fPword;
	private String fFirstname;
	private String fSurname;
	private String fDob;
	private String fAddress;

	public TutorsBean()
	{
		//Empty Constructor
	}

	public TutorsBean(ResultSet resultSet)
	{
		try
		{
			fTutorId = String.valueOf(resultSet.getString("Tutor_Id"));
			fFirstname = String.valueOf(resultSet.getString("Firstname"));
			fSurname = String.valueOf(resultSet.getString("Surname"));
			fEmail = String.valueOf(resultSet.getString("Email"));
			fDob = String.valueOf(resultSet.getString("Dob"));
			fAddress = String.valueOf(resultSet.getString("Address"));
			fPword = String.valueOf(resultSet.getString("Pword"));
		}
		catch (SQLException e)
		{
			LOG.error("Unable to create Bean from ResultSet", e);
		}
	}

	public void setTutorId(String id)
	{
		fTutorId = id;
	}
	public String getTutorId()
	{
		return fTutorId;
	}

	public void setEmail(String email)
	{
		fEmail = email;
	}
	public String getEmail()
	{
		return fEmail;
	}

	public void setPword(String pword)
	{
		fPword = pword;
	}
	public String getPword()
	{
		return fPword;
	}

	public void setFirstname(String name)
	{
		fFirstname = name;
	}
	public String getFirstname()
	{
		return fFirstname;
	}

	public void setSurname(String name)
	{
		fSurname = name;
	}
	public String getSurname()
	{
		return fSurname;
	}

	public void setDob(String dob)
	{
		fDob = dob;
	}
	public String getDob()
	{
		return fDob;
	}

	public void setAddress(String address)
	{
		fAddress = address;
	}
	public String getAddress()
	{
		return fAddress;
	}
}
