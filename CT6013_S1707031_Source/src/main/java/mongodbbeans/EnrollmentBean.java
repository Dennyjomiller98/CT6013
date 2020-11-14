package mongodbbeans;

import java.io.Serializable;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import org.bson.Document;

@Singleton(name = "MongoClientProviderEJB")
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class EnrollmentBean implements Serializable
{
	protected String fStudentEmail;
	protected String fCourseCode;
	protected String fModuleSelections;

	public EnrollmentBean()
	{
		//Empty Constructor
	}

	public EnrollmentBean(Document enrollmentDoc)
	{
		fStudentEmail = String.valueOf(enrollmentDoc.get("Student_Email"));
		fCourseCode = String.valueOf(enrollmentDoc.get("Course_Code"));
		fModuleSelections = String.valueOf(enrollmentDoc.get("Module_Selections"));
	}

	public String getStudentEmail()
	{
		return fStudentEmail;
	}
	public void setStudentEmail(String studentEmail)
	{
		fStudentEmail = studentEmail;
	}

	public String getCourseCode()
	{
		return fCourseCode;
	}
	public void setCourseCode(String courseCode)
	{
		fCourseCode = courseCode;
	}

	//ModuleSelections is a CSV String of all selected ModuleCodes (Can be used to find data on each module)
	public String getModuleSelections()
	{
		return fModuleSelections;
	}
	public void setModuleSelections(String moduleSelections)
	{
		fModuleSelections = moduleSelections;
	}
}
