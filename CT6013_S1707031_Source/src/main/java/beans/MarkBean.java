package beans;

import java.io.Serializable;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import org.bson.Document;

@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class MarkBean implements Serializable
{
	protected String fModuleCode;
	protected int fFinalMark;
	protected String fStudentEmail;
	protected String fTeacherMarkingEmail;

	public MarkBean()
	{
		//Empty Constructor
	}

	public MarkBean(Document markDoc)
	{
		fModuleCode = String.valueOf(markDoc.get("Module_Code"));
		fStudentEmail = String.valueOf(markDoc.get("Student_Email"));
		fTeacherMarkingEmail = String.valueOf(markDoc.get("Marker_Email"));
		String finalMark = String.valueOf(markDoc.get("Final_Mark"));
		fFinalMark = Integer.parseInt(finalMark);
	}

	public String getModuleCode()
	{
		return fModuleCode;
	}
	public void setModuleCode(String moduleCode)
	{
		fModuleCode = moduleCode;
	}

	public String getMarkerEmail()
	{
		return fStudentEmail;
	}
	public void setMarkerEmail(String markerEmail)
	{
		fTeacherMarkingEmail = markerEmail;
	}

	public String getStudentEmail()
	{
		return fStudentEmail;
	}
	public void setStudentEmail(String studentEmail)
	{
		fStudentEmail = studentEmail;
	}

	public int getFinalMark()
	{
		return fFinalMark;
	}
	public void setFinalMark(int finalMark)
	{
		fFinalMark = finalMark;
	}
}
