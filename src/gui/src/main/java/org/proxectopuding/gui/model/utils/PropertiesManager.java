package org.proxectopuding.gui.model.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PropertiesManager {
	
	private static final Logger LOGGER = Logger.getLogger(PropertiesManager.class.getName());

	public static Properties getProperties(String file) {
		
		Properties properties = new Properties();
		
		InputStream inputStream = PropertiesManager
				.class.getClassLoader().getResourceAsStream(file);
		
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Unable to load properties from file: {0}", file);
			LOGGER.log(Level.SEVERE, "Unable to load properties from file", e);
		}
		
		return properties;
	}
	
	public static List<String> getPropertyValues(String propertyValue) {
		
		List<String> propertyValues = new ArrayList<String>();
		
		if (propertyValue != null) {
			String[] values = propertyValue.split("\\s*,\\s*");
			propertyValues.addAll(Arrays.asList(values));
		}
		
		return propertyValues; 
	}
}
