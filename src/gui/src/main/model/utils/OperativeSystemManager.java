package main.model.utils;

import java.util.Locale;

public class OperativeSystemManager {
	
	private static String OS_NAME;
	private static boolean isWindows;
	private static boolean isMacOs;
	private static boolean isUnix;
	private static String language;
	
	static {
		setOperativeSystem();
		setLanguage();
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
	
	public static String getLanguage() {
		return language;
	}
	
	private static void setOperativeSystem() {
		
		OS_NAME = System.getProperty("os.name");
		
		if (OS_NAME.startsWith("Windows")) {
			isWindows = true;
		} else if (OS_NAME.startsWith("Mac OS")) {
			isMacOs = true;
		} else { // UNIX-like by default.
			isUnix = true;
		}
	}
	
	private static void setLanguage() {
		language = Locale.getDefault().getLanguage();
	}

}
