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
				//ArrayList<Subtopic> subtopics = sub.getSubtopics();
				request.setAttribute(Keys.SUBJECT, sub);
				//request.setAttribute(Keys.SUBJECT_NAME, sub.getTitle());
				//request.setAttribute(Keys.SUBTOPICS_LIST, subtopics);
				//request.setAttribute(Keys.SUBJECT_KEY, sub.getKey());
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