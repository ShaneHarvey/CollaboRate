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
	public RequestSubtopic(Entity ent){
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
	public static ArrayList<RequestSubtopic> getSubtopicsRequest(Key sKey) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Filter subjectFilter = new FilterPredicate(ENT_SUBTOPIC_SUBJECT,
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
	public static boolean insertSubtopicRequest(Key subReqKey){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Filter subjectFilter = new FilterPredicate(ENT_SUBTOPIC_SUBJECT,
				FilterOperator.EQUAL, subReqKey);
		Query subtopicRequestQuery = new Query(ENT_SUBTOPIC_REQUEST).setFilter(
				subjectFilter);
		try{
			PreparedQuery pq = datastore.prepare(subtopicRequestQuery);
			Entity request = pq.asSingleEntity();
			if(request != null){
				RequestSubtopic s = new RequestSubtopic(request);
				Subtopic newSubtopic = Subtopic.createSubtopic(s.getTitle(), s.getSubjectKey(), s.getDescription(), s.getOrder());
				newSubtopic.save();
				return true;
			}
			return false;
		} catch(PreparedQuery.TooManyResultsException e){
			e.printStackTrace();
			return false;
		}
	}
}
