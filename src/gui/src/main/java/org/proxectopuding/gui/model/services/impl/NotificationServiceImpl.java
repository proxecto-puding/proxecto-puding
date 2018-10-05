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
