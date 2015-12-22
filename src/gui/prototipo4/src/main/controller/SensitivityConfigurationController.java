package main.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main.model.services.DeviceManagerService;
import main.model.services.I18nService;
import main.model.services.NotificationService;
import main.model.services.impl.DeviceManagerServiceImpl;
import main.model.services.impl.I18nServiceImpl;
import main.model.services.impl.NotificationServiceImpl;
import main.model.utils.Notification;

public class SensitivityConfigurationController {
	
	private I18nService i18nService = new I18nServiceImpl();
	private DeviceManagerService deviceManagerService =
			new DeviceManagerServiceImpl();
	private NotificationService notificationService =
			new NotificationServiceImpl();
	
	public String getTranslationForBagPressureLabel() {
		return i18nService.getTranslation("sensitivityConfiguration.bagPressure.label");
	}
	
	public int getBagPressure() {
		String productId = deviceManagerService.
				getSelectedBagpipeDevice().getProductId();
		return deviceManagerService.getBagPressure(productId);
	}
	
	public ChangeListener getChangeListenerForBagPressureSlider() {
		
		ChangeListener changeListener = new ChangeListener() {
			
			public void stateChanged(ChangeEvent event) {
				
				JSlider sliderBagPressure = (JSlider) event.getSource();
				if (!sliderBagPressure.getValueIsAdjusting()) {
					String productId = deviceManagerService.
							getSelectedBagpipeDevice().getProductId();
					int bagPressure = sliderBagPressure.getValue();
					deviceManagerService.setBagPressure(productId, bagPressure);
				}
			}
		};
		
		return changeListener;
	}
	
	// TODO Test this because of the final modifier.
	public PropertyChangeListener
			getPropertyChangeListenerForBagPressureSlider(
					final JSlider sliderBagPressure) {
		
		PropertyChangeListener propertyChangeListener = 
				new PropertyChangeListener() {
				
			public void propertyChange(PropertyChangeEvent event) {
				
				String propertyName = event.getPropertyName();
				if (Notification.CHANTER_SELECTED.toString() == propertyName) {
					String productId = (String) event.getNewValue();
					int bagPressure = deviceManagerService.getBagPressure(productId);
					sliderBagPressure.setValue(bagPressure);
				}
			}
			
		};
		
		notificationService.addNotificationListener(
				Notification.CHANTER_SELECTED, propertyChangeListener);
		
		return propertyChangeListener;
	}
	
}
