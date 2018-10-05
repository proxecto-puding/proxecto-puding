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

import java.util.List;

import org.proxectopuding.gui.model.entities.BagpipeDevice;
import org.proxectopuding.gui.model.entities.FingeringOffset;
import org.proxectopuding.gui.model.services.ConfigurationApplicationService;
import org.proxectopuding.gui.model.services.DeviceManagerService;
import org.proxectopuding.gui.model.services.I18nService;
import org.proxectopuding.gui.model.services.NotificationService;
import org.proxectopuding.gui.model.utils.Notification;

import com.google.inject.Inject;

public class FingeringConfigurationController extends Controller {
	
	private final DeviceManagerService deviceManagerService;
	private final ConfigurationApplicationService confAppService;
	
	@Inject
	public FingeringConfigurationController(I18nService i18nService,
			DeviceManagerService deviceManagerService,
			ConfigurationApplicationService confAppService,
			NotificationService notificationService) {
		
		super(i18nService, notificationService);
		
		this.deviceManagerService = deviceManagerService;
		this.confAppService = confAppService;
	}

	public String getCustomFingeringNoteLabel() {
		return getI18nService().getTranslation("fingeringConfiguration.customFingeringNote.label");
	}
	
	public String[] getCustomFingeringNotes() {
		
		String[] fingeringNotes = {};
		
		List<String> list = confAppService.getCustomFingeringNotes();
		fingeringNotes = list.toArray(new String[list.size()]);
		
		return fingeringNotes;
	}
	
	public String getCustomFingeringNote() {
		return confAppService.getCustomFingeringNote();
	}
	
	public void resetCustomFingeringNotes() {
		confAppService.resetCustomFingeringNotes();;
	}
	
	public void onCustomFingeringNoteSelected(String customFingeringNote) {
		
		confAppService.setCustomFingeringNote(customFingeringNote);
		getNotificationService().sendNotification(this,
				Notification.FINGERING_NOTE_SELECTED,
				customFingeringNote);
	}
	
	public String getCustomFingeringOctaveLabel() {
		return getI18nService().getTranslation("fingeringConfiguration.customFingeringOctave.label");
	}
	
	public Integer[] getCustomFingeringOctaves() {
		
		Integer[] customFingeringOctaves = {};
		
		List<Integer> list = confAppService.getCustomFingeringOctaves();
		customFingeringOctaves = list.toArray(new Integer[list.size()]);
		
		return customFingeringOctaves;
	}
	
	public int getCustomFingeringOctave() {
		return confAppService.getCustomFingeringOctave();
	}
	
	public void onCustomFingeringOctaveSelected(Integer customFingeringOctave) {
		
		confAppService.setCustomFingeringOctave(customFingeringOctave);
		getNotificationService().sendNotification(this,
				Notification.FINGERING_OCTAVE_SELECTED,
				customFingeringOctave);
	}
	
	public String getCustomFingeringNumberLabel() {
		return getI18nService().getTranslation("fingeringConfiguration.customFingeringNumber.label");
	}
	
	public Integer[] getCustomFingeringNumbers() {
		
		Integer[] customFingeringNumbers = {};
		
		BagpipeDevice device = deviceManagerService.getSelectedBagpipeDevice();
		
		if (device != null) {
			
			String productId = device.getProductId();
			List<FingeringOffset> fingerings =
					deviceManagerService.getFingerings(productId);
			List<Integer> list = 
					confAppService.getCustomFingeringNumbers(fingerings);
			customFingeringNumbers = list.toArray(new Integer[list.size()]);
		}
		
		return customFingeringNumbers;
	}
	
	public int getCustomFingeringNumber() {
		return confAppService.getCustomFingeringNumber();
	}
	
	public void onCustomFingeringNumberSelected(Integer customFingeringNumber) {
		
		confAppService.setCustomFingeringNumber(customFingeringNumber);
		getNotificationService().sendNotification(this,
				Notification.FINGERING_NUMBER_SELECTED,
				customFingeringNumber);
	}
	
	public String getCustomFingeringNewButtonLabel() {
		return getI18nService().getTranslation("fingeringConfiguration.customFingeringNew.button");
	}
	
	public void onAddCustomFingering() {
		
		BagpipeDevice device = deviceManagerService.getSelectedBagpipeDevice();
		
		if (device != null) {
			
			int customFingeringNumber =
					confAppService.addCustomFingeringNumber();
			FingeringOffset customFingering = confAppService.
					getCustomFingering(customFingeringNumber);
			String productId = device.getProductId();
			List<FingeringOffset> fingerings = 
					deviceManagerService.getFingerings(productId);
			fingerings.add(customFingering);
			deviceManagerService.setFingerings(productId, fingerings);
			getNotificationService().sendNotification(this,
					Notification.FINGERING_NUMBER_ADDED,
					customFingeringNumber);
		}
	}
	
	public String getCustomFingeringRemoveButtonLabel() {
		return getI18nService().getTranslation("fingeringConfiguration.customFingeringRemove.button");
	}
	
	public void onRemoveCustomFingering() {
		
		BagpipeDevice device = deviceManagerService.getSelectedBagpipeDevice();
		
		if (device != null) {
			
			int customFingeringNumber =
					confAppService.getCustomFingeringNumber();
			FingeringOffset customFingering = confAppService.
					getCustomFingering(customFingeringNumber);
			String productId = device.getProductId();
			List<FingeringOffset> fingerings = 
					deviceManagerService.getFingerings(productId);
			fingerings.remove(customFingering);
			deviceManagerService.setFingerings(productId, fingerings);
			confAppService.removeCustomFingeringNumber(
					customFingeringNumber);
			getNotificationService().sendNotification(this,
					Notification.FINGERING_NUMBER_REMOVED,
					customFingeringNumber);
		}
	}
	
	public String getSensorsLabel() {
		return getI18nService().getTranslation("fingeringConfiguration.sensors.label");
	}
	
	public String getLeftHandSensorsLabel() {
		return getI18nService().getTranslation("fingeringConfiguration.leftHandSensors.label");
	}
	
	public String getRightHandSensorsLabel() {
		return getI18nService().getTranslation("fingeringConfiguration.rightHandSensors.label");
	}
	
	public boolean isCustomFingeringSensorSelected(int sensor) {
		
		return confAppService.isCustomFingeringSensorSelected(sensor);
	}
	
	public void onCustomFingeringSensorSelected(int sensor, boolean selected) {
		
		BagpipeDevice device = deviceManagerService.getSelectedBagpipeDevice();
		
		if (device != null) {
			
			int customFingeringNumber =
					confAppService.getCustomFingeringNumber();
			FingeringOffset customFingering = confAppService
					.getCustomFingering(customFingeringNumber);
			String productId = device.getProductId();
			List<FingeringOffset> fingerings = 
					deviceManagerService.getFingerings(productId);
			fingerings.remove(customFingering);
			
			customFingering = confAppService
					.setCustomFingeringSensor(sensor, selected);
			
			fingerings.add(customFingering);
			deviceManagerService.setFingerings(productId, fingerings);
		}
	}
}
