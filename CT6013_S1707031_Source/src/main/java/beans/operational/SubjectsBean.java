package beans.operational;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;

/**
 * @author Denny-Jo
 * SubjectsBean JavaBean - Assists with storing DB data for the Subject Table
 * */
public class SubjectsBean
{
	static final Logger LOG = Logger.getLogger(SubjectsBean.class);

	private String fSubjectId;
	private String fName;
	private String fCourses;

	public SubjectsBean()
	{
		//Empty Constructor
	}

	public SubjectsBean(ResultSet resultSet)
	{
		try
		{
			fSubjectId = String.valueOf(resultSet.getString("Subject_Id"));
			fName = String.valueOf(resultSet.getString("Subject_Name"));
			fCourses = String.valueOf(resultSet.getString("Subject_Courses"));
		}
		catch (SQLException e)
		{
			LOG.error("Unable to create Bean from ResultSet", e);
		}
	}

	public void setSubjectId(String id)
	{
		fSubjectId = id;
	}
	public String getSubjectId()
	{
		return fSubjectId;
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
