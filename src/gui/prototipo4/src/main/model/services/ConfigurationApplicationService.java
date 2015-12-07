package main.model.services;

import java.util.List;

public interface ConfigurationApplicationService {
	
	/**
	 * Get the available reading tones.
	 * @return The available reading tones.
	 */
	public List<String> getReadingTones();
	
	/**
	 * Get the current reading tone.
	 * @return The current reading tone.
	 */
	public String getReadingTone();
	
	/**
	 * Set the current reading tone.
	 * @param readingTone The current reading tone.
	 */
	public void setReadingTone(String readingTone);

}
