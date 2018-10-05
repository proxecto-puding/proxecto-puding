/*
 * Proxecto Puding
 * Copyright (C) 2011 Alejo Pacín <info@proxecto-puding.org>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.proxectopuding.gui.view.swing;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeListener;

import org.proxectopuding.gui.controller.TabbedPaneController;
import org.proxectopuding.gui.view.FingeringConfigurationView;
import org.proxectopuding.gui.view.SelectionConfigurationView;
import org.proxectopuding.gui.view.SensitivityConfigurationView;
import org.proxectopuding.gui.view.StartConfigurationView;
import org.proxectopuding.gui.view.TabbedPaneView;
import org.proxectopuding.gui.view.TuningConfigurationView;

import com.google.inject.Inject;

public class TabbedPaneViewImpl extends ViewImpl implements TabbedPaneView {
	
	private final StartConfigurationView startConfigurationView;
	private final SelectionConfigurationView selectionConfigurationView;
	private final TuningConfigurationView tuningConfigurationView;
	private final SensitivityConfigurationView sensitivityConfigurationView;
	private final FingeringConfigurationView fingeringConfigurationView;
	
	private final TabbedPaneController tabbedPaneController;
	
	@Inject
	public TabbedPaneViewImpl(StartConfigurationView startConfigurationView,
			SelectionConfigurationView selectionConfigurationView,
			TuningConfigurationView tuningConfigurationView,
			SensitivityConfigurationView sensitivityConfigurationView,
			FingeringConfigurationView fingeringConfigurationView,
			TabbedPaneController tabbedPaneController) {
		
		this.startConfigurationView = startConfigurationView;
		this.selectionConfigurationView = selectionConfigurationView;
		this.tuningConfigurationView = tuningConfigurationView;
		this.sensitivityConfigurationView = sensitivityConfigurationView;
		this.fingeringConfigurationView = fingeringConfigurationView;
		this.tabbedPaneController = tabbedPaneController;
	}
	
	@Override
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
