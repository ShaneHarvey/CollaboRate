package material;

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
	public static final String MATERIAL_RATING = "Rating";
	public static final String MATERIAL_RATING_COUNT = "Rating_Count";
	public static final String MATERIAL_FLAGGED_COUNT = "Flagged_Count";
	public static final String MATERIAL_DATE = "Date Created";
	public static final String MATERIAL_AUTHOR = "Author";
	public static final String MATERIAL_TITLE = "title";
	public static final String MATERIAL_SUBTOPIC = "subtopic";
	public static final String MATERIAL_URL = "url";
	public static final String MATERIAL_DESCRIPTION = "description";

	protected Material(Entity e) {
		super(e);
		entity.setProperty(MATERIAL_RATING, 0);
		/*
		 * entity.setProperty(MATERIAL_RATING_COUNT, 0);
		 * entity.setProperty(MATERIAL_RATING, 0);
		 * entity.setProperty(MATERIAL_FLAGGED_COUNT, 0); Date currentDate = new
		 * Date(); entity.setProperty(MATERIAL_DATE, currentDate);
		 * entity.setProperty(MATERIAL_AUTHOR, "");
		 * entity.setProperty(MATERIAL_TITLE, "");
		 * entity.setProperty(MATERIAL_SUBTOPIC, "");
		 */
	}

	/**
	 * Updates the average rating for the material, given the new single rating
	 * 
	 * @param rateStar
	 *            int - a single rating
	 */
	public void updateRating(int rateStar) {
		double rating = (double) entity.getProperty(MATERIAL_RATING);
		int rating_count = (int) entity.getProperty(MATERIAL_RATING_COUNT);
		double temp = rating_count * rating;
		rating_count++;
		temp = (temp + rateStar) / (rating_count);
		rating = temp;
		entity.setProperty(MATERIAL_RATING, rating);
		entity.setProperty(MATERIAL_RATING_COUNT, rating_count);
		save();
	}

	/**
	 * update the count of flags this Material has received by 1
	 */
	public void updateFlagged() {
		int flagged_count = (int) entity.getProperty(MATERIAL_FLAGGED_COUNT);
		flagged_count++;
		entity.setProperty(MATERIAL_FLAGGED_COUNT, flagged_count);
		save();
	}

	/**
	 * set the number of flags this material has to 0
	 */
	public void resetFlagged() {
		entity.setProperty(MATERIAL_FLAGGED_COUNT, 0);
		save();
	}

	public void setAutor(Key author) {
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
		String title = (String) entity.getProperty(MATERIAL_TITLE);
		return title.length() > 20 ? title.substring(0, 20) : title;
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
	 * TODO: Return the average global rating of this material
	 */
	public int getGlobalRating(){
		return 3;
	}
	
}
