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
	
	public abstract List<String> getCommand(MidiServerConfiguration configuration);
	
}
