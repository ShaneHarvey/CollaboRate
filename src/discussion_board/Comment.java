package discussion_board;

import java.util.ArrayList;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;

public class Comment extends DiscussionEntries{
	
	private static final long serialVersionUID = 5700286441889702608L;
	
	private static final String ENT_COMMENT = "comment";
	private static final String ENT_COMMENT_POST = "postID";
	private Comment(Entity comment) {
		super(comment);
	}
	public static Comment getComment(Key commentKey){
		try {
			Entity commentE = DatastoreServiceFactory.getDatastoreService().get(
					commentKey);
			return new Comment(commentE);
		} catch (EntityNotFoundException e) {
			return null;
		}
	}
	public static Comment getFromKeyString(String commentKey) {
		return getComment(KeyFactory.stringToKey(commentKey));
	}
	private void setPostID(Key postKey){
		this.entity.setProperty(ENT_COMMENT_POST, postKey);
	}
	public Key getPostID(){
		return (Key)this.entity.getProperty(ENT_COMMENT_POST);
	}
	public static Comment createComment(Key authorKey, String commentBody, Key postKey){
		Entity commentE = new Entity(ENT_COMMENT);
		Comment c = new Comment(commentE);
		c.setPostID(postKey);
		c.setAuthor(authorKey);
		c.setDate();
		c.setBody(commentBody);
		c.save();
		return c;
	}
	public static ArrayList<Comment> getCommentsForPost(Key postID){
		ArrayList<Comment> comments = new ArrayList<Comment>();
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Filter subtopicFilter = new FilterPredicate(ENT_COMMENT_POST,
				FilterOperator.EQUAL, postID);
		Query photoQuery = new Query(ENT_COMMENT).addSort(DiscussionEntries.ENTRY_DATE,
				SortDirection.DESCENDING).setFilter(subtopicFilter);
		PreparedQuery pq = datastore.prepare(photoQuery);
		//ArrayList<Comment> topRatedQuestions = new ArrayList<Comment>();
		for (Entity result : pq.asIterable()) {
			comments.add(new Comment(result));
		}
		return comments;
	}
}
