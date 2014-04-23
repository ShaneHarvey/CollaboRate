package material;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

import java.util.Collections;
public class MaterialMetadata {
	
	public static int getRating(Key materialID){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter materialIdFilter = 
				new FilterPredicate(UserMaterialMetadata.MATERIALID,
					FilterOperator.EQUAL,
					materialID);
		Query q = new Query(UserMaterialMetadata.USER_METADATA).setFilter(materialIdFilter);
		PreparedQuery pq = datastore.prepare(q);
		int resultCounter = 0;
		int resultTotal = 0;
		long resultRating = 0;
		for (Entity result : pq.asIterable()) {
			 resultRating = (long)result.getProperty(UserMaterialMetadata.MATERIAL_RATING);
			 if(resultRating<0){continue;}
			 resultTotal+=resultRating;
			 resultCounter++;
		}
		return resultCounter == 0 ? 0 : resultTotal / resultCounter;
	}
	
	public static ArrayList<RatedMaterial> getSortedByRating(Key stID){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		//Sort based on MaterialID
		Query q = new Query(UserMaterialMetadata.USER_METADATA).addSort(UserMaterialMetadata.MATERIALID);
		Filter subtopicIDFilter = 
				new FilterPredicate(UserMaterialMetadata.Material_SubTopic,
					FilterOperator.EQUAL,
					stID);
		q.setFilter(subtopicIDFilter);
		PreparedQuery pq = datastore.prepare(q);
		
		// Current material being viewed
		Entity currentMaterial = null;
		int currentNumRated = 1;//Count the number of ratings in the current Material
		long currentRatingSum = 0; // Running sum of ratings
		ArrayList<RatedMaterial> ratedList = new ArrayList<RatedMaterial>();// Hold all of the flagged material
		// Get an iterator over all flagged materials
		Iterator<Entity> ents = pq.asIterable().iterator();
		// If there are rated materials, initialize currentMaterial to the first
		if(ents.hasNext())  {
			currentMaterial = ents.next();
			currentRatingSum = (long)currentMaterial.getProperty(UserMaterialMetadata.MATERIAL_RATING);
		}
		// Go over the rest of the rated materials
		while(ents.hasNext()) {
			// Get current from iterator
			Entity curr = ents.next();
			// If they have the same key, they are the same material type, just increment counter
			if(currentMaterial.getKey().equals(curr.getProperty(UserMaterialMetadata.MATERIALID))) {
				currentNumRated++;
				currentRatingSum += (long)curr.getProperty(UserMaterialMetadata.MATERIAL_RATING);
			}
			else{
				// New material found, add rated material to list and move on to start counting next material.
				ratedList.add(new RatedMaterial(currentMaterial, currentRatingSum / currentNumRated));
				currentMaterial = curr; // Advance to next entity
				currentNumRated = 1; // Reset the flag count
				currentRatingSum = (long)curr.getProperty(UserMaterialMetadata.MATERIAL_RATING);
			}
		}
		// The last entity will be skipped by the for loop, add it here
		if(currentMaterial != null)
			ratedList.add(new RatedMaterial(currentMaterial, currentRatingSum / currentNumRated));
		Collections.sort(ratedList);// sort the materials
		Collections.reverse(ratedList); // Want in descending order so reverse
		return ratedList;//return the flagged list 
	}
	
	/*public static int getFlagged(Key materialID){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter materialIdFilter = 
				new FilterPredicate(UserMaterialMetadata.MATERIALID,
					FilterOperator.EQUAL,
					materialID);
		Query q = new Query(UserMaterialMetadata.USER_METADATA).setFilter(materialIdFilter);
		PreparedQuery pq = datastore.prepare(q);
		int resultTotal = 0;
		boolean resultFlagged;
		for (Entity result : pq.asIterable()) {
			 resultFlagged = (boolean)result.getProperty(UserMaterialMetadata.MATERIAL_FLAGGED);
			 if(resultFlagged==false){continue;}
			 resultTotal++;;
		}
		return (int)resultTotal;
	}*/
	
	public static ArrayList<FlaggedMaterial> getSortedFlagged(){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		//Sort based on MaterialID
		Query q = new Query(UserMaterialMetadata.USER_METADATA).addSort(UserMaterialMetadata.MATERIALID);
		Filter isFlagged = new FilterPredicate(UserMaterialMetadata.MATERIAL_FLAGGED,
				FilterOperator.EQUAL,
				true);
		q.setFilter(isFlagged);
		PreparedQuery pq = datastore.prepare(q);
		
		// Current material being viewed
		Entity currentMaterial = null;
		int currentNumFlagged = 1;//Count the number of flagged in the current Material
		ArrayList<FlaggedMaterial> flaggedList = new ArrayList<FlaggedMaterial>();// Hold all of the flagged material
		// Get an iterator over all flagged materials
		Iterator<Entity> ents = pq.asIterable().iterator();
		// If there are flagged materials, initialize currentMaterial to the first
		if(ents.hasNext()) 
			currentMaterial = ents.next();
		// Go over the rest of the flagged materials
		while(ents.hasNext()) {
			// Get current from iterator
			Entity curr = ents.next();
			// If they have the same key, they are the same material type, just increment counter
			if(currentMaterial.getKey().equals(curr.getProperty(UserMaterialMetadata.MATERIALID)))
				currentNumFlagged++;
			else{
				// New material found, add flagged material to list and move on to start counting next material.
				flaggedList.add(new FlaggedMaterial(currentMaterial, currentNumFlagged));
				currentMaterial = curr; // Advance to next entity
				currentNumFlagged = 1; // Reset the flag count
			}
		}
		// The last entity will be skipped by the for loop, add it here
		if(currentMaterial != null)
			flaggedList.add(new FlaggedMaterial(currentMaterial, currentNumFlagged));
		Collections.sort(flaggedList);//call the collections sort which will sort the array list
		return flaggedList;//return the flagged list 
	}
	
	public static class RatedMaterial implements Comparable<RatedMaterial>  {
		private Entity ent;
		private int rating =0;
		
		public RatedMaterial(Entity ent, Long rating){
			this.ent = ent;
			this.rating = rating.intValue();
		}
		
		@Override
		public int compareTo(RatedMaterial o) {
			return this.rating - o.rating;
		}
		
		public long getMaterialType() {
			return (long) ent.getProperty(UserMaterialMetadata.MATERIAL_TYPE);
		}
		
		public Key getKey() {
			return (Key)ent.getProperty(UserMaterialMetadata.MATERIALID);
		}
	}
	
	public static class FlaggedMaterial implements Comparable<FlaggedMaterial>  {
		private Entity ent;
		private int numFlagged =0;
		
		public FlaggedMaterial(Entity ent, int nFlagged){
			this.ent = ent;
			this.numFlagged=nFlagged;
		}
		
		@Override
		public int compareTo(FlaggedMaterial o) {
			return this.numFlagged - o.numFlagged;
		}
		
		public long getMaterialType() {
			return (long) ent.getProperty(UserMaterialMetadata.MATERIAL_TYPE);
		}
		
		public Key getKey() {
			return (Key)ent.getProperty(UserMaterialMetadata.MATERIALID);
		}
	}
}
