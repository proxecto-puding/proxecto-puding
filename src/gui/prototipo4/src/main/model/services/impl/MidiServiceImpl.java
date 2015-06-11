package main.model.services.impl;

import java.io.IOException;
import java.util.List;

import main.model.entities.MidiServer;
import main.model.entities.MidiServerConfiguration;
import main.model.services.MidiService;

public class MidiServiceImpl implements MidiService {
	
	private static MidiServer server;
	private static ProcessBuilder builder;
	private static Process process;
	
	static {
		server = MidiServer.getInstance();
	};

	@Override
	public Process start(MidiServerConfiguration configuration, boolean restart) {
		
		Process process = null;
		
		try {
			if(builder == null || !restart) {
				List<String> command = server.getCommand(configuration);
				builder = new ProcessBuilder().command(command);
			}
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
	public void stop() {
		if (process != null) {
			process.destroy();
		}
	}

}
