package servlets.mongoservlets.redirects;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mongodb.EnrollmentConnections;
import mongodbbeans.EnrollmentBean;
import org.apache.log4j.Logger;

@WebServlet(name = "homeToCourseEnrollment")
public class HomeToCourseEnrollment extends HttpServlet
{
	static final Logger LOG = Logger.getLogger(HomeToCourseEnrollment.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	{
		//Just A Redirect
		try
		{
			LOG.debug("Attempting Enrollment page redirect");
			String studentEmail = request.getParameter("studentEmail");
			if(studentEmail != null)
			{
				LOG.debug("student email passed through, establishing EnrollmentConnection");
				//Logged in, enrolled. Retrieve enrollment details.
				EnrollmentConnections enrollConn = new EnrollmentConnections();
				EnrollmentBean enrollmentBean = enrollConn.retrieveSingleEnrollment(studentEmail);
				if (enrollmentBean != null)
				{
					request.getSession(true).setAttribute("enrollStudent", enrollmentBean.getStudentEmail() );
					request.getSession(true).setAttribute("enrollCourse", enrollmentBean.getCourseCode() );
					request.getSession(true).setAttribute("enrollModules", enrollmentBean.getModuleSelections() );
					request.getSession(true).removeAttribute("enrollErrors");
					request.getSession(true).setAttribute("enrollSuccess", "Enrollment details retrieved successfully" );
				}
				else
				{
					LOG.error("No enrollment data found in DB for student email: " + studentEmail);
					//Remove session attributes
					request.getSession(true).removeAttribute("enrollStudent");
					request.getSession(true).removeAttribute("enrollCourse");
					request.getSession(true).removeAttribute("enrollModules");
					request.getSession(true).setAttribute("enrollErrors", "Please try selecting a course.");
					request.getSession(true).removeAttribute("enrollSuccess");
				}
			}
			else
			{
				//Clear enrollment details as not enrolled/not logged in/teacher.
				LOG.debug("not logged in, teacher, or not enrolled. removing session attributes for enrollment");
				request.getSession(true).removeAttribute("enrollStudent");
				request.getSession(true).removeAttribute("enrollCourse");
				request.getSession(true).removeAttribute("enrollModules");
				request.getSession(true).removeAttribute("enrollErrors");
				request.getSession(true).removeAttribute("enrollSuccess");
			}

			//redirect to enrollment page regardless of outcome.
			response.sendRedirect(request.getContextPath() + "/jsp/courses/courseenrollment.jsp");
		}
		catch (IOException e)
		{
			LOG.error("Unable to redirect to enrollment page.",e);
		}
	}
}