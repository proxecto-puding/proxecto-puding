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
