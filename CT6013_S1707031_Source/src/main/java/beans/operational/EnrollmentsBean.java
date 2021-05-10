package beans.operational;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;

public class EnrollmentsBean
{
	static final Logger LOG = Logger.getLogger(EnrollmentsBean.class);

	private String fEnrollmentId;
	private String fStudentId;
	private String fIsEnrolled;
	private String fCourseId;
	private String fEnrollmentDate;
	private String fDropout;

	public EnrollmentsBean()
	{
		//Empty Constructor
	}

	public EnrollmentsBean(ResultSet resultSet)
	{
		try
		{
			fEnrollmentId = String.valueOf(resultSet.getString("Enrollment_Id"));
			fStudentId = String.valueOf(resultSet.getString("Student_Id"));
			fIsEnrolled = String.valueOf(resultSet.getString("Is_Enrolled"));
			fCourseId = String.valueOf(resultSet.getString("Course_Id"));
			fEnrollmentDate = String.valueOf(resultSet.getString("Enrollment_Date"));
			fDropout = String.valueOf(resultSet.getString("Has_Dropped"));
		}
		catch (SQLException e)
		{
			LOG.error("Unable to create Bean from ResultSet", e);
		}
	}

	public void setEnrollmentId(String enrollmentId)
	{
		fEnrollmentId = enrollmentId;
	}
	public String getEnrollmentId()
	{
		return fEnrollmentId;
	}

	public void setStudentId(String studentId)
	{
		fStudentId = studentId;
	}
	public String getStudentId()
	{
		return fStudentId;
	}

	public void setIsEnrolled(String isEnrolled)
	{
		fIsEnrolled = isEnrolled;
	}
	public String getIsEnrolled()
	{
		return fIsEnrolled;
	}

	public void setCourseId(String courseId)
	{
		fCourseId = courseId;
	}
	public String getCourseId()
	{
		return fCourseId;
	}

	public void setEnrollmentDate(String dateToString)
	{
		fEnrollmentDate = dateToString;
	}
	public String getEnrollmentDate()
	{
		return fEnrollmentDate;
	}

	public void setHasDropped(String isDropout)
	{
		fDropout = isDropout;
	}
	public String getHasDropped()
	{
		return fDropout;
	}
}
