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

package org.proxectopuding.gui.model.services;

import java.beans.PropertyChangeListener;

import org.proxectopuding.gui.model.utils.Notification;

public interface NotificationService {
	
	/**
	 * Send a notification to all the listeners subscribed to a property change event.
	 * @param source The bean that fired the event.
	 * propertyName - The programmatic name of the property that was changed.
	 * @param propertyName The programmatic name of the property that was changed.
	 * @param newValue The new value of the property.
	 */
	public void sendNotification(Object source, Notification propertyName, Object newValue);
	
	/**
	 * Register a listener to be notified of any bound property updates.
	 * @param propertyName The programmatic name of the property that is going to be listened.
	 * @param listener A source bean to be notified of any bound property updates.
	 */
	public void addNotificationListener(Notification propertyName, PropertyChangeListener listener);

	/**
	 * Unregister a listener from being notified of any bound property updates.
	 * @param propertyName The programmatic name of the property it wants to be stop being notified about.
	 * @param listener The source bean to stop being notified of any bound property updates.
	 */
	public void removeNotificationListener(Notification propertyName, PropertyChangeListener listener);

}
