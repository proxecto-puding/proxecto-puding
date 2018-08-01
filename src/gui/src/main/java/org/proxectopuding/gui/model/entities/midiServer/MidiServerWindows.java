package org.proxectopuding.gui.model.entities.midiServer;

import java.util.ArrayList;
import java.util.List;

public class MidiServerWindows implements MidiServer {
	
	private static final String EXECUTABLE_PATH = "c:\\timidity\\timw32g.exe";
	
	public MidiServerWindows(MidiServerGeneral midiServer) {
		
	}

	@Override
	public List<String> getCommand() {
		
		List<String> command = new ArrayList<String>();
		
		// Executable.
		command.add(EXECUTABLE_PATH);
		
		return command;
	};

}