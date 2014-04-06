package servlets;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import constants.Keys;
import account.Account;

import com.google.gson.*;

public class HomeServlet extends HttpServlet {

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		
		// See if this is a page request or ajax call
		String action = (String) request.getParameter("action");
		// Attempt to retrieve account from session
		Account acc = (Account) request.getSession().getAttribute(
				Keys.ACCOUNT);
		
		if (action == null) {
			// Redirect to correct page based on if user is logged in or not.
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
		else if("createSubject".equals(action)) {
			// Make sure this person is an admin
			if(acc.getType() != Account.ActorType.ADMIN) 
				response.getWriter().print("");
			else {
				String subject = URLDecoder.decode(request.getParameter("subjectName"), "UTF-8");
				// This is a json array of strings
				String subTopicList = URLDecoder.decode(request.getParameter("subTopicList"), "UTF-8");
				String[] subTopics = new Gson().fromJson(subTopicList, String[].class);
				// Create the subject and add the subtopics to it
				response.getWriter().print("success");
			}
		}
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