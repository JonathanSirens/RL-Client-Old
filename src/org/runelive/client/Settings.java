package org.runelive.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.runelive.Configuration;
import org.runelive.client.constants.GameFrameConstants;
import org.runelive.task.Task;
import org.runelive.task.TaskManager;

public final class Settings {

	public static void load() {
		try {
			File oldFile = new File(Signlink.getOldCacheDirectory() + File.separator + "settings.dat");
			File file = new File(Signlink.getCacheDirectory() + File.separator + "settings.dat");
			if (!file.exists()) {
				if (oldFile.exists()) {
					Files.move(oldFile.toPath(), file.toPath(), StandardCopyOption.ATOMIC_MOVE);
					System.out.println("Old settings moved to new cache dir.");
				} else {
					return;
				}
			}
			DataInputStream stream = new DataInputStream(new FileInputStream(file));
			try {
				Configuration.SAVE_ACCOUNTS = stream.readBoolean();
				Configuration.NEW_FUNCTION_KEYS = stream.readBoolean();
				Configuration.NEW_HEALTH_BARS = stream.readBoolean();
				Configuration.NEW_HITMARKS = stream.readBoolean();
				Configuration.CONSTITUTION_ENABLED = stream.readBoolean();
				Configuration.NEW_CURSORS = stream.readBoolean();
				Configuration.DISPLAY_HP_ABOVE_HEAD = stream.readBoolean();
				Configuration.DISPLAY_USERNAMES_ABOVE_HEAD = stream.readBoolean();
				GameFrameConstants.gameframeType = stream.readBoolean() ? GameFrameConstants.GameFrameType.FRAME_554
						: GameFrameConstants.GameFrameType.FRAME_525;
				Configuration.NOTIFICATIONS_ENABLED = stream.readBoolean();
				Configuration.HIGH_DETAIL = stream.readBoolean();
				Client.instance.rememberMe = stream.readBoolean();
				Client.instance.saved_characters_usernames[0] = stream.readUTF();
				Client.instance.saved_characters_usernames[1] = stream.readUTF();
				Client.instance.saved_characters_usernames[2] = stream.readUTF();
				Client.instance.saved_characters_passwords[0] = stream.readUTF();
				Client.instance.saved_characters_passwords[1] = stream.readUTF();
				Client.instance.saved_characters_passwords[2] = stream.readUTF();
				Client.myUsername = Client.instance.saved_characters_usernames[0];
				Client.instance.password = Client.instance.saved_characters_passwords[0];
				Client.instance.splitChatColor = stream.readInt();
				Client.instance.variousSettings[287] = Client.instance.variousSettings[502] = stream.readByte();
				Client.instance.updateConfig(287);
				if (!Configuration.HIGH_DETAIL) {
					Client.setLowDetail();
				} else {
					Client.setHighDetail();
				}

				/*
				 * Quick prayers / curses
				 */
				String q = stream.readUTF();
				for (int i = 0; i < q.length(); i++)
					Client.instance.quickPrayers[i] = Integer.parseInt(q.substring(i, i + 1));
				q = stream.readUTF();
				for (int i = 0; i < q.length(); i++) {
					Client.instance.quickCurses[i] = Integer.parseInt(q.substring(i, i + 1));
				}
				if (stream.available() >= 1) {
					Configuration.FOG_ENABLED = stream.readBoolean();
				}
				if (stream.available() >= 1) {
					Configuration.TOGGLE_ROOF_OFF = stream.readBoolean();
				}
				if (stream.available() >= 1) {
					Client.instance.changeMenuText = stream.readBoolean();
				}
			} catch (IOException e) {
				file.delete();
				Configuration.SAVE_ACCOUNTS = true;
				Configuration.NEW_FUNCTION_KEYS = true;
				Configuration.NEW_FUNCTION_KEYS = true;
				Configuration.NEW_HEALTH_BARS = true;
				Configuration.NEW_HITMARKS = true;
				Configuration.CONSTITUTION_ENABLED = true;
				Configuration.NEW_CURSORS = true;
				Configuration.DISPLAY_HP_ABOVE_HEAD = false;
				Configuration.DISPLAY_USERNAMES_ABOVE_HEAD = false;
				GameFrameConstants.gameframeType = GameFrameConstants.GameFrameType.FRAME_554;
				Configuration.NOTIFICATIONS_ENABLED = true;
				Configuration.HIGH_DETAIL = false;
				Client.instance.rememberMe = true;
				Client.instance.saved_characters_usernames[0] = "Empty";
				Client.instance.saved_characters_usernames[1] = "Empty";
				Client.instance.saved_characters_usernames[2] = "Empty";
				Client.instance.saved_characters_passwords[0] = "none";
				Client.instance.saved_characters_passwords[1] = "none";
				Client.instance.saved_characters_passwords[2] = "none";
				Client.instance.myUsername = "";
				Client.instance.password = "";
				e.printStackTrace();
			} finally {
				stream.close();
			}
		} catch (IOException e) {

		}
	}

	private static final Task SAVE_TASK = new Task() {
		@Override
		public void execute() {
			saveBlock();
		}
	};

	/**
	 * Saves the player save data on the task thread.
	 */
	public static void save() {
		TaskManager.submit(SAVE_TASK);
	}

	/**
	 * Saves the players data
	 *
	 * @throws IOException
	 */
	private static void saveBlock() {
		try {
			File file = new File(Signlink.getCacheDirectory() + "/settings.dat");
			if (!file.exists()) {
				file.createNewFile();
			}
			DataOutputStream stream = new DataOutputStream(new FileOutputStream(file));
			if (stream != null) {
				stream.writeBoolean(Configuration.SAVE_ACCOUNTS);
				stream.writeBoolean(Configuration.NEW_FUNCTION_KEYS);
				stream.writeBoolean(Configuration.NEW_HEALTH_BARS);
				stream.writeBoolean(Configuration.NEW_HITMARKS);
				stream.writeBoolean(Configuration.CONSTITUTION_ENABLED);
				stream.writeBoolean(Configuration.NEW_CURSORS);
				stream.writeBoolean(Configuration.DISPLAY_HP_ABOVE_HEAD);
				stream.writeBoolean(Configuration.DISPLAY_USERNAMES_ABOVE_HEAD);
				stream.writeBoolean(
						GameFrameConstants.gameframeType == GameFrameConstants.GameFrameType.FRAME_525 ? false : true);
				stream.writeBoolean(Configuration.NOTIFICATIONS_ENABLED);
				stream.writeBoolean(Configuration.HIGH_DETAIL);
				stream.writeBoolean(Client.instance.rememberMe);
				stream.writeUTF(Client.instance.saved_characters_usernames[0]);
				stream.writeUTF(Client.instance.saved_characters_usernames[1]);
				stream.writeUTF(Client.instance.saved_characters_usernames[2]);
				stream.writeUTF(Client.instance.saved_characters_passwords[0]);
				stream.writeUTF(Client.instance.saved_characters_passwords[1]);
				stream.writeUTF(Client.instance.saved_characters_passwords[2]);
				stream.writeInt(Client.instance.splitChatColor);
				stream.writeByte(Client.instance.variousSettings[502]); // Split
																		// private
																		// chat?

				/*
				 * Quick prayers & curses saving
				 */
				String stringSave = "";
				for (int i = 0; i < Client.instance.quickPrayers.length; i++) {
					stringSave = stringSave + Client.instance.quickPrayers[i];
				}
				stream.writeUTF(stringSave);
				stringSave = "";
				for (int i = 0; i < Client.instance.quickCurses.length; i++) {
					stringSave = stringSave + Client.instance.quickCurses[i];
				}
				stream.writeUTF(stringSave);

				stream.writeBoolean(Configuration.FOG_ENABLED);
				stream.writeBoolean(Configuration.TOGGLE_ROOF_OFF);
				stream.writeBoolean(Client.instance.changeMenuText);
				stream.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
