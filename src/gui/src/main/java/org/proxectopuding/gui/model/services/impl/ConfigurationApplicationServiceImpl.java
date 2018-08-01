package org.proxectopuding.gui.model.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

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

public class ConfigurationApplicationServiceImpl implements ConfigurationApplicationService {
	
	private static final Logger LOGGER = Logger.getLogger(ConfigurationApplicationServiceImpl.class.getName());
	
	private static final String[] READING_TONE_IDS =
		{"C", "D"};
	private static final int[] READING_TONE_VALUES =
		{0, 2};
	private static final String[] READING_TONE_TRANSLATION_IDS =
		{"readingTones.C", "readingTones.D"};
	private static ReadingTone DEFAULT_READING_TONE;
	
	private static final String[] TUNING_TONE_IDS =
		{"C", "Cs", "D", "Ds", "E", "F", "Fs", "G", "Gs", "A", "As", "B"};
	private static final int[] TUNING_TONE_VALUES =
		{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
	private static final String[] TUNING_TONE_TRANSLATION_IDS =
		{"tuningTones.C", "tuningTones.Cs",
		"tuningTones.D", "tuningTones.Ds",
		"tuningTones.E",
		"tuningTones.F", "tuningTones.Fs",
		"tuningTones.G", "tuningTones.Gs",
		"tuningTones.A", "tuningTones.As",
		"tuningTones.B"};
	private static TuningTone DEFAULT_TUNING_TONE;
	
	private static final Integer[] TUNING_OCTAVES =
		{-1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
	private static final int DEFAULT_TUNING_OCTAVE = 4;
	
	private static final String[] SAMPLE_TRANSLATION_IDS =
		{"samples.MIDI", "samples.galician"};
	private static String DEFAULT_SAMPLE;
	
	private static final Boolean[] DEFAULT_FINGERING_TYPES =
		{true, false, false};
	
	private static final Boolean DEFAULT_BAG_ENABLED = true;
	
	private static final Boolean[] DEFAULT_DRONES_ENABLED =
		{true, false, false};
	
	private static final int DEFAULT_TUNING_FREQUENCY = 440;
	
	private static final String[] TUNING_MODE_TRANSLATION_IDS =
		{"tuningModes.tempered", "tuningModes.pure"};
	private static String DEFAULT_TUNING_MODE;
	
	private static final String[] PRECISE_TUNING_NOTE_IDS =
		{"C", "Cs", "D", "Ds", "E", "F", "Fs", "G", "Gs", "A", "As", "B"};
	private static final int[] PRECISE_TUNING_NOTE_VALUES =
		{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
	private static final String[] PRECISE_TUNING_NOTE_TRANSLATION_IDS =
		{"preciseTuningNotes.C", "preciseTuningNotes.Cs",
		"preciseTuningNotes.D", "preciseTuningNotes.Ds",
		"preciseTuningNotes.E",
		"preciseTuningNotes.F", "preciseTuningNotes.Fs",
		"preciseTuningNotes.G", "preciseTuningNotes.Gs",
		"preciseTuningNotes.A", "preciseTuningNotes.As",
		"preciseTuningNotes.B"};
	private static PreciseTuningNote DEFAULT_PRECISE_TUNING_NOTE;
	
	private static final Integer[] PRECISE_TUNING_OCTAVES =
		{-1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
	private static final int DEFAULT_PRECISE_TUNING_OCTAVE = 4;
	
	private static final int DEFAULT_PRECISE_TUNING_CENTS = 0; 
	
	private static final String[] CUSTOM_FINGERING_NOTE_IDS =
		{"C", "Cs", "D", "Ds", "E", "F", "Fs", "G", "Gs", "A", "As", "B"};
	private static final int[] CUSTOM_FINGERING_NOTE_VALUES =
		{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
	private static final String[] CUSTOM_FINGERING_NOTE_TRANSLATION_IDS =
		{"customFingeringNotes.C", "customFingeringNotes.Cs",
		"customFingeringNotes.D", "customFingeringNotes.Ds",
		"customFingeringNotes.E",
		"customFingeringNotes.F", "customFingeringNotes.Fs",
		"customFingeringNotes.G", "customFingeringNotes.Gs",
		"customFingeringNotes.A", "customFingeringNotes.As",
		"customFingeringNotes.B"};
	private static FingeringNote DEFAULT_CUSTOM_FINGERING_NOTE;
	
	private static final Integer[] CUSTOM_FINGERING_OCTAVES =
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
		
		LOGGER.log(Level.INFO, "Getting selected bagpipe configuration type");
		return bagpipeConfigurationType;
	}

	@Override
	public void setSelectedBagpipeConfigurationType(
			BagpipeConfigurationType bagpipeConfigurationType) {
		
		LOGGER.log(Level.INFO, "Setting selected bagpipe configuration type: {0}", bagpipeConfigurationType);
		this.bagpipeConfigurationType = bagpipeConfigurationType;
	}
	
	@Override
	public List<String> getReadingTones() {
		LOGGER.log(Level.INFO, "Getting reading tones");
		return new ArrayList<String>(readingTones.keySet());
	}
	
	@Override
	public String getReadingTone() {
		LOGGER.log(Level.INFO, "Getting reading tone");
		return readingTone.getTranslationText();
	}

	@Override
	public void setReadingTone(String readingTone) {
		
		LOGGER.log(Level.INFO, "Setting reading tone: {0}", readingTone);
		ReadingTone newReadingTone = readingTones.get(readingTone); 
		if (newReadingTone != null) {
			this.readingTone = newReadingTone;
		}
	}
	
	@Override
	public List<String> getTuningTones() {
		LOGGER.log(Level.INFO, "Getting tuning tones");
		return new ArrayList<String>(tuningTones.keySet());
	}
	
	@Override
	public String getTuningTone(int tuningTone)
			throws IllegalArgumentException {
		
		LOGGER.log(Level.INFO, "Getting tuning tone: {0}", tuningTone);
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
		
		LOGGER.log(Level.INFO, "Getting tuning tone: {0}", tuningTone);
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
		LOGGER.log(Level.INFO, "Getting default tuning tone");
		return DEFAULT_TUNING_TONE.getTranslationText();
	}
	
	@Override
	public List<Integer> getTuningOctaves() {
		LOGGER.log(Level.INFO, "Getting tuning octaves");
		return new ArrayList<Integer>(Arrays.asList(TUNING_OCTAVES));
	}
	
	@Override
	public int getDefaultTuningOctave() {
		LOGGER.log(Level.INFO, "Getting default tuning octave");
		return DEFAULT_TUNING_OCTAVE;
	}
	
	@Override
	public List<String> getSamples() {
		LOGGER.log(Level.INFO, "Getting samples");
		return new ArrayList<String>(samples.keySet());
	}
	
	@Override
	public String getSample() {
		LOGGER.log(Level.INFO, "Getting sample");
		return sample;
	}
	
	@Override
	public void setSample(String sample) {
		LOGGER.log(Level.INFO, "Setting sample: {0}", sample);
		this.sample = sample;
	}
	
	@Override
	public List<Boolean> getDefaultFingeringTypesEnabled() {
		LOGGER.log(Level.INFO, "Getting default fingering types enabled");
		return new ArrayList<Boolean>(Arrays.asList(DEFAULT_FINGERING_TYPES));
	}
	
	@Override
	public Boolean isDefaultBagEnabled() {
		LOGGER.log(Level.INFO, "Is default bag enabled enabled");
		return DEFAULT_BAG_ENABLED;
	}
	
	@Override
	public List<Boolean> getDefaultDronesEnabled() {
		LOGGER.log(Level.INFO, "Getting default drones enabled");
		return new ArrayList<Boolean>(Arrays.asList(DEFAULT_DRONES_ENABLED));
	}
	
	@Override
	public int getTuningFrequency() {
		LOGGER.log(Level.INFO, "Getting tuning frequency");
		return tuningFrequency;
	}
	
	@Override
	public void setTuningFrequency(int tuningFrequency) {
		LOGGER.log(Level.INFO, "Setting tuning frequency: {0}", tuningFrequency);
		this.tuningFrequency = tuningFrequency;
	}
	
	@Override
	public List<String> getTuningModes() {
		LOGGER.log(Level.INFO, "Getting tuning modes");
		return new ArrayList<String>(tuningModes.keySet());
	}
	
	@Override
	public String getTuningMode() {
		LOGGER.log(Level.INFO, "Getting tuning mode");
		return tuningMode;
	}
	
	@Override
	public void setTuningMode(String tuningMode) {
		LOGGER.log(Level.INFO, "Setting tuning mode: {0}", tuningMode);
		this.tuningMode = tuningMode;
	}
	
	@Override
	public String getDefaultTuningMode() {
		LOGGER.log(Level.INFO, "Getting default tuning mode");
		return DEFAULT_TUNING_MODE;
	}
	
	@Override
	public List<String> getPreciseTuningNotes() {
		LOGGER.log(Level.INFO, "Getting precise tuning notes");
		return new ArrayList<String>(preciseTuningNotes.keySet());
	}
	
	@Override
	public String getPreciseTuningNote() {
		LOGGER.log(Level.INFO, "Getting precise tuning note");
		return preciseTuningNote.getTranslationText();
	}
	
	@Override
	public void setPreciseTuningNote(String preciseTuningNote) {
		
		LOGGER.log(Level.INFO, "Setting precise tuning note: {0}", preciseTuningNote);
		PreciseTuningNote newPreciseTuningNote =
				preciseTuningNotes.get(preciseTuningNote); 
		if (newPreciseTuningNote != null) {
			this.preciseTuningNote = newPreciseTuningNote;
		}
	}
	
	@Override
	public void setPreciseTuningNote(int preciseTuningNote) {
		
		LOGGER.log(Level.INFO, "Setting precise tuning note: {0}", preciseTuningNote);
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
		
		LOGGER.log(Level.INFO, "Reseting precise tuning notes");
		int current = preciseTuningNote.getValue();
		setPreciseTuningNotes();
		setPreciseTuningNote(current);
		return getPreciseTuningNotes();
	}
	
	@Override
	public List<Integer> getPreciseTuningOctaves() {
		LOGGER.log(Level.INFO, "Getting precise tuning octaves");
		return new ArrayList<Integer>(Arrays.asList(PRECISE_TUNING_OCTAVES));
	}
	
	@Override
	public int getPreciseTuningOctave() {
		LOGGER.log(Level.INFO, "Getting precise tuning octave");
		return preciseTuningOctave;
	}
	
	@Override
	public void setPreciseTuningOctave(int preciseTuningOctave) {
		
		LOGGER.log(Level.INFO, "Setting precise tuning octave: {0}", preciseTuningOctave);
		this.preciseTuningOctave = preciseTuningOctave;
	}
	
	@Override
	public int getPreciseTuningCents() {

		LOGGER.log(Level.INFO, "Getting precise tuning cents");
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
	
		LOGGER.log(Level.INFO, "Setting precise tuning cents: {0}", preciseTuningCents);
		setPreciseTuning(preciseTuningNote, preciseTuningOctave,
				preciseTuningCents);
	}
	
	@Override
	public List<String> getCustomFingeringNotes() {
		LOGGER.log(Level.INFO, "Getting custom fingering notes");
		return new ArrayList<String>(customFingeringNotes.keySet());
	}

	@Override
	public String getCustomFingeringNote() {
		LOGGER.log(Level.INFO, "Getting custom fingering note");
		return customFingeringNote.getTranslationText();
	}

	@Override
	public void setCustomFingeringNote(String customFingeringNote) {
		
		LOGGER.log(Level.INFO, "Setting custom fingering note: {0}", customFingeringNote);
		FingeringNote newCustomFingeringNote =
				customFingeringNotes.get(customFingeringNote); 
		if (newCustomFingeringNote != null) {
			this.customFingeringNote = newCustomFingeringNote;
		}
	}
	
	@Override
	public void setCustomFingeringNote(int customFingeringNote) {
		
		LOGGER.log(Level.INFO, "Setting custom fingering note: {0}", customFingeringNote);
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
		
		LOGGER.log(Level.INFO, "Reseting custom fingering notes");
		int current = customFingeringNote.getValue();
		setCustomFingeringNotes();
		setCustomFingeringNote(current);
		return getCustomFingeringNotes();
	}
	
	@Override
	public List<Integer> getCustomFingeringOctaves() {
		LOGGER.log(Level.INFO, "Getting custom fingering octaves");
		return new ArrayList<Integer>(Arrays.asList(CUSTOM_FINGERING_OCTAVES));
	}

	@Override
	public int getCustomFingeringOctave() {
		LOGGER.log(Level.INFO, "Getting custom fingering octave");
		return customFingeringOctave;
	}

	@Override
	public void setCustomFingeringOctave(Integer customFingeringOctave) {
		
		LOGGER.log(Level.INFO, "Setting custom fingering octave: {0}", customFingeringOctave);
		this.customFingeringOctave = customFingeringOctave;
	}
	
	@Override
	public List<Integer> getCustomFingeringNumbers(
			List<FingeringOffset> fingerings) {
		
		LOGGER.log(Level.INFO, "Getting custom fingering numbers: {0}", fingerings);
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
		LOGGER.log(Level.INFO, "Getting custom fingering number");
		return customFingeringNumber;
	}

	@Override
	public void setCustomFingeringNumber(int customFingeringNumber) {
		
		LOGGER.log(Level.INFO, "Setting custom fingering number: {0}", customFingeringNumber);
		this.customFingeringNumber = customFingeringNumber;
	}
	
	@Override
	public int addCustomFingeringNumber() {
		
		LOGGER.log(Level.INFO, "Adding custom fingering number");
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
		LOGGER.log(Level.INFO, "Getting custom fingering: {0}", customFingeringNumber);
		return customFingeringNumbers.get(customFingeringNumber);
	}

	@Override
	public void removeCustomFingeringNumber(int customFingeringNumber) {
		LOGGER.log(Level.INFO, "Removing custom fingering: {0}", customFingeringNumber);
		customFingeringNumbers.remove(customFingeringNumber);		
	}
	
	@Override
	public boolean isCustomFingeringSensorSelected(int sensor) {

		LOGGER.log(Level.INFO, "Is custom fingering sensor selected: {0}", sensor);
		boolean isSelected = false;
		
		if (customFingeringNumbers.size() > 0) {
			
			FingeringOffset customFingering =
					customFingeringNumbers.get(customFingeringNumber);
			int fingering = customFingering.getFingering();
			// See: http://stackoverflow.com/questions/4844342/change-bits-value-in-byte
			isSelected = (fingering == (fingering | (1 << sensor)));
		}
		
		return isSelected;
	}

	@Override
	public FingeringOffset setCustomFingeringSensor(int sensor,
			boolean isSelected) {
		
		LOGGER.log(Level.INFO, "Set custom fingering sensor selected: {0} {1}", new Object[]{sensor, isSelected});
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
		
		LOGGER.log(Level.INFO, "Getting MIDI server configuration");
		MidiServerConfiguration configuration = new MidiServerConfiguration();
		
		String productId =
				deviceManagerService.getSelectedBagpipeDevice().getProductId();
		int tuningTone = deviceManagerService.getTuningTone(productId);
		int tuningOctave = deviceManagerService.getTuningOctave(productId);
		boolean usePureIntonationMode =
				tuningModes.get(tuningMode).equals(TuningMode.PURE);
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
	
	// Private
	
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
				i18nManager.getTranslations(READING_TONE_TRANSLATION_IDS);
		
		readingTones = ReadingTone.getTones(READING_TONE_IDS, READING_TONE_VALUES,
				READING_TONE_TRANSLATION_IDS, translations);
		
		// Default reading tone: C
		DEFAULT_READING_TONE = readingTones.get(translations.get(0));
		readingTone = DEFAULT_READING_TONE;
	}
	
	private void setTuningTones() {
		
		List<String> translations =
				i18nManager.getTranslations(TUNING_TONE_TRANSLATION_IDS);
		
		tuningTones = TuningTone.getTones(TUNING_TONE_IDS, TUNING_TONE_VALUES,
				TUNING_TONE_TRANSLATION_IDS, translations);
		
		// Default tuning tone: C
		DEFAULT_TUNING_TONE = tuningTones.get(translations.get(0));
	}
	
	private void setSamples() {
		
		List<String> translations =
				i18nManager.getTranslations(SAMPLE_TRANSLATION_IDS);
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
				i18nManager.getTranslations(TUNING_MODE_TRANSLATION_IDS);
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
				i18nManager.getTranslations(PRECISE_TUNING_NOTE_TRANSLATION_IDS);
		
		preciseTuningNotes = PreciseTuningNote.getNotes(PRECISE_TUNING_NOTE_IDS,
				PRECISE_TUNING_NOTE_VALUES, PRECISE_TUNING_NOTE_TRANSLATION_IDS,
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
				i18nManager.getTranslations(CUSTOM_FINGERING_NOTE_TRANSLATION_IDS);
		
		customFingeringNotes = FingeringNote.getNotes(CUSTOM_FINGERING_NOTE_IDS,
				CUSTOM_FINGERING_NOTE_VALUES, CUSTOM_FINGERING_NOTE_TRANSLATION_IDS,
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
