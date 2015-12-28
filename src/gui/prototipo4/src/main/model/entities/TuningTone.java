package main.model.entities;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TuningTone extends Tone {
	
	public TuningTone() {
		super();
	}
	
	public TuningTone(String id, int value, String translationId,
			String translationText) {
		
		super(id, value, translationId, translationText);
	}
	
	public static Map<String, TuningTone> getTones(String[] ids, int[] values,
			String[] translationIds, List<String> translationTexts)
			throws IllegalArgumentException {
		
		Map<String, TuningTone> tones = new LinkedHashMap<String, TuningTone>();
		
		if (ids.length != translationIds.length ||
				ids.length != values.length ||
				ids.length != translationTexts.size()) {
			throw new IllegalArgumentException("Parameters length should match");
		}
		
		String id = null;
		int value = -1;
		String translationId = null;
		String translationText = null;
		TuningTone tone = null;
		for (int i = 0; i < ids.length; i++) {
			
			id = ids[i];
			value = values[i];
			translationId = translationIds[i];
			translationText = translationTexts.get(i);
			tone = new TuningTone(id, value, translationId, translationText);
			tones.put(translationText, tone);
		}
		
		return tones;
	}

}
