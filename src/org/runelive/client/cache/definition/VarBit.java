package org.runelive.client.cache.definition;

import org.runelive.client.cache.Archive;
import org.runelive.client.io.ByteBuffer;

public final class VarBit {

	public static VarBit[] cache;

	public static void unpackConfig(Archive archive) {
		ByteBuffer buffer = new ByteBuffer(archive.get("varbit.dat"));
		int size = 5133;

		if (cache == null) {
			cache = new VarBit[size];
		}

		for (int i = 0; i < size; i++) {
			if (cache[i] == null) {
				cache[i] = new VarBit();
			}

			cache[i].readValues(buffer);
		}

		if (buffer.position != buffer.buffer.length) {
			System.out.println("varbit load mismatch");
		}
	}

	public int configId;
	public int configValue;
	public int anInt650;

	private VarBit() {
	}

	private void readValues(ByteBuffer buffer) {
		do {
			int opcode = buffer.getUnsignedByte();

			if (opcode == 0) {
				return;
			}

			if (opcode == 1) {
				configId = buffer.getUnsignedShort();
				configValue = buffer.getUnsignedByte();
				anInt650 = buffer.getUnsignedByte();
			} else if (opcode == 10) {
				buffer.getString();
			} else if (opcode == 3) {
				buffer.getIntLittleEndian();
			} else if (opcode == 4) {
				buffer.getIntLittleEndian();
			} else if (opcode != 2) {
				System.out.println("Error unrecognised config code: " + opcode);
			}
		} while (true);
	}

}