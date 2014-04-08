package material;

import java.io.Serializable;
import java.util.ArrayList;

import javax.crypto.KeyGenerator;

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

public class Subtopic implements Serializable {
	private Entity subtopicEntity;
	private Subject sub;
	private static final String ENT_SUBTOPIC_TITLE = "subtopicTitle";
	private static final String ENT_SUBTOPIC_DESCRIPTION = "subtopicDescription";
	private static final String ENT_SUBTOPIC = "subtopic";
	private static final String ENT_SUBTOPIC_SUBJECT = "subject";
	
	private Subtopic(Entity sEntity){
		subtopicEntity = sEntity;
	}
	public static Subtopic getSubtopic(Key key){
		try {
			Entity subtopicE = DatastoreServiceFactory.getDatastoreService().get(key);
			return new Subtopic(subtopicE);
		} catch (EntityNotFoundException e) {
			return null;
		}
	}
	public static Subtopic getFromKeyString(String key) {
		return getSubtopic(KeyFactory.stringToKey(key));
	}
	
	public static Subtopic createSubtopic(String sTitle, Key subjectKey ,String sDescription){
		Entity subtopicE = new Entity(ENT_SUBTOPIC);
		Subtopic s = new Subtopic(subtopicE);
		s.setTitle(sTitle);
		s.setSubjectKey(subjectKey);
		s.setDescription(sDescription);
		s.saveSubtopic();
		return s;
	}
	private void setTitle(String subjectTitle){
		subtopicEntity.setProperty(ENT_SUBTOPIC_TITLE, subjectTitle);
	}
	private void setDescription(String subjectDescription){
		subtopicEntity.setProperty(ENT_SUBTOPIC_DESCRIPTION, subjectDescription);
	}
	private void setSubjectKey(Key k){
		subtopicEntity.setProperty(ENT_SUBTOPIC_SUBJECT, k);
	}
	public String getTitle(){
		return (String)subtopicEntity.getProperty(ENT_SUBTOPIC_TITLE);
	}
	public String getDescription(){
		return (String)subtopicEntity.getProperty(ENT_SUBTOPIC_DESCRIPTION);
	}
	public Key getKey(){
		return subtopicEntity.getKey();
	}
	public String getKeyAsString() {
		return KeyFactory.keyToString(getKey());
	}
	public void saveSubtopic(){
		DatastoreServiceFactory.getDatastoreService().put(subtopicEntity);
	}
	public void deleteSubtopic(){
		DatastoreServiceFactory.getDatastoreService().delete(subtopicEntity.getKey());
	}
	public static ArrayList<Subtopic> getSubtopics(Key sKey){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter subjectFilter = new FilterPredicate(ENT_SUBTOPIC_SUBJECT,
				   FilterOperator.EQUAL,
				   sKey);
		Query subtopicQuery = new Query(ENT_SUBTOPIC).addSort(ENT_SUBTOPIC_TITLE, SortDirection.ASCENDING).setFilter(subjectFilter); 
		PreparedQuery pq = datastore.prepare(subtopicQuery);
		ArrayList<Subtopic> subtopics = new ArrayList<Subtopic>();
		for (Entity result : pq.asIterable()) {
			Subtopic tempSubtopic = new Subtopic(result);
			subtopics.add(tempSubtopic);
		}
		return subtopics;
	}
	public Subject getSubject(){
		if(sub == null)
			sub = Subject.getSubject((Key)subtopicEntity.getProperty(ENT_SUBTOPIC_SUBJECT));
		return sub;
	}
}
