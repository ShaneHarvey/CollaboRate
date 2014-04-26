package material;

import java.io.Serializable;
import java.util.ArrayList;

import account.Account;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

import database.DBObject;

public class Test extends DBObject implements Serializable {

	private static final long serialVersionUID = -7018562988368587595L;
	public static final String TEST = "test";
	public static final String TEST_USER_ID = "userKey";
	public static final String TEST_SUBJECT = "subjectKey";
	public static final String TEST_SUBTOPIC = "subtopicKey";
	public static final String TEST_PASSED = "passed";
	private static final int MAX_QUESTION_COUNT = 10;
	private static final double MINIMUM_GRADE = .6;
	private ArrayList<Question> questionList;
	private int currentQuestion = 0;
	private int numCorrectAnswers = 0;

	private Test(Entity test) {
		super(test);
	}

	private void setUserKey(Key uID) {
		entity.setProperty(TEST_USER_ID, uID);
	}

	private void setSubtopicKey(Key sID) {
		entity.setProperty(TEST_SUBTOPIC, sID);
	}

	private void setSubjectKey(Key sID) {
		entity.setProperty(TEST_SUBTOPIC, sID);
	}

	public void setPassed() {
		entity.setProperty(TEST_PASSED, true);
	}

	public boolean getPassed() {
		return (boolean) entity.getProperty(TEST_PASSED);
	}

	public Key getUserKey() {
		return (Key) entity.getProperty(TEST_USER_ID);
	}

	public Key getSubtopicKey() {
		return (Key) entity.getProperty(TEST_SUBJECT);
	}

	public Key getSubjectKey() {
		return (Key) entity.getProperty(TEST_SUBTOPIC);
	}

	public void setQuestionList(ArrayList<Question> list) {
		this.questionList = list;
	}

	public ArrayList<Question> getQuestionList() {
		return this.questionList;
	}

	public static Test createTest(Account acc, Subtopic st) throws Exception {
		Test t = getTest(acc, st);
		if(t != null) {
			if(!t.getPassed())
				t.delete();
			else
				throw new Exception("Test already passed");
		}
		Entity testEntity = new Entity(TEST);
		testEntity.setProperty(TEST_PASSED, false);
		t = new Test(testEntity);
		t.setSubjectKey(st.getSubject().getKey());
		t.setSubtopicKey(st.getKey());
		t.setUserKey(acc.getKey());
		t.setQuestionList(Question.getRandomVerifiedQuestions(MAX_QUESTION_COUNT,
				st.getKey()));
		return t;
	}

	/**
	 * Check if the email exists in the datastore already
	 * 
	 * @param email
	 * @return true if the email exists, false otherwise
	 */
	public static Test getTest(Account acc, Subtopic st) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Filter userFilter = new FilterPredicate(TEST_USER_ID,
				FilterOperator.EQUAL, acc.getKey());
		Filter subtopicFilter = new FilterPredicate(TEST_SUBTOPIC,
				FilterOperator.EQUAL, st.getKey());
		Filter combinedFilter =
				CompositeFilterOperator.and(userFilter, subtopicFilter);
		Query q = new Query(TEST).setFilter(combinedFilter);
		PreparedQuery pq = datastore.prepare(q);
		Entity test = pq.asSingleEntity();
		if(test != null){
			return new Test(test);
		}else {
			return null;
		}
	}

	public boolean gradeTest() {
		boolean passed = ((double)numCorrectAnswers / (double)questionList.size()) >= MINIMUM_GRADE;
		if (passed) {
			setPassed();
			this.save();
		}
		return passed;
	}
	
	/**
	 * Return what percentage of minimum grade currently completed
	 */
	public int getPercentageCorrect(){
		return (int)( (100.0 / MINIMUM_GRADE) * ((double)numCorrectAnswers / (double)questionList.size()));
	}

	/**
	 * Gets the current question.
	 * 
	 * @return the current question or null if the test is over
	 */
	public Question getCurrentQuestion() {
		if (currentQuestion < questionList.size())
			return questionList.get(currentQuestion);
		else
			return null;
	}

	/**
	 * Records the result of a question.
	 * 
	 * @param isCorrect
	 *            if user got this answer correct
	 * @return boolean true if the test is over
	 */
	public boolean logResult(boolean isCorrect) {
		if (currentQuestion < questionList.size()) {
			if (isCorrect)
				numCorrectAnswers++;
		}
		if (currentQuestion == questionList.size() - 1) {
			return true;
		}
		// Increment to next question
		currentQuestion++;
		return false;
	}
}
