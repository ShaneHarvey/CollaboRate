package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import material.MaterialMetadata;
import material.MaterialMetadata.RatedMaterial;
import material.Notes;
import material.Question;
import material.Subject;
import material.Subtopic;
import material.Video;
import material.UserMaterialMetadata.MaterialType;
import constants.Keys;

public class TopRatedContentServlet extends HttpServlet {

	private static final long serialVersionUID = 273986268403104165L;

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		String stID = request.getParameter(Keys.SUBJECT_TOPIC_KEY);
		String type = request.getParameter(Keys.CONTENT_TYPE);
		Integer from = null;
		Integer to = null;
		try {
			from = Integer.parseInt(request.getParameter(Keys.FROM_INDEX));
			to = Integer.parseInt(request.getParameter(Keys.TO_INDEX));
		} catch (Exception e) {
		}
		if (stID == null || type == null || from == null || to == null) {
			// If no subjectId, redirect to home
			response.sendRedirect("/home");
		} else {
			// Get subtopic and place in request
			try {
				Subtopic st = Subtopic.getFromKeyString(stID);
				request.setAttribute(Keys.SUBTOPIC, st);
				Subject sub = st.getSubject();
				request.setAttribute(Keys.SUBJECT, sub);
				// Place top rated questions, videos, notes into response
				ArrayList<RatedMaterial> rated = MaterialMetadata
						.getSortedByRating(st.getKey());
				switch (type) {
					case "video": {
						ArrayList<Video> videos = new ArrayList<Video>();
						ArrayList<Video> ratedVideos = new ArrayList<Video>();
						int count = 0;
						// Go through all rated materials
						for (RatedMaterial fm : rated) {
							// If found required materials, stop
							if (videos.size() >= (to - from))
								break;
							// Get material type of this material
							MaterialType t = MaterialType.fromValue(fm
									.getMaterialType());
							// If type is video
							if (t == MaterialType.VIDEO) {
								// Increment counter
								count++;
								Video vid = Video.getVideo(fm.getKey());
								if (count >= from)
									videos.add(vid);;
								ratedVideos.add(vid);
							}
						}
						// If still not enough videos, just start adding randomly
						if (videos.size() < (to - from)) {
							ArrayList<Video> rands = Video.getXVideos(to
									- count + videos.size(), st.getKey());
							while (videos.size() < (to - from)
									&& rands.size() > 0) {
								Video temp = rands.remove(0);
								if (ratedVideos.contains(temp))
									continue;
								count++;
								if (count >= from)
									videos.add(temp);
							}
						}
						// Put all flagged questions in request
						request.setAttribute(Keys.TOP_VIDEOS, videos);
						request.setAttribute(Keys.FROM_INDEX, from);
						request.setAttribute(Keys.TO_INDEX, to);
						request.setAttribute("hasMore", videos.size() == (to - from));
						getServletContext().getRequestDispatcher(
								"/top-videos.jsp").forward(request, response);
						break;
					}
					case "notes": {
						ArrayList<Notes> notes = new ArrayList<Notes>();
						ArrayList<Notes> ratedNotes = new ArrayList<Notes>();
						int count = 0;
						// Go through all rated materials
						for (RatedMaterial fm : rated) {
							// If found required materials, stop
							if (notes.size() >= (to - from))
								break;
							// Get material type of this material
							MaterialType t = MaterialType.fromValue(fm
									.getMaterialType());
							// If type is question
							if (t == MaterialType.NOTES) {
								// Increment question counter
								count++;
								Notes note = Notes.getNotes(fm.getKey());
								// If reached point after minimum question, add to
								// list
								if (count >= from)
									notes.add(note);
								ratedNotes.add(note);
							}
						}
						// If still not enough notes, just start adding randomly
						if (notes.size() < (to - from)) {
							/*
							 * (To - From) = questions needed (From - Count) =
							 * questions needed before start taking questions
							 * (questions.size()) = number of questions already have
							 * (needed + needed + have = total required)
							 */
							ArrayList<Notes> rands = Notes.getXNotes(to
									- count + notes.size(), st.getKey());
							while (notes.size() < (to - from)
									&& rands.size() > 0) {
								Notes temp = rands.remove(0);
								if (ratedNotes.contains(temp))
									continue;
								count++;
								if (count >= from)
									notes.add(temp);
							}
						}
						// Put all flagged questions in request
						request.setAttribute(Keys.TOP_NOTES, notes);
						request.setAttribute(Keys.FROM_INDEX, from);
						request.setAttribute(Keys.TO_INDEX, to);
						request.setAttribute("hasMore", notes.size() == (to - from));
						getServletContext().getRequestDispatcher(
								"/top-notes.jsp").forward(request, response);
						break;
					}
					default: {
						ArrayList<Question> questions = new ArrayList<Question>();
						ArrayList<Question> ratedQuestions = new ArrayList<Question>();
						int count = 0;
						// Go through all rated materials
						for (RatedMaterial fm : rated) {
							// If found required materials, stop
							if (questions.size() >= (to - from))
								break;
							// Get material type of this material
							MaterialType t = MaterialType.fromValue(fm
									.getMaterialType());
							// If type is question
							if (t == MaterialType.QUESTION) {
								// Increment question counter
								count++;
								Question quest = Question.getQuestion(fm.getKey());
								// If reached point after minimum question, add to
								// list
								if (count >= from)
									questions.add(quest);
								ratedQuestions.add(quest);
							}
						}
						// If still not enough questions, just start adding randomly
						if (questions.size() < (to - from)) {
							/*
							 * (To - From) = questions needed (From - Count) =
							 * questions needed before start taking questions
							 * (questions.size()) = number of questions already have
							 * (needed + needed + have = total required)
							 */
							ArrayList<Question> rands = Question.getXQuestions(to
									- count + questions.size(), st.getKey());
							while (questions.size() < (to - from)
									&& rands.size() > 0) {
								Question temp = rands.remove(0);
								if (ratedQuestions.contains(temp))
									continue;
								count++;
								if (count >= from)
									questions.add(temp);
							}
						}
						// Put all flagged questions in request
						request.setAttribute(Keys.TOP_QUESTIONS, questions);
						request.setAttribute(Keys.FROM_INDEX, from);
						request.setAttribute(Keys.TO_INDEX, to);
						request.setAttribute("hasMore", questions.size() == (to - from));
						getServletContext().getRequestDispatcher(
								"/top-questions.jsp").forward(request, response);
					}
				}
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