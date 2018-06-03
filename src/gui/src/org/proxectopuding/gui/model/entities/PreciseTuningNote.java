package org.proxectopuding.gui.model.entities;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PreciseTuningNote extends Note {
	
	public PreciseTuningNote() {
		super();
	}
	
	public PreciseTuningNote(String id, int value, String translationId,
			String translationText) {
		
		super(id, value, translationId, translationText);
	}
	
	public static Map<String, PreciseTuningNote> getNotes(String[] ids,
			int[] values, String[] translationIds,
			List<String> translationTexts, ReadingTone readingTone)
			throws IllegalArgumentException {
		
		Map<String, PreciseTuningNote> notes =
				new LinkedHashMap<String, PreciseTuningNote>();
		
		if (ids.length != translationIds.length ||
				ids.length != values.length ||
				ids.length != translationTexts.size()) {
			throw new IllegalArgumentException("Parameters length should match");
		}
		
		String id = null;
		int value = -1;
		String translationId = null;
		String translationText = null;
		PreciseTuningNote note = null;
		int j = -1;
		for (int i = 0; i < ids.length; i++) {
			// Value note name is consequent with the reading tone.
			j = (i + readingTone.getValue()) % ids.length;
			id = ids[j];
			value = values[i];
			translationId = translationIds[j];
			translationText = translationTexts.get(j);
			note = new PreciseTuningNote(id, value, translationId, translationText);
			notes.put(translationText, note);
		}
		
		return notes;
	}

}
