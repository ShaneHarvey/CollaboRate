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

public class NotesMetadata extends UserMaterialMetadata{

	private static final long serialVersionUID = -7499175721777484363L;

	private NotesMetadata(Entity e) {
		super(e);
	}
	
	public static NotesMetadata createNotesMetadata(Key uID, Key mID){
		Entity e = new Entity(USER_METADATA);
		e.setProperty(UserMaterialMetadata.MATERIAL_TYPE, UserMaterialMetadata.MaterialType.NOTES.val);
		NotesMetadata temp = new NotesMetadata(e);
		temp.setMaterialID(mID);
		temp.setUserID(uID);
		temp.setFlagged(false);
		temp.setMaterialRating(-1);
		temp.save();
		return temp;
	}
	
	public static NotesMetadata getNotesMetadata(Key uID, Key mID){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter userIdFilter =
				new FilterPredicate(USERID,
				   FilterOperator.EQUAL,
				   uID);
		Filter materialIdFilter = 
				new FilterPredicate(MATERIALID,
					FilterOperator.EQUAL,
					mID);
		Filter materialTypeFilter = new FilterPredicate(UserMaterialMetadata.MATERIAL_TYPE,
				FilterOperator.EQUAL,
				UserMaterialMetadata.MaterialType.NOTES.val);
		Filter combinedFilter =
				CompositeFilterOperator.and(userIdFilter, materialIdFilter, materialTypeFilter);
		
		Query q = new Query(USER_METADATA).setFilter(combinedFilter);
		try{
			PreparedQuery pq = datastore.prepare(q);
			Entity metadata = pq.asSingleEntity();
			if(metadata != null){
				return new NotesMetadata(metadata);
			}else {
				return null;
			}
		} catch(PreparedQuery.TooManyResultsException e){
			e.printStackTrace();
			return null;
		}
	}
}
