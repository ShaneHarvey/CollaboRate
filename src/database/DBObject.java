package database;

import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public abstract class DBObject {
	protected Entity entity;
	
	protected DBObject(Entity ent) {
		this.entity = ent;
	}
	
	public String getKeyAsString() {
		return KeyFactory.keyToString(entity.getKey());
	}
	
	public Key getKey(){
		return entity.getKey();
	}
	
	public void save() {
		DatastoreServiceFactory.getDatastoreService().put(entity);
	}
	
	public void delete() {
		DatastoreServiceFactory.getDatastoreService().delete(entity.getKey());
	}
}
