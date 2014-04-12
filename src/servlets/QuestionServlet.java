package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import account.Account;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import material.Question;
import material.Subject;
import material.Subtopic;
import material.UserMaterialMetadata;
import constants.Keys;

public class QuestionServlet extends HttpServlet {

	private static final long serialVersionUID = 1593485103847102005L;

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		String qID = request.getParameter(Keys.QUESTION_KEY);
		String action = request.getParameter("action");
		Account user = (Account) request.getSession().getAttribute(Keys.ACCOUNT);
		Key questionKey = KeyFactory.stringToKey(qID);
		if (action == null) {
			if (qID == null) {
				// If no subjectId, redirect to home
				response.sendRedirect("/home");
			} else {
				try {
					// Get subtopic and place is request
					Question ques = Question.getFromKeyString(qID);
					request.setAttribute(Keys.QUESTION, ques);
					Subtopic st = ques.getSubtopic();
					request.setAttribute(Keys.SUBTOPIC, st);
					Subject sub = st.getSubject();
					request.setAttribute(Keys.SUBJECT, sub);
		
					getServletContext().getRequestDispatcher("/question.jsp").forward(
							request, response);
				} catch(IllegalArgumentException e){
					response.sendRedirect("/home");
				}
			}
		} else {
			if ("answerquestion".equals(action)) {
				// Two cases, can answer the question and save response
				// or just give the person feedback (not logged in)
				int answer = Integer.parseInt(request.getParameter("answer"));
				if(Question.getQuestion(questionKey).getCorrectIndex() == answer){
					UserMaterialMetadata a = UserMaterialMetadata.getUserMaterialMetadata(user.getKey(), questionKey);
					if(a!= null){
						a.setViewed();
						a.save();
					}
					else{
						UserMaterialMetadata newRow = UserMaterialMetadata.createUserMaterialMetadata(user.getKey(), questionKey);
						newRow.setViewed();
						newRow.save();
					}
				}
				else{
					//TODO Fill in wrong answer portion  
				}
				
				response.getWriter().print("success");
			} else if ("ratequestion".equals(action)) {
				// Rate question
				int rating = Integer.parseInt(request.getParameter("rating"));
				UserMaterialMetadata a = UserMaterialMetadata.getUserMaterialMetadata(user.getKey(), questionKey);
				if(a!= null){
					a.setMaterialRating(rating);
					a.save();
				}
				else{
					UserMaterialMetadata newRow = UserMaterialMetadata.createUserMaterialMetadata(user.getKey(), questionKey);
					newRow.setMaterialRating(rating);
					newRow.save();
				}
				response.getWriter().print("success");
			} else if ("flagquestion".equals(action)) {
				// Flag question
				UserMaterialMetadata a = UserMaterialMetadata.getUserMaterialMetadata(user.getKey(), questionKey);
				if(a != null){
					a.setFlagged(true);
					a.save();
				}
				else{
					UserMaterialMetadata newRow = UserMaterialMetadata.createUserMaterialMetadata(user.getKey(), questionKey);
					newRow.setFlagged(true);
					newRow.save();
				}
				response.getWriter().print("success");
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