package material;

import java.io.Serializable;
import java.util.ArrayList;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

import database.DBObject;

public class Test extends DBObject implements Serializable {

	private static final long serialVersionUID = -7018562988368587595L;
	private static final String TEST = "test";
	private static final String TEST_USER_ID = "userKey";
	private static final String TEST_SUBTOPIC = "subtopicKey";
	private static final String TEST_PASSED = "passed";
	private int questionCount = 3;
	private ArrayList<Question> questionList;
	private Test(Entity test){
		super(test);
		test.setProperty(TEST_PASSED, false);
	}
	private void setUserKey(Key uID){
		entity.setProperty(TEST_USER_ID, uID);
	}
	private void setSuptopicKey(Key sID){
		entity.setProperty(TEST_SUBTOPIC, sID);
	}
	public void setPassed(){
		entity.setProperty(TEST_PASSED, true);
	}
	public boolean getPassed(){
		return (boolean)entity.getProperty(TEST_PASSED);
	}
	public Key getUserKey(){
		return (Key)entity.getProperty(TEST_USER_ID);
	}
	public Key getSubtopicKey(){
		return (Key)entity.getProperty(TEST_SUBTOPIC);
	}
	public void incrementQuestionCount(){
		this.questionCount++;
	}
	public void decrementQuestionCount(){
		this.questionCount--;
	}
	public int getQuestionCount(){
		return this.questionCount;
	}
	public void setQuestionList(ArrayList<Question> list){
		this.questionList = list;
	}
	public ArrayList<Question> getQuestionList(){
		return this.questionList;
	}
	/*public static Test createTest(Key uID, Key sID){
		Entity testEntity = new Entity(TEST);
		Test t = new Test(testEntity);
		t.setSuptopicKey(sID);
		t.setUserKey(uID);

	}*/
	
}
