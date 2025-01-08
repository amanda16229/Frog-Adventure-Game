package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class ObjKey extends SuperObject{
	
	public ObjKey() {
		name = "Key";
		
		try{
			img = ImageIO.read(getClass().getResourceAsStream(""));
			
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

}
