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

package org.proxectopuding.gui.model.services.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.proxectopuding.gui.model.services.I18nService;
import org.proxectopuding.gui.model.utils.I18nManager;

import com.google.inject.Inject;

public class I18nServiceImpl implements I18nService {
	
	private static final Logger LOGGER = Logger.getLogger(I18nServiceImpl.class.getName());
	
	private final I18nManager i18nManager;
	
	@Inject
	public I18nServiceImpl(I18nManager i18nManager) {
		
		this.i18nManager = i18nManager;
	}

	@Override
	public String getTranslation(String translationId) {
		
		LOGGER.log(Level.INFO, "Getting translation for: {0}", translationId);
		return i18nManager.getTranslation(translationId);
	}

}
