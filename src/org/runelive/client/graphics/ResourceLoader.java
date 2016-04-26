package org.runelive.client.graphics;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.HashMap;

import org.runelive.client.Signlink;

public class ResourceLoader {
	
	private static final HashMap<String, Image> loadedImages = new HashMap<String, Image>();

	public Image getImage(String imageName) {
		if(loadedImages.containsKey(imageName))
			return loadedImages.get(imageName);
		Image img = null;
		try {
			img = Toolkit.getDefaultToolkit().getImage(Signlink.getCacheDirectory() + ""+imageName+".png");
		} catch(Exception e) {
			e.printStackTrace();
			img = null;
		}
		if(img != null)
			loadedImages.put(imageName, img);
		return img;
	}

}
