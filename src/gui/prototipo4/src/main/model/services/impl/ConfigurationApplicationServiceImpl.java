package main.model.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import main.model.entities.ReadingTone;
import main.model.services.ConfigurationApplicationService;
import main.model.utils.I18nManager;

public class ConfigurationApplicationServiceImpl
		implements ConfigurationApplicationService {
	
	private static final String[] readingTonesId =
		{"readingTones.C", "readingTones.D"};
	private static ReadingTone DEFAULT_READING_TONE;
	private static ReadingTone readingTone;
	
	private static Map<String, ReadingTone> readingTones;
	
	static {
		setReadingTones();
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
	
	private static void setReadingTones() {
		
		readingTones = new TreeMap<String, ReadingTone>();
		List<String> translations = I18nManager.getTranslations(readingTonesId);
		ReadingTone c = new ReadingTone("C", readingTonesId[0], translations.get(0));
		ReadingTone d = new ReadingTone("D", readingTonesId[1], translations.get(1));
		readingTones.put(translations.get(0), c);
		readingTones.put(translations.get(1), d);
		DEFAULT_READING_TONE = c;
		readingTone = DEFAULT_READING_TONE;
	}
		
}
