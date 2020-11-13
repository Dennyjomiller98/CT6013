package mongodbbeans;

import org.bson.Document;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import java.util.Date;

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
    private String fPostcode;
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
        fAddress = String.valueOf(studentDoc.get("Postcode"));
        fPassword = String.valueOf(studentDoc.get("Password"));
        String isEnrolled = String.valueOf(studentDoc.get("Is_Enrolled"));
        fIsEnrolled = isEnrolled.equals("true");
    }

    public String getStudentID()
    {
        return fStudentID;
    }
    private void setStudentID(String studentID)
    {
        fStudentID = studentID;
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

    public boolean isEnrolled()
    {
        return fIsEnrolled;
    }
    private void setEnrolled(boolean isStudentEnrolled)
    {
        fIsEnrolled = isStudentEnrolled;
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
