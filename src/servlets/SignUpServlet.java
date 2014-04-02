package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import constants.Keys;
import account.Account;

public class SignUpServlet  extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
		String action = (String)request.getParameter("action");

		if(action == null || request.getParameter("email") == null || request.getParameter("password") == null){
			getServletContext().getRequestDispatcher("/signup.jsp").forward(request, response);
		}
		else if(action.equals("createaccount")){
			String email = URLDecoder.decode((String)request.getParameter("email"), "UTF-8");
			String password = URLDecoder.decode((String)request.getParameter("password"), "UTF-8");
			Account check = Account.createUserAccount(email, password);
			if( check == null){
				response.getWriter().print("");
			}
			else{
				request.getSession().setAttribute(Keys.ACCOUNT, check);
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
