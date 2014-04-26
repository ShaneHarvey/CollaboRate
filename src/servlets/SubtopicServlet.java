package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import account.Account;
import material.MaterialMetadata;
import material.MaterialMetadata.RatedMaterial;
import material.Notes;
import material.Question;
import material.Subject;
import material.Subtopic;
import material.Test;
import material.Video;
import material.UserMaterialMetadata.MaterialType;
import constants.Keys;

public class SubtopicServlet extends HttpServlet {

	private static final long serialVersionUID = 1593485103847102005L;
	
	private static final int MATERIAL_COUNT = 5;

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		String stID = request.getParameter(Keys.SUBJECT_TOPIC_KEY);
		if (stID == null) {
			// If no subjectId, redirect to home
			response.sendRedirect("/home");
		} else {
			// Get subtopic and place in request
			try {
				Subtopic st = Subtopic.getFromKeyString(stID);
				request.setAttribute(Keys.SUBTOPIC, st);
				Subject sub = st.getSubject();
				request.setAttribute(Keys.SUBJECT, sub);
				Account acc = (Account) request.getSession().getAttribute(
						Keys.ACCOUNT);
				// Place top rated questions, videos, notes into response
				ArrayList<RatedMaterial> rated = MaterialMetadata
						.getSortedByRating(st.getKey());
				ArrayList<Question> rQuestions = new ArrayList<Question>();
				ArrayList<Video> rVideos = new ArrayList<Video>();
				ArrayList<Notes> rNotes = new ArrayList<Notes>();

				// Iterate over all flagged data and place them into their
				// respective lists
				for (RatedMaterial fm : rated) {
					MaterialType t = MaterialType.fromValue(fm
							.getMaterialType());
					switch (t) {
						case VIDEO:
							if(rVideos.size() < MATERIAL_COUNT)
								rVideos.add(Video.getVideo(fm.getKey()));
							break;
						case QUESTION:
							if(rQuestions.size() < MATERIAL_COUNT)
								rQuestions.add(Question.getQuestion(fm.getKey()));
							break;
						case NOTES:
							if(rNotes.size() < MATERIAL_COUNT)
								rNotes.add(Notes.getNotes(fm.getKey()));
					}
				}
				
				// If questions doesn't have 5 questions, make 5
				if(rQuestions.size() < MATERIAL_COUNT) {
					ArrayList<Question> rands = Question.getXQuestions(5, st.getKey());
					while(rQuestions.size() < MATERIAL_COUNT && rands.size() > 0) {
						Question temp = rands.remove(0);
						if(!rQuestions.contains(temp))
							rQuestions.add(temp);
					}
				}
				
				// If videos doesn't have 5 videos, make 5
				if(rVideos.size() < MATERIAL_COUNT) {
					ArrayList<Video> rands = Video.getXVideos(5, st.getKey());
					while(rVideos.size() < MATERIAL_COUNT && rands.size() > 0) {
						Video temp = rands.remove(0);
						if(!rVideos.contains(temp))
							rVideos.add(temp);
					}
				}
				
				// If notes doesn't have 5 notes, make 5
				if(rNotes.size() < MATERIAL_COUNT) {
					ArrayList<Notes> rands = Notes.getXNotes(5, st.getKey());
					while(rNotes.size() < MATERIAL_COUNT && rands.size() > 0) {
						Notes temp = rands.remove(0);
						if(!rNotes.contains(temp))
							rNotes.add(temp);
					}
				}

				// Put all flagged questions in request
				request.setAttribute(Keys.TOP_QUESTIONS, rQuestions);
				// Put all flagged videos in request
				request.setAttribute(Keys.TOP_VIDEOS, rVideos);
				// Put all flagged lectures in request
				request.setAttribute(Keys.TOP_NOTES, rNotes);
				
				// If logged in, try to place test data for this subtopic in the
				// response
				if (acc != null) {
					Test t = Test.getTest(acc, st);
					// If user hasn't taken this test or hasn't passed it, false
					if (t == null || !t.getPassed())
						request.setAttribute(Keys.TEST, false);
					else
						request.setAttribute(Keys.TEST, true);
				}
				getServletContext().getRequestDispatcher("/subtopic.jsp")
						.forward(request, response);
			} catch (IllegalArgumentException e) {
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