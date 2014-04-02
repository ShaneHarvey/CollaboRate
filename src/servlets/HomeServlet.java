package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import constants.Keys;
import account.Account;;

public class HomeServlet extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
		// Attempt to retrieve account from session
		Account acc = (Account)request.getSession().getAttribute(Keys.ACCOUNT);
		// Redirect to correct page based on if user is logged in or not.
		if(acc == null)
			getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
		else if(Account.ActorType.USER == acc.getType() || Account.ActorType.TRUSTED_USER == acc.getType())
			getServletContext().getRequestDispatcher("/home.jsp").forward(request, response);
		else if(Account.ActorType.ADMIN == acc.getType())
			getServletContext().getRequestDispatcher("/admin-home.jsp").forward(request, response);
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
		processRequest(request, response);
    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
		processRequest(request, response);
    }
}