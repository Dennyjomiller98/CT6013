package servlets;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

@WebServlet(name = "DatabaseSelection")
public class DatabaseSelection extends HttpServlet
{
    static final Logger LOG = Logger.getLogger(DatabaseSelection.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    {
        handleDatabaseSelectRequest(request, response);
    }

    private void handleDatabaseSelectRequest(HttpServletRequest request, HttpServletResponse response)
    {
        //Assert Oracle selection
        boolean haveSelected=false;
        String choice = request.getParameter("oracle");
        if (choice != null && !choice.equals("") && choice.equals("oracle")) {
            try {
                response.sendRedirect(request.getContextPath() + "/jsp/oraclehomepage.jsp");
                haveSelected=true;
            } catch (IOException e) {
                LOG.error("Failure to select Oracle as preferred database", e);
            }
        }
        //Assert MongoDB selection
        String mongodb = request.getParameter("mongodb");
        if (mongodb != null && !mongodb.equals("") && mongodb.equals("mongodb"))
        {
            try {
                response.sendRedirect(request.getContextPath() + "/jsp/mongohomepage.jsp");
                haveSelected=true;
            } catch (IOException e) {
                LOG.error("Failure to select MongoDB as preferred database", e);
            }
        }

        //Default Fallback - go back to DB select page
        if(!haveSelected)
        {
            try
            {
                response.sendRedirect(request.getContextPath() + "databaseselection.jsp");
            }
            catch (IOException e)
            {
                LOG.error("No Database selected, and failure in returning to Main DBSelection page", e);
            }
        }
    }
}