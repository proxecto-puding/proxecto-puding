package org.proxectopuding.gui.services.unit;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.junit.Before;
import org.junit.Test;
import org.proxectopuding.gui.model.services.NotificationService;
import org.proxectopuding.gui.model.services.impl.NotificationServiceImpl;
import org.proxectopuding.gui.model.utils.Notification;
import org.proxectopuding.gui.model.utils.NotificationManager;

public class NotificationServiceUnitTest {
	
	private static final Object SOURCE = new Object();
	private static final Notification PROPERTY_NAME =
			Notification.CHANTER_SELECTED;
	private static final Object NEW_VALUE = new Object();
	private static final PropertyChangeListener LISTENER = 
			getPropertyChangeListener();
	
	private NotificationManager notificationManager =
			mock(NotificationManager.class);
	private NotificationService notificationService =
			new NotificationServiceImpl(notificationManager);

	@Before
	public void before() {
		
		reset(notificationManager);
	}
	
	@Test
	public void sendNotification() {
		
		// Given
		doNothing().when(notificationManager)
				.sendNotification(SOURCE, PROPERTY_NAME.name(), NEW_VALUE);
		
		// When
		notificationService.sendNotification(SOURCE, PROPERTY_NAME, NEW_VALUE);
		
		// Then
		verify(notificationManager, times(1))
				.sendNotification(SOURCE, PROPERTY_NAME.name(), NEW_VALUE);
	}
	
	@Test
	public void addNotificationListener() {
		
		// Given
		doNothing().when(notificationManager)
				.addNotificationListener(PROPERTY_NAME.name(), LISTENER);
		
		// When
		notificationService.addNotificationListener(PROPERTY_NAME, LISTENER);
		
		// Then
		verify(notificationManager, times(1))
				.addNotificationListener(PROPERTY_NAME.name(), LISTENER);
	}

	@Test
	public void removeNotificationListener() {
		
		// Given
		doNothing().when(notificationManager)
				.removeNotificationListener(PROPERTY_NAME.name(), LISTENER);
		
		// When
		notificationService.removeNotificationListener(PROPERTY_NAME, LISTENER);
		
		// Then
		verify(notificationManager, times(1))
				.removeNotificationListener(PROPERTY_NAME.name(), LISTENER);
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
