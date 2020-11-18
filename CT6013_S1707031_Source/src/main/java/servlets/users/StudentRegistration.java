package servlets.users;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mongodb.StudentConnections;
import beans.StudentBean;
import org.apache.log4j.Logger;
import org.bson.Document;

@WebServlet(name = "studentRegistration")
public class StudentRegistration extends HttpServlet
{
    static final Logger LOG = Logger.getLogger(StudentRegistration.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        //Retrieve form values for Student
        if(request.getSession(true).getAttribute("DBSELECTION") != null)
        {
            String dbSelection = request.getSession(true).getAttribute("DBSELECTION").toString();
            Document studentToRegister = new Document();
            studentToRegister.append("First_Name", request.getParameter("firstname"));
            studentToRegister.append("Surname", request.getParameter("surname"));
            studentToRegister.append("Email", request.getParameter("email"));
            studentToRegister.append("DOB", request.getParameter("dob"));
            String fullAddress = request.getParameter("address1") + "," + request.getParameter("address2") + "," + request.getParameter("city") + "," + request.getParameter("postcode");
            studentToRegister.append("Address", fullAddress);
            studentToRegister.append("Is_Enrolled", "false");
            studentToRegister.append("Is_Teacher", "false");
            validatePassword(request, response, studentToRegister);

            if(dbSelection.equalsIgnoreCase("MONGODB"))
            {
                //MongoDB Selection
                mongoRegisterStudent(request, response, studentToRegister);
            }
            else if (dbSelection.equalsIgnoreCase("ORACLE"))
            {
                //Oracle DB
                oracleRegisterStudent(request, response, studentToRegister);
            }
            else
            {
                //No DB selection
                LOG.error("Unknown database choice, returning to DB select page.");
                redirectToDBSelect(request, response);
            }
        }
        else
        {
            //No attribute found, go back to DB Select page
            LOG.error("Unknown database choice, returning to DB select page.");
            redirectToDBSelect(request, response);
        }
    }

    private void validatePassword(HttpServletRequest request, HttpServletResponse response, Document studentToRegister)
    {
        LOG.debug("Validating Passwords");
        String pword = request.getParameter("pword");
        String pwordConfirm = request.getParameter("pwordConfirm");
        if(pword.equals(pwordConfirm))
        {
            String hashedPassword = PasswordEncryptDecrypt.encryptPasswordToStore(pword);
            studentToRegister.append("Password", hashedPassword);
        }
        else
        {
            //Doesn't match, error student out.
            request.getSession(true).removeAttribute("registrationSuccess");
            request.getSession(true).setAttribute("registrationErrors", "An Error has occurred, the passwords do not match.");
            try
            {
                response.sendRedirect(request.getContextPath() + "/jsp/teachers/teacherprofile.jsp");
            }
            catch (IOException e)
            {
                LOG.error("Unable to redirect back to Profile page after teacher update failure.",e);
            }
        }
    }

    private void oracleRegisterStudent(HttpServletRequest request, HttpServletResponse response, Document studentToRegister)
    {
        //Access Oracle Connection and add student to DB
        LOG.debug("Oracle connection for student creation");
        oracle.StudentConnections conn = new oracle.StudentConnections();
        StudentBean email = conn.retrieveSingleStudent(studentToRegister.getString("Email"));
        if (email == null)
        {
            //Add Student
            conn.registerStudentToDB(studentToRegister);
            request.getSession(true).removeAttribute("registrationErrors");
            redirectMe(request, response);
        }
        else
        {
            //Reg failure, student exists
            LOG.debug("Student already exists: " + studentToRegister.getString("Email"));
            request.getSession(true).setAttribute("registrationErrors", "Unable to register student, the email already exists.");
            try
            {
                response.sendRedirect(request.getContextPath() + "/jsp/students/studentregistration.jsp");
            }
            catch (IOException e)
            {
                LOG.error("Unable to redirect after Student Registration Failure", e);
            }
        }
    }

    private void mongoRegisterStudent(HttpServletRequest request, HttpServletResponse response, Document studentToRegister)
    {
        StudentConnections studentConn = new StudentConnections();

        //Check student is not already registered.
        StudentBean studentEmail = studentConn.retrieveSingleStudent(studentToRegister.getString("Email"));
        if(studentEmail == null)
        {
            //Register Student
            studentConn.registerStudentToDB(studentToRegister);
            LOG.debug("Student Registration completed successfully");
            request.getSession(true).removeAttribute("registrationErrors");
            redirectMe(request, response);
        }
        else
        {
            //Register failure, email exists, alert user.
            request.getSession(true).setAttribute("registrationErrors", "Unable to register student, the email already exists.");
            try
            {
                response.sendRedirect(request.getContextPath() + "/jsp/students/studentregistration.jsp");
            }
            catch (IOException e)
            {
                LOG.error("Unable to redirect after Student Registration Failure", e);
            }
        }
    }

    private void redirectMe(HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
            response.sendRedirect(request.getContextPath() + "/jsp/students/studentlogin.jsp");
        } catch (IOException e) {
            LOG.error("Failure to redirect after student successfully registered", e);
        }
    }

    private void redirectToDBSelect(HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
            response.sendRedirect(request.getContextPath() + "/jsp/databaseselection.jsp");
        } catch (IOException e) {
            LOG.error("Failure to redirect after student register failure", e);
        }
    }
}