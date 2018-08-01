package org.proxectopuding.gui.model.services.impl;

import java.beans.PropertyChangeListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.proxectopuding.gui.model.services.NotificationService;
import org.proxectopuding.gui.model.utils.Notification;
import org.proxectopuding.gui.model.utils.NotificationManager;

import com.google.inject.Inject;

public class NotificationServiceImpl implements NotificationService {
	
	private static final Logger LOGGER = Logger.getLogger(NotificationServiceImpl.class.getName());

	private final NotificationManager notificationManager;
	
	@Inject
	public NotificationServiceImpl(NotificationManager notificationManager) {
		
		this.notificationManager = notificationManager;
	}
	
	@Override
	public void sendNotification(Object source, Notification propertyName,
			Object newValue) {
		
		LOGGER.log(Level.INFO, "Sending notification: {0} {1} {2}", new Object[]{source, propertyName, newValue});
		notificationManager.sendNotification(
				source, propertyName.toString(), newValue);
	}

	@Override
	public void addNotificationListener(Notification propertyName,
			PropertyChangeListener listener) {
		
		LOGGER.log(Level.INFO, "Adding notification listener: {0} {1}", new Object[]{propertyName, listener});
		notificationManager.addNotificationListener(
				propertyName.toString(), listener);
	}

	@Override
	public void removeNotificationListener(Notification propertyName,
			PropertyChangeListener listener) {
		
		LOGGER.log(Level.INFO, "Removing notification listener: {0} {1}", new Object[]{propertyName, listener});
		notificationManager.removeNotificationListener(
				propertyName.toString(), listener);
	}

}
