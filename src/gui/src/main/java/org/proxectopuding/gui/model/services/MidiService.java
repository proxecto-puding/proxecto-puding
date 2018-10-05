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
