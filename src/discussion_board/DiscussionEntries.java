package discussion_board;

import java.util.Date;

import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

import database.DBObject;

public abstract class DiscussionEntries extends DBObject {
	public static final String ENTRY_DATE = "Date Created";
	public static final String ENTRY_AUTHOR = "Author";
	public static final String ENTRY_BODY = "Body";
	public DiscussionEntries(Entity ent){
		super(ent);
	}
	protected void setAuthor(Key authorID){
		entity.setProperty(ENTRY_AUTHOR, authorID);
	}
	protected void setEntryBody(String msgBody){
		entity.setProperty(ENTRY_BODY, msgBody);
	}
	/**
	 * Sets the Discussion Entry to the current date
	 */
	protected void setDate(){
		Date currentDate = new Date();
		entity.setProperty(ENTRY_DATE, currentDate);
	}
	public Key getAuthor(){
		return (Key)entity.getProperty(ENTRY_AUTHOR);
	}
	public Date getDate(){
		return (Date)entity.getProperty(ENTRY_DATE);
	}
	public String getEntryBody(){
		return (String)entity.getProperty(ENTRY_BODY);
	}

}
