package beans.operational.dimensions;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;

public class DimEnrollmentsBean
{
	static final Logger LOG = Logger.getLogger(DimEnrollmentsBean.class);

	private String fDimId;
	private String fEnrollmentId;
	private String fEffectiveDate;
	private String fExpiredDate;
	private String fIsCurrent;
	private String fStudentId;
	private String fIsEnrolled;
	private String fCourseId;
	private String fEnrollmentDate;
	private String fHasDropped;

	public DimEnrollmentsBean()
	{
		//Empty Constructor
	}

	public DimEnrollmentsBean(ResultSet resultSet)
	{
		try
		{
			fDimId = String.valueOf(resultSet.getString("Dimension_Id"));
			fEnrollmentId = String.valueOf(resultSet.getString("Enrollment_Id"));
			fEffectiveDate = String.valueOf(resultSet.getString("Date_Effective"));
			fExpiredDate = String.valueOf(resultSet.getString("Date_Expired"));
			fIsCurrent = String.valueOf(resultSet.getString("Is_Current"));
			fStudentId = String.valueOf(resultSet.getString("Student_Id"));
			fIsEnrolled = String.valueOf(resultSet.getString("Is_Enrolled"));
			fCourseId = String.valueOf(resultSet.getString("Course_Id"));
			fEnrollmentDate = String.valueOf(resultSet.getString("Enrollment_Date"));
			fHasDropped = String.valueOf(resultSet.getString("Has_Dropped"));
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

	public void setEnrollmentId(String id)
	{
		fEnrollmentId = id;
	}
	public String getEnrollmentId()
	{
		return fEnrollmentId;
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
		fHasDropped = isDropout;
	}
	public String getHasDropped()
	{
		return fHasDropped;
	}
}
