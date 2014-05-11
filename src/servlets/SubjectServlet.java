package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Key;
import com.google.gson.Gson;

import account.Account;
import material.Statistics;
import material.Subject;
import material.Subtopic;
import constants.Keys;

public class SubjectServlet extends HttpServlet {

	private static final long serialVersionUID = 1593485103847102005L;

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		String action = request.getParameter("action");
		Account user = (Account) request.getSession()
				.getAttribute(Keys.ACCOUNT);
		if("updatetopics".equals(action) && user != null && user.getType() == Account.ActorType.ADMIN){
			String tops = request.getParameter("toplist");
			String[] topArray = new Gson().fromJson(tops, String[].class);
			long index = 0;
			for(String t : topArray){
				Subtopic top = Subtopic.getFromKeyString(t);
				top.setOrder(index++);
				top.save();
			}
			return;
		}
		String subID = request.getParameter(Keys.SUBJECT_KEY);
		if(subID == null) {
			// If no subjectId, redirect to home
			response.sendRedirect("/home");
		}
		else {
			try{
				Subject sub = Subject.getFromKeyString(subID);

				// Load the statistics
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
				request.setAttribute(Keys.SUBJECT, sub);
				
				/* Used to display drag and drop subtopics or not in jsp*/
				request.setAttribute("accountIsAdmin", user == null? false : user.getType() == Account.ActorType.ADMIN);
				
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