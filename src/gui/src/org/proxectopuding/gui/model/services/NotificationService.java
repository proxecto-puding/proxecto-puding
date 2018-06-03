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
