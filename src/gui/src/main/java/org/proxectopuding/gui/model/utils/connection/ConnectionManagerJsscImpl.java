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

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.proxectopuding.gui.model.services.MidiService;
import org.proxectopuding.gui.model.utils.OperativeSystemManager;

import jssc.SerialPort;
import jssc.SerialPortList;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;

public class ConnectionManagerJsscImpl extends ConnectionManagerAbstractImpl {
	
	private static final Logger LOGGER = Logger.getLogger(ConnectionManagerJsscImpl.class.getName());

	private SerialPort serialPort;
	
	@Inject
	public ConnectionManagerJsscImpl(
			OperativeSystemManager operativeSystemManager,
			MidiService midiService) {
		
		super(operativeSystemManager, midiService);
	}
	
	@Override
	public void connect() throws IOException {
		
		if (serialPort != null && serialPort.isOpened()) {
			LOGGER.log(Level.INFO, "Already connected to COM port: " + PORT_NAME);
			return;
		}
		
		ImmutableList<String> portNames = ImmutableList.of();
		try {
			portNames = ImmutableList.copyOf(SerialPortList.getPortNames());
		} catch (Exception e) {
			String msg = "Unable to initialize the serial connection. Could not find any system port";
			LOGGER.log(Level.SEVERE, msg, e);
			throw new IOException(msg);
		}

		// First, find the instance of the serial port set in PORT_NAME.
		if (!portNames.contains(PORT_NAME)) {
			String msg = "Unable to initialize the serial connection. Could not find COM port";
			LOGGER.log(Level.SEVERE, msg);
			throw new IOException(msg);
		}
		serialPort = new SerialPort(PORT_NAME);
		
		try {
			// Open serial port, and use class name for the appName
			boolean isOpened = serialPort.openPort();
			if (!isOpened) {
				String msg = "Unable to initialize the serial connection. Could not open COM port";
				LOGGER.log(Level.SEVERE, msg);
				throw new IOException(msg);
			}
			LOGGER.log(Level.INFO, "Connected to COM port: " + PORT_NAME);
		
			// Set port parameters
			serialPort.setParams(SerialPort.BAUDRATE_9600,
	                 SerialPort.DATABITS_8,
	                 SerialPort.STOPBITS_1,
	                 SerialPort.PARITY_NONE, false, true);
			serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
			
			// Allow serial device to reset before starting communication.
			delay(CONNECTION_TIME_OUT);
			
		} catch (Exception e) {
			String msg = "Unable to initialize the serial connection";
			LOGGER.log(Level.SEVERE, msg, e);
			throw new IOException(msg);
		}
	}
	
	@Override
	public synchronized void disconnect() {
		
		if (serialPort != null && serialPort.isOpened()) {
			try {
				serialPort.closePort();
				LOGGER.log(Level.INFO, "Disconnected from COM port: " + PORT_NAME);
			} catch (Exception e) {
				String msg = "Unable to close the serial connection";
				LOGGER.log(Level.SEVERE, msg, e);
			}
		}
	}
	
	@Override
	public String readData() {
		
		return readData(true);
	}
	
	@Override
	public String readData(boolean disconnect) {
		
		String data = null;
		
		try {
			// Before initializing the serial connection, it is needed to stop
			// the MIDI service to avoid missing data read by it.
			getMidiService().stop();
			connect();
			data = serialPort.readString();
			LOGGER.log(Level.INFO, data);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Unable to read data from the serial port", e);
		} finally {
			if (disconnect) {
				disconnect();
				// Once we finish reading the data coming from the serial port,
				// restart the MIDI service with the same configuration.
				getMidiService().restart();
			}
		}
		
		return data;
	}
	
	@Override
	public void writeData(String data) {
		
		writeData(data, true);
	}
	
	@Override
	public void writeData(String data, boolean disconnect) {
		try {
			connect();
			LOGGER.log(Level.INFO, data);
			serialPort.writeString(data);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Unable to write data to the serial port", e);
		} finally {
			if (disconnect) {
				disconnect();
			}
		}
	}
}
