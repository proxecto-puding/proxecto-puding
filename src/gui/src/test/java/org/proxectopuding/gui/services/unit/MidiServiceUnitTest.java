package org.proxectopuding.gui.services.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.proxectopuding.gui.model.entities.midiServer.MidiServer;
import org.proxectopuding.gui.model.entities.midiServer.MidiServerConfiguration;
import org.proxectopuding.gui.model.services.MidiService;
import org.proxectopuding.gui.model.services.impl.MidiServiceImpl;

import com.google.common.collect.ImmutableList;

public class MidiServiceUnitTest {
	
	private static final List<String> COMMAND = ImmutableList.of("echo");
	private static final MidiServerConfiguration CONFIGURATION =
			new MidiServerConfiguration();
	
	private MidiServer midiServer = mock(MidiServer.class);
	private MidiService midiService = new MidiServiceImpl(midiServer);
	
	@Before
	public void before() {
		
		reset(midiServer);
	}

	@Test
	public void start() {
		
		// Given
		when(midiServer.getCommand()).thenReturn(COMMAND);
		
		// When
		Process process = midiService.start();
		
		// Then
		verify(midiServer, times(1)).getCommand();
		assertNotNull(process);
	}
	
	@Test
	public void restart() {
		
		// Given
		when(midiServer.getCommand()).thenReturn(COMMAND);
		
		// When
		Process process = midiService.restart();
		
		// Then
		verify(midiServer, times(1)).getCommand();
		assertNotNull(process);
		delay(1);
		assertFalse(process.isAlive());
	}
	
	@Test
	public void stop() {
		
		// Given
		when(midiServer.getCommand()).thenReturn(COMMAND);
		
		// When
		Process process = midiService.start();
		midiService.stop();
		
		// Then
		verify(midiServer, times(1)).getCommand();
		assertNotNull(process);
		delay(1);
		assertFalse(process.isAlive());
	}
	
	@Test
	public void getConfiguration() {
		
		// Given
		when(midiServer.getConfiguration()).thenReturn(CONFIGURATION);
		
		// When
		MidiServerConfiguration configuration = midiService.getConfiguration();
		
		// Then
		verify(midiServer, times(1)).getConfiguration();
		assertEquals(CONFIGURATION, configuration);
	}
	
	@Test
	public void setConfiguration() {
		
		// Given
		doNothing().when(midiServer).setConfiguration(CONFIGURATION);
		
		// When
		midiService.setConfiguration(CONFIGURATION);
		
		// Then
		verify(midiServer, times(1)).setConfiguration(CONFIGURATION);
	}
	
	private void delay(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			fail(e.getMessage());
		}
	}
}
