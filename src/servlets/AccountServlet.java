package servlets;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import account.Account;
import settings.Settings;

public class AccountServlet extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
		String action = (String)request.getParameter("action");
		
		if(action == null){
			// If user isn't logged in, redirect to home
			if(request.getSession().getAttribute(Settings.ACCOUNT) == null) {
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
			//String currentPassword = URLDecoder.decode((String)request.getParameter("currentPassword"), "UTF-8");
			// Attempt to retrieve account from session
			Account acc = (Account)request.getSession().getAttribute(Settings.ACCOUNT);
			Account check = Account.updateAccount(acc.getEmail(),displayName ,currentPassword, newPassword);
			if(check == null){
				response.getWriter().print("");
			}
			else{
				request.getSession().setAttribute(Settings.ACCOUNT, check);
				response.getWriter().print("success");
				
			}
			
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
