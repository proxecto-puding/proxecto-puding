package org.proxectopuding.gui.model.entities.midiServer;

import java.util.ArrayList;
import java.util.List;

public class MidiServerMacOs implements MidiServer {

	private static final String EXECUTABLE_PATH = "/usr/bin/timidity";
	
	public MidiServerMacOs(MidiServerGeneral midiServer) {
		
	}

	@Override
	public List<String> getCommand(String portName) {
		
		List<String> command = new ArrayList<String>();
		
		// Executable.
		command.add(EXECUTABLE_PATH);
		// MIDI port name.
		command.add(portName);
		
		return command;
	}

}
