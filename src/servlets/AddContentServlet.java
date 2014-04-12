package servlets;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import material.Notes;
import material.Question;
import material.Subject;
import material.Subtopic;
import material.Video;

import com.google.gson.*;

import constants.Keys;
import account.Account;

public class AddContentServlet extends HttpServlet {

	private static final long serialVersionUID = -6795978814514268314L;

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		Account acc = (Account) request.getSession().getAttribute(Keys.ACCOUNT);
		String stid =  request.getParameter(Keys.SUBJECT_TOPIC_KEY);
		Subtopic subtopic;
		if (acc == null)
			response.sendRedirect("/home");
		else {
			String action = request.getParameter("action");

			if (action == null) {
				String subjectKey = request.getParameter(Keys.SUBJECT_KEY);
				if (subjectKey == null) {
					request.setAttribute(Keys.SUBJECT_LIST,
							Subject.getSubjects());
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
				if ("createquestion".equals(action)) {
					// Create a question
					String questionDescription = URLDecoder.decode(
							request.getParameter("description"), "UTF-8");
					String answersString = URLDecoder.decode(
							request.getParameter("answersList"), "UTF-8");
					String[] answersList = new Gson().fromJson(answersString,
							String[].class);
					// we are going to store the answers as json
					// we need to discuss
					int answerIndex = Integer.parseInt(request
							.getParameter("answerIndex"));

					Question.createQuestion(questionDescription, answersList,
							answerIndex, subtopic.getKey(), acc.getKey());

					response.getWriter().print("success");
				} else if ("addvideo".equals(action)) {
					// Add a video
					String videoDescription = URLDecoder.decode(
							request.getParameter("description"), "UTF-8");
					String videoURL = URLDecoder.decode(
							request.getParameter("url"), "UTF-8");

					Video.createVideo(videoDescription, videoDescription,
							videoURL, subtopic.getKey(), acc.getKey());

					response.getWriter().print("success");
				} else if ("addnotes".equals(action)) {
					// Add notes
					String notesDescription = URLDecoder.decode(
							request.getParameter("description"), "UTF-8");
					String notesURL = URLDecoder.decode(
							request.getParameter("url"), "UTF-8");

					Notes.createNotes(notesDescription, notesDescription,
							notesURL, subtopic.getKey(), acc.getKey());

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