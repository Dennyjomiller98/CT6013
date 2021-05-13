package servlets.dwpreview;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 * @author Denny-Jo
 * Load - Servlet for Retrieving the Loaded ETL DW information for Stakeholders to view
 * */
public class Load extends HttpServlet
{
	static final Logger LOG = Logger.getLogger(Load.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	{

	}
}
