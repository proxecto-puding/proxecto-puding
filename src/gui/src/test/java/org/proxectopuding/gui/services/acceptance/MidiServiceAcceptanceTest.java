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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.proxectopuding.gui.model.entities.midiServer.MidiServer;
import org.proxectopuding.gui.model.entities.midiServer.MidiServerConfiguration;
import org.proxectopuding.gui.model.entities.midiServer.MidiServerGeneral;
import org.proxectopuding.gui.model.services.MidiService;
import org.proxectopuding.gui.model.services.impl.MidiServiceImpl;
import org.proxectopuding.gui.model.utils.FileDownload;
import org.proxectopuding.gui.model.utils.FileDownloader;
import org.proxectopuding.gui.model.utils.FileUtils;
import org.proxectopuding.gui.model.utils.MidiUtils;
import org.proxectopuding.gui.model.utils.OperativeSystemManager;
import org.proxectopuding.gui.model.utils.SoundFontManager;

public class MidiServiceAcceptanceTest {
	
	private static final MidiServerConfiguration CONFIGURATION =
			new MidiServerConfiguration();

	private FileUtils fileUtils = new FileUtils();
	private FileDownload fileDownload = new FileDownload(fileUtils);
	private FileDownloader fileDownloader = new FileDownloader(fileDownload);
	private SoundFontManager soundFontManager =
			new SoundFontManager(fileDownloader, fileUtils);
	private OperativeSystemManager operativeSystemManager =
			new OperativeSystemManager();
	private MidiUtils midiUtils = new MidiUtils();
	private MidiServer midiServer = new MidiServerGeneral(soundFontManager,
			operativeSystemManager, fileUtils, midiUtils);
	private MidiService midiService = new MidiServiceImpl(midiServer);
	
	/**
	 * Feature: user configures the device
	 */
	
	/**
	 * Scenario: user opens the application
	 * When user opens the application
	 * Then the MIDI server is started
	 */
	@Test
	public void openApplication() {
		
		// When
		Process process = midiService.start();
		
		// Then
		assertNotNull(process);
		assertTrue(process.isAlive());
		process.destroy();
	}
	
	/**
	 * Scenario: user applies a new configuration
	 * When user opens the application
	 * Then the MIDI server is started
	 */
	@Test
	public void applyConfiguration() {
		
		// When
		Process process = midiService.start();
		delay(1);
		midiService.setConfiguration(CONFIGURATION);
		process = midiService.restart();
		MidiServerConfiguration configuration = midiService.getConfiguration();
		
		// Then
		assertNotNull(process);
		assertTrue(process.isAlive());
		assertNotNull(configuration);
		process.destroy();
	}
	
	private void delay(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			fail(e.getMessage());
		}
	}
}
