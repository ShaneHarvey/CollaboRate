package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import material.RequestSubject;
import material.RequestSubtopic;
import account.Account;

import com.google.appengine.api.datastore.KeyFactory;

import constants.Keys;

public class DeleteSubjectRequestServlet extends HttpServlet {

	private static final long serialVersionUID = -3858981710259933086L;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
	
		String subjectRequest = request.getParameter("subKey");
		Account acc = (Account) request.getSession().getAttribute(Keys.ACCOUNT);
		
		if (acc == null || acc.getType() != Account.ActorType.ADMIN){
			response.sendRedirect("/home");
			return;
		}
		if(subjectRequest== null){
			response.sendRedirect("/home");
		}
		RequestSubject deleteSubject = RequestSubject.getFromKeyString(subjectRequest);
		if(deleteSubject!=null){
			ArrayList<RequestSubtopic> subtopicsToDelete = RequestSubtopic.getSubjectSubtopicsRequest(KeyFactory.stringToKey(subjectRequest));
			for(RequestSubtopic delteIndividual : subtopicsToDelete){//delete each subtopic
				delteIndividual.delete();
			}
			deleteSubject.delete();//delete the subject request
		}
		response.sendRedirect("/home");
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
