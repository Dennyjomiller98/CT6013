package beans.operational.dimensions;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;

public class DimTutorsBean
{
	static final Logger LOG = Logger.getLogger(DimTutorsBean.class);

	private String fDimId;
	private String fTutorId;
	private String fEffectiveDate;
	private String fExpiredDate;
	private String fIsCurrent;
	private String fEmail;
	private String fPword;
	private String fFirstname;
	private String fSurname;
	private String fDob;
	private String fAddress;

	public DimTutorsBean()
	{
		//Empty Constructor
	}

	public DimTutorsBean(ResultSet resultSet)
	{
		try
		{
			fDimId = String.valueOf(resultSet.getString("Dimension_Id"));
			fTutorId = String.valueOf(resultSet.getString("Tutor_Id"));
			fEffectiveDate = String.valueOf(resultSet.getString("Date_Effective"));
			fExpiredDate = String.valueOf(resultSet.getString("Date_Expired"));
			fIsCurrent = String.valueOf(resultSet.getString("Is_Current"));
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

	public void setDimensionId(String id)
	{
		fDimId = id;
	}
	public String setDimensionId()
	{
		return fDimId;
	}

	public void setTutorId(String id)
	{
		fTutorId = id;
	}
	public String getTutorId()
	{
		return fTutorId;
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
