package org.ikov.client.particles;

public class Vector3 {

	public static final Vector3 VECTOR_3 = new Vector3(0, 0, 0);
	private int x;
	private int y;
	private int z;

	public Vector3(int var1, int var2, int var3) {
		this.x = var1;
		this.y = var2;
		this.z = var3;
	}

	public final int I() {
		return this.x;
	}

	public final int Z() {
		return this.y;
	}

	public final int C() {
		return this.z;
	}

	public final Vector3 I(Vector3 var1) {
		return new Vector3(this.x - var1.x, this.y - var1.y, this.z - var1.z);
	}

	public final Vector3 I(float var1) {
		return new Vector3((int) ((float) this.x / var1), (int) ((float) this.y / var1), (int) ((float) this.z / var1));
	}

	public final Vector3 Z(Vector3 var1) {
		this.x += var1.x;
		this.y += var1.y;
		this.z += var1.z;
		return this;
	}

	public final Vector3 B() {
		return new Vector3(this.x, this.y, this.z);
	}

	public final String toString() {
		return "vector{x=" + this.x + ", y=" + this.y + ", z=" + this.z + '}';
	}

}
