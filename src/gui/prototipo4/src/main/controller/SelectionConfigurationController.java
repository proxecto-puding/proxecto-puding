package main.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Dictionary;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main.model.entities.BagpipeDevice;
import main.model.services.DeviceManagerService;
import main.model.services.I18nService;
import main.model.services.NotificationService;
import main.model.services.impl.DeviceManagerServiceImpl;
import main.model.services.impl.I18nServiceImpl;
import main.model.services.impl.NotificationServiceImpl;
import main.model.utils.Notification;

public class SelectionConfigurationController {
	
	private I18nService i18nService = new I18nServiceImpl();
	private DeviceManagerService deviceManagerService =
			new DeviceManagerServiceImpl();
	private NotificationService notificationService =
			new NotificationServiceImpl();
	
	private int oldVolume = -1;
	
	public String getTranslationForVolumeLabel() {
		return i18nService.getTranslation("selectionConfiguration.volume.label");
	}
	
	public int getVolume() {
		
		int volume = -1;
		
		BagpipeDevice selectedDevice = 
				deviceManagerService.getSelectedBagpipeDevice();
		if (selectedDevice != null) {
			String productId = selectedDevice.getProductId();
			volume = deviceManagerService.getVolume(productId);
		}
		
		return volume;
	}
	
	public ChangeListener getChangeListenerForVolumeSlider() {
		
		ChangeListener changeListener = new ChangeListener() {
			
			public void stateChanged(ChangeEvent event) {
				
				JSlider sliderVolume = (JSlider) event.getSource();
				int volume = sliderVolume.getValue();
				
				// Update the slider cursor label.
				@SuppressWarnings("unchecked")
				Dictionary<Integer,JLabel> labels =
						(Dictionary<Integer,JLabel>)
								sliderVolume.getLabelTable();
				if (oldVolume != -1 &&
						oldVolume != sliderVolume.getMinimum() &&
						oldVolume != sliderVolume.getMaximum()) {
					
					labels.remove(oldVolume);
				}
				labels.put(volume, new JLabel(Integer.toString(volume)));
				sliderVolume.setLabelTable(labels);
				oldVolume = volume;

				// Set the new device volume.
				if (!sliderVolume.getValueIsAdjusting()) {

					BagpipeDevice selectedDevice = 
							deviceManagerService.getSelectedBagpipeDevice();
					if (selectedDevice != null) {
						String productId = selectedDevice.getProductId();
						deviceManagerService.setVolume(productId, volume);
					}
				}
			}
		};
		
		return changeListener;
	}
	
	// TODO Test this because of the final modifier.
	public PropertyChangeListener getPropertyChangeListenerForVolumeSlider(
			final JSlider sliderVolume) {
		
		PropertyChangeListener propertyChangeListener = 
				new PropertyChangeListener() {
				
			public void propertyChange(PropertyChangeEvent event) {
				
				String propertyName = event.getPropertyName();
				if (Notification.CHANTER_SELECTED.toString() == propertyName) {
					String productId = (String) event.getNewValue();
					int volume = deviceManagerService.getVolume(productId);
					sliderVolume.setValue(volume);
				}
			}
			
		};
		
		notificationService.addNotificationListener(
				Notification.CHANTER_SELECTED, propertyChangeListener);
		
		return propertyChangeListener;
	}
	
	public String getTranslationForTuningToneLabel() {
		return i18nService.getTranslation("selectionConfiguration.tuningTone.label");
	}
	
	public String getTranslationForTuningOctaveLabel() {
		return i18nService.getTranslation("selectionConfiguration.tuningOctave.label");
	}
	
	public String getTranslationForSamplesLabel() {
		return i18nService.getTranslation("selectionConfiguration.samples.label");
	}
	
	public String getTranslationForFingeringTypesLabel() {
		return i18nService.getTranslation("selectionConfiguration.fingeringTypes.label");
	}
	
	public String getTranslationForFingeringTypesAbertoCheckBox() {
		return i18nService.getTranslation("selectionConfiguration.fingeringTypes.aberto.checkbox");
	}
	
	public String getTranslationForFingeringTypesPechadoCheckBox() {
		return i18nService.getTranslation("selectionConfiguration.fingeringTypes.pechado.checkbox");
	}
	
	public String getTranslationForFingeringTypesCustomCheckBox() {
		return i18nService.getTranslation("selectionConfiguration.fingeringTypes.custom.checkbox");
	}
	
	public String getTranslationForComplementsLabel() {
		return i18nService.getTranslation("selectionConfiguration.complements.label");
	}
	
	public String getTranslationForComplementsBagCheckBox() {
		return i18nService.getTranslation("selectionConfiguration.complements.bag.checkbox");
	}
	
	public String getTranslationForComplementsDronesLabel() {
		return i18nService.getTranslation("selectionConfiguration.complements.drones.label");
	}
	
	public String getTranslationForComplementsDronesBassDroneCheckBox() {
		return i18nService.getTranslation("selectionConfiguration.complements.drones.bassDrone.checkbox");
	}
	
	public String getTranslationForComplementsDronesTenorDroneCheckBox() {
		return i18nService.getTranslation("selectionConfiguration.complements.drones.tenorDrone.checkbox");
	}
	
	public String getTranslationForComplementsDronesHighDroneCheckBox() {
		return i18nService.getTranslation("selectionConfiguration.complements.drones.highDrone.checkbox");
	}
	
}
