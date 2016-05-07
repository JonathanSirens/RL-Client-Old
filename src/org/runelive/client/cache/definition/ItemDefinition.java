package org.runelive.client.cache.definition;

import java.io.File;
import java.io.FileOutputStream;

import org.runelive.client.Client;
import org.runelive.client.RandomColor;
import org.runelive.client.List;
import org.runelive.client.cache.Archive;
import org.runelive.client.graphics.DrawingArea;
import org.runelive.client.graphics.Sprite;
import org.runelive.client.io.ByteBuffer;
import org.runelive.client.world.Model;
import org.runelive.client.Signlink;
import org.runelive.client.world.Rasterizer;

public final class ItemDefinition {

	private static final int[] BLACK_FIX = { 13101, 13672, 13675, 6568, 10636,
			12158, 12159, 12160, 12161, 12162, 12163, 12164, 12165, 12166,
			12167, 12168, 12527, 18017, 18018, 18019, 18020, 3140, 13481,
			14479, 14481, 19337, 19342, 21077, 21047 };
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
							byte abyte[] = Client.instance.decompressors[1].decompress(ids);
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
		/*	if (Hardcode.readOSRSItem(itemDef)) {
			if (!itemDef.name.contains("hat") && !itemDef.name.contains("boot") && !itemDef.name.contains("cape")) {
				itemDef.maleWieldY += 8;
				itemDef.femaleWieldY += 8;
			}

			if (itemDef.name.contains("hat")) {
				itemDef.maleWieldZ = 5;
				itemDef.femaleWieldZ = 5;
			}

		} else {
			itemDef.readValues(buffer);
		}*/
		itemDef.readValues(buffer);
		if (itemDef.modifiedModelColors != null) {
			for (int i2 = 0; i2 < itemDef.modifiedModelColors.length; i2++) {
				if (itemDef.originalModelColors[i2] == 0) {
					itemDef.originalModelColors[i2] = 1;
				}
			}
		}

		for (int a : BLACK_FIX) {
			if (itemDef.id == a) {
				itemDef.modifiedModelColors = new int[1];
				itemDef.originalModelColors = new int[1];
				itemDef.modifiedModelColors[0] = 0;
				itemDef.originalModelColors[0] = 1;
			}
		}

		int customId = itemDef.id;

		if (customId >= 13700 && customId <= 13709) {
			/*
			 * final ItemDefinition stat = get(14876); definition.name = "Tier "
			 * + (1 + (customId - 13700)) + " Emblem"; definition.actions =
			 * stat.actions.clone(); //definition.modifiedModelColors =
			 * stat.modifiedModelColors.clone();
			 * //definition.originalModelColors =
			 * stat.originalModelColors.clone(); definition.modelID =
			 * stat.modelID; definition.modelOffset1 = stat.modelOffset1;
			 * definition.modelOffsetY = stat.modelOffsetY;
			 * definition.modelRotation1 = stat.modelRotation1;
			 * definition.modelRotation2 = stat.modelRotation2;
			 * definition.groundActions = stat.groundActions; definition.value =
			 * stat.value; definition.modelZoom = stat.modelZoom;
			 * definition.certID = -1; definition.certTemplateID = -1;
			 * definition.stackable = false;
			 */
			itemDef.certID = -1;
			itemDef.certTemplateID = -1;
			itemDef.stackable = false;
		}
		ItemDefinition itemDef2;
		switch (customId) {
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
		case 19670:
			itemDef.name = "Vote scroll";
			itemDef.actions = new String[5];
			itemDef.actions[4] = "Drop";
			itemDef.actions[0] = "Claim";
			itemDef.actions[2] = "Claim-All";
			break;
	case 12703:
		//itemDef.setDefaults();
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
			itemDef.name = "Drygore Longsword";
			itemDef.description2 = "A powerful sword made from the chitlin of the Kalphite King.";		
			itemDef.modelZoom = 1493;
			itemDef.modelRotation1 = 618;
			itemDef.modelRotation2 = 1086;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffsetY = -5;
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
			itemDef.name = "Off-hand Drygore Longsword";
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
			itemDef.name = "Drygore Rapier";
			itemDef.description2 = "A powerful rapier made from the chitlin of the Kalphite King.";		
			itemDef.modelZoom = 1493;
			itemDef.modelRotation1 = 618;
			itemDef.modelRotation2 = 996;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffsetY = -5;
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
			itemDef.name = "Off-hand Drygore Rapier";
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

	case 21100:
			itemDef.modelID = 67007;
			itemDef.name = "Drygore Mace";
			itemDef.description2 = "A powerful mace made from the chitlin of the Kalphite King.";		
			itemDef.modelZoom = 1493;
			itemDef.modelRotation1 = 618;
			itemDef.modelRotation2 = 996;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffsetY = -5;
			itemDef.maleWearId = 67008;
			itemDef.femaleWearId = 67009;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.actions[4] = "Drop";
	break;	

	case 21101:
			itemDef.modelID = 67011;
			itemDef.name = "Off-hand Drygore Mace";
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
		itemDef.immitate(get(6570));
		itemDef.originalModelColors = new int[] {
			1
		};
		itemDef.modifiedModelColors  = new int[] {
			40
		};
		break;
	case 12704:
		itemDef.name = "Infernal Pickaxe";
		itemDef.modelID = 29393;
		itemDef.maleWearId = 29260;
		itemDef.femaleWearId = 29260;
		itemDef.modelRotation1 = 224;
		itemDef.modelRotation2 = 1056;
		itemDef.modelZoom = 1070;
		itemDef.groundActions = new String[] { null, null, "Take", null, null };
		itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
		break;
		
	case 12706:
		itemDef.name = "Infernal axe";
		itemDef.modelID = 29395;
		itemDef.maleWearId = 29259;
		itemDef.femaleWearId = 29259;
		itemDef.modelRotation1 = 224;
		itemDef.modelRotation2 = 1056;
		itemDef.modelZoom = 1070;
		itemDef.groundActions = new String[] { null, null, "Take", null, null };
		itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
		break;

	case 12708:
		itemDef.name = "Pegasian boots";
		itemDef.modelID = 29396;
		itemDef.modelZoom = 900;
		itemDef.modelRotation1 = 165;
		itemDef.modelRotation2 = 99;
		itemDef.modelOffset1 = 3;
		itemDef.modelOffsetY =-7;
		itemDef.maleWearId = 29252;
		itemDef.femaleWearId = 29253;
		itemDef.actions = new String[5];
		itemDef.actions[1] = "Wear";
		break;

		case 12710:
		itemDef.name = "Primordial Boots";
		itemDef.modelID = 29397;
		itemDef.modelZoom = 900;
		itemDef.modelRotation1 = 165;
		itemDef.modelRotation2 = 99;
		itemDef.modelOffset1 = 3;
		itemDef.modelOffsetY =-7;
		itemDef.maleWearId = 29250;
		itemDef.femaleWearId = 29255;
		itemDef.actions = new String[5];
		itemDef.actions[1] = "Wear";
		break;

		case 12712:
		itemDef.name = "Eternal boots";
		itemDef.modelID = 29394;
		itemDef.modelZoom = 900;
		itemDef.modelRotation1 = 165;
		itemDef.modelRotation2 = 99;
		itemDef.modelOffset1 = 3;
		itemDef.modelOffsetY =-7;
		itemDef.maleWearId = 29249;
		itemDef.femaleWearId = 29254;
		itemDef.actions = new String[5];
		itemDef.actions[1] = "Wear";
		break;
		case 691:
			itemDef.name = "Proof of Kill";
			itemDef.actions = new String[] { null, null, null, null, "Drop"};
		    itemDef.description2 = "I should give this to the king to prove my loyalty.";
			break;
		case 10034:
		case 10033:
			itemDef.actions = new String[] { null, null, null, null, "Drop"};
			break;
		case 13727:
			itemDef.actions = new String[] { null, null, null, null, "Drop"};
			break;
		case 6500:
			itemDef.setDefaults();
			itemDef.immitate(get(9952));
			itemDef.name = "Charming imp";
			itemDef.stackable = false;
			//	itemDef.modelRotation1 = 85;
			//	itemDef.modelRotation2 = 1867;
			itemDef.actions = new String[] { null, null, "Check", "Config", "Drop"};
			break;
		case 11995:
			itemDef.setDefaults();
			itemDef.immitate(get(12458));
			itemDef.name = "Pet Chaos elemental";
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
			itemDef.originalModelColors = new int[] {
				21947
			};
			itemDef.modifiedModelColors  = new int[] {
				17467
			};
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
			itemDef.originalModelColors = new int[] {
				21947
			};
			itemDef.modifiedModelColors  = new int[] {
				17467
			};
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
		   itemDef.stackIDs = new int[] {21081, 21082, 21083, 21084, 0, 0, 0, 0, 0, 0};
		   itemDef.stackAmounts = new int[] {2, 3, 4, 5, 0, 0, 0, 0, 0, 0};
			itemDef.originalModelColors = new int[] {
				21947
			};
			itemDef.modifiedModelColors  = new int[] {
				17467
			};
			itemDef.stackable = true;
		break;
		case 21081:
			itemDef.setDefaults();
		   itemDef.name = "null";
		   itemDef.modelID = 76056;
		   itemDef.modelRotation1 = 512;
		   itemDef.modelRotation2 = 121;
		   itemDef.modelZoom = 1230;
		   	 itemDef.stackIDs = new int[] {21081, 21082, 21083, 21084, 0, 0, 0, 0, 0, 0};
		   itemDef.stackAmounts = new int[] {2, 3, 4, 5, 0, 0, 0, 0, 0, 0};
			itemDef.originalModelColors = new int[] {
				21947
			};
			itemDef.modifiedModelColors  = new int[] {
				17467
			};
			itemDef.stackable = true;
		break;
		case 21082:
			itemDef.setDefaults();
		   itemDef.name = "null";
		   itemDef.modelID = 76057;
		   itemDef.modelRotation1 = 512;
		   itemDef.modelRotation2 = 121;
		   itemDef.modelZoom = 1230;
			itemDef.stackIDs = new int[] {21081, 21082, 21083, 21084, 0, 0, 0, 0, 0, 0};
		   itemDef.stackAmounts = new int[] {2, 3, 4, 5, 0, 0, 0, 0, 0, 0};
			itemDef.originalModelColors = new int[] {
				21947
			};
			itemDef.modifiedModelColors  = new int[] {
				17467
			};
			itemDef.stackable = true;
		break;
		case 21083:
			itemDef.setDefaults();
		   itemDef.name = "null";
		   itemDef.modelID = 76058;
		   itemDef.modelRotation1 = 512;
		   itemDef.modelRotation2 = 202;
		   itemDef.modelZoom = 1347;
		   itemDef.stackIDs = new int[] {21081, 21082, 21083, 21084, 0, 0, 0, 0, 0, 0};
		   itemDef.stackAmounts = new int[] {2, 3, 4, 5, 0, 0, 0, 0, 0, 0};
			itemDef.originalModelColors = new int[] {
				21947
			};
			itemDef.modifiedModelColors  = new int[] {
				17467
			};
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
		   itemDef.stackIDs = new int[] {21081, 21082, 21083, 21084, 0, 0, 0, 0, 0, 0};
		   itemDef.stackAmounts = new int[] {2, 3, 4, 5, 0, 0, 0, 0, 0, 0};
			itemDef.originalModelColors = new int[] {
				21947
			};
			itemDef.modifiedModelColors  = new int[] {
				17467
			};
			itemDef.stackable = true;
		break;
		case 21085:
			itemDef.modelID = 65270;
			itemDef.name = "Completionist cape";
			itemDef.description2 = "We'd pat you on the back, but this cape would get in the way.";
			itemDef.modelZoom = 1385;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffsetY = 24;
			itemDef.modelRotation1 = 279;
			itemDef.modelRotation2 = 948;
			itemDef.maleWearId = 65297;
			itemDef.femaleWearId = 65297;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[3] = "Customise";
			itemDef.originalModelColors = new int[] {
				49950, 49950, 49950, 49950
			};
			itemDef.modifiedModelColors = new int[] {
				65214, 65200, 65186, 62995
			};
			break;
		case 21086:
			itemDef.modelID = 65270;
			itemDef.name = "Completionist cape";
			itemDef.description2 = "We'd pat you on the back, but this cape would get in the way.";
			itemDef.modelZoom = 1385;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffsetY = 24;
			itemDef.modelRotation1 = 279;
			itemDef.modelRotation2 = 948;
			itemDef.maleWearId = 65297;
			itemDef.femaleWearId = 65297;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[3] = "Customise";
			itemDef.originalModelColors = new int[] {
				10939, 10939, 10939, 10939
			};
			itemDef.modifiedModelColors = new int[] {
				65214, 65200, 65186, 62995
			};
			break;

		case 21087:
			itemDef.modelID = 65270;
			itemDef.name = "Completionist cape";
			itemDef.description2 = "We'd pat you on the back, but this cape would get in the way.";
			itemDef.modelZoom = 1385;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffsetY = 24;
			itemDef.modelRotation1 = 279;
			itemDef.modelRotation2 = 948;
			itemDef.maleWearId = 65297;
			itemDef.femaleWearId = 65297;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[3] = "Customise";
			itemDef.originalModelColors = new int[] {
				3016, 3016, 3016, 3016
			};
			itemDef.modifiedModelColors = new int[] {
				65214, 65200, 65186, 62995
			};
			break;
		case 21095:
			itemDef.modelID = 65270;
			itemDef.name = "Completionist cape";
			itemDef.description2 = "We'd pat you on the back, but this cape would get in the way.";
			itemDef.modelZoom = 1385;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffsetY = 24;
			itemDef.modelRotation1 = 279;
			itemDef.modelRotation2 = 948;
			itemDef.maleWearId = 65297;
			itemDef.femaleWearId = 65297;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[3] = "Customise";
			itemDef.originalModelColors = new int[] {
				926, 926, 926, 926
			};
			itemDef.modifiedModelColors = new int[] {
				65214, 65200, 65186, 62995
			};
			break;
		case 21099:
			itemDef.modelID = 65270;
			itemDef.name = "Completionist cape";
			itemDef.description2 = "We'd pat you on the back, but this cape would get in the way.";
			itemDef.modelZoom = 1385;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffsetY = 24;
			itemDef.modelRotation1 = 279;
			itemDef.modelRotation2 = 948;
			itemDef.maleWearId = 65297;
			itemDef.femaleWearId = 65297;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[3] = "Customise";
			itemDef.originalModelColors = new int[] {
				34503, 34503, 34503, 34503
			};
			itemDef.modifiedModelColors = new int[] {
				65214, 65200, 65186, 62995
			};
			break;
		case 21098:
			itemDef.modelID = 65270;
			itemDef.name = "Completionist cape";
			itemDef.description2 = "We'd pat you on the back, but this cape would get in the way.";
			itemDef.modelZoom = 1385;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffsetY = 24;
			itemDef.modelRotation1 = 279;
			itemDef.modelRotation2 = 948;
			itemDef.maleWearId = 65297;
			itemDef.femaleWearId = 65297;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[3] = "Customise";
			itemDef.originalModelColors = new int[] {
				22428, 22428, 22428, 22428
			};
			itemDef.modifiedModelColors = new int[] {
				65214, 65200, 65186, 62995
			};
			break;	
		case 21097:
			itemDef.modelID = 65270;
			itemDef.name = "Completionist cape";
			itemDef.description2 = "We'd pat you on the back, but this cape would get in the way.";
			itemDef.modelZoom = 1385;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffsetY = 24;
			itemDef.modelRotation1 = 279;
			itemDef.modelRotation2 = 948;
			itemDef.maleWearId = 65297;
			itemDef.femaleWearId = 65297;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[3] = "Customise";
			itemDef.originalModelColors = new int[] {
				43848, 43848, 43848, 43848
			};
			itemDef.modifiedModelColors = new int[] {
				65214, 65200, 65186, 62995
			};
			break;
		case 21096:
			itemDef.modelID = 65270;
			itemDef.name = "Completionist cape";
			itemDef.description2 = "We'd pat you on the back, but this cape would get in the way.";
			itemDef.modelZoom = 1385;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffsetY = 24;
			itemDef.modelRotation1 = 279;
			itemDef.modelRotation2 = 948;
			itemDef.maleWearId = 65297;
			itemDef.femaleWearId = 65297;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[3] = "Customise";
			itemDef.originalModelColors = new int[] {
				127, 127, 127, 127
			};
			itemDef.modifiedModelColors = new int[] {
				65214, 65200, 65186, 62995
			};
			break;
		case 21093:
			itemDef.modelID = 65270;
			itemDef.name = "Completionist cape";
			itemDef.description2 = "We'd pat you on the back, but this cape would get in the way.";
			itemDef.modelZoom = 1385;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffsetY = 24;
			itemDef.modelRotation1 = 279;
			itemDef.modelRotation2 = 948;
			itemDef.maleWearId = 65297;
			itemDef.femaleWearId = 65297;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[3] = "Customise";
			itemDef.originalModelColors = new int[] {
				10388, 10388, 10388, 10388
			};
			itemDef.modifiedModelColors = new int[] {
				65214, 65200, 65186, 62995
			};
			break;	
		case 21094:
			itemDef.modelID = 65270;
			itemDef.name = "Completionist cape";
			itemDef.description2 = "We'd pat you on the back, but this cape would get in the way.";
			itemDef.modelZoom = 1385;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffsetY = 24;
			itemDef.modelRotation1 = 279;
			itemDef.modelRotation2 = 948;
			itemDef.maleWearId = 65297;
			itemDef.femaleWearId = 65297;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[3] = "Customise";
			itemDef.originalModelColors = new int[] {
				1, 1, 1, 1
			};
			itemDef.modifiedModelColors = new int[] {
				65214, 65200, 65186, 62995
			};
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
		   itemDef.modelRotation1 = 236;
		   itemDef.modelRotation2 = 236;
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
		case 11996:
			MobDefinition kbd = MobDefinition.get(50);
			itemDef.immitate(ItemDefinition.get(17488));
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
			itemDef.immitate(ItemDefinition.get(17488));
			itemDef.name = "Pet Queen white dragon";
			itemDef.modelID = kbd2.npcModels[0];
			itemDef.anInt167 = 40;
			itemDef.anInt192 = 40;
			itemDef.anInt191 = 40;
			itemDef.modelOffset1 = 70;
			itemDef.modelOffsetY -= 60;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			itemDef.modifiedModelColors = new int[] {10502, 43906, 11140, 10378, 0, 11138, 809, 33};
			itemDef.originalModelColors = new int[] {100, 100, 226770, 100, 100, 100, 226770, 226770};
			break;	
		case 21050:
			itemDef.setDefaults();
			itemDef.name = "Attack master cape";
			itemDef.modelZoom = 2650; //Model Zoom
			itemDef.maleWearId = 53347; //Male Equip 1
			itemDef.femaleWearId = 53347; //Male Equip 2
			itemDef.modelID = 76001; //Model ID
			itemDef.modelRotation1 = 504; //Model Rotation 1
			itemDef.modelRotation2 = 1000; //Model Rotation 2
			itemDef.modelOffset1 = 5; //Model Offset 1
			itemDef.modelOffsetY = 1; //Model Offset 2
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
		break;			
		case 21051:
			itemDef.setDefaults();
			itemDef.name = "Defence master cape";
			itemDef.modelZoom = 2650; //Model Zoom
			itemDef.maleWearId = 53348; //Male Equip 1
			itemDef.femaleWearId = 53348; //Male Equip 2
			itemDef.modelID = 76003; //Model ID
			itemDef.modelRotation1 = 504; //Model Rotation 1
			itemDef.modelRotation2 = 1000; //Model Rotation 2
			itemDef.modelOffset1 = 5; //Model Offset 1
			itemDef.modelOffsetY = 1; //Model Offset 2
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
		break;
		case 21052:
			itemDef.setDefaults();
			itemDef.name = "Strength master cape";
			itemDef.modelZoom = 2650; //Model Zoom
			itemDef.maleWearId = 53349; //Male Equip 1
			itemDef.femaleWearId = 53349; //Male Equip 2
			itemDef.modelID = 76005; //Model ID
			itemDef.modelRotation1 = 504; //Model Rotation 1
			itemDef.modelRotation2 = 1000; //Model Rotation 2
			itemDef.modelOffset1 = 5; //Model Offset 1
			itemDef.modelOffsetY = 1; //Model Offset 2
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
		break;
		case 21053:
			itemDef.setDefaults();
			itemDef.name = "Const. master cape";
			itemDef.modelZoom = 2650; //Model Zoom
			itemDef.maleWearId = 53350; //Male Equip 1
			itemDef.femaleWearId = 53350; //Male Equip 2
			itemDef.modelID = 76007; //Model ID
			itemDef.modelRotation1 = 504; //Model Rotation 1
			itemDef.modelRotation2 = 1000; //Model Rotation 2
			itemDef.modelOffset1 = 5; //Model Offset 1
			itemDef.modelOffsetY = 1; //Model Offset 2
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
		break;
		case 21054:
			itemDef.setDefaults();
			itemDef.name = "Ranging master cape";
			itemDef.modelZoom = 2650; //Model Zoom
			itemDef.maleWearId = 53351; //Male Equip 1
			itemDef.femaleWearId = 53351; //Male Equip 2
			itemDef.modelID = 76009; //Model ID
			itemDef.modelRotation1 = 504; //Model Rotation 1
			itemDef.modelRotation2 = 1000; //Model Rotation 2
			itemDef.modelOffset1 = 5; //Model Offset 1
			itemDef.modelOffsetY = 1; //Model Offset 2
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
		break;	
		case 21055:
			itemDef.setDefaults();
			itemDef.name = "Prayer master cape";
			itemDef.modelZoom = 2650; //Model Zoom
			itemDef.maleWearId = 53352; //Male Equip 1
			itemDef.femaleWearId = 53352; //Male Equip 2
			itemDef.modelID = 76011; //Model ID
			itemDef.modelRotation1 = 504; //Model Rotation 1
			itemDef.modelRotation2 = 1000; //Model Rotation 2
			itemDef.modelOffset1 = 5; //Model Offset 1
			itemDef.modelOffsetY = 1; //Model Offset 2
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
		break;
		case 21056:
			itemDef.setDefaults();
			itemDef.name = "Magic master cape";
			itemDef.modelZoom = 2650; //Model Zoom
			itemDef.maleWearId = 53353; //Male Equip 1
			itemDef.femaleWearId = 53353; //Male Equip 2
			itemDef.modelID = 76013; //Model ID
			itemDef.modelRotation1 = 504; //Model Rotation 1
			itemDef.modelRotation2 = 1000; //Model Rotation 2
			itemDef.modelOffset1 = 5; //Model Offset 1
			itemDef.modelOffsetY = 1; //Model Offset 2
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
		break;
		case 21057:
			itemDef.setDefaults();
			itemDef.name = "Cooking master cape";
			itemDef.modelZoom = 2650; //Model Zoom
			itemDef.maleWearId = 53354; //Male Equip 1
			itemDef.femaleWearId = 53354; //Male Equip 2
			itemDef.modelID = 76015; //Model ID
			itemDef.modelRotation1 = 504; //Model Rotation 1
			itemDef.modelRotation2 = 1000; //Model Rotation 2
			itemDef.modelOffset1 = 5; //Model Offset 1
			itemDef.modelOffsetY = 1; //Model Offset 2
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
		break;
		case 21058:
			itemDef.setDefaults();
			itemDef.name = "Woodcut. master cape";
			itemDef.modelZoom = 2650; //Model Zoom
			itemDef.maleWearId = 53355; //Male Equip 1
			itemDef.femaleWearId = 53355; //Male Equip 2
			itemDef.modelID = 76017; //Model ID
			itemDef.modelRotation1 = 504; //Model Rotation 1
			itemDef.modelRotation2 = 1000; //Model Rotation 2
			itemDef.modelOffset1 = 5; //Model Offset 1
			itemDef.modelOffsetY = 1; //Model Offset 2
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
		break;
		case 21059:
			itemDef.setDefaults();
			itemDef.name = "Fletching master cape";
			itemDef.modelZoom = 2650; //Model Zoom
			itemDef.maleWearId = 53356; //Male Equip 1
			itemDef.femaleWearId = 53356; //Male Equip 2
			itemDef.modelID = 76019; //Model ID
			itemDef.modelRotation1 = 504; //Model Rotation 1
			itemDef.modelRotation2 = 1000; //Model Rotation 2
			itemDef.modelOffset1 = 5; //Model Offset 1
			itemDef.modelOffsetY = 1; //Model Offset 2
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
		break;
		case 21060:
			itemDef.setDefaults();
			itemDef.name = "Fishing master cape";
			itemDef.modelZoom = 2650; //Model Zoom
			itemDef.maleWearId = 53357; //Male Equip 1
			itemDef.femaleWearId = 53357; //Male Equip 2
			itemDef.modelID = 76021; //Model ID
			itemDef.modelRotation1 = 504; //Model Rotation 1
			itemDef.modelRotation2 = 1000; //Model Rotation 2
			itemDef.modelOffset1 = 5; //Model Offset 1
			itemDef.modelOffsetY = 1; //Model Offset 2
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
		break;
		case 21061:
			itemDef.setDefaults();
			itemDef.name = "Firemaking master cape";
			itemDef.modelZoom = 2650; //Model Zoom
			itemDef.maleWearId = 53358; //Male Equip 1
			itemDef.femaleWearId = 53358; //Male Equip 2
			itemDef.modelID = 76023; //Model ID
			itemDef.modelRotation1 = 504; //Model Rotation 1
			itemDef.modelRotation2 = 1000; //Model Rotation 2
			itemDef.modelOffset1 = 5; //Model Offset 1
			itemDef.modelOffsetY = 1; //Model Offset 2
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
		break;
		case 21062:
			itemDef.setDefaults();
			itemDef.name = "Crafting master cape";
			itemDef.modelZoom = 2650; //Model Zoom
			itemDef.maleWearId = 53359; //Male Equip 1
			itemDef.femaleWearId = 53359; //Male Equip 2
			itemDef.modelID = 76025; //Model ID
			itemDef.modelRotation1 = 504; //Model Rotation 1
			itemDef.modelRotation2 = 1000; //Model Rotation 2
			itemDef.modelOffset1 = 5; //Model Offset 1
			itemDef.modelOffsetY = 1; //Model Offset 2
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
		break;
		case 21063:
			itemDef.setDefaults();
			itemDef.name = "Smithing master cape";
			itemDef.modelZoom = 2650; //Model Zoom
			itemDef.maleWearId = 53360; //Male Equip 1
			itemDef.femaleWearId = 53360; //Male Equip 2
			itemDef.modelID = 76027; //Model ID
			itemDef.modelRotation1 = 504; //Model Rotation 1
			itemDef.modelRotation2 = 1000; //Model Rotation 2
			itemDef.modelOffset1 = 5; //Model Offset 1
			itemDef.modelOffsetY = 1; //Model Offset 2
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
		break;
		case 21064:
			itemDef.setDefaults();
			itemDef.name = "Mining master cape";
			itemDef.modelZoom = 2650; //Model Zoom
			itemDef.maleWearId = 53361; //Male Equip 1
			itemDef.femaleWearId = 53361; //Male Equip 2
			itemDef.modelID = 76029; //Model ID
			itemDef.modelRotation1 = 504; //Model Rotation 1
			itemDef.modelRotation2 = 1000; //Model Rotation 2
			itemDef.modelOffset1 = 5; //Model Offset 1
			itemDef.modelOffsetY = 1; //Model Offset 2
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
		break;
		case 21065:
			itemDef.setDefaults();
			itemDef.name = "Herblore master cape";
			itemDef.modelZoom = 2650; //Model Zoom
			itemDef.maleWearId = 53362; //Male Equip 1
			itemDef.femaleWearId = 53362; //Male Equip 2
			itemDef.modelID = 76031; //Model ID
			itemDef.modelRotation1 = 504; //Model Rotation 1
			itemDef.modelRotation2 = 1000; //Model Rotation 2
			itemDef.modelOffset1 = 5; //Model Offset 1
			itemDef.modelOffsetY = 1; //Model Offset 2
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
		break;
		case 21066:
			itemDef.setDefaults();
			itemDef.name = "Agility master cape";
			itemDef.modelZoom = 2650; //Model Zoom
			itemDef.maleWearId = 53363; //Male Equip 1
			itemDef.femaleWearId = 53363; //Male Equip 2
			itemDef.modelID = 76033; //Model ID
			itemDef.modelRotation1 = 504; //Model Rotation 1
			itemDef.modelRotation2 = 1000; //Model Rotation 2
			itemDef.modelOffset1 = 5; //Model Offset 1
			itemDef.modelOffsetY = 1; //Model Offset 2
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
		break;
		case 21067:
			itemDef.setDefaults();
			itemDef.name = "Thieving master cape";
			itemDef.modelZoom = 2650; //Model Zoom
			itemDef.maleWearId = 53364; //Male Equip 1
			itemDef.femaleWearId = 53364; //Male Equip 2
			itemDef.modelID = 76035; //Model ID
			itemDef.modelRotation1 = 504; //Model Rotation 1
			itemDef.modelRotation2 = 1000; //Model Rotation 2
			itemDef.modelOffset1 = 5; //Model Offset 1
			itemDef.modelOffsetY = 1; //Model Offset 2
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
		break;
		case 21068:
			itemDef.setDefaults();
			itemDef.name = "Slayer master cape";
			itemDef.modelZoom = 2650; //Model Zoom
			itemDef.maleWearId = 53365; //Male Equip 1
			itemDef.femaleWearId = 53365; //Male Equip 2
			itemDef.modelID = 76037; //Model ID
			itemDef.modelRotation1 = 504; //Model Rotation 1
			itemDef.modelRotation2 = 1000; //Model Rotation 2
			itemDef.modelOffset1 = 5; //Model Offset 1
			itemDef.modelOffsetY = 1; //Model Offset 2
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
		break;
		case 21069:
			itemDef.setDefaults();
			itemDef.name = "Farming master cape";
			itemDef.modelZoom = 2650; //Model Zoom
			itemDef.maleWearId = 53366; //Male Equip 1
			itemDef.femaleWearId = 53366; //Male Equip 2
			itemDef.modelID = 76039; //Model ID
			itemDef.modelRotation1 = 504; //Model Rotation 1
			itemDef.modelRotation2 = 1000; //Model Rotation 2
			itemDef.modelOffset1 = 5; //Model Offset 1
			itemDef.modelOffsetY = 1; //Model Offset 2
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
		break;
		case 21070:
			itemDef.setDefaults();
			itemDef.name = "Runecraft. master cape";
			itemDef.modelZoom = 2650; //Model Zoom
			itemDef.maleWearId = 53367; //Male Equip 1
			itemDef.femaleWearId = 53367; //Male Equip 2
			itemDef.modelID = 76041; //Model ID
			itemDef.modelRotation1 = 504; //Model Rotation 1
			itemDef.modelRotation2 = 1000; //Model Rotation 2
			itemDef.modelOffset1 = 5; //Model Offset 1
			itemDef.modelOffsetY = 1; //Model Offset 2
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
		break;
		case 21071:
			itemDef.setDefaults();
			itemDef.name = "Constr. master cape";
			itemDef.modelZoom = 2650; //Model Zoom
			itemDef.maleWearId = 53368; //Male Equip 1
			itemDef.femaleWearId = 53368; //Male Equip 2
			itemDef.modelID = 76043; //Model ID
			itemDef.modelRotation1 = 504; //Model Rotation 1
			itemDef.modelRotation2 = 1000; //Model Rotation 2
			itemDef.modelOffset1 = 5; //Model Offset 1
			itemDef.modelOffsetY = 1; //Model Offset 2
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
		break;
		case 21072:
			itemDef.setDefaults();
			itemDef.name = "Hunter master cape";
			itemDef.modelZoom = 2650; //Model Zoom
			itemDef.maleWearId = 53369; //Male Equip 1
			itemDef.femaleWearId = 53369; //Male Equip 2
			itemDef.modelID = 76045; //Model ID
			itemDef.modelRotation1 = 504; //Model Rotation 1
			itemDef.modelRotation2 = 1000; //Model Rotation 2
			itemDef.modelOffset1 = 5; //Model Offset 1
			itemDef.modelOffsetY = 1; //Model Offset 2
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
		break;
		case 21073:
			itemDef.setDefaults();
			itemDef.name = "Summoning master cape";
			itemDef.modelZoom = 2650; //Model Zoom
			itemDef.maleWearId = 53370; //Male Equip 1
			itemDef.femaleWearId = 53370; //Male Equip 2
			itemDef.modelID = 76047; //Model ID
			itemDef.modelRotation1 = 504; //Model Rotation 1
			itemDef.modelRotation2 = 1000; //Model Rotation 2
			itemDef.modelOffset1 = 5; //Model Offset 1
			itemDef.modelOffsetY = 1; //Model Offset 2
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
		break;
			case 13001:
				itemDef.setDefaults();
				itemDef.immitate(get(12458));
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
		case 13002:
			itemDef.setDefaults();
			itemDef.immitate(get(12458));
			itemDef.name = "Pet Rock crab";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			itemDef.stackable = false;
			break;
		case 13003:
			itemDef.setDefaults();
			itemDef.immitate(get(12458));
			itemDef.name = "Pet Rock crab";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			itemDef.stackable = false;
			break;
		case 13004:
			itemDef.setDefaults();
			itemDef.immitate(get(12458));
			itemDef.name = "Pet Rock crab";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			itemDef.stackable = false;
			break;	
		case 13005:
			itemDef.setDefaults();
			itemDef.immitate(get(12458));
			itemDef.name = "Pet Rock crab";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			itemDef.stackable = false;
			break;	
		case 13006:
			itemDef.setDefaults();
			itemDef.immitate(get(12458));
			itemDef.name = "Pet Rock crab";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			itemDef.stackable = false;
			break;
		case 13007:
			itemDef.setDefaults();
			itemDef.immitate(get(12458));
			itemDef.name = "Pet Rock crab";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			itemDef.stackable = false;
			break;	
		case 13008:
			itemDef.setDefaults();
			itemDef.immitate(get(12458));
			itemDef.name = "Pet Rock crab";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			itemDef.stackable = false;
			break;
		case 11997:
			itemDef.setDefaults();
			itemDef.immitate(get(12458));
			itemDef.name = "Pet General graardor";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			break;
		case 11978:
			itemDef.setDefaults();
			itemDef.immitate(get(12458));
			itemDef.name = "Pet TzTok-Jad";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			break;
		case 12001:
			itemDef.setDefaults();
			itemDef.immitate(get(12458));
			itemDef.name = "Pet Corporeal beast";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			break;
		case 12002:
			itemDef.setDefaults();
			itemDef.immitate(get(12458));
			itemDef.name = "Pet Kree'arra";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			break;
		case 12003:
			itemDef.setDefaults();
			itemDef.immitate(get(12458));
			itemDef.name = "Pet K'ril tsutsaroth";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			break;
		case 12004:
			itemDef.setDefaults();
			itemDef.immitate(get(12458));
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
			itemDef.immitate(get(20935));
			itemDef.name = "Vote Reward Book";
			itemDef.actions = new String[] { "Claim", null, null, null, "Drop" };
			itemDef.stackable = true;
			break;
		case 12005:
			itemDef.setDefaults();
			itemDef.immitate(get(12458));
			itemDef.name = "Pet Dagannoth supreme";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			break;
		case 12006:
			itemDef.setDefaults();
			itemDef.immitate(get(12458));
			itemDef.name = "Pet Dagannoth prime";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			break;
		case 11990:
			itemDef.setDefaults();
			itemDef.immitate(get(12458));
			itemDef.name = "Pet Dagannoth rex";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			break;
		case 11991:
			itemDef.setDefaults();
			itemDef.immitate(get(12458));
			itemDef.name = "Pet Frost dragon";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			break;
		case 11992:
			itemDef.setDefaults();
			itemDef.immitate(get(12458));
			itemDef.name = "Pet Tormented demon";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			break;
		case 11993:
			itemDef.setDefaults();
			itemDef.immitate(get(12458));
			itemDef.name = "Pet Kalphite queen";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			break;
		case 11994:
			itemDef.setDefaults();
			itemDef.immitate(get(12458));
			itemDef.name = "Pet Slash bash";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			break;
		case 11989:
			itemDef.setDefaults();
			itemDef.immitate(get(12458));
			itemDef.name = "Pet Phoenix";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			break;
		case 11988:
			itemDef.setDefaults();
			itemDef.immitate(get(12458));
			itemDef.name = "Pet Bandos avatar";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			break;
		case 11987:
			itemDef.setDefaults();
			itemDef.immitate(get(12458));
			itemDef.name = "Pet Nex";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			break;
		case 11986:
			itemDef.setDefaults();
			itemDef.immitate(get(12458));
			itemDef.name = "Pet Jungle strykewyrm";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			break;
		case 11985:
			itemDef.setDefaults();
			itemDef.immitate(get(12458));
			itemDef.name = "Pet Desert strykewyrm";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			break;
		case 11984:
			itemDef.setDefaults();
			itemDef.immitate(get(12458));
			itemDef.name = "Pet Ice strykewyrm";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			break;
		case 11983:
			itemDef.setDefaults();
			itemDef.immitate(get(12458));
			itemDef.name = "Pet Green dragon";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			break;
		case 11982:
			itemDef.setDefaults();
			itemDef.immitate(get(12458));
			itemDef.name = "Pet Baby blue dragon";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			break;
		case 11981:
			itemDef.setDefaults();
			itemDef.immitate(get(12458));
			itemDef.name = "Pet Blue dragon";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			break;
		case 1853:
			itemDef2 = ItemDefinition.get(10944);
			itemDef.name = "Premium Scroll";
			itemDef.description2 = "A scroll that can be claimed for Premium Donator.";
			itemDef.modifiedModelColors = new int[] { 
				9164, 6742
			};
			itemDef.originalModelColors = new int[] {
				957, 64278
			};
			itemDef.modelID = itemDef2.modelID;
			itemDef.modelOffset1 = itemDef2.modelOffset1;
			itemDef.modelOffsetX = itemDef2.modelOffsetX;
			itemDef.modelOffsetY = itemDef2.modelOffsetY;
			itemDef.modelRotation2 = itemDef2.modelRotation2;
			itemDef.modelRotation1 = itemDef2.modelRotation1;
			itemDef.modelZoom = itemDef2.modelZoom;
			itemDef.actions = new String[5];
			itemDef.actions[0] = "Claim";
			itemDef.actions[4] = "Drop";	
		break;
		case 1854:
			itemDef2 = ItemDefinition.get(10944);
			itemDef.name = "Prime Scroll";
			itemDef.description2 = "A scroll that can be claimed for Prime Donator.";
			itemDef.modifiedModelColors = new int[] { 
				9164, 6742
			};
			itemDef.originalModelColors = new int[] {
				11057, 11057
			};
			itemDef.modelID = itemDef2.modelID;
			itemDef.modelOffset1 = itemDef2.modelOffset1;
			itemDef.modelOffsetX = itemDef2.modelOffsetX;
			itemDef.modelOffsetY = itemDef2.modelOffsetY;
			itemDef.modelRotation2 = itemDef2.modelRotation2;
			itemDef.modelRotation1 = itemDef2.modelRotation1;
			itemDef.modelZoom = itemDef2.modelZoom;
			itemDef.actions = new String[5];
			itemDef.actions[0] = "Claim";
			itemDef.actions[4] = "Drop";	
		break;
		case 1855:
			itemDef2 = ItemDefinition.get(10944);
			itemDef.name = "Platinum Scroll";
			itemDef.description2 = "A scroll that can be claimed for Platinum Donator.";
			itemDef.modifiedModelColors = new int[] { 
				9164, 6742
			};
			itemDef.originalModelColors = new int[] {
				95, 95
			};
			itemDef.modelID = itemDef2.modelID;
			itemDef.modelOffset1 = itemDef2.modelOffset1;
			itemDef.modelOffsetX = itemDef2.modelOffsetX;
			itemDef.modelOffsetY = itemDef2.modelOffsetY;
			itemDef.modelRotation2 = itemDef2.modelRotation2;
			itemDef.modelRotation1 = itemDef2.modelRotation1;
			itemDef.modelZoom = itemDef2.modelZoom;
			itemDef.actions = new String[5];
			itemDef.actions[0] = "Claim";
			itemDef.actions[4] = "Drop";	
		break;
		case 11979:
			itemDef.setDefaults();
			itemDef.immitate(get(12458));
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
			itemDef.name = "Wilderness Key";
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
			itemDef.actions = new String[]{"Fill", null, "Empty", "Check", null, null};
			break;
		case 11998:
			itemDef.name = "Scimitar";
			itemDef.actions = new String[]{null, null, null, null, null, null};
			break;
		case 11999:
			itemDef.name = "Scimitar";
			itemDef.actions = new String[]{null, null, null, null, null, null};
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
			itemDef.actions = new String[]{null, null, null, null, null, null};
			break;
		case 17401:
			itemDef.name = "Damaged Hammer";
			itemDef.actions = new String[]{null, null, null, null, null, null};
			break;
		case 17402:
			itemDef.name = "Damaged Hammer";
			itemDef.actions = new String[]{null, null, null, null, null, null};
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
			itemDef.name = "Gold Ring";
			itemDef.actions = new String[]{null, null, null, null, null, null};
			break;
		case 15010:
			itemDef.modelID = 2429;
			itemDef.name = "Gold Ring";
			itemDef.actions = new String[]{null, null, null, null, null, null};
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
			itemDef.actions = new String[]{"Open", null, null, null, null, null};
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
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
			itemDef.modelID = 61729;
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
			//itemDef.maleXOffset = 7;
			itemDef.maleWieldY = -7;
			itemDef.originalModelColors = new int[] { 17350, 17350, 0, 17350, 17350, 17350 };
			itemDef.modifiedModelColors  = new int[] { 56428, 55404, 30338, 399, 16319};
			break;
		case 21088: 
			itemDef.groundActions = new String[] { null, null, "Take" };
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
			itemDef.name = "Disco Hat";
			itemDef.description = "A colorfull hat.".getBytes();
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
			itemDef.modifiedModelColors  = new int[] { 56428, 55404, 30338, 399, 16319};
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
			//itemDef.maleXOffset = 7;
			itemDef.maleWieldY = -7;
			itemDef.originalModelColors = new int[] { 123770, 123770, 0, 123770, 123770 };
			itemDef.modifiedModelColors  = new int[] { 56428, 55404, 30338, 399, 16319};
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
			//itemDef.maleXOffset = 7;
			itemDef.maleWieldY = -7;
			itemDef.originalModelColors = new int[] { 933, 933, 0, 933, 933 };
			itemDef.modifiedModelColors  = new int[] { 56428, 55404, 30338, 399, 16319};
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
			//itemDef.maleXOffset = 7;
			itemDef.maleWieldY = -7;
			itemDef.originalModelColors = new int[] { 100, 100, 0, 100, 100 };
			itemDef.modifiedModelColors  = new int[] { 56428, 55404, 30338, 399, 16319};
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
			//itemDef.maleXOffset = 7;
			itemDef.maleWieldY = -7;
			itemDef.originalModelColors = new int[] { 11200, 11200, 0, 11200, 11200 };
			itemDef.modifiedModelColors  = new int[] { 56428, 55404, 30338, 399, 16319};
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
			//itemDef.maleXOffset = 7;
			itemDef.maleWieldY = -7;
			itemDef.originalModelColors = new int[] { 461770, 461770, 0, 461770, 461770 };
			itemDef.modifiedModelColors  = new int[] { 56428, 55404, 30338, 399, 16319};
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
			//itemDef.maleXOffset = 7;
			itemDef.maleWieldY = -7;
			itemDef.originalModelColors = new int[] { 49950, 49950, 0, 49950, 49950 };
			itemDef.modifiedModelColors  = new int[] { 56428, 55404, 30338, 399, 16319};
			break;
		case 21008:
			itemDef.setDefaults();
			itemDef.immitate(get(11732));
			itemDef.originalModelColors = new int[] {17350};
			itemDef.modifiedModelColors  = new int[] {926};
			break;
		case 21009:
			itemDef.setDefaults();
			itemDef.immitate(get(11732));
			itemDef.originalModelColors = new int[] {226770};
			itemDef.modifiedModelColors  = new int[] {926};
			break;
		case 21010:
			itemDef.setDefaults();
			itemDef.immitate(get(11732));
			itemDef.originalModelColors = new int[] {123770};
			itemDef.modifiedModelColors  = new int[] {926};
			break;
		case 11732:
			itemDef.immitate(get(11732));
			itemDef.originalModelColors = new int[] {933};
			itemDef.modifiedModelColors  = new int[] {926};
			break;
		case 21012:
			itemDef.setDefaults();
			itemDef.immitate(get(11732));
			itemDef.originalModelColors = new int[] {100};
			itemDef.modifiedModelColors  = new int[] {926};
			break;
		case 21013:
			itemDef.setDefaults();
			itemDef.immitate(get(11732));
			itemDef.originalModelColors = new int[] {76770};
			itemDef.modifiedModelColors  = new int[] {926};
			break;
		case 21014:
			itemDef.setDefaults();
			itemDef.immitate(get(11732));
			itemDef.originalModelColors = new int[] {461770};
			itemDef.modifiedModelColors  = new int[] {926};
			break;
		case 21015:
			itemDef.setDefaults();
			itemDef.immitate(get(11732));
			itemDef.originalModelColors = new int[] {49950};
			itemDef.modifiedModelColors  = new int[] {926};
			break;
		case 21042:
			itemDef.setDefaults();
			itemDef.immitate(get(11732));
			itemDef.originalModelColors = new int[] {6020};
			itemDef.modifiedModelColors  = new int[] {926};
			break;
		case 21016:
			itemDef.setDefaults();
			itemDef.immitate(get(15702));
			itemDef.originalModelColors = new int[] {
				17350, 17350, 17350, 17350, 17350,
				17350, 17350, 17350, 17350, 17350,
				17350, 17350, 17350, 17350
			};
			itemDef.modifiedModelColors  = new int[] {
				36613, 3974, 36616, 36621, 36626,
				36638, 36652, 6808, 8067, 8072,
				36620, 36627, 36639, 36652
			};
			break;
		case 21017:
			itemDef.setDefaults();
			itemDef.immitate(get(15702));
			itemDef.originalModelColors = new int[] {
				226770, 226770, 226770, 226770, 226770,
				226770, 226770, 226770, 226770, 226770,
				226770, 226770, 226770, 226770
			};
			itemDef.modifiedModelColors  = new int[] {
				36613, 3974, 36616, 36621, 36626,
				36638, 36652, 6808, 8067, 8072,
				36620, 36627, 36639, 36652
			};
			break;
		case 21018:
			itemDef.setDefaults();
			itemDef.immitate(get(15702));
			itemDef.originalModelColors = new int[] {
				123770, 123770, 123770, 123770, 123770,
				123770, 123770, 123770, 123770, 123770,
				123770, 123770, 123770, 123770
			};
			itemDef.modifiedModelColors  = new int[] {
				36613, 3974, 36616, 36621, 36626,
				36638, 36652, 6808, 8067, 8072,
				36620, 36627, 36639, 36652
			};
			break;
		case 21019:
			itemDef.setDefaults();
			itemDef.immitate(get(15702));
			itemDef.originalModelColors = new int[] {
				933, 933, 933, 933, 933,
				933, 933, 933, 933, 933,
				933, 933, 933, 933
			};
			itemDef.modifiedModelColors  = new int[] {
				36613, 3974, 36616, 36621, 36626,
				36638, 36652, 6808, 8067, 8072,
				36620, 36627, 36639, 36652
			};
			break;
		case 21020:
			itemDef.setDefaults();
			itemDef.immitate(get(15702));
			itemDef.originalModelColors = new int[] {
				100, 100, 100, 100, 100,
				100, 100, 100, 100, 100,
				100, 100, 100, 100
			};
			itemDef.modifiedModelColors  = new int[] {
				36613, 3974, 36616, 36621, 36626,
				36638, 36652, 6808, 8067, 8072,
				36620, 36627, 36639, 36652
			};
			break;
		case 21021:
			itemDef.setDefaults();
			itemDef.immitate(get(15702));
			itemDef.originalModelColors = new int[] {
				76770, 76770, 76770, 76770, 76770,
				76770, 76770, 76770, 76770, 76770,
				76770, 76770, 76770, 76770
			};
			itemDef.modifiedModelColors  = new int[] {
				36613, 3974, 36616, 36621, 36626,
				36638, 36652, 6808, 8067, 8072,
				36620, 36627, 36639, 36652
			};
			break;
		case 21022:
			itemDef.setDefaults();
			itemDef.immitate(get(15702));
			itemDef.originalModelColors = new int[] {
				461770, 461770, 461770, 461770, 461770,
				461770, 461770, 461770, 461770, 461770,
				461770, 461770, 461770, 461770
			};
			itemDef.modifiedModelColors  = new int[] {
				36613, 3974, 36616, 36621, 36626,
				36638, 36652, 6808, 8067, 8072,
				36620, 36627, 36639, 36652
			};
			break;
		case 21023:
			itemDef.setDefaults();
			itemDef.immitate(get(15702));
			itemDef.originalModelColors = new int[] {
				49950, 49950, 49950, 49950, 49950,
				49950, 49950, 49950, 49950, 49950,
				49950, 49950, 49950, 49950
			};
			itemDef.modifiedModelColors  = new int[] {
				36613, 3974, 36616, 36621, 36626,
				36638, 36652, 6808, 8067, 8072,
				36620, 36627, 36639, 36652
			};
			break;
		case 21024:
			itemDef.setDefaults();
			itemDef.immitate(get(1038));
			itemDef.originalModelColors = new int[] {
				226770
			};
			itemDef.modifiedModelColors  = new int[] {
				926
			};
			itemDef.name = "Aqua partyhat";
			break;
		case 21025:
			itemDef.setDefaults();
			itemDef.immitate(get(1038));
			itemDef.originalModelColors = new int[] {
				123770
			};
			itemDef.modifiedModelColors  = new int[] {
				926
			};
			itemDef.name = "Pink partyhat";
			break;
		case 21026:
			itemDef.setDefaults();
			itemDef.immitate(get(1038));
			itemDef.originalModelColors = new int[] {
				461770
			};
			itemDef.modifiedModelColors  = new int[] {
				926
			};
			itemDef.name = "Lava partyhat";
			break;
		case 21027:
			itemDef.setDefaults();
			itemDef.immitate(get(1053));
			itemDef.originalModelColors = new int[] {
				17350
			};
			itemDef.modifiedModelColors  = new int[] {
				926
			};
			itemDef.name = "Lime halloween mask";
			break;
		case 21028:
			itemDef.setDefaults();
			itemDef.immitate(get(1053));
			itemDef.originalModelColors = new int[] {
				226770
			};
			itemDef.modifiedModelColors  = new int[] {
				926
			};
			itemDef.name = "Aqua halloween mask";
			break;
		case 21029:
			itemDef.setDefaults();
			itemDef.immitate(get(1053));
			itemDef.originalModelColors = new int[] {
				123770
			};
			itemDef.modifiedModelColors  = new int[] {
				926
			};
			itemDef.name = "Pink halloween mask";
			break;
		case 21030:
			itemDef.setDefaults();
			itemDef.immitate(get(1053));
			itemDef.originalModelColors = new int[] {
				100
			};
			itemDef.modifiedModelColors  = new int[] {
				926
			};
			itemDef.name = "White halloween mask";
			break;
		case 21031:
			itemDef.setDefaults();
			itemDef.immitate(get(1053));
			itemDef.originalModelColors = new int[] {
				11200
			};
			itemDef.modifiedModelColors  = new int[] {
				926
			};
			itemDef.name = "Yellow halloween mask";
			break;
		case 21032:
			itemDef.setDefaults();
			itemDef.immitate(get(1053));
			itemDef.originalModelColors = new int[] {
				461770
			};
			itemDef.modifiedModelColors  = new int[] {
				926
			};
			itemDef.name = "Lava halloween mask";
			break;
		case 21033:
			itemDef.setDefaults();
			itemDef.immitate(get(1053));
			itemDef.originalModelColors = new int[] {
				49950
			};
			itemDef.modifiedModelColors  = new int[] {
				926
			};
			itemDef.name = "Purple halloween mask";
			break;
		case 21034:
			itemDef.setDefaults();
			itemDef.immitate(get(1053));
			itemDef.originalModelColors = new int[] {
				6020
			};
			itemDef.modifiedModelColors  = new int[] {
				926
			};
			itemDef.name = "Black halloween mask";
			break;
		case 21035:
			itemDef.setDefaults();
			itemDef.immitate(get(1050));
			itemDef.originalModelColors = new int[] {
				17350
			};
			itemDef.modifiedModelColors  = new int[] {
				933
			};
			itemDef.name = "Lime santa hat";
			break;
		case 21036:
			itemDef.setDefaults();
			itemDef.immitate(get(1050));
			itemDef.originalModelColors = new int[] {
				226770
			};
			itemDef.modifiedModelColors  = new int[] {
				933
			};
			itemDef.name = "Aqua santa hat";
			break;
		case 21037:
			itemDef.setDefaults();
			itemDef.immitate(get(1050));
			itemDef.originalModelColors = new int[] {
				123770
			};
			itemDef.modifiedModelColors  = new int[] {
				933
			};
			itemDef.name = "Pink santa hat";
			break;
		case 21038:
			itemDef.setDefaults();
			itemDef.immitate(get(1050));
			itemDef.originalModelColors = new int[] {
				100
			};
			itemDef.modifiedModelColors  = new int[] {
				933
			};
			itemDef.name = "White santa hat";
			break;
		case 21039:
			itemDef.setDefaults();
			itemDef.immitate(get(1050));
			itemDef.originalModelColors = new int[] {
				11200
			};
			itemDef.modifiedModelColors  = new int[] {
				933
			};
			itemDef.name = "Yellow santa hat";
			break;
		case 21040:
			itemDef.setDefaults();
			itemDef.immitate(get(1050));
			itemDef.originalModelColors = new int[] {
				461770
			};
			itemDef.modifiedModelColors  = new int[] {
				933
			};
			itemDef.name = "Lava santa hat";
			break;
		case 21041:
			itemDef.setDefaults();
			itemDef.immitate(get(1050));
			itemDef.originalModelColors = new int[] {
				49950
			};
			itemDef.modifiedModelColors  = new int[] {
				933
			};
			itemDef.name = "Purple santa hat";
			break;
		case 21048:
			itemDef.setDefaults();
			itemDef.immitate(get(1050));
			itemDef.originalModelColors = new int[] {
				6020
			};
			itemDef.modifiedModelColors  = new int[] {
				933
			};
			itemDef.name = "Black santa hat";
			break;
		case 21049:
			itemDef.setDefaults();
			itemDef.immitate(get(1038));
			itemDef.originalModelColors = new int[] {
				6020
			};
			itemDef.modifiedModelColors  = new int[] {
				926
			};
			itemDef.name = "Black partyhat";
			break;
		case 21043:
			itemDef.setDefaults();
			itemDef.immitate(get(1747));
			itemDef.modifiedModelColors = new int[] {
				22416, 22424
			};
			itemDef.originalModelColors  = new int[] {
				100, 226770
			};
			itemDef.name = "White dragonhide";
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
				itemDef.immitate(get(9976));
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
				break;
			case 11976:
				itemDef.name = "Seagull";
				itemDef.immitate(get(12458));
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
				break;
		case 21044:
			itemDef.setDefaults();
			itemDef.immitate(get(2503));
			itemDef.modifiedModelColors = new int[] {
				22416, 22424
			};
			itemDef.originalModelColors  = new int[] {
				100, 226770
			};
			itemDef.name = "White d'hide body";
			break;
		case 21045:
			itemDef.setDefaults();
			itemDef.immitate(get(2497));
			itemDef.modifiedModelColors = new int[] {
				22416, 22424, 7566, 57, 61, 1821, 5907, 21898, 20884, 926, 5012
			};
			itemDef.originalModelColors  = new int[] {
				100, 226770, 100, 100, 100, 100, 226770, 226770, 226770, 226770, 100
			};
			itemDef.name = "White d'hide chaps";
			break;
		case 21046:
			itemDef.setDefaults();
			itemDef.immitate(get(2491));
			itemDef.modifiedModelColors = new int[] {
				8472, 8720, 22416
			};
			itemDef.originalModelColors  = new int[] {
				100, 226770, 100
			};
			itemDef.name = "White d'hide vamb";
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
			itemDef.modelID = 61812;
			break;
		case 14189:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Super attack flask (6)";
			itemDef.actions = new String[]{null, null, null, null, null, null};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
			itemDef.modelID = 61732;
			break;
		case 14187:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Super attack flask (5)";
			itemDef.actions = new String[]{null, null, null, null, null, null};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
			itemDef.modelID = 61729;
			break;
		case 14185:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Super attack flask (4)";
			itemDef.actions = new String[]{null, null, null, null, null, null};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
			itemDef.modelID = 61764;
			break;
		case 14183:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Super attack flask (3)";
			itemDef.actions = new String[]{null, null, null, null, null, null};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
			itemDef.modelID = 61727;
			break;
		case 14181:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Super attack flask (2)";
			itemDef.actions = new String[]{null, null, null, null, null, null};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
			itemDef.modelID = 61731;
			break;
		case 14179:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Super attack flask (1)";
			itemDef.actions = new String[]{null, null, null, null, null, null};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
			itemDef.modelID = 61812;
			break;
		case 14177:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Super strength flask (6)";
			itemDef.actions = new String[]{null, null, null, null, null, null};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
			itemDef.modelID = 61732;
			break;
		case 14175:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Super strength flask (5)";
			itemDef.actions = new String[]{null, null, null, null, null, null};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
			itemDef.modelID = 61729;
			break;
		case 14173:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Super strength flask (4)";
			itemDef.actions = new String[]{null, null, null, null, null, null};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
			itemDef.modelID = 61764;
			break;
		case 14171:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Super strength flask (3)";
			itemDef.actions = new String[]{null, null, null, null, null, null};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
			itemDef.modelID = 61727;
			break;
		case 14169:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Super strength flask (2)";
			itemDef.actions = new String[]{null, null, null, null, null, null};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
			itemDef.modelID = 61731;
			break;
		case 14167:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Super strength flask (1)";
			itemDef.actions = new String[]{null, null, null, null, null, null};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
			itemDef.modelID = 61812;
			break;
		case 14153:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Ranging flask (6)";
			itemDef.actions = new String[]{null, null, null, null, null, null};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
			itemDef.modelID = 61732;
			break;
		case 14151:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Ranging flask (5)";
			itemDef.actions = new String[]{null, null, null, null, null, null};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
			itemDef.modelID = 61729;
			break;
		case 14149:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Ranging flask (4)";
			itemDef.actions = new String[]{null, null, null, null, null, null};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
			itemDef.groundActions[2] = "Take";
			itemDef.modelID = 61764;
			break;
		case 14147:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Ranging flask (3)";
			itemDef.actions = new String[]{null, null, null, null, null, null};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
			itemDef.modelID = 61727;
			break;
		case 14145:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Ranging flask (2)";
			itemDef.actions = new String[]{null, null, null, null, null, null};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
			itemDef.modelID = 61731;
			break;
		case 14143:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Ranging flask (1)";
			itemDef.actions = new String[]{null, null, null, null, null, null};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
			itemDef.modelID = 61729;
			break;
		case 7629:
			itemDef.setDefaults();
			itemDef.immitate(get(761));
			itemDef.actions = new String[5];
			itemDef.actions[0] = "Redeem";
			itemDef.actions[4] = "Drop";	
			itemDef.description = "This scroll can be redeemed for a $125 RuneLive payment. [137,500 credits]".getBytes();
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
			itemDef.modelID = 61812;
			break;
		case 14428:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Saradomin brew flask (6)";
			itemDef.actions = new String[]{null, null, null, null, null, null};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
			itemDef.modelID = 61732;
			break;
		case 14426:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Saradomin brew flask (5)";
			itemDef.actions = new String[]{null, null, null, null, null, null};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
			itemDef.modelID = 61729;
			/*	itemDef.anInt196 = 40;
			itemDef.anInt184 = 200;*/
			break;
		case 14424:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Saradomin brew flask (4)";
			itemDef.actions = new String[]{null, null, null, null, null, null};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
			itemDef.modelID = 61764;
			/*	itemDef.anInt196 = 40;
			itemDef.anInt184 = 200;*/
			break;
		case 14422:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Saradomin brew flask (3)";
			itemDef.actions = new String[]{null, null, null, null, null, null};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
			itemDef.modelID = 61727;
			/*	itemDef.anInt196 = 40;
			itemDef.anInt184 = 200;*/
			break;
		case 14420:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Saradomin brew flask (2)";
			itemDef.actions = new String[]{null, null, null, null, null, null};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
			itemDef.modelID = 61731;
			/*	itemDef.anInt196 = 40;
			itemDef.anInt184 = 200;*/
			break;
		case 14418:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Saradomin brew flask (1)";
			itemDef.actions = new String[]{null, null, null, null, null, null};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
			itemDef.modelID = 61812;
			/*	itemDef.anInt196 = 40;
			itemDef.anInt184 = 200;*/
			break;
		case 14416:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Super restore flask (6)";
			itemDef.actions = new String[]{null, null, null, null, null, null};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
			itemDef.modelID = 61732;
			break;
		case 14414:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Super restore flask (5)";
			itemDef.actions = new String[]{null, null, null, null, null, null};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
			itemDef.modelID = 61729;
			break;
		case 14412:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Super restore flask (4)";
			itemDef.actions = new String[]{null, null, null, null, null, null};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
			itemDef.modelID = 61764;
			break;
		case 14410:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Super restore flask (3)";
			itemDef.actions = new String[]{null, null, null, null, null, null};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
			itemDef.modelID = 61727;
			break;
		case 14408:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Super restore flask (2)";
			itemDef.actions = new String[]{null, null, null, null, null, null};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
			itemDef.modelID = 61731;
			break;
		case 14406:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Super restore flask (1)";
			itemDef.actions = new String[]{null, null, null, null, null, null};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
			itemDef.modelID = 61812;
			break;
		case 14404:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Magic flask (6)";
			itemDef.actions = new String[]{null, null, null, null, null, null};
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
			itemDef.originalModelColors = new int[] { 37440 };
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
			itemDef.modelID = 61732;
			break;
		case 14402:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Magic flask (5)";
			itemDef.actions = new String[]{null, null, null, null, null, null};
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
			itemDef.originalModelColors = new int[] { 37440 };
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
			itemDef.modelID = 61729;
			break;
		case 14400:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Magic flask (4)";
			itemDef.actions = new String[]{null, null, null, null, null, null};
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
			itemDef.originalModelColors = new int[] { 37440 };
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
			itemDef.modelID = 61764;
			break;
		case 14398:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Magic flask (3)";
			itemDef.actions = new String[]{null, null, null, null, null, null};
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
			itemDef.originalModelColors = new int[] { 37440 };
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
			itemDef.modelID = 61727;
			break;
		case 14396:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Magic flask (2)";
			itemDef.actions = new String[]{null, null, null, null, null, null};
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
			itemDef.originalModelColors = new int[] { 37440 };
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
			itemDef.modelID = 61731;
			break;
		case 14394:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Magic flask (1)";
			itemDef.actions = new String[]{null, null, null, null, null, null};
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
			itemDef.originalModelColors = new int[] { 37440 };
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.modifiedModelColors  = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[]{"Drink", null, null, null, null, "Drop"};
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
			itemDef.actions = new String[] {"Activate", null, "Social-Slayer", null, "Destroy"};
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
			itemDef.femaleWearId= 43693;
			break;
		case 19111:
			itemDef.name ="TokHaar-Kal";
			itemDef.value = 60000;
			itemDef.maleWearId = 62575;
			itemDef.maleWearId = 62582;
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
			itemDef.setDefaults();
			itemDef.immitate(get(20072));
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.modifiedModelColors = new int[1];
			itemDef.originalModelColors = new int[1];
			itemDef.modifiedModelColors[0] = 28; // colors
			itemDef.modifiedModelColors[0] = 74; // colors
			itemDef.originalModelColors[0] = 38676; // colors
			itemDef.originalModelColors[0] = 924; // colors 
			itemDef.maleWearId = 15413;
			itemDef.femaleWearId = 15413;
			itemDef.name = "Dragon Defender";
			itemDef.description2 = "A pointy off-hand knife.";
			break;
		case 20072:
			itemDef.modifiedModelColors = new int[1];
			itemDef.originalModelColors = new int[1];
			itemDef.modifiedModelColors[0] = 28; // colors
			itemDef.modifiedModelColors[0] = 74; // colors
			itemDef.originalModelColors[0] = 38676; // colors
			itemDef.originalModelColors[0] = 924; // colors 
			itemDef.maleWearId = 15413;
			itemDef.femaleWearId = 15413;
			itemDef.name = "Dragon Defender";
			itemDef.description2 = "A pointy off-hand knife.";
			break;
		case 10934:
			itemDef.immitate(get(607));
			itemDef.name = "$25 Payment Scroll";
			itemDef.description = "This scroll can be redeemed for a $25 RuneLive payment. [27,875 credits]".getBytes();
			itemDef.actions = new String[5];
			itemDef.actions[0] = "Redeem";
			break;
		case 10935:
			itemDef.immitate(get(608));
			itemDef.name = "$50 Payment Scroll";
			itemDef.description = "This scroll can be redeemed for a $50 RuneLive payment. [55,750 credits]".getBytes();
			itemDef.actions = new String[5];
			itemDef.actions[0] = "Redeem";
		break;
		case 10943:
			itemDef.immitate(get(607));
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
			itemDef.actions = new String[] {null, "Wear", null, null, null, null};
			break;
		case 20084:
			itemDef.name = "Golden Maul";
			break;
		case 6199:
			itemDef.name = "Mystery Box";
			itemDef.actions = new String[5];
			itemDef.actions[0] = "Open";
			break;
		case 15501:
			itemDef.name = "Legendary Mystery Box";
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
			itemDef.name = "Max Cape";
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
			itemDef.name = "Veteran Cape";
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
			itemDef.modelZoom = 1385;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffsetY = 24;
			itemDef.modelRotation1 = 279;
			itemDef.modelRotation2 = 948;
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
		case 11618:
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
			itemDef.immitate(get(15486));
			itemDef.modifiedModelColors = new int [11];
			itemDef.originalModelColors = new int [11];
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
			itemDef.immitate(get(15486));
			itemDef.modifiedModelColors = new int [11];
			itemDef.originalModelColors = new int [11];
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
			itemDef.immitate(get(15486));
			itemDef.modifiedModelColors = new int [11];
			itemDef.originalModelColors = new int [11];
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
			itemDef.immitate(get(15486));
			itemDef.modifiedModelColors = new int [11];
			itemDef.originalModelColors = new int [11];
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
			itemDef.modifiedModelColors = new int [3];
			itemDef.originalModelColors = new int [3];
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
			itemDef.modifiedModelColors = new int [3];
			itemDef.originalModelColors = new int [3];
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
			itemDef.modifiedModelColors = new int [3];
			itemDef.originalModelColors = new int [3];
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
			itemDef.modifiedModelColors = new int [3];
			itemDef.originalModelColors = new int [3];
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
			itemDef.immitate(get(20929));
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
			itemDef.modifiedModelColors = new int[2];
			itemDef.originalModelColors = new int[2];
			itemDef.modifiedModelColors[0] = 4550;
			itemDef.originalModelColors[0] = 0;
			itemDef.modifiedModelColors[1] = 4540;
			itemDef.originalModelColors[1] = 0;
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
		}
	
		if(itemDef.description2 != null) {
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

		switch (itemDef.id) {

			case 20147:
				itemDef.modifiedModelColors = new int[2];
				itemDef.originalModelColors = new int[2];
				itemDef.modifiedModelColors[0] = 4550;
				itemDef.originalModelColors[0] = 1;
				itemDef.modifiedModelColors[1] = 4540;
				itemDef.originalModelColors[1] = 1;
				break;
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

		if(i > ItemDefinition.totalItems) {
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
		int k1 = Rasterizer.centerX;
		int l1 = Rasterizer.centerY;
		int ai[] = Rasterizer.lineOffsets;
		int ai1[] = DrawingArea.pixels;
		int i2 = DrawingArea.width;
		int j2 = DrawingArea.height;
		int k2 = DrawingArea.topX;
		int l2 = DrawingArea.bottomX;
		int i3 = DrawingArea.topY;
		int j3 = DrawingArea.bottomY;
		Rasterizer.notTextured = false;
		DrawingArea.initDrawingArea(32, 32, sprite2.myPixels);
		DrawingArea.drawPixels(32, 0, 0, 0, 32);
		Rasterizer.method364();
		int k3 = definition.modelZoom;

		if (k == -1) {
			k3 = (int) (k3 * 1.5D);
		}

		if (k > 0) {
			k3 = (int) (k3 * 1.04D);
		}

		int l3 = Rasterizer.SINE[definition.modelRotation1] * k3 >> 16;
		int i4 = Rasterizer.COSINE[definition.modelRotation1] * k3 >> 16;
		model.renderSingle(definition.modelRotation2, definition.modelOffsetX, definition.modelRotation1, definition.modelOffset1, l3 + model.modelHeight / 2 + definition.modelOffsetY, i4 + definition.modelOffsetY);

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
					if (sprite2.myPixels[k5 + l4 * 32] == 0 && k5 > 0 && l4 > 0 && sprite2.myPixels[k5 - 1 + (l4 - 1) * 32] > 0) {
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

		DrawingArea.initDrawingArea(j2, i2, ai1);
		DrawingArea.setBounds(k2, i3, l2, j3);
		Rasterizer.centerX = k1;
		Rasterizer.centerY = l1;
		Rasterizer.lineOffsets = ai;
		Rasterizer.notTextured = true;

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
		int k1 = Rasterizer.centerX;
		int l1 = Rasterizer.centerY;
		int ai[] = Rasterizer.lineOffsets;
		int ai1[] = DrawingArea.pixels;
		int i2 = DrawingArea.width;
		int j2 = DrawingArea.height;
		int k2 = DrawingArea.topX;
		int l2 = DrawingArea.bottomX;
		int i3 = DrawingArea.topY;
		int j3 = DrawingArea.bottomY;
		Rasterizer.notTextured = false;
		DrawingArea.initDrawingArea(32, 32, sprite2.myPixels);
		DrawingArea.drawPixels(32, 0, 0, 0, 32);
		Rasterizer.method364();
		int k3 = definition.modelZoom;
		if (zoom != -1 && zoom != 0)
			k3 = (definition.modelZoom * 100) / zoom;
		if (k == -1) {
			k3 = (int) (k3 * 1.5D);
		}

		if (k > 0) {
			k3 = (int) (k3 * 1.04D);
		}

		int l3 = Rasterizer.SINE[definition.modelRotation1] * k3 >> 16;
		int i4 = Rasterizer.COSINE[definition.modelRotation1] * k3 >> 16;
		model.renderSingle(definition.modelRotation2, definition.modelOffsetX, definition.modelRotation1, definition.modelOffset1, l3 + model.modelHeight / 2 + definition.modelOffsetY, i4 + definition.modelOffsetY);

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
					if (sprite2.myPixels[k5 + l4 * 32] == 0 && k5 > 0 && l4 > 0 && sprite2.myPixels[k5 - 1 + (l4 - 1) * 32] > 0) {
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

		DrawingArea.initDrawingArea(j2, i2, ai1);
		DrawingArea.setBounds(k2, i3, l2, j3);
		Rasterizer.centerX = k1;
		Rasterizer.centerY = l1;
		Rasterizer.lineOffsets = ai;
		Rasterizer.notTextured = true;

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

	public String[] actions;
	private int anInt162;
	int anInt164;
	public int maleWearId;
	private int anInt166;
	private int anInt167;
	private int anInt173;
	private int maleDialogue;
	private int anInt184;
	private int anInt185;
	int anInt188;
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
	int[] modifiedModelColors;
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
			model.scaleT(anInt167, anInt191, anInt192);
		}

		if (modifiedModelColors != null) {
			for (int l = 0; l < modifiedModelColors.length; l++) {
				model.method476(modifiedModelColors[l], originalModelColors[l]);
			}
		}

		model.light(64 + anInt196, 768 + anInt184, -50, -10, -50, true);
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

		if (!Model.method463(k)) {
			flag = false;
		}

		if (l != -1 && !Model.method463(l)) {
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
		int l = anInt188;
		int i1 = anInt185;

		if (j == 1) {
			k = femaleWearId;
			l = anInt164;
			i1 = anInt162;
		}

		if (k == -1) {
			return true;
		}

		boolean flag = true;

		if (!Model.method463(k)) {
			flag = false;
		}

		if (l != -1 && !Model.method463(l)) {
			flag = false;
		}

		if (i1 != -1 && !Model.method463(i1)) {
			flag = false;
		}

		return flag;
	}

	public Model method196(int i) {
		int j = maleWearId;
		int k = anInt188;
		int l = anInt185;

		if (i == 1) {
			j = femaleWearId;
			k = anInt164;
			l = anInt162;
		}

		if (j == -1) {
			return null;
		}

		Model model = Model.fetchModel(j);

		if (k != -1) {
			if (l != -1) {
				Model model_1 = Model.fetchModel(k);
				Model model_3 = Model.fetchModel(l);
				Model model_1s[] = { model, model_1, model_3 };
				model = new Model(3, model_1s);
			} else {
				Model model_2 = Model.fetchModel(k);
				Model models[] = { model, model_2 };
				model = new Model(2, models);
			}
		}

		if (i == 0 && (maleWieldX != 0 || maleWieldY != 0 || maleWieldZ != 0)) {
			model.translate(maleWieldX, maleWieldY, maleWieldZ);
		}

		if (i == 1 && (femaleWieldX != 0 || femaleWieldY != 0 || femaleWieldZ != 0)) {
			model.translate(femaleWieldX, femaleWieldY, femaleWieldZ);
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
	
	public void immitate(ItemDefinition other) {
		name = other.name;
		description = other.description;
		modifiedModelColors = other.modifiedModelColors;
		originalModelColors = other.originalModelColors;
		anInt167 = other.anInt167; anInt192 = other.anInt192; anInt191 = other.anInt191;
		modelRotation2 = other.modelRotation2; modelRotation1 = other.modelRotation1;
		modelOffset1 = other.modelOffset1; modelOffsetY = other.modelOffsetY;
		modelOffsetX = other.modelOffsetX;
		modelZoom = other.modelZoom;
		modelID = other.modelID;
		actions = other.actions;
		maleWearId = other.maleWearId; anInt188 = other.anInt188; anInt185 = other.anInt185;
		femaleWearId = other.femaleWearId; femaleWearId = other.femaleWearId; anInt162 = other.anInt162;
		maleDialogue = other.maleDialogue; anInt166 = other.anInt166;
		femaleDialogue = other.femaleDialogue; anInt173 = other.anInt173;
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
				anInt188 = buffer.getUnsignedShort();
			} else if (opcode == 25) {
				femaleWearId = buffer.getUnsignedShort();
				femaleWieldY = buffer.getSignedByte();
			} else if (opcode == 26) {
				anInt164 = buffer.getUnsignedShort();
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
				anInt185 = buffer.getUnsignedShort();
			} else if (opcode == 79) {
				anInt162 = buffer.getUnsignedShort();
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
		anInt188 = -1;
		femaleWearId = -1;
		anInt164 = -1;
		anInt185 = -1;
		anInt162 = -1;
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
		anInt185 = definition.anInt185;
		femaleWearId = definition.femaleWearId;
		anInt173 = definition.anInt173;
		maleDialogue = definition.maleDialogue;
		groundActions = definition.groundActions;
		maleWearId = definition.maleWearId;
		name = definition.name;
		anInt188 = definition.anInt188;
		membersObject = definition.membersObject;
		femaleDialogue = definition.femaleDialogue;
		anInt164 = definition.anInt164;
		anInt162 = definition.anInt162;
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