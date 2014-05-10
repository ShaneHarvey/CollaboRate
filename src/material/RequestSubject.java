package material;

import java.io.Serializable;
import java.util.ArrayList;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;

public class RequestSubject extends Subject implements Serializable{
	private static final long serialVersionUID = -8250505173208208901L;
	private static final String ENT_SUBJECT_REQUEST = "subject_request";
	private ArrayList<RequestSubtopic> subtopicsRequestList;
	public RequestSubject(Entity ent){
		super(ent);
	}
	public static RequestSubject getSubjectRequest(Key key) {
		try {
			Entity reqSubjectE = DatastoreServiceFactory.getDatastoreService()
					.get(key);
			return new RequestSubject(reqSubjectE);
		} catch (EntityNotFoundException e) {
			return null;
		}
	}
	public static RequestSubject getFromKeyString(String key) {
		return getSubjectRequest(KeyFactory.stringToKey(key));
	}
	/**
	 * Returns a ArrayList of all the associated subtopics for a requested subject
	 * @return
	 */
	public ArrayList<RequestSubtopic> getSubtopicRequests(){
		if(subtopicsRequestList == null)
			subtopicsRequestList = RequestSubtopic.getSubjectSubtopicsRequest(entity.getKey());
		return subtopicsRequestList;
	}
	/**
	 * Creates a new entity that represents a new subtopic request
	 * @param sTitle - Title of the requested subject
	 * @param sDescription - Description of the requested subject
	 * @param subtopicsRequests - String array of the requested subjects subtopics
	 * @return - the RequetSubject object
	 */
	public static RequestSubject createSubjectRequest(String sTitle, String sDescription, String[] subtopicsRequests){
		Entity subjectE = new Entity(ENT_SUBJECT_REQUEST);
		//TODO check to make sure sTitle does not match a Subject title in the datastore
		RequestSubject s = new RequestSubject(subjectE);
		s.setTitle(sTitle);
		s.setDescription(sDescription);
		s.save();
		long order = 0;
		for(String st: subtopicsRequests) {
			RequestSubtopic.createSubjectSubtopicRequest(st, s.getKey(), st, order);
			order += 100;
		}
		return s;
	}
	/**
	 * Generates an array list of all the subjects that have been requested by users
	 * @return - an array list containing RequestSubject objects that will be displayed in the UI
	 */
	public static ArrayList<RequestSubject> getSubjectRequests(){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query subjectQuery = new Query(ENT_SUBJECT_REQUEST).addSort(
				ENT_SUBJECT_TITLE, SortDirection.DESCENDING);
		PreparedQuery pq = datastore.prepare(subjectQuery);
		ArrayList<RequestSubject> reqSubjects = new ArrayList<RequestSubject>();
		for (Entity result : pq.asIterable()) {
			RequestSubject tempSubject = new RequestSubject(result);
			reqSubjects.add(tempSubject);
		}
		return reqSubjects;
	}
	/**
	 * Inserts a Requested subject into the Subject table and all the associated subtopics for it.
	 * @param subjectReqKey The key of the Requested Subject to insert
	 * @return Return true if the insertion was successful or false if it failed
	 */
	public static Subject insertSubjectRequest(Key subjectReqKey, Key cKey){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Entity subRequest;
		try {
			subRequest = datastore.get(subjectReqKey);
			RequestSubject req = new RequestSubject(subRequest);
			Subject newSubject = Subject.createSubject(req.getTitle(), req.getDescription(), cKey);
			
			datastore.delete(subjectReqKey);//delete the Subject Request after it has been inserted
			return newSubject;
		} catch (EntityNotFoundException e) {
			return null;
		}
		
	}
}
