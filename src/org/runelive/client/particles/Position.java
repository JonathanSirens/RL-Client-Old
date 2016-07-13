package org.runelive.client.particles;

public class Position {

	public static final Position POSITION = new Position(0, 0, 0);
	
	private int x;
	private int y;
	private int z;

	public Position(int var1, int var2, int var3) {
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

	public final Position I(Position var1) {
		return new Position(this.x - var1.x, this.y - var1.y, this.z - var1.z);
	}

	public final Position I(float var1) {
		return new Position((int) ((float) this.x / var1), (int) ((float) this.y / var1), (int) ((float) this.z / var1));
	}

	public final Position Z(Position var1) {
		this.x += var1.x;
		this.y += var1.y;
		this.z += var1.z;
		return this;
	}

	public final Position B() {
		return new Position(this.x, this.y, this.z);
	}

	public final String toString() {
		return "vector{x=" + this.x + ", y=" + this.y + ", z=" + this.z + '}';
	}

}
