package material;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import account.Account;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;

import database.DBObject;


public class Subject extends DBObject implements Serializable{

	private static final long serialVersionUID = 5487744678389202343L;
	public static final String ENT_SUBJECT_TITLE = "subjectTitle";
	public static final String ENT_SUBJECT_DESCRIPTION ="subjectDescription";
	public static final String ENT_SUBJECT ="subject";
	public static final String ENT_SUBJECT_CATEGORY = "category";
	private ArrayList<Subtopic> subtopicsList;
	/**
	 * Constructor for the Subject class. Sets the subjectEntity to the passed entity
	 * @param sEntity
	 */
	protected Subject(Entity ent){
		super(ent);
	}
	/**
	 * Gets a subtopic from a given key
	 * @param subjectKey string representation of the subject entity key
	 * @return the Subject object
	 */
	public static Subject getSubject(Key sKey){
		try {
			Entity subjectE = DatastoreServiceFactory.getDatastoreService().get(sKey);
			return new Subject(subjectE);
		} catch (EntityNotFoundException e) {
			return null;
		}
	}
	/**
	 * Given the string representation of a subject key, return the subject
	 * @param sKey the string representation of the key
	 * @return The subject with this key
	 */
	public static Subject getFromKeyString(String sKey) {
		return getSubject(KeyFactory.stringToKey(sKey));
	}
	
	/**
	 * Create a new subject. 
	 * @param sTitle The title of the subject
	 * @param sDescription the description of the subject
	 * @return the Subject object
	 */
	public static Subject createSubject(String sTitle, String sDescription, String[] subtopics, Key cKey){
		Entity subjectE = new Entity(ENT_SUBJECT);
		//TODO check to make sure sTitle does not match a Subject title in the datastore
		Subject s = new Subject(subjectE);
		s.setTitle(sTitle);
		s.setDescription(sDescription);
		s.setCategory(cKey);
		s.save();
		long order = 0;
		for(String st: subtopics) {
			Subtopic.createSubtopic(st, s.getKey(), st, order);
			order ++;
		}
		return s;
	}
	public static Subject createSubject(String sTitle, String sDescription, Key cKey){
		Entity subjectE = new Entity(ENT_SUBJECT);
		//TODO check to make sure sTitle does not match a Subject title in the datastore
		Subject s = new Subject(subjectE);
		s.setTitle(sTitle);
		s.setDescription(sDescription);
		s.setCategory(cKey);
		s.save();
		return s;
	}
	public static Subject createSubject(String sTitle, String sDescription,ArrayList<RequestSubtopic> subtopics, Key cKey){
		Entity subjectE = new Entity(ENT_SUBJECT);
		//TODO check to make sure sTitle does not match a Subject title in the datastore
		Subject s = new Subject(subjectE);
		s.setTitle(sTitle);
		s.setDescription(sDescription);
		s.setCategory(cKey);
		s.save();
		long order = 0;
		for(RequestSubtopic st: subtopics) {
			Subtopic.createSubtopic(st.getTitle(), st.getKey(), st.getDescription(), st.getOrder());
			order ++;
		}
		return s;
	}
	protected void setTitle(String subjectTitle){
		entity.setProperty(ENT_SUBJECT_TITLE, subjectTitle);
	}
	protected void setDescription(String subjectDescription){
		entity.setProperty(ENT_SUBJECT_DESCRIPTION, subjectDescription);
	}
	protected void setCategory(Key cKey){
		entity.setProperty(ENT_SUBJECT_CATEGORY, cKey);
	}
	public Key getCategory(){
		return (Key)entity.getProperty(ENT_SUBJECT_CATEGORY);
	}
	public String getTitle(){
		return (String)entity.getProperty(ENT_SUBJECT_TITLE);
	}
	public String getDescription(){
		return (String)entity.getProperty(ENT_SUBJECT_DESCRIPTION);
	}
	public static ArrayList<Subject> getAllSubjects(){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query photoQuery = new Query(ENT_SUBJECT).addSort(ENT_SUBJECT_TITLE, SortDirection.ASCENDING);  
		PreparedQuery pq = datastore.prepare(photoQuery);
		ArrayList<Subject> subjects = new ArrayList<Subject>();
		for (Entity result : pq.asIterable()) {
			Subject tempSubject = new Subject(result);
			subjects.add(tempSubject);
		}
		return subjects;
	}
	/**
	 * Get a list of all of the subtopics under this subject
	 * @return An array of all subtopics
	 */
	public ArrayList<Subtopic> getSubtopics(){
		if(subtopicsList == null)
			subtopicsList = Subtopic.getSubtopics(entity.getKey());
		return subtopicsList;
	}
	public static void insertDemoSubjects(String sTitle, String sDescription){
		Entity subjectE = new Entity(ENT_SUBJECT);
		Subject s = new Subject(subjectE);
		s.setTitle(sTitle);
		s.setDescription(sDescription);
		s.save();
	}
	
	/**
	 * Check if the user is trusted with this subject
	 * 
	 * @param acc The user
	 * @return Is user trusted with this subject
	 */
	public boolean userTrusted(Account acc) {
		if(acc == null)
			return false;
		if(acc.getType() == Account.ActorType.ADMIN)
			return true;
		else {
			// For each subtopic under this subject, check if this user
			// has finished a test under that subtopic
			for(Subtopic s : getSubtopics()) {
				Test t = Test.getTest(acc, s);
				if(t == null || !t.getPassed())
					return false;
			}
			// If made it to this point, user completed tests for all subtopics
			return true;
		}
	}
	
	/**
	 * Static version of userTrusted
	 */
	public static boolean userTrusted(Subject sub, Account acc) {
		return sub.userTrusted(acc);
	}
	
	/**
	 * @return A list of all unverified questions for this subject
	 */
	public ArrayList<Question> getUnverifiedQuestions(){
		return Question.getUnverifiedQuestions(this);
	}
	
	
	
	public boolean saveAllSubtopics(){
		if(subtopicsList == null || subtopicsList.isEmpty()){
			return false;
		}
		
		for(Subtopic s: subtopicsList){
			s.save();
		}
		return true;
	}
	
	/**
	 * Change the place of a given subtopic in the current subject. Fails if the subtopics have not been 
	 * loaded. Subtopics have been loaded iff getSubtopics method was called.
	 * @param subtopic
	 * @param newOrder - the new place in the list to put this subtopic
	 * @return boolean
	 */
	public boolean changeOrder(Subtopic subtopic, long newOrder){
		
		if(subtopicsList == null || subtopicsList.isEmpty()){
			return false;
		}
		//bounds check
		if (newOrder < 0) {
			return this.removeSubtopic(subtopic.getOrder());
		} else if (newOrder >= subtopicsList.size()) {
			newOrder = subtopicsList.size()-1;
		}
		int i = (int)subtopic.getOrder();
		subtopicsList.remove(i);//remove the subtopic that is being moved
		subtopicsList.add((int)newOrder, subtopic);
		
		int counter = 0;
		for(Subtopic s: subtopicsList){
			s.setOrder(counter++);
		}
		
		this.save();
		return this.saveAllSubtopics();
		
	}
	
	
	/**
	 * Add the given subtopic to this subject at the desired position
	 * @param newSubtopic
	 * @param order
	 * @return
	 */
	public boolean insertSubtopic(RequestSubtopic reqSubtopic, long order){
		if(subtopicsList == null)
			getSubtopics();
		/*if(subtopicsList == null || subtopicsList.isEmpty()){
			return false;
		}*/
		//bounds check
		if(order<0 || order>subtopicsList.size()){
			order = subtopicsList.size();
		}
		Subtopic newSubtopic = Subtopic.createSubtopic(reqSubtopic.getTitle(), reqSubtopic.getSubjectKey(), reqSubtopic.getDescription(), reqSubtopic.getOrder());
		newSubtopic.setOrder(order);
		subtopicsList.add((int)order,newSubtopic);
		
		int counter = 0;
		for(Subtopic s: subtopicsList){
			s.setOrder(counter++);
		}
		
		reqSubtopic.delete();//get rid of request since it has been added
		this.save(); //save the subject
		return saveAllSubtopics();	//save all the subtopics because you may have changed some of their orders
	}
	
	/**
	 * add the given subtopic to this subject at the end of the subtopic list
	 * @param newSubtopic
	 * @return
	 */
	public boolean insertSubtopic(RequestSubtopic reqSubtopic){
		if(subtopicsList == null || subtopicsList.isEmpty()){
			return false;
		}
		Subtopic newSubtopic = Subtopic.createSubtopic(reqSubtopic.getTitle(), reqSubtopic.getSubjectKey(), reqSubtopic.getDescription(), reqSubtopic.getOrder());
		newSubtopic.setOrder(subtopicsList.size());
		subtopicsList.add(subtopicsList.size(),newSubtopic);
		
		
		int counter = 0;
		for(Subtopic s: subtopicsList){
			s.setOrder(counter++);
		}
		
		reqSubtopic.delete();
		this.save();
		//newSubtopic.save();
		return saveAllSubtopics();
	}
	
	public boolean removeSubtopic(long order){
		if(subtopicsList == null || subtopicsList.isEmpty()){
			return false;
		}
		
		Subtopic toDelete = subtopicsList.remove((int)order);
		
		int counter = 0;
		for(Subtopic s: subtopicsList){
			s.setOrder(counter++);
		}
		
		toDelete.delete();
		this.save();
		return saveAllSubtopics();

	}
	
	public static ArrayList<Subject> getCategorySubjects(Key cKey) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Filter categoryFilter = new FilterPredicate(ENT_SUBJECT_CATEGORY,
				FilterOperator.EQUAL, cKey);
		Query subtopicQuery = new Query(ENT_SUBJECT).setFilter(
				categoryFilter).addSort(ENT_SUBJECT_TITLE, SortDirection.ASCENDING);
		PreparedQuery pq = datastore.prepare(subtopicQuery);
		ArrayList<Subject> subjects = new ArrayList<Subject>();
		for (Entity result : pq.asIterable()) {
			Subject tempSubject = new Subject(result);
			subjects.add(tempSubject);
		}
		return subjects;
	}
	public static ArrayList<Subject> getCategorySubjects(String cKey){
		Key categoryKey = KeyFactory.stringToKey(cKey);
		return getCategorySubjects(categoryKey);
	}
	//public boolean change
	/**
	 * deep delete, overrides and calls DataBaseObject delete
	 */
	@Override
	public void delete(){
		if(subtopicsList == null){
			getSubtopics();
		}
		
		for(Subtopic s: subtopicsList){
			s.delete();//will cascade down to materials, metadata.
		}
		
		super.delete();//delete this entity
	}
	
}
