package main.model.utils;

public enum Notification {

	CHANTER_SELECTED("CHANTER_SELECTED"),
	READING_TONE_SELECTED("READING_TONE_SELECTED"),
	PRECISE_TUNING_NOTE_SELECTED("PRECISE_TUNING_NOTE_SELECTED"),
	PRECISE_TUNING_OCTAVE_SELECTED("PRECISE_TUNING_OCTAVE_SELECTED");
	
	private String name;
	
	private Notification(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
}
