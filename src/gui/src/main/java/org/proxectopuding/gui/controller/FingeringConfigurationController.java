package org.proxectopuding.gui.controller;

import java.beans.PropertyChangeListener;
import java.util.List;

import org.proxectopuding.gui.model.entities.BagpipeDevice;
import org.proxectopuding.gui.model.entities.FingeringOffset;
import org.proxectopuding.gui.model.services.ConfigurationApplicationService;
import org.proxectopuding.gui.model.services.DeviceManagerService;
import org.proxectopuding.gui.model.services.I18nService;
import org.proxectopuding.gui.model.services.NotificationService;
import org.proxectopuding.gui.model.utils.Notification;

import com.google.inject.Inject;

public class FingeringConfigurationController {
	
	private final I18nService i18nService;
	private final DeviceManagerService deviceManagerService;
	private final ConfigurationApplicationService confAppService;
	private final NotificationService notificationService;
	
	@Inject
	public FingeringConfigurationController(I18nService i18nService,
			DeviceManagerService deviceManagerService,
			ConfigurationApplicationService confAppService,
			NotificationService notificationService) {
		
		this.i18nService = i18nService;
		this.deviceManagerService = deviceManagerService;
		this.confAppService = confAppService;
		this.notificationService = notificationService;
	}

	public String getCustomFingeringNoteLabel() {
		return i18nService.getTranslation("fingeringConfiguration.customFingeringNote.label");
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
		notificationService.sendNotification(this,
				Notification.FINGERING_NOTE_SELECTED,
				customFingeringNote);
	}
	
	public String getCustomFingeringOctaveLabel() {
		return i18nService.getTranslation("fingeringConfiguration.customFingeringOctave.label");
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
		notificationService.sendNotification(this,
				Notification.FINGERING_OCTAVE_SELECTED,
				customFingeringOctave);
	}
	
	public String getCustomFingeringNumberLabel() {
		return i18nService.getTranslation("fingeringConfiguration.customFingeringNumber.label");
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
		notificationService.sendNotification(this,
				Notification.FINGERING_NUMBER_SELECTED,
				customFingeringNumber);
	}
	
	public String getCustomFingeringNewButtonLabel() {
		return i18nService.getTranslation("fingeringConfiguration.customFingeringNew.button");
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
			notificationService.sendNotification(this,
					Notification.FINGERING_NUMBER_ADDED,
					customFingeringNumber);
		}
	}
	
	public String getCustomFingeringRemoveButtonLabel() {
		return i18nService.getTranslation("fingeringConfiguration.customFingeringRemove.button");
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
			notificationService.sendNotification(this,
					Notification.FINGERING_NUMBER_REMOVED,
					customFingeringNumber);
		}
	}
	
	public String getSensorsLabel() {
		return i18nService.getTranslation("fingeringConfiguration.sensors.label");
	}
	
	public String getLeftHandSensorsLabel() {
		return i18nService.getTranslation("fingeringConfiguration.leftHandSensors.label");
	}
	
	public String getRightHandSensorsLabel() {
		return i18nService.getTranslation("fingeringConfiguration.rightHandSensors.label");
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
	
	public void subscribe(Notification notification,
			PropertyChangeListener propertyChangeListener) {
		
		notificationService.addNotificationListener(notification,
				propertyChangeListener);
	}
}
