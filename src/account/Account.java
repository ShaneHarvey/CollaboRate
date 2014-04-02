package account;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.PreparedQuery;

import java.io.Serializable;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class Account implements Serializable {
	private static final String passwordRegEx = "^(.{0,}(([a-zA-Z][^a-zA-Z])|"
			+ "([^a-zA-Z][a-zA-Z])).{4,})|(.{1,}(([a-zA-Z][^a-zA-Z])|"
			+ "([^a-zA-Z][a-zA-Z])).{3,})|(.{2,}(([a-zA-Z][^a-zA-Z])|"
			+ "([^a-zA-Z][a-zA-Z])).{2,})|(.{3,}(([a-zA-Z][^a-zA-Z])|"
			+ "([^a-zA-Z][a-zA-Z])).{1,})|(.{4,}(([a-zA-Z][^a-zA-Z])|"
			+ "([^a-zA-Z][a-zA-Z])).{0,})$";
	private static final String emailRegEx = "^[_a-zA-Z0-9-]+(.[_a-zA-Z0-9-]+)@[a-zA-Z0-9-]+(.[a-zA-Z0-9-]+)(.[a-z]{2,4})$";
	// Different types of actors
	public enum ActorType { USER(0), ADMIN(1), TRUSTED_USER(2) ;
		public final long val;
		private ActorType(int val) {
			this.val = val;
		}
		// Given value of enum, get enum
		public static ActorType fromValue(long value) {  
	        for (ActorType at: ActorType.values()) {  
	            if (at.val == value) {  
	                return at;  
	            }  
	        }  
	        return null;  
	    }  
	}
	
	// Entity keys
	private static final String ENT_ACCOUNT_EMAIL= "email";
	private static final String ENT_DISPLAY_NAME = "displayName";
	private static final String ENT_PASSWORD= "password";
	private static final String ENT_ACTOR_TYPE = "actorType";
	private static final String ENT_ACCOUNT = "account";
	
	// Instance variables
	private final String email;//stores the actors email address
	private final long actorType;//stores the actors actorType
	private final Key key;//store the key for the datastore entity
	private String password;//stores the actors account password
	private String displayName;//stores the actors display name
	
	/**
	 * Constructor for the Account object which constructs an account object that is stored in the session
	 * @param user is the DataStore entity which has all the account properties
	 */
	private Account(Entity user){
		key = user.getKey();
		email = (String)user.getProperty(ENT_ACCOUNT_EMAIL);
		password = (String)user.getProperty(ENT_PASSWORD);
		displayName = (String)user.getProperty(ENT_DISPLAY_NAME);
		actorType = (long)user.getProperty(ENT_ACTOR_TYPE);
	}
	
	/**
	 * Getter for the email address for this Account
	 * @return string which represents the email
	 */
	public String getEmail(){
		return email;
	}
	
	/**
	 * Getter for the displayName for this Account
	 * @return string which represents the displayName
	 */
	public String getDisplayName(){
		return displayName;
	}
	
	/**
	 * Set the password of this account
	 * @param password The new password for this Account
	 */
	public void setPassword(String pass) {
		password = pass;
	}
	
	/**
	 * Set the displayName of this Account
	 * @param name The new display name of this Account
	 */
	public void setDisplayName(String name) {
		this.displayName = name;
	}
	
	/**
	 * Getter for the actorType of the Account
	 * @return string which represents the actorType
	 */
	public ActorType getType(){
		return ActorType.fromValue(actorType);
	}
	
	/**
	 * Update this Account
	 */
	public boolean updateDB(){
		try{
			DatastoreServiceFactory.getDatastoreService().put(getEntity());
			return true;
		}
		catch (Exception e){
			return false;
		}
	}
	
	/**
	 * Get an entity representation of this Account
	 */
	private Entity getEntity(){
		if(this.key == null)
			return null;
		// Create Account
		Entity ent = new Entity(ENT_ACCOUNT, this.key);
		ent.setProperty(ENT_ACCOUNT_EMAIL, this.email);
		ent.setProperty(ENT_PASSWORD, this.password);
		ent.setProperty(ENT_ACTOR_TYPE, this.actorType);
		ent.setProperty(ENT_DISPLAY_NAME, this.displayName);
		return ent;
	}
	
	/**
	 * Verify that this is the users password
	 * 
	 * @param password Potential password of this account
	 * @return Do the passwords match?
	 */
	public boolean verifyPassword(String pass) {
		return password.equals(pass);
	}
	
	/**
	 * Method that is called by SignUpServlet to create a new user actor
	 * @param email email address associated with the account
	 * @param password associated with the account
	 * @return account object we construct
	 */
	public static Account createUserAccount(String email, String password){
		Pattern passwordRegex = Pattern.compile(passwordRegEx);
		Pattern  emailRegex = Pattern.compile(emailRegEx);
		Matcher passwordMatcher = passwordRegex.matcher(password);
		Matcher emailMatcher = emailRegex.matcher(email);
		if (!passwordMatcher.matches() || !emailMatcher.matches()){	
			return null;
		}

		// Check if the email exists in the datastore
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter emailFilter = new FilterPredicate(ENT_ACCOUNT_EMAIL,
				   FilterOperator.EQUAL,
				   email);
		Query q = new Query(ENT_ACCOUNT).setFilter(emailFilter);
		PreparedQuery pq = datastore.prepare(q);
		if( pq.countEntities(FetchOptions.Builder.withLimit(1)) != 0){
			return null;
		}
		// Create Account
		Entity newUser = new Entity(ENT_ACCOUNT);
		newUser.setProperty(ENT_ACCOUNT_EMAIL, email);
		newUser.setProperty(ENT_PASSWORD, password);
		newUser.setProperty(ENT_ACTOR_TYPE, ActorType.USER.val);
		DatastoreServiceFactory.getDatastoreService().put(newUser);
		return new Account(newUser);		
	}
	
	/**
	 * Method that is called by LoginServlet when an user wants to login. Verify the email and checks if the passwords match
	 * @param email account email address
	 * @param password account password
	 * @return return the Account object that will be stored in the session 
	 */
	public static Account loadAccount(String email){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter emailFilter =
				new FilterPredicate(ENT_ACCOUNT_EMAIL,
				   FilterOperator.EQUAL,
				   email);
		Query q = new Query(ENT_ACCOUNT).setFilter(emailFilter);
		try{
			PreparedQuery pq = datastore.prepare(q);
			Entity account = pq.asSingleEntity();
			if(account != null){
				return new Account(account);
			}else {
				return null;
			}
		} catch(PreparedQuery.TooManyResultsException e){
			return null;
		}
	}
}
