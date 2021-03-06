package material;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

import database.DBObject;

public abstract class UserMaterialMetadata extends DBObject {

	private static final long serialVersionUID = 3784610917718375779L;

	/*
	 * Material types
	 */
	public enum MaterialType {
		VIDEO(0), QUESTION(1), NOTES(2);
		public final long val;

		private MaterialType(int val) {
			this.val = val;
		}

		// Given value of enum, get enum
		public static MaterialType fromValue(long value) {
			for (MaterialType mt : MaterialType.values()) {
				if (mt.val == value) {
					return mt;
				}
			}
			return null;
		}
	}

	public static final String USERID = "userID";
	public static final String MATERIALID = "materialID";
	public static final String MATERIAL_RATING = "materialRating";
	public static final String MATERIAL_FLAGGED = "materialFlagged";
	public static final String MATERIAL_VIEWED = "materialViewed";
	public static final String USER_METADATA = "user_material_metadata";// table
	public static final String METADATA_DATE = "date";																	// name
	public static final String MATERIAL_TYPE = "materialType";
	public static final String Material_SubTopic = "materialSubtopic";
	
	public UserMaterialMetadata(Entity e, Key stID) {
		super(e);
		e.setProperty(Material_SubTopic, stID);
	}

	protected void setUserID(Key uID) {
		entity.setProperty(USERID, uID);
	}

	protected void setMaterialID(Key mID) {
		entity.setProperty(MATERIALID, mID);
	}
	
	protected void setDate(){
		Date currentDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		try {
			Date todayWithZeroTime =formatter.parse(formatter.format(currentDate));
			entity.setProperty(METADATA_DATE, todayWithZeroTime);
		} catch (ParseException e) {
			entity.setProperty(METADATA_DATE, currentDate);
			//e.printStackTrace();
		}
		
	}
	
	public void setFlagged(boolean b) {
		entity.setProperty(MATERIAL_FLAGGED, b);
	}

	public void setViewed() {
		entity.setProperty(MATERIAL_VIEWED, true);
	}

	public void setMaterialRating(int rating) {
		entity.setProperty(MATERIAL_RATING, rating);
	}
	
	public Date getDate(){
		return (Date)entity.getProperty(METADATA_DATE);
	}
	
	public boolean getFlagged() {
		Boolean flagged = (Boolean) entity.getProperty(MATERIAL_FLAGGED);
		return flagged == null ? false : flagged;
	}

	public boolean getViewed() {
		Boolean viewed = (Boolean) entity.getProperty(MATERIAL_VIEWED);
		return viewed == null ? false : viewed;
	}

	public int getRating() {
		Long rating = (Long) entity.getProperty(MATERIAL_RATING);
		return rating == null ? -1 : rating.intValue();
	}
}