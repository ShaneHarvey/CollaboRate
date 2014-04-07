package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import material.Subject;
import material.Subtopic;
import constants.Keys;

public class SubtopicServlet extends HttpServlet {

	private static final long serialVersionUID = 1593485103847102005L;

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		String stID = request.getParameter(Keys.SUBJECT_TOPIC_KEY);
		if(stID == null) {
			// If no subjectId, redirect to home
			response.sendRedirect("/home");
		}
		else {
			// Get subtopic being loaded
			Subtopic st = Subtopic.getFromKeyString(stID);
			request.setAttribute(Keys.SUBTOPIC_NAME, st.getTitle());
			// TODO: Place videos, notes, questions into session
			
			getServletContext().getRequestDispatcher("/topic.jsp").forward(request, response);
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