package main.model.entities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import main.model.utils.FileUtils;
import main.model.utils.MidiUtils;

public class MidiServerUnix extends MidiServer {
	
	private static final String REAL_SAMPLES = "--config-file=";
	private static final String FREQ_TABLE = "--freq-table=";
	private static final String VIBRATO = "--vibrato";
	private static final String CHORUS = "--chorus=n";
	private static final String DEF_CONFIG_FILE_PATH =
			"/etc/timidity/timidity.cfg";
	
	private static String configFilePath;
	private static String tablePath;
	
	static {
		path = "/usr/bin/timidity";
		tempPath = "/tmp";
	};

	@Override
	public List<String> getCommand(MidiServerConfiguration configuration) {
		
		List<String> command = new ArrayList<String>();
		
		// Executable.
		command.add(path);
		
		if (configuration != null) {
			// Real samples.
			if (configuration.useRealSamples()) {
				configFilePath = getRealSamplesConfigFilePath();
				if (configFilePath != null) {
					command.add(REAL_SAMPLES + configFilePath);
				}
			}
			
			// Tuning mode, tuning frequency and precise tunings.
			tablePath = getFrequencyTablePath(configuration);
			if (tablePath != null) {
				command.add(FREQ_TABLE + tablePath);
			}
			
			// Continuous vibrato.
			if (configuration.useContinuousVibrato()) {
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
			System.err.println(
					"Error while getting the real samples configuration file path." +
					" Message: " + e.getMessage());
			e.printStackTrace();
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
			tempConfigFilePath = FileUtils.copyFileToDirectory(
					DEF_CONFIG_FILE_PATH, tempPath);
		} catch (IOException e) {
			tempConfigFilePath = null;
			System.err.println("Error while copying the default config file to the temporal directory." +
					" Message: " + e.getMessage());
			e.printStackTrace();
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
			System.err.println(
					"Error while setting the SoundFont source." +
					" Message: " + e.getMessage());
			e.printStackTrace();
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
			List<String> lines = FileUtils.readLines(configFile);
			
			// Disable other sound sources.
			for (String line : lines) {
				if (line.startsWith("source")) {
					line = "#" + line;
				}
			}
			
			FileUtils.writeLines(configFile, lines, false);
			
		} catch (IOException e) {
			System.err.println(
					"Error while disabling other SoundFont sources." +
					" Message: " + e.getMessage());
			e.printStackTrace();
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
			List<String> lines = FileUtils.readLines(configFile);
			
			// Enable custom SoundFont source.
			String soundFontDirectory =
					soundFontFilePath.substring(0,
							soundFontFilePath.lastIndexOf(File.separator)+1);
			String soundFontFile = soundFontFilePath.substring(
					soundFontFilePath.lastIndexOf(File.separator));
			lines.add("dir " + soundFontDirectory);
			lines.add("soundfont " + soundFontFile);
			
			FileUtils.writeLines(configFile, lines, false);
			
		} catch (IOException e) {
			System.err.println(
					"Error while enabling a custom SoundFont source." +
					" Message: " + e.getMessage());
			e.printStackTrace();
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
		
		frequencyTablePath = MidiUtils.generateFrequencyTable(tone, octave,
				frequency, usePureIntonation, preciseTunings, tempPath);
		
		return frequencyTablePath;
	}

}
