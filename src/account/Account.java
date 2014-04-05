package account;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
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
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import hashing.PasswordHash;

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
	 * @return true if the update was successful, false if it fails
	 */
	public boolean setPassword(String password) {
		try {
			//Create secure hash
			String hash = PasswordHash.createHash(password);
			this.password = hash;
			return true;
		} catch(Exception e){
			e.printStackTrace();
			return false;
		}
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
			e.printStackTrace();
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
		try {
			Entity ent = DatastoreServiceFactory.getDatastoreService().get(this.key);
			ent.setProperty(ENT_ACCOUNT_EMAIL, this.email);
			ent.setProperty(ENT_DISPLAY_NAME, this.displayName);
			ent.setProperty(ENT_PASSWORD, this.password);
			ent.setProperty(ENT_ACTOR_TYPE, this.actorType);
			return ent;
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	/**
	 * Verify that this is the users password
	 * 
	 * @param password Potential password of this account
	 * @return Do the passwords match?
	 */
	public boolean verifyPassword(String password) {
		try {
			return PasswordHash.validatePassword(password, this.password);
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Method to verify that an email is a valid address
	 * 
	 * @param email email address
	 * @return boolean true if email is a valid address, false otherwise
	 */
	public static boolean validateEmail(String email){
		Pattern  emailRegex = Pattern.compile(emailRegEx);
		Matcher emailMatcher = emailRegex.matcher(email);
		return emailMatcher.matches();
	}
	
	/**
	 * Method to verify that a password is valid according to out specifications
	 * 
	 * @param email email address
	 * @return boolean true if email is a valid address, false otherwise
	 */
	public static boolean validatePassword(String password){
		Pattern  passwordRegex = Pattern.compile(passwordRegEx);
		Matcher passwordMatcher = passwordRegex.matcher(password);
		return passwordMatcher.matches();
	}
	
	/**
	 * Method that is called by SignUpServlet to create a new user actor
	 * @param email email address associated with the account
	 * @param password associated with the account
	 * @return account object we construct
	 */
	public static Account createUserAccount(String email, String password){
		//Make sure the email is valid and password is secure enough
		if (!validateEmail(email) || !validatePassword(password)){	
			return null;
		}

		// Check if the email exists in the datastore already
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter emailFilter = new FilterPredicate(ENT_ACCOUNT_EMAIL,
				   FilterOperator.EQUAL,
				   email);
		Query q = new Query(ENT_ACCOUNT).setFilter(emailFilter);
		PreparedQuery pq = datastore.prepare(q);
		if( pq.countEntities(FetchOptions.Builder.withLimit(1)) != 0){
			return null;
		}
		
		try {
			//Create secure hash
			String hash = PasswordHash.createHash(password);	
			// Create Account
			Entity newUser = new Entity(ENT_ACCOUNT);
			newUser.setProperty(ENT_ACCOUNT_EMAIL, email);
			newUser.setProperty(ENT_PASSWORD, hash);
			newUser.setProperty(ENT_ACTOR_TYPE, ActorType.USER.val);
			DatastoreServiceFactory.getDatastoreService().put(newUser);
			return new Account(newUser);	
		} catch(Exception e){
			e.printStackTrace();
			return null;
		}
			
	}
	
	/**
	 * Method that is called by LoginServlet when an user wants to login.
	 * 
	 * @param email account email address
	 * @return Account object associated with email that will be stored in the session 
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
				//Check password here?
				return new Account(account);
			}else {
				return null;
			}
		} catch(PreparedQuery.TooManyResultsException e){
			e.printStackTrace();
			return null;
		}
	}
}
