package servlets;

import beans.UserBean;
import beans.operational.dimensions.DimCoursesBean;
import beans.operational.dimensions.DimTutorsBean;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import oracle.AuthenticationConnections;
import oracle.DataViewConnections;
import oracle.UserConnections;
import org.apache.log4j.Logger;

public class AuthenticateLogin extends HttpServlet
{
	static final Logger LOG = Logger.getLogger(AuthenticateLogin.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	{
		request.getSession(true).removeAttribute("success");
		request.getSession(true).removeAttribute("errors");

		UserBean userBean = new UserBean();
		userBean.setUsername(request.getParameter("email"));
		userBean.setPin(request.getParameter("pin"));

		AuthenticationConnections authenticationConnections = new AuthenticationConnections();
		boolean isCorrectPin = authenticationConnections.validatePinForUser(userBean);
		if(isCorrectPin)
		{
			//Correct, log user in
			UserConnections userConnections = new UserConnections();
			UserBean loggedInBean = userConnections.retrieveSingleUser(userBean.getUsername());
			request.getSession(true).removeAttribute("errors");
			request.getSession(true).setAttribute("email", loggedInBean.getUsername());
			request.getSession(true).setAttribute("pword", loggedInBean.getPword());

			//Get View Page Information
			DataViewConnections connections = new DataViewConnections();
			List<DimCoursesBean> dwCourses = connections.getDWCourses();
			if(dwCourses != null && !dwCourses.isEmpty())
			{
				request.getSession(true).setAttribute("allCourses", dwCourses);
			}
			List<DimTutorsBean> dwTutors = connections.getDWTutors();
			if(dwTutors != null && !dwTutors.isEmpty())
			{
				request.getSession(true).setAttribute("allTutors", dwTutors);
			}

			try
			{
				response.sendRedirect(request.getContextPath() + "/jsp/view.jsp");
			} catch (IOException e) {
				LOG.error("login success", e);
			}
		}
		else
		{
			//Redirect, wrong pin
			LOG.error("Incorrect auth pin");
			request.getSession(true).removeAttribute("pword");
			request.getSession(true).setAttribute("errors", "Incorrect Pin");

			try
			{
				response.sendRedirect(request.getContextPath() + "/jsp/2fa.jsp");
			} catch (IOException e) {
				LOG.error("Failure to redirect after 2FA key validation", e);
			}
		}
	}
}
