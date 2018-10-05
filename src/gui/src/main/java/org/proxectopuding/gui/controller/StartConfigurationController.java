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

public class StartConfigurationController extends Controller {
	
	private final DeviceManagerService deviceManagerService;
	private final ConfigurationApplicationService confAppService;
	
	@Inject
	public StartConfigurationController(I18nService i18nService,
			DeviceManagerService deviceManagerService,
			ConfigurationApplicationService confAppService,
			NotificationService notificationService) {

		super(i18nService, notificationService);
		
		this.deviceManagerService = deviceManagerService;
		this.confAppService = confAppService;
	}

	public String getChanterSelectionLabel() {
		return getI18nService().getTranslation("startConfiguration.chanterSelection.label");
	}
	
	public String getSearchLabel() {
		return getI18nService().getTranslation("startConfiguration.search.label");
	}
	
	public String getReadingToneLabel() {
		return getI18nService().getTranslation("startConfiguration.readingTone.label");
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
		getNotificationService().sendNotification(this,
				Notification.CHANTER_SELECTED, productId);
	}
	
	public void onSearch() {
		
		Set<BagpipeDevice> devices =
				deviceManagerService.findBagpipeDevices();
		if (devices.size() > 0) {
			getNotificationService().sendNotification(this,
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
		getNotificationService().sendNotification(this,
				Notification.READING_TONE_SELECTED, readingTone);
	}
	
	public void subscribe(Notification notification,
			PropertyChangeListener propertyChangeListener) {
		
		getNotificationService().addNotificationListener(notification,
				propertyChangeListener);
	}
}
