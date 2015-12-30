package main.model.services;

import main.model.entities.MidiServerConfiguration;

public interface MidiService {
	
	/**
	 * Start the MIDI service.
	 * @return The process within the MIDI service is running.
	 */
	public Process start();
	
	/**
	 * Restart the MIDI service.
	 * @return The process within the MIDI service is running.
	 */
	public Process restart();
	
	/**
	 * Stop the MIDI service.
	 */
	public void stop();
	
	/**
	 * Get the MIDI server configuration.
	 * @return A MIDI server configuration.
	 */
	public MidiServerConfiguration getConfiguration();
	
	/**
	 * Set the MIDI server configuration.
	 * @param configuration A MIDI server configuration.
	 */
	public void setConfiguration(MidiServerConfiguration configuration);
	
}
