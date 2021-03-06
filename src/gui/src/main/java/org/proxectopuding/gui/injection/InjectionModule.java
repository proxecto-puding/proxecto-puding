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

package org.proxectopuding.gui.injection;

import org.proxectopuding.gui.controller.ContentPanelController;
import org.proxectopuding.gui.controller.FingeringConfigurationController;
import org.proxectopuding.gui.controller.MainController;
import org.proxectopuding.gui.controller.MenuBarController;
import org.proxectopuding.gui.controller.SelectionConfigurationController;
import org.proxectopuding.gui.controller.SensitivityConfigurationController;
import org.proxectopuding.gui.controller.StartConfigurationController;
import org.proxectopuding.gui.controller.TabbedPaneController;
import org.proxectopuding.gui.controller.TuningConfigurationController;
import org.proxectopuding.gui.model.entities.midiServer.MidiServer;
import org.proxectopuding.gui.model.entities.midiServer.MidiServerGeneral;
import org.proxectopuding.gui.model.services.BrowserService;
import org.proxectopuding.gui.model.services.ConfigurationApplicationService;
import org.proxectopuding.gui.model.services.DeviceManagerService;
import org.proxectopuding.gui.model.services.I18nService;
import org.proxectopuding.gui.model.services.MidiService;
import org.proxectopuding.gui.model.services.NotificationService;
import org.proxectopuding.gui.model.services.impl.BrowserServiceImpl;
import org.proxectopuding.gui.model.services.impl.ConfigurationApplicationServiceImpl;
import org.proxectopuding.gui.model.services.impl.DeviceManagerServiceImpl;
import org.proxectopuding.gui.model.services.impl.I18nServiceImpl;
import org.proxectopuding.gui.model.services.impl.MidiServiceImpl;
import org.proxectopuding.gui.model.services.impl.NotificationServiceImpl;
import org.proxectopuding.gui.model.services.impl.mocks.DeviceManagerServiceMockImpl;
import org.proxectopuding.gui.model.utils.BrowserManager;
import org.proxectopuding.gui.model.utils.ConfigurationManager;
import org.proxectopuding.gui.model.utils.DeviceManager;
import org.proxectopuding.gui.model.utils.FileDownload;
import org.proxectopuding.gui.model.utils.FileDownloader;
import org.proxectopuding.gui.model.utils.FileUtils;
import org.proxectopuding.gui.model.utils.I18nManager;
import org.proxectopuding.gui.model.utils.MidiUtils;
import org.proxectopuding.gui.model.utils.NotificationManager;
import org.proxectopuding.gui.model.utils.OperativeSystemManager;
import org.proxectopuding.gui.model.utils.PropertiesManager;
import org.proxectopuding.gui.model.utils.connection.ConnectionManager;
import org.proxectopuding.gui.model.utils.connection.ConnectionManagerJsscImpl;
import org.proxectopuding.gui.view.ContentPanelView;
import org.proxectopuding.gui.view.FingeringConfigurationView;
import org.proxectopuding.gui.view.MainView;
import org.proxectopuding.gui.view.MenuBarView;
import org.proxectopuding.gui.view.SelectionConfigurationView;
import org.proxectopuding.gui.view.SensitivityConfigurationView;
import org.proxectopuding.gui.view.StartConfigurationView;
import org.proxectopuding.gui.view.TabbedPaneView;
import org.proxectopuding.gui.view.TuningConfigurationView;
import org.proxectopuding.gui.view.swing.ContentPanelViewImpl;
import org.proxectopuding.gui.view.swing.FingeringConfigurationViewImpl;
import org.proxectopuding.gui.view.swing.MainViewImpl;
import org.proxectopuding.gui.view.swing.MenuBarViewImpl;
import org.proxectopuding.gui.view.swing.SelectionConfigurationViewImpl;
import org.proxectopuding.gui.view.swing.SensitivityConfigurationViewImpl;
import org.proxectopuding.gui.view.swing.StartConfigurationViewImpl;
import org.proxectopuding.gui.view.swing.TabbedPaneViewImpl;
import org.proxectopuding.gui.view.swing.TuningConfigurationViewImpl;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class InjectionModule extends AbstractModule {

	@Override
	protected void configure() {
		
		// Views
		bind(ContentPanelView.class).to(ContentPanelViewImpl.class);
		bind(FingeringConfigurationView.class).to(FingeringConfigurationViewImpl.class);
		bind(MainView.class).to(MainViewImpl.class);
		bind(MenuBarView.class).to(MenuBarViewImpl.class);
		bind(SelectionConfigurationView.class).to(SelectionConfigurationViewImpl.class);
		bind(SensitivityConfigurationView.class).to(SensitivityConfigurationViewImpl.class);
		bind(StartConfigurationView.class).to(StartConfigurationViewImpl.class);
		bind(TabbedPaneView.class).to(TabbedPaneViewImpl.class);
		bind(TuningConfigurationView.class).to(TuningConfigurationViewImpl.class);
		
		// Controllers
		bind(ContentPanelController.class);
		bind(FingeringConfigurationController.class);
		bind(MainController.class);
		bind(MenuBarController.class);
		bind(SelectionConfigurationController.class);
		bind(SensitivityConfigurationController.class);
		bind(StartConfigurationController.class);
		bind(TabbedPaneController.class);
		bind(TuningConfigurationController.class);
		
		// Services
		bind(BrowserService.class).to(BrowserServiceImpl.class).in(Singleton.class);
		bind(ConfigurationApplicationService.class).to(ConfigurationApplicationServiceImpl.class).in(Singleton.class);
//		bind(DeviceManagerService.class).to(DeviceManagerServiceImpl.class).in(Singleton.class);
		bind(DeviceManagerService.class).to(DeviceManagerServiceMockImpl.class).in(Singleton.class);
		bind(I18nService.class).to(I18nServiceImpl.class).in(Singleton.class);
		bind(MidiService.class).to(MidiServiceImpl.class).in(Singleton.class);
		bind(NotificationService.class).to(NotificationServiceImpl.class).in(Singleton.class);
		
		// Utils
		bind(BrowserManager.class).in(Singleton.class);
		bind(ConfigurationManager.class).in(Singleton.class);
		bind(ConnectionManager.class).to(ConnectionManagerJsscImpl.class).in(Singleton.class);
		bind(DeviceManager.class).in(Singleton.class);
		bind(FileDownloader.class).in(Singleton.class);
		bind(FileDownload.class).in(Singleton.class);
		bind(FileUtils.class).in(Singleton.class);
		bind(I18nManager.class).in(Singleton.class);
		bind(MidiUtils.class).in(Singleton.class);
		bind(NotificationManager.class).in(Singleton.class);
		bind(OperativeSystemManager.class).in(Singleton.class);
		bind(PropertiesManager.class).in(Singleton.class);
		
		// Entities
		bind(MidiServer.class).to(MidiServerGeneral.class).in(Singleton.class);
	}
}
