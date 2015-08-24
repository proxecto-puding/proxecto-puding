package main.model.entities;

import java.util.List;

public abstract class MidiServer {
	
	protected static String path;
	
	static {
		setMidiServer();
	};
	
	private static MidiServer server;
	
	protected MidiServer() {
		// Singleton.
	}
	
	public static MidiServer getInstance() {
		return server;
	}
	
	/**
	 * Setup the MIDI server depending on the OS.
	 */
	private static void setMidiServer() {
		if (OperativeSystem.isWindows()) {
			server = new MidiServerWindows();
		} else if (OperativeSystem.isMacOs()) {
			server = new MidiServerMacOs();
		} else if (OperativeSystem.isUnix()) {
			server = new MidiServerUnix();
		}
	}
	
	public String getPath() {
		return path;
	}
	
	/**
	 * Generate a list of strings containing the command and the parameters
	 * needed to execute the MIDI server with the provided configuration.
	 * @param configuration Configuration to apply to the MIDI server.
	 * @return A command list.
	 */
	public abstract List<String> getCommand(MidiServerConfiguration configuration);
	
}
