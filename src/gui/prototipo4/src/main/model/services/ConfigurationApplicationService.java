package main.model.services;

import java.util.List;

public interface ConfigurationApplicationService {
	
	/**
	 * Get the list of reading tones.
	 * @return A list of reading tones.
	 */
	public List<String> getReadingTones();
	
	/**
	 * Get the current reading tone.
	 * @return The current reading tone.
	 */
	public String getReadingTone();
	
	/**
	 * Set the current reading tone.
	 * @param readingTone A reading tone.
	 */
	public void setReadingTone(String readingTone);
	
	/**
	 * Get the list of tuning tones.
	 * @return A list of tuning tones.
	 */
	public List<String> getTuningTones();
	
	/**
	 * Get a string representation of a given tuning tone.
	 * @param tuningTone A tuning tone as integer between [0, 11].
	 * @return A tuning tone as string.
	 * @throws IllegalArgumentException If the given tuning tone is not in the
	 * expected range.
	 */
	public String getTuningTone(int tuningTone) throws IllegalArgumentException;
		
	/**
	 * Get an integer representation of a given tuning tone.
	 * @param tuningTone A tuning tone as string.
	 * @return A tuning tone as integer.
	 * @throws IllegalArgumentException If the given tuning tone does not
	 * correspond to any mapped string representation. 
	 */
	public int getTuningTone(String tuningTone) throws IllegalArgumentException;
	
	/**
	 * Get the default tuning tone.
	 * @return A tuning tone.
	 */
	public String getDefaultTuningTone();
	
	/**
	 * Get the list of tuning octaves.
	 * @return A list of tuning octaves.
	 */
	public List<Integer> getTuningOctaves();
	
	/**
	 * Get the default tuning octave.
	 * @return A tuning octave.
	 */
	public int getDefaultTuningOctave();
	
	/**
	 * Get the list of samples.
	 * @return A list of samples.
	 */
	public List<String> getSamples();
	
	/**
	 * Get the default fingering types.
	 * @return A list of fingering types.
	 */
	public List<Boolean> getDefaultFingeringTypesEnabled();

	/**
	 * Get the current sample.
	 * @return A sample.
	 */
	public String getSample();
	
	/**
	 * Set the current sample.
	 * @param sample A sample.
	 */
	public void setSample(String sample);
	
	/**
	 * Check the default value for the bag.
	 * @return A boolean indicating if the default bag is enabled.
	 */
	public Boolean isDefaultBagEnabled();

	/**
	 * Get the default drones status.
	 * @return A list of drones.
	 */
	public List<Boolean> getDefaultDronesEnabled();

	/**
	 * Get the current tuning frequency.
	 * @return A tuning frequency.
	 */
	public int getTuningFrequency();
	
	/**
	 * Set the current tuning frequency.
	 * @param tuningFrequency A tuning frequency.
	 */
	public void setTuningFrequency(int tuningFrequency);

	/**
	 * Get the list of tuning modes.
	 * @return A list of tuning modes.
	 */
	public List<String> getTuningModes();
	
	/**
	 * Get the current tuning mode.
	 * @return A tuning mode.
	 */
	public String getTuningMode();
	
	/**
	 * Set the current tuning mode.
	 * @param tuningMode A tuning mode.
	 */
	public void setTuningMode(String tuningMode);

	/**
	 * Get the default tuning mode.
	 * @return A tuning mode.
	 */
	public String getDefaultTuningMode();

	/**
	 * Get the list of precise tuning notes.
	 * @return A list of precise tuning notes.
	 */
	public List<String> getPreciseTuningNotes();

	/**
	 * Get the current precise tuning note.
	 * @return A precise tuning note.
	 */
	public String getPreciseTuningNote();

	/**
	 * Set the current precise tuning note.
	 * @param preciseTuningTone A precise tuning note name.
	 */
	public void setPreciseTuningNote(String preciseTuningNote);
	
	/**
	 * Set the current precise tuning note.
	 * @param preciseTuningNote A precise tuning note value.
	 */
	public void setPreciseTuningNote(int preciseTuningNote);

	/**
	 * Reset the list of precise tuning notes.
	 * @return A list of precise tuning notes.
	 */
	public List<String> resetPreciseTuningNotes();

	/**
	 * Get the list of precise tuning octaves.
	 * @return A list of precise tuning octaves.
	 */
	public List<Integer> getPreciseTuningOctaves();

	/**
	 * Get the current precise tuning octave.
	 * @return A precise tuning octave.
	 */
	public int getPreciseTuningOctave();

	/**
	 * Set the current precise tuning octave.
	 * @param preciseTuningOctave A precise tuning octave.
	 */
	public void setPreciseTuningOctave(int preciseTuningOctave);

	/**
	 * Get the current precise tuning cents.
	 * @return Precise tuning cents.
	 */
	public int getPreciseTuningCents();

	/**
	 * Set the current precise tuning cents.
	 * @param preciseTuningCents Precise tuning cents.
	 */
	public void setPreciseTuningCents(int preciseTuningCents);
	
}
