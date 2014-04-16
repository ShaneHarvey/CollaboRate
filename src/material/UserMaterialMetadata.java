package material;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

import database.DBObject;

public class UserMaterialMetadata extends DBObject {
	
	private static final long serialVersionUID = 3784610917718375779L;
	
	public static final String USERID ="userID";
	public static final String MATERIALID = "materialID";
	public static final String MATERIAL_RATING = "materialRating";
	public static final String MATERIAL_FLAGGED = "materialFlagged";
	public static final String MATERIAL_VIEWED = "materialViewed";
	public static final String USER_METADATA = "user_material_metadata";//table name
	public UserMaterialMetadata(Entity e){
		super(e);
	}
	protected void setUserID(Key uID){
		entity.setProperty(USERID, uID);
	}
	protected void setMaterialID(Key mID){
		entity.setProperty(MATERIALID, mID);
	}
	public void setFlagged(boolean b){
		entity.setProperty(MATERIAL_FLAGGED, b);
	}
	public void setViewed(){
		entity.setProperty(MATERIAL_VIEWED, true);
	}
	public void setMaterialRating(int rating){
		entity.setProperty(MATERIAL_RATING, rating);
	}
	public boolean getFlagged(){
		Boolean flagged  = (Boolean)entity.getProperty(MATERIAL_FLAGGED);
		return flagged == null ? false : flagged;
	}
	public boolean getViewed(){
		Boolean viewed  = (Boolean)entity.getProperty(MATERIAL_VIEWED);
		return viewed == null ? false : viewed;
	}
	public int getRating(){
		Long rating = (Long)entity.getProperty(MATERIAL_RATING);
		return rating == null ? -1 : rating.intValue();
	}

	public static UserMaterialMetadata createUserMaterialMetadata(Key uID, Key mID){
		Entity e = new Entity(USER_METADATA);
		UserMaterialMetadata temp = new UserMaterialMetadata(e);
		temp.setMaterialID(mID);
		temp.setUserID(uID);
		temp.setFlagged(false);
		temp.setMaterialRating(-1);
		temp.save();
		return temp;
	}
	public static UserMaterialMetadata getUserMaterialMetadata(Key uID, Key mID){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter userIdFilter =
				new FilterPredicate(USERID,
				   FilterOperator.EQUAL,
				   uID);
		Filter materialIdFilter = 
				new FilterPredicate(MATERIALID,
					FilterOperator.EQUAL,
					mID);
		Filter combinedFilter =
				CompositeFilterOperator.and(userIdFilter,materialIdFilter);

		Query q = new Query(USER_METADATA).setFilter(combinedFilter);
		try{
			PreparedQuery pq = datastore.prepare(q);
			Entity metadata = pq.asSingleEntity();
			if(metadata != null){
				return new UserMaterialMetadata(metadata);
			}else {
				return null;
			}
		} catch(PreparedQuery.TooManyResultsException e){
			e.printStackTrace();
			return null;
		}
	}
}
