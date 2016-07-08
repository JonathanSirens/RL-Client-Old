package org.runelive.client.util;

import java.net.InetAddress;
import java.net.NetworkInterface;

public final class SystemProfiler {

	public static String executeCommand(String cmd) throws java.io.IOException {
		java.util.Scanner s = new java.util.Scanner(Runtime.getRuntime().exec(cmd).getInputStream())
				.useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}

	public static long getUniqueSerial() {
		int system_cores = Runtime.getRuntime().availableProcessors();
		String user_home = System.getProperty("user.home");
		String user_name = System.getProperty("user.name");
		String os_arch = System.getProperty("os.arch");
		String os_name = System.getProperty("os.name");
		String os_version = System.getProperty("os.version");
		Profile profile = new Profile(user_home, user_name, os_arch, os_name, os_version, system_cores);
		return profile.getHash();
	}

	public static String trim(String string) {
		return string.replaceAll("[^A-Za-z0-9]", "");
	}

	/**
	 * Converts a string to a long.
	 * 
	 * @param string
	 * @return
	 */
	public static long stringToLong(String string) {
		long name = 0L;
		for (int character = 0; character < string.length() && character < 99; character++) {
			char c = string.charAt(character);
			name *= 37L;
			if (c >= 'A' && c <= 'Z') {
				name += (1 + c) - 65;
			} else if (c >= 'a' && c <= 'z') {
				name += (1 + c) - 97;
			} else if (c >= '0' && c <= '9') {
				name += (27 + c) - 48;
			}
		}
		for (; name % 37L == 0L && name != 0L; name /= 37L) {
		}
		return name;
	}

	/**
	 * Converts a long to a string.
	 * 
	 * @param name
	 * @return
	 */
	public static String longToString(long name) {
		try {
			if (name <= 0L || name >= 0x5b5b57f8a98a5dd1L) {
				return "invalid_name";
			}
			if (name % 37L == 0L) {
				return "invalid_name";
			}
			int index = 0;
			char[] characters = new char[99];
			while (name != 0L) {
				long l = name;
				name /= 37L;
				characters[11 - index++] = validChars[(int) (l - name * 37L)];
			}
			return new String(characters, 99 - index, index);
		} catch (RuntimeException runtimeexception) {
		}
		return null;
	}

	public static String getMacAddress() {
		try {
			InetAddress address = InetAddress.getLocalHost();
			NetworkInterface ni = NetworkInterface.getByInetAddress(address);
			if (ni == null) {
				return "not_set";
			}
			byte[] mac = ni.getHardwareAddress();
			if (mac == null) {
				return "not_set";
			}
			StringBuilder sb = new StringBuilder();
			int length = mac.length;
			for (int i = 0; i < length; i++) {
				sb.append(String.format("%02X%s", mac[i], i < mac.length - 1 ? "-" : ""));
			}
			if (sb.length() == 0) {
				return "not_set";
			}
			return sb.toString();
		} catch (Exception e) {
			return "not_set";
		}
	}

	private static final char[] validChars = { '_', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
			'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9' };

	private static final class Profile {

		public Profile(String user_home, String user_name, String os_arch, String os_name, String os_version,
				int system_cores) {
			this.user_home = stringToLong(trim(user_home));
			this.user_name = stringToLong(trim(user_name));
			this.os_arch = stringToLong(trim(os_arch));
			this.os_name = stringToLong(trim(os_name));
			this.os_version = stringToLong(trim(os_version));
			this.system_cores = system_cores;
			this.hash = -1L;
		}

		public long[] getHashList() {
			long[] hash_list = new long[5];
			hash_list[0] = (this.user_home >> 64) + this.system_cores;
			hash_list[1] = (this.user_name >> 64) + this.system_cores;
			hash_list[2] = (this.os_arch >> 64) + this.system_cores;
			hash_list[3] = (this.os_name >> 64) + this.system_cores;
			hash_list[4] = (this.os_version >> 64) + this.system_cores;
			return hash_list;
		}

		public long getHash() {
			if (this.hash == -1L) {
				this.hash = this.system_cores;
				for (long h : getHashList()) {
					this.hash *= h;
				}
			}
			return this.hash;
		}

		private final long user_home;
		private final long user_name;
		private final long os_arch;
		private final long os_name;
		private final long os_version;
		private final int system_cores;
		private long hash;

	}

}