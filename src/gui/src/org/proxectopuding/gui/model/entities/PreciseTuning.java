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
