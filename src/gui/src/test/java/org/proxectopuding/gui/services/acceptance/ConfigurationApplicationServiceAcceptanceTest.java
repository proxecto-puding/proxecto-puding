package org.proxectopuding.gui.services.acceptance;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.proxectopuding.gui.model.entities.BagpipeConfigurationType;
import org.proxectopuding.gui.model.entities.FingeringOffset;

import com.google.common.collect.ImmutableList;

public class ConfigurationApplicationServiceAcceptanceTest {

	@Test
	public void getSelectedBagpipeConfigurationType() {
		
	}
	
	@Test
	public void setSelectedBagpipeConfigurationType() {
		
		BagpipeConfigurationType bagpipeConfigurationType = null;
	}
	
	@Test
	public void getReadingTones() {
		
	}
	
	@Test
	public void getReadingTone() {
		
	}
	
	@Test
	public void setReadingTone() {
		
		String readingTone = null;
	}
	
	@Test
	public void getTuningTones() {
		
	}
	
	@Test
	public void getTuningToneAsString() {
		try {
			int tuningTone = -1;
			
		} catch (IllegalArgumentException e) {
			Assert.fail(e.getMessage());
		}
	}
		
	@Test
	public void getTuningToneAsInt() {
		try {
			String tuningTone = null;
			
		} catch (IllegalArgumentException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void getDefaultTuningTone() {
		
	}
	
	@Test
	public void getTuningOctaves() {
		
	}
	
	@Test
	public void getDefaultTuningOctave() {
		
	}
	
	@Test
	public void getSamples() {
		
	}
	
	@Test
	public void getDefaultFingeringTypesEnabled() {
		
	}

	@Test
	public void getSample() {
		
	}
	
	@Test
	public void setSample() {
		
		String sample = null;
	}
	
	@Test
	public void isDefaultBagEnabled() {
		
	}

	@Test
	public void getDefaultDronesEnabled() {
		
	}

	@Test
	public void getTuningFrequency() {
		
	}
	
	@Test
	public void setTuningFrequency() {
		
		int tuningFrequency = -1;
	}

	@Test
	public void getTuningModes() {
		
	}
	
	@Test
	public void getTuningMode() {
		
	}
	
	@Test
	public void setTuningMode() {
		
		String tuningMode = null;
	}

	@Test
	public void getDefaultTuningMode() {
		
	}

	@Test
	public void getPreciseTuningNotes() {
		
	}

	@Test
	public void getPreciseTuningNote() {
		
	}

	@Test
	public void setPreciseTuningNoteFromString() {
		
		String preciseTuningNote = null;
	}
	
	@Test
	public void setPreciseTuningNoteFromInt() {
		
		int preciseTuningNote = -1;
	}

	@Test
	public void resetPreciseTuningNotes() {
		
	}

	@Test
	public void getPreciseTuningOctaves() {
		
	}

	@Test
	public void getPreciseTuningOctave() {
		
	}

	@Test
	public void setPreciseTuningOctave() {
		
		int preciseTuningOctave = -1;
	}

	@Test
	public void getPreciseTuningCents() {
		
	}

	@Test
	public void setPreciseTuningCents() {
		
		int preciseTuningCents = -1;
	}
	
	@Test
	public void getCustomFingeringNotes() {
		
	}
	
	@Test
	public void getCustomFingeringNote() {
		
	}
	
	@Test
	public void setCustomFingeringNoteFromString() {
		
		String customFingeringNote = null;
	}
	
	@Test
	public void setCustomFingeringNoteFromInt() {
		
		int customFingeringNote = -1;
	}
	
	@Test
	public void resetCustomFingeringNotes() {
		
		
	}

	@Test
	public void getCustomFingeringOctaves() {
		
	}

	@Test
	public void getCustomFingeringOctave() {
		
	}

	@Test
	public void setCustomFingeringOctave() {
		
		Integer customFingeringOctave = -1;
	}

	@Test
	public void getCustomFingeringNumbers() {
		
		List<FingeringOffset> fingerings = ImmutableList.of();
	}

	@Test
	public void getCustomFingeringNumber() {
		
	}

	@Test
	public void setCustomFingeringNumber() {
		
		int customFingeringNumber = -1;
	}

	@Test
	public void addCustomFingeringNumber() {
		
	}

	@Test
	public void getCustomFingering() {
		
		int customFingeringNumber = -1;
	}

	@Test
	public void removeCustomFingeringNumber() {
		
		int customFingeringNumber = -1;
	}

	@Test
	public void isCustomFingeringSensorSelected() {
		
		int sensor = -1;
	}
	
	@Test
	public void setCustomFingeringSensor() {
		
		int sensor = -1;
		boolean isSelected = false;
	}
	
	@Test
	public void getMidiServerConfiguration() {
		
	}
}
