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

import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class Account {
	private String email;
	private String password;
	private String displayName;
	private Key key;
	private DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	
	private Account(Entity user){
		key = user.getKey();
		email = (String)user.getProperty(USER_EMAIL);
		password = (String)user.getProperty(PASSWORD);
		displayName = (String)user.getProperty(DISPLAY_NAME);
	}
	
	public static Account createAccount(String email, String password){
		Pattern passwordRegex = Pattern.compile("^(.{0,}(([a-zA-Z][^a-zA-Z])|"
				+ "([^a-zA-Z][a-zA-Z])).{4,})|(.{1,}(([a-zA-Z][^a-zA-Z])|"
				+ "([^a-zA-Z][a-zA-Z])).{3,})|(.{2,}(([a-zA-Z][^a-zA-Z])|"
				+ "([^a-zA-Z][a-zA-Z])).{2,})|(.{3,}(([a-zA-Z][^a-zA-Z])|"
				+ "([^a-zA-Z][a-zA-Z])).{1,})|(.{4,}(([a-zA-Z][^a-zA-Z])|"
				+ "([^a-zA-Z][a-zA-Z])).{0,})$");
		Pattern  emailRegex = Pattern.compile("^[_a-zA-Z0-9-]+(.[_a-zA-Z0-9-]+)@[a-zA-Z0-9-]+(.[a-zA-Z0-9-]+)(.[a-zA-Z]{2,4})$");
		Matcher passwordMatcher = passwordRegex.matcher(password);
		Matcher emailMatcher = emailRegex.matcher(email);
		if (!passwordMatcher.matches() || !emailMatcher.matches()){
			return null;
		}
		// Check if the email exists in the datastore
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter emailFilter = new FilterPredicate(USER_EMAIL,
				   FilterOperator.EQUAL,
				   email);
		Query q = new Query(ACCOUNT).setFilter(emailFilter);
		PreparedQuery pq = datastore.prepare(q);
		if( pq.countEntities(FetchOptions.Builder.withLimit(1)) != 0){
			return null;
		}
		// Create Account
		Entity newUser = new Entity(ACCOUNT);
		newUser.setProperty(USER_EMAIL, email);
		newUser.setProperty(PASSWORD, password);
		DatastoreServiceFactory.getDatastoreService().put(newUser);
		return new Account(newUser);		
	}
	
	public static Account verifyAccount(String email, String password){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter emailFilter =
				new FilterPredicate(USER_EMAIL,
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
}
