package mongodbbeans;

import org.bson.Document;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import java.util.Date;

@Singleton(name = "MongoClientProviderEJB")
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class TeacherBean
{
	private String fTeacherID;
	private String fFirstName;
	private String fSurname;
	private String fEmail;
	private Date fDOB;
	private String fAddress;
	private String fPostcode;
	private String fPassword;

	public TeacherBean() {
		//Empty NoArgs constructor
	}
	public TeacherBean(Document teacherDoc)
	{
		fTeacherID = String.valueOf(teacherDoc.get("Teacher_ID"));
		fFirstName = String.valueOf(teacherDoc.get("First_Name"));
		fSurname = String.valueOf(teacherDoc.get("Surname"));
		fEmail = String.valueOf(teacherDoc.get("Email"));
		fDOB = (Date) teacherDoc.get("DOB");
		fAddress = String.valueOf(teacherDoc.get("Address"));
		fAddress = String.valueOf(teacherDoc.get("Postcode"));
		fPassword = String.valueOf(teacherDoc.get("Password"));
	}

	public String getTeacherID()
	{
		return fTeacherID;
	}
	private void setTeacherID(String teacherID)
	{
		fTeacherID = teacherID;
	}

	public String getFirstName()
	{
		return fFirstName;
	}
	private void setFirstName(String firstName)
	{
		fFirstName = firstName;
	}

	public String getSurname()
	{
		return fSurname;
	}
	private void setSurname(String surname)
	{
		fSurname = surname;
	}

	public String getEmail()
	{
		return fEmail;
	}
	private void setEmail(String email)
	{
		fEmail = email;
	}

	public Date getDOB()
	{
		return fDOB;
	}
	private void setDOB(Date dateOfBirth)
	{
		fDOB = dateOfBirth;
	}

	public String getAddress()
	{
		return fAddress;
	}
	private void setAddress(String addressCsv)
	{
		fAddress = addressCsv;
	}

	public String getPostcode()
	{
		return fPostcode;
	}
	private void setPostcode(String postcode)
	{
		fPostcode = postcode;
	}

	public String getPassword()
	{
		return fPassword;
	}
	private void setPassword(String pword)
	{
		fPassword = pword;
	}
}
