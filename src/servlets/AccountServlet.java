package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
