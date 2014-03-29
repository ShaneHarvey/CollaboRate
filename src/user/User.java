package user;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import static settings.Settings.*;

public class User {
	private DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	private Entity user;
	
	public User(String userName, String password){
		user = new Entity(USER_DATASTORE);
		user.setProperty(USER_NAME_PROPERTY, userName);
		user.setProperty(PASSWORD_PROPERTY, password);
		datastore.put(user);
	}
	
	public void putUser(){
		datastore.put(user);
	}
}
