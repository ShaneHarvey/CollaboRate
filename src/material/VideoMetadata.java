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

public class VideoMetadata extends UserMaterialMetadata{

	private static final long serialVersionUID = -7499175721777484363L;

	private VideoMetadata(Entity e, Key stID) {
		super(e, stID);
	}
	
	public static VideoMetadata createVideoMetadata(Key uID, Video vid){
		Entity e = new Entity(USER_METADATA);
		e.setProperty(UserMaterialMetadata.MATERIAL_TYPE, UserMaterialMetadata.MaterialType.VIDEO.val);
		VideoMetadata temp = new VideoMetadata(e, vid.getSubtopicKey());
		temp.setMaterialID(vid.getKey());
		temp.setUserID(uID);
		temp.setFlagged(false);
		temp.setMaterialRating(-1);
		temp.save();
		return temp;
	}
	
	public static VideoMetadata getVideoMetadata(Key uID, Video vid){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter userIdFilter =
				new FilterPredicate(USERID,
				   FilterOperator.EQUAL,
				   uID);
		Filter materialIdFilter = 
				new FilterPredicate(MATERIALID,
					FilterOperator.EQUAL,
					vid.getKey());
		Filter materialTypeFilter = new FilterPredicate(UserMaterialMetadata.MATERIAL_TYPE,
				FilterOperator.EQUAL,
				UserMaterialMetadata.MaterialType.VIDEO.val);
		Filter combinedFilter =
				CompositeFilterOperator.and(userIdFilter, materialIdFilter, materialTypeFilter);
		
		Query q = new Query(USER_METADATA).setFilter(combinedFilter);
		try{
			PreparedQuery pq = datastore.prepare(q);
			Entity metadata = pq.asSingleEntity();
			if(metadata != null){
				return new VideoMetadata(metadata, vid.getSubtopicKey());
			}else {
				return null;
			}
		} catch(PreparedQuery.TooManyResultsException e){
			e.printStackTrace();
			return null;
		}
	}
}
