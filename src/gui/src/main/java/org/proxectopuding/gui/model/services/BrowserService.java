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

package org.proxectopuding.gui.model.services;

public interface BrowserService {

	/**
	 * Open the "About" using the default browser.
	 */
	void openAboutUrl();
	
	/**
	 * Open the "Bagpipe API" using the default browser.
	 */
	void openBagpipeApiUrl();
	
	/**
	 * Open the "Configuration Application API" using the default browser.
	 */
	void openConfAppApiUrl();
	
	/**
	 * Open the "User Manual" using the default browser.
	 */
	void openUserManualUrl();
	
	/**
	 * Open the "Technical Manual" using the default browser.
	 */
	void openTechnicalManualUrl();
	
	/**
	 * Open the provided URI using the default browser.
	 * @param uri URI to be shown.
	 */
	void openUri(String uri);
}
