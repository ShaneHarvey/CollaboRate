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

public class Category extends DBObject implements Serializable {
	
	private static final long serialVersionUID = 5487744678389202343L;
	public static final String ENT_CATEGORY_TITLE = "categoryTitle";
	public static final String ENT_CATEGORY ="category";
	private ArrayList<Subject> subjectList;
	/**
	 * Constructor for the Category class. Sets the categoryEntity to the passed entity
	 * @param sEntity
	 */
	private Category(Entity ent){
		super(ent);
	}
	/**
	 * Gets a category from a given key
	 * @param category Key
	 * @return the Category object
	 */
	public static Category getCategory(Key cKey){
		try {
			Entity categoryE = DatastoreServiceFactory.getDatastoreService().get(cKey);
			return new Category(categoryE);
		} catch (EntityNotFoundException e) {
			return null;
		}
	}
	public static Category getCategory(String cKey) {
		return getCategory(KeyFactory.stringToKey(cKey));
	}
	
	
	
	/**
	 * Given the string representation of a category key, return the category
	 * @param cKey the string representation of the key
	 * @return The category with this key
	 */
	public static Category getFromKeyString(String cKey) {
		return getCategory(KeyFactory.stringToKey(cKey));
	}
	public static Category createCategory(String cTitle){
		Entity categoryE = new Entity(ENT_CATEGORY);
		Category c = new Category(categoryE);
		c.setTitle(cTitle);
		c.save();
		return c;
	}
	private void setTitle(String categoryTitle){
		entity.setProperty(ENT_CATEGORY_TITLE, categoryTitle);
	}
	public String getTitle(){
		return (String)entity.getProperty(ENT_CATEGORY_TITLE);
	}
	public static ArrayList<Category> getAllCategories(){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query photoQuery = new Query(ENT_CATEGORY).addSort(ENT_CATEGORY_TITLE, SortDirection.ASCENDING);  
		PreparedQuery pq = datastore.prepare(photoQuery);
		ArrayList<Category> categories = new ArrayList<Category>();
		for (Entity result : pq.asIterable()) {
			Category tempCategory = new Category(result);
			categories.add(tempCategory);
		}
		return categories;
	}
	public ArrayList<Subject> getSubjects(){
		if(subjectList == null)
			subjectList = Subject.getCategorySubjects(entity.getKey());
		return subjectList;
	}
	
	public String getKeyAsString(){
		return (String)KeyFactory.keyToString(entity.getKey());
	}

}
