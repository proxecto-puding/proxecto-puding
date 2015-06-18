package main.model.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import main.model.utils.MidiUtils;

public class MidiServerUnix extends MidiServer {
	
	private static final String REAL_SAMPLES = "--config-file=";
	private static final String FREQ_TABLE = "--freq-table=";
	private static final String TEMP_PATH = "/tmp";
	private static final String VIBRATO = "--vibrato";
	private static final String CHORUS = "--chorus=n";
	
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
			command.add(REAL_SAMPLES + configFilePath);
		}
		
		// Tuning mode, tuning frequency and precise tunings.
		tablePath = getFrequencyTablePath(configuration);
		command.add(FREQ_TABLE + tablePath);
		
		// Continuous vibrato.
		if (configuration.useContinuousVibrato()) {
			// This option is only for hardware not supporting manual vibrato.
			command.add(VIBRATO);
			command.add(CHORUS);
		}
		
		return command;
	}
	
	private String getRealSamplesConfigFilePath() {
		
		String realSamplesConfigFilePath = null;
		
		// TODO Implement.
		// Copy the bagpipe.sf2 to /tmp.
		// Copy the timidity.cfg of the system to /tmp.
		// Modify the timidity.cfg to include the sf2 file.
		// Return the path to this config file.
		
		return realSamplesConfigFilePath;
	}
	
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
