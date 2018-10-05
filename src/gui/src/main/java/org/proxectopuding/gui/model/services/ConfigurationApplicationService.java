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

package org.proxectopuding.gui.model.services;

import java.util.List;

import org.proxectopuding.gui.model.entities.BagpipeConfigurationType;
import org.proxectopuding.gui.model.entities.FingeringOffset;
import org.proxectopuding.gui.model.entities.midiServer.MidiServerConfiguration;

public interface ConfigurationApplicationService {
	
	/**
	 * Get the current device configuration type.
	 * @return A bagpipe configuration type.
	 */
	public BagpipeConfigurationType getSelectedBagpipeConfigurationType();
	
	/**
	 * Set the current device configuration type.
	 * @param bagpipeConfigurationType A bagpipe configuration type.
	 */
	public void setSelectedBagpipeConfigurationType(BagpipeConfigurationType bagpipeConfigurationType);
	
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
	
	/**
	 * Get the list of custom fingering notes.
	 * @return A list of custom fingering notes.
	 */
	public List<String> getCustomFingeringNotes();
	
	/**
	 * Get the current custom fingering note.
	 * @return A custom fingering note.
	 */
	public String getCustomFingeringNote();
	
	/**
	 * Set the current custom fingering note.
	 * @param customFingeringNote A custom fingering note name.
	 */
	public void setCustomFingeringNote(String customFingeringNote);
	
	/**
	 * Set the current custom fingering note.
	 * @param customFingeringNote A custom fingering note value.
	 */
	public void setCustomFingeringNote(int customFingeringNote);
	
	/**
	 * Reset the list of custom fingering notes.
	 * @return A list of custom fingering notes.
	 */
	public List<String> resetCustomFingeringNotes();

	/**
	 * Get the list of custom fingering octaves.
	 * @return A list of custom fingering octaves.
	 */
	public List<Integer> getCustomFingeringOctaves();

	/**
	 * Get the current custom fingering octave.
	 * @return A custom fingering octave.
	 */
	public int getCustomFingeringOctave();

	/**
	 * Set the current custom fingering octave.
	 * @param customFingeringOctave A custom fingering octave.
	 */
	public void setCustomFingeringOctave(Integer customFingeringOctave);

	/**
	 * Get a list of custom fingering numbers.
	 * @param fingerings A list of custom fingerings.
	 * @return A list of custom fingering numbers.
	 */
	public List<Integer> getCustomFingeringNumbers(List<FingeringOffset> fingerings);

	/**
	 * Get the current custom fingering number.
	 * @return A custom fingering number.
	 */
	public int getCustomFingeringNumber();

	/**
	 * Set the current custom fingering number.
	 * @param customFingeringNumber A custom fingering number.
	 */
	public void setCustomFingeringNumber(int customFingeringNumber);

	/**
	 * Add a new custom fingering number to the list.
	 * @return The new custom fingering number.
	 */
	public int addCustomFingeringNumber();

	/**
	 * Get a custom fingering by number.
	 * @param customFingeringNumber A custom fingering number.
	 * @return A custom fingering.
	 */
	public FingeringOffset getCustomFingering(int customFingeringNumber);

	/**
	 * Remove a custom fingering from the list.
	 * @param customFingeringNumber A custom fingering number.
	 */
	public void removeCustomFingeringNumber(int customFingeringNumber);

	/**
	 * Check if the given sensor is selected.
	 * @param sensor Sensor number.
	 * @return A boolean indicating if the sensor is selected.
	 */
	public boolean isCustomFingeringSensorSelected(int sensor);
	
	/**
	 * Select or not the provided sensor. 
	 * @param sensor Sensor number.
	 * @param isSelected Boolean indicating if the sensor is selected.
	 * @return The associated fingering.
	 */
	public FingeringOffset setCustomFingeringSensor(int sensor, boolean isSelected);

	/**
	 * Get a MIDI server configuration.
	 * @return A MIDI server configuration.
	 */
	public MidiServerConfiguration getMidiServerConfiguration();
	
}
