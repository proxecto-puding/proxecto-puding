package org.proxectopuding.gui.controller;

import java.beans.PropertyChangeListener;
import java.util.List;

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

	public String getTuningFrequencyLabel() {
		return i18nService.getTranslation("tuningConfiguration.tuningFrequency.label");
	}
	
	public int getTuningFrequency() {
		return confAppService.getTuningFrequency();
	}
	
	public void onTuningFrequencySelected(int tuningFrequency) {
		
		confAppService.setTuningFrequency(tuningFrequency);
	}
	
	public String getTuningHzLabel() {
		return i18nService.getTranslation("tuningConfiguration.tuningHz.label");
	}
	
	public String getTuningModeLabel() {
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
	
	public void onTuningModeSelected(String tuningMode) {
		
		confAppService.setTuningMode(tuningMode);
	}
	
	public String getPreciseTuningSettingsLabel() {
		return i18nService.getTranslation("tuningConfiguration.preciseTuningSettings.label");
	}
	
	public String getPreciseTuningNoteLabel() {
		return i18nService.getTranslation("tuningConfiguration.preciseTuningNote.label");
	}
	
	public String[] getPreciseTuningNotes() {
		
		String[] preciseTuningNotes = {};
		
		List<String> list = confAppService.getPreciseTuningNotes();
		preciseTuningNotes = list.toArray(new String[list.size()]);
		
		return preciseTuningNotes;
	}
	
	public void resetPreciseTuningNotes() {
		confAppService.resetPreciseTuningNotes();
	}
	
	public String getPreciseTuningNote() {
		return confAppService.getPreciseTuningNote();
	}
	
	public void onPreciseTuningNoteSelected(String preciseTuningNote) {
		
		confAppService.setPreciseTuningNote(preciseTuningNote);
		notificationService.sendNotification(this,
				Notification.PRECISE_TUNING_NOTE_SELECTED,
				preciseTuningNote);
	}
	
	public String getPreciseTuningOctaveLabel() {
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
	
	public void onPreciseTuningOctaveSelected(int preciseTuningOctave) {
		
		confAppService.setPreciseTuningOctave(preciseTuningOctave);
		notificationService.sendNotification(this,
				Notification.PRECISE_TUNING_OCTAVE_SELECTED,
				preciseTuningOctave);
	}
	
	public String getPreciseTuningCentsLabel() {
		return i18nService.getTranslation("tuningConfiguration.preciseTuningCents.label");
	}
	
	public int getPreciseTuningCents() {
		return confAppService.getPreciseTuningCents();
	}
	
	public void onPreciseTuningCentsSelected(int preciseTuningCents) {
		
		confAppService.setPreciseTuningCents(preciseTuningCents);
	}
	
	public void subscribe(Notification notification,
			PropertyChangeListener propertyChangeListener) {
		
		notificationService.addNotificationListener(notification,
				propertyChangeListener);
	}
}
