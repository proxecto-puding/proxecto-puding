package org.proxectopuding.gui.services.integration;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.proxectopuding.gui.model.entities.BagpipeConfigurationType;
import org.proxectopuding.gui.model.entities.FingeringOffset;
import org.proxectopuding.gui.model.entities.midiServer.MidiServer;
import org.proxectopuding.gui.model.entities.midiServer.MidiServerGeneral;
import org.proxectopuding.gui.model.entities.midiServer.MidiServerUnix;
import org.proxectopuding.gui.model.services.ConfigurationApplicationService;
import org.proxectopuding.gui.model.services.DeviceManagerService;
import org.proxectopuding.gui.model.services.MidiService;
import org.proxectopuding.gui.model.services.impl.ConfigurationApplicationServiceImpl;
import org.proxectopuding.gui.model.services.impl.DeviceManagerServiceImpl;
import org.proxectopuding.gui.model.services.impl.MidiServiceImpl;
import org.proxectopuding.gui.model.utils.ConfigurationManager;
import org.proxectopuding.gui.model.utils.DeviceManager;
import org.proxectopuding.gui.model.utils.FileDownload;
import org.proxectopuding.gui.model.utils.FileDownloader;
import org.proxectopuding.gui.model.utils.FileUtils;
import org.proxectopuding.gui.model.utils.I18nManager;
import org.proxectopuding.gui.model.utils.MidiUtils;
import org.proxectopuding.gui.model.utils.OperativeSystemManager;
import org.proxectopuding.gui.model.utils.PropertiesManager;
import org.proxectopuding.gui.model.utils.SoundFontManager;
import org.proxectopuding.gui.model.utils.connection.ConnectionManager;
import org.proxectopuding.gui.model.utils.connection.ConnectionManagerJsscImpl;

import com.google.common.collect.ImmutableList;

public class ConfigurationApplicationServiceIntegrationTest {
	
	private FileUtils fileUtils = new FileUtils();
	private FileDownload fileDownload = new FileDownload(fileUtils);
	private FileDownloader fileDownloader = new FileDownloader(fileDownload);
	private SoundFontManager soundFontManager =
			new SoundFontManager(fileDownloader, fileUtils);
	private OperativeSystemManager operativeSystemManager =
			new OperativeSystemManager();
	private MidiUtils midiUtils = new MidiUtils();
	private MidiServerGeneral midiServerGeneral =
			new MidiServerGeneral(soundFontManager, operativeSystemManager,
					fileUtils, midiUtils);
	private MidiServer midiServer = new MidiServerUnix(midiServerGeneral);
	private MidiService midiService = new MidiServiceImpl(midiServer);
	private ConnectionManager connectionManager =
			new ConnectionManagerJsscImpl(operativeSystemManager, midiService);
	private DeviceManager deviceManager = new DeviceManager();
	private DeviceManagerService deviceManagerService =
			new DeviceManagerServiceImpl(connectionManager, deviceManager);
	
	private PropertiesManager propertiesManager = new PropertiesManager();
	private ConfigurationManager configurationManager =
			new ConfigurationManager(propertiesManager);
	private I18nManager i18nManager =
			new I18nManager(configurationManager, operativeSystemManager,
					propertiesManager);
	private ConfigurationApplicationService confAppService =
			new ConfigurationApplicationServiceImpl(i18nManager,
					deviceManagerService);

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
