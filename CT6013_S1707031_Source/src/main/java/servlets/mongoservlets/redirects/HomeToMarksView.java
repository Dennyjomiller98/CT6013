package servlets.mongoservlets.redirects;

import java.io.IOException;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mongodb.EnrollmentConnections;
import mongodb.MarkConnections;
import mongodbbeans.EnrollmentBean;
import mongodbbeans.MarkBean;
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
			EnrollmentConnections enrollConn = new EnrollmentConnections();
			EnrollmentBean enrollmentBean = enrollConn.retrieveSingleEnrollment(studentEmail);
			if (enrollmentBean != null)
			{
				request.getSession(true).setAttribute("enrollStudent", enrollmentBean.getStudentEmail());
				request.getSession(true).setAttribute("enrollCourse", enrollmentBean.getCourseCode());
				request.getSession(true).setAttribute("enrollModules", enrollmentBean.getModuleSelections());
			}
			MarkConnections markConn = new MarkConnections();
			List<MarkBean> markBeans = markConn.retrieveAllMarksForStudent(studentEmail);
			if (markBeans != null)
			{
				LOG.debug("Setting session attributes for all Marks");
				request.getSession(true).setAttribute("allMarks", markBeans);
			}
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