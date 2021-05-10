package beans.operational.dimensions;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;

public class DimSubjectsBean
{
	static final Logger LOG = Logger.getLogger(DimSubjectsBean.class);

	private String fDimId;
	private String fSubjectId;
	private String fEffectiveDate;
	private String fExpiredDate;
	private String fIsCurrent;
	private String fName;
	private String fCourses;

	public DimSubjectsBean()
	{
		//Empty Constructor
	}

	public DimSubjectsBean(ResultSet resultSet)
	{
		try
		{
			fDimId = String.valueOf(resultSet.getString("Dimension_Id"));
			fSubjectId = String.valueOf(resultSet.getString("Subject_Id"));
			fEffectiveDate = String.valueOf(resultSet.getString("Date_Effective"));
			fExpiredDate = String.valueOf(resultSet.getString("Date_Expired"));
			fIsCurrent = String.valueOf(resultSet.getString("Is_Current"));
			fName = String.valueOf(resultSet.getString("Subject_Name"));
			fCourses = String.valueOf(resultSet.getString("Subject_Courses"));
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

	public void setSubjectId(String id)
	{
		fSubjectId = id;
	}
	public String getSubjectId()
	{
		return fSubjectId;
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

	public void setName(String name)
	{
		fName = name;
	}
	public String getName()
	{
		return fName;
	}

	public void setCourses(String coursesCsv)
	{
		fCourses = coursesCsv;
	}
	public String getCourses()
	{
		return fCourses;
	}
}
