package org.proxectopuding.gui.model.services.impl;

import org.proxectopuding.gui.model.services.BrowserService;
import org.proxectopuding.gui.model.utils.BrowserManager;
import org.proxectopuding.gui.model.utils.ConfigurationManager;

import com.google.inject.Inject;

public class BrowserServiceImpl implements BrowserService {

	private final BrowserManager browserManager;
	
	private final String aboutUrl;
	private final String bagpipeApiUrl;
	private final String confAppApiUrl;
	private final String userManualUrl;
	
	@Inject
	public BrowserServiceImpl(BrowserManager browserManager,
			ConfigurationManager configurationManager) {

		this.browserManager = browserManager;
		
		aboutUrl = configurationManager.getAboutUrl();
		bagpipeApiUrl = configurationManager.getBagpipeApiUrl();
		confAppApiUrl = configurationManager.getConfAppApiUrl();
		userManualUrl = configurationManager.getUserManualUrl();
	}
	
	@Override
	public void openUri(String uri) {
		
		browserManager.openUri(uri);
	}

	@Override
	public void openAboutUrl() {
		
		browserManager.openUri(aboutUrl);
	}

	@Override
	public void openBagpipeApiUrl() {
		
		browserManager.openUri(bagpipeApiUrl);
	}

	@Override
	public void openConfAppApiUrl() {
		
		browserManager.openUri(confAppApiUrl);
	}

	@Override
	public void openUserManualUrl() {
		
		browserManager.openUri(userManualUrl);
	}

}
