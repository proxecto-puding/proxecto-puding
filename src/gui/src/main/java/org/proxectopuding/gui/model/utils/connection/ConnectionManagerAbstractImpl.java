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

package org.proxectopuding.gui.model.utils.connection;

import org.proxectopuding.gui.model.services.MidiService;
import org.proxectopuding.gui.model.utils.OperativeSystemManager;

import com.google.inject.Inject;

public abstract class ConnectionManagerAbstractImpl implements ConnectionManager {
	
	private static final String PORT_NAME_WINDOWS = "COM3";
	private static final String PORT_NAME_MACOS = "/dev/tty.usbserial-A9007UX1";
	private static final String PORT_NAME_UNIX = "/dev/ttyACM0";
	// Milliseconds to block while waiting for port open
	protected static final int CONNECTION_TIME_OUT = 2000;

	protected String PORT_NAME;
	private final MidiService midiService;
	
	@Inject
	public ConnectionManagerAbstractImpl(
			OperativeSystemManager operativeSystemManager,
			MidiService midiService) {
		
		setPortName(operativeSystemManager);
		this.midiService = midiService;
		this.midiService.setPortName(PORT_NAME);
	}
	
	/**
	 * Get a MIDI service.
	 * @return a MIDI service.
	 */
	protected MidiService getMidiService() {
		return midiService;
	}
	
	/**
	 * Delay the execution during a provided amount of milliseconds.
	 * @param millis Milliseconds the execution is going to be delayed.
	 */
	protected void delay(long millis) {
		try {
		    Thread.sleep(millis);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
	}
	
	/**
	 * Set the serial port name depending on the OS.
	 */
	private void setPortName(OperativeSystemManager operativeSystemManager) {
		if (operativeSystemManager.isWindows()) {
			PORT_NAME = PORT_NAME_WINDOWS;
		} else if (operativeSystemManager.isMacOs()) {
			PORT_NAME = PORT_NAME_MACOS;
		} else if (operativeSystemManager.isUnix()) {
			PORT_NAME = PORT_NAME_UNIX;
		}
	}

}
