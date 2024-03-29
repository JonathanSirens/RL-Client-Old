package org.runelive.client.cache.definition;

import java.io.File;
import java.io.FileOutputStream;

import org.runelive.client.Client;
import org.runelive.client.List;
import org.runelive.client.RandomColor;
import org.runelive.client.Signlink;
import org.runelive.client.cache.Archive;
import org.runelive.client.graphics.Canvas2D;
import org.runelive.client.graphics.Sprite;
import org.runelive.client.io.ByteBuffer;
import org.runelive.client.world.Canvas3D;
import org.runelive.client.world.Model;

public final class ItemDefinition {

	private static ByteBuffer buffer;
	private static ItemDefinition[] cache;
	private static int cacheIndex;
	public static boolean isMembers = true;
	public static List mruNodes1 = new List(100);
	public static List mruNodes2 = new List(50);
	private static int[] streamIndices;
	public static int totalItems;

	public static void dumpItemModelsForId(int i) {
		try {
			ItemDefinition d = get(i);

			if (d != null) {
				int[] models = { d.maleWearId, d.femaleWearId, d.modelID, };

				for (int ids : models) {// 13655
					if (ids > 0) {
						try {
							System.out.println("Dumping item model: " + ids);
							byte abyte[] = Client.instance.cacheIndices[1].get(ids);
							File map = new File(Signlink.getCacheDirectory() + "models/" + ids + ".gz");
							FileOutputStream fos = new FileOutputStream(map);
							fos.write(abyte);
							fos.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static final String[] GLOVE_NAME = {
		"Bronze", "Iron", "Steel", "Black",
		"Mithril", "Adamant", "Rune", "Dragon",
		"Barrows"
	};
	
	public static ItemDefinition get(int id) {
		for (int i = 0; i < 10; i++) {
			if (cache[i].id == id) {
				if (i == 21088) {
					cache[i].originalModelColors[0] = RandomColor.currentColour;
				}
				return cache[i];
			}
		}

		cacheIndex = (cacheIndex + 1) % 10;
		ItemDefinition itemDef = cache[cacheIndex];
		buffer.position = streamIndices[id];
		itemDef.id = id;
		itemDef.setDefaults();
		itemDef.readValues(buffer);
		
		if (itemDef.id >= 7454 && itemDef.id <= 7462) {
			itemDef.name = GLOVE_NAME[itemDef.id - 7454] + " gloves";
			return itemDef;
		}

		if (itemDef.modifiedModelColors != null) {
			int[] oldc = itemDef.modifiedModelColors;
			int[] newc = itemDef.originalModelColors;
			itemDef.modifiedModelColors = new int[oldc.length + 1];
			itemDef.originalModelColors = new int[newc.length + 1];
			for (int index = 0; index < itemDef.originalModelColors.length; index++) {
				if (index < itemDef.originalModelColors.length - 1) {
					itemDef.modifiedModelColors[index] = oldc[index];
					itemDef.originalModelColors[index] = newc[index];
				} else {
					itemDef.modifiedModelColors[index] = 0;
					itemDef.originalModelColors[index] = 1;
				}
			}
		} else {
			itemDef.modifiedModelColors = new int[1];
			itemDef.originalModelColors = new int[1];
			itemDef.modifiedModelColors[0] = 0;
			itemDef.originalModelColors[0] = 1;
		}

		int customId = itemDef.id;

		if (customId >= 13700 && customId <= 13709) {
			itemDef.certID = -1;
			itemDef.certTemplateID = -1;
			itemDef.stackable = false;
		}
		ItemDefinition itemDef2;
		switch (customId) {
			case 10008:
			case 10009:
				itemDef.modelOffsetY = -28;
				break;
			case 11907:
				itemDef.name = "Gold-trimmed wizard set";
				break;
			case 15355:
				itemDef.name = "Wilderness scroll";
				break;
			case 11906:
				itemDef.name = "Gold-trimmed wizard set";
				itemDef.actions = new String[5];
				itemDef.actions[0] = "Open";
				break;
			case 12437:
				itemDef.actions = new String[5];
				itemDef.actions[0] = "Activate";
				break;
			case 11211:
				itemDef.actions = new String[5];
				itemDef.actions[0] = "Jiggle";
				break;
			case 4142:
				itemDef.actions = new String[5];
				itemDef.actions[0] = "Lick";
				break;

			case 4490:
				itemDef.name = "Poop";
				itemDef.actions = new String[5];
				itemDef.actions[0] = "Shovel";
				break;
			case 9722:
				itemDef.name = "Key of death";
				itemDef.originalModelColors = new int[] { 2866, 2858, 2870 };
				itemDef.modifiedModelColors = new int[] { 5194, 5186, 5198 };
				break;
			case 9725:
				itemDef.imitate(get(9722));
				itemDef.name = "Key of fear";
				itemDef.originalModelColors = new int[] { 933, 933 - 8, 933 + 12 };
				itemDef.modifiedModelColors = new int[] { 5194, 5186, 5198 };
				break;
			case 9724:
				itemDef.imitate(get(9722));
				itemDef.name = "Key of cobra";
				itemDef.modifiedModelColors = new int[] { 226770, 226764, 226774 };
				itemDef.originalModelColors = new int[] { 5194, 5186, 5198 };
				break;
			case 9723:
				itemDef.imitate(get(9722));
				itemDef.name = "Key of blitz";
				itemDef.modifiedModelColors = new int[] { 2866, 2858, 2870 };
				itemDef.originalModelColors = new int[] { 5194, 5186, 5198 };
				break;
			case 19670:
				itemDef.name = "Vote scroll";
				itemDef.actions = new String[5];
				itemDef.actions[4] = "Drop";
				itemDef.actions[0] = "Claim";
				itemDef.actions[2] = "Claim-All";
				break;
			case 19891:
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
				break;
			case 12703:
				// itemDef.setDefaults();
				itemDef.name = "Hellpuppy";
				itemDef.modelID = 29392;
				itemDef.modelRotation1 = 0;
				itemDef.modelRotation2 = 0;
				itemDef.modelZoom = 3000;
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
				break;
			case 21089:
				itemDef.modelID = 66994;
				itemDef.name = "Drygore longsword";
				itemDef.description2 = "A powerful sword made from the chitlin of the Kalphite King.";
				itemDef.modelZoom = 1645;
				itemDef.modelRotation2 = 444;
				itemDef.modelRotation1 = 377;
				itemDef.modelOffset1 = 3;
				itemDef.maleWearId = 66992;
				itemDef.femaleWearId = 66993;
				itemDef.groundActions = new String[5];
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wield";
				itemDef.actions[4] = "Drop";
				break;
			case 21090:
				itemDef.modelID = 66998;
				itemDef.name = "Off-hand drygore longsword";
				itemDef.description2 = "A powerful off-hand sword made from the chitlin of the Kalphite King.";
				itemDef.modelZoom = 1493;
				itemDef.modelRotation1 = 618;
				itemDef.modelRotation2 = 1407;
				itemDef.modelOffset1 = 0;
				itemDef.modelOffsetY = -5;
				itemDef.maleWearId = 66996;
				itemDef.femaleWearId = 66997;
				itemDef.groundActions = new String[5];
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wield";
				itemDef.actions[4] = "Drop";
				break;
			case 21091:
				itemDef.modelID = 67000;
				itemDef.name = "Drygore rapier";
				itemDef.description2 = "A powerful rapier made from the chitlin of the Kalphite King.";
				itemDef.modelZoom = 1053;
				itemDef.modelRotation2 = 458;
				itemDef.modelRotation1 = 228;
				itemDef.modelOffset1 = -1;
				itemDef.modelOffsetY = -47;
				itemDef.maleWearId = 67001;
				itemDef.femaleWearId = 67002;
				itemDef.groundActions = new String[5];
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wield";
				itemDef.actions[4] = "Drop";
				break;
			case 21092:
				itemDef.modelID = 67004;
				itemDef.name = "Off-hand drygore rapier";
				itemDef.description2 = "A powerful off-hand rapier made from the chitlin of the Kalphite King.";
				itemDef.modelZoom = 1493;
				itemDef.modelRotation1 = 618;
				itemDef.modelRotation2 = 1407;
				itemDef.modelOffset1 = 0;
				itemDef.modelOffsetY = -5;
				itemDef.maleWearId = 67005;
				itemDef.femaleWearId = 67006;
				itemDef.groundActions = new String[5];
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wield";
				itemDef.actions[4] = "Drop";
				break;
			case 21111:
				itemDef.setDefaults();
				itemDef.name = "Ganodermic visor";
				itemDef.description = "It's an Ganodermic visor".getBytes();
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wield";
				itemDef.modelID = 10935;
				itemDef.maleWearId = 10373;
				itemDef.femaleWearId = 10523;
				itemDef.modelZoom = 1118;
				itemDef.modelRotation1 = 215;
				itemDef.modelRotation2 = 175;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -30;
				break;
			case 21112:
				itemDef.setDefaults();
				itemDef.name = "Ganodermic poncho";
				itemDef.description = "It's an Ganodermic poncho".getBytes();
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wield";
				itemDef.modelID = 10919;
				itemDef.maleWearId = 10490;
				itemDef.femaleWearId = 10664;
				itemDef.modelZoom = 1513;
				itemDef.modelRotation1 = 485;
				itemDef.modelRotation2 = 13;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -3;
				break;
			case 21113:
				itemDef.setDefaults();
				itemDef.name = "Ganodermic leggings";
				itemDef.description = "It's an Ganodermic leggings".getBytes();
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wield";
				itemDef.modelID = 10942;
				itemDef.maleWearId = 10486;
				itemDef.femaleWearId = 10578;
				itemDef.modelZoom = 1513;
				itemDef.modelRotation1 = 498;
				itemDef.modelRotation2 = 0;
				itemDef.modelOffset1 = 8;
				itemDef.modelOffsetY = -18;
				break;
			case 21114:
				itemDef.setDefaults();
				itemDef.name = "Polypore staff";
				itemDef.description = "It's an Polypore staff".getBytes();
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wield";
				itemDef.modelID = 13426;
				itemDef.maleWearId = 13417;
				itemDef.femaleWearId = 13417;
				itemDef.modelZoom = 3750;
				itemDef.modelRotation1 = 1454;
				itemDef.modelRotation2 = 997;
				itemDef.modelOffsetX = 1145;
				itemDef.modelOffset1 = 0;
				itemDef.modelOffsetY = 8;
				break;
			case 21115:
				itemDef.imitate(get(15459));
				itemDef.originalModelColors = new int[] { 1, 1 };
				itemDef.modifiedModelColors = new int[] { 43955, 22439 };
				itemDef.name = "Defender lvl 6";
				break;
			case 21116:
				itemDef.imitate(get(15459));
				itemDef.originalModelColors = new int[] { 123770, 123770 };
				itemDef.modifiedModelColors = new int[] { 43955, 22439 };
				itemDef.name = "Defender lvl 7";
				break;
			case 21118:
				itemDef.setDefaults();
				itemDef.name = "Partyhat & specs";
				itemDef.description = "A very special partyhat.".getBytes();
				itemDef.maleWearId = 28505;
				itemDef.femaleWearId = 28576;
				itemDef.modelID = 28693;
				itemDef.modelRotation1 = 242;
				itemDef.modelRotation2 = 0;
				itemDef.modelZoom = 653;
				itemDef.modelOffset1 = 0;
				itemDef.modelOffsetY = -1;
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wield";
				itemDef.actions[3] = "Check";
				itemDef.originalModelColors = new int[] { 1 };
				itemDef.modifiedModelColors = new int[] { 43963 };
				break;
			case 21119:
				itemDef.setDefaults();
				itemDef.name = "Partyhat & specs";
				itemDef.description = "A very special partyhat.".getBytes();
				itemDef.maleWearId = 28505;
				itemDef.femaleWearId = 28576;
				itemDef.modelID = 28693;
				itemDef.modelRotation1 = 242;
				itemDef.modelRotation2 = 0;
				itemDef.modelZoom = 653;
				itemDef.modelOffset1 = 0;
				itemDef.modelOffsetY = -1;
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wield";
				itemDef.actions[3] = "Check";
				itemDef.originalModelColors = new int[] { 123770 };
				itemDef.modifiedModelColors = new int[] { 43963 };
				break;
			case 21100:
				itemDef.modelID = 67007;
				itemDef.name = "Drygore mace";
				itemDef.description2 = "A powerful mace made from the chitlin of the Kalphite King.";
                itemDef.modelZoom = 1118;
                itemDef.modelRotation1 = 228;
                itemDef.modelRotation2 = 485;
                itemDef.modelOffset1 = -1;
                itemDef.modelOffsetY = -47;
				itemDef.maleWearId = 67008;
				itemDef.femaleWearId = 67009;
				itemDef.groundActions = new String[5];
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wield";
				itemDef.actions[4] = "Drop";
				break;

			/*
			case 13889:
				itemDef.actions[2] = "Check";
				break;
			*/

			case 21101:
				itemDef.modelID = 67011;
				itemDef.name = "Off-hand drygore mace";
				itemDef.description2 = "A powerful off-hand mace made from the chitlin of the Kalphite King.";
				itemDef.modelZoom = 1493;
				itemDef.modelRotation1 = 618;
				itemDef.modelRotation2 = 1407;
				itemDef.modelOffset1 = 0;
				itemDef.modelOffsetY = -5;
				itemDef.maleWearId = 67012;
				itemDef.femaleWearId = 67013;
				itemDef.groundActions = new String[5];
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wield";
				itemDef.actions[4] = "Drop";
				break;
			case 21102:
				itemDef.imitate(get(6570));
				itemDef.name = "Water cape";
				itemDef.originalModelColors = new int[] { 52 };
				itemDef.modifiedModelColors = new int[] { 40 };
				break;
			case 7887:
				itemDef.name = "Pet Seagull";
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
				break;
			case 21252:
				itemDef.imitate(get(12021));
				itemDef.name = "Pet Beaver";
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
				break;
			case 21251:
				itemDef.imitate(get(9976));
				itemDef.name = "Pet Chinchompa";
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
				break;
			case 21250:
				itemDef.name = "Pet Rock Golem";
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
				break;
			case 21117:
				itemDef.imitate(get(6199));
				itemDef.name = "Achievement box";
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Open", null, null, null, null, "Drop" };
				break;
			case 21106:
				itemDef.setDefaults();
				itemDef.imitate(get(1450));
				itemDef.name = "Astral talisman";
				itemDef.originalModelColors = new int[] { 54503 };
				itemDef.modifiedModelColors = new int[] { 926 };
				break;
			case 21104:
				itemDef.setDefaults();
				itemDef.name = "Dragonbone spirit shield";
				itemDef.modelZoom = 1616; // Model Zoom
				itemDef.maleWearId = 70013; // Male Equip 1
				itemDef.femaleWearId = 70013; // Male Equip 2
				itemDef.modelID = 70014; // Model ID
				itemDef.modelRotation1 = 396; // Model Rotation 1
				itemDef.modelRotation2 = 1050; // Model Rotation 2
				itemDef.modelOffset1 = -3; // Model Offset 1
				itemDef.modelOffsetY = 16; // Model Offset 2
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				itemDef.actions[4] = "Drop";
				break;

			case 21103:
				itemDef.setDefaults();
				itemDef.modelID = 70011;
				itemDef.modelZoom = 1380;
				itemDef.modelRotation1 = 67;
				itemDef.modelRotation2 = 67;
				itemDef.modelOffset1 = 9;
				itemDef.modelOffsetY = -4;
				itemDef.name = "Venenatis spiderling";
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
				break;
			case 3046:
			case 3044:
			case 3042:
			case 3040:
				itemDef.originalModelColors = new int[] { 2524 };
				itemDef.modifiedModelColors = new int[] { 61 };
				break;
			case 13576:
				itemDef.setDefaults();
				itemDef.name = "Dragon warhammer";
				itemDef.modelID = 70010;
				itemDef.modelRotation1 = 1510;
				itemDef.modelRotation2 = 1785;
				itemDef.modelOffset1 = 9;
				itemDef.modelOffsetY = -4;
				itemDef.modelZoom = 1600;
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
				itemDef.maleWearId = 70008;
				itemDef.femaleWearId = 70009;
				break;
			case 12704:
				itemDef.name = "Infernal pickaxe";
				itemDef.modelID = 29393;
				itemDef.maleWearId = 29260;
				itemDef.femaleWearId = 29260;
				itemDef.modelRotation1 = 1683;
				itemDef.modelRotation2 = 1885;
				itemDef.modelOffset1 = -1;
				itemDef.modelOffsetY = -9;
				itemDef.modelZoom = 1221;
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
				break;

			case 12706:
				itemDef.name = "Infernal axe";
				itemDef.modelID = 29395;
				itemDef.maleWearId = 29259;
				itemDef.femaleWearId = 29259;
				itemDef.modelRotation1 = 512;
				itemDef.modelRotation2 = 1212;
				itemDef.modelOffset1 = 7;
				itemDef.modelOffsetY = 16;
				itemDef.modelZoom = 1663;
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
				break;

			case 12708:
				itemDef.name = "Pegasian boots";
				itemDef.modelID = 29396;
				itemDef.modelZoom = 976;
				itemDef.modelRotation1 = 147;
				itemDef.modelRotation2 = 279;
				itemDef.modelOffset1 = 5;
				itemDef.modelOffsetY = -5;
				itemDef.maleWearId = 29252;
				itemDef.femaleWearId = 29253;
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				break;

			case 12710:
				itemDef.name = "Primordial boots";
				itemDef.modelID = 29397;
				itemDef.modelZoom = 976;
				itemDef.modelRotation1 = 147;
				itemDef.modelRotation2 = 279;
				itemDef.modelOffset1 = 5;
				itemDef.modelOffsetY = -5;
				itemDef.maleWearId = 29250;
				itemDef.femaleWearId = 29255;
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				break;

			case 6640:
				itemDef.setDefaults();
				itemDef.name = "Primordial crystal";
				itemDef.description2 = "A pair of upgraded steadfast boots.";
				itemDef.modelID = 29263;
				itemDef.modelZoom = 740;
				itemDef.modelRotation1 = 429;
				itemDef.modelRotation2 = 225;
				itemDef.modelOffset1 = 5;
				itemDef.modelOffsetY = 5;
				break;

			case 6641:
				itemDef.setDefaults();
				itemDef.name = "Eternal crystal";
				itemDef.description2 = "A pair of upgraded ragefire boots.";
				itemDef.modelID = 29264;
				itemDef.modelZoom = 740;
				itemDef.modelRotation1 = 429;
				itemDef.modelRotation2 = 225;
				itemDef.modelOffset1 = 5;
				itemDef.modelOffsetY = 5;
				break;

			case 6642:
				itemDef.setDefaults();
				itemDef.name = "Pegasian crystal";
				itemDef.description2 = "A pair of upgraded glaiven boots.";
				itemDef.modelID = 29261;
				itemDef.modelZoom = 740;
				itemDef.modelRotation1 = 429;
				itemDef.modelRotation2 = 225;
				itemDef.modelOffset1 = 5;
				itemDef.modelOffsetY = 5;
				break;

			case 6643:
				itemDef.setDefaults();
				itemDef.name = "Smouldering stone";
				itemDef.description2 = "A smouldering stone from the depths of Hell.";
				itemDef.modelID = 29262;
				itemDef.modelZoom = 653;
				itemDef.modelRotation1 = 229;
				itemDef.modelRotation2 = 1818;
				itemDef.modelOffset1 = 0;
				itemDef.modelOffsetY = -8;
				break;

			case 12712:
				itemDef.name = "Eternal boots";
				itemDef.modelID = 29394;
				itemDef.modelZoom = 976;
				itemDef.modelRotation1 = 147;
				itemDef.modelRotation2 = 279;
				itemDef.modelOffset1 = 5;
				itemDef.modelOffsetY = -5;
				itemDef.maleWearId = 29249;
				itemDef.femaleWearId = 29254;
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				break;
			case 691:
				itemDef.name = "Proof of kill";
				itemDef.actions = new String[] { null, null, null, null, "Drop" };
				itemDef.description2 = "I should give this to the king to prove my loyalty.";
				break;
			case 10034:
			case 10033:
				itemDef.actions = new String[] { null, "Weild", null, null, "Drop" };
				break;
			case 13727:
				itemDef.actions = new String[] { null, null, null, null, "Drop" };
				break;
			case 6500:
				itemDef.setDefaults();
				itemDef.imitate(get(9952));
				itemDef.name = "Charming imp";
				itemDef.stackable = false;
				// itemDef.modelRotation1 = 85;
				// itemDef.modelRotation2 = 1867;
				itemDef.actions = new String[] { null, null, "Check", "Config", "Drop" };
				break;
			case 11995:
				itemDef.setDefaults();
				itemDef.modelID = 28256;
				itemDef.modelZoom = 1284;
				itemDef.modelRotation1 = 0;
				itemDef.modelRotation2 = 175;
				itemDef.modelOffset1 = -66;
				itemDef.modelOffsetY = 75;
				itemDef.modelOffsetX = 1939;
				itemDef.name = "Chaos Elemental Jr.";
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
				break;
			case 11882:
				itemDef.actions = new String[5];
				itemDef.actions[0] = "Open";
				itemDef.name = "Black gold-trim set (lg)";
				break;
			case 11883:
				itemDef.name = "Black gold-trim set (lg)";
				break;
			case 11885:
				itemDef.name = "Black gold-trim set (sk)";
				break;
			case 21074:
				itemDef.setDefaults();
				itemDef.name = "Staff of the dead";
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wield";
				itemDef.description2 = "A ghastly weapon with evil origins.";
				itemDef.modelID = 76048;
				itemDef.maleWearId = 76049;
				itemDef.femaleWearId = 76049;
				itemDef.modelRotation1 = 148;
				itemDef.modelRotation2 = 1300;
				itemDef.modelZoom = 1420;
				itemDef.modelOffset1 = -5;
				itemDef.modelOffsetY = 2;
				break;
			case 11517:
				itemDef.setDefaults();
				itemDef.modelID = 2789;
				itemDef.modelOffset1 = 0;
				itemDef.modelOffsetY = -1;
				itemDef.modelRotation1 = 84;
				itemDef.modelRotation2 = 1996;
				itemDef.modelZoom = 550;
				itemDef.originalModelColors = new int[] { 22418 };
				itemDef.modifiedModelColors = new int[] { 61 };
				itemDef.name = "Super combat potion (4)";
				itemDef.groundActions = new String[5];
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				break;

			case 11519:
				itemDef.setDefaults();
				itemDef.modelID = 2697;
				itemDef.modelOffset1 = 0;
				itemDef.modelOffsetY = -1;
				itemDef.modelRotation1 = 84;
				itemDef.modelRotation2 = 1996;
				itemDef.modelZoom = 550;
				itemDef.originalModelColors = new int[] { 22418 };
				itemDef.modifiedModelColors = new int[] { 61 };
				itemDef.name = "Super combat potion (3)";
				itemDef.groundActions = new String[5];
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				break;

			case 11521:
				itemDef.setDefaults();
				itemDef.modelID = 2384;
				itemDef.modelOffset1 = 0;
				itemDef.modelOffsetY = -1;
				itemDef.modelRotation1 = 84;
				itemDef.modelRotation2 = 1996;
				itemDef.modelZoom = 550;
				itemDef.originalModelColors = new int[] { 22418 };
				itemDef.modifiedModelColors = new int[] { 61 };
				itemDef.name = "Super combat potion (2)";
				itemDef.groundActions = new String[5];
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				break;

			case 11523:
				itemDef.setDefaults();
				itemDef.modelID = 2621;
				itemDef.modelOffset1 = 0;
				itemDef.modelOffsetY = -1;
				itemDef.modelRotation1 = 84;
				itemDef.modelRotation2 = 1996;
				itemDef.modelZoom = 550;
				itemDef.originalModelColors = new int[] { 22418 };
				itemDef.modifiedModelColors = new int[] { 61 };
				itemDef.name = "Super combat potion (1)";
				itemDef.groundActions = new String[5];
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				break;
			case 11525:
				itemDef.setDefaults();
				itemDef.modelID = 2789;
				itemDef.modelOffset1 = 0;
				itemDef.modelOffsetY = -1;
				itemDef.modelRotation1 = 84;
				itemDef.modelRotation2 = 1996;
				itemDef.modelZoom = 550;
				itemDef.originalModelColors = new int[] { 26772 };
				itemDef.modifiedModelColors = new int[] { 61 };
				itemDef.name = "Anti-venom (4)";
				itemDef.groundActions = new String[5];
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				break;
			case 11526:
				itemDef.setDefaults();
				itemDef.modelID = 2429;
				itemDef.name = "Anti-venom (4)";
				itemDef.actions = new String[] { null, null, null, null, null, null };
				itemDef.modelZoom = 760;
				itemDef.modelRotation2 = 28;
				itemDef.modelRotation1 = 552;
				itemDef.modelOffsetX = itemDef.modelOffset1 = 0;
				itemDef.stackable = true;
				itemDef.certID = 11525;
				itemDef.certTemplateID = 799;
				break;
			case 11527:
				itemDef.setDefaults();
				itemDef.modelID = 2697;
				itemDef.modelOffset1 = 0;
				itemDef.modelOffsetY = -1;
				itemDef.modelRotation1 = 84;
				itemDef.modelRotation2 = 1996;
				itemDef.modelZoom = 550;
				itemDef.originalModelColors = new int[] { 26772 };
				itemDef.modifiedModelColors = new int[] { 61 };
				itemDef.name = "Anti-venom (3)";
				itemDef.groundActions = new String[5];
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				break;
			case 11528:
				itemDef.setDefaults();
				itemDef.modelID = 2429;
				itemDef.name = "Anti-venom (3)";
				itemDef.actions = new String[] { null, null, null, null, null, null };
				itemDef.modelZoom = 760;
				itemDef.modelRotation2 = 28;
				itemDef.modelRotation1 = 552;
				itemDef.modelOffsetX = itemDef.modelOffset1 = 0;
				itemDef.stackable = true;
				itemDef.certID = 11527;
				itemDef.certTemplateID = 799;
				break;
			case 11529:
				itemDef.setDefaults();
				itemDef.modelID = 2384;
				itemDef.modelOffset1 = 0;
				itemDef.modelOffsetY = -1;
				itemDef.modelRotation1 = 84;
				itemDef.modelRotation2 = 1996;
				itemDef.modelZoom = 550;
				itemDef.originalModelColors = new int[] { 26772 };
				itemDef.modifiedModelColors = new int[] { 61 };
				itemDef.name = "Anti-venom (2)";
				itemDef.groundActions = new String[5];
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				break;
			case 11530:
				itemDef.setDefaults();
				itemDef.modelID = 2429;
				itemDef.name = "Anti-venom (2)";
				itemDef.actions = new String[] { null, null, null, null, null, null };
				itemDef.modelZoom = 760;
				itemDef.modelRotation2 = 28;
				itemDef.modelRotation1 = 552;
				itemDef.modelOffsetX = itemDef.modelOffset1 = 0;
				itemDef.stackable = true;
				itemDef.certID = 11529;
				itemDef.certTemplateID = 799;
				break;
			case 11531:
				itemDef.setDefaults();
				itemDef.modelID = 2621;
				itemDef.modelOffset1 = 0;
				itemDef.modelOffsetY = -1;
				itemDef.modelRotation1 = 84;
				itemDef.modelRotation2 = 1996;
				itemDef.modelZoom = 550;
				itemDef.originalModelColors = new int[] { 26772 };
				itemDef.modifiedModelColors = new int[] { 61 };
				itemDef.name = "Anti-venom (1)";
				itemDef.groundActions = new String[5];
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				break;
			case 11532:
				itemDef.setDefaults();
				itemDef.modelID = 2429;
				itemDef.name = "Anti-venom (1)";
				itemDef.actions = new String[] { null, null, null, null, null, null };
				itemDef.modelZoom = 760;
				itemDef.modelRotation2 = 28;
				itemDef.modelRotation1 = 552;
				itemDef.modelOffsetX = itemDef.modelOffset1 = 0;
				itemDef.stackable = true;
				itemDef.certID = 11531;
				itemDef.certTemplateID = 799;
				break;
			case 21077:
				itemDef.setDefaults();
				itemDef.name = "Toxic staff of the dead";
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wield";
				itemDef.actions[2] = "Check";
				itemDef.actions[4] = "Uncharge";
				itemDef.description2 = "A ghastly weapon with evil origins.";
				itemDef.modelID = 19224;
				itemDef.maleWearId = 14402;
				itemDef.femaleWearId = 14402;
				itemDef.modelRotation1 = 512;
				itemDef.modelRotation2 = 1010;
				itemDef.modelZoom = 2150;
				itemDef.modelOffsetY -= 8;
				itemDef.originalModelColors = new int[] { 21947 };
				itemDef.modifiedModelColors = new int[] { 17467 };
				break;
			case 21079:
				itemDef.setDefaults();
				itemDef.name = "Toxic staff (uncharged)";
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wield";
				itemDef.actions[3] = "Dismantle";
				itemDef.description2 = "A ghastly weapon with evil origins.";
				itemDef.modelID = 19225;
				itemDef.maleWearId = 14402;
				itemDef.femaleWearId = 14402;
				itemDef.modelRotation1 = 512;
				itemDef.modelRotation2 = 1010;
				itemDef.modelZoom = 2150;
				itemDef.modelOffsetY -= 8;
				itemDef.originalModelColors = new int[] { 21947 };
				itemDef.modifiedModelColors = new int[] { 17467 };
				break;
			case 21080:
				itemDef.setDefaults();
				itemDef.name = "Zulrah's scales";
				itemDef.actions = new String[5];
				itemDef.description2 = "Scales in which origin from a heavy abyss.";
				itemDef.modelID = 76055;
				itemDef.modelRotation1 = 212;
				itemDef.modelRotation2 = 148;
				itemDef.modelZoom = 1370;
				itemDef.modelOffset1 = 7;
				itemDef.stackIDs = new int[] { 21081, 21082, 21083, 21084, 0, 0, 0, 0, 0, 0 };
				itemDef.stackAmounts = new int[] { 2, 3, 4, 5, 0, 0, 0, 0, 0, 0 };
				itemDef.originalModelColors = new int[] { 21947 };
				itemDef.modifiedModelColors = new int[] { 17467 };
				itemDef.stackable = true;
				break;
			case 21081:
				itemDef.setDefaults();
				itemDef.name = "null";
				itemDef.modelID = 76056;
				itemDef.modelRotation1 = 512;
				itemDef.modelRotation2 = 121;
				itemDef.modelZoom = 1230;
				itemDef.stackIDs = new int[] { 21081, 21082, 21083, 21084, 0, 0, 0, 0, 0, 0 };
				itemDef.stackAmounts = new int[] { 2, 3, 4, 5, 0, 0, 0, 0, 0, 0 };
				itemDef.originalModelColors = new int[] { 21947 };
				itemDef.modifiedModelColors = new int[] { 17467 };
				itemDef.stackable = true;
				break;
			case 21082:
				itemDef.setDefaults();
				itemDef.name = "null";
				itemDef.modelID = 76057;
				itemDef.modelRotation1 = 512;
				itemDef.modelRotation2 = 121;
				itemDef.modelZoom = 1230;
				itemDef.stackIDs = new int[] { 21081, 21082, 21083, 21084, 0, 0, 0, 0, 0, 0 };
				itemDef.stackAmounts = new int[] { 2, 3, 4, 5, 0, 0, 0, 0, 0, 0 };
				itemDef.originalModelColors = new int[] { 21947 };
				itemDef.modifiedModelColors = new int[] { 17467 };
				itemDef.stackable = true;
				break;
			case 21083:
				itemDef.setDefaults();
				itemDef.name = "null";
				itemDef.modelID = 76058;
				itemDef.modelRotation1 = 512;
				itemDef.modelRotation2 = 202;
				itemDef.modelZoom = 1347;
				itemDef.stackIDs = new int[] { 21081, 21082, 21083, 21084, 0, 0, 0, 0, 0, 0 };
				itemDef.stackAmounts = new int[] { 2, 3, 4, 5, 0, 0, 0, 0, 0, 0 };
				itemDef.originalModelColors = new int[] { 21947 };
				itemDef.modifiedModelColors = new int[] { 17467 };
				itemDef.stackable = true;
				break;
			case 21084:
				itemDef.setDefaults();
				itemDef.name = "null";
				itemDef.modelID = 76059;
				itemDef.modelRotation1 = 512;
				itemDef.modelRotation2 = 40;
				itemDef.modelZoom = 1537;
				itemDef.modelOffset1 = 2;
				itemDef.stackIDs = new int[] { 21081, 21082, 21083, 21084, 0, 0, 0, 0, 0, 0 };
				itemDef.stackAmounts = new int[] { 2, 3, 4, 5, 0, 0, 0, 0, 0, 0 };
				itemDef.originalModelColors = new int[] { 21947 };
				itemDef.modifiedModelColors = new int[] { 17467 };
				itemDef.stackable = true;
				break;
			/*
			case 12540:
				itemDef.name = "3rd age longsword";
				itemDef.description2 = "It's a longsword from the 3rd age!";
				itemDef.modelZoom = 1726;
				itemDef.modelRotation1 = 1576;
				itemDef.modelRotation2 = 242;
				itemDef.modelOffset1 = 5;
				itemDef.modelOffsetY = 4;
				itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
				itemDef.modelID = 28633;
				itemDef.maleWearId = 28618;
				itemDef.femaleWearId = 28618;
				break;
				*/
			case 21085:
				itemDef.modelID = 65270;
				itemDef.name = "Completionist cape";
				itemDef.description2 = "We'd pat you on the back, but this cape would get in the way.";
				itemDef.modelZoom = 1316;
				itemDef.modelOffset1 = -1;
				itemDef.modelOffsetY = 24;
				itemDef.modelRotation1 = 252;
				itemDef.modelRotation2 = 1020;
				itemDef.maleWearId = 77000;
				itemDef.femaleWearId = 77000;
				itemDef.groundActions = new String[5];
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				itemDef.actions[3] = "Customise";
				itemDef.originalModelColors = new int[] { 49950, 49950, 49950, 49950 };
				itemDef.modifiedModelColors = new int[] { 65214, 65200, 65186, 62995 };
				break;
			case 21086:
				itemDef.modelID = 65270;
				itemDef.name = "Completionist cape";
				itemDef.description2 = "We'd pat you on the back, but this cape would get in the way.";
				itemDef.modelZoom = 1316;
				itemDef.modelOffset1 = -1;
				itemDef.modelOffsetY = 24;
				itemDef.modelRotation1 = 252;
				itemDef.modelRotation2 = 1020;
				itemDef.maleWearId = 77001;
				itemDef.femaleWearId = 77001;
				itemDef.groundActions = new String[5];
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				itemDef.actions[3] = "Customise";
				itemDef.originalModelColors = new int[] { 10939, 10939, 10939, 10939 };
				itemDef.modifiedModelColors = new int[] { 65214, 65200, 65186, 62995 };
				break;

			case 21087:
				itemDef.modelID = 65270;
				itemDef.name = "Completionist cape";
				itemDef.description2 = "We'd pat you on the back, but this cape would get in the way.";
				itemDef.modelZoom = 1316;
				itemDef.modelOffset1 = -1;
				itemDef.modelOffsetY = 24;
				itemDef.modelRotation1 = 252;
				itemDef.modelRotation2 = 1020;
				itemDef.maleWearId = 77002;
				itemDef.femaleWearId = 77002;
				itemDef.groundActions = new String[5];
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				itemDef.actions[3] = "Customise";
				itemDef.originalModelColors = new int[] { 3016, 3016, 3016, 3016 };
				itemDef.modifiedModelColors = new int[] { 65214, 65200, 65186, 62995 };
				break;
			case 21095:
				itemDef.modelID = 65270;
				itemDef.name = "Completionist cape";
				itemDef.description2 = "We'd pat you on the back, but this cape would get in the way.";
				itemDef.modelZoom = 1316;
				itemDef.modelOffset1 = -1;
				itemDef.modelOffsetY = 24;
				itemDef.modelRotation1 = 252;
				itemDef.modelRotation2 = 1020;
				itemDef.maleWearId = 77003;
				itemDef.femaleWearId = 77003;
				itemDef.groundActions = new String[5];
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				itemDef.actions[3] = "Customise";
				itemDef.originalModelColors = new int[] { 926, 926, 926, 926 };
				itemDef.modifiedModelColors = new int[] { 65214, 65200, 65186, 62995 };
				break;
			case 21099:
				itemDef.modelID = 65270;
				itemDef.name = "Completionist cape";
				itemDef.description2 = "We'd pat you on the back, but this cape would get in the way.";
				itemDef.modelZoom = 1316;
				itemDef.modelOffset1 = -1;
				itemDef.modelOffsetY = 24;
				itemDef.modelRotation1 = 252;
				itemDef.modelRotation2 = 1020;
				itemDef.maleWearId = 77004;
				itemDef.femaleWearId = 77004;
				itemDef.groundActions = new String[5];
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				itemDef.actions[3] = "Customise";
				itemDef.originalModelColors = new int[] { 34503, 34503, 34503, 34503 };
				itemDef.modifiedModelColors = new int[] { 65214, 65200, 65186, 62995 };
				break;
			case 21098:
				itemDef.modelID = 65270;
				itemDef.name = "Completionist cape";
				itemDef.description2 = "We'd pat you on the back, but this cape would get in the way.";
				itemDef.modelZoom = 1316;
				itemDef.modelOffset1 = -1;
				itemDef.modelOffsetY = 24;
				itemDef.modelRotation1 = 252;
				itemDef.modelRotation2 = 1020;
				itemDef.maleWearId = 77005;
				itemDef.femaleWearId = 77005;
				itemDef.groundActions = new String[5];
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				itemDef.actions[3] = "Customise";
				itemDef.originalModelColors = new int[] { 22428, 22428, 22428, 22428 };
				itemDef.modifiedModelColors = new int[] { 65214, 65200, 65186, 62995 };
				break;
			case 21097:
				itemDef.modelID = 65270;
				itemDef.name = "Completionist cape";
				itemDef.description2 = "We'd pat you on the back, but this cape would get in the way.";
				itemDef.modelZoom = 1316;
				itemDef.modelOffset1 = -1;
				itemDef.modelOffsetY = 24;
				itemDef.modelRotation1 = 252;
				itemDef.modelRotation2 = 1020;
				itemDef.maleWearId = 77006;
				itemDef.femaleWearId = 77006;
				itemDef.groundActions = new String[5];
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				itemDef.actions[3] = "Customise";
				itemDef.originalModelColors = new int[] { 43848, 43848, 43848, 43848 };
				itemDef.modifiedModelColors = new int[] { 65214, 65200, 65186, 62995 };
				break;
			case 21096: //White
				itemDef.modelID = 65270;
				itemDef.name = "Completionist cape";
				itemDef.description2 = "We'd pat you on the back, but this cape would get in the way.";
				itemDef.modelZoom = 1316;
				itemDef.modelOffset1 = -1;
				itemDef.modelOffsetY = 24;
				itemDef.modelRotation1 = 252;
				itemDef.modelRotation2 = 1020;
				itemDef.maleWearId = 77007;
				itemDef.femaleWearId = 77007;
				itemDef.groundActions = new String[5];
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				itemDef.actions[3] = "Customise";
				itemDef.originalModelColors = new int[] { 127, 127, 127, 127 };
				itemDef.modifiedModelColors = new int[] { 65214, 65200, 65186, 62995 };
				break;
			case 21093:
				itemDef.modelID = 65270;
				itemDef.name = "Completionist cape";
				itemDef.description2 = "We'd pat you on the back, but this cape would get in the way.";
				itemDef.modelZoom = 1316;
				itemDef.modelOffset1 = -1;
				itemDef.modelOffsetY = 24;
				itemDef.modelRotation1 = 252;
				itemDef.modelRotation2 = 1020;
				itemDef.maleWearId = 77008;
				itemDef.femaleWearId = 77008;
				itemDef.groundActions = new String[5];
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				itemDef.actions[3] = "Customise";
				itemDef.originalModelColors = new int[] { 10388, 10388, 10388, 10388 };
				itemDef.modifiedModelColors = new int[] { 65214, 65200, 65186, 62995 };
				break;
			case 21094:
				itemDef.modelID = 65270;
				itemDef.name = "Completionist cape";
				itemDef.description2 = "We'd pat you on the back, but this cape would get in the way.";
				itemDef.modelZoom = 1316;
				itemDef.modelOffset1 = -1;
				itemDef.modelOffsetY = 24;
				itemDef.modelRotation1 = 252;
				itemDef.modelRotation2 = 1020;
				itemDef.maleWearId = 77009;
				itemDef.femaleWearId = 77009;
				itemDef.groundActions = new String[5];
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				itemDef.actions[3] = "Customise";
				itemDef.originalModelColors = new int[] { 1, 1, 1, 1 };
				itemDef.modifiedModelColors = new int[] { 65214, 65200, 65186, 62995 };
				break;
			case 21078:
				itemDef.setDefaults();
				itemDef.name = "Serpentine visage";
				itemDef.actions = new String[5];
				itemDef.description2 = "A ghastly weapon with evil origins.";
				itemDef.modelID = 19218;
				itemDef.modelRotation2 = 498;
				itemDef.modelZoom = 800;
				itemDef.modelOffset1 = 5;
				itemDef.modelOffsetY = 7;
				break;
			case 21076:
				itemDef.setDefaults();
				itemDef.name = "Magic fang";
				itemDef.actions = new String[5];
				itemDef.description2 = "A fang of dark fortune.";
				itemDef.modelID = 19227;
				itemDef.modelZoom = 1095;
				itemDef.modelRotation2 = 1832;
				itemDef.modelOffsetY = 4;
				itemDef.modelOffset1 = 6;
				itemDef.modelRotation1 = 579;
				break;
			case 21075:
				itemDef.setDefaults();
				itemDef.name = "Armadyl crossbow";
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wield";
				itemDef.description2 = "A ghastly weapon with evil origins.";
				itemDef.modelID = 76050;
				itemDef.maleWearId = 76051;
				itemDef.femaleWearId = 76051;
				itemDef.modelRotation2 = 110;
				itemDef.modelRotation1 = 240;
				itemDef.modelZoom = 1330;
				itemDef.modelOffset1 = -6;
				itemDef.modelOffsetY = -36;
				break;
			case 12926:
				itemDef.setDefaults();
				itemDef.modelID = 25000;
				itemDef.name = "Toxic blowpipe";
				itemDef.description2 = "It's a Toxic blowpipe.";
				itemDef.modelZoom = 1158;
				itemDef.modelRotation1 = 768;
				itemDef.modelRotation2 = 189;
				itemDef.modelOffset1 = -7;
				itemDef.modelOffsetY = 4;
				itemDef.maleWearId = 14403;
				itemDef.femaleWearId = 14403;
				itemDef.actions = new String[] { null, "Wield", "Check", "Unload", "Uncharge" };
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				break;
			case 7968:
				itemDef.name = "Imbue Scroll";
				itemDef.actions = new String[] { null, null, null, null, "Destroy" };
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				break;
			case 11996:
				MobDefinition kbd = MobDefinition.get(50);
				itemDef.imitate(ItemDefinition.get(17488));
				itemDef.name = "Pet King black dragon";
				itemDef.modelID = kbd.npcModels[0];
				itemDef.anInt167 = 40;
				itemDef.anInt192 = 40;
				itemDef.anInt191 = 40;
				itemDef.modelOffset1 = 70;
				itemDef.modelOffsetY -= 60;
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
				break;
			case 13000:
				itemDef.setDefaults();
				MobDefinition kbd2 = MobDefinition.get(50);
				itemDef.imitate(ItemDefinition.get(17488));
				itemDef.name = "Pet Queen white dragon";
				itemDef.modelID = kbd2.npcModels[0];
				itemDef.anInt167 = 40;
				itemDef.anInt192 = 40;
				itemDef.anInt191 = 40;
				itemDef.modelOffset1 = 70;
				itemDef.modelOffsetY -= 60;
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
				itemDef.modifiedModelColors = new int[] { 10502, 43906, 11140, 10378, 0, 11138, 809, 33 };
				itemDef.originalModelColors = new int[] { 100, 100, 226770, 100, 100, 100, 226770, 226770 };
				break;
			case 21050:
				itemDef.setDefaults();
				itemDef.name = "Attack master cape";
				itemDef.modelZoom = 2650; // Model Zoom
				itemDef.maleWearId = 53347; // Male Equip 1
				itemDef.femaleWearId = 53347; // Male Equip 2
				itemDef.modelID = 76001; // Model ID
				itemDef.modelRotation1 = 504; // Model Rotation 1
				itemDef.modelRotation2 = 1000; // Model Rotation 2
				itemDef.modelOffset1 = 5; // Model Offset 1
				itemDef.modelOffsetY = 1; // Model Offset 2
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				itemDef.actions[4] = "Drop";
				break;
			case 21051:
				itemDef.setDefaults();
				itemDef.name = "Defence master cape";
				itemDef.modelZoom = 2650; // Model Zoom
				itemDef.maleWearId = 53348; // Male Equip 1
				itemDef.femaleWearId = 53348; // Male Equip 2
				itemDef.modelID = 76003; // Model ID
				itemDef.modelRotation1 = 504; // Model Rotation 1
				itemDef.modelRotation2 = 1000; // Model Rotation 2
				itemDef.modelOffset1 = 5; // Model Offset 1
				itemDef.modelOffsetY = 1; // Model Offset 2
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				itemDef.actions[4] = "Drop";
				break;
			case 21052:
				itemDef.setDefaults();
				itemDef.name = "Strength master cape";
				itemDef.modelZoom = 2650; // Model Zoom
				itemDef.maleWearId = 53349; // Male Equip 1
				itemDef.femaleWearId = 53349; // Male Equip 2
				itemDef.modelID = 76005; // Model ID
				itemDef.modelRotation1 = 504; // Model Rotation 1
				itemDef.modelRotation2 = 1000; // Model Rotation 2
				itemDef.modelOffset1 = 5; // Model Offset 1
				itemDef.modelOffsetY = 1; // Model Offset 2
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				itemDef.actions[4] = "Drop";
				break;
			case 21053:
				itemDef.setDefaults();
				itemDef.name = "Const. master cape";
				itemDef.modelZoom = 2650; // Model Zoom
				itemDef.maleWearId = 53350; // Male Equip 1
				itemDef.femaleWearId = 53350; // Male Equip 2
				itemDef.modelID = 76007; // Model ID
				itemDef.modelRotation1 = 504; // Model Rotation 1
				itemDef.modelRotation2 = 1000; // Model Rotation 2
				itemDef.modelOffset1 = 5; // Model Offset 1
				itemDef.modelOffsetY = 1; // Model Offset 2
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				itemDef.actions[4] = "Drop";
				break;
			case 21054:
				itemDef.setDefaults();
				itemDef.name = "Ranging master cape";
				itemDef.modelZoom = 2650; // Model Zoom
				itemDef.maleWearId = 53351; // Male Equip 1
				itemDef.femaleWearId = 53351; // Male Equip 2
				itemDef.modelID = 76009; // Model ID
				itemDef.modelRotation1 = 504; // Model Rotation 1
				itemDef.modelRotation2 = 1000; // Model Rotation 2
				itemDef.modelOffset1 = 5; // Model Offset 1
				itemDef.modelOffsetY = 1; // Model Offset 2
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				itemDef.actions[4] = "Drop";
				break;
			case 21055:
				itemDef.setDefaults();
				itemDef.name = "Prayer master cape";
				itemDef.modelZoom = 2650; // Model Zoom
				itemDef.maleWearId = 53352; // Male Equip 1
				itemDef.femaleWearId = 53352; // Male Equip 2
				itemDef.modelID = 76011; // Model ID
				itemDef.modelRotation1 = 504; // Model Rotation 1
				itemDef.modelRotation2 = 1000; // Model Rotation 2
				itemDef.modelOffset1 = 5; // Model Offset 1
				itemDef.modelOffsetY = 1; // Model Offset 2
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				itemDef.actions[4] = "Drop";
				break;
			case 21107:
				itemDef.setDefaults();
				itemDef.name = "Serpentine helm";
				itemDef.modelZoom = 700;
				itemDef.modelRotation1 = 215;
				itemDef.modelRotation2 = 1724;
				itemDef.modelOffsetY = -17;
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, "Wear", "Check", null, "Uncharge" };
				itemDef.modelID = 19220;
				itemDef.maleWearId = 14395;
				itemDef.femaleWearId = 14398;
				break;
			case 21108:
				itemDef.setDefaults();
				itemDef.name = "Trident of the seas";
				itemDef.description = "A weapon from the deep.".getBytes();
				itemDef.maleWearId = 28230;
				itemDef.femaleWearId = 28230;
				itemDef.modelID = 28232;
				itemDef.modelRotation1 = 420;
				itemDef.modelRotation2 = 1130;
				itemDef.modelZoom = 2755;
				itemDef.modelOffset1 = 0;
				itemDef.modelOffsetY = 0;
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wield";
				itemDef.actions[3] = "Check";
				break;
			case 21109:
				itemDef.setDefaults();
				itemDef.name = "Partyhat & specs";
				itemDef.description = "A very special partyhat.".getBytes();
				itemDef.maleWearId = 28505;
				itemDef.femaleWearId = 28576;
				itemDef.modelID = 28693;
				itemDef.modelRotation1 = 242;
				itemDef.modelRotation2 = 0;
				itemDef.modelZoom = 653;
				itemDef.modelOffset1 = 0;
				itemDef.modelOffsetY = -1;
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wield";
				itemDef.actions[3] = "Check";
				break;
			case 21110:
				itemDef.setDefaults();
				itemDef.imitate(get(2572));
				itemDef.name = "Ring of wealth (i)";
				itemDef.anInt184 = 200;
				itemDef.anInt196 = 40;
				break;
			case 21056:
				itemDef.setDefaults();
				itemDef.name = "Magic master cape";
				itemDef.modelZoom = 2650; // Model Zoom
				itemDef.maleWearId = 53353; // Male Equip 1
				itemDef.femaleWearId = 53353; // Male Equip 2
				itemDef.modelID = 76013; // Model ID
				itemDef.modelRotation1 = 504; // Model Rotation 1
				itemDef.modelRotation2 = 1000; // Model Rotation 2
				itemDef.modelOffset1 = 5; // Model Offset 1
				itemDef.modelOffsetY = 1; // Model Offset 2
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				itemDef.actions[4] = "Drop";
				break;
			case 21057:
				itemDef.setDefaults();
				itemDef.name = "Cooking master cape";
				itemDef.modelZoom = 2650; // Model Zoom
				itemDef.maleWearId = 53354; // Male Equip 1
				itemDef.femaleWearId = 53354; // Male Equip 2
				itemDef.modelID = 76015; // Model ID
				itemDef.modelRotation1 = 504; // Model Rotation 1
				itemDef.modelRotation2 = 1000; // Model Rotation 2
				itemDef.modelOffset1 = 5; // Model Offset 1
				itemDef.modelOffsetY = 1; // Model Offset 2
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				itemDef.actions[4] = "Drop";
				break;
			case 21058:
				itemDef.setDefaults();
				itemDef.name = "Woodcut. master cape";
				itemDef.modelZoom = 2650; // Model Zoom
				itemDef.maleWearId = 53355; // Male Equip 1
				itemDef.femaleWearId = 53355; // Male Equip 2
				itemDef.modelID = 76017; // Model ID
				itemDef.modelRotation1 = 504; // Model Rotation 1
				itemDef.modelRotation2 = 1000; // Model Rotation 2
				itemDef.modelOffset1 = 5; // Model Offset 1
				itemDef.modelOffsetY = 1; // Model Offset 2
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				itemDef.actions[4] = "Drop";
				break;
			case 21059:
				itemDef.setDefaults();
				itemDef.name = "Fletching master cape";
				itemDef.modelZoom = 2650; // Model Zoom
				itemDef.maleWearId = 53356; // Male Equip 1
				itemDef.femaleWearId = 53356; // Male Equip 2
				itemDef.modelID = 76019; // Model ID
				itemDef.modelRotation1 = 504; // Model Rotation 1
				itemDef.modelRotation2 = 1000; // Model Rotation 2
				itemDef.modelOffset1 = 5; // Model Offset 1
				itemDef.modelOffsetY = 1; // Model Offset 2
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				itemDef.actions[4] = "Drop";
				break;
			case 21060:
				itemDef.setDefaults();
				itemDef.name = "Fishing master cape";
				itemDef.modelZoom = 2650; // Model Zoom
				itemDef.maleWearId = 53357; // Male Equip 1
				itemDef.femaleWearId = 53357; // Male Equip 2
				itemDef.modelID = 76021; // Model ID
				itemDef.modelRotation1 = 504; // Model Rotation 1
				itemDef.modelRotation2 = 1000; // Model Rotation 2
				itemDef.modelOffset1 = 5; // Model Offset 1
				itemDef.modelOffsetY = 1; // Model Offset 2
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				itemDef.actions[4] = "Drop";
				break;
			case 21061:
				itemDef.setDefaults();
				itemDef.name = "Firemaking master cape";
				itemDef.modelZoom = 2650; // Model Zoom
				itemDef.maleWearId = 53358; // Male Equip 1
				itemDef.femaleWearId = 53358; // Male Equip 2
				itemDef.modelID = 76023; // Model ID
				itemDef.modelRotation1 = 504; // Model Rotation 1
				itemDef.modelRotation2 = 1000; // Model Rotation 2
				itemDef.modelOffset1 = 5; // Model Offset 1
				itemDef.modelOffsetY = 1; // Model Offset 2
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				itemDef.actions[4] = "Drop";
				break;
			case 21062:
				itemDef.setDefaults();
				itemDef.name = "Crafting master cape";
				itemDef.modelZoom = 2650; // Model Zoom
				itemDef.maleWearId = 53359; // Male Equip 1
				itemDef.femaleWearId = 53359; // Male Equip 2
				itemDef.modelID = 76025; // Model ID
				itemDef.modelRotation1 = 504; // Model Rotation 1
				itemDef.modelRotation2 = 1000; // Model Rotation 2
				itemDef.modelOffset1 = 5; // Model Offset 1
				itemDef.modelOffsetY = 1; // Model Offset 2
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				itemDef.actions[4] = "Drop";
				break;
			case 21063:
				itemDef.setDefaults();
				itemDef.name = "Smithing master cape";
				itemDef.modelZoom = 2650; // Model Zoom
				itemDef.maleWearId = 53360; // Male Equip 1
				itemDef.femaleWearId = 53360; // Male Equip 2
				itemDef.modelID = 76027; // Model ID
				itemDef.modelRotation1 = 504; // Model Rotation 1
				itemDef.modelRotation2 = 1000; // Model Rotation 2
				itemDef.modelOffset1 = 5; // Model Offset 1
				itemDef.modelOffsetY = 1; // Model Offset 2
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				itemDef.actions[4] = "Drop";
				break;
			case 21064:
				itemDef.setDefaults();
				itemDef.name = "Mining master cape";
				itemDef.modelZoom = 2650; // Model Zoom
				itemDef.maleWearId = 53361; // Male Equip 1
				itemDef.femaleWearId = 53361; // Male Equip 2
				itemDef.modelID = 76029; // Model ID
				itemDef.modelRotation1 = 504; // Model Rotation 1
				itemDef.modelRotation2 = 1000; // Model Rotation 2
				itemDef.modelOffset1 = 5; // Model Offset 1
				itemDef.modelOffsetY = 1; // Model Offset 2
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				itemDef.actions[4] = "Drop";
				break;
			case 21065:
				itemDef.setDefaults();
				itemDef.name = "Herblore master cape";
				itemDef.modelZoom = 2650; // Model Zoom
				itemDef.maleWearId = 53362; // Male Equip 1
				itemDef.femaleWearId = 53362; // Male Equip 2
				itemDef.modelID = 76031; // Model ID
				itemDef.modelRotation1 = 504; // Model Rotation 1
				itemDef.modelRotation2 = 1000; // Model Rotation 2
				itemDef.modelOffset1 = 5; // Model Offset 1
				itemDef.modelOffsetY = 1; // Model Offset 2
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				itemDef.actions[4] = "Drop";
				break;
			case 21066:
				itemDef.setDefaults();
				itemDef.name = "Agility master cape";
				itemDef.modelZoom = 2650; // Model Zoom
				itemDef.maleWearId = 53363; // Male Equip 1
				itemDef.femaleWearId = 53363; // Male Equip 2
				itemDef.modelID = 76033; // Model ID
				itemDef.modelRotation1 = 504; // Model Rotation 1
				itemDef.modelRotation2 = 1000; // Model Rotation 2
				itemDef.modelOffset1 = 5; // Model Offset 1
				itemDef.modelOffsetY = 1; // Model Offset 2
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				itemDef.actions[4] = "Drop";
				break;
			case 21067:
				itemDef.setDefaults();
				itemDef.name = "Thieving master cape";
				itemDef.modelZoom = 2650; // Model Zoom
				itemDef.maleWearId = 53364; // Male Equip 1
				itemDef.femaleWearId = 53364; // Male Equip 2
				itemDef.modelID = 76035; // Model ID
				itemDef.modelRotation1 = 504; // Model Rotation 1
				itemDef.modelRotation2 = 1000; // Model Rotation 2
				itemDef.modelOffset1 = 5; // Model Offset 1
				itemDef.modelOffsetY = 1; // Model Offset 2
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				itemDef.actions[4] = "Drop";
				break;
			case 21068:
				itemDef.setDefaults();
				itemDef.name = "Slayer master cape";
				itemDef.modelZoom = 2650; // Model Zoom
				itemDef.maleWearId = 53365; // Male Equip 1
				itemDef.femaleWearId = 53365; // Male Equip 2
				itemDef.modelID = 76037; // Model ID
				itemDef.modelRotation1 = 504; // Model Rotation 1
				itemDef.modelRotation2 = 1000; // Model Rotation 2
				itemDef.modelOffset1 = 5; // Model Offset 1
				itemDef.modelOffsetY = 1; // Model Offset 2
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				itemDef.actions[4] = "Drop";
				break;
			case 21069:
				itemDef.setDefaults();
				itemDef.name = "Farming master cape";
				itemDef.modelZoom = 2650; // Model Zoom
				itemDef.maleWearId = 53366; // Male Equip 1
				itemDef.femaleWearId = 53366; // Male Equip 2
				itemDef.modelID = 76039; // Model ID
				itemDef.modelRotation1 = 504; // Model Rotation 1
				itemDef.modelRotation2 = 1000; // Model Rotation 2
				itemDef.modelOffset1 = 5; // Model Offset 1
				itemDef.modelOffsetY = 1; // Model Offset 2
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				itemDef.actions[4] = "Drop";
				break;
			case 21070:
				itemDef.setDefaults();
				itemDef.name = "Runecraft. master cape";
				itemDef.modelZoom = 2650; // Model Zoom
				itemDef.maleWearId = 53367; // Male Equip 1
				itemDef.femaleWearId = 53367; // Male Equip 2
				itemDef.modelID = 76041; // Model ID
				itemDef.modelRotation1 = 504; // Model Rotation 1
				itemDef.modelRotation2 = 1000; // Model Rotation 2
				itemDef.modelOffset1 = 5; // Model Offset 1
				itemDef.modelOffsetY = 1; // Model Offset 2
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				itemDef.actions[4] = "Drop";
				break;
			case 21071:
				itemDef.setDefaults();
				itemDef.name = "Constr. master cape";
				itemDef.modelZoom = 2650; // Model Zoom
				itemDef.maleWearId = 53368; // Male Equip 1
				itemDef.femaleWearId = 53368; // Male Equip 2
				itemDef.modelID = 76043; // Model ID
				itemDef.modelRotation1 = 504; // Model Rotation 1
				itemDef.modelRotation2 = 1000; // Model Rotation 2
				itemDef.modelOffset1 = 5; // Model Offset 1
				itemDef.modelOffsetY = 1; // Model Offset 2
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				itemDef.actions[4] = "Drop";
				break;
			case 6349:
				itemDef.name = "Paiman's Sandals";
				break;
			case 1844:
				itemDef.name = "Paiman's robe top";
				break;
			case 1845:
				itemDef.name = "Paiman's robe legs";
				break;
			case 4611:
				itemDef.name = "Paiman's Turban";
				break;
			case 21072:
				itemDef.setDefaults();
				itemDef.name = "Hunter master cape";
				itemDef.modelZoom = 2650; // Model Zoom
				itemDef.maleWearId = 53369; // Male Equip 1
				itemDef.femaleWearId = 53369; // Male Equip 2
				itemDef.modelID = 76045; // Model ID
				itemDef.modelRotation1 = 504; // Model Rotation 1
				itemDef.modelRotation2 = 1000; // Model Rotation 2
				itemDef.modelOffset1 = 5; // Model Offset 1
				itemDef.modelOffsetY = 1; // Model Offset 2
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				itemDef.actions[4] = "Drop";
				break;
			case 21073:
				itemDef.setDefaults();
				itemDef.name = "Summoning master cape";
				itemDef.modelZoom = 2650; // Model Zoom
				itemDef.maleWearId = 53370; // Male Equip 1
				itemDef.femaleWearId = 53370; // Male Equip 2
				itemDef.modelID = 76047; // Model ID
				itemDef.modelRotation1 = 504; // Model Rotation 1
				itemDef.modelRotation2 = 1000; // Model Rotation 2
				itemDef.modelOffset1 = 5; // Model Offset 1
				itemDef.modelOffsetY = 1; // Model Offset 2
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				itemDef.actions[4] = "Drop";
				break;
			case 13001:
				itemDef.setDefaults();
				itemDef.imitate(get(12458));
				itemDef.name = "Pet Rock crab";
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
				itemDef.stackable = false;
				break;
			case 12487:
				itemDef.name = "Raccoon";
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
				itemDef.stackable = false;
				break;
			case 7500:
				itemDef.name = "Jonny";
				itemDef.actions = new String[5];
				itemDef.actions[4] = "Drop";
				itemDef.actions[0] = "Roast";
				itemDef.actions[2] = "Shave head";
				break;
			case 13002:
				itemDef.setDefaults();
				itemDef.imitate(get(12458));
				itemDef.name = "Pet Rock crab";
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
				itemDef.stackable = false;
				break;
			case 13003:
				itemDef.setDefaults();
				itemDef.imitate(get(12458));
				itemDef.name = "Pet Rock crab";
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
				itemDef.stackable = false;
				break;
			case 13004:
				itemDef.setDefaults();
				itemDef.imitate(get(12458));
				itemDef.name = "Pet Rock crab";
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
				itemDef.stackable = false;
				break;
			case 13005:
				itemDef.setDefaults();
				itemDef.imitate(get(12458));
				itemDef.name = "Pet Rock crab";
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
				itemDef.stackable = false;
				break;
			case 13006:
				itemDef.setDefaults();
				itemDef.imitate(get(12458));
				itemDef.name = "Pet Rock crab";
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
				itemDef.stackable = false;
				break;
			case 13007:
				itemDef.setDefaults();
				itemDef.imitate(get(12458));
				itemDef.name = "Pet Rock crab";
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
				itemDef.stackable = false;
				break;
			case 13008:
				itemDef.setDefaults();
				itemDef.imitate(get(12458));
				itemDef.name = "Pet Rock crab";
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
				itemDef.stackable = false;
				break;
			case 11997:
				itemDef.setDefaults();
				itemDef.imitate(get(12458));
				itemDef.name = "Pet General graardor";
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
				break;
			case 11978:
				itemDef.setDefaults();
				itemDef.imitate(get(12458));
				itemDef.name = "Pet TzTok-Jad";
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
				break;
			case 12001:
				itemDef.setDefaults();
				itemDef.imitate(get(12458));
				itemDef.name = "Pet Corporeal beast";
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
				break;
			case 12002:
				itemDef.setDefaults();
				itemDef.imitate(get(12458));
				itemDef.name = "Pet Kree'arra";
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
				break;
			case 12003:
				itemDef.setDefaults();
				itemDef.imitate(get(12458));
				itemDef.name = "Pet K'ril tsutsaroth";
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
				break;
			case 12004:
				itemDef.setDefaults();
				itemDef.imitate(get(12458));
				itemDef.name = "Pet Commander zilyana";
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
				break;
			case 5020:
				itemDef.name = "Claim 10 Yells";
				itemDef.actions = new String[] { "Claim", null, null, null, "Drop" };
				itemDef.stackable = true;
				break;
			case 5021:
				itemDef.name = "Claim 50 Yells";
				itemDef.actions = new String[] { "Claim", null, null, null, "Drop" };
				itemDef.stackable = true;
				break;
			case 5022:
				itemDef.name = "Claim 100 Yells";
				itemDef.actions = new String[] { "Claim", null, null, null, "Drop" };
				itemDef.stackable = true;
				break;
			case 10944:
				itemDef.setDefaults();
				itemDef.imitate(get(20935));
				itemDef.name = "Vote Reward Book";
				itemDef.actions = new String[] { "Claim", null, null, null, "Drop" };
				itemDef.stackable = true;
				break;
			case 12005:
				itemDef.setDefaults();
				itemDef.imitate(get(12458));
				itemDef.name = "Pet Dagannoth supreme";
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
				break;
			case 12006:
				itemDef.setDefaults();
				itemDef.imitate(get(12458));
				itemDef.name = "Pet Dagannoth prime";
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
				break;
			case 11990:
				itemDef.setDefaults();
				itemDef.imitate(get(12458));
				itemDef.name = "Pet Dagannoth rex";
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
				break;
			case 11991:
				itemDef.setDefaults();
				itemDef.imitate(get(12458));
				itemDef.name = "Pet Frost dragon";
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
				break;
			case 11992:
				itemDef.setDefaults();
				itemDef.imitate(get(12458));
				itemDef.name = "Pet Tormented demon";
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
				break;
			case 11993:
				itemDef.setDefaults();
				itemDef.imitate(get(12458));
				itemDef.name = "Pet Kalphite queen";
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
				break;
			case 11994:
				itemDef.setDefaults();
				itemDef.imitate(get(12458));
				itemDef.name = "Pet Slash bash";
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
				break;
			case 11989:
				itemDef.setDefaults();
				itemDef.imitate(get(12458));
				itemDef.name = "Pet Phoenix";
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
				break;
			case 11988:
				itemDef.setDefaults();
				itemDef.imitate(get(12458));
				itemDef.name = "Pet Bandos avatar";
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
				break;
			case 11987:
				itemDef.setDefaults();
				itemDef.imitate(get(12458));
				itemDef.name = "Pet Nex";
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
				break;
			case 11986:
				itemDef.setDefaults();
				itemDef.imitate(get(12458));
				itemDef.name = "Pet Jungle strykewyrm";
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
				break;
			case 11985:
				itemDef.setDefaults();
				itemDef.imitate(get(12458));
				itemDef.name = "Pet Desert strykewyrm";
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
				break;
			case 11984:
				itemDef.setDefaults();
				itemDef.imitate(get(12458));
				itemDef.name = "Pet Ice strykewyrm";
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
				break;
			case 11983:
				itemDef.setDefaults();
				itemDef.imitate(get(12458));
				itemDef.name = "Pet Green dragon";
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
				break;
			case 11982:
				itemDef.setDefaults();
				itemDef.imitate(get(12458));
				itemDef.name = "Pet Baby blue dragon";
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
				break;
			case 11981:
				itemDef.setDefaults();
				itemDef.imitate(get(12458));
				itemDef.name = "Pet Blue dragon";
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
				break;
			case 11979:
				itemDef.setDefaults();
				itemDef.imitate(get(12458));
				itemDef.name = "Pet Black dragon";
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
				break;
			case 1543:
			case 1544:
			case 1545:
			case 1546:
			case 1547:
			case 1548:
				itemDef.name = "Wilderness key";
				itemDef.description2 = "This key is used in the deep wilderness.";
				break;
			case 14667:
				itemDef.name = "Zombie fragment";
				itemDef.modelID = ItemDefinition.get(14639).modelID;
				break;
			case 15182:
				itemDef.actions[0] = "Bury";
				break;
			case 15084:
				itemDef.actions[0] = "Roll";
				itemDef.name = "Dice (up to 100)";
				itemDef2 = ItemDefinition.get(15098);
				itemDef.modelID = itemDef2.modelID;
				itemDef.modelOffset1 = itemDef2.modelOffset1;
				itemDef.modelOffsetX = itemDef2.modelOffsetX;
				itemDef.modelOffsetY = itemDef2.modelOffsetY;
				itemDef.modelZoom = itemDef2.modelZoom;
				break;
			case 2996:
				itemDef.name = "Agility ticket";
				break;
			case 5510:
			case 5512:
			case 5509:
				itemDef.actions = new String[] { "Fill", null, "Empty", "Check", null, null };
				break;
			case 11998:
				itemDef.name = "Scimitar";
				itemDef.actions = new String[] { null, null, null, null, null, null };
				break;
			case 11999:
				itemDef.name = "Scimitar";
				itemDef.actions = new String[] { null, null, null, null, null, null };
				itemDef.modelZoom = 700;
				itemDef.modelRotation2 = 0;
				itemDef.modelRotation1 = 350;
				itemDef.modelID = 2429;
				itemDef.modelOffsetX = itemDef.modelOffset1 = 0;
				itemDef.stackable = true;
				itemDef.certID = 11998;
				itemDef.certTemplateID = 799;
				break;
			case 1389:
				itemDef.name = "Staff";
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				itemDef.actions[4] = "Drop";
				break;
			case 1390:
				itemDef.name = "Staff";
				itemDef.actions = new String[] { null, null, null, null, null, null };
				break;
			case 17401:
				itemDef.name = "Damaged Hammer";
				itemDef.actions = new String[] { null, null, null, null, null, null };
				break;
			case 17402:
				itemDef.name = "Damaged hammer";
				itemDef.actions = new String[] { null, null, null, null, null, null };
				itemDef.modelZoom = 760;
				itemDef.modelRotation2 = 28;
				itemDef.modelRotation1 = 552;
				itemDef.modelID = 2429;
				itemDef.modelOffsetX = itemDef.modelOffset1 = 0;
				itemDef.stackable = true;
				itemDef.certID = 17401;
				itemDef.certTemplateID = 799;
				break;
			case 15009:
				itemDef.name = "Gold ring";
				itemDef.actions = new String[] { null, null, null, null, null, null };
				break;
			case 15010:
				itemDef.modelID = 2429;
				itemDef.name = "Gold ring";
				itemDef.actions = new String[] { null, null, null, null, null, null };
				itemDef.modelZoom = 760;
				itemDef.modelRotation2 = 28;
				itemDef.modelRotation1 = 552;
				itemDef.modelOffsetX = itemDef.modelOffset1 = 0;
				itemDef.stackable = true;
				itemDef.certID = 15009;
				itemDef.certTemplateID = 799;
				break;

			case 11884:
				itemDef.name = "Black gold-trim set (sk)";
				itemDef.actions = new String[] { "Open", null, null, null, null, null };
				break;
			case 14207:
				itemDef.setDefaults();
				itemDef.stackable = false;
				itemDef.groundActions = new String[5];
				itemDef.name = "Potion flask";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.groundActions[2] = "Take";
				itemDef.modelID = 61741;
				itemDef.actions = new String[] { null, null, null, null, null, "Drop" };
				break;
			case 14208:
				itemDef.setDefaults();
				itemDef.modelID = 2429;
				itemDef.name = "Potion flask";
				itemDef.actions = new String[] { null, null, null, null, null, null };
				itemDef.modelZoom = 760;
				itemDef.modelRotation2 = 28;
				itemDef.modelRotation1 = 552;
				itemDef.modelOffsetX = itemDef.modelOffset1 = 0;
				itemDef.stackable = true;
				itemDef.certID = 14207;
				itemDef.certTemplateID = 799;
				break;
			case 14200:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.stackable = false;
				itemDef.name = "Prayer flask (6)";
				itemDef.description2 = "6 doses of prayer potion.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 28488 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61732;
				break;
			case 14198:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.stackable = false;
				itemDef.name = "Prayer flask (5)";
				itemDef.description2 = "5 doses of prayer potion.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 28488 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61729;
				break;
			case 19672:
			case 19673:
			case 19674:
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
				break;
			case 21000:
				itemDef.setDefaults();
				itemDef.modelID = 70000;
				itemDef.name = "Lime whip";
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				itemDef.modelZoom = 840;
				itemDef.maleWearId = 70001;
				itemDef.femaleWearId = 70001;
				itemDef.modelRotation1 = 280;
				itemDef.modelRotation2 = 0;
				itemDef.modelOffsetY = 56;
				itemDef.maleWieldY = -7;
				itemDef.originalModelColors = new int[] { 17350, 17350, 0, 17350, 17350, 17350 };
				itemDef.modifiedModelColors = new int[] { 56428, 55404, 30338, 399, 16319 };
				break;
			case 11601:
				itemDef.setDefaults();
				itemDef.name = "Dragonbone upgrade kit";
				itemDef.modelZoom = 1016;
				itemDef.modelRotation1 = 390;
				itemDef.modelRotation2 = 2043;
				itemDef.modelOffset1 = 3;
				itemDef.modelOffsetY = 1;
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, null, null, null, "Drop" };
				itemDef.modelID = 70148;
				break;
			case 11602:
				itemDef.setDefaults();
				itemDef.name = "Dragonbone mage hat";
				itemDef.modelZoom = 1250;
				itemDef.modelRotation1 = 229;
				itemDef.modelRotation2 = 177;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -74;
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, "Wear", "Split", null, "Drop" };
				itemDef.modelID = 70722;
				itemDef.maleWearId = 70657;
				itemDef.femaleWearId = 70686;
				break;
			case 11603:
				itemDef.setDefaults();
				itemDef.name = "Dragonbone mage top";
				itemDef.modelZoom = 1711;
				itemDef.modelRotation1 = 500;
				itemDef.modelRotation2 = 2047;
				itemDef.modelOffsetY = 3;
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, "Wear", "Split", null, "Drop" };
				itemDef.modelID = 70723;
				itemDef.maleWearId = 70668;
				itemDef.femaleWearId = 70697;
				break;
			case 11604:
				itemDef.setDefaults();
				itemDef.name = "Dragonbone mage bottoms";
				itemDef.modelZoom = 1772;
				itemDef.modelRotation1 = 431;
				itemDef.modelRotation2 = 1818;
				itemDef.modelOffset1 = -1;
				itemDef.modelOffsetY = 1;
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, "Wear", "Split", null, "Drop" };
				itemDef.modelID = 70716;
				itemDef.maleWearId = 70664;
				itemDef.femaleWearId = 70693;
				break;
			case 11605:
				itemDef.setDefaults();
				itemDef.name = "Dragonbone gloves (magic)";
				itemDef.modelZoom = 592;
				itemDef.modelRotation1 = 215;
				itemDef.modelRotation2 = 175;
				itemDef.modelOffset1 = 2;
				itemDef.modelOffsetY = -17;
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, "Wear", "Split", null, "Drop" };
				itemDef.modelID = 70721;
				itemDef.maleWearId = 70654;
				itemDef.femaleWearId = 70683;
				break;
			case 11606:
				itemDef.setDefaults();
				itemDef.name = "Dragonbone boots (magic)";
				itemDef.modelZoom = 855;
				itemDef.modelRotation1 = 242;
				itemDef.modelRotation2 = 0;
				itemDef.modelOffset1 = 3;
				itemDef.modelOffsetY = 18;
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, "Wear", "Split", null, "Drop" };
				itemDef.modelID = 70719;
				itemDef.maleWearId = 70652;
				itemDef.femaleWearId = 70681;
				break;
			case 11607:
				itemDef.setDefaults();
				itemDef.name = "Dragonbone full helm";
				itemDef.modelZoom = 780;
				itemDef.modelRotation1 = 229;
				itemDef.modelRotation2 = 177;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -34;
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, "Wear", "Split", null, "Drop" };
				itemDef.modelID = 70729;
				itemDef.maleWearId = 70662;
				itemDef.femaleWearId = 70691;
				break;
			case 11608:
				itemDef.setDefaults();
				itemDef.name = "Dragonbone platebody";
				itemDef.modelZoom = 1758;
				itemDef.modelRotation1 = 512;
				itemDef.modelRotation2 = 0;
				itemDef.modelOffsetY = 1;
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, "Wear", "Split", null, "Drop" };
				itemDef.modelID = 70730;
				itemDef.maleWearId = 70669;
				itemDef.femaleWearId = 70698;
				break;
			case 11609:
				itemDef.setDefaults();
				itemDef.name = "Dragonbone gloves (melee)";
				itemDef.modelZoom = 760;
				itemDef.modelRotation1 = 552;
				itemDef.modelRotation2 = 28;
				itemDef.modelOffset1 = 3;
				itemDef.modelOffsetY = 11;
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, "Wear", "Split", null, "Drop" };
				itemDef.modelID = 70725;
				itemDef.maleWearId = 70655;
				itemDef.femaleWearId = 70684;
				break;
			case 11610:
				itemDef.setDefaults();
				itemDef.name = "Dragonbone boots (melee)";
				itemDef.modelZoom = 921;
				itemDef.modelRotation1 = 303;
				itemDef.modelRotation2 = 257;
				itemDef.modelOffset1 = 12;
				itemDef.modelOffsetY = 18;
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, "Wear", "Split", null, "Drop" };
				itemDef.modelID = 70731;
				itemDef.maleWearId = 70653;
				itemDef.femaleWearId = 70682;
				break;
			case 11611:
				itemDef.setDefaults();
				itemDef.name = "Dragonbone platelegs";
				itemDef.modelZoom = 1772;
				itemDef.modelRotation1 = 512;
				itemDef.modelRotation2 = 0;
				itemDef.modelOffsetY = 5;
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, "Wear", "Split", null, "Drop" };
				itemDef.modelID = 70728;
				itemDef.maleWearId = 70665;
				itemDef.femaleWearId = 70695;
				break;
			case 11612:
				itemDef.setDefaults();
				itemDef.name = "Dragonbone plateskirt";
				itemDef.modelZoom = 1772;
				itemDef.modelRotation1 = 512;
				itemDef.modelRotation2 = 0;
				itemDef.modelOffsetY = 5;
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, "Wear", "Split", null, "Drop" };
				itemDef.modelID = 70724;
				itemDef.maleWearId = 70666;
				itemDef.femaleWearId = 70694;
				break;
			case 11613:
				itemDef.setDefaults();
				itemDef.name = "Dragon kiteshield";
				itemDef.modelZoom = 1378;
				itemDef.modelRotation1 = 264;
				itemDef.modelRotation2 = 1913;
				itemDef.modelOffset1 = 7;
				itemDef.modelOffsetY = 58;
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
				itemDef.modelID = 70128;
				itemDef.maleWearId = 70672;
				itemDef.femaleWearId = 70672;
				break;
			case 21134:
				itemDef.setDefaults();
				itemDef.name = "Royal dragonhide";
				itemDef.modelZoom = 1190;
				itemDef.modelRotation1 = 440;
				itemDef.modelRotation2 = 116;
				itemDef.modelOffset1 = -1;
				itemDef.modelOffsetY = -2;
				itemDef.modifiedModelColors = new int[] { 49950, 49997 };
				itemDef.originalModelColors = new int[] { 22416, 22424 };
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, null, null, null, "Drop" };
				itemDef.modelID = 2356;
				break;
			case 21043:
				itemDef.setDefaults();
				itemDef.imitate(get(1747));
				itemDef.modifiedModelColors = new int[] { 22416, 22424 };
				itemDef.originalModelColors = new int[] { 100, 226770 };
				itemDef.name = "White dragonhide";
				break;
			case 11614:
				itemDef.setDefaults();
				itemDef.name = "Dragonbone vambraces";
				itemDef.modelZoom = 537;
				itemDef.modelRotation1 = 215;
				itemDef.modelRotation2 = 175;
				itemDef.modelOffset1 = 2;
				itemDef.modelOffsetY = -9;
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, "Wear", "Split", null, "Drop" };
				itemDef.modelID = 70738;
				itemDef.maleWearId = 70656;
				itemDef.femaleWearId = 70685;
				break;
			case 11615:
				itemDef.setDefaults();
				itemDef.name = "Dragonbone chaps";
				itemDef.modelZoom = 1772;
				itemDef.modelRotation1 = 512;
				itemDef.modelRotation2 = 0;
				itemDef.modelOffsetY = 5;
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, "Wear", "Split", null, "Drop" };
				itemDef.modelID = 70735;
				itemDef.maleWearId = 70667;
				itemDef.femaleWearId = 70696;
				break;
			case 21044:
				itemDef.setDefaults();
				itemDef.imitate(get(2503));
				itemDef.modifiedModelColors = new int[] { 22416, 22424 };
				itemDef.originalModelColors = new int[] { 100, 226770 };
				itemDef.name = "White d'hide body";
				break;
			case 21045:
				itemDef.setDefaults();
				itemDef.imitate(get(2497));
				itemDef.modifiedModelColors = new int[] { 22416, 22424, 7566, 57, 61, 1821, 5907, 21898, 20884, 926, 5012 };
				itemDef.originalModelColors = new int[] { 100, 226770, 100, 100, 100, 100, 226770, 226770, 226770, 226770,
						100 };
				itemDef.name = "White d'hide chaps";
				break;
			case 21046:
				itemDef.setDefaults();
				itemDef.imitate(get(2491));
				itemDef.modifiedModelColors = new int[] { 8472, 8720, 22416 };
				itemDef.originalModelColors = new int[] { 100, 226770, 100 };
				itemDef.name = "White d'hide vamb";
				break;
			case 11618:
				itemDef.setDefaults();
				itemDef.name = "Dragonbone body";
				itemDef.modelZoom = 1758;
				itemDef.modelRotation1 = 512;
				itemDef.modelRotation2 = 0;
				itemDef.modelOffset1 = 1;
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, "Wear", "Split", null, "Drop" };
				itemDef.modelID = 70736;
				itemDef.maleWearId = 70670;
				itemDef.femaleWearId = 70699;
				break;
			case 11616:
				itemDef.setDefaults();
				itemDef.name = "Dragonbone coif";
				itemDef.modelZoom = 780;
				itemDef.modelRotation1 = 229;
				itemDef.modelRotation2 = 177;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -34;
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, "Wear", "Split", null, "Drop" };
				itemDef.modelID = 70733;
				itemDef.maleWearId = 70663;
				itemDef.femaleWearId = 70692;
				break;
			case 11617:
				itemDef.setDefaults();
				itemDef.name = "Dragonbone boots (ranged)";
				itemDef.modelZoom = 921;
				itemDef.modelRotation1 = 303;
				itemDef.modelRotation2 = 257;
				itemDef.modelOffset1 = 12;
				itemDef.modelOffsetY = 18;
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, "Wear", "Split", null, "Drop" };
				itemDef.modelID = 70731;
				itemDef.maleWearId = 70653;
				itemDef.femaleWearId = 70682;
				break;
			case 11620:
				itemDef.setDefaults();
				itemDef.name = "Dragonbone bolt stabiliser";
				itemDef.modelZoom = 789;
				itemDef.modelRotation1 = 175;
				itemDef.modelRotation2 = 81;
				itemDef.modelOffset1 = 5;
				itemDef.modelOffsetY = 3;
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, null, null, null, "Drop" };
				itemDef.modelID = 70153;
				break;
			case 11621:
				itemDef.setDefaults();
				itemDef.name = "Royal frame";
				itemDef.modelZoom = 946;
				itemDef.modelRotation1 = 337;
				itemDef.modelRotation2 = 444;
				itemDef.modelOffset1 = 2;
				itemDef.modelOffsetY = -17;
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, null, null, null, "Drop" };
				itemDef.modelID = 70130;
				break;
			case 11622:
				itemDef.setDefaults();
				itemDef.name = "Royal sight";
				itemDef.modelZoom = 1045;
				itemDef.modelRotation1 = 229;
				itemDef.modelRotation2 = 450;
				itemDef.modelOffset1 = -3;
				itemDef.modelOffsetY = 28;
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, null, null, null, "Drop" };
				itemDef.modelID = 70258;
				break;
			case 11623:
				itemDef.setDefaults();
				itemDef.name = "Royal torsion spring";
				itemDef.modelZoom = 724;
				itemDef.modelRotation1 = 175;
				itemDef.modelRotation2 = 350;
				itemDef.modelOffset1 = -1;
				itemDef.modelOffsetY = 5;
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, null, null, null, "Drop" };
				itemDef.modelID = 70146;
				break;
			case 11624:
				itemDef.setDefaults();
				itemDef.name = "Dragonbone crossbow";
				itemDef.modelZoom = 1250;
				itemDef.modelRotation1 = 269;
				itemDef.modelRotation2 = 2007;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = 29;
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, "Wield", "Check state", null, "Destroy" };
				itemDef.modelID = 70257;
				itemDef.maleWearId = 70671;
				itemDef.femaleWearId = 70671;
				break;
			case 11625:
				itemDef.setDefaults();
				itemDef.stackable = true;
				itemDef.name = "Dragonbone bolts";
				itemDef.modelZoom = 1220;
				itemDef.modelRotation1 = 216;
				itemDef.modelRotation2 = 100;
				itemDef.modelOffset1 = 6;
				itemDef.modelOffsetY = -29;
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
				itemDef.modelID = 70140;
				break;
			case 21088:
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
				itemDef.originalModelColors = new int[1];
				itemDef.modifiedModelColors = new int[1];
				itemDef.originalModelColors[0] = RandomColor.currentColour;
				itemDef.modifiedModelColors[0] = 926;
				itemDef.modelID = 2635;
				itemDef.modelZoom = 440;
				itemDef.modelRotation1 = 76;
				itemDef.modelRotation2 = 1850;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = 1;
				itemDef.maleWearId = 187;
				itemDef.femaleWearId = 363;
				itemDef.maleDialogue = 39372;
				itemDef.femaleDialogue = 39372;
				itemDef.name = "Disco hat";
				itemDef.description = "A colorful hat.".getBytes();
				break;
			case 21001:
				itemDef.setDefaults();
				itemDef.modelID = 70000;
				itemDef.name = "Aqua whip";
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				itemDef.modelZoom = 840;
				itemDef.maleWearId = 70001;
				itemDef.femaleWearId = 70001;
				itemDef.modelRotation1 = 280;
				itemDef.modelRotation2 = 0;
				itemDef.modelOffsetY = 56;
				itemDef.maleWieldY = -7;
				itemDef.originalModelColors = new int[] { 226770, 226770, 0, 226770, 226770 };
				itemDef.modifiedModelColors = new int[] { 56428, 55404, 30338, 399, 16319 };
				break;
			case 21002:
				itemDef.setDefaults();
				itemDef.modelID = 70000;
				itemDef.name = "Pink whip";
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				itemDef.modelZoom = 840;
				itemDef.maleWearId = 70001;
				itemDef.femaleWearId = 70001;
				itemDef.modelRotation1 = 280;
				itemDef.modelRotation2 = 0;
				itemDef.modelOffsetY = 56;
				// itemDef.maleXOffset = 7;
				itemDef.maleWieldY = -7;
				itemDef.originalModelColors = new int[] { 123770, 123770, 0, 123770, 123770 };
				itemDef.modifiedModelColors = new int[] { 56428, 55404, 30338, 399, 16319 };
				break;
			case 21003:
				itemDef.setDefaults();
				itemDef.modelID = 70000;
				itemDef.name = "Premium whip";
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				itemDef.modelZoom = 840;
				itemDef.maleWearId = 70001;
				itemDef.femaleWearId = 70001;
				itemDef.modelRotation1 = 280;
				itemDef.modelRotation2 = 0;
				itemDef.modelOffsetY = 56;
				// itemDef.maleXOffset = 7;
				itemDef.maleWieldY = -7;
				itemDef.originalModelColors = new int[] { 933, 933, 0, 933, 933 };
				itemDef.modifiedModelColors = new int[] { 56428, 55404, 30338, 399, 16319 };
				break;
			case 21004:
				itemDef.setDefaults();
				itemDef.modelID = 70000;
				itemDef.name = "White whip";
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				itemDef.modelZoom = 840;
				itemDef.maleWearId = 70001;
				itemDef.femaleWearId = 70001;
				itemDef.modelRotation1 = 280;
				itemDef.modelRotation2 = 0;
				itemDef.modelOffsetY = 56;
				// itemDef.maleXOffset = 7;
				itemDef.maleWieldY = -7;
				itemDef.originalModelColors = new int[] { 100, 100, 0, 100, 100 };
				itemDef.modifiedModelColors = new int[] { 56428, 55404, 30338, 399, 16319 };
				break;
			case 21005:
				itemDef.setDefaults();
				itemDef.modelID = 70000;
				itemDef.name = "Yellow whip";
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				itemDef.modelZoom = 840;
				itemDef.maleWearId = 70001;
				itemDef.femaleWearId = 70001;
				itemDef.modelRotation1 = 280;
				itemDef.modelRotation2 = 0;
				itemDef.modelOffsetY = 56;
				// itemDef.maleXOffset = 7;
				itemDef.maleWieldY = -7;
				itemDef.originalModelColors = new int[] { 11200, 11200, 0, 11200, 11200 };
				itemDef.modifiedModelColors = new int[] { 56428, 55404, 30338, 399, 16319 };
				break;
			case 21006:
				itemDef.setDefaults();
				itemDef.modelID = 70000;
				itemDef.name = "Lava whip";
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				itemDef.modelZoom = 840;
				itemDef.maleWearId = 70001;
				itemDef.femaleWearId = 70001;
				itemDef.modelRotation1 = 280;
				itemDef.modelRotation2 = 0;
				itemDef.modelOffsetY = 56;
				// itemDef.maleXOffset = 7;
				itemDef.maleWieldY = -7;
				itemDef.originalModelColors = new int[] { 461770, 461770, 0, 461770, 461770 };
				itemDef.modifiedModelColors = new int[] { 56428, 55404, 30338, 399, 16319 };
				break;
			case 21007:
				itemDef.setDefaults();
				itemDef.modelID = 70000;
				itemDef.name = "Purple whip";
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				itemDef.modelZoom = 840;
				itemDef.maleWearId = 70001;
				itemDef.femaleWearId = 70001;
				itemDef.modelRotation1 = 280;
				itemDef.modelRotation2 = 0;
				itemDef.modelOffsetY = 56;
				// itemDef.maleXOffset = 7;
				itemDef.maleWieldY = -7;
				itemDef.originalModelColors = new int[] { 49950, 49950, 0, 49950, 49950 };
				itemDef.modifiedModelColors = new int[] { 56428, 55404, 30338, 399, 16319 };
				break;
			case 21008:
				itemDef.setDefaults();
				itemDef.imitate(get(11732));
				itemDef.originalModelColors = new int[] { 17350 };
				itemDef.modifiedModelColors = new int[] { 926 };
				break;
			case 21009:
				itemDef.setDefaults();
				itemDef.imitate(get(11732));
				itemDef.originalModelColors = new int[] { 226770 };
				itemDef.modifiedModelColors = new int[] { 926 };
				break;
			case 21010:
				itemDef.setDefaults();
				itemDef.imitate(get(11732));
				itemDef.originalModelColors = new int[] { 123770 };
				itemDef.modifiedModelColors = new int[] { 926 };
				break;
			case 11732:
				itemDef.imitate(get(11732));
				itemDef.originalModelColors = new int[] { 933 };
				itemDef.modifiedModelColors = new int[] { 926 };
				break;
			case 21012:
				itemDef.setDefaults();
				itemDef.imitate(get(11732));
				itemDef.originalModelColors = new int[] { 100 };
				itemDef.modifiedModelColors = new int[] { 926 };
				break;
			case 21013:
				itemDef.setDefaults();
				itemDef.imitate(get(11732));
				itemDef.originalModelColors = new int[] { 76770 };
				itemDef.modifiedModelColors = new int[] { 926 };
				break;
			case 21014:
				itemDef.setDefaults();
				itemDef.imitate(get(11732));
				itemDef.originalModelColors = new int[] { 461770 };
				itemDef.modifiedModelColors = new int[] { 926 };
				break;
			case 21015:
				itemDef.setDefaults();
				itemDef.imitate(get(11732));
				itemDef.originalModelColors = new int[] { 49950 };
				itemDef.modifiedModelColors = new int[] { 926 };
				break;
			case 21042:
				itemDef.setDefaults();
				itemDef.imitate(get(11732));
				itemDef.originalModelColors = new int[] { 1 };
				itemDef.modifiedModelColors = new int[] { 926 };
				break;
			case 21016:
				itemDef.setDefaults();
				itemDef.imitate(get(15702));
				itemDef.originalModelColors = new int[] { 17350, 17350, 17350, 17350, 17350, 17350, 17350, 17350, 17350,
						17350, 17350, 17350, 17350, 17350 };
				itemDef.modifiedModelColors = new int[] { 36613, 3974, 36616, 36621, 36626, 36638, 36652, 6808, 8067, 8072,
						36620, 36627, 36639, 36652 };
				break;
			case 21017:
				itemDef.setDefaults();
				itemDef.imitate(get(15702));
				itemDef.originalModelColors = new int[] { 226770, 226770, 226770, 226770, 226770, 226770, 226770, 226770,
						226770, 226770, 226770, 226770, 226770, 226770 };
				itemDef.modifiedModelColors = new int[] { 36613, 3974, 36616, 36621, 36626, 36638, 36652, 6808, 8067, 8072,
						36620, 36627, 36639, 36652 };
				break;
			case 21018:
				itemDef.setDefaults();
				itemDef.imitate(get(15702));
				itemDef.originalModelColors = new int[] { 123770, 123770, 123770, 123770, 123770, 123770, 123770, 123770,
						123770, 123770, 123770, 123770, 123770, 123770 };
				itemDef.modifiedModelColors = new int[] { 36613, 3974, 36616, 36621, 36626, 36638, 36652, 6808, 8067, 8072,
						36620, 36627, 36639, 36652 };
				break;
			case 21019:
				itemDef.setDefaults();
				itemDef.imitate(get(15702));
				itemDef.originalModelColors = new int[] { 933, 933, 933, 933, 933, 933, 933, 933, 933, 933, 933, 933, 933,
						933 };
				itemDef.modifiedModelColors = new int[] { 36613, 3974, 36616, 36621, 36626, 36638, 36652, 6808, 8067, 8072,
						36620, 36627, 36639, 36652 };
				break;
			case 21020:
				itemDef.setDefaults();
				itemDef.imitate(get(15702));
				itemDef.originalModelColors = new int[] { 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100,
						100 };
				itemDef.modifiedModelColors = new int[] { 36613, 3974, 36616, 36621, 36626, 36638, 36652, 6808, 8067, 8072,
						36620, 36627, 36639, 36652 };
				break;
			case 21021:
				itemDef.setDefaults();
				itemDef.imitate(get(15702));
				itemDef.originalModelColors = new int[] { 76770, 76770, 76770, 76770, 76770, 76770, 76770, 76770, 76770,
						76770, 76770, 76770, 76770, 76770 };
				itemDef.modifiedModelColors = new int[] { 36613, 3974, 36616, 36621, 36626, 36638, 36652, 6808, 8067, 8072,
						36620, 36627, 36639, 36652 };
				break;
			case 21022:
				itemDef.setDefaults();
				itemDef.imitate(get(15702));
				itemDef.originalModelColors = new int[] { 461770, 461770, 461770, 461770, 461770, 461770, 461770, 461770,
						461770, 461770, 461770, 461770, 461770, 461770 };
				itemDef.modifiedModelColors = new int[] { 36613, 3974, 36616, 36621, 36626, 36638, 36652, 6808, 8067, 8072,
						36620, 36627, 36639, 36652 };
				break;
			case 21023:
				itemDef.setDefaults();
				itemDef.imitate(get(15702));
				itemDef.originalModelColors = new int[] { 49950, 49950, 49950, 49950, 49950, 49950, 49950, 49950, 49950,
						49950, 49950, 49950, 49950, 49950 };
				itemDef.modifiedModelColors = new int[] { 36613, 3974, 36616, 36621, 36626, 36638, 36652, 6808, 8067, 8072,
						36620, 36627, 36639, 36652 };
				break;
			case 21024:
				itemDef.setDefaults();
				itemDef.imitate(get(1038));
				itemDef.originalModelColors = new int[] { 226770 };
				itemDef.modifiedModelColors = new int[] { 926 };
				itemDef.name = "Aqua partyhat";
				break;
			case 21025:
				itemDef.setDefaults();
				itemDef.imitate(get(1038));
				itemDef.originalModelColors = new int[] { 123770 };
				itemDef.modifiedModelColors = new int[] { 926 };
				itemDef.name = "Pink partyhat";
				break;
			case 21026:
				itemDef.setDefaults();
				itemDef.imitate(get(1038));
				itemDef.originalModelColors = new int[] { 461770 };
				itemDef.modifiedModelColors = new int[] { 926 };
				itemDef.name = "Lava partyhat";
				break;
			case 21027:
				itemDef.setDefaults();
				itemDef.imitate(get(1053));
				itemDef.originalModelColors = new int[] { 17350 };
				itemDef.modifiedModelColors = new int[] { 926 };
				itemDef.name = "Lime halloween mask";
				break;
			case 21028:
				itemDef.setDefaults();
				itemDef.imitate(get(1053));
				itemDef.originalModelColors = new int[] { 226770 };
				itemDef.modifiedModelColors = new int[] { 926 };
				itemDef.name = "Aqua halloween mask";
				break;
			case 21029:
				itemDef.setDefaults();
				itemDef.imitate(get(1053));
				itemDef.originalModelColors = new int[] { 123770 };
				itemDef.modifiedModelColors = new int[] { 926 };
				itemDef.name = "Pink halloween mask";
				break;
			case 21030:
				itemDef.setDefaults();
				itemDef.imitate(get(1053));
				itemDef.originalModelColors = new int[] { 100 };
				itemDef.modifiedModelColors = new int[] { 926 };
				itemDef.name = "White halloween mask";
				break;
			case 21031:
				itemDef.setDefaults();
				itemDef.imitate(get(1053));
				itemDef.originalModelColors = new int[] { 11200 };
				itemDef.modifiedModelColors = new int[] { 926 };
				itemDef.name = "Yellow halloween mask";
				break;
			case 21032:
				itemDef.setDefaults();
				itemDef.imitate(get(1053));
				itemDef.originalModelColors = new int[] { 461770 };
				itemDef.modifiedModelColors = new int[] { 926 };
				itemDef.name = "Lava halloween mask";
				break;
			case 21033:
				itemDef.setDefaults();
				itemDef.imitate(get(1053));
				itemDef.originalModelColors = new int[] { 49950 };
				itemDef.modifiedModelColors = new int[] { 926 };
				itemDef.name = "Purple halloween mask";
				break;
			case 21034:
				itemDef.setDefaults();
				itemDef.imitate(get(1053));
				itemDef.originalModelColors = new int[] { 1 };
				itemDef.modifiedModelColors = new int[] { 926 };
				itemDef.name = "Black halloween mask";
				break;
			case 21035:
				itemDef.setDefaults();
				itemDef.imitate(get(1050));
				itemDef.originalModelColors = new int[] { 17350 };
				itemDef.modifiedModelColors = new int[] { 933 };
				itemDef.name = "Lime santa hat";
				break;
			case 21036:
				itemDef.setDefaults();
				itemDef.imitate(get(1050));
				itemDef.originalModelColors = new int[] { 226770 };
				itemDef.modifiedModelColors = new int[] { 933 };
				itemDef.name = "Aqua santa hat";
				break;
			case 21037:
				itemDef.setDefaults();
				itemDef.imitate(get(1050));
				itemDef.originalModelColors = new int[] { 123770 };
				itemDef.modifiedModelColors = new int[] { 933 };
				itemDef.name = "Pink santa hat";
				break;
			case 21038:
				itemDef.setDefaults();
				itemDef.imitate(get(1050));
				itemDef.originalModelColors = new int[] { 100 };
				itemDef.modifiedModelColors = new int[] { 933 };
				itemDef.name = "White santa hat";
				break;
			case 21039:
				itemDef.setDefaults();
				itemDef.imitate(get(1050));
				itemDef.originalModelColors = new int[] { 11200 };
				itemDef.modifiedModelColors = new int[] { 933 };
				itemDef.name = "Yellow santa hat";
				break;
			case 21040:
				itemDef.setDefaults();
				itemDef.imitate(get(1050));
				itemDef.originalModelColors = new int[] { 461770 };
				itemDef.modifiedModelColors = new int[] { 933 };
				itemDef.name = "Lava santa hat";
				break;
			case 21041:
				itemDef.setDefaults();
				itemDef.imitate(get(1050));
				itemDef.originalModelColors = new int[] { 49950 };
				itemDef.modifiedModelColors = new int[] { 933 };
				itemDef.name = "Purple santa hat";
				break;
			case 21048:
				itemDef.setDefaults();
				itemDef.imitate(get(1050));
				itemDef.originalModelColors = new int[] { 1 };
				itemDef.modifiedModelColors = new int[] { 933 };
				itemDef.name = "Black santa hat";
				break;
			case 21049:
				itemDef.setDefaults();
				itemDef.imitate(get(1038));
				itemDef.originalModelColors = new int[] { 1 };
				itemDef.modifiedModelColors = new int[] { 926 };
				itemDef.name = "Black partyhat";
				break;
			case 21047:
				itemDef.setDefaults();
				itemDef.name = "Abyssal tentacle";
				itemDef.description2 = "A weapon from the abyss, embedded in a slimy tentacle.";
				itemDef.modelID = 70006;
				itemDef.modelZoom = 940;
				itemDef.modelRotation2 = 121;
				itemDef.modelRotation1 = 280;
				itemDef.maleWieldY = 0;
				itemDef.femaleWieldY = 3;
				itemDef.modelOffset1 = 4;
				itemDef.modelOffsetY = -10;
				itemDef.maleWearId = 70007;
				itemDef.femaleWearId = 70007;
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wield";
				itemDef.actions[3] = "Check";
				itemDef.actions[4] = "Dissolve";
				break;
			case 11975:
				itemDef.name = "Chinchompa";
				itemDef.imitate(get(9976));
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
				break;
			case 11976:
				itemDef.name = "Seagull";
				itemDef.imitate(get(12458));
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
				break;
			case 1053:
				itemDef.name = "Green halloween mask";
				break;
			case 1055:
				itemDef.name = "Blue halloween mask";
				break;
			case 1057:
				itemDef.name = "Red halloween mask";
				break;
			case 14196:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.stackable = false;
				itemDef.name = "Prayer flask (4)";
				itemDef.description2 = "4 doses of prayer potion.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 28488 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61764;
				break;
			case 14194:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.stackable = false;
				itemDef.name = "Prayer flask (3)";
				itemDef.description2 = "3 doses of prayer potion.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 28488 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61727;
				break;
			case 14192:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.stackable = false;
				itemDef.name = "Prayer flask (2)";
				itemDef.description2 = "2 doses of prayer potion.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 28488 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61731;
				break;
			case 14190:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.stackable = false;
				itemDef.name = "Prayer flask (1)";
				itemDef.description2 = "1 dose of prayer potion.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 28488 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61812;
				break;
			case 14189:
				itemDef.setDefaults();
				itemDef.modelID = 2429;
				itemDef.name = "Super attack flask (6)";
				itemDef.actions = new String[] { null, null, null, null, null, null };
				itemDef.modelZoom = 760;
				itemDef.modelRotation2 = 28;
				itemDef.modelRotation1 = 552;
				itemDef.modelOffsetX = itemDef.modelOffset1 = 0;
				itemDef.stackable = true;
				itemDef.certID = 14188;
				itemDef.certTemplateID = 799;
				break;
			case 14188:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.stackable = false;
				itemDef.name = "Super attack flask (6)";
				itemDef.description2 = "6 doses of super attack potion.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 43848 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61732;
				break;
			case 14187:
				itemDef.setDefaults();
				itemDef.modelID = 2429;
				itemDef.name = "Super attack flask (5)";
				itemDef.actions = new String[] { null, null, null, null, null, null };
				itemDef.modelZoom = 760;
				itemDef.modelRotation2 = 28;
				itemDef.modelRotation1 = 552;
				itemDef.modelOffsetX = itemDef.modelOffset1 = 0;
				itemDef.stackable = true;
				itemDef.certID = 14186;
				itemDef.certTemplateID = 799;
				break;
			case 14186:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.stackable = false;
				itemDef.name = "Super attack flask (5)";
				itemDef.description2 = "5 doses of super attack potion.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 43848 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61729;
				break;
			case 14185:
				itemDef.setDefaults();
				itemDef.modelID = 2429;
				itemDef.name = "Super attack flask (4)";
				itemDef.actions = new String[] { null, null, null, null, null, null };
				itemDef.modelZoom = 760;
				itemDef.modelRotation2 = 28;
				itemDef.modelRotation1 = 552;
				itemDef.modelOffsetX = itemDef.modelOffset1 = 0;
				itemDef.stackable = true;
				itemDef.certID = 14184;
				itemDef.certTemplateID = 799;
				break;
			case 14184:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.stackable = false;
				itemDef.name = "Super attack flask (4)";
				itemDef.description2 = "4 doses of super attack potion.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 43848 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61764;
				break;
			case 14183:
				itemDef.setDefaults();
				itemDef.modelID = 2429;
				itemDef.name = "Super attack flask (3)";
				itemDef.actions = new String[] { null, null, null, null, null, null };
				itemDef.modelZoom = 760;
				itemDef.modelRotation2 = 28;
				itemDef.modelRotation1 = 552;
				itemDef.modelOffsetX = itemDef.modelOffset1 = 0;
				itemDef.stackable = true;
				itemDef.certID = 14182;
				itemDef.certTemplateID = 799;
				break;
			case 14182:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.stackable = false;
				itemDef.name = "Super attack flask (3)";
				itemDef.description2 = "3 doses of super attack potion.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 43848 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61727;
				break;
			case 14181:
				itemDef.setDefaults();
				itemDef.modelID = 2429;
				itemDef.name = "Super attack flask (2)";
				itemDef.actions = new String[] { null, null, null, null, null, null };
				itemDef.modelZoom = 760;
				itemDef.modelRotation2 = 28;
				itemDef.modelRotation1 = 552;
				itemDef.modelOffsetX = itemDef.modelOffset1 = 0;
				itemDef.stackable = true;
				itemDef.certID = 14180;
				itemDef.certTemplateID = 799;
				break;
			case 14180:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.stackable = false;
				itemDef.name = "Super attack flask (2)";
				itemDef.description2 = "2 doses of super attack potion.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 43848 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61731;
				break;
			case 14179:
				itemDef.setDefaults();
				itemDef.modelID = 2429;
				itemDef.name = "Super attack flask (1)";
				itemDef.actions = new String[] { null, null, null, null, null, null };
				itemDef.modelZoom = 760;
				itemDef.modelRotation2 = 28;
				itemDef.modelRotation1 = 552;
				itemDef.modelOffsetX = itemDef.modelOffset1 = 0;
				itemDef.stackable = true;
				itemDef.certID = 14178;
				itemDef.certTemplateID = 799;
				break;
			case 14178:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.stackable = false;
				itemDef.name = "Super attack flask (1)";
				itemDef.description2 = "1 dose of super attack potion.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 43848 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61812;
				break;
			case 14177:
				itemDef.setDefaults();
				itemDef.modelID = 2429;
				itemDef.name = "Super strength flask (6)";
				itemDef.actions = new String[] { null, null, null, null, null, null };
				itemDef.modelZoom = 760;
				itemDef.modelRotation2 = 28;
				itemDef.modelRotation1 = 552;
				itemDef.modelOffsetX = itemDef.modelOffset1 = 0;
				itemDef.stackable = true;
				itemDef.certID = 14176;
				itemDef.certTemplateID = 799;
				break;
			case 14176:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.stackable = false;
				itemDef.name = "Super strength flask (6)";
				itemDef.description2 = "6 doses of super strength potion.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 119 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61732;
				break;
			case 14175:
				itemDef.setDefaults();
				itemDef.modelID = 2429;
				itemDef.name = "Super strength flask (5)";
				itemDef.actions = new String[] { null, null, null, null, null, null };
				itemDef.modelZoom = 760;
				itemDef.modelRotation2 = 28;
				itemDef.modelRotation1 = 552;
				itemDef.modelOffsetX = itemDef.modelOffset1 = 0;
				itemDef.stackable = true;
				itemDef.certID = 14174;
				itemDef.certTemplateID = 799;
				break;
			case 14174:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.stackable = false;
				itemDef.name = "Super strength flask (5)";
				itemDef.description2 = "5 doses of super strength potion.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 119 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61729;
				break;
			case 14173:
				itemDef.setDefaults();
				itemDef.modelID = 2429;
				itemDef.name = "Super strength flask (4)";
				itemDef.actions = new String[] { null, null, null, null, null, null };
				itemDef.modelZoom = 760;
				itemDef.modelRotation2 = 28;
				itemDef.modelRotation1 = 552;
				itemDef.modelOffsetX = itemDef.modelOffset1 = 0;
				itemDef.stackable = true;
				itemDef.certID = 14172;
				itemDef.certTemplateID = 799;
				break;
			case 14172:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.stackable = false;
				itemDef.name = "Super strength flask (4)";
				itemDef.description2 = "4 doses of super strength potion.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 119 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61764;
				break;
			case 14171:
				itemDef.setDefaults();
				itemDef.modelID = 2429;
				itemDef.name = "Super strength flask (3)";
				itemDef.actions = new String[] { null, null, null, null, null, null };
				itemDef.modelZoom = 760;
				itemDef.modelRotation2 = 28;
				itemDef.modelRotation1 = 552;
				itemDef.modelOffsetX = itemDef.modelOffset1 = 0;
				itemDef.stackable = true;
				itemDef.certID = 14170;
				itemDef.certTemplateID = 799;
				break;
			case 14170:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.stackable = false;
				itemDef.name = "Super strength flask (3)";
				itemDef.description2 = "3 doses of super strength potion.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 119 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61727;
				break;
			case 14169:
				itemDef.setDefaults();
				itemDef.modelID = 2429;
				itemDef.name = "Super strength flask (2)";
				itemDef.actions = new String[] { null, null, null, null, null, null };
				itemDef.modelZoom = 760;
				itemDef.modelRotation2 = 28;
				itemDef.modelRotation1 = 552;
				itemDef.modelOffsetX = itemDef.modelOffset1 = 0;
				itemDef.stackable = true;
				itemDef.certID = 14168;
				itemDef.certTemplateID = 799;
				break;
			case 14168:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.stackable = false;
				itemDef.name = "Super strength flask (2)";
				itemDef.description2 = "2 doses of super strength potion.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 119 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61731;
				break;
			case 14167:
				itemDef.setDefaults();
				itemDef.modelID = 2429;
				itemDef.name = "Super strength flask (1)";
				itemDef.actions = new String[] { null, null, null, null, null, null };
				itemDef.modelZoom = 760;
				itemDef.modelRotation2 = 28;
				itemDef.modelRotation1 = 552;
				itemDef.modelOffsetX = itemDef.modelOffset1 = 0;
				itemDef.stackable = true;
				itemDef.certID = 14166;
				itemDef.certTemplateID = 799;
				break;
			case 14166:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.stackable = false;
				itemDef.name = "Super strength flask (1)";
				itemDef.description2 = "1 dose of super strength potion.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 119 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61812;
				break;
			case 14164:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.stackable = false;
				itemDef.name = "Super defence flask (6)";
				itemDef.description2 = "6 doses of super defence potion.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 8008 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61732;
				break;
			case 14162:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.stackable = false;
				itemDef.name = "Super defence flask (5)";
				itemDef.description2 = "5 doses of super defence potion.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 8008 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61729;
				break;
			case 14160:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.stackable = false;
				itemDef.name = "Super defence flask (4)";
				itemDef.description2 = "4 doses of super defence potion.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 8008 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61764;
				break;
			case 14158:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.stackable = false;
				itemDef.name = "Super defence flask (3)";
				itemDef.description2 = "3 doses of super defence potion.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 8008 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61727;
				break;
			case 14156:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.stackable = false;
				itemDef.name = "Super defence flask (2)";
				itemDef.description2 = "2 doses of super defence potion.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 8008 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61731;
				break;
			case 14154:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.stackable = false;
				itemDef.name = "Super defence flask (1)";
				itemDef.description2 = "1 dose of super defence potion.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 8008 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61812;
				break;
			case 14153:
				itemDef.setDefaults();
				itemDef.modelID = 2429;
				itemDef.name = "Ranging flask (6)";
				itemDef.actions = new String[] { null, null, null, null, null, null };
				itemDef.modelZoom = 760;
				itemDef.modelRotation2 = 28;
				itemDef.modelRotation1 = 552;
				itemDef.modelOffsetX = itemDef.modelOffset1 = 0;
				itemDef.stackable = true;
				itemDef.certID = 14152;
				itemDef.certTemplateID = 799;
				break;
			case 14152:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.stackable = false;
				itemDef.name = "Ranging flask (6)";
				itemDef.description2 = "6 doses of ranging potion.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 36680 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61732;
				break;
			case 14151:
				itemDef.setDefaults();
				itemDef.modelID = 2429;
				itemDef.name = "Ranging flask (5)";
				itemDef.actions = new String[] { null, null, null, null, null, null };
				itemDef.modelZoom = 760;
				itemDef.modelRotation2 = 28;
				itemDef.modelRotation1 = 552;
				itemDef.modelOffsetX = itemDef.modelOffset1 = 0;
				itemDef.stackable = true;
				itemDef.certID = 14150;
				itemDef.certTemplateID = 799;
				break;
			case 14150:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.stackable = false;
				itemDef.name = "Ranging flask (5)";
				itemDef.description2 = "5 doses of ranging potion.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 36680 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61729;
				break;
			case 14149:
				itemDef.setDefaults();
				itemDef.modelID = 2429;
				itemDef.name = "Ranging flask (4)";
				itemDef.actions = new String[] { null, null, null, null, null, null };
				itemDef.modelZoom = 760;
				itemDef.modelRotation2 = 28;
				itemDef.modelRotation1 = 552;
				itemDef.modelOffsetX = itemDef.modelOffset1 = 0;
				itemDef.stackable = true;
				itemDef.certID = 14148;
				itemDef.certTemplateID = 799;
				break;
			case 14148:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.stackable = false;
				itemDef.name = "Ranging flask (4)";
				itemDef.description2 = "4 doses of ranging potion.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 36680 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.groundActions[2] = "Take";
				itemDef.modelID = 61764;
				break;
			case 14147:
				itemDef.setDefaults();
				itemDef.modelID = 2429;
				itemDef.name = "Ranging flask (3)";
				itemDef.actions = new String[] { null, null, null, null, null, null };
				itemDef.modelZoom = 760;
				itemDef.modelRotation2 = 28;
				itemDef.modelRotation1 = 552;
				itemDef.modelOffsetX = itemDef.modelOffset1 = 0;
				itemDef.stackable = true;
				itemDef.certID = 14146;
				itemDef.certTemplateID = 799;
				break;
			case 14146:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.stackable = false;
				itemDef.name = "Ranging flask (3)";
				itemDef.description2 = "3 doses of ranging potion.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 36680 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61727;
				break;
			case 14145:
				itemDef.setDefaults();
				itemDef.modelID = 2429;
				itemDef.name = "Ranging flask (2)";
				itemDef.actions = new String[] { null, null, null, null, null, null };
				itemDef.modelZoom = 760;
				itemDef.modelRotation2 = 28;
				itemDef.modelRotation1 = 552;
				itemDef.modelOffsetX = itemDef.modelOffset1 = 0;
				itemDef.stackable = true;
				itemDef.certID = 14144;
				itemDef.certTemplateID = 799;
				break;
			case 14144:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.stackable = false;
				itemDef.name = "Ranging flask (2)";
				itemDef.description2 = "2 doses of ranging potion.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 36680 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61731;
				break;
			case 14143:
				itemDef.setDefaults();
				itemDef.modelID = 2429;
				itemDef.name = "Ranging flask (1)";
				itemDef.actions = new String[] { null, null, null, null, null, null };
				itemDef.modelZoom = 760;
				itemDef.modelRotation2 = 28;
				itemDef.modelRotation1 = 552;
				itemDef.modelOffsetX = itemDef.modelOffset1 = 0;
				itemDef.stackable = true;
				itemDef.certID = 14142;
				itemDef.certTemplateID = 799;
				break;
			case 14142:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.stackable = false;
				itemDef.name = "Ranging flask (1)";
				itemDef.description2 = "1 dose of ranging potion.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 36680 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61812;
				break;
			case 14140:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.stackable = false;
				itemDef.name = "Super antipoison flask (6)";
				itemDef.description2 = "6 doses of super antipoison.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 62404 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61732;
				break;
			case 14138:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.stackable = false;
				itemDef.name = "Super antipoison flask (5)";
				itemDef.description2 = "5 doses of super antipoison.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 62404 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61729;
				break;
			case 7629:
				itemDef.setDefaults();
				itemDef.imitate(get(761));
				itemDef.actions = new String[5];
				itemDef.actions[0] = "Redeem";
				itemDef.actions[4] = "Drop";
				itemDef.description = "This scroll can be redeemed for a $125 RuneLive payment. [137,500 credits]"
						.getBytes();
				itemDef.name = "$125 Payment Scroll";
				break;
			case 14136:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.stackable = false;
				itemDef.name = "Super antipoison flask (4)";
				itemDef.description2 = "4 doses of super antipoison.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 62404 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61764;
				break;
			case 14134:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.name = "Super antipoison flask (3)";
				itemDef.stackable = false;
				itemDef.description2 = "3 doses of super antipoison.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 62404 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61727;
				break;
			case 14132:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.name = "Super antipoison flask (2)";
				itemDef.stackable = false;
				itemDef.description2 = "2 doses of super antipoison.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 62404 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61731;
				break;
			case 14130:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.name = "Super antipoison flask (1)";
				itemDef.stackable = false;
				itemDef.description2 = "1 dose of super antipoison.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffsetX = 1;
				itemDef.modelOffset1 = -1;
				itemDef.originalModelColors = new int[] { 62404 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61812;
				break;
			case 14428:
				itemDef.setDefaults();
				itemDef.modelID = 2429;
				itemDef.name = "Saradomin brew flask (6)";
				itemDef.actions = new String[] { null, null, null, null, null, null };
				itemDef.modelZoom = 760;
				itemDef.modelRotation2 = 28;
				itemDef.modelRotation1 = 552;
				itemDef.modelOffsetX = itemDef.modelOffset1 = 0;
				itemDef.stackable = true;
				itemDef.certID = 14427;
				itemDef.certTemplateID = 799;
				break;
			case 14427:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.name = "Saradomin brew flask (6)";
				itemDef.stackable = false;
				itemDef.description2 = "6 doses of saradomin brew.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffsetX = 1;
				itemDef.modelOffset1 = -1;
				itemDef.originalModelColors = new int[] { 10939 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61732;
				itemDef.anInt184 = 200;
				itemDef.anInt196 = 40;
				break;
			case 14426:
				itemDef.setDefaults();
				itemDef.modelID = 2429;
				itemDef.name = "Saradomin brew flask (5)";
				itemDef.actions = new String[] { null, null, null, null, null, null };
				itemDef.modelZoom = 760;
				itemDef.modelRotation2 = 28;
				itemDef.modelRotation1 = 552;
				itemDef.modelOffsetX = itemDef.modelOffset1 = 0;
				itemDef.stackable = true;
				itemDef.certID = 14425;
				itemDef.certTemplateID = 799;
				break;
			case 14425:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.name = "Saradomin brew flask (5)";
				itemDef.stackable = false;
				itemDef.description2 = "5 doses of saradomin brew.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffsetX = 1;
				itemDef.modelOffset1 = -1;
				itemDef.originalModelColors = new int[] { 10939 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61729;
				itemDef.anInt184 = 200;
				itemDef.anInt196 = 40;
				break;
			case 14424:
				itemDef.setDefaults();
				itemDef.modelID = 2429;
				itemDef.name = "Saradomin brew flask (4)";
				itemDef.actions = new String[] { null, null, null, null, null, null };
				itemDef.modelZoom = 760;
				itemDef.modelRotation2 = 28;
				itemDef.modelRotation1 = 552;
				itemDef.modelOffsetX = itemDef.modelOffset1 = 0;
				itemDef.stackable = true;
				itemDef.certID = 14423;
				itemDef.certTemplateID = 799;
				break;
			case 14423:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.name = "Saradomin brew flask (4)";
				itemDef.stackable = false;
				itemDef.description2 = "4 doses of saradomin brew.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffsetX = 1;
				itemDef.modelOffset1 = -1;
				itemDef.originalModelColors = new int[] { 10939 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61764;
				itemDef.anInt184 = 200;
				itemDef.anInt196 = 40;
				break;
			case 14422:
				itemDef.setDefaults();
				itemDef.modelID = 2429;
				itemDef.name = "Saradomin brew flask (3)";
				itemDef.actions = new String[] { null, null, null, null, null, null };
				itemDef.modelZoom = 760;
				itemDef.modelRotation2 = 28;
				itemDef.modelRotation1 = 552;
				itemDef.modelOffsetX = itemDef.modelOffset1 = 0;
				itemDef.stackable = true;
				itemDef.certID = 14421;
				itemDef.certTemplateID = 799;
				break;
			case 14421:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.name = "Saradomin brew flask (3)";
				itemDef.stackable = false;
				itemDef.description2 = "3 doses of saradomin brew.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffsetX = 1;
				itemDef.modelOffset1 = -1;
				itemDef.originalModelColors = new int[] { 10939 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61727;
				itemDef.anInt184 = 200;
				itemDef.anInt196 = 40;
				break;
			case 14420:
				itemDef.setDefaults();
				itemDef.modelID = 2429;
				itemDef.name = "Saradomin brew flask (2)";
				itemDef.actions = new String[] { null, null, null, null, null, null };
				itemDef.modelZoom = 760;
				itemDef.modelRotation2 = 28;
				itemDef.modelRotation1 = 552;
				itemDef.modelOffsetX = itemDef.modelOffset1 = 0;
				itemDef.stackable = true;
				itemDef.certID = 14419;
				itemDef.certTemplateID = 799;
				break;
			case 14419:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.name = "Saradomin brew flask (2)";
				itemDef.stackable = false;
				itemDef.description2 = "2 doses of saradomin brew.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffsetX = 1;
				itemDef.modelOffset1 = -1;
				itemDef.originalModelColors = new int[] { 10939 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61731;
				itemDef.anInt184 = 200;
				itemDef.anInt196 = 40;
				break;
			case 14418:
				itemDef.setDefaults();
				itemDef.modelID = 2429;
				itemDef.name = "Saradomin brew flask (1)";
				itemDef.actions = new String[] { null, null, null, null, null, null };
				itemDef.modelZoom = 760;
				itemDef.modelRotation2 = 28;
				itemDef.modelRotation1 = 552;
				itemDef.modelOffsetX = itemDef.modelOffset1 = 0;
				itemDef.stackable = true;
				itemDef.certID = 14417;
				itemDef.certTemplateID = 799;
				break;
			case 14417:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.name = "Saradomin brew flask (1)";
				itemDef.stackable = false;
				itemDef.description2 = "1 dose of saradomin brew.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffsetX = 1;
				itemDef.modelOffset1 = -1;
				itemDef.originalModelColors = new int[] { 10939 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61812;
				itemDef.anInt184 = 200;
				itemDef.anInt196 = 40;
				break;
			case 14416:
				itemDef.setDefaults();
				itemDef.modelID = 2429;
				itemDef.name = "Super restore flask (6)";
				itemDef.actions = new String[] { null, null, null, null, null, null };
				itemDef.modelZoom = 760;
				itemDef.modelRotation2 = 28;
				itemDef.modelRotation1 = 552;
				itemDef.modelOffsetX = itemDef.modelOffset1 = 0;
				itemDef.stackable = true;
				itemDef.certID = 14415;
				itemDef.certTemplateID = 799;
				break;
			case 14415:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.name = "Super restore flask (6)";
				itemDef.stackable = false;
				itemDef.description2 = "6 doses of super restore potion.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 62135 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61732;
				break;
			case 14414:
				itemDef.setDefaults();
				itemDef.modelID = 2429;
				itemDef.name = "Super restore flask (5)";
				itemDef.actions = new String[] { null, null, null, null, null, null };
				itemDef.modelZoom = 760;
				itemDef.modelRotation2 = 28;
				itemDef.modelRotation1 = 552;
				itemDef.modelOffsetX = itemDef.modelOffset1 = 0;
				itemDef.stackable = true;
				itemDef.certID = 14413;
				itemDef.certTemplateID = 799;
				break;
			case 14413:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.name = "Super restore flask (5)";
				itemDef.stackable = false;
				itemDef.description2 = "5 doses of super restore potion.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 62135 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61729;
				break;
			case 14412:
				itemDef.setDefaults();
				itemDef.modelID = 2429;
				itemDef.name = "Super restore flask (4)";
				itemDef.actions = new String[] { null, null, null, null, null, null };
				itemDef.modelZoom = 760;
				itemDef.modelRotation2 = 28;
				itemDef.modelRotation1 = 552;
				itemDef.modelOffsetX = itemDef.modelOffset1 = 0;
				itemDef.stackable = true;
				itemDef.certID = 14411;
				itemDef.certTemplateID = 799;
				break;
			case 14411:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.name = "Super restore flask (4)";
				itemDef.stackable = false;
				itemDef.description2 = "4 doses of super restore potion.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 62135 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61764;
				break;
			case 14410:
				itemDef.setDefaults();
				itemDef.modelID = 2429;
				itemDef.name = "Super restore flask (3)";
				itemDef.actions = new String[] { null, null, null, null, null, null };
				itemDef.modelZoom = 760;
				itemDef.modelRotation2 = 28;
				itemDef.modelRotation1 = 552;
				itemDef.modelOffsetX = itemDef.modelOffset1 = 0;
				itemDef.stackable = true;
				itemDef.certID = 14409;
				itemDef.certTemplateID = 799;
				break;
			case 14409:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.name = "Super restore flask (3)";
				itemDef.stackable = false;
				itemDef.description2 = "3 doses of super restore potion.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 62135 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61727;
				break;
			case 14408:
				itemDef.setDefaults();
				itemDef.modelID = 2429;
				itemDef.name = "Super restore flask (2)";
				itemDef.actions = new String[] { null, null, null, null, null, null };
				itemDef.modelZoom = 760;
				itemDef.modelRotation2 = 28;
				itemDef.modelRotation1 = 552;
				itemDef.modelOffsetX = itemDef.modelOffset1 = 0;
				itemDef.stackable = true;
				itemDef.certID = 14407;
				itemDef.certTemplateID = 799;
				break;
			case 14407:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.name = "Super restore flask (2)";
				itemDef.stackable = false;
				itemDef.description2 = "2 doses of super restore potion.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 62135 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61731;
				break;
			case 14406:
				itemDef.setDefaults();
				itemDef.modelID = 2429;
				itemDef.name = "Super restore flask (1)";
				itemDef.actions = new String[] { null, null, null, null, null, null };
				itemDef.modelZoom = 760;
				itemDef.modelRotation2 = 28;
				itemDef.modelRotation1 = 552;
				itemDef.modelOffsetX = itemDef.modelOffset1 = 0;
				itemDef.stackable = true;
				itemDef.certID = 14405;
				itemDef.certTemplateID = 799;
				break;
			case 14405:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.name = "Super restore flask (1)";
				itemDef.stackable = false;
				itemDef.description2 = "1 dose of super restore potion.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 62135 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61812;
				break;
			case 14404:
				itemDef.setDefaults();
				itemDef.modelID = 2429;
				itemDef.name = "Magic flask (6)";
				itemDef.actions = new String[] { null, null, null, null, null, null };
				itemDef.modelZoom = 760;
				itemDef.modelRotation2 = 28;
				itemDef.modelRotation1 = 552;
				itemDef.modelOffsetX = itemDef.modelOffset1 = 0;
				itemDef.stackable = true;
				itemDef.certID = 14403;
				itemDef.certTemplateID = 799;
				break;
			case 14403:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.stackable = false;
				itemDef.name = "Magic flask (6)";
				itemDef.description = "6 doses of magic potion.".getBytes();
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 2524 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61732;
				break;
			case 14402:
				itemDef.setDefaults();
				itemDef.modelID = 2429;
				itemDef.name = "Magic flask (5)";
				itemDef.actions = new String[] { null, null, null, null, null, null };
				itemDef.modelZoom = 760;
				itemDef.modelRotation2 = 28;
				itemDef.modelRotation1 = 552;
				itemDef.modelOffsetX = itemDef.modelOffset1 = 0;
				itemDef.stackable = true;
				itemDef.certID = 14401;
				itemDef.certTemplateID = 799;
				break;
			case 14401:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.name = "Magic flask (5)";
				itemDef.stackable = false;
				itemDef.description2 = "5 doses of magic potion.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 2524 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61729;
				break;
			case 14400:
				itemDef.setDefaults();
				itemDef.modelID = 2429;
				itemDef.name = "Magic flask (4)";
				itemDef.actions = new String[] { null, null, null, null, null, null };
				itemDef.modelZoom = 760;
				itemDef.modelRotation2 = 28;
				itemDef.modelRotation1 = 552;
				itemDef.modelOffsetX = itemDef.modelOffset1 = 0;
				itemDef.stackable = true;
				itemDef.certID = 14399;
				itemDef.certTemplateID = 799;
				break;
			case 14399:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.name = "Magic flask (4)";
				itemDef.stackable = false;
				itemDef.description2 = "4 doses of magic potion.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 2524 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61764;
				break;
			case 14398:
				itemDef.setDefaults();
				itemDef.modelID = 2429;
				itemDef.name = "Magic flask (3)";
				itemDef.actions = new String[] { null, null, null, null, null, null };
				itemDef.modelZoom = 760;
				itemDef.modelRotation2 = 28;
				itemDef.modelRotation1 = 552;
				itemDef.modelOffsetX = itemDef.modelOffset1 = 0;
				itemDef.stackable = true;
				itemDef.certID = 14397;
				itemDef.certTemplateID = 799;
				break;
			case 14397:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.name = "Magic flask (3)";
				itemDef.stackable = false;
				itemDef.description2 = "3 doses of magic potion.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 2524 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61727;
				break;
			case 14396:
				itemDef.setDefaults();
				itemDef.modelID = 2429;
				itemDef.name = "Magic flask (2)";
				itemDef.actions = new String[] { null, null, null, null, null, null };
				itemDef.modelZoom = 760;
				itemDef.modelRotation2 = 28;
				itemDef.modelRotation1 = 552;
				itemDef.modelOffsetX = itemDef.modelOffset1 = 0;
				itemDef.stackable = true;
				itemDef.certID = 14395;
				itemDef.certTemplateID = 799;
				break;
			case 14395:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.stackable = false;
				itemDef.name = "Magic flask (2)";
				itemDef.description2 = "2 doses of magic potion.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 2524 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61731;
				break;
			case 14394:
				itemDef.setDefaults();
				itemDef.modelID = 2429;
				itemDef.name = "Magic flask (1)";
				itemDef.actions = new String[] { null, null, null, null, null, null };
				itemDef.modelZoom = 760;
				itemDef.modelRotation2 = 28;
				itemDef.modelRotation1 = 552;
				itemDef.modelOffsetX = itemDef.modelOffset1 = 0;
				itemDef.stackable = true;
				itemDef.certID = 14393;
				itemDef.certTemplateID = 799;
				break;
			case 14393:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.name = "Magic flask (1)";
				itemDef.stackable = false;
				itemDef.description2 = "1 dose of magic potion.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 2524 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61812;
				break;
			case 14385:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.name = "Recover special flask (6)";
				itemDef.stackable = false;
				itemDef.description2 = "6 doses of recover special.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 38222 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61732;
				break;
			case 14383:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.name = "Recover special flask (5)";
				itemDef.stackable = false;
				itemDef.description2 = "5 doses of recover special.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 38222 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61729;
				break;
			case 14381:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.stackable = false;
				itemDef.name = "Recover special flask (4)";
				itemDef.description2 = "4 doses of recover special.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 38222 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61764;
				break;
			case 14379:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.stackable = false;
				itemDef.name = "Recover special flask (3)";
				itemDef.description2 = "3 doses of recover special.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 38222 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61727;
				break;
			case 14377:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.stackable = false;
				itemDef.name = "Recover special flask (2)";
				itemDef.description2 = "2 doses of recover special.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 38222 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61731;
				break;
			case 14375:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.stackable = false;
				itemDef.name = "Recover special flask (1)";
				itemDef.description2 = "1 dose of recover special.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 38222 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61812;
				break;
			case 14373:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.stackable = false;
				itemDef.name = "Extreme attack flask (6)";
				itemDef.description2 = "6 doses of extreme attack potion.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 33112 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61732;
				break;
			case 14371:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.stackable = false;
				itemDef.name = "Extreme attack flask (5)";
				itemDef.description2 = "5 doses of extreme attack potion.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 33112 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61729;
				break;
			case 14369:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.stackable = false;
				itemDef.name = "Extreme attack flask (4)";
				itemDef.description2 = "4 doses of extreme attack potion.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 33112 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61764;
				break;
			case 14367:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.stackable = false;
				itemDef.name = "Extreme attack flask (3)";
				itemDef.description2 = "3 doses of extreme attack potion.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 33112 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61727;
				break;
			case 14365:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.stackable = false;
				itemDef.name = "Extreme attack flask (2)";
				itemDef.description2 = "2 doses of extreme attack potion.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 33112 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61731;
				break;
			case 14363:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.stackable = false;
				itemDef.name = "Extreme attack flask (1)";
				itemDef.description2 = "1 dose of extreme attack potion.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 33112 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61812;
				break;
			case 14361:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.stackable = false;
				itemDef.name = "Extreme strength flask (6)";
				itemDef.description2 = "6 doses of extreme strength potion.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 127 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61732;
				break;
			case 14359:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.stackable = false;
				itemDef.name = "Extreme strength flask (5)";
				itemDef.description2 = "5 doses of extreme strength potion.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 127 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61729;
				break;
			case 14357:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.stackable = false;
				itemDef.name = "Extreme strength flask (4)";
				itemDef.description2 = "4 doses of extreme strength potion.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 127 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61764;
				break;
			case 14355:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.stackable = false;
				itemDef.name = "Extreme strength flask (3)";
				itemDef.description2 = "3 doses of extreme strength potion.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 127 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61727;
				break;
			case 14353:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.stackable = false;
				itemDef.name = "Extreme strength flask (2)";
				itemDef.description2 = "2 doses of extreme strength potion.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 127 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61731;
				break;
			case 14351:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.stackable = false;
				itemDef.name = "Extreme strength flask (1)";
				itemDef.description2 = "1 dose of extreme strength potion.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 127 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61812;
				break;
			case 14349:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.stackable = false;
				itemDef.name = "Extreme defence flask (6)";
				itemDef.description2 = "6 doses of extreme defence potion.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 10198 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61732;
				break;
			case 14347:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.stackable = false;
				itemDef.name = "Extreme defence flask (5)";
				itemDef.description2 = "5 doses of extreme defence potion.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 10198 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61729;
				break;
			case 14345:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.stackable = false;
				itemDef.name = "Extreme defence flask (4)";
				itemDef.description2 = "4 doses of extreme defence potion.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 10198 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61764;
				break;
			case 14343:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.name = "Extreme defence flask (3)";
				itemDef.stackable = false;
				itemDef.description2 = "3 doses of extreme defence potion.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 10198 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61727;
				break;
			case 14341:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.stackable = false;
				itemDef.name = "Extreme defence flask (2)";
				itemDef.description2 = "2 doses of extreme defence potion.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 10198 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61731;
				break;
			case 14339:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.stackable = false;
				itemDef.name = "Extreme defence flask (1)";
				itemDef.description2 = "1 dose of extreme defence potion.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 10198 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61812;
				break;
			case 14337:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.stackable = false;
				itemDef.name = "Extreme magic flask (6)";
				itemDef.description2 = "6 doses of extreme magic potion.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 33490 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61732;
				break;
			case 14335:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.stackable = false;
				itemDef.name = "Extreme magic flask (5)";
				itemDef.description2 = "5 doses of extreme magic potion.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 33490 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61729;
				break;
			case 14333:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.stackable = false;
				itemDef.name = "Extreme magic flask (4)";
				itemDef.description2 = "4 doses of extreme magic potion.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 33490 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61764;
				break;
			case 14331:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.stackable = false;
				itemDef.name = "Extreme magic flask (3)";
				itemDef.stackable = false;
				itemDef.description2 = "3 doses of extreme magic potion.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 33490 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61727;
				break;
			case 14329:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.stackable = false;
				itemDef.name = "Extreme magic flask (2)";
				itemDef.stackable = false;
				itemDef.description2 = "2 doses of extreme magic potion.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 33490 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61731;
				break;
			case 14327:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.name = "Extreme magic flask (1)";
				itemDef.stackable = false;
				itemDef.description2 = "1 dose of extreme magic potion.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 33490 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61812;
				break;
			case 14325:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.name = "Extreme ranging flask (6)";
				itemDef.stackable = false;
				itemDef.description2 = "6 doses of extreme ranging potion.";
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 13111 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61732;
				break;
			case 14323:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.name = "Extreme ranging flask (5)";
				itemDef.description2 = "5 doses of extreme ranging potion.";
				itemDef.stackable = false;
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 13111 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61729;
				break;
			case 14321:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.name = "Extreme ranging flask (4)";
				itemDef.description2 = "4 doses of extreme ranging potion.";
				itemDef.stackable = false;
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 13111 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61764;
				break;
			case 14319:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.name = "Extreme ranging flask (3)";
				itemDef.description2 = " 3 doses of extreme ranging potion.";
				itemDef.stackable = false;
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 13111 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61727;
				break;
			case 14317:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.name = "Extreme ranging flask (2)";
				itemDef.description2 = "2 doses of extreme ranging potion.";
				itemDef.stackable = false;
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 13111 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61731;
				break;
			case 14315:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.name = "Extreme ranging flask (1)";
				itemDef.description2 = "1 dose of extreme ranging potion.";
				itemDef.stackable = false;
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 13111 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61812;
				break;
			case 14313:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.name = "Super prayer flask (6)";
				itemDef.description2 = "6 doses of super prayer potion.";
				itemDef.stackable = false;
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 3016 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61732;
				break;
			case 14311:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.name = "Super prayer flask (5)";
				itemDef.description2 = "5 doses of super prayer potion.";
				itemDef.stackable = false;
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 3016 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61729;
				break;
			case 14309:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.name = "Super prayer flask (4)";
				itemDef.description2 = "4 doses of super prayer potion.";
				itemDef.stackable = false;
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 3016 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61764;
				break;
			case 14307:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.name = "Super prayer flask (3)";
				itemDef.description2 = "3 doses of super prayer potion.";
				itemDef.stackable = false;
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 3016 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61727;
				break;
			case 14305:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.name = "Super prayer flask (2)";
				itemDef.description2 = "2 doses of super prayer potion.";
				itemDef.stackable = false;
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 3016 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61731;
				break;
			case 14303:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.name = "Super prayer flask (1)";
				itemDef.description2 = "1 dose of super prayer potion.";
				itemDef.stackable = false;
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 3016 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61812;
				break;
			case 14301:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.name = "Overload flask (6)";
				itemDef.description2 = "6 doses of overload potion.";
				itemDef.stackable = false;
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 0 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61732;
				break;
			case 14299:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.name = "Overload flask (5)";
				itemDef.description2 = "5 doses of overload potion.";
				itemDef.stackable = false;
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 0 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61729;
				break;
			case 14297:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.name = "Overload flask (4)";
				itemDef.description2 = "4 doses of overload potion.";
				itemDef.stackable = false;
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 0 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61764;
				break;
			case 14295:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.name = "Overload flask (3)";
				itemDef.description2 = "3 doses of overload potion.";
				itemDef.stackable = false;
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 0 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61727;
				break;
			case 14293:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.name = "Overload flask (2)";
				itemDef.description2 = "2 doses of overload potion.";
				itemDef.stackable = false;
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 0 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61731;
				break;
			case 14291:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.name = "Overload flask (1)";
				itemDef.description2 = "1 dose of overload potion.";
				itemDef.stackable = false;
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 0 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.groundActions[2] = "Take";

				itemDef.modelID = 61812;
				break;
			case 14289:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.name = "Prayer renewal flask (6)";
				itemDef.description2 = "6 doses of prayer renewal.";
				itemDef.stackable = false;
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 926 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61732;
				break;
			case 14287:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.name = "Prayer renewal flask (5)";
				itemDef.description2 = "5 doses of prayer renewal.";
				itemDef.stackable = false;
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 926 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61729;
				break;
			case 15123:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.name = "Prayer renewal flask (4)";
				itemDef.description2 = "4 doses of prayer renewal potion.";
				itemDef.stackable = false;
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 926 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61764;
				break;
			case 15121:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.name = "Prayer renewal flask (3)";
				itemDef.description2 = "3 doses of prayer renewal potion.";
				itemDef.stackable = false;
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 926 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61727;
				break;
			case 15119:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.name = "Prayer renewal flask (2)";
				itemDef.description2 = "2 doses of prayer renewal potion.";
				itemDef.stackable = false;
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 926 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61731;
				break;
			case 7340:
				itemDef.setDefaults();
				itemDef.groundActions = new String[5];
				itemDef.name = "Prayer renewal flask (1)";
				itemDef.description2 = "1 dose of prayer renewal potion";
				itemDef.stackable = false;
				itemDef.modelZoom = 804;
				itemDef.modelRotation1 = 131;
				itemDef.modelRotation2 = 198;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -1;
				itemDef.originalModelColors = new int[] { 926 };
				itemDef.modifiedModelColors = new int[] { 33715 };
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
				itemDef.modelID = 61812;
				break;
			case 15262:
				itemDef.actions = new String[5];
				itemDef.actions[0] = "Open";
				itemDef.actions[2] = "Open-All";
				break;
			case 6570:
				itemDef.actions[2] = "Upgrade";
				break;
			case 4155:
				itemDef.name = "Slayer gem";
				itemDef.actions = new String[] { "Activate", null, "Social-Slayer", null, "Destroy" };
				break;
			case 13663:
				itemDef.name = "Stat reset cert.";
				itemDef.actions = new String[5];
				itemDef.actions[4] = "Drop";
				itemDef.actions[0] = "Open";
				break;
			case 13653:
				itemDef.name = "Energy fragment";
				break;
			case 292:
				itemDef.name = "Ingredients book";
				break;
			case 15707:
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				itemDef.actions[0] = "Create Party";
				break;
			case 6040:
				itemDef.name = "Healthorg's Amulet";
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				itemDef.actions[0] = "Teleport";
				itemDef.actions[4] = "Drop";
				break;
			case 14501:
				itemDef.modelID = 44574;
				itemDef.maleWearId = 43693;
				itemDef.femaleWearId = 43693;
				break;
			case 19111:
				itemDef.name = "TokHaar-Kal";
				itemDef.maleWearId = 62575;
				itemDef.femaleWearId = 62582;
				itemDef.groundActions = new String[5];
				itemDef.groundActions[2] = "Take";
				itemDef.modelOffset1 = -4;
				itemDef.modelID = 62592;
				itemDef.stackable = false;
				itemDef.description2 = "A cape made of ancient, enchanted rocks.";
				itemDef.modelZoom = 1616;
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				itemDef.actions[4] = "Drop";
				itemDef.modelOffsetX = 0;
				itemDef.modelRotation1 = 339;
				itemDef.modelRotation2 = 192;
				break;
			case 13262:
				itemDef.modelID = 62368;
				itemDef.name = "Dragon defender";
				itemDef.modelZoom = 592;
				itemDef.modelRotation1 = 323;
				itemDef.modelRotation2 = 1845;
				itemDef.modelOffset1 = -16;
				itemDef.modelOffsetY = -3;
				itemDef.maleWearId = 62367;
				itemDef.femaleWearId = 62367;
				itemDef.groundActions = new String[5];
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wield";
				itemDef.actions[4] = "Drop";
				break;
			case 20072:
				itemDef.modelID = 62368;
				itemDef.name = "Dragon defender";
				itemDef.modelZoom = 592;
				itemDef.modelRotation1 = 323;
				itemDef.modelRotation2 = 1845;
				itemDef.modelOffset1 = -16;
				itemDef.modelOffsetY = -3;
				itemDef.maleWearId = 62367;
				itemDef.femaleWearId = 62367;
				itemDef.groundActions = new String[5];
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wield";
				itemDef.actions[4] = "Drop";
				break;
			case 10934:
				itemDef.imitate(get(607));
				itemDef.name = "$25 Payment Scroll";
				itemDef.description = "This scroll can be redeemed for a $25 RuneLive payment. [27,875 credits]".getBytes();
				itemDef.actions = new String[5];
				itemDef.actions[0] = "Redeem";
				break;
			case 10935:
				itemDef.imitate(get(608));
				itemDef.name = "$50 Payment Scroll";
				itemDef.description = "This scroll can be redeemed for a $50 RuneLive payment. [55,750 credits]".getBytes();
				itemDef.actions = new String[5];
				itemDef.actions[0] = "Redeem";
				break;
			case 10943:
				itemDef.imitate(get(607));
				itemDef.name = "$10 Payment Scroll";
				itemDef.description = "This scroll can be redeemed for a $10 RuneLive payment. [10,000 credits]".getBytes();
				itemDef.actions = new String[5];
				itemDef.actions[0] = "Redeem";
				break;
			case 995:
				itemDef.name = "Coins";
				itemDef.actions = new String[5];
				itemDef.actions[4] = "Drop";
				itemDef.actions[3] = "Add-to-pouch";
				break;
			case 17291:
				itemDef.name = "Blood necklace";
				itemDef.actions = new String[] { null, "Wear", null, null, null, null };
				break;
			case 20084:
				itemDef.name = "Golden maul";
				break;

			case 6199:
				itemDef.name = "Mystery box";
				itemDef.actions = new String[5];
				itemDef.actions[0] = "Open";
				break;
			case 3849:
				itemDef.name = "Casket of horror";
				itemDef.actions = new String[5];
				itemDef.actions[0] = "Open";
				break;
			case 15501:
				itemDef.name = "Legendary mystery box";
				itemDef.actions = new String[5];
				itemDef.actions[0] = "Open";
				break;
			case 6568: // To replace Transparent black with opaque black.
				itemDef.modifiedModelColors = new int[1];
				itemDef.originalModelColors = new int[1];
				itemDef.modifiedModelColors[0] = 0;
				itemDef.originalModelColors[0] = 2059;
				break;
			case 996:
			case 997:
			case 998:
			case 999:
			case 1000:
			case 1001:
			case 1002:
			case 1003:
			case 1004:
				itemDef.name = "Coins";
				break;

			case 14017:
				itemDef.name = "Brackish blade";
				itemDef.modelZoom = 1488;
				itemDef.modelRotation1 = 276;
				itemDef.modelRotation2 = 1580;
				itemDef.modelOffsetY = 1;
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
				itemDef.modelID = 64593;
				itemDef.maleWearId = 64704;
				itemDef.femaleWearId = 64704;
				break;

			case 15220:
				itemDef.name = "Berserker ring (i)";
				itemDef.modelZoom = 600;
				itemDef.modelRotation1 = 324;
				itemDef.modelRotation2 = 1916;
				itemDef.modelOffset1 = 3;
				itemDef.modelOffsetY = -15;
				itemDef.groundActions = new String[5];
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				itemDef.actions[4] = "Drop";
				itemDef.modelID = 7735; // if it doesn't work try 7735
				itemDef.maleWearId = -1;
				// itemDefinition.maleArm = -1;
				itemDef.femaleWearId = -1;
				// itemDefinition.femaleArm = -1;
				break;
			case 14019:
				itemDef.modelID = 65262;
				itemDef.name = "Max cape";
				itemDef.description2 = "A cape worn by those who've achieved 99 in all skills.";
				itemDef.modelZoom = 1385;
				itemDef.modelOffset1 = 0;
				itemDef.modelOffsetY = 24;
				itemDef.modelRotation1 = 279;
				itemDef.modelRotation2 = 948;
				itemDef.maleWearId = 65300;
				itemDef.femaleWearId = 65322;
				itemDef.groundActions = new String[5];
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				break;
			case 14020:
				itemDef.name = "Veteran hood";
				itemDef.description2 = "A hood worn by Chivalry's veterans.";
				itemDef.modelZoom = 760;
				itemDef.modelRotation1 = 11;
				itemDef.modelRotation2 = 81;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -3;
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
				itemDef.modelID = 65271;
				itemDef.maleWearId = 65289;
				itemDef.femaleWearId = 65314;
				break;
			case 14021:
				itemDef.modelID = 65261;
				itemDef.name = "Veteran cape";
				itemDef.description2 = "A cape worn by Chivalry's veterans.";
				itemDef.modelZoom = 760;
				itemDef.modelOffset1 = 0;
				itemDef.modelOffsetY = 24;
				itemDef.modelRotation1 = 279;
				itemDef.modelRotation2 = 948;
				itemDef.maleWearId = 65305;
				itemDef.femaleWearId = 65318;
				itemDef.groundActions = new String[5];
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				break;
			case 14022:
				itemDef.modelID = 65270;
				itemDef.name = "Completionist cape";
				itemDef.description2 = "We'd pat you on the back, but this cape would get in the way.";
				itemDef.modelZoom = 1316;
				itemDef.modelOffset1 = -1;
				itemDef.modelOffsetY = 24;
				itemDef.modelRotation1 = 252;
				itemDef.modelRotation2 = 1020;
				itemDef.maleWearId = 65297;
				itemDef.femaleWearId = 65297;
				itemDef.groundActions = new String[5];
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				itemDef.actions[3] = "Customise";
				break;
			case 9666:
			case 11814:
			case 11816:
			case 11818:
			case 11820:
			case 11822:
			case 11824:
			case 11826:
			case 11828:
			case 11830:
			case 11832:
			case 11834:
			case 11836:
			case 11838:
			case 11840:
			case 11842:
			case 11844:
			case 11846:
			case 11848:
			case 11850:
			case 11852:
			case 11854:
			case 11856:
			case 11858:
			case 11860:
			case 11862:
			case 11864:
			case 11866:
			case 11868:
			case 11870:
			case 11874:
			case 11876:
			case 11878:
			case 11886:
			case 11890:
			case 11894:
			case 11898:
			case 11902:
			case 11904:
			case 11926:
			case 11928:
			case 11930:
			case 11938:
			case 11942:
			case 11944:
			case 11946:
			case 14525:
			case 14527:
			case 14529:
			case 14531:
			case 19588:
			case 19592:
			case 19596:
			case 11908:
			case 11910:
			case 11912:
			case 11914:
			case 11916:
			case 11920:
			case 11922:
			case 11924:
			case 11960:
			case 11962:
			case 11967:
			case 19586:
			case 19584:
			case 19590:
			case 19594:
			case 19598:
				itemDef.actions = new String[5];
				itemDef.actions[0] = "Open";
				break;

			case 14004:
				itemDef.setDefaults();
				itemDef.imitate(get(15486));
				itemDef.modifiedModelColors = new int[11];
				itemDef.originalModelColors = new int[11];
				itemDef.modifiedModelColors[0] = 7860;
				itemDef.originalModelColors[0] = 38310;
				itemDef.modifiedModelColors[1] = 7876;
				itemDef.originalModelColors[1] = 38310;
				itemDef.modifiedModelColors[2] = 7892;
				itemDef.originalModelColors[2] = 38310;
				itemDef.modifiedModelColors[3] = 7884;
				itemDef.originalModelColors[3] = 38310;
				itemDef.modifiedModelColors[4] = 7868;
				itemDef.originalModelColors[4] = 38310;
				itemDef.modifiedModelColors[5] = 7864;
				itemDef.originalModelColors[5] = 38310;
				itemDef.modifiedModelColors[6] = 7880;
				itemDef.originalModelColors[6] = 38310;
				itemDef.modifiedModelColors[7] = 7848;
				itemDef.originalModelColors[7] = 38310;
				itemDef.modifiedModelColors[8] = 7888;
				itemDef.originalModelColors[8] = 38310;
				itemDef.modifiedModelColors[9] = 7872;
				itemDef.originalModelColors[9] = 38310;
				itemDef.modifiedModelColors[10] = 7856;
				itemDef.originalModelColors[10] = 38310;
				break;
			case 14005:
				itemDef.setDefaults();
				itemDef.imitate(get(15486));
				itemDef.modifiedModelColors = new int[11];
				itemDef.originalModelColors = new int[11];
				itemDef.modifiedModelColors[0] = 7860;
				itemDef.originalModelColors[0] = 432;
				itemDef.modifiedModelColors[1] = 7876;
				itemDef.originalModelColors[1] = 432;
				itemDef.modifiedModelColors[2] = 7892;
				itemDef.originalModelColors[2] = 432;
				itemDef.modifiedModelColors[3] = 7884;
				itemDef.originalModelColors[3] = 432;
				itemDef.modifiedModelColors[4] = 7868;
				itemDef.originalModelColors[4] = 432;
				itemDef.modifiedModelColors[5] = 7864;
				itemDef.originalModelColors[5] = 432;
				itemDef.modifiedModelColors[6] = 7880;
				itemDef.originalModelColors[6] = 432;
				itemDef.modifiedModelColors[7] = 7848;
				itemDef.originalModelColors[7] = 432;
				itemDef.modifiedModelColors[8] = 7888;
				itemDef.originalModelColors[8] = 432;
				itemDef.modifiedModelColors[9] = 7872;
				itemDef.originalModelColors[9] = 432;
				itemDef.modifiedModelColors[10] = 7856;
				itemDef.originalModelColors[10] = 432;
				break;

			case 14006:
				itemDef.setDefaults();
				itemDef.imitate(get(15486));
				itemDef.modifiedModelColors = new int[11];
				itemDef.originalModelColors = new int[11];
				itemDef.modifiedModelColors[0] = 7860;
				itemDef.originalModelColors[0] = 24006;
				itemDef.modifiedModelColors[1] = 7876;
				itemDef.originalModelColors[1] = 24006;
				itemDef.modifiedModelColors[2] = 7892;
				itemDef.originalModelColors[2] = 24006;
				itemDef.modifiedModelColors[3] = 7884;
				itemDef.originalModelColors[3] = 24006;
				itemDef.modifiedModelColors[4] = 7868;
				itemDef.originalModelColors[4] = 24006;
				itemDef.modifiedModelColors[5] = 7864;
				itemDef.originalModelColors[5] = 24006;
				itemDef.modifiedModelColors[6] = 7880;
				itemDef.originalModelColors[6] = 24006;
				itemDef.modifiedModelColors[7] = 7848;
				itemDef.originalModelColors[7] = 24006;
				itemDef.modifiedModelColors[8] = 7888;
				itemDef.originalModelColors[8] = 24006;
				itemDef.modifiedModelColors[9] = 7872;
				itemDef.originalModelColors[9] = 24006;
				itemDef.modifiedModelColors[10] = 7856;
				itemDef.originalModelColors[10] = 24006;
				break;
			case 14007:
				itemDef.setDefaults();
				itemDef.imitate(get(15486));
				itemDef.modifiedModelColors = new int[11];
				itemDef.originalModelColors = new int[11];
				itemDef.modifiedModelColors[0] = 7860;
				itemDef.originalModelColors[0] = 14285;
				itemDef.modifiedModelColors[1] = 7876;
				itemDef.originalModelColors[1] = 14285;
				itemDef.modifiedModelColors[2] = 7892;
				itemDef.originalModelColors[2] = 14285;
				itemDef.modifiedModelColors[3] = 7884;
				itemDef.originalModelColors[3] = 14285;
				itemDef.modifiedModelColors[4] = 7868;
				itemDef.originalModelColors[4] = 14285;
				itemDef.modifiedModelColors[5] = 7864;
				itemDef.originalModelColors[5] = 14285;
				itemDef.modifiedModelColors[6] = 7880;
				itemDef.originalModelColors[6] = 14285;
				itemDef.modifiedModelColors[7] = 7848;
				itemDef.originalModelColors[7] = 14285;
				itemDef.modifiedModelColors[8] = 7888;
				itemDef.originalModelColors[8] = 14285;
				itemDef.modifiedModelColors[9] = 7872;
				itemDef.originalModelColors[9] = 14285;
				itemDef.modifiedModelColors[10] = 7856;
				itemDef.originalModelColors[10] = 14285;
				break;
			case 14003:
				itemDef.name = "Robin hood hat";
				itemDef.modelID = 3021;
				itemDef.modifiedModelColors = new int[3];
				itemDef.originalModelColors = new int[3];
				itemDef.modifiedModelColors[0] = 15009;
				itemDef.originalModelColors[0] = 30847;
				itemDef.modifiedModelColors[1] = 17294;
				itemDef.originalModelColors[1] = 32895;
				itemDef.modifiedModelColors[2] = 15252;
				itemDef.originalModelColors[2] = 30847;
				itemDef.modelZoom = 650;
				itemDef.modelRotation1 = 2044;
				itemDef.modelRotation2 = 256;
				itemDef.modelOffset1 = -3;
				itemDef.modelOffsetY = -5;
				itemDef.maleWearId = 3378;
				itemDef.femaleWearId = 3382;
				itemDef.maleDialogue = 3378;
				itemDef.femaleDialogue = 3382;
				itemDef.groundActions = new String[5];
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				itemDef.actions[4] = "Drop";
				break;

			case 14001:
				itemDef.name = "Robin hood hat";
				itemDef.modelID = 3021;
				itemDef.modifiedModelColors = new int[3];
				itemDef.originalModelColors = new int[3];
				itemDef.modifiedModelColors[0] = 15009;
				itemDef.originalModelColors[0] = 10015;
				itemDef.modifiedModelColors[1] = 17294;
				itemDef.originalModelColors[1] = 7730;
				itemDef.modifiedModelColors[2] = 15252;
				itemDef.originalModelColors[2] = 7973;
				itemDef.modelZoom = 650;
				itemDef.modelRotation1 = 2044;
				itemDef.modelRotation2 = 256;
				itemDef.modelOffset1 = -3;
				itemDef.modelOffsetY = -5;
				itemDef.maleWearId = 3378;
				itemDef.femaleWearId = 3382;
				itemDef.maleDialogue = 3378;
				itemDef.femaleDialogue = 3382;
				itemDef.groundActions = new String[5];
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				itemDef.actions[4] = "Drop";
				break;

			case 14002:
				itemDef.name = "Robin hood hat";
				itemDef.modelID = 3021;
				itemDef.modifiedModelColors = new int[3];
				itemDef.originalModelColors = new int[3];
				itemDef.modifiedModelColors[0] = 15009;
				itemDef.originalModelColors[0] = 35489;
				itemDef.modifiedModelColors[1] = 17294;
				itemDef.originalModelColors[1] = 37774;
				itemDef.modifiedModelColors[2] = 15252;
				itemDef.originalModelColors[2] = 35732;
				itemDef.modelZoom = 650;
				itemDef.modelRotation1 = 2044;
				itemDef.modelRotation2 = 256;
				itemDef.modelOffset1 = -3;
				itemDef.modelOffsetY = -5;
				itemDef.maleWearId = 3378;
				itemDef.femaleWearId = 3382;
				itemDef.maleDialogue = 3378;
				itemDef.femaleDialogue = 3382;
				itemDef.groundActions = new String[5];
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				itemDef.actions[4] = "Drop";
				break;

			case 14000:
				itemDef.name = "Robin hood hat";
				itemDef.modelID = 3021;
				itemDef.modifiedModelColors = new int[3];
				itemDef.originalModelColors = new int[3];
				itemDef.modifiedModelColors[0] = 15009;
				itemDef.originalModelColors[0] = 3745;
				itemDef.modifiedModelColors[1] = 17294;
				itemDef.originalModelColors[1] = 3982;
				itemDef.modifiedModelColors[2] = 15252;
				itemDef.originalModelColors[2] = 3988;
				itemDef.modelZoom = 650;
				itemDef.modelRotation1 = 2044;
				itemDef.modelRotation2 = 256;
				itemDef.modelOffsetX = 1;
				itemDef.modelOffsetY = -5;
				itemDef.groundActions = new String[5];
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				itemDef.actions[4] = "Drop";
				itemDef.maleWearId = 3378;
				itemDef.femaleWearId = 3382;
				itemDef.maleDialogue = 3378;
				itemDef.femaleDialogue = 3382;
				break;

			case 20000:
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				itemDef.actions[4] = "Drop";
				itemDef.modelID = 53835;
				itemDef.name = "Steadfast boots";
				itemDef.modelZoom = 900;
				itemDef.modelRotation1 = 165;
				itemDef.modelRotation2 = 99;
				itemDef.modelOffset1 = 3;
				itemDef.modelOffsetY = -7;
				itemDef.maleWearId = 53327;
				itemDef.femaleWearId = 53643;
				itemDef.description2 = "A pair of Steadfast boots.";
				break;

			case 20001:
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				itemDef.actions[4] = "Drop";
				itemDef.modelID = 53828;
				itemDef.name = "Glaiven boots";
				itemDef.modelZoom = 900;
				itemDef.modelRotation1 = 165;
				itemDef.modelRotation2 = 99;
				itemDef.modelOffset1 = 3;
				itemDef.modelOffsetY = -7;
				itemDef.femaleWearId = 53309;
				itemDef.maleWearId = 53309;
				itemDef.description2 = "A pair of Glaiven boots.";
				break;

			case 20002:
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				itemDef.actions[4] = "Drop";
				itemDef.description2 = "A pair of Ragefire boots.";
				itemDef.modelID = 53897;
				itemDef.name = "Ragefire boots";
				itemDef.modelZoom = 900;
				itemDef.modelRotation1 = 165;
				itemDef.modelRotation2 = 99;
				itemDef.modelOffset1 = 3;
				itemDef.modelOffsetY = -7;
				itemDef.maleWearId = 53330;
				itemDef.femaleWearId = 53651;
				break;
			case 14018:
				itemDef.imitate(get(20929));
				break;
			case 14008:
				itemDef.modelID = 62714;
				itemDef.name = "Torva full helm";
				itemDef.description2 = "Torva full helm";
				itemDef.modelZoom = 672;
				itemDef.modelRotation1 = 85;
				itemDef.modelRotation2 = 1867;
				itemDef.modelOffset1 = 0;
				itemDef.modelOffsetY = -3;
				itemDef.maleWearId = 62738;
				itemDef.femaleWearId = 62754;
				itemDef.groundActions = new String[5];
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				itemDef.actions[2] = "Check-charges";
				itemDef.actions[4] = "Drop";
				itemDef.maleDialogue = 62729;
				itemDef.femaleDialogue = 62729;
				break;
			case 14009:
				itemDef.modelID = 62699;
				itemDef.name = "Torva platebody";
				itemDef.description2 = "Torva platebody";
				itemDef.modelZoom = 1506;
				itemDef.modelRotation1 = 473;
				itemDef.modelRotation2 = 2042;
				itemDef.modelOffset1 = 0;
				itemDef.modelOffsetY = 0;
				itemDef.maleWearId = 62746;
				itemDef.femaleWearId = 62762;
				itemDef.groundActions = new String[5];
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				itemDef.actions[2] = "Check-charges";
				itemDef.actions[4] = "Drop";
				break;

			case 14010:
				itemDef.modelID = 62701;
				itemDef.name = "Torva platelegs";
				itemDef.description2 = "Torva platelegs";
				itemDef.modelZoom = 1740;
				itemDef.modelRotation1 = 474;
				itemDef.modelRotation2 = 2045;
				itemDef.modelOffset1 = 0;
				itemDef.modelOffsetY = -5;
				itemDef.maleWearId = 62743;
				itemDef.femaleWearId = 62760;
				itemDef.groundActions = new String[5];
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				itemDef.actions[2] = "Check-charges";
				itemDef.actions[4] = "Drop";
				break;

			case 14011:
				itemDef.modelID = 62693;
				itemDef.name = "Pernix cowl";
				itemDef.description2 = "Pernix cowl";
				itemDef.modelZoom = 800;
				itemDef.modelRotation1 = 532;
				itemDef.modelRotation2 = 14;
				itemDef.modelOffset1 = -1;
				itemDef.modelOffsetY = 1;
				itemDef.maleWearId = 62739;
				itemDef.femaleWearId = 62756;
				itemDef.groundActions = new String[5];
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				itemDef.actions[2] = "Check-charges";
				itemDef.actions[4] = "Drop";
				itemDef.maleDialogue = 62731;
				itemDef.femaleDialogue = 62727;
				/*itemDef.modifiedModelColors = new int[2];
				itemDef.originalModelColors = new int[2];
				itemDef.modifiedModelColors[0] = 4550;
				itemDef.originalModelColors[0] = 0;
				itemDef.modifiedModelColors[1] = 4540;
				itemDef.originalModelColors[1] = 0;*/
				break;

			case 14012:
				itemDef.modelID = 62709;
				itemDef.name = "Pernix body";
				itemDef.description2 = "Pernix body";
				itemDef.modelZoom = 1378;
				itemDef.modelRotation1 = 485;
				itemDef.modelRotation2 = 2042;
				itemDef.modelOffset1 = -1;
				itemDef.modelOffsetY = 7;
				itemDef.maleWearId = 62744;
				itemDef.femaleWearId = 62765;
				itemDef.groundActions = new String[5];
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				itemDef.actions[2] = "Check-charges";
				itemDef.actions[4] = "Drop";
				break;

			case 14013:
				itemDef.modelID = 62695;
				itemDef.name = "Pernix chaps";
				itemDef.description2 = "Pernix chaps";
				itemDef.modelZoom = 1740;
				itemDef.modelRotation1 = 504;
				itemDef.modelRotation2 = 0;
				itemDef.modelOffset1 = 4;
				itemDef.modelOffsetY = 3;
				itemDef.maleWearId = 62741;
				itemDef.femaleWearId = 62757;
				itemDef.groundActions = new String[5];
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				itemDef.actions[2] = "Check-charges";
				itemDef.actions[4] = "Drop";
				break;
			case 14014:
				itemDef.modelID = 62710;
				itemDef.name = "Virtus mask";
				itemDef.description2 = "Virtus mask";
				itemDef.modelZoom = 928;
				itemDef.modelRotation1 = 406;
				itemDef.modelRotation2 = 2041;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = -5;
				itemDef.maleWearId = 62736;
				itemDef.femaleWearId = 62755;
				itemDef.groundActions = new String[5];
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				itemDef.actions[2] = "Check-charges";
				itemDef.actions[4] = "Drop";
				itemDef.maleDialogue = 62728;
				itemDef.femaleDialogue = 62728;
				break;

			case 14015:
				itemDef.modelID = 62704;
				itemDef.name = "Virtus robe top";
				itemDef.description2 = "Virtus robe top";
				itemDef.modelZoom = 1122;
				itemDef.modelRotation1 = 488;
				itemDef.modelRotation2 = 3;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = 0;
				itemDef.maleWearId = 62748;
				itemDef.femaleWearId = 62764;
				itemDef.groundActions = new String[5];
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				itemDef.actions[2] = "Check-charges";
				itemDef.actions[4] = "Drop";
				break;

			case 14016:
				itemDef.modelID = 62700;
				itemDef.name = "Virtus robe legs";
				itemDef.description2 = "Virtus robe legs";
				itemDef.modelZoom = 1740;
				itemDef.modelRotation1 = 498;
				itemDef.modelRotation2 = 2045;
				itemDef.modelOffset1 = -1;
				itemDef.modelOffsetY = 4;
				itemDef.maleWearId = 62742;
				itemDef.femaleWearId = 62758;
				itemDef.groundActions = new String[5];
				itemDef.groundActions[2] = "Take";
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wear";
				itemDef.actions[2] = "Check-charges";
				itemDef.actions[4] = "Drop";
				break;

			case 11716:
				itemDef.actions = new String[] { null, "Wield", "Make-Hasta", null, "Drop" };
				break;

			case 21120:
				itemDef.name = "Zamorakian hasta";
				itemDef.modelZoom = 1900;
				itemDef.modelRotation1 = 1257;
				itemDef.modelRotation2 = 512;
				itemDef.modelOffset1 = 4;
				itemDef.modelOffsetY = -4;
				itemDef.originalModelColors = new int[] { 41 };
				itemDef.modifiedModelColors = new int[] { 78 };
				itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
				itemDef.modelID = 78038;
				itemDef.maleWearId = 77654;
				itemDef.femaleWearId = 77654;
				break;

			case 21121:
				itemDef.setDefaults();
				itemDef.imitate(get(6666));
				itemDef.name = "Pink flippers";
				itemDef.modifiedModelColors = new int[] { 11171, 11175 };
				itemDef.originalModelColors = new int[] { 123770, 123770 + 4 };
				break;

			case 21122:
				itemDef.setDefaults();
				itemDef.imitate(get(6666));
				itemDef.name = "Black flippers";
				itemDef.modifiedModelColors = new int[] { 11171, 11175 };
				itemDef.originalModelColors = new int[] { 1, 1 + 4 };
				break;

			case 21123:
				itemDef.setDefaults();
				itemDef.imitate(get(6666));
				itemDef.name = "Aqua flippers";
				itemDef.modifiedModelColors = new int[] { 11171, 11175 };
				itemDef.originalModelColors = new int[] { 226770, 226770 + 4 };
				break;

			case 21124:
				itemDef.setDefaults();
				itemDef.imitate(get(6666));
				itemDef.name = "Red flippers";
				itemDef.modifiedModelColors = new int[] { 11171, 11175 };
				itemDef.originalModelColors = new int[] { 933, 933 + 4 };
				break;

			case 21125:
				itemDef.setDefaults();
				itemDef.imitate(get(6666));
				itemDef.name = "Lime flippers";
				itemDef.modifiedModelColors = new int[] { 11171, 11175 };
				itemDef.originalModelColors = new int[] { 17350, 17350 + 4 };
				break;

			case 21126:
				itemDef.setDefaults();
				itemDef.imitate(get(6666));
				itemDef.name = "White flippers";
				itemDef.modifiedModelColors = new int[] { 11171, 11175 };
				itemDef.originalModelColors = new int[] { 100, 100 + 4 };
				break;

			case 21127:
				itemDef.setDefaults();
				itemDef.imitate(get(6666));
				itemDef.name = "Purple flippers";
				itemDef.modifiedModelColors = new int[] { 11171, 11175 };
				itemDef.originalModelColors = new int[] { 49950, 49950 + 4 };
				break;

			case 21128:
				itemDef.setDefaults();
				itemDef.imitate(get(13101));
				itemDef.name = "White top hat";
				itemDef.modifiedModelColors = new int[] { 0, 4, 8, 12, 16, 20, 24 };
				itemDef.originalModelColors = new int[] { 100, 100 + 4, 100 + 8, 100 + 12, 100 + 16, 100 + 20, 100 + 24 };
				break;

			case 21129:
				itemDef.setDefaults();
				itemDef.imitate(get(13101));
				itemDef.name = "Pink top hat";
				itemDef.modifiedModelColors = new int[] { 0, 4, 8, 12, 16, 20, 24 };
				itemDef.originalModelColors = new int[] { 123770, 123770, 123770, 123770, 123770, 123770, 123770 };
				break;

			case 21130:
				itemDef.setDefaults();
				itemDef.imitate(get(13101));
				itemDef.name = "Purple top hat";
				itemDef.modifiedModelColors = new int[] { 0, 4, 8, 12, 16, 20, 24 };
				itemDef.originalModelColors = new int[] { 49950, 49950 + 4, 49950 + 8, 49950 + 12, 49950 + 16, 49950 + 20,
						49950 + 24 };
				break;

			case 21131:
				itemDef.setDefaults();
				itemDef.imitate(get(13101));
				itemDef.name = "Lime top hat";
				itemDef.modifiedModelColors = new int[] { 0, 4, 8, 12, 16, 20, 24 };
				itemDef.originalModelColors = new int[] { 17350, 17350 + 4, 17350 + 8, 17350 + 12, 17350 + 16, 17350 + 20,
						17350 + 24 };
				break;

			case 21132:
				itemDef.setDefaults();
				itemDef.imitate(get(13101));
				itemDef.name = "Aqua top hat";
				itemDef.modifiedModelColors = new int[] { 0, 4, 8, 12, 16, 20, 24 };
				itemDef.originalModelColors = new int[] { 226770, 226770 + 4, 226770 + 8, 226770 + 12, 226770 + 16,
						226770 + 20, 226770 + 24 };
				break;

			case 21133:
				itemDef.setDefaults();
				itemDef.imitate(get(13101));
				itemDef.name = "Premium top hat";
				itemDef.modifiedModelColors = new int[] { 0, 4, 8, 12, 16, 20, 24, 549, 553, 673, 668 };
				itemDef.originalModelColors = new int[] { 933, 933 + 4, 933 + 8, 933 + 12, 933 + 16, 933 + 20, 933 + 24,
						11200, 11204, 11324, 11324 - 5 };
				break;
			case 21136:
				itemDef.setDefaults();
				itemDef.name = "3rd age bow";
				itemDef.modelZoom = 1979;
				itemDef.modelRotation1 = 1589;
				itemDef.modelRotation2 = 768;
				itemDef.modelOffset1 = -20;
				itemDef.modelOffsetY = -14;
				itemDef.modelOffsetX = 835;
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
				itemDef.modelID = 28678;
				itemDef.maleWearId = 28622;
				itemDef.femaleWearId = 28622;
				break;
			case 21137:
				itemDef.setDefaults();
				itemDef.modelID = 2429;
				itemDef.name = "3rd age bow";
				itemDef.actions = new String[] { null, null, null, null, null, null };
				itemDef.modelZoom = 1979;
				itemDef.modelRotation1 = 1589;
				itemDef.modelRotation2 = 768;
				itemDef.modelOffset1 = -20;
				itemDef.modelOffsetY = -14;
				itemDef.modelOffsetX = 835;
				itemDef.stackable = true;
				itemDef.certID = 21136;
				itemDef.certTemplateID = 799;
				break;
			case 21138:
				itemDef.setDefaults();
				itemDef.description2 = "A beautifully crafted wand infused by ancient wizards.";
				itemDef.name = "3rd age wand";
				itemDef.modelZoom = 1347;
				itemDef.modelRotation1 = 1468;
				itemDef.modelRotation2 = 1805;
				itemDef.modelOffset1 = 0;
				itemDef.modelOffsetY = 1;
				itemDef.modelOffsetX = 835;
				itemDef.modelID = 28654;
				itemDef.maleWearId = 28619;
				itemDef.femaleWearId = 28619;
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
				break;
			case 21139:
				itemDef.setDefaults();
				itemDef.modelID = 2429;
				itemDef.description2 = "A beautifully crafted wand infused by ancient wizards.";
				itemDef.name = "3rd age wand";
				itemDef.actions = new String[] { null, null, null, null, null, null };
				itemDef.modelZoom = 760;
				itemDef.modelRotation2 = 28;
				itemDef.modelRotation1 = 552;
				itemDef.modelOffset1 = 0;
				itemDef.modelOffsetY = 1;
				itemDef.modelOffsetX = 835;
				itemDef.stackable = true;
				itemDef.certID = 21138;
				itemDef.certTemplateID = 799;
				break;
			case 21140:
				itemDef.setDefaults();
				itemDef.modelID = 31524;
				itemDef.maleWearId = 31227;
				itemDef.femaleWearId = 31233;
				itemDef.description2 = "A deep sense of torture burns within this powerful amulet.";
				itemDef.name = "Amulet of torture";
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
				itemDef.modelZoom = 620;
				itemDef.modelRotation1 = 424;
				itemDef.modelRotation2 = 68;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = 16;
				break;
			case 21141:
				itemDef.setDefaults();
				itemDef.modelID = 31524;
				itemDef.description2 = "A deep sense of torture burns within this powerful amulet.";
				itemDef.name = "Amulet of torture";
				itemDef.actions = new String[] { null, null, null, null, null, null };
				itemDef.modelZoom = 620;
				itemDef.modelRotation1 = 424;
				itemDef.modelRotation2 = 68;
				itemDef.modelOffset1 = 1;
				itemDef.modelOffsetY = 16;
				itemDef.stackable = true;
				itemDef.certID = 21140;
				itemDef.certTemplateID = 799;
				break;
			case 21142:
				itemDef.setDefaults();
				itemDef.modelID = 31522;
				itemDef.maleWearId = 31237;
				itemDef.femaleWearId = 31237;
				itemDef.description2 = "A weapon forged from the wreckage of an airship.";
				itemDef.name = "Light ballista";
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
				itemDef.modelZoom = 1250;
				itemDef.modelRotation1 = 189;
				itemDef.modelRotation2 = 148;
				itemDef.modelOffset1 = 8;
				itemDef.modelOffsetY = -18;
				break;
			case 21143:
				itemDef.setDefaults();
				itemDef.modelID = 31522;
				itemDef.description2 = "A weapon forged from the wreckage of an airship.";
				itemDef.name = "Light ballista";
				itemDef.actions = new String[] { null, null, null, null, null, null };
				itemDef.modelZoom = 760;
				itemDef.modelZoom = 1250;
				itemDef.modelRotation1 = 189;
				itemDef.modelRotation2 = 148;
				itemDef.modelOffset1 = 8;
				itemDef.modelOffsetY = -18;
				itemDef.stackable = true;
				itemDef.certID = 21142;
				itemDef.certTemplateID = 799;
				break;
			case 21144:
				itemDef.setDefaults();
				itemDef.modelID = 31523;
				itemDef.maleWearId = 31236;
				itemDef.femaleWearId = 31236;
				itemDef.description2 = "A weapon forged from the wreckage of an airship.";
				itemDef.name = "Heavy ballista";
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
				itemDef.modelZoom = 1284;
				itemDef.modelRotation1 = 189;
				itemDef.modelRotation2 = 148;
				itemDef.modelOffset1 = 8;
				itemDef.modelOffsetY = -18;
				itemDef.maleWieldX = 55; //char north/south
				itemDef.maleWieldY = 0; // char to left/right
				itemDef.maleWieldZ = 26; // char up/down
				itemDef.femaleWieldX = 55; //char north/south
				itemDef.femaleWieldY = 0; // char to left/right
				itemDef.femaleWieldZ = 26; // char up/down
				break;
			case 21145:
				itemDef.setDefaults();
				itemDef.modelID = 31523;
				itemDef.description2 = "A weapon forged from the wreckage of an airship.";
				itemDef.name = "Heavy ballista";
				itemDef.actions = new String[] { null, null, null, null, null, null };
				itemDef.modelZoom = 1284;
				itemDef.modelRotation1 = 189;
				itemDef.modelRotation2 = 148;
				itemDef.modelOffset1 = 8;
				itemDef.modelOffsetY = -18;
				itemDef.stackable = true;
				itemDef.certID = 21142;
				itemDef.certTemplateID = 799;
				break;
			case 21146:
				itemDef.setDefaults();
				itemDef.modelID = 31511;
				itemDef.description2 = "A dragon tipped javelin.";
				itemDef.name = "Dragon javelin";
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
				itemDef.modelZoom = 1470;
				itemDef.modelRotation1 = 268;
				itemDef.modelRotation2 = 1964;
				itemDef.modelOffset1 = -2;
				itemDef.modelOffsetY = 63;
				itemDef.stackable = true;
				break;

			case 20147:
				itemDef.modifiedModelColors = new int[2];
				itemDef.originalModelColors = new int[2];
				itemDef.modifiedModelColors[0] = 4550;
				itemDef.originalModelColors[0] = 1;
				itemDef.modifiedModelColors[1] = 4540;
				itemDef.originalModelColors[1] = 1;
				break;

			case 21148:
				itemDef.setDefaults();
				itemDef.imitate(get(15434));
				itemDef.name = "Cape of darkness";
				itemDef.description2 = "A cape made from a dark realm.";
				itemDef.originalModelColors = new int[] {0, 12, 12, 7, 5, 2};
				itemDef.modifiedModelColors = new int[] {42, 32, 39577, 39572, 39570, 39567};
				break;


			case 11283:
			case 11284:
			case 11285:
				itemDef.maleWieldY = 18;
				itemDef.femaleWieldY = 11;
				break;
		}

		if (itemDef.description2 != null) {
			itemDef.description = itemDef.description2.getBytes();
		}
		if (itemDef.certTemplateID != -1) {
			itemDef.toNote();
		}

		if (itemDef.lendTemplateID != -1) {
			itemDef.toLend();
		}

		if (!isMembers && itemDef.membersObject) {
			itemDef.name = "Members Object";
			itemDef.description = "Login to a members' server to use this object.".getBytes();
			itemDef.groundActions = null;
			itemDef.actions = null;
			itemDef.team = 0;
		}
		return itemDef;
	}

	public static Sprite getSprite(int i, int j, int k) {
		if (k == 0) {
			Sprite sprite = (Sprite) mruNodes1.insertFromCache(i);

			if (sprite != null && sprite.maxHeight != j && sprite.maxHeight != -1) {
				sprite.unlink();
				sprite = null;
			}

			if (sprite != null) {
				return sprite;
			}
		}

		if (i > ItemDefinition.totalItems) {
			return null;
		}
		ItemDefinition definition = get(i);

		if (definition.stackIDs == null) {
			j = -1;
		}

		if (j > 1) {
			int i1 = -1;

			for (int j1 = 0; j1 < 10; j1++) {
				if (j >= definition.stackAmounts[j1] && definition.stackAmounts[j1] != 0) {
					i1 = definition.stackIDs[j1];
				}
			}

			if (i1 != -1) {
				definition = get(i1);
			}
		}

		Model model = definition.getInventoryModel(1);

		if (model == null) {
			return null;
		}

		Sprite sprite = null;

		if (definition.certTemplateID != -1) {
			sprite = getSprite(definition.certID, 10, -1);

			if (sprite == null) {
				return null;
			}
		}

		if (definition.lendTemplateID != -1) {
			sprite = getSprite(definition.lendID, 50, 0);

			if (sprite == null) {
				return null;
			}
		}

		Sprite sprite2 = new Sprite(32, 32);
		int k1 = Canvas3D.centerX;
		int l1 = Canvas3D.centerY;
		int ai[] = Canvas3D.lineOffsets;
		int ai1[] = Canvas2D.pixels;
		float depth[] = Canvas2D.depthBuffer;
		int i2 = Canvas2D.width;
		int j2 = Canvas2D.height;
		int k2 = Canvas2D.topX;
		int l2 = Canvas2D.bottomX;
		int i3 = Canvas2D.topY;
		int j3 = Canvas2D.bottomY;
		Canvas3D.notTextured = false;
		Canvas2D.initDrawingArea(32, 32, sprite2.myPixels, new float[32*32]);
		Canvas2D.drawPixels(32, 0, 0, 0, 32);
		Canvas3D.method364();
		int k3 = definition.modelZoom;

		if (k == -1) {
			k3 = (int) (k3 * 1.5D);
		}

		if (k > 0) {
			k3 = (int) (k3 * 1.04D);
		}

		int l3 = Canvas3D.SINE[definition.modelRotation1] * k3 >> 16;
		int i4 = Canvas3D.COSINE[definition.modelRotation1] * k3 >> 16;
		model.method482(definition.modelRotation2, definition.modelOffsetX, definition.modelRotation1,
				definition.modelOffset1, l3 + model.modelHeight / 2 + definition.modelOffsetY,
				i4 + definition.modelOffsetY);

		for (int i5 = 31; i5 >= 0; i5--) {
			for (int j4 = 31; j4 >= 0; j4--) {
				if (sprite2.myPixels[i5 + j4 * 32] == 0) {
					if (i5 > 0 && sprite2.myPixels[i5 - 1 + j4 * 32] > 1) {
						sprite2.myPixels[i5 + j4 * 32] = 1;
					} else if (j4 > 0 && sprite2.myPixels[i5 + (j4 - 1) * 32] > 1) {
						sprite2.myPixels[i5 + j4 * 32] = 1;
					} else if (i5 < 31 && sprite2.myPixels[i5 + 1 + j4 * 32] > 1) {
						sprite2.myPixels[i5 + j4 * 32] = 1;
					} else if (j4 < 31 && sprite2.myPixels[i5 + (j4 + 1) * 32] > 1) {
						sprite2.myPixels[i5 + j4 * 32] = 1;
					}
				}
			}
		}

		if (k > 0) {
			for (int j5 = 31; j5 >= 0; j5--) {
				for (int k4 = 31; k4 >= 0; k4--) {
					if (sprite2.myPixels[j5 + k4 * 32] == 0) {
						if (j5 > 0 && sprite2.myPixels[j5 - 1 + k4 * 32] == 1) {
							sprite2.myPixels[j5 + k4 * 32] = k;
						} else if (k4 > 0 && sprite2.myPixels[j5 + (k4 - 1) * 32] == 1) {
							sprite2.myPixels[j5 + k4 * 32] = k;
						} else if (j5 < 31 && sprite2.myPixels[j5 + 1 + k4 * 32] == 1) {
							sprite2.myPixels[j5 + k4 * 32] = k;
						} else if (k4 < 31 && sprite2.myPixels[j5 + (k4 + 1) * 32] == 1) {
							sprite2.myPixels[j5 + k4 * 32] = k;
						}
					}
				}
			}
		} else if (k == 0) {
			for (int k5 = 31; k5 >= 0; k5--) {
				for (int l4 = 31; l4 >= 0; l4--) {
					if (sprite2.myPixels[k5 + l4 * 32] == 0 && k5 > 0 && l4 > 0
							&& sprite2.myPixels[k5 - 1 + (l4 - 1) * 32] > 0) {
						sprite2.myPixels[k5 + l4 * 32] = 0x302020;
					}
				}
			}
		}

		if (definition.certTemplateID != -1) {
			int l5 = sprite.maxWidth;
			int j6 = sprite.maxHeight;
			sprite.maxWidth = 32;
			sprite.maxHeight = 32;
			sprite.drawSprite(0, 0);
			sprite.maxWidth = l5;
			sprite.maxHeight = j6;
		}

		if (definition.lendTemplateID != -1) {
			int l5 = sprite.maxWidth;
			int j6 = sprite.maxHeight;
			sprite.maxWidth = 32;
			sprite.maxHeight = 32;
			sprite.drawSprite(0, 0);
			sprite.maxWidth = l5;
			sprite.maxHeight = j6;
		}

		if (k == 0) {
			mruNodes1.removeFromCache(sprite2, i);
		}

		Canvas2D.initDrawingArea(j2, i2, ai1, null);
		Canvas2D.setBounds(k2, i3, l2, j3);
		Canvas3D.centerX = k1;
		Canvas3D.centerY = l1;
		Canvas3D.lineOffsets = ai;
		Canvas3D.notTextured = true;

		if (definition.stackable) {
			sprite2.maxWidth = 33;
		} else {
			sprite2.maxWidth = 32;
		}

		sprite2.maxHeight = j;
		return sprite2;
	}

	public static Sprite getSprite(int i, int j, int k, int zoom) {
		if (k == 0 && zoom != -1) {
			Sprite sprite = (Sprite) mruNodes1.insertFromCache(i);

			if (sprite != null && sprite.maxHeight != j && sprite.maxHeight != -1) {
				sprite.unlink();
				sprite = null;
			}

			if (sprite != null) {
				return sprite;
			}
		}

		ItemDefinition definition = get(i);

		if (definition.stackIDs == null) {
			j = -1;
		}

		if (j > 1) {
			int i1 = -1;

			for (int j1 = 0; j1 < 10; j1++) {
				if (j >= definition.stackAmounts[j1] && definition.stackAmounts[j1] != 0) {
					i1 = definition.stackIDs[j1];
				}
			}

			if (i1 != -1) {
				definition = get(i1);
			}
		}

		Model model = definition.getInventoryModel(1);

		if (model == null) {
			return null;
		}

		Sprite sprite = null;

		if (definition.certTemplateID != -1) {
			sprite = getSprite(definition.certID, 10, -1);

			if (sprite == null) {
				return null;
			}
		}

		if (definition.lendTemplateID != -1) {
			sprite = getSprite(definition.lendID, 50, 0);

			if (sprite == null) {
				return null;
			}
		}

		Sprite sprite2 = new Sprite(32, 32);
		int k1 = Canvas3D.centerX;
		int l1 = Canvas3D.centerY;
		int ai[] = Canvas3D.lineOffsets;
		int ai1[] = Canvas2D.pixels;
		float depth[] = Canvas2D.depthBuffer;
		int i2 = Canvas2D.width;
		int j2 = Canvas2D.height;
		int k2 = Canvas2D.topX;
		int l2 = Canvas2D.bottomX;
		int i3 = Canvas2D.topY;
		int j3 = Canvas2D.bottomY;
		Canvas3D.notTextured = false;
		Canvas2D.initDrawingArea(32, 32, sprite2.myPixels, new float[32*32]);
		Canvas2D.drawPixels(32, 0, 0, 0, 32);
		Canvas3D.method364();
		int k3 = definition.modelZoom;
		if (zoom != -1 && zoom != 0)
			k3 = (definition.modelZoom * 100) / zoom;
		if (k == -1) {
			k3 = (int) (k3 * 1.5D);
		}

		if (k > 0) {
			k3 = (int) (k3 * 1.04D);
		}

		int l3 = Canvas3D.SINE[definition.modelRotation1] * k3 >> 16;
		int i4 = Canvas3D.COSINE[definition.modelRotation1] * k3 >> 16;
		model.method482(definition.modelRotation2, definition.modelOffsetX, definition.modelRotation1,
				definition.modelOffset1, l3 + model.modelHeight / 2 + definition.modelOffsetY,
				i4 + definition.modelOffsetY);

		for (int i5 = 31; i5 >= 0; i5--) {
			for (int j4 = 31; j4 >= 0; j4--) {
				if (sprite2.myPixels[i5 + j4 * 32] == 0) {
					if (i5 > 0 && sprite2.myPixels[i5 - 1 + j4 * 32] > 1) {
						sprite2.myPixels[i5 + j4 * 32] = 1;
					} else if (j4 > 0 && sprite2.myPixels[i5 + (j4 - 1) * 32] > 1) {
						sprite2.myPixels[i5 + j4 * 32] = 1;
					} else if (i5 < 31 && sprite2.myPixels[i5 + 1 + j4 * 32] > 1) {
						sprite2.myPixels[i5 + j4 * 32] = 1;
					} else if (j4 < 31 && sprite2.myPixels[i5 + (j4 + 1) * 32] > 1) {
						sprite2.myPixels[i5 + j4 * 32] = 1;
					}
				}
			}
		}

		if (k > 0) {
			for (int j5 = 31; j5 >= 0; j5--) {
				for (int k4 = 31; k4 >= 0; k4--) {
					if (sprite2.myPixels[j5 + k4 * 32] == 0) {
						if (j5 > 0 && sprite2.myPixels[j5 - 1 + k4 * 32] == 1) {
							sprite2.myPixels[j5 + k4 * 32] = k;
						} else if (k4 > 0 && sprite2.myPixels[j5 + (k4 - 1) * 32] == 1) {
							sprite2.myPixels[j5 + k4 * 32] = k;
						} else if (j5 < 31 && sprite2.myPixels[j5 + 1 + k4 * 32] == 1) {
							sprite2.myPixels[j5 + k4 * 32] = k;
						} else if (k4 < 31 && sprite2.myPixels[j5 + (k4 + 1) * 32] == 1) {
							sprite2.myPixels[j5 + k4 * 32] = k;
						}
					}
				}
			}
		} else if (k == 0) {
			for (int k5 = 31; k5 >= 0; k5--) {
				for (int l4 = 31; l4 >= 0; l4--) {
					if (sprite2.myPixels[k5 + l4 * 32] == 0 && k5 > 0 && l4 > 0
							&& sprite2.myPixels[k5 - 1 + (l4 - 1) * 32] > 0) {
						sprite2.myPixels[k5 + l4 * 32] = 0x302020;
					}
				}
			}
		}

		if (definition.certTemplateID != -1) {
			int l5 = sprite.maxWidth;
			int j6 = sprite.maxHeight;
			sprite.maxWidth = 32;
			sprite.maxHeight = 32;
			sprite.drawSprite(0, 0);
			sprite.maxWidth = l5;
			sprite.maxHeight = j6;
		}

		if (definition.lendTemplateID != -1) {
			int l5 = sprite.maxWidth;
			int j6 = sprite.maxHeight;
			sprite.maxWidth = 32;
			sprite.maxHeight = 32;
			sprite.drawSprite(0, 0);
			sprite.maxWidth = l5;
			sprite.maxHeight = j6;
		}

		if (k == 0 && i != 21088) {
			mruNodes1.removeFromCache(sprite2, i);
		}

		Canvas2D.initDrawingArea(j2, i2, ai1, null);
		Canvas2D.setBounds(k2, i3, l2, j3);
		Canvas3D.centerX = k1;
		Canvas3D.centerY = l1;
		Canvas3D.lineOffsets = ai;
		Canvas3D.notTextured = true;

		if (definition.stackable) {
			sprite2.maxWidth = 33;
		} else {
			sprite2.maxWidth = 32;
		}

		sprite2.maxHeight = j;
		return sprite2;
	}

	public static void nullify() {
		mruNodes2 = null;
		mruNodes1 = null;
		streamIndices = null;
		cache = null;
		buffer = null;
	}

	public static void unpackConfig(Archive streamLoader) {
		buffer = new ByteBuffer(streamLoader.get("obj.dat"));
		ByteBuffer stream = new ByteBuffer(streamLoader.get("obj.idx"));
		totalItems = stream.getUnsignedShort();
		streamIndices = new int[totalItems];
		int i = 2;

		for (int j = 0; j < totalItems; j++) {
			streamIndices[j] = i;
			i += stream.getUnsignedShort();
		}

		cache = new ItemDefinition[10];

		for (int k = 0; k < 10; k++) {
			cache[k] = new ItemDefinition();
		}
	}

	public static void printModelIds() {
		/*
		 * java.util.List<Integer> modelIds = new ArrayList<>(); for (int index
		 * = 0; index < totalItems; index++) { ItemDefinition def = get(index);
		 * if (def == null) { continue; } //modelIds.add(def.modelID); if
		 * (def.maleWearId > 0 && !modelIds.contains(def.maleWearId)) {
		 * modelIds.add(def.maleWearId); } if (def.maleWearId2 > 0 &&
		 * !modelIds.contains(def.maleWearId2)) { modelIds.add(def.maleWearId2);
		 * } if (def.maleWearId3 > 0 && !modelIds.contains(def.maleWearId3)) {
		 * modelIds.add(def.maleWearId3); } if (def.femaleWearId > 0 &&
		 * !modelIds.contains(def.femaleWearId)) {
		 * modelIds.add(def.femaleWearId); } if (def.femaleWearId2 > 0 &&
		 * !modelIds.contains(def.femaleWearId2)) {
		 * modelIds.add(def.femaleWearId2); } if (def.femaleWearId3 > 0 &&
		 * !modelIds.contains(def.femaleWearId3)) {
		 * modelIds.add(def.femaleWearId3); } } System.out.println(
		 * "Item models: " + modelIds.toString());
		 */
	}

	public String[] actions;
	private int femaleWearId3;
	int femaleWearId2;
	public int maleWearId;
	private int anInt166;
	private int anInt167;
	private int anInt173;
	private int maleDialogue;
	private int anInt184;
	private int maleWearId3;
	int maleWearId2;
	private int anInt191;
	private int anInt192;
	private int anInt196;
	private int femaleDialogue;
	public int femaleWearId;
	private int modelOffsetX;
	public int certID;
	public int certTemplateID;
	public byte[] description;
	public String description2;
	public byte femaleWieldX;
	public byte femaleWieldY;
	public byte femaleWieldZ;
	public String[] groundActions;
	public int id;
	public int lendID;
	private int lendTemplateID;
	public byte maleWieldX;
	public byte maleWieldY;
	public byte maleWieldZ;
	public boolean membersObject;
	public int modelID;
	int modelOffset1;
	int modelOffsetY;
	public int modelRotation1;
	public int modelRotation2;
	public int modelZoom;
	public int[] modifiedModelColors;
	public String name;
	int[] originalModelColors;
	public boolean stackable;
	private int[] stackAmounts;
	private int[] stackIDs;
	public int team;
	public int value;

	public ItemDefinition() {
		id = -1;
	}

	public Model getInventoryModel(int amount) {
		if (stackIDs != null && amount > 1) {
			int id = -1;

			for (int i = 0; i < 10; i++) {
				if (amount >= stackAmounts[i] && stackAmounts[i] != 0) {
					id = stackIDs[i];
				}
			}

			if (id != -1) {
				return get(id).getInventoryModel(1);
			}
		}

		Model model = (Model) mruNodes2.insertFromCache(id);

		if (model != null) {
			return model;
		}

		model = Model.fetchModel(modelID);

		if (model == null) {
			return null;
		}

		if (anInt167 != 128 || anInt192 != 128 || anInt191 != 128) {
			model.method478(anInt167, anInt191, anInt192);
		}

		if (modifiedModelColors != null) {
			for (int l = 0; l < modifiedModelColors.length; l++) {
				model.method476(modifiedModelColors[l], originalModelColors[l]);
			}
		}

		model.method479(64 + anInt196, 768 + anInt184, -50, -10, -50, true);
		model.aBoolean1659 = true;
		if (this.id != 21088) {
			mruNodes2.removeFromCache(model, id);
		}
		return model;
	}

	public boolean dialogueModelFetched(int j) {
		int k = maleDialogue;
		int l = anInt166;

		if (j == 1) {
			k = femaleDialogue;
			l = anInt173;
		}

		if (k == -1) {
			return true;
		}

		boolean flag = true;

		if (!Model.isModelLoaded(k)) {
			flag = false;
		}

		if (l != -1 && !Model.isModelLoaded(l)) {
			flag = false;
		}

		return flag;
	}

	public Model method194(int j) {
		int k = maleDialogue;
		int l = anInt166;

		if (j == 1) {
			k = femaleDialogue;
			l = anInt173;
		}

		if (k == -1) {
			return null;
		}

		Model model = Model.fetchModel(k);

		if (l != -1) {
			Model model_1 = Model.fetchModel(l);
			Model models[] = { model, model_1 };
			model = new Model(2, models);
		}

		if (modifiedModelColors != null) {
			for (int i1 = 0; i1 < modifiedModelColors.length; i1++) {
				model.method476(modifiedModelColors[i1], originalModelColors[i1]);
			}
		}

		return model;
	}

	public boolean method195(int j) {
		int k = maleWearId;
		int l = maleWearId2;
		int i1 = maleWearId3;

		if (j == 1) {
			k = femaleWearId;
			l = femaleWearId2;
			i1 = femaleWearId3;
		}

		if (k == -1) {
			return true;
		}

		boolean flag = true;

		if (!Model.isModelLoaded(k)) {
			flag = false;
		}

		if (l != -1 && !Model.isModelLoaded(l)) {
			flag = false;
		}

		if (i1 != -1 && !Model.isModelLoaded(i1)) {
			flag = false;
		}

		return flag;
	}

	public Model getEquippedModel(int gender) {
		int primaryModel = maleWearId;
		int secondaryModel = maleWearId2;
		int emblem = maleWearId3;

		if (gender == 1) {
			primaryModel = femaleWearId;
			secondaryModel = femaleWearId2;
			emblem = femaleWearId3;
		}

		if (primaryModel == -1) {
			return null;
		}

		Model model = Model.fetchModel(primaryModel);

		if (secondaryModel != -1) {
			if (emblem != -1) {
				Model model_1 = Model.fetchModel(secondaryModel);
				Model model_3 = Model.fetchModel(emblem);
				Model model_1s[] = { model, model_1, model_3 };
				model = new Model(3, model_1s);
			} else {
				Model model_2 = Model.fetchModel(secondaryModel);
				Model models[] = { model, model_2 };
				model = new Model(2, models);
			}
		}

		if (gender == 0 && (maleWieldX != 0 || maleWieldY != 0 || maleWieldZ != 0)) {
			model.method475(maleWieldX, maleWieldY, maleWieldZ);
		}

		if (gender == 1 && (femaleWieldX != 0 || femaleWieldY != 0 || femaleWieldZ != 0)) {
			model.method475(femaleWieldX, femaleWieldY, femaleWieldZ);
		}

		//Cheap fix for the offsets of female wield models
		if (gender == 1) {
			if (id == 11283 || id == 11284 || id == 11285) {
				model.method475(-3, 0, 0);
			}
			for (String itemActions : actions) {
				if (itemActions == null || itemActions.length() == 0) {
					continue;
				}
				if (itemActions.equalsIgnoreCase("Wield")) {
					model.method475(3, femaleWieldY - 12, 5);
				}
			}
		}

		if (modifiedModelColors != null) {
			for (int i1 = 0; i1 < modifiedModelColors.length; i1++) {
				model.method476(modifiedModelColors[i1], originalModelColors[i1]);
			}
		}

		return model;
	}

	public Model method202(int i) {
		if (stackIDs != null && i > 1) {
			int j = -1;

			for (int k = 0; k < 10; k++) {
				if (i >= stackAmounts[k] && stackAmounts[k] != 0) {
					j = stackIDs[k];
				}
			}

			if (j != -1) {
				return get(j).method202(1);
			}
		}

		Model model = Model.fetchModel(modelID);

		if (model == null) {
			return null;
		}

		if (modifiedModelColors != null) {
			for (int l = 0; l < modifiedModelColors.length; l++) {
				model.method476(modifiedModelColors[l], originalModelColors[l]);
			}
		}

		return model;
	}

	public void imitate(ItemDefinition other) {
		name = other.name;
		description = other.description;
		modifiedModelColors = other.modifiedModelColors;
		originalModelColors = other.originalModelColors;
		anInt167 = other.anInt167;
		anInt192 = other.anInt192;
		anInt191 = other.anInt191;
		modelRotation2 = other.modelRotation2;
		modelRotation1 = other.modelRotation1;
		modelOffset1 = other.modelOffset1;
		modelOffsetY = other.modelOffsetY;
		modelOffsetX = other.modelOffsetX;
		modelZoom = other.modelZoom;
		modelID = other.modelID;
		actions = other.actions;
		maleWearId = other.maleWearId;
		maleWearId2 = other.maleWearId2;
		maleWearId3 = other.maleWearId3;
		femaleWearId = other.femaleWearId;
		femaleWearId = other.femaleWearId;
		femaleWearId3 = other.femaleWearId3;
		maleDialogue = other.maleDialogue;
		anInt166 = other.anInt166;
		femaleDialogue = other.femaleDialogue;
		anInt173 = other.anInt173;
	}

	private void readValues(ByteBuffer buffer) {
		do {
			int opcode = buffer.getUnsignedByte();

			if (opcode == 0) {
				return;
			} else if (opcode == 1) {
				modelID = buffer.getUnsignedShort();
			} else if (opcode == 2) {
				name = buffer.getString();
			} else if (opcode == 3) {
				description = buffer.getBytes();
			} else if (opcode == 4) {
				modelZoom = buffer.getUnsignedShort();
			} else if (opcode == 5) {
				modelRotation1 = buffer.getUnsignedShort();
			} else if (opcode == 6) {
				modelRotation2 = buffer.getUnsignedShort();
			} else if (opcode == 7) {
				modelOffset1 = buffer.getUnsignedShort();

				if (modelOffset1 > 32767) {
					modelOffset1 -= 0x10000;
				}
			} else if (opcode == 8) {
				modelOffsetY = buffer.getUnsignedShort();

				if (modelOffsetY > 32767) {
					modelOffsetY -= 0x10000;
				}
			} else if (opcode == 10) {
				buffer.getUnsignedShort();
			} else if (opcode == 11) {
				stackable = true;
			} else if (opcode == 12) {
				value = buffer.getIntLittleEndian();
			} else if (opcode == 16) {
				membersObject = true;
			} else if (opcode == 23) {
				maleWearId = buffer.getUnsignedShort();
				maleWieldY = buffer.getSignedByte();
			} else if (opcode == 24) {
				maleWearId2 = buffer.getUnsignedShort();
			} else if (opcode == 25) {
				femaleWearId = buffer.getUnsignedShort();
				femaleWieldY = buffer.getSignedByte();
			} else if (opcode == 26) {
				femaleWearId2 = buffer.getUnsignedShort();
			} else if (opcode >= 30 && opcode < 35) {
				if (groundActions == null) {
					groundActions = new String[5];
				}

				groundActions[opcode - 30] = buffer.getString();

				if (groundActions[opcode - 30].equalsIgnoreCase("hidden")) {
					groundActions[opcode - 30] = null;
				}
			} else if (opcode >= 35 && opcode < 40) {
				if (actions == null) {
					actions = new String[5];
				}

				actions[opcode - 35] = buffer.getString();
			} else if (opcode == 40) {
				int size = buffer.getUnsignedByte();
				modifiedModelColors = new int[size];
				originalModelColors = new int[size];

				for (int k = 0; k < size; k++) {
					modifiedModelColors[k] = buffer.getUnsignedShort();
					originalModelColors[k] = buffer.getUnsignedShort();
				}
			} else if (opcode == 78) {
				maleWearId3 = buffer.getUnsignedShort();
			} else if (opcode == 79) {
				femaleWearId3 = buffer.getUnsignedShort();
			} else if (opcode == 90) {
				maleDialogue = buffer.getUnsignedShort();
			} else if (opcode == 91) {
				femaleDialogue = buffer.getUnsignedShort();
			} else if (opcode == 92) {
				anInt166 = buffer.getUnsignedShort();
			} else if (opcode == 93) {
				anInt173 = buffer.getUnsignedShort();
			} else if (opcode == 95) {
				modelOffsetX = buffer.getUnsignedShort();
			} else if (opcode == 97) {
				certID = buffer.getUnsignedShort();
			} else if (opcode == 98) {
				certTemplateID = buffer.getUnsignedShort();
			} else if (opcode >= 100 && opcode < 110) {
				if (stackIDs == null) {
					stackIDs = new int[10];
					stackAmounts = new int[10];
				}

				stackIDs[opcode - 100] = buffer.getUnsignedShort();
				stackAmounts[opcode - 100] = buffer.getUnsignedShort();
			} else if (opcode == 110) {
				anInt167 = buffer.getUnsignedShort();
			} else if (opcode == 111) {
				anInt192 = buffer.getUnsignedShort();
			} else if (opcode == 112) {
				anInt191 = buffer.getUnsignedShort();
			} else if (opcode == 113) {
				anInt196 = buffer.getSignedByte();
			} else if (opcode == 114) {
				anInt184 = buffer.getSignedByte() * 5;
			} else if (opcode == 115) {
				team = buffer.getUnsignedByte();
			} else if (opcode == 121) {
				lendID = buffer.getUnsignedShort();
			} else if (opcode == 122) {
				lendTemplateID = buffer.getUnsignedShort();
			}
		} while (true);
	}

	private void setDefaults() {
		modelID = 0;
		name = null;
		description = null;
		originalModelColors = null;
		modifiedModelColors = null;
		modelZoom = 2000;
		modelRotation1 = 0;
		modelRotation2 = 0;
		modelOffsetX = 0;
		modelOffset1 = 0;
		modelOffsetY = 0;
		stackable = false;
		value = 1;
		membersObject = false;
		groundActions = null;
		actions = null;
		lendID = -1;
		lendTemplateID = -1;
		maleWearId = -1;
		maleWearId2 = -1;
		femaleWearId = -1;
		femaleWearId2 = -1;
		maleWearId3 = -1;
		femaleWearId3 = -1;
		maleDialogue = -1;
		anInt166 = -1;
		femaleDialogue = -1;
		anInt173 = -1;
		stackIDs = null;
		stackAmounts = null;
		certID = -1;
		certTemplateID = -1;
		anInt167 = 128;
		anInt192 = 128;
		anInt191 = 128;
		anInt196 = 0;
		anInt184 = 0;
		team = 0;
		femaleWieldY = 0;
		femaleWieldX = 0;
		femaleWieldZ = 0;
		maleWieldX = 0;
		maleWieldZ = 0;
		maleWieldY = 0;
	}

	private void toLend() {
		ItemDefinition itemDef = get(lendTemplateID);
		actions = new String[5];
		modelID = itemDef.modelID;
		modelOffset1 = itemDef.modelOffset1;
		modelRotation2 = itemDef.modelRotation2;
		modelOffsetY = itemDef.modelOffsetY;
		modelZoom = itemDef.modelZoom;
		modelRotation1 = itemDef.modelRotation1;
		modelOffsetX = itemDef.modelOffsetX;
		value = 0;
		ItemDefinition definition = get(lendID);
		anInt166 = definition.anInt166;
		originalModelColors = definition.originalModelColors;
		maleWearId3 = definition.maleWearId3;
		femaleWearId = definition.femaleWearId;
		anInt173 = definition.anInt173;
		maleDialogue = definition.maleDialogue;
		groundActions = definition.groundActions;
		maleWearId = definition.maleWearId;
		name = definition.name;
		maleWearId2 = definition.maleWearId2;
		membersObject = definition.membersObject;
		femaleDialogue = definition.femaleDialogue;
		femaleWearId2 = definition.femaleWearId2;
		femaleWearId3 = definition.femaleWearId3;
		modifiedModelColors = definition.modifiedModelColors;
		team = definition.team;

		if (definition.actions != null) {
			for (int i = 0; i < 4; i++) {
				actions[i] = definition.actions[i];
			}
		}

		actions[4] = "Discard";
	}

	private void toNote() {
		ItemDefinition definition = get(certTemplateID);
		modelID = definition.modelID;
		modelZoom = definition.modelZoom;
		modelRotation1 = definition.modelRotation1;
		modelRotation2 = definition.modelRotation2;
		modelOffsetX = definition.modelOffsetX;
		modelOffset1 = definition.modelOffset1;
		modelOffsetY = definition.modelOffsetY;
		modifiedModelColors = definition.modifiedModelColors;
		originalModelColors = definition.originalModelColors;
		definition = get(certID);
		name = definition.name;
		membersObject = definition.membersObject;
		value = definition.value;
		String s = "a";
		char c = definition.name.charAt(0);

		if (c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U') {
			s = "an";
		}

		description = ("Swap this note at any bank for " + s + " " + definition.name + ".").getBytes();
		stackable = true;
	}

}