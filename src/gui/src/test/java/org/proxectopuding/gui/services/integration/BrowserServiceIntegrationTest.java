package org.proxectopuding.gui.services.integration;

import org.junit.Test;
import org.proxectopuding.gui.model.services.BrowserService;
import org.proxectopuding.gui.model.services.impl.BrowserServiceImpl;
import org.proxectopuding.gui.model.utils.BrowserManager;
import org.proxectopuding.gui.model.utils.ConfigurationManager;
import org.proxectopuding.gui.model.utils.PropertiesManager;

public class BrowserServiceIntegrationTest {
	
	private static final String URI = "http://proxecto-puding.org";
	
	private BrowserManager browserManager = new BrowserManager();
	private PropertiesManager propertiesManager = new PropertiesManager();
	private ConfigurationManager configurationManager =
			new ConfigurationManager(propertiesManager);
	private BrowserService browserService =
			new BrowserServiceImpl(browserManager, configurationManager);
	
	@Test
	public void openAboutUrl() {
		
		// When
		browserService.openAboutUrl();
	}
	
	@Test
	public void openBagpipeApiUrl() {
		
		// When
		browserService.openBagpipeApiUrl();
	}
	
	@Test
	public void openConfAppApiUrl() {
		
		// When
		browserService.openConfAppApiUrl();
	}
	
	@Test
	public void openUserManualUrl() {
		
		// When
		browserService.openUserManualUrl();
	}
	
	@Test
	public void openTechnicalManualUrl() {
		
		// When
		browserService.openTechnicalManualUrl();
	}
	
	@Test
	public void openUri() {
		
		// When
		browserService.openUri(URI);
	}
}
