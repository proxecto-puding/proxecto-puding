package main.model.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import main.model.entities.ReadingTone;
import main.model.entities.TuningTone;
import main.model.services.ConfigurationApplicationService;
import main.model.utils.I18nManager;

public class ConfigurationApplicationServiceImpl
		implements ConfigurationApplicationService {
	
	private static final String[] readingToneIds =
		{"C", "D"};
	private static final int[] readingToneValues =
		{0, 2};
	private static final String[] readingToneTranslationIds =
		{"readingTones.C", "readingTones.D"};
	private static ReadingTone DEFAULT_READING_TONE;
	private static ReadingTone readingTone;
	
	private static final String[] tuningToneIds =
		{"C", "Cs", "D", "Ds", "E", "F", "Fs", "G", "Gs", "A", "As", "B"};
	private static final int[] tuningToneValues =
		{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
	private static final String[] tuningToneTranslationIds =
		{"tuningTones.C", "tuningTones.Cs",
		"tuningTones.D", "tuningTones.Ds",
		"tuningTones.E",
		"tuningTones.F", "tuningTones.Fs",
		"tuningTones.G", "tuningTones.Gs",
		"tuningTones.A", "tuningTones.As",
		"tuningTones.B"};
	private static TuningTone DEFAULT_TUNING_TONE;
	
	private static Integer[] tuningOctaves =
		{-1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
	private static final int DEFAULT_TUNING_OCTAVE = 4;
	
	private static final String[] sampleTranslationIds =
		{"samples.MIDI", "samples.galician"};
	private static String DEFAULT_SAMPLE;
	
	private static final Boolean[] DEFAULT_FINGERING_TYPES =
		{true, false, false};
	
	private static Map<String, ReadingTone> readingTones;
	private static Map<String, TuningTone> tuningTones;
	private static List<String> samples;
	private static String sample;
	
	static {
		setReadingTones();
		setTuningTones();
		setSamples();
	};
	
	@Override
	public List<String> getReadingTones() {
		
		return new ArrayList<String>(readingTones.keySet());
	}
	
	@Override
	public String getReadingTone() {
		
		return readingTone.getTranslationText();
	}

	@Override
	public void setReadingTone(String readingTone) {
		
		ReadingTone newReadingTone = readingTones.get(readingTone); 
		if (newReadingTone != null) {
			ConfigurationApplicationServiceImpl.readingTone = newReadingTone;
		}
	}
	
	@Override
	public List<String> getTuningTones() {
		
		return new ArrayList<String>(tuningTones.keySet());
	}
	
	@Override
	public String getTuningTone(int tuningTone)
			throws IllegalArgumentException {
		
		String tone = null;
		
		if (tuningTone >= 0 && tuningTone < 12) {
			
			for (TuningTone t : tuningTones.values()) {
				if (t.getValue() == tuningTone) {
					tone = t.getTranslationText();
					break;
				}
			}
			
			if (tone == null) {
				throw new IllegalArgumentException("Provided tuning tone not found");
			}
			
		} else {
			throw new IllegalArgumentException("Provided tuning tone is not in range");
		}
		
		return tone;
	}
	
	@Override
	public int getTuningTone(String tuningTone)
			throws IllegalArgumentException {
		
		int tone = -1;
		
		if (tuningTone != null) {
			if (tuningTones.containsKey(tuningTone)) {
				tone = tuningTones.get(tuningTone).getValue();
			} else {
				throw new IllegalArgumentException("Provided tuning tone not found");
			}
		} else {
			throw new IllegalArgumentException("Tuning tone cannot be null");
		}
		
		return tone;
	}
	
	@Override
	public String getDefaultTuningTone() {
		return DEFAULT_TUNING_TONE.getTranslationText();
	}
	
	@Override
	public List<Integer> getTuningOctaves() {
		return new ArrayList<Integer>(Arrays.asList(tuningOctaves));
	}
	
	@Override
	public int getDefaultTuningOctave() {
		return DEFAULT_TUNING_OCTAVE;
	}
	
	@Override
	public List<String> getSamples() {
		return samples;
	}
	
	@Override
	public String getSample() {
		return sample;
	}
	
	@Override
	public void setSample(String sample) {
		ConfigurationApplicationServiceImpl.sample = sample;
	}
	
	@Override
	public List<Boolean> getDefaultFingeringTypes() {
		return new ArrayList<Boolean>(Arrays.asList(DEFAULT_FINGERING_TYPES));
	}
	
	private static void setReadingTones() {
		
		List<String> translations =
				I18nManager.getTranslations(readingToneTranslationIds);
		
		readingTones = ReadingTone.getTones(readingToneIds, readingToneValues,
				readingToneTranslationIds, translations);
		
		// Default reading tone: C
		DEFAULT_READING_TONE = readingTones.get(translations.get(0));
		readingTone = DEFAULT_READING_TONE;
	}
	
	private static void setTuningTones() {
		
		List<String> translations =
				I18nManager.getTranslations(tuningToneTranslationIds);
		
		tuningTones = TuningTone.getTones(tuningToneIds, tuningToneValues,
				tuningToneTranslationIds, translations);
		
		// Default tuning tone: C
		DEFAULT_TUNING_TONE = tuningTones.get(translations.get(0));
	}
	
	private static void setSamples() {
		
		List<String> translations =
				I18nManager.getTranslations(sampleTranslationIds);
		
		samples = new ArrayList<String>(translations);
		
		// Default sample: MIDI
		DEFAULT_SAMPLE = samples.get(0);
		sample = DEFAULT_SAMPLE;
	}
		
}
