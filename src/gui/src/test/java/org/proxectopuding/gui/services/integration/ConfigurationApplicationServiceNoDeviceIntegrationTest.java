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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;
import org.proxectopuding.gui.model.entities.FingeringOffset;
import org.proxectopuding.gui.model.entities.midiServer.MidiServer;
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

public class ConfigurationApplicationServiceNoDeviceIntegrationTest {
	
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

	@Test
	public void getReadingTones() {
		
		// When
		List<String> readingTones = confAppService.getReadingTones();
		
		// Then
		assertTrue(readingTones.size() > 0);
	}
	
	@Test
	public void getReadingTone() {
		
		// Given
		List<String> readingTones = confAppService.getReadingTones();
		assertTrue(readingTones.size() > 0);
		String expectedReadingTone = readingTones.get(0);
		confAppService.setReadingTone(expectedReadingTone);
	
		// When
		String readingTone = confAppService.getReadingTone();
		
		// Then
		assertEquals(expectedReadingTone, readingTone);
	}
	
	@Test
	public void setReadingTone() {
		
		// Given
		List<String> readingTones = confAppService.getReadingTones();
		assertTrue(readingTones.size() > 0);
		String expectedReadingTone = readingTones.get(0);
	
		// When
		confAppService.setReadingTone(expectedReadingTone);
		
		// Then
		String readingTone = confAppService.getReadingTone();
		assertEquals(expectedReadingTone, readingTone);
	}
	
	@Test
	public void getTuningTones() {
		
		// When
		List<String> tuningTones = confAppService.getTuningTones();
		
		// Then
		assertTrue(tuningTones.size() > 0);
	}
	
	@Test
	public void getTuningToneAsString() {
		try {
			// Given
			List<String> tuningTones = confAppService.getTuningTones();
			assertTrue(tuningTones.size() > 0);
			String expectedTuningToneAsString = tuningTones.get(0);
			int tuningToneAsInt =
					confAppService.getTuningTone(expectedTuningToneAsString);
			
			// When
			String tuningToneAsString =
					confAppService.getTuningTone(tuningToneAsInt);
			
			// Then
			assertEquals(expectedTuningToneAsString, tuningToneAsString);
			
		} catch (IllegalArgumentException e) {
			fail(e.getMessage());
		}
	}
		
	@Test
	public void getTuningToneAsInt() {
		try {
			// Given
			List<String> tuningTones = confAppService.getTuningTones();
			assertTrue(tuningTones.size() > 0);
			String expectedTuningToneAsString = tuningTones.get(0);
			
			// When
			int tuningToneAsInt =
					confAppService.getTuningTone(expectedTuningToneAsString);
			
			// Then
			String tuningToneAsString =
					confAppService.getTuningTone(tuningToneAsInt);
			assertEquals(expectedTuningToneAsString, tuningToneAsString);
			
		} catch (IllegalArgumentException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void getDefaultTuningTone() {
	
		// When
		String defaultTuningTone = confAppService.getDefaultTuningTone();
		
		// Then
		assertNotNull(defaultTuningTone);
	}
	
	@Test
	public void getTuningOctaves() {
		
		// When
		List<Integer> tuningOctaves = confAppService.getTuningOctaves();
		
		// Then
		assertTrue(tuningOctaves.size() > 0);
	}
	
	@Test
	public void getDefaultTuningOctave() {
		
		// When
		int defaultTuningOctave = confAppService.getDefaultTuningOctave();
		
		// Then
		assertEquals(defaultTuningOctave, 4);
	}
	
	@Test
	public void getSamples() {
		
		// When
		List<String> samples = confAppService.getSamples();
		
		// Then
		assertTrue(samples.size() > 0);
	}
	
	@Test
	public void getDefaultFingeringTypesEnabled() {
		
		// When
		List<Boolean> defaultFingeringTypesEnabled =
				confAppService.getDefaultFingeringTypesEnabled();
		
		// Then
		assertTrue(defaultFingeringTypesEnabled.size() > 0);
	}

	@Test
	public void getSample() {
		
		// Given
		List<String> samples = confAppService.getSamples();
		assertTrue(samples.size() > 0);
		String expectedSample = samples.get(0);
		confAppService.setSample(expectedSample);
		
		// When
		String sample = confAppService.getSample();
		
		// Then
		assertEquals(expectedSample, sample);
	}
	
	@Test
	public void setSample() {
		
		// Given
		List<String> samples = confAppService.getSamples();
		assertTrue(samples.size() > 0);
		String expectedSample = samples.get(0);
		
		// When
		confAppService.setSample(expectedSample);
		
		// Then
		String sample = confAppService.getSample();
		assertEquals(expectedSample, sample);
	}
	
	@Test
	public void isDefaultBagEnabled() {
		
		// When
		boolean isDefaultBagEnabled = confAppService.isDefaultBagEnabled();
		
		// Then
		assertTrue(isDefaultBagEnabled);
	}

	@Test
	public void getDefaultDronesEnabled() {
		
		// When
		List<Boolean> defaultDronesEnabled =
				confAppService.getDefaultDronesEnabled();
		
		// Then
		assertTrue(defaultDronesEnabled.size() > 0);
	}

	@Test
	public void getTuningFrequency() {
		
		// Given
		int expectedTuningFrequency = 440;
		confAppService.setTuningFrequency(440);
		
		// When
		int tuningFrequency = confAppService.getTuningFrequency();
		
		// Then
		assertEquals(expectedTuningFrequency, tuningFrequency);
	}
	
	@Test
	public void setTuningFrequency() {
		
		// Given
		int expectedTuningFrequency = 440;
		
		// When
		confAppService.setTuningFrequency(440);
		
		// Then
		int tuningFrequency = confAppService.getTuningFrequency();
		assertEquals(expectedTuningFrequency, tuningFrequency);
	}

	@Test
	public void getTuningModes() {
		
		// When
		List<String> tuningModes = confAppService.getTuningModes();
		
		// Then
		assertTrue(tuningModes.size() > 0);
	}
	
	@Test
	public void getTuningMode() {
		
		// Given
		List<String> tuningModes = confAppService.getTuningModes();
		assertTrue(tuningModes.size() > 0);
		String expectedTuningMode = confAppService.getTuningMode();
		confAppService.setTuningMode(expectedTuningMode);
		
		// When
		String tuningMode = confAppService.getTuningMode();
		
		// Then
		assertEquals(expectedTuningMode, tuningMode);
	}
	
	@Test
	public void setTuningMode() {
		
		// Given
		List<String> tuningModes = confAppService.getTuningModes();
		assertTrue(tuningModes.size() > 0);
		String expectedTuningMode = confAppService.getTuningMode();
		
		// When
		confAppService.setTuningMode(expectedTuningMode);
		
		// Then
		String tuningMode = confAppService.getTuningMode();
		assertEquals(expectedTuningMode, tuningMode);
	}

	@Test
	public void getDefaultTuningMode() {
		
		// When
		String defaultTuningMode = confAppService.getDefaultTuningMode();
		
		// Then
		assertNotNull(defaultTuningMode);
	}

	@Test
	public void getPreciseTuningNotes() {
		
		// When
		List<String> preciseTuningNotes = confAppService.getPreciseTuningNotes();
		
		// Then
		assertTrue(preciseTuningNotes.size() > 0);
	}

	@Test
	public void getPreciseTuningNote() {
	
		// Given
		List<String> preciseTuningNotes = confAppService.getPreciseTuningNotes();
		assertTrue(preciseTuningNotes.size() > 0);
		String expectedPreciseTuningNote = preciseTuningNotes.get(0);
		confAppService.setPreciseTuningNote(expectedPreciseTuningNote);
		
		// When
		String preciseTuningNote = confAppService.getPreciseTuningNote();
		
		// Then
		assertEquals(expectedPreciseTuningNote, preciseTuningNote);
	}

	@Test
	public void setPreciseTuningNoteFromString() {
		
		// Given
		List<String> preciseTuningNotes = confAppService.getPreciseTuningNotes();
		assertTrue(preciseTuningNotes.size() > 0);
		String expectedPreciseTuningNote = preciseTuningNotes.get(0);
		
		// When
		confAppService.setPreciseTuningNote(expectedPreciseTuningNote);
		
		// Then
		String preciseTuningNote = confAppService.getPreciseTuningNote();
		assertEquals(expectedPreciseTuningNote, preciseTuningNote);
	}
	
	@Test
	public void setPreciseTuningNoteFromInt() {
		
		// Given
		List<String> preciseTuningNotes = confAppService.getPreciseTuningNotes();
		assertTrue(preciseTuningNotes.size() > 0);
		int preciseTuningNoteAsInt = 0;
		String expectedPreciseTuningNote =
				preciseTuningNotes.get(preciseTuningNoteAsInt);
		
		// When
		confAppService.setPreciseTuningNote(preciseTuningNoteAsInt);
		
		// Then
		String preciseTuningNote = confAppService.getPreciseTuningNote();
		assertEquals(expectedPreciseTuningNote, preciseTuningNote);
	}

	@Test
	public void resetPreciseTuningNotes() {
		
		// Given
		List<String> preciseTuningNotes = confAppService.getPreciseTuningNotes();
		assertTrue(preciseTuningNotes.size() > 0);
		String expectedPreciseTuningNote = preciseTuningNotes.get(0);
		confAppService.setPreciseTuningNote(expectedPreciseTuningNote);
		
		// When
		preciseTuningNotes = confAppService.resetPreciseTuningNotes();
		
		// Then
		assertTrue(preciseTuningNotes.size() > 0);
		String preciseTuningNote = confAppService.getPreciseTuningNote();
		assertEquals(expectedPreciseTuningNote, preciseTuningNote);
	}

	@Test
	public void getPreciseTuningOctaves() {
	
		// When
		List<Integer> preciseTuningOctaves =
				confAppService.getPreciseTuningOctaves();
		
		// Then
		assertTrue(preciseTuningOctaves.size() > 0);
	}

	@Test
	public void getPreciseTuningOctave() {
		
		// Given
		List<Integer> preciseTuningOctaves =
				confAppService.getPreciseTuningOctaves();
		assertTrue(preciseTuningOctaves.size() > 0);
		int expectedPreciseTuningOctave = preciseTuningOctaves.get(0);
		confAppService.setPreciseTuningOctave(expectedPreciseTuningOctave);
		
		// When
		int preciseTuningOctave = confAppService.getPreciseTuningOctave();
		
		// Then
		assertEquals(expectedPreciseTuningOctave, preciseTuningOctave);
	}

	@Test
	public void setPreciseTuningOctave() {
		
		// Given
		List<Integer> preciseTuningOctaves =
				confAppService.getPreciseTuningOctaves();
		assertTrue(preciseTuningOctaves.size() > 0);
		int expectedPreciseTuningOctave = preciseTuningOctaves.get(0);
		
		// When
		confAppService.setPreciseTuningOctave(expectedPreciseTuningOctave);
		
		// Then
		int preciseTuningOctave = confAppService.getPreciseTuningOctave();
		assertEquals(expectedPreciseTuningOctave, preciseTuningOctave);
	}

	@Test
	public void getPreciseTuningCents() {
		
		// Given
		int expectedPreciseTuningCents = 10;
		confAppService.setPreciseTuningCents(expectedPreciseTuningCents);
		
		// When
		int preciseTuningCents = confAppService.getPreciseTuningCents();
		
		// Then
		assertEquals(expectedPreciseTuningCents, preciseTuningCents);
	}

	@Test
	public void setPreciseTuningCents() {
		
		// Given
		int expectedPreciseTuningCents = 10;
		
		// When
		confAppService.setPreciseTuningCents(expectedPreciseTuningCents);
		
		// Then
		int preciseTuningCents = confAppService.getPreciseTuningCents();
		assertEquals(expectedPreciseTuningCents, preciseTuningCents);
	}
	
	@Test
	public void getCustomFingeringNotes() {
		
		// When
		List<String> customFingeringNotes =
				confAppService.getCustomFingeringNotes();
		
		// Then
		assertTrue(customFingeringNotes.size() > 0);
	}
	
	@Test
	public void getCustomFingeringNote() {
		
		// Given
		List<String> customFingeringNotes =
				confAppService.getCustomFingeringNotes();
		assertTrue(customFingeringNotes.size() > 0);
		String expectedCustomFingeringNote = customFingeringNotes.get(0);
		confAppService.setCustomFingeringNote(expectedCustomFingeringNote);
		
		// When
		String customFingeringNote = confAppService.getCustomFingeringNote();
		
		// Then
		assertEquals(expectedCustomFingeringNote, customFingeringNote);
	}
	
	@Test
	public void setCustomFingeringNoteFromString() {
		
		// Given
		List<String> customFingeringNotes =
				confAppService.getCustomFingeringNotes();
		assertTrue(customFingeringNotes.size() > 0);
		String expectedCustomFingeringNote = customFingeringNotes.get(0);
		
		// When
		confAppService.setCustomFingeringNote(expectedCustomFingeringNote);
		
		// Then
		String customFingeringNote = confAppService.getCustomFingeringNote();
		assertEquals(expectedCustomFingeringNote, customFingeringNote);
	}
	
	@Test
	public void setCustomFingeringNoteFromInt() {
		
		// Given
		List<String> customFingeringNotes =
				confAppService.getCustomFingeringNotes();
		assertTrue(customFingeringNotes.size() > 0);
		String expectedCustomFingeringNote = customFingeringNotes.get(0);
		int customFingeringNoteFromInt = 0;
		
		// When
		confAppService.setCustomFingeringNote(customFingeringNoteFromInt);
		
		// Then
		String customFingeringNote = confAppService.getCustomFingeringNote();
		assertEquals(expectedCustomFingeringNote, customFingeringNote);
	}
	
	@Test
	public void resetCustomFingeringNotes() {
		
		// Given
		List<String> customFingeringNotes =
				confAppService.getCustomFingeringNotes();
		assertTrue(customFingeringNotes.size() > 0);
		String expectedCustomFingeringNote = customFingeringNotes.get(0);
		confAppService.setCustomFingeringNote(expectedCustomFingeringNote);
		
		// When
		customFingeringNotes = confAppService.resetCustomFingeringNotes();
		
		// Then
		assertTrue(customFingeringNotes.size() > 0);
		String customFingeringNote = confAppService.getCustomFingeringNote();
		assertEquals(expectedCustomFingeringNote, customFingeringNote);
	}

	@Test
	public void getCustomFingeringOctaves() {
		
		// When
		List<Integer> customFingeringOctaves =
				confAppService.getCustomFingeringOctaves();
		
		// Then
		assertTrue(customFingeringOctaves.size() > 0);
	}

	@Test
	public void getCustomFingeringOctave() {

		// Given
		List<Integer> customFingeringOctaves =
				confAppService.getCustomFingeringOctaves();
		assertTrue(customFingeringOctaves.size() > 0);
		int expectedCustomFingeringOctave = customFingeringOctaves.get(0);
		confAppService.setCustomFingeringOctave(expectedCustomFingeringOctave);
		
		// When
		int customFingeringOctave = confAppService.getCustomFingeringOctave();
		
		// Then
		assertEquals(expectedCustomFingeringOctave, customFingeringOctave);
	}

	@Test
	public void setCustomFingeringOctave() {
		
		// Given
		List<Integer> customFingeringOctaves =
				confAppService.getCustomFingeringOctaves();
		assertTrue(customFingeringOctaves.size() > 0);
		int expectedCustomFingeringOctave = customFingeringOctaves.get(0);
		
		// When
		confAppService.setCustomFingeringOctave(expectedCustomFingeringOctave);
		
		// Then
		int customFingeringOctave = confAppService.getCustomFingeringOctave();
		assertEquals(expectedCustomFingeringOctave, customFingeringOctave);
	}

	@Test
	public void addCustomFingeringNumber() {
		
		// When
		int customFingeringNumber = confAppService.addCustomFingeringNumber();
		
		// Then
		FingeringOffset customFingering =
				confAppService.getCustomFingering(customFingeringNumber);
		assertNotNull(customFingering);
	}

	@Test
	public void getCustomFingering() {
		
		// Given
		int customFingeringNumber = confAppService.addCustomFingeringNumber();
				
		// When
		FingeringOffset customFingering =
				confAppService.getCustomFingering(customFingeringNumber);
		
		// Then
		assertNotNull(customFingering);
	}

	@Test
	public void removeCustomFingeringNumber() {
		
		// Given
		int customFingeringNumber = confAppService.addCustomFingeringNumber();
		FingeringOffset customFingering =
				confAppService.getCustomFingering(customFingeringNumber);
		assertNotNull(customFingering);
		
		// When
		confAppService.removeCustomFingeringNumber(customFingeringNumber);
		
		// Then
		customFingering =
				confAppService.getCustomFingering(customFingeringNumber);
		assertNull(customFingering);
	}
}
