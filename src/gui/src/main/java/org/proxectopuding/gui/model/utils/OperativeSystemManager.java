package org.proxectopuding.gui.model.utils;

import java.util.Locale;

public class OperativeSystemManager {
	
	private String OS_NAME;
	private boolean isWindows;
	private boolean isMacOs;
	private boolean isUnix;
	private String language;
	
	public OperativeSystemManager() {
		
		setOperativeSystem();
		setLanguage();
	}
	
	public boolean isWindows() {
		return isWindows;
	}
	
	public boolean isMacOs() {
		return isMacOs;
	}
	
	public boolean isUnix() {
		return isUnix;
	}
	
	public String getLanguage() {
		return language;
	}
	
	private void setOperativeSystem() {
		
		OS_NAME = System.getProperty("os.name");
		
		if (OS_NAME.startsWith("Windows")) {
			isWindows = true;
		} else if (OS_NAME.startsWith("Mac OS")) {
			isMacOs = true;
		} else { // UNIX-like by default.
			isUnix = true;
		}
	}
	
	private void setLanguage() {
		language = Locale.getDefault().getLanguage();
	}

}
