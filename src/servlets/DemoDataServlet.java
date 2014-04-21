package servlets;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import material.Notes;
import material.Subject;
import material.Subtopic;
import material.Video;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class DemoDataServlet extends HttpServlet{

	private static final long serialVersionUID = 4232287421153255825L;

	/**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        //PrintWriter out = response.getWriter();
        //System.out.println("Reading File from Java code");
        ////Name of the file
        String text=request.getParameter("text");
        String subtopicKey = request.getParameter("subtopicKey");
        String accountKey =request.getParameter("accountKey");
        String subjectKey = request.getParameter("subjectKey");
        String uploadDataType = request.getParameter("uploadDataType");
        if(uploadDataType.equals("subject")){
        	String [] line =text.split("\n");
    	    String [] attributes;
            int i = 0;
            while(i< line.length){
            	attributes=line[i].split(";");
            	for(int j = 0; j<attributes.length;j++){
            		attributes[j] = attributes[j].replaceAll(";", "");
            	}
            	Subject.insertDemoSubjects(attributes[0], attributes[1]);
            	i++;
            }
        }
        else if(uploadDataType.equals("subtopic")){
        	Key subKey = KeyFactory.stringToKey(subjectKey);
        	String [] line =text.split("\n");
    	    String [] attributes;
            int i = 0;
            while(i< line.length){
            	attributes=line[i].split(";");
            	for(int j = 0; j<attributes.length;j++){
            		attributes[j] = attributes[j].replaceAll(";", "");
            	}
            	Subtopic.createSubtopic(attributes[0], subKey, attributes[1],i);
            	i++;
            }
        }
        else if(uploadDataType.equals("video")){
        	Key sKey = KeyFactory.stringToKey(subtopicKey);
            Key aKey = KeyFactory.stringToKey(accountKey);
            Key subKey = KeyFactory.stringToKey(subjectKey);
            String [] line =text.split("\n");
    	    String [] attributes;
            int i = 0;
            while(i< line.length){
            	attributes=line[i].split(";");
            	for(int j = 0; j<attributes.length;j++){
            		attributes[j] = attributes[j].replaceAll(";", "");
            	}
            	Video.createVideo(attributes[0], attributes[1], attributes[2], sKey, aKey, subKey);
            	i++;
            }
        }
        else if(uploadDataType.equals("notes")){
        	Key sKey = KeyFactory.stringToKey(subtopicKey);
            Key aKey = KeyFactory.stringToKey(accountKey);
            Key subKey = KeyFactory.stringToKey(subjectKey);
            String [] line =text.split("\n");
    	    String [] attributes;
            int i = 0;
            while(i< line.length){
            	attributes=line[i].split(";");
            	for(int j = 0; j<attributes.length;j++){
            		attributes[j] = attributes[j].replaceAll(";", "");
            	}
            	Notes.createNotes(attributes[0], attributes[1], attributes[2], sKey, aKey, subKey);
            	i++;
            }
        }
        else if(uploadDataType.equals("question")){
        	/*Key sKey = KeyFactory.stringToKey(subtopicKey);
            Key aKey = KeyFactory.stringToKey(accountKey);*/
        }
        
	    
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
