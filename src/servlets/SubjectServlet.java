package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Key;

import account.Account;
import material.Statistics;
import material.Subject;
import material.Subtopic;
import constants.Keys;

public class SubjectServlet extends HttpServlet {

	private static final long serialVersionUID = 1593485103847102005L;

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		String subID = request.getParameter(Keys.SUBJECT_KEY);
		if(subID == null) {
			// If no subjectId, redirect to home
			response.sendRedirect("/home");
		}
		else {
			// Load subject and subtopics and put them into the response
			try{
				Subject sub = Subject.getFromKeyString(subID);
				ArrayList<Subtopic> subtopics = sub.getSubtopics();
				request.setAttribute(Keys.SUBJECT_NAME, sub.getTitle());
				request.setAttribute(Keys.SUBTOPICS_LIST, subtopics);
				request.setAttribute(Keys.SUBJECT_KEY, sub.getKey());
				// Load the statistics
				Account user = (Account)request.getSession().getAttribute(Keys.ACCOUNT);
				Key subjectKey = sub.getKey();

				int numQuestionsForSubject = 	Statistics.getNumberOfQuestionsForSubject(subjectKey);
				int numTopics = 				Statistics.getNumberOfSubtopics(subjectKey);
				request.setAttribute(Keys.NUM_Q_SUB, numQuestionsForSubject);
				request.setAttribute(Keys.NUM_TOPS, numTopics);
				if(user == null){
					request.setAttribute(Keys.NUM_Q_COMPLETED, "None");
					request.setAttribute(Keys.NUM_Q_CORRECT, "None");
					request.setAttribute(Keys.PERCENT_Q_CORRECT, "None");
					request.setAttribute(Keys.NUM_TOP_COMPLETED, "None");
					request.setAttribute(Keys.NUM_TOP_NOT_STARTED, "None");
				} else{
					Key userKey = user.getKey();
					int numQuestionsCompleted = 	Statistics.getNumberQuestionsCompleted(userKey, subjectKey);
					int numQuestionsCorrect = 		Statistics.getNumberQuestionsCorrect(userKey, subjectKey);
					double percentQuestionsCorrect =Statistics.getPercentageCorrect(userKey, subjectKey);
					int numSubtopicsCompleted =		Statistics.getSubtopicsCompleted(userKey, subjectKey);
					int numSubtopicsNotStarted = 	Statistics.getSubtopicsNotStarted(userKey, subjectKey);
					request.setAttribute(Keys.NUM_Q_COMPLETED, Integer.toString(numQuestionsCompleted));
					request.setAttribute(Keys.NUM_Q_CORRECT, Integer.toString(numQuestionsCorrect));
					request.setAttribute(Keys.PERCENT_Q_CORRECT, (int)Math.round(percentQuestionsCorrect)+"%");
					request.setAttribute(Keys.NUM_TOP_COMPLETED, Integer.toString(numSubtopicsCompleted));
					request.setAttribute(Keys.NUM_TOP_NOT_STARTED, Integer.toString(numSubtopicsNotStarted));
				}
				getServletContext().getRequestDispatcher("/subject.jsp").forward(request, response);
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