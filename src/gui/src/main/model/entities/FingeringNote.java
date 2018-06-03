package main.model.entities;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FingeringNote extends Note {

	public FingeringNote() {
		super();
	}
	
	public FingeringNote(String id, int value, String translationId,
			String translationText) {
		
		super(id, value, translationId, translationText);
	}
	
	public static Map<String, FingeringNote> getNotes(String[] ids,
			int[] values, String[] translationIds,
			List<String> translationTexts, ReadingTone readingTone)
			throws IllegalArgumentException {
		
		Map<String, FingeringNote> notes =
				new LinkedHashMap<String, FingeringNote>();
		
		if (ids.length != translationIds.length ||
				ids.length != values.length ||
				ids.length != translationTexts.size()) {
			throw new IllegalArgumentException("Parameters length should match");
		}
		
		String id = null;
		int value = -1;
		String translationId = null;
		String translationText = null;
		FingeringNote note = null;
		int j = -1;
		for (int i = 0; i < ids.length; i++) {
			// Value note name is consequent with the reading tone.
			j = (i + readingTone.getValue()) % ids.length;
			id = ids[j];
			value = values[i];
			translationId = translationIds[j];
			translationText = translationTexts.get(j);
			note = new FingeringNote(id, value, translationId, translationText);
			notes.put(translationText, note);
		}
		
		return notes;
	}
	
}
