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

public class QuestionServlet extends HttpServlet {

	private static final long serialVersionUID = 1593485103847102005L;

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		String action = request.getParameter("action");
		Account user = (Account) request.getSession()
				.getAttribute(Keys.ACCOUNT);
		if (action == null) {
			String qID = request.getParameter(Keys.QUESTION_KEY);
			Key questionKey = KeyFactory.stringToKey(qID);
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
					if (user != null) {
						QuestionMetadata data = QuestionMetadata
								.getQuestionMetadata(user.getKey(), questionKey);
						request.setAttribute(Keys.META_DATA, data);
					}
					getServletContext().getRequestDispatcher("/question.jsp")
							.forward(request, response);
				} catch (IllegalArgumentException e) {
					response.sendRedirect("/home");
				}
			}
		} else {
			String subjectKey = request.getParameter(Keys.SUBJECT_KEY);
			Key sKey = KeyFactory.stringToKey(subjectKey);
			if ("answerquestion".equals(action)) {
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
								user.getKey(), questionKey, sKey);
					a.setAnswer(answer == correctAnswer);
					a.save();
				}
				response.getWriter().print(
						"{ \"answer\" : " + correctAnswer + "}");
			} else if ("ratecontent".equals(action)) {
				// If not logged in can't rate
				if (user == null)
					return;
				String qID = request.getParameter(Keys.CONTENT_KEY);
				Key questionKey = KeyFactory.stringToKey(qID);
				int rating = Integer.parseInt(request.getParameter("rating"));
				QuestionMetadata a = QuestionMetadata.getQuestionMetadata(
						user.getKey(), questionKey);
				if (a == null)
					a = QuestionMetadata.createQuestionMetadata(user.getKey(),
							questionKey, sKey);
				a.setMaterialRating(rating);
				a.save();
			} else if ("flagcontent".equals(action)) {
				// If not logged in can't flag
				if (user == null)
					return;
				String qID = request.getParameter(Keys.CONTENT_KEY);
				Key questionKey = KeyFactory.stringToKey(qID);
				QuestionMetadata a = QuestionMetadata.getQuestionMetadata(
						user.getKey(), questionKey);

				boolean flag = "true".equals(request.getParameter("flag"));
				if (a == null)
					a = QuestionMetadata.createQuestionMetadata(user.getKey(),
							questionKey, sKey);
				a.setFlagged(flag);
				a.save();
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