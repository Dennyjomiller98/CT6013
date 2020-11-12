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
        Document customerToRegister = new Document();
        customerToRegister.append("First_Name", request.getParameter("firstname"));
        customerToRegister.append("Surname", request.getParameter("surname"));
        customerToRegister.append("Email", request.getParameter("email"));
        customerToRegister.append("Password", request.getParameter("pword"));

        StudentConnections studentConn = new StudentConnections();
        studentConn.registerCustomerToDB(customerToRegister);
        LOG.debug("Customer Registration completed successfully");

        //Attempt to retrieve cust data TODO - Dont do this way obvs (TESTING TO SEE BEAN FUNCTIONALITY)
        StudentBean studentBean = studentConn.retrieveSingleStudent(request.getParameter("studentID"));
        LOG.debug("Created studentBean: testing firstname, lastname, studentID, Password, email , etc:" + studentBean );

        redirectMe(request, response);
    }

    private void redirectMe(HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
            response.sendRedirect(request.getContextPath() + "/jsp/students/studentlogin.jsp");
        } catch (IOException e) {
            LOG.error("Failure to select Oracle as preferred database", e);
        }
    }
}
