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

package org.proxectopuding.gui.controller;

import org.proxectopuding.gui.model.entities.BagpipeConfigurationType;
import org.proxectopuding.gui.model.services.ConfigurationApplicationService;
import org.proxectopuding.gui.model.services.I18nService;
import org.proxectopuding.gui.model.services.NotificationService;

import com.google.inject.Inject;

public class TabbedPaneController extends Controller {
	
	private final ConfigurationApplicationService confAppService;
	
	@Inject
	public TabbedPaneController(I18nService i18nService,
			ConfigurationApplicationService confAppService,
			NotificationService notificationService) {
		
		super(i18nService, notificationService);
		
		this.confAppService = confAppService;
	}
	
	public String getStartPanelLabel() {
		return getI18nService().getTranslation("tabbedPane.startPanel.title");
	}
	
	public String getSelectionPanelLabel() {
		return getI18nService().getTranslation("tabbedPane.selectionPanel.title");
	}
	
	public String getTuningPanelLabel() {
		return getI18nService().getTranslation("tabbedPane.tuningPanel.title");
	}
	
	public String getSensitivityPanelLabel() {
		return getI18nService().getTranslation("tabbedPane.sensitivityPanel.title");
	}
	
	public String getFingeringPanelLabel() {
		return getI18nService().getTranslation("tabbedPane.fingeringPanel.title");
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
