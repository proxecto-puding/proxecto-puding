package main.model.entities;

import java.io.IOException;
import java.util.List;

import main.model.utils.SoundFontManager;

public abstract class MidiServer {
	
	protected static String path;
	protected static String tempPath;
	
	static {
		setMidiServer();
		checkSoundFontFile();
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
	
	/**
	 * Check if the SoundFont file is ready to use.
	 */
	private static void checkSoundFontFile() {
		try {
			if (!isSoundFontFileDownloaded()) {
				downloadSoundFontFile();
			} else if (!isSoundFontFileCopied()) {
				copySoundFontFile();
			}
		} catch (IOException e) {
			System.err.println(
					"Error while checking if the SoundFont file is ready to use." +
					" Message: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * Check if the SoundFont file is downloaded.
	 * @return A boolean indicating if the file is downloaded.
	 * @throws IOException If the SoundFont file is still being downloaded.
	 */
	public static boolean isSoundFontFileDownloaded() throws IOException {
		
		return SoundFontManager.isSoundFontFileDownloaded();
	}
	
	/**
	 * Download the SoundFont file to use from the Internet.
	 */
	public static void downloadSoundFontFile() {
		
		SoundFontManager.downloadSoundFontFile();
	}
	
	/**
	 * Check if the SoundFont file is placed into the temporal directory.
	 * @return A boolean indicating if the SoundFont is placed into the
	 * destination directory.
	 */
	public static boolean isSoundFontFileCopied() {
		
		return SoundFontManager.isSoundFontFileCopied(tempPath);
	}
	
	/**
	 * Copy the SoundFont file into the temporal directory.
	 * @return The path where the SoundFont file is placed in. Null otherwise.
	 * @throws IOException If a problem comes up when copying the file.
	 */
	public static String copySoundFontFile() throws IOException {
		
		try {
			return SoundFontManager.copySoundFontFile(tempPath);
		} catch (IOException e) {
			System.err.println("Error while copying the SoundFont file to the temporal directory." +
					" Message: " + e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * Generate a list of strings containing the command and the parameters
	 * needed to execute the MIDI server with the provided configuration.
	 * @param configuration Configuration to apply to the MIDI server.
	 * @return A command list.
	 */
	public abstract List<String> getCommand(MidiServerConfiguration configuration);
	
}
