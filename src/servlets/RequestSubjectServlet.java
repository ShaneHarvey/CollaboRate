package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import material.RequestSubject;
import constants.Keys;
import account.Account;

import com.google.gson.*;

public class RequestSubjectServlet extends HttpServlet {

	private static final long serialVersionUID = 1019307870758244635L;

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		// See if this is a page request or ajax call
		String action = (String) request.getParameter("action");
		// Attempt to retrieve account from session
		Account user = (Account) request.getSession().getAttribute(Keys.ACCOUNT);
		if (user == null) {
			// If no user, redirect to home
			response.sendRedirect("/home");
			return;
		}
		if (action == null) {
			response.sendRedirect("/RequestSubject.jsp");
		}else {
			if ("requestsubject".equals(action)) {
				String subject = request.getParameter("subjectName");
				// This is a json array of strings
				String subTopicList = request.getParameter("subTopicList");
				String[] subtopics = new Gson().fromJson(subTopicList,
						String[].class);
				// Try to create the subject and subtopics
				try {
					RequestSubject.createSubjectRequest(subject, subject, subtopics);
					response.getWriter().print("success");
				} catch (Exception e) {
					e.printStackTrace();
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