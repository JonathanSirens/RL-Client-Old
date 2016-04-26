package org.runelive.client.particles;

public class KC {

	private int I;
	private int Z;
	private int rgb;
	private float B;
	private Vector3 D;
	private float alpha;
	private boolean J;
	private QC S;
	private Vector3 A;

	public final float getAlpha() {
		return this.alpha;
	}

	public final boolean Z() {
		return this.J;
	}

	public final void C() {
		if (this.S != null) {
			++this.Z;
			if (this.Z >= this.S.A()) {
				this.J = true;
			} else {
				this.rgb += this.S.H();
				this.B += this.S.E();
				this.A.Z(this.D);
				this.D.Z(this.S.G());
				this.alpha += this.S.Z();
			}
		}
	}

	public final QC B() {
		return this.S;
	}

	public final Vector3 D() {
		return this.A;
	}

	public KC(QC var1, Vector3 var2, int var3) {
		this(var1.J(), var1.F(), var1.S().B(), var1.B().I(QC.I).Z(var2), var1.I(), var3);
		this.S = var1;
	}

	public KC(int var1, float var2, Vector3 var3, Vector3 var4, float var5, int var6) {
		this.Z = 0;
		this.J = false;
		this.S = null;
		this.rgb = var1;
		this.B = var2;
		this.D = var3;
		this.A = var4;
		this.alpha = var5;
		this.I = var6;
	}

	public final int F() {
		return this.I;
	}

	public final int getRgb() {
		return this.rgb;
	}
	
	public void setRgb(int rgb) {
		this.rgb = rgb;
	}
	
	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}

		

	public final float S() {
		return this.B;
	}
}
