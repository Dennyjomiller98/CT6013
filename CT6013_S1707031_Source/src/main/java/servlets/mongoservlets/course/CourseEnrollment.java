package servlets.mongoservlets.course;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

@WebServlet(name = "mongoCourseEnrollment")
public class CourseEnrollment extends HttpServlet
{
	static final Logger LOG = Logger.getLogger(CourseEnrollment.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	{
		//Empty Constructor
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	{
		//Save course enrollment
	}
}

