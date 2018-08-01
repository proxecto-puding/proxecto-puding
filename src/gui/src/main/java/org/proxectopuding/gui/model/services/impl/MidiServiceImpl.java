package org.proxectopuding.gui.model.services.impl;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.proxectopuding.gui.model.entities.midiServer.MidiServer;
import org.proxectopuding.gui.model.entities.midiServer.MidiServerConfiguration;
import org.proxectopuding.gui.model.services.MidiService;

import com.google.inject.Inject;

public class MidiServiceImpl implements MidiService {
	
	private static final Logger LOGGER = Logger.getLogger(MidiServiceImpl.class.getName());
	
	private final MidiServer midiServer;
	private ProcessBuilder processBuilder;
	private Process process;
	
	@Inject
	public MidiServiceImpl(MidiServer midiServer) {
		
		this.midiServer = midiServer;
		this.processBuilder = new ProcessBuilder();
	}

	@Override
	public Process start() {
		
		LOGGER.log(Level.INFO, "Starting MIDI server");
		Process process = null;
		
		try {
			List<String> command = midiServer.getCommand();
			processBuilder = processBuilder.command(command);
			process = processBuilder.start();
		} catch (IOException e) {
			process = null;
			LOGGER.log(Level.SEVERE, "Unable to start the MIDI server", e);
		}
		
		return process;
	}
	
	@Override
	public Process restart() {
		LOGGER.log(Level.INFO, "Restarting MIDI server");
		stop();
		return start();
	}

	@Override
	public void stop() {
		LOGGER.log(Level.INFO, "Stopping MIDI server");
		if (process != null) {
			process.destroy();
		}
	}
	
	@Override
	public MidiServerConfiguration getConfiguration() {
		LOGGER.log(Level.INFO, "Getting MIDI server configuration");
		return midiServer.getConfiguration();
	}
	
	@Override
	public void setConfiguration(MidiServerConfiguration configuration) {
		LOGGER.log(Level.INFO, "Setting MIDI server configuration: {0}", configuration);
		midiServer.setConfiguration(configuration);
	}

}
