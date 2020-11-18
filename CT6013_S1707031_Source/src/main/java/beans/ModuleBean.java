package beans;

import java.io.Serializable;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import org.bson.Document;

@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class ModuleBean implements Serializable
{
	protected String fModuleStart;
	protected String fModuleEnd;
	protected String fModuleCode;
	protected String fModuleName;
	protected String fModuleTutor;
	protected String fRelatedCourse;
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
		String semesterString = String.valueOf(moduleDoc.get("Semester"));
		fSemester = Integer.parseInt(semesterString);
		String isCompulsory = String.valueOf(moduleDoc.get("Is_Compulsory"));
		fIsCompulsory = isCompulsory.equals("on");
		fModuleStart = String.valueOf(moduleDoc.get("Module_Start"));
		fModuleEnd = String.valueOf(moduleDoc.get("Module_End"));
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
	public void setModuleTutor(String moduleTutor)
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

	//-1 both semesters, 1 semester 1, 2 semester 2
	//if 1 / 2, Module CATS is 15
	//if -1, CATS is 30, if -2 (placement) cats is 120
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

	public String getModuleStart()
	{
		return fModuleStart;
	}
	public void setModuleStart(String moduleStart)
	{
		fModuleStart = moduleStart;
	}

	public String getModuleEnd()
	{
		return fModuleEnd;
	}
	public void setModuleEnd(String moduleEnd)
	{
		fModuleEnd = moduleEnd;
	}
}