package servlets;

import mongodb.MongoDBConnections;
import org.apache.log4j.Logger;
import org.bson.Document;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "Registration")
public class Registration extends HttpServlet
{
    static final Logger LOG = Logger.getLogger(Registration.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try{
        PrintWriter pw = response.getWriter();
        pw.print("Testing Servlet Registration Post Method");
        Document customerToRegister = new Document();
        customerToRegister.append("First_Name", request.getParameter("firstname"));
        customerToRegister.append("Surname", request.getParameter("surname"));
        customerToRegister.append("Email", request.getParameter("email"));
        customerToRegister.append("Password", request.getParameter("pword"));

        MongoDBConnections mongoConn = new MongoDBConnections();
        mongoConn.registerCustomerToDB(customerToRegister);
        pw.print("Registration complete");
        LOG.debug("Customer Registration completed successfully");
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
