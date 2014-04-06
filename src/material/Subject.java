package material;

import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class Subject {
	private Entity subjectEntity;
	private static final String ENT_SUBJECT_TITLE = "subjectTitle";
	private static final String ENT_SUBJECT_DESCRIPTION ="subjectDescription";
	private static final String ENT_SUBJECT ="subject";
	/**
	 * Constructor for the Subject class. Sets the subjectEntity to the passed entity
	 * @param sEntity
	 */
	private Subject(Entity sEntity){
		subjectEntity = sEntity;
	}
	/**
	 * Gets a subtopic from a given key
	 * @param subjectKey string representation of the subject entity key
	 * @return the Subject object
	 */
	public static Subject getSubject(String subjectKey){
		Key sKey = KeyFactory.stringToKey(subjectKey);
		try {
			Entity subjectE = DatastoreServiceFactory.getDatastoreService().get(sKey);
			return new Subject(subjectE);
		} catch (EntityNotFoundException e) {
			return null;
		}
	}
	/**
	 * Create a new subject. 
	 * @param sTitle The title of the subject
	 * @param sDescription the description of the subject
	 * @return the Subject object
	 */
	public static Subject createSubject(String sTitle, String sDescription){
		Entity subjectE = new Entity(ENT_SUBJECT);
		Subject s = new Subject(subjectE);
		s.setTitle(sTitle);
		s.setDescription(sDescription);
		s.saveSubject();
		return s;
	}
	private void setTitle(String subjectTitle){
		subjectEntity.setProperty(ENT_SUBJECT_TITLE, subjectTitle);
	}
	private void setDescription(String subjectDescription){
		subjectEntity.setProperty(ENT_SUBJECT_DESCRIPTION, subjectDescription);
	}
	public String getTitle(){
		return (String)subjectEntity.getProperty(ENT_SUBJECT_TITLE);
	}
	public String getDescription(){
		return (String)subjectEntity.getProperty(ENT_SUBJECT_DESCRIPTION);
	}
	public String getSubjectKey(){
		return KeyFactory.keyToString(subjectEntity.getKey());
	}
	public void saveSubject(){
		DatastoreServiceFactory.getDatastoreService().put(subjectEntity);
	}
	public void deleteSubject(){
		DatastoreServiceFactory.getDatastoreService().delete(subjectEntity.getKey());
	}
}
