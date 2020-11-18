package beans;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import org.bson.Document;

@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class TeacherBean
{
	protected String fFirstName;
	protected String fSurname;
	protected String fEmail;
	protected String fDOB;
	protected String fAddress;
	protected String fPassword;
	protected boolean fIsTeacher;
	protected boolean fIsEnrolled;

	public TeacherBean() {
		//Empty NoArgs constructor
	}
	public TeacherBean(Document teacherDoc)
	{
		fFirstName = String.valueOf(teacherDoc.get("First_Name"));
		fSurname = String.valueOf(teacherDoc.get("Surname"));
		fEmail = String.valueOf(teacherDoc.get("Email"));
		fDOB = String.valueOf(teacherDoc.get("DOB"));
		fAddress = String.valueOf(teacherDoc.get("Address"));
		fPassword = String.valueOf(teacherDoc.get("Password"));
		String isTeacher = String.valueOf(teacherDoc.get("Is_Teacher"));
		fIsTeacher = isTeacher.equals("true");
		String isEnrolled = String.valueOf(teacherDoc.get("Is_Enrolled"));
		fIsEnrolled = isEnrolled.equals("true");
	}

	public String getFirstName()
	{
		return fFirstName;
	}
	public void setFirstName(String firstName)
	{
		fFirstName = firstName;
	}

	public String getSurname()
	{
		return fSurname;
	}
	public void setSurname(String surname)
	{
		fSurname = surname;
	}

	public String getEmail()
	{
		return fEmail;
	}
	public void setEmail(String email)
	{
		fEmail = email;
	}

	public String getDOB()
	{
		return fDOB;
	}
	public void setDOB(String dateOfBirth)
	{
		fDOB = dateOfBirth;
	}

	public String getAddress()
	{
		return fAddress;
	}
	public void setAddress(String addressCsv)
	{
		fAddress = addressCsv;
	}

	public String getPassword()
	{
		return fPassword;
	}
	public void setPassword(String pword)
	{
		fPassword = pword;
	}

	public boolean isTeacher()
	{
		return fIsTeacher;
	}
	public void setTeacher(boolean isTeacher)
	{
		fIsTeacher = isTeacher;
	}

	public boolean isEnrolled()
	{
		return fIsEnrolled;
	}
	public void setEnrolled(boolean isEnrolled)
	{
		fIsEnrolled = isEnrolled;
	}
}