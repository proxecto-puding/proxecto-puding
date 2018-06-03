package org.proxectopuding.gui.model.utils;

import java.awt.Desktop;
import java.net.URI;

public class BrowserManager {
	
	public static void openUri(String uri) {
		
		if (Desktop.isDesktopSupported()) {
			
			Desktop desktop = Desktop.getDesktop();
			if (desktop != null) {
				try {
					desktop.browse(new URI(uri));
				} catch (Exception e) {
					System.err.println("Error while opening URL '" + uri + "' " +
							" Message: " + e.getMessage());
					e.printStackTrace();
				}
			} else {
				// TODO Search for a proper alternative.
			}
			
		} else {
			// TODO Search for a proper alternative.
		}
		
	}

}
