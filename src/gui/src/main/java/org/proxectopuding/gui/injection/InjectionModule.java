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

public class InjectionModule extends AbstractModule {

	@Override
	protected void configure() {
		
		// Views
		bind(ContentPanelViewImpl.class);
		bind(FingeringConfigurationViewImpl.class);
		bind(MainViewImpl.class);
		bind(MenuBarViewImpl.class);
		bind(SelectionConfigurationViewImpl.class);
		bind(SensitivityConfigurationViewImpl.class);
		bind(StartConfigurationViewImpl.class);
		bind(TabbedPaneViewImpl.class);
		bind(TuningConfigurationViewImpl.class);
		
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
		bind(BrowserService.class).to(BrowserServiceImpl.class);
		bind(ConfigurationApplicationService.class).to(ConfigurationApplicationServiceImpl.class);
		bind(DeviceManagerService.class).to(DeviceManagerServiceImpl.class);
		bind(I18nService.class).to(I18nServiceImpl.class);
		bind(MidiService.class).to(MidiServiceImpl.class);
		bind(NotificationService.class).to(NotificationServiceImpl.class);
		
		// Utils
		bind(BrowserManager.class);
		bind(ConfigurationManager.class);
		bind(ConnectionManager.class).to(ConnectionManagerJsscImpl.class);
		bind(DeviceManager.class);
		bind(FileDownloader.class);
		bind(FileDownload.class);
		bind(FileUtils.class);
		bind(I18nManager.class);
		bind(MidiUtils.class);
		bind(NotificationManager.class);
		bind(OperativeSystemManager.class);
		bind(PropertiesManager.class);
		
		// Entities
		bind(MidiServer.class).to(MidiServerGeneral.class);
	}
}
