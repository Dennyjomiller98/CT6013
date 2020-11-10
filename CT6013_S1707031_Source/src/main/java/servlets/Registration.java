package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import org.apache.log4j.Logger;
@WebServlet(name = "Registration")
public class Registration extends HttpServlet {
    static Logger LOG = Logger.getLogger(Registration.class);
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        pw.print("Testing Servlet Registration Post Method");
        registerCustomer();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    private void registerCustomer() {
        LOG.debug("Beginning Customer Registration");
    }


}
