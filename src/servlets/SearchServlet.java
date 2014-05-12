package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import search.SearchResult;
import search.SearchService;
import account.Account;
import constants.Keys;
import discussion_board.Post;

public class SearchServlet  extends HttpServlet {
	
	private static final long serialVersionUID = 2282144151069028631L;
	
	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		
		String query = request.getParameter("q");
		if(query != null){
			// DisplayTag sort option
			String pagesort = (String) request.getSession().getAttribute("d-49653-s");
			if(pagesort == null){
				ArrayList<SearchResult> results = SearchService.searchPosts(query);
				// Use Session so that the DisplayTag library can handle sorting
				request.getSession().setAttribute("results", results);
			}
		} 
		getServletContext().getRequestDispatcher("/search.jsp").forward(request, response);
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
