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
		MarkConnections markConn = new MarkConnections();
		Document markDoc = new Document();
		markDoc.append("Module_Code", request.getParameter("moduleCode"));
		markDoc.append("Student_Email", request.getParameter("studentEmail"));
		markDoc.append("Marker_Email", request.getParameter("courseTutor"));

		String allGrades = request.getParameter("allGrades");
		String singleGrades = request.getParameter("singleGrades");
		if(allGrades != null)
		{
			markDoc.append("Final_Mark", allGrades);
		}
		else if (singleGrades != null)
		{
			markDoc.append("Final_Mark", singleGrades);
		}
		//Check validity
		if(amIPercentage(markDoc.getString("Final_Mark")))
		{
			//Ad to DB and set session values
			LOG.debug("Adding marks to DB");
			markConn.addMarks(markDoc);
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
			LOG.error("Unable to redirect back to marks addition page after mark save",e);
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
}