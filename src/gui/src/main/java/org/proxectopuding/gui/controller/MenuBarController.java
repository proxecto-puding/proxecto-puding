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

package org.proxectopuding.gui.controller;

import org.proxectopuding.gui.model.services.BrowserService;
import org.proxectopuding.gui.model.services.I18nService;
import org.proxectopuding.gui.model.services.MidiService;
import org.proxectopuding.gui.model.services.NotificationService;

import com.google.inject.Inject;

public class MenuBarController extends Controller {
	
	private final BrowserService browserService;
	private final MidiService midiService;
	
	@Inject
	public MenuBarController(I18nService i18nService,
			BrowserService browserService, MidiService midiService,
			NotificationService notificationService) {
		
		super(i18nService, notificationService);
		
		this.browserService = browserService;
		this.midiService = midiService;
	}
	
	public String getFileMenuLabel() {
		return getI18nService().getTranslation("menuBar.file");
	}
	
	public String getExitMenuItemLabel() {
		return getI18nService().getTranslation("menuBar.file.exit");
	}
	
	public String getHelpMenuLabel() {
		return getI18nService().getTranslation("menuBar.help");
	}
	
	public String getUserManualMenuItemLabel() {
		return getI18nService().getTranslation("menuBar.help.userManual");
	}
	
	public String getTechnicalManualMenuItemLabel() {
		return getI18nService().getTranslation("menuBar.help.technicalManual");
	}
	
	public String getConfAppApiMenuItemLabel() {
		return getI18nService().getTranslation("menuBar.help.confAppApi");
	}
	
	public String getBagpipeApiMenuItemLabel() {
		return getI18nService().getTranslation("menuBar.help.bagpipeApi");
	}
	
	public String getAboutMenuItemLabel() {
		return getI18nService().getTranslation("menuBar.help.about");
	}
	
	public void onExit() {
		
		midiService.stop();
		System.exit(0);
	}
	
	public void onOpenUserManual() {
		
		browserService.openUserManualUrl();
	}
	
	public void onOpenTechnicalManual() {
		
		browserService.openTechnicalManualUrl();
	}
	
	public void openConfAppApi() {
		
		browserService.openConfAppApiUrl();
	}
	
	public void openBagpipeApi() {
		
		browserService.openBagpipeApiUrl();
	}
	
	public void openAbout() {
		
		browserService.openAboutUrl();
	}
	
}
