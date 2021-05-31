package beans.dw;

public class DWEnrollmentsBean
{
	private String fId;
	private String fStudentId;
	private String fFirstname;
	private String fSurname;
	private String fCourseId;
	private String fCourseName;
	private String fEnrollmentDate;
	private String fHasDropped;

	public DWEnrollmentsBean()
	{
		//Empty Constructor
	}

	public void setId(String enrollmentId)
	{
		fId = enrollmentId;
	}
	public String getId()
	{
		return fId;
	}

	public void setStudentId(String studentId)
	{
		fStudentId = studentId;
	}
	public String getStudentId()
	{
		return fStudentId;
	}

	public void setStudentFirstname(String firstname)
	{
		fFirstname = firstname;
	}
	public String getStudentFirstname()
	{
		return fFirstname;
	}

	public void setStudentSurname(String surname)
	{
		fSurname = surname;
	}
	public String getStudentSurname()
	{
		return fSurname;
	}

	public void setCourseId(String courseId)
	{
		fCourseId = courseId;
	}
	public String getCourseId()
	{
		return fCourseId;
	}

	public void setCourseName(String courseName)
	{
		fCourseName = courseName;
	}
	public String getCourseName()
	{
		return fCourseName;
	}

	public void setEnrollmentDate(String enrollmentDate)
	{
		fEnrollmentDate = enrollmentDate;
	}
	public String getEnrollmentDate()
	{
		return fEnrollmentDate;
	}

	public void setHasDropped(String hasDropped)
	{
		fHasDropped = hasDropped;
	}
	public String getHasDropped()
	{
		return fHasDropped;
	}
}
