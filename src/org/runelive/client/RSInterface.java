package org.runelive.client;

import org.runelive.client.accounts.Account;
import org.runelive.client.cache.Archive;
import org.runelive.client.cache.definition.ItemDefinition;
import org.runelive.client.cache.definition.MobDefinition;
import org.runelive.client.entity.player.Player;
import org.runelive.client.graphics.CacheSpriteLoader;
import org.runelive.client.graphics.Sprite;
import org.runelive.client.graphics.fonts.TextClass;
import org.runelive.client.graphics.fonts.TextDrawingArea;
import org.runelive.client.graphics.rsinterface.CustomInterfaces;
import org.runelive.client.graphics.rsinterface.SummoningInterfaceData;
import org.runelive.client.io.ByteBuffer;
import org.runelive.client.world.Model;

public class RSInterface {

	private static CustomInterfaces customInterfaces;

	public static CustomInterfaces getCustomInterfaces() {
		return customInterfaces;
	}
	
	public void setSprite(Sprite sprite) {
		sprite1 = sprite;
	}

	public int customOpacity = 0;
	public boolean drawsTransparent;
	private static Archive aClass44;
	private static List aMRUNodes_238;
	private static final List aMRUNodes_264 = new List(30);
	public static TextDrawingArea[] fonts;
	public static RSInterface[] interfaceCache;

	private static void addActionButton(int id, int sprite, int sprite2, int width, int height, String s) {
		RSInterface rsi = interfaceCache[id] = new RSInterface();
		rsi.sprite1 = CacheSpriteLoader.getCacheSprite(sprite);

		if (sprite2 != -1) {
			rsi.sprite2 = CacheSpriteLoader.getCacheSprite(sprite == sprite2 ? sprite + 1 : sprite2);
		}

		rsi.tooltip = s;
		rsi.contentType = 0;
		rsi.atActionType = 1;
		rsi.width = width;
		rsi.hoverType = 52;
		rsi.parentID = id;
		rsi.id = id;
		rsi.type = 5;
		rsi.height = height;
	}

	public static void addModel(int ID, int modelId, int rot1, int rot2, int zoom, int w, int h) {
		RSInterface t = interfaceCache[ID] = new RSInterface();
		t.id = ID;
		t.parentID = ID;
		t.type = 6;
		t.width = w;
		t.height = h;
		t.mediaType = 1;
		t.mediaID = modelId;
		t.anInt256 = modelId;
		t.disabledAnimationId = -1;
		t.enabledAnimationId = -1;
		t.modelRotation1 = rot1;
		t.modelRotation2 = rot2;
		t.modelZoom = zoom;
		t.atActionType = (t.contentType = 0);
	}

	public static void addBackground(int id, int opacity, int color) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.textColor = color;
		tab.id = id;
		tab.parentID = id;
		tab.type = 11;
		tab.atActionType = 0;
		tab.contentType = 0;
		tab.opacity = (byte) opacity;
	}

	public static void constructRegularInterface(int id, String title) {
		RSInterface tab = addTabInterface(id);
		addHDSprite2(id + 1, 73, 73);
		addText(id + 2, title, fonts, 2, 16750623);
		interfaceCache[(id + 2)].centerText = true;

		addHoverButton2(id + 3, 74, 74, 16, 16, "Close", 250, id + 4, 3);
		addHoveredButton2(id + 4, 75, 75, 16, 16, id + 5);

		setChildren(4, tab);
		int BASEX = 15;
		int BASEY = 15;
		setBoundry(0, id + 1, BASEX + 0, BASEY + 0, tab);
		setBounds(id + 2, BASEX + 190 - 46, BASEY + 5, 1, tab);
		setBoundry(2, id + 3, BASEX + 430, BASEY + 4, tab);
		setBoundry(3, id + 4, BASEX + 430, BASEY + 4, tab);
	}
	
	public static void addNoteOptions(int id, String text,
			TextDrawingArea tda[], int idx, int color, int width, int height) {
		RSInterface Tab = addTabInterface(id);
		Tab.parentID = id;
		Tab.id = id;
		Tab.type = 4;
		Tab.atActionType = 1;
		Tab.width = width;
		Tab.height = height;
		Tab.contentType = 0;
		Tab.hoverType = 0;
		Tab.centerText = false;
		Tab.textDrawingAreas = tda[idx];
		Tab.message = text;
		Tab.textColor = color;
		Tab.actions = new String[] { "Edit", "Colour", "Delete" };
	}

	public static void addClanChatListTextWithOptions(int id, String text, String ignore, boolean owner,
			TextDrawingArea tda[], int idx, int color, int width, int height) {
		RSInterface Tab = addTabInterface(id);
		Tab.parentID = id;
		Tab.id = id;
		Tab.type = 4;
		Tab.atActionType = 1;
		Tab.width = width;
		Tab.height = height;
		Tab.contentType = 0;
		Tab.hoverType = 0;
		// Tab.mOverInterToTrigger = -1;
		Tab.centerText = false;
		// Tab.enabledText = true;
		Tab.textDrawingAreas = tda[idx];
		Tab.message = text;
		// Tab.aString228 = "";
		Tab.textColor = color;
		String s = Tab.message;
		if (s.contains("<img")) {
			int prefix = s.indexOf("<img=");
			int suffix = s.indexOf(">");
			s = s.replaceAll(s.substring(prefix + 5, suffix), "");
			s = s.replaceAll("</img>", "");
			s = s.replaceAll("<img=>", "");
		}
		if (!s.equals(ignore)) {
			if (owner) {
				Tab.actions = new String[] { "Promote to Recruit @or1@" + s + "", "Promote to Corporal @or1@" + s + "",
						"Promote to Sergeant @or1@" + s + "", "Promote to Lieutenant @or1@" + s + "",
						"Promote to Captain @or1@" + s + "", "Promote to General @or1@" + s + "",
						"Demote @or1@" + s + "", "Kick @or1@" + s + "" };
			} else {
				Tab.actions = new String[] { "Kick @or1@" + s + "" };
			}
		}
	}

	public static void addToItemGroup(RSInterface rsi, int w, int h, int x, int y, boolean actions, String action1,
			String action2, String action3) {
		rsi.width = w;
		rsi.height = h;
		rsi.inv = new int[w * h];
		rsi.invStackSizes = new int[w * h];
		rsi.usableItemInterface = false;
		rsi.isInventoryInterface = false;
		rsi.invSpritePadX = x;
		rsi.invSpritePadY = y;
		rsi.spritesX = new int[20];
		rsi.spritesY = new int[20];
		rsi.sprites = new Sprite[20];
		rsi.actions = new String[5];
		if (actions) {
			rsi.actions[0] = action1;
			rsi.actions[1] = action2;
			rsi.actions[2] = action3;
		}
		rsi.type = 2;
	}

	public static void addPrayerWithTooltip(int i, int configId, int configFrame, int requiredValues,
			int prayerSpriteID, int Hover, String tooltip) {
		RSInterface Interface = addTabInterface(i);
		Interface.id = i;
		Interface.parentID = 5608;
		Interface.type = 5;
		Interface.atActionType = 4;
		Interface.contentType = 0;
		Interface.opacity = 0;
		Interface.hoverType = Hover;
		Interface.sprite1 = CacheSpriteLoader.getCacheSprite(927);
		Interface.width = 34;
		Interface.height = 34;
		Interface.valueCompareType = new int[1];
		Interface.requiredValues = new int[1];
		Interface.valueCompareType[0] = 1;
		Interface.requiredValues[0] = configId;
		Interface.valueIndexArray = new int[1][3];
		Interface.valueIndexArray[0][0] = 5;
		Interface.valueIndexArray[0][1] = configFrame;
		Interface.valueIndexArray[0][2] = 0;
		Interface.tooltip = tooltip;
		Interface = addTabInterface(i + 1);
		Interface.id = i + 1;
		Interface.parentID = 5608;
		Interface.type = 5;
		Interface.atActionType = 0;
		Interface.contentType = 0;
		Interface.opacity = 0;
		Interface.sprite1 = CacheSpriteLoader.getCacheSprite(899 + prayerSpriteID);
		Interface.sprite2 = CacheSpriteLoader.getCacheSprite(873 + prayerSpriteID);
		if (prayerSpriteID == 26) {
			Interface.sprite2 = CacheSpriteLoader.getCacheSprite(892);
		} else if (prayerSpriteID == 27) {
			Interface.sprite2 = CacheSpriteLoader.getCacheSprite(893);
		}
		Interface.width = 34;
		Interface.height = 34;
		Interface.valueCompareType = new int[1];
		Interface.requiredValues = new int[1];
		Interface.valueCompareType[0] = 2;
		Interface.requiredValues[0] = requiredValues + 1;
		Interface.valueIndexArray = new int[1][3];
		Interface.valueIndexArray[0][0] = 2;
		Interface.valueIndexArray[0][1] = 5;
		Interface.valueIndexArray[0][2] = 0;
	}

	public static void addCurseWithTooltip(int i, int configId, int configFrame, int requiredValues, int prayerSpriteID,
			String PrayerName, int Hover) {
		RSInterface Interface = addTabInterface(i);
		Interface.id = i;
		Interface.parentID = 22500;
		Interface.type = 5;
		Interface.atActionType = 4;
		Interface.contentType = 0;
		Interface.opacity = 0;
		Interface.hoverType = Hover;
		Interface.sprite1 = CacheSpriteLoader.getCacheSprite(927);
		Interface.width = 34;
		Interface.height = 34;
		Interface.valueCompareType = new int[1];
		Interface.requiredValues = new int[1];
		Interface.valueCompareType[0] = 1;
		Interface.requiredValues[0] = configId;
		Interface.valueIndexArray = new int[1][3];
		Interface.valueIndexArray[0][0] = 5;
		Interface.valueIndexArray[0][1] = configFrame;
		Interface.valueIndexArray[0][2] = 0;
		Interface.tooltip = "Activate@lre@ " + PrayerName;
		Interface = addTabInterface(i + 1);
		Interface.id = i + 1;
		Interface.parentID = 22500;
		Interface.type = 5;
		Interface.atActionType = 0;
		Interface.contentType = 0;
		Interface.opacity = 0;
		Interface.sprite1 = CacheSpriteLoader.getCacheSprite(947 + prayerSpriteID);
		Interface.sprite2 = CacheSpriteLoader.getCacheSprite(927 + prayerSpriteID);

		Interface.width = 34;
		Interface.height = 34;
		Interface.valueCompareType = new int[1];
		Interface.requiredValues = new int[1];
		Interface.valueCompareType[0] = 2;
		Interface.requiredValues[0] = requiredValues + 1;
		Interface.valueIndexArray = new int[1][3];
		Interface.valueIndexArray[0][0] = 2;
		Interface.valueIndexArray[0][1] = 5;
		Interface.valueIndexArray[0][2] = 0;
	}

	public static void setBoundry(int frame, int ID, int X, int Y, RSInterface RSInterface) {
		RSInterface.children[frame] = ID;
		RSInterface.childX[frame] = X;
		RSInterface.childY[frame] = Y;
	}

	protected static final int[] SETTING_CONFIGS = { 516, 517, 518, 519, 520, 521, 522, 523, 524, 525, 526, 527, 528,
			529, 530, 531, 532 };

	public static void addCheckmarkHover(int interfaceID, int actionType, int hoverid, int spriteId, int spriteId2,
			int Width, int Height, int configFrame, int configId, String Tooltip, int hoverId2, int hoverSpriteId,
			int hoverSpriteId2, int hoverId3, String hoverDisabledText, String hoverEnabledText, int X, int Y) {
		RSInterface hover = addTabInterface(interfaceID);
		hover.id = interfaceID;
		hover.parentID = interfaceID;
		hover.type = 5;
		hover.atActionType = actionType;
		hover.contentType = 0;
		hover.opacity = 0;
		hover.hoverType = hoverid;
		hover.sprite1 = CacheSpriteLoader.getCacheSprite(spriteId);
		hover.sprite2 = CacheSpriteLoader.getCacheSprite(spriteId2);
		hover.width = Width;
		hover.tooltip = Tooltip;
		hover.height = Height;
		hover.valueCompareType = new int[1];
		hover.requiredValues = new int[1];
		hover.valueCompareType[0] = 1;
		hover.requiredValues[0] = configId;
		hover.valueIndexArray = new int[1][3];
		hover.valueIndexArray[0][0] = 5;
		hover.valueIndexArray[0][1] = configFrame;
		hover.valueIndexArray[0][2] = 0;
		hover = addTabInterface(hoverid);
		hover.parentID = hoverid;
		hover.id = hoverid;
		hover.type = 0;
		hover.atActionType = 0;
		hover.width = 550;
		hover.height = 334;
		hover.interfaceShown = true;
		hover.hoverType = -1;
		addSprite(hoverId2, hoverSpriteId, hoverSpriteId2, configId, configFrame);
		addHoverBox(hoverId3, interfaceID, hoverDisabledText, hoverEnabledText, configId, configFrame);
		setChildren(2, hover);
		setBounds(hoverId2, 0, 0, 0, hover);
		setBounds(hoverId3, X, Y, 1, hover);

	}

	public boolean hovers;
	public static final int BOLD_TEXT = 2;
	public static final int MEDIUM_TEXT = 1;
	public static final int SMALL_TEXT = 0;

	public static void addBankHover(int interfaceID, int actionType, int hoverid, int spriteId, int spriteId2,
			int Width, int Height, int configFrame, int configId, String Tooltip, int hoverId2, int hoverSpriteId,
			int hoverSpriteId2, int hoverId3, String hoverDisabledText, String hoverEnabledText, int X, int Y) {
		RSInterface hover = addTabInterface(interfaceID);
		hover.id = interfaceID;
		hover.parentID = interfaceID;
		hover.type = 5;
		hover.atActionType = actionType;
		hover.contentType = 0;
		hover.opacity = 0;
		hover.hoverType = hoverid;
		hover.sprite1 = CacheSpriteLoader.getCacheSprite(spriteId);
		hover.sprite2 = CacheSpriteLoader.getCacheSprite(spriteId2);
		hover.width = Width;
		hover.tooltip = Tooltip;
		hover.height = Height;
		hover.valueCompareType = new int[1];
		hover.requiredValues = new int[1];
		hover.valueCompareType[0] = 1;
		hover.requiredValues[0] = configId;
		hover.valueIndexArray = new int[1][3];
		hover.valueIndexArray[0][0] = 5;
		hover.valueIndexArray[0][1] = configFrame;
		hover.valueIndexArray[0][2] = 0;
		hover = addTabInterface(hoverid);
		hover.parentID = hoverid;
		hover.id = hoverid;
		hover.type = 0;
		hover.atActionType = 0;
		hover.width = 550;
		hover.height = 334;
		hover.interfaceShown = true;
		hover.hoverType = -1;
		addSprite(hoverId2, hoverSpriteId, hoverSpriteId2, configId, configFrame);
		addHoverBox(hoverId3, interfaceID, hoverDisabledText, hoverEnabledText, configId, configFrame);
		setChildren(2, hover);
		setBounds(hoverId2, 15, 60, 0, hover);
		setBounds(hoverId3, X, Y, 1, hover);
	}

	public static void addBankHover(int interfaceID, int actionType, int hoverid, int spriteId, int spriteId2,
			String NAME, int Width, int Height, int configFrame, int configId, String Tooltip, int hoverId2,
			int hoverSpriteId, int hoverSpriteId2, String hoverSpriteName, int hoverId3, String hoverDisabledText,
			String hoverEnabledText, int X, int Y, int sprite1, int sprite2) {
		RSInterface hover = addTabInterface(interfaceID);
		hover.id = interfaceID;
		hover.parentID = interfaceID;
		hover.type = 5;
		hover.atActionType = actionType;
		hover.contentType = 0;
		hover.hoverType = hoverid;
		hover.sprite2 = CacheSpriteLoader.getCacheSprite(sprite1);
		hover.sprite1 = CacheSpriteLoader.getCacheSprite(sprite2);
		hover.width = Width;
		hover.tooltip = Tooltip;
		hover.height = Height;
		hover.valueCompareType = new int[1];
		hover.requiredValues = new int[1];
		hover.valueCompareType[0] = 1;
		hover.requiredValues[0] = configId;
		hover.valueIndexArray = new int[1][3];
		hover.valueIndexArray[0][0] = 5;
		hover.valueIndexArray[0][1] = configFrame;
		hover.valueIndexArray[0][2] = 0;
		hover = addTabInterface(hoverid);
		hover.parentID = hoverid;
		hover.id = hoverid;
		hover.type = 0;
		hover.atActionType = 0;
		hover.width = 550;
		hover.height = 334;
		hover.interfaceShown = true;
		hover.hoverType = -1;
		addSprite(hoverId2, hoverSpriteId, hoverSpriteId2, hoverSpriteName, configId, configFrame, sprite1, sprite2);
		addHoverBox(hoverId3, interfaceID, hoverDisabledText, hoverEnabledText, configId, configFrame);
		setChildren(2, hover);
		setBounds(hoverId2, 15, 60, 0, hover);
		setBounds(hoverId3, X, Y, 1, hover);
	}

	public static void addSprite(int ID, int i, int i2, String name, int configId, int configFrame, int sprite1,
			int sprite2) {
		RSInterface Tab = addTabInterface(ID);
		Tab.id = ID;
		Tab.parentID = ID;
		Tab.type = 5;
		Tab.atActionType = 0;
		Tab.contentType = 0;
		Tab.width = 512;
		Tab.height = 334;
		Tab.hoverType = -1;
		Tab.valueCompareType = new int[1];
		Tab.requiredValues = new int[1];
		Tab.valueCompareType[0] = 1;
		Tab.requiredValues[0] = configId;
		Tab.valueIndexArray = new int[1][3];
		Tab.valueIndexArray[0][0] = 5;
		Tab.valueIndexArray[0][1] = configFrame;
		Tab.valueIndexArray[0][2] = 0;
		if (name == null) {
			/*
			 * Tab.itemSpriteZoom1 = -1; Tab.itemSpriteId1 = i;
			 * Tab.itemSpriteZoom2 = 70; Tab.itemSpriteId2 = i2;
			 */
		} else {
			// Tab.disabledSprite = imageLoader(i, name);
			// Tab.enabledSprite = imageLoader(i2, name);
			Tab.sprite2 = CacheSpriteLoader.getCacheSprite(sprite1);
			Tab.sprite1 = CacheSpriteLoader.getCacheSprite(sprite2);
		}
	}

	public static void addPet(int ID) {
		RSInterface petCanvas = interfaceCache[ID] = new RSInterface();
		petCanvas.id = ID;
		petCanvas.parentID = ID;
		petCanvas.type = 6;
		petCanvas.atActionType = 0;
		petCanvas.contentType = 3291;
		petCanvas.width = 136;
		petCanvas.height = 168;
		petCanvas.hoverType = 0;
		petCanvas.modelZoom = 875;
		petCanvas.modelRotation1 = 40;
		petCanvas.modelRotation2 = 1800;
		petCanvas.mediaType = 2;
		petCanvas.mediaID = 4000;
	}

	public static void addBobStorage(int index) {
		RSInterface rsi = interfaceCache[index] = new RSInterface();
		rsi.actions = new String[5];
		rsi.spritesX = new int[20];
		rsi.invStackSizes = new int[30];
		rsi.inv = new int[30];
		rsi.spritesY = new int[20];

		rsi.children = new int[0];
		rsi.childX = new int[0];
		rsi.childY = new int[0];

		rsi.actions[0] = "Remove 1";
		rsi.actions[1] = "Remove 5";
		rsi.actions[2] = "Remove 10";
		rsi.actions[3] = "Remove All";
		rsi.actions[4] = "Remove X";
		rsi.usableItemInterface = false;
		rsi.isInventoryInterface = false;
		// rsi.aBoolean251 = false;
		rsi.filled = false;
		rsi.deletesTargetSlot = false;
		rsi.usableItemInterface = false;
		rsi.isInventoryInterface = false;
		rsi.allowSwapItems = true;
		// rsi.interfaceShown = false;
		rsi.type = -1;
		rsi.invSpritePadX = 24;
		rsi.invSpritePadY = 24;
		rsi.height = 5;
		rsi.width = 6;
		rsi.parentID = 2702;
		rsi.id = index;
		rsi.type = 2;
	}

	public static void addFamiliarHead(int interfaceID, int width, int height, int zoom) {
		RSInterface rsi = addTabInterface(interfaceID);
		rsi.type = 6;
		rsi.mediaType = 2;
		rsi.mediaID = 4000;
		rsi.modelZoom = zoom;
		rsi.modelRotation1 = 40;
		rsi.modelRotation2 = 1800;
		rsi.height = height;
		rsi.width = width;
	}

	public static void addButton(int id, int sid, String tooltip, int w, int h) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;
		tab.parentID = id;
		tab.type = 5;
		tab.atActionType = 1;
		tab.contentType = 0;
		tab.opacity = (byte) 0;
		tab.hoverType = 52;
		tab.sprite1 = CacheSpriteLoader.getCacheSprite(sid);
		tab.width = w;
		tab.height = h;
		tab.tooltip = tooltip;
	}

	public static void addButton2(int id, int sid, String tooltip, int w, int h) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;
		tab.parentID = id;
		tab.type = 5;
		tab.atActionType = 1;
		tab.contentType = 0;
		tab.opacity = (byte) 0;
		tab.hoverType = 52;
		tab.sprite1 = CacheSpriteLoader.getCacheSprite2(sid);
		tab.width = w;
		tab.height = h;
		tab.tooltip = tooltip;
	}

	public static void addSkillChatSprite(int id, int skill) {
		addSpriteLoader(id, 755 + skill);
	}

	public static void addRectangle(int id, int opacity, int color, int enColor, boolean filled, int width,
			int height) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.textColor = color;
		tab.anInt219 = enColor;
		tab.filled = filled;
		tab.id = id;
		tab.parentID = id;
		tab.type = 3;
		tab.atActionType = 0;
		tab.contentType = 0;
		tab.opacity = ((byte) opacity);
		tab.enabledOpacity = ((byte) opacity);
		tab.width = width;
		tab.height = height;
	}
	
	public static void addColorBox(int interfaceID, int width, int height) {
		RSInterface rsi = addTabInterface(interfaceID);
		rsi.type = 14;
		rsi.height = height;
		rsi.width = width;
		rsi.anInt219 = 0xFF0000;
	}
	
	public static void addItemModel(int interfaceID, int width, int height, int zoom) {
		RSInterface rsi = addTabInterface(interfaceID);
		rsi.type = 6;
		rsi.mediaType = 1;
		rsi.mediaID = 65300;//65297
		rsi.modelZoom = zoom;
		rsi.modelRotation1 = 0;
		rsi.modelRotation2 = 1020;
		rsi.height = height;
		rsi.width = width;
	}

	public static void addRectangle(int id, int opacity, int color, boolean filled, int width, int height) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.textColor = color;
		tab.filled = filled;
		tab.id = id;
		tab.parentID = id;
		tab.type = 3;
		tab.atActionType = 0;
		tab.contentType = 0;
		tab.opacity = (byte) opacity;
		tab.enabledOpacity = (byte) opacity;
		tab.width = width;
		tab.height = height;
	}

	public static void addBankItem(int index) {
		RSInterface rsi = interfaceCache[index] = new RSInterface();
		rsi.actions = new String[5];
		rsi.spritesX = new int[20];
		rsi.invStackSizes = new int[30];
		rsi.inv = new int[30];
		rsi.spritesY = new int[20];
		rsi.children = new int[0];
		rsi.childX = new int[0];
		rsi.childY = new int[0];
		rsi.invSpritePadX = 24;
		rsi.invSpritePadY = 24;
		rsi.height = 5;
		rsi.width = 6;
		rsi.id = index;
		rsi.type = 2;
		rsi.hideStackSize = true;
		rsi.hideExamine = true;
	}

	public static void addLunarSprite(int i, int j, int sprite_id) {
		RSInterface RSInterface = addTabInterface(i);
		RSInterface.id = i;
		RSInterface.parentID = i;
		RSInterface.type = 5;
		RSInterface.atActionType = 0;
		RSInterface.contentType = 0;
		RSInterface.opacity = 0;
		RSInterface.hoverType = 52;
		RSInterface.sprite1 = CacheSpriteLoader.getCacheSprite2(sprite_id);
		RSInterface.width = 500;
		RSInterface.height = 500;
		RSInterface.tooltip = "";
	}

	public static void addButtonWSpriteLoader(int id, int sprite, String tooltip, int w, int h) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;
		tab.parentID = id;
		tab.type = 5;
		tab.atActionType = 1;
		tab.contentType = 0;
		tab.opacity = (byte) 0;
		tab.hoverType = 52;
		if (sprite != -1) {
			tab.sprite1 = CacheSpriteLoader.getCacheSprite(sprite);
			tab.sprite2 = CacheSpriteLoader.getCacheSprite(sprite);
		}
		tab.width = w;
		tab.height = h;
		tab.tooltip = tooltip;
	}

	public static void addHoverButtonWSpriteLoader(int i, int spriteId, int width, int height, String text,
			int contentType, int hoverOver, int aT) {// hoverable
		// button
		RSInterface tab = addTabInterface(i);
		tab.id = i;
		tab.parentID = i;
		tab.type = 5;
		tab.atActionType = aT;
		tab.contentType = contentType;
		tab.opacity = 0;
		tab.hoverType = hoverOver;
		tab.sprite2 = CacheSpriteLoader.getCacheSprite(spriteId);
		tab.sprite1 = CacheSpriteLoader.getCacheSprite(spriteId);
		tab.width = width;
		tab.height = height;
		tab.tooltip = text;
	}

	public static void addHoverButtonWSpriteLoader2(int i, int spriteId, int width, int height, String text,
			int contentType, int hoverOver, int aT) {// hoverable
		// button
		RSInterface tab = addTabInterface(i);
		tab.id = i;
		tab.parentID = i;
		tab.type = 5;
		tab.atActionType = aT;
		tab.contentType = contentType;
		tab.opacity = 0;
		tab.hoverType = hoverOver;
		tab.sprite2 = CacheSpriteLoader.getCacheSprite2(spriteId);
		tab.sprite1 = CacheSpriteLoader.getCacheSprite2(spriteId);
		tab.width = width;
		tab.height = height;
		tab.tooltip = text;
	}

	public static void createHover(int id, int x, int width) {
		RSInterface hover = addInterface(id);
		hover.type = 10;
		hover.contentType = x;
		hover.width = width;
		hover.height = 28;
	}

	public static void createSkillHover(int id, int x) {
		RSInterface hover = addInterface(id);
		hover.type = 10;
		hover.contentType = x;
		hover.width = 60;
		hover.height = 28;
	}

	public static void addSkillText(int id, boolean max, int skill) {
		RSInterface text = addInterface(id);
		text.id = id;
		text.parentID = id;
		text.type = 4;
		text.atActionType = 0;
		text.width = 15;
		text.height = 12;
		text.textDrawingAreas = fonts[0];
		text.textShadow = true;
		text.centerText = true;
		text.textColor = 16776960;
		if (!max) {
			text.valueIndexArray = new int[1][];
			text.valueIndexArray[0] = new int[3];
			text.valueIndexArray[0][0] = 1;
			text.valueIndexArray[0][1] = skill;
			text.valueIndexArray[0][2] = 0;
		} else {
			text.valueIndexArray = new int[2][];
			text.valueIndexArray[0] = new int[3];
			text.valueIndexArray[0][0] = 1;
			text.valueIndexArray[0][1] = skill;
			text.valueIndexArray[0][2] = 0;
			text.valueIndexArray[1] = new int[1];
			text.valueIndexArray[1][0] = 0;
		}
		text.message = "%1";
	}

	private static final int CLOSE_BUTTON = 580, CLOSE_BUTTON_HOVER = 581;

	public static void addButton(int ID, int type, int hoverID, int dS, int eS, int W, int H, String text,
			int configFrame, int configId) {
		RSInterface rsinterface = addInterface(ID);
		rsinterface.id = ID;
		rsinterface.parentID = ID;
		rsinterface.type = 5;
		rsinterface.atActionType = type;
		rsinterface.opacity = 0;
		rsinterface.hoverType = hoverID;
		if (dS >= 0) {
			rsinterface.sprite1 = CacheSpriteLoader.getCacheSprite(dS);
		}
		if (eS >= 0) {
			rsinterface.sprite2 = CacheSpriteLoader.getCacheSprite(eS);
		}
		rsinterface.width = W;
		rsinterface.height = H;
		rsinterface.tooltip = text;
		rsinterface.interfaceShown = true;
		rsinterface.valueCompareType = new int[1];
		rsinterface.requiredValues = new int[1];
		rsinterface.valueCompareType[0] = 1;
		rsinterface.requiredValues[0] = configId;
		rsinterface.valueIndexArray = new int[1][3];
		rsinterface.valueIndexArray[0][0] = 5;
		rsinterface.valueIndexArray[0][1] = configFrame;
		rsinterface.valueIndexArray[0][2] = 0;
	}

	private static void addCacheSprite(int id, int sprite1, int sprite2, String sprites) {
		RSInterface rsi = interfaceCache[id] = new RSInterface();
		rsi.sprite1 = method207(sprite1, aClass44, sprites);
		rsi.sprite2 = method207(sprite2, aClass44, sprites);
		rsi.parentID = id;
		rsi.id = id;
		rsi.type = 5;
	}

	public static void addAPlayerHead(int interfaceID, Account a) {
		RSInterface rsi = addTabInterface(interfaceID);
		rsi.type = 6;
		rsi.mediaType = (a.getIDKHead() <= 0 ? 11 : 10);
		int anim = headAnims[Client.getRandom(headAnims.length - 1, false)];
		rsi.enabledAnimationId = rsi.disabledAnimationId = anim;
		rsi.mediaID = (a.getIDKHead() <= 0 ? a.getHelmet() : a.getIDKHead());
		rsi.plrJaw = a.getJaw();
		if (a.getGender() == 1)
			rsi.plrJaw = -1;
		rsi.gender = a.getGender();
		rsi.modelZoom = 2000;
		rsi.modelRotation1 = 40;
		rsi.modelRotation2 = 1800;
		rsi.height = 150;
		rsi.width = 150;
	}

	private final static int[] headAnims = new int[] { 9846, 9742, 9827, 9841, 9851, 9745, 9785, 9805, 9810, 9815, 9820,
			9860, 9835, 9845, 9850, 9855, 9864, 9851 };
	public int plrJaw, gender;

	public static void addChar(int ID) {
		RSInterface t = interfaceCache[ID] = new RSInterface();
		t.id = ID;
		t.parentID = ID;
		t.type = 6;
		t.atActionType = 0;
		t.contentType = 328;
		t.width = 136;
		t.height = 168;
		t.opacity = 0;
		t.hoverType = 0;
		t.modelZoom = 560;
		t.modelRotation1 = 150;
		t.modelRotation2 = 0;
		t.disabledAnimationId = -1;
		t.enabledAnimationId = -1;
	}

	public static void addConfigButton(int ID, int pID, int bID, int bID2, int width, int height, String tT,
			int configID, int aT, int configFrame) {
		RSInterface Tab = addTabInterface(ID);
		Tab.parentID = pID;
		Tab.id = ID;
		Tab.type = 5;
		Tab.atActionType = aT;
		Tab.contentType = 0;
		Tab.width = width;
		Tab.height = height;
		Tab.opacity = 0;
		Tab.hoverType = -1;
		Tab.valueCompareType = new int[1];
		Tab.requiredValues = new int[1];
		Tab.valueCompareType[0] = 1;
		Tab.requiredValues[0] = configID;
		Tab.valueIndexArray = new int[1][3];
		Tab.valueIndexArray[0][0] = 5;
		Tab.valueIndexArray[0][1] = configFrame;
		Tab.valueIndexArray[0][2] = 0;
		Tab.sprite1 = CacheSpriteLoader.getCacheSprite(bID);
		Tab.sprite2 = CacheSpriteLoader.getCacheSprite(bID2);
		Tab.tooltip = tT;
	}

	public static void setSelectableValues(int frame, int configId, int requiredValue) {
		RSInterface rsi = interfaceCache[frame];
		rsi.valueCompareType = new int[] { 5 };
		rsi.requiredValues = new int[] { requiredValue };
		rsi.valueIndexArray = new int[1][3];
		rsi.valueIndexArray[0][0] = 5;
		rsi.valueIndexArray[0][1] = configId;
		rsi.valueIndexArray[0][2] = 0;
	}

	public static void addText(int i, String disabledText, String enabledText, int disabledColor, int enabledColor,
			boolean centered, boolean shadow, int hoverType, int fontId) {
		try {
			RSInterface rsinterface = addTabInterface(i);
			rsinterface.parentID = i;
			rsinterface.id = i;
			rsinterface.type = 4;
			rsinterface.atActionType = 0;
			rsinterface.width = 0;
			rsinterface.height = 0;
			rsinterface.contentType = 0;
			rsinterface.hoverType = hoverType;
			rsinterface.centerText = centered;
			rsinterface.textShadow = shadow;
			rsinterface.textDrawingAreas = fonts[fontId];
			rsinterface.message = disabledText;
			rsinterface.aString228 = enabledText;
			rsinterface.textColor = disabledColor;
			rsinterface.anInt219 = enabledColor;
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public static void setRequireMoney(int id, int amount) {
		interfaceCache[id].valueIndexArray = new int[1][];
		interfaceCache[id].requiredValues = new int[1];
		interfaceCache[id].valueCompareType = new int[1];
		interfaceCache[id].valueIndexArray[0] = new int[4];
		interfaceCache[id].valueIndexArray[0][0] = 4;
		interfaceCache[id].valueIndexArray[0][1] = 3214;
		interfaceCache[id].valueIndexArray[0][2] = 995;
		interfaceCache[id].valueIndexArray[0][3] = 0;
		interfaceCache[id].requiredValues[0] = amount;
		interfaceCache[id].valueCompareType[0] = 10;
	}

	public static void addText(int childId, String text, int color, boolean center, boolean shadow,
			TextDrawingArea rsFont) {
		RSInterface rsi = RSInterface.addInterface(childId);
		rsi.parentID = childId;
		rsi.id = childId;
		rsi.type = 4;
		rsi.atActionType = 0;
		rsi.width = 0;
		rsi.height = 11;
		rsi.contentType = 0;
		rsi.opacity = 0;
		rsi.hoverType = -1;
		rsi.centerText = center;
		rsi.textShadow = shadow;
		rsi.textDrawingAreas = rsFont;
		rsi.message = "";
		rsi.textColor = color;
	}

	private static void addHover(int interfaceId, int actionType, int contentType, int hoverid, int spriteId, int W,
			int H, String tip) {
		RSInterface rsinterfaceHover = addInterface(interfaceId);
		rsinterfaceHover.id = interfaceId;
		rsinterfaceHover.parentID = interfaceId;
		rsinterfaceHover.type = 5;
		rsinterfaceHover.atActionType = actionType;
		rsinterfaceHover.contentType = contentType;
		rsinterfaceHover.hoverType = hoverid;
		rsinterfaceHover.sprite1 = rsinterfaceHover.sprite2 = CacheSpriteLoader.getCacheSprite(spriteId);
		rsinterfaceHover.width = W;
		rsinterfaceHover.height = H;
		rsinterfaceHover.tooltip = tip;
	}

	private static void addHoverBox(int id, int ParentID, String text, String text2, int configId, int configFrame) {
		RSInterface rsi = addTabInterface(id);
		rsi.id = id;
		rsi.parentID = ParentID;
		rsi.type = 8;
		rsi.aString228 = text;// disabledText
		rsi.tooltipBoxText = text2;
		rsi.message = text;
		rsi.valueCompareType = new int[1];
		rsi.requiredValues = new int[1];
		rsi.valueCompareType[0] = 1;
		rsi.requiredValues[0] = configId;
		rsi.valueIndexArray = new int[1][3];
		rsi.valueIndexArray[0][0] = 5;
		rsi.valueIndexArray[0][1] = configFrame;
		rsi.valueIndexArray[0][2] = 0;
	}

	public static void addItemOnInterface(int childId, int interfaceId, String[] options) {
		RSInterface rsi = interfaceCache[childId] = new RSInterface();
		rsi.actions = new String[10];
		rsi.spritesX = new int[20];
		rsi.inv = new int[30];
		rsi.invStackSizes = new int[25];
		rsi.spritesY = new int[20];
		rsi.children = new int[0];
		rsi.childX = new int[0];
		rsi.childY = new int[0];
		for (int i = 0; i < rsi.actions.length; i++) {
			if (i < options.length) {
				if (options[i] != null) {
					rsi.actions[i] = options[i];
				}
			}
		}
		rsi.centerText = true;
		rsi.filled = false;
		rsi.deletesTargetSlot = false;
		rsi.usableItemInterface = false;
		rsi.isInventoryInterface = false;
		rsi.allowSwapItems = false;
		rsi.invSpritePadX = 23;
		rsi.invSpritePadY = 22;
		rsi.height = 5;
		rsi.width = 6;
		rsi.parentID = interfaceId;
		rsi.id = childId;
		rsi.type = 2;
	}

	public static void addHoverText(int id, String text, String tooltip, TextDrawingArea tda[], int idx, int color,
			boolean center, boolean textShadowed, int width, int height) {
		RSInterface rsinterface = addInterface(id);
		rsinterface.id = id;
		rsinterface.parentID = id;
		rsinterface.type = 4;
		rsinterface.atActionType = 1;
		rsinterface.width = width;
		rsinterface.height = height;
		rsinterface.contentType = 0;
		rsinterface.opacity = 0;
		rsinterface.hoverType = -1;
		rsinterface.centerText = center;
		rsinterface.textShadow = textShadowed;
		rsinterface.textDrawingAreas = tda[idx];
		rsinterface.message = text;
		rsinterface.textColor = color;
		rsinterface.tooltip = tooltip;
	}
	
	public static void addHoverButton(int interfaceId, int spriteId, int width, int height, String text,
			int contentType, int hoverOver, int actionType) {
		addHoverButton(interfaceId, spriteId, width, height, text, contentType, hoverOver, actionType, null);
	}
	
	public static void addHoverButton(int interfaceId, int spriteId, int width, int height, String text, int contentType, int hoverOver, int actionType, 
			String tooltip) {
		RSInterface tab = addTabInterface(interfaceId);
		tab.id = interfaceId;
		tab.parentID = interfaceId;
		tab.type = 5;
		tab.atActionType = actionType;
		tab.contentType = contentType;
		tab.opacity = 0;
		tab.hoverType = hoverOver;
		
		if (spriteId >= 0) {
			tab.sprite1 = CacheSpriteLoader.getCacheSprite(spriteId);
			tab.sprite2 = CacheSpriteLoader.getCacheSprite(spriteId);
		}

		tab.width = width;
		tab.height = height;
		tab.tooltip = text;
	}

	public static void addHoverButton2(int interfaceId, int spriteId, int width, int height, String text,
			int contentType, int hoverOver, int actionType) {
		// hoverable button
		RSInterface tab = addTabInterface(interfaceId);
		tab.id = interfaceId;
		tab.parentID = interfaceId;
		tab.type = 5;
		tab.atActionType = actionType;
		tab.contentType = contentType;
		tab.opacity = 0;
		tab.hoverType = hoverOver;

		if (spriteId >= 0) {
			tab.sprite1 = CacheSpriteLoader.getCacheSprite2(spriteId);
			tab.sprite2 = CacheSpriteLoader.getCacheSprite2(spriteId);
		}

		tab.width = width;
		tab.height = height;
		tab.tooltip = text;
	}

	public static void addHoverButton(int i, int disabledSprite, int enabledSprite, int width, int height, String text,
			int contentType, int hoverOver, int aT) {// hoverable
		// button
		RSInterface tab = addTabInterface(i);
		tab.id = i;
		tab.parentID = i;
		tab.type = 5;
		tab.atActionType = aT;
		tab.contentType = contentType;
		tab.opacity = 0;
		tab.hoverType = hoverOver;
		tab.sprite1 = CacheSpriteLoader.getCacheSprite(disabledSprite);
		tab.sprite2 = CacheSpriteLoader.getCacheSprite(enabledSprite);
		tab.width = width;
		tab.height = height;
		tab.tooltip = text;
	}

	public static void addHoverButton2(int i, int disabledSprite, int enabledSprite, int width, int height, String text,
			int contentType, int hoverOver, int aT) {// hoverable
		// button
		RSInterface tab = addTabInterface(i);
		tab.id = i;
		tab.parentID = i;
		tab.type = 5;
		tab.atActionType = aT;
		tab.contentType = contentType;
		tab.opacity = 0;
		tab.hoverType = hoverOver;
		tab.sprite1 = CacheSpriteLoader.getCacheSprite2(disabledSprite);
		tab.sprite2 = CacheSpriteLoader.getCacheSprite2(enabledSprite);
		tab.width = width;
		tab.height = height;
		tab.tooltip = text;
	}

	public static void addHoveredButton(int i, int disabledSprite, int enabledSprite, int w, int h, int IMAGEID) {
		RSInterface tab = addTabInterface(i);
		tab.parentID = i;
		tab.id = i;
		tab.type = 0;
		tab.atActionType = 0;
		tab.width = w;
		tab.height = h;
		tab.interfaceShown = true;
		tab.opacity = 0;
		tab.hoverType = -1;
		tab.scrollMax = 0;
		addHoverImage(IMAGEID, disabledSprite, enabledSprite);
		tab.totalChildren(1);
		tab.child(0, IMAGEID, 0, 0);
	}

	public static void addHoveredButton2(int i, int disabledSprite, int enabledSprite, int w, int h, int IMAGEID) {
		RSInterface tab = addTabInterface(i);
		tab.parentID = i;
		tab.id = i;
		tab.type = 0;
		tab.atActionType = 0;
		tab.width = w;
		tab.height = h;
		tab.interfaceShown = true;
		tab.opacity = 0;
		tab.hoverType = -1;
		tab.scrollMax = 0;
		addHoverImage2(IMAGEID, disabledSprite, enabledSprite);
		tab.totalChildren(1);
		tab.child(0, IMAGEID, 0, 0);
	}

	public static void addHoveredButton(int i, int j, int w, int h, int IMAGEID) {
		// hoverable button
		RSInterface tab = addTabInterface(i);
		tab.parentID = i;
		tab.id = i;
		tab.type = 0;
		tab.atActionType = 0;
		tab.width = w;
		tab.height = h;
		tab.interfaceShown = true;
		tab.opacity = 0;
		tab.hoverType = -1;
		tab.scrollMax = 0;
		addHoverImage(IMAGEID, j, j);
		tab.totalChildren(1);
		tab.child(0, IMAGEID, 0, 0);
	}

	private static void addHoverImage(int i, int j, int k) {
		RSInterface tab = addTabInterface(i);
		tab.id = i;
		tab.parentID = i;
		tab.type = 5;
		tab.atActionType = 0;
		tab.contentType = 0;
		tab.width = 512;
		tab.height = 334;
		tab.opacity = 0;
		tab.hoverType = 52;
		tab.sprite1 = CacheSpriteLoader.getCacheSprite(j);
		tab.sprite2 = CacheSpriteLoader.getCacheSprite(k);
	}

	private static void addHoverImage2(int i, int j, int k) {
		RSInterface tab = addTabInterface(i);
		tab.id = i;
		tab.parentID = i;
		tab.type = 5;
		tab.atActionType = 0;
		tab.contentType = 0;
		tab.width = 512;
		tab.height = 334;
		tab.opacity = 0;
		tab.hoverType = 52;
		tab.sprite1 = CacheSpriteLoader.getCacheSprite2(j);
		tab.sprite2 = CacheSpriteLoader.getCacheSprite2(k);
	}

	public static RSInterface addInterface(int id) {
		RSInterface rsi = interfaceCache[id] = new RSInterface();
		rsi.id = id;
		rsi.parentID = id;
		rsi.width = 512;
		rsi.height = 334;
		return rsi;
	}

	// done
	public static void addLunar2RunesSmallBox(int ID, int r1, int r2, int ra1, int ra2, int rune1, int lvl, String name,
			String descr, TextDrawingArea[] TDA, int sid, int suo, int type) {
		RSInterface rsInterface = addInterface(ID);
		rsInterface.id = ID;
		rsInterface.parentID = 1151;
		rsInterface.type = 5;
		rsInterface.atActionType = type;
		rsInterface.contentType = 0;
		rsInterface.hoverType = ID + 1;
		rsInterface.spellUsableOn = suo;
		rsInterface.selectedActionName = "Cast On";
		rsInterface.width = 20;
		rsInterface.height = 20;
		rsInterface.tooltip = "Cast @gre@" + name;
		rsInterface.spellName = name;
		rsInterface.valueCompareType = new int[3];
		rsInterface.requiredValues = new int[3];
		rsInterface.valueCompareType[0] = 3;
		rsInterface.requiredValues[0] = ra1;
		rsInterface.valueCompareType[1] = 3;
		rsInterface.requiredValues[1] = ra2;
		rsInterface.valueCompareType[2] = 3;
		rsInterface.requiredValues[2] = lvl;
		rsInterface.valueIndexArray = new int[3][];
		rsInterface.valueIndexArray[0] = new int[4];
		rsInterface.valueIndexArray[0][0] = 4;
		rsInterface.valueIndexArray[0][1] = 3214;
		rsInterface.valueIndexArray[0][2] = r1;
		rsInterface.valueIndexArray[0][3] = 0;
		rsInterface.valueIndexArray[1] = new int[4];
		rsInterface.valueIndexArray[1][0] = 4;
		rsInterface.valueIndexArray[1][1] = 3214;
		rsInterface.valueIndexArray[1][2] = r2;
		rsInterface.valueIndexArray[1][3] = 0;
		rsInterface.valueIndexArray[2] = new int[3];
		rsInterface.valueIndexArray[2][0] = 1;
		rsInterface.valueIndexArray[2][1] = 6;
		rsInterface.valueIndexArray[2][2] = 0;
		rsInterface.sprite1 = CacheSpriteLoader.getCacheSprite(sid);
		rsInterface.sprite2 = CacheSpriteLoader.getCacheSprite(sid + 39);
		RSInterface INT = addInterface(ID + 1);
		INT.interfaceShown = true;
		INT.hoverType = -1;
		setChildren(7, INT);
		addLunarSprite(ID + 2, 205);
		setBounds(ID + 2, 0, 0, 0, INT);
		addText(ID + 3, "Level " + (lvl + 1) + ": " + name, 0xFF981F, true, true, 52, TDA, 1);
		setBounds(ID + 3, 90, 4, 1, INT);
		addText(ID + 4, descr, 0xAF6A1A, true, true, 52, TDA, 0);
		setBounds(ID + 4, 90, 19, 2, INT);
		setBounds(30016, 37, 35, 3, INT);// Rune
		setBounds(rune1, 112, 35, 4, INT);// Rune
		addRuneText(ID + 5, ra1 + 1, r1, TDA);
		setBounds(ID + 5, 50, 66, 5, INT);
		addRuneText(ID + 6, ra2 + 1, r2, TDA);
		setBounds(ID + 6, 123, 66, 6, INT);
	}

	public static void addItemModel(int interfaceId, int itemId, int w, int h, int zoom) {
		RSInterface rsinterface = interfaceCache[interfaceId] = new RSInterface();
		ItemDefinition itemDef = ItemDefinition.get(itemId);
		rsinterface.modelRotation1 = itemDef.modelRotation1;
		rsinterface.modelRotation2 = itemDef.modelRotation2;
		rsinterface.type = 6;
		rsinterface.mediaType = 4;
		rsinterface.mediaID = itemId;
		rsinterface.modelZoom = zoom;
		rsinterface.height = h;
		rsinterface.width = w;
	}

	// done
	public static void addLunar3RunesBigBox(int ID, int r1, int r2, int r3, int ra1, int ra2, int ra3, int rune1,
			int rune2, int lvl, String name, String descr, TextDrawingArea[] TDA, int sid, int suo, int type) {
		RSInterface rsInterface = addInterface(ID);
		rsInterface.id = ID;
		rsInterface.parentID = 1151;
		rsInterface.type = 5;
		rsInterface.atActionType = type;
		rsInterface.contentType = 0;
		rsInterface.hoverType = ID + 1;
		rsInterface.spellUsableOn = suo;
		rsInterface.selectedActionName = "Cast on";
		rsInterface.width = 20;
		rsInterface.height = 20;
		rsInterface.tooltip = "Cast @gre@" + name;
		rsInterface.spellName = name;
		rsInterface.valueCompareType = new int[4];
		rsInterface.requiredValues = new int[4];
		rsInterface.valueCompareType[0] = 3;
		rsInterface.requiredValues[0] = ra1;
		rsInterface.valueCompareType[1] = 3;
		rsInterface.requiredValues[1] = ra2;
		rsInterface.valueCompareType[2] = 3;
		rsInterface.requiredValues[2] = ra3;
		rsInterface.valueCompareType[3] = 3;
		rsInterface.requiredValues[3] = lvl;
		rsInterface.valueIndexArray = new int[4][];
		rsInterface.valueIndexArray[0] = new int[4];
		rsInterface.valueIndexArray[0][0] = 4;
		rsInterface.valueIndexArray[0][1] = 3214;
		rsInterface.valueIndexArray[0][2] = r1;
		rsInterface.valueIndexArray[0][3] = 0;
		rsInterface.valueIndexArray[1] = new int[4];
		rsInterface.valueIndexArray[1][0] = 4;
		rsInterface.valueIndexArray[1][1] = 3214;
		rsInterface.valueIndexArray[1][2] = r2;
		rsInterface.valueIndexArray[1][3] = 0;
		rsInterface.valueIndexArray[2] = new int[4];
		rsInterface.valueIndexArray[2][0] = 4;
		rsInterface.valueIndexArray[2][1] = 3214;
		rsInterface.valueIndexArray[2][2] = r3;
		rsInterface.valueIndexArray[2][3] = 0;
		rsInterface.valueIndexArray[3] = new int[3];
		rsInterface.valueIndexArray[3][0] = 1;
		rsInterface.valueIndexArray[3][1] = 6;
		rsInterface.valueIndexArray[3][2] = 0;
		rsInterface.sprite1 = CacheSpriteLoader.getCacheSprite(sid);
		rsInterface.sprite2 = CacheSpriteLoader.getCacheSprite(sid + 39);
		RSInterface INT = addInterface(ID + 1);
		INT.interfaceShown = true;
		INT.hoverType = -1;
		setChildren(9, INT);
		addLunarSprite(ID + 2, 206);
		setBounds(ID + 2, 0, 0, 0, INT);
		addText(ID + 3, "Level " + (lvl + 1) + ": " + name, 0xFF981F, true, true, 52, TDA, 1);
		setBounds(ID + 3, 90, 4, 1, INT);
		addText(ID + 4, descr, 0xAF6A1A, true, true, 52, TDA, 0);
		setBounds(ID + 4, 90, 21, 2, INT);
		setBounds(30016, 14, 48, 3, INT);
		setBounds(rune1, 74, 48, 4, INT);
		setBounds(rune2, 130, 48, 5, INT);
		addRuneText(ID + 5, ra1 + 1, r1, TDA);
		setBounds(ID + 5, 26, 79, 6, INT);
		addRuneText(ID + 6, ra2 + 1, r2, TDA);
		setBounds(ID + 6, 87, 79, 7, INT);
		addRuneText(ID + 7, ra3 + 1, r3, TDA);
		setBounds(ID + 7, 142, 79, 8, INT);
	}

	// done
	public static void addLunar3RunesLargeBox(int ID, int r1, int r2, int r3, int ra1, int ra2, int ra3, int rune1,
			int rune2, int lvl, String name, String descr, TextDrawingArea[] TDA, int sid, int suo, int type) {
		RSInterface rsInterface = addInterface(ID);
		rsInterface.id = ID;
		rsInterface.parentID = 1151;
		rsInterface.type = 5;
		rsInterface.atActionType = type;
		rsInterface.contentType = 0;
		rsInterface.hoverType = ID + 1;
		rsInterface.spellUsableOn = suo;
		rsInterface.selectedActionName = "Cast on";
		rsInterface.width = 20;
		rsInterface.height = 20;
		rsInterface.tooltip = "Cast @gre@" + name;
		rsInterface.spellName = name;
		rsInterface.valueCompareType = new int[4];
		rsInterface.requiredValues = new int[4];
		rsInterface.valueCompareType[0] = 3;
		rsInterface.requiredValues[0] = ra1;
		rsInterface.valueCompareType[1] = 3;
		rsInterface.requiredValues[1] = ra2;
		rsInterface.valueCompareType[2] = 3;
		rsInterface.requiredValues[2] = ra3;
		rsInterface.valueCompareType[3] = 3;
		rsInterface.requiredValues[3] = lvl;
		rsInterface.valueIndexArray = new int[4][];
		rsInterface.valueIndexArray[0] = new int[4];
		rsInterface.valueIndexArray[0][0] = 4;
		rsInterface.valueIndexArray[0][1] = 3214;
		rsInterface.valueIndexArray[0][2] = r1;
		rsInterface.valueIndexArray[0][3] = 0;
		rsInterface.valueIndexArray[1] = new int[4];
		rsInterface.valueIndexArray[1][0] = 4;
		rsInterface.valueIndexArray[1][1] = 3214;
		rsInterface.valueIndexArray[1][2] = r2;
		rsInterface.valueIndexArray[1][3] = 0;
		rsInterface.valueIndexArray[2] = new int[4];
		rsInterface.valueIndexArray[2][0] = 4;
		rsInterface.valueIndexArray[2][1] = 3214;
		rsInterface.valueIndexArray[2][2] = r3;
		rsInterface.valueIndexArray[2][3] = 0;
		rsInterface.valueIndexArray[3] = new int[3];
		rsInterface.valueIndexArray[3][0] = 1;
		rsInterface.valueIndexArray[3][1] = 6;
		rsInterface.valueIndexArray[3][2] = 0;
		rsInterface.sprite1 = CacheSpriteLoader.getCacheSprite(sid);
		rsInterface.sprite2 = CacheSpriteLoader.getCacheSprite(sid + 39);
		RSInterface INT = addInterface(ID + 1);
		INT.interfaceShown = true;
		INT.hoverType = -1;
		setChildren(9, INT);
		addLunarSprite(ID + 2, 207);
		setBounds(ID + 2, 0, 0, 0, INT);
		addText(ID + 3, "Level " + (lvl + 1) + ": " + name, 0xFF981F, true, true, 52, TDA, 1);
		setBounds(ID + 3, 90, 4, 1, INT);
		addText(ID + 4, descr, 0xAF6A1A, true, true, 52, TDA, 0);
		setBounds(ID + 4, 90, 34, 2, INT);
		setBounds(30016, 14, 61, 3, INT);
		setBounds(rune1, 74, 61, 4, INT);
		setBounds(rune2, 130, 61, 5, INT);
		addRuneText(ID + 5, ra1 + 1, r1, TDA);
		setBounds(ID + 5, 26, 92, 6, INT);
		addRuneText(ID + 6, ra2 + 1, r2, TDA);
		setBounds(ID + 6, 87, 92, 7, INT);
		addRuneText(ID + 7, ra3 + 1, r3, TDA);
		setBounds(ID + 7, 142, 92, 8, INT);
	}

	// done
	public static void addLunar3RunesSmallBox(int ID, int r1, int r2, int r3, int ra1, int ra2, int ra3, int rune1,
			int rune2, int lvl, String name, String descr, TextDrawingArea[] TDA, int sid, int suo, int type) {
		RSInterface rsInterface = addInterface(ID);
		rsInterface.id = ID;
		rsInterface.parentID = 1151;
		rsInterface.type = 5;
		rsInterface.atActionType = type;
		rsInterface.contentType = 0;
		rsInterface.hoverType = ID + 1;
		rsInterface.spellUsableOn = suo;
		rsInterface.selectedActionName = "Cast on";
		rsInterface.width = 20;
		rsInterface.height = 20;
		rsInterface.tooltip = "Cast @gre@" + name;
		rsInterface.spellName = name;
		rsInterface.valueCompareType = new int[4];
		rsInterface.requiredValues = new int[4];
		rsInterface.valueCompareType[0] = 3;
		rsInterface.requiredValues[0] = ra1;
		rsInterface.valueCompareType[1] = 3;
		rsInterface.requiredValues[1] = ra2;
		rsInterface.valueCompareType[2] = 3;
		rsInterface.requiredValues[2] = ra3;
		rsInterface.valueCompareType[3] = 3;
		rsInterface.requiredValues[3] = lvl;
		rsInterface.valueIndexArray = new int[4][];
		rsInterface.valueIndexArray[0] = new int[4];
		rsInterface.valueIndexArray[0][0] = 4;
		rsInterface.valueIndexArray[0][1] = 3214;
		rsInterface.valueIndexArray[0][2] = r1;
		rsInterface.valueIndexArray[0][3] = 0;
		rsInterface.valueIndexArray[1] = new int[4];
		rsInterface.valueIndexArray[1][0] = 4;
		rsInterface.valueIndexArray[1][1] = 3214;
		rsInterface.valueIndexArray[1][2] = r2;
		rsInterface.valueIndexArray[1][3] = 0;
		rsInterface.valueIndexArray[2] = new int[4];
		rsInterface.valueIndexArray[2][0] = 4;
		rsInterface.valueIndexArray[2][1] = 3214;
		rsInterface.valueIndexArray[2][2] = r3;
		rsInterface.valueIndexArray[2][3] = 0;
		rsInterface.valueIndexArray[3] = new int[3];
		rsInterface.valueIndexArray[3][0] = 1;
		rsInterface.valueIndexArray[3][1] = 6;
		rsInterface.valueIndexArray[3][2] = 0;
		rsInterface.sprite1 = CacheSpriteLoader.getCacheSprite(sid);
		rsInterface.sprite2 = CacheSpriteLoader.getCacheSprite(sid + 39);
		RSInterface INT = addInterface(ID + 1);
		INT.interfaceShown = true;
		INT.hoverType = -1;
		setChildren(9, INT);
		addLunarSprite(ID + 2, 205);
		setBounds(ID + 2, 0, 0, 0, INT);
		if (name.toLowerCase().contains("training teleports") || name.toLowerCase().contains("skilling areas")
				|| name.toLowerCase().contains("minigames") || name.toLowerCase().contains("dungeons")
				|| name.toLowerCase().contains("quests") || name.toLowerCase().contains("city teleports")
				|| name.toLowerCase().contains("wilderness areas") || name.toLowerCase().contains("boss teleports")) {
			addText(ID + 3, name, 0xFF981F, true, true, 52, 1);
		} else {
			addText(ID + 3, "Level " + (lvl + 1) + ": " + name, 0xFF981F, true, true, 52, 1);
		}
		setBounds(ID + 3, 90, 4, 1, INT);
		addText(ID + 4, descr, 0xAF6A1A, true, true, 52, TDA, 0);
		setBounds(ID + 4, 90, 19, 2, INT);
		setBounds(30016, 14, 35, 3, INT);
		setBounds(rune1, 74, 35, 4, INT);
		setBounds(rune2, 130, 35, 5, INT);
		addRuneText(ID + 5, ra1 + 1, r1, TDA);
		setBounds(ID + 5, 26, 66, 6, INT);
		addRuneText(ID + 6, ra2 + 1, r2, TDA);
		setBounds(ID + 6, 87, 66, 7, INT);
		addRuneText(ID + 7, ra3 + 1, r3, TDA);
		setBounds(ID + 7, 142, 66, 8, INT);
	}

	// done
	private static void addLunarSprite(int i, int j) {
		RSInterface RSInterface = addInterface(i);
		RSInterface.id = i;
		RSInterface.parentID = i;
		RSInterface.type = 5;
		RSInterface.atActionType = 0;
		RSInterface.contentType = 0;
		RSInterface.opacity = 0;
		RSInterface.hoverType = 52;
		RSInterface.sprite1 = CacheSpriteLoader.getCacheSprite(j);
		RSInterface.width = 500;
		RSInterface.height = 500;
		RSInterface.tooltip = "";
	}

	// done
	public static void addRuneText(int ID, int runeAmount, int RuneID, TextDrawingArea[] font) {
		RSInterface rsInterface = addInterface(ID);
		rsInterface.id = ID;
		rsInterface.parentID = 1151;
		rsInterface.type = 4;
		rsInterface.atActionType = 0;
		rsInterface.contentType = 0;
		rsInterface.width = 0;
		rsInterface.height = 14;
		rsInterface.opacity = 0;
		rsInterface.hoverType = -1;
		rsInterface.valueCompareType = new int[1];
		rsInterface.requiredValues = new int[1];
		rsInterface.valueCompareType[0] = 3;
		rsInterface.requiredValues[0] = runeAmount;
		rsInterface.valueIndexArray = new int[1][4];
		rsInterface.valueIndexArray[0][0] = 4;
		rsInterface.valueIndexArray[0][1] = 3214;
		rsInterface.valueIndexArray[0][2] = RuneID;
		rsInterface.valueIndexArray[0][3] = 0;
		rsInterface.centerText = true;
		rsInterface.textDrawingAreas = font[0];
		rsInterface.textShadow = true;
		rsInterface.message = "%1/" + runeAmount + "";
		rsInterface.aString228 = "";
		rsInterface.textColor = 12582912;
		rsInterface.anInt219 = 49152;
	}

	public static void addSprite(int id, int spriteId) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;
		tab.parentID = id;
		tab.type = 5;
		tab.atActionType = 0;
		tab.contentType = 0;
		tab.opacity = (byte) 0;
		tab.hoverType = 52;

		if (spriteId != -1) {
			tab.sprite1 = CacheSpriteLoader.getCacheSprite(spriteId);
			tab.sprite2 = CacheSpriteLoader.getCacheSprite(spriteId);
		}

		tab.width = 512;
		tab.height = 334;
	}

	public static void addTransparentSprite(int id, int spriteId, int spriteId2, int op) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;
		tab.parentID = id;
		tab.type = 13;
		tab.atActionType = 0;
		tab.contentType = 0;
		tab.hoverType = 52;
		tab.sprite1 = CacheSpriteLoader.getCacheSprite(spriteId);
		tab.sprite2 = CacheSpriteLoader.getCacheSprite(spriteId2);
		tab.width = 512;
		tab.height = 334;
		tab.opacity = (byte) op;
		tab.customOpacity = op;
	}

	public static void addSprite(int ID, int i, int i2, int configId, int configFrame) {
		RSInterface Tab = addTabInterface(ID);
		Tab.id = ID;
		Tab.parentID = ID;
		Tab.type = 5;
		Tab.atActionType = 0;
		Tab.contentType = 0;
		Tab.width = 512;
		Tab.height = 334;
		Tab.opacity = (byte) 0;
		Tab.hoverType = -1;
		Tab.valueCompareType = new int[1];
		Tab.requiredValues = new int[1];
		Tab.valueCompareType[0] = 1;
		Tab.requiredValues[0] = configId;
		Tab.valueIndexArray = new int[1][3];
		Tab.valueIndexArray[0][0] = 5;
		Tab.valueIndexArray[0][1] = configFrame;
		Tab.valueIndexArray[0][2] = 0;
		Tab.sprite1 = CacheSpriteLoader.getCacheSprite(i);
		Tab.sprite2 = CacheSpriteLoader.getCacheSprite(i2);
	}

	public static RSInterface addTabInterface(int id) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;// 250
		tab.parentID = id;// 236
		tab.type = 0;// 262
		tab.atActionType = 0;// 217
		tab.contentType = 0;
		tab.width = 512;// 220
		tab.height = 700;// 267
		tab.opacity = (byte) 0;
		tab.hoverType = -1;// Int 230
		return tab;
	}

	/* 3204: */ public static void addClickableText(int id, String text, String tooltip, TextDrawingArea[] tda, int idx,
			int color, boolean center, boolean shadow, int width)
	/* 3205: */ {
		/* 3206:3144 */ RSInterface tab = addTabInterface(id);
		/* 3207:3145 */ tab.parentID = id;
		/* 3208:3146 */ tab.id = id;
		/* 3209:3147 */ tab.type = 4;
		/* 3210:3148 */ tab.atActionType = 1;
		/* 3211:3149 */ tab.width = width;
		/* 3212:3150 */ tab.height = 11;
		/* 3213:3151 */ tab.contentType = 0;
		/* 3214:3152 */ tab.opacity = 0;
		/* 3215:3153 */ tab.hoverType = -1;
		/* 3216:3154 */ tab.centerText = center;
		/* 3217:3155 */ tab.textShadow = shadow;
		/* 3218:3156 */ tab.textDrawingAreas = tda[idx];
		/* 3219:3157 */ tab.message = text;
		/* 3220:3158 */ tab.aString228 = "";
		tab.textColor = color;
		/* 3221:3159 */ tab.anInt219 = 0;

		/* 3223:3161 */ tab.anInt216 = 16777215;

		/* 3224:3162 */ tab.anInt239 = 0;
		/* 3225:3163 */ tab.tooltip = tooltip;
		/* 3226: */ }

	/* 982: */ public static void addClickableText(int id, String text, String tooltip, TextDrawingArea[] tda, int idx,
			int color, int width, int height)
	/* 983: */ {
		/* 984: 942 */ RSInterface Tab = addTabInterface(id);
		/* 985: 943 */ Tab.parentID = id;
		/* 986: 944 */ Tab.id = id;
		/* 987: 945 */ Tab.type = 4;
		/* 988: 946 */ Tab.atActionType = 1;
		/* 989: 947 */ Tab.width = width;
		/* 990: 948 */ Tab.height = height;
		/* 991: 949 */ Tab.contentType = 0;
		/* 992: 950 */ Tab.opacity = 0;
		/* 993: 951 */ Tab.hoverType = -1;
		/* 994: 952 */ Tab.centerText = false;
		/* 995: 953 */ Tab.textShadow = true;
		/* 996: 954 */ Tab.textDrawingAreas = tda[idx];
		/* 997: 955 */ Tab.message = text;
		/* 998: 956 */ Tab.tooltip = tooltip;
		/* 999: 957 */ Tab.aString228 = "";
		/* 1000: 958 */ Tab.textColor = color;
		/* 1001: 959 */ Tab.anInt219 = 0;
		/* 1002: 960 */ Tab.anInt216 = 16777215;
		/* 1003: 961 */ Tab.anInt239 = 0;
		/* 1004: */ }

	public static void addSkillButton(int id, String skillGuide) {
		RSInterface button = addTabInterface(id);
		button.type = 5;
		button.atActionType = 5;
		button.contentType = 0;
		button.width = 60;
		button.height = 28;
		// button.disabledSprite = getSprite("Interfaces/Skilltab/Button");
		button.tooltip = "@whi@View @or1@" + skillGuide + " @whi@Options";
	}

	public static void addText(int i, String s, int k, boolean l, boolean m, int a, TextDrawingArea[] TDA, int j) {
		RSInterface RSInterface = addInterface(i);
		RSInterface.parentID = i;
		RSInterface.id = i;
		RSInterface.type = 4;
		RSInterface.atActionType = 0;
		RSInterface.width = 0;
		RSInterface.height = 0;
		RSInterface.contentType = 0;
		RSInterface.opacity = 0;
		RSInterface.hoverType = a;
		RSInterface.centerText = l;
		RSInterface.textShadow = m;
		RSInterface.textDrawingAreas = TDA[j];
		RSInterface.message = s;
		RSInterface.aString228 = "";
		RSInterface.textColor = k;
	}

	public static void addText(int id, String text, TextDrawingArea tda[], int idx, int color, boolean centered) {
		RSInterface rsi = interfaceCache[id] = new RSInterface();

		if (centered) {
			rsi.centerText = true;
		}

		rsi.textShadow = true;
		rsi.textDrawingAreas = tda[idx];
		rsi.message = text;
		rsi.textColor = color;
		rsi.id = id;
		rsi.type = 4;
	}

	public static void addText(int id, String text, TextDrawingArea tda[], int idx, int color, boolean center,
			boolean shadow) {
		RSInterface tab = addTabInterface(id);
		tab.parentID = id;
		tab.id = id;
		tab.type = 4;
		tab.atActionType = 0;
		tab.width = 0;
		tab.height = 11;
		tab.contentType = 0;
		tab.opacity = 0;
		tab.hoverType = -1;
		tab.centerText = center;
		tab.textShadow = shadow;
		tab.textDrawingAreas = tda[idx];
		tab.message = text;
		tab.aString228 = "";
		tab.textColor = color;
		tab.anInt219 = 0;
		tab.anInt216 = 0;
		tab.anInt239 = 0;
	}

	public static void addText(int id, String text, TextDrawingArea wid[], int idx, int color) {
		RSInterface rsinterface = addTabInterface(id);
		rsinterface.id = id;
		rsinterface.parentID = id;
		rsinterface.type = 4;
		rsinterface.atActionType = 0;
		rsinterface.width = 174;
		rsinterface.height = 11;
		rsinterface.contentType = 0;
		rsinterface.hoverType = -1;
		rsinterface.centerText = false;
		rsinterface.textShadow = true;
		rsinterface.textDrawingAreas = wid[idx];
		rsinterface.message = text;
		rsinterface.textColor = color;
	}

	public static void addTransparentSpriteWSpriteLoader(int id, int spriteId, int opacity) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;
		tab.parentID = id;
		tab.type = 3;
		tab.atActionType = 0;
		tab.contentType = 0;
		tab.opacity = (byte) opacity;
		tab.hoverType = 52;
		tab.sprite2 = CacheSpriteLoader.getCacheSprite(spriteId);
		tab.sprite1 = CacheSpriteLoader.getCacheSprite(spriteId);
		tab.width = 512;
		tab.height = 334;
		// tab.drawsTransparent = true;
	}

	public static void addTransparentSpriteWSpriteLoader2(int id, int spriteId, int opacity) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;
		tab.parentID = id;
		tab.type = 13;
		tab.atActionType = 0;
		tab.contentType = 0;
		tab.opacity = (byte) opacity;
		tab.customOpacity = opacity;
		tab.hoverType = 52;
		tab.sprite1 = CacheSpriteLoader.getCacheSprite2(spriteId);
		tab.sprite2 = CacheSpriteLoader.getCacheSprite2(spriteId);
		tab.width = 512;
		tab.height = 334;
		tab.drawsTransparent = true;
	}

	public static void addConfigButtonWSpriteLoader(int ID, int pID, int bID, int bID2, int width, int height,
			String tT, int configID, int aT, int configFrame) {
		RSInterface Tab = addTabInterface(ID);
		Tab.parentID = pID;
		Tab.id = ID;
		Tab.type = 5;
		Tab.atActionType = aT;
		Tab.contentType = 0;
		Tab.width = width;
		Tab.height = height;
		Tab.hoverType = -1;
		Tab.valueCompareType = new int[1];
		Tab.requiredValues = new int[1];
		Tab.valueCompareType[0] = 1;
		Tab.requiredValues[0] = configID;
		Tab.valueIndexArray = new int[1][3];
		Tab.valueIndexArray[0][0] = 5;
		Tab.valueIndexArray[0][1] = configFrame;
		Tab.valueIndexArray[0][2] = 0;
		Tab.sprite1 = CacheSpriteLoader.getCacheSprite(bID);
		Tab.sprite2 = CacheSpriteLoader.getCacheSprite(bID2);
		Tab.tooltip = tT;
	}

	public static void addButtonWSpriteLoader2(int id, int sprite, String tooltip, int w, int h) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;
		tab.parentID = id;
		tab.type = 5;
		tab.atActionType = 1;
		tab.contentType = 0;
		tab.opacity = (byte) 0;
		tab.hoverType = 52;
		if (sprite != -1) {
			tab.sprite2 = CacheSpriteLoader.getCacheSprite2(sprite);
			tab.sprite1 = CacheSpriteLoader.getCacheSprite2(sprite);
		}
		tab.width = w;
		tab.height = h;
		tab.tooltip = tooltip;
	}

	public static void addButtonWSpriteLoader(int id, int spriteId, String tooltip) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;
		tab.parentID = id;
		tab.type = 5;
		tab.atActionType = 1;
		tab.contentType = 0;
		tab.opacity = (byte) 0;
		tab.hoverType = 52;
		tab.sprite2 = CacheSpriteLoader.getCacheSprite(spriteId);
		tab.sprite1 = CacheSpriteLoader.getCacheSprite(spriteId);
		tab.width = tab.sprite2.myWidth;
		tab.height = tab.sprite1.myHeight - 2;
		tab.tooltip = tooltip;
	}

	public static void addToggleButton(int id, int sprite, int setconfig, int width, int height, String s) {
		RSInterface rsi = addInterface(id);
		rsi.sprite1 = CacheSpriteLoader.getCacheSprite(sprite);
		rsi.sprite2 = CacheSpriteLoader.getCacheSprite(sprite + 1);
		rsi.requiredValues = new int[1];
		rsi.requiredValues[0] = 1;
		rsi.valueCompareType = new int[1];
		rsi.valueCompareType[0] = 1;
		rsi.valueIndexArray = new int[1][3];
		rsi.valueIndexArray[0][0] = 5;
		rsi.valueIndexArray[0][1] = setconfig;
		rsi.valueIndexArray[0][2] = 0;
		rsi.atActionType = 4;
		rsi.width = width;
		rsi.hoverType = -1;
		rsi.parentID = id;
		rsi.id = id;
		rsi.type = 5;
		rsi.height = height;
		rsi.tooltip = s;
	}

	public static void addToggleButton2(int id, int sprite, int setconfig, int width, int height, String s) {
		RSInterface rsi = addInterface(id);
		rsi.sprite1 = CacheSpriteLoader.getCacheSprite2(sprite);
		rsi.sprite2 = CacheSpriteLoader.getCacheSprite2(sprite + 1);
		rsi.requiredValues = new int[1];
		rsi.requiredValues[0] = 1;
		rsi.valueCompareType = new int[1];
		rsi.valueCompareType[0] = 1;
		rsi.valueIndexArray = new int[1][3];
		rsi.valueIndexArray[0][0] = 5;
		rsi.valueIndexArray[0][1] = setconfig;
		rsi.valueIndexArray[0][2] = 0;
		rsi.atActionType = 4;
		rsi.width = width;
		rsi.hoverType = -1;
		rsi.parentID = id;
		rsi.id = id;
		rsi.type = 5;
		rsi.height = height;
		rsi.tooltip = s;
	}

	public void addTooltip(int id, String text) {
		RSInterface rsi = addInterface(id);
		rsi.id = id;
		rsi.type = 0;
		rsi.interfaceShown = true;
		rsi.hoverType = -1;
		addTooltipBox(id + 1, text);
		rsi.totalChildren(1);
		rsi.child(0, id + 1, 0, 0);
	}

	public static void addBankItem(int index, Boolean hasOption) {
		RSInterface rsi = interfaceCache[index] = new RSInterface();
		rsi.actions = new String[5];
		rsi.spritesX = new int[20];
		rsi.invStackSizes = new int[30];
		rsi.inv = new int[30];
		rsi.spritesY = new int[20];

		rsi.children = new int[0];
		rsi.childX = new int[0];
		rsi.childY = new int[0];

		rsi.invSpritePadX = 24;
		rsi.invSpritePadY = 24;
		rsi.height = 5;
		rsi.width = 6;
		rsi.parentID = 5292;
		rsi.id = index;
		rsi.type = 2;
		rsi.hideStackSize = true;
		rsi.hideExamine = hasOption;
	}

	private void addTooltipBox(int id, String text) {
		RSInterface rsi = addInterface(id);
		rsi.id = id;
		rsi.parentID = id;
		rsi.type = 9;
		rsi.tooltipBoxText = text;
	}

	public static void drawRune(int i, int id) {
		RSInterface RSInterface = addInterface(i);
		RSInterface.type = 5;
		RSInterface.atActionType = 0;
		RSInterface.contentType = 0;
		RSInterface.opacity = 0;
		RSInterface.hoverType = 52;
		RSInterface.sprite1 = CacheSpriteLoader.getCacheSprite(id);
		RSInterface.width = 500;
		RSInterface.height = 500;
	}

	private static Sprite method207(int i, Archive streamLoader, String s) {
		long l = (TextClass.method585(s) << 8) + i;
		Sprite sprite = (Sprite) aMRUNodes_238.insertFromCache(l);
		if (sprite != null) {
			return sprite;
		}
		try {
			sprite = new Sprite(streamLoader, s, i);
			aMRUNodes_238.removeFromCache(sprite, l);
		} catch (Exception _ex) {
			return null;
		}
		return sprite;
	}

	public static void clearModelCache(boolean flag, Model model) {
		int i = 0;// was parameter
		int j = 5;// was parameter
		if (flag) {
			return;
		}
		aMRUNodes_264.unlinkAll();
		if (model != null && j != 4) {
			aMRUNodes_264.removeFromCache(model, (j << 16) + i);
		}
	}

	public static void removeSomething(int id) {
		interfaceCache[id] = new RSInterface();
	}

	public static void removeSomething(RSInterface child) {
		interfaceCache[child.id] = new RSInterface();
	}

	public static void setBounds(int id, int x, int y, int index, RSInterface RSinterface) {
		RSinterface.children[index] = id;
		RSinterface.childX[index] = x;
		RSinterface.childY[index] = y;
	}

	public static void setChildren(int total, RSInterface i) {
		i.children = new int[total];
		i.childX = new int[total];
		i.childY = new int[total];
	}

	public void setChild(int id, int interID, int x, int y) {
		children[id] = interID;
		childX[id] = x;
		childY[id] = y;
	}

	public static void addSpriteLoaderSpriteWithOpacity(int childId, int spriteId, int opacity) {
		RSInterface rsi = RSInterface.interfaceCache[childId] = new RSInterface();
		rsi.id = childId;
		rsi.parentID = childId;
		rsi.type = 5;
		rsi.atActionType = 0;
		rsi.contentType = 0;
		rsi.opacity = (byte) opacity;
		rsi.customOpacity = opacity;
		rsi.hoverType = 52;
		rsi.sprite1 = CacheSpriteLoader.getCacheSprite2(spriteId);
		rsi.sprite2 = CacheSpriteLoader.getCacheSprite2(spriteId);
		rsi.sprite1ID = rsi.sprite2ID = spriteId;
		rsi.width = rsi.sprite1.myWidth;
		rsi.height = rsi.sprite1.myHeight;
		rsi.drawsTransparent = true;
	}

	public static void addSpriteLoaderConfigButton(int childId, int parentId, int imageId, int secondImageId,
			String tooltip, int configType, int actionType, int configId) {
		RSInterface rsi = addInterface(childId);
		rsi.parentID = parentId;
		rsi.id = childId;
		rsi.type = 5;
		rsi.atActionType = actionType;
		rsi.contentType = 0;
		rsi.customOpacity = 0;
		rsi.hoverType = -1;
		rsi.valueCompareType = new int[1];

		rsi.requiredValues = new int[1];
		rsi.valueCompareType[0] = 1;
		rsi.requiredValues[0] = configType;
		rsi.valueIndexArray = new int[1][3];
		rsi.valueIndexArray[0][0] = 5;
		rsi.valueIndexArray[0][1] = configId;
		rsi.valueIndexArray[0][2] = 0;
		rsi.sprite1 = imageId == -1 ? null : CacheSpriteLoader.getCacheSprite2(imageId);
		rsi.sprite2 = secondImageId == -1 ? null : CacheSpriteLoader.getCacheSprite2(secondImageId);
		rsi.width = rsi.sprite1 != null ? rsi.sprite1.myWidth : rsi.sprite2.myWidth;
		rsi.height = rsi.sprite1 != null ? rsi.sprite1.myHeight : rsi.sprite2.myHeight;
		rsi.tooltip = tooltip;
	}

	public static void addSpriteLoaderButtonWithTooltipBox(int childId, int spriteId, String tooltip, int hoverSpriteId,
			int tooltipBoxChildId, String tooltipBoxText, int tooltipx, int tooltipy) {
		RSInterface rsi = RSInterface.interfaceCache[childId] = new RSInterface();
		rsi.id = childId;
		rsi.parentID = childId;
		rsi.type = 5;
		rsi.atActionType = 1;
		rsi.contentType = 0;
		rsi.hoverType = tooltipBoxChildId;
		rsi.sprite2 = CacheSpriteLoader.getCacheSprite(spriteId);
		rsi.sprite1 = CacheSpriteLoader.getCacheSprite(spriteId);
		rsi.width = rsi.sprite2.myWidth;
		rsi.height = rsi.sprite1.myHeight - 2;
		rsi.tooltip = tooltip;
		// rsi.isFalseTooltip = true;
		addTooltip2(tooltipBoxChildId, tooltipBoxText, tooltipx, tooltipy);
	}

	public static void addTooltip2(int id, String text, int x, int y) {
		RSInterface rsinterface = addTabInterface(id);
		rsinterface.parentID = id;
		rsinterface.type = 0;
		rsinterface.interfaceShown = true;
		rsinterface.hoverType = -1;
		addTooltipBox2(id + 1, text);
		rsinterface.totalChildren(1);
		rsinterface.child(0, id + 1, x, y);
	}

	public static void addTooltipBox2(int id, String text) {
		RSInterface rsi = addInterface(id);
		rsi.id = id;
		rsi.parentID = id;
		rsi.type = 12;
		rsi.message = text;
	}

	public static void addSpriteLoader(int childId, int spriteId) {
		RSInterface rsi = RSInterface.interfaceCache[childId] = new RSInterface();
		rsi.id = childId;
		rsi.parentID = childId;
		rsi.type = 5;
		rsi.atActionType = 0;
		rsi.contentType = 0;
		rsi.sprite2 = CacheSpriteLoader.getCacheSprite(spriteId);
		rsi.sprite1 = CacheSpriteLoader.getCacheSprite(spriteId);

		// rsi.sprite1.spriteLoader = rsi.sprite2.spriteLoader = true;
		// rsi.hoverSprite1 = CacheSpriteLoader.getCacheSprite(hoverSpriteId];
		// rsi.hoverSprite2 = CacheSpriteLoader.getCacheSprite(hoverSpriteId];
		// rsi.hoverSprite1.spriteLoader = rsi.hoverSprite2.spriteLoader = true;
		// rsi.sprite1 = rsi.sprite2 = spriteId;
		// rsi.hoverSprite1Id = rsi.hoverSprite2Id = hoverSpriteId;
		rsi.width = rsi.sprite2.myWidth;
		rsi.height = rsi.sprite1.myHeight - 2;
		// rsi.isFalseTooltip = true;
	}

	public static void addSpriteLoader2(int childId, int spriteId) {
		RSInterface rsi = RSInterface.interfaceCache[childId] = new RSInterface();
		rsi.id = childId;
		rsi.parentID = childId;
		rsi.type = 5;
		rsi.atActionType = 0;
		rsi.contentType = 0;
		rsi.sprite2 = CacheSpriteLoader.getCacheSprite2(spriteId);
		rsi.sprite1 = CacheSpriteLoader.getCacheSprite2(spriteId);

		// rsi.sprite1.spriteLoader = rsi.sprite2.spriteLoader = true;
		// rsi.hoverSprite1 = CacheSpriteLoader.getCacheSprite(hoverSpriteId];
		// rsi.hoverSprite2 = CacheSpriteLoader.getCacheSprite(hoverSpriteId];
		// rsi.hoverSprite1.spriteLoader = rsi.hoverSprite2.spriteLoader = true;
		// rsi.sprite1 = rsi.sprite2 = spriteId;
		// rsi.hoverSprite1Id = rsi.hoverSprite2Id = hoverSpriteId;
		rsi.width = rsi.sprite2.myWidth;
		rsi.height = rsi.sprite1.myHeight - 2;
		// rsi.isFalseTooltip = true;
	}

	public static void addCloseButton(int child, int hoverChild, int hoverImageChild) {
		addHoverButtonWSpriteLoader(child, 652, 21, 21, "Close", 250, hoverChild, 3);
		addHoveredImageWSpriteLoader(hoverChild, 653, 21, 21, hoverImageChild);
	}

	public static void addHoveredImageWSpriteLoader(int i, int spriteId, int w, int h, int imgInterface) {
		RSInterface tab = addTabInterface(i);
		tab.id = i;
		tab.parentID = i;
		tab.type = 0;
		tab.atActionType = 0;
		tab.contentType = 0;
		tab.opacity = 0;
		tab.hoverType = -1;
		tab.scrollMax = 0;
		tab.interfaceShown = true;
		tab.width = w;
		tab.height = h;
		addHoverImageWSpriteLoader(imgInterface, spriteId);
		tab.totalChildren(1);
		tab.child(0, imgInterface, 0, 0);
	}

	public static void addSpriteLoaderHoverButton(int childId, int spriteId, String tooltip, int hoverSpriteId) {
		RSInterface rsi = RSInterface.interfaceCache[childId] = new RSInterface();
		rsi.id = childId;
		rsi.parentID = childId;
		rsi.type = 5;
		rsi.atActionType = spriteId != CLOSE_BUTTON ? 1 : 3;
		rsi.contentType = 0;
		rsi.opacity = 0;
		rsi.hoverType = 52;
		rsi.sprite1 = CacheSpriteLoader.getCacheSprite2(spriteId);
		rsi.sprite2 = CacheSpriteLoader.getCacheSprite2(spriteId);
		rsi.sprite1ID = rsi.sprite2ID = spriteId;
		rsi.width = rsi.sprite1.myWidth;
		rsi.height = rsi.sprite1.myHeight;
		rsi.tooltip = tooltip;
	}

	public static void addSpriteLoaderHoverButton1(int childId, int spriteId, String tooltip, int hoverSpriteId) {
		RSInterface rsi = RSInterface.interfaceCache[childId] = new RSInterface();
		rsi.id = childId;
		rsi.parentID = childId;
		rsi.type = 5;
		rsi.atActionType = spriteId != CLOSE_BUTTON ? 1 : 3;
		rsi.contentType = 0;
		rsi.opacity = 0;
		rsi.hoverType = 52;
		rsi.sprite1 = CacheSpriteLoader.getCacheSprite(spriteId);
		rsi.sprite2 = CacheSpriteLoader.getCacheSprite(spriteId);
		rsi.sprite1ID = rsi.sprite2ID = spriteId;
		rsi.width = rsi.sprite1.myWidth;
		rsi.height = rsi.sprite1.myHeight;
		rsi.tooltip = tooltip;
	}

	public static void addHoverImageWSpriteLoader(int i, int spriteId) {
		RSInterface tab = addTabInterface(i);
		tab.id = i;
		tab.parentID = i;
		tab.type = 5;
		tab.atActionType = 0;
		tab.contentType = 0;
		tab.width = 512;
		tab.height = 334;
		tab.opacity = 0;
		tab.hoverType = 52;
		tab.sprite2 = CacheSpriteLoader.getCacheSprite(spriteId);
		tab.sprite1 = CacheSpriteLoader.getCacheSprite(spriteId);
	}

	public static void addHoverSpriteLoaderButton(int i, int spriteId, int width, int height, String text,
			int contentType, int hoverOver, int aT) {// hoverable
		// button
		RSInterface tab = addTabInterface(i);
		tab.id = i;
		tab.parentID = i;
		tab.type = 5;
		tab.atActionType = aT;
		tab.contentType = contentType;
		tab.opacity = 0;
		tab.hoverType = hoverOver;
		tab.sprite2 = CacheSpriteLoader.getCacheSprite(spriteId);
		tab.sprite1 = CacheSpriteLoader.getCacheSprite(spriteId);
		tab.width = width;
		tab.height = height;
		tab.tooltip = text;
	}

	public static void addHoveredSpriteLoaderButton(int i, int w, int h, int IMAGEID, int spriteId) {
		RSInterface tab = addTabInterface(i);
		tab.parentID = i;
		tab.id = i;
		tab.type = 0;
		tab.atActionType = 0;
		tab.width = w;
		tab.height = h;
		tab.interfaceShown = true;
		tab.opacity = 0;
		tab.hoverType = -1;
		tab.scrollMax = 0;
		tab.sprite2 = CacheSpriteLoader.getCacheSprite(spriteId);
		tab.sprite1 = CacheSpriteLoader.getCacheSprite(spriteId);
		tab.totalChildren(1);
		tab.child(0, IMAGEID, 0, 0);
	}

	public static void Sidebar0a(int id, int id2, int id3, String text1, String text2, String text3, String text4,
			int str1x, int str1y, int str2x, int str2y, int str3x, int str3y, int str4x, int str4y, int img1x,
			int img1y, int img2x, int img2y, int img3x, int img3y, int img4x, int img4y, TextDrawingArea[] tda) // 4button
	// spec
	{
		RSInterface rsi = addInterface(id); // 2423
		/* addText(ID, "Text", tda, Size, Colour, Centered); */
		addText(id2, "", tda, 3, 0xff981f, true); // 2426
		addText(id2 + 11, text1, tda, 0, 0xff981f, false);
		addText(id2 + 12, text2, tda, 0, 0xff981f, false);
		addText(id2 + 13, text3, tda, 0, 0xff981f, false);
		addText(id2 + 14, text4, tda, 0, 0xff981f, false);
		/* specialBar(ID); */
		rsi.specialBar(id3); // 7599

		rsi.width = 190;
		rsi.height = 261;

		int last = 15;
		int frame = 0;
		rsi.totalChildren(last, last, last);

		rsi.child(frame, id2 + 3, 21, 46);
		frame++; // 2429
		rsi.child(frame, id2 + 4, 104, 99);
		frame++; // 2430
		rsi.child(frame, id2 + 5, 21, 99);
		frame++; // 2431
		rsi.child(frame, id2 + 6, 105, 46);
		frame++; // 2432

		rsi.child(frame, id2 + 7, img1x, img1y);
		frame++; // bottomright 2433
		rsi.child(frame, id2 + 8, img2x, img2y);
		frame++; // topleft 2434
		rsi.child(frame, id2 + 9, img3x, img3y);
		frame++; // bottomleft 2435
		rsi.child(frame, id2 + 10, img4x, img4y);
		frame++; // topright 2436

		rsi.child(frame, id2 + 11, str1x, str1y);
		frame++; // chop 2437
		rsi.child(frame, id2 + 12, str2x, str2y);
		frame++; // slash 2438
		rsi.child(frame, id2 + 13, str3x, str3y);
		frame++; // lunge 2439
		rsi.child(frame, id2 + 14, str4x, str4y);
		frame++; // block 2440

		rsi.child(frame, 19300, 0, 0);
		frame++; // stuffs
		rsi.child(frame, id2, 94, 4);
		frame++; // weapon 2426
		rsi.child(frame, id3, 21, 205);
		frame++; // special attack 7599

		for (int i = id2 + 3; i < id2 + 7; i++) { // 2429 - 2433
			rsi = interfaceCache[i];
			rsi.sprite1 = CacheSpriteLoader.getCacheSprite(82);
			rsi.sprite2 = CacheSpriteLoader.getCacheSprite(83);
			rsi.width = 68;
			rsi.height = 44;
		}
	}

	public static void Sidebar0b(int id, int id2, String text1, String text2, String text3, String text4, int str1x,
			int str1y, int str2x, int str2y, int str3x, int str3y, int str4x, int str4y, int img1x, int img1y,
			int img2x, int img2y, int img3x, int img3y, int img4x, int img4y, TextDrawingArea[] tda) // 4button
	// nospec
	{
		RSInterface rsi = addInterface(id); // 2423
		/* addText(ID, "Text", tda, Size, Colour, Centered); */
		addText(id2, "", tda, 3, 0xff981f, true); // 2426
		addText(id2 + 11, text1, tda, 0, 0xff981f, false);
		addText(id2 + 12, text2, tda, 0, 0xff981f, false);
		addText(id2 + 13, text3, tda, 0, 0xff981f, false);
		addText(id2 + 14, text4, tda, 0, 0xff981f, false);

		rsi.width = 190;
		rsi.height = 261;

		int last = 14;
		int frame = 0;
		rsi.totalChildren(last, last, last);

		rsi.child(frame, id2 + 3, 21, 46);
		frame++; // 2429
		rsi.child(frame, id2 + 4, 104, 99);
		frame++; // 2430
		rsi.child(frame, id2 + 5, 21, 99);
		frame++; // 2431
		rsi.child(frame, id2 + 6, 105, 46);
		frame++; // 2432

		rsi.child(frame, id2 + 7, img1x, img1y);
		frame++; // bottomright 2433
		rsi.child(frame, id2 + 8, img2x, img2y);
		frame++; // topleft 2434
		rsi.child(frame, id2 + 9, img3x, img3y);
		frame++; // bottomleft 2435
		rsi.child(frame, id2 + 10, img4x, img4y);
		frame++; // topright 2436

		rsi.child(frame, id2 + 11, str1x, str1y);
		frame++; // chop 2437
		rsi.child(frame, id2 + 12, str2x, str2y);
		frame++; // slash 2438
		rsi.child(frame, id2 + 13, str3x, str3y);
		frame++; // lunge 2439
		rsi.child(frame, id2 + 14, str4x, str4y);
		frame++; // block 2440

		rsi.child(frame, 19300, 0, 0);
		frame++; // stuffs
		rsi.child(frame, id2, 94, 4);
		frame++; // weapon 2426

		for (int i = id2 + 3; i < id2 + 7; i++) { // 2429 - 2433
			rsi = interfaceCache[i];
			rsi.sprite1 = CacheSpriteLoader.getCacheSprite(82);
			rsi.sprite2 = CacheSpriteLoader.getCacheSprite(83);
			rsi.width = 68;
			rsi.height = 44;
		}
	}

	public static void Sidebar0c(int id, int id2, int id3, String text1, String text2, String text3, int str1x,
			int str1y, int str2x, int str2y, int str3x, int str3y, int img1x, int img1y, int img2x, int img2y,
			int img3x, int img3y, TextDrawingArea[] tda) // 3button
	// spec
	{
		RSInterface rsi = addInterface(id); // 2423
		/* addText(ID, "Text", tda, Size, Colour, Centered); */
		addText(id2, "", tda, 3, 0xff981f, true); // 2426
		addText(id2 + 9, text1, tda, 0, 0xff981f, false);
		addText(id2 + 10, text2, tda, 0, 0xff981f, false);
		addText(id2 + 11, text3, tda, 0, 0xff981f, false);
		/* specialBar(ID); */
		rsi.specialBar(id3); // 7599

		rsi.width = 190;
		rsi.height = 261;

		int last = 12;
		int frame = 0;
		rsi.totalChildren(last, last, last);

		rsi.child(frame, id2 + 3, 21, 99);
		frame++;
		rsi.child(frame, id2 + 4, 105, 46);
		frame++;
		rsi.child(frame, id2 + 5, 21, 46);
		frame++;

		rsi.child(frame, id2 + 6, img1x, img1y);
		frame++; // topleft
		rsi.child(frame, id2 + 7, img2x, img2y);
		frame++; // bottomleft
		rsi.child(frame, id2 + 8, img3x, img3y);
		frame++; // topright

		rsi.child(frame, id2 + 9, str1x, str1y);
		frame++; // chop
		rsi.child(frame, id2 + 10, str2x, str2y);
		frame++; // slash
		rsi.child(frame, id2 + 11, str3x, str3y);
		frame++; // lunge

		rsi.child(frame, 19300, 0, 0);
		frame++; // stuffs
		rsi.child(frame, id2, 94, 4);
		frame++; // weapon
		rsi.child(frame, id3, 21, 205);
		frame++; // special attack 7599

		for (int i = id2 + 3; i < id2 + 6; i++) {
			rsi = interfaceCache[i];
			rsi.sprite1 = CacheSpriteLoader.getCacheSprite(82);
			rsi.sprite2 = CacheSpriteLoader.getCacheSprite(83);
			rsi.width = 68;
			rsi.height = 44;
		}
	}

	public static void Sidebar0d(int id, int id2, String text1, String text2, String text3, int str1x, int str1y,
			int str2x, int str2y, int str3x, int str3y, int img1x, int img1y, int img2x, int img2y, int img3x,
			int img3y, TextDrawingArea[] tda) {
		RSInterface rsi = addInterface(id); // 2423
		/* addText(ID, "Text", tda, Size, Colour, Centered); */
		addText(id2, "", tda, 3, 0xff981f, true); // 2426
		addText(id2 + 9, text1, tda, 0, 0xff981f, false);
		addText(id2 + 10, text2, tda, 0, 0xff981f, false);
		addText(id2 + 11, text3, tda, 0, 0xff981f, false);

		// addText(353, "Spell", tda, 0, 0xff981f, false);
		removeSomething(353);
		addText(354, "Spell", tda, 0, 0xff981f, false);

		addCacheSprite(337, 19, 0, "combaticons");
		addCacheSprite(338, 13, 0, "combaticons2");
		addCacheSprite(339, 14, 0, "combaticons2");

		/* addToggleButton(id, sprite, config, width, height, tooltip); */
		// addToggleButton(349, 349, 108, 68, 44, "Select");
		removeSomething(349);
		addToggleButton(350, 68, 108, 68, 44, "Select");

		rsi.width = 190;
		rsi.height = 261;

		int last = 15;
		int frame = 0;
		rsi.totalChildren(last, last, last);

		rsi.child(frame, id2 + 3, 20, 115);
		frame++;
		rsi.child(frame, id2 + 4, 20, 80);
		frame++;
		rsi.child(frame, id2 + 5, 20, 45);
		frame++;

		rsi.child(frame, id2 + 6, img1x, img1y);
		frame++; // topleft
		rsi.child(frame, id2 + 7, img2x, img2y);
		frame++; // bottomleft
		rsi.child(frame, id2 + 8, img3x, img3y);
		frame++; // topright

		rsi.child(frame, id2 + 9, str1x, str1y);
		frame++; // bash
		rsi.child(frame, id2 + 10, str2x, str2y);
		frame++; // pound
		rsi.child(frame, id2 + 11, str3x, str3y);
		frame++; // focus

		rsi.child(frame, 349, 105, 46);
		frame++; // spell1
		rsi.child(frame, 350, 104, 106);
		frame++; // spell2

		rsi.child(frame, 353, 125, 74);
		frame++; // spell
		rsi.child(frame, 354, 125, 134);
		frame++; // spell

		rsi.child(frame, 19300, 0, 0);
		frame++; // stuffs
		rsi.child(frame, id2, 94, 4);
		frame++; // weapon
	}

	private static void sprite1(int id, int sprite) {
		RSInterface class9 = interfaceCache[id];
		class9.sprite1 = CacheSpriteLoader.getCacheSprite(sprite);
	}

	public static void textSize(int id, TextDrawingArea tda[], int idx) {
		RSInterface rsi = interfaceCache[id];
		rsi.textDrawingAreas = tda[idx];
	}

	static void unpack(Archive streamLoader, TextDrawingArea textDrawingAreas[], Archive streamLoader_1) {
		aMRUNodes_238 = new List(50000);
		fonts = textDrawingAreas;
		ByteBuffer stream = new ByteBuffer(streamLoader.get("data"));
		int i = -1;
		stream.getUnsignedShort();
		// int j = stream.getUnsignedShort();
		interfaceCache = new RSInterface[85000];
		while (stream.position < stream.buffer.length) {
			int k = stream.getUnsignedShort();
			if (k == 65535) {
				i = stream.getUnsignedShort();
				k = stream.getUnsignedShort();
			}
			RSInterface rsInterface = interfaceCache[k] = new RSInterface();
			rsInterface.id = k;
			rsInterface.parentID = i;
			rsInterface.type = stream.getUnsignedByte();
			rsInterface.atActionType = stream.getUnsignedByte();
			rsInterface.contentType = stream.getUnsignedShort();
			rsInterface.width = stream.getUnsignedShort();
			rsInterface.height = stream.getUnsignedShort();
			rsInterface.opacity = (byte) stream.getUnsignedByte();
			rsInterface.hoverType = stream.getUnsignedByte();
			if (rsInterface.hoverType != 0) {
				rsInterface.hoverType = (rsInterface.hoverType - 1 << 8) + stream.getUnsignedByte();
			} else {
				rsInterface.hoverType = -1;
			}

			int i1 = stream.getUnsignedByte();

			if (i1 > 0) {
				rsInterface.valueCompareType = new int[i1];
				rsInterface.requiredValues = new int[i1];
				for (int j1 = 0; j1 < i1; j1++) {
					rsInterface.valueCompareType[j1] = stream.getUnsignedByte();
					rsInterface.requiredValues[j1] = stream.getUnsignedShort();
				}
			}

			int k1 = stream.getUnsignedByte();

			if (k1 > 0) {
				rsInterface.valueIndexArray = new int[k1][];
				for (int l1 = 0; l1 < k1; l1++) {
					int i3 = stream.getUnsignedShort();
					rsInterface.valueIndexArray[l1] = new int[i3];
					for (int l4 = 0; l4 < i3; l4++) {
						rsInterface.valueIndexArray[l1][l4] = stream.getUnsignedShort();
					}
				}
			}

			if (rsInterface.type == 0) {
				rsInterface.drawsTransparent = false;
				rsInterface.scrollMax = stream.getUnsignedShort();
				rsInterface.interfaceShown = stream.getUnsignedByte() == 1;
				int i2 = stream.getUnsignedShort();
				rsInterface.children = new int[i2];
				rsInterface.childX = new int[i2];
				rsInterface.childY = new int[i2];

				for (int j3 = 0; j3 < i2; j3++) {
					rsInterface.children[j3] = stream.getUnsignedShort();
					rsInterface.childX[j3] = stream.getSignedShort();
					rsInterface.childY[j3] = stream.getSignedShort();
				}
			}

			if (rsInterface.type == 1) {
				stream.getUnsignedShort();
				stream.getUnsignedByte();
			}

			if (rsInterface.type == 2) {
				rsInterface.inv = new int[rsInterface.width * rsInterface.height];
				rsInterface.invStackSizes = new int[rsInterface.width * rsInterface.height];
				rsInterface.allowSwapItems = stream.getUnsignedByte() == 1;
				rsInterface.isInventoryInterface = stream.getUnsignedByte() == 1;
				rsInterface.usableItemInterface = stream.getUnsignedByte() == 1;
				rsInterface.deletesTargetSlot = stream.getUnsignedByte() == 1;
				rsInterface.invSpritePadX = stream.getUnsignedByte();
				rsInterface.invSpritePadY = stream.getUnsignedByte();
				rsInterface.spritesX = new int[20];
				rsInterface.spritesY = new int[20];
				rsInterface.sprites = new Sprite[20];

				for (int j2 = 0; j2 < 20; j2++) {
					int k3 = stream.getUnsignedByte();

					if (k3 == 1) {
						rsInterface.spritesX[j2] = stream.getSignedShort();
						rsInterface.spritesY[j2] = stream.getSignedShort();
						String s1 = stream.getString();

						if (streamLoader_1 != null && s1.length() > 0) {
							int i5 = s1.lastIndexOf(",");
							rsInterface.sprites[j2] = method207(Integer.parseInt(s1.substring(i5 + 1)), streamLoader_1,
									s1.substring(0, i5));
						}
					}
				}

				rsInterface.actions = new String[5];

				for (int l3 = 0; l3 < 5; l3++) {
					rsInterface.actions[l3] = stream.getString();
					if (rsInterface.actions[l3].length() == 0) {
						rsInterface.actions[l3] = null;
					}
					if (rsInterface.parentID == 3824) {
						rsInterface.actions[4] = "Buy X";
					}
					if (rsInterface.parentID == 3822) {
						rsInterface.actions[4] = "Sell X";
						rsInterface.hideExamine = true;
					}
					if (rsInterface.parentID == 5292) {
						rsInterface.actions = new String[] { "Withdraw-1", "Withdraw-5", "Withdraw-10", "Withdraw-All",
								"Withdraw-All but one", "Withdraw-X" };
						rsInterface.hideExamine = true;
					}
				}
				if (rsInterface.parentID == 1644) {
					rsInterface.actions[2] = "Operate";
				}
			}

			if (rsInterface.type == 3) {
				rsInterface.filled = stream.getUnsignedByte() == 1;
			}

			if (rsInterface.type == 4 || rsInterface.type == 1) {
				rsInterface.centerText = stream.getUnsignedByte() == 1;
				int k2 = stream.getUnsignedByte();
				if (textDrawingAreas != null) {
					rsInterface.textDrawingAreas = textDrawingAreas[k2];
				}
				rsInterface.textShadow = stream.getUnsignedByte() == 1;
			}

			if (rsInterface.type == 4) {
				rsInterface.message = stream.getString().replaceAll("RuneScape", "RuneLive");
				rsInterface.aString228 = stream.getString();
			}

			if (rsInterface.type == 1 || rsInterface.type == 3 || rsInterface.type == 4) {
				rsInterface.textColor = stream.getIntLittleEndian();
			}

			if (rsInterface.type == 3 || rsInterface.type == 4) {
				rsInterface.anInt219 = stream.getIntLittleEndian();
				rsInterface.anInt216 = stream.getIntLittleEndian();
				rsInterface.anInt239 = stream.getIntLittleEndian();
			}

			if (rsInterface.type == 5) {
				rsInterface.drawsTransparent = false;
				String s = stream.getString();

				if (streamLoader_1 != null && s.length() > 0) {
					int i4 = s.lastIndexOf(",");
					rsInterface.sprite1ID = Integer.parseInt(s.substring(i4 + 1));
					rsInterface.sprite1 = method207(Integer.parseInt(s.substring(i4 + 1)), streamLoader_1,
							s.substring(0, i4));
				}
				s = stream.getString();

				if (streamLoader_1 != null && s.length() > 0) {
					int j4 = s.lastIndexOf(",");
					rsInterface.sprite2ID = Integer.parseInt(s.substring(j4 + 1));
					rsInterface.sprite2 = method207(Integer.parseInt(s.substring(j4 + 1)), streamLoader_1,
							s.substring(0, j4));
				}

			}

			if (rsInterface.type == 6) {
				int l = stream.getUnsignedByte();

				if (l != 0) {
					rsInterface.mediaType = 1;
					rsInterface.mediaID = (l - 1 << 8) + stream.getUnsignedByte();
				}

				l = stream.getUnsignedByte();

				if (l != 0) {
					rsInterface.anInt255 = 1;
					rsInterface.anInt256 = (l - 1 << 8) + stream.getUnsignedByte();
				}

				l = stream.getUnsignedByte();

				if (l != 0) {
					rsInterface.disabledAnimationId = (l - 1 << 8) + stream.getUnsignedByte();
				} else {
					rsInterface.disabledAnimationId = -1;
				}

				l = stream.getUnsignedByte();

				if (l != 0) {
					rsInterface.enabledAnimationId = (l - 1 << 8) + stream.getUnsignedByte();
				} else {
					rsInterface.enabledAnimationId = -1;
				}

				rsInterface.modelZoom = stream.getUnsignedShort();
				rsInterface.modelRotation1 = stream.getUnsignedShort();
				rsInterface.modelRotation2 = stream.getUnsignedShort();
			}

			if (rsInterface.type == 7) {
				rsInterface.inv = new int[rsInterface.width * rsInterface.height];
				rsInterface.invStackSizes = new int[rsInterface.width * rsInterface.height];
				rsInterface.centerText = stream.getUnsignedByte() == 1;
				int l2 = stream.getUnsignedByte();
				if (textDrawingAreas != null) {
					rsInterface.textDrawingAreas = textDrawingAreas[l2];
				}
				rsInterface.textShadow = stream.getUnsignedByte() == 1;
				rsInterface.textColor = stream.getIntLittleEndian();
				rsInterface.invSpritePadX = stream.getSignedShort();
				rsInterface.invSpritePadY = stream.getSignedShort();
				rsInterface.isInventoryInterface = stream.getUnsignedByte() == 1;
				rsInterface.actions = new String[5];

				for (int k4 = 0; k4 < 5; k4++) {
					rsInterface.actions[k4] = stream.getString();
					if (rsInterface.actions[k4].length() == 0) {
						rsInterface.actions[k4] = null;
					}
				}
			}

			if (rsInterface.atActionType == 2 || rsInterface.type == 2) {
				rsInterface.selectedActionName = stream.getString();
				rsInterface.spellName = stream.getString();
				rsInterface.spellUsableOn = stream.getUnsignedShort();
			}

			if (rsInterface.type == 8) {
				rsInterface.message = stream.getString();
			}

			if (rsInterface.atActionType == 1 || rsInterface.atActionType == 4 || rsInterface.atActionType == 5
					|| rsInterface.atActionType == 6) {
				rsInterface.tooltip = stream.getString();

				if (rsInterface.tooltip.length() == 0) {
					if (rsInterface.atActionType == 1) {
						rsInterface.tooltip = "Ok";
					}
					if (rsInterface.atActionType == 4) {
						rsInterface.tooltip = "Select";
					}
					if (rsInterface.atActionType == 5) {
						rsInterface.tooltip = "Select";
					}
					if (rsInterface.atActionType == 6) {
						rsInterface.tooltip = "Continue";
					}
				}
			}
		}

		aClass44 = streamLoader;
		try {
			customInterfaces = new CustomInterfaces(textDrawingAreas);
			customInterfaces.loadCustoms();
			interfaceCache[3983].textDrawingAreas = fonts[1];
		} catch (Exception e) {
			e.printStackTrace();
		}
		aMRUNodes_238 = null;
	}

	public static final int BH_OFFSET = 17;

	public static void addSummoningText(int i, String s, int k, boolean l, boolean m, int a, TextDrawingArea[] TDA,
			int j) {
		RSInterface RSInterface = addTabInterface(i);
		RSInterface.parentID = i;
		RSInterface.id = i;
		RSInterface.type = 4;
		RSInterface.atActionType = 0;
		RSInterface.width = 0;
		RSInterface.height = 0;
		RSInterface.contentType = 0;
		RSInterface.hoverType = a;
		RSInterface.centerText = l;
		// RSInterface.dis = m;
		RSInterface.textDrawingAreas = TDA[j];
		RSInterface.message = s;
		RSInterface.message = s;
		RSInterface.textColor = k;
		RSInterface.interfaceShown = true;
		RSInterface.hoverType = -1;
	}

	public static void addInAreaHoverSpriteLoader(int i, int sprite, int w, int h, String text, int contentType,
			int actionType) {
		RSInterface tab = addTabInterface(i);
		tab.id = i;
		tab.parentID = i;
		tab.type = 5;
		tab.atActionType = actionType;
		tab.contentType = contentType;
		tab.hoverType = i;
		tab.sprite1 = CacheSpriteLoader.getCacheSprite(sprite);
		tab.width = w;
		tab.height = h;
		tab.tooltip = text;
	}

	public static void addPouch(int ID, int r1[], int ra1, int r2, int lvl, String name, TextDrawingArea[] TDA,
			int imageID, int type) {
		RSInterface rsInterface = addTabInterface(ID);
		rsInterface.id = ID;
		rsInterface.parentID = 1151;
		rsInterface.type = 5;
		rsInterface.atActionType = type;
		rsInterface.contentType = 0;
		rsInterface.hoverType = ID + 1;
		rsInterface.width = 32;
		rsInterface.height = 32;
		rsInterface.tooltip = (new StringBuilder()).append("Infuse @or1@").append(name).toString();
		rsInterface.spellName = name;
		rsInterface.valueCompareType = new int[2];
		rsInterface.requiredValues = new int[2];
		rsInterface.valueCompareType[0] = 3;
		rsInterface.requiredValues[0] = ra1;
		rsInterface.valueCompareType[1] = 3;
		rsInterface.requiredValues[1] = lvl - 1;
		rsInterface.summonReq = lvl - 1;
		rsInterface.valueIndexArray = new int[2 + r1.length][];
		for (int i = 0; i < r1.length; i++) {
			rsInterface.valueIndexArray[i] = new int[4];
			rsInterface.valueIndexArray[i][0] = 4;
			rsInterface.valueIndexArray[i][1] = 3214;
			rsInterface.valueIndexArray[i][2] = r1[i];
			rsInterface.valueIndexArray[i][3] = 0;
		}
		rsInterface.valueIndexArray[1] = new int[3];
		rsInterface.valueIndexArray[1][0] = 1;
		rsInterface.valueIndexArray[1][1] = 6;
		rsInterface.valueIndexArray[1][2] = 0;
		rsInterface.itemSpriteZoom = 150;
		rsInterface.itemSpriteId = r2;
		rsInterface.itemSpriteIndex = imageID;
		// rsInterface.greyScale = true;
		RSInterface hover = addTabInterface(ID + 1);
		hover.hoverType = -1;
		hover.interfaceShown = true;
		if (imageID < SummoningInterfaceData.summoningItemRequirements.length) {
			addSprite(ID + 6, SummoningInterfaceData.summoningItemRequirements[imageID][0], null, 150, 150);
			addSprite(ID - 1200, SummoningInterfaceData.summoningItemRequirements[imageID][1], null, 150, 150);
			addSprite(ID - 1201, SummoningInterfaceData.summoningItemRequirements[imageID][2], null, 150, 150);
			addRuneText(ID - 1202, SummoningInterfaceData.summoningItemAmountRequirements[imageID][0],
					SummoningInterfaceData.summoningItemRequirements[imageID][0], TDA);
			addRuneText(ID - 1203, SummoningInterfaceData.summoningItemAmountRequirements[imageID][1],
					SummoningInterfaceData.summoningItemRequirements[imageID][1], TDA);
			if (SummoningInterfaceData.summoningItemAmountRequirements[imageID][2] > 0)
				addRuneText(ID - 1204, SummoningInterfaceData.summoningItemAmountRequirements[imageID][2],
						SummoningInterfaceData.summoningItemRequirements[imageID][2], TDA);
			setChildren(SummoningInterfaceData.summoningItemAmountRequirements[imageID][2] > 0 ? 9 : 8, hover);
			setBounds(ID + 6, 14, 33, 3, hover);
			setBounds(ID - 1200, 70, 33, 4, hover);
			setBounds(ID - 1201, 120, 33, 5, hover);
			setBounds(ID - 1202, 30, 65, 6, hover);
			setBounds(ID - 1203, 85, 65, 7, hover);
			if (SummoningInterfaceData.summoningItemAmountRequirements[imageID][2] > 0)
				setBounds(ID - 1204, 133, 65, 8, hover);
		} else
			setChildren(3, hover);
		addSpriteLoader(ID + 2, 1007);
		addText(ID + 3, (new StringBuilder()).append("Level ").append(lvl).append(": ").append(name).toString(),
				0xff981f, true, true, 52, 1);
		addText(ID + 4, "This item requires:", 0xaf6a1a, true, true, 52, 0);
		setBounds(ID + 2, 0, 0, 0, hover);
		setBounds(ID + 3, 90, 4, 1, hover);
		setBounds(ID + 4, 90, 19, 2, hover);
	}

	public static void addSprite(int id, int spriteId, String spriteName, int zoom1, int zoom2) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;
		tab.parentID = id;
		tab.type = 5;
		tab.atActionType = 0;
		tab.contentType = 0;
		tab.opacity = (byte) 0;
		tab.hoverType = 52;
		if (spriteName == null) {
			tab.itemSpriteZoom = zoom1;
			tab.itemSpriteId = spriteId;
		}
		tab.width = 512;
		tab.height = 334;
	}

	public static void addSprite(int a, int id, int spriteId, String spriteName, boolean l) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;
		tab.parentID = id;
		tab.type = 5;
		tab.atActionType = 0;
		tab.contentType = 0;
		tab.opacity = (byte) 0;
		tab.hoverType = 52;
		if (spriteId > 0) {
			tab.sprite1 = CacheSpriteLoader.getCacheSprite(spriteId);
			tab.sprite2 = CacheSpriteLoader.getCacheSprite(spriteId);
		}
		tab.width = 512;
		tab.height = 334;
	}

	public static void addText(int id, String text, TextDrawingArea tda[], int idx, int color, boolean center,
			boolean shadow, boolean cc) {
		RSInterface tab = addTabInterface(id);
		tab.parentID = id;
		tab.id = id;
		tab.type = 4;
		tab.atActionType = 0;
		tab.width = 0;
		tab.height = 11;
		tab.contentType = 0;
		tab.opacity = 0;
		tab.hoverType = -1;
		tab.centerText = center;
		tab.textShadow = shadow;
		tab.textDrawingAreas = tda[idx];
		tab.message = text;
		// tab.enabledMessage = "";
		tab.textColor = color;
		// tab.enabledColor = 0;
		// tab.disabledMouseOverColor = 0;
		// tab.enabledMouseOverColor = 0;
	}

	public static void addText(int i, String s, int k, boolean l, boolean m, int a, int j) {
		try {
			RSInterface rsinterface = addTabInterface(i);
			rsinterface.parentID = i;
			rsinterface.id = i;
			rsinterface.type = 4;
			rsinterface.atActionType = 0;
			rsinterface.width = 0;
			rsinterface.height = 0;
			rsinterface.contentType = 0;
			rsinterface.hoverType = a;
			rsinterface.centerText = l;
			rsinterface.textShadow = m;
			rsinterface.textDrawingAreas = RSInterface.fonts[j];
			rsinterface.message = s;
			rsinterface.textColor = k;
		} catch (Exception e) {
		}
	}

	public static void addHoverButton(int i, String imageName, int j, int width, int height, String text,
			int contentType, int hoverOver, int aT) {// hoverable
		// button
		RSInterface tab = addTabInterface(i);
		tab.id = i;
		tab.parentID = i;
		tab.type = 5;
		tab.atActionType = aT;
		tab.contentType = contentType;
		tab.opacity = 0;
		tab.hoverType = hoverOver;
		// tab.disabledSprite = imageLoader(j, imageName);
		// tab.enabledSprite = imageLoader(j, imageName);
		tab.width = width;
		tab.height = height;
		tab.tooltip = text;
	}

	public static void addHDSprite(int id, int spriteId, int sprite2) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;
		tab.parentID = id;
		tab.type = 5;
		tab.advancedSprite = true;
		tab.atActionType = 0;
		tab.contentType = 0;
		tab.opacity = (byte) 0;
		tab.hoverType = 52;
		tab.sprite1 = CacheSpriteLoader.getCacheSprite(spriteId);
		tab.sprite2 = CacheSpriteLoader.getCacheSprite(sprite2);
		tab.width = 512;
		tab.height = 1024;
	}

	public static void addHDSprite2(int id, int spriteId, int sprite2) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;
		tab.parentID = id;
		tab.type = 5;
		tab.advancedSprite = true;
		tab.atActionType = 0;
		tab.contentType = 0;
		tab.opacity = (byte) 0;
		tab.hoverType = 52;
		tab.sprite1 = CacheSpriteLoader.getCacheSprite2(spriteId);
		tab.sprite2 = CacheSpriteLoader.getCacheSprite2(sprite2);
		tab.width = 512;
		tab.height = 1024;
	}

	public static void addHDSprite2(int id, int spriteId) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;
		tab.parentID = id;
		tab.type = 5;
		tab.advancedSprite = true;
		tab.atActionType = 0;
		tab.contentType = 0;
		tab.opacity = (byte) 0;
		tab.hoverType = 52;
		tab.sprite1 = CacheSpriteLoader.getCacheSprite2(spriteId);
		tab.sprite2 = CacheSpriteLoader.getCacheSprite2(spriteId);
		tab.width = 512;
		tab.height = 1024;
	}

	/**
	 * Insert a new child into an already existing interface
	 *
	 * @param inter
	 * @param child
	 */
	public static void insertNewChild(RSInterface inter, int id, int x, int y) {
		int[] newChildren = new int[inter.children.length + 1];
		int[] newChildX = new int[inter.childX.length + 1];
		int[] newChildY = new int[inter.childY.length + 1];
		for (int i = 0; i < inter.children.length; i++) {
			newChildren[i] = inter.children[i];
		}
		for (int i = 0; i < inter.childX.length; i++) {
			newChildX[i] = inter.childX[i];
		}
		for (int i = 0; i < inter.childY.length; i++) {
			newChildY[i] = inter.childY[i];
		}

		inter.children = newChildren;
		inter.childX = newChildX;
		inter.childY = newChildY;
		inter.children[inter.children.length - 1] = id;
		inter.childX[inter.childX.length - 1] = x;
		inter.childY[inter.childY.length - 1] = y;
	}

	boolean filled;
	String[] actions;
	boolean allowSwapItems;
	int anInt208;
	int anInt216;
	public int anInt219;
	int mediaType;
	int anInt239;
	int anInt246;
	private int anInt255;
	private int anInt256;
	int disabledAnimationId;
	int enabledAnimationId;
	public String aString228;
	public int atActionType;
	public boolean centerText;
	public int[] children;
	public int[] childX;
	public int[] childY;
	public int contentType;
	boolean deletesTargetSlot;
	boolean drawSecondary;
	public int height;
	public int hoverType;
	public int id;
	public boolean interfaceShown;
	public int[] inv;
	public int invSpritePadX;
	public int invSpritePadY;
	public int[] invStackSizes;
	public boolean isInventoryInterface;
	public boolean drawInfinity;
	int mediaID;
	public String message;
	int modelRotation1;
	int modelRotation2;
	int modelZoom;
	public int enabledOpacity;
	public byte opacity;
	public int parentID;
	public int[] requiredValues;
	public int scrollMax;
	public int scrollPosition;
	public String secondaryText;
	public String selectedActionName;
	public String spellName;
	public int spellUsableOn;
	public Sprite sprite1;
	public Sprite sprite2;
	Sprite[] sprites;
	int[] spritesX;
	int[] spritesY;
	public int textColor;
	TextDrawingArea textDrawingAreas;
	boolean textShadow;
	public String tooltip;
	String tooltipBoxText;
	public int type;
	boolean usableItemInterface;
	public int[] valueCompareType;
	public int[][] valueIndexArray;
	public int width;
	public int xOffset;
	public int yOffset;
	boolean hideStackSize, hideExamine;
	public boolean advancedSprite;
	public int summonReq;
	public boolean greyScale;
	public int itemSpriteId;
	public int itemSpriteZoom;
	public int itemSpriteIndex;

	public RSInterface() {
	}

	public void child(int id, int interID, int x, int y) {
		children[id] = interID;
		childX[id] = x;
		childY[id] = y;
	}

	public static void addRectangleClickable(int id, int opacity, int color, boolean filled, int width, int height) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		/* 6525:6377 */ tab.textColor = color;
		/* 6526:6378 */ tab.filled = filled;
		/* 6527:6379 */ tab.id = id;
		/* 6528:6380 */ tab.parentID = id;
		/* 6529:6381 */ tab.type = 3;
		/* 6530:6382 */ tab.atActionType = 5;
		/* 6531:6383 */ tab.contentType = 0;
		/* 6532:6384 */ tab.opacity = ((byte) opacity);
		/* 6533:6385 */ tab.width = width;
		/* 6534:6386 */ tab.height = height;
		/* 6535:6387 */ tab.tooltip = "Select";
	}

	private Model getMediaModel(int i, int j) {
		Model model = (Model) aMRUNodes_264.insertFromCache((i << 16) + j);
		if (model != null) {
			return model;
		}
		if (i == 1) {
			model = Model.fetchModel(j);
		}
		if (i == 2) {
			model = MobDefinition.get(j).method160();
		}
		if (i == 3) {
			model = Client.myPlayer.getPlayerModel();
		}
		if (i == 4) {
			model = ItemDefinition.get(j).method202(50);
		}
		if (i == 5) {
			model = null;
		}
		if (model != null) {
			aMRUNodes_264.removeFromCache(model, (i << 16) + j);
		}
		if (i == 10 || i == 11) {
			Player p = new Player();
			p.visible = true;
			p.equipment[0] = i == 10 ? (j + 256) : (j + 512);
			if (p.myGender == 0)
				p.equipment[1] = plrJaw + 256;
			p.myGender = gender;
			model = p.getPlayerModel();
		}
		return model;
	}

	Model method209(int j, int k, boolean flag) {
		Model model;
		if (flag) {
			model = getMediaModel(anInt255, anInt256);
		} else {
			model = getMediaModel(mediaType, mediaID);
		}
		if (model == null) {
			return null;
		}
		if (k == -1 && j == -1 && model.anIntArray1640 == null) {
			return model;
		}
		Model model_1 = new Model(true, FrameReader.isNullFrame(k) & FrameReader.isNullFrame(j), false, model);
		if (k != -1 || j != -1) {
			model_1.method469();
		}
		if (k != -1) {
			model_1.applyTransform(k);
		}
		if (j != -1) {
			model_1.applyTransform(j);
		}
		model_1.method479(84, 1000, -90, -580, -90, true);
		return model_1;
	}

	private void specialBar(int id) // 7599
	{
		addActionButton(id - 12, 70, -1, 150, 26, "Use @gre@Special Attack");

		for (int i = id - 11; i < id; i++) {
			removeSomething(i);
		}

		RSInterface rsi = interfaceCache[id - 12];
		rsi.width = 150;
		rsi.height = 26;
		rsi = interfaceCache[id];
		rsi.width = 150;
		rsi.height = 26;
		rsi.child(0, id - 12, 0, 0);
		rsi.child(12, id + 1, 3, 7);
		rsi.child(23, id + 12, 16, 8);

		for (int i = 13; i < 23; i++) {
			rsi.childY[i] -= 1;
		}

		rsi = interfaceCache[id + 1];
		rsi.type = 5;
		rsi.sprite1 = CacheSpriteLoader.getCacheSprite(71);

		for (int i = id + 2; i < id + 12; i++) {
			rsi = interfaceCache[i];
			rsi.type = 5;
		}

		sprite1(id + 2, 72);
		sprite1(id + 3, 73);
		sprite1(id + 4, 74);
		sprite1(id + 5, 75);
		sprite1(id + 6, 76);
		sprite1(id + 7, 77);
		sprite1(id + 8, 78);
		sprite1(id + 9, 79);
		sprite1(id + 10, 80);
		sprite1(id + 11, 81);
	}

	void swapInventoryItems(int i, int j) {
		int k = inv[i];
		inv[i] = inv[j];
		inv[j] = k;
		k = invStackSizes[i];
		invStackSizes[i] = invStackSizes[j];
		invStackSizes[j] = k;
	}

	public void totalChildren(int t) {
		children = new int[t];
		childX = new int[t];
		childY = new int[t];
	}

	public void totalChildren(int id, int x, int y) {
		children = new int[id];
		childX = new int[x];
		childY = new int[y];
	}

	public long[] showChangeExclamationDelay;
	public int[] showChangeExclamationTimer;
	public boolean[] showChangeExclamation;

	public int sprite1ID;
	public int sprite2ID;

}