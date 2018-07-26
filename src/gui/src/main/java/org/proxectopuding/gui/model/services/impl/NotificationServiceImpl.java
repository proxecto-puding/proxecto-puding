package org.proxectopuding.gui.model.services.impl;

import java.beans.PropertyChangeListener;

import org.proxectopuding.gui.model.services.NotificationService;
import org.proxectopuding.gui.model.utils.Notification;
import org.proxectopuding.gui.model.utils.NotificationManager;

import com.google.inject.Inject;

public class NotificationServiceImpl implements NotificationService {

	private final NotificationManager notificationManager;
	
	@Inject
	public NotificationServiceImpl(NotificationManager notificationManager) {
		
		this.notificationManager = notificationManager;
	}
	
	@Override
	public void sendNotification(Object source, Notification propertyName,
			Object newValue) {
		
		notificationManager.sendNotification(
				source, propertyName.toString(), newValue);
	}

	@Override
	public void addNotificationListener(Notification propertyName,
			PropertyChangeListener listener) {
		
		notificationManager.addNotificationListener(
				propertyName.toString(), listener);
	}

	@Override
	public void removeNotificationListener(Notification propertyName,
			PropertyChangeListener listener) {
		
		notificationManager.removeNotificationListener(
				propertyName.toString(), listener);
	}

}
