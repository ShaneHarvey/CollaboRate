package material;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;

import material.Material;

public class Notes extends Material {

	private static final long serialVersionUID = -7018562988368587595L;
	private static final String ENT_NOTES = "notes";

	private Notes(Entity lectures) {
		super(lectures);
	}

	public static Notes getNotes(Key lectureKey) {
		try {
			Entity lectureE = DatastoreServiceFactory.getDatastoreService()
					.get(lectureKey);
			return new Notes(lectureE);
		} catch (EntityNotFoundException e) {
			return null;
		}
	}
	
	public static Notes getFromKeyString(String key) {
		return getNotes(KeyFactory.stringToKey(key));
	}

	public static Notes createNotes(String lTitle, String lDescription,
			String lURL, Key lKey, Key authorKey, Key subjectKey) {
		Entity ent = new Entity(ENT_NOTES);
		Notes l = new Notes(ent);
		l.setTitle(lTitle);
		// Remove until using
		//l.setDescription(lDescription);
		l.setURL(lURL);
		l.setSubtopicKey(lKey);
		l.setAuthor(authorKey);
		l.setSubject(subjectKey);
		l.setDate();
		l.save();
		return l;
	}

	/*public static ArrayList<Notes> getFlaggedNotes() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query photoQuery = new Query(ENT_NOTES).addSort(MATERIAL_FLAGGED_COUNT,
				SortDirection.DESCENDING);
		PreparedQuery pq = datastore.prepare(photoQuery);
		ArrayList<Notes> listOfFlagged = new ArrayList<Notes>();
		for (Entity result : pq.asIterable()) {
			listOfFlagged.add(new Notes(result));
		}
		return listOfFlagged;
	}*/

	/*public static ArrayList<Notes> getTopRatedNotes(int limit, Key sKey) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Filter subtopicFilter = new FilterPredicate(MATERIAL_SUBTOPIC,
				FilterOperator.EQUAL, sKey);
		Query photoQuery = new Query(ENT_NOTES).setFilter(subtopicFilter);/*.addSort(MATERIAL_RATING,
				SortDirection.DESCENDING).setFilter(subtopicFilter);
		PreparedQuery pq = datastore.prepare(photoQuery);
		ArrayList<Notes> topRatedLectures = new ArrayList<Notes>();
		for (Entity result : pq.asList(FetchOptions.Builder.withLimit(limit))) {
			topRatedLectures.add(new Notes(result));
		}
		return topRatedLectures;
	}*/

	public static ArrayList<Notes> getMostRecentNotes(int limit, Key sKey) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Filter subtopicFilter = new FilterPredicate(MATERIAL_SUBTOPIC,
				FilterOperator.EQUAL, sKey);
		Query photoQuery = new Query(ENT_NOTES).addSort(MATERIAL_DATE,
				SortDirection.DESCENDING).setFilter(subtopicFilter);
		PreparedQuery pq = datastore.prepare(photoQuery);
		ArrayList<Notes> topRatedLectures = new ArrayList<Notes>();
		for (Entity result : pq.asList(FetchOptions.Builder.withLimit(limit))) {
			topRatedLectures.add(new Notes(result));
		}
		return topRatedLectures;
	}
	
	public static ArrayList<Notes> getXNotes(int limit, Key sKey) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Filter subtopicFilter = new FilterPredicate(MATERIAL_SUBTOPIC,
				FilterOperator.EQUAL, sKey);
		Query randQuery = new Query(ENT_NOTES).setFilter(subtopicFilter);
		PreparedQuery pq = datastore.prepare(randQuery);
		
		// Add first x notes
		ArrayList<Notes> notes = new ArrayList<Notes>();
		for (Entity result : pq.asIterable()) {
			if(notes.size() < limit)
				notes.add(new Notes(result));
			else
				break;
		}
		return notes;
	}
	public static ArrayList<Notes> getUsersGeneratedNotes(Key userKey){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter userFilter = new FilterPredicate(MATERIAL_AUTHOR, FilterOperator.EQUAL, userKey);
		Query userContent = new Query(ENT_NOTES).addSort(MATERIAL_DATE, SortDirection.DESCENDING).setFilter(userFilter);
		PreparedQuery pq = datastore.prepare(userContent);
		ArrayList<Notes> notes = new ArrayList<Notes>();
		//System.out.println(userKey.toString());
		for(Entity result: pq.asIterable()){
				notes.add(new Notes(result));
		}
		return notes;
	}
	public static Notes getMostRecentNotes(){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query photoQuery = new Query(ENT_NOTES).addSort(MATERIAL_DATE,
				SortDirection.DESCENDING).addSort(MATERIAL_DATE, SortDirection.DESCENDING);
		PreparedQuery pq = datastore.prepare(photoQuery);
		Entity toReturn = null;
		for (Entity result : pq.asList(FetchOptions.Builder.withLimit(1))) {
			toReturn = result;
			break;
		}
		return new Notes(toReturn);
	}	
}
