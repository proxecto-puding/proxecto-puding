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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;
import org.proxectopuding.gui.model.entities.BagpipeConfiguration;
import org.proxectopuding.gui.model.entities.BagpipeConfigurationType;
import org.proxectopuding.gui.model.entities.BagpipeDevice;
import org.proxectopuding.gui.model.entities.SelectionConfiguration;
import org.proxectopuding.gui.model.entities.midiServer.MidiServer;
import org.proxectopuding.gui.model.entities.midiServer.MidiServerGeneral;
import org.proxectopuding.gui.model.entities.midiServer.MidiServerUnix;
import org.proxectopuding.gui.model.services.DeviceManagerService;
import org.proxectopuding.gui.model.services.MidiService;
import org.proxectopuding.gui.model.services.impl.DeviceManagerServiceImpl;
import org.proxectopuding.gui.model.services.impl.MidiServiceImpl;
import org.proxectopuding.gui.model.utils.DeviceManager;
import org.proxectopuding.gui.model.utils.FileDownload;
import org.proxectopuding.gui.model.utils.FileDownloader;
import org.proxectopuding.gui.model.utils.FileUtils;
import org.proxectopuding.gui.model.utils.MidiUtils;
import org.proxectopuding.gui.model.utils.OperativeSystemManager;
import org.proxectopuding.gui.model.utils.SoundFontManager;
import org.proxectopuding.gui.model.utils.connection.ConnectionManager;
import org.proxectopuding.gui.model.utils.connection.ConnectionManagerJsscImpl;

public class DeviceManagerServiceAcceptanceTest {
	
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

	/**
	 * Feature: user configures a device
	 */
	
	/**
	 * Scenario: user selects a device
	 * Given devices have been found
	 * When user selects a device
	 * Then device's configurations are loaded
	 * And device figures out as selected
	 */
	@Test
	public void selectDevice() {
		
		// Given
		Set<BagpipeDevice> devices = deviceManagerService.findBagpipeDevices();
		assertTrue(devices.size() > 0);
		String expectedProductId = devices.iterator().next().getProductId();
		
		// When
		deviceManagerService.setSelectedBagpipeDevice(expectedProductId);
		Set<BagpipeConfiguration> configurations =
				deviceManagerService.findBagpipeConfigurations(expectedProductId);
		
		// Then
		BagpipeDevice device = deviceManagerService.getSelectedBagpipeDevice();
		assertEquals(expectedProductId, device.getProductId());
		assertEquals(BagpipeConfigurationType.values().length,
				configurations.size());
		configurations.forEach(configuration ->
				assertEquals(expectedProductId, configuration.getProductId()));
	}
	
	/**
	 * Scenario: user applies a configuration
	 * Given device is selected
	 * And device is configured
	 * When user applies a configuration
	 * Then device is reconfigured
	 */
	@Test
	public void applyConfiguration() {
		
		// Given
		Set<BagpipeDevice> devices = deviceManagerService.findBagpipeDevices();
		assertTrue(devices.size() > 0);
		String expectedProductId = devices.iterator().next().getProductId();
		deviceManagerService.setSelectedBagpipeDevice(expectedProductId);
		Set<BagpipeConfiguration> configurations =
				deviceManagerService.findBagpipeConfigurations(expectedProductId);
		BagpipeDevice device = deviceManagerService.getSelectedBagpipeDevice();
		assertEquals(expectedProductId, device.getProductId());
		assertEquals(BagpipeConfigurationType.values().length,
				configurations.size());
		configurations.forEach(configuration ->
				assertEquals(expectedProductId, configuration.getProductId()));
		BagpipeConfigurationType expectedConfigurationType =
				BagpipeConfigurationType.SELECT;
		BagpipeConfiguration expectedConfiguration = deviceManagerService
				.getBagpipeConfiguration(expectedProductId,
						expectedConfigurationType.name());
		SelectionConfiguration expectedConfigurationData =
				(SelectionConfiguration) expectedConfiguration;
		expectedConfigurationData.setBagEnabled(false);
		
		// When
		deviceManagerService.sendBagpipeConfiguration(expectedConfiguration);
		
		// Then
		BagpipeConfiguration configuration = deviceManagerService
				.findBagpipeConfiguration(expectedConfiguration);
		assertEquals(expectedProductId, configuration.getProductId());
		assertEquals(expectedConfigurationType.name(), configuration.getType());
		SelectionConfiguration configurationData =
				(SelectionConfiguration) configuration;
		assertEquals(expectedConfigurationData.isBagEnabled(),
				configurationData.isBagEnabled());
	}
}
