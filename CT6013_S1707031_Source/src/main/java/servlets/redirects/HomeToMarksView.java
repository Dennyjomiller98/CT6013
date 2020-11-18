package servlets.redirects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mongodb.EnrollmentConnections;
import mongodb.MarkConnections;
import beans.EnrollmentBean;
import beans.MarkBean;
import org.apache.log4j.Logger;

@WebServlet(name = "homeToMarksView")
public class HomeToMarksView extends HttpServlet
{
	static final Logger LOG = Logger.getLogger(HomeToMarksView.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	{
		String studentEmail = request.getParameter("studentEmail");
		if (studentEmail != null)
		{
			//Get details for student enrollment
			if(request.getSession(true).getAttribute("DBSELECTION") != null)
			{
				getStudentEnrollmentAndMarks(request, response, studentEmail);
			}
			else
			{
				//No DB selection
				LOG.error("Unknown database choice, returning to DB select page.");
				redirectToDBSelect(request, response);
			}

			try
			{
				response.sendRedirect(request.getContextPath() + "/jsp/marks/marksview.jsp");
			}
			catch (IOException e)
			{
				LOG.error("Unable to redirect to view Marks page.", e);
			}
		}
	}

	private void getStudentEnrollmentAndMarks(HttpServletRequest request, HttpServletResponse response, String studentEmail)
	{
		String dbSelection = request.getSession(true).getAttribute("DBSELECTION").toString();
		EnrollmentBean enrollmentBean = null;
		if(dbSelection.equalsIgnoreCase("MONGODB"))
		{
			EnrollmentConnections enrollConn = new EnrollmentConnections();
			enrollmentBean = enrollConn.retrieveSingleEnrollment(studentEmail);
		}
		else if (dbSelection.equalsIgnoreCase("ORACLE"))
		{
			oracle.EnrollmentConnections enrollConn = new oracle.EnrollmentConnections();
			enrollmentBean = enrollConn.retrieveSingleEnrollment(studentEmail);
		}
		else
		{
			//No DB selection
			LOG.error("Unknown database choice, returning to DB select page.");
			redirectToDBSelect(request, response);
		}

		if (enrollmentBean != null)
		{
			request.getSession(true).setAttribute("enrollStudent", enrollmentBean.getStudentEmail());
			request.getSession(true).setAttribute("enrollCourse", enrollmentBean.getCourseCode());
			request.getSession(true).setAttribute("enrollModules", enrollmentBean.getModuleSelections());
		}

		//Get Marks
		List<MarkBean> markBeans = new ArrayList<>();
		if(dbSelection.equalsIgnoreCase("MONGODB"))
		{
			MarkConnections markConn = new MarkConnections();
			markBeans = markConn.retrieveAllMarksForStudent(studentEmail);
		}
		else if (dbSelection.equalsIgnoreCase("ORACLE"))
		{
			oracle.MarkConnections markConn = new oracle.MarkConnections();
			markBeans = markConn.retrieveAllMarksForStudent(studentEmail);
		}

		if (markBeans != null)
		{
			LOG.debug("Setting session attributes for all Marks");
			request.getSession(true).setAttribute("allMarks", markBeans);
		}
	}

	private void redirectToDBSelect(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			response.sendRedirect(request.getContextPath() + "/jsp/databaseselection.jsp");
		} catch (IOException e) {
			LOG.error("Failure to redirect after view marks redirect failure", e);
		}
	}
}