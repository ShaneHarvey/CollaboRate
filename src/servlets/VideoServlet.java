package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import material.Subject;
import material.Subtopic;
import material.Video;
import constants.Keys;

public class VideoServlet extends HttpServlet {

	private static final long serialVersionUID = 1593485103847102005L;

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		String vID = request.getParameter(Keys.VIDEO_KEY);
		if (vID == null) {
			// If no subjectId, redirect to home
			response.sendRedirect("/home");
		} else {
			try {
				// Get subtopic and place is request
				Video vid = Video.getFromKeyString(vID);
				request.setAttribute(Keys.VIDEO, vid);
				Subtopic st = vid.getSubtopic();
				request.setAttribute(Keys.SUBTOPIC, st);
				Subject sub = st.getSubject();
				request.setAttribute(Keys.SUBJECT, sub);
	
				getServletContext().getRequestDispatcher("/video.jsp").forward(
						request, response);
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