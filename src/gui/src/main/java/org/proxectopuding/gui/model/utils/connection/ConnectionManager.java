package org.proxectopuding.gui.model.utils.connection;

import java.io.IOException;

public interface ConnectionManager {

	/**
	 * Initialize the connection with the serial device.
	 * @throws IOException in case of connection issues.
	 */
	void connect() throws IOException;
	
	/**
	 * End the connection with the serial device.
	 * This should be called when you stop using the port.
	 * This will prevent port locking on platforms like Linux.
	 */
	void disconnect();
	
	/**
	 * Read data from the serial device.
	 * @return A string containing all the received data.
	 */
	String readData();
	
	/**
	 * Write data to the serial device.
	 * @param data Data to send.
	 */
	void writeData(String data);
}
