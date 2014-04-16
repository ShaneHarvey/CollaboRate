package material;

import java.util.ArrayList;

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
	public static double getRating(Key materialID){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter materialIdFilter = 
				new FilterPredicate(UserMaterialMetadata.MATERIALID,
					FilterOperator.EQUAL,
					materialID);
		Query q = new Query(UserMaterialMetadata.USER_METADATA).setFilter(materialIdFilter);
		PreparedQuery pq = datastore.prepare(q);
		int resultCounter = 0;
		int resultTotal = 0;
		int resultRating = 0;
		for (Entity result : pq.asIterable()) {
			 resultRating = (int)result.getProperty(UserMaterialMetadata.MATERIAL_RATING);
			 if(resultRating<0){continue;}
			 resultTotal+=resultRating;
			 resultCounter++;
		}
		return (double)resultTotal/resultCounter;
	}
	public static int getFlagged(Key materialID){
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
	}
	public static ArrayList<FlaggedMaterial> getSortedFlagged(){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		//Sort based on MaterialID
		Query q = new Query(UserMaterialMetadata.USER_METADATA).addSort(UserMaterialMetadata.MATERIALID);
		Filter isFlagged = new FilterPredicate(UserMaterialMetadata.MATERIAL_FLAGGED,
				FilterOperator.EQUAL,
				true);
		q.setFilter(isFlagged);
		PreparedQuery pq = datastore.prepare(q);
		
		Key currentMaterialID = null;//The result set will be in chunks based on the Material ID so we count we keep track of which one we are tracking
		int currentNumFlagged = 0;//Count the number of flagged in the current Material ID
		ArrayList<FlaggedMaterial> flaggedList = new ArrayList<FlaggedMaterial>();//The object to hold all the material and flagged pairs
		for (Entity result : pq.asIterable()) {//iterate through the whole table
			Key resultKey = (Key)result.getProperty(UserMaterialMetadata.MATERIALID);//get the material key
			if(resultKey.equals(currentMaterialID)){//if the currentMaterialID does not equal the result key we need to dump the two values into the array list
				currentNumFlagged++;
			}
			else{//increment count
				flaggedList.add(new FlaggedMaterial(currentMaterialID, currentNumFlagged));//dump the values into the array list by making a new instance of the object
				currentMaterialID = resultKey;//advance the currentMaterial Key to the next Key
				currentNumFlagged = 0;
			}
		}
		flaggedList.remove(0);//remove the 0th index because it is a garbage key
		Collections.sort(flaggedList);//call the collections sort which will sort the array list
		return flaggedList;//return the flagged list 
	}
	public static class FlaggedMaterial implements Comparable<FlaggedMaterial>  {
		//private Key materialID;
		private int numFlagged =0;
		public FlaggedMaterial(Key mID, int nFlagged){
			//this.materialID= mID;
			this.numFlagged=nFlagged;
		}
		
		@Override
		public int compareTo(FlaggedMaterial o) {
			return this.numFlagged - o.numFlagged;
		}
	}
}
