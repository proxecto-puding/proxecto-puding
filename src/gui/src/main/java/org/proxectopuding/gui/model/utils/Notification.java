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

package org.proxectopuding.gui.model.utils;

public enum Notification {

	CHANTER_FOUND("CHANTER_FOUND"),
	CHANTER_SELECTED("CHANTER_SELECTED"),
	READING_TONE_SELECTED("READING_TONE_SELECTED"),
	PRECISE_TUNING_NOTE_SELECTED("PRECISE_TUNING_NOTE_SELECTED"),
	PRECISE_TUNING_OCTAVE_SELECTED("PRECISE_TUNING_OCTAVE_SELECTED"),
	FINGERING_NOTE_SELECTED("FINGERING_NOTE_SELECTED"),
	FINGERING_OCTAVE_SELECTED("FINGERING_OCTAVE_SELECTED"),
	FINGERING_NUMBER_SELECTED("FINGERING_NUMBER_SELECTED"),
	FINGERING_NUMBER_ADDED("FINGERING_NUMBER_ADDED"),
	FINGERING_NUMBER_REMOVED("FINGERING_NUMBER_REMOVED");
	
	private String name;
	
	private Notification(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
}
