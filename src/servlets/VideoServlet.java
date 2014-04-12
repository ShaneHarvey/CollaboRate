package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.KeyFactory;

import account.Account;
import material.Subject;
import material.Subtopic;
import material.UserMaterialMetadata;
import material.Video;
import constants.Keys;

public class VideoServlet extends HttpServlet {

	private static final long serialVersionUID = 1593485103847102005L;

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		String vID = request.getParameter(Keys.VIDEO_KEY);
		Account user = (Account) request.getSession().getAttribute(Keys.ACCOUNT);
		if (vID == null) {
			// If no subjectId, redirect to home
			response.sendRedirect("/home");
		} else {
			// Get subtopic and place is request
			Video vid = Video.getFromKeyString(vID);
			request.setAttribute(Keys.VIDEO, vid);
			Subtopic st = vid.getSubtopic();
			request.setAttribute(Keys.SUBTOPIC, st);
			Subject sub = st.getSubject();
			request.setAttribute(Keys.SUBJECT, sub);
			//Set the viewed or make a new viewed attribute in the Metadata table
			UserMaterialMetadata a = UserMaterialMetadata.getUserMaterialMetadata(user.getKey(), KeyFactory.stringToKey(vID));
			if(a!= null){
				a.setViewed();
				a.save();
			}
			else{
				UserMaterialMetadata newRow = UserMaterialMetadata.createUserMaterialMetadata(user.getKey(), KeyFactory.stringToKey(vID));
				newRow.setViewed();
				newRow.save();
			}

			getServletContext().getRequestDispatcher("/video.jsp").forward(
					request, response);
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