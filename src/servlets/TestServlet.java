package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import account.Account;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.gson.Gson;

import material.Question;
import material.QuestionMetadata;
import material.Subject;
import material.Subtopic;
import material.Test;
import constants.Keys;

public class TestServlet extends HttpServlet {

	private static final long serialVersionUID = 1593485103847102005L;

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		String action = request.getParameter("action");
		Account user = (Account) request.getSession()
				.getAttribute(Keys.ACCOUNT);
		if (user == null) {
			// If no user, redirect to home
			response.sendRedirect("/home");
			return;
		}
		if (action == null) {
			try {
				// Get subtopic and subject
				String subjectID =	request.getParameter(Keys.SUBJECT_KEY);
				String subtopicID = request.getParameter(Keys.SUBJECT_TOPIC_KEY);
				Key subjectKey = 	KeyFactory.stringToKey(subjectID);
				Key subtopicKey = 	KeyFactory.stringToKey(subtopicID);
				Subtopic st = 		Subtopic.getSubtopic(subtopicKey);
				Subject sub = 		Subject.getSubject(subjectKey);
				request.setAttribute(Keys.SUBTOPIC, st);
				request.setAttribute(Keys.SUBJECT, sub);
				// See if this user already took test
				Test test = Test.getTest(user.getKey(), subjectKey, subtopicKey);
				if(test != null && test.getPassed()) {
					response.sendRedirect("/home");	
					return;
				}
				// Create test
				test = Test.createTest(user.getKey(), subjectKey, subtopicKey);
				// Store the test in the session
				Question question = test.getCurrentQuestion();
				if (question == null){
					// If no questions could be loaded redirect
					response.sendRedirect("/home");	
					return;
				}
				request.setAttribute(Keys.QUESTION, question);
				request.getSession().setAttribute(Keys.TEST, test);
				// Load Metadata for question
				QuestionMetadata data = QuestionMetadata
						.getQuestionMetadata(user.getKey(), question.getKey());
				request.setAttribute(Keys.META_DATA, data);
				getServletContext().getRequestDispatcher("/take-test.jsp")
						.forward(request, response);
			} catch (Exception e) {
				response.sendRedirect("/home");
			}
		} else {
			if ("answerquestion".equals(action)) {
				// Load the test object
				Test test = (Test) request.getSession().getAttribute(Keys.TEST);
				if(test == null){
					response.sendRedirect("/subtopic?"+Keys.SUBJECT_TOPIC_KEY+
							"="+request.getParameter(Keys.SUBJECT_TOPIC_KEY));
					return;
				}
				String qID = request.getParameter(Keys.QUESTION_KEY);
				Key questionKey = KeyFactory.stringToKey(qID);
				// Get the users answer
				int userAnswer = Integer.parseInt(request.getParameter("answer"));
				// Get correct answer
				int correctAnswer = Question.getQuestion(questionKey)
						.getCorrectIndex();
				// Update their question meta data
				QuestionMetadata a = QuestionMetadata.getQuestionMetadata(
						user.getKey(), questionKey);
				if (a == null)
					a = QuestionMetadata.createQuestionMetadata(
							user.getKey(), questionKey);
				a.setAnswer(userAnswer == correctAnswer);
				a.save();
				// Log the user's result
				boolean testOver = test.logResult(userAnswer == correctAnswer);
				// End test early if user has passed
				boolean passed = test.gradeTest();
				if(testOver || passed){
					//Remove test from session
					request.getSession().removeAttribute(Keys.TEST);
					response.getWriter().print("{ \"answer\":" + correctAnswer + 
							",\"testResult\":" + passed + "}");
				} else {
					// Load the next question
					Question nextQuestion = test.getCurrentQuestion();
					request.setAttribute(Keys.QUESTION, nextQuestion);
					request.getSession().setAttribute(Keys.TEST, test);
					QuestionMetadata data = QuestionMetadata.getQuestionMetadata(user.getKey(), nextQuestion.getKey());
					// Send the answer and the next question
					response.getWriter().print("{ \"answer\":" + correctAnswer + 
							",\"nextQuestion\": {\"title\" : " + "\""+nextQuestion.getTitle()+"\"" +
							",\"answerChoices\":" + nextQuestion.getAnswerChoicesJson() +
							",\"qid\" : " + "\"" + nextQuestion.getKeyAsString() + "\"" + 
							",\"globalRating\":" + nextQuestion.getGlobalRating() + "}" +
							",\"pct\":" + test.getPercentageCorrect() +
							(data != null ? ",\"userRating\":" + data.getRating() + 
							",\"flagged\":" + data.getFlagged() : "") + "}");
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