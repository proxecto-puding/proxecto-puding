package main.model.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import main.model.entities.PreciseTuning;
import main.model.entities.PreciseTuningNote;
import main.model.entities.ReadingTone;
import main.model.entities.Sample;
import main.model.entities.TuningMode;
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
	
	private static final Boolean DEFAULT_BAG_ENABLED = true;
	
	private static final Boolean[] DEFAULT_DRONES_ENABLED =
		{true, false, false};
	
	private static final int DEFAULT_TUNING_FREQUENCY = 440;
	
	private static final String[] tuningModeTranslationIds =
		{"tuningModes.tempered", "tuningModes.pure"};
	private static String DEFAULT_TUNING_MODE;
	
	private static final String[] preciseTuningNoteIds =
		{"C", "Cs", "D", "Ds", "E", "F", "Fs", "G", "Gs", "A", "As", "B"};
	private static final int[] preciseTuningNoteValues =
		{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
	private static final String[] preciseTuningNoteTranslationIds =
		{"preciseTuningNotes.C", "preciseTuningNotes.Cs",
		"preciseTuningNotes.D", "preciseTuningNotes.Ds",
		"preciseTuningNotes.E",
		"preciseTuningNotes.F", "preciseTuningNotes.Fs",
		"preciseTuningNotes.G", "preciseTuningNotes.Gs",
		"preciseTuningNotes.A", "preciseTuningNotes.As",
		"preciseTuningNotes.B"};
	private static PreciseTuningNote DEFAULT_PRECISE_TUNING_NOTE;
	
	private static Integer[] preciseTuningOctaves =
		{-1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
	private static final int DEFAULT_PRECISE_TUNING_OCTAVE = 4;
	
	private static final int DEFAULT_PRECISE_TUNING_CENTS = 0;
	
	private static Map<String, ReadingTone> readingTones;
	private static ReadingTone readingTone;
	private static Map<String, TuningTone> tuningTones;
	private static Map<String, Sample> samples;
	private static String sample;
	private static int tuningFrequency;
	private static Map<String, TuningMode> tuningModes;
	private static String tuningMode;
	private static Map<String, PreciseTuningNote> preciseTuningNotes;
	private static PreciseTuningNote preciseTuningNote;
	private static int preciseTuningOctave;
	private static Map<Integer, PreciseTuning> preciseTunings;
	
	static {
		setReadingTones();
		setTuningTones();
		setSamples();
		setTuningFrequency();
		setTuningModes();
		setPreciseTuningNotes();
		setPreciseTuningOctave();
		setPreciseTuningTunings();
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
		return new ArrayList<String>(samples.keySet());
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
	public List<Boolean> getDefaultFingeringTypesEnabled() {
		return new ArrayList<Boolean>(Arrays.asList(DEFAULT_FINGERING_TYPES));
	}
	
	@Override
	public Boolean isDefaultBagEnabled() {
		return DEFAULT_BAG_ENABLED;
	}
	
	@Override
	public List<Boolean> getDefaultDronesEnabled() {
		return new ArrayList<Boolean>(Arrays.asList(DEFAULT_DRONES_ENABLED));
	}
	
	@Override
	public int getTuningFrequency() {
		return tuningFrequency;
	}
	
	@Override
	public void setTuningFrequency(int tuningFrequency) {
		ConfigurationApplicationServiceImpl.tuningFrequency = tuningFrequency;
	}
	
	@Override
	public List<String> getTuningModes() {
		return new ArrayList<String>(tuningModes.keySet());
	}
	
	@Override
	public String getTuningMode() {
		return tuningMode;
	}
	
	@Override
	public void setTuningMode(String tuningMode) {
		ConfigurationApplicationServiceImpl.tuningMode = tuningMode;
	}
	
	@Override
	public String getDefaultTuningMode() {
		return DEFAULT_TUNING_MODE;
	}
	
	@Override
	public List<String> getPreciseTuningNotes() {
		return new ArrayList<String>(preciseTuningNotes.keySet());
	}
	
	@Override
	public String getPreciseTuningNote() {
		return preciseTuningNote.getTranslationText();
	}
	
	@Override
	public void setPreciseTuningNote(String preciseTuningNote) {
		
		PreciseTuningNote newPreciseTuningNote =
				preciseTuningNotes.get(preciseTuningNote); 
		if (newPreciseTuningNote != null) {
			ConfigurationApplicationServiceImpl.preciseTuningNote =
					newPreciseTuningNote;
		}
	}
	
	@Override
	public void setPreciseTuningNote(int preciseTuningNote) {
		
		String note = null;
		
		for (PreciseTuningNote n : preciseTuningNotes.values()) {
			if (n.getValue() == preciseTuningNote) {
				note = n.getTranslationText();
				break;
			}
		}
		
		if (note != null) {
			setPreciseTuningNote(note);
		}
	}
	
	@Override
	public List<String> resetPreciseTuningNotes() {
		
		int current = preciseTuningNote.getValue();
		setPreciseTuningNotes();
		setPreciseTuningNote(current);
		return getPreciseTuningNotes();
	}
	
	@Override
	public List<Integer> getPreciseTuningOctaves() {
		return new ArrayList<Integer>(Arrays.asList(preciseTuningOctaves));
	}
	
	@Override
	public int getPreciseTuningOctave() {
		return preciseTuningOctave;
	}
	
	@Override
	public void setPreciseTuningOctave(int preciseTuningOctave) {
		
		ConfigurationApplicationServiceImpl.preciseTuningOctave =
				preciseTuningOctave;
	}
	
	@Override
	public int getPreciseTuningCents() {

		Integer key = preciseTuningNote.getValue();
		PreciseTuning preciseTuning = preciseTunings.get(key);
		if (preciseTuning != null) {
			return preciseTuning.getCents();
		} else {
			return DEFAULT_PRECISE_TUNING_CENTS;
		}
	}
	
	@Override
	public void setPreciseTuningCents(int preciseTuningCents) {
	
		setPreciseTuning(preciseTuningNote, preciseTuningOctave,
				preciseTuningCents);
	}
	
	private void setPreciseTuning(PreciseTuningNote note, int octave,
			int cents) {
		
		if (cents == 0) {
			preciseTunings.remove(note.getValue());
		} else {
			Integer key = note.getValue();
			PreciseTuning preciseTuning =
					new PreciseTuning(key, octave, cents);
			preciseTunings.put(key, preciseTuning);
		}
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
		List<Sample> samplesList = Arrays.asList(Sample.values());
		
		if (translations.size() != samplesList.size()) {
			throw new IllegalArgumentException("Parameters size should match");
		}
		
		samples = new LinkedHashMap<String, Sample>();
		String key = null;
		Sample value = null;
		for (int i = 0; i < translations.size(); i++) {
			key = translations.get(i);
			value = samplesList.get(i);
			samples.put(key, value);
		}
		
		// Default sample: MIDI
		DEFAULT_SAMPLE = translations.get(0);
		sample = DEFAULT_SAMPLE;
	}
	
	private static void setTuningFrequency() {
		tuningFrequency = DEFAULT_TUNING_FREQUENCY;
	}
	
	private static void setTuningModes() {
	
		List<String> translations =
				I18nManager.getTranslations(tuningModeTranslationIds);
		List<TuningMode> modes = Arrays.asList(TuningMode.values());
		
		if (translations.size() != modes.size()) {
			throw new IllegalArgumentException("Parameters size should match");
		}
		
		tuningModes = new LinkedHashMap<String, TuningMode>();
		String key = null;
		TuningMode value = null;
		for (int i = 0; i < translations.size(); i++) {
			key = translations.get(i);
			value = modes.get(i);
			tuningModes.put(key, value);
		}
		
		// Default tuning mode: Tempered
		DEFAULT_TUNING_MODE = translations.get(0);		
		tuningMode = DEFAULT_TUNING_MODE;
	}
	
	private static void setPreciseTuningNotes() {
		
		List<String> translations =
				I18nManager.getTranslations(preciseTuningNoteTranslationIds);
		
		preciseTuningNotes = PreciseTuningNote.getNotes(preciseTuningNoteIds,
				preciseTuningNoteValues, preciseTuningNoteTranslationIds,
				translations, readingTone);
		
		// Default precise tuning note: C, but consequent with the reading tone.
		int i = readingTone.getValue(); 
		DEFAULT_PRECISE_TUNING_NOTE =
				preciseTuningNotes.get(translations.get(i));
		preciseTuningNote = DEFAULT_PRECISE_TUNING_NOTE;
	}
	
	private static void setPreciseTuningOctave() {
		preciseTuningOctave = DEFAULT_PRECISE_TUNING_OCTAVE;
	}
	
	private static void setPreciseTuningTunings() {
		preciseTunings = new LinkedHashMap<Integer, PreciseTuning>();
	}
		
}
