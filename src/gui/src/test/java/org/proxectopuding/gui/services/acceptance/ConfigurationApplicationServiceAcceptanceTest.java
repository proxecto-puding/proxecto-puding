package org.proxectopuding.gui.services.acceptance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;
import org.proxectopuding.gui.model.entities.BagpipeConfiguration;
import org.proxectopuding.gui.model.entities.BagpipeConfigurationType;
import org.proxectopuding.gui.model.entities.BagpipeDevice;
import org.proxectopuding.gui.model.entities.midiServer.MidiServer;
import org.proxectopuding.gui.model.entities.midiServer.MidiServerConfiguration;
import org.proxectopuding.gui.model.entities.midiServer.MidiServerGeneral;
import org.proxectopuding.gui.model.entities.midiServer.MidiServerUnix;
import org.proxectopuding.gui.model.services.ConfigurationApplicationService;
import org.proxectopuding.gui.model.services.DeviceManagerService;
import org.proxectopuding.gui.model.services.MidiService;
import org.proxectopuding.gui.model.services.impl.ConfigurationApplicationServiceImpl;
import org.proxectopuding.gui.model.services.impl.DeviceManagerServiceImpl;
import org.proxectopuding.gui.model.services.impl.MidiServiceImpl;
import org.proxectopuding.gui.model.utils.ConfigurationManager;
import org.proxectopuding.gui.model.utils.DeviceManager;
import org.proxectopuding.gui.model.utils.FileDownload;
import org.proxectopuding.gui.model.utils.FileDownloader;
import org.proxectopuding.gui.model.utils.FileUtils;
import org.proxectopuding.gui.model.utils.I18nManager;
import org.proxectopuding.gui.model.utils.MidiUtils;
import org.proxectopuding.gui.model.utils.OperativeSystemManager;
import org.proxectopuding.gui.model.utils.PropertiesManager;
import org.proxectopuding.gui.model.utils.SoundFontManager;
import org.proxectopuding.gui.model.utils.connection.ConnectionManager;
import org.proxectopuding.gui.model.utils.connection.ConnectionManagerJsscImpl;

public class ConfigurationApplicationServiceAcceptanceTest {
	
	private FileUtils fileUtils = new FileUtils();
	private FileDownload fileDownload = new FileDownload(fileUtils);
	private FileDownloader fileDownloader = new FileDownloader(fileDownload);
	private SoundFontManager soundFontManager =
			new SoundFontManager(fileDownloader, fileUtils);
	private OperativeSystemManager operativeSystemManager =
			new OperativeSystemManager();
	private MidiUtils midiUtils = new MidiUtils();
	private MidiServerGeneral midiServerGeneral =
			new MidiServerGeneral(soundFontManager, operativeSystemManager,
					fileUtils, midiUtils);
	private MidiServer midiServer = new MidiServerUnix(midiServerGeneral);
	private MidiService midiService = new MidiServiceImpl(midiServer);
	private ConnectionManager connectionManager =
			new ConnectionManagerJsscImpl(operativeSystemManager, midiService);
	private DeviceManager deviceManager = new DeviceManager();
	private DeviceManagerService deviceManagerService =
			new DeviceManagerServiceImpl(connectionManager, deviceManager);
	
	private PropertiesManager propertiesManager = new PropertiesManager();
	private ConfigurationManager configurationManager =
			new ConfigurationManager(propertiesManager);
	private I18nManager i18nManager =
			new I18nManager(configurationManager, operativeSystemManager,
					propertiesManager);
	private ConfigurationApplicationService confAppService =
			new ConfigurationApplicationServiceImpl(i18nManager,
					deviceManagerService);
	
	/**
	 * Feature: user configures a device
	 */
	
	/**
	 * Scenario: user applies a configuration
	 * Given user change some MIDI related value
	 * When user applies a configuration
	 * Then MIDI service is reconfigured
	 */
	@Test
	public void applyConfiguration() {
		
		// Given
		int expectedTuningFrequency = 442;
		confAppService.setTuningFrequency(expectedTuningFrequency);
		
		// When
		MidiServerConfiguration expectedMidiServerConfiguration =
				confAppService.getMidiServerConfiguration();
		assertEquals(expectedTuningFrequency,
				expectedMidiServerConfiguration.getTuningFrequency());
		midiService.setConfiguration(expectedMidiServerConfiguration);
		midiService.restart();
		
		// Then
		MidiServerConfiguration midiServiceConfiguration =
				midiService.getConfiguration();
		assertEquals(expectedTuningFrequency,
				midiServiceConfiguration.getTuningFrequency());
	}
}
