package org.proxectopuding.gui.model.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;
import org.proxectopuding.gui.model.services.MidiService;
import org.proxectopuding.gui.model.services.impl.MidiServiceImpl;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

public class ConnectionManager implements SerialPortEventListener {
	
	private static final Logger LOGGER = Logger.getLogger(ConnectionManager.class.getName());

	private static final String PORT_NAME_WINDOWS = "COM3";
	private static final String PORT_NAME_MACOS = "/dev/tty.usbserial-A9007UX1";
	private static final String PORT_NAME_UNIX = "/dev/ttyACM0";
	private static final String DISCOVERY_BEACON = "DBEACON";
	// Milliseconds to block while waiting for port open
	private static final int TIME_OUT = 2000;
	// Default bits per second for COM port
	private static final int DATA_RATE = 9600;
	
	private static String PORT_NAME;
	
	private SerialPort serialPort;
	/*
	* A BufferedReader which will be fed by a InputStreamReader 
	* converting the bytes into characters 
	* making the displayed results code page independent.
	*/
	private BufferedReader input;
	// The output stream to the port
	private OutputStream output;
	
	private static ConnectionManager connection;
	private static MidiService midiService;
	
	static {
		setPortName();
		midiService = new MidiServiceImpl();
	};
	
	private ConnectionManager() {
		// Singleton.
	}
	
	/**
	 * Get the connection manager singleton instance.
	 * @return The connection manager instance.
	 */
	public static ConnectionManager getInstance() {
		if (connection == null) {
			connection = new ConnectionManager();
		}
		
		return connection;
	}
	
	/**
	 * Initialize the connection manager.
	 */
	public void initialize() {
		
		// The next line is for Raspberry Pi and 
        // gets us into the while loop and was suggested here:
		// http://www.raspberrypi.org/phpBB3/viewtopic.php?f=81&t=32186
        // System.setProperty("gnu.io.rxtx.SerialPorts", "/dev/ttyACM0");

		Enumeration<?> portEnum = null;
		try {
			// FIXME Exception in parallel thread. Uncatchable. 
			/*
			 * java.lang.UnsatisfiedLinkError: no rxtxSerial in java.library.path thrown while loading gnu.io.RXTXCommDriver
				Exception in thread "AWT-EventQueue-0" java.lang.UnsatisfiedLinkError: no rxtxSerial in java.library.path
					at java.lang.ClassLoader.loadLibrary(ClassLoader.java:1867)
					at java.lang.Runtime.loadLibrary0(Runtime.java:870)
					at java.lang.System.loadLibrary(System.java:1122)
					at gnu.io.CommPortIdentifier.<clinit>(CommPortIdentifier.java:83)
					at org.proxectopuding.gui.model.utils.ConnectionManager.initialize(ConnectionManager.java:82)
			 */
			portEnum = CommPortIdentifier.getPortIdentifiers();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Unable to initialize the serial connection. Could not find any system port");
			return;
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
			LOGGER.log(Level.SEVERE, "Unable to initialize the serial connection. Could not find COM port");
			return;
		}
		
		try {
			// Open serial port, and use class name for the appName
			serialPort = (SerialPort) portId.open(this.getClass().getName(), TIME_OUT);
		
			// Set port parameters
			serialPort.setSerialPortParams(DATA_RATE,
					SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);
		
			// Open the streams
			input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
			output = serialPort.getOutputStream();
		
			// Add event listeners
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
			
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Unable to initialize the serial connection", e);
		}
	}
		
	/**
	 * This should be called when you stop using the port.
	 * This will prevent port locking on platforms like Linux.
	 */
	public synchronized void close() {
		
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}
	
	/**
	 * Handle an event on the serial port. Read the data and print it.
	 */
	@Override
	public synchronized void serialEvent(SerialPortEvent oEvent) {
		
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				String inputLine = input.readLine();
				// TODO Implement.
				LOGGER.log(Level.INFO, inputLine);
			} catch (Exception e) {
				LOGGER.log(Level.SEVERE, "Unable to read data from the serial connection", e);
			}
		} else {
			// Ignore all the other eventTypes, but you should consider the other ones.
		}
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
	
	/**
	 * Read data from the serial port.
	 * @return A string containing all the received data.
	 */
	public String readData() {
		
		String data = null;
		
		StringWriter writer = new StringWriter();
		try {
			// Before initializing the serial connection, it is needed to stop
			// the MIDI service to avoid missing data read by it.
			midiService.stop();
			initialize();
			IOUtils.copy(serialPort.getInputStream(), writer, "UTF-8");
			data = writer.toString();
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Unable to read data from the serial port", e);
		} finally {
			close();
			// Once we finish reading the data coming from the serial port, we
			// restart the MIDI service with the same configuration.
			midiService.restart();
		}
		
		return data;
	}
	
	/**
	 * Write data to the serial port.
	 * @param data Data to send.
	 */
	public void writeData(String data) {
		try {
			initialize();
			IOUtils.write(data, serialPort.getOutputStream());
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Unable to write data to the serial port", e);
		} finally {
			close();
		}
	}
	
	/**
	 * Send a discovery beacon for finding serial devices.
	 */
	public void sendDiscoveryBeacon() {
		
		LOGGER.log(Level.INFO, "Sending discovery beacon");
		writeData(DISCOVERY_BEACON);
	}
	
	/**
	 * Delay the execution during a provided amount of milliseconds.
	 * @param millis Milliseconds the execution is going to be delayed.
	 */
	public void delay(long millis) {
		try {
		    Thread.sleep(millis);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
	}
	
	/**
	 * Set the serial port name depending on the OS.
	 */
	private static void setPortName() {
		if (OperativeSystemManager.isWindows()) {
			PORT_NAME = PORT_NAME_WINDOWS;
		} else if (OperativeSystemManager.isMacOs()) {
			PORT_NAME = PORT_NAME_MACOS;
		} else if (OperativeSystemManager.isUnix()) {
			PORT_NAME = PORT_NAME_UNIX;
		}
	}

}
