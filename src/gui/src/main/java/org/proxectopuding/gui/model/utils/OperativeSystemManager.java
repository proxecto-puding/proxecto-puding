/*
 * Proxecto Puding
 * Copyright (C) 2011 Alejo Pacín <info@proxecto-puding.org>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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
