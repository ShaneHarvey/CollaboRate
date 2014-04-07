package servlets;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import material.Subject;
import material.Subtopic;
import material.Video;

import com.google.gson.*;

import constants.Keys;
import account.Account;

public class AddContentServlet extends HttpServlet {

	private static final long serialVersionUID = -6795978814514268314L;
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
		Account acc = (Account)request.getSession().getAttribute(Keys.ACCOUNT);
		Subtopic subtopic = (Subtopic)request.getSession().getAttribute(Keys.SUBTOPIC);
		if(acc == null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.sendRedirect("/home");
		}
		else {
			String action = request.getParameter("action");
			HttpSession session = request.getSession();
			
			if(action == null)
				getServletContext().getRequestDispatcher("/add-content.jsp").forward(request, response);
			else if("createquestion".equals(action)) {
				// Create a question
				String questionDescription = URLDecoder.decode(request.getParameter("description"), "UTC-8");
				String answersString = URLDecoder.decode(request.getParameter("answersList"), "UTC-8");
				String[] answersList = new Gson().fromJson(answersString, String[].class);
				int answerIndex = Integer.parseInt(request.getParameter("answerIndex"));
				
				// TODO: Create the question
				
				response.getWriter().print("success");
			}
			else if("addvideo".equals(action)) {
				// Add a video
				String videoDescription = URLDecoder.decode(request.getParameter("description"), "UTC-8");
				String videoURL = URLDecoder.decode(request.getParameter("url"), "UTC-8");
				
				// TODO: Create the video
				Video.createVideo(videoDescription, videoDescription, videoURL, subtopic.getSubjectKey(), acc.getKey());
				
				response.getWriter().print("success");
			}
			if("addnotes".equals(action)) {
				// Add notes
				String notesDescription = URLDecoder.decode(request.getParameter("description"), "UTC-8");
				String notesURL = URLDecoder.decode(request.getParameter("url"), "UTC-8");
				
				// TODO: Create the notes
				
				response.getWriter().print("success");
			}
			
		}
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