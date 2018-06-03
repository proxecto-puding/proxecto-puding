package org.proxectopuding.gui.model.services.impl;

import java.io.IOException;
import java.util.List;

import org.proxectopuding.gui.model.entities.MidiServer;
import org.proxectopuding.gui.model.entities.MidiServerConfiguration;
import org.proxectopuding.gui.model.services.MidiService;

public class MidiServiceImpl implements MidiService {
	
	private static MidiServer server;
	private static ProcessBuilder builder;
	private static Process process;
	
	static {
		server = MidiServer.getInstance();
		builder = new ProcessBuilder();
	};

	@Override
	public Process start() {
		
		Process process = null;
		
		try {
			List<String> command = server.getCommand();
			builder = builder.command(command);
			process = builder.start();
		} catch (IOException e) {
			process = null;
			System.err.println("Error while starting the MIDI server." +
					" Message: " + e.getMessage());
			e.printStackTrace();
		}
		
		return process;
	}
	
	@Override
	public Process restart() {
		stop();
		return start();
	}

	@Override
	public void stop() {
		if (process != null) {
			process.destroy();
		}
	}
	
	@Override
	public MidiServerConfiguration getConfiguration() {
		return server.getConfiguration();
	}
	
	@Override
	public void setConfiguration(MidiServerConfiguration configuration) {
		server.setConfiguration(configuration);
	}

}
