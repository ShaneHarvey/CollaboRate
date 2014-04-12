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

import database.DBObject;


public class Subject extends DBObject implements Serializable{

	private static final long serialVersionUID = 5487744678389202343L;
	//private Entity subjectEntity;
	private static final String ENT_SUBJECT_TITLE = "subjectTitle";
	private static final String ENT_SUBJECT_DESCRIPTION ="subjectDescription";
	private static final String ENT_SUBJECT ="subject";
	private ArrayList<Subtopic> subtopicsList;
	/**
	 * Constructor for the Subject class. Sets the subjectEntity to the passed entity
	 * @param sEntity
	 */
	private Subject(Entity ent){
		super(ent);
	}
	/**
	 * Gets a subtopic from a given key
	 * @param subjectKey string representation of the subject entity key
	 * @return the Subject object
	 */
	public static Subject getSubject(Key sKey){
		try {
			Entity subjectE = DatastoreServiceFactory.getDatastoreService().get(sKey);
			return new Subject(subjectE);
		} catch (EntityNotFoundException e) {
			return null;
		}
	}
	/**
	 * Given the string representation of a subject key, return the subject
	 * @param sKey the string representation of the key
	 * @return The subject with this key
	 */
	public static Subject getFromKeyString(String sKey) {
		return getSubject(KeyFactory.stringToKey(sKey));
	}
	
	/**
	 * Create a new subject. 
	 * @param sTitle The title of the subject
	 * @param sDescription the description of the subject
	 * @return the Subject object
	 */
	public static Subject createSubject(String sTitle, String sDescription, String[] subtopics){
		Entity subjectE = new Entity(ENT_SUBJECT);
		Subject s = new Subject(subjectE);
		s.setTitle(sTitle);
		s.setDescription(sDescription);
		s.save();
		long order = 0;
		for(String st: subtopics) {
			Subtopic.createSubtopic(st, s.getKey(), st, order);
			order += 100;
		}
		return s;
	}
	private void setTitle(String subjectTitle){
		entity.setProperty(ENT_SUBJECT_TITLE, subjectTitle);
	}
	private void setDescription(String subjectDescription){
		entity.setProperty(ENT_SUBJECT_DESCRIPTION, subjectDescription);
	}
	public String getTitle(){
		return (String)entity.getProperty(ENT_SUBJECT_TITLE);
	}
	public String getDescription(){
		return (String)entity.getProperty(ENT_SUBJECT_DESCRIPTION);
	}
	public static ArrayList<Subject> getSubjects(){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query photoQuery = new Query(ENT_SUBJECT).addSort(ENT_SUBJECT_TITLE, SortDirection.ASCENDING);  
		PreparedQuery pq = datastore.prepare(photoQuery);
		ArrayList<Subject> subjects = new ArrayList<Subject>();
		for (Entity result : pq.asIterable()) {
			Subject tempSubject = new Subject(result);
			subjects.add(tempSubject);
		}
		return subjects;
	}
	/**
	 * Get a list of all of the subtopics under this subject
	 * @return An array of all subtopics
	 */
	public ArrayList<Subtopic> getSubtopics(){
		if(subtopicsList == null)
			subtopicsList = Subtopic.getSubtopics(entity.getKey());
		return subtopicsList;
	}
	public static void insertDemoSubjects(String sTitle, String sDescription){
		Entity subjectE = new Entity(ENT_SUBJECT);
		Subject s = new Subject(subjectE);
		s.setTitle(sTitle);
		s.setDescription(sDescription);
		s.save();
	}
}
