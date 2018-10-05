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

import java.util.List;

public class SelectionConfiguration extends BagpipeConfiguration {
	
	private int volume;
	private List<Boolean> fingeringTypes;
	private boolean bagEnabled;
	private List<Boolean> dronesEnabled;
	
	public int getVolume() {
		return volume;
	}
	
	public void setVolume(int volume) {
		this.volume = volume;
	}
	
	public List<Boolean> getFingeringTypes() {
		return fingeringTypes;
	}
	
	public void setFingeringTypes(List<Boolean> fingeringTypes) {
		this.fingeringTypes = fingeringTypes;
	}
	
	public boolean isBagEnabled() {
		return bagEnabled;
	}
	
	public void setBagEnabled(boolean bagEnabled) {
		this.bagEnabled = bagEnabled;
	}
	
	public List<Boolean> getDronesEnabled() {
		return dronesEnabled;
	}
	
	public void setDronesEnabled(List<Boolean> dronesEnabled) {
		this.dronesEnabled = dronesEnabled;
	}

}
