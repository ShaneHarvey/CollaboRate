package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import material.Subject;
import material.Subtopic;
import constants.Keys;
import discussion_board.Post;

public class DiscussionBoardServlet extends HttpServlet {
	
	private static final long serialVersionUID = 6318581988690447782L;
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
		String subID = request.getParameter(Keys.SUBJECT_KEY);
		// See if should load subject
		if(subID != null) {
			Subject sub = Subject.getFromKeyString(subID);
			request.setAttribute(Keys.SUBJECT, sub);
			String subtopicID = request.getParameter(Keys.SUBJECT_TOPIC_KEY);
			// See if should load subtopic
			if(subtopicID != null) {
				Subtopic subtopic = Subtopic.getFromKeyString(subtopicID);
				request.setAttribute(Keys.SUBTOPIC, subtopic);
				String postID = request.getParameter(Keys.POST_KEY);
				// See if should load post
				if(postID != null) {
					Post post = Post.getFromKeyString(postID);
					request.setAttribute(Keys.POST, post);
				}
			}	
		}
		getServletContext().getRequestDispatcher("/discussion-board.jsp").forward(request, response);
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
		processRequest(request, response);
    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
		processRequest(request, response);
    }
}
