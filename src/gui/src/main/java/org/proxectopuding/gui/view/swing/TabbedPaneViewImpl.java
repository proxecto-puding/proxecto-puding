package org.proxectopuding.gui.view.swing;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeListener;

import org.proxectopuding.gui.controller.TabbedPaneController;
import org.proxectopuding.gui.view.TabbedPaneView;

import com.google.inject.Inject;

public class TabbedPaneViewImpl extends ViewImpl implements TabbedPaneView {
	
	private final StartConfigurationViewImpl startConfigurationView;
	private final SelectionConfigurationViewImpl selectionConfigurationView;
	private final TuningConfigurationViewImpl tuningConfigurationView;
	private final SensitivityConfigurationViewImpl sensitivityConfigurationView;
	private final FingeringConfigurationViewImpl fingeringConfigurationView;
	
	private final TabbedPaneController tabbedPaneController;
	
	@Inject
	public TabbedPaneViewImpl(StartConfigurationViewImpl startConfigurationView,
			SelectionConfigurationViewImpl selectionConfigurationView,
			TuningConfigurationViewImpl tuningConfigurationView,
			SensitivityConfigurationViewImpl sensitivityConfigurationView,
			FingeringConfigurationViewImpl fingeringConfigurationView,
			TabbedPaneController tabbedPaneController) {
		
		this.startConfigurationView = startConfigurationView;
		this.selectionConfigurationView = selectionConfigurationView;
		this.tuningConfigurationView = tuningConfigurationView;
		this.sensitivityConfigurationView = sensitivityConfigurationView;
		this.fingeringConfigurationView = fingeringConfigurationView;
		this.tabbedPaneController = tabbedPaneController;
	}
	
	public JTabbedPane getTabbedPane() {
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		// Start panel
		String panelStartTitle = getStartPanelTitle();
		JPanel panelStart = getStartPanel();
		tabbedPane.addTab(panelStartTitle, null, panelStart, null);
		
		// Selection panel
		String panelSelectTitle = getSelectionPanelTitle();
		JPanel panelSelect = getSelectionPanel();
		tabbedPane.addTab(panelSelectTitle, null, panelSelect, null);
		
		// Tuning panel
		String panelTuningTitle = getTuningPanelTitle();
		JPanel panelTuning = getTuningPanel();
		tabbedPane.addTab(panelTuningTitle, null, panelTuning, null);
		
		// Sensitivity panel
		String panelSensitTitle = getSensitivityPanelTitle();
		JPanel panelSensit = getSensitivityPanel();
		tabbedPane.addTab(panelSensitTitle, null, panelSensit, null);
		
		// Fingerings panel
		String panelFingerTitle = getFingeringPanelTitle();
		JPanel panelFinger = getFingeringPanel();
		tabbedPane.addTab(panelFingerTitle, null, panelFinger, null);
		
		// Tab selection
		ChangeListener changeListener = event -> {
			int index = tabbedPane.getSelectedIndex();
			String title = tabbedPane.getTitleAt(index);
			tabbedPaneController.onTabSelected(title);
		};
		tabbedPane.addChangeListener(changeListener);
		
		return tabbedPane;
	}
	
	private String getStartPanelTitle() {
		return tabbedPaneController.getStartPanelLabel();
	}
	
	private JPanel getStartPanel() {
		return startConfigurationView.getStartPanel();
	}
	
	private String getSelectionPanelTitle() {
		return tabbedPaneController.getSelectionPanelLabel();
	}
	
	private JPanel getSelectionPanel() {
		return selectionConfigurationView.getSelectionPanel();
	}
	
	private String getTuningPanelTitle() {
		return tabbedPaneController.getTuningPanelLabel();
	}
	
	private JPanel getTuningPanel() {
		return tuningConfigurationView.getTuningPanel();
	}
	
	private String getSensitivityPanelTitle() {
		return tabbedPaneController.getSensitivityPanelLabel();
	}
	
	private JPanel getSensitivityPanel() {
		return sensitivityConfigurationView.getSensitivityPanel();
	}
	
	private String getFingeringPanelTitle() {
		return tabbedPaneController.getFingeringPanelLabel();
	}
	
	private JPanel getFingeringPanel() {
		return fingeringConfigurationView.getFingeringPanel();
	}

}
