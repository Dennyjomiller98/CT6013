package beans.operational.dimensions;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;

/**
 * @author Denny-Jo
 * DimStudentsBean JavaBean - Assists with storing DB data for the Students SlowChangingDimension Table
 * */
public class DimStudentsBean
{
	static final Logger LOG = Logger.getLogger(DimStudentsBean.class);

	private String fDimId;
	private String fStudentId;
	private String fEffectiveDate;
	private String fExpiredDate;
	private String fIsCurrent;
	private String fEmail;
	private String fPword;
	private String fFirstname;
	private String fSurname;
	private String fDob;
	private String fAddress;

	public DimStudentsBean()
	{
		//Empty Constructor
	}

	public DimStudentsBean(ResultSet resultSet)
	{
		try
		{
			fDimId = String.valueOf(resultSet.getString("Dimension_Id"));
			fStudentId = String.valueOf(resultSet.getString("Student_Id"));
			fEffectiveDate = String.valueOf(resultSet.getString("Date_Effective"));
			fExpiredDate = String.valueOf(resultSet.getString("Date_Expired"));
			fIsCurrent = String.valueOf(resultSet.getString("Is_Current"));
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

	public void setDimensionId(String id)
	{
		fDimId = id;
	}
	public String getDimensionId()
	{
		return fDimId;
	}

	public void setStudentId(String id)
	{
		fStudentId = id;
	}
	public String getStudentId()
	{
		return fStudentId;
	}

	public void setEffectiveDate(String date)
	{
		fEffectiveDate = date;
	}
	public String getEffectiveDate()
	{
		return fEffectiveDate;
	}

	public void setExpiredDate(String date)
	{
		fExpiredDate = date;
	}
	public String getExpiredDate()
	{
		return fExpiredDate;
	}

	public void setIsCurrent(String isCurrentRecord)
	{
		fIsCurrent = isCurrentRecord;
	}
	public String getIsCurrent()
	{
		return fIsCurrent;
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
