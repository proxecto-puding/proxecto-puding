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

package org.proxectopuding.gui.services.integration;

import static org.junit.Assert.assertEquals;

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
				
				// Then
				assertEquals(SOURCE, evt.getSource());
				assertEquals(Notification.CHANTER_SELECTED.name(),
						evt.getPropertyName());
				assertEquals(NEW_VALUE, evt.getNewValue());
			}
		};
	}
}
