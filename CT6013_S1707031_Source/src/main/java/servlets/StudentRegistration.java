package servlets;

import beans.StudentBean;
import mongodb.StudentConnections;
import org.apache.log4j.Logger;
import org.bson.Document;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "StudentRegistration")
public class StudentRegistration extends HttpServlet
{
    static final Logger LOG = Logger.getLogger(StudentRegistration.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try
        {
            PrintWriter pw = response.getWriter();
            pw.print("Testing Servlet Registration Post Method");
            Document customerToRegister = new Document();
            customerToRegister.append("First_Name", request.getParameter("firstname"));
            customerToRegister.append("Surname", request.getParameter("surname"));
            customerToRegister.append("Email", request.getParameter("email"));
            customerToRegister.append("Password", request.getParameter("pword"));

            StudentConnections studentConn = new StudentConnections();
            studentConn.registerCustomerToDB(customerToRegister);
            pw.print("Registration complete");
            LOG.debug("Customer Registration completed successfully");

            //Attempt to retrieve cust data TODO - Dont do this way obvs (TESTING TO SEE BEAN FUNCTIONALITY)
            StudentBean studentBean = studentConn.retrieveSingleStudent(request.getParameter("studentID"));
            pw.print("testing firstname, lastname, studentID, Password, email , etc:" + studentBean );
        }
            catch(IOException ioe)
        {
            LOG.error("Unable to obtain PrintWriter", ioe);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        // Empty, should not use GET.
        // Reason:Form submission in registration: method="POST"
    }

}
