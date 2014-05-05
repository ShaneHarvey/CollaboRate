package material;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
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
		v.setAuthor(authorKey);
		v.setSubject(subjectKey);
		v.setDate();
		v.save();
		return v;
	}

	/*public static ArrayList<Video> getFlaggedVideos() {
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
	}*/

	/*public static ArrayList<Video> getTopRatedVideos(int limit, Key sKey) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Filter subtopicFilter = new FilterPredicate(MATERIAL_SUBTOPIC,
				FilterOperator.EQUAL, sKey);
		Query photoQuery = new Query(ENT_VIDEO).setFilter(subtopicFilter);/*.addSort(MATERIAL_RATING,
				SortDirection.DESCENDING).setFilter(subtopicFilter);
		PreparedQuery pq = datastore.prepare(photoQuery);
		ArrayList<Video> topRatedVideos = new ArrayList<Video>();
		for (Entity result : pq.asList(FetchOptions.Builder.withLimit(limit))) {
			topRatedVideos.add(new Video(result));
		}
		return topRatedVideos;
	}*/

	public static ArrayList<Video> getMostRecentVideos(int limit, Key sKey) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Filter subtopicFilter = new FilterPredicate(MATERIAL_SUBTOPIC,
				FilterOperator.EQUAL, sKey);
		Query photoQuery = new Query(ENT_VIDEO).addSort(MATERIAL_DATE,
				SortDirection.DESCENDING).setFilter(subtopicFilter);
		PreparedQuery pq = datastore.prepare(photoQuery);
		ArrayList<Video> topRatedVideos = new ArrayList<Video>();
		for (Entity result : pq.asList(FetchOptions.Builder.withLimit(limit))) {
			topRatedVideos.add(new Video(result));
		}
		return topRatedVideos;
	}
	
	public static ArrayList<Video> getXVideos(int limit, Key sKey) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Filter subtopicFilter = new FilterPredicate(MATERIAL_SUBTOPIC,
				FilterOperator.EQUAL, sKey);
		Query randQuery = new Query(ENT_VIDEO).setFilter(subtopicFilter);
		PreparedQuery pq = datastore.prepare(randQuery);
		
		// Add first x videos
		ArrayList<Video> videos = new ArrayList<Video>();
		for (Entity result : pq.asIterable()) {
			if(videos.size() < limit)
				videos.add(new Video(result));
			else
				break;
		}
		return videos;
	}
	public static ArrayList<Video> getUsersGeneratedVideo(Key userKey){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter userFilter = new FilterPredicate(MATERIAL_AUTHOR, FilterOperator.EQUAL, userKey);
		Query userContent = new Query(ENT_VIDEO).addSort(MATERIAL_DATE, SortDirection.DESCENDING).setFilter(userFilter);
		PreparedQuery pq = datastore.prepare(userContent);
		ArrayList<Video> videos = new ArrayList<Video>();
		for(Entity result:pq.asIterable()){
				videos.add(new Video(result));
		}
		return videos;
	}
	public static Video getHottestVideo(){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		//Sort based on MaterialID
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date currentDate = new Date();
		try {
			Date todayWithZeroTime =formatter.parse(formatter.format(currentDate));
			Filter dateFilter = new FilterPredicate(UserMaterialMetadata.METADATA_DATE,
					FilterOperator.EQUAL,
					todayWithZeroTime);
			Filter videoFilter = new FilterPredicate(UserMaterialMetadata.MATERIAL_TYPE,
					FilterOperator.EQUAL,
					UserMaterialMetadata.MaterialType.VIDEO.val);
			Filter videoAndDateFilter = CompositeFilterOperator.and(dateFilter, videoFilter);
			Query q = new Query(UserMaterialMetadata.USER_METADATA).addSort(UserMaterialMetadata.MATERIALID).setFilter(videoAndDateFilter);
			PreparedQuery pq = datastore.prepare(q);
			
			// Current material being viewed
			Entity currentMaterial = null;
			int currentTimesAttempted = 1;//Count the number of viewed in the current Material
			ArrayList<DayCount> attemptedList = new ArrayList<DayCount>();// Hold all of the flagged material
			// Get an iterator over all viewed materials
			Iterator<Entity> ents = pq.asIterable().iterator();
			// If there are viewed materials, initialize currentMaterial to the first
			if(ents.hasNext()) 
				currentMaterial = ents.next();
			// Go over the rest of the flagged materials
			while(ents.hasNext()) {
				// Get current from iterator
				Entity curr = ents.next();
				// If they have the same key, they are the same material type, just increment counter
				if(currentMaterial.getKey().equals(curr.getProperty(UserMaterialMetadata.MATERIALID)))
					currentTimesAttempted++;
				else{
					// New material found, add viewed material to list and move on to start counting next material.
					attemptedList.add(new DayCount(currentMaterial, currentTimesAttempted));
					currentMaterial = curr; // Advance to next entity
					currentTimesAttempted = 1; // Reset the flag count
				}
			}
			// The last entity will be skipped by the for loop, add it here
			if(currentMaterial != null)
				attemptedList.add(new DayCount(currentMaterial, currentTimesAttempted));
			Collections.sort(attemptedList);//call the collections sort which will sort the array list
			if(attemptedList.size()>0){
				Key videoKey = (Key) attemptedList.get(0).ent.getProperty(UserMaterialMetadata.MATERIALID);
				return Video.getVideo(videoKey);
			}
			else{
				Query photoQuery = new Query(ENT_VIDEO).addSort(MATERIAL_DATE,
						SortDirection.DESCENDING);
				PreparedQuery pq2 = datastore.prepare(photoQuery);
				ArrayList<Video> topRatedVideos = new ArrayList<Video>();
				Entity result = pq2.asList(FetchOptions.Builder.withLimit(1)).get(0);
				if(result!= null){
					return new Video(result);
				}
			}
			return null;
			
		} catch (ParseException e) {
			return null;
		}
	}
}
