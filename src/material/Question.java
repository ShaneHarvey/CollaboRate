package material;

import java.io.Serializable;

import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class Question extends Material implements Serializable{
	
	//private Entity entity;
	
	private final static String QUESTION = "question"; 
	private final static String QUESTION_BODY = "question_body";
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
	
	
	
	
	
	private void setQuestionBody(String questionBody){
		entity.setProperty(QUESTION_BODY, questionBody);
	}
	
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
	
	
	public String getQuestionBody(){
		return (String) entity.getProperty(QUESTION_BODY);
	}
	
	public String getAnswerChoices(){
		return (String) entity.getProperty(ANSWER_CHOICES);
	}
	
	public String getAnswerExplainations(){
		return (String) entity.getProperty(ANSWER_EXPLAINATIONS);
	}
	
	public int getCorrectIndex(){
		return  Integer.parseInt((String)entity.getProperty(CORRECT_INDEX));
	}
	
	
	
	/*public void saveQuestion(){
		DatastoreServiceFactory.getDatastoreService().put(entity);
	}
	
	public void deleteQuestion(){
		DatastoreServiceFactory.getDatastoreService().delete(entity.getKey());
	}
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
	
	public static Question createQuestion(String questionBody, String choicesJSON, String explainationsJSON, String correctIndex){
		Question newQuestion = new Question();
		newQuestion.setQuestionBody(questionBody);
		newQuestion.setAnswerChoices(choicesJSON);
		newQuestion.setAnswerExplainations(explainationsJSON);
		newQuestion.setCorrectIndex(correctIndex);
		newQuestion.save();
		
		return newQuestion;
		
	}
	
	
	public static Question createQuestion(String questionBody, String choicesJSON, String explainationsJSON, int correctIndex){
		return createQuestion(questionBody, choicesJSON, explainationsJSON, String.valueOf(correctIndex));
	}
	
	
	public static Question [] getTopQuestions(int numberToReturn){
		Question [] topQuestions = new Question[numberToReturn];
		
		
		
		//pq.aslist(FetchOptions.Builder.withLimit(numberToReturn));
		
		
		return topQuestions;
		
	}
	
	

}
