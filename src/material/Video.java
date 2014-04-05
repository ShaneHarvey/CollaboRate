package material;
import java.util.ArrayList;

import account.Account;
import account.Account.ActorType;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.PreparedQuery;

import material.Material;
public class Video extends Material {
	//private Entity entity;
	// Entity keys
	private static final String ENT_VIDEO_URL= "videoURL";
	private static final String ENT_VIDEO_TITLE = "vidoeTitle";
	private static final String ENT_VIDEO_DESCRIPTION ="videoDescription";
	private static final String ENT_VIDEO ="video";
	private static final String ENT_VIDEO_SUBTOPIC = "subtopic";
	private Video(Entity video){
		super(video);
	}
	public static Video getVideo(String videoKey){
		Key vKey = KeyFactory.stringToKey(videoKey);
		try {
			Entity videoE = DatastoreServiceFactory.getDatastoreService().get(vKey);
			return new Video(videoE);
		} catch (EntityNotFoundException e) {
			return null;
		}
	}
	public static Video createVideo(String vTitle, String vDescription, String vURL, String sKey){
		Entity videoE = new Entity(ENT_VIDEO);
		Video v = new Video(videoE);
		v.setVideoTitle(vTitle);
		v.setVideoDescription(vDescription);
		v.setVideoURL(vURL);
		v.setVideoSubtopic(sKey);
		v.save();
		return v;
	}
	/*public void saveVideo(){
		DatastoreServiceFactory.getDatastoreService().put(entity);
	}
	public void deleteVideo(){
		DatastoreServiceFactory.getDatastoreService().delete(entity.getKey());
	}*/
	private void setVideoURL(String vURL){
		entity.setProperty(ENT_VIDEO_URL, vURL);
	}
	private void setVideoTitle(String vTitle){
		entity.setProperty(ENT_VIDEO_TITLE, vTitle);
	}
	private void setVideoDescription(String vDescription){
		entity.setProperty(ENT_VIDEO_DESCRIPTION, vDescription);
	}
	private void setVideoSubtopic(String sKey){
		Key subTopicKey = KeyFactory.stringToKey(sKey);
		entity.setProperty(ENT_VIDEO_SUBTOPIC, subTopicKey);
	}
	public String getVideoURL(){
		return (String)entity.getProperty(ENT_VIDEO_URL);
	}
	public String getVideoTitle(){
		return (String)entity.getProperty(ENT_VIDEO_TITLE);
	}
	public String getVideoDescription(){
		return (String)entity.getProperty(ENT_VIDEO_DESCRIPTION);
	}
	public String getVideoSubtopic(){
		return (String)entity.getProperty(ENT_VIDEO_SUBTOPIC);
	}
	public Key getVideoKey(){
		return (Key)entity.getKey();
	}
}
