package main.model.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesManager {

	public static Properties getProperties(String file) {
		
		Properties properties = new Properties();
		
		InputStream inputStream = PropertiesManager
				.class.getResourceAsStream(file);
		
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
	
}
