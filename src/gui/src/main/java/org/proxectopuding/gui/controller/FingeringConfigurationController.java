package org.proxectopuding.gui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

import org.proxectopuding.gui.model.entities.BagpipeDevice;
import org.proxectopuding.gui.model.entities.FingeringOffset;
import org.proxectopuding.gui.model.services.ConfigurationApplicationService;
import org.proxectopuding.gui.model.services.DeviceManagerService;
import org.proxectopuding.gui.model.services.I18nService;
import org.proxectopuding.gui.model.services.NotificationService;
import org.proxectopuding.gui.model.services.impl.ConfigurationApplicationServiceImpl;
import org.proxectopuding.gui.model.services.impl.DeviceManagerServiceImpl;
import org.proxectopuding.gui.model.services.impl.I18nServiceImpl;
import org.proxectopuding.gui.model.services.impl.NotificationServiceImpl;
import org.proxectopuding.gui.model.utils.Notification;

public class FingeringConfigurationController {
	
	private I18nService i18nService = new I18nServiceImpl();
	private DeviceManagerService deviceManagerService =
			new DeviceManagerServiceImpl();
	private ConfigurationApplicationService confAppService =
			new ConfigurationApplicationServiceImpl();
	private NotificationService notificationService =
			new NotificationServiceImpl();

	public String getTranslationForCustomFingeringNoteLabel() {
		return i18nService.getTranslation("fingeringConfiguration.customFingeringNote.label");
	}
	
	public String[] getCustomFingeringNotes() {
		
		String[] fingeringNotes = {};
		
		List<String> list = confAppService.getCustomFingeringNotes();
		fingeringNotes = list.toArray(new String[list.size()]);
		
		return fingeringNotes;
	}
	
	public String getCustomFingeringNote() {
		return confAppService.getCustomFingeringNote();
	}
	
	public ActionListener getActionListenerForCustomFingeringNoteComboBox() {
		
		ActionListener actionListener = new ActionListener() {
			
			public void actionPerformed(ActionEvent event) {
				
				@SuppressWarnings("unchecked")
				JComboBox<String> comboBoxCustomFingeringNote =
						(JComboBox<String>) event.getSource();
				String customFingeringNote = 
						(String) comboBoxCustomFingeringNote.getSelectedItem();
				confAppService.setCustomFingeringNote(customFingeringNote);
				notificationService.sendNotification(comboBoxCustomFingeringNote,
						Notification.FINGERING_NOTE_SELECTED,
						customFingeringNote);
			}
		};
		
		return actionListener;
	}
	
	// TODO Test this because of the final modifier.
	public PropertyChangeListener
			getPropertyChangeListenerForCustomFingeringNoteComboBox(
					final JComboBox<String> comboBoxCustomFingeringNote) {
		
		PropertyChangeListener propertyChangeListener = 
				new PropertyChangeListener() {
				
			public void propertyChange(PropertyChangeEvent event) {
				
				String propertyName = event.getPropertyName();
				if (Notification.READING_TONE_SELECTED.toString()
						== propertyName) {
					
					confAppService.resetCustomFingeringNotes();
					String[] customFingeringNotes = getCustomFingeringNotes();
					ComboBoxModel<String> customFingeringNoteModel =
							new DefaultComboBoxModel<String>(customFingeringNotes);
					comboBoxCustomFingeringNote.setModel(customFingeringNoteModel);
					String customFingeringNote = getCustomFingeringNote();
					comboBoxCustomFingeringNote.setSelectedItem(customFingeringNote);
				}
			}
		};
		
		notificationService.addNotificationListener(
				Notification.READING_TONE_SELECTED, propertyChangeListener);
		
		return propertyChangeListener;
	}
	
	public String getTranslationForCustomFingeringOctaveLabel() {
		return i18nService.getTranslation("fingeringConfiguration.customFingeringOctave.label");
	}
	
	public Integer[] getCustomFingeringOctaves() {
		
		Integer[] customFingeringOctaves = {};
		
		List<Integer> list = confAppService.getCustomFingeringOctaves();
		customFingeringOctaves = list.toArray(new Integer[list.size()]);
		
		return customFingeringOctaves;
	}
	
	public int getCustomFingeringOctave() {
		return confAppService.getCustomFingeringOctave();
	}
	
	public ActionListener getActionListenerForCustomFingeringOctaveComboBox() {
		
		ActionListener actionListener = new ActionListener() {
			
			public void actionPerformed(ActionEvent event) {
				
				@SuppressWarnings("unchecked")
				JComboBox<Integer> comboBoxCustomFingeringOctave =
						(JComboBox<Integer>) event.getSource();
				Integer customFingeringOctave = (Integer)
						comboBoxCustomFingeringOctave.getSelectedItem();
				confAppService.setCustomFingeringOctave(customFingeringOctave);
				notificationService.sendNotification(
						comboBoxCustomFingeringOctave,
						Notification.FINGERING_OCTAVE_SELECTED,
						customFingeringOctave);
			}
		};
		
		return actionListener;
	}
	
	public String getTranslationForCustomFingeringNumberLabel() {
		return i18nService.getTranslation("fingeringConfiguration.customFingeringNumber.label");
	}
	
	public Integer[] getCustomFingeringNumbers() {
		
		Integer[] customFingeringNumbers = {};
		
		BagpipeDevice device = deviceManagerService.getSelectedBagpipeDevice();
		
		if (device != null) {
			
			String productId = device.getProductId();
			List<FingeringOffset> fingerings =
					deviceManagerService.getFingerings(productId);
			List<Integer> list = 
					confAppService.getCustomFingeringNumbers(fingerings);
			customFingeringNumbers = list.toArray(new Integer[list.size()]);
		}
		
		return customFingeringNumbers;
	}
	
	public int getCustomFingeringNumber() {
		return confAppService.getCustomFingeringNumber();
	}
	
	public ActionListener getActionListenerForCustomFingeringNumberComboBox() {
		
		ActionListener actionListener = new ActionListener() {
			
			public void actionPerformed(ActionEvent event) {
				
				@SuppressWarnings("unchecked")
				JComboBox<Integer> comboBoxCustomFingeringNumber =
						(JComboBox<Integer>) event.getSource();
				Integer customFingeringNumber = (Integer)
						comboBoxCustomFingeringNumber.getSelectedItem();
				confAppService.setCustomFingeringNumber(customFingeringNumber);
				notificationService.sendNotification(
						comboBoxCustomFingeringNumber,
						Notification.FINGERING_NUMBER_SELECTED,
						customFingeringNumber);
			}
		};		
		
		return actionListener;
	}
	
	// TODO Test this because of the final modifier.
	public PropertyChangeListener
			getPropertyChangeListenerForCustomFingeringNumberComboBox(
					final JComboBox<Integer> comboBoxCustomFingeringNumber) {
		
		PropertyChangeListener propertyChangeListener = 
				new PropertyChangeListener() {
				
			public void propertyChange(PropertyChangeEvent event) {
				
				String propertyName = event.getPropertyName();
				if ((Notification.CHANTER_SELECTED.toString()
						== propertyName) ||
					(Notification.FINGERING_NOTE_SELECTED.toString()
						== propertyName) ||
					(Notification.FINGERING_OCTAVE_SELECTED.toString()
						== propertyName) ||
					(Notification.FINGERING_NUMBER_ADDED.toString()
						== propertyName) ||
					(Notification.FINGERING_NUMBER_REMOVED.toString()
						== propertyName)) {
					
					Integer[] customFingeringNumbers =
							getCustomFingeringNumbers();
					ComboBoxModel<Integer> customFingeringNumberModel =
							new DefaultComboBoxModel<Integer>(
									customFingeringNumbers);
					comboBoxCustomFingeringNumber.setModel(
							customFingeringNumberModel);
					Integer customFingeringNumber = getCustomFingeringNumber();
					comboBoxCustomFingeringNumber.setSelectedItem(
							customFingeringNumber);	
				}
			}
		};
		
		notificationService.addNotificationListener(
				Notification.CHANTER_SELECTED, propertyChangeListener);
		notificationService.addNotificationListener(
				Notification.FINGERING_NOTE_SELECTED, propertyChangeListener);
		notificationService.addNotificationListener(
				Notification.FINGERING_OCTAVE_SELECTED, propertyChangeListener);
		notificationService.addNotificationListener(
				Notification.FINGERING_NUMBER_ADDED, propertyChangeListener);
		notificationService.addNotificationListener(
				Notification.FINGERING_NUMBER_REMOVED, propertyChangeListener);
		
		return propertyChangeListener;
	}
	
	public String getTranslationForCustomFingeringNewButtonText() {
		return i18nService.getTranslation("fingeringConfiguration.customFingeringNew.button");
	}
	
	public ActionListener getActionListenerForCustomFingeringNewButton() {
		
		ActionListener actionListener = new ActionListener() {
			
			public void actionPerformed(ActionEvent event) {
				
				BagpipeDevice device = deviceManagerService.
						getSelectedBagpipeDevice();
				
				if (device != null) {
					
					JButton btnCustomFingeringNew =
							(JButton) event.getSource();
					int customFingeringNumber =
							confAppService.addCustomFingeringNumber();
					FingeringOffset customFingering = confAppService.
							getCustomFingering(customFingeringNumber);
					String productId = device.getProductId();
					List<FingeringOffset> fingerings = 
							deviceManagerService.getFingerings(productId);
					fingerings.add(customFingering);
					deviceManagerService.setFingerings(productId, fingerings);
					notificationService.sendNotification(
							btnCustomFingeringNew,
							Notification.FINGERING_NUMBER_ADDED,
							customFingeringNumber);
				}
			}
		};
		
		return actionListener;
	}
	
	public String getTranslationForCustomFingeringRemoveButton() {
		return i18nService.getTranslation("fingeringConfiguration.customFingeringRemove.button");
	}
	
	public ActionListener getActionListenerForCustomFingeringRemoveButton() {
		
		ActionListener actionListener = new ActionListener() {
			
			public void actionPerformed(ActionEvent event) {
				
				BagpipeDevice device = deviceManagerService.
						getSelectedBagpipeDevice();
				
				if (device != null) {
					
					JButton btnCustomFingeringRemove =
							(JButton) event.getSource();
					int customFingeringNumber =
							confAppService.getCustomFingeringNumber();
					FingeringOffset customFingering = confAppService.
							getCustomFingering(customFingeringNumber);
					String productId = device.getProductId();
					List<FingeringOffset> fingerings = 
							deviceManagerService.getFingerings(productId);
					fingerings.remove(customFingering);
					deviceManagerService.setFingerings(productId, fingerings);
					confAppService.removeCustomFingeringNumber(
							customFingeringNumber);
					notificationService.sendNotification(
							btnCustomFingeringRemove,
							Notification.FINGERING_NUMBER_REMOVED,
							customFingeringNumber);
				}
			}
		};
		
		return actionListener;
	}
	
	public String getTranslationForChanterImageLabel() {
		return i18nService.getTranslation("fingeringConfiguration.chanterImage.label");
	}
	
	public String getTranslationForSensorsLabel() {
		return i18nService.getTranslation("fingeringConfiguration.sensors.label");
	}
	
	public String getTranslationForLeftHandSensorsLabel() {
		return i18nService.getTranslation("fingeringConfiguration.leftHandSensors.label");
	}
	
	public String getTranslationForRightHandSensorsLabel() {
		return i18nService.getTranslation("fingeringConfiguration.rightHandSensors.label");
	}
	
	public ActionListener getActionListenerForCustomFingeringSensorCheckBox(
			final int sensor) {
		
		ActionListener actionListener = new ActionListener() {
			
			public void actionPerformed(ActionEvent event) {
				
				BagpipeDevice device = deviceManagerService.
						getSelectedBagpipeDevice();
				
				if (device != null) {
					
					JCheckBox checkBoxSensor =
							(JCheckBox) event.getSource();
					
					int customFingeringNumber =
							confAppService.getCustomFingeringNumber();
					FingeringOffset customFingering = confAppService.
							getCustomFingering(customFingeringNumber);
					String productId = device.getProductId();
					List<FingeringOffset> fingerings = 
							deviceManagerService.getFingerings(productId);
					fingerings.remove(customFingering);
					
					boolean isSelected = checkBoxSensor.isSelected();
					customFingering = confAppService.
							setCustomFingeringSensor(sensor, isSelected);
					
					fingerings.add(customFingering);
					deviceManagerService.setFingerings(productId, fingerings);
				}
			}
		};
		
		return actionListener;
	}
	
	// TODO Test this because of the final modifier.
	public PropertyChangeListener
			getPropertyChangeListenerForCustomFingeringSensorCheckBox(
					final int sensor, final JCheckBox checkBoxSensor) {
		
		PropertyChangeListener propertyChangeListener = 
				new PropertyChangeListener() {
				
			public void propertyChange(PropertyChangeEvent event) {
				
				String propertyName = event.getPropertyName();
				if ((Notification.CHANTER_SELECTED.toString()
						== propertyName) ||
					(Notification.FINGERING_NOTE_SELECTED.toString()
						== propertyName) ||
					(Notification.FINGERING_OCTAVE_SELECTED.toString()
						== propertyName) ||
					(Notification.FINGERING_NUMBER_ADDED.toString()
						== propertyName) ||
					(Notification.FINGERING_NUMBER_REMOVED.toString()
						== propertyName)) {
					
					boolean isSelected = confAppService.
							isCustomFingeringSensorSelected(sensor);
					checkBoxSensor.setSelected(isSelected); 
				}
			}
		};
		
		notificationService.addNotificationListener(
				Notification.CHANTER_SELECTED, propertyChangeListener);
		notificationService.addNotificationListener(
				Notification.FINGERING_NOTE_SELECTED, propertyChangeListener);
		notificationService.addNotificationListener(
				Notification.FINGERING_OCTAVE_SELECTED, propertyChangeListener);
		notificationService.addNotificationListener(
				Notification.FINGERING_NUMBER_ADDED, propertyChangeListener);
		notificationService.addNotificationListener(
				Notification.FINGERING_NUMBER_REMOVED, propertyChangeListener);
		
		return propertyChangeListener;
	}
	
}
