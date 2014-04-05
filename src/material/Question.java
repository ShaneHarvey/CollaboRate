package material;

import java.io.Serializable;
import java.util.ArrayList;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;

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
	
	private void setAnswerChoices(String jsonChoices){
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
	
	public String getAnswerChoices(){
		return (String) entity.getProperty(ANSWER_CHOICES);
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
	public static Question getQuestion(String qkey){
		
		Key key = KeyFactory.stringToKey(qkey);
		try{
			Entity qEntity = DatastoreServiceFactory.getDatastoreService().get(key);
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
	public static Question createQuestion(String title, String choicesJSON, String explainationsJSON, String correctIndex){
		Question newQuestion = new Question();
		newQuestion.setTitle(title);
		newQuestion.setAnswerChoices(choicesJSON);
		newQuestion.setAnswerExplainations(explainationsJSON);
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
	public static Question createQuestion(String questionBody, String choicesJSON, String explainationsJSON, int correctIndex){
		return createQuestion(questionBody, choicesJSON, explainationsJSON, String.valueOf(correctIndex));
	}
	
	

}
