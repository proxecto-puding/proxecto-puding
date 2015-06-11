package main.model.entities;

public class OperativeSystem {
	
	private static String OS_NAME;
	private static boolean isWindows;
	private static boolean isMacOs;
	private static boolean isUnix;
	
	static {
		OS_NAME = System.getProperty("os.name");
		
		if (OS_NAME.startsWith("Windows")) {
			isWindows = true;
		} else if (OS_NAME.startsWith("Mac OS")) {
			isMacOs = true;
		} else { // UNIX-like by default.
			isUnix = true;
		}
	};
	
	public static boolean isWindows() {
		return isWindows;
	}
	
	public static boolean isMacOs() {
		return isMacOs;
	}
	
	public static boolean isUnix() {
		return isUnix;
	}

}
