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

	private static final long serialVersionUID = -7499175721777484363L;
	public static final String STATS_SUBJECT = "subject";
	private static final String QUES_CORRECT_ANSWER = "answerCorrect";
	public static final String STATS_CORRECT = "correct";
	private QuestionMetadata(Entity e, Key stID) {
		super(e, stID);
	}

	public void setAnswer(boolean correct) {
		entity.setProperty(QUES_CORRECT_ANSWER, correct);
	}
	private void setSubjectKey(Key sKey){
		entity.setProperty(STATS_SUBJECT, sKey);
	}
	private void setCorrect(boolean b){
		entity.setProperty(STATS_CORRECT, b);
	}
	public Key getSubjectKey(){
		return (Key)entity.getProperty(STATS_SUBJECT);
	}
	public boolean getCorrect(){
		return (boolean)entity.getProperty(STATS_CORRECT);
	}

	/*
	 * Return false if never answered this question or answered it incorrectly
	 */
	public boolean getAnswerCorrect() {
		Boolean correct = (Boolean) entity.getProperty(QUES_CORRECT_ANSWER);
		return correct == null ? false : correct;
	}
	
	public static QuestionMetadata createQuestionMetadata(Key uID, Question ques){
		Entity e = new Entity(USER_METADATA);
		e.setProperty(UserMaterialMetadata.MATERIAL_TYPE, UserMaterialMetadata.MaterialType.QUESTION.val);
		QuestionMetadata temp = new QuestionMetadata(e, ques.getSubtopicKey());
		temp.setMaterialID(ques.getKey());
		temp.setUserID(uID);
		temp.setFlagged(false);
		temp.setMaterialRating(-1);
		temp.setAnswer(false);
		temp.setSubjectKey(ques.getSubject());
		temp.save();
		return temp;
	}
	
	public static QuestionMetadata getQuestionMetadata(Key uID, Question ques){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter userIdFilter =
				new FilterPredicate(USERID,
				   FilterOperator.EQUAL,
				   uID);
		Filter materialIdFilter = 
				new FilterPredicate(MATERIALID,
					FilterOperator.EQUAL,
					ques.getKey());
		Filter materialTypeFilter = new FilterPredicate(UserMaterialMetadata.MATERIAL_TYPE,
				FilterOperator.EQUAL,
				UserMaterialMetadata.MaterialType.QUESTION.val);
		Filter combinedFilter =
				CompositeFilterOperator.and(userIdFilter, materialIdFilter, materialTypeFilter);
		
		Query q = new Query(USER_METADATA).setFilter(combinedFilter);
		try{
			PreparedQuery pq = datastore.prepare(q);
			Entity metadata = pq.asSingleEntity();
			if(metadata != null){
				return new QuestionMetadata(metadata, ques.getSubtopicKey());
			}else {
				return null;
			}
		} catch(PreparedQuery.TooManyResultsException e){
			e.printStackTrace();
			return null;
		}
	}
}
