package material;

import java.io.Serializable;
import java.util.ArrayList;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;

import database.DBObject;

public class QuestionStatistics extends DBObject implements Serializable{
	public static final String STATS = "question_stats";
	public static final String STATS_SUBJECT = "subject";
	public static final String STATS_USER = "user";
	public static final String STATS_QUESTION = "question";
	public static final String STATS_CORRECT = "correct";
			
	private QuestionStatistics(Entity ent){
		super(ent);
	}
	private void setSubjectKey(Key sKey){
		entity.setProperty(STATS_SUBJECT, sKey);
	}
	private void setUserKey(Key uKey){
		entity.setProperty(STATS_USER, uKey);
	}
	private void setQuestionKey(Key qKey){
		entity.setProperty(STATS_QUESTION, qKey);
	}
	private void setCorrect(boolean b){
		entity.setProperty(STATS_CORRECT, b);
	}
	public Key getSubjectKey(){
		return (Key)entity.getProperty(STATS_SUBJECT);
	}
	public Key getUserKey(){
		return (Key)entity.getProperty(STATS_USER);
	}
	public Key getQuestionKey(){
		return (Key)entity.getProperty(STATS_QUESTION);
	}
	public boolean getCorrect(){
		return (boolean)entity.getProperty(STATS_CORRECT);
	}
	/*public static void insertStatistic(Key sKey, Key qKey, Key uKey, boolean status){
		Entity stats = new Entity(STATS);
		QuestionStatistics qs = new QuestionStatistics(stats);
		qs.setCorrect(status);
		qs.setQuestionKey(qKey);
		qs.setSubjectKey(sKey);
		qs.setUserKey(uKey);
		qs.save();
	}
	public static int getNumberQuestionsCompleted(Key uKey, Key sKey){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter userFilter = new FilterPredicate(STATS_USER,
				FilterOperator.EQUAL, uKey);
		Filter subjectFilter = new FilterPredicate(STATS_SUBJECT,
				FilterOperator.EQUAL, sKey);
		Filter user_Subject_Filter =
				  CompositeFilterOperator.and(userFilter, subjectFilter);
		Query photoQuery = new Query(STATS).setFilter(user_Subject_Filter);
		return datastore.prepare(photoQuery).countEntities(FetchOptions.Builder.withLimit(max));
		//PreparedQuery pq = datastore.prepare(photoQuery);
	}
	private static int getNumberQuestionsCorrect(Key uKey, Key sKey){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter userFilter = new FilterPredicate(STATS_USER,
				FilterOperator.EQUAL, uKey);
		Filter subjectFilter = new FilterPredicate(STATS_SUBJECT,
				FilterOperator.EQUAL, sKey);
		Filter correctFilter = new FilterPredicate(STATS_CORRECT,
				FilterOperator.EQUAL, true);
		Filter user_Subject_Filter =
				  CompositeFilterOperator.and(userFilter, subjectFilter, correctFilter);
		Query photoQuery = new Query(STATS).setFilter(user_Subject_Filter);
		return datastore.prepare(photoQuery).countEntities(FetchOptions.Builder.withLimit(max));
	}
	private static double getPercentageCorrect(Key uKey, Key sKey){
		int totalQuestions = getNumberQuestionsCompleted(uKey, sKey);
		int correctQuestions = getNumberQuestionsCorrect(uKey, sKey);
		return (double) correctQuestions/totalQuestions;
	}*/
}
