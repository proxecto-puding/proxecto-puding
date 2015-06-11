package main.model.entities;

public class PreciseTuning implements Comparable<PreciseTuning> {
	
	// From 0 to 11.
	private int note;
	private int octave;
	private int cents;
	
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
