package material;

import account.Account;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

import discussion_board.Comment;

public class UserMaterialMetadata {
	private Entity entity;
	public static final String USERID ="userID";
	public static final String MATERIALID = "materialID";
	public static final String MATERIAL_RATING = "materialRating";
	public static final String MATERIAL_FLAGGED = "materialFlagged";
	public static final String MATERIAL_VIEWED = "materialViewed";
	public static final String USER_METADATA = "user_material_metadata";//table name
	public UserMaterialMetadata(Entity e){
		this.entity = e;
	}
	private void setUserID(Key uID){
		entity.setProperty(USERID, uID);
	}
	private void setMaterialID(Key mID){
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
	public Key getFlagged(){
		return (Key)entity.getProperty(MATERIAL_FLAGGED);
	}
	public boolean getViewed(){
		return (boolean)entity.getProperty(MATERIAL_VIEWED);
	}
	public int getRating(){
		return (int)entity.getProperty(MATERIAL_RATING);
	}
	public void save() {
		DatastoreServiceFactory.getDatastoreService().put(entity);
	}
	public void delete() {
		DatastoreServiceFactory.getDatastoreService().delete(entity.getKey());
	}
	public UserMaterialMetadata createUserMaterialMetadata(Key uID, Key mID){
		this.setMaterialID(mID);
		this.setUserID(uID);
		this.setFlagged(false);
		this.setMaterialRating(-1);
		this.save();
		return new UserMaterialMetadata(this.entity);
	}
	public UserMaterialMetadata getUserMaterialMetadata(Key uID, Key mID){
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
				return createUserMaterialMetadata(uID, mID);
			}
		} catch(PreparedQuery.TooManyResultsException e){
			e.printStackTrace();
			return null;
		}
	}
}
