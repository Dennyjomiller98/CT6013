package mongodbbeans;

import java.io.Serializable;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import org.bson.Document;

@Singleton(name = "MongoClientProviderEJB")
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class ModuleBean implements Serializable
{
	protected String fModuleDeadlines;
	protected String fModuleCode;
	protected String fModuleName;
	protected String fModuleTutor;
	protected String fRelatedCourse;
	protected int fCATS;
	protected int fSemester;
	protected boolean fIsCompulsory;

	public ModuleBean()
	{
		//Empty No Args Constructor
	}

	public ModuleBean(Document moduleDoc)
	{
		fModuleCode = String.valueOf(moduleDoc.get("Module_Code"));
		fModuleName = String.valueOf(moduleDoc.get("Module_Name"));
		fModuleTutor = String.valueOf(moduleDoc.get("Module_Tutor"));
		fRelatedCourse = String.valueOf(moduleDoc.get("Related_Course"));
		String cats = String.valueOf(moduleDoc.get("CATS"));
		fCATS = Integer.parseInt(cats);
		String semesterString = String.valueOf(moduleDoc.get("Semester"));
		fSemester = Integer.parseInt(semesterString);
		String isCompulsory = String.valueOf(moduleDoc.get("Is_Compulsory"));
		fIsCompulsory = isCompulsory.equals("true");
		fModuleDeadlines = String.valueOf(moduleDoc.get("Module_Deadlines"));
	}

	public String getModuleCode()
	{
		return fModuleCode;
	}
	public void setModuleCode(String moduleCode)
	{
		fModuleCode = moduleCode;
	}

	public String getModuleName()
	{
		return fModuleName;
	}
	public void setModuleName(String moduleName)
	{
		fModuleName = moduleName;
	}

	public String getModuleTutor()
	{
		return fModuleTutor;
	}
	public void setfModuleTutor(String moduleTutor)
	{
		fModuleTutor = moduleTutor;
	}

	public String getRelatedCourse()
	{
		return fRelatedCourse;
	}
	public void setRelatedCourse(String relatedCourse)
	{
		fRelatedCourse = relatedCourse;
	}

	public int getCATS()
	{
		return fCATS;
	}
	public void setCATS(int cats)
	{
		fCATS = cats;
	}

	//-1 both semesters, 1 semester 1, 2 semester 2
	public int getSemester()
	{
		return fSemester;
	}
	public void setSemester(int semester)
	{
		fSemester = semester;
	}

	public boolean isCompulsory()
	{
		return fIsCompulsory;
	}
	public void setCompulsory(boolean isCompulsory)
	{
		fIsCompulsory = isCompulsory;
	}

	//String csv, relates to semesters (each deadline is for each semester)
	public String getModuleDeadlines()
	{
		return fModuleDeadlines;
	}
	public void setModuleDeadlines(String moduleDeadlines)
	{
		fModuleDeadlines = moduleDeadlines;
	}
}