package org.proxectopuding.gui.model.services.impl;

import org.proxectopuding.gui.model.services.BrowserService;
import org.proxectopuding.gui.model.utils.BrowserManager;
import org.proxectopuding.gui.model.utils.ConfigurationManager;

public class BrowserServiceImpl implements BrowserService {

	private static final String aboutUrl;
	private static final String bagpipeApiUrl;
	private static final String confAppApiUrl;
	private static final String userManualUrl;
	
	static {
		aboutUrl = ConfigurationManager.getAboutUrl();
		bagpipeApiUrl = ConfigurationManager.getBagpipeApiUrl();
		confAppApiUrl = ConfigurationManager.getConfAppApiUrl();
		userManualUrl = ConfigurationManager.getUserManualUrl();
	};
	
	@Override
	public void openUri(String uri) {
		
		BrowserManager.openUri(uri);
		
	}

	@Override
	public void openAboutUrl() {
		
		BrowserManager.openUri(aboutUrl);
		
	}

	@Override
	public void openBagpipeApiUrl() {
		
		BrowserManager.openUri(bagpipeApiUrl);
		
	}

	@Override
	public void openConfAppApiUrl() {
		
		BrowserManager.openUri(confAppApiUrl);
		
	}

	@Override
	public void openUserManualUrl() {
		
		BrowserManager.openUri(userManualUrl);
		
	}

}
