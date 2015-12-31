package main.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main.model.entities.BagpipeDevice;
import main.model.services.ConfigurationApplicationService;
import main.model.services.DeviceManagerService;
import main.model.services.I18nService;
import main.model.services.NotificationService;
import main.model.services.impl.ConfigurationApplicationServiceImpl;
import main.model.services.impl.DeviceManagerServiceImpl;
import main.model.services.impl.I18nServiceImpl;
import main.model.services.impl.NotificationServiceImpl;
import main.model.utils.Notification;

public class SelectionConfigurationController {
	
	private I18nService i18nService = new I18nServiceImpl();
	private DeviceManagerService deviceManagerService =
			new DeviceManagerServiceImpl();
	private ConfigurationApplicationService confAppService =
			new ConfigurationApplicationServiceImpl();
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
	
	public String[] getTuningTones() {
		
		String[] tuningTones = {};
		
		List<String> list = confAppService.getTuningTones();
		tuningTones = list.toArray(new String[list.size()]);
		
		return tuningTones;
	}
	
	public String getTuningTone() {
		
		String tuningTone = null;
		
		BagpipeDevice selectedDevice = 
				deviceManagerService.getSelectedBagpipeDevice();
		if (selectedDevice != null) {
			String productId = selectedDevice.getProductId();
			int tone = deviceManagerService.getTuningTone(productId);
			tuningTone = confAppService.getTuningTone(tone);
		}
		
		if (tuningTone == null) {
			tuningTone = confAppService.getDefaultTuningTone();
		}
		
		return tuningTone;
	}
	
	public ActionListener getActionListenerForTuningToneComboBox() {
		
		ActionListener actionListener = new ActionListener() {
			
			public void actionPerformed(ActionEvent event) {
				
				@SuppressWarnings("unchecked")
				JComboBox<String> comboBoxTuningTone =
						(JComboBox<String>) event.getSource();
				String tuningTone = 
						(String) comboBoxTuningTone.getSelectedItem();
				
				BagpipeDevice selectedDevice = 
						deviceManagerService.getSelectedBagpipeDevice();
				if (selectedDevice != null) {
					String productId = selectedDevice.getProductId();
					int tone = confAppService.getTuningTone(tuningTone);
					deviceManagerService.setTuningTone(productId, tone);
				}
			}
		};
		
		return actionListener;
	}
	
	// TODO Test this because of the final modifier.
	public PropertyChangeListener 
			getPropertyChangeListenerForTuningToneComboBox(
					final JComboBox<String> comboBoxTuningTone) {
		
		PropertyChangeListener propertyChangeListener = 
				new PropertyChangeListener() {
				
			public void propertyChange(PropertyChangeEvent event) {
				
				String propertyName = event.getPropertyName();
				if (Notification.CHANTER_SELECTED.toString() == propertyName) {
					String productId = (String) event.getNewValue();
					int tuningTone =
							deviceManagerService.getTuningTone(productId);
					String tone = confAppService.getTuningTone(tuningTone);
					comboBoxTuningTone.setSelectedItem(tone);
				}
			}
			
		};
		
		notificationService.addNotificationListener(
				Notification.CHANTER_SELECTED, propertyChangeListener);
		
		return propertyChangeListener;
	}
	
	public String getTranslationForTuningOctaveLabel() {
		return i18nService.getTranslation("selectionConfiguration.tuningOctave.label");
	}
	
	public Integer[] getTuningOctaves() {
		
		Integer[] tuningOctaves = {};
		
		List<Integer> list = confAppService.getTuningOctaves();
		tuningOctaves = list.toArray(new Integer[list.size()]);
		
		return tuningOctaves;
	}
	
	public int getTuningOctave() {
	
		int tuningOctave = Integer.MIN_VALUE;
		
		BagpipeDevice selectedDevice = 
				deviceManagerService.getSelectedBagpipeDevice();
		if (selectedDevice != null) {
			String productId = selectedDevice.getProductId();
			try {
				tuningOctave = deviceManagerService.getTuningOctave(productId);
			} catch(IllegalArgumentException e) {
				// Skip.
			}
		}
		
		if (tuningOctave == Integer.MIN_VALUE) {
			tuningOctave = confAppService.getDefaultTuningOctave();
		}
		
		return tuningOctave;
	}
	
	public ActionListener getActionListenerForTuningOctaveComboBox() {
		
		ActionListener actionListener = new ActionListener() {
			
			public void actionPerformed(ActionEvent event) {
				
				@SuppressWarnings("unchecked")
				JComboBox<Integer> comboBoxTuningOctave =
						(JComboBox<Integer>) event.getSource();
				Integer tuningOctave = 
						(Integer) comboBoxTuningOctave.getSelectedItem();
				
				BagpipeDevice selectedDevice = 
						deviceManagerService.getSelectedBagpipeDevice();
				if (selectedDevice != null) {
					String productId = selectedDevice.getProductId();
					deviceManagerService.setTuningOctave(productId, tuningOctave);
				}
			}
		};
		
		return actionListener;
	}
	
	// TODO Test this because of the final modifier.
	public PropertyChangeListener 
			getPropertyChangeListenerForTuningOctaveComboBox(
					final JComboBox<Integer> comboBoxTuningOctave) {
		
		PropertyChangeListener propertyChangeListener = 
				new PropertyChangeListener() {
				
			public void propertyChange(PropertyChangeEvent event) {
				
				String propertyName = event.getPropertyName();
				if (Notification.CHANTER_SELECTED.toString() == propertyName) {
					String productId = (String) event.getNewValue();
					int tuningOctave =
							deviceManagerService.getTuningOctave(productId);
					comboBoxTuningOctave.setSelectedItem(tuningOctave);
				}
			}
			
		};
		
		notificationService.addNotificationListener(
				Notification.CHANTER_SELECTED, propertyChangeListener);
		
		return propertyChangeListener;
	}
	
	public String getTranslationForSamplesLabel() {
		return i18nService.getTranslation("selectionConfiguration.samples.label");
	}
	
	public String[] getSamples() {
		
		String[] samples = {};
		
		List<String> list = confAppService.getSamples();
		samples = list.toArray(new String[list.size()]);
		
		return samples;
	}
	
	public String getSample() {
		return confAppService.getSample();
	}
	
	public ActionListener getActionListenerForSamplesComboBox() {
		
		ActionListener actionListener = new ActionListener() {
			
			public void actionPerformed(ActionEvent event) {
				
				@SuppressWarnings("unchecked")
				JComboBox<String> comboBoxSamples =
						(JComboBox<String>) event.getSource();
				String sample = 
						(String) comboBoxSamples.getSelectedItem();
				confAppService.setSample(sample);
			}
		};
		
		return actionListener;
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
	
	public List<Boolean> getFingeringTypes() {
		
		List<Boolean> fingeringTypes = new ArrayList<Boolean>();
		
		BagpipeDevice selectedDevice = 
				deviceManagerService.getSelectedBagpipeDevice();
		if (selectedDevice != null) {
			String productId = selectedDevice.getProductId();
			fingeringTypes = deviceManagerService.getFingeringTypes(productId);
		}
		
		if (fingeringTypes.isEmpty()) {
			fingeringTypes = confAppService.getDefaultFingeringTypes();
		}
		
		return fingeringTypes;
	}
	
	// fingeringType:
	// 0 - Aberto
	// 1 - Pechado
	// 2 - Custom
	public ActionListener getActionListenerForFingeringTypesCheckBox(
			final int fingeringType) {
		
		ActionListener actionListener = new ActionListener() {
			
			public void actionPerformed(ActionEvent event) {
				
				JCheckBox chckbxFingeringType =
						(JCheckBox) event.getSource();
				boolean isSelected = chckbxFingeringType.isSelected();
				
				BagpipeDevice selectedDevice = 
						deviceManagerService.getSelectedBagpipeDevice();
				if (selectedDevice != null) {
					String productId = selectedDevice.getProductId();
					List<Boolean> fingeringTypes =
							deviceManagerService.getFingeringTypes(productId);
					fingeringTypes.set(fingeringType, isSelected);
					deviceManagerService.setFingeringTypes(productId, fingeringTypes);
				}
			}
		};
		
		return actionListener;
	}
	
	// TODO Test this because of the final modifier.
	public PropertyChangeListener 
			getPropertyChangeListenerForFingeringTypesCheckBox(
					final int fingeringType,
					final JCheckBox chckbxFingeringType) {
		
		PropertyChangeListener propertyChangeListener = 
				new PropertyChangeListener() {
				
			public void propertyChange(PropertyChangeEvent event) {
				
				String propertyName = event.getPropertyName();
				if (Notification.CHANTER_SELECTED.toString() == propertyName) {
					String productId = (String) event.getNewValue();
					List<Boolean> fingeringTypes =
							deviceManagerService.getFingeringTypes(productId);
					boolean isSelected = fingeringTypes.get(fingeringType);
					chckbxFingeringType.setSelected(isSelected);
				}
			}
			
		};
		
		notificationService.addNotificationListener(
				Notification.CHANTER_SELECTED, propertyChangeListener);
		
		return propertyChangeListener;
	}
	
	public String getTranslationForComplementsLabel() {
		return i18nService.getTranslation("selectionConfiguration.complements.label");
	}
	
	public String getTranslationForComplementsBagCheckBox() {
		return i18nService.getTranslation("selectionConfiguration.complements.bag.checkbox");
	}
		
	public Boolean getComplementsBagCheckBox() {
		
		Boolean bag = null;
		
		BagpipeDevice selectedDevice = 
				deviceManagerService.getSelectedBagpipeDevice();
		if (selectedDevice != null) {
			String productId = selectedDevice.getProductId();
			bag = deviceManagerService.isBagEnabled(productId);
		}
		
		if (bag == null) {
			bag = confAppService.isDefaultBagEnabled();
		}
		
		return bag;
	}
	
	public ActionListener getActionListenerForComplementsBagCheckBox() {
		
		ActionListener actionListener = new ActionListener() {
			
			public void actionPerformed(ActionEvent event) {
				
				JCheckBox chckbxBag = (JCheckBox) event.getSource();
				boolean isSelected = chckbxBag.isSelected();
				
				BagpipeDevice selectedDevice = 
						deviceManagerService.getSelectedBagpipeDevice();
				if (selectedDevice != null) {
					String productId = selectedDevice.getProductId();
					deviceManagerService.setBagEnabled(productId, isSelected);
				}
			}
		};
		
		return actionListener;
	}
	
	// TODO Test this because of the final modifier.
	public PropertyChangeListener
			getPropertyChangeListenerForComplementsBagCheckBox(
					final JCheckBox chckbxBag) {
		
		PropertyChangeListener propertyChangeListener = 
				new PropertyChangeListener() {
				
			public void propertyChange(PropertyChangeEvent event) {
				
				String propertyName = event.getPropertyName();
				if (Notification.CHANTER_SELECTED.toString() == propertyName) {
					String productId = (String) event.getNewValue();
					boolean isBagEnabled =
							deviceManagerService.isBagEnabled(productId);
					chckbxBag.setSelected(isBagEnabled);
				}
			}
			
		};
		
		notificationService.addNotificationListener(
				Notification.CHANTER_SELECTED, propertyChangeListener);
		
		return propertyChangeListener;
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
