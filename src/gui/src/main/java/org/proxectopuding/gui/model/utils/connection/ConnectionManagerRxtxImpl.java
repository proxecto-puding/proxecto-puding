package org.proxectopuding.gui.model.utils.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

public class ConnectionManagerRxtxImpl extends ConnectionManagerAbstractImpl {
	
	private static final Logger LOGGER = Logger.getLogger(ConnectionManagerRxtxImpl.class.getName());
	
	// Default bits per second for COM port
	private static final int DATA_RATE = 9600;

	private SerialPort serialPort;
	/*
	* A BufferedReader which will be fed by a InputStreamReader 
	* converting the bytes into characters 
	* making the displayed results code page independent.
	*/
	private BufferedReader input;
	// The output stream to the port
	private OutputStream output;
	
	private static ConnectionManagerRxtxImpl connection;
	
	private ConnectionManagerRxtxImpl() {
		// Singleton.
	}
	
	/**
	 * Get the connection manager singleton instance.
	 * @return The connection manager instance.
	 */
	public static ConnectionManagerRxtxImpl getInstance() {
		if (connection == null) {
			connection = new ConnectionManagerRxtxImpl();
		}
		
		return connection;
	}
	
	public void connect() throws IOException {
		
		// The next line is for Raspberry Pi and 
        // gets us into the while loop and was suggested here:
		// http://www.raspberrypi.org/phpBB3/viewtopic.php?f=81&t=32186
        System.setProperty("gnu.io.rxtx.SerialPorts", PORT_NAME);

		Enumeration<?> portEnum = null;
		try {
			portEnum = CommPortIdentifier.getPortIdentifiers();
		} catch (Exception e) {
			String msg = "Unable to initialize the serial connection. Could not find any system port";
			LOGGER.log(Level.SEVERE, msg, e);
			throw new IOException(msg);
		}

		// First, find the instance of the serial port set in PORT_NAME.
		CommPortIdentifier portId = null;
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
			if (currPortId.getName().equals(PORT_NAME)) {
				portId = currPortId;
				break;
			}
		}
		
		if (portId == null) {
			String msg = "Unable to initialize the serial connection. Could not find COM port";
			LOGGER.log(Level.SEVERE, msg);
			throw new IOException(msg);
		}
		
		try {
			// Open serial port, and use class name for the appName
			serialPort = (SerialPort) portId.open(this.getClass().getName(), CONNECTION_TIME_OUT);
		
			// Set port parameters
			serialPort.setSerialPortParams(DATA_RATE,
					SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);
			
			// Allow serial device to reset before starting communication.
			delay(CONNECTION_TIME_OUT);
		
			// Open the streams
			input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
			output = serialPort.getOutputStream();
		
		} catch (Exception e) {
			String msg = "Unable to initialize the serial connection";
			LOGGER.log(Level.SEVERE, msg, e);
			throw new IOException(msg);
		}
	}
		
	public synchronized void disconnect() {
		
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}
	
	public String readData() {
		
		String data = null;
		
		StringWriter writer = new StringWriter();
		try {
			// Before initializing the serial connection, it is needed to stop
			// the MIDI service to avoid missing data read by it.
			getMidiService().stop();
			connect();
			IOUtils.copy(serialPort.getInputStream(), writer, "UTF-8");
			data = writer.toString();
		} catch (IOException e) {
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
			IOUtils.write(data.getBytes(StandardCharsets.UTF_8), output);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Unable to write data to the serial port", e);
		} finally {
			disconnect();
		}
	}
	
	public void sendDiscoveryBeacon() {
		
		LOGGER.log(Level.INFO, "Sending discovery beacon");
		writeData(DISCOVERY_BEACON);
	}
	
	/**
	 * Get an input buffered reader.
	 * @return A buffered reader.
	 */
	public BufferedReader getBufferedReader() {
		return input;
	}
	
	/**
	 * Get an output stream.
	 * @return An output stream.
	 */
	public OutputStream getOutputStream() {
		return output;
	}
}
