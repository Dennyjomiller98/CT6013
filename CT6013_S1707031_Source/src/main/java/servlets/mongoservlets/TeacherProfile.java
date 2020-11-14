package servlets.mongoservlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

@WebServlet(name = "mongoTeacherProfile")
public class TeacherProfile extends HttpServlet
{
	static final Logger LOG = Logger.getLogger(TeacherProfile.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	{
		//Retrieve Teacher details
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	{
		//Save Teacher details
	}
}