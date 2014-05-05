package material;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

public class DayCount implements Comparable<DayCount>{
	Entity ent;
	private int timesAttempted =0;
	
	public DayCount(Entity ent, int attempted){
		this.ent = ent;
		this.timesAttempted=attempted;
	}
	
	@Override
	public int compareTo(DayCount o) {
		return this.timesAttempted- o.timesAttempted;
	}
	
	public long getMaterialType() {
		return (long) ent.getProperty(UserMaterialMetadata.MATERIAL_TYPE);
	}
	
	public Key getKey() {
		return (Key)ent.getProperty(UserMaterialMetadata.MATERIALID);
	}
}
