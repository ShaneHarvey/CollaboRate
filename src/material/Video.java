package material;

import java.io.Serializable;
import java.util.ArrayList;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.PreparedQuery;

import material.Material;

public class Video extends Material implements Serializable {

	private static final long serialVersionUID = -5875498799377954045L;
	private static final String ENT_VIDEO = "video";

	private Video(Entity video) {
		super(video);
	}

	public static Video getVideo(Key videoKey) {
		try {
			Entity videoE = DatastoreServiceFactory.getDatastoreService().get(
					videoKey);
			return new Video(videoE);
		} catch (EntityNotFoundException e) {
			return null;
		}
	}

	public static Video getFromKeyString(String key) {
		return getVideo(KeyFactory.stringToKey(key));
	}

	public static Video createVideo(String vTitle, String vDescription,
			String vURL, Key sKey, Key authorKey, Key subjectKey) {
		Entity videoE = new Entity(ENT_VIDEO);
		Video v = new Video(videoE);
		v.setTitle(vTitle);
		// Removed until using
		//v.setDescription(vDescription);
		v.setURL(vURL);
		v.setSubtopicKey(sKey);
		v.setAutor(authorKey);
		v.setSubject(subjectKey);
		v.save();
		return v;
	}

	public static ArrayList<Video> getFlaggedVideos() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query photoQuery = new Query(ENT_VIDEO).addSort(MATERIAL_FLAGGED_COUNT,
				SortDirection.DESCENDING);
		PreparedQuery pq = datastore.prepare(photoQuery);
		ArrayList<Video> listOfFlagged = new ArrayList<Video>();
		for (Entity result : pq.asIterable()) {
			listOfFlagged.add(new Video(result));
		}
		return listOfFlagged;
	}

	public static ArrayList<Video> getTopRatedVideos(int limit, Key sKey) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Filter subtopicFilter = new FilterPredicate(MATERIAL_SUBTOPIC,
				FilterOperator.EQUAL, sKey);
		Query photoQuery = new Query(ENT_VIDEO).addSort(MATERIAL_RATING,
				SortDirection.DESCENDING).setFilter(subtopicFilter);
		PreparedQuery pq = datastore.prepare(photoQuery);
		pq.asList(FetchOptions.Builder.withLimit(limit));
		ArrayList<Video> topRatedVideos = new ArrayList<Video>();
		for (Entity result : pq.asIterable()) {
			topRatedVideos.add(new Video(result));
		}
		return topRatedVideos;
	}

	public static ArrayList<Video> getMostRecentVideos(int limit, Key sKey) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Filter subtopicFilter = new FilterPredicate(MATERIAL_SUBTOPIC,
				FilterOperator.EQUAL, sKey);
		Query photoQuery = new Query(ENT_VIDEO).addSort(MATERIAL_DATE,
				SortDirection.DESCENDING).setFilter(subtopicFilter);
		PreparedQuery pq = datastore.prepare(photoQuery);
		pq.asList(FetchOptions.Builder.withLimit(limit));
		ArrayList<Video> topRatedVideos = new ArrayList<Video>();
		for (Entity result : pq.asIterable()) {
			topRatedVideos.add(new Video(result));
		}
		return topRatedVideos;
	}
}
