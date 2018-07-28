package org.proxectopuding.gui.services.acceptance;

import org.junit.Test;
import org.proxectopuding.gui.model.services.BrowserService;
import org.proxectopuding.gui.model.services.impl.BrowserServiceImpl;
import org.proxectopuding.gui.model.utils.BrowserManager;
import org.proxectopuding.gui.model.utils.ConfigurationManager;
import org.proxectopuding.gui.model.utils.PropertiesManager;

public class BrowserServiceAcceptanceTest {
	
	private BrowserManager browserManager = new BrowserManager();
	private PropertiesManager propertiesManager = new PropertiesManager();
	private ConfigurationManager configurationManager =
			new ConfigurationManager(propertiesManager);
	private BrowserService browserService =
			new BrowserServiceImpl(browserManager, configurationManager);
	
	/**
	 * Feature: user wants to check the documentation
	 */
	
	/**
	 * Scenario: user wants to check the user's manual
	 * When user request to see the user's manual
	 * Then the required actors are notified
	 */
	@Test
	public void openUserManualUrl() {
		
		// When
		browserService.openUserManualUrl();
	}
}
