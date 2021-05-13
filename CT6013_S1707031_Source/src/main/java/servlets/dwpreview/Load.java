package servlets.dwpreview;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import managers.dataview.DecisionMakerDataManager;
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
		//TODO - retrieve form data (Question asked, course/tutor/year/etc)

		//TODO - View DW data as a decision maker (Manager MUST authorize/check privileges)
		DecisionMakerDataManager dataManager = new DecisionMakerDataManager();
		dataManager.attemptDataRetrieval(/*Form data, course/tutor/year select etc*/);

		//TODO - redirect back to view, set attributes for data we want, and then it will load into table
	}
}
