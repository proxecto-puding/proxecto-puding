package main.model.utils;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class NotificationManager {
	
	private static HashMap<String, List<PropertyChangeListener>>
			propertyChangeListeners;
	
	static {
		propertyChangeListeners = 
				new HashMap<String, List<PropertyChangeListener>>();
	};
	
	private NotificationManager() {
		
	};
	
	public static void sendNotification(Object source, String propertyName,
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
	
	public static void addNotificationListener(String propertyName,
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
	
	public static void removeNotificationListener(String propertyName,
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
