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

package org.proxectopuding.gui.model.services.impl;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.proxectopuding.gui.model.entities.midiServer.MidiServer;
import org.proxectopuding.gui.model.entities.midiServer.MidiServerConfiguration;
import org.proxectopuding.gui.model.services.MidiService;

import com.google.inject.Inject;

public class MidiServiceImpl implements MidiService {
	
	private static final Logger LOGGER = Logger.getLogger(MidiServiceImpl.class.getName());
	
	private final MidiServer midiServer;
	private ProcessBuilder processBuilder;
	private Process process;
	private String portName = "";
	
	@Inject
	public MidiServiceImpl(MidiServer midiServer) {
		
		this.midiServer = midiServer;
		this.processBuilder = new ProcessBuilder();
	}
	
	@Override
	public void setPortName(String portName) {
		this.portName = portName;
	}

	@Override
	public Process start() {
		
		LOGGER.log(Level.INFO, "Starting MIDI server");
		
		try {
			List<String> command = midiServer.getCommand(portName);
			LOGGER.log(Level.INFO, "Command: " + command);
			processBuilder = processBuilder.command(command);
			process = processBuilder.start();
		} catch (IOException e) {
			process = null;
			LOGGER.log(Level.SEVERE, "Unable to start the MIDI server", e);
		}
		
		return process;
	}
	
	@Override
	public Process restart() {
		LOGGER.log(Level.INFO, "Restarting MIDI server");
		stop();
		return start();
	}

	@Override
	public void stop() {
		LOGGER.log(Level.INFO, "Stopping MIDI server");
		if (process != null) {
			process.destroy();
			LOGGER.log(Level.INFO, "MIDI server stopped");
		}
	}
	
	@Override
	public MidiServerConfiguration getConfiguration() {
		LOGGER.log(Level.INFO, "Getting MIDI server configuration");
		return midiServer.getConfiguration();
	}
	
	@Override
	public void setConfiguration(MidiServerConfiguration configuration) {
		LOGGER.log(Level.INFO, "Setting MIDI server configuration: {0}", configuration);
		midiServer.setConfiguration(configuration);
	}

}
