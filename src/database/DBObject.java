package database;

import java.io.Serializable;

import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public abstract class DBObject implements Serializable {
	
	private static final long serialVersionUID = -5078184817336657562L;
	
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
	
	@Override
	public boolean equals(Object obj){
		if(obj == null || !(obj instanceof DBObject))
			return false;
		return entity.getKey().equals(((DBObject)obj).getKey());
	}
}
