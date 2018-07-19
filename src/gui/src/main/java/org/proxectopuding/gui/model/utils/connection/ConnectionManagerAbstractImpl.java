package org.proxectopuding.gui.model.utils.connection;

import java.util.logging.Logger;

import org.proxectopuding.gui.model.services.MidiService;
import org.proxectopuding.gui.model.services.impl.MidiServiceImpl;
import org.proxectopuding.gui.model.utils.OperativeSystemManager;

public abstract class ConnectionManagerAbstractImpl implements ConnectionManager {
	
	private static final Logger LOGGER = Logger.getLogger(ConnectionManagerAbstractImpl.class.getName());

	private static final String PORT_NAME_WINDOWS = "COM3";
	private static final String PORT_NAME_MACOS = "/dev/tty.usbserial-A9007UX1";
	private static final String PORT_NAME_UNIX = "/dev/ttyACM1";
	
	protected static final String DISCOVERY_BEACON = "DBEACON";
	// Milliseconds to block while waiting for port open
	protected static final int CONNECTION_TIME_OUT = 2000;

	protected static String PORT_NAME;
	private static MidiService midiService;
	
	static {
		setPortName();
		midiService = new MidiServiceImpl();
	};
	
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
