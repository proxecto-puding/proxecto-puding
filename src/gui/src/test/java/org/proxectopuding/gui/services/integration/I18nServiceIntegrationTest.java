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

package org.proxectopuding.gui.services.integration;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.proxectopuding.gui.model.services.I18nService;
import org.proxectopuding.gui.model.services.impl.I18nServiceImpl;
import org.proxectopuding.gui.model.utils.ConfigurationManager;
import org.proxectopuding.gui.model.utils.I18nManager;
import org.proxectopuding.gui.model.utils.OperativeSystemManager;
import org.proxectopuding.gui.model.utils.PropertiesManager;

public class I18nServiceIntegrationTest {
	
	private static String TRANSLATION_ID = "readingTones.C";
	private static String TRANSLATION = "Do";
	
	private PropertiesManager propertiesManager = new PropertiesManager();	
	private ConfigurationManager configurationManager =
			new ConfigurationManager(propertiesManager);
	private OperativeSystemManager operativeSystemManager = new OperativeSystemManager();
	private I18nManager i18nManager = new I18nManager(configurationManager,
			operativeSystemManager, propertiesManager);
	private I18nService i18nService = new I18nServiceImpl(i18nManager);
	
	@Test
	public void getTranslation() {
		
		// When
		String translation = i18nService.getTranslation(TRANSLATION_ID);
		
		// Then
		assertEquals(TRANSLATION, translation);
	}
}
