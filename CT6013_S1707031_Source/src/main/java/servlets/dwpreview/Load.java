package servlets.dwpreview;

import beans.dw.DWResultsBean;
import java.io.IOException;
import java.util.Map;
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
		request.getSession(true).removeAttribute("success");
		request.getSession(true).removeAttribute("results");
		request.getSession(true).removeAttribute("doQuery");
		request.getSession(true).removeAttribute("errors");

		String userEmail = request.getParameter("email");
		String querySelected = request.getParameter("select");
		String yearSelect = request.getParameter("yearSelect");
		String tutorSelect = request.getParameter("tutorSelect");
		String courseSelect = request.getParameter("courseSelect");


		if(userEmail != null && (querySelected != null && !querySelected.equals("none")))
		{
			attemptDataRetrieval(request, userEmail, querySelected, yearSelect, tutorSelect, courseSelect);
		}
		else
		{
			if (userEmail == null)
			{
				request.getSession(true).setAttribute("errors", "No logged in User found. Could not retrieve data.");
			}
			else if(querySelected == null)
			{
				request.getSession(true).setAttribute("errors", "Please Select an Option from the Dropdown");
			}
		}
		attemptRedirect(request, response);
	}

	private void attemptDataRetrieval(HttpServletRequest request, String userEmail, String querySelected, String yearSelect, String tutorSelect, String courseSelect)
	{
		DecisionMakerDataManager dataManager = new DecisionMakerDataManager();
		boolean hasAuthorisedRole = dataManager.validateRole(userEmail, querySelected);
		if(hasAuthorisedRole)
		{
			DWResultsBean resultsBean = dataManager.attemptDataRetrieval(querySelected, yearSelect, courseSelect, tutorSelect);
			if(resultsBean != null)
			{
				//Check for errors
				Map<String, String> errors = resultsBean.getErrors();
				if(errors != null && !errors.isEmpty())
				{
					StringBuilder errOutput = new StringBuilder("Error with: ");
					//Error out if any errors occurred
					for (Map.Entry<String, String> error : errors.entrySet())
					{
						errOutput.append(error.getKey()).append(" : ").append(error.getValue());
					}
					request.getSession(true).setAttribute("errors", errOutput.toString());
				}
				else
				{
					//Continue setting Session Attributes
					setSessionDataForQuery(resultsBean, querySelected, request);
				}
			}
			else
			{
				request.getSession(true).setAttribute("errors", "An Error has occurred Querying the Database Warehouse. Could not retrieve data.");
			}
		}
		else
		{
			request.getSession(true).setAttribute("errors", "You are not authorized to view this data.");
		}
	}

	private void attemptRedirect(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			response.sendRedirect(request.getContextPath() + "/jsp/view.jsp");
		}
		catch (IOException e)
		{
			LOG.error("Unable to redirect.",e);
		}
	}

	private void setSessionDataForQuery(DWResultsBean resultsBean, String querySelected, HttpServletRequest request)
	{
		request.getSession(true).setAttribute("doQuery", "true");
		request.getSession(true).setAttribute("Success", "Information Successfully Retrieved");

		//TODO - set session attributes from DWResultsBean
	}
}