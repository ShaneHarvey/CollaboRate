package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.gson.Gson;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import material.Notes;
import material.Question;
import material.Subject;
import material.Subtopic;
import material.Video;
import constants.Keys;
import account.Account;

public class AddContentServlet extends HttpServlet {

	private static final long serialVersionUID = -6795978814514268314L;

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		Account acc = (Account) request.getSession().getAttribute(Keys.ACCOUNT);
		String stid =  request.getParameter(Keys.SUBJECT_TOPIC_KEY);
		String subjectKeyStr = request.getParameter(Keys.SUBJECT_KEY);
		Subtopic subtopic;
		if (acc == null)
			response.sendRedirect("/home");
		else {
			String action = request.getParameter("action");

			if (action == null) {
				String subjectKey = request.getParameter(Keys.SUBJECT_KEY);
				if (subjectKey == null) {
					request.setAttribute(Keys.SUBJECT_LIST,
							Subject.getAllSubjects());
				} else {
					// Place the subject in the session;
					Subject sub = Subject.getFromKeyString(subjectKey);
					request.setAttribute(Keys.SUBJECT, sub);
					String subtopicKey = request
							.getParameter(Keys.SUBJECT_TOPIC_KEY);
					if (subtopicKey != null) {
						// Place the subtopic in the session
						Subtopic st = Subtopic.getFromKeyString(subtopicKey);
						request.setAttribute(Keys.SUBTOPIC, st);
					}
				}
				getServletContext().getRequestDispatcher("/add-content.jsp")
						.forward(request, response);
			} else if(stid != null && (subtopic = Subtopic.getFromKeyString(stid)) != null) {
				Key subjectKey2 = KeyFactory.stringToKey(subjectKeyStr);;
				if ("createquestion".equals(action)) {
					// Create a question
					String questionDescription =  Jsoup.clean(request.getParameter("description"), Whitelist.basicWithImages());
					String answerDescription = Jsoup.clean(request.getParameter("answerdescription"), Whitelist.basicWithImages());

					String answersString = request.getParameter("answersList");
					String[] unsafeAnswers = new Gson().fromJson(answersString,String[].class);
					String[] safeAnswers = new String[unsafeAnswers.length];
					for(int i=0; i< unsafeAnswers.length; i++){
						safeAnswers[i] = Jsoup.clean(unsafeAnswers[i], Whitelist.basicWithImages());
					}
					
					int answerIndex = Integer.parseInt(request
							.getParameter("answerIndex"));
					String shortTitle = request.getParameter("shorttitle");
					Question.createQuestion(questionDescription, shortTitle, answerDescription, safeAnswers,
							answerIndex, subtopic, acc);

					response.getWriter().print("success");
				} else if ("addvideo".equals(action)) {
					// Add a video
					String videoDescription = request.getParameter("description");
					String videoURL = request.getParameter("url");

					Video.createVideo(videoDescription, videoDescription,
							videoURL, subtopic.getKey(), acc.getKey(), subjectKey2);

					response.getWriter().print("success");
				} else if ("addnotes".equals(action)) {
					// Add notes
					String notesDescription = request.getParameter("description");
					String notesURL = request.getParameter("url");
					
					Notes.createNotes(notesDescription, notesDescription,
							notesURL, subtopic.getKey(), acc.getKey(),subjectKey2);

					response.getWriter().print("success");
				}
			}
			else {
				response.getWriter().print("");
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