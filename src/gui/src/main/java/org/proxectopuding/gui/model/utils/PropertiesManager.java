package org.proxectopuding.gui.model.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class PropertiesManager {

	public static Properties getProperties(String file) {
		
		Properties properties = new Properties();
		
		InputStream inputStream = PropertiesManager
				.class.getClassLoader().getResourceAsStream(file);
		
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			System.err.println("Error while loading properties from file '" +
					file + "'" +
					" Message: " + e.getMessage());
			e.printStackTrace();
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
