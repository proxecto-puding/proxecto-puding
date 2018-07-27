package org.proxectopuding.gui.model.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.proxectopuding.gui.model.entities.BagpipeConfigurationType;
import org.proxectopuding.gui.model.entities.FingeringNote;
import org.proxectopuding.gui.model.entities.FingeringOffset;
import org.proxectopuding.gui.model.entities.PreciseTuning;
import org.proxectopuding.gui.model.entities.PreciseTuningNote;
import org.proxectopuding.gui.model.entities.ReadingTone;
import org.proxectopuding.gui.model.entities.Sample;
import org.proxectopuding.gui.model.entities.TuningMode;
import org.proxectopuding.gui.model.entities.TuningTone;
import org.proxectopuding.gui.model.entities.midiServer.MidiServerConfiguration;
import org.proxectopuding.gui.model.services.ConfigurationApplicationService;
import org.proxectopuding.gui.model.services.DeviceManagerService;
import org.proxectopuding.gui.model.utils.I18nManager;

import com.google.inject.Inject;

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
	
	private static final String[] customFingeringNoteIds =
		{"C", "Cs", "D", "Ds", "E", "F", "Fs", "G", "Gs", "A", "As", "B"};
	private static final int[] customFingeringNoteValues =
		{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
	private static final String[] customFingeringNoteTranslationIds =
		{"customFingeringNotes.C", "customFingeringNotes.Cs",
		"customFingeringNotes.D", "customFingeringNotes.Ds",
		"customFingeringNotes.E",
		"customFingeringNotes.F", "customFingeringNotes.Fs",
		"customFingeringNotes.G", "customFingeringNotes.Gs",
		"customFingeringNotes.A", "customFingeringNotes.As",
		"customFingeringNotes.B"};
	private static FingeringNote DEFAULT_CUSTOM_FINGERING_NOTE;
	
	private static Integer[] customFingeringOctaves =
		{-1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
	private static final int DEFAULT_CUSTOM_FINGERING_OCTAVE = 4;
	
	private BagpipeConfigurationType bagpipeConfigurationType;
	private Map<String, ReadingTone> readingTones;
	private ReadingTone readingTone;
	private Map<String, TuningTone> tuningTones;
	private Map<String, Sample> samples;
	private String sample;
	private int tuningFrequency;
	private Map<String, TuningMode> tuningModes;
	private String tuningMode;
	private Map<String, PreciseTuningNote> preciseTuningNotes;
	private PreciseTuningNote preciseTuningNote;
	private int preciseTuningOctave;
	private Map<Integer, PreciseTuning> preciseTunings;
	private Map<String, FingeringNote> customFingeringNotes;
	private FingeringNote customFingeringNote;
	private int customFingeringOctave;
	private Map<Integer, FingeringOffset> customFingeringNumbers;
	private int customFingeringNumber;
	
	private final I18nManager i18nManager;
	private final DeviceManagerService deviceManagerService;
	
	@Inject
	public ConfigurationApplicationServiceImpl(I18nManager i18nManager,
			DeviceManagerService deviceManagerService) {
		
		this.i18nManager = i18nManager;
		this.deviceManagerService = deviceManagerService;
		
		setReadingTones();
		setTuningTones();
		setSamples();
		setTuningFrequency();
		setTuningModes();
		setPreciseTuningNotes();
		setPreciseTuningOctave();
		setPreciseTuningTunings();
		setCustomFingeringNotes();
		setCustomFingeringOctave();
		setCustomFingeringNumbers();
	}
	
	@Override
	public BagpipeConfigurationType getSelectedBagpipeConfigurationType() {
		return bagpipeConfigurationType;
	}

	@Override
	public void setSelectedBagpipeConfigurationType(
			BagpipeConfigurationType bagpipeConfigurationType) {
		
		this.bagpipeConfigurationType =
				bagpipeConfigurationType;
	}
	
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
			this.readingTone = newReadingTone;
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
		this.sample = sample;
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
		this.tuningFrequency = tuningFrequency;
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
		this.tuningMode = tuningMode;
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
			this.preciseTuningNote = newPreciseTuningNote;
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
		
		this.preciseTuningOctave = preciseTuningOctave;
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
	
	@Override
	public List<String> getCustomFingeringNotes() {
		return new ArrayList<String>(customFingeringNotes.keySet());
	}

	@Override
	public String getCustomFingeringNote() {
		return customFingeringNote.getTranslationText();
	}

	@Override
	public void setCustomFingeringNote(String customFingeringNote) {
		
		FingeringNote newCustomFingeringNote =
				customFingeringNotes.get(customFingeringNote); 
		if (newCustomFingeringNote != null) {
			this.customFingeringNote = newCustomFingeringNote;
		}
	}
	
	@Override
	public void setCustomFingeringNote(int customFingeringNote) {
		
		String note = null;
		
		for (FingeringNote n : customFingeringNotes.values()) {
			if (n.getValue() == customFingeringNote) {
				note = n.getTranslationText();
				break;
			}
		}
		
		if (note != null) {
			setCustomFingeringNote(note);
		}
	}

	@Override
	public List<String> resetCustomFingeringNotes() {
		
		int current = customFingeringNote.getValue();
		setCustomFingeringNotes();
		setCustomFingeringNote(current);
		return getCustomFingeringNotes();
	}
	
	@Override
	public List<Integer> getCustomFingeringOctaves() {
		return new ArrayList<Integer>(Arrays.asList(customFingeringOctaves));
	}

	@Override
	public int getCustomFingeringOctave() {
		return customFingeringOctave;
	}

	@Override
	public void setCustomFingeringOctave(Integer customFingeringOctave) {
		
		this.customFingeringOctave = customFingeringOctave;
	}
	
	@Override
	public List<Integer> getCustomFingeringNumbers(
			List<FingeringOffset> fingerings) {
		
		customFingeringNumbers = new LinkedHashMap<Integer, FingeringOffset>();
		
		if (customFingeringNote != null) {
			
			int note = customFingeringNote.getValue();
			int octave = customFingeringOctave;
			int offset = (12 * octave) + note;
			int key = 0;
			for (FingeringOffset fingering : fingerings) {
				
				if (offset == fingering.getOffset()) {
					key++;
					customFingeringNumbers.put(key, fingering);
				}
			}
			
			if (!customFingeringNumbers.isEmpty()) {
				customFingeringNumber = 1;
			} 
		}
		
		return new ArrayList<Integer>(customFingeringNumbers.keySet());
	}

	@Override
	public int getCustomFingeringNumber() {
		return customFingeringNumber;
	}

	@Override
	public void setCustomFingeringNumber(int customFingeringNumber) {
		
		this.customFingeringNumber = customFingeringNumber;
	}
	
	@Override
	public int addCustomFingeringNumber() {
		
		int key = customFingeringNumbers.size() + 1;
		FingeringOffset customFingering = new FingeringOffset();
		// 0 - All the holes open.
		int fingering = 0;
		customFingering.setFingering(fingering);
		int note = customFingeringNote.getValue();
		int octave = customFingeringOctave;
		int offset = (12 * octave) + note;
		customFingering.setOffset(offset);
		customFingeringNumbers.put(key, customFingering);
		
		return key;
	}

	@Override
	public FingeringOffset getCustomFingering(int customFingeringNumber) {
		return customFingeringNumbers.get(customFingeringNumber);
	}

	@Override
	public void removeCustomFingeringNumber(int customFingeringNumber) {
		customFingeringNumbers.remove(customFingeringNumber);		
	}
	
	@Override
	public boolean isCustomFingeringSensorSelected(int sensor) {

		boolean isSelected = false;
		
		if (customFingeringNumbers.size() > 0) {
			
			FingeringOffset customFingering =
					customFingeringNumbers.get(customFingeringNumber);
			int fingering = customFingering.getFingering();
			// Bitwise and.
			isSelected = ((fingering & sensor) == 1);
		}
		
		return isSelected;
	}

	@Override
	public FingeringOffset setCustomFingeringSensor(int sensor,
			boolean isSelected) {
		
		FingeringOffset customFingering = null;
		
		if (customFingeringNumbers.size() > 0) {
			
			customFingering =
					customFingeringNumbers.get(customFingeringNumber);
			int fingering = customFingering.getFingering();
			// See: http://stackoverflow.com/questions/4844342/change-bits-value-in-byte
			if (isSelected) {
				fingering = (fingering | (1 << sensor));
			} else {
				fingering = (fingering & ~(1 << sensor));
			}
			customFingering.setFingering(fingering);
		}
		
		return customFingering;
	}
	
	@Override
	public MidiServerConfiguration getMidiServerConfiguration() {
		
		MidiServerConfiguration configuration = new MidiServerConfiguration();
		
		String productId = deviceManagerService.getSelectedBagpipeDevice().getProductId();
		int tuningTone = deviceManagerService.getTuningTone(productId);
		int tuningOctave = deviceManagerService.getTuningOctave(productId);
		boolean usePureIntonationMode = tuningModes.get(tuningMode).equals(TuningMode.PURE);
		boolean useRealSamples = !samples.get(sample).equals(Sample.MIDI);
		boolean useContinuousVibrato = false;
		
		configuration.setTuningTone(tuningTone);
		configuration.setTuningOctave(tuningOctave);
		configuration.setTuningFrequency(tuningFrequency);
		configuration.setUsePureIntonationMode(usePureIntonationMode);
		configuration.setUseRealSamples(useRealSamples);
		configuration.setUseContinuousVibrato(useContinuousVibrato);
		configuration.setPreciseTunings(
				new LinkedHashSet<PreciseTuning>(preciseTunings.values()));
		
		return configuration;
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
	
	private void setReadingTones() {
		
		List<String> translations =
				i18nManager.getTranslations(readingToneTranslationIds);
		
		readingTones = ReadingTone.getTones(readingToneIds, readingToneValues,
				readingToneTranslationIds, translations);
		
		// Default reading tone: C
		DEFAULT_READING_TONE = readingTones.get(translations.get(0));
		readingTone = DEFAULT_READING_TONE;
	}
	
	private void setTuningTones() {
		
		List<String> translations =
				i18nManager.getTranslations(tuningToneTranslationIds);
		
		tuningTones = TuningTone.getTones(tuningToneIds, tuningToneValues,
				tuningToneTranslationIds, translations);
		
		// Default tuning tone: C
		DEFAULT_TUNING_TONE = tuningTones.get(translations.get(0));
	}
	
	private void setSamples() {
		
		List<String> translations =
				i18nManager.getTranslations(sampleTranslationIds);
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
	
	private void setTuningFrequency() {
		tuningFrequency = DEFAULT_TUNING_FREQUENCY;
	}
	
	private void setTuningModes() {
	
		List<String> translations =
				i18nManager.getTranslations(tuningModeTranslationIds);
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
	
	private void setPreciseTuningNotes() {
		
		List<String> translations =
				i18nManager.getTranslations(preciseTuningNoteTranslationIds);
		
		preciseTuningNotes = PreciseTuningNote.getNotes(preciseTuningNoteIds,
				preciseTuningNoteValues, preciseTuningNoteTranslationIds,
				translations, readingTone);
		
		// Default precise tuning note: C, but consequent with the reading tone.
		int i = readingTone.getValue(); 
		DEFAULT_PRECISE_TUNING_NOTE =
				preciseTuningNotes.get(translations.get(i));
		preciseTuningNote = DEFAULT_PRECISE_TUNING_NOTE;
	}
	
	private void setPreciseTuningOctave() {
		preciseTuningOctave = DEFAULT_PRECISE_TUNING_OCTAVE;
	}
	
	private void setPreciseTuningTunings() {
		preciseTunings = new LinkedHashMap<Integer, PreciseTuning>();
	}
	
	private void setCustomFingeringNotes() {
		
		List<String> translations =
				i18nManager.getTranslations(customFingeringNoteTranslationIds);
		
		customFingeringNotes = FingeringNote.getNotes(customFingeringNoteIds,
				customFingeringNoteValues, customFingeringNoteTranslationIds,
				translations, readingTone);
		
		// Default custom fingering note: C, but consequent with the reading tone.
		int i = readingTone.getValue(); 
		DEFAULT_CUSTOM_FINGERING_NOTE =
				customFingeringNotes.get(translations.get(i));
		customFingeringNote = DEFAULT_CUSTOM_FINGERING_NOTE;
	}
	
	private void setCustomFingeringOctave() {
		customFingeringOctave = DEFAULT_CUSTOM_FINGERING_OCTAVE;
	}
	
	private void setCustomFingeringNumbers() {
		customFingeringNumbers = new LinkedHashMap<Integer, FingeringOffset>();
		customFingeringNumber = -1;
	}
		
}
