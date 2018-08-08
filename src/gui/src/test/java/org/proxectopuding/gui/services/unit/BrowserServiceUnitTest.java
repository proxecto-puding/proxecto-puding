package org.proxectopuding.gui.services.unit;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.proxectopuding.gui.model.services.BrowserService;
import org.proxectopuding.gui.model.services.impl.BrowserServiceImpl;
import org.proxectopuding.gui.model.utils.BrowserManager;
import org.proxectopuding.gui.model.utils.ConfigurationManager;

public class BrowserServiceUnitTest {
	
	private static final String ABOUT_URL = "ABOUT_URL";
	private static final String BAGPIPE_API_URL = "BAGPIPE_API_URL";
	private static final String CONF_APP_API_URL = "CONF_APP_API_URL";
	private static final String USER_MANUAL_URL = "USER_MANUAL_URL";
	private static final String TECHNICAL_MANUAL_URL = "TECHNICAL_MANUAL_URL";
	
	private BrowserManager browserManager = mock(BrowserManager.class);
	private ConfigurationManager configurationManager =
			mock(ConfigurationManager.class);
	private BrowserService browserService;
	
	@Before
	public void before() {
		
		reset(browserManager, configurationManager);
	}
	
	@Test
	public void openAboutUrl() {
		
		// Given
		when(configurationManager.getAboutUrl()).thenReturn(ABOUT_URL);
		doNothing().when(browserManager).openUri(ABOUT_URL);
		browserService = new BrowserServiceImpl(browserManager, configurationManager);
		
		// When
		browserService.openAboutUrl();
		
		// Then
		verify(browserManager, times(1)).openUri(ABOUT_URL);
	}
	
	@Test
	public void openBagpipeApiUrl() {
		
		// Given
		when(configurationManager.getBagpipeApiUrl()).thenReturn(BAGPIPE_API_URL);
		doNothing().when(browserManager).openUri(BAGPIPE_API_URL);
		browserService = new BrowserServiceImpl(browserManager, configurationManager);

		// When
		browserService.openBagpipeApiUrl();
		
		// Then
		verify(browserManager, times(1)).openUri(BAGPIPE_API_URL);
	}
	
	@Test
	public void openConfAppApiUrl() {
		
		// Given
		when(configurationManager.getConfAppApiUrl()).thenReturn(CONF_APP_API_URL);
		doNothing().when(browserManager).openUri(CONF_APP_API_URL);
		browserService = new BrowserServiceImpl(browserManager, configurationManager);
		
		// When
		browserService.openConfAppApiUrl();
		
		// Then
		verify(browserManager, times(1)).openUri(CONF_APP_API_URL);
	}
	
	@Test
	public void openUserManualUrl() {
		
		// Given
		when(configurationManager.getUserManualUrl()).thenReturn(USER_MANUAL_URL);
		doNothing().when(browserManager).openUri(USER_MANUAL_URL);
		browserService = new BrowserServiceImpl(browserManager, configurationManager);
		
		// When
		browserService.openUserManualUrl();
		
		// Then
		verify(browserManager, times(1)).openUri(USER_MANUAL_URL);
	}
	
	@Test
	public void openTechnicalManualUrl() {
		
		// Given
		when(configurationManager.getTechnicalManualUrl()).thenReturn(TECHNICAL_MANUAL_URL);
		doNothing().when(browserManager).openUri(TECHNICAL_MANUAL_URL);
		browserService = new BrowserServiceImpl(browserManager, configurationManager);
		
		// When
		browserService.openTechnicalManualUrl();
		
		// Then
		verify(browserManager, times(1)).openUri(TECHNICAL_MANUAL_URL);
	}
	
	@Test
	public void openUri() {
		
		// Given
		doNothing().when(browserManager).openUri(anyString());
		browserService = new BrowserServiceImpl(browserManager, configurationManager);
		
		// When
		browserService.openUri(anyString());
		
		// Then
		verify(browserManager, times(1)).openUri(anyString());
	}
}
