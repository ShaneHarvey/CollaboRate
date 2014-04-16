package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import account.Account;
import material.UserMaterialMetadata;
import constants.Keys;

public class NotesServlet extends HttpServlet {

	private static final long serialVersionUID = 1533369119236558515L;

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		String action = request.getParameter("action");
		if (action != null) {
			Account user = (Account) request.getSession().getAttribute(
					Keys.ACCOUNT);
			if ("ratecontent".equals(action)) {
				// If not logged in can't rate
				if (user == null)
					return;
				String nID = request.getParameter(Keys.CONTENT_KEY);
				Key notesKey = KeyFactory.stringToKey(nID);
				int rating = Integer.parseInt(request.getParameter("rating"));
				UserMaterialMetadata a = UserMaterialMetadata
						.getUserMaterialMetadata(user.getKey(), notesKey);
				if (a == null)
					a = UserMaterialMetadata.createUserMaterialMetadata(
							user.getKey(), notesKey);
				a.setMaterialRating(rating);
				a.save();
			} 
			else if ("flagcontent".equals(action)) {
				// If not logged in can't flag
				if (user == null)
					return;
				String nID = request.getParameter(Keys.CONTENT_KEY);
				Key notesKey = KeyFactory.stringToKey(nID);
				UserMaterialMetadata a = UserMaterialMetadata
						.getUserMaterialMetadata(user.getKey(), notesKey);

				boolean flag = "true".equals(request.getParameter("flag"));
				if (a == null)
					a = UserMaterialMetadata.createUserMaterialMetadata(
							user.getKey(), notesKey);
				a.setFlagged(flag);
				a.save();
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