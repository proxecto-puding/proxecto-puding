package main.model.services.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.Enumeration;

import org.apache.commons.io.IOUtils;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

public class ConnectionManager implements SerialPortEventListener {

	private static final String PORT_NAME_WINDOWS = "COM3";
	private static final String PORT_NAME_MACOS = "/dev/tty.usbserial-A9007UX1";
	private static final String PORT_NAME_UNIX = "/dev/ttyACM0";
	private static final String DISCOVERY_BEACON = "DBEACON";
	// Milliseconds to block while waiting for port open
	private static final int TIME_OUT = 2000;
	// Default bits per second for COM port
	private static final int DATA_RATE = 9600;
	
	private static String OS_NAME;
	private static String PORT_NAME;
	private static SerialPort serialPort;
	/*
	* A BufferedReader which will be fed by a InputStreamReader 
	* converting the bytes into characters 
	* making the displayed results codepage independent.
	*/
	private static BufferedReader input;
	// The output stream to the port
	private static OutputStream output;
	
	static {
		OS_NAME = System.getProperty("os.name");
		
		if (OS_NAME.startsWith("Windows")) {
			PORT_NAME = PORT_NAME_WINDOWS;
		} else if (OS_NAME.startsWith("Mac OS")) {
			PORT_NAME = PORT_NAME_MACOS;
		} else { // UNIX-like by default.
			PORT_NAME = PORT_NAME_UNIX;
		}
	};
	
	public void initialize() {
		
		// The next line is for Raspberry Pi and 
        // gets us into the while loop and was suggested here:
		// http://www.raspberrypi.org/phpBB3/viewtopic.php?f=81&t=32186
        // System.setProperty("gnu.io.rxtx.SerialPorts", "/dev/ttyACM0");

		Enumeration<?> portEnum = CommPortIdentifier.getPortIdentifiers();
		CommPortIdentifier portId = null;
		
		// First, find the instance of the serial port set in PORT_NAME.
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
			if (currPortId.getName().equals(PORT_NAME)) {
				portId = currPortId;
				break;
			}
		}
		
		if (portId == null) {
			System.out.println("Could not find COM port.");
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
			e.printStackTrace();
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
				// String inputLine = input.readLine();
				// System.out.println(inputLine);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			// Ignore all the other eventTypes, but you should consider the other ones.
		}
		
	}
	
	public static BufferedReader getBufferedReader() {
		return input;
	}
	
	public static OutputStream getOutputStream() {
		return output;
	}
	
	public static String readData() {
		
		StringWriter writer = new StringWriter();
		try {
			IOUtils.copy(serialPort.getInputStream(), writer, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		String data = writer.toString();
		
		return data;
	}
	
	public static void writeData(String data) {
		try {
			IOUtils.write(data, serialPort.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void sendDiscoveryBeacon() {
		writeData(DISCOVERY_BEACON);
	}
	
	public static void delay(long millis) {
		try {
		    Thread.sleep(millis);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
	}

}
