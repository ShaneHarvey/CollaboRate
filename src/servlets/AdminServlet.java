package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import material.MaterialMetadata;
import material.MaterialMetadata.FlaggedMaterial;
import material.RequestSubtopic;
import material.UserMaterialMetadata.MaterialType;
import material.Notes;
import material.Question;
import material.Subject;
import material.Subtopic;
import material.Video;
import constants.Keys;
import account.Account;

import com.google.gson.*;

public class AdminServlet extends HttpServlet {

	private static final long serialVersionUID = 1019307870758244635L;

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		// See if this is a page request or ajax call
		String action = (String) request.getParameter("action");
		// Attempt to retrieve account from session
		Account acc = (Account) request.getSession().getAttribute(Keys.ACCOUNT);
		
		

		if (action == null || action.equals("changeOrder") || action.equals("insertOrder")) {
			// Make sure there is an admin account account
			if (acc == null || acc.getType() != Account.ActorType.ADMIN)
				response.sendRedirect("/logout");
			else {
				ArrayList<FlaggedMaterial> flagged = MaterialMetadata
						.getSortedFlagged();
				ArrayList<Question> fQuestions = new ArrayList<Question>();
				ArrayList<Video> fVideos = new ArrayList<Video>();
				ArrayList<Notes> fNotes = new ArrayList<Notes>();

				// Iterate over all flagged data and place them into their
				// respective lists
				for (FlaggedMaterial fm : flagged) {
					MaterialType t = MaterialType.fromValue(fm
							.getMaterialType());
					switch (t) {
						case VIDEO:
							fVideos.add(Video.getVideo(fm.getKey()));
							break;
						case QUESTION:
							fQuestions.add(Question.getQuestion(fm.getKey()));
							break;
						case NOTES:
							fNotes.add(Notes.getNotes(fm.getKey()));
					}
				}

				// Put all flagged questions in request
				request.setAttribute(Keys.FLAGGED_QUESTIONS, fQuestions);
				// Put all flagged videos in request
				request.setAttribute(Keys.FLAGGED_VIDEOS, fVideos);
				// Put all flagged lectures in request
				request.setAttribute(Keys.FLAGGED_NOTES, fNotes);
				
				
				
				//Stuff for manage subject  -  phil

				
				request.setAttribute(Keys.SUBJECT_LIST,
						Subject.getAllSubjects());
				String subjectKey = request.getParameter(Keys.SUBJECT_KEY);
				if (subjectKey == null || subjectKey.equals("")) {
					response.getWriter().print("");
				}
				else {
					// Place the subject in the session;
					Subject sub = Subject.getFromKeyString(subjectKey);
					//request.setAttribute(Keys.SUBJECT, sub);
					sub.getSubtopics();
					
					String subtopicListHTML="<h3>Current Subtopics</h3><table class=\"subtopicList\" id=\"" + subjectKey +"\">";
					String subtopicListHTMLend = "</table>";
					
					
					if("changeOrder".equals(action)){
						String subtopicKey = request.getParameter(Keys.SUBJECT_TOPIC_KEY);
						Subtopic subtopic = Subtopic.getFromKeyString(subtopicKey);
						String newPlaceString = request.getParameter(Keys.ORDER);
						
						sub.changeOrder(subtopic, Integer.parseInt(newPlaceString));
					}
					else if("insertOrder".equals(action)){
						String requestedKey = request.getParameter(Keys.REQUESTED_SUBTOPIC_KEY);
						RequestSubtopic reqSubtopic = RequestSubtopic.getFromKeyString(requestedKey);
						
						sub.insertSubtopic(reqSubtopic,reqSubtopic.getOrder());
					}
					
					//put the subtopics there
					String next = "";
					for(Subtopic s:sub.getSubtopics()){
						next = "<tr><td><input id=\"" + s.getKeyAsString() + "\" class=\"subtopicInput\" value=\"" + s.getOrder() + "\" size=\"5\" onchange=\"reOrder(this.id)\"></td>" 
								+ "<td>" + s.getTitle() + "</td></tr>";
						
						subtopicListHTML += next;
					}
					
					//end subtopics
					subtopicListHTML += "</table><br/><br/><br/>";
					
					//start requested subtopics
					subtopicListHTML += "<h3>Requested Subtopics</h3><table>";
					
					
					//here i include the description
					for(RequestSubtopic rs: RequestSubtopic.getSubtopicsRequestfromSubject(subjectKey)){
						next = "<tr><td><span id=\"" + rs.getKeyAsString() + "\" class=\"glyphicon glyphicon-plus hoverHand\""
								+  "onclick=\"insertInOrder(this.id)\"></td>" 
								+ "<td>" + rs.getTitle() + "</td><td>" + rs.getDescription()+"</td</tr>";
						
						subtopicListHTML += next;
					}
					
					
					
					
					
					
					response.getWriter().print(subtopicListHTML);
					
				}

				getServletContext().getRequestDispatcher("/admin-home.jsp")
						.forward(request, response);
			}
		} else {
			if ("createsubject".equals(action)) {
				String subject = request.getParameter("subjectName");
				// This is a json array of strings
				String subTopicList = request.getParameter("subTopicList");
				String[] subtopics = new Gson().fromJson(subTopicList,
						String[].class);

				// Try to create the subject and subtopics
				try {
					Subject.createSubject(subject, subject, subtopics);
					response.getWriter().print("success");
				} catch (Exception e) {
					e.printStackTrace();
					response.getWriter().print("");
				}
			} 
			else if ("removecontent".equals(action)) {
				String cID = request.getParameter(Keys.CONTENT_KEY);
				long cType = Long.parseLong(request.getParameter(Keys.CONTENT_TYPE));
				MaterialType t = MaterialType.fromValue(cType);
				switch(t) {
				case VIDEO:
					Video v = Video.getFromKeyString(cID);
					v.delete();
					break;
				case QUESTION: 
					Question q = Question.getFromKeyString(cID);
					q.delete();
					break;
				case NOTES: 
					Notes n = Notes.getFromKeyString(cID);
					n.delete();
					break;
				}
			}
			else if("unflagcontent".equals(action)) {
				String cID = request.getParameter(Keys.CONTENT_KEY);
				long cType = Long.parseLong(request.getParameter(Keys.CONTENT_TYPE));
				MaterialType t = MaterialType.fromValue(cType);
				switch(t) {
				case VIDEO:
					Video v = Video.getFromKeyString(cID);
					v.unflag();
					break;
				case QUESTION: 
					Question q = Question.getFromKeyString(cID);
					q.unflag();
					break;
				case NOTES: 
					Notes n = Notes.getFromKeyString(cID);
					n.unflag();
					break;
				}
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