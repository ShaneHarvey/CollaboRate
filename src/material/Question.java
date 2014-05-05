package material;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;

import account.Account;

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
import com.google.gson.*;

public class Question extends Material implements Serializable {

	private static final long serialVersionUID = -3534572743954510719L;

	public final static String QUESTION = "question";
	private final static String ANSWER_CHOICES = "answer_choices";
	private final static String ANSWER_EXPLAINATIONS = "answer_explainations";
	private final static String CORRECT_INDEX = "correct_index";
	private final static String QUESTION_VERIFIED = "verified";

	private Question(Entity e) {
		super(e);
	}

	private void setAnswerChoices(String[] choices) {
		String jsonChoices = new Gson().toJson(choices);
		entity.setProperty(ANSWER_CHOICES, jsonChoices);
	}

	/*
	 * private void setAnswerExplainations(String jsonAnswer) {
	 * entity.setProperty(ANSWER_EXPLAINATIONS, jsonAnswer); }
	 */

	private void setCorrectIndex(int index) {
		entity.setProperty(CORRECT_INDEX, String.valueOf(index));
	}

	public String[] getAnswerChoices() {
		String jsonChoices = (String) entity.getProperty(ANSWER_CHOICES);
		return new Gson().fromJson(jsonChoices, String[].class);
	}

	public String getAnswerChoicesJson() {
		return (String) entity.getProperty(ANSWER_CHOICES);
	}

	public String getAnswerExplainations() {
		return (String) entity.getProperty(ANSWER_EXPLAINATIONS);
	}

	public int getCorrectIndex() {
		return Integer.parseInt((String) entity.getProperty(CORRECT_INDEX));
	}

	public boolean checkAnswer(int userChoice) {
		return (getCorrectIndex() == userChoice);
	}

	public void setVerified(boolean isVerified) {
		entity.setProperty(QUESTION_VERIFIED, isVerified);
	}

	/**
	 * Get a question object that has the given key in the data store
	 * 
	 * @param qkey
	 *            String - a String representation of the entity key in the data
	 *            store
	 * @return
	 */
	public static Question getQuestion(Key qkey) {
		try {
			Entity qEntity = DatastoreServiceFactory.getDatastoreService().get(
					qkey);
			return new Question(qEntity);
		} catch (EntityNotFoundException e) {
			return null;
		}
	}

	public static Question getFromKeyString(String key) {
		return getQuestion(KeyFactory.stringToKey(key));
	}

	/**
	 * Create a Question object, and entity, and put the entity in the datastore
	 * 
	 * @param title
	 *            String - the actual question
	 * @param choicesJSON
	 *            String - the JSON representation of all the choices for this
	 *            question
	 * @param explainationsJSON
	 *            String = the JSON representation of all the explainations for
	 *            all the choices
	 * @param correctIndex
	 *            String - which choice is correct
	 * @return
	 */
	public static Question createQuestion(String title, String[] choicesJSON,
			int correctIndex, Subtopic st, Account acc) {
		// Took String explanationsJSON out this week, will add back next week
		
		Subject sub = st.getSubject();
		Entity ent = new Entity(QUESTION);
		Question newQuestion = new Question(ent);
		newQuestion.setTitle(title);
		newQuestion.setAnswerChoices(choicesJSON);
		// newQuestion.setAnswerExplainations(explainationsJSON);
		newQuestion.setCorrectIndex(correctIndex);
		newQuestion.setAuthor(acc.getKey());
		newQuestion.setSubtopicKey(st.getKey());
		newQuestion.setSubject(sub.getKey());
		newQuestion.setVerified(sub.userTrusted(acc));
		newQuestion.setDate();
		newQuestion.save();

		return newQuestion;
	}

	public static ArrayList<Question> getMostRecentQuestions(int limit, Key sKey) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Filter subtopicFilter = new FilterPredicate(MATERIAL_SUBTOPIC,
				FilterOperator.EQUAL, sKey);
		Query photoQuery = new Query(QUESTION).addSort(MATERIAL_DATE,
				SortDirection.DESCENDING).setFilter(subtopicFilter);
		PreparedQuery pq = datastore.prepare(photoQuery);
		ArrayList<Question> topRatedQuestions = new ArrayList<Question>();
		for (Entity result : pq.asList(FetchOptions.Builder.withLimit(limit))) {
			topRatedQuestions.add(new Question(result));
		}
		return topRatedQuestions;
	}

	public static ArrayList<Question> getXQuestions(int limit, Key sKey) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Filter subtopicFilter = new FilterPredicate(MATERIAL_SUBTOPIC,
				FilterOperator.EQUAL, sKey);
		Query randQuery = new Query(QUESTION).setFilter(subtopicFilter);
		PreparedQuery pq = datastore.prepare(randQuery);

		// Add first x questions
		ArrayList<Question> questions = new ArrayList<Question>();
		for (Entity result : pq.asIterable()) {
			if (questions.size() < limit)
				questions.add(new Question(result));
			else
				break;
		}
		return questions;
	}
	
	public static ArrayList<Question> getUnverifiedQuestions(Subject sub){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Filter subtopicFilter = new FilterPredicate(MATERIAL_SUBJECT,
				FilterOperator.EQUAL, sub.getKey());
		Filter verifiedFilter = new FilterPredicate(QUESTION_VERIFIED,
				FilterOperator.EQUAL, false);
		Filter combinedFilter = CompositeFilterOperator.and(subtopicFilter,
				verifiedFilter);
		Query randQuery = new Query(QUESTION).setFilter(combinedFilter);
		
		PreparedQuery pq = datastore.prepare(randQuery);
		ArrayList<Question> questions = new ArrayList<Question>();
		// Create questions
		for (Entity result : pq.asIterable()) {
			questions.add(new Question(result));
		}
		return questions;
	}
	
	public static ArrayList<Question> getAllUnverifiedQuestions(){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Filter verifiedFilter = new FilterPredicate(QUESTION_VERIFIED,
				FilterOperator.EQUAL, false);
		Query randQuery = new Query(QUESTION).setFilter(verifiedFilter);
		
		PreparedQuery pq = datastore.prepare(randQuery);
		ArrayList<Question> questions = new ArrayList<Question>();
		// Create questions
		for (Entity result : pq.asIterable()) {
			questions.add(new Question(result));
		}
		return questions;
	}

	public static ArrayList<Question> getRandomVerifiedQuestions(int limit,
			Key sKey) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Filter subtopicFilter = new FilterPredicate(MATERIAL_SUBTOPIC,
				FilterOperator.EQUAL, sKey);
		Filter verifiedFilter = new FilterPredicate(QUESTION_VERIFIED,
				FilterOperator.EQUAL, true);
		Filter combinedFilter = CompositeFilterOperator.and(subtopicFilter,
				verifiedFilter);
		Query randQuery = new Query(QUESTION).setFilter(combinedFilter);
		// Only get the keys of the entites
		randQuery.setKeysOnly();
		PreparedQuery pq = datastore.prepare(randQuery);

		ArrayList<Entity> randomKeys = new ArrayList<Entity>();
		for (Entity result : pq.asIterable()) {
			randomKeys.add(result);
		}
		Collections.shuffle(randomKeys);
		ArrayList<Question> randomQuestions = new ArrayList<Question>();
		if (randomKeys.size() < limit)
			limit = randomKeys.size();
		for (Entity result : randomKeys.subList(0, limit)) {
			randomQuestions.add(getQuestion(result.getKey()));
		}
		return randomQuestions;
	}

	public static ArrayList<Question> getUsersGeneratedQuestions(Key userKey){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter userFilter = new FilterPredicate(MATERIAL_AUTHOR, FilterOperator.EQUAL, userKey);
		Query userContent = new Query(QUESTION).setFilter(userFilter).addSort(MATERIAL_DATE, SortDirection.DESCENDING);
		PreparedQuery pq = datastore.prepare(userContent);
		ArrayList<Question> questions = new ArrayList<Question>();
		for(Entity result:pq.asIterable()){
				questions.add(new Question(result));
		}
		return questions;
	}
	public static Question getHottestQuestion(){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		//Sort based on MaterialID
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date currentDate = new Date();
		try {
			Date todayWithZeroTime =formatter.parse(formatter.format(currentDate));
			Filter dateFilter = new FilterPredicate(UserMaterialMetadata.METADATA_DATE,
					FilterOperator.EQUAL,
					todayWithZeroTime);
			Filter questionFilter = new FilterPredicate(UserMaterialMetadata.MATERIAL_TYPE,
					FilterOperator.EQUAL,
					UserMaterialMetadata.MaterialType.QUESTION.val);
			Filter questionAndDateFilter = CompositeFilterOperator.and(dateFilter, questionFilter);
			Query q = new Query(UserMaterialMetadata.USER_METADATA).addSort(UserMaterialMetadata.MATERIALID).setFilter(questionAndDateFilter);
			PreparedQuery pq = datastore.prepare(q);
			
			// Current material being viewed
			Entity currentMaterial = null;
			int currentTimesAttempted = 1;//Count the number of flagged in the current Material
			ArrayList<DayCount> attemptedList = new ArrayList<DayCount>();// Hold all of the flagged material
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
					currentTimesAttempted++;
				else{
					// New material found, add flagged material to list and move on to start counting next material.
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
				Key questionKey = (Key) attemptedList.get(0).ent.getProperty(UserMaterialMetadata.MATERIALID);
				return Question.getQuestion(questionKey);
			}
			else{
				Query photoQuery = new Query(QUESTION).addSort(MATERIAL_DATE,
						SortDirection.DESCENDING);
				PreparedQuery pq2 = datastore.prepare(photoQuery);
				ArrayList<Question> topRatedQuestions = new ArrayList<Question>();
				Entity recentQuestion = pq2.asList(FetchOptions.Builder.withLimit(1)).get(0);
				if(recentQuestion != null){
					return new Question(recentQuestion);
				}
			}
			return null;
		} catch (ParseException e) {
			return null;
		}
		
	}
}
