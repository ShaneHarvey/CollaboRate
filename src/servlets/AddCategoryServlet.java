package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import material.Category;
import constants.Keys;
import account.Account;
public class AddCategoryServlet extends HttpServlet {

	private static final long serialVersionUID = 1019307870758244635L;

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		// See if this is a page request or ajax call
		String action = (String) request.getParameter("action");
		// Attempt to retrieve account from session
		Account user = (Account) request.getSession().getAttribute(Keys.ACCOUNT);
		if (user == null) {
			// If no user, redirect to home
			response.sendRedirect("/home");
			return;
		}
		if (action.equals("addcategory")){
				String categoryName = request.getParameter("categoryName");
				// Try to create the subject and subtopics
				try {
					Category.createCategory(categoryName);
					response.getWriter().print("success");
				} catch (Exception e) {
					e.printStackTrace();
					response.getWriter().print("");
				}
			//response.sendRedirect("/home");
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