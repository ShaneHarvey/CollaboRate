package account;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.PreparedQuery;

import static settings.Settings.*;

import java.io.Serializable;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class Account implements Serializable {
	private String email;//stores the actors email address
	private String password;//stores the actors account password
	private String displayName;//stores the actors display name
	private String actorType;//stores the actors actorType
	private Key key;//store the key for the datastore entity
	private static final String passwordRegEx = "^(.{0,}(([a-zA-Z][^a-zA-Z])|"
			+ "([^a-zA-Z][a-zA-Z])).{4,})|(.{1,}(([a-zA-Z][^a-zA-Z])|"
			+ "([^a-zA-Z][a-zA-Z])).{3,})|(.{2,}(([a-zA-Z][^a-zA-Z])|"
			+ "([^a-zA-Z][a-zA-Z])).{2,})|(.{3,}(([a-zA-Z][^a-zA-Z])|"
			+ "([^a-zA-Z][a-zA-Z])).{1,})|(.{4,}(([a-zA-Z][^a-zA-Z])|"
			+ "([^a-zA-Z][a-zA-Z])).{0,})$";
	private static final String emailRegEx = "^[_a-zA-Z0-9-]+(.[_a-zA-Z0-9-]+)@[a-zA-Z0-9-]+(.[a-zA-Z0-9-]+)(.[a-z]{2,4})$";
	
	//"^(.{0,}(([a-zA-Z][^a-zA-Z])|([^a-zA-Z][a-zA-Z])).{4,})|(.{1,}(([a-zA-Z][^a-zA-Z])|([^a-zA-Z][a-zA-Z])).{3,})|(.{2,}(([a-zA-Z][^a-zA-Z])|([^a-zA-Z][a-zA-Z])).{2,})|(.{3,}(([a-zA-Z][^a-zA-Z])|([^a-zA-Z][a-zA-Z])).{1,})|(.{4,}(([a-zA-Z][^a-zA-Z])|([^a-zA-Z][a-zA-Z])).{0,})$";
					
	//private DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	
	/**
	 * Constructor for the Account object which constructs an account object that is stored in the session
	 * @param user is the DataStore entity which has all the account properties
	 */
	private Account(Entity user){
		key = user.getKey();
		email = (String)user.getProperty(ACCOUNT_EMAIL);
		password = (String)user.getProperty(PASSWORD);
		displayName = (String)user.getProperty(DISPLAY_NAME);
		actorType = (String)user.getProperty(ACTOR_TYPE);
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
		if (!passwordMatcher.matches()){	
			return null;
		}
		if (!emailMatcher.matches()){
			return null;
		}
		// Check if the email exists in the datastore
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter emailFilter = new FilterPredicate(ACCOUNT_EMAIL,
				   FilterOperator.EQUAL,
				   email);
		Query q = new Query(ACCOUNT).setFilter(emailFilter);
		PreparedQuery pq = datastore.prepare(q);
		if( pq.countEntities(FetchOptions.Builder.withLimit(1)) != 0){
			return null;
		}
		// Create Account
		Entity newUser = new Entity(ACCOUNT);
		newUser.setProperty(ACCOUNT_EMAIL, email);
		newUser.setProperty(PASSWORD, password);
		newUser.setProperty(ACTOR_TYPE, USER);
		DatastoreServiceFactory.getDatastoreService().put(newUser);
		return new Account(newUser);		
	}
	/**
	 * Method that is called by LoginServlet when an user wants to login. Verify the email and checks if the passwords match
	 * @param email account email address
	 * @param password account password
	 * @return return the Account object that will be stored in the session 
	 */
	public static Account verifyAccount(String email, String password){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter emailFilter =
				new FilterPredicate(ACCOUNT_EMAIL,
				   FilterOperator.EQUAL,
				   email);
		Query q = new Query(ACCOUNT).setFilter(emailFilter);
		try{
			PreparedQuery pq = datastore.prepare(q);
			Entity account = pq.asSingleEntity();
			if(account != null && password.equals((String)account.getProperty(PASSWORD))){
				return new Account(account);
			}else {
				return null;
			}
		} catch(PreparedQuery.TooManyResultsException e){
			return null;
		}
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
	 * Getter for the actorType of the Account
	 * @return string which represents the actorType
	 */
	public String getType(){
		return actorType;
	}
	/**
	 * Method called by AccountServlet to update the password and displayname
	 * @param email accounts associated email address
	 * @param displayName new displayname to be shown 
	 * @param currentPassword the current password
	 * @param newPassword the new password
	 * @return Account object which will be stored in the session
	 */
	public static Account updateAccount(String email, String displayName, String currentPassword, String newPassword){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter emailFilter =
				new FilterPredicate(ACCOUNT_EMAIL,
				   FilterOperator.EQUAL,
				   email);
		Query q = new Query(ACCOUNT).setFilter(emailFilter);
		try{
			PreparedQuery pq = datastore.prepare(q);
			Entity account = pq.asSingleEntity();
			//Make sure the c
			if(account != null && currentPassword.equals((String)account.getProperty(PASSWORD))){
				//Set the new Password 
				if(newPassword!=null && !newPassword.equals("")){
					account.setProperty(PASSWORD, newPassword);
				}
				//Set the new displayname
				if(displayName != null && !displayName.equals("")){
					account.setProperty(DISPLAY_NAME, displayName);
				}
				DatastoreServiceFactory.getDatastoreService().put(account);
				return new Account(account);
			}else {
				return null;
			}
		}catch (PreparedQuery.TooManyResultsException e){
			return null;
		}
	}
}
