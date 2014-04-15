package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import account.Account;
import material.Subject;
import material.Subtopic;
import constants.Keys;
import discussion_board.Comment;
import discussion_board.Post;

public class DiscussionBoardServlet extends HttpServlet {

	private static final long serialVersionUID = 6318581988690447782L;

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String action = request.getParameter("action");
		if (action == null) {
			String subID = request.getParameter(Keys.SUBJECT_KEY);
			// See if should load subject
			if (subID != null) {
				Subject sub = Subject.getFromKeyString(subID);
				request.setAttribute(Keys.SUBJECT, sub);
				String subtopicID = request
						.getParameter(Keys.SUBJECT_TOPIC_KEY);
				// See if should load subtopic
				if (subtopicID != null) {
					Subtopic subtopic = Subtopic.getFromKeyString(subtopicID);
					request.setAttribute(Keys.SUBTOPIC, subtopic);
					String postID = request.getParameter(Keys.POST_KEY);
					// See if should load post
					if (postID != null) {
						Post post = Post.getFromKeyString(postID);
						request.setAttribute(Keys.POST, post);
					}
				}
			}
			getServletContext().getRequestDispatcher("/discussion-board.jsp")
					.forward(request, response);
		}
		else {
			if("createpost".equals(action)) {
				String content = request.getParameter("content");
				Account acc = (Account)request.getSession().getAttribute(Keys.ACCOUNT);
				if(content != null && content.length() > 0 && acc != null) {
					String subtopic = request.getParameter("subtopic");
					Subtopic sub = Subtopic.getFromKeyString(subtopic);
					// Create post
					Post.createPost(acc.getKey(), content, sub.getKey());
					response.getWriter().print("success");
				}
				else
					response.getWriter().print("");
			}
			else if("createcomment".equals(action)) {
				String content = request.getParameter("content");
				Account acc = (Account)request.getSession().getAttribute(Keys.ACCOUNT);
				if(content != null && content.length() > 0 && acc != null) {
					String postID = request.getParameter("post");
					Post post = Post.getFromKeyString(postID);
					// Create post
					Comment.createComment(acc.getKey(), content, post.getKey());
					response.getWriter().print("success");
				}
				else
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
