package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import account.Account;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import material.Question;
import material.QuestionMetadata;
import material.Subject;
import material.Subtopic;
import constants.Keys;

public class TestServlet extends HttpServlet {

	private static final long serialVersionUID = 1593485103847102005L;

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		String action = request.getParameter("action");
		Account user = (Account) request.getSession()
				.getAttribute(Keys.ACCOUNT);
		if (action == null) {
			// TODO: Create a test and get a question from it instead of hard coded question.
			String qID = "ag5jeWJlci1ncmFwZS1iM3IVCxIIcXVlc3Rpb24YgICAgICAwggM";
			// Make sure there's a question and a user
			if (qID == null || user == null) {
				// If no subjectId, redirect to home
				response.sendRedirect("/home");
			} else {
				try {
					Key questionKey = KeyFactory.stringToKey(qID);
					// Get subtopic and place is request
					Question ques = Question.getFromKeyString(qID);
					request.setAttribute(Keys.QUESTION, ques);
					Subtopic st = ques.getSubtopic();
					request.setAttribute(Keys.SUBTOPIC, st);
					Subject sub = st.getSubject();
					request.setAttribute(Keys.SUBJECT, sub);
					if (user != null) {
						QuestionMetadata data = QuestionMetadata
								.getQuestionMetadata(user.getKey(), questionKey);
						request.setAttribute(Keys.META_DATA, data);
					}
					getServletContext().getRequestDispatcher("/take-test.jsp")
							.forward(request, response);
				} catch (IllegalArgumentException e) {
					response.sendRedirect("/home");
				}
			}
		} else {
			if ("answerquestion".equals(action)) {
				// TODO: Add functionality to update test object
				String qID = request.getParameter(Keys.QUESTION_KEY);
				Key questionKey = KeyFactory.stringToKey(qID);
				// Get the correct answer
				int answer = Integer.parseInt(request.getParameter("answer"));
				// Get correct answer
				int correctAnswer = Question.getQuestion(questionKey)
						.getCorrectIndex();
				// If user exists, update their question meta data
				if (user != null) {
					// Get the question metadata
					QuestionMetadata a = QuestionMetadata.getQuestionMetadata(
							user.getKey(), questionKey);
					if (a == null)
						a = QuestionMetadata.createQuestionMetadata(
								user.getKey(), questionKey);
					a.setAnswer(answer == correctAnswer);
					a.save();
				}
				response.getWriter().print(
						"{ \"answer\" : " + correctAnswer + "}");
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