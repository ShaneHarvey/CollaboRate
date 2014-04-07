package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import constants.Keys;

import java.net.*;

import account.*;
import static constants.Keys.*;

public class LoginServlet extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
		String action = (String)request.getParameter("action");
		
		if(action == null){
			getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
		}
		else if(action.equals("login")){
			String email = URLDecoder.decode((String)request.getParameter("email"), "UTF-8");
			String password = URLDecoder.decode((String)request.getParameter("password"), "UTF-8");
			Account acc = Account.loadAccount(email);
			if(acc == null){
				response.getWriter().print("");
				return;
			}
			if(!acc.verifyPassword(password)) {
				response.getWriter().print("");
			}
			else{
				request.getSession().setAttribute(Keys.ACCOUNT, acc);
				response.getWriter().print("success");	
			}
		}
		else{
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
