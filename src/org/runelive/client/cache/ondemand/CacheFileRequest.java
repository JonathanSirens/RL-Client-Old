package org.runelive.client.cache.ondemand;

import org.runelive.client.cache.node.NodeSub;

public final class CacheFileRequest extends NodeSub {

	private int dataType;
	public byte buffer[];
	public int id;
	boolean incomplete;
	int requestAge;

	public CacheFileRequest() {
		incomplete = true;
	}

	public byte[] getData() {
		return buffer;
	}

	public void setData(byte buffer[]) {
		this.buffer = buffer;
	}

	public int getIndex() {
		return dataType;
	}

	public void setCacheIndex(int dataType) {
		this.dataType = dataType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public static final int MODEL = 0;
	public static final int ANIM = 1;
	public static final int MIDI = 2;
	public static final int MAP = 3;
	public static final int TEXTURE = 4;
	public static final int MODEL_EXT = 5;

}