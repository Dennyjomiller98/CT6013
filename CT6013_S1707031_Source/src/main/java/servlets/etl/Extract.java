package servlets.etl;

import beans.operational.AssignmentsBean;
import etl.ExtractHelper;
import etl.LoadHelper;
import etl.TransformHelper;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class Extract extends HttpServlet
{
	static final Logger LOG = Logger.getLogger(Extract.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	{
		//Retrieve ALL Operational Database Data, and store in Beans ready for Transform Process
		try
		{
			//TODO - complete this for all beans/tables, AssignmentsBean has been used as proof of concept
			ExtractHelper etlExtractHelper = new ExtractHelper();
			List<AssignmentsBean> assignmentsBeans = etlExtractHelper.retrieveAssignmentsTable();

			//Now we need to Transform the data, sanitize information for each bit of data before placing in the DW
			TransformHelper etlTransformHelper = new TransformHelper();
			assignmentsBeans = etlTransformHelper.transformAssignmentsData(assignmentsBeans);

			//Data has been Transformed, so now we can Load
			LoadHelper etlLoadHelper = new LoadHelper();
			boolean loadSuccess = etlLoadHelper.loadData(assignmentsBeans);
			if(loadSuccess)
			{
				request.getSession(true).setAttribute("success", "Database Warehouse ETL Process Complete" );
			}
			else
			{
				request.getSession(true).setAttribute("errors", "An error has occurred whilst Loading Data for the Database Warehouse" );
			}
			attemptRedirect(request, response);
		}
		catch(Exception e)
		{
			request.getSession(true).setAttribute("errors", "An error has occurred whilst Extracting Data for the Database Warehouse" );
			LOG.error("An error has occurred whilst Extracting Data for the Database Warehouse", e);
			attemptRedirect(request, response);
		}
	}

	private void attemptRedirect(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
		}
		catch (IOException e)
		{
			LOG.error("Unable to redirect.",e);
		}
	}
}
