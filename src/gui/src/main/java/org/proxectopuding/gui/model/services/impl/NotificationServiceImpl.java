package org.proxectopuding.gui.model.services.impl;

import java.beans.PropertyChangeListener;

import org.proxectopuding.gui.model.services.NotificationService;
import org.proxectopuding.gui.model.utils.Notification;
import org.proxectopuding.gui.model.utils.NotificationManager;

public class NotificationServiceImpl implements NotificationService {

	@Override
	public void sendNotification(Object source, Notification propertyName,
			Object newValue) {
		
		NotificationManager.sendNotification(
				source, propertyName.toString(), newValue);
	}

	@Override
	public void addNotificationListener(Notification propertyName,
			PropertyChangeListener listener) {
		
		NotificationManager.addNotificationListener(
				propertyName.toString(), listener);
	}

	@Override
	public void removeNotificationListener(Notification propertyName,
			PropertyChangeListener listener) {
		
		NotificationManager.removeNotificationListener(
				propertyName.toString(), listener);
	}

}
