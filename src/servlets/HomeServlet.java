package servlets;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import material.Subject;
import constants.Keys;
import account.Account;

import com.google.gson.*;

public class HomeServlet extends HttpServlet {

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		// Attempt to retrieve account from session
		Account acc = (Account) request.getSession().getAttribute(Keys.ACCOUNT);
		
		/* 
		 * This is hacky and I hate it but I have yet to come up with a better solution....
		 * You cannot call static class methods through el, so this must always be in the header. 
		 */
		if(request.getSession().getAttribute(Keys.ALL_SUBJECTS_LIST) == null)
			request.getSession().setAttribute(Keys.ALL_SUBJECTS_LIST, Subject.getSubjects());
		
		// If user is logged out, redirect
		if (acc == null)
			getServletContext().getRequestDispatcher("/index.jsp").forward(
					request, response);
		else if (Account.ActorType.USER == acc.getType()
				|| Account.ActorType.TRUSTED_USER == acc.getType())
			getServletContext().getRequestDispatcher("/home.jsp").forward(
					request, response);
		else if (Account.ActorType.ADMIN == acc.getType())
			getServletContext().getRequestDispatcher("/admin-home.jsp")
					.forward(request, response);
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