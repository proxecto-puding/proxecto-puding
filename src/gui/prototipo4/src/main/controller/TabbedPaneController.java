package main.controller;

import main.model.services.I18nService;
import main.model.services.impl.I18nServiceImpl;

public class TabbedPaneController {
	
	private I18nService i18nService = new I18nServiceImpl();
	
	public String getTranslationForStartPanelTitle() {
		return i18nService.getTranslation("tabbedPane.startPanel.title");
	}
	
	public String getTranslationForSelectionPanelTitle() {
		return i18nService.getTranslation("tabbedPane.selectionPanel.title");
	}
	
	public String getTranslationForTuningPanelTitle() {
		return i18nService.getTranslation("tabbedPane.tuningPanel.title");
	}
	
	public String getTranslationForSensitivityPanelTitle() {
		return i18nService.getTranslation("tabbedPane.sensitivityPanel.title");
	}
	
	public String getTranslationForFingeringPanelTitle() {
		return i18nService.getTranslation("tabbedPane.fingeringPanel.title");
	}

}
