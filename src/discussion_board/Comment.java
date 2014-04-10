package discussion_board;

import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class Comment extends DiscussionEntries{
	private static final String ENT_COMMENT = "comment";
	private static final String ENT_COMMENT_POST = "postID";
	public Comment(Entity comment) {
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
	public static Comment createPost(Key authorKey, String commentBody, Key postKey){
		Entity commentE = new Entity(ENT_COMMENT);
		Comment c = new Comment(commentE);
		c.setPostID(postKey);
		c.setAuthor(authorKey);
		c.setDate();
		c.setEntryBody(commentBody);
		c.save();
		return c;
	}
}
