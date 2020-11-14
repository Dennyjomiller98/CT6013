package oraclebeans;

import java.util.Date;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import org.bson.Document;

@Singleton(name = "MongoClientProviderEJB")
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class StudentBean
{
    private String fStudentID;
    private String fFirstName;
    private String fSurname;
    private String fEmail;
    private Date fDOB;
    private String fAddress;
    private boolean fIsEnrolled;
    private String fPassword;

    public StudentBean() {
        //Empty NoArgs constructor
    }
    public StudentBean(Document studentDoc)
    {
        fStudentID = String.valueOf(studentDoc.get("Student_ID"));
        fFirstName = String.valueOf(studentDoc.get("First_Name"));
        fSurname = String.valueOf(studentDoc.get("Surname"));
        fEmail = String.valueOf(studentDoc.get("Email"));
        fDOB = (Date) studentDoc.get("DOB");
        fAddress = String.valueOf(studentDoc.get("Address"));
        fPassword = String.valueOf(studentDoc.get("pword"));
        String isEnrolled = String.valueOf(studentDoc.get("isEnrolled"));
        fIsEnrolled = isEnrolled.equals("true");
    }

    public String getStudentID()
    {
        return fStudentID;
    }
    public void setStudentID(String studentID)
    {
        fStudentID = studentID;
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

    public Date getDOB()
    {
        return fDOB;
    }
    public void setDOB(Date dateOfBirth)
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
}
