package org.runelive.client.cache.definition;

import java.io.File;
import java.io.FileOutputStream;

import org.runelive.client.Client;
import org.runelive.client.FrameReader;
import org.runelive.client.List;
import org.runelive.client.Signlink;
import org.runelive.client.cache.Archive;
import org.runelive.client.cache.ondemand.CacheFileRequest;
import org.runelive.client.cache.ondemand.CacheFileRequester;
import org.runelive.client.io.ByteBuffer;
import org.runelive.client.world.Model;

public final class ObjectDefinition {

	private static int[] streamIndices;
	private static int[] streamIndices667;
	private static ByteBuffer stream667;

	private static final int[] showBlack = { 3735, 26346, 26347, 26348, 26358, 26359, 26360, 26361, 26362, 26363,
			26364 };

	private static final int[] removeObjects = { 4651, 4565, 52843, 23897, 23633, 307, 8985, 57264, 23983, 632, 4656,
			24265, 24271, 24272, 24274, 24273, 24275, 24266, 24267, 24268, 24269, 24270 };

	public static ObjectDefinition forID(int id) {
		for (int j = 0; j < 20; j++) {
			if (cache[j].id == id) {
				return cache[j];
			}
		}
		if (id >= streamIndices.length) {
			// return forID667(id);
		}

		// cacheIndex = (cacheIndex + 1) % 20;,

		cacheIndex = (cacheIndex + 1) % 20;
		ObjectDefinition definition = cache[cacheIndex];

		boolean loadNew = (
		/* id == 8550 || id == 8551 || id == 7847 || id == 8150 || */id == 32159 || id == 32157 || id == 36672
				|| id == 36675 || id == 36692 || id == 34138 || id >= 39260 && id <= 39271 || id == 39229 || id == 39230
				|| id == 39231 || id == 36676 || id == 36692 || id > 11915 && id <= 11929 || id >= 11426 && id <= 11444
				|| id >= 14835 && id <= 14845 || id >= 11391 && id <= 11397 || id >= 12713 && id <= 12715);
		if (id < 0) {
			id = 0;
		}
		// stream.position = streamIndices[id];
		try {
			if (id > streamIndices.length || loadNew)
				stream667.position = streamIndices667[id];
			else
				stream.position = streamIndices[id];
		} catch (Exception e) {
			e.printStackTrace();
		}
		definition.id = id;
		definition.setDefaults();
		// definition.readValues(stream);
		if (id > streamIndices.length || loadNew)
			definition.readValues(stream667);
		else
			definition.readValues(stream);
		for (int element : showBlack) {
			if (id == element) {
				definition.modifiedModelColors = new int[1];
				definition.originalModelColors = new int[1];
				definition.modifiedModelColors[0] = 0;
				definition.originalModelColors[0] = 1;
			}

		}

		int[][] shootingStars = { { 38661, 42165 }, { 38662, 42166 }, { 38663, 42163 }, { 38664, 42164 },
				{ 38665, 42160 }, { 38666, 42159 }, { 38667, 42168 }, { 38668, 42169 }, };

		for (int[] i : shootingStars) {
			if (id == i[0]) {
				stream.position = streamIndices[3514];
				definition.setDefaults();
				definition.readValues(stream);
				definition.objectModelIDs = new int[1];
				definition.objectModelIDs[0] = i[1];
				definition.sizeX = 2;
				definition.sizeY = 2;
				definition.name = "Crashed star";
				definition.actions = new String[5];
				definition.actions[0] = "Mine";
				definition.description = "A crashed star!".getBytes();
			}
		}
		loadEvilTree(definition);
		if (definition.description == null) {
			definition.description = ("It's a " + definition.name + ".").getBytes();
		}
		if (definition.actions == null || definition.actions.length < 5) {
			String[] newActions = new String[5];
			if (definition.actions != null) {
				for (int i = 0; i < 5; i++) {
					if (i >= definition.actions.length) {
						newActions[i] = null;
					} else {
						newActions[i] = definition.actions[i];
					}
				}
			}
			definition.actions = newActions;
		}

		boolean removeObject = definition.id == 1442 || definition.id == 1433 || definition.id == 1443
				|| definition.id == 1441 || definition.id == 26916 || definition.id == 26917 || definition.id == 5244
				|| definition.id == 2623 || definition.id == 2956 || definition.id == 463 || definition.id == 462
				|| definition.id == 10527 || definition.id == 10529 || definition.id == 40257 || definition.id == 296
				|| definition.id == 300 || definition.id == 1747 || definition.id == 7332 || definition.id == 7326
				|| definition.id == 7325 || definition.id == 7385 || definition.id == 7331 || definition.id == 7385
				|| definition.id == 7320 || definition.id == 7317 || definition.id == 7323 || definition.id == 7354
				|| definition.id == 1536 || definition.id == 1537 || definition.id == 5126 || definition.id == 1551
				|| definition.id == 1553 || definition.id == 1516 || definition.id == 1519 || definition.id == 1557
				|| definition.id == 1558 || definition.id == 7126 || definition.id == 733 || definition.id == 14233
				|| definition.id == 14235 || definition.id == 1596 || definition.id == 1597 || definition.id == 14751
				|| definition.id == 14752 || definition.id == 14923 || definition.id == 36844 || definition.id == 30864
				|| definition.id == 2514 || definition.id == 1805 || definition.id == 15536 || definition.id == 2399
				|| definition.id == 14749 || definition.id == 29315 || definition.id == 29316 || definition.id == 29319
				|| definition.id == 29320 || definition.id == 29360 || definition.id == 1528 || definition.id == 36913
				|| definition.id == 36915 || definition.id == 15516 || definition.id == 35549 || definition.id == 35551
				|| definition.id == 26808 || definition.id == 26910 || definition.id == 26913 || definition.id == 24381
				|| definition.id == 15514 || definition.id == 25891 || definition.id == 26082 || definition.id == 26081
				|| definition.id == 1530 || definition.id == 16776 || definition.id == 16778 || definition.id == 28589
				|| definition.id == 1533 || definition.id == 17089 || definition.id == 1600 || definition.id == 1601
				|| definition.id == 11707 || definition.id == 24376 || definition.id == 24378 || definition.id == 40108
				|| definition.id == 59 || definition.id == 2069 || definition.id == 36846;
		if (removeObject) {
			definition.objectModelIDs = null;
			definition.hasActions = false;
			definition.isUnwalkable = false;
			return definition;
		}
		for (int ids = 0; ids < removeObjects.length; ids++) {
			if (id == removeObjects[ids]) {
				definition.objectModelIDs = null;
				definition.hasActions = false;
				definition.isUnwalkable = false;
				return definition;
			}
		}
		/*
		 * if(definition.varbitIndex <= 484 && definition.varbitIndex >= 469) {
		 * definition.configID = definition.varbitIndex; definition.varbitIndex
		 * = -1; }
		 */
		if (definition.name != null && definition.id != 591) {
			String s = definition.name.toLowerCase();
			if (s.contains("bank") && !s.contains("closed")) {

			}
		}
		switch (id) {
		case 398:
			definition.name = "Wilderness coffin";
			break;
		case 732:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 2298 };
			definition.name = "null";
			definition.isUnwalkable = false;
			definition.animationID = 494;
			definition.brightness = 50;
			definition.contrast = 25;
			definition.aBoolean779 = false;
			definition.hasActions = false;
			break;

		case 1502:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 782, 783, 784 };
			definition.anIntArray776 = new int[] { 0, 1, 9 };
			definition.name = "null";
			definition.adjustToTerrain = true;
			definition.nonFlatShading = true;
			definition.originalModelColors = new int[] { 8 };
			definition.modifiedModelColors = new int[] { 6689 };
			definition.hasActions = false;
			break;

		case 4451:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 2214, 4873, 16239, 2215, 16238 };
			definition.anIntArray776 = new int[] { 0, 2, 4, 9, 22 };
			definition.name = "null";
			definition.aBoolean779 = false;
			definition.mapSceneID = 22;
			definition.hasActions = false;
			break;

		case 6926:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 6932, 15553, 15554, 15555, 15556 };
			definition.anIntArray776 = new int[] { 0, 1, 2, 3, 9 };
			definition.name = "null";
			definition.isUnwalkable = false;
			definition.hasActions = false;
			break;

		case 7823:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29377 };
			definition.anIntArray776 = new int[] { 0 };
			definition.name = "null";
			definition.nonFlatShading = true;
			definition.hasActions = false;
			break;

		case 7824:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29368 };
			definition.anIntArray776 = new int[] { 0 };
			definition.name = "null";
			definition.nonFlatShading = true;
			definition.hasActions = false;
			break;

		case 7825:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29364 };
			definition.anIntArray776 = new int[] { 0 };
			definition.name = "null";
			definition.nonFlatShading = true;
			definition.hasActions = false;
			break;

		case 7826:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29369 };
			definition.anIntArray776 = new int[] { 0 };
			definition.name = "null";
			definition.nonFlatShading = true;
			definition.hasActions = false;
			break;

		case 7827:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29370 };
			definition.anIntArray776 = new int[] { 0 };
			definition.name = "null";
			definition.nonFlatShading = true;
			definition.hasActions = false;
			break;

		case 7828:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29367 };
			definition.anIntArray776 = new int[] { 0 };
			definition.name = "null";
			definition.nonFlatShading = true;
			definition.hasActions = false;
			break;

		case 7829:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29375 };
			definition.anIntArray776 = new int[] { 2 };
			definition.name = "null";
			definition.nonFlatShading = true;
			definition.hasActions = false;
			break;

		case 7830:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29365 };
			definition.anIntArray776 = new int[] { 3 };
			definition.name = "null";
			definition.nonFlatShading = true;
			definition.hasActions = false;
			break;

		case 7834:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29376, 29371 };
			definition.anIntArray776 = new int[] { 10, 0 };
			definition.name = "null";
			definition.nonFlatShading = true;
			definition.hasActions = false;
			break;

		case 11853:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 9297 };
			definition.name = "null";
			definition.animationID = 2599;
			definition.brightness = 25;
			definition.contrast = 15;
			definition.aBoolean779 = false;
			definition.hasActions = false;
			break;

		case 12930:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 1124 };
			definition.anIntArray776 = new int[] { 22 };
			definition.name = "null";
			definition.adjustToTerrain = true;
			definition.originalModelColors = new int[] { 20, 20, 20 };
			definition.modifiedModelColors = new int[] { 4899, 5921, 4892 };
			definition.hasActions = false;
			break;

		case 12931:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 1139 };
			definition.anIntArray776 = new int[] { 22 };
			definition.name = "null";
			definition.adjustToTerrain = true;
			definition.originalModelColors = new int[] { 20, 20, 20 };
			definition.modifiedModelColors = new int[] { 4899, 5921, 4892 };
			definition.hasActions = false;
			break;

		case 12932:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 1032 };
			definition.anIntArray776 = new int[] { 22 };
			definition.name = "null";
			definition.adjustToTerrain = true;
			definition.originalModelColors = new int[] { 20, 20, 20 };
			definition.modifiedModelColors = new int[] { 4899, 5921, 4892 };
			definition.hasActions = false;
			break;

		case 14645:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 1079 };
			definition.name = "Skeleton";
			definition.aBoolean757 = false;
			definition.originalModelColors = new int[] { 24 };
			definition.modifiedModelColors = new int[] { 6241 };
			definition.hasActions = false;
			break;

		case 14674:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 1079 };
			definition.name = "Skeleton";
			definition.aBoolean757 = false;
			definition.originalModelColors = new int[] { 20 };
			definition.modifiedModelColors = new int[] { 6241 };
			definition.hasActions = false;
			break;

		case 14675:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 1080 };
			definition.name = "Skeleton";
			definition.sizeX = 2;
			definition.aBoolean757 = false;
			definition.originalModelColors = new int[] { 20 };
			definition.modifiedModelColors = new int[] { 6241 };
			definition.hasActions = false;
			break;

		case 17118:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 2297 };
			definition.name = "Pool of Slime";
			definition.sizeX = 3;
			definition.sizeY = 3;
			definition.isUnwalkable = false;
			definition.animationID = 493;
			definition.brightness = 50;
			definition.contrast = 25;
			definition.originalModelColors = new int[] { 20797 };
			definition.modifiedModelColors = new int[] { 5058 };
			definition.aBoolean779 = false;
			definition.modelSizeX = 256;
			definition.modelSizeH = 256;
			definition.modelSizeY = 256;
			definition.hasActions = true;
			break;

		case 20196:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29383 };
			definition.anIntArray776 = new int[] { 22 };
			definition.name = "null";
			definition.isUnwalkable = false;
			definition.adjustToTerrain = true;
			definition.nonFlatShading = true;
			definition.hasActions = false;
			break;

		case 20737:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 834 };
			definition.name = "null";
			definition.adjustToTerrain = true;
			definition.nonFlatShading = true;
			definition.originalModelColors = new int[] { 0 };
			definition.modifiedModelColors = new int[] { 6689 };
			definition.hasActions = false;
			break;

		case 21696:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29379 };
			definition.anIntArray776 = new int[] { 22 };
			definition.name = "null";
			definition.isUnwalkable = false;
			definition.adjustToTerrain = true;
			definition.nonFlatShading = true;
			definition.hasActions = false;
			break;

		case 21697:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29381 };
			definition.anIntArray776 = new int[] { 22 };
			definition.name = "null";
			definition.isUnwalkable = false;
			definition.adjustToTerrain = true;
			definition.nonFlatShading = true;
			definition.hasActions = false;
			break;

		case 21698:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29387 };
			definition.anIntArray776 = new int[] { 22 };
			definition.name = "null";
			definition.isUnwalkable = false;
			definition.adjustToTerrain = true;
			definition.nonFlatShading = true;
			definition.hasActions = false;
			break;

		case 21699:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29389 };
			definition.anIntArray776 = new int[] { 22 };
			definition.name = "null";
			definition.isUnwalkable = false;
			definition.adjustToTerrain = true;
			definition.nonFlatShading = true;
			definition.hasActions = false;
			break;

		case 21700:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29388 };
			definition.anIntArray776 = new int[] { 22 };
			definition.name = "null";
			definition.isUnwalkable = false;
			definition.adjustToTerrain = true;
			definition.nonFlatShading = true;
			definition.hasActions = false;
			break;

		case 21701:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29384 };
			definition.anIntArray776 = new int[] { 22 };
			definition.name = "null";
			definition.isUnwalkable = false;
			definition.adjustToTerrain = true;
			definition.nonFlatShading = true;
			definition.hasActions = false;
			break;

		case 21702:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29386 };
			definition.anIntArray776 = new int[] { 22 };
			definition.name = "null";
			definition.isUnwalkable = false;
			definition.adjustToTerrain = true;
			definition.nonFlatShading = true;
			definition.hasActions = false;
			break;

		case 21703:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29380 };
			definition.anIntArray776 = new int[] { 22 };
			definition.name = "null";
			definition.isUnwalkable = false;
			definition.adjustToTerrain = true;
			definition.nonFlatShading = true;
			definition.hasActions = false;
			break;

		case 21704:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29308 };
			definition.anIntArray776 = new int[] { 22 };
			definition.name = "null";
			definition.isUnwalkable = false;
			definition.adjustToTerrain = true;
			definition.hasActions = false;
			break;

		case 21705:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29307 };
			definition.anIntArray776 = new int[] { 22 };
			definition.name = "null";
			definition.isUnwalkable = false;
			definition.adjustToTerrain = true;
			definition.hasActions = false;
			break;

		case 21706:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29309 };
			definition.anIntArray776 = new int[] { 22 };
			definition.name = "null";
			definition.isUnwalkable = false;
			definition.adjustToTerrain = true;
			definition.hasActions = false;
			break;

		case 21707:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29310 };
			definition.anIntArray776 = new int[] { 22 };
			definition.name = "null";
			definition.isUnwalkable = false;
			definition.adjustToTerrain = true;
			definition.hasActions = false;
			break;

		case 21708:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29344 };
			definition.anIntArray776 = new int[] { 22 };
			definition.name = "null";
			definition.isUnwalkable = false;
			definition.adjustToTerrain = true;
			definition.nonFlatShading = true;
			definition.hasActions = false;
			break;

		case 21709:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29348 };
			definition.anIntArray776 = new int[] { 22 };
			definition.name = "null";
			definition.isUnwalkable = false;
			definition.adjustToTerrain = true;
			definition.nonFlatShading = true;
			definition.hasActions = false;
			break;

		case 21710:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29343 };
			definition.anIntArray776 = new int[] { 22 };
			definition.name = "null";
			definition.isUnwalkable = false;
			definition.adjustToTerrain = true;
			definition.nonFlatShading = true;
			definition.hasActions = false;
			break;

		case 21711:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29342 };
			definition.anIntArray776 = new int[] { 22 };
			definition.name = "null";
			definition.isUnwalkable = false;
			definition.adjustToTerrain = true;
			definition.nonFlatShading = true;
			definition.hasActions = false;
			break;

		case 21712:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29345 };
			definition.anIntArray776 = new int[] { 22 };
			definition.name = "null";
			definition.isUnwalkable = false;
			definition.adjustToTerrain = true;
			definition.nonFlatShading = true;
			definition.hasActions = false;
			break;

		case 21713:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29339 };
			definition.anIntArray776 = new int[] { 22 };
			definition.name = "null";
			definition.isUnwalkable = false;
			definition.adjustToTerrain = true;
			definition.nonFlatShading = true;
			definition.hasActions = true;
			break;

		case 21714:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29349 };
			definition.anIntArray776 = new int[] { 22 };
			definition.name = "null";
			definition.isUnwalkable = false;
			definition.adjustToTerrain = true;
			definition.nonFlatShading = true;
			definition.hasActions = false;
			break;

		case 21715:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29340 };
			definition.anIntArray776 = new int[] { 22 };
			definition.name = "null";
			definition.isUnwalkable = false;
			definition.adjustToTerrain = true;
			definition.nonFlatShading = true;
			definition.hasActions = false;
			break;

		case 21716:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29338 };
			definition.anIntArray776 = new int[] { 22 };
			definition.name = "null";
			definition.isUnwalkable = false;
			definition.adjustToTerrain = true;
			definition.nonFlatShading = true;
			definition.hasActions = true;
			break;

		case 21717:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29347 };
			definition.anIntArray776 = new int[] { 22 };
			definition.name = "null";
			definition.isUnwalkable = false;
			definition.adjustToTerrain = true;
			definition.nonFlatShading = true;
			definition.hasActions = true;
			break;

		case 21718:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29346 };
			definition.anIntArray776 = new int[] { 22 };
			definition.name = "null";
			definition.isUnwalkable = false;
			definition.adjustToTerrain = true;
			definition.nonFlatShading = true;
			definition.hasActions = false;
			break;

		case 21748:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29341 };
			definition.anIntArray776 = new int[] { 22 };
			definition.name = "null";
			definition.isUnwalkable = false;
			definition.adjustToTerrain = true;
			definition.nonFlatShading = true;
			definition.hasActions = false;
			break;

		case 21749:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29385 };
			definition.anIntArray776 = new int[] { 22 };
			definition.name = "null";
			definition.isUnwalkable = false;
			definition.adjustToTerrain = true;
			definition.nonFlatShading = true;
			definition.hasActions = false;
			break;

		case 21750:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29378 };
			definition.anIntArray776 = new int[] { 22 };
			definition.name = "null";
			definition.isUnwalkable = false;
			definition.adjustToTerrain = true;
			definition.nonFlatShading = true;
			definition.hasActions = false;
			break;

		case 21751:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29350 };
			definition.anIntArray776 = new int[] { 22 };
			definition.name = "null";
			definition.isUnwalkable = false;
			definition.adjustToTerrain = true;
			definition.nonFlatShading = true;
			definition.hasActions = false;
			break;

		case 21752:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29352 };
			definition.anIntArray776 = new int[] { 22 };
			definition.name = "null";
			definition.isUnwalkable = false;
			definition.adjustToTerrain = true;
			definition.nonFlatShading = true;
			definition.hasActions = false;
			break;

		case 21753:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29359 };
			definition.anIntArray776 = new int[] { 22 };
			definition.name = "null";
			definition.isUnwalkable = false;
			definition.adjustToTerrain = true;
			definition.nonFlatShading = true;
			definition.hasActions = false;
			break;

		case 21754:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29351 };
			definition.anIntArray776 = new int[] { 22 };
			definition.name = "null";
			definition.isUnwalkable = false;
			definition.adjustToTerrain = true;
			definition.nonFlatShading = true;
			definition.hasActions = false;
			break;

		case 21755:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29358 };
			definition.anIntArray776 = new int[] { 22 };
			definition.name = "null";
			definition.isUnwalkable = false;
			definition.adjustToTerrain = true;
			definition.nonFlatShading = true;
			definition.hasActions = false;
			break;

		case 21756:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29353 };
			definition.anIntArray776 = new int[] { 22 };
			definition.name = "null";
			definition.isUnwalkable = false;
			definition.adjustToTerrain = true;
			definition.nonFlatShading = true;
			definition.hasActions = false;
			break;

		case 21757:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29354 };
			definition.anIntArray776 = new int[] { 22 };
			definition.name = "null";
			definition.isUnwalkable = false;
			definition.adjustToTerrain = true;
			definition.nonFlatShading = true;
			definition.hasActions = false;
			break;

		case 21758:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29357 };
			definition.anIntArray776 = new int[] { 22 };
			definition.name = "null";
			definition.isUnwalkable = false;
			definition.adjustToTerrain = true;
			definition.nonFlatShading = true;
			definition.hasActions = false;
			break;

		case 21759:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29355 };
			definition.anIntArray776 = new int[] { 22 };
			definition.name = "null";
			definition.isUnwalkable = false;
			definition.adjustToTerrain = true;
			definition.nonFlatShading = true;
			definition.hasActions = false;
			break;

		case 21760:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29356 };
			definition.anIntArray776 = new int[] { 22 };
			definition.name = "null";
			definition.isUnwalkable = false;
			definition.adjustToTerrain = true;
			definition.nonFlatShading = true;
			definition.hasActions = false;
			break;

		case 21761:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29281 };
			definition.name = "null";
			definition.sizeX = 2;
			definition.sizeY = 2;
			definition.adjustToTerrain = true;
			definition.hasActions = false;
			break;

		case 21762:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29282 };
			definition.name = "null";
			definition.sizeX = 2;
			definition.sizeY = 2;
			definition.adjustToTerrain = true;
			definition.hasActions = false;
			break;

		case 21763:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29283 };
			definition.name = "null";
			definition.adjustToTerrain = true;
			definition.hasActions = false;
			break;

		case 21765:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29284 };
			definition.name = "null";
			definition.sizeX = 2;
			definition.sizeY = 2;
			definition.hasActions = false;
			break;

		case 21766:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29275 };
			definition.name = "null";
			definition.sizeX = 2;
			definition.sizeY = 2;
			definition.hasActions = false;
			break;

		case 21767:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29276 };
			definition.name = "null";
			definition.isUnwalkable = false;
			definition.adjustToTerrain = true;
			definition.hasActions = false;
			break;

		case 21768:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29271 };
			definition.name = "null";
			definition.isUnwalkable = false;
			definition.adjustToTerrain = true;
			definition.hasActions = false;
			break;

		case 21769:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29272 };
			definition.name = "null";
			definition.isUnwalkable = false;
			definition.adjustToTerrain = true;
			definition.hasActions = false;
			break;

		case 21770:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29273 };
			definition.name = "null";
			definition.isUnwalkable = false;
			definition.adjustToTerrain = true;
			definition.hasActions = false;
			break;

		case 21772:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29399 };
			definition.name = "Portcullis";
			definition.sizeX = 3;
			definition.aBoolean764 = true;
			definition.actions = new String[] { "Exit", null, null, null, null };
			definition.hasActions = true;
			break;

		case 21773:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29280 };
			definition.name = "Soul Devourer";
			definition.sizeX = 5;
			definition.sizeY = 5;
			definition.adjustToTerrain = true;
			definition.animationID = 4517;
			definition.hasActions = true;
			break;

		case 21775:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29294 };
			definition.anIntArray776 = new int[] { 0 };
			definition.name = "null";
			definition.adjustToTerrain = true;
			definition.nonFlatShading = true;
			definition.hasActions = false;
			break;

		case 21776:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29305 };
			definition.anIntArray776 = new int[] { 0 };
			definition.name = "null";
			definition.adjustToTerrain = true;
			definition.nonFlatShading = true;
			definition.hasActions = false;
			break;

		case 21777:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29292 };
			definition.anIntArray776 = new int[] { 0 };
			definition.name = "null";
			definition.adjustToTerrain = true;
			definition.nonFlatShading = true;
			definition.hasActions = false;
			break;

		case 21779:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29293 };
			definition.anIntArray776 = new int[] { 0 };
			definition.name = "null";
			definition.adjustToTerrain = true;
			definition.nonFlatShading = true;
			definition.hasActions = false;
			break;

		case 21780:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29299 };
			definition.anIntArray776 = new int[] { 0 };
			definition.name = "null";
			definition.adjustToTerrain = true;
			definition.nonFlatShading = true;
			definition.hasActions = false;
			break;

		case 21946:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29300 };
			definition.anIntArray776 = new int[] { 0 };
			definition.name = "null";
			definition.adjustToTerrain = true;
			definition.nonFlatShading = true;
			definition.hasActions = false;
			break;

		case 21947:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29296 };
			definition.anIntArray776 = new int[] { 2 };
			definition.name = "null";
			definition.adjustToTerrain = true;
			definition.nonFlatShading = true;
			definition.hasActions = false;
			break;

		case 22494:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29295 };
			definition.anIntArray776 = new int[] { 0 };
			definition.name = "null";
			definition.adjustToTerrain = true;
			definition.nonFlatShading = true;
			definition.hasActions = false;
			break;

		case 22495:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29289 };
			definition.name = "null";
			definition.adjustToTerrain = true;
			definition.nonFlatShading = true;
			definition.hasActions = false;
			break;

		case 23100:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29290 };
			definition.name = "null";
			definition.sizeX = 3;
			definition.sizeY = 3;
			definition.adjustToTerrain = true;
			definition.animationID = 4516;
			definition.hasActions = false;
			break;

		case 23101:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29287 };
			definition.name = "Soul boat";
			definition.sizeX = 3;
			definition.sizeY = 3;
			definition.actions = new String[] { "Board", null, null, null, null };
			definition.hasActions = true;
			break;

		case 23102:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29279 };
			definition.name = "null";
			definition.sizeX = 3;
			definition.sizeY = 3;
			definition.aBoolean764 = true;
			definition.hasActions = false;
			break;

		case 23104:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29285 };
			definition.name = "Iron Winch";
			definition.sizeX = 2;
			definition.sizeY = 2;
			definition.adjustToTerrain = true;
			definition.actions = new String[] { "Turn", "Peek", null, null, null };
			definition.hasActions = true;
			break;

		case 23106:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29334 };
			definition.anIntArray776 = new int[] { 22 };
			definition.name = "null";
			definition.isUnwalkable = false;
			definition.adjustToTerrain = true;
			definition.nonFlatShading = true;
			definition.hasActions = false;
			break;

		case 23107:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29335 };
			definition.anIntArray776 = new int[] { 22 };
			definition.name = "null";
			definition.isUnwalkable = false;
			definition.adjustToTerrain = true;
			definition.nonFlatShading = true;
			definition.hasActions = false;
			break;

		case 23108:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29336 };
			definition.anIntArray776 = new int[] { 22 };
			definition.name = "null";
			definition.isUnwalkable = false;
			definition.adjustToTerrain = true;
			definition.nonFlatShading = true;
			definition.hasActions = false;
			break;

		case 23109:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29331 };
			definition.anIntArray776 = new int[] { 22 };
			definition.name = "null";
			definition.adjustToTerrain = true;
			definition.nonFlatShading = true;
			definition.hasActions = false;
			break;

		case 23112:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29333 };
			definition.anIntArray776 = new int[] { 22 };
			definition.name = "null";
			definition.isUnwalkable = false;
			definition.adjustToTerrain = true;
			definition.nonFlatShading = true;
			definition.hasActions = false;
			break;

		case 23610:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29332 };
			definition.anIntArray776 = new int[] { 22 };
			definition.name = "null";
			definition.isUnwalkable = false;
			definition.adjustToTerrain = true;
			definition.nonFlatShading = true;
			definition.hasActions = false;
			break;

		case 26294:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29337 };
			definition.anIntArray776 = new int[] { 22 };
			definition.name = "null";
			definition.isUnwalkable = false;
			definition.adjustToTerrain = true;
			definition.nonFlatShading = true;
			definition.hasActions = false;
			break;

		case 26571:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29398 };
			definition.name = "null";
			definition.sizeX = 3;
			definition.aBoolean764 = true;
			definition.hasActions = false;
			break;
		case 27059:
			definition.setDefaults();
			definition.objectModelIDs = new int[] { 29602 };
			definition.anIntArray776 = new int[] { 22 };
			definition.name = "null";
			definition.isUnwalkable = false;
			definition.nonFlatShading = true;
			definition.aBoolean736 = true;
			definition.hasActions = false;
			break;

		}
		if (definition.anIntArray776 != null) {
			if (definition.anIntArray776[0] == 22) {
				definition.hasActions = true;
				definition.isUnwalkable = false;
			}
		}
		if (id == 57225) {
			definition.actions = new String[5];
			definition.actions[0] = "Climb-over";
		}
		if (id == 5259) {
			definition.actions = new String[5];
			definition.name = "Ghost Town Barrier";
			definition.actions[0] = "Pass";
		}
		if (id == 10805 || id == 10806 || id == 10807 || id == 28089) {
			definition.name = "Grand Exchange clerk";
			definition.hasActions = true;
			definition.actions = new String[5];
			definition.actions[0] = "Use";
		}
		if (id == 10091) {
			definition.actions = new String[] { "Bait", null, null, null, null };
			definition.name = "@yel@Rocktail spot";
		}
		if (id == 7836 || id == 7808) {
			definition.hasActions = true;
			definition.actions = new String[] { "Dump-weeds", null, null, null, null };
			definition.name = "Compost bin";
		}
		if (id == 11698) {
			definition.objectModelIDs = new int[] { 1 };
			definition.modifiedModelColors = new int[] { 6817, 6697, 6693, 7580 };
			definition.originalModelColors = new int[] { 21543, 21547, 45, 7341 };
		}
		// if(id == 4436)
		// System.out.println(""+definition.objectModelIDs.length+"
		// "+object.objectModelIDs[0]+"");

		if (id == 11699) {
			definition.objectModelIDs = new int[] { 2 };
			definition.hasActions = false;
			definition.modifiedModelColors = new int[] { 74, 43117 };
			definition.originalModelColors = new int[] { 21543, 21547 };
		}
		if (id == 884) {
			definition.actions = new String[] { "Investigate", "Contribute", null, null, null };
			definition.name = "Well of Goodwill";
		}
		if (id == 26945) {
			definition.actions = new String[] { "Investigate", "Contribute", null, null, null };
			definition.name = "Well of Goodwill";
		}
		if(id == 2515) {
			definition.actions = new String[] { "Climb-in", null, null, null, null };
			definition.name = "Row boat";
		}
		if (id == 25014 || id == 25026 || id == 25020 || id == 25019 || id == 25024 || id == 25025 || id == 25016
				|| id == 5167 || id == 5168) {
			definition.actions = new String[5];
		}
		if (id == 1948) {
			definition.name = "Wall";
		}
		if (id == 2274) {
			definition.hasActions = true;
			definition.actions = new String[] { "Use", "Reenable Toggle", null, null, null };
		}
		if (id == 25029) {
			definition.actions = new String[5];
			definition.actions[0] = "Go-through";
		}
		if (id == 19187 || id == 19175) {
			definition.actions = new String[5];
			definition.actions[0] = "Dismantle";
		}
		if (id == 6434) {
			definition.actions = new String[5];
			definition.actions[0] = "Enter";
		}
		if (id == 2182) {
			definition.actions = new String[5];
			definition.actions[0] = "Buy-Items";
			definition.name = "Culinaromancer's chest";
		}
		if (id == 10177) {
			definition.actions = new String[5];
			definition.actions[0] = "Climb-down";
			definition.actions[1] = "Climb-up";
		}
		if (id == 39515) {
			definition.name = "Frost Dragon Portal";
		}
		if (id == 2026) {
			definition.actions = new String[5];
			definition.actions[0] = "Net";
		}
		if (id == 2029) {
			definition.actions = new String[5];
			definition.actions[0] = "Lure";
			definition.actions[1] = "Bait";
		}
		if (id == 2030) {
			definition.actions = new String[5];
			definition.actions[0] = "Cage";
			definition.actions[1] = "Harpoon";
		}
		if (id == 7352) {
			definition.name = "Gatestone portal";
			definition.actions = new String[5];
			definition.actions[0] = "Enter";
		}
		if (id == 11356) {
			definition.name = "Training Portal";
		}
		if (id == 47120) {
			definition.name = "Altar";
			definition.actions = new String[5];
			definition.actions[0] = "Craft-rune";
		}
		if (id == 20331) {
			definition.actions = new String[5];
			definition.actions[0] = "Steal-from";
		}
		if (id == 57258) {
			definition.actions[1] = null;
		}
		if (id == 47180) {
			definition.name = "Frost Dragon Portal Device";
			definition.actions = new String[5];
			definition.actions[0] = "Activate";
		}
		if (id == 8702) {
			definition.name = "Rocktail Barrel";
			definition.actions = new String[5];
			definition.actions[0] = "Fish-from";
		}
		if (id == 2783) {
			definition.hasActions = true;
			definition.name = "Anvil";
			definition.actions = new String[5];
			definition.actions[0] = "Smith-on";
		}
		if (id == 172) {
			definition.name = "Crystal chest";
		}
		if (id == 6714) {
			definition.hasActions = true;
			definition.name = "Door";
			definition.actions[0] = "Open";
		}
		if (id == 8550 || id == 8150 || id == 8551 || id == 7847 || id == 8550) {
			definition.actions = new String[] { null, "Inspect", null, "Guide", null };

			definition.hasActions = true;

		}
		if (id == 42151 || id == 42160) {
			definition.name = "Rocks";
			definition.hasActions = true;
			definition.mapSceneID = 11;
		}
		if (id == 42158 || id == 42157) {
			definition.name = "Rocks";
			definition.hasActions = true;
			definition.mapSceneID = 12;
		}
		if (id == 42123 || id == 42124 || id == 42119 || id == 42120 || id == 42118 || id == 42122) {
			definition.name = "Tree";
			definition.hasActions = true;
			definition.actions = new String[] { "Cut", null, null, null, null };
			definition.mapSceneID = 0;
		}
		if (id == 42127 || id == 42131 || id == 42133 || id == 42129 || id == 42134) {
			definition.name = "Tree";
			definition.hasActions = true;
			definition.actions = new String[] { "Cut", null, null, null, null };
			definition.mapSceneID = 6;
		}
		if (id == 42082 || id == 42083)
			definition.mapSceneID = 0;
		if (id >= 42087 && id <= 42117)
			definition.mapSceneID = 4;
		if (id > 30000 && definition.name != null && definition.name.toLowerCase().contains("gravestone"))
			definition.mapSceneID = 34;
		if (id == 36676) {
			definition.objectModelIDs = new int[] { 17374, 17383 };
			definition.anIntArray776 = null;
		}
		if (id == 34255) {
			definition.configID = 8002;
			definition.configObjectIDs = new int[] { 15385 };
		}
		if (id == 13830) {
			// definition.objectModelIDs = new int[] {12199};
			definition.configID = 8003;
			definition.configObjectIDs = new int[] { 13217, 13218, 13219, 13220, 13221, 13222, 13223 };
		}
		if (id == 21634) {
			definition.hasActions = true;
			definition.actions = new String[5];
			definition.actions[0] = "Sail";
		}
		if (id == 11339) {
			definition.hasActions = true;
			definition.actions = new String[5];
			definition.actions[0] = "Search";
		}
		if (id == 10284) {
			definition.name = "Chest";
			definition.hasActions = true;
			definition.actions = new String[5];
			definition.actions[0] = "Open";
		}
		if (id == 22721) {
			definition.hasActions = true;
			definition.actions = new String[5];
			definition.actions[0] = "Smelt";
		}
		if (id == 7837) {
			definition.hasActions = true;
			definition.actions = new String[5];
		}
		if (id == 4908) {
			// System.out.println(""+adjustToTerrain);
		}
		if (id == 26280) {
			definition.hasActions = true;
			definition.actions = new String[5];
			definition.actions[0] = "Study";
		}
		if (id == 27339 || id == 27306) {
			definition.hasActions = true;
			definition.name = "Mystical Monolith";
			definition.actions = new String[5];
			definition.actions[0] = "Travel";
			definition.actions[1] = "Pray-at";
		}

		for (int i1 : hotSpotIDs) {
			if (i1 == id) {
				definition.configID = 8000;
				definition.configObjectIDs = new int[] { id, 0 - 1 };
			}
		}
		if (id == 15314 || id == 15313) {
			definition.configID = 8000;
			definition.configObjectIDs = new int[] { id, -1 };
		}
		if (id == 15306) {
			definition.configID = 8001;
			definition.configObjectIDs = new int[] { id, -1, 13015 };
		}
		if (id == 15305) {
			definition.configID = 8001;
			definition.configObjectIDs = new int[] { id, -1, 13016 };
		}
		if (id == 15317) {
			definition.configID = 8001;
			definition.configObjectIDs = new int[] { id, -1, 13096 };
		}
		if (id == 8550) {
			definition.configObjectIDs = new int[] { 8576, 8575, 8574, 8573, 8576, 8576, 8558, 8559, 8560, 8561, 8562,
					8562, 8562, 8580, 8581, 8582, 8583, 8584, 8584, 8584, 8535, 8536, 8537, 8538, 8539, 8539, 8539,
					8641, 8642, 8643, 8644, 8645, 8645, 8645, 8618, 8619, 8620, 8621, 8622, 8623, 8624, 8624, 8624,
					8595, 8596, 8597, 8598, 8599, 8600, 8601, 8601, 8601, 8656, 8657, 8658, 8659, 8660, 8661, 8662,
					8663, 8664, 8664, 8664, 8576, 8576, 8576, 8576, 8576, 8576, 8576, 8563, 8564, 8565, 8566, 8576,
					8576, 8576, 8585, 8586, 8587, 8588, 8576, 8576, 8576, 8540, 8541, 8542, 8543, 8576, 8576, 8576,
					8646, 8647, 8648, 8649, 8576, 8576, 8576, 8625, 8626, 8627, 8628, 8629, 8630, 8576, 8576, 8576,
					8602, 8603, 8604, 8605, 8606, 8607, 8576, 8576, 8576, 8665, 8666, 8667, 8668, 8669, 8670, 8671,
					8672, 8576, 8576, 8576, 8576, 8576, 8576, 8576, 8576, 8576, 8576, 8576, 8567, 8568, 8569, 8576,
					8576, 8576, 8576, 8589, 8590, 8591, 8576, 8576, 8576, 8576, 8544, 8545, 8546, 8576, 8576, 8576,
					8576, 8650, 8651, 8652, 8576, 8576, 8576, 8576, 8631, 8632, 8633, 8634, 8635, 8576, 8576, 8576,
					8576, 8608, 8609, 8610, 8611, 8612, 8576, 8576, 8576, 8576, 8673, 8674, 8675, 8676, 8677, 8678,
					8679, 8576, 8576, 8576, 8576, 8576, 8576, 8576, 8576, 8576, 8576, 8576, 8570, 8571, 8572, 8576,
					8576, 8576, 8576, 8592, 8593, 8594, 8576, 8576, 8576, 8576, 8547, 8548, 8549, 8576, 8576, 8576,
					8576, 8653, 8654, 8655, 8576, 8576, 8576, 8576, 8636, 8637, 8638, 8639, 8640, 8576, 8576, 8576,
					8576, 8613, 8614, 8615, 8616, 8617, 8576, 8576, 8576, 8576, 8680, 8681, 8682, 8683, 8684, 8685,
					8686, 8576, 8576, 8576, 8576 };
		}
		if (id == 8551) {
			definition.configObjectIDs = new int[] { 8576, 8575, 8574, 8573, 8576, 8576, 8558, 8559, 8560, 8561, 8562,
					8562, 8562, 8580, 8581, 8582, 8583, 8584, 8584, 8584, 8535, 8536, 8537, 8538, 8539, 8539, 8539,
					8641, 8642, 8643, 8644, 8645, 8645, 8645, 8618, 8619, 8620, 8621, 8622, 8623, 8624, 8624, 8624,
					8595, 8596, 8597, 8598, 8599, 8600, 8601, 8601, 8601, 8656, 8657, 8658, 8659, 8660, 8661, 8662,
					8663, 8664, 8664, 8664, 8576, 8576, 8576, 8576, 8576, 8576, 8576, 8563, 8564, 8565, 8566, 8576,
					8576, 8576, 8585, 8586, 8587, 8588, 8576, 8576, 8576, 8540, 8541, 8542, 8543, 8576, 8576, 8576,
					8646, 8647, 8648, 8649, 8576, 8576, 8576, 8625, 8626, 8627, 8628, 8629, 8630, 8576, 8576, 8576,
					8602, 8603, 8604, 8605, 8606, 8607, 8576, 8576, 8576, 8665, 8666, 8667, 8668, 8669, 8670, 8671,
					8672, 8576, 8576, 8576, 8576, 8576, 8576, 8576, 8576, 8576, 8576, 8576, 8567, 8568, 8569, 8576,
					8576, 8576, 8576, 8589, 8590, 8591, 8576, 8576, 8576, 8576, 8544, 8545, 8546, 8576, 8576, 8576,
					8576, 8650, 8651, 8652, 8576, 8576, 8576, 8576, 8631, 8632, 8633, 8634, 8635, 8576, 8576, 8576,
					8576, 8608, 8609, 8610, 8611, 8612, 8576, 8576, 8576, 8576, 8673, 8674, 8675, 8676, 8677, 8678,
					8679, 8576, 8576, 8576, 8576, 8576, 8576, 8576, 8576, 8576, 8576, 8576, 8570, 8571, 8572, 8576,
					8576, 8576, 8576, 8592, 8593, 8594, 8576, 8576, 8576, 8576, 8547, 8548, 8549, 8576, 8576, 8576,
					8576, 8653, 8654, 8655, 8576, 8576, 8576, 8576, 8636, 8637, 8638, 8639, 8640, 8576, 8576, 8576,
					8576, 8613, 8614, 8615, 8616, 8617, 8576, 8576, 8576, 8576, 8680, 8681, 8682, 8683, 8684, 8685,
					8686, 8576, 8576, 8576, 8576 };
		}
		if (id == 7847) {
			definition.configObjectIDs = new int[] { 7843, 7842, 7841, 7840, 7843, 7843, 7843, 7843, 7867, 7868, 7869,
					7870, 7871, 7899, 7900, 7901, 7902, 7903, 7883, 7884, 7885, 7886, 7887, 7919, 7920, 7921, 7922,
					7923, 7851, 7852, 7853, 7854, 7855, 7918, 7917, 7916, 7915, 41538, 41539, 41540, 41541, 41542, 7843,
					7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843,
					7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7872, 7873, 7874,
					7875, 7843, 7904, 7905, 7906, 7907, 7843, 7888, 7889, 7890, 7891, 7843, 7924, 7925, 7926, 7927,
					7843, 7856, 7857, 7858, 7859, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843,
					7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843,
					7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7876, 7877,
					7878, 7843, 7843, 7908, 7909, 7910, 7843, 7843, 7892, 7893, 7894, 7843, 7843, 7928, 7929, 7930,
					7843, 7843, 7860, 7861, 7862, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843,
					7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843,
					7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7879, 7880,
					7881, 7882, 7843, 7911, 7912, 7913, 7914, 7843, 7895, 7896, 7897, 7898, 7843, 7931, 7932, 7933,
					7934, 7843, 7863, 7864, 7865, 7866, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843,
					7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843,
					7843, 7843, 7843, 7843, 7843 };
		}
		if (id == 8150) {
			definition.configObjectIDs = new int[] { 8135, 8134, 8133, 8132, 8139, 8140, 8141, 8142, 8143, 8143, 8143,
					8139, 8140, 8141, 8142, 8143, 8143, 8143, 8139, 8140, 8141, 8142, 8143, 8143, 8143, 8139, 8140,
					8141, 8142, 8143, 8143, 8143, 8139, 8140, 8141, 8142, 8143, 8143, 8143, 8139, 8140, 8141, 8142,
					8143, 8143, 8143, 8139, 8140, 8141, 8142, 8143, 8143, 8143, 8139, 8140, 8141, 8142, 8143, 8143,
					8143, 21101, 21127, 21159, 21178, 21185, 21185, 21185, 17776, 8139, 8140, 8141, 8142, 8143, 8143,
					8143, 8139, 8140, 8141, 8142, 8143, 8143, 8143, 8139, 8140, 8141, 8142, 8143, 8143, 8143, 8139,
					8140, 8141, 8142, 8143, 8143, 8143, 8139, 8140, 8141, 8142, 8143, 8143, 8143, 8139, 8140, 8141,
					8142, 8143, 8143, 8143, 17777, 17778, 17780, 17781, 17781, 17781, -1, -1, -1, -1, -1, -1, -1, -1,
					-1, -1, -1, -1, 8144, 8145, 8146, 8144, 8145, 8146, 8144, 8145, 8146, 8144, 8145, 8146, 8144, 8145,
					8146, 8144, 8145, 8146, 8144, 8145, 8146, 8144, 8145, 8146, 8144, 8145, 8146, 8144, 8145, 8146,
					8144, 8145, 8146, 8144, 8145, 8146, 8144, 8145, 8146, 8144, 8145, 8146, 8147, 8148, 8149, 8144,
					8145, 8146, 8144, 8145, 8146, 8135, 8135, 8135, 8135, 8135, 8135, 8135, 8135, 8135, 8135, 8135,
					8135, 8135, 9044, 9045, 9046, 9047, 9048, 9048, 9049, 9050, 9051, 9052, 9053, 9054, 8139, 8140,
					8141, 8142, 8143, 8143, 8143, 8144, 8145, 8146, 8135, 8135, 8135, 8135, 8135, 8135, -1, 8135, 8135,
					8135, 8135, 8135, 8135, 8135, 8135, 8135, 8135, 8135, 8135, 8135, 8135, 8135, 8135, 8135, 8135,
					8135, 8135, 8135, 8135, 8135, 8135, 8135, 8135, 8135, 8135, 8135, 8135, 8135, 8135, 8135, 8135,
					8135 };
		}

		switch (id) {
		case 6725:
		case 6714:
		case 6734:
		case 6730:
		case 6749:
		case 6742:
		case 6723:
		case 6728:
		case 6747:
		case 6744:
		case 6741:
		case 6779:
		case 6743:
		case 6719:
		case 6717:
		case 6731:
		case 6716:
		case 6720:
		case 6738:
		case 6726:
		case 6740:
		case 6721:
		case 6748:
		case 6729:
		case 6745:
		case 6718:
		case 6780:
		case 6746:
		case 6750:
		case 6722:
		case 6715:
			definition.name = "Door";
			definition.hasActions = true;
			break;
		case 4875:
			definition.name = "Banana Stall";
			break;
		case 4874:
			definition.name = "Ring Stall";
			break;
		case 13493:
			definition.actions = new String[5];
			definition.actions[0] = "Steal from";
			break;
		case 2152:
			definition.actions = new String[5];
			definition.actions[0] = "Infuse Pouches";
			definition.actions[1] = "Renew Points";
			definition.name = "Summoning Obelisk";
			break;
		case 4306:
			definition.actions = new String[5];
			definition.actions[0] = "Use";
			break;
		case 2732:
			definition.actions = new String[5];
			definition.actions[0] = "Add logs";
			break;
		case 2:
			definition.name = "Entrance";
			break;
		}

		return definition;
	}

	public static void nullify() {
		mruNodes1 = null;
		mruNodes2 = null;
		streamIndices = null;
		cache = null;
		stream = null;
	}

	public static void unpackConfig(Archive streamLoader) {
		/*
		 * stream = new ByteBuffer(streamLoader.get("loc.dat")); ByteBuffer
		 * stream = new ByteBuffer(streamLoader.get("loc.idx")); // stream667 =
		 * new ByteBuffer(streamLoader.get("667loc.dat")); // ByteBuffer
		 * idxBuffer667 = new // ByteBuffer(streamLoader.get("667loc.idx")); int
		 * totalObjects = stream.getUnsignedShort(); // System.out.println(
		 * "total objects: " + totalObjects); // int totalObjects667 =
		 * idxBuffer667.getUnsignedShort(); // System.out.println(
		 * "total 667 objects: " + totalObjects667); streamIndices = new
		 * int[totalObjects + 20000]; int i = 2; for (int j = 0; j <
		 * totalObjects; j++) { streamIndices[j] = i; i +=
		 * stream.getUnsignedShort(); } /* totalObjects = totalObjects667;
		 * streamIndices667 = new int[totalObjects]; i = 2; for (int j = 0; j <
		 * totalObjects; j++) { streamIndices667[j] = i; i +=
		 * idxBuffer667.getUnsignedShort(); }
		 *
		 * 
		 * cache = new ObjectDefinition[20]; for (int k = 0; k < 20; k++) {
		 * cache[k] = new ObjectDefinition(); }
		 */

		stream = new ByteBuffer(streamLoader.get("loc.dat"));
		ByteBuffer stream = new ByteBuffer(streamLoader.get("loc.idx"));
		stream667 = new ByteBuffer(streamLoader.get("667loc.dat"));
		ByteBuffer streamIdx667 = new ByteBuffer(streamLoader.get("667loc.idx"));

		int totalObjects = stream.getUnsignedShort();
		int totalObjects667 = streamIdx667.getUnsignedShort();
		streamIndices = new int[totalObjects];
		streamIndices667 = new int[totalObjects667];
		int i = 2;
		for (int j = 0; j < totalObjects; j++) {
			streamIndices[j] = i;
			i += stream.getUnsignedShort();
		}
		i = 2;
		for (int j = 0; j < totalObjects667; j++) {
			streamIndices667[j] = i;
			i += streamIdx667.getUnsignedShort();
		}
		cache = new ObjectDefinition[20];
		for (int k = 0; k < 20; k++)
			cache[k] = new ObjectDefinition();
	}

	public boolean aBoolean736;
	private byte brightness;
	private int offsetX;
	public String name;
	private int modelSizeY;
	private static final Model[] aModelArray741s = new Model[4];
	private byte contrast;
	public int sizeX;
	private int offsetH;
	public int mapFunctionID;
	private int[] originalModelColors;
	private int modelSizeX;
	public int configID;
	private boolean aBoolean751;
	public static boolean lowDetail;
	private static ByteBuffer stream;
	public int id;
	public boolean aBoolean757;
	public int mapSceneID;
	public int configObjectIDs[];
	private int anInt760;
	public int sizeY;
	public boolean adjustToTerrain;
	public boolean aBoolean764;
	public static Client clientInstance;
	private boolean isSolidObject;
	public boolean isUnwalkable;
	public int plane;
	private boolean nonFlatShading;
	private static int cacheIndex;
	private int modelSizeH;
	private int[] objectModelIDs;
	public int varbitIndex;
	public int anInt775;
	public int[] anIntArray776;
	public byte description[];
	public boolean hasActions;
	public boolean aBoolean779;
	public static List mruNodes2 = new List(30);
	public int animationID;
	private static ObjectDefinition[] cache;
	private int offsetY;
	private int[] modifiedModelColors;
	public static List mruNodes1 = new List(500);
	public String actions[];

	public void preloadModelsExtras(CacheFileRequester requester) {
		if (objectModelIDs == null) {
			return;
		}
		for (int objectModelID : objectModelIDs) {
			requester.method560(objectModelID & 0xffff, 0);
		}
	}

	public void preloadObjectModels(CacheFileRequester requester, boolean useExtrasQueue) {
		if (useExtrasQueue) {
			preloadModelsExtras(requester);
			return;
		}
		if (objectModelIDs == null) {
			return;
		}
		// System.out.println("Requesting object preload for object: " +
		// this.id);
		for (int objectModelID : objectModelIDs) {
			requester.pushRequest(CacheFileRequest.MODEL, objectModelID & 0xffff);
		}
	}

	public boolean method577(int i) {
		if (anIntArray776 == null) {
			if (objectModelIDs == null) {
				return true;
			}
			if (i != 10) {
				return true;
			}
			boolean flag1 = true;
			for (int objectModelID : objectModelIDs) {
				flag1 &= Model.method463(objectModelID & 0xffff);
			}

			return flag1;
		}
		for (int j = 0; j < anIntArray776.length; j++) {
			if (anIntArray776[j] == i) {
				return Model.method463(objectModelIDs[j] & 0xffff);
			}
		}

		return true;
	}

	public Model renderObject(int i, int j, int k, int l, int i1, int j1, int k1) {
		Model model = getAnimatedModel(i, k1, j);
		if (model == null) {
			return null;
		}
		if (adjustToTerrain || nonFlatShading) {
			model = new Model(adjustToTerrain, nonFlatShading, model);
		}
		if (adjustToTerrain) {
			int l1 = (k + l + i1 + j1) / 4;
			for (int i2 = 0; i2 < model.vertexCount; i2++) {
				int j2 = model.vertexX[i2];
				int k2 = model.vertexZ[i2];
				int l2 = k + (l - k) * (j2 + 64) / 128;
				int i3 = j1 + (i1 - j1) * (j2 + 64) / 128;
				int j3 = l2 + (i3 - l2) * (k2 + 64) / 128;
				model.vertexY[i2] += j3 - l1;
			}

			model.method467();
		}
		return model;
	}

	public boolean method579() {
		if (objectModelIDs == null) {
			return true;
		}
		boolean flag1 = true;
		for (int objectModelID : objectModelIDs) {
			flag1 &= Model.method463(objectModelID & 0xffff);
		}
		return flag1;
	}

	public ObjectDefinition method580() {
		int i = -1;
		if (varbitIndex != -1) {
			VarBit varBit = VarBit.cache[varbitIndex];
			int j = varBit.configId;
			int k = varBit.configValue;
			int l = varBit.anInt650;
			int i1 = Client.anIntArray1232[l - k];
			// System.out.println("j: " + j + " k: " + k);
			i = clientInstance.variousSettings[j] >> k & i1;
		} else if (configID != -1) {
			i = clientInstance.variousSettings[configID];
		}
		if (i < 0 || i >= configObjectIDs.length || configObjectIDs[i] == -1) {
			return null;
		} else {
			return forID(configObjectIDs[i]);
		}
	}

	private Model getAnimatedModel(int j, int k, int l) {
		Model model = null;
		long l1;
		if (anIntArray776 == null) {
			if (j != 10) {
				return null;
			}
			l1 = (id << 8) + l + ((long) (k + 1) << 32);
			Model model_1 = (Model) mruNodes2.insertFromCache(l1);
			if (model_1 != null) {
				return model_1;
			}
			if (objectModelIDs == null) {
				return null;
			}
			boolean flag1 = aBoolean751 ^ l > 3;
			int k1 = objectModelIDs.length;
			for (int i2 = 0; i2 < k1; i2++) {
				int l2 = objectModelIDs[i2];
				if (flag1) {
					l2 += 0x10000;
				}
				model = (Model) mruNodes1.insertFromCache(l2);
				if (model == null) {
					model = Model.method462(l2 & 0xffff);
					if (model == null) {
						return null;
					}
					if (flag1) {
						model.method477();
					}
					mruNodes1.removeFromCache(model, l2);
				}
				if (k1 > 1) {
					aModelArray741s[i2] = model;
				}
			}

			if (k1 > 1) {
				model = new Model(k1, aModelArray741s);
			}
		} else {
			int i1 = -1;
			for (int j1 = 0; j1 < anIntArray776.length; j1++) {
				if (anIntArray776[j1] != j) {
					continue;
				}
				i1 = j1;
				break;
			}

			if (i1 == -1) {
				return null;
			}
			l1 = (id << 8) + (i1 << 3) + l + ((long) (k + 1) << 32);
			Model model_2 = (Model) mruNodes2.insertFromCache(l1);
			if (model_2 != null) {
				return model_2;
			}
			if (objectModelIDs == null) {
				return null;
			}
			int j2 = objectModelIDs[i1];
			boolean flag3 = aBoolean751 ^ l > 3;
			if (flag3) {
				j2 += 0x10000;
			}
			model = (Model) mruNodes1.insertFromCache(j2);
			if (model == null) {
				model = Model.method462(j2 & 0xffff);
				if (model == null) {
					return null;
				}
				if (flag3) {
					model.method477();
				}
				mruNodes1.removeFromCache(model, j2);
			}
		}
		boolean flag;
		flag = modelSizeX != 128 || modelSizeH != 128 || modelSizeY != 128;
		boolean flag2;
		flag2 = offsetX != 0 || offsetH != 0 || offsetY != 0;
		Model model_3 = new Model(modifiedModelColors == null, FrameReader.isNullFrame(k),
				l == 0 && k == -1 && !flag && !flag2, model);
		if (k != -1) {
			model_3.createBones();
			model_3.applyTransform(k);
			model_3.triangleSkin = null;
			model_3.vertexSkin = null;
		}
		while (l-- > 0) {
			model_3.method473();
		}
		if (modifiedModelColors != null) {
			for (int k2 = 0; k2 < modifiedModelColors.length; k2++) {
				model_3.method476(modifiedModelColors[k2], originalModelColors[k2]);
			}

		}
		if (flag) {
			model_3.scaleT(modelSizeX, modelSizeY, modelSizeH);
		}
		if (flag2) {
			model_3.translate(offsetX, offsetH, offsetY);
		}
		model_3.light(64 + brightness, 768 + contrast * 5, -50, -10, -50, !nonFlatShading);
		if (anInt760 == 1) {
			model_3.anInt1654 = model_3.modelHeight;
		}
		mruNodes2.removeFromCache(model_3, l1);
		return model_3;
	}

	private final static int[] hotSpotIDs = new int[] { 13374, 13375, 13376, 13377, 13378, 39260, 39261, 39262, 39263,
			39264, 39265, 2715, 13366, 13367, 13368, 13369, 13370, 13371, 13372, 15361, 15362, 15363, 15366, 15367,
			15364, 15365, 15410, 15412, 15411, 15414, 15415, 15413, 15416, 15416, 15418, 15419, 15419, 15419, 15419,
			15419, 15419, 15419, 15419, 15402, 15405, 15401, 15398, 15404, 15403, 15400, 15400, 15399, 15302, 15302,
			15302, 15302, 15302, 15302, 15304, 15303, 15303, 15301, 15300, 15300, 15300, 15300, 15299, 15299, 15299,
			15299, 15298, 15443, 15445, 15447, 15446, 15444, 15441, 15439, 15448, 15450, 15266, 15265, 15264, 15263,
			15263, 15263, 15263, 15263, 15263, 15263, 15263, 15267, 15262, 15260, 15261, 15268, 15379, 15378, 15377,
			15386, 15383, 15382, 15384, 34255, 15380, 15381, 15346, 15344, 15345, 15343, 15342, 15296, 15297, 15297,
			15294, 15293, 15292, 15291, 15290, 15289, 15288, 15287, 15286, 15282, 15281, 15280, 15279, 15278, 15277,
			15397, 15396, 15395, 15393, 15392, 15394, 15390, 15389, 15388, 15387, 44909, 44910, 44911, 44908, 15423,
			15423, 15423, 15423, 15420, 48662, 15422, 15421, 15425, 15425, 15424, 18813, 18814, 18812, 18815, 18811,
			18810, 15275, 15275, 15271, 15271, 15276, 15270, 15269, 13733, 13733, 13733, 13733, 13733, 13733, 15270,
			15274, 15273, 15406, 15407, 15408, 15409, 15368, 15375, 15375, 15375, 15375, 15376, 15376, 15376, 15376,
			15373, 15373, 15374, 15374, 15370, 15371, 15372, 15369, 15426, 15426, 15435, 15438, 15434, 15434, 15431,
			15431, 15431, 15431, 15436, 15436, 15436, 15436, 15436, 15436, 15437, 15437, 15437, 15437, 15437, 15437,
			15350, 15348, 15347, 15351, 15349, 15353, 15352, 15354, 15356, 15331, 15331, 15331, 15331, 15355, 15355,
			15355, 15355, 15330, 15330, 15330, 15330, 15331, 15331, 15323, 15325, 15325, 15324, 15324, 15329, 15328,
			15326, 15327, 15325, 15325, 15324, 15324, 15330, 15330, 15330, 15330, 15331, 15331, 34138, 15330, 15330,
			34138, 34138, 15330, 34138, 15330, 15331, 15331, 15337, 15336, 39230, 39231, 36692, 39229, 36676, 34138,
			15330, 15330, 34138, 34138, 15330, 34138, 15330, 15331, 15331, 36675, 36672, 36672, 36675, 36672, 36675,
			36675, 36672, 15331, 15331, 15330, 15330, 15257, 15256, 15259, 15259, 15327, 15326 };

	private void readValues(ByteBuffer stream) {
		int i = -1;
		label0: do {
			int opcode;
			do {
				opcode = stream.getUnsignedByte();
				if (opcode == 0)
					break label0;
				if (opcode == 1) {
					int k = stream.getUnsignedByte();
					if (k > 0)
						if (objectModelIDs == null || lowDetail) {
							anIntArray776 = new int[k];
							objectModelIDs = new int[k];
							for (int k1 = 0; k1 < k; k1++) {
								objectModelIDs[k1] = stream.getUnsignedShort();
								anIntArray776[k1] = stream.getUnsignedByte();
							}
						} else {
							stream.position += k * 3;
						}
				} else if (opcode == 2)
					name = stream.getString();
				else if (opcode == 3)
					description = stream.getBytes();
				else if (opcode == 5) {
					int l = stream.getUnsignedByte();
					if (l > 0)
						if (objectModelIDs == null || lowDetail) {
							anIntArray776 = null;
							objectModelIDs = new int[l];
							for (int l1 = 0; l1 < l; l1++)
								objectModelIDs[l1] = stream.getUnsignedShort();
						} else {
							stream.position += l * 2;
						}
				} else if (opcode == 14)
					sizeX = stream.getUnsignedByte();
				else if (opcode == 15)
					sizeY = stream.getUnsignedByte();
				else if (opcode == 17)
					isUnwalkable = false;
				else if (opcode == 18)
					aBoolean757 = false;
				else if (opcode == 19) {
					i = stream.getUnsignedByte();
					if (i == 1)
						hasActions = true;
				} else if (opcode == 21)
					adjustToTerrain = true;
				else if (opcode == 22)
					nonFlatShading = true;
				else if (opcode == 23)
					aBoolean764 = true;
				else if (opcode == 24) {
					animationID = stream.getUnsignedShort();
					if (animationID == 65535)
						animationID = -1;
				} else if (opcode == 28)
					anInt775 = stream.getUnsignedByte();
				else if (opcode == 29)
					brightness = stream.getSignedByte();
				else if (opcode == 39)
					contrast = stream.getSignedByte();
				else if (opcode >= 30 && opcode < 39) {
					if (actions == null)
						actions = new String[10];
					actions[opcode - 30] = stream.getString();
					if (actions[opcode - 30].equalsIgnoreCase("hidden"))
						actions[opcode - 30] = null;
				} else if (opcode == 40) {
					int i1 = stream.getUnsignedByte();
					modifiedModelColors = new int[i1];
					originalModelColors = new int[i1];
					for (int i2 = 0; i2 < i1; i2++) {
						modifiedModelColors[i2] = stream.getUnsignedShort();
						originalModelColors[i2] = stream.getUnsignedShort();
					}
				} else if (opcode == 60)
					mapFunctionID = stream.getUnsignedShort();
				else if (opcode == 62)
					aBoolean751 = true;
				else if (opcode == 64)
					aBoolean779 = false;
				else if (opcode == 65)
					modelSizeX = stream.getUnsignedShort();
				else if (opcode == 66)
					modelSizeH = stream.getUnsignedShort();
				else if (opcode == 67)
					modelSizeY = stream.getUnsignedShort();
				else if (opcode == 68)
					mapSceneID = stream.getUnsignedShort();
				else if (opcode == 69)
					plane = stream.getUnsignedByte();
				else if (opcode == 70)
					offsetX = stream.getSignedShort();
				else if (opcode == 71)
					offsetH = stream.getSignedShort();
				else if (opcode == 72)
					offsetY = stream.getSignedShort();
				else if (opcode == 73)
					aBoolean736 = true;
				else if (opcode == 74) {
					isSolidObject = true;
				} else {
					if (opcode != 75)
						continue;
					anInt760 = stream.getUnsignedByte();
				}
				continue label0;
			} while (opcode != 77);
			varbitIndex = stream.getUnsignedShort();
			if (varbitIndex == 65535)
				varbitIndex = -1;
			configID = stream.getUnsignedShort();
			if (configID == 65535)
				configID = -1;
			int j1 = stream.getUnsignedByte();
			configObjectIDs = new int[j1 + 1];
			for (int j2 = 0; j2 <= j1; j2++) {
				configObjectIDs[j2] = stream.getUnsignedShort();
				if (configObjectIDs[j2] == 65535)
					configObjectIDs[j2] = -1;
			}

		} while (true);
		if (i == -1) {
			hasActions = objectModelIDs != null && (anIntArray776 == null || anIntArray776[0] == 10);
			if (actions != null)
				hasActions = true;
		}
		if (isSolidObject) {
			isUnwalkable = false;
			aBoolean757 = false;
		}
		if (anInt760 == -1)
			anInt760 = isUnwalkable ? 1 : 0;
	}

	public void immitate(ObjectDefinition definition2) {
		objectModelIDs = definition2.objectModelIDs;
		anIntArray776 = definition2.anIntArray776;

		name = definition2.name;
		description = definition2.description;

		modifiedModelColors = definition2.modifiedModelColors;
		originalModelColors = definition2.originalModelColors;

		sizeX = definition2.sizeX;
		sizeY = definition2.sizeY;

		isUnwalkable = definition2.isUnwalkable;

		aBoolean757 = definition2.aBoolean757;

		hasActions = definition2.hasActions;

		adjustToTerrain = definition2.adjustToTerrain;
		nonFlatShading = definition2.nonFlatShading;
		aBoolean764 = definition2.aBoolean764;
		animationID = definition2.animationID;
		anInt775 = definition2.anInt775;
		brightness = definition2.brightness;
		contrast = definition2.contrast;
		actions = definition2.actions;

		mapFunctionID = definition2.mapFunctionID;
		mapSceneID = definition2.mapSceneID;
		aBoolean751 = definition2.aBoolean751;
		aBoolean779 = definition2.aBoolean779;
		modelSizeX = definition2.modelSizeX;
		modelSizeH = definition2.modelSizeH;
		modelSizeY = definition2.modelSizeY;
		plane = definition2.plane;
		offsetX = definition2.offsetX;
		offsetH = definition2.offsetH;
		offsetY = definition2.offsetY;
		aBoolean736 = definition2.aBoolean736;
		isSolidObject = definition2.isSolidObject;
		anInt760 = definition2.anInt760;
		varbitIndex = definition2.varbitIndex;
		configID = definition2.configID;
		configObjectIDs = definition2.configObjectIDs;

	}

	private void setDefaults() {
		objectModelIDs = null;
		anIntArray776 = null;
		name = null;
		description = null;
		modifiedModelColors = null;
		originalModelColors = null;
		sizeX = 1;
		sizeY = 1;
		isUnwalkable = true;
		aBoolean757 = true;
		hasActions = false;
		adjustToTerrain = false;
		nonFlatShading = false;
		aBoolean764 = false;
		animationID = -1;
		anInt775 = 16;
		brightness = 0;
		contrast = 0;
		actions = null;
		mapFunctionID = -1;
		mapSceneID = -1;
		aBoolean751 = false;
		aBoolean779 = true;
		modelSizeX = 128;
		modelSizeH = 128;
		modelSizeY = 128;
		plane = 0;
		offsetX = 0;
		offsetH = 0;
		offsetY = 0;
		aBoolean736 = false;
		isSolidObject = false;
		anInt760 = -1;
		varbitIndex = -1;
		configID = -1;
		configObjectIDs = null;
	}

	public static void loadEvilTree(ObjectDefinition definition) {
		switch (definition.id) {

		case 11391:
			definition.objectModelIDs = new int[] { 45733, 45735 };
			definition.anIntArray776 = null;
			definition.name = "Seedling";
			definition.modifiedModelColors = new int[] { 0 };
			definition.originalModelColors = new int[] { 1 };
			definition.sizeX = 1;
			definition.sizeY = 1;
			definition.isUnwalkable = true;
			definition.aBoolean757 = true;
			definition.hasActions = true;
			definition.adjustToTerrain = false;
			definition.nonFlatShading = false;
			definition.aBoolean764 = false;
			definition.animationID = 1694;
			definition.anInt775 = 16;
			definition.brightness = 15;
			definition.contrast = 0;
			definition.actions = new String[] { "Nurture", };
			definition.mapFunctionID = -1;
			definition.mapSceneID = -1;
			definition.aBoolean751 = false;
			definition.aBoolean779 = true;
			definition.modelSizeX = 128;
			definition.modelSizeH = 128;
			definition.modelSizeY = 128;
			definition.plane = 0;
			definition.offsetX = 0;
			definition.offsetH = 0;
			definition.offsetY = 0;
			definition.aBoolean736 = false;
			definition.isSolidObject = false;
			definition.anInt760 = 1;
			definition.varbitIndex = -1;
			definition.configID = -1;
			definition.configObjectIDs = null;
			break;
		case 11392:
			definition.objectModelIDs = new int[] { 45733, 45731, 45735 };
			definition.anIntArray776 = null;
			definition.name = "Sapling";
			definition.modifiedModelColors = new int[] { 0 };
			definition.originalModelColors = new int[] { 1 };
			definition.sizeX = 1;
			definition.sizeY = 1;
			definition.isUnwalkable = true;
			definition.aBoolean757 = true;
			definition.hasActions = true;
			definition.adjustToTerrain = false;
			definition.nonFlatShading = false;
			definition.aBoolean764 = false;
			definition.animationID = 1695;
			definition.anInt775 = 16;
			definition.brightness = 15;
			definition.contrast = 0;
			definition.actions = new String[] { "Nurture", };
			definition.mapFunctionID = -1;
			definition.mapSceneID = -1;
			definition.aBoolean751 = false;
			definition.aBoolean779 = true;
			definition.modelSizeX = 128;
			definition.modelSizeH = 128;
			definition.modelSizeY = 128;
			definition.plane = 0;
			definition.offsetX = 0;
			definition.offsetH = 0;
			definition.offsetY = 0;
			definition.aBoolean736 = false;
			definition.isSolidObject = false;
			definition.anInt760 = 1;
			definition.varbitIndex = -1;
			definition.configID = -1;
			definition.configObjectIDs = null;
			break;
		case 11394:
			definition.objectModelIDs = new int[] { 45736, 45739, 45735 };
			definition.anIntArray776 = null;
			definition.name = "Young tree";
			definition.modifiedModelColors = new int[] { 0 };
			definition.originalModelColors = new int[] { 1 };
			definition.sizeX = 3;
			definition.sizeY = 3;
			definition.isUnwalkable = true;
			definition.aBoolean757 = true;
			definition.hasActions = true;
			definition.adjustToTerrain = false;
			definition.nonFlatShading = false;
			definition.aBoolean764 = false;
			definition.animationID = 1697;
			definition.anInt775 = 16;
			definition.brightness = 15;
			definition.contrast = 0;
			definition.actions = new String[] { "Nurture", };
			definition.mapFunctionID = -1;
			definition.mapSceneID = -1;
			definition.aBoolean751 = false;
			definition.aBoolean779 = true;
			definition.modelSizeX = 128;
			definition.modelSizeH = 128;
			definition.modelSizeY = 128;
			definition.plane = 0;
			definition.offsetX = 0;
			definition.offsetH = 0;
			definition.offsetY = 0;
			definition.aBoolean736 = false;
			definition.isSolidObject = false;
			definition.anInt760 = 1;
			definition.varbitIndex = -1;
			definition.configID = -1;
			definition.configObjectIDs = null;
			break;
		case 11395:
			definition.objectModelIDs = new int[] { 45739, 45741, 45735 };
			definition.anIntArray776 = null;
			definition.name = "Young tree";
			definition.modifiedModelColors = new int[] { 0 };
			definition.originalModelColors = new int[] { 1 };
			definition.sizeX = 3;
			definition.sizeY = 3;
			definition.isUnwalkable = true;
			definition.aBoolean757 = true;
			definition.hasActions = true;
			definition.adjustToTerrain = false;
			definition.nonFlatShading = false;
			definition.aBoolean764 = false;
			definition.animationID = 1698;
			definition.anInt775 = 16;
			definition.brightness = 15;
			definition.contrast = 0;
			definition.actions = new String[] { "Nurture", };
			definition.mapFunctionID = -1;
			definition.mapSceneID = -1;
			definition.aBoolean751 = false;
			definition.aBoolean779 = true;
			definition.modelSizeX = 128;
			definition.modelSizeH = 128;
			definition.modelSizeY = 128;
			definition.plane = 0;
			definition.offsetX = 0;
			definition.offsetH = 0;
			definition.offsetY = 0;
			definition.aBoolean736 = false;
			definition.isSolidObject = false;
			definition.anInt760 = 1;
			definition.varbitIndex = -1;
			definition.configID = -1;
			definition.configObjectIDs = null;
			break;
		case 12713:
			definition.objectModelIDs = new int[] { 45759 };
			definition.anIntArray776 = null;
			definition.name = "Fallen tree";
			definition.modifiedModelColors = new int[] { 0 };
			definition.originalModelColors = new int[] { 1 };
			definition.sizeX = 3;
			definition.sizeY = 3;
			definition.isUnwalkable = true;
			definition.aBoolean757 = true;
			definition.hasActions = true;
			definition.adjustToTerrain = false;
			definition.nonFlatShading = false;
			definition.aBoolean764 = false;
			definition.animationID = -1;
			definition.anInt775 = 16;
			definition.brightness = 15;
			definition.contrast = 0;
			definition.actions = null;
			definition.mapFunctionID = -1;
			definition.mapSceneID = -1;
			definition.aBoolean751 = false;
			definition.aBoolean779 = true;
			definition.modelSizeX = 128;
			definition.modelSizeH = 128;
			definition.modelSizeY = 128;
			definition.plane = 0;
			definition.offsetX = 0;
			definition.offsetH = 0;
			definition.offsetY = 0;
			definition.aBoolean736 = false;
			definition.isSolidObject = false;
			definition.anInt760 = 1;
			definition.varbitIndex = -1;
			definition.configID = -1;
			definition.configObjectIDs = null;
			break;
		case 12714:
			definition.objectModelIDs = new int[] { 45754 };
			definition.anIntArray776 = null;
			definition.name = "Fallen tree";
			definition.modifiedModelColors = new int[] { 0 };
			definition.originalModelColors = new int[] { 1 };
			definition.sizeX = 3;
			definition.sizeY = 3;
			definition.isUnwalkable = true;
			definition.aBoolean757 = true;
			definition.hasActions = true;
			definition.adjustToTerrain = false;
			definition.nonFlatShading = false;
			definition.aBoolean764 = false;
			definition.animationID = -1;
			definition.anInt775 = 16;
			definition.brightness = 15;
			definition.contrast = 0;
			definition.actions = null;
			definition.mapFunctionID = -1;
			definition.mapSceneID = -1;
			definition.aBoolean751 = false;
			definition.aBoolean779 = true;
			definition.modelSizeX = 128;
			definition.modelSizeH = 128;
			definition.modelSizeY = 128;
			definition.plane = 0;
			definition.offsetX = 0;
			definition.offsetH = 0;
			definition.offsetY = 0;
			definition.aBoolean736 = false;
			definition.isSolidObject = false;
			definition.anInt760 = 1;
			definition.varbitIndex = -1;
			definition.configID = -1;
			definition.configObjectIDs = null;
			break;
		case 12715:
			definition.objectModelIDs = new int[] { 45752 };
			definition.anIntArray776 = null;
			definition.name = "Fallen tree";
			definition.modifiedModelColors = new int[] { 0 };
			definition.originalModelColors = new int[] { 1 };
			definition.sizeX = 3;
			definition.sizeY = 3;
			definition.isUnwalkable = true;
			definition.aBoolean757 = true;
			definition.hasActions = true;
			definition.adjustToTerrain = false;
			definition.nonFlatShading = false;
			definition.aBoolean764 = false;
			definition.animationID = -1;
			definition.anInt775 = 16;
			definition.brightness = 15;
			definition.contrast = 0;
			definition.actions = null;
			definition.mapFunctionID = -1;
			definition.mapSceneID = -1;
			definition.aBoolean751 = false;
			definition.aBoolean779 = true;
			definition.modelSizeX = 128;
			definition.modelSizeH = 128;
			definition.modelSizeY = 128;
			definition.plane = 0;
			definition.offsetX = 0;
			definition.offsetH = 0;
			definition.offsetY = 0;
			definition.aBoolean736 = false;
			definition.isSolidObject = false;
			definition.anInt760 = 1;
			definition.varbitIndex = -1;
			definition.configID = -1;
			definition.configObjectIDs = null;
			break;
		case 11922:
			definition.objectModelIDs = new int[] { 45748 };
			definition.anIntArray776 = null;
			definition.name = "Elder evil tree";
			definition.modifiedModelColors = new int[] { 0 };
			definition.originalModelColors = new int[] { 1 };
			definition.sizeX = 3;
			definition.sizeY = 3;
			definition.isUnwalkable = true;
			definition.aBoolean757 = true;
			definition.hasActions = true;
			definition.adjustToTerrain = false;
			definition.nonFlatShading = false;
			definition.aBoolean764 = false;
			definition.animationID = 1134;
			definition.anInt775 = 16;
			definition.brightness = 15;
			definition.contrast = 0;
			definition.actions = new String[] { "Chop", };
			definition.mapFunctionID = -1;
			definition.mapSceneID = -1;
			definition.aBoolean751 = false;
			definition.aBoolean779 = true;
			definition.modelSizeX = 128;
			definition.modelSizeH = 128;
			definition.modelSizeY = 128;
			definition.plane = 0;
			definition.offsetX = 0;
			definition.offsetH = 0;
			definition.offsetY = 0;
			definition.aBoolean736 = false;
			definition.isSolidObject = false;
			definition.anInt760 = 1;
			definition.varbitIndex = -1;
			definition.configID = -1;
			definition.configObjectIDs = null;
			break;
		case 11919:
			definition.objectModelIDs = new int[] { 45750 };
			definition.anIntArray776 = null;
			definition.name = "Evil magic tree";
			definition.modifiedModelColors = new int[] { 0 };
			definition.originalModelColors = new int[] { 1 };
			definition.sizeX = 3;
			definition.sizeY = 3;
			definition.isUnwalkable = true;
			definition.aBoolean757 = true;
			definition.hasActions = true;
			definition.adjustToTerrain = false;
			definition.nonFlatShading = false;
			definition.aBoolean764 = false;
			definition.animationID = 1679;
			definition.anInt775 = 16;
			definition.brightness = 15;
			definition.contrast = 0;
			definition.actions = new String[] { "Chop", };
			definition.mapFunctionID = -1;
			definition.mapSceneID = -1;
			definition.aBoolean751 = false;
			definition.aBoolean779 = true;
			definition.modelSizeX = 128;
			definition.modelSizeH = 128;
			definition.modelSizeY = 128;
			definition.plane = 0;
			definition.offsetX = 0;
			definition.offsetH = 0;
			definition.offsetY = 0;
			definition.aBoolean736 = false;
			definition.isSolidObject = false;
			definition.anInt760 = 1;
			definition.varbitIndex = -1;
			definition.configID = -1;
			definition.configObjectIDs = null;
			break;
		case 11916:
			definition.objectModelIDs = new int[] { 45757 };
			definition.anIntArray776 = null;
			definition.name = "Evil yew tree";
			definition.modifiedModelColors = new int[] { 0 };
			definition.originalModelColors = new int[] { 1 };
			definition.sizeX = 3;
			definition.sizeY = 3;
			definition.isUnwalkable = true;
			definition.aBoolean757 = true;
			definition.hasActions = true;
			definition.adjustToTerrain = false;
			definition.nonFlatShading = false;
			definition.aBoolean764 = false;
			definition.animationID = 1685;
			definition.anInt775 = 16;
			definition.brightness = 15;
			definition.contrast = 0;
			definition.actions = new String[] { "Chop", };
			definition.mapFunctionID = -1;
			definition.mapSceneID = -1;
			definition.aBoolean751 = false;
			definition.aBoolean779 = true;
			definition.modelSizeX = 128;
			definition.modelSizeH = 128;
			definition.modelSizeY = 128;
			definition.plane = 0;
			definition.offsetX = 0;
			definition.offsetH = 0;
			definition.offsetY = 0;
			definition.aBoolean736 = false;
			definition.isSolidObject = false;
			definition.anInt760 = 1;
			definition.varbitIndex = -1;
			definition.configID = -1;
			definition.configObjectIDs = null;
			break;
		case 4446:
			definition.objectModelIDs = new int[5];
			definition.objectModelIDs[0] = 669;
			definition.objectModelIDs[1] = 670;
			definition.objectModelIDs[2] = 671;
			definition.objectModelIDs[3] = 672;
			definition.objectModelIDs[4] = 673;
			break;
		case 11444:
			definition.objectModelIDs = new int[] { 45745 };
			definition.anIntArray776 = null;
			definition.name = "Evil maple tree";
			definition.modifiedModelColors = new int[] { 0 };
			definition.originalModelColors = new int[] { 1 };
			definition.sizeX = 3;
			definition.sizeY = 3;
			definition.isUnwalkable = true;
			definition.aBoolean757 = true;
			definition.hasActions = true;
			definition.adjustToTerrain = false;
			definition.nonFlatShading = false;
			definition.aBoolean764 = false;
			definition.animationID = 1682;
			definition.anInt775 = 16;
			definition.brightness = 15;
			definition.contrast = 0;
			definition.actions = new String[] { "Chop", };
			definition.mapFunctionID = -1;
			definition.mapSceneID = -1;
			definition.aBoolean751 = false;
			definition.aBoolean779 = true;
			definition.modelSizeX = 128;
			definition.modelSizeH = 128;
			definition.modelSizeY = 128;
			definition.plane = 0;
			definition.offsetX = 0;
			definition.offsetH = 0;
			definition.offsetY = 0;
			definition.aBoolean736 = false;
			definition.isSolidObject = false;
			definition.anInt760 = 1;
			definition.varbitIndex = -1;
			definition.configID = -1;
			definition.configObjectIDs = null;
			break;
		case 11441:
			definition.objectModelIDs = new int[] { 45762 };
			definition.anIntArray776 = null;
			definition.name = "Evil willow tree";
			definition.modifiedModelColors = new int[] { 0 };
			definition.originalModelColors = new int[] { 1 };
			definition.sizeX = 3;
			definition.sizeY = 3;
			definition.isUnwalkable = true;
			definition.aBoolean757 = true;
			definition.hasActions = true;
			definition.adjustToTerrain = false;
			definition.nonFlatShading = false;
			definition.aBoolean764 = false;
			definition.animationID = 1688;
			definition.anInt775 = 16;
			definition.brightness = 15;
			definition.contrast = 0;
			definition.actions = new String[] { "Chop", };
			definition.mapFunctionID = -1;
			definition.mapSceneID = -1;
			definition.aBoolean751 = false;
			definition.aBoolean779 = true;
			definition.modelSizeX = 128;
			definition.modelSizeH = 128;
			definition.modelSizeY = 128;
			definition.plane = 0;
			definition.offsetX = 0;
			definition.offsetH = 0;
			definition.offsetY = 0;
			definition.aBoolean736 = false;
			definition.isSolidObject = false;
			definition.anInt760 = 1;
			definition.varbitIndex = -1;
			definition.configID = -1;
			definition.configObjectIDs = null;
			break;
		case 11437:
			definition.objectModelIDs = new int[] { 45765 };
			definition.anIntArray776 = null;
			definition.name = "Evil oak tree";
			definition.modifiedModelColors = new int[] { 0 };
			definition.originalModelColors = new int[] { 1 };
			definition.sizeX = 3;
			definition.sizeY = 3;
			definition.isUnwalkable = true;
			definition.aBoolean757 = true;
			definition.hasActions = true;
			definition.adjustToTerrain = false;
			definition.nonFlatShading = false;
			definition.aBoolean764 = false;
			definition.animationID = 1691;
			definition.anInt775 = 16;
			definition.brightness = 15;
			definition.contrast = 0;
			definition.actions = new String[] { "Chop", };
			definition.mapFunctionID = -1;
			definition.mapSceneID = -1;
			definition.aBoolean751 = false;
			definition.aBoolean779 = true;
			definition.modelSizeX = 128;
			definition.modelSizeH = 128;
			definition.modelSizeY = 128;
			definition.plane = 0;
			definition.offsetX = 0;
			definition.offsetH = 0;
			definition.offsetY = 0;
			definition.aBoolean736 = false;
			definition.isSolidObject = false;
			definition.anInt760 = 1;
			definition.varbitIndex = -1;
			definition.configID = -1;
			definition.configObjectIDs = null;
			break;
		case 11435:
			definition.objectModelIDs = new int[] { 45769 };
			definition.anIntArray776 = null;
			definition.name = "Evil tree";
			definition.modifiedModelColors = new int[] { 0 };
			definition.originalModelColors = new int[] { 1 };
			definition.sizeX = 3;
			definition.sizeY = 3;
			definition.isUnwalkable = true;
			definition.aBoolean757 = true;
			definition.hasActions = true;
			definition.adjustToTerrain = false;
			definition.nonFlatShading = false;
			definition.aBoolean764 = false;
			definition.animationID = 1676;
			definition.anInt775 = 16;
			definition.brightness = 15;
			definition.contrast = 0;
			definition.actions = new String[] { "Chop", };
			definition.mapFunctionID = -1;
			definition.mapSceneID = -1;
			definition.aBoolean751 = false;
			definition.aBoolean779 = true;
			definition.modelSizeX = 128;
			definition.modelSizeH = 128;
			definition.modelSizeY = 128;
			definition.plane = 0;
			definition.offsetX = 0;
			definition.offsetH = 0;
			definition.offsetY = 0;
			definition.aBoolean736 = false;
			definition.isSolidObject = false;
			definition.anInt760 = 1;
			definition.varbitIndex = -1;
			definition.configID = -1;
			definition.configObjectIDs = null;
			break;
		case 11433:
			definition.objectModelIDs = new int[] { 45743 };
			definition.anIntArray776 = null;
			definition.name = "Evil root";
			definition.modifiedModelColors = new int[] { 0 };
			definition.originalModelColors = new int[] { 1 };
			definition.sizeX = 1;
			definition.sizeY = 1;
			definition.isUnwalkable = true;
			definition.aBoolean757 = true;
			definition.hasActions = true;
			definition.adjustToTerrain = false;
			definition.nonFlatShading = false;
			definition.aBoolean764 = false;
			definition.animationID = 353;
			definition.anInt775 = 16;
			definition.brightness = 15;
			definition.contrast = 0;
			definition.actions = new String[] { "Chop", };
			definition.mapFunctionID = -1;
			definition.mapSceneID = -1;
			definition.aBoolean751 = false;
			definition.aBoolean779 = true;
			definition.modelSizeX = 128;
			definition.modelSizeH = 128;
			definition.modelSizeY = 128;
			definition.plane = 0;
			definition.offsetX = 0;
			definition.offsetH = 0;
			definition.offsetY = 0;
			definition.aBoolean736 = false;
			definition.isSolidObject = false;
			definition.anInt760 = 1;
			definition.varbitIndex = -1;
			definition.configID = -1;
			definition.configObjectIDs = null;
			break;
		case 11432:
			definition.objectModelIDs = new int[] { 45743 };
			definition.anIntArray776 = null;
			definition.name = "Evil root";
			definition.modifiedModelColors = new int[] { 0 };
			definition.originalModelColors = new int[] { 1 };
			definition.sizeX = 1;
			definition.sizeY = 1;
			definition.isUnwalkable = true;
			definition.aBoolean757 = true;
			definition.hasActions = true;
			definition.adjustToTerrain = false;
			definition.nonFlatShading = false;
			definition.aBoolean764 = false;
			definition.animationID = 354;
			definition.anInt775 = 16;
			definition.brightness = 15;
			definition.contrast = 0;
			definition.actions = new String[] { "Chop", };
			definition.mapFunctionID = -1;
			definition.mapSceneID = -1;
			definition.aBoolean751 = false;
			definition.aBoolean779 = true;
			definition.modelSizeX = 128;
			definition.modelSizeH = 128;
			definition.modelSizeY = 128;
			definition.plane = 0;
			definition.offsetX = 0;
			definition.offsetH = 0;
			definition.offsetY = 0;
			definition.aBoolean736 = false;
			definition.isSolidObject = false;
			definition.anInt760 = 1;
			definition.varbitIndex = -1;
			definition.configID = -1;
			definition.configObjectIDs = null;
			break;
		case 11431:
			definition.objectModelIDs = new int[] { 45743 };
			definition.anIntArray776 = null;
			definition.name = "Evil root";
			definition.modifiedModelColors = new int[] { 0 };
			definition.originalModelColors = new int[] { 1 };
			definition.sizeX = 1;
			definition.sizeY = 1;
			definition.isUnwalkable = true;
			definition.aBoolean757 = true;
			definition.hasActions = true;
			definition.adjustToTerrain = false;
			definition.nonFlatShading = false;
			definition.aBoolean764 = false;
			definition.animationID = 353;
			definition.anInt775 = 16;
			definition.brightness = 15;
			definition.contrast = 0;
			definition.actions = new String[] { "Chop", };
			definition.mapFunctionID = -1;
			definition.mapSceneID = -1;
			definition.aBoolean751 = false;
			definition.aBoolean779 = true;
			definition.modelSizeX = 128;
			definition.modelSizeH = 128;
			definition.modelSizeY = 128;
			definition.plane = 0;
			definition.offsetX = 0;
			definition.offsetH = 0;
			definition.offsetY = 0;
			definition.aBoolean736 = false;
			definition.isSolidObject = false;
			definition.anInt760 = 1;
			definition.varbitIndex = -1;
			definition.configID = -1;
			definition.configObjectIDs = null;
			break;
		case 11430:
			definition.objectModelIDs = new int[] { 45743 };
			definition.anIntArray776 = null;
			definition.name = "Evil root";
			definition.modifiedModelColors = new int[] { 0 };
			definition.originalModelColors = new int[] { 1 };
			definition.sizeX = 1;
			definition.sizeY = 1;
			definition.isUnwalkable = true;
			definition.aBoolean757 = true;
			definition.hasActions = true;
			definition.adjustToTerrain = false;
			definition.nonFlatShading = false;
			definition.aBoolean764 = false;
			definition.animationID = 354;
			definition.anInt775 = 16;
			definition.brightness = 15;
			definition.contrast = 0;
			definition.actions = new String[] { "Chop", };
			definition.mapFunctionID = -1;
			definition.mapSceneID = -1;
			definition.aBoolean751 = false;
			definition.aBoolean779 = true;
			definition.modelSizeX = 128;
			definition.modelSizeH = 128;
			definition.modelSizeY = 128;
			definition.plane = 0;
			definition.offsetX = 0;
			definition.offsetH = 0;
			definition.offsetY = 0;
			definition.aBoolean736 = false;
			definition.isSolidObject = false;
			definition.anInt760 = 1;
			definition.varbitIndex = -1;
			definition.configID = -1;
			definition.configObjectIDs = null;
			break;
		case 11429:
			definition.objectModelIDs = new int[] { 45743 };
			definition.anIntArray776 = null;
			definition.name = "Evil root";
			definition.modifiedModelColors = new int[] { 0 };
			definition.originalModelColors = new int[] { 1 };
			definition.sizeX = 1;
			definition.sizeY = 1;
			definition.isUnwalkable = true;
			definition.aBoolean757 = true;
			definition.hasActions = true;
			definition.adjustToTerrain = false;
			definition.nonFlatShading = false;
			definition.aBoolean764 = false;
			definition.animationID = 353;
			definition.anInt775 = 16;
			definition.brightness = 15;
			definition.contrast = 0;
			definition.actions = new String[] { "Chop", };
			definition.mapFunctionID = -1;
			definition.mapSceneID = -1;
			definition.aBoolean751 = false;
			definition.aBoolean779 = true;
			definition.modelSizeX = 128;
			definition.modelSizeH = 128;
			definition.modelSizeY = 128;
			definition.plane = 0;
			definition.offsetX = 0;
			definition.offsetH = 0;
			definition.offsetY = 0;
			definition.aBoolean736 = false;
			definition.isSolidObject = false;
			definition.anInt760 = 1;
			definition.varbitIndex = -1;
			definition.configID = -1;
			definition.configObjectIDs = null;
			break;
		case 11428:
			definition.objectModelIDs = new int[] { 45743 };
			definition.anIntArray776 = null;
			definition.name = "Evil root";
			definition.modifiedModelColors = new int[] { 0 };
			definition.originalModelColors = new int[] { 1 };
			definition.sizeX = 1;
			definition.sizeY = 1;
			definition.isUnwalkable = true;
			definition.aBoolean757 = true;
			definition.hasActions = true;
			definition.adjustToTerrain = false;
			definition.nonFlatShading = false;
			definition.aBoolean764 = false;
			definition.animationID = 354;
			definition.anInt775 = 16;
			definition.brightness = 15;
			definition.contrast = 0;
			definition.actions = new String[] { "Chop", };
			definition.mapFunctionID = -1;
			definition.mapSceneID = -1;
			definition.aBoolean751 = false;
			definition.aBoolean779 = true;
			definition.modelSizeX = 128;
			definition.modelSizeH = 128;
			definition.modelSizeY = 128;
			definition.plane = 0;
			definition.offsetX = 0;
			definition.offsetH = 0;
			definition.offsetY = 0;
			definition.aBoolean736 = false;
			definition.isSolidObject = false;
			definition.anInt760 = 1;
			definition.varbitIndex = -1;
			definition.configID = -1;
			definition.configObjectIDs = null;
			break;
		case 11427:
			definition.objectModelIDs = new int[] { 45743 };
			definition.anIntArray776 = null;
			definition.name = "Evil root";
			definition.modifiedModelColors = new int[] { 0 };
			definition.originalModelColors = new int[] { 1 };
			definition.sizeX = 1;
			definition.sizeY = 1;
			definition.isUnwalkable = true;
			definition.aBoolean757 = true;
			definition.hasActions = true;
			definition.adjustToTerrain = false;
			definition.nonFlatShading = false;
			definition.aBoolean764 = false;
			definition.animationID = 353;
			definition.anInt775 = 16;
			definition.brightness = 15;
			definition.contrast = 0;
			definition.actions = new String[] { "Chop", };
			definition.mapFunctionID = -1;
			definition.mapSceneID = -1;
			definition.aBoolean751 = false;
			definition.aBoolean779 = true;
			definition.modelSizeX = 128;
			definition.modelSizeH = 128;
			definition.modelSizeY = 128;
			definition.plane = 0;
			definition.offsetX = 0;
			definition.offsetH = 0;
			definition.offsetY = 0;
			definition.aBoolean736 = false;
			definition.isSolidObject = false;
			definition.anInt760 = 1;
			definition.varbitIndex = -1;
			definition.configID = -1;
			definition.configObjectIDs = null;
			break;
		case 11426:
			definition.objectModelIDs = new int[] { 45743 };
			definition.anIntArray776 = null;
			definition.name = "Evil root";
			definition.modifiedModelColors = new int[] { 0 };
			definition.originalModelColors = new int[] { 1 };
			definition.sizeX = 1;
			definition.sizeY = 1;
			definition.isUnwalkable = true;
			definition.aBoolean757 = true;
			definition.hasActions = true;
			definition.adjustToTerrain = false;
			definition.nonFlatShading = false;
			definition.aBoolean764 = false;
			definition.animationID = 354;
			definition.anInt775 = 16;
			definition.brightness = 15;
			definition.contrast = 0;
			definition.actions = new String[] { "Chop", };
			definition.mapFunctionID = -1;
			definition.mapSceneID = -1;
			definition.aBoolean751 = false;
			definition.aBoolean779 = true;
			definition.modelSizeX = 128;
			definition.modelSizeH = 128;
			definition.modelSizeY = 128;
			definition.plane = 0;
			definition.offsetX = 0;
			definition.offsetH = 0;
			definition.offsetY = 0;
			definition.aBoolean736 = false;
			definition.isSolidObject = false;
			definition.anInt760 = 1;
			definition.varbitIndex = -1;
			definition.configID = -1;
			definition.configObjectIDs = null;
			break;

		}
	}

	public static void dumpObjectModels() {
		dumpObjectModels(streamIndices);
		dumpObjectModels(streamIndices667);
	}

	public static void dumpObjectModels(int[] indices) {
		int dumped = 0, exceptions = 0;
		for (int i = 0; i < indices.length - 1; i++) {
			ObjectDefinition object = forID(i);
			if (object == null)
				continue;
			if (object.objectModelIDs == null)
				continue;
			for (int model : object.objectModelIDs) {
				try {
					byte abyte[] = clientInstance.cacheIndices[1].get(model);
					File modelFile = new File(
							Signlink.getCacheDirectory().toString() + "/objectModels/" + model + ".gz");
					FileOutputStream fos = new FileOutputStream(modelFile);
					fos.write(abyte);
					fos.close();
					dumped++;
				} catch (Exception e) {
					exceptions++;
				}
			}
		}
		System.out.println("Dumped " + dumped + " object models with " + exceptions + " exceptions.");
	}
}