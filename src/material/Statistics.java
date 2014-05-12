package material;

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

public class Statistics {
	private static final int max = Integer.MAX_VALUE;
	/**
	 * Generate the count on the number of questions the user has attempted.
	 * @param uKey The user key we are generating the query on
	 * @param sKey The subject key where the number of questions will be counted
	 * @return the number of questions attempted in a subject by a user
	 */
	public static int getNumberQuestionsCompleted(Key uKey, Key sKey){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter userFilter = new FilterPredicate(QuestionMetadata.USERID,
				FilterOperator.EQUAL, uKey);
		Filter subjectFilter = new FilterPredicate(QuestionMetadata.STATS_SUBJECT,
				FilterOperator.EQUAL, sKey);
		Filter user_Subject_Filter =
				  CompositeFilterOperator.and(userFilter, subjectFilter);
		Query photoQuery = new Query(QuestionMetadata.USER_METADATA).setFilter(user_Subject_Filter);
		return datastore.prepare(photoQuery).countEntities(FetchOptions.Builder.withLimit(max));
	}
	
	/**
	 * Function counts the number of questions the user answered correctly.
	 * @param uKey The user key that we are generating the count on
	 * @param sKey The subject that the count will be generated on
	 * @return the integer which represents the count on the number of answered correctly in a subject
	 */
	public static int getNumberQuestionsCorrect(Key uKey, Key sKey){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter userFilter = new FilterPredicate(QuestionMetadata.USERID,
				FilterOperator.EQUAL, uKey);
		Filter subjectFilter = new FilterPredicate(QuestionMetadata.STATS_SUBJECT,
				FilterOperator.EQUAL, sKey);
		Filter correctFilter = new FilterPredicate(QuestionMetadata.QUES_CORRECT_ANSWER,
				FilterOperator.EQUAL, true);
		Filter user_Subject_Filter =
				  CompositeFilterOperator.and(userFilter, subjectFilter, correctFilter);
		Query photoQuery = new Query(QuestionMetadata.USER_METADATA).setFilter(user_Subject_Filter);
		return datastore.prepare(photoQuery).countEntities(FetchOptions.Builder.withLimit(max));
	}
	
	/**
	 * Get the percentage of correct at the subject level for a user
	 * @param uKey The user Key the query will be performed on
	 * @param sKey The subject Key the query will be performed on 
	 * @return the double representing the percentage correct
	 */
	public static double getPercentageCorrect(Key uKey, Key sKey){
		int totalQuestions = getNumberQuestionsCompleted(uKey, sKey);
		int correctQuestions = getNumberQuestionsCorrect(uKey, sKey);
		return  ((double)correctQuestions/totalQuestions)* 100;
	}
	
	/**
	 * The counts the number of questions there are for a given subject
	 * @param subKey the subject key for the query
	 * @return the count on the number of questions there are in a subject
	 */
	public static int getNumberOfQuestionsForSubject(Key subKey){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter subjectFilter = new FilterPredicate(Material.MATERIAL_SUBJECT,
				FilterOperator.EQUAL, subKey);
		Query photoQuery = new Query(Question.QUESTION).setFilter(subjectFilter);
		return datastore.prepare(photoQuery).countEntities(FetchOptions.Builder.withLimit(max));
	}
	
	/**
	 * Generates the number of subtopics for a given subject
	 * @param subjectKey the subject key for a given subject
	 * @return the count on the number of subtopics for a given subject
	 */
	public static int getNumberOfSubtopics(Key subjectKey){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter subjectFilter = new FilterPredicate(Subtopic.ENT_SUBTOPIC_SUBJECT,
				FilterOperator.EQUAL, subjectKey);
		Query photoQuery = new Query(Subtopic.ENT_SUBTOPIC).setFilter(subjectFilter);
		return datastore.prepare(photoQuery).countEntities(FetchOptions.Builder.withLimit(max));
	}
	
	/**
	 * Generates the number of passed subtopics for a given subtopic
	 * @param uKey the user key to generate the query on
	 * @param subjectKey the subject key to generate the query on
	 * @return the count on the number of subtopics completed
	 */
	public static int getSubtopicsCompleted(Key uKey, Key subjectKey){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter userFilter = new FilterPredicate(Test.TEST_USER_ID,
				FilterOperator.EQUAL, uKey);
		Filter subjectFilter = new FilterPredicate(Test.TEST_SUBJECT,
				FilterOperator.EQUAL, subjectKey);
		Filter completedFilter = new FilterPredicate(Test.TEST_PASSED,
				FilterOperator.EQUAL, true);
		Filter userSubjectCompletedFilter =
				  CompositeFilterOperator.and(userFilter, subjectFilter, completedFilter);
		Query photoQuery = new Query(Test.TEST).setFilter(userSubjectCompletedFilter);
		return datastore.prepare(photoQuery).countEntities(FetchOptions.Builder.withLimit(max));
	}
	
	/**
	 * Generates the number of subtopics not passed by a user on a subtopic
	 * @param uKey the user key to generate the query on 
	 * @param subjectKey the subject key to generate the query on 
	 * @return the count on the number of subtopics not started on a subtopic
	 */
	public static int getSubtopicsNotStarted(Key uKey, Key subjectKey){
		int totalNumberOfSubtopics = getNumberOfSubtopics(subjectKey);
		int numberSubtopicsCompleted = getSubtopicsCompleted(uKey, subjectKey);
		return totalNumberOfSubtopics - numberSubtopicsCompleted;
	}
	
	/**
	 * Checks if the given subtopic is passed or failed
	 * @param uKey the user key to generate the query on 
	 * @param subtopicKey the subject key to generate the query on
	 * @return the boolean value that true if the passed a subtopic or false otherwise
	 */
	public static boolean getSubtopicPassedStatus(Key uKey, Key subtopicKey){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter userFilter = new FilterPredicate(Test.TEST_USER_ID,
				FilterOperator.EQUAL, uKey);
		Filter subjectFilter = new FilterPredicate(Test.TEST_SUBTOPIC,
				FilterOperator.EQUAL, subtopicKey);
		Filter userSubtopicPassedFilter =
				  CompositeFilterOperator.and(userFilter, subjectFilter);
		Query photoQuery = new Query(Test.TEST).setFilter(userSubtopicPassedFilter);
		try{
			PreparedQuery pq = datastore.prepare(photoQuery);
			Entity test = pq.asSingleEntity();
			if(test != null){
				boolean check = (boolean)test.getProperty(Test.TEST_PASSED);
				if(check){
					return true;
				}
			}
			return false;
		} catch(PreparedQuery.TooManyResultsException e){
			e.printStackTrace();
			return false;
		}
	}
}
