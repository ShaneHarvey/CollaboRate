package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import material.Question;
import material.Subject;
import material.Subtopic;
import material.Video;
import constants.Keys;

public class QuestionServlet extends HttpServlet {

	private static final long serialVersionUID = 1593485103847102005L;

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		String qID = request.getParameter(Keys.QUESTION_KEY);
		if (qID == null) {
			// If no subjectId, redirect to home
			response.sendRedirect("/home");
		} else {
			try {
				// Get subtopic and place is request
				Question ques = Question.getFromKeyString(qID);
				request.setAttribute(Keys.QUESTION, ques);
				Subtopic st = ques.getSubtopic();
				request.setAttribute(Keys.SUBTOPIC, st);
				Subject sub = st.getSubject();
				request.setAttribute(Keys.SUBJECT, sub);
	
				getServletContext().getRequestDispatcher("/question.jsp").forward(
						request, response);
			} catch(IllegalArgumentException e){
				response.sendRedirect("/home");
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