package org.runelive.client.particles;

import org.runelive.client.graphics.SpriteLoader2;
import org.runelive.client.Client;

final class MC extends QC {

	MC() {
		this.Z(new Vector3(0, -1, 0));
		this.getImage(new Vector3(0, -1, 0));
		this.getImage(20);
		this.I(0.002F);
		this.setImage(Client.cacheSprite2[88]);
		this.K();
	}

}
