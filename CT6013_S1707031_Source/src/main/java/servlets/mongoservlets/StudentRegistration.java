package servlets.mongoservlets;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mongodb.StudentConnections;
import mongodbbeans.StudentBean;
import org.apache.log4j.Logger;
import org.bson.Document;

@WebServlet(name = "mongoStudentRegistration")
public class StudentRegistration extends HttpServlet
{
    static final Logger LOG = Logger.getLogger(StudentRegistration.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        //Retrieve form values for Student
        Document studentToRegister = new Document();
        studentToRegister.append("Student_ID", request.getParameter("studentID"));
        studentToRegister.append("First_Name", request.getParameter("firstname"));
        studentToRegister.append("Surname", request.getParameter("surname"));
        studentToRegister.append("Email", request.getParameter("email"));
        studentToRegister.append("DOB", request.getParameter("dob"));
        String fullAddress = request.getParameter("address1") + "," + request.getParameter("address2") + "," + request.getParameter("city") + "," + request.getParameter("postcode");
        studentToRegister.append("Address", fullAddress);
        studentToRegister.append("Password", request.getParameter("pword"));
        studentToRegister.append("Is_Enrolled", "false");
        studentToRegister.append("Is_Teacher", "false");

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
}
