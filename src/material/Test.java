package material;

import java.io.Serializable;
import java.util.ArrayList;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

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
		test.setProperty(TEST_PASSED, false);
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

	public static Test createTest(Key uID, Key subjectID, Key subtopicID) {
		Entity testEntity = new Entity(TEST);
		Test t = new Test(testEntity);
		t.setSubjectKey(subjectID);
		t.setSubtopicKey(subtopicID);
		t.setUserKey(uID);
		t.setQuestionList(Question.getRandomQuestions(MAX_QUESTION_COUNT,
				subtopicID));
		return t;
	}

	public boolean gradeTest() {
		boolean passed = ((numCorrectAnswers/questionList.size()) >= MINIMUM_GRADE);
		if(passed)
			setPassed();
		this.save();
		return passed;
	}

	/**
	 * Gets the current question.
	 * 
	 * @return the current question or null if the test is over
	 */
	public Question getCurrentQuestion(){
		if (currentQuestion < questionList.size())
			return questionList.get(currentQuestion);
		else
			return null;
	}
	/**
	 * Records the result of a question.
	 * 
	 * @param isCorrect
	 *           if user got this answer correct
	 * @return boolean
	 * 			  true if the test is over
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
