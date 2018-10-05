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

import java.awt.Desktop;
import java.awt.Desktop.Action;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;

public class BrowserManager {
	
	private static final Logger LOGGER = Logger.getLogger(BrowserManager.class.getName());
	
	private final Desktop desktop;
	private final DefaultExecutor executor;
	
	public BrowserManager() {
		
		if (Desktop.isDesktopSupported()) {
			desktop = Desktop.getDesktop();
		} else {
			desktop = null;
			LOGGER.log(Level.SEVERE, "Desktop not supported");
		}
		executor = new DefaultExecutor();
		executor.setExitValue(0);
	}
	
	public void openUri(String uri) {
		
		if (desktop != null) {
			try {
				if (desktop.isSupported(Action.BROWSE)) {
					desktop.browse(new URI(uri));
				} else {
					// TODO Make it OS/browser-configurable.
					String command = "/usr/lib/firefox/firefox " + uri;
					CommandLine commandLine = CommandLine.parse(command);
					executor.execute(commandLine);
				}
			} catch (Exception e) {
				LOGGER.log(Level.SEVERE, "Unable to open the URL: {0}", uri);
				LOGGER.log(Level.SEVERE, "Unable to open the URL", e);
			}
		} else {
			LOGGER.log(Level.SEVERE, "Desktop not supported");
		}
	}

}
