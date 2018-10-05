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

package org.proxectopuding.gui.model.entities;

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
