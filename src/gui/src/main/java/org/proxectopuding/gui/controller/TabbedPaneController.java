package org.proxectopuding.gui.controller;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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

	public ChangeListener getChangeListenerForTabedPane() {
		
		ChangeListener changeListener = new ChangeListener() {
			
			public void stateChanged(ChangeEvent event) {
				
				JTabbedPane tabbedPane = (JTabbedPane) event.getSource();
				int index = tabbedPane.getSelectedIndex();
				String title = tabbedPane.getTitleAt(index);
				
				if (title != null) {
					
					BagpipeConfigurationType configurationType =
							getBagpipeConfigurationType(title);
					confAppService.setSelectedBagpipeConfigurationType(
							configurationType);
				}
			}
		};
		
		return changeListener;
	}
	
	private BagpipeConfigurationType getBagpipeConfigurationType(String title) {
		
		BagpipeConfigurationType configurationType = null;
		
		String start = getTranslationForStartPanelTitle();
		String selection = getTranslationForSelectionPanelTitle();
		String tuning = getTranslationForTuningPanelTitle();
		String sensitivity = getTranslationForSensitivityPanelTitle();
		String fingering = getTranslationForFingeringPanelTitle();
		
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
