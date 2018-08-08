package org.proxectopuding.gui.model.services.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.proxectopuding.gui.model.services.BrowserService;
import org.proxectopuding.gui.model.utils.BrowserManager;
import org.proxectopuding.gui.model.utils.ConfigurationManager;

import com.google.inject.Inject;

public class BrowserServiceImpl implements BrowserService {
	
	private static final Logger LOGGER = Logger.getLogger(BrowserServiceImpl.class.getName());

	private final BrowserManager browserManager;
	
	private final String aboutUrl;
	private final String bagpipeApiUrl;
	private final String confAppApiUrl;
	private final String userManualUrl;
	private final String technicalManualUrl;
	
	@Inject
	public BrowserServiceImpl(BrowserManager browserManager,
			ConfigurationManager configurationManager) {

		this.browserManager = browserManager;
		
		aboutUrl = configurationManager.getAboutUrl();
		bagpipeApiUrl = configurationManager.getBagpipeApiUrl();
		confAppApiUrl = configurationManager.getConfAppApiUrl();
		userManualUrl = configurationManager.getUserManualUrl();
		technicalManualUrl = configurationManager.getTechnicalManualUrl();
	}
	
	@Override
	public void openAboutUrl() {
		
		LOGGER.log(Level.INFO, "Opening about url");
		browserManager.openUri(aboutUrl);
	}

	@Override
	public void openBagpipeApiUrl() {
		
		LOGGER.log(Level.INFO, "Opening bagpipe api url");
		browserManager.openUri(bagpipeApiUrl);
	}

	@Override
	public void openConfAppApiUrl() {
		
		LOGGER.log(Level.INFO, "Opening conf app api url");
		browserManager.openUri(confAppApiUrl);
	}

	@Override
	public void openUserManualUrl() {
		
		LOGGER.log(Level.INFO, "Opening user manual url");
		browserManager.openUri(userManualUrl);
	}
	
	@Override
	public void openTechnicalManualUrl() {
		
		LOGGER.log(Level.INFO, "Opening technical manual url");
		browserManager.openUri(technicalManualUrl);
	}
	
	@Override
	public void openUri(String uri) {
		
		LOGGER.log(Level.INFO, "Opening uri: {0}", uri);
		browserManager.openUri(uri);
	}

}
