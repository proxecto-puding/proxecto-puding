package org.proxectopuding.gui.controller;

import org.proxectopuding.gui.model.services.BrowserService;
import org.proxectopuding.gui.model.services.I18nService;
import org.proxectopuding.gui.model.services.MidiService;
import org.proxectopuding.gui.model.services.NotificationService;

import com.google.inject.Inject;

public class MenuBarController extends Controller {
	
	private final I18nService i18nService;
	private final BrowserService browserService;
	private final MidiService midiService;
	
	@Inject
	public MenuBarController(I18nService i18nService,
			BrowserService browserService, MidiService midiService,
			NotificationService notificationService) {
		
		super(notificationService);
		
		this.i18nService = i18nService;
		this.browserService = browserService;
		this.midiService = midiService;
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
		
		midiService.stop();
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
