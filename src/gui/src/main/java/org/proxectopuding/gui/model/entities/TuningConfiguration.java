package org.proxectopuding.gui.model.entities;

public class TuningConfiguration extends BagpipeConfiguration {
	
	// From 0 to 11.
	private int tone;
	// From -1 to 9.
	private int octave;
	
	public int getTone() {
		return tone;
	}
	
	public void setTone(int tone) {
		this.tone = tone;
	}
	
	public int getOctave() {
		return octave;
	}
	
	public void setOctave(int octave) {
		this.octave = octave;
	}

}
