package org.runelive.client.particles.impl;

import org.runelive.client.particles.Particle;
import org.runelive.client.particles.Position;

public final class AttackParticle extends Particle {

    public AttackParticle() {
        this.Z(new Position(0, -1, 0));
        this.getImage(new Position(0, -1, 0));
        this.getImage(29);
        this.setRGB(0);
        this.I(1);
        this.Z(2.0F);
        this.getImage(0.05F);
        this.I(0.075F);
        this.K();
        this.B(0);
    }
}
