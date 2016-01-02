package main.controller;

import main.model.services.I18nService;
import main.model.services.impl.I18nServiceImpl;

public class FingeringConfigurationController {
	
	private I18nService i18nService = new I18nServiceImpl();

	public String getTranslationForCustomFingeringNoteLabel() {
		return i18nService.getTranslation("fingeringConfiguration.customFingeringNote.label");
	}
	
	public String getTranslationForCustomFingeringOctaveLabel() {
		return i18nService.getTranslation("fingeringConfiguration.customFingeringOctave.label");
	}
	
	public String getTranslationForCustomFingeringNumberLabel() {
		return i18nService.getTranslation("fingeringConfiguration.customFingeringNumber.label");
	}
	
	public String getTranslationForCustomFingeringNewButton() {
		return i18nService.getTranslation("fingeringConfiguration.customFingeringNew.button");
	}
	
	public String getTranslationForCustomFingeringRemoveButton() {
		return i18nService.getTranslation("fingeringConfiguration.customFingeringRemove.button");
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
	
}
