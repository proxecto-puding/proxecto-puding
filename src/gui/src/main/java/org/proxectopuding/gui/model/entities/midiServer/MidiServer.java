package org.proxectopuding.gui.model.entities.midiServer;

import java.io.IOException;
import java.util.List;

public interface MidiServer {

	/**
	 * Check if the SoundFont file is downloaded.
	 * @return A boolean indicating if the file is downloaded.
	 * @throws IOException If the SoundFont file is still being downloaded.
	 */
	default boolean isSoundFontFileDownloaded() throws IOException {
		return false;
	}
	
	/**
	 * Download the SoundFont file to use from the Internet.
	 */
	default void downloadSoundFontFile() {
		// Skip.
	}
	
	/**
	 * Check if the SoundFont file is placed into the temporal directory.
	 * @return A boolean indicating if the SoundFont is placed into the
	 * destination directory.
	 */
	default boolean isSoundFontFileCopied() {
		return false;
	}
	
	/**
	 * Copy the SoundFont file into the temporal directory.
	 * @return The path where the SoundFont file is placed in. Null otherwise.
	 * @throws IOException If a problem comes up when copying the file.
	 */
	default String copySoundFontFile() throws IOException {
		return null;
	}
	
	/**
	 * Get the MIDI server configuration.
	 * @return A MIDI server configuration.
	 */
	default MidiServerConfiguration getConfiguration() {
		return null;
	}
	
	/**
	 * Set the MIDI server configuration.
	 * @param configuration A MIDI server configuration.
	 */
	default void setConfiguration(MidiServerConfiguration configuration) {
		// Skip.
	}
	
	/**
	 * Generate a list of strings containing the command and the parameters
	 * needed to execute the MIDI server with the current configuration.
	 * @param portName MIDI port name.
	 * @return A command list.
	 */
	List<String> getCommand(String portName);
	
	
}
