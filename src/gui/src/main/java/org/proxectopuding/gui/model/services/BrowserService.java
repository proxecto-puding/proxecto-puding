package org.proxectopuding.gui.model.services;

public interface BrowserService {

	/**
	 * Open the "About" using the default browser.
	 */
	void openAboutUrl();
	
	/**
	 * Open the "Bagpipe API" using the default browser.
	 */
	void openBagpipeApiUrl();
	
	/**
	 * Open the "Configuration Application API" using the default browser.
	 */
	void openConfAppApiUrl();
	
	/**
	 * Open the "User Manual" using the default browser.
	 */
	void openUserManualUrl();
	
	/**
	 * Open the "Technical Manual" using the default browser.
	 */
	void openTechnicalManualUrl();
	
	/**
	 * Open the provided URI using the default browser.
	 * @param uri URI to be shown.
	 */
	void openUri(String uri);
}
