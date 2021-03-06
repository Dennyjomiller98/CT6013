package servlets;

import beans.UserBean;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mail.Emailer;
import oracle.AuthenticationConnections;
import oracle.UserConnections;
import org.apache.log4j.Logger;

public class Login extends HttpServlet
{
	static final Logger LOG = Logger.getLogger(Login.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	{
		request.getSession(true).removeAttribute("success");
		request.getSession(true).removeAttribute("errors");

		UserBean userBean = new UserBean();
		userBean.setUsername(request.getParameter("email").toLowerCase());
		userBean.setPword(request.getParameter("pword"));

		//User login
		boolean userFound = oracleLogin(userBean);
		if(userFound)
		{
			String pin = generateAuthentication2FA(userBean);
			Emailer mailer = new Emailer();
			mailer.emailKeyToUser(userBean, pin);

			//Set email for user
			request.getSession(true).setAttribute("email", userBean.getUsername());
			try
			{
				response.sendRedirect(request.getContextPath() + "/jsp/2fa.jsp");
			} catch (IOException e) {
				LOG.error("Failure to redirect after 2FA key sent", e);
			}
		}
		else
		{
			try
			{
				response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
			} catch (IOException e) {
				LOG.error("Failure to login", e);
			}
		}
	}

	private String generateAuthentication2FA(UserBean userBean)
	{
		String pin;
		AuthenticationConnections conn = new AuthenticationConnections();
		pin = conn.generatePinForUser(userBean);
		return pin;
	}

	private boolean oracleLogin(UserBean userBean)
	{
		boolean ret;
		UserConnections conn = new UserConnections();
		ret = conn.attemptLogin(userBean);
		return ret;
	}
}
