package beans.operational;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;

public class AssignmentsBean
{
	static final Logger LOG = Logger.getLogger(AssignmentsBean.class);

	private int fAssignmentId;
	private String fStudentId;
	private int fAcademicYear;
	private int fModule;
	private int fSemester;
	private int fGrade;
	private String fResit;
	private int fResitGrade;

	public AssignmentsBean()
	{
		//Empty Constructor
	}

	public AssignmentsBean(ResultSet resultSet)
	{
		try
		{
			fAssignmentId = Integer.parseInt(resultSet.getString("Assignment_Id"));
			fStudentId = String.valueOf(resultSet.getString("Student_Id"));
			fAcademicYear = Integer.parseInt(resultSet.getString("Academic_Year"));
			fModule = Integer.parseInt(resultSet.getString("Module"));
			fSemester = Integer.parseInt(resultSet.getString("Semester"));
			fGrade = Integer.parseInt(resultSet.getString("Grade"));
			fResit = String.valueOf(resultSet.getString("Resit"));
			fResitGrade = Integer.parseInt(resultSet.getString("Resit_Grade"));
		}
		catch (SQLException e)
		{
			LOG.error("Unable to create Bean from ResultSet", e);
		}
	}

	public void setAssignmentId(int id)
	{
		fAssignmentId = id;
	}
	public int getAssignmentId()
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

	public void setAcademicYear(int year)
	{
		fAcademicYear = year;
	}
	public int getAcademicYear()
	{
		return fAcademicYear;
	}

	public void setModule(int moduleId)
	{
		fModule = moduleId;
	}

	public int getModule()
	{
		return fModule;
	}

	public void setSemester(int semester)
	{
		fSemester = semester;
	}
	public int getSemester()
	{
		return fSemester;
	}

	public void setGrade(int grade)
	{
		fGrade = grade;
	}
	public int getGrade()
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

	public void setResitGrade(int resitGrade)
	{
		fResitGrade = resitGrade;
	}
	public int getResitGrade()
	{
		return fResitGrade;
	}
}