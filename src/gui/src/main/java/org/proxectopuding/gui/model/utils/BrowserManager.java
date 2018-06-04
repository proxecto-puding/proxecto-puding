package org.proxectopuding.gui.model.utils;

import java.awt.Desktop;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BrowserManager {
	
	private static final Logger LOGGER = Logger.getLogger(BrowserManager.class.getName());
	
	public static void openUri(String uri) {
		
		if (Desktop.isDesktopSupported()) {
			
			Desktop desktop = Desktop.getDesktop();
			if (desktop != null) {
				try {
					desktop.browse(new URI(uri));
				} catch (Exception e) {
					LOGGER.log(Level.SEVERE, "Unable to open the URL: {0}", uri);
					LOGGER.log(Level.SEVERE, "Unable to open the URL", e);
				}
			} else {
				// TODO Search for a proper alternative.
			}
			
		} else {
			// TODO Search for a proper alternative.
		}
		
	}

}
