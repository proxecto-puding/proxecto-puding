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

	public Properties getProperties(String file) {
		
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
	
	public List<String> getPropertyValues(String propertyValue) {
		
		List<String> propertyValues = new ArrayList<String>();
		
		if (propertyValue != null) {
			String[] values = propertyValue.split("\\s*,\\s*");
			propertyValues.addAll(Arrays.asList(values));
		}
		
		return propertyValues; 
	}
}
