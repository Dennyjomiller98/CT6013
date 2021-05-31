package beans.operational;

import beans.dw.DWAssignmentsBean;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;

/**
 * @author Denny-Jo
 * AssignmentsBean JavaBean - Assists with storing DB data for the Assignments Table
 * */
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
	private String fInternational;
	private String fTutorId;
	private String fTutorFirstname;
	private String fTutorSurname;

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
			fInternational = String.valueOf(resultSet.getString("International"));
		}
		catch (SQLException e)
		{
			LOG.error("Unable to create Bean from ResultSet", e);
		}
	}

	public AssignmentsBean(DWAssignmentsBean bean)
	{
		try
		{
			fAssignmentId = String.valueOf(bean.getAssignmentId());
			fStudentId = String.valueOf(bean.getStudentId());
			fAcademicYear = String.valueOf(bean.getAcademicYear());
			fModule = String.valueOf(bean.getModule());
			fSemester = String.valueOf(bean.getSemester());
			fGrade = String.valueOf(bean.getGrade());
			fResit = String.valueOf(bean.getResit());
			fResitGrade = String.valueOf(bean.getResitGrade());
			fInternational = String.valueOf(bean.getInternational());
			fTutorId = String.valueOf(bean.getTutorId());
			fTutorFirstname = String.valueOf(bean.getTutorFirstname());
			fTutorSurname = String.valueOf(bean.getTutorSurname());
		}
		catch (Exception e)
		{
			LOG.error("Unable to create Bean from DW Bean", e);
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

	public void setTutorId(String id)
	{
		fTutorId = id;
	}
	public String getTutorId()
	{
		return fTutorId;
	}

	public void setTutorFirstname(String firstname)
	{
		fTutorFirstname = firstname;
	}
	public String getTutorFirstname()
	{
		return fTutorFirstname;
	}

	public void setTutorSurname(String surname)
	{
		fTutorSurname = surname;
	}
	public String getTutorSurname()
	{
		return fTutorSurname;
	}
}