package main.model.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class I18nManager {
	
	private static final String TRANLATIONS_FILE_PATH =
			"/main/resources/i18n/translations-";
	private static final String TRANSLATIONS_FILE_FORMAT = ".properties";
	private static final String DEFAULT_LANGUAGE = "gl";
	private static List<String> supportedLanguages;
	private static String language = DEFAULT_LANGUAGE;
	private static Properties properties;

	static {
		setupLanguage();
		getTranslations();
	};
	
	public static String getTranslation(String translationId) {
		
		return properties.getProperty(translationId);
	}
	
	public static List<String> getTranslations(String[] translationIds) {
		
		List<String> translations = new ArrayList<String>();
		
		for (String id : translationIds) {
			translations.add(getTranslation(id));
		}
		
		return translations;
	}
	
	private static void setupLanguage() {
		
		supportedLanguages = ConfigurationManager.getSupportedLanguages();
		String systemLanguage = OperativeSystemManager.getLanguage();
		if (systemLanguage != null && supportedLanguages != null &&
				supportedLanguages.contains(systemLanguage)) {
			language = systemLanguage;
		}
	}
	
	private static void getTranslations() {
		properties = PropertiesManager.getProperties(
				TRANLATIONS_FILE_PATH + language + TRANSLATIONS_FILE_FORMAT);
	}
	
}
