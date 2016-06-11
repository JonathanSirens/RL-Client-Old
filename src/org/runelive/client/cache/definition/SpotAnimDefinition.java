package org.runelive.client.cache.definition;

import org.runelive.client.List;
import org.runelive.client.cache.Archive;
import org.runelive.client.io.ByteBuffer;
import org.runelive.client.world.Model;

public final class SpotAnimDefinition {

	public static List list = new List(30);
	public static SpotAnimDefinition[] cache;

	public static void load(Archive archive) {
		ByteBuffer stream = new ByteBuffer(archive.get("spotanim.dat"));
		int length = stream.getUnsignedShort();
		if (cache == null)
			cache = new SpotAnimDefinition[length];
		for (int j = 0; j < length; j++) {
			if (cache[j] == null)
				cache[j] = new SpotAnimDefinition();
			cache[j].id = j;
			cache[j].readValues(stream);
		}
		custom();
	}

	private static void custom() {
		cache[2274].modelId = cache[2281].modelId;
		cache[2274].animationId = cache[2281].animationId;
		cache[2274].rotation = 90;
		cache[2274].animation = cache[2281].animation;
		
		System.out.println(""+cache[2699].modelId);
		//Cerberus
		cache[1242].modelId = 29306;
		cache[1242].animationId = 4497;
		cache[1243].modelId = 29306;
		cache[1243].animationId = 4498;
		cache[1244].modelId = 29390;
		cache[1244].animationId = 4499;
		cache[1245].modelId = 29390;
		cache[1245].animationId = 4500;
		cache[1246].modelId = 29311;
		cache[1246].animationId = 4502;
		cache[1247].modelId = 29269;
		cache[1247].animationId = 4501;
		//Blowpipe
		//System.out.println("Color codes start ---");
		//for(int i = 0; i < cache[226].changedModelColours.length; i++) {
		//	System.out.println(""+cache[226].changedModelColours[i]);
		//}
		//System.out.println(cache[226].modelId+"");
		
		//Zulrah
		cache[1044].modelId = 70003;
		cache[1044].animationId = 5358;
		cache[1044].shadow = 20;
		cache[1044].originalModelColours = new int[] {22476, 25511, 0, 0, 0, 0, };
		cache[1044].changedModelColours = new int[] {30608, 30236, 0, 0, 0, 0, };
		
		cache[1046].modelId = 70004;
		cache[1046].animationId = 6648;
		cache[1046].sizeXY = 96;
		cache[1046].sizeZ = 96;
		cache[1046].shadow = 60;
		cache[1046].originalModelColours = new int[] {5060, 11185, 11200, 0, 0, 0, };
		cache[1046].changedModelColours = new int[] {4025, 6067, 7097, 0, 0, 0, };
		
		cache[1043].modelId = 76054;
		cache[1043].animationId = 876;

		cache[156].modelId = 70005;
		cache[156].animationId = 692;
		cache[156].sizeXY = 128;
		cache[156].sizeZ = 128;
		cache[156].rotation = 0;
		cache[156].shadow = 60;
		cache[156].lightness = 60;
		
		cache[301].modelId = 76053;
		cache[301].animationId = 1714;
		cache[301].sizeXY = 128;
		cache[301].sizeZ = 128;
		cache[301].rotation = 0;
		cache[301].shadow = 30;
		cache[301].lightness = 10;
		cache[301].originalModelColours = new int[] {0, 0, 0, 0, 0, 0};
		cache[301].changedModelColours = new int[] {0, 0, 0, 0, 0, 0};
		cache[301].animation = Animation.cache[1714];
		
		cache[1253].modelId = 20824;
		cache[1253].animationId = 5461;
		cache[1253].originalModelColours = new int[] {43051, 43038, 4, 0, 0, 0};
		cache[1253].changedModelColours = new int[] {42939, 42956, 39198, 0, 0, 0};
		cache[1253].animation = Animation.cache[cache[1253].animationId];
			
		cache[1228].modelId = 76052;
		cache[1228].animationId = 7084;
		cache[1228].sizeXY = 100;
		cache[1228].sizeZ = 100;
		cache[1228].rotation = 0;
		cache[1228].shadow = 0;
		cache[1228].lightness = 0;
		cache[1228].originalModelColours = new int[] {62119, 61999, 59612, 0, 0, 0};
		cache[1228].changedModelColours = new int[] {18626, 18626, 18626, 0, 0, 0};
		cache[1228].animation = Animation.cache[7084];
	}
	/*
	public static void load(Archive archive) {
		ByteBuffer buffer = new ByteBuffer(archive.get("spotanim.dat"));
		int length = buffer.getUnsignedShort();

		if (cache == null) {
			cache = new SpotAnimDefinition[length];
		}

		for (int i = 0; i < length; i++) {
			if (cache[i] == null) {
				cache[i] = new SpotAnimDefinition();
			}

			cache[i].id = i;
			cache[i].readValues(buffer);
			switch (i) {
			case 1247:
				cache[i].modelId = 60776;
				cache[i].animationId = 4001;
				cache[i].animation = Animation.cache[4001];
				break;
			case 1248:
				cache[i].modelId = 60776;
				cache[i].animationId = 4002;
				cache[i].animation = Animation.cache[4002];
				break;
			}
		}
	}*/

	public Animation animation;
	private int id;
	public int modelId;
	private int animationId;
	public int sizeXY;
	public int sizeZ;
	public int rotation;
	public int shadow;
	public int lightness;
	private int[] originalModelColours;
	private int[] changedModelColours;

	private SpotAnimDefinition() {
		animationId = -1;
		originalModelColours = new int[6];
		changedModelColours = new int[6];
		sizeXY = 128;
		sizeZ = 128;
	}

	public Model getModel() {
		Model model = (Model) list.insertFromCache(id);

		if (model != null) {
			return model;
		}

		model = Model.fetchModel(modelId);

		if (model == null) {
			return null;
		}

		for (int i = 0; i < 6; i++) {
			if (originalModelColours[0] != 0) {
				model.method476(originalModelColours[i], changedModelColours[i]);
			}
		}

		list.removeFromCache(model, id);
		return model;
	}

	private void readValues(ByteBuffer stream) {
        do {
            int i = stream.getUnsignedByte();
            if (i == 0)
                return;
            if (i == 1) {
                modelId = stream.getUnsignedShort();
            }
            else if (i == 2) {
                animationId = stream.getUnsignedShort();
                if (Animation.cache != null)
                	animation = Animation.cache[animationId];
            } else if (i == 4)
                sizeXY = stream.getUnsignedShort();
            else if (i == 5)
                sizeZ = stream.getUnsignedShort();
            else if (i == 6)
                rotation = stream.getUnsignedShort();
            else if (i == 7)
                shadow = stream.getUnsignedByte();
            else if (i == 8)
                lightness = stream.getUnsignedByte();
            else if (i == 40) {
                int j = stream.getUnsignedByte();
                for (int k = 0; k < j; k++) {
                    originalModelColours[k] = stream.getUnsignedShort();
                    changedModelColours[k] = stream.getUnsignedShort();
                }
            } else
                System.out.println("Error unrecognised spotanim config code: "
                        + i);
        } while (true);
    }
}