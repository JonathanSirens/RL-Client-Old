package org.runelive.client.cache.definition;

import org.runelive.client.cache.Archive;
import org.runelive.client.io.ByteBuffer;
import org.runelive.client.world.Model;

public final class IdentityKit {

	private static int length;
	public static IdentityKit[] cache;

	public static void unpackConfig(Archive streamLoader) {
		ByteBuffer stream = new ByteBuffer(streamLoader.get("idk.dat"));
		setLength(stream.getUnsignedShort());

		if (cache == null) {
			cache = new IdentityKit[getLength()];
		}

		// List<Integer> modelIds = new ArrayList<>();
		for (int j = 0; j < getLength(); j++) {
			if (cache[j] == null) {
				cache[j] = new IdentityKit();
			}

			cache[j].readValues(stream);

			// for (int id : cache[j].bodyModelIds) {
			// modelIds.add(id);
			// }
		}
		// System.out.println("IDK models: " + modelIds.toString());
	}

	private int anInt657;
	private int[] bodyModelIds;
	private final int[] srcColors;
	private final int[] destColors;
	private final int[] headModelIds = { -1, -1, -1, -1, -1 };
	private boolean aBoolean662;

	public IdentityKit() {
		setBodyPartId(-1);
		srcColors = new int[6];
		destColors = new int[6];
		setNotSelectable(false);
	}

	public boolean isBodyModelLoaded() {
		if (bodyModelIds == null) {
			return true;
		}

		boolean flag = true;

		for (int j = 0; j < bodyModelIds.length; j++) {
			if (!Model.method463(bodyModelIds[j])) {
				flag = false;
			}
		}

		return flag;
	}

	public Model getBodyModel() {
		if (bodyModelIds == null) {
			return null;
		}

		Model models[] = new Model[bodyModelIds.length];

		for (int i = 0; i < bodyModelIds.length; i++) {
			models[i] = Model.method462(bodyModelIds[i]);
		}

		Model model;

		if (models.length == 1) {
			model = models[0];
		} else {
			model = new Model(models.length, models);
		}

		for (int j = 0; j < 6; j++) {
			if (srcColors[j] == 0) {
				break;
			}
			model.method476(srcColors[j], destColors[j]);
		}

		return model;
	}

	public boolean isDialogModelsLoaded() {
		boolean flag1 = true;

		for (int i = 0; i < 5; i++) {
			if (headModelIds[i] != -1 && !Model.method463(headModelIds[i])) {
				flag1 = false;
			}
		}

		return flag1;
	}

	public Model getDialogModel() {
		Model models[] = new Model[5];
		int j = 0;

		for (int k = 0; k < 5; k++) {
			if (headModelIds[k] != -1) {
				models[j++] = Model.method462(headModelIds[k]);
			}
		}

		Model model = new Model(j, models);

		for (int l = 0; l < 6; l++) {
			if (srcColors[l] == 0) {
				break;
			}
			model.method476(srcColors[l], destColors[l]);
		}

		return model;
	}

	private void readValues(ByteBuffer buffer) {
		do {
			int opcode = buffer.getUnsignedByte();

			if (opcode == 0) {
				return;
			}
			if (opcode == 1) {
				setBodyPartId(buffer.getUnsignedByte());
			} else if (opcode == 2) {
				int total = buffer.getUnsignedByte();
				bodyModelIds = new int[total];
				for (int k = 0; k < total; k++) {
					bodyModelIds[k] = buffer.getUnsignedShort();
				}

			} else if (opcode == 3) {
				setNotSelectable(true);
			} else if (opcode >= 40 && opcode < 50) {
				srcColors[opcode - 40] = buffer.getUnsignedShort();
			} else if (opcode >= 50 && opcode < 60) {
				destColors[opcode - 50] = buffer.getUnsignedShort();
			} else if (opcode >= 60 && opcode < 70) {
				headModelIds[opcode - 60] = buffer.getUnsignedShort();
			} else {
				System.out.println("Error unrecognised config code: " + opcode);
			}
		} while (true);
	}

	public boolean isaBoolean662() {
		return aBoolean662;
	}

	public void setNotSelectable(boolean aBoolean662) {
		this.aBoolean662 = aBoolean662;
	}

	public static int getLength() {
		return length;
	}

	public static void setLength(int length) {
		IdentityKit.length = length;
	}

	public int getAnInt657() {
		return anInt657;
	}

	public void setBodyPartId(int anInt657) {
		this.anInt657 = anInt657;
	}

}