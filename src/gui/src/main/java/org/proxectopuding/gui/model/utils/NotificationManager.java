package org.proxectopuding.gui.model.utils;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class NotificationManager {
	
	private Map<String, List<PropertyChangeListener>> propertyChangeListeners;
	
	public NotificationManager() {

		propertyChangeListeners = 
				new HashMap<String, List<PropertyChangeListener>>();
	};
	
	public void sendNotification(Object source, String propertyName,
			Object newValue) {
				
		if (propertyChangeListeners.containsKey(propertyName)) {
			List<PropertyChangeListener> listeners = 
					propertyChangeListeners.get(propertyName);
			PropertyChangeEvent event = new PropertyChangeEvent(
					source, propertyName, null, newValue);
			for (PropertyChangeListener listener : listeners) {
				listener.propertyChange(event);
			}
		}
	}
	
	public void addNotificationListener(String propertyName,
			PropertyChangeListener listener) {
		
		if (propertyChangeListeners.containsKey(propertyName)) {
			List<PropertyChangeListener> listeners = 
					propertyChangeListeners.get(propertyName);
			if (!listeners.contains(listener)) {
				listeners.add(listener);
			}
		} else {
			List<PropertyChangeListener> listeners =
					new ArrayList<PropertyChangeListener>();
			listeners.add(listener);
			propertyChangeListeners.put(propertyName, listeners);
		}
	}
	
	public void removeNotificationListener(String propertyName,
			PropertyChangeListener listener) {
		
		if (propertyChangeListeners.containsKey(propertyName)) {
			List<PropertyChangeListener> listeners = 
					propertyChangeListeners.get(propertyName);
			if (listeners.contains(listener)) {
				listeners.remove(listener);
			}
		}
	}

}
