package org.ikov.client.particles;

import org.ikov.client.graphics.SpriteLoader2;
import org.ikov.client.graphics.Sprite;

import java.util.Random;

public class QC {

	public static final Random I = new Random(System.currentTimeMillis());
	public static QC[] Z = new QC[] { new MC(), new NC(), new OC(), new PC() };
	private float currentTimeMillis = 1.0F;
	private float C = 1.0F;
	private int B = -1;
	private int D = -1;
	private Vector3 F;
	private Vector3 J;
	private int S;
	private int E;
	private Sprite particleSprite;
	private float H;
	private float K;
	private QB M;
	private Vector3 N;
	private int O;
	private float P;
	private float Q;

	public QC() {
		this.F = Vector3.VECTOR_3;
		this.J = Vector3.VECTOR_3;
		this.S = 1;
		this.E = 1;
		this.H = 1.0F;
		this.K = 0.05F;
		this.M = new UC(Vector3.VECTOR_3);
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

	public final int J() {
		return this.B;
	}

	public final void Z(int var1) {
		this.B = var1;
	}

	public final Vector3 S() {
		return this.F;
	}

	public final void Z(Vector3 var1) {
		this.F = var1;
	}

	public final void getImage(Vector3 var1) {
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

	public final Vector3 G() {
		return this.N;
	}

	public final int H() {
		return this.O;
	}

	public final void K() {
		this.P = (this.C - this.currentTimeMillis) / ((float) this.S * 1.0F);
		this.O = (this.D - this.B) / this.S;
		this.N = this.J.I(this.F).I((float) this.S);
		this.Q = (this.K - this.H) / (float) this.S;
	}

}
