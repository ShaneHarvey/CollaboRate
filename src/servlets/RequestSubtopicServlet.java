package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import material.RequestSubtopic;
import constants.Keys;
import account.Account;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.gson.*;

public class RequestSubtopicServlet extends HttpServlet {

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
			request.setAttribute("sid", (String) request.getParameter("sid"));
			request.setAttribute("subjectname", (String)request.getParameter("subjectname"));
			getServletContext().getRequestDispatcher("/RequestSubtopic.jsp").forward(request, response);
			return;
		}else {
			if ("requestsubject".equals(action)) {
				Key sKey = KeyFactory.stringToKey((String)request.getParameter("sid"));
				// This is a json array of strings
				String subTopicList = request.getParameter("subTopicList");
				String[] subtopics = new Gson().fromJson(subTopicList,
						String[].class);
				// Try to create the subject and subtopics
				try {
					for(String t:subtopics){
						RequestSubtopic.createSubtopicRequest(t, sKey, t, 100);
					}
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