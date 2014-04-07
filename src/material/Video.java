package material;
import java.util.ArrayList;
import java.util.HashMap;

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
import com.google.appengine.api.datastore.Query.SortDirection;
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
	public static Video getVideo(Key videoKey){
		//Key vKey = KeyFactory.stringToKey(videoKey);
		try {
			Entity videoE = DatastoreServiceFactory.getDatastoreService().get(videoKey);
			return new Video(videoE);
		} catch (EntityNotFoundException e) {
			return null;
		}
	}
	public static Video createVideo(String vTitle, String vDescription, String vURL, Key sKey, Key authorKey){
		Entity videoE = new Entity(ENT_VIDEO);
		Video v = new Video(videoE);
		v.setVideoTitle(vTitle);
		v.setVideoDescription(vDescription);
		v.setVideoURL(vURL);
		v.setSubtopicKey(sKey);
		//v.setVideoSubtopic(sKey);
		v.setAutor(authorKey);
		v.save();
		return v;
	}

	private void setVideoURL(String vURL){
		entity.setProperty(ENT_VIDEO_URL, vURL);
	}
	private void setVideoTitle(String vTitle){
		entity.setProperty(ENT_VIDEO_TITLE, vTitle);
	}
	private void setVideoDescription(String vDescription){
		entity.setProperty(ENT_VIDEO_DESCRIPTION, vDescription);
	}
	/*private void setVideoSubtopic(Key sKey){
		//Key subTopicKey = KeyFactory.stringToKey(sKey);
		entity.setProperty(ENT_VIDEO_SUBTOPIC, sKey);
	}*/
	public String getVideoURL(){
		return (String)entity.getProperty(ENT_VIDEO_URL);
	}
	public String getVideoTitle(){
		return (String)entity.getProperty(ENT_VIDEO_TITLE);
	}
	public String getVideoDescription(){
		return (String)entity.getProperty(ENT_VIDEO_DESCRIPTION);
	}
	/*public String getVideoSubtopic(){
		return (String)entity.getProperty(ENT_VIDEO_SUBTOPIC);
	}*/
	public Key getVideoKey(){
		return (Key)entity.getKey();
	}
	/*public static ArrayList<String> getFlaggedVideos(){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query photoQuery = new Query(ENT_VIDEO).addSort(MATERIAL_FLAGGED_COUNT, SortDirection.DESCENDING);  
		PreparedQuery pq = datastore.prepare(photoQuery);
		ArrayList<String> listOfFlagged = new ArrayList();
		for (Entity result : pq.asIterable()) {
			String videoKey = KeyFactory.keyToString(result.getKey());
			String videoTitle = (String)result.getProperty(ENT_VIDEO_TITLE); 
			String jsonString = "{\"videoKey\":\""+videoKey +"\", \"lectureTitle\":\""+videoTitle+"\"}";
			listOfFlagged.add(jsonString);
		}
		return listOfFlagged;
	}
	public static ArrayList<String> getTopRatedVideos(){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query photoQuery = new Query(ENT_VIDEO).addSort(MATERIAL_RATING, SortDirection.DESCENDING);  
		PreparedQuery pq = datastore.prepare(photoQuery);
		ArrayList<String> topRatedVideos = new ArrayList();
		for (Entity result : pq.asIterable()) {
			String videoKey = KeyFactory.keyToString(result.getKey());
			String videoTitle = (String)result.getProperty(ENT_VIDEO_TITLE);
			String jsonString = "{\"videoKey\":\""+videoKey +"\", \"lectureTitle\":\""+videoTitle+"\"}";
			topRatedVideos.add(jsonString);
		}
		return topRatedVideos;
	}
	public static ArrayList<String> getMostRecentVideos(){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query photoQuery = new Query(ENT_VIDEO).addSort(MATERIAL_DATE, SortDirection.DESCENDING);  
		PreparedQuery pq = datastore.prepare(photoQuery);
		ArrayList<String> topRatedVideos = new ArrayList();
		for (Entity result : pq.asIterable()) {
			String videoKey = KeyFactory.keyToString(result.getKey());
			String videoTitle = (String)result.getProperty(ENT_VIDEO_TITLE);
			String jsonString = "{\"videoKey\":\""+videoKey +"\", \"lectureTitle\":\""+videoTitle+"\"}";
			topRatedVideos.add(jsonString);
		}
		return topRatedVideos;
	}*/
}
