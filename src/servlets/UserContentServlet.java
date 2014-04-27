package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import material.MaterialMetadata;
import material.MaterialMetadata.FlaggedMaterial;
import material.RequestSubject;
import material.RequestSubtopic;
import material.UserMaterialMetadata.MaterialType;
import material.Notes;
import material.Question;
import material.Subject;
import material.Subtopic;
import material.Video;
import material.VideoMetadata;
import constants.Keys;
import account.Account;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.gson.*;

public class UserContentServlet extends HttpServlet {

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
			ArrayList<Question> questions = Question.getUsersGeneratedQuestions(user.getKey());
			ArrayList<Video> videos = Video.getUsersGeneratedVideo(user.getKey());
			ArrayList<Notes> notes = Notes.getUsersGeneratedNotes(user.getKey());

			// Put all questions in request
			request.setAttribute(Keys.USERS_QUESTIONS, questions);
			// Put all the user notes in request
			request.setAttribute(Keys.USERS_NOTES,notes);
			// Put all videos in request
			request.setAttribute(Keys.USERS_VIDEOS, videos);

			getServletContext().getRequestDispatcher("/user-content.jsp").forward(request, response);
			return;
		}else {
			if ("requestsubject".equals(action)) {
				
			} 
			//response.sendRedirect("/home");
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