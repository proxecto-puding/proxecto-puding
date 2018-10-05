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

package org.proxectopuding.gui.model.exceptions;

public class ApplicationException extends Exception {

	private static final long serialVersionUID = -5296358882337729993L;

	private ErrorKey errorKey;
	
	public ApplicationException(ErrorKey errorKey) {
		
		this.errorKey = errorKey;
	}

	public ErrorKey getErrorKey() {
		return errorKey;
	}

	public void setErrorKey(ErrorKey errorKey) {
		this.errorKey = errorKey;
	}
}
