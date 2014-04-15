package material;

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

public class QuestionMetadata extends UserMaterialMetadata{

	private static final String QUES_CORRECT_ANSWER = "answerCorrect";

	private QuestionMetadata(Entity e) {
		super(e);
	}

	public void setAnswer(boolean correct) {
		entity.setProperty(QUES_CORRECT_ANSWER, correct);
	}

	/*
	 * Return false if never answered this question or answered it incorrectly
	 */
	public boolean correctAnswer() {
		Boolean correct = (Boolean) entity.getProperty(QUES_CORRECT_ANSWER);
		return correct == null ? false : correct;
	}
	
	public static QuestionMetadata createQuestionMetadata(Key uID, Key mID){
		Entity e = new Entity(USER_METADATA);
		QuestionMetadata temp = new QuestionMetadata(e);
		temp.setMaterialID(mID);
		temp.setUserID(uID);
		temp.setFlagged(false);
		temp.setMaterialRating(-1);
		temp.setAnswer(false);
		temp.save();
		return temp;
	}
	public static QuestionMetadata getQuestionMetadata(Key uID, Key mID){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter userIdFilter =
				new FilterPredicate(USERID,
				   FilterOperator.EQUAL,
				   uID);
		Filter materialIdFilter = 
				new FilterPredicate(MATERIALID,
					FilterOperator.EQUAL,
					mID);
		Filter combinedFilter =
				CompositeFilterOperator.and(userIdFilter,materialIdFilter);

		Query q = new Query(USER_METADATA).setFilter(combinedFilter);
		try{
			PreparedQuery pq = datastore.prepare(q);
			Entity metadata = pq.asSingleEntity();
			if(metadata != null){
				return new QuestionMetadata(metadata);
			}else {
				return null;
			}
		} catch(PreparedQuery.TooManyResultsException e){
			e.printStackTrace();
			return null;
		}
	}
}