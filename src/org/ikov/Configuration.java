package org.ikov;

import org.ikov.client.updates.ClientUpdate;

/**
 * The client's features can easily be toggled/changed here.
 * @author Gabriel Hannason
 */
public class Configuration {

	/** CONNECTION **/
	public final static boolean LOCAL = false;
	public final static String SERVER_HOST = LOCAL ? "127.0.0.1" : "158.69.125.71"; // 158.69.125.71
	public final static int SERVER_PORT = 59018;
	
	/** LOADS CACHE FROM ./ IF TRUE, OTHERWISE USER.HOME FOLDER**/
	public static final boolean DROPBOX_MODE = false;
	
	/** MAIN CONSTANTS **/
	public final static String CLIENT_NAME = "Ikov " + ClientUpdate.clientVersion + "";
	public final static String CACHE_DIRECTORY_NAME = "ikov_cache2"; // Cache folder name
	public final static int CLIENT_VERSION = 3;
	public final static boolean JAGCACHED_ENABLED = false;
	public final static String JAGCACHED_HOST = "";
	
	/** UPDATING **/
	public final static int NPC_BITS = 18;
	
	/** FEATURES **/
	public static boolean SAVE_ACCOUNTS = false;
	public static boolean DISPLAY_HP_ABOVE_HEAD = false;
	public static boolean DISPLAY_USERNAMES_ABOVE_HEAD = false;
	public static boolean TWEENING_ENABLED = true;
	
	public static boolean NEW_HITMARKS = false;
	public static boolean CONSTITUTION_ENABLED = true;
	public static boolean NEW_HEALTH_BARS = true;
	
	public static boolean MONEY_POUCH_ENABLED = false;
	public static boolean SMILIES_ENABLED = true;
	public static boolean NOTIFICATIONS_ENABLED = true;
	public static boolean NEW_CURSORS = true;
	public static boolean NEW_FUNCTION_KEYS = true;
	
	public static boolean FOG_ENABLED = true;
	/**
	 * The client will run in high memory with textures rendering
	 */
	public static boolean HIGH_DETAIL = true;
	public static boolean hdTexturing = true;
	public static boolean hdMinimap = true;
	public static boolean hdShading = true;
	
	/**
	 * Roofs should be displayed
	 */
	public static boolean TOGGLE_ROOF_OFF = true;

	/**
	 * Should the new fov be displayed?
	 */
	public static boolean TOGGLE_FOV = true;

}