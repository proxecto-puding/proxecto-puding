package main.model.entities;

import java.util.ArrayList;
import java.util.List;

public class MidiServerUnix extends MidiServer {
	
	static {
		path = "/usr/bin/timidity";
	};

	@Override
	public List<String> getCommand(MidiServerConfiguration configuration) {
		List<String> command = new ArrayList<String>();
		
		command.add(path);
		
		// TODO Add the arguments from the configuration.
		if (configuration.useRealSamples()) {
			// TODO Map.
		}
		// TODO Tuning frequency.
		if (configuration.useNaturalTuningMode()) {
			// TODO Map.
		}
		if (configuration.useContinuousVibrato()) {
			// TODO Map.
		}
		// TODO Precise tunings.
		
		return command;
	}

}
