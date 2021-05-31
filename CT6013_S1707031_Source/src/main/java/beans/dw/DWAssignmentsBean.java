package beans.dw;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;

public class DWAssignmentsBean
{
	static final Logger LOG = Logger.getLogger(DWAssignmentsBean.class);
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

	public DWAssignmentsBean(ResultSet resultSet)
	{
		try
		{
			fAssignmentId = String.valueOf(resultSet.getString("Assignment_Id"));
			fStudentId = String.valueOf(resultSet.getString("Student_Id"));
			fAcademicYear = String.valueOf(resultSet.getString("Academic_Year"));
			fModule = String.valueOf(resultSet.getString("Module_Id"));
			fSemester = String.valueOf(resultSet.getString("Semester"));
			fGrade = String.valueOf(resultSet.getString("Grade"));
			fResit = String.valueOf(resultSet.getString("Resit"));
			fResitGrade = String.valueOf(resultSet.getString("Resit_Grade"));
			fInternational = String.valueOf(resultSet.getString("International"));
		}
		catch (SQLException e)
		{
			LOG.error("Unable to create Bean from ResultSet", e);
		}
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
