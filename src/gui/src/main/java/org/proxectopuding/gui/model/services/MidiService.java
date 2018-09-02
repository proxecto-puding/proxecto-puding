package org.proxectopuding.gui.model.services;

import org.proxectopuding.gui.model.entities.midiServer.MidiServerConfiguration;

public interface MidiService {
	
	/**
	 * Set MIDI port name.
	 * @param portName MIDI port name.
	 */
	public void setPortName(String portName);
	
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
