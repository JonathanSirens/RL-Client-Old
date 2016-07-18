package org.runelive.client.particles;

import java.util.Random;

import org.runelive.client.graphics.Sprite;
import org.runelive.client.particles.impl.*;

public class Particle {

	public static final Particle[] PARTICLE_ARRAY = new Particle[] { new MaxParticle(), new TokHaarParticle(), new DungeoneeringParticle(),
			new CompletionistParticle(), new TrimmedCompletionistParticle(), new BlackCompletionistParticle(), new GrayCompletionistParticle(),
			new WhiteCompletionistParticle(), new BlueCompletionistParticle(), new GreenCompletionistParticle(), new AquaCompletionistParticle(),
			new RedCompletionistParticle(), new PurpleCompletionistParticle(), new YellowCompletionistParticle(), new OrangeCompletionistParticle()
	};

	public static final Random I = new Random(System.currentTimeMillis());
	private float currentTimeMillis = 1.0F;
	private float C = 1.0F;
	
	/**
	 * Decimal color.
	 */
	private int rgbColor = -1;
	
	private int D = -1;
	private Position F;
	private Position J;
	private int S;
	private int E;
	private Sprite particleSprite;
	private float H;
	private float K;
	private QB M;
	private Position N;
	private int O;
	private float P;
	private float Q;

	public Particle() {
		this.F = Position.POSITION;
		this.J = Position.POSITION;
		this.S = 1;
		this.E = 1;
		this.H = 1.0F;
		this.K = 0.05F;
		this.M = new UC(Position.POSITION);
	}

	public final float I() {
		return this.H;
	}

	public final void I(float var1) {
		this.H = var1;
	}

	public final float Z() {
		return this.Q;
	}

	public final Sprite getImage() {
		return this.particleSprite;
	}

	public final void setImage(Sprite image) {
		this.particleSprite = image;
	}

	public final QB B() {
		return this.M;
	}

	public final int D() {
		return this.E;
	}

	public final void I(int var1) {
		this.E = var1;
	}

	public final float F() {
		return this.currentTimeMillis;
	}

	public final void Z(float var1) {
		this.currentTimeMillis = var1;
	}

	public final void getImage(float var1) {
		this.C = var1;
	}

	public final int getRGB() {
		return this.rgbColor;
	}

	public final void setRGB(int var1) {
		this.rgbColor = var1;
	}

	public final Position S() {
		return this.F;
	}

	public final void Z(Position var1) {
		this.F = var1;
	}

	public final void getImage(Position var1) {
		this.J = var1;
	}

	public final int A() {
		return this.S;
	}

	public final void getImage(int var1) {
		this.S = var1;
	}

	public final void B(int var1) {
		this.O = var1;
	}

	public final float E() {
		return this.P;
	}

	public final Position G() {
		return this.N;
	}

	public final int H() {
		return this.O;
	}

	public final void K() {
		this.P = (this.C - this.currentTimeMillis) / ((float) this.S * 1.0F);
		this.O = (this.D - this.rgbColor) / this.S;
		this.N = this.J.I(this.F).I((float) this.S);
		this.Q = (this.K - this.H) / (float) this.S;
	}
}