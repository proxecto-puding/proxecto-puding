package main.controller;

import main.model.services.I18nService;
import main.model.services.impl.I18nServiceImpl;

public class TuningConfigurationController {
	
	private I18nService i18nService = new I18nServiceImpl();

	public String getTranslationForTuningFrequencyLabel() {
		return i18nService.getTranslation("tuningConfiguration.tuningFrequency.label");
	}
	
	public String getTranslationForTuningHzLabel() {
		return i18nService.getTranslation("tuningConfiguration.tuningHz.label");
	}
	
	public String getTranslationForTuningModeLabel() {
		return i18nService.getTranslation("tuningConfiguration.tuningMode.label");
	}
	
	public String getTranslationForPreciseTuningSettingsLabel() {
		return i18nService.getTranslation("tuningConfiguration.preciseTuningSettings.label");
	}
	
	public String getTranslationForPreciseTuningNoteLabel() {
		return i18nService.getTranslation("tuningConfiguration.preciseTuningNote.label");
	}
	
	public String getTranslationForPreciseTuningOctaveLabel() {
		return i18nService.getTranslation("tuningConfiguration.preciseTuningOctave.label");
	}
	
	public String getTranslationForPreciseTuningCentsLabel() {
		return i18nService.getTranslation("tuningConfiguration.preciseTuningCents.label");
	}
	
}
