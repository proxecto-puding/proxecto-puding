package main.controller;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main.model.entities.BagpipeConfigurationType;
import main.model.services.ConfigurationApplicationService;
import main.model.services.I18nService;
import main.model.services.impl.ConfigurationApplicationServiceImpl;
import main.model.services.impl.I18nServiceImpl;

public class TabbedPaneController {
	
	private I18nService i18nService = new I18nServiceImpl();
	private ConfigurationApplicationService confAppService =
			new ConfigurationApplicationServiceImpl();
	
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
