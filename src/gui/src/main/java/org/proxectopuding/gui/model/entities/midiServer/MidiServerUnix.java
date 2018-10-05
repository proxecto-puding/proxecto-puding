/*
 * Proxecto Puding
 * Copyright (C) 2011 Alejo Pacín <info@proxecto-puding.org>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.proxectopuding.gui.model.entities.midiServer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.proxectopuding.gui.model.entities.PreciseTuning;

public class MidiServerUnix implements MidiServer {
	
	private static final Logger LOGGER = Logger.getLogger(MidiServerUnix.class.getName());
	
	private static final String REAL_SAMPLES = "--config-file=";
	private static final String FREQ_TABLE = "--freq-table=";
	private static final String VIBRATO = "--vibrato";
	private static final String CHORUS = "--chorus=n";
	private static final String DEF_CONFIG_FILE_PATH =
			"/etc/timidity/timidity.cfg";
	private static final String EXECUTABLE_PATH = "/usr/bin/timidity";
	private static final String TEMP_CONFIG_FILE_PATH = "/tmp";
	
	private final MidiServerGeneral midiServer;
	private String configFilePath;
	private String tablePath;
	
	public MidiServerUnix(MidiServerGeneral midiServer) {
		
		this.midiServer = midiServer;
		this.midiServer.setTempConfigFilePath(TEMP_CONFIG_FILE_PATH);
	}
	
	@Override
	public boolean isSoundFontFileDownloaded() throws IOException {
		
		return midiServer.isSoundFontFileDownloaded();
	}
	
	@Override
	public void downloadSoundFontFile() {
		
		midiServer.downloadSoundFontFile();
	}
	
	@Override
	public boolean isSoundFontFileCopied() {
		
		return midiServer.isSoundFontFileCopied();
	}
	
	@Override
	public String copySoundFontFile() throws IOException {
		
		return midiServer.copySoundFontFile();
	}

	@Override
	public MidiServerConfiguration getConfiguration() {
		
		return midiServer.getConfiguration();
	}
	
	@Override
	public void setConfiguration(MidiServerConfiguration configuration) {
		
		midiServer.setConfiguration(configuration);
	}
	
	@Override
	public List<String> getCommand(String portName) {
		
		List<String> command = new ArrayList<String>();
		
		// Executable.
		command.add(EXECUTABLE_PATH);
		// MIDI port name.
		// TODO Remove this line when the original portName sends MIDI.
		portName = this.getClass().getClassLoader().getResource("midi/chantada.midi").getPath();
		command.add(portName);
		
		if (getConfiguration() != null) {
			// Real samples.
			if (getConfiguration().useRealSamples()) {
				configFilePath = getRealSamplesConfigFilePath();
				if (configFilePath != null) {
					command.add(REAL_SAMPLES + configFilePath);
				}
			}
			
			// Tuning mode, tuning frequency and precise tunings.
			tablePath = getFrequencyTablePath(getConfiguration());
			if (tablePath != null) {
				command.add(FREQ_TABLE + tablePath);
			}
			
			// Continuous vibrato.
			if (getConfiguration().useContinuousVibrato()) {
				// Only for hardware not supporting manual vibrato.
				command.add(VIBRATO);
				command.add(CHORUS);
			}
		}
		
		return command;
	}
	
	/**
	 * Configure the MIDI server configuration file to use real samples
	 * returning the path to the custom configuration file to use.
	 * @return The path to custom configuration file to use.
	 */
	private String getRealSamplesConfigFilePath() {
		
		String realSamplesConfigFilePath = null;
		
		try {
			// Download the SoundFont file.
			if (!isSoundFontFileDownloaded()) {
				downloadSoundFontFile();
			}
			
			// Copy the SoundFont file to the temporal path.
			String soundFontFilePath = null;
			if (!isSoundFontFileCopied()) {
				soundFontFilePath = copySoundFontFile();
			}
			
			// Copy the timidity.cfg of the system to the temporal path.
			realSamplesConfigFilePath = copyDefaultConfigFile();
				
			if (realSamplesConfigFilePath != null) {
				// Modify the timidity.cfg to use the SoundFont file.
				setSoundFontSource(realSamplesConfigFilePath,
						soundFontFilePath);
			}
		} catch (Exception e) {
			realSamplesConfigFilePath = null;
			LOGGER.log(Level.SEVERE, "Unable to get the real samples configuration file path", e);
		}
		
		return realSamplesConfigFilePath;
	}
	
	/**
	 * Copy the timidity.cfg of the system to the temporal path.
	 * @return The path to the timidity.cfg copy.
	 * @throws IOException If a problem comes up when copying the file.
	 */
	private String copyDefaultConfigFile() throws IOException {
		
		String tempConfigFilePath = null;
		
		try {
			tempConfigFilePath = midiServer.getFileUtils().copyFileToDirectory(
					DEF_CONFIG_FILE_PATH, TEMP_CONFIG_FILE_PATH);
		} catch (IOException e) {
			tempConfigFilePath = null;
			LOGGER.log(Level.SEVERE, "Unable to copy the default config file to the temporal directory", e);
			throw e;
		}
		
		return tempConfigFilePath;
	}
	
	/**
	 * Configure the MIDI server configuration file to use the provided
	 * SoundFont source file.
	 * @param configFilePath Path to the MIDI server configuration file.
	 * @param soundFontFilePath Path to the SoundFont source file.
	 * @return A boolean indicating if the configuration was fine.
	 * @throws IOException If a problem comes up while managing the
	 * configuration file.
	 */
	private boolean setSoundFontSource(String configFilePath,
			String soundFontFilePath) throws IOException {
		
		boolean  isSet = false;
		
		try {
			// Disable other sound sources.
			disableOtherSoundFontSources(configFilePath);
			
			// Enable custom SoundFont source.
			enableCustomSoundFontSource(configFilePath, soundFontFilePath);
			
			isSet = true;
			
		} catch (IOException e) {
			isSet = false;
			LOGGER.log(Level.SEVERE, "Unable to set the SoundFont source", e);
			throw e;
		}
		
		return isSet;
	}
	
	/**
	 * Disable other SoundFont sources present in the MIDI server configuration
	 * file.
	 * @param configFilePath Path to the MIDI server configuration file.
	 * @throws IOException If a problem comes up while managing the
	 * configuration file.
	 */
	private void disableOtherSoundFontSources(String configFilePath)
			throws IOException {
		
		try {
			File configFile = new File(configFilePath);
			List<String> lines = midiServer.getFileUtils().readLines(configFile);
			
			// Disable other sound sources.
			for (String line : lines) {
				if (line.startsWith("source")) {
					line = "#" + line;
				}
			}
			
			midiServer.getFileUtils().writeLines(configFile, lines, false);
			
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Unable to disable other SoundFont sources", e);
			throw e;
		}	
	}
	
	/**
	 * Enable a custom SoundFont source in the MIDI server configuration file.
	 * @param configFilePath Path to the MIDI server configuration file.
	 * @param soundFontFilePath Path to the SoundFont source file.
	 * @throws IOException If a problem comes up while managing the
	 * configuration file.
	 */
	private void enableCustomSoundFontSource(String configFilePath,
			String soundFontFilePath) throws IOException {
		
		try {
			File configFile = new File(configFilePath);
			List<String> lines = midiServer.getFileUtils().readLines(configFile);
			
			// Enable custom SoundFont source.
			String soundFontDirectory =
					soundFontFilePath.substring(0,
							soundFontFilePath.lastIndexOf(File.separator)+1);
			String soundFontFile = soundFontFilePath.substring(
					soundFontFilePath.lastIndexOf(File.separator));
			lines.add("dir " + soundFontDirectory);
			lines.add("soundfont " + soundFontFile);
			
			midiServer.getFileUtils().writeLines(configFile, lines, false);
			
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Unable to enable a custom SoundFont source", e);
			throw e;
		}
	}
	
	/**
	 * Generate a custom tuning frequency table based on the provided
	 * configuration returning the path to the file containing the table.
	 * @param configuration MIDI server configuration.
	 * @return The path to file containing the frequency table.
	 */
	private String getFrequencyTablePath(
			MidiServerConfiguration configuration) {
		
		String frequencyTablePath = null;
		
		int tone = configuration.getTuningTone();
		int octave = configuration.getTuningOctave();
		int frequency = configuration.getTuningFrequency();
		boolean usePureIntonation = configuration.usePureIntonationMode();
		Set<PreciseTuning> preciseTunings = configuration.getPreciseTunings();
		
		frequencyTablePath = midiServer.getMidiUtils().generateFrequencyTable(
				tone, octave, frequency, usePureIntonation, preciseTunings,
				TEMP_CONFIG_FILE_PATH);
		
		return frequencyTablePath;
	}
}
