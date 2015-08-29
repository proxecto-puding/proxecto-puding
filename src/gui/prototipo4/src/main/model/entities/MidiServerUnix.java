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
	private static final String TEMP_PATH = "/tmp";
	private static final String VIBRATO = "--vibrato";
	private static final String CHORUS = "--chorus=n";
	private static final String SF2_FILE_PATH = "sounds/FluidR3_GM.sf2";
	private static final String DEF_CONFIG_FILE_PATH =
			"/etc/timidity/timidity.cfg";
	
	private static String configFilePath;
	private static String tablePath;
	
	static {
		path = "/usr/bin/timidity";
	};

	@Override
	public List<String> getCommand(MidiServerConfiguration configuration) {
		
		List<String> command = new ArrayList<String>();
		
		// Executable.
		command.add(path);
		
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
			// This option is only for hardware not supporting manual vibrato.
			command.add(VIBRATO);
			command.add(CHORUS);
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
			// Copy the SoundFont file to the temporal path.
			// TODO Test this line carefully. SF2 file could not be found this way.
			String soundFontFilePath =
					FileUtils.copyFileToDirectory(SF2_FILE_PATH, TEMP_PATH);
			// Copy the timidity.cfg of the system to the temporal path.
			realSamplesConfigFilePath = 
				FileUtils.copyFileToDirectory(DEF_CONFIG_FILE_PATH, TEMP_PATH);
			if (realSamplesConfigFilePath != null) {
				// Modify the timidity.cfg to use the SoundFont file.
				setSoundFontSource(realSamplesConfigFilePath,
						soundFontFilePath);
			}
		} catch (IOException e) {
			realSamplesConfigFilePath = null;
			System.err.println(
					"Error while getting the real samples configuration file path." +
					" Message: " + e.getMessage());
			e.printStackTrace();
		}
		
		return realSamplesConfigFilePath;
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
		
		File configFile = new File(configFilePath);
		try {
			List<String> lines = FileUtils.readLines(configFile);
			
			// Disable other sound sources.
			for (String line : lines) {
				if (line.startsWith("source")) {
					line = "#" + line;
				}
			}
			
			// Enable custom SoundFont source.
			String soundFontDirectory =
					soundFontFilePath.substring(0,
							soundFontFilePath.lastIndexOf("/")+1);
			String soundFontFile = soundFontFilePath.substring(
					soundFontFilePath.lastIndexOf("/"));
			lines.add("dir " + soundFontDirectory);
			lines.add("soundfont " + soundFontFile);
			
			FileUtils.writeLines(configFile, lines, false);
			
			isSet = true;
		} catch (IOException e) {
			System.err.println(
					"Error while setting the SoundFont source." +
					" Message: " + e.getMessage());
			e.printStackTrace();
			throw e;
		}
		
		return isSet;
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
				frequency, usePureIntonation, preciseTunings, TEMP_PATH);
		
		return frequencyTablePath;
	}

}
