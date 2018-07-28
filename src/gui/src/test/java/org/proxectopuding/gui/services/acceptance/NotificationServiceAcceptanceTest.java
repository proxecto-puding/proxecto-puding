package org.proxectopuding.gui.services.acceptance;

import static org.junit.Assert.assertEquals;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.junit.Test;
import org.proxectopuding.gui.model.services.NotificationService;
import org.proxectopuding.gui.model.services.impl.NotificationServiceImpl;
import org.proxectopuding.gui.model.utils.Notification;
import org.proxectopuding.gui.model.utils.NotificationManager;

public class NotificationServiceAcceptanceTest {
	
	private static final Object SOURCE = new Object();
	private static final Notification PROPERTY_NAME =
			Notification.CHANTER_SELECTED;
	private static final Object NEW_VALUE = new Object();
	private static final PropertyChangeListener LISTENER = 
			getPropertyChangeListener();
	
	private NotificationManager notificationManager = new NotificationManager();
	private NotificationService notificationService =
			new NotificationServiceImpl(notificationManager);

	/**
	 * Feature: user performs a notifiable action
	 */
	
	/**
	 * Scenario: user selects a device
	 * Given there are other actors that need to be notified on device selected
	 * When user selects a device
	 * Then the required actors are notified
	 */
	@Test
	public void selectDevice() {
		
		// Given
		notificationService.addNotificationListener(PROPERTY_NAME, LISTENER);
		
		// When
		notificationService.sendNotification(SOURCE, PROPERTY_NAME, NEW_VALUE);
	}
	
	private static PropertyChangeListener getPropertyChangeListener() {
		
		return new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				
				// Then
				assertEquals(SOURCE, evt.getSource());
				assertEquals(Notification.CHANTER_SELECTED.name(),
						evt.getPropertyName());
				assertEquals(NEW_VALUE, evt.getNewValue());
			}
		};
	}
}
