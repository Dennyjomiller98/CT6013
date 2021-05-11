package beans.operational;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;

/**
 * @author Denny-Jo
 * StudentsBean JavaBean - Assists with storing DB data for the Students Table
 * */
public class StudentsBean
{
	static final Logger LOG = Logger.getLogger(StudentsBean.class);

	private String fStudentId;
	private String fEmail;
	private String fPword;
	private String fFirstname;
	private String fSurname;
	private String fDob;
	private String fAddress;

	public StudentsBean()
	{
		//Empty Constructor
	}

	public StudentsBean(ResultSet resultSet)
	{
		try
		{
			fStudentId = String.valueOf(resultSet.getString("Student_Id"));
			fEmail = String.valueOf(resultSet.getString("Email"));
			fPword = String.valueOf(resultSet.getString("Pword"));
			fFirstname = String.valueOf(resultSet.getString("Firstname"));
			fSurname = String.valueOf(resultSet.getString("Surname"));
			fDob = String.valueOf(resultSet.getString("Dob"));
			fAddress = String.valueOf(resultSet.getString("Address"));
		}
		catch (SQLException e)
		{
			LOG.error("Unable to create Bean from ResultSet", e);
		}
	}

	public void setStudentId(String id)
	{
		fStudentId = id;
	}
	public String getStudentId()
	{
		return fStudentId;
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
