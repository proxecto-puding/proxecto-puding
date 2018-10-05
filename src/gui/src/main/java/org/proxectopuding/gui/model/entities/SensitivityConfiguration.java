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

package org.proxectopuding.gui.model.entities;

public class SensitivityConfiguration extends BagpipeConfiguration {
	
	// From 1 to 100.
	private int bagPressure;

	public int getBagPressure() {
		return bagPressure;
	}

	public void setBagPressure(int bagPressure) {
		
		int pressure = bagPressure % 100;
		pressure = pressure <= 0 ? 1 : pressure;
		
		this.bagPressure = bagPressure;
	}

}
