package org.proxectopuding.gui.controller;

import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Set;

import org.proxectopuding.gui.model.entities.BagpipeDevice;
import org.proxectopuding.gui.model.services.ConfigurationApplicationService;
import org.proxectopuding.gui.model.services.DeviceManagerService;
import org.proxectopuding.gui.model.services.I18nService;
import org.proxectopuding.gui.model.services.NotificationService;
import org.proxectopuding.gui.model.utils.Notification;

import com.google.inject.Inject;

public class StartConfigurationController {
	
	private final I18nService i18nService;
	private final DeviceManagerService deviceManagerService;
	private final ConfigurationApplicationService confAppService;
	private final NotificationService notificationService;
	
	@Inject
	public StartConfigurationController(I18nService i18nService,
			DeviceManagerService deviceManagerService,
			ConfigurationApplicationService confAppService,
			NotificationService notificationService) {

		this.i18nService = i18nService;
		this.deviceManagerService = deviceManagerService;
		this.confAppService = confAppService;
		this.notificationService = notificationService;
	}

	public String getChanterSelectionLabel() {
		return i18nService.getTranslation("startConfiguration.chanterSelection.label");
	}
	
	public String getSearchLabel() {
		return i18nService.getTranslation("startConfiguration.search.label");
	}
	
	public String getReadingToneLabel() {
		return i18nService.getTranslation("startConfiguration.readingTone.label");
	}
	
	public String[] getChanters() {
		
		String[] chanters = {};
		
		List<String> list = deviceManagerService.getBagpipeDeviceIds();
		chanters = list.toArray(new String[list.size()]);
		
		return chanters;
	}
	
	public void onChanterSelected(String productId) {
		
		deviceManagerService.setSelectedBagpipeDevice(productId);
		deviceManagerService.findBagpipeConfigurations(productId);
		notificationService.sendNotification(this,
				Notification.CHANTER_SELECTED, productId);
	}
	
	public void onSearch() {
		
		Set<BagpipeDevice> devices =
				deviceManagerService.findBagpipeDevices();
		if (devices.size() > 0) {
			notificationService.sendNotification(this,
					Notification.CHANTER_FOUND, devices);
		}
	}
	
	public String[] getReadingTones() {
		
		String[] readingTones = {};
		
		List<String> list = confAppService.getReadingTones();
		readingTones = list.toArray(new String[list.size()]);
		
		return readingTones;
	}
	
	public void onReadingToneSelected(String readingTone) {
		
		confAppService.setReadingTone(readingTone);
		notificationService.sendNotification(this,
				Notification.READING_TONE_SELECTED, readingTone);
	}
	
	public void subscribe(Notification notification,
			PropertyChangeListener propertyChangeListener) {
		
		notificationService.addNotificationListener(notification,
				propertyChangeListener);
	}
}
