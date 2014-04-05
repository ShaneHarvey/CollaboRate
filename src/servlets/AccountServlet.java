package servlets;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import constants.Keys;
import account.Account;

public class AccountServlet extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
		String action = (String)request.getParameter("action");
		
		if(action == null){
			// If user isn't logged in, redirect to home
			if(request.getSession().getAttribute(Keys.ACCOUNT) == null) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.sendRedirect("/home");
			}
			else
				getServletContext().getRequestDispatcher("/my-account.jsp").forward(request, response);
		}
		else if(action.equals("updateaccount")){
			// Update user's account
			String displayName = URLDecoder.decode((String)request.getParameter("displayName"), "UTF-8");
			String currentPassword = URLDecoder.decode((String)request.getParameter("currentPassword"), "UTF-8");
			String newPassword = URLDecoder.decode((String)request.getParameter("newPassword"), "UTF-8");
			// Attempt to retrieve account from session
			Account acc = (Account)request.getSession().getAttribute(Keys.ACCOUNT);
			// Make sure user entered correct password
			if(!acc.verifyPassword(currentPassword)) {
				response.getWriter().print("");
				return;
			}
			if(displayName != null && !"".equals(displayName))
				acc.setDisplayName(displayName);
			if(newPassword != null && !"".equals(newPassword))
				acc.changePassword(currentPassword, newPassword);
			if(acc.updateDB())
				response.getWriter().print("success");
			else
				response.getWriter().print("");
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
