package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import org.apache.logging.log4j.*;
@WebServlet(name = "servlets.Registration")
public class Registration extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(Registration.class);
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        pw.print("Testing Servlet servlets.Registration Post Method");
        registerCustomer();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    private void registerCustomer() {
        LOG.debug("Beginning Customer servlets.Registration");
    }


}
