package main;

import object.ObjKey;

public class AssetSetter {

	GamePanel gp;
	
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setObject() {
		gp.obj[0] = new ObjKey();
		//gp.obj[0].worldX = ;
	}
}
