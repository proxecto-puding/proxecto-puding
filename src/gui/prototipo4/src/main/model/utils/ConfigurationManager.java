package main.model.utils;

import java.util.Properties;

public class ConfigurationManager {

	private static final String CONFIG_FILE_PATH =
			"/configuration.properties";
	
	private static Properties properties;
	
	static {
		properties = PropertiesManager.getProperties(CONFIG_FILE_PATH);
	};
	
	public static String getAboutUrl() {
		return properties.getProperty("aboutUrl");
	}
	
	public static String getBagpipeApiUrl() {
		return properties.getProperty("bagpipeApiUrl");
	}
	
	public static String getConfAppApiUrl() {
		return properties.getProperty("confAppApiUrl");
	}
	
	public static String getUserManualUrl() {
		return properties.getProperty("userManualUrl");
	}
	
}
