package main.model.services.impl;

import main.model.services.I18nService;
import main.model.utils.I18nManager;

public class I18nServiceImpl implements I18nService {

	@Override
	public String getTranslation(String translationId) {
		
		return I18nManager.getTranslation(translationId);
	}

}
