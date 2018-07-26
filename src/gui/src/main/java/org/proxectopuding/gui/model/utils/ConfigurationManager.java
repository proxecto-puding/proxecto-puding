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
	
	public List<String> getSupportedLanguages() {
		
		String propertyValue = properties.getProperty("supportedLanguages");
		return propertiesManager.getPropertyValues(propertyValue);
	}
	
}
