package beans.operational.dimensions;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;

/**
 * @author Denny-Jo
 * DimModulesBean JavaBean - Assists with storing DB data for the Modules SlowChangingDimension Table
 * */
public class DimModulesBean
{
	static final Logger LOG = Logger.getLogger(DimModulesBean.class);

	private String fDimId;
	private String fModuleId;
	private String fEffectiveDate;
	private String fExpiredDate;
	private String fIsCurrent;
	private String fModuleName;
	private String fModuleTutor;
	private String fSemesterLength;
	private String fSemester;
	private String fCats;

	public DimModulesBean()
	{
		//Empty Constructor
	}

	public DimModulesBean(ResultSet resultSet)
	{
		try
		{
			fDimId = String.valueOf(resultSet.getString("Dimension_Id"));
			fModuleId = String.valueOf(resultSet.getString("Module_Id"));
			fEffectiveDate = String.valueOf(resultSet.getString("Date_Effective"));
			fExpiredDate = String.valueOf(resultSet.getString("Date_Expired"));
			fIsCurrent = String.valueOf(resultSet.getString("Is_Current"));
			fModuleName = String.valueOf(resultSet.getString("Module_Name"));
			fModuleTutor = String.valueOf(resultSet.getString("Module_Tutor"));
			fSemesterLength = String.valueOf(resultSet.getString("Semester_Length"));
			fSemester = String.valueOf(resultSet.getString("Semester"));
			fCats = String.valueOf(resultSet.getString("Cats"));
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

	public void setModuleId(String id)
	{
		fModuleId = id;
	}
	public String getModuleId()
	{
		return fModuleId;
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

	public void setModuleName(String name)
	{
		fModuleName = name;
	}
	public String getModuleName()
	{
		return fModuleName;
	}

	public void setModuleTutor(String tutor)
	{
		fModuleTutor = tutor;
	}
	public String getModuleTutor()
	{
		return fModuleTutor;
	}

	public void setSemesterLength(String length)
	{
		fSemesterLength = length;
	}
	public String getSemesterLength()
	{
		return fSemesterLength;
	}

	public void setSemester(String semester)
	{
		fSemester = semester;
	}
	public String getSemester()
	{
		return fSemester;
	}

	public void setCats(String cats)
	{
		fCats = cats;
	}
	public String getCats()
	{
		return fCats;
	}
}
