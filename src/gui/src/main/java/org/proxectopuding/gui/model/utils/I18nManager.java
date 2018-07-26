package org.proxectopuding.gui.model.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.google.inject.Inject;

public class I18nManager {
	
	private static final String TRANLATIONS_FILE_PATH = "i18n/translations-";
	private static final String TRANSLATIONS_FILE_FORMAT = ".properties";
	private static final String DEFAULT_LANGUAGE = "gl";
	
	private List<String> supportedLanguages;
	private String language = DEFAULT_LANGUAGE;
	private Properties properties;

	@Inject
	public I18nManager(ConfigurationManager configurationManager,
			OperativeSystemManager operativeSystemManager,
			PropertiesManager propertiesManager) {
		
		setupLanguage(configurationManager, operativeSystemManager);
		getTranslations(propertiesManager);
	}
	
	public String getTranslation(String translationId) {
		
		String translation = properties.getProperty(translationId);
		return translation != null ? translation : translationId;
	}
	
	public List<String> getTranslations(String[] translationIds) {
		
		List<String> translations = new ArrayList<String>();
		
		for (String id : translationIds) {
			translations.add(getTranslation(id));
		}
		
		return translations;
	}
	
	private void setupLanguage(ConfigurationManager configurationManager,
			OperativeSystemManager operativeSystemManager) {
		
		supportedLanguages = configurationManager.getSupportedLanguages();
		String systemLanguage = operativeSystemManager.getLanguage();
		if (systemLanguage != null && supportedLanguages != null &&
				supportedLanguages.contains(systemLanguage)) {
			language = systemLanguage;
		}
	}
	
	private void getTranslations(PropertiesManager propertiesManager) {
		
		properties = propertiesManager.getProperties(
				TRANLATIONS_FILE_PATH + language + TRANSLATIONS_FILE_FORMAT);
	}
	
}
