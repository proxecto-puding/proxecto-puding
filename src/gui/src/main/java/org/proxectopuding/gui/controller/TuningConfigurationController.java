/*
 * Proxecto Puding
 * Copyright (C) 2011 Alejo Pacín <info@proxecto-puding.org>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.proxectopuding.gui.controller;

import java.util.List;

import org.proxectopuding.gui.model.services.ConfigurationApplicationService;
import org.proxectopuding.gui.model.services.I18nService;
import org.proxectopuding.gui.model.services.NotificationService;
import org.proxectopuding.gui.model.utils.Notification;

import com.google.inject.Inject;

public class TuningConfigurationController extends Controller {
	
	private final ConfigurationApplicationService confAppService;
	
	@Inject
	public TuningConfigurationController(I18nService i18nService,
			ConfigurationApplicationService confAppService,
			NotificationService notificationService) {
		
		super(i18nService, notificationService);
		
		this.confAppService = confAppService;
	}

	public String getTuningFrequencyLabel() {
		return getI18nService().getTranslation("tuningConfiguration.tuningFrequency.label");
	}
	
	public int getTuningFrequency() {
		return confAppService.getTuningFrequency();
	}
	
	public void onTuningFrequencySelected(int tuningFrequency) {
		
		confAppService.setTuningFrequency(tuningFrequency);
	}
	
	public String getTuningHzLabel() {
		return getI18nService().getTranslation("tuningConfiguration.tuningHz.label");
	}
	
	public String getTuningModeLabel() {
		return getI18nService().getTranslation("tuningConfiguration.tuningMode.label");
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
		return getI18nService().getTranslation("tuningConfiguration.preciseTuningSettings.label");
	}
	
	public String getPreciseTuningNoteLabel() {
		return getI18nService().getTranslation("tuningConfiguration.preciseTuningNote.label");
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
		getNotificationService().sendNotification(this,
				Notification.PRECISE_TUNING_NOTE_SELECTED,
				preciseTuningNote);
	}
	
	public String getPreciseTuningOctaveLabel() {
		return getI18nService().getTranslation("tuningConfiguration.preciseTuningOctave.label");
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
		getNotificationService().sendNotification(this,
				Notification.PRECISE_TUNING_OCTAVE_SELECTED,
				preciseTuningOctave);
	}
	
	public String getPreciseTuningCentsLabel() {
		return getI18nService().getTranslation("tuningConfiguration.preciseTuningCents.label");
	}
	
	public int getPreciseTuningCents() {
		return confAppService.getPreciseTuningCents();
	}
	
	public void onPreciseTuningCentsSelected(int preciseTuningCents) {
		
		confAppService.setPreciseTuningCents(preciseTuningCents);
	}
}
