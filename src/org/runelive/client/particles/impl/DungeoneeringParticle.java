package org.runelive.client.particles.impl;

import org.runelive.client.particles.Particle;
import org.runelive.client.particles.Position;

public final class DungeoneeringParticle extends Particle {

	public DungeoneeringParticle() {
		this.Z(new Position(0, -1, 0));
		this.getImage(new Position(0, -1, 0));
		this.getImage(29);
		this.setRGB(0);
		this.I(2);
		this.Z(2.0F);
		this.getImage(0.05F);
		this.I(0.095F);
		this.K();
		this.B(0);
	}
}
