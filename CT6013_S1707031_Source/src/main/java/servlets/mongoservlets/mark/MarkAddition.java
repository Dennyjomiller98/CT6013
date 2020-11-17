package servlets.mongoservlets.mark;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mongodb.MarkConnections;
import org.apache.log4j.Logger;
import org.bson.Document;

@WebServlet(name = "mongoMarkAddition")
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
		markDoc.append("Final_Mark", request.getParameter("allGrades"));
		markConn.addMarks(markDoc);

		//Set attributes again
		//TODO - Set session attributes once added


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
}

