package org.proxectopuding.gui.model.entities.midiServer;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.proxectopuding.gui.model.utils.FileUtils;
import org.proxectopuding.gui.model.utils.MidiUtils;
import org.proxectopuding.gui.model.utils.OperativeSystemManager;
import org.proxectopuding.gui.model.utils.SoundFontManager;

import com.google.inject.Inject;

public class MidiServerGeneral implements MidiServer {
	
	private static final Logger LOGGER = Logger.getLogger(MidiServerGeneral.class.getName());
	
	private final SoundFontManager soundFontManager;
	private final OperativeSystemManager operativeSystemManager;
	private final FileUtils fileUtils;
	private final MidiUtils midiUtils;
	private MidiServer midiServer;
	private MidiServerConfiguration configuration;
	
	private String tempConfigFilePath;
	
	@Inject
	public MidiServerGeneral(SoundFontManager soundFontManager,
			OperativeSystemManager operativeSystemManager,
			FileUtils fileUtils, MidiUtils midiUtils) {
		
		this.soundFontManager = soundFontManager;
		this.operativeSystemManager = operativeSystemManager;
		this.fileUtils = fileUtils;
		this.midiUtils = midiUtils;
		this.configuration = new MidiServerConfiguration();
		setMidiServer();
		checkSoundFontFile();
	}
	
	public boolean isSoundFontFileDownloaded() throws IOException {
		
		return soundFontManager.isSoundFontFileDownloaded();
	}
	
	public void downloadSoundFontFile() {
		
		soundFontManager.downloadSoundFontFile();
	}
	
	public boolean isSoundFontFileCopied() {
		
		return soundFontManager.isSoundFontFileCopied(tempConfigFilePath);
	}
	
	public String copySoundFontFile() throws IOException {
		
		try {
			return soundFontManager.copySoundFontFile(tempConfigFilePath);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Unable to copy the SoundFont file to the temporal directory", e);
			throw e;
		}
	}
	
	public MidiServerConfiguration getConfiguration() {
		return configuration;
	}
	
	public void setConfiguration(MidiServerConfiguration configuration) {
		
		this.configuration = configuration;
	}
	
	public List<String> getCommand() {
		
		return midiServer.getCommand();
	}
	
	/**
	 * Set the path where to copy the temporal configuration file and relatives.
	 * @param tempConfigFilePath Temporal path.
	 */
	protected void setTempConfigFilePath(String tempConfigFilePath) {
		
		this.tempConfigFilePath = tempConfigFilePath;
	}
	
	/**
	 * Get a reference to the file utils.
	 * @return A reference to the file utils.
	 */
	protected FileUtils getFileUtils() {
		
		return fileUtils;
	}
	
	/**
	 * Get a reference to the MIDI utils.
	 * @return A reference to the MIDI utils.
	 */
	protected MidiUtils getMidiUtils() {
		
		return midiUtils;
	}
	
	/**
	 * Setup the MIDI server depending on the OS.
	 */
	private void setMidiServer() {
		
		if (midiServer == null) {
			if (operativeSystemManager.isWindows()) {
				midiServer = new MidiServerWindows(this);
			} else if (operativeSystemManager.isMacOs()) {
				midiServer = new MidiServerMacOs(this);
			} else if (operativeSystemManager.isUnix()) {
				midiServer = new MidiServerUnix(this);
			}
		}
	}
	
	/**
	 * Check if the SoundFont file is ready to use.
	 */
	private void checkSoundFontFile() {
		
		try {
			if (!isSoundFontFileDownloaded()) {
				downloadSoundFontFile();
			} else if (!isSoundFontFileCopied()) {
				copySoundFontFile();
			}
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Unable to check if the SoundFont file is ready to use", e);
		}
	}
	
}
