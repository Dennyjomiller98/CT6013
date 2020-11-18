package beans;

import java.io.Serializable;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import org.bson.Document;

@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class StudentBean implements Serializable
{
    protected String fFirstName;
    protected String fSurname;
    protected String fEmail;
    protected String fDOB;
    protected String fAddress;
    protected boolean fIsEnrolled;
    protected String fPassword;
    protected boolean fIsTeacher;

    public StudentBean()
    {
        //Empty NoArgs constructor
    }
    public StudentBean(Document studentDoc)
    {
        fFirstName = String.valueOf(studentDoc.get("First_Name"));
        fSurname = String.valueOf(studentDoc.get("Surname"));
        fEmail = String.valueOf(studentDoc.get("Email"));
        fDOB =  String.valueOf(studentDoc.get("DOB"));
        fAddress = String.valueOf(studentDoc.get("Address"));
        fPassword = String.valueOf(studentDoc.get("Password"));
        String isEnrolled = String.valueOf(studentDoc.get("Is_Enrolled"));
        fIsEnrolled = isEnrolled.equals("true");
        String isTeacher = String.valueOf(studentDoc.get("Is_Teacher"));
        fIsTeacher = isTeacher.equals("true");
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

    public boolean isEnrolled()
    {
        return fIsEnrolled;
    }
    public void setEnrolled(boolean isStudentEnrolled)
    {
        fIsEnrolled = isStudentEnrolled;
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
}