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

import java.util.List;
import java.util.Properties;

import com.google.inject.Inject;

public class ConfigurationManager {

	private static final String CONFIG_FILE_PATH =
			"configuration.properties";
	
	private final PropertiesManager propertiesManager;
	private final Properties properties;
	
	@Inject
	public ConfigurationManager(PropertiesManager propertiesManager) {
		
		this.propertiesManager = propertiesManager;
		this.properties = propertiesManager.getProperties(CONFIG_FILE_PATH);
	}
	
	public String getAboutUrl() {
		return properties.getProperty("aboutUrl");
	}
	
	public String getBagpipeApiUrl() {
		return properties.getProperty("bagpipeApiUrl");
	}
	
	public String getConfAppApiUrl() {
		return properties.getProperty("confAppApiUrl");
	}
	
	public String getUserManualUrl() {
		return properties.getProperty("userManualUrl");
	}
	
	public String getTechnicalManualUrl() {
		return properties.getProperty("technicalManualUrl");
	}
	
	public List<String> getSupportedLanguages() {
		
		String propertyValue = properties.getProperty("supportedLanguages");
		return propertiesManager.getPropertyValues(propertyValue);
	}
	
}
