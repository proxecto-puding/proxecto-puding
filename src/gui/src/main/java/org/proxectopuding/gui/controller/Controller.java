package org.proxectopuding.gui.controller;

import java.beans.PropertyChangeListener;

import org.proxectopuding.gui.model.services.I18nService;
import org.proxectopuding.gui.model.services.NotificationService;
import org.proxectopuding.gui.model.utils.Notification;

public class Controller {

	private final I18nService i18nService;
	private final NotificationService notificationService;
	
	public Controller(I18nService i18nService,
			NotificationService notificationService) {
		
		this.i18nService = i18nService;
		this.notificationService = notificationService;
	}
	
	public void subscribe(Notification notification,
			PropertyChangeListener propertyChangeListener) {
		
		notificationService.addNotificationListener(notification,
				propertyChangeListener);
	}
	
	protected I18nService getI18nService() {
		return i18nService;
	}
	
	protected NotificationService getNotificationService() {
		return notificationService;
	}
}
