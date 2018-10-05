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

package org.proxectopuding.gui.services.acceptance;

import org.junit.Test;
import org.proxectopuding.gui.model.services.BrowserService;
import org.proxectopuding.gui.model.services.impl.BrowserServiceImpl;
import org.proxectopuding.gui.model.utils.BrowserManager;
import org.proxectopuding.gui.model.utils.ConfigurationManager;
import org.proxectopuding.gui.model.utils.PropertiesManager;

public class BrowserServiceAcceptanceTest {
	
	private BrowserManager browserManager = new BrowserManager();
	private PropertiesManager propertiesManager = new PropertiesManager();
	private ConfigurationManager configurationManager =
			new ConfigurationManager(propertiesManager);
	private BrowserService browserService =
			new BrowserServiceImpl(browserManager, configurationManager);
	
	/**
	 * Feature: user wants to check the documentation
	 */
	
	/**
	 * Scenario: user wants to check the user's manual
	 * When user request to see the user's manual
	 * Then the required actors are notified
	 */
	@Test
	public void openUserManualUrl() {
		
		// When
		browserService.openUserManualUrl();
	}
}
