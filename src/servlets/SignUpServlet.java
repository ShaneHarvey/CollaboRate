package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import constants.Keys;
import account.Account;

public class SignUpServlet  extends HttpServlet {

	private static final long serialVersionUID = 4210020416380902696L;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
		String action = request.getParameter("action");

		if(action == null || request.getParameter("email") == null || request.getParameter("password") == null){
			getServletContext().getRequestDispatcher("/signup.jsp").forward(request, response);
		}
		else if(action.equals("createaccount")){
			String email = request.getParameter("email");
			String password = request.getParameter("password");
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
