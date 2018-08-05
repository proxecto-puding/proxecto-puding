package org.proxectopuding.gui.controller;

import java.beans.PropertyChangeListener;

import org.proxectopuding.gui.model.services.NotificationService;
import org.proxectopuding.gui.model.utils.Notification;

public class Controller {

	private final NotificationService notificationService;
	
	public Controller(NotificationService notificationService) {
		
		this.notificationService = notificationService;
	}
	
	public void subscribe(Notification notification,
			PropertyChangeListener propertyChangeListener) {
		
		notificationService.addNotificationListener(notification,
				propertyChangeListener);
	}
	
	protected NotificationService getNotificationService() {
		return notificationService;
	}
}
