package material;

import java.util.Date;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

import database.DBObject;

/**
 * Super class of Question, Video, and Lecture
 * 
 * @author Phil
 * 
 */
public abstract class Material extends DBObject {

	private static final long serialVersionUID = -7741052799916096321L;

	private Subtopic subtopic;
	public static final String MATERIAL_DATE = "Date Created";
	public static final String MATERIAL_AUTHOR = "Author";
	public static final String MATERIAL_TITLE = "title";
	public static final String MATERIAL_SUBTOPIC = "subtopic";
	public static final String MATERIAL_SUBJECT = "subject";
	public static final String MATERIAL_URL = "url";
	public static final String MATERIAL_DESCRIPTION = "description";

	protected Material(Entity e) {
		super(e);
	}

	public void setAuthor(Key author) {
		entity.setProperty(MATERIAL_AUTHOR, author);
	}

	protected void setTitle(String title) {
		entity.setProperty(MATERIAL_TITLE, title);
	}

	protected void setSubtopicKey(Key subtopicKey) {
		entity.setProperty(MATERIAL_SUBTOPIC, subtopicKey);
	}
	
	protected void setURL(String url) {
		entity.setProperty(MATERIAL_URL, url);
	}
	
	protected void setDescription(String description) {
		entity.setProperty(MATERIAL_DESCRIPTION, description);
	}
	protected void setSubject(Key subjectKey){
		entity.setProperty(MATERIAL_SUBJECT, subjectKey);
	}
	/**
	 * Sets the Discussion Entry to the current date
	 */
	protected void setDate(){
		Date currentDate = new Date();
		entity.setProperty(MATERIAL_DATE, currentDate);
	}
	public Date getDate(){
		return (Date)entity.getProperty(MATERIAL_DATE);
	}
	public Key getSubject(){
		return (Key)entity.getProperty(MATERIAL_SUBJECT);
	}
	public Key getAuthor() {
		return (Key) entity.getProperty(MATERIAL_AUTHOR);
	}

	public String getTitle() {
		return (String) entity.getProperty(MATERIAL_TITLE);
	}
	
	public String getURL() {
		return (String) entity.getProperty(MATERIAL_URL);
	}
	
	public String getDescription(){
		return (String) entity.getProperty(MATERIAL_DESCRIPTION);
	}

	public String getShortTitle() {
		return (String) entity.getProperty(MATERIAL_TITLE);
	}

	public Key getSubtopicKey() {
		return (Key) entity.getProperty(MATERIAL_SUBTOPIC);
	}

	public double getRating(){
		return MaterialMetadata.getRating(entity.getKey());
	}
		
	public int getUserRating(Key uID){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter userIDFilter =
				new FilterPredicate(UserMaterialMetadata.USERID,
				   FilterOperator.EQUAL,
				   uID);
		Query q = new Query(UserMaterialMetadata.USER_METADATA).setFilter(userIDFilter);
		try{
			PreparedQuery pq = datastore.prepare(q);
			Entity ent= pq.asSingleEntity();
			if(ent != null){
				return (int)ent.getProperty(UserMaterialMetadata.MATERIAL_RATING);
			}else {
				return -1;
			}
		} catch(PreparedQuery.TooManyResultsException e){
			e.printStackTrace();
			return -1;
		}
	}
	
	public boolean getUserFlagged(Key uID){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter userIDFilter =
				new FilterPredicate(UserMaterialMetadata.USERID,
				   FilterOperator.EQUAL,
				   uID);
		Query q = new Query(UserMaterialMetadata.USER_METADATA).setFilter(userIDFilter);
		try{
			PreparedQuery pq = datastore.prepare(q);
			Entity ent= pq.asSingleEntity();
			if(ent != null){
				return (boolean)ent.getProperty(UserMaterialMetadata.MATERIAL_FLAGGED);
			}else {
				return false;
			}
		} catch(PreparedQuery.TooManyResultsException e){
			e.printStackTrace();
			return false;
		}
	}

	public Subtopic getSubtopic() {
		if(subtopic == null)
			subtopic = Subtopic.getSubtopic((Key) entity.getProperty(MATERIAL_SUBTOPIC));
		return subtopic;
	}
	
	/*
	 * Return the average global rating of this material
	 */
	public int getGlobalRating(){
		return MaterialMetadata.getRating(entity.getKey());
	}
	
	@Override
	public void delete() {
		deleteAllMetadata();
		super.delete();
	}

	private void deleteAllMetadata() {
		// Delete all metadata for this material
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Filter materialIdFilter = new FilterPredicate(
				UserMaterialMetadata.MATERIALID, FilterOperator.EQUAL,
				entity.getKey());

		Query q = new Query(UserMaterialMetadata.USER_METADATA).setFilter(materialIdFilter);
		PreparedQuery pq = datastore.prepare(q);
		for (Entity ent : pq.asIterable()) {
			DatastoreServiceFactory.getDatastoreService().delete(ent.getKey());
		}
	}
	
	/**
	 *  Unflag all metadata for this material 
	 */
	public void unflag() {
		// Delete all metadata for this material
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Filter materialIdFilter = new FilterPredicate(UserMaterialMetadata.MATERIALID,
				FilterOperator.EQUAL, entity.getKey());

		Query q = new Query(UserMaterialMetadata.USER_METADATA).setFilter(materialIdFilter);
		PreparedQuery pq = datastore.prepare(q);
		for (Entity ent : pq.asIterable()) {
			ent.setProperty(UserMaterialMetadata.MATERIAL_FLAGGED, false);
			DatastoreServiceFactory.getDatastoreService().put(ent);
		}
	}
}
