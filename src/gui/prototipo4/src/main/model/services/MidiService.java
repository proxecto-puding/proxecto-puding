package main.model.services;

import main.model.entities.MidiServerConfiguration;

public interface MidiService {
	
	/**
	 * Start the MIDI service.
	 * @param configuration MIDI service configuration to use.
	 * @param restart Indicate if it should be restarted.
	 * @return The process within the MIDI service is running.
	 */
	Process start(MidiServerConfiguration configuration, boolean restart);
	/**
	 * Stop the MIDI service.
	 */
	void stop();
}
