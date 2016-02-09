package main.controller;

import main.model.services.I18nService;
import main.model.services.impl.I18nServiceImpl;

public class ContentPanelController {
	
	private I18nService i18nService = new I18nServiceImpl();
	
	public String getTranslationForApplyButtonText() {
		return i18nService.getTranslation("contentPanel.apply.button");
	}
	
	public String getTranslationForUndoButtonText() {
		return i18nService.getTranslation("contentPanel.undo.button");
	}
	
	public String getTranslationForDefaultButtonText() {
		return i18nService.getTranslation("contentPanel.default.button");
	}

}
