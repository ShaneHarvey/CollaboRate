package material;

import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class Subtopic {
	private Entity subtopicEntity;
	private static final String ENT_SUBTOPIC_TITLE = "subtopicTitle";
	private static final String ENT_SUBTOPIC_DESCRIPTION ="subtopicDescription";
	private static final String ENT_SUBTOPIC ="subtopic";
	private static final String ENT_SUBTOPIC_SUBJECT = "subject";
	private Subtopic(Entity sEntity){
		subtopicEntity = sEntity;
	}
	public static Subtopic getSubject(String subtopicKey){
		Key sKey = KeyFactory.stringToKey(subtopicKey);
		try {
			Entity subtopicE = DatastoreServiceFactory.getDatastoreService().get(sKey);
			return new Subtopic(subtopicE);
		} catch (EntityNotFoundException e) {
			return null;
		}
	}
	public static Subtopic createSubtopic(String sTitle, String sKey ,String sDescription){
		Entity subtopicE = new Entity(ENT_SUBTOPIC);
		Subtopic s = new Subtopic(subtopicE);
		s.setTitle(sTitle);
		s.setSubjectKey(sKey);
		s.setDescription(sDescription);
		return s;
	}
	private void setTitle(String subjectTitle){
		subtopicEntity.setProperty(ENT_SUBTOPIC_TITLE, subjectTitle);
	}
	private void setDescription(String subjectDescription){
		subtopicEntity.setProperty(ENT_SUBTOPIC_DESCRIPTION, subjectDescription);
	}
	private void setSubjectKey(String skey){
		subtopicEntity.setProperty(ENT_SUBTOPIC_SUBJECT, KeyFactory.stringToKey(skey));
	}
	public String getTitle(){
		return (String)subtopicEntity.getProperty(ENT_SUBTOPIC_TITLE);
	}
	public String getDescription(){
		return (String)subtopicEntity.getProperty(ENT_SUBTOPIC_DESCRIPTION);
	}
	public String getSubtopicKey(){
		return KeyFactory.keyToString(subtopicEntity.getKey());
	}
	public String getSubjectKey(){
		return KeyFactory.keyToString(subtopicEntity.getKey());
	}
	public void saveSubtopic(){
		DatastoreServiceFactory.getDatastoreService().put(subtopicEntity);
	}
	public void deleteSubtopic(){
		DatastoreServiceFactory.getDatastoreService().delete(subtopicEntity.getKey());
	}
	
}
