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

public class Notes extends Material implements Serializable {

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

	public static Notes createNotes(String lTitle, String lDescription,
			String lURL, Key lKey, Key authorKey) {
		Entity ent = new Entity(ENT_NOTES);
		Notes l = new Notes(ent);
		l.setTitle(lTitle);
		// Remove until using
		//l.setDescription(lDescription);
		l.setURL(lURL);
		l.setSubtopicKey(lKey);
		l.setAutor(authorKey);
		l.save();
		return l;
	}

	public static ArrayList<Notes> getFlaggedNotes() {
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
	}

	public static ArrayList<Notes> getTopRatedNotes(int limit) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query photoQuery = new Query(ENT_NOTES).addSort(MATERIAL_RATING,
				SortDirection.DESCENDING);
		PreparedQuery pq = datastore.prepare(photoQuery);
		pq.asList(FetchOptions.Builder.withLimit(limit));
		ArrayList<Notes> topRatedLectures = new ArrayList<Notes>();
		for (Entity result : pq.asIterable()) {
			topRatedLectures.add(new Notes(result));
		}
		return topRatedLectures;
	}

	public static ArrayList<Notes> getMostRecentNotes(int limit) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query photoQuery = new Query(ENT_NOTES).addSort(MATERIAL_DATE,
				SortDirection.DESCENDING);
		PreparedQuery pq = datastore.prepare(photoQuery);
		ArrayList<Notes> topRatedLectures = new ArrayList<Notes>();
		for (Entity result : pq.asIterable()) {
			topRatedLectures.add(new Notes(result));
		}
		return topRatedLectures;
	}
}
