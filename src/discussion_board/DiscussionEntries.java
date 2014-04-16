package discussion_board;

import java.util.Date;

import account.Account;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

import database.DBObject;

public abstract class DiscussionEntries extends DBObject {

	private static final long serialVersionUID = 4865163883535185476L;
	
	public static final String ENTRY_DATE = "Date Created";
	public static final String ENTRY_AUTHOR = "Author";
	public static final String ENTRY_BODY = "Body";
	private Account author;
	public DiscussionEntries(Entity ent){
		super(ent);
	}
	protected void setAuthor(Key authorID){
		entity.setProperty(ENTRY_AUTHOR, authorID);
	}
	protected void setBody(String msgBody){
		entity.setProperty(ENTRY_BODY, msgBody);
	}
	/**
	 * Sets the Discussion Entry to the current date
	 */
	protected void setDate(){
		Date currentDate = new Date();
		entity.setProperty(ENTRY_DATE, currentDate);
	}
	public Account getAuthor(){
		if(author == null)
			author = Account.getAccount((Key)entity.getProperty(ENTRY_AUTHOR));
		return author;
	}
	public Date getDate(){
		return (Date)entity.getProperty(ENTRY_DATE);
	}
	public String getBody(){
		return (String)entity.getProperty(ENTRY_BODY);
	}
	
	/**
	 * Can the given account delete the given discussion entry?
	 */
	public static boolean canDelete(DiscussionEntries ent, Account acc) {
		return (acc != null && ent != null) && (acc.getType() == Account.ActorType.ADMIN || acc.getKey().equals(ent.getAuthor().getKey()));
	}
}
