package org.proxectopuding.gui.controller;

import org.proxectopuding.gui.model.entities.BagpipeConfigurationType;
import org.proxectopuding.gui.model.services.ConfigurationApplicationService;
import org.proxectopuding.gui.model.services.I18nService;

import com.google.inject.Inject;

public class TabbedPaneController {
	
	private final I18nService i18nService;
	private final ConfigurationApplicationService confAppService;
	
	@Inject
	public TabbedPaneController(I18nService i18nService,
			ConfigurationApplicationService confAppService) {
		
		this.i18nService = i18nService;
		this.confAppService = confAppService;
	}
	
	public String getStartPanelLabel() {
		return i18nService.getTranslation("tabbedPane.startPanel.title");
	}
	
	public String getSelectionPanelLabel() {
		return i18nService.getTranslation("tabbedPane.selectionPanel.title");
	}
	
	public String getTuningPanelLabel() {
		return i18nService.getTranslation("tabbedPane.tuningPanel.title");
	}
	
	public String getSensitivityPanelLabel() {
		return i18nService.getTranslation("tabbedPane.sensitivityPanel.title");
	}
	
	public String getFingeringPanelLabel() {
		return i18nService.getTranslation("tabbedPane.fingeringPanel.title");
	}

	public void onTabSelected(String title) {
		
		if (title != null) {
			BagpipeConfigurationType configurationType =
					getBagpipeConfigurationType(title);
			confAppService.setSelectedBagpipeConfigurationType(
					configurationType);
		}
	}
	
	private BagpipeConfigurationType getBagpipeConfigurationType(String title) {
		
		BagpipeConfigurationType configurationType = null;
		
		String start = getStartPanelLabel();
		String selection = getSelectionPanelLabel();
		String tuning = getTuningPanelLabel();
		String sensitivity = getSensitivityPanelLabel();
		String fingering = getFingeringPanelLabel();
		
		if (title.equalsIgnoreCase(start)) {
			configurationType = BagpipeConfigurationType.START;
		} else if (title.equalsIgnoreCase(selection)) {
			configurationType = BagpipeConfigurationType.SELECT;
		} else if (title.equalsIgnoreCase(tuning)) {
			configurationType = BagpipeConfigurationType.TUNING;
		} else if (title.equalsIgnoreCase(sensitivity)) {
			configurationType = BagpipeConfigurationType.SENSIT;
		} else if (title.equalsIgnoreCase(fingering)) {
			configurationType = BagpipeConfigurationType.FINGER;
		}
		
		return configurationType;
	}

}
