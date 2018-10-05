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

public enum BagpipeConfigurationAction {
	
	CURRENT("current"),
	NEW("new"),
	RESET("reset"),
	DEFAULT("default");
	
	private final String action;
	
	private BagpipeConfigurationAction(final String action) {
        this.action = action;
    }
	
	@Override
    public String toString() {
        return action;
    }

	public static BagpipeConfigurationAction from(String action) {
		
		return action != null ?
				BagpipeConfigurationAction.valueOf(action.toUpperCase()) : null;
	}
}
