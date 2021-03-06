package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import material.Category;
import material.MaterialMetadata;
import material.MaterialMetadata.FlaggedMaterial;
import material.RequestSubject;
import material.RequestSubtopic;
import material.UserMaterialMetadata.MaterialType;
import material.Notes;
import material.Question;
import material.Subject;
import material.Subtopic;
import material.Video;
import constants.Keys;
import account.Account;
import dataStructures.Pair;
import dataStructures.Triple;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.gson.*;

public class AdminServlet extends HttpServlet {

	private static final long serialVersionUID = 1019307870758244635L;

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		// See if this is a page request or ajax call
		String action = (String) request.getParameter("action");
		// Attempt to retrieve account from session
		Account acc = (Account) request.getSession().getAttribute(Keys.ACCOUNT);
		
		if (action == null) {// || action.equals("changeOrder") || action.equals("insertOrder")) {
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
				
				request.setAttribute(Keys.SUBJECT_REQUEST_LIST, RequestSubject.getSubjectRequests());
				request.setAttribute(Keys.CATEGORY_LIST, Category.getAllCategories());
				
				getServletContext().getRequestDispatcher("/admin-home.jsp")
						.forward(request, response);
			}
		} 
		else if ("createsubject".equals(action)) {
				String category = request.getParameter("categoryKey");
				Key categoryKey = KeyFactory.stringToKey(category);
				String subject = request.getParameter("subjectName");
				// This is a json array of strings
				String subTopicList = request.getParameter("subTopicList");
				String[] subtopics = new Gson().fromJson(subTopicList,
						String[].class);

				// Try to create the subject and subtopics
				try {
					Subject.createSubject(subject, subject, subtopics,categoryKey);
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
		else if("getsubjects".equals(action)) {
			String categoryKey = request.getParameter(Keys.CATEGORY_KEY);
			Category c = Category.getCategory(categoryKey);
			ArrayList<Pair<String, String>> subs = new ArrayList<Pair<String, String>>();
			for(Subject sub : c.getSubjects()) {
				subs.add(new Pair<String,String>(sub.getTitle(), sub.getKeyAsString()));
			}
			response.getWriter().print(new Gson().toJson(subs));
		}
		else if("getsubtopics".equals(action)){
			String subjectKey = request.getParameter(Keys.SUBJECT_KEY);
			Subject sub = Subject.getFromKeyString(subjectKey);
			ArrayList<Triple<String, String, Long>> sts = new ArrayList<Triple<String, String, Long>>();
			for(Subtopic st : sub.getSubtopics()) {
				sts.add(new Triple<String, String, Long>(st.getTitle(), st.getKeyAsString(), st.getOrder()));
			}
			ArrayList<Pair<String, String>> strs = new ArrayList<Pair<String, String>>();
			for(RequestSubtopic rs: RequestSubtopic.getSubtopicsRequestfromSubject(subjectKey)){
				strs.add(new Pair<String,String>(rs.getTitle(), rs.getKeyAsString()));
			}
			response.getWriter().print("{ \"sts\" : " + new Gson().toJson(sts) + " , \"strs\" : " + new Gson().toJson(strs) + "}");
		}
		else if("insertOrder".equals(action)) {
			String subjectKey = request.getParameter(Keys.SUBJECT_KEY);
			Subject sub = Subject.getFromKeyString(subjectKey);
			String requestedKey = request.getParameter(Keys.REQUESTED_SUBTOPIC_KEY);
			RequestSubtopic reqSubtopic = RequestSubtopic.getFromKeyString(requestedKey);
			sub.insertSubtopic(reqSubtopic,reqSubtopic.getOrder());
			ArrayList<Triple<String, String, Long>> sts = new ArrayList<Triple<String, String, Long>>();
			for(Subtopic st : sub.getSubtopics()) {
				sts.add(new Triple<String, String, Long>(st.getTitle(), st.getKeyAsString(), st.getOrder()));
			}
			response.getWriter().print(new Gson().toJson(sts));
		}
		else if("deletesubtopic".equals(action)) {
			String stid = request.getParameter(Keys.SUBJECT_TOPIC_KEY);
			Subtopic st = Subtopic.getFromKeyString(stid);
			st.delete();
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