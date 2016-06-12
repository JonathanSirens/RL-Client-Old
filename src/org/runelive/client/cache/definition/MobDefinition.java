package org.runelive.client.cache.definition;

import org.runelive.Configuration;
import org.runelive.client.Client;
import org.runelive.client.FrameReader;
import org.runelive.client.List;
import org.runelive.client.cache.Archive;
import org.runelive.client.io.ByteBuffer;
import org.runelive.client.world.Model;

public final class MobDefinition {

	private static MobDefinition[] cache;
	private static int cacheIndex;
	public static Client clientInstance;
	public static List mruNodes = new List(30);
	private static ByteBuffer buffer;
	private static int[] streamIndices;

	public static MobDefinition get(int id) {
		for (int i = 0; i < 20; i++) {
			if (cache[i].id == id) {
				return cache[i];
			}
		}

		cacheIndex = (cacheIndex + 1) % 20;
		MobDefinition definition = cache[cacheIndex] = new MobDefinition();
		buffer.position = streamIndices[id];
		definition.id = id;
		definition.readValues(buffer);
		switch (id) {
        case 83:
        	System.out.println(""+definition.npcModels[1]);
        	break;
		case 5866:
			definition.name = "Cerberus";
			definition.npcModels = new int[] { 29270 };
			definition.npcSizeInSquares = 5;
			definition.actions = new String[] { null, "Attack", null, null, null };
			definition.standAnimation = 4484;
			definition.walkAnimation = 4488;
			definition.drawYellowDotOnMap = true;
			definition.combatLevel = 318;
			definition.adjustVertextPointsXOrY = 128;
			definition.adjustVertextPointZ = 128;
		break;
		case 2722:
			MobDefinition spot1 = get(318);
			definition.npcSizeInSquares = spot1.npcSizeInSquares;
			definition.adjustVertextPointsXOrY = spot1.adjustVertextPointsXOrY;
			definition.adjustVertextPointZ = spot1.adjustVertextPointZ;
			definition.actions = new String[] { "Net", null, null, null, null };
			break;
		case 2724:
			MobDefinition spot2 = get(318);
			definition.npcSizeInSquares = spot2.npcSizeInSquares;
			definition.adjustVertextPointsXOrY = spot2.adjustVertextPointsXOrY;
			definition.adjustVertextPointZ = spot2.adjustVertextPointZ;
			definition.actions = new String[] { "Bait", null, null, null, null };
			break;
		case 130:
			definition.name = "Ganodermic beast";
			definition.combatLevel = 280;
			definition.npcSizeInSquares = 3;
			definition.drawYellowDotOnMap = true;
			definition.adjustVertextPointsXOrY = 128;
			definition.adjustVertextPointZ = 128;
			definition.npcModels = new int[] {13888};
			definition.actions = new String[] { null, "Attack", null, null, null };
			definition.degreesToTurn = 32;
			definition.standAnimation = 4553;
			definition.walkAnimation = 4555;
			break;
		case 501:
			definition.name = "Queen Revenant";
			break;
		case 3019:
			MobDefinition spot3 = get(318);
			definition.npcSizeInSquares = spot3.npcSizeInSquares;
			definition.adjustVertextPointsXOrY = spot3.adjustVertextPointsXOrY;
			definition.adjustVertextPointZ = spot3.adjustVertextPointZ;
			definition.actions = new String[] { "Net", null, null, null, null };
			break;
		case 5867:
			definition.name = "Summoned Soul";
			definition.npcModels = new int[] { 29268 };
			definition.npcSizeInSquares = 1;
			definition.actions = new String[] { null, null, null, null, null };
			definition.standAnimation = 4505;
			definition.walkAnimation = 4505;
			definition.drawYellowDotOnMap = true;
			definition.combatLevel = 96;
			definition.adjustVertextPointsXOrY = 140;
			definition.adjustVertextPointZ = 140;
		break;
		case 5868:
			definition.name = "Summoned Soul";
			definition.npcModels = new int[] { 29267 };
			definition.npcSizeInSquares = 1;
			definition.actions = new String[] { null, null, null, null, null };
			definition.standAnimation = 4505;
			definition.walkAnimation = 4505;
			definition.drawYellowDotOnMap = true;
			definition.combatLevel = 96;
			definition.adjustVertextPointsXOrY = 140;
			definition.adjustVertextPointZ = 140;
		break;

		case 5869:
			definition.name = "Summoned Soul";
			definition.npcModels = new int[] { 29266 };
			definition.npcSizeInSquares = 1;
			definition.actions = new String[] { null, null, null, null, null };
			definition.standAnimation = 4505;
			definition.walkAnimation = 4505;
			definition.drawYellowDotOnMap = true;
			definition.combatLevel = 79;
			definition.adjustVertextPointsXOrY = 140;
			definition.adjustVertextPointZ = 140;
		break;
		case 5870:
				definition.name = "Hell Puppy";
				definition.npcModels = new int[] { 29270 };
				definition.npcSizeInSquares = 2;
				definition.actions = new String[5];
				definition.actions[0] = "Pick-up";
				definition.standAnimation = 4484;
				definition.walkAnimation = 4488;
				definition.drawYellowDotOnMap = true;
				definition.combatLevel = 318;
				definition.adjustVertextPointZ = 40;
				definition.adjustVertextPointsXOrY = 40;
				break;
		case 2000:
			definition.npcModels = new int[2];
			definition.npcModels[0] = 28294;
			definition.npcModels[1] = 28295;
			definition.name = "Venenatis";
			definition.actions = new String[] {null, "Attack", null, null, null};
			definition.adjustVertextPointsXOrY = 200;
			definition.adjustVertextPointZ = 200;
			MobDefinition ven = get(60);
			definition.standAnimation = ven.standAnimation;
			definition.walkAnimation = ven.walkAnimation;
			definition.combatLevel = 464;
			definition.npcSizeInSquares = 5;
			break;
		case 2127:
			definition.name = "Player Owned Shop Trader";
			definition.actions = new String[] {"Talk-To", null, "Search through stores", "Open your store", "Open someone's store"};
			break;
		case 2001:
			definition.npcModels = new int[1];
			definition.npcModels[0] = 28293;
			definition.name = "Scorpia";
			definition.actions = new String[] {null, "Attack", null, null, null};
			MobDefinition scor = get(107);
			definition.standAnimation = scor.standAnimation;
			definition.walkAnimation = scor.walkAnimation;
			definition.combatLevel = 464;
			definition.npcSizeInSquares = 3;
			break;
		case 2002:
			definition.npcModels = new int[1];
			definition.npcModels[0] = 28299;
			definition.name = "Vet'ion";
			definition.actions = new String[] {null, "Attack", null, null, null};
			MobDefinition vet = get(90);
			definition.standAnimation = vet.standAnimation;
			definition.walkAnimation = vet.walkAnimation;
			definition.combatLevel = 464;
			break;
			case 1385:
				definition.actions = new String[] {"Talk-to", null, "Change Home", null, null};
				break;
		case 4646:
			definition.actions = new String[] {"Talk-to", null, "Teleport", null, null};
			break;
		case 4663:
			definition.actions = new String[] {"Talk-to", null, null, null, null};
			break;
		case 2947:
			definition.actions = new String[] {"Pickpocket", null, null, null, null};
			break;
		case 4000:
			definition.copy(get(50));
			definition.name = "Queen White Dragon";
			definition.originalModelColours = new int[] {10502, 43906, 11140, 10378, 0, 11138, 809, 33};
			definition.changedModelColours = new int[] {100, 100, 226770, 100, 100, 100, 226770, 226770};
			definition.combatLevel = 224;
			break;
		case 4001:
			definition.copy(get(4000));
			definition.name = "Queen White Dragon";
			definition.actions = new String[5];
			definition.actions[0] = "Pick-up";
			definition.adjustVertextPointZ = 63;
			definition.adjustVertextPointsXOrY = 63;
			definition.npcSizeInSquares = 3;
			definition.originalModelColours = new int[] {10502, 43906, 11140, 10378, 0, 11138, 809, 33};
			definition.changedModelColours = new int[] {100, 100, 226770, 100, 100, 100, 226770, 226770};
			definition.combatLevel = 224;
			break;
		case 4002:
			definition.copy(get(2633));
			break;
		case 2003:
			definition.npcModels = new int[1];
			definition.npcModels[0] = 28281;
			definition.name = "Kraken";
            definition.actions = new String[] {null, "Attack", null, null, null};
            MobDefinition eld = get(3847);
            definition.npcModels = new int[1];
            definition.npcModels[0] = 28233;
            definition.combatLevel = 291;
            definition.standAnimation = 3989;
            definition.walkAnimation = eld.walkAnimation;
            definition.adjustVertextPointsXOrY = definition.adjustVertextPointZ = 84;
            break;
		case 2005:
			definition.npcModels = new int[2];
			definition.npcModels[0] = 28294;
			definition.npcModels[1] = 28295;
			definition.name = "Venenatis";
			definition.actions = new String[] {"Pick-up", null, null, null, null};
			MobDefinition ven2 = get(60);
			definition.standAnimation = ven2.standAnimation;
			definition.walkAnimation = ven2.walkAnimation;
			definition.combatLevel = 464;
			definition.actions[0] = "Pick-up";
			definition.adjustVertextPointZ = 63;
			definition.adjustVertextPointsXOrY = 63;
			definition.npcSizeInSquares = 3;
			break;	
		case 1472:
			definition.adjustVertextPointZ += 25;
			definition.adjustVertextPointsXOrY += 25;
			definition.name = "Todd the wrath";
			definition.combatLevel = 583;
			break;
		case 2004:
			definition.npcModels = new int[1];
			definition.npcModels[0] = 28231;
			definition.name = "Cave kraken";
			definition.actions = new String[] {null, "Attack", null, null, null};
			MobDefinition cave = get(3847);
			definition.npcModels = new int[1];
			definition.npcModels[0] = 28233;
			definition.combatLevel = 127;
			definition.standAnimation = 3989;
			definition.walkAnimation = cave.walkAnimation;
			definition.adjustVertextPointsXOrY = definition.adjustVertextPointZ = 42;
			break;
		case 457:
			definition.name = "Ghost Town Citizen";
			definition.actions = new String[]{"Talk-to", null, "Teleport", null, null};
			break;
		case 198:
			definition.name = "Master Chief";
			definition.actions = new String[]{"Talk-to", null, "Spawn Last Boss", null, null};
			break;
		case 1093:
			definition.name = "Billy the Goat";
			definition.actions = new String[]{"Talk-to", null, null, null, null};
			break;
		case 5417:
			definition.combatLevel = 210;
			break;
		case 5418:
			definition.combatLevel = 90;
			break;
		case 6715:
			definition.combatLevel = 91;
			break;
		case 6716:
			definition.combatLevel = 128;
			break;
		case 6701:
			definition.combatLevel = 173;
			break;
		case 6725:
			definition.combatLevel = 224;
			break;
		case 6691:
			definition.npcSizeInSquares = 2;
			definition.combatLevel = 301;
			break;
		case 8710:
		case 8707:
		case 8706:
		case 8705:
			definition.name = "Musician";
			definition.actions = new String[]{"Listen-to", null, null, null, null};
			break;
		case 947:
			definition.name = "Grand Exchange";
			definition.actions = new String[]{"Open", null, null, null, null};
			break;
		case 9939:
			definition.combatLevel = 607;
			break;
		case 688:
			definition.name = "Archer";
			break;
		case 4540:
			definition.combatLevel = 299;
			break;
		case 3101:
			definition.adjustVertextPointZ = definition.adjustVertextPointsXOrY = 80;
			definition.npcSizeInSquares = 1;
			definition.actions = new String[]{"Talk-to", null, "Start", "Rewards", null};
			break;
		case 6222:
			definition.name = "Kree'arra";
			definition.npcSizeInSquares = 5;
			definition.standAnimation = 6972;
			definition.walkAnimation = 6973;
			definition.actions = new String[]{null, "Attack", null, null, null};
			definition.adjustVertextPointZ = definition.adjustVertextPointsXOrY = 110;
			break;
		case 6203:
			definition.npcModels = new int[] {27768, 27773, 27764, 27765, 27770};
			definition.name = "K'ril Tsutsaroth";
			definition.npcSizeInSquares = 5;
			definition.standAnimation = 6943;
			definition.walkAnimation = 6942;
			definition.actions = new String[]{null, "Attack", null, null, null};
			definition.adjustVertextPointZ = definition.adjustVertextPointsXOrY = 110;
			break;
		case 1610:
		case 491:
		case 10216:
			definition.actions = new String[]{null, "Attack", null, null, null};
			break;
		case 7969:
			definition.actions = new String[]{"Talk-to", null, "Trade", null, null};
			break;
		case 1382:
			definition.name = "Glacor";
			definition.npcModels = new int[]{58940};
			definition.npcSizeInSquares = 3;
			//	definition.anInt86 = 475;
			definition.adjustVertextPointsXOrY = definition.adjustVertextPointZ = 180;
			definition.standAnimation = 10869;
			definition.walkAnimation = 10867;
			definition.actions = new String[]{null, "Attack", null, null, null};
			definition.combatLevel = 123;
			definition.drawYellowDotOnMap = true;
			definition.combatLevel = 188;
			break;
		case 4249:
			definition.name = "Gambler";
			break;
		case 6970:
			definition.actions = new String[] {"Trade", null, "Exchange Shards", null, null};
			break;
		case 4657:
			definition.actions = new String[] {"Talk-to", null, "Claim Items", "Check Total", "Teleport"};
			break;
		case 605:
			definition.actions = new String[] {"Talk-to", null, "Vote Rewards", "Loyalty Titles", null};
			break;
		case 8591:
			definition.actions = new String[] {"Talk-to", null, "Trade", null, null};
			break;
		case 316:
		case 315:
		case 310:
		case 314:
		case 312:
		case 313:
			definition.adjustVertextPointsXOrY = 30;
			break;
			case 309:
				definition.name = "Rocky shoal";
				definition.actions = new String[] {"Bait", null, null, null, null};
				definition.adjustVertextPointsXOrY = 30;
				break;
		case 318:
			definition.adjustVertextPointsXOrY = 30;
			definition.actions = new String[] {"Net", null, "Lure", null, null};
			break;
		case 805:
			definition.actions = new String[] {"Trade", null, "Tan hide", null, null};
			break;
		case 461:
		case 844:
		case 650:
		case 5112:
		case 3789:
		case 802:
		case 520:
		case 521:
		case 11226:
			definition.actions = new String[] {"Trade", null, null, null, null};
			break;
		case 653:
			definition.actions = new String[] {"Trade", null, null, null, null};
			break;
		case 8022:
		case 8028:
			String color = id == 8022 ? "Yellow" : "Green";
			definition.name = ""+color+" energy source";
			definition.actions = new String[] {"Siphon", null, null, null, null};
			break;
		case 8444:
			definition.actions = new String[5];
			definition.actions[0] = "Trade";
			break;
		case 2579:
			definition.name = "Max";
			definition.description = "One of RuneLive's completionists.".getBytes();
			definition.combatLevel = 1337;
			definition.actions = new String[5];
			definition.actions[0] = "Talk-to";
			definition.actions[2] = "Trade";
			definition.npcModels = new int[7];
			definition.npcModels[0] = 65289; //Hat
			definition.npcModels[1] = 62746;
			definition.npcModels[2] = 62743;
			definition.npcModels[3] = 65300; //Cape
			definition.npcModels[4] = 13319;
			definition.npcModels[5] = 27738; //Boots
			definition.npcModels[6] = 20147;
			definition.standAnimation = 808;
			definition.walkAnimation = 819;
			definition.dialogueModels = MobDefinition.get(517).dialogueModels;
			break;
		case 6830:
		case 6841:
		case 6796:
		case 7331:
		case 6831:
		case 7361:
		case 6847:
		case 6872:
		case 7353:
		case 6835:
		case 6845:
		case 6808:
		case 7370:
		case 7333:
		case 7351:
		case 7367:
		case 6853:
		case 6855:
		case 6857:
		case 6859:
		case 6861:
		case 6863:
		case 9481:
		case 6827:
		case 6889:
		case 6813:
		case 6817:
		case 7372:
		case 6839:
		case 8575:
		case 7345:
		case 6799:
		case 7335:
		case 7347:
		case 6800:
		case 9488:
		case 6804:
		case 6822:
		case 6849:
		case 7355:
		case 7357:
		case 7359:
		case 7341:
		case 7329:
		case 7339:
		case 7349:
		case 7375:
		case 7343:
		case 6820:
		case 6865:
		case 6809:
		case 7363:
		case 7337:
		case 7365:
		case 6991:
		case 6992:
		case 6869:
		case 6818:
		case 6843:
		case 6823:
		case 7377:
		case 6887:
		case 6885:
		case 6883:
		case 6881:
		case 6879:
		case 6877:
		case 6875:
		case 6833:
		case 6851:
		case 5079:
		case 5080:
		case 6824:
			definition.actions = new String[] {null, null, null, null, null};
			break;
		case 6806: // thorny snail
		case 6807:
		case 6994: // spirit kalphite
		case 6995:
		case 6867: // bull ant
		case 6868:
		case 6794: // spirit terrorbird
		case 6795:
		case 6815: // war tortoise
		case 6816:
		//case 6874:// pack yak
		//case 6873: // pack yak
		case 3594: // yak
		case 3590: // war tortoise
		case 3596: // terrorbird
			definition.actions = new String[] {"Store", null, null, null, null};
			break;
		case 548:
			definition.actions = new String[] {"Trade", null, null, null, null};
			break;
		case 3299:
		case 437:
			definition.actions = new String[] {"Trade", null, null, null, null};
			break;
		case 1265:
		case 1267:
		case 8459:
			definition.drawYellowDotOnMap = true;
			break;
		case 961:
			definition.actions = new String[] {null, null, "Buy Consumables", "Restore Stats", null};
			definition.name = "Healer";
			break;
		case 705:
			definition.actions = new String[] {null, null, "Buy Armour", "Buy Weapons", "Buy Jewelries"};
			definition.name = "Warrior";
			break;
		case 1861:
			definition.actions = new String[] {null, null, "Buy Equipment", "Buy Ammunition", null};
			definition.name = "Archer";
			break;
		case 946:
			definition.actions = new String[] {null, null, "Buy Equipment", "Buy Runes", "Quick-buy"};
			definition.name = "Mage";
			break;
			case 6537:
				definition.actions = new String[] {null, null, "Talk-to", "Sell-Artifacts", null};
				break;
		case 2253:
			definition.actions = new String[] {null, "Buy Skillcapes", "Buy Skillcapes (t)", "Buy Skillcapes (m)", "Buy Hoods"};
			break;
		case 3147:
			definition.actions = new String[] {"Trade", null, null, null, null};
			definition.name = "Lazim";
			break;
		case 5093:
			definition.actions = new String[] {"Trade", null, null, null, null};
			definition.name = "Boss Point Store";
			break;
		case 291:
			definition.actions = new String[] {"Pickpocket", null, null, null, null};
			break;
		case 2292:
			definition.actions = new String[] {"Trade", null, null, null, null};
			definition.name = "Merchant";
			break;
		case 2676:
			definition.actions = new String[] {"Makeover", null, null, null, null};
			break;
		case 1360:
			definition.actions = new String[] {"Talk-to", null, null, null, null};
			break;
		case 494:
			definition.walkAnimation = 819;
			definition.actions = new String[] {"Talk-to", null, null, null, null};
			break;
		case 1685:
			definition.name = "Pure";
			definition.actions = new String[] {"Trade", null, null, null, null};
			break;
			case 8648:
				definition.name = "Pet Rock Golem";
				definition.actions = new String[] {"Pick-up", null, null, null, null};
				break;
			case 5559:
				definition.name = "Pet Raccoon";
				definition.actions = new String[] {"Pick-up", null, null, null, null};
				break;
			case 9579:
				definition.name = "Pet Chinchompa";
				definition.actions = new String[] {"Pick-up", null, null, null, null};
				break;
			case 2707:
				definition.name = "Pet Seagull";
				definition.actions = new String[] {"Pick-up", null, null, null, null};
				break;
		case 3030:
			definition.name = "King black dragon";
			definition.actions = new String[5];
			definition.actions[0] = "Pick-up";
			definition.npcModels = new int[] {17414, 17415, 17429, 17422};
			definition.combatLevel = 276;
			definition.standAnimation = 90;
			definition.walkAnimation = 4635;
			definition.adjustVertextPointZ = 63;
			definition.adjustVertextPointsXOrY = 63;
			definition.npcSizeInSquares = 3;
			break;

		case 3031:
			
			definition.name = "General graardor";
			definition.actions = new String[5];
			definition.actions[0] = "Pick-up";
			definition.npcModels = new int[] {27785, 27789};
			definition.combatLevel = 624;
			definition.standAnimation = 7059;
			definition.walkAnimation = 7058;
			definition.adjustVertextPointZ = 40;
			definition.adjustVertextPointsXOrY = 40;
			break;	

		case 3032:
			definition.name = "TzTok-Jad";
			definition.actions = new String[5];
			definition.actions[0] = "Pick-up";
			definition.npcModels = new int[] {34131};
			definition.combatLevel = 702;
			definition.standAnimation = 9274;
			definition.walkAnimation = 9273;
			definition.adjustVertextPointZ = 45;
			definition.adjustVertextPointsXOrY = 45;
			definition.npcSizeInSquares = 2;
			break;

		case 3033:
			definition.name = "Chaos elemental";
			definition.actions = new String[5];
			definition.actions[0] = "Pick-up";
			definition.npcModels = new int[] {11216};
			definition.combatLevel = 305;
			definition.standAnimation = 3144;
			definition.walkAnimation = 3145;
			definition.adjustVertextPointZ = 62;
			definition.adjustVertextPointsXOrY = 62;
			break;

		case 3034:
			definition.name = "Corporeal beast";
			definition.actions = new String[5];
			definition.actions[0] = "Pick-up";
			definition.npcModels = new int[] {40955};
			definition.combatLevel = 785;
			definition.standAnimation = 10056;
			definition.walkAnimation = 10055;
			definition.adjustVertextPointZ = 45;
			definition.adjustVertextPointsXOrY = 45;
			definition.npcSizeInSquares = 2;
			break;

		case 3035:
			definition.name = "Kree'arra";
			definition.actions = new String[5];
			definition.actions[0] = "Pick-up";
			definition.npcModels = new int[] {28003, 28004};
			definition.combatLevel = 580;
			definition.standAnimation = 6972;
			definition.walkAnimation = 6973;
			definition.adjustVertextPointZ = 43;
			definition.adjustVertextPointsXOrY = 43;
			definition.npcSizeInSquares = 2;
			break;

		case 3036:
			definition.name = "K'ril tsutsaroth";
			definition.actions = new String[5];
			definition.actions[0] = "Pick-up";
			definition.npcModels = new int[] {27768, 27773, 27764, 27765, 27770};
			definition.combatLevel = 650;
			definition.standAnimation = 6943;
			definition.walkAnimation = 6942;
			definition.adjustVertextPointZ = 43;
			definition.adjustVertextPointsXOrY = 43;
			definition.npcSizeInSquares = 2;
			break;
		case 3037:
			definition.name = "Commander zilyana";
			definition.actions = new String[5];
			definition.actions[0] = "Pick-up";
			definition.npcModels = new int[] {28057, 28071, 28078, 28056};
			definition.combatLevel = 596;
			definition.standAnimation = 6963;
			definition.walkAnimation = 6962;
			definition.adjustVertextPointZ = 103;
			definition.adjustVertextPointsXOrY = 103;
			definition.npcSizeInSquares = 2;
			break;
			case 6914:
				definition.name = "Pet Raccoon";
				definition.actions = new String[5];
				definition.actions[0] = "Pick-up";
				break;
			case 4906:
				definition.name = "Wilfred";
				definition.actions = new String[5];
				definition.actions = new String[] {"Trade with", null, "Exchange-Kindling", null, null};
				definition.description = "A master woodcutter.".getBytes();
				break;
			case 6247:
			definition.name = "Commander zilyana";
			//definition.actions = new String[5];
			//definition.actions[0] = "Pick-up";
			definition.npcModels = new int[] {28057, 28071, 28078, 28056};
			//definition.combatLevel = 596;
			definition.standAnimation = 6963;
			definition.walkAnimation = 6962;
			//definition.adjustVertextPointZ = 103;
			//definition.adjustVertextPointsXOrY = 103;
			//definition.npcSizeInSquares = 2;
			break;
		case 6873:
		case 6874:
			definition.standAnimation = 5785;
			definition.walkAnimation = 5781;
			definition.npcModels[0] = 23892;
			//23892
			break;
		case 5529:
			definition.standAnimation = 5785;
			definition.walkAnimation = 5781;
			//23892
			break;
			
		case 3038:
			definition.name = "Dagannoth supreme";
			definition.actions = new String[5];
			definition.actions[0] = "Pick-up";
			definition.npcModels = new int[] {9941, 9943};
			definition.combatLevel = 303;
			definition.standAnimation = 2850;
			definition.walkAnimation = 2849;
			definition.adjustVertextPointZ = 105;
			definition.adjustVertextPointsXOrY = 105;
			definition.npcSizeInSquares = 2;
			break;

		case 3039:
			definition.name = "Dagannoth prime"; //9940, 9943, 9942
			definition.actions = new String[5];
			definition.actions[0] = "Pick-up";
			definition.npcModels = new int[] {9940, 9943, 9942};
			definition.originalModelColours = new int[]{11930, 27144, 16536, 16540};
			definition.changedModelColours = new int[]{5931, 1688, 21530, 21534};
			definition.combatLevel = 303;
			definition.standAnimation = 2850;
			definition.walkAnimation = 2849;
			definition.adjustVertextPointZ = 105;
			definition.adjustVertextPointsXOrY = 105;
			definition.npcSizeInSquares = 2;
			break;

		case 3040:
			definition.name = "Dagannoth rex";
			definition.actions = new String[5];
			definition.actions[0] = "Pick-up";
			definition.npcModels = new int[] {9941};
			definition.originalModelColours = new int[]{16536, 16540, 27144, 2477};
			definition.changedModelColours = new int[]{7322, 7326, 10403, 2595};
			definition.combatLevel = 303;
			definition.standAnimation = 2850;
			definition.walkAnimation = 2849;
			definition.adjustVertextPointZ = 105;
			definition.adjustVertextPointsXOrY = 105;
			definition.npcSizeInSquares = 2;
			break;
		case 3047:
			definition.name = "Frost dragon";
			definition.combatLevel = 166;
			definition.standAnimation = 13156;
			definition.walkAnimation = 13157;
			definition.walkingBackwardsAnimation = -1;
			definition.walkRightAnimation = -1;
			definition.walkLeftAnimation = -1;
			//definition.type = 51;
			definition.degreesToTurn = 32 ;
			definition.npcModels = new int[] {56767, 55294};
			definition.actions = new String[5];
			definition.actions[0] = "Pick-up";
			definition.adjustVertextPointZ = 72;
			definition.adjustVertextPointsXOrY = 72;
			definition.npcSizeInSquares = 2;
			break;

		case 3048:
			definition.npcModels = new int[]{44733};
			definition.name = "Tormented demon";
			definition.combatLevel = 450;
			definition.standAnimation = 10921;
			definition.walkAnimation = 10920;
			definition.walkingBackwardsAnimation = -1;
			definition.walkRightAnimation = -1;
			definition.walkLeftAnimation = -1;
		//	definition.type = 8349;
			definition.degreesToTurn = 32;
			definition.actions = new String[5];
			definition.actions[0] = "Pick-up";
			definition.adjustVertextPointZ = 60;
			definition.adjustVertextPointsXOrY = 60;
			definition.npcSizeInSquares = 2;
			break;
		case 3050:
			definition.npcModels = new int[] {24602, 24605, 24606};
			definition.name = "Kalphite queen";
			definition.actions = new String[5];
			definition.actions[0] = "Pick-up";
			definition.combatLevel = 333;
			definition.standAnimation = 6236;
			definition.walkAnimation = 6236;
			definition.adjustVertextPointZ = 70;
			definition.adjustVertextPointsXOrY = 70;
			definition.npcSizeInSquares = 2;
			break;
		case 3051:
			definition.npcModels = new int[] {46141};
			definition.name = "Slash bash";
			definition.actions = new String[5];
			definition.actions[0] = "Pick-up";
			definition.combatLevel = 111;
			definition.standAnimation = 11460;
			definition.walkAnimation = 11461;
			definition.adjustVertextPointZ = 65;
			definition.adjustVertextPointsXOrY = 65;
			definition.npcSizeInSquares = 2;
			break;
		case 3052:
			definition.npcModels = new int[] {45412};
			definition.name = "Phoenix";
			definition.actions = new String[5];
			definition.actions[0] = "Pick-up";
			definition.combatLevel = 235;
			definition.standAnimation = 11074;
			definition.walkAnimation = 11075;
			definition.adjustVertextPointZ = 70;
			definition.adjustVertextPointsXOrY = 70;
			definition.npcSizeInSquares = 2;
			break;
		case 2042://regular
			definition.name = "Zulrah";
			definition.actions = new String[] {null, "Attack", null, null, null};
			definition.npcModels = new int[1];
			definition.npcModels[0] = 14407;
			definition.standAnimation = 5070;
			definition.walkAnimation = 5070;
			definition.combatLevel = 725;
			definition.adjustVertextPointsXOrY = 100;
			definition.adjustVertextPointZ = 100;
			break;
		case 2043://melee
			definition.name = "Zulrah";
			definition.actions = new String[] {null, "Attack", null, null, null};
			definition.npcModels = new int[1];
			definition.npcModels[0] = 14408;
			definition.standAnimation = 5070;
			definition.walkAnimation = 5070;
			definition.combatLevel = 725;
			definition.adjustVertextPointsXOrY = 100;
			definition.adjustVertextPointZ = 100;
			break;
		case 2044://mage
			definition.name = "Zulrah";
			definition.actions = new String[] {null, "Attack", null, null, null};
			definition.npcModels = new int[1];
			definition.npcModels[0] = 14409;
			definition.standAnimation = 5070;
			definition.walkAnimation = 5070;
			definition.combatLevel = 725;
			definition.adjustVertextPointsXOrY = 100;
			definition.adjustVertextPointZ = 100;
			break;
		case 2045://jad
			definition.name = "Zulrah";
			definition.actions = new String[] {null, "Attack", null, null, null};
			definition.npcModels = new int[1];
			definition.npcModels[0] = 14408;
			definition.standAnimation = 5070;
			definition.walkAnimation = 5070;
			definition.combatLevel = 725;
			definition.adjustVertextPointsXOrY = 100;
			definition.adjustVertextPointZ = 100;
			break;
		case 3053:
			definition.npcModels = new int[] {46058, 46057};
			definition.name = "Bandos avatar";
			definition.actions = new String[5];
			definition.actions[0] = "Pick-up";
			definition.combatLevel = 299;
			definition.standAnimation = 11242;
			definition.walkAnimation = 11255;
			definition.adjustVertextPointZ = 70;
			definition.adjustVertextPointsXOrY = 70;
			definition.npcSizeInSquares = 2;
			break;
		case 3054:
			definition.npcModels = new int[] {62717};
			definition.name = "Nex";
			definition.actions = new String[5];
			definition.actions[0] = "Pick-up";
			definition.combatLevel = 565;
			definition.standAnimation = 6320;
			definition.walkAnimation = 6319;
			definition.adjustVertextPointZ = 95;
			definition.adjustVertextPointsXOrY = 95;
			definition.npcSizeInSquares = 1;
			break;
		case 3055:
			definition.npcModels = new int[] {51852, 51853};
			definition.name = "Jungle strykewyrm";
			definition.actions = new String[5];
			definition.actions[0] = "Pick-up";
			definition.combatLevel = 110;
			definition.standAnimation = 12790;
			definition.walkAnimation = 12790;
			definition.adjustVertextPointZ = 60;
			definition.adjustVertextPointsXOrY = 60;
			definition.npcSizeInSquares = 1;
			break;
		case 945:
			definition.name = "RuneLive Guide";
			break;
		case 3056:
			definition.npcModels = new int[] {51848, 51850};
			definition.name = "Desert strykewyrm";
			definition.actions = new String[5];
			definition.actions[0] = "Pick-up";
			definition.combatLevel = 130;
			definition.standAnimation = 12790;
			definition.walkAnimation = 12790;
			definition.adjustVertextPointZ = 60;
			definition.adjustVertextPointsXOrY = 60;
			definition.npcSizeInSquares = 1;
			break;
		case 3057:
			definition.npcModels = new int[] {51847, 51849};
			definition.name = "Ice strykewyrm";
			definition.actions = new String[5];
			definition.actions[0] = "Pick-up";
			definition.combatLevel = 210;
			definition.standAnimation = 12790;
			definition.walkAnimation = 12790;
			definition.adjustVertextPointZ = 65;
			definition.adjustVertextPointsXOrY = 65;
			definition.npcSizeInSquares = 1;
			break;
		case 3058:
			definition.npcModels = new int[] {49142, 49144};
			definition.name = "Green dragon";
			definition.actions = new String[5];
			definition.actions[0] = "Pick-up";
			definition.combatLevel = 79;
			definition.standAnimation = 12248;
			definition.walkAnimation = 12246;
			definition.adjustVertextPointZ = 70;
			definition.adjustVertextPointsXOrY = 70;
			definition.npcSizeInSquares = 2;
			break;
		case 3059:
			definition.npcModels = new int[] {57937};
			definition.name = "Baby blue dragon";
			definition.actions = new String[5];
			definition.actions[0] = "Pick-up";
			definition.combatLevel = 48;
			definition.standAnimation = 14267;
			definition.walkAnimation = 14268;
			definition.adjustVertextPointZ = 85;
			definition.adjustVertextPointsXOrY = 85;
			definition.npcSizeInSquares = 1;
			break;
		case 3060:
			definition.npcModels = new int[] {49137, 49144};
			definition.name = "Blue dragon";
			definition.actions = new String[5];
			definition.actions[0] = "Pick-up";
			definition.combatLevel = 111;
			definition.standAnimation = 12248;
			definition.walkAnimation = 12246;
			definition.adjustVertextPointZ = 70;
			definition.adjustVertextPointsXOrY = 70;
			definition.npcSizeInSquares = 2;
			break;
		case 3061:
			definition.npcModels = new int[] {14294, 49144};
			definition.name = "Black dragon";
			definition.actions = new String[5];
			definition.actions[0] = "Pick-up";
			definition.combatLevel = 227;
			definition.standAnimation = 12248;
			definition.walkAnimation = 12246;
			definition.adjustVertextPointZ = 70;
			definition.adjustVertextPointsXOrY = 70;
			definition.npcSizeInSquares = 2;
			break;
		case 5507:
			definition.copy(get(1265));
			definition.originalModelColours = new int[] {10396};
			definition.changedModelColours = new int[] {17350};
			break;
		case 5508:
			definition.copy(get(1265));
			definition.originalModelColours = new int[] {10396};
			definition.changedModelColours = new int[] {226770};
			break;
		case 5509:
			definition.copy(get(1265));
			definition.originalModelColours = new int[] {10396};
			definition.changedModelColours = new int[] {123770};
			break;
		case 5510:
			definition.copy(get(1265));
			definition.originalModelColours = new int[] {10396};
			definition.changedModelColours = new int[] {933};
			break;
		case 5511:
			definition.copy(get(1265));
			definition.originalModelColours = new int[] {10396};
			definition.changedModelColours = new int[] {100};
			break;
		case 5030:
			definition.actions[2] = "Travel";
			break;
		case 5512:
			definition.copy(get(1265));
			definition.originalModelColours = new int[] {10396};
			definition.changedModelColours = new int[] {76770};
			break;
		case 5513:
			definition.copy(get(1265));
			definition.originalModelColours = new int[] {10396};
			definition.changedModelColours = new int[] {461770};
			break;
		case 5514:
			definition.copy(get(1265));
			definition.originalModelColours = new int[] {10396};
			definition.changedModelColours = new int[] {49950};
			break;
		case 5523:
			definition.copy(get(1613));
			definition.originalModelColours = new int[] {5202, 187, 183, 192, 179};
			definition.changedModelColours = new int[] {17350, 17350, 17350, 17350, 17350};
			break;
		case 5524:
			definition.copy(get(1613));
			definition.originalModelColours = new int[] {5202, 187, 183, 192, 179};
			definition.changedModelColours = new int[] {226770, 226770, 226770, 226770, 226770};
			break;
		case 5525:
			definition.copy(get(1613));
			definition.originalModelColours = new int[] {5202, 187, 183, 192, 179};
			definition.changedModelColours = new int[] {123770, 123770, 123770, 123770, 123770};
			break;
		case 5526:
			definition.copy(get(1613));
			definition.originalModelColours = new int[] {5202, 187, 183, 192, 179};
			definition.changedModelColours = new int[] {933, 933, 933, 933, 933};
			break;
		case 5527:
			definition.copy(get(1613));
			definition.originalModelColours = new int[] {5202, 187, 183, 192, 179};
			definition.changedModelColours = new int[] {100, 100, 100, 100, 100};
			break;	
		case 5528:
			definition.copy(get(1613));
			definition.originalModelColours = new int[] {5202, 187, 183, 192, 179};
			definition.changedModelColours = new int[] {76770, 76770, 76770, 76770, 76770};
			break;
		case 5531:
			definition.copy(get(1613));
			definition.originalModelColours = new int[] {5202, 187, 183, 192, 179};
			definition.changedModelColours = new int[] {49950, 49950, 49950, 49950, 49950};
			break;
		case 5532:
			definition.copy(get(1613));
			definition.originalModelColours = new int[] {5202, 187, 183, 192, 179};
			definition.changedModelColours = new int[] {6020, 6020, 6020, 6020, 6020};
			break;
		case 5550:
			definition.copy(get(1613));
			definition.originalModelColours = new int[] {5202, 187, 183, 192, 179};
			definition.changedModelColours = new int[] {461770, 461770, 461770, 461770, 461770};
			break;
			
		case 5551:
			definition.copy(get(1265));
			definition.originalModelColours = new int[] {10396};
			definition.changedModelColours = new int[] {17350};
			definition.adjustVertextPointZ -= 37;
			definition.adjustVertextPointsXOrY -= 37;
			definition.actions = new String[5];
			definition.actions[0] = "Pick-up";
			break;
		case 5552:
			definition.copy(get(1265));
			definition.originalModelColours = new int[] {10396};
			definition.changedModelColours = new int[] {226770};
			definition.adjustVertextPointZ -= 37;
			definition.adjustVertextPointsXOrY -= 37;
			definition.actions = new String[5];
			definition.actions[0] = "Pick-up";
			break;
		case 5553:
			definition.copy(get(1265));
			definition.originalModelColours = new int[] {10396};
			definition.changedModelColours = new int[] {123770};
			definition.adjustVertextPointZ -= 37;
			definition.adjustVertextPointsXOrY -= 37;
			definition.actions = new String[5];
			definition.actions[0] = "Pick-up";
			break;
		case 5554:
			definition.copy(get(1265));
			definition.originalModelColours = new int[] {10396};
			definition.changedModelColours = new int[] {933};
			definition.adjustVertextPointZ -= 37;
			definition.adjustVertextPointsXOrY -= 37;
			definition.actions = new String[5];
			definition.actions[0] = "Pick-up";
			break;
		case 5555:
			definition.copy(get(1265));
			definition.originalModelColours = new int[] {10396};
			definition.changedModelColours = new int[] {100};
			definition.adjustVertextPointZ -= 37;
			definition.adjustVertextPointsXOrY -= 37;
			definition.actions = new String[5];
			definition.actions[0] = "Pick-up";
			break;
		case 5556:
			definition.copy(get(1265));
			definition.originalModelColours = new int[] {10396};
			definition.changedModelColours = new int[] {76770};
			definition.adjustVertextPointZ -= 37;
			definition.adjustVertextPointsXOrY -= 37;
			definition.actions = new String[5];
			definition.actions[0] = "Pick-up";
			break;
		case 5557:
			definition.copy(get(1265));
			definition.originalModelColours = new int[] {10396};
			definition.changedModelColours = new int[] {461770};
			definition.adjustVertextPointZ -= 37;
			definition.adjustVertextPointsXOrY -= 37;
			definition.actions = new String[5];
			definition.actions[0] = "Pick-up";
			break;
		case 5558:
			definition.copy(get(1265));
			definition.originalModelColours = new int[] {10396};
			definition.changedModelColours = new int[] {49950};
			definition.adjustVertextPointZ -= 37;
			definition.adjustVertextPointsXOrY -= 37;
			definition.actions = new String[5];
			definition.actions[0] = "Pick-up";
			break;
			
		case 5515:
			definition.copy(get(2783));
			definition.originalModelColours = new int[] {5640, 898, 4502, 4750, 5388, 268};
			definition.changedModelColours = new int[] {17350, 17350, 17350, 17350, 17350, 17350};
			break;
		case 5516:
			definition.copy(get(2783));
			definition.originalModelColours = new int[] {5640, 898, 4502, 4750, 5388, 268};
			definition.changedModelColours = new int[] {226770, 226770, 226770, 226770, 226770, 226770};
			break;
		case 5517:
			definition.copy(get(2783));
			definition.originalModelColours = new int[] {5640, 898, 4502, 4750, 5388, 268};
			definition.changedModelColours = new int[] {123770, 123770, 123770, 123770, 123770, 123770};
			break;
		case 5518:
			definition.copy(get(2783));
			definition.originalModelColours = new int[] {5640, 898, 4502, 4750, 5388, 268};
			definition.changedModelColours = new int[] {933, 933, 933, 933, 933, 933};
			break;
		case 5519:
			definition.copy(get(2783));
			definition.originalModelColours = new int[] {5640, 898, 4502, 4750, 5388, 268};
			definition.changedModelColours = new int[] {100, 100, 100, 100, 100, 100};
			break;
		case 5520:
			definition.copy(get(2783));
			definition.originalModelColours = new int[] {5640, 898, 4502, 4750, 5388, 268};
			definition.changedModelColours = new int[] {76770, 76770, 76770, 76770, 76770, 76770};
			break;
		case 5521:
			definition.copy(get(2783));
			definition.originalModelColours = new int[] {5640, 898, 4502, 4750, 5388, 268};
			definition.changedModelColours = new int[] {49950, 49950, 49950, 49950, 49950, 49950};
			break;
		case 5522:
			definition.copy(get(2783));
			definition.originalModelColours = new int[] {5640, 898, 4502, 4750, 5388, 268};
			definition.changedModelColours = new int[] {461770, 461770, 461770, 461770, 461770, 461770};
			break;
		case 5500:
			definition.copy(get(1615));
			definition.originalModelColours = new int[] {4015, 4025, 920, 7580};
			definition.changedModelColours = new int[] {17350, 17350, 17350, 17350, 17350};
			break;
		case 5501:
			definition.copy(get(1615));
			definition.originalModelColours = new int[] {4015, 4025, 920, 7580};
			definition.changedModelColours = new int[] {226770, 226770, 226770, 226770, 226770};
			break;
		case 5502:
			definition.copy(get(1615));
			definition.originalModelColours = new int[] {4015, 4025, 920, 7580};
			definition.changedModelColours = new int[] {123770, 123770, 123770, 123770, 123770};
			break;
		case 5503:
			definition.copy(get(1615));
			definition.originalModelColours = new int[] {4015, 4025, 920, 7580};
			definition.changedModelColours = new int[] {933, 933, 933, 933, 933};
			break;
		case 5504:
			definition.copy(get(1615));
			definition.originalModelColours = new int[] {4015, 4025, 920, 7580};
			definition.changedModelColours = new int[] {100, 100, 100, 100, 100};
			break;
		case 5505:
			definition.copy(get(1615));
			definition.originalModelColours = new int[] {4015, 4025, 920, 7580};
			definition.changedModelColours = new int[] {76770, 76770, 76770, 76770, 76770};
			break;
		case 5506:
			definition.copy(get(1615));
			definition.originalModelColours = new int[] {4015, 4025, 920, 7580};
			definition.changedModelColours = new int[] {49950, 49950, 49950, 49950, 49950};
			break;
		case 5533:
			definition.copy(get(1615));
			definition.originalModelColours = new int[] {4015, 4025, 920, 7580};
			definition.changedModelColours = new int[] {461770, 461770, 461770, 461770, 461770};
			break;
		}
		return definition;
	}
	
	public void copy(MobDefinition other) {		
		name = other.name;
		npcModels = other.npcModels;
		npcSizeInSquares = other.npcSizeInSquares;
		adjustVertextPointsXOrY = other.adjustVertextPointsXOrY;
		standAnimation = other.standAnimation;
		walkAnimation = other.walkAnimation;
		actions = other.actions;
		combatLevel = other.combatLevel;
		adjustVertextPointZ = other.adjustVertextPointZ;
		visibilityOrRendering = other.visibilityOrRendering;
		dialogueModels = other.dialogueModels;
	}
	
	public void copy(int id) {
		MobDefinition other = get(id);
		changedModelColours = other.changedModelColours.clone();
		childrenIDs = other.childrenIDs.clone();
		combatLevel = other.combatLevel;
		configChild = other.configChild;
		degreesToTurn = other.degreesToTurn;
		description = other.description;
		dialogueModels = other.dialogueModels;
		disableRightClick = false;
		drawYellowDotOnMap = other.drawYellowDotOnMap;
		headIcon = other.headIcon;
		modelLightning = other.modelLightning;
		modelShadowing = other.modelShadowing;
		npcModels = other.npcModels.clone();
		originalModelColours = other.originalModelColours.clone();
		standAnimation = other.standAnimation;
		varBitChild = other.varBitChild;
		visibilityOrRendering = other.visibilityOrRendering;
		walkAnimation = other.walkAnimation;
		walkingBackwardsAnimation = other.walkingBackwardsAnimation;
		walkLeftAnimation = other.walkLeftAnimation;
		walkRightAnimation = other.walkRightAnimation;
	}

	public static void nullify() {
		mruNodes = null;
		streamIndices = null;
		cache = null;
		buffer = null;
	}

	public static void load(Archive archive) {
		buffer = new ByteBuffer(archive.get("npc.dat"));
		ByteBuffer stream2 = new ByteBuffer(archive.get("npc.idx"));
		int totalNPCs = stream2.getUnsignedShort();
		streamIndices = new int[totalNPCs];
		int position = 2;

		for (int i = 0; i < totalNPCs; i++) {
			streamIndices[i] = position;
			position += stream2.getUnsignedShort();
		}

		cache = new MobDefinition[20];

		for (int i = 0; i < 20; i++) {
			cache[i] = new MobDefinition();
		}
	}

	public String[] actions;
	private int adjustVertextPointsXOrY;
	private int adjustVertextPointZ;
	private int[] changedModelColours;
	public int[] childrenIDs;
	public int combatLevel;
	private int configChild;
	public int degreesToTurn;
	public byte[] description;
	private int[] dialogueModels;
	public boolean disableRightClick;
	public boolean drawYellowDotOnMap;
	public int headIcon;
	private int modelLightning;
	private int modelShadowing;
	public String name;
	public int[] npcModels;
	public byte npcSizeInSquares;
	private int[] originalModelColours;
	public int standAnimation;
	public int id;
	private int varBitChild;
	public boolean visibilityOrRendering;
	public int walkAnimation;
	public int walkingBackwardsAnimation;
	public int walkLeftAnimation;
	public int walkRightAnimation;

	private MobDefinition() {
		walkRightAnimation = -1;
		varBitChild = -1;
		walkingBackwardsAnimation = -1;
		configChild = -1;
		combatLevel = -1;
		walkAnimation = -1;
		npcSizeInSquares = 1;
		headIcon = -1;
		standAnimation = -1;
		id = -1;
		degreesToTurn = 32;
		walkLeftAnimation = -1;
		disableRightClick = true;
		adjustVertextPointZ = 128;
		drawYellowDotOnMap = true;
		adjustVertextPointsXOrY = 128;
		visibilityOrRendering = false;
	}

	public Model method160() {
		if (childrenIDs != null) {
			MobDefinition definition = method161();

			if (definition == null) {
				return null;
			} else {
				return definition.method160();
			}
		}

		if (dialogueModels == null) {
			return null;
		}

		boolean flag1 = false;

		for (int i = 0; i < dialogueModels.length; i++) {
			if (!Model.isModelLoaded(dialogueModels[i])) {
				flag1 = true;
			}
		}

		if (flag1) {
			return null;
		}

		Model aclass30_sub2_sub4_sub6s[] = new Model[dialogueModels.length];

		for (int j = 0; j < dialogueModels.length; j++) {
			aclass30_sub2_sub4_sub6s[j] = Model.fetchModel(dialogueModels[j]);
		}

		Model model;

		if (aclass30_sub2_sub4_sub6s.length == 1) {
			model = aclass30_sub2_sub4_sub6s[0];
		} else {
			model = new Model(aclass30_sub2_sub4_sub6s.length, aclass30_sub2_sub4_sub6s);
		}

		if (originalModelColours != null) {
			for (int k = 0; k < originalModelColours.length; k++) {
				model.method476(originalModelColours[k], changedModelColours[k]);
			}
		}

		return model;
	}

	public MobDefinition method161() {
		int j = -1;

		try {
			if (varBitChild != -1) {
				VarBit varBit = VarBit.cache[varBitChild];
				int k = varBit.configId;
				int l = varBit.configValue;
				int i1 = varBit.anInt650;
				int j1 = Client.anIntArray1232[i1 - l];
				// System.out.println("k: " + k + " l: " + l);
				j = clientInstance.variousSettings[k] >> l & j1;
			} else if (configChild != -1) {
				j = clientInstance.variousSettings[configChild];
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (j < 0 || j >= childrenIDs.length || childrenIDs[j] == -1) {
			return null;
		} else {
			return get(childrenIDs[j]);
		}
	}

	public Model method164(int j, int frame, int ai[], int nextFrame, int cycle1, int cycle2) {
		if (childrenIDs != null) {
			MobDefinition entityDef = method161();

			if (entityDef == null) {
				return null;
			} else {
				return entityDef.method164(j, frame, ai, nextFrame, cycle1, cycle2);
			}
		}

		Model model = (Model) mruNodes.insertFromCache(id);

		if (model == null) {
			boolean flag = false;

			for (int i1 = 0; i1 < npcModels.length; i1++) {
				if (!Model.isModelLoaded(npcModels[i1])) {
					flag = true;
				}
			}

			if (flag) {
				return null;
			}

			Model aclass30_sub2_sub4_sub6s[] = new Model[npcModels.length];

			for (int j1 = 0; j1 < npcModels.length; j1++) {
				aclass30_sub2_sub4_sub6s[j1] = Model.fetchModel(npcModels[j1]);
			}

			if (aclass30_sub2_sub4_sub6s.length == 1) {
				model = aclass30_sub2_sub4_sub6s[0];
			} else {
				model = new Model(aclass30_sub2_sub4_sub6s.length, aclass30_sub2_sub4_sub6s);
			}

			if (originalModelColours != null) {
				for (int k1 = 0; k1 < originalModelColours.length; k1++) {
					model.method476(originalModelColours[k1], changedModelColours[k1]);
				}
			}

			model.createBones();
			model.light(84 + modelLightning, 1000 + modelShadowing, -90, -580, -90, true);
			mruNodes.removeFromCache(model, id);
		}

		Model model_1 = Model.aModel_1621;
		model_1.method464(model, FrameReader.isNullFrame(frame) & FrameReader.isNullFrame(j));
/*
		if (frame != -1 && j != -1) {
			model_1.method471(ai, j, frame);
		} else if (frame != -1 && !Configuration.TWEENING_ENABLED) {
			model_1.applyTransform(frame);
		} else if (frame != -1 && nextFrame != -1 && Configuration.TWEENING_ENABLED) {
			model_1.interpolateFrames(frame, nextFrame, cycle1, cycle2);
		}*/
		
		if (frame != -1 && j != -1)
			model_1.method471(ai, j, frame);
		else if (frame != -1 && nextFrame != -1 && Configuration.TWEENING_ENABLED)
			model_1.interpolateFrames(frame, nextFrame, cycle1, cycle2);
		else if (frame != -1)
			model_1.applyTransform(frame);
		
		if (adjustVertextPointsXOrY != 128 || adjustVertextPointZ != 128) {
			model_1.scaleT(adjustVertextPointsXOrY, adjustVertextPointsXOrY, adjustVertextPointZ);
		}

		model_1.method466();
		model_1.triangleSkin = null;
		model_1.vertexSkin = null;

		if (npcSizeInSquares == 1) {
			model_1.aBoolean1659 = true;
		}

		return model_1;
	}

	private void readValues(ByteBuffer buffer) {
		do {
			final int opcode = buffer.getUnsignedByte();

			if (opcode == 0) {
				return;
			}

			if (opcode == 1) {
				int j = buffer.getUnsignedByte();
				npcModels = new int[j];

				for (int j1 = 0; j1 < j; j1++) {
					npcModels[j1] = buffer.getUnsignedShort();
				}
			} else if (opcode == 2) {
				name = buffer.getString();
			} else if (opcode == 3) {
				description = buffer.getBytes();
			} else if (opcode == 12) {
				npcSizeInSquares = buffer.getSignedByte();
			} else if (opcode == 13) {
				standAnimation = buffer.getUnsignedShort();
			} else if (opcode == 14) {
				walkAnimation = buffer.getUnsignedShort();
			} else if (opcode == 17) {
				walkAnimation = buffer.getUnsignedShort();
				walkingBackwardsAnimation = buffer.getUnsignedShort();
				walkLeftAnimation = buffer.getUnsignedShort();
				walkRightAnimation = buffer.getUnsignedShort();

				if (walkAnimation == 65535) {
					walkAnimation = -1;
				}

				if (walkingBackwardsAnimation == 65535) {
					walkingBackwardsAnimation = -1;
				}

				if (walkLeftAnimation == 65535) {
					walkLeftAnimation = -1;
				}

				if (walkRightAnimation == 65535) {
					walkRightAnimation = -1;
				}
			} else if (opcode >= 30 && opcode < 40) {
				if (actions == null) {
					actions = new String[5];
				}

				actions[opcode - 30] = buffer.getString();

				if (actions[opcode - 30].equalsIgnoreCase("hidden")) {
					actions[opcode - 30] = null;
				}
			} else if (opcode == 40) {
				int length = buffer.getUnsignedByte();
				changedModelColours = new int[length];
				originalModelColours = new int[length];

				for (int i = 0; i < length; i++) {
					originalModelColours[i] = buffer.getUnsignedShort();
					changedModelColours[i] = buffer.getUnsignedShort();
				}
			} else if (opcode == 60) {
				int length = buffer.getUnsignedByte();
				dialogueModels = new int[length];

				for (int i = 0; i < length; i++) {
					dialogueModels[i] = buffer.getUnsignedShort();
				}
			} else if (opcode == 90) {
				buffer.getUnsignedShort();
			} else if (opcode == 91) {
				buffer.getUnsignedShort();
			} else if (opcode == 92) {
				buffer.getUnsignedShort();
			} else if (opcode == 93) {
				drawYellowDotOnMap = false;
			} else if (opcode == 95) {
				combatLevel = buffer.getUnsignedShort();
			} else if (opcode == 97) {
				adjustVertextPointsXOrY = buffer.getUnsignedShort();
			} else if (opcode == 98) {
				adjustVertextPointZ = buffer.getUnsignedShort();
			} else if (opcode == 99) {
				visibilityOrRendering = true;
			} else if (opcode == 100) {
				modelLightning = buffer.getSignedByte();
			} else if (opcode == 101) {
				modelShadowing = buffer.getSignedByte() * 5;
			} else if (opcode == 102) {
				headIcon = buffer.getUnsignedShort();
			} else if (opcode == 103) {
				degreesToTurn = buffer.getUnsignedShort();
			} else if (opcode == 106) {
				varBitChild = buffer.getUnsignedShort();

				if (varBitChild == 65535) {
					varBitChild = -1;
				}

				configChild = buffer.getUnsignedShort();

				if (configChild == 65535) {
					configChild = -1;
				}

				int length = buffer.getUnsignedByte();
				childrenIDs = new int[length + 1];

				for (int i = 0; i <= length; i++) {
					childrenIDs[i] = buffer.getUnsignedShort();

					if (childrenIDs[i] == 65535) {
						childrenIDs[i] = -1;
					}
				}
			} else if (opcode == 107) {
				disableRightClick = false;
			}
		} while (true);
	}

}