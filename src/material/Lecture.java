package material;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;

import material.Material;
public class Lecture extends Material{
	//private Entity entity;
		// Entity keys
		private static final String ENT_LECTURE_URL= "lectureURL";
		private static final String ENT_LECTURE_TITLE = "lectureTitle";
		private static final String ENT_LECTURE_DESCRIPTION ="lectureDescription";
		private static final String ENT_LECTURE ="lecture";
		private static final String ENT_LECTURE_SUBTOPIC = "subtopic";
		private Lecture(Entity lectures){
			super(lectures);
		}
		public static Lecture getVideo(String lectureKey){
			Key lKey = KeyFactory.stringToKey(lectureKey);
			try {
				Entity lectureE = DatastoreServiceFactory.getDatastoreService().get(lKey);
				return new Lecture(lectureE);
			} catch (EntityNotFoundException e) {
				return null;
			}
		}
		public static Lecture createVideo(String lTitle, String lDescription, String lURL, String lKey, String authorKey){
			Entity lectureE = new Entity(ENT_LECTURE);
			Lecture l = new Lecture(lectureE);
			l.setLectureTitle(lTitle);
			l.setLectureDescription(lDescription);
			l.setLectureURL(lURL);
			l.setLectureSubtopic(lKey);
			l.setAutor(authorKey);
			l.save();
			return l;
		}
		
		private void setLectureURL(String lURL){
			entity.setProperty(ENT_LECTURE_URL, lURL);
		}
		private void setLectureTitle(String lTitle){
			entity.setProperty(ENT_LECTURE_TITLE, lTitle);
		}
		private void setLectureDescription(String lDescription){
			entity.setProperty(ENT_LECTURE_DESCRIPTION, lDescription);
		}
		private void setLectureSubtopic(String lKey){
			Key subTopicKey = KeyFactory.stringToKey(lKey);
			entity.setProperty(ENT_LECTURE_SUBTOPIC, subTopicKey);
		}
		public String getLectureURL(){
			return (String)entity.getProperty(ENT_LECTURE_URL);
		}
		public String getLectureTitle(){
			return (String)entity.getProperty(ENT_LECTURE_TITLE);
		}
		public String getLectureDescription(){
			return (String)entity.getProperty(ENT_LECTURE_DESCRIPTION);
		}
		public String getLectureSubtopic(){
			return (String)entity.getProperty(ENT_LECTURE_SUBTOPIC);
		}
		public Key getLectureKey(){
			return (Key)entity.getKey();
		}
		/*public static ArrayList<String> getFlaggedLectures(){
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			Query photoQuery = new Query(ENT_LECTURE).addSort(MATERIAL_FLAGGED_COUNT, SortDirection.DESCENDING);  
			PreparedQuery pq = datastore.prepare(photoQuery);
			ArrayList<String> listOfFlagged = new ArrayList();
			for (Entity result : pq.asIterable()) {
				String lectureKey = KeyFactory.keyToString(result.getKey());
				String lectureTitle = (String)result.getProperty(ENT_LECTURE_TITLE); 
				String jsonString = "{\"lectureKey\":\""+lectureKey +"\", \"lectureTitle\":\""+lectureTitle+"\"}";
				listOfFlagged.add(jsonString);
			}
			return listOfFlagged;
		}
		public static ArrayList<String> getTopRatedLectures(int limit){
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			Query photoQuery = new Query(ENT_LECTURE).addSort(MATERIAL_RATING, SortDirection.DESCENDING);  
			PreparedQuery pq = datastore.prepare(photoQuery);
			pq.asList(FetchOptions.Builder.withLimit(limit));
			String returnString = "{\"Status\":\"ok\"";
			ArrayList<String> topRatedLectures = new ArrayList();
			for (Entity result : pq.asIterable()) {
				String lectureKey = KeyFactory.keyToString(result.getKey());
				String lectureTitle = (String)result.getProperty(ENT_LECTURE_TITLE);
				String jsonString = "{\"lectureKey\":\""+lectureKey +"\", \"lectureTitle\":\""+lectureTitle+"\"}";
				topRatedLectures.add(jsonString);
			}
			return topRatedLectures;
		}
		public static ArrayList<String>  getMostRecentVideos(){
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			Query photoQuery = new Query(ENT_LECTURE).addSort(MATERIAL_DATE, SortDirection.DESCENDING);  
			PreparedQuery pq = datastore.prepare(photoQuery);
			ArrayList<String> topRatedLectures = new ArrayList();
			for (Entity result : pq.asIterable()) {
				String lectureKey = KeyFactory.keyToString(result.getKey());
				String lectureTitle = (String)result.getProperty(ENT_LECTURE_TITLE);
				String jsonString = "{\"lectureKey\":\""+lectureKey +"\", \"lectureTitle\":\""+lectureTitle+"\"}";
				topRatedLectures.add(jsonString);
			}
			return topRatedLectures;
		}*/
}
