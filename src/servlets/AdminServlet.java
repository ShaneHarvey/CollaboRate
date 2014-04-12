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

public class AdminServlet extends HttpServlet {

	private static final long serialVersionUID = 1019307870758244635L;

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		// See if this is a page request or ajax call
		String action = (String) request.getParameter("action");
		// Attempt to retrieve account from session
		Account acc = (Account) request.getSession().getAttribute(Keys.ACCOUNT);

		// Make sure there is an admin account account
		if (acc == null || acc.getType() != Account.ActorType.ADMIN)
			response.getWriter().print("");
		else {
			if ("createsubject".equals(action)) {
				String subject = request.getParameter("subjectName");
				// This is a json array of strings
				String subTopicList = request.getParameter("subTopicList");
				String[] subtopics = new Gson().fromJson(subTopicList,
						String[].class);

				// Try to create the subject and subtopics
				try {
					Subject.createSubject(subject, subject, subtopics);
					response.getWriter().print("success");
				} catch (Exception e) {
					response.getWriter().print("");
				}
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