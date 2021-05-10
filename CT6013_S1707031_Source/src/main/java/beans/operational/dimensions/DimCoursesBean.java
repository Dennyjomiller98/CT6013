package beans.operational.dimensions;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;

public class DimCoursesBean
{
	static final Logger LOG = Logger.getLogger(DimCoursesBean.class);

	private String fDimId;
	private String fCourseId;
	private String fEffectiveDate;
	private String fExpiredDate;
	private String fIsCurrent;
	private String fCourseName;
	private String fModuleIds;
	private String fTutor;
	private String fSubjectId;

	public DimCoursesBean()
	{
		//Empty Constructor
	}

	public DimCoursesBean(ResultSet resultSet)
	{
		try
		{
			fDimId = String.valueOf(resultSet.getString("Dimension_Id"));
			fCourseId = String.valueOf(resultSet.getString("Course_Id"));
			fEffectiveDate = String.valueOf(resultSet.getString("Date_Effective"));
			fExpiredDate = String.valueOf(resultSet.getString("Date_Expired"));
			fIsCurrent = String.valueOf(resultSet.getString("Is_Current"));
			fCourseName = String.valueOf(resultSet.getString("Course_Name"));
			fModuleIds = String.valueOf(resultSet.getString("Module_Ids"));
			fTutor = String.valueOf(resultSet.getString("Course_Tutor"));
			fSubjectId = String.valueOf(resultSet.getString("Subject_Id"));
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

	public void setCourseId(String id)
	{
		fCourseId = id;
	}
	public String getCourseId()
	{
		return fCourseId;
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

	public void setCourseName(String name)
	{
		fCourseName = name;
	}
	public String getCourseName()
	{
		return fCourseName;
	}

	public void setModuleIds(String modulesCsv)
	{
		fModuleIds = modulesCsv;
	}
	public String getModuleIds()
	{
		return fModuleIds;
	}

	public void setTutor(String tutor)
	{
		fTutor = tutor;
	}
	public String getTutor()
	{
		return fTutor;
	}

	public void setSubjectId(String subjectId)
	{
		fSubjectId = subjectId;
	}
	public String getSubjectId()
	{
		return fSubjectId;
	}
}
