package material;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

public class Statistics {
	private static final int max = Integer.MAX_VALUE;
	public static int getNumberQuestionsCompleted(Key uKey, Key sKey){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter userFilter = new FilterPredicate(QuestionStatistics.STATS_USER,
				FilterOperator.EQUAL, uKey);
		Filter subjectFilter = new FilterPredicate(QuestionStatistics.STATS_SUBJECT,
				FilterOperator.EQUAL, sKey);
		Filter user_Subject_Filter =
				  CompositeFilterOperator.and(userFilter, subjectFilter);
		Query photoQuery = new Query(QuestionStatistics.STATS).setFilter(user_Subject_Filter);
		return datastore.prepare(photoQuery).countEntities(FetchOptions.Builder.withLimit(max));
		//PreparedQuery pq = datastore.prepare(photoQuery);
	}
	public static int getNumberQuestionsCorrect(Key uKey, Key sKey){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter userFilter = new FilterPredicate(QuestionStatistics.STATS_USER,
				FilterOperator.EQUAL, uKey);
		Filter subjectFilter = new FilterPredicate(QuestionStatistics.STATS_SUBJECT,
				FilterOperator.EQUAL, sKey);
		Filter correctFilter = new FilterPredicate(QuestionStatistics.STATS_CORRECT,
				FilterOperator.EQUAL, true);
		Filter user_Subject_Filter =
				  CompositeFilterOperator.and(userFilter, subjectFilter, correctFilter);
		Query photoQuery = new Query(QuestionStatistics.STATS).setFilter(user_Subject_Filter);
		return datastore.prepare(photoQuery).countEntities(FetchOptions.Builder.withLimit(max));
	}
	private static double getPercentageCorrect(Key uKey, Key sKey){
		int totalQuestions = getNumberQuestionsCompleted(uKey, sKey);
		int correctQuestions = getNumberQuestionsCorrect(uKey, sKey);
		return (double) (correctQuestions/totalQuestions)* 100;
	}
	public static int getNumberOfQuestionsForSubject(Key subKey){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter subjectFilter = new FilterPredicate(Material.MATERIAL_SUBJECT,
				FilterOperator.EQUAL, subKey);
		Query photoQuery = new Query(Question.QUESTION).setFilter(subjectFilter);
		return datastore.prepare(photoQuery).countEntities(FetchOptions.Builder.withLimit(max));
	}
	public static int getNumberOfSubtopics(Key subjectKey){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter subjectFilter = new FilterPredicate(Subtopic.ENT_SUBTOPIC_SUBJECT,
				FilterOperator.EQUAL, subjectKey);
		Query photoQuery = new Query(Subtopic.ENT_SUBTOPIC).setFilter(subjectFilter);
		return datastore.prepare(photoQuery).countEntities(FetchOptions.Builder.withLimit(max));
	}
	public static int getSubtopicsCompleted(Key uKey, Key subjectKey){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter userFilter = new FilterPredicate(Test.TEST_USER_ID,
				FilterOperator.EQUAL, uKey);
		Filter subjectFilter = new FilterPredicate(Test.TEST_SUBJECT,
				FilterOperator.EQUAL, subjectKey);
		Filter completedFilter = new FilterPredicate(Test.TEST_PASSED,
				FilterOperator.EQUAL, true);
		Filter user_Subject_Completed_Filter =
				  CompositeFilterOperator.and(userFilter, subjectFilter, completedFilter);
		Query photoQuery = new Query(QuestionStatistics.STATS).setFilter(user_Subject_Completed_Filter);
		return datastore.prepare(photoQuery).countEntities(FetchOptions.Builder.withLimit(max));
	}
	public static int getSubtopicsNotStarted(Key uKey, Key subjectKey){
		int totalNumberOfSubtopics = getNumberOfSubtopics(subjectKey);
		int numberSubtopicsCompleted = getSubtopicsCompleted(uKey, subjectKey);
		return totalNumberOfSubtopics - numberSubtopicsCompleted;
	}
}
