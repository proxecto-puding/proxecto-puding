package org.proxectopuding.gui.view.swing;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeListener;

import org.proxectopuding.gui.controller.TabbedPaneController;

public class TabbedPaneView extends View {
	
	private StartConfigurationView startConfigurationView = 
			new StartConfigurationView();
	private SelectionConfigurationView selectionConfigurationView =
			new SelectionConfigurationView();
	private TuningConfigurationView tuningConfigurationView =
			new TuningConfigurationView();
	private SensitivityConfigurationView sensitivityConfigurationView =
			new SensitivityConfigurationView();
	private FingeringConfigurationView fingeringConfigurationView =
			new FingeringConfigurationView();
	
	private TabbedPaneController tabbedPaneController =
			new TabbedPaneController();
	
	public JTabbedPane getTabbedPane() {
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		// Start panel.
		String panelStartTitle = getStartPanelTitle();
		JPanel panelStart = getStartPanel();
		tabbedPane.addTab(panelStartTitle, null, panelStart, null);
		
		// Selection panel.
		String panelSelectTitle = getSelectionPanelTitle();
		JPanel panelSelect = getSelectionPanel();
		tabbedPane.addTab(panelSelectTitle, null, panelSelect, null);
		
		// Tuning panel.
		String panelTuningTitle = getTuningPanelTitle();
		JPanel panelTuning = getTuningPanel();
		tabbedPane.addTab(panelTuningTitle, null, panelTuning, null);
		
		// Sensitivity panel.
		String panelSensitTitle = getSensitivityPanelTitle();
		JPanel panelSensit = getSensitivityPanel();
		tabbedPane.addTab(panelSensitTitle, null, panelSensit, null);
		
		// Fingerings panel.
		String panelFingerTitle = getFingeringPanelTitle();
		JPanel panelFinger = getFingeringPanel();
		tabbedPane.addTab(panelFingerTitle, null, panelFinger, null);
		
		// Tab selection.
		ChangeListener changeListener = tabbedPaneController.
				getChangeListenerForTabedPane();
		tabbedPane.addChangeListener(changeListener);
		
		return tabbedPane;
	}
	
	private String getStartPanelTitle() {
		return tabbedPaneController.getTranslationForStartPanelTitle();
	}
	
	private JPanel getStartPanel() {
		return startConfigurationView.getStartPanel();
	}
	
	private String getSelectionPanelTitle() {
		return tabbedPaneController.getTranslationForSelectionPanelTitle();
	}
	
	private JPanel getSelectionPanel() {
		return selectionConfigurationView.getSelectionPanel();
	}
	
	private String getTuningPanelTitle() {
		return tabbedPaneController.getTranslationForTuningPanelTitle();
	}
	
	private JPanel getTuningPanel() {
		return tuningConfigurationView.getTuningPanel();
	}
	
	private String getSensitivityPanelTitle() {
		return tabbedPaneController.getTranslationForSensitivityPanelTitle();
	}
	
	private JPanel getSensitivityPanel() {
		return sensitivityConfigurationView.getSensitivityPanel();
	}
	
	private String getFingeringPanelTitle() {
		return tabbedPaneController.getTranslationForFingeringPanelTitle();
	}
	
	private JPanel getFingeringPanel() {
		return fingeringConfigurationView.getFingeringPanel();
	}

}
