package org.proxectopuding.gui.services.integration;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.junit.Test;
import org.proxectopuding.gui.model.services.NotificationService;
import org.proxectopuding.gui.model.services.impl.NotificationServiceImpl;
import org.proxectopuding.gui.model.utils.Notification;
import org.proxectopuding.gui.model.utils.NotificationManager;

public class NotificationServiceIntegrationTest {
	
	private static final Object SOURCE = new Object();
	private static final Notification PROPERTY_NAME =
			Notification.CHANTER_SELECTED;
	private static final Object NEW_VALUE = new Object();
	private static final PropertyChangeListener LISTENER = 
			getPropertyChangeListener();
	
	private NotificationManager notificationManager = new NotificationManager();
	private NotificationService notificationService =
			new NotificationServiceImpl(notificationManager);

	@Test
	public void sendNotification() {
		
		// Given
		notificationService.addNotificationListener(PROPERTY_NAME, LISTENER);
		
		// When
		notificationService.sendNotification(SOURCE, PROPERTY_NAME, NEW_VALUE);
	}
	
	@Test
	public void addNotificationListener() {
		
		// When
		notificationService.addNotificationListener(PROPERTY_NAME, LISTENER);
	}

	@Test
	public void removeNotificationListener() {
		
		// Given
		notificationService.addNotificationListener(PROPERTY_NAME, LISTENER);
		
		// When
		notificationService.removeNotificationListener(PROPERTY_NAME, LISTENER);
	}
	
	private static PropertyChangeListener getPropertyChangeListener() {
		
		return new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				// Skip.
			}
		};
	}
}
