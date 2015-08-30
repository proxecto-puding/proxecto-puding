package main.model.entities;

import java.io.File;
import java.io.IOException;
import java.util.List;

public abstract class MidiServer {
	
	protected static final String SOUNDFONT_URL = "https://goo.gl/uNtY5u";
	private static final String SOUNDFONT_FILE_PATH = "sounds/FluidR3_GM.sf2";
	private static final String SOUNDFONT_FILE_NAME = "FluidR3_GM.sf2";
	protected static String path;
	protected static String tempPath;
	
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
	
	public String getSoundFontUrl() {
		return SOUNDFONT_URL;
	}
	
	public String getSoundFontFilePath() {
		return SOUNDFONT_FILE_PATH;
	}
	
	public String getSoundFontFileName() {
		return SOUNDFONT_FILE_NAME;
	}
	
	/**
	 * Check if the SoundFont file is placed into the temporal directory.
	 * @return The path where the SoundFont file is placed in. Null otherwise.
	 */
	public String isSoundFontFilePlaced() {
		
		String soundFontFilePath = null;
		boolean isPlaced = false;
		
		File tempDirectory = new File(tempPath);
		
		try {
			soundFontFilePath = tempDirectory.getCanonicalPath();
			if (!soundFontFilePath.endsWith(File.separator)) {
				soundFontFilePath += File.separator;
			}
			soundFontFilePath += SOUNDFONT_FILE_NAME;
			File destFile = new File(soundFontFilePath);
			
			isPlaced = destFile.exists();
		} catch (IOException e) {
			isPlaced = false;
			System.err.println(
					"Error while checking the SoundFont file is placed." +
					" Message: " + e.getMessage());
			e.printStackTrace();
		}
		
		if (!isPlaced) {
			soundFontFilePath = null;
		}
		
		return soundFontFilePath;
	}
	
	/**
	 * Check if the SoundFont file is downloaded.
	 * @return A boolean indicating if the file is downloaded.
	 */
	public boolean isSoundFontFileDownloaded() {
		
		boolean isDownloaded = false;
		
		File file = new File(SOUNDFONT_FILE_PATH);
		isDownloaded = file.exists();
		
		return isDownloaded;
	}
	
	/**
	 * Generate a list of strings containing the command and the parameters
	 * needed to execute the MIDI server with the provided configuration.
	 * @param configuration Configuration to apply to the MIDI server.
	 * @return A command list.
	 */
	public abstract List<String> getCommand(MidiServerConfiguration configuration);
	
}
