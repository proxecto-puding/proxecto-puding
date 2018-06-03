package org.proxectopuding.gui.model.services.impl;

import org.proxectopuding.gui.model.services.I18nService;
import org.proxectopuding.gui.model.utils.I18nManager;

public class I18nServiceImpl implements I18nService {

	@Override
	public String getTranslation(String translationId) {
		
		return I18nManager.getTranslation(translationId);
	}

}
