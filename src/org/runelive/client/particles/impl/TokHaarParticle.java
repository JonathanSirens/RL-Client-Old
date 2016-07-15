package org.runelive.client.particles.impl;

import org.runelive.client.particles.Particle;
import org.runelive.client.particles.Position;

public final class TokHaarParticle extends Particle {

	public TokHaarParticle() {
		this.Z(new Position(0, -3, 0));
		this.getImage(new Position(0, -3, 0));
		this.getImage(19);
		this.setRGB(0xF6CD65);
		this.I(1);
		this.Z(1.5F);
		this.getImage(0.0F);
		this.I(0.075F);
		this.K();
		this.B(2304);
	}

}
