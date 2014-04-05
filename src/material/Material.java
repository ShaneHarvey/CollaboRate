package material;

import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

public abstract class Material {
	Entity entity;
	public static final String MATERIAL_RATING = "Rating";
	public static final String MATERIAL_RATING_COUNT= "Rating_Count";
	public static final String MATERIAL_FLAGGED= "";
	protected Material(Entity e){
		this.entity=e;
		entity.setProperty(MATERIAL_RATING_COUNT, String.valueOf(0));
		entity.setProperty(MATERIAL_RATING, String.valueOf(0));
		entity.setProperty(MATERIAL_FLAGGED, String.valueOf(0));
	}
	public void updateRating(int rateStar){
		double rating = (double)entity.getProperty(MATERIAL_RATING);
		long rating_count = (long)entity.getProperty(MATERIAL_RATING_COUNT);
		double temp = rating_count * rating;
		rating_count++;
		temp = (temp + rateStar)/(rating_count);
		rating = temp;
		entity.setProperty(MATERIAL_RATING, String.valueOf(rating));
		entity.setProperty(MATERIAL_RATING_COUNT, String.valueOf(rating_count));
		save();
	}
	public void updateFlagged(){
		
	}
	public void save(){
		DatastoreServiceFactory.getDatastoreService().put(entity);
	}
	public void delete(){
		DatastoreServiceFactory.getDatastoreService().delete(entity.getKey());
	}
	
}
