package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import constants.Keys;
import account.*;

public class LoginServlet extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
		String action = request.getParameter("action");
		
		if(action == null){
			getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
		}
		else if(action.equals("login")){
			String email = request.getParameter("email");
			String password = request.getParameter("password");
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
