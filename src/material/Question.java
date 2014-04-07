package material;

import java.io.Serializable;
import java.util.ArrayList;

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
import com.google.gson.*;
public class Question extends Material implements Serializable{
	
	//private Entity entity;
	
	private final static String QUESTION = "question"; 
	//private final static String QUESTION_BODY = "question_body";
	private final static String ANSWER_CHOICES = "answer_choices";
	private final static String ANSWER_EXPLAINATIONS = "answer_explainations";
	private final static String CORRECT_INDEX = "correct_index";
	//private final static String QU = "question";
	
	
	
	private Question(){
		this(new Entity(QUESTION));		
	}
	
	private Question(Entity e){
		super(e);
		this.entity = e;
	}
	
	/*private void setQuestionBody(String questionBody){
		entity.setProperty(QUESTION_BODY, questionBody);
	}*/
	
	private void setAnswerChoices(String[] choices){
		String jsonChoices = new Gson().toJson(choices);
		entity.setProperty(ANSWER_CHOICES, jsonChoices);
	}
	
	private void setAnswerExplainations(String jsonAnswer){
		entity.setProperty(ANSWER_EXPLAINATIONS, jsonAnswer);
	}
	
	private void setCorrectIndex(int index){
		entity.setProperty(CORRECT_INDEX, String.valueOf(index));
	}
	
	private void setCorrectIndex(String index){
		entity.setProperty(CORRECT_INDEX, index);
	}
	
	
	/*public String getQuestionBody(){
		return (String) entity.getProperty(QUESTION_BODY);
	}*/
	
	public String[] getAnswerChoices(){
		String jsonChoices = (String) entity.getProperty(ANSWER_CHOICES);
		return new Gson().fromJson(jsonChoices, String[].class);
	}
	
	public String getAnswerExplainations(){
		return (String) entity.getProperty(ANSWER_EXPLAINATIONS);
	}
	
	public int getCorrectIndex(){
		return  Integer.parseInt((String)entity.getProperty(CORRECT_INDEX));
	}
		
	/**
	 * Get a question object that has the given key in the data store
	 * @param qkey String - a String representation of the entity key in the data store
	 * @return
	 */
	public static Question getQuestion(Key qkey){
		
		//Key key = KeyFactory.stringToKey(qkey);
		try{
			Entity qEntity = DatastoreServiceFactory.getDatastoreService().get(qkey);
			return new Question(qEntity);
		}
		catch(EntityNotFoundException e){
			return null;
		}
	}
	
	
	/**
	 * Create a Question object, and entity, and put the entity in the datastore
	 * @param title String - the actual question
	 * @param choicesJSON String - the JSON representation of all the choices for this question
	 * @param explainationsJSON String = the JSON representation of all the explainations for all the choices 
	 * @param correctIndex String - which choice is correct
	 * @return
	 */
	public static Question createQuestion(String title, String[] choicesJSON, String correctIndex){
		//Took String explanationsJSON out this week, will add back next week
		
		Question newQuestion = new Question();
		newQuestion.setTitle(title);
		newQuestion.setAnswerChoices(choicesJSON);
		//newQuestion.setAnswerExplainations(explainationsJSON);
		newQuestion.setCorrectIndex(correctIndex);
		newQuestion.save();
		
		return newQuestion;
		
	}
	
	/**
	 * Create a Question object, and entity, and put the entity in the datastore
	 * @param title String - the actual question
	 * @param choicesJSON String - the JSON representation of all the choices for this question
	 * @param explainationsJSON String = the JSON representation of all the explainations for all the choices 
	 * @param correctIndex int - which choice is correct
	 * @return
	 */
	public static Question createQuestion(String questionBody, String[] choicesJSON, int correctIndex){
		//String explaniationsJSON taken out will add back later
		return createQuestion(questionBody, choicesJSON, String.valueOf(correctIndex));
	}
	public static ArrayList<Question> getFlaggedLectures(){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query photoQuery = new Query(QUESTION).addSort(MATERIAL_FLAGGED_COUNT, SortDirection.DESCENDING);  
		PreparedQuery pq = datastore.prepare(photoQuery);
		ArrayList<Question> listOfFlagged = new ArrayList();
		for (Entity result : pq.asIterable()) {
			listOfFlagged.add(new Question(result));
		}
		return listOfFlagged;
	}
	public static ArrayList<Question> getTopRatedLectures(int limit){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query photoQuery = new Query(QUESTION).addSort(MATERIAL_RATING, SortDirection.DESCENDING);  
		PreparedQuery pq = datastore.prepare(photoQuery);
		pq.asList(FetchOptions.Builder.withLimit(limit));
		ArrayList<Question> topRatedQuestions = new ArrayList();
		for (Entity result : pq.asIterable()) {
			topRatedQuestions.add(new Question(result));
		}
		return topRatedQuestions;
	}
	public static ArrayList<Question>  getMostRecentVideos(int limit){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query photoQuery = new Query(QUESTION).addSort(MATERIAL_DATE, SortDirection.DESCENDING);  
		PreparedQuery pq = datastore.prepare(photoQuery);
		ArrayList<Question> topRatedQuestions = new ArrayList();
		for (Entity result : pq.asIterable()) {
			topRatedQuestions.add(new Question(result));
		}
		return topRatedQuestions;
	}
	

}
