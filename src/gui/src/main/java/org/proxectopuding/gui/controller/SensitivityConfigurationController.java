package org.proxectopuding.gui.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Dictionary;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.proxectopuding.gui.model.entities.BagpipeDevice;
import org.proxectopuding.gui.model.services.DeviceManagerService;
import org.proxectopuding.gui.model.services.I18nService;
import org.proxectopuding.gui.model.services.NotificationService;
import org.proxectopuding.gui.model.utils.Notification;

import com.google.inject.Inject;

public class SensitivityConfigurationController {
	
	private final I18nService i18nService;
	private final DeviceManagerService deviceManagerService;
	private final NotificationService notificationService;
	
	private int oldBagPressure = -1;
	
	@Inject
	public SensitivityConfigurationController(I18nService i18nService,
			DeviceManagerService deviceManagerService,
			NotificationService notificationService) {
		
		this.i18nService = i18nService;
		this.deviceManagerService = deviceManagerService;
		this.notificationService = notificationService;
	}
	
	public String getTranslationForBagPressureLabel() {
		return i18nService.getTranslation("sensitivityConfiguration.bagPressure.label");
	}
	
	public int getBagPressure() {
		
		int bagPressure = -1;
		
		BagpipeDevice selectedDevice = 
				deviceManagerService.getSelectedBagpipeDevice();
		if (selectedDevice != null) {
			String productId = selectedDevice.getProductId();
			bagPressure = deviceManagerService.getBagPressure(productId);
		}
		
		return bagPressure;
	}
	
	public ChangeListener getChangeListenerForBagPressureSlider() {
		
		ChangeListener changeListener = new ChangeListener() {
			
			public void stateChanged(ChangeEvent event) {
				
				JSlider sliderBagPressure = (JSlider) event.getSource();
				int bagPressure = sliderBagPressure.getValue();
				
				// Update the slider cursor label.
				@SuppressWarnings("unchecked")
				Dictionary<Integer,JLabel> labels =
						(Dictionary<Integer,JLabel>)
								sliderBagPressure.getLabelTable();
				if (oldBagPressure != -1 &&
						oldBagPressure != sliderBagPressure.getMinimum() &&
						oldBagPressure != sliderBagPressure.getMaximum()) {
					
					labels.remove(oldBagPressure);
				}
				labels.put(bagPressure, new JLabel(Integer.toString(bagPressure)));
				sliderBagPressure.setLabelTable(labels);
				oldBagPressure = bagPressure;

				// Set the new device bag pressure.
				if (!sliderBagPressure.getValueIsAdjusting()) {

					BagpipeDevice selectedDevice = 
							deviceManagerService.getSelectedBagpipeDevice();
					if (selectedDevice != null) {
						String productId = selectedDevice.getProductId();
						deviceManagerService.
								setBagPressure(productId, bagPressure);
					}
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
					int bagPressure =
							deviceManagerService.getBagPressure(productId);
					sliderBagPressure.setValue(bagPressure);
				}
			}
			
		};
		
		notificationService.addNotificationListener(
				Notification.CHANTER_SELECTED, propertyChangeListener);
		
		return propertyChangeListener;
	}
	
}
