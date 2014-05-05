package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import account.Account;
import material.Subject;
import material.Subtopic;
import material.Video;
import material.VideoMetadata;
import constants.Keys;

public class VideoServlet extends HttpServlet {

	private static final long serialVersionUID = 1593485103847102005L;

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		String action = request.getParameter("action");
		Account user = (Account) request.getSession()
				.getAttribute(Keys.ACCOUNT);
		if (action == null) {
			String vID = request.getParameter(Keys.VIDEO_KEY);
			if (vID == null) {
				// If no subjectId, redirect to home
				response.sendRedirect("/home");
			} 
			else {
				// Get subtopic and place is request
				Video vid = Video.getFromKeyString(vID);
				request.setAttribute(Keys.VIDEO, vid);
				Subtopic st = vid.getSubtopic();
				request.setAttribute(Keys.SUBTOPIC, st);
				Subject sub = st.getSubject();
				request.setAttribute(Keys.SUBJECT, sub);
				// If user is logged in, put meta data for video in session
				if (user != null) {
					VideoMetadata data = VideoMetadata
							.getVideoMetadata(user.getKey(),
									vid);
					
					if(data == null){
						VideoMetadata newData = VideoMetadata.createVideoMetadata(user.getKey(), vid);
						request.setAttribute(Keys.META_DATA, data);
					}
					else{
						request.setAttribute(Keys.META_DATA, data);
					}
				}

				getServletContext().getRequestDispatcher("/video.jsp").forward(
						request, response);
			}
		} 
		else {
			if ("ratecontent".equals(action)) {
				// If not logged in can't rate
				if (user == null)
					return;
				String vID = request.getParameter(Keys.CONTENT_KEY);
				//Key videoKey = KeyFactory.stringToKey(vID);
				int rating = Integer.parseInt(request.getParameter("rating"));
				VideoMetadata a = VideoMetadata
						.getVideoMetadata(user.getKey(), Video.getFromKeyString(vID));
				if (a == null)
					a = VideoMetadata.createVideoMetadata(
							user.getKey(), Video.getFromKeyString(vID));
				a.setMaterialRating(rating);
				a.save();
			} else if ("flagcontent".equals(action)) {
				// If not logged in can't flag
				if (user == null)
					return;
				String vID = request.getParameter(Keys.CONTENT_KEY);
				//Key videoKey = KeyFactory.stringToKey(vID);
				VideoMetadata a = VideoMetadata
						.getVideoMetadata(user.getKey(), Video.getFromKeyString(vID));

				boolean flag = "true".equals(request.getParameter("flag"));
				if (a == null)
					a = VideoMetadata.createVideoMetadata(
							user.getKey(), Video.getFromKeyString(vID));
				a.setFlagged(flag);
				a.save();
			}
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