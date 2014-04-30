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
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;

public class RequestSubtopic extends Subtopic implements Serializable {
	private static final long serialVersionUID = -8250505173208208901L;
	private static final String ENT_SUBTOPIC_REQUEST = "subtopic_request";
	private static final String ENT_SUBJECT_SUBTOPIC_REQUEST = "subject_subtopic_request";
	private RequestSubtopic(Entity ent){
		super(ent);
	}
	public static RequestSubtopic getSubtopicRequest(Key key) {
		try {
			Entity reqSubtopicE = DatastoreServiceFactory.getDatastoreService()
					.get(key);
			return new RequestSubtopic(reqSubtopicE);
		} catch (EntityNotFoundException e) {
			return null;
		}
	}
	public static RequestSubtopic getFromKeyString(String key) {
		return getSubtopicRequest(KeyFactory.stringToKey(key));
	}
	/**
	 * Creates a new subtopic request for an already present subject in the datastore 
	 * @param sTitle The title of the requested subtopic
	 * @param subjectKey the Key of the subject 
	 * @param sDescription the description of the requested subtopic
	 * @param order The order where the requested subject will be placed
	 * @return the Requested Subtopic object 
	 */
	public static RequestSubtopic createSubtopicRequest(String sTitle, Key subjectKey,
			String sDescription, long order) {
		//TODO Make sure that sTitle does not match any of the subtopics present in the data store//Dont want dupilicat
		Entity reqSubtopicE = new Entity(ENT_SUBTOPIC_REQUEST);
		RequestSubtopic s = new RequestSubtopic(reqSubtopicE);
		s.setTitle(sTitle);
		s.setSubjectKey(subjectKey);
		s.setDescription(sDescription);
		s.setOrder(order);	
		s.save();
		return s;
	}
	/**
	 * Creates a sutopic request for a requested subject. The subject is not already present in the datastore
	 * @param sTitle The title of the subtopic
	 * @param subjectKey The key of the requested subject
	 * @param sDescription The description of the subtopic
	 * @param order The order of the subtopic
	 * @return the Requested Subject object
	 */
	public static RequestSubtopic createSubjectSubtopicRequest(String sTitle, Key subjectKey, String sDescription, long order){
		//TODO Make sure that sTitle does not match any of the subtopics present in the data store//Dont want dupilicat
		Entity reqSubtopicE = new Entity(ENT_SUBJECT_SUBTOPIC_REQUEST);
		RequestSubtopic s = new RequestSubtopic(reqSubtopicE);
		s.setTitle(sTitle);
		s.setSubjectKey(subjectKey);
		s.setDescription(sDescription);
		s.setOrder(order);	
		s.save();
		return s;
	}
	/**
	 * Gets the requested subtopics for a given subject Key. This method is called by insert requested subject to also insert the subtopics assocaiate with it
	 * @param sKey the requestedSubject Key
	 * @return The array list of Requested Subtopic objects.
	 */
	public static ArrayList<RequestSubtopic> getSubjectSubtopicsRequest(Key sKey) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Filter subjectFilter = new FilterPredicate(ENT_SUBTOPIC_SUBJECT,
				FilterOperator.EQUAL, sKey);
		Query subtopicQuery = new Query(ENT_SUBJECT_SUBTOPIC_REQUEST).addSort(
				ENT_SUBTOPIC_ORDER, SortDirection.ASCENDING).setFilter(
				subjectFilter);
		PreparedQuery pq = datastore.prepare(subtopicQuery);
		ArrayList<RequestSubtopic> reqSubtopics = new ArrayList<RequestSubtopic>();
		for (Entity result : pq.asIterable()) {
			RequestSubtopic tempSubtopic = new RequestSubtopic(result);
			reqSubtopics.add(tempSubtopic);
		}
		return reqSubtopics;
	}
	
	
	/**
	 * Gets the requested subtopics for a given subject Key. This method is called by insert requested subject to also insert the subtopics assocaiate with it
	 * @param sKey the requestedSubject Key
	 * @return The array list of Requested Subtopic objects.
	 */
	public static ArrayList<RequestSubtopic> getSubjectSubtopicsRequest(String sKey) {
		return getSubjectSubtopicsRequest(KeyFactory.stringToKey(sKey));
	}
	
	
	/**
	 * Gets the requested subtopics. Different from the subject subtopic requests.
	 * @return An array list of RequestedSubtopic objects
	 */
	public static ArrayList<RequestSubtopic> getSubtopicRequests(){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query subtopicQuery = new Query(ENT_SUBTOPIC_REQUEST).addSort(ENT_SUBTOPIC_TITLE);
		PreparedQuery pq = datastore.prepare(subtopicQuery);
		ArrayList<RequestSubtopic> reqSubtopics = new ArrayList<RequestSubtopic>();
		for(Entity result: pq.asIterable()){
			RequestSubtopic tempSubtopic = new RequestSubtopic(result);
			reqSubtopics.add(tempSubtopic);
		}
		return reqSubtopics;
	}
	/**
	 * Inserts the subtopic request into the Subtopic index in the datastore
	 * @param subReqKey The Requested Subtopic key to enter into the database
	 * @return returns true if the insertion was successful 
	 */
	public static boolean insertSubtopicRequest(Key subReqKey){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		try {
			Entity subtopicRequest = datastore.get(subReqKey);
			RequestSubtopic newRequestedSubtopic = new RequestSubtopic(subtopicRequest);
			Subtopic newSubtopic = Subtopic.createSubtopic(newRequestedSubtopic.getTitle(), newRequestedSubtopic.getSubjectKey(), newRequestedSubtopic.getDescription(), newRequestedSubtopic.getOrder());
			newSubtopic.save();
			datastore.delete(subReqKey);//deletes the subject 
			return true;	
		} catch (EntityNotFoundException e) {
			return false;
		}
	}
	
	
	/**
	 * Gets the requested subtopics for a given subject Key. This method is called by insert requested subject to also insert the subtopics assocaiate with it
	 * @param sKey the requestedSubject Key
	 * @return The array list of Requested Subtopic objects.
	 */
	public static ArrayList<RequestSubtopic> getSubtopicsRequestfromSubject(Key sKey) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Filter subjectFilter = new FilterPredicate("subject",
				FilterOperator.EQUAL, sKey);
		Query subtopicQuery = new Query(ENT_SUBTOPIC_REQUEST).addSort(
				ENT_SUBTOPIC_ORDER, SortDirection.ASCENDING).setFilter(
				subjectFilter);
		PreparedQuery pq = datastore.prepare(subtopicQuery);
		ArrayList<RequestSubtopic> reqSubtopics = new ArrayList<RequestSubtopic>();
		for (Entity result : pq.asIterable()) {
			RequestSubtopic tempSubtopic = new RequestSubtopic(result);
			reqSubtopics.add(tempSubtopic);
		}
		return reqSubtopics;
	}
	
	
	/**
	 * Gets the requested subtopics for a given subject Key. This method is called by insert requested subject to also insert the subtopics assocaiate with it
	 * @param sKey the requestedSubject Key
	 * @return The array list of Requested Subtopic objects.
	 */
	public static ArrayList<RequestSubtopic> getSubtopicsRequestfromSubject(String sKey) {
		return getSubtopicsRequestfromSubject(KeyFactory.stringToKey(sKey));
	}
	
	
}//class
