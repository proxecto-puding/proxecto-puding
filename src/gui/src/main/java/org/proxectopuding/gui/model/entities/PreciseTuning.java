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

public class PreciseTuning implements Comparable<PreciseTuning> {
	
	// From 0 to 11.
	private int note;
	// From -1 to 9.
	private int octave;
	// From -1200 to 1200.
	private int cents;
	
	public PreciseTuning(int note, int octave, int cents) {
		
		this.note = note;
		this.octave = octave;
		this.cents = cents;
	}
	
	public int getNote() {
		return note;
	}
	
	public void setNote(int note) {
		this.note = note;
	}
	
	public int getOctave() {
		return octave;
	}
	
	public void setOctave(int octave) {
		this.octave = octave;
	}
	
	public int getCents() {
		return cents;
	}
	
	public void setCents(int cents) {
		this.cents = cents;
	}
	
	@Override
	public boolean equals(Object preciseTuning) {
		PreciseTuning tuning = (PreciseTuning) preciseTuning;
		
		return note == tuning.note &&
				octave == tuning.octave;
	}

	@Override
	public int compareTo(PreciseTuning preciseTuning) {
		
		// Each octave has 12 semitones.
		int compareByOctaveAndNote = new Integer(octave * 12 + note).
				compareTo(new Integer(octave * 12 + preciseTuning.note));
		
		return compareByOctaveAndNote;
	}
	
}
