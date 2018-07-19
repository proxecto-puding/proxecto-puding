package org.proxectopuding.gui.model.utils.connection;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import jssc.SerialPort;
import jssc.SerialPortList;

import com.google.common.collect.ImmutableList;

public class ConnectionManagerJsscImpl extends ConnectionManagerAbstractImpl {
	
	private static final Logger LOGGER = Logger.getLogger(ConnectionManagerJsscImpl.class.getName());

	private SerialPort serialPort;
	
	private static ConnectionManagerJsscImpl connection;
	
	private ConnectionManagerJsscImpl() {
		// Singleton.
	}
	
	/**
	 * Get the connection manager singleton instance.
	 * @return The connection manager instance.
	 */
	public static ConnectionManagerJsscImpl getInstance() {
		if (connection == null) {
			connection = new ConnectionManagerJsscImpl();
		}
		
		return connection;
	}
	
	public void connect() throws IOException {
		
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
		
			// Set port parameters
			serialPort.setParams(SerialPort.BAUDRATE_9600,
	                 SerialPort.DATABITS_8,
	                 SerialPort.STOPBITS_1,
	                 SerialPort.PARITY_NONE);
			
			// Allow serial device to reset before starting communication.
			delay(CONNECTION_TIME_OUT);
			
		} catch (Exception e) {
			String msg = "Unable to initialize the serial connection";
			LOGGER.log(Level.SEVERE, msg, e);
			throw new IOException(msg);
		}
	}
		
	public synchronized void disconnect() {
		
		if (serialPort != null) {
			try {
				serialPort.closePort();
			} catch (Exception e) {
				String msg = "Unable to close the serial connection";
				LOGGER.log(Level.SEVERE, msg, e);
			}
		}
	}
	
	public String readData() {
		
		String data = null;
		
		try {
			// Before initializing the serial connection, it is needed to stop
			// the MIDI service to avoid missing data read by it.
			getMidiService().stop();
			connect();
			data = serialPort.readString();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Unable to read data from the serial port", e);
		} finally {
			disconnect();
			// Once we finish reading the data coming from the serial port, we
			// restart the MIDI service with the same configuration.
			getMidiService().restart();
		}
		
		return data;
	}
	
	public void writeData(String data) {
		try {
			connect();
			serialPort.writeString(data);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Unable to write data to the serial port", e);
		} finally {
			disconnect();
		}
	}
	
	public void sendDiscoveryBeacon() {
		
		LOGGER.log(Level.INFO, "Sending discovery beacon");
		writeData(DISCOVERY_BEACON);
	}
}
