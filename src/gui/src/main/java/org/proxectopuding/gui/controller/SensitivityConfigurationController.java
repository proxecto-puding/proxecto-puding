package org.proxectopuding.gui.controller;

import java.beans.PropertyChangeListener;

import org.proxectopuding.gui.model.entities.BagpipeDevice;
import org.proxectopuding.gui.model.services.DeviceManagerService;
import org.proxectopuding.gui.model.services.I18nService;
import org.proxectopuding.gui.model.services.NotificationService;
import org.proxectopuding.gui.model.utils.Notification;

import com.google.inject.Inject;

public class SensitivityConfigurationController {
	
	private final I18nService i18nService;
	private final DeviceManagerService deviceManagerService;
	private final NotificationService notificationService;
	
	@Inject
	public SensitivityConfigurationController(I18nService i18nService,
			DeviceManagerService deviceManagerService,
			NotificationService notificationService) {
		
		this.i18nService = i18nService;
		this.deviceManagerService = deviceManagerService;
		this.notificationService = notificationService;
	}
	
	public String getBagPressureLabel() {
		return i18nService.getTranslation("sensitivityConfiguration.bagPressure.label");
	}
	
	public int getBagPressure() {
		
		int bagPressure = -1;
		
		BagpipeDevice selectedDevice = 
				deviceManagerService.getSelectedBagpipeDevice();
		if (selectedDevice != null) {
			String productId = selectedDevice.getProductId();
			bagPressure = deviceManagerService.getBagPressure(productId);
		}
		
		return bagPressure;
	}
	
	public void onBagPressureSelected(int bagPressure) {
		
		BagpipeDevice selectedDevice = 
				deviceManagerService.getSelectedBagpipeDevice();
		if (selectedDevice != null) {
			String productId = selectedDevice.getProductId();
			deviceManagerService.setBagPressure(productId, bagPressure);
		}
	}
	
	public void subscribe(Notification notification,
			PropertyChangeListener propertyChangeListener) {
		
		notificationService.addNotificationListener(notification,
				propertyChangeListener);
	}
}
