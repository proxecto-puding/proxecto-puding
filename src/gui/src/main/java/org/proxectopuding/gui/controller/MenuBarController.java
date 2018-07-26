package org.proxectopuding.gui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	
	public String getTranslationForFileMenu() {
		return i18nService.getTranslation("menuBar.file");
	}
	
	public String getTranslationForExitMenuItem() {
		return i18nService.getTranslation("menuBar.file.exit");
	}
	
	public String getTranslationForHelpMenu() {
		return i18nService.getTranslation("menuBar.help");
	}
	
	public String getTranslationForUserManualMenuItem() {
		return i18nService.getTranslation("menuBar.help.userManual");
	}
	
	public String getTranslationForConfAppApiMenuItem() {
		return i18nService.getTranslation("menuBar.help.confAppApi");
	}
	
	public String getTranslationForBagpipeApiMenuItem() {
		return i18nService.getTranslation("menuBar.help.bagpipeApi");
	}
	
	public String getTranslationForAboutMenuItem() {
		return i18nService.getTranslation("menuBar.help.about");
	}
	
	public ActionListener getActionListenerForExitMenuItem() {
		
		ActionListener actionListener = new ActionListener() {
			
			public void actionPerformed(ActionEvent event) {
				
				System.exit(0);
			}
		};
		
		return actionListener;
	}
	
	public ActionListener getActionListenerForUserManualMenuItem() {
		
		ActionListener actionListener = new ActionListener() {
			
			public void actionPerformed(ActionEvent event) {
				
				browserService.openUserManualUrl();
			}
		};
		
		return actionListener;
	}
	
	public ActionListener getActionListenerForConfAppApiMenuItem() {
		
		ActionListener actionListener = new ActionListener() {
			
			public void actionPerformed(ActionEvent event) {
				
				browserService.openConfAppApiUrl();
			}
		};
		
		return actionListener;
	}
	
	public ActionListener getActionListenerForBagpipeApiMenuItem() {
		
		ActionListener actionListener = new ActionListener() {
			
			public void actionPerformed(ActionEvent event) {
				
				browserService.openBagpipeApiUrl();
			}
		};
		
		return actionListener;
	}
	
	public ActionListener getActionListenerForAboutMenuItem() {
		
		ActionListener actionListener = new ActionListener() {
			
			public void actionPerformed(ActionEvent event) {
				
				browserService.openAboutUrl();
			}
		};
		
		return actionListener;
	}
	
}
