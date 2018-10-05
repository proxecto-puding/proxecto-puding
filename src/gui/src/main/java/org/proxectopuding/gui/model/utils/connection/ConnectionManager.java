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
	 * Read data from the serial device.
	 * @param disconnect Indicate if ending the connection.
	 * @return A string containing all the received data.
	 */
	String readData(boolean disconnect);
	
	/**
	 * Write data to the serial device.
	 * @param data Data to send.
	 */
	void writeData(String data);
	
	/**
	 * Write data to the serial device.
	 * @param data Data to send. 
	 * @param disconnect Indicate if ending the connection.
	 */
	void writeData(String data, boolean disconnect);
}
