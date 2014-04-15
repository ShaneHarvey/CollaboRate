package discussion_board;

import java.io.Serializable;
import java.util.ArrayList;

import material.Video;

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

public class Post extends DiscussionEntries{
	private static final String ENT_POST = "post";
	private static final String ENT_SUBTOPIC = "subtopic";
	private ArrayList<Comment> postComments = null;
	private Post(Entity post) {
		super(post);
	}
	public static Post getPost(Key postKey){
		try {
			Entity postE = DatastoreServiceFactory.getDatastoreService().get(
					postKey);
			return new Post(postE);
		} catch (EntityNotFoundException e) {
			return null;
		}
	}
	public static Post getFromKeyString(String postKey) {
		return getPost(KeyFactory.stringToKey(postKey));
	}
	private void setSubtopic(Key subtopicKey){
		this.entity.setProperty(ENT_SUBTOPIC, subtopicKey);
	}
	public Key getSubtopic(){
		return (Key)this.entity.getProperty(ENT_SUBTOPIC);
	}
	public static Post createPost(Key authorKey, String postBody, Key subtopicKey){
		Entity postE = new Entity(ENT_POST);
		Post p = new Post(postE);
		p.setSubtopic(subtopicKey);
		p.setAuthor(authorKey);
		p.setDate();
		p.setBody(postBody);
		p.save();
		return p;
	}
	public ArrayList<Comment> getComments(){
		postComments=Comment.getCommentsForPost(entity.getKey());
		return postComments;
	}
	public static ArrayList<Post> getPostsForSubtopic(Key subtopicKey){
		ArrayList<Post> posts = new ArrayList();
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Filter subtopicFilter = new FilterPredicate(ENT_SUBTOPIC,
				FilterOperator.EQUAL, subtopicKey);
		Query photoQuery = new Query(ENT_POST).addSort(DiscussionEntries.ENTRY_DATE,
				SortDirection.DESCENDING).setFilter(subtopicFilter);
		PreparedQuery pq = datastore.prepare(photoQuery);
		for (Entity result : pq.asIterable()) {
			posts.add(new Post(result));
		}
		return posts;
	}
}
