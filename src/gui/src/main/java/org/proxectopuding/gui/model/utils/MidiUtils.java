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

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.proxectopuding.gui.model.entities.PreciseTuning;

public class MidiUtils {
	
	private static final Logger LOGGER = Logger.getLogger(MidiUtils.class.getName());
	
	private static final String FREQ_TABLE = "freqtable.tbl";
	private static double[] pureIntonationRatios = 
		{1.0/1, 16.0/15, 8.0/7, 6.0/5, 5.0/4, 4.0/3, 16.0/11, 3.0/2, 8.0/5,
			5.0/3, 7.0/4, 15.0/8};
	
	/**
	 * Generate a table of tuning frequencies to be used by the MIDI server.
	 * @param tone Base tone.
	 * @param octave Base octave.
	 * @param frequency Base frequency.
	 * @param usePureIntonation Indicate if it is going to be used pure/just or
	 * tempered intonation.
	 * @param preciseTunings List of precise custom tunings for particular
	 * notes.
	 * @param path Directory path where the table is going to be stored.
	 * @return The path to the generated table file.
	 */
	public String generateFrequencyTable(int tone, int octave,
			int frequency, boolean usePureIntonation,
			Set<PreciseTuning> preciseTunings, String path) {
		
		String tablePath = getTablePath(path);
		
		String table = generateFrequencyTable(tone, octave, frequency,
				usePureIntonation, preciseTunings);
		
		writeFrequencyTabletoFile(tablePath, table);
		
		return tablePath;
	}
	
	/**
	 * Generate the full path to the frequency table file.
	 * @param path Directory path where the table is going to be stored.
	 * @return The full path to the frequency table file.
	 */
	private String getTablePath(String path) {
		
		String tablePath = path;
		
		if (!tablePath.endsWith("/")) {
			tablePath += "/";
		}
		tablePath += FREQ_TABLE;
		
		return tablePath;
	}
	
	/**
	 * Write the frequency table to a file.
	 * @param path Directory path where the table is going to be stored.
	 * @param table Table content conformed by a list of frequencies.
	 */
	private void writeFrequencyTabletoFile(String path, String table) {
		writeFrequencyTabletoFile(path, table, false);
	}
	
	/**
	 * Write the frequency table to a file.
	 * @param path Directory path where the table is going to be stored.
	 * @param table Table content conformed by a list of frequencies.
	 * @param append Indicate if it should append the content to the file.
	 */
	private void writeFrequencyTabletoFile(String path, String table,
			boolean append) {
		try {
			FileUtils.writeStringToFile(new File(path), table, "UTF-8", append);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Unable to write the frequency table to a file", e);
		}
	}
		
	/**
	 * Generate a table of tuning frequencies to be used by the MIDI server.
	 * @param tone Base tone.
	 * @param octave Base octave.
	 * @param frequency Base frequency.
	 * @param usePureIntonation Indicate if it is going to be used pure/just or
	 * tempered intonation.
	 * @param preciseTunings List of precise custom tunings for particular
	 * notes.
	 * @return The path to the generated table file.
	 */
	private String generateFrequencyTable(int tone, int octave,
			int frequency, boolean usePureIntonation,
			Set<PreciseTuning> preciseTunings) {
		
		String table = "";
		
		double[] frequencies;
		
		if (usePureIntonation) {
			frequencies = generatePureIntonationFrequencies(tone, octave,
					frequency);
		} else {
			frequencies = generateTemperedScaleFrequencies(frequency);
		}
		
		frequencies = generateCustomFrequencies(tone, octave, frequencies,
				preciseTunings);
		
		int f; // Frequency in mHz.
		
		for (int i = 0; i < frequencies.length; i++) {
			f = (int) Math.round(frequencies[i] * 1000);
			table +=  f + "\n";
		}
		
		return table;
	}
	
	/**
	 * Generate the frequencies for a pure intonation scale using a tempered
	 * scale as base to obtain the base tone frequency and then generate the
	 * rest of the frequencies using the pure intonation ratios.
	 * @param frequency A4 frequency (by default, 440Hz).
	 * @return A 128 array containing all the calculated frequencies in Hz.
	 */
	private double[] generatePureIntonationFrequencies(int tone,
			int octave, int frequency) {
		
		double[] frequencies = generateTemperedScaleFrequencies(frequency);
		
		int toneNumber = getMidiNoteNumber(tone, octave);
		double toneFrequency = frequencies[toneNumber];
		double octaveRatio;
		double noteRatio;
		for (int i = 0; i < 128; i++) {
			octaveRatio = getOctaveRatio(i, toneNumber);
			noteRatio = getPureIntonationNoteRatio(i, tone);
			frequencies[i] = toneFrequency * octaveRatio * noteRatio;
		}
		
		return frequencies;
	}
	
	/**
	 * Get the ratio to apply to a MIDI note number based on the distance in
	 * octaves to the MIDI tone number used as reference (first degree of the
	 * main octave).
	 * @param noteMidiNumber Note for which we want to get the octave ratio.
	 * @param toneMidiNumber First degree of the main octave.
	 * @return An octave ratio.
	 */
	private double getOctaveRatio(int midiNoteNumber,
			int midiToneNumber) {
		
		double octaveRatio;
		
		int octaveDiff = (midiNoteNumber - midiToneNumber) / 12;
		
		if (octaveDiff > 0) {
			octaveRatio = 1.0 * octaveDiff;
		} else if (octaveDiff < 0) {
			octaveRatio = 1.0 / octaveDiff;
		} else { // octaveDiff == 0
			octaveRatio = 1.0;
		}
		
		return octaveRatio;		
	}
	
	/**
	 * Get the ratio to apply to a MIDI note number based on the distance
	 * between the it and the MIDI tone number, just taking only care of the
	 * note position in the same octave. The distance will always be between 0
	 * and 11.
	 * @param midiNoteNumber Note for which we want to get the note ratio.
	 * @param midiToneNumber First degree of the main octave.
	 * @return A note ratio.
	 */
	private double getPureIntonationNoteRatio(int midiNoteNumber,
			int midiToneNumber) {
		
		double noteRatio;
		
		int noteDiff = (midiNoteNumber - midiToneNumber) % 12;
		if (noteDiff < 0) {
			noteDiff += 12;
		}
		noteRatio = pureIntonationRatios[noteDiff];
		
		return noteRatio;		
	}
	
	/**
	 * Generate the frequencies for a tempered scale using the A4 frequency.
	 * @param frequency A4 frequency (by default, 440Hz).
	 * @return A 128 array containing all the calculated frequencies in Hz.
	 */
	private double[] generateTemperedScaleFrequencies(int frequency) {
		
		double[] table = new double[128];
		
		for (int i = 0; i < 128; i++) {
			table[i] = getNoteFrequencyFromA4Frequency(i, frequency);
		}
		
		return table;
	}
	
	/**
	 * Get the frequency for a note relative to the A4 frequency for a tempered
	 * scale.
	 * @param note MIDI note (0 to 127).
	 * @param frequency A4 frequency (by default, 440Hz).
	 * @return The calculated frequency in Hz.
	 */
	private double getNoteFrequencyFromA4Frequency(int note, int frequency) {
		
		int a4 = 69;
		double n = (note - a4); // Distance to A4.
		
		double fn = Math.pow(2, n/12) * frequency;
				
		return fn;
	}
	
	/**
	 * Generate special frequencies for the given custom tunings.
	 * @param tone Base/tuning tone/note (0 to 11).
	 * @param octave Base/tuning octave (-1 to 9).
	 * @param frequencies Original frequencies.
	 * @param preciseTunings User custom tunings.
	 * @return Custom frequencies.
	 */
	private double[] generateCustomFrequencies(int tone, int octave,
			double[] frequencies, Set<PreciseTuning> preciseTunings) {
		
		double[] customFrequencies = frequencies;
		
		int toneNumber = getMidiNoteNumber(tone, octave);
		double toneFrequency = frequencies[toneNumber];
		int noteNumber;
		double relativeFrequency;
		double customCents;
		double customFrequency;
		for (PreciseTuning p : preciseTunings) {
			noteNumber = getMidiNoteNumber(p.getNote(), p.getOctave());
			relativeFrequency = frequencies[noteNumber];
			customCents = getCents(toneFrequency, relativeFrequency) + p.getCents();
			customFrequency = getRelativeFrequency(toneFrequency, customCents);
			if (isLegitFrequency(noteNumber, customFrequency, customFrequencies)) {
				customFrequencies[noteNumber] = customFrequency;
			}
		}
		
		return customFrequencies;		
	}
	
	/**
	 * Check if a given custom frequency for a given MIDI note number is legit.
	 * That means the custom frequency is between the previous and the next one.
	 * @param midiNoteNumber A MIDI note number.
	 * @param frequency A frequency.
	 * @param frequencies A list of frequencies.
	 * @return A boolean indicating if the frequency is legit.
	 */
	private boolean isLegitFrequency(int midiNoteNumber,
			double frequency, double[] frequencies) {
		
		boolean isLegitFrequency = true;
		
		int previousNoteNumber = midiNoteNumber - 1;
		int nextNoteNumber = midiNoteNumber + 1;
		if (previousNoteNumber >= 0) {
			isLegitFrequency = isLegitFrequency &&
					frequency > frequencies[previousNoteNumber];
		}
		if (nextNoteNumber <= 127) {
			isLegitFrequency = isLegitFrequency &&
					frequency < frequencies[nextNoteNumber];
		}
		
		return isLegitFrequency;
	}
	
	/**
	 * Get the cents corresponding to the ratio (f2/f1).
	 * @param f1 Base/tuning frequency.
	 * @param f2 Frequency relative to the note for which we want to calculate
	 * the cents.
	 * @return Cents.
	 */
	private double getCents(double f1, double f2) {
		
		return getCents(f2/f1);
	}
	
	/**
	 * Get the cents corresponding to the given ratio (f2/f1).
	 * @param ratio Ratio between frequencies (f2/f1).
	 * @return Cents.
	 */
	private double getCents(double ratio) {
		
		double cents = 1200 * (Math.log(ratio) / Math.log(2));
		
		return cents;
	}
	
	/**
	 * Get the relative frequency to the given base/tuning frequency and cents.
	 * @param f1 Base/tuning frequency.
	 * @param cents Cents corresponding to the ratio (f2/f1).
	 * @return A relative frequency (f2).
	 */
	private double getRelativeFrequency(double f1, double cents) {
		
		double f2 = f1 * Math.pow(2, cents/1200);
				
		 return f2;
	}
	
	/**
	 * Get the correspondent MIDI note number given a tone and an octave.
	 * @param tone Base/tuning tone/note (0 to 11).
	 * @param octave Base/tuning octave (-1 to 9).
	 * @return A MIDI note number (0 to 127).
	 */
	private int getMidiNoteNumber(int tone, int octave) {
		
		int noteNumber = ((octave + 1) * 12) + tone; 
		
		return noteNumber;
	}

}
