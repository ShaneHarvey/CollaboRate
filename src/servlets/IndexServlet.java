package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import material.Notes;
import material.Question;
import material.Subject;
import material.Subtopic;
import material.Video;
import material.VideoMetadata;
import account.Account;
import constants.Keys;

public class IndexServlet  extends HttpServlet  {
	private static final long serialVersionUID = 1593485103847102005L;
	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		
		Video hottestVideo = Video.getHottestVideo();
		request.setAttribute(Keys.VIDEO, hottestVideo);
		
		Notes mostRecentVideo = Notes.getMostRecentNotes();
		request.setAttribute(Keys.NOTES, mostRecentVideo);
		
		//Question ques = Question.getFromKeyString("ag5jeWJlci1ncmFwZS1iNXIVCxIIcXVlc3Rpb24YgICAgICAwAkM");
		Question ques = Question.getHottestQuestion();
		request.setAttribute(Keys.QUESTION, ques);
		
		getServletContext().getRequestDispatcher("/index.jsp").forward(
				request, response);
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
