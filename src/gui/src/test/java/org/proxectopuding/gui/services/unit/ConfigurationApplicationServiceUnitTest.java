package org.proxectopuding.gui.services.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.proxectopuding.gui.model.entities.BagpipeConfigurationType;
import org.proxectopuding.gui.model.entities.FingeringOffset;
import org.proxectopuding.gui.model.services.ConfigurationApplicationService;
import org.proxectopuding.gui.model.services.DeviceManagerService;
import org.proxectopuding.gui.model.services.impl.ConfigurationApplicationServiceImpl;
import org.proxectopuding.gui.model.utils.I18nManager;

import com.google.common.collect.ImmutableList;

public class ConfigurationApplicationServiceUnitTest {

	private I18nManager i18nManager = mock(I18nManager.class);
	private DeviceManagerService deviceManagerService =
			mock(DeviceManagerService.class);
	private ConfigurationApplicationService confAppService =
			new ConfigurationApplicationServiceImpl(i18nManager,
					deviceManagerService);
	
	@Before
	public void before() {
		
		reset(i18nManager, deviceManagerService);
	}

	@Test
	public void getSelectedBagpipeConfigurationType() {
		
		// Given
		
		
		// When
		confAppService.getSelectedBagpipeConfigurationType();
		
		// Then
		
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
			fail(e.getMessage());
		}
	}
		
	@Test
	public void getTuningToneAsInt() {
		try {
			String tuningTone = null;
			
		} catch (IllegalArgumentException e) {
			fail(e.getMessage());
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
