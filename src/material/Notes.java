package material;

import java.io.Serializable;
import java.util.ArrayList;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;

import material.Material;
public class Notes extends Material implements Serializable{
		// Entity keys
		private static final String ENT_NOTES_URL= "notesURL";
		private static final String ENT_NOTES_TITLE = "notesTitle";
		private static final String ENT_NOTES_DESCRIPTION ="notesDescription";
		private static final String ENT_NOTES ="notes";
		private static final String ENT_NOTES_SUBTOPIC = "subtopic";
		private Notes(Entity lectures){
			super(lectures);
		}
		public static Notes getNotes(Key lectureKey){
			//Key lKey = KeyFactory.stringToKey(lectureKey);
			try {
				Entity lectureE = DatastoreServiceFactory.getDatastoreService().get(lectureKey);
				return new Notes(lectureE);
			} catch (EntityNotFoundException e) {
				return null;
			}
		}
		public static Notes createNotes(String lTitle, String lDescription, String lURL, Key lKey, Key authorKey){
			Entity ent = new Entity(ENT_NOTES);
			Notes l = new Notes(ent);
			l.setNotesTitle(lTitle);
			l.setNotesDescription(lDescription);
			l.setNotesURL(lURL);
			//l.setNotesSubtopic(lKey);
			l.setSubtopicKey(lKey);
			l.setAutor(authorKey);
			l.save();
			return l;
		}
		
		private void setNotesURL(String lURL){
			entity.setProperty(ENT_NOTES_URL, lURL);
		}
		private void setNotesTitle(String lTitle){
			entity.setProperty(ENT_NOTES_TITLE, lTitle);
		}
		private void setNotesDescription(String lDescription){
			entity.setProperty(ENT_NOTES_DESCRIPTION, lDescription);
		}
		private void setNotesSubtopic(Key lKey){
			//Key subTopicKey = KeyFactory.stringToKey(lKey);
			entity.setProperty(ENT_NOTES_SUBTOPIC, lKey);
		}
		public String getNotesURL(){
			return (String)entity.getProperty(ENT_NOTES_URL);
		}
		public String getNotesTitle(){
			return (String)entity.getProperty(ENT_NOTES_TITLE);
		}
		public String getNotesDescription(){
			return (String)entity.getProperty(ENT_NOTES_DESCRIPTION);
		}
		public String getNotesSubtopic(){
			return (String)entity.getProperty(ENT_NOTES_SUBTOPIC);
		}
		public Key getNotesKey(){
			return (Key)entity.getKey();
		}
		public static ArrayList<Notes> getFlaggedNotes(){
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			Query photoQuery = new Query(ENT_NOTES).addSort(MATERIAL_FLAGGED_COUNT, SortDirection.DESCENDING);  
			PreparedQuery pq = datastore.prepare(photoQuery);
			ArrayList<Notes> listOfFlagged = new ArrayList<Notes>();
			for (Entity result : pq.asIterable()) {
				listOfFlagged.add(new Notes(result));
			}
			return listOfFlagged;
		}
		public static ArrayList<Notes> getTopRatedNotes(int limit){
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			Query photoQuery = new Query(ENT_NOTES).addSort(MATERIAL_RATING, SortDirection.DESCENDING);  
			PreparedQuery pq = datastore.prepare(photoQuery);
			pq.asList(FetchOptions.Builder.withLimit(limit));
			ArrayList<Notes> topRatedLectures = new ArrayList<Notes>();
			for (Entity result : pq.asIterable()) {
				topRatedLectures.add(new Notes(result));
			}
			return topRatedLectures;
		}
		public static ArrayList<Notes>  getMostRecentNotes(int limit){
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			Query photoQuery = new Query(ENT_NOTES).addSort(MATERIAL_DATE, SortDirection.DESCENDING);  
			PreparedQuery pq = datastore.prepare(photoQuery);
			ArrayList<Notes> topRatedLectures = new ArrayList<Notes>();
			for (Entity result : pq.asIterable()) {
				topRatedLectures.add(new Notes(result));
			}
			return topRatedLectures;
		}
}
