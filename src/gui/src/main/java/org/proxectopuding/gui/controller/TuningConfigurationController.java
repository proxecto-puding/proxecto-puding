package org.proxectopuding.gui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.proxectopuding.gui.model.services.ConfigurationApplicationService;
import org.proxectopuding.gui.model.services.I18nService;
import org.proxectopuding.gui.model.services.NotificationService;
import org.proxectopuding.gui.model.utils.Notification;

import com.google.inject.Inject;

public class TuningConfigurationController {
	
	private final I18nService i18nService;
	private final ConfigurationApplicationService confAppService;
	private final NotificationService notificationService;
	
	@Inject
	public TuningConfigurationController(I18nService i18nService,
			ConfigurationApplicationService confAppService,
			NotificationService notificationService) {
		
		this.i18nService = i18nService;
		this.confAppService = confAppService;
		this.notificationService = notificationService;
	}

	public String getTranslationForTuningFrequencyLabel() {
		return i18nService.getTranslation("tuningConfiguration.tuningFrequency.label");
	}
	
	public int getTuningFrequency() {
		return confAppService.getTuningFrequency();
	}
	
	public ChangeListener getChangeListenerForTuningFrequencySpinner() {
		
		ChangeListener changeListener = new ChangeListener() {
			
			public void stateChanged(ChangeEvent event) {
				
				JSpinner spinnerTuningFrequency = (JSpinner) event.getSource();
				int tuningFrequency = (Integer) spinnerTuningFrequency.getValue();
				confAppService.setTuningFrequency(tuningFrequency);
			}
		};
		
		return changeListener;
	}
	
	public String getTranslationForTuningHzLabel() {
		return i18nService.getTranslation("tuningConfiguration.tuningHz.label");
	}
	
	public String getTranslationForTuningModeLabel() {
		return i18nService.getTranslation("tuningConfiguration.tuningMode.label");
	}
	
	public String[] getTuningModes() {
		
		String[] tuningMones = {};
		
		List<String> list = confAppService.getTuningModes();
		tuningMones = list.toArray(new String[list.size()]);
		
		return tuningMones;
	}
	
	public String getTuningMode() {
		return confAppService.getTuningMode();
	}
	
	public ActionListener getActionListenerForTuningModeComboBox() {
		
		ActionListener actionListener = new ActionListener() {
			
			public void actionPerformed(ActionEvent event) {
				
				@SuppressWarnings("unchecked")
				JComboBox<String> comboBoxTuningMode =
						(JComboBox<String>) event.getSource();
				String tuningMode = 
						(String) comboBoxTuningMode.getSelectedItem();
				confAppService.setTuningMode(tuningMode);
			}
		};
		
		return actionListener;
	}
	
	public String getTranslationForPreciseTuningSettingsLabel() {
		return i18nService.getTranslation("tuningConfiguration.preciseTuningSettings.label");
	}
	
	public String getTranslationForPreciseTuningNoteLabel() {
		return i18nService.getTranslation("tuningConfiguration.preciseTuningNote.label");
	}
	
	public String[] getPreciseTuningNotes() {
		
		String[] preciseTuningNotes = {};
		
		List<String> list = confAppService.getPreciseTuningNotes();
		preciseTuningNotes = list.toArray(new String[list.size()]);
		
		return preciseTuningNotes;
	}
	
	public String getPreciseTuningNote() {
		return confAppService.getPreciseTuningNote();
	}
	
	public ActionListener getActionListenerForPreciseTuningNoteComboBox() {
		
		ActionListener actionListener = new ActionListener() {
			
			public void actionPerformed(ActionEvent event) {
				
				@SuppressWarnings("unchecked")
				JComboBox<String> comboBoxPreciseTuningNote =
						(JComboBox<String>) event.getSource();
				String preciseTuningNote = 
						(String) comboBoxPreciseTuningNote.getSelectedItem();
				confAppService.setPreciseTuningNote(preciseTuningNote);
				notificationService.sendNotification(comboBoxPreciseTuningNote,
						Notification.PRECISE_TUNING_NOTE_SELECTED,
						preciseTuningNote);
			}
		};
		
		return actionListener;
	}
	
	// TODO Test this because of the final modifier.
	public PropertyChangeListener
			getPropertyChangeListenerForPreciseTuningNoteComboBox(
					final JComboBox<String> comboBoxPreciseTuningNote) {
		
		PropertyChangeListener propertyChangeListener = 
				new PropertyChangeListener() {
				
			public void propertyChange(PropertyChangeEvent event) {
				
				String propertyName = event.getPropertyName();
				if (Notification.READING_TONE_SELECTED.toString()
						== propertyName) {
					
					confAppService.resetPreciseTuningNotes();
					String[] preciseTuningNotes = getPreciseTuningNotes();
					ComboBoxModel<String> preciseTuningNoteModel =
							new DefaultComboBoxModel<String>(preciseTuningNotes);
					comboBoxPreciseTuningNote.setModel(preciseTuningNoteModel);
					String preciseTuningNote = getPreciseTuningNote();
					comboBoxPreciseTuningNote.setSelectedItem(preciseTuningNote);
				}
			}
			
		};
		
		notificationService.addNotificationListener(
				Notification.READING_TONE_SELECTED, propertyChangeListener);
		
		return propertyChangeListener;
	}
	
	public String getTranslationForPreciseTuningOctaveLabel() {
		return i18nService.getTranslation("tuningConfiguration.preciseTuningOctave.label");
	}
	
	public Integer[] getPreciseTuningOctaves() {
		
		Integer[] preciseTuningOctaves = {};
		
		List<Integer> list = confAppService.getPreciseTuningOctaves();
		preciseTuningOctaves = list.toArray(new Integer[list.size()]);
		
		return preciseTuningOctaves;
	}
	
	public int getPreciseTuningOctave() {
		return confAppService.getPreciseTuningOctave();
	}
	
	public ActionListener getActionListenerForPreciseTuningOctaveComboBox() {
		
		ActionListener actionListener = new ActionListener() {
			
			public void actionPerformed(ActionEvent event) {
				
				@SuppressWarnings("unchecked")
				JComboBox<Integer> comboBoxPreciseTuningOctave =
						(JComboBox<Integer>) event.getSource();
				Integer preciseTuningOctave = (Integer)
						comboBoxPreciseTuningOctave.getSelectedItem();
				confAppService.setPreciseTuningOctave(preciseTuningOctave);
				notificationService.sendNotification(comboBoxPreciseTuningOctave,
						Notification.PRECISE_TUNING_OCTAVE_SELECTED,
						preciseTuningOctave);
			}
		};
		
		return actionListener;
	}
	
	public String getTranslationForPreciseTuningCentsLabel() {
		return i18nService.getTranslation("tuningConfiguration.preciseTuningCents.label");
	}
	
	public int getPreciseTuningCents() {
		return confAppService.getPreciseTuningCents();
	}
	
	public ChangeListener getChangeListenerForPreciseTuningCentsSpinner() {
		
		ChangeListener changeListener = new ChangeListener() {
			
			public void stateChanged(ChangeEvent event) {
				
				JSpinner spinnerPreciseTuningCents = (JSpinner) event.getSource();
				int preciseTuningCents = (Integer) spinnerPreciseTuningCents.getValue();
				confAppService.setPreciseTuningCents(preciseTuningCents);
			}
		};
		
		return changeListener;
	}
	
	// TODO Test this because of the final modifier.
	public PropertyChangeListener
			getPropertyChangeListenerForPreciseTuningCentsSpinner(
					final JSpinner spinnerPreciseTuningCents) {
		
		PropertyChangeListener propertyChangeListener = 
				new PropertyChangeListener() {
				
			public void propertyChange(PropertyChangeEvent event) {
				
				String propertyName = event.getPropertyName();
				if ((Notification.READING_TONE_SELECTED.toString()
						== propertyName) ||
						(Notification.PRECISE_TUNING_NOTE_SELECTED.toString()
						== propertyName) ||
						(Notification.PRECISE_TUNING_OCTAVE_SELECTED.toString()
								== propertyName)) {
					
					int preciseTuningCents = getPreciseTuningCents();
					spinnerPreciseTuningCents.setValue(preciseTuningCents);
				}
			}
			
		};
		
		notificationService.addNotificationListener(
			Notification.READING_TONE_SELECTED, propertyChangeListener);
		notificationService.addNotificationListener(
			Notification.PRECISE_TUNING_NOTE_SELECTED, propertyChangeListener);
		notificationService.addNotificationListener(
			Notification.PRECISE_TUNING_OCTAVE_SELECTED, propertyChangeListener);
		
		return propertyChangeListener;
	}
	
}
