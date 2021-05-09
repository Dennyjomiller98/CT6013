package beans.operational;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;

public class AssignmentsBean
{
	static final Logger LOG = Logger.getLogger(AssignmentsBean.class);

	private String fAssignmentId;
	private String fStudentId;
	private String fAcademicYear;
	private String fModule;
	private String fSemester;
	private String fGrade;
	private String fResit;
	private String fResitGrade;

	public AssignmentsBean()
	{
		//Empty Constructor
	}

	public AssignmentsBean(ResultSet resultSet)
	{
		try
		{
			fAssignmentId = String.valueOf(resultSet.getString("Assignment_Id"));
			fStudentId = String.valueOf(resultSet.getString("Student_Id"));
			fAcademicYear = String.valueOf(resultSet.getString("Academic_Year"));
			fModule = String.valueOf(resultSet.getString("Module"));
			fSemester = String.valueOf(resultSet.getString("Semester"));
			fGrade = String.valueOf(resultSet.getString("Grade"));
			fResit = String.valueOf(resultSet.getString("Resit"));
			fResitGrade = String.valueOf(resultSet.getString("Resit_Grade"));
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
}