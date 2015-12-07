package main.model.services;

public interface BrowserService {

	/**
	 * Open the "About" using the default browser.
	 */
	public void openAboutUrl();
	
	/**
	 * Open the "Bagpipe API" using the default browser.
	 */
	public void openBagpipeApiUrl();
	
	/**
	 * Open the "Configuration Application API" using the default browser.
	 */
	public void openConfAppApiUrl();
	
	/**
	 * Open the "User Manual" using the default browser.
	 */
	public void openUserManualUrl();
	
	/**
	 * Open the provided URI using the default browser.
	 * @param uri URI to be shown.
	 */
	public void openUri(String uri);
	
}
