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

import database.DBObject;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import material.Subject;
import hashing.PasswordHash;

public class Account extends DBObject implements Serializable {
	private static final long serialVersionUID = 6746812229424845845L;
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
	private static final String ENT_ACCOUNT_EMAIL = "email";
	private static final String ENT_DISPLAY_NAME = "displayName";
	private static final String ENT_HASH = "hash";
	private static final String ENT_ACTOR_TYPE = "actorType";
	private static final String ENT_ACCOUNT = "account";
	
	/**
	 * Constructor for the Account object which constructs an account object that is stored in the session
	 * @param account is the DataStore entity which has all the account properties
	 */
	private Account(Entity account){
		super(account);
	}
	
	/**
	 * @return email of this account
	 */
	public String getEmail(){
		return (String)entity.getProperty(ENT_ACCOUNT_EMAIL);
	}
	
	/**
	 * @return hashed password of this account
	 */
	private String getHash(){
		return (String)entity.getProperty(ENT_HASH);
	}
	
	/**
	 * @return display name of this account (can be null)
	 */
	public String getDisplayName(){
		return (String)entity.getProperty(ENT_DISPLAY_NAME);
	}
	
	public String getDisplayNameOrEmail() {
		String display = getDisplayName();
		return display == null ? display : getEmail();
	}
	
	/**
	 * @return actorType of this account
	 */
	public ActorType getType(){
		return ActorType.fromValue((long)entity.getProperty(ENT_ACTOR_TYPE));
	}
	
	/**
	 * @param newEmail must be a valid email address that is not in use already.
	 */
	private void setEmail(String newEmail){
		entity.setProperty(ENT_DISPLAY_NAME, newEmail);
	}
	
	/**
	 * @param newHash must be a valid hash, i.e. the return of PasswordHash.createHash()
	 */
	private void setHash(String newHash){
		entity.setProperty(ENT_HASH, newHash);
	}
	
	/**
	 * @param name The new display name of this Account
	 */
	public void setDisplayName(String name) {
		entity.setProperty(ENT_DISPLAY_NAME, name);
	}
	
	/**
	 * @param actorType must be a valid ActorType
	 */
	public void setType(ActorType actorType){
		entity.setProperty(ENT_ACTOR_TYPE,actorType);
	}
	
	/**
	 * Change the email of this account
	 * @param currentPassword 	The current password for this Account
	 * @param newEmail 			The new email address
	 */
	public void changeEmail(String currentPassword, String newEmail) {
		try {
			if(verifyPassword(currentPassword) && validateEmail(newEmail)
					&& !isEmailInUse(newEmail)){
				setEmail(newEmail);
			}
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Change the password of this account
	 * @param oldPassword 	The new password for this Account
	 * @param newPassword 	The new password for this Account
	 * @return true if the update was successful, false if it fails
	 */
	public boolean changePassword(String currentPassword, String newPassword) {
		try {
			//Verify that the password is correct and the new password is secure
			if(verifyPassword(currentPassword) && validatePassword(newPassword)){
				setHash(PasswordHash.createHash(newPassword));
				return true;
			}
			return false;
		} catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Verify that this is the users password
	 * 
	 * @param password 	Potential password of this account
	 * @return Do the passwords match?
	 */
	public boolean verifyPassword(String password) {
		try {
			return PasswordHash.validatePassword(password, getHash());
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Method to verify that an email is a valid address
	 * 
	 * @param email 	email address to validate
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
	 * Check if the email exists in the datastore already
	 * 
	 * @param email 
	 * @return true if the email exists, false otherwise
	 */
	private static boolean isEmailInUse(String email){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter emailFilter = new FilterPredicate(ENT_ACCOUNT_EMAIL,
				   FilterOperator.EQUAL,
				   email);
		Query q = new Query(ENT_ACCOUNT).setFilter(emailFilter);
		PreparedQuery pq = datastore.prepare(q);
		if( pq.countEntities(FetchOptions.Builder.withLimit(1)) != 0){
			return true;
		}
		return false;
	}
	
	/**
	 * Method that is called by SignUpServlet to create a new user actor
	 * @param email 	email address associated with the account
	 * @param password 	associated with the account
	 * @return account object we construct
	 */
	public static Account createUserAccount(String email, String password){
		//Make sure the email is valid and password is secure enough
		if (!validateEmail(email) || !validatePassword(password)){	
			return null;
		}

		// Check if the email exists in the datastore already
		if(isEmailInUse(email)){
			return null;
		}
		
		try {
			//Create secure hash
			String hash = PasswordHash.createHash(password);	
			// Create Account
			Entity newUser = new Entity(ENT_ACCOUNT);
			newUser.setProperty(ENT_ACCOUNT_EMAIL, email);
			newUser.setProperty(ENT_HASH, hash);
			newUser.setProperty(ENT_ACTOR_TYPE, email.contains("admin") ? ActorType.ADMIN.val : ActorType.USER.val);
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
	
	public static Account getAccount(Key key){
		try {
			Entity ent = DatastoreServiceFactory.getDatastoreService().get(key);
			return new Account(ent);
		} catch (EntityNotFoundException e) {
			return null;
		}
	}
}
