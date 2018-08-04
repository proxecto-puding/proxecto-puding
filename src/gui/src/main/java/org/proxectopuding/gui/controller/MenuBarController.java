package org.proxectopuding.gui.controller;

import org.proxectopuding.gui.model.services.BrowserService;
import org.proxectopuding.gui.model.services.I18nService;

import com.google.inject.Inject;

public class MenuBarController {
	
	private final I18nService i18nService;
	private final BrowserService browserService;
	
	@Inject
	public MenuBarController(I18nService i18nService,
			BrowserService browserService) {
		
		this.i18nService = i18nService;
		this.browserService = browserService;
	}
	
	public String getFileMenuLabel() {
		return i18nService.getTranslation("menuBar.file");
	}
	
	public String getExitMenuItemLabel() {
		return i18nService.getTranslation("menuBar.file.exit");
	}
	
	public String getHelpMenuLabel() {
		return i18nService.getTranslation("menuBar.help");
	}
	
	public String getUserManualMenuItemLabel() {
		return i18nService.getTranslation("menuBar.help.userManual");
	}
	
	public String getConfAppApiMenuItemLabel() {
		return i18nService.getTranslation("menuBar.help.confAppApi");
	}
	
	public String getBagpipeApiMenuItemLabel() {
		return i18nService.getTranslation("menuBar.help.bagpipeApi");
	}
	
	public String getAboutMenuItemLabel() {
		return i18nService.getTranslation("menuBar.help.about");
	}
	
	public void onExit() {
		
		// TODO Stop MIDI server
		System.exit(0);
	}
	
	public void onOpenUserManual() {
		
		browserService.openUserManualUrl();
	}
	
	public void openConfAppApi() {
		
		browserService.openConfAppApiUrl();
	}
	
	public void openBagpipeApi() {
		
		browserService.openBagpipeApiUrl();
	}
	
	public void openAbout() {
		
		browserService.openAboutUrl();
	}
	
}
