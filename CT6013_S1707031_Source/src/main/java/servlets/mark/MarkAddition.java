package servlets.mark;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mongodb.MarkConnections;
import org.apache.log4j.Logger;
import org.bson.Document;

@WebServlet(name = "markAddition")
public class MarkAddition extends HttpServlet
{
	static final Logger LOG = Logger.getLogger(MarkAddition.class);

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	{
		//Save Mark data to DB
		LOG.debug("Adding marks data to DB");
		if(request.getSession(true).getAttribute("DBSELECTION") != null)
		{
			String dbSelection = request.getSession(true).getAttribute("DBSELECTION").toString();
			Document markDoc = new Document();
			markDoc.append("Module_Code", request.getParameter("moduleCode"));
			markDoc.append("Student_Email", request.getParameter("studentEmail"));
			markDoc.append("Marker_Email", request.getParameter("courseTutor"));

			String allGrades = request.getParameter("allGrades");
			String singleGrades = request.getParameter("singleGrades");
			if (allGrades != null)
			{
				markDoc.append("Final_Mark", allGrades);
			}
			else if (singleGrades != null)
			{
				markDoc.append("Final_Mark", singleGrades);
			}
			//Check validity
			if (amIPercentage(markDoc.getString("Final_Mark")))
			{
				//Ad to DB and set session values
				LOG.debug("Adding marks to DB");
				if(dbSelection.equalsIgnoreCase("MONGODB"))
				{
					MarkConnections markConn = new MarkConnections();
					markConn.addMarks(markDoc);
				}
				else if (dbSelection.equalsIgnoreCase("ORACLE"))
				{
					oracle.MarkConnections markConn = new oracle.MarkConnections();
					markConn.addMarks(markDoc);
				}
				else
				{
					//No DB selection
					LOG.error("Unknown database choice, returning to DB select page.");
					redirectToDBSelect(request, response);
				}
				request.getSession(true).setAttribute("markSuccess", "Mark submitted successfully");
				request.getSession(true).removeAttribute("markErrors");
			}
			else
			{
				request.getSession(true).setAttribute("markErrors", "Provided Grade was not between 0 and 100.");
				request.getSession(true).removeAttribute("markSuccess");
			}

			//Remove attributes
			request.getSession(true).removeAttribute("allMarkBeans");
			request.getSession(true).removeAttribute("allEnrollmentToReturn");
			request.getSession(true).removeAttribute("singleMarkBean");
			request.getSession(true).removeAttribute("singleEnrollmentToReturn");

			//Return to Add marks page
			try
			{
				response.sendRedirect(request.getContextPath() + "/jsp/marks/marksaddition.jsp");
			}
			catch (IOException e)
			{
				LOG.error("Unable to redirect back to marks addition page after mark save", e);
			}
		}
		else
		{
			//No DB selection
			LOG.error("Unknown database choice, returning to DB select page.");
			redirectToDBSelect(request, response);
		}
	}

	private boolean amIPercentage(String markValue)
	{
		//If value isn't between 0 and 100, is not valid marks, so we fail addition to DB
		try
		{
			int value = Integer.parseInt(markValue);
			return value >= 0 && value <= 100;
		}
		catch(Exception e)
		{
			LOG.error("Value provided was NOT a number", e);
			return false;
		}
	}

	private void redirectToDBSelect(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			response.sendRedirect(request.getContextPath() + "/jsp/databaseselection.jsp");
		} catch (IOException e) {
			LOG.error("Failure to redirect after Teacher Login failure", e);
		}
	}
}