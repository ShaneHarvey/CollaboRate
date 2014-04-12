package material;

import com.google.appengine.api.datastore.Key;

public class FlaggedMaterial implements Comparable  {
	private Key materialID;
	private int numFlagged =0;
	public FlaggedMaterial(Key mID, int nFlagged){
		this.materialID=mID;
		this.numFlagged=nFlagged;
	}
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		if(o instanceof FlaggedMaterial){
			FlaggedMaterial temp =(FlaggedMaterial)o;
			return this.numFlagged - temp.numFlagged;
		}
		else {
			return -1;
		}
		
	}
	
}
