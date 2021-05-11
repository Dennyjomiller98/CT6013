package beans.operational;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;

/**
 * @author Denny-Jo
 * CoursesBean JavaBean - Assists with storing DB data for the Courses Table
 * */
public class CoursesBean
{
	static final Logger LOG = Logger.getLogger(CoursesBean.class);

	private String fCourseId;
	private String fSubjectId;
	private String fCourseName;
	private String fModuleIds;
	private String fTutor;

	public CoursesBean()
	{
		//Empty Constructor
	}

	public CoursesBean(ResultSet resultSet)
	{
		try
		{
			fCourseId = String.valueOf(resultSet.getString("Course_Id"));
			fSubjectId = String.valueOf(resultSet.getString("Subject_Id"));
			fCourseName = String.valueOf(resultSet.getString("Course_Name"));
			fModuleIds = String.valueOf(resultSet.getString("Module_Ids"));
			fTutor = String.valueOf(resultSet.getString("Course_Tutor"));
		}
		catch (SQLException e)
		{
			LOG.error("Unable to create Bean from ResultSet", e);
		}
	}

	public void setCourseId(String courseId)
	{
		fCourseId = courseId;
	}
	public String getCourseId()
	{
		return fCourseId;
	}

	public void setSubjectId(String subjectId)
	{
		fSubjectId = subjectId;
	}
	public String getSubjectId()
	{
		return fSubjectId;
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

	public void setCourseTutor(String tutorEmail)
	{
		fTutor = tutorEmail;
	}
	public String getCourseTutor()
	{
		return fTutor;
	}
}
