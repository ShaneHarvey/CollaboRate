package discussion_board;

import java.io.Serializable;

import material.Video;

import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class Post extends DiscussionEntries{
	private static final String ENT_POST = "post";
	private static final String ENT_SUBTOPIC = "subtopic";
	public Post(Entity post) {
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
		p.setEntryBody(postBody);
		p.save();
		return p;
	}
	
}
