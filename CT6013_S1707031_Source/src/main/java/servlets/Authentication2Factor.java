package servlets;

import beans.UserBean;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mail.Emailer;
import oracle.AuthenticationConnections;
import org.apache.log4j.Logger;

public class Authentication2Factor extends HttpServlet
{
	static final Logger LOG = Logger.getLogger(Authentication2Factor.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	{
		String user = request.getParameter("user");
		AuthenticationConnections connections = new AuthenticationConnections();
		UserBean userBean = new UserBean();
		userBean.setUsername(user);
		String pin = connections.generatePinForUser(userBean);
		Emailer mailer = new Emailer();
		mailer.emailKeyToUser(userBean, pin);

		try
		{
			response.sendRedirect(request.getContextPath() + "/jsp/2fa.jsp");
		} catch (IOException e) {
			LOG.error("Failure to redirect after 2FA key validation", e);
		}
	}
}

