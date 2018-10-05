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
