package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import constants.Keys;
import account.Account;

public class HomeServlet extends HttpServlet {

	private static final long serialVersionUID = -2732756669335936329L;

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		// Attempt to retrieve account from session
		Account acc = (Account) request.getSession().getAttribute(Keys.ACCOUNT);

		// If user is logged out, redirect
		if (acc == null)
			getServletContext().getRequestDispatcher("/index.jsp").forward(
					request, response);
		else if (Account.ActorType.ADMIN == acc.getType())
			response.sendRedirect("/admin");
		else
			getServletContext().getRequestDispatcher("/home.jsp").forward(
					request, response);

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		processRequest(request, response);
	}
}