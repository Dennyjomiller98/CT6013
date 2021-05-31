package beans.dw;

public class DWAssignmentsBean
{
	private String fAssignmentId;
	private String fStudentId;
	private String fAcademicYear;
	private String fModule;
	private String fSemester;
	private String fGrade;
	private String fResit;
	private String fResitGrade;
	private String fInternational;
	private String fFirstname;
	private String fSurname;
	private String fCourseId;
	private String fCourseName;

	public DWAssignmentsBean()
	{
		//Empty Constructor
	}

	public void setAssignmentId(String id)
	{
		fAssignmentId = id;
	}
	public String getAssignmentId()
	{
		return fAssignmentId;
	}

	public void setStudentId(String id)
	{
		fStudentId = id;
	}
	public String getStudentId()
	{
		return fStudentId;
	}

	public void setAcademicYear(String year)
	{
		fAcademicYear = year;
	}
	public String getAcademicYear()
	{
		return fAcademicYear;
	}

	public void setModule(String moduleId)
	{
		fModule = moduleId;
	}

	public String getModule()
	{
		return fModule;
	}

	public void setSemester(String semester)
	{
		fSemester = semester;
	}
	public String getSemester()
	{
		return fSemester;
	}

	public void setGrade(String grade)
	{
		fGrade = grade;
	}
	public String getGrade()
	{
		return fGrade;
	}

	public void setResit(String shouldResit)
	{
		fResit = shouldResit;
	}
	public String getResit()
	{
		return fResit;
	}

	public void setResitGrade(String resitGrade)
	{
		fResitGrade = resitGrade;
	}
	public String getResitGrade()
	{
		return fResitGrade;
	}

	public void setInternational(String isInternational)
	{
		fInternational = isInternational;
	}
	public String getInternational()
	{
		return fInternational;
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

	public void setCourseId(String id)
	{
		fCourseId = id;
	}
	public String getCourseId()
	{
		return fCourseId;
	}

	public void setCourseName(String name)
	{
		fCourseName = name;
	}
	public String getCourseName()
	{
		return fCourseName;
	}
}
