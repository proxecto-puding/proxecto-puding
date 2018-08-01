package org.proxectopuding.gui.model.services.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.proxectopuding.gui.model.services.I18nService;
import org.proxectopuding.gui.model.utils.I18nManager;

import com.google.inject.Inject;

public class I18nServiceImpl implements I18nService {
	
	private static final Logger LOGGER = Logger.getLogger(I18nServiceImpl.class.getName());
	
	private final I18nManager i18nManager;
	
	@Inject
	public I18nServiceImpl(I18nManager i18nManager) {
		
		this.i18nManager = i18nManager;
	}

	@Override
	public String getTranslation(String translationId) {
		
		LOGGER.log(Level.INFO, "Getting translation for: {0}", translationId);
		return i18nManager.getTranslation(translationId);
	}

}
