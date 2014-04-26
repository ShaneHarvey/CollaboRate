package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import material.Question;
import material.Subject;
import constants.Keys;
import account.Account;

public class VerifyServlet extends HttpServlet {

	private static final long serialVersionUID = 4597862894200222001L;

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String action = request.getParameter("action");

		Account acc = (Account) request.getSession().getAttribute(
				Keys.ACCOUNT);
		if (action == null) {
			String subKey = request.getParameter(Keys.SUBJECT_KEY);
			Subject sub = Subject.getFromKeyString(subKey);
			// Make sure there's a user, subject, and user has access to this
			// subject
			if (acc == null || sub == null || !sub.userTrusted(acc))
				response.sendRedirect("/home");
			else {
				request.setAttribute(Keys.SUBJECT, sub);
				getServletContext().getRequestDispatcher("/verify-content.jsp")
						.forward(request, response);
			}
		} 
		else {
			
			if ("removecontent".equals(action)) {
				String contentID = request.getParameter("cid");
				Question q = Question.getFromKeyString(contentID);
				if(q.getSubtopic().getSubject().userTrusted(acc))
					q.delete();
			}
			else if("verifycontent".equals(action)) {
				String contentID = request.getParameter("cid");
				Question q = Question.getFromKeyString(contentID);
				if(q.getSubtopic().getSubject().userTrusted(acc)) {
					q.setVerified(true);
					q.save();
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
