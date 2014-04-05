package material;

import java.util.Date;

import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.DatastoreService;


public abstract class Material {
	Entity entity;
	public static final String MATERIAL_RATING = "Rating";
	public static final String MATERIAL_RATING_COUNT= "Rating_Count";
	public static final String MATERIAL_FLAGGED_COUNT= "Flagged_Count";
	public static final String MATERIAL_DATE = "Date Created";
	public static final String MATERIAL_AUTHOR = "Author";
	protected Material(Entity e){
		this.entity=e;
		entity.setProperty(MATERIAL_RATING_COUNT, 0);
		entity.setProperty(MATERIAL_RATING, 0);
		entity.setProperty(MATERIAL_FLAGGED_COUNT, 0);
		Date currentDate = new Date();
		entity.setProperty(MATERIAL_DATE, currentDate);
	}
	public void updateRating(int rateStar){
		double rating = (double)entity.getProperty(MATERIAL_RATING);
		int rating_count = (int)entity.getProperty(MATERIAL_RATING_COUNT);
		double temp = rating_count * rating;
		rating_count++;
		temp = (temp + rateStar)/(rating_count);
		rating = temp;
		entity.setProperty(MATERIAL_RATING, rating);
		entity.setProperty(MATERIAL_RATING_COUNT, rating_count);
		save();
	}
	public void updateFlagged(){
		int flagged_count = (int)entity.getProperty(MATERIAL_FLAGGED_COUNT);
		flagged_count++;
		entity.setProperty(MATERIAL_FLAGGED_COUNT, flagged_count);
		save();
	}
	public void setAutor(String author){
		entity.setProperty(MATERIAL_AUTHOR, KeyFactory.stringToKey(author));
	}
	public void save(){
		DatastoreServiceFactory.getDatastoreService().put(entity);
	}
	public void delete(){
		DatastoreServiceFactory.getDatastoreService().delete(entity.getKey());
	}
	
}
