package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import material.Category;
import material.RequestSubject;
import material.RequestSubtopic;
import material.Subject;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import constants.Keys;
import account.Account;

public class ApproveSubjectRequestServlet extends HttpServlet {

	private static final long serialVersionUID = -3858981710259933086L;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
		String action = request.getParameter("action");
		// Attempt to retrieve account from session
		Account acc = (Account) request.getSession().getAttribute(Keys.ACCOUNT);
				
		if (acc == null || acc.getType() != Account.ActorType.ADMIN){
			response.sendRedirect("/home");
			return;
		}
		if(action!=null){
			if(action.equals("load")){
				String subjectRequest = request.getParameter("subjectrequest");
				Key subjectRequestKey = KeyFactory.stringToKey(subjectRequest);
				if(subjectRequest!=null && subjectRequestKey!=null){
					request.setAttribute(Keys.CATEGORY_LIST, Category.getAllCategories());
					request.setAttribute(Keys.SUBJECT_REQUEST, RequestSubject.getFromKeyString(subjectRequest));
					request.getRequestDispatcher("/ApproveSubjectRequest.jsp").forward(request, response);
				
				}
				else{
					response.sendRedirect("/home");
				}
			}
			else if(action.equals("insert")){
				String subjectRequest = request.getParameter("subKey");
				String categoryKeyString = request.getParameter("categoryKey");
				RequestSubject insertSubject = RequestSubject.getFromKeyString(subjectRequest);
				Key categoryKey = KeyFactory.stringToKey(categoryKeyString);
				if(insertSubject!=null && categoryKey != null){
					ArrayList<RequestSubtopic> subtopicsToInsert = RequestSubtopic.getSubjectSubtopicsRequest(KeyFactory.stringToKey(subjectRequest));
					Subject newSubject = RequestSubject.insertSubjectRequest(insertSubject.getKey(), categoryKey);//insert the subject and delete the subject request entry
					for(RequestSubtopic checkIndividual : subtopicsToInsert){//insert or delete each subtopic
						String checkIfChecked = request.getParameter(checkIndividual.getKeyAsString());
						if(checkIfChecked!= null && checkIfChecked.equals("true")){
							RequestSubtopic.insertSubtopicRequest(checkIndividual.getKey(), newSubject.getKey() );
						}
						else{
							checkIndividual.delete();
						}
					}
					//RequestSubject.insertSubjectRequest(insertSubject.getKey(), categoryKey);
				}
				response.sendRedirect("/home");
			}
			else{
				response.sendRedirect("/home");
			}
		}else{
			response.sendRedirect("/home");
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
