package org.proxectopuding.gui.services.integration;

import static org.junit.Assert.assertFalse;
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

public class MidiServiceIntegrationTest {
	
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
	
	@Test
	public void start() {
		
		// When
		Process process = midiService.start();
		
		// Then
		assertNotNull(process);
		assertTrue(process.isAlive());
		process.destroy();
	}
	
	@Test
	public void restart() {
		
		// When
		Process process = midiService.start();
		delay(1);
		process = midiService.restart();
		
		// Then
		assertNotNull(process);
		assertTrue(process.isAlive());
		process.destroy();
	}
	
	@Test
	public void stop() {
		
		// When
		Process process = midiService.start();
		delay(1);
		midiService.stop();
		
		// Then
		assertNotNull(process);
		delay(1);
		assertFalse(process.isAlive());
	}
	
	@Test
	public void getConfiguration() {
		
		// When
		MidiServerConfiguration configuration = midiService.getConfiguration();
		
		// Then
		assertNotNull(configuration);
	}
	
	@Test
	public void setConfiguration() {
		
		// When
		midiService.setConfiguration(CONFIGURATION);
	}
	
	private void delay(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			fail(e.getMessage());
		}
	}
}
