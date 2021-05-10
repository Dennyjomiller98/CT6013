package beans.operational;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;

public class ModulesBean
{
	static final Logger LOG = Logger.getLogger(ModulesBean.class);

	private String fModuleId;
	private String fModuleName;
	private String fModuleTutor;
	private String fSemesterLength;
	private String fSemester;
	private String fCats;

	public ModulesBean()
	{
		//Empty Constructor
	}

	public ModulesBean(ResultSet resultSet)
	{
		try
		{
			fModuleId = String.valueOf(resultSet.getString("Module_Id"));
			fModuleName = String.valueOf(resultSet.getString("Module_Name"));
			fModuleTutor = String.valueOf(resultSet.getString("Module_Tutor"));
			fSemesterLength = String.valueOf(resultSet.getString("Semester_Length"));
			fSemester = String.valueOf(resultSet.getString("Semester"));
			fCats = String.valueOf(resultSet.getString("Cats"));
		}
		catch (SQLException e)
		{
			LOG.error("Unable to create Bean from ResultSet", e);
		}
	}

	public void setModuleId(String moduleId)
	{
		fModuleId = moduleId;
	}
	public String getModuleId()
	{
		return fModuleId;
	}

	public void setModuleName(String name)
	{
		fModuleName = name;
	}
	public String getModuleName()
	{
		return fModuleName;
	}

	public void setModuleTutor(String tutor)
	{
		fModuleTutor = tutor;
	}
	public String getModuleTutor()
	{
		return fModuleTutor;
	}

	public void setSemesterLength(String length)
	{
		fSemesterLength = length;
	}
	public String getSemesterLength()
	{
		return fSemesterLength;
	}

	public void setSemester(String semester)
	{
		fSemester = semester;
	}
	public String getSemester()
	{
		return fSemester;
	}

	public void setCats(String cats)
	{
		fCats = cats;
	}
	public String getCats()
	{
		return fCats;
	}
}
