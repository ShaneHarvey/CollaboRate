package Servlets;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import settings.Settings;
import account.Account;

public class UserAccountServlet extends HttpServlet{
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
		String action = (String)request.getParameter("action");
		
		if(action == null || request.getParameter("email") == null || request.getParameter("password") == null){
			getServletContext().getRequestDispatcher("/my-account.jsp").forward(request, response);
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
