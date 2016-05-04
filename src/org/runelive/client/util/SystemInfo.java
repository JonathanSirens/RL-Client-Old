package org.runelive.client.util;

public class SystemInfo {

	/**
	 * Gets the name of the installed operating system.
	 * 
	 * @return String value of operating system name.
	 */
	public static String getOsName() {
		return System.getProperty("os.name");
	}

	/**
	 * Gets a count of processors installed on the system.
	 * 
	 * @return Numeric (int) value of computer processor count.
	 */
	public static int getCores() {
		return Runtime.getRuntime().availableProcessors();
	}
	
	/**
	 * Gets a count of processors installed on the system.
	 * 
	 * @return String value of computer processor count.
	 */
	public static String getCoresToString() {
		String[] prefix = { "Single", "Dual", "Triple", "Quad", "Penta", "Hexa", "Hepta", "Octa", "Nona", "Deca" };
		return prefix[SystemInfo.getCores() - 1] + "-Core";
	}
	
	/**
	 * Get Total Memory available to JVM.
	 * 
	 * @return Numeric (long) value of count memory in Bytes.
	 */
	public static long getTotalMem() {
		return (Runtime.getRuntime().totalMemory());
	}

	/**
	 * Maximum memory used by JVM during runtime in Bytes.
	 * 
	 * @return Numeric (long) value of max memory in Bytes.
	 */
	public static long getMaxMem() {
		return (Runtime.getRuntime().maxMemory());
	}

	/**
	 * Get free memory.
	 * 
	 * @return Numeric (long) value of free memory in Bytes.
	 */
	public static long getFreeMem() {
		return (Runtime.getRuntime().freeMemory());
	}
	
	/**
	 * Get CPU Architecture. (32 bit / 64 bit, etc...)
	 * 
	 * @return String value of CPU Architecture.
	 */
	public static String getCPUArch() {
		return System.getProperty("os.arch");
	}
	
	/**
     * Get OS Architecture. (32 bit / 64 bit, etc...)
     * 
     * @return String value of OS Architecture.
     */
	public static String getOSArch() {
        return System.getProperty("sun.arch.data.model");
    }
	
	/**
	 * Confirms whether the installed operating system is Windows.
	 */
	public static boolean isWindows() {
		return (getOsName().toLowerCase().indexOf("windows") >= 0);
	}

	/**
	 * Confirms whether the installed operating system is Linux.
	 */
	public static boolean isLinux() {
		return getOsName().toLowerCase().indexOf("linux") >= 0;
	}

	/**
	 * Confirms whether the installed operating system is Unix-based.
	 */
	public static boolean isUnix() {
		final String os = getOsName().toLowerCase();
		if ((os.indexOf("sunos") >= 0) || (os.indexOf("linux") >= 0)) {
			return true;
		}
		if (isMac() && (System.getProperty("os.version", "").startsWith("10."))) {
			return true;
		}
		return false;
	}

	/**
	 * Confirms whether the installed operating system is Mac.
	 */
	public static boolean isMac() {
		final String os = getOsName().toLowerCase();
		return os.startsWith("mac") || os.startsWith("darwin");
	}

	/**
	 * Confirms whether the installed operating system is Solaris.
	 */
	public static boolean isSolaris() {
		final String os = getOsName().toLowerCase();
		return os.indexOf("sunos") >= 0;
	}

	/**
	 * Confirms whether the installed operating system is Aix.
	 */
	public static boolean isAix() {
		return getOsName().toLowerCase().indexOf("aix") >= 0;
	}

}