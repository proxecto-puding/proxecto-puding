package org.proxectopuding.gui.services.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.proxectopuding.gui.model.entities.BagpipeConfiguration;
import org.proxectopuding.gui.model.entities.BagpipeConfigurationType;
import org.proxectopuding.gui.model.entities.BagpipeDevice;
import org.proxectopuding.gui.model.entities.FingeringConfiguration;
import org.proxectopuding.gui.model.entities.FingeringOffset;
import org.proxectopuding.gui.model.entities.SelectionConfiguration;
import org.proxectopuding.gui.model.entities.SensitivityConfiguration;
import org.proxectopuding.gui.model.entities.StartConfiguration;
import org.proxectopuding.gui.model.entities.TuningConfiguration;
import org.proxectopuding.gui.model.entities.midiServer.MidiServerConfiguration;
import org.proxectopuding.gui.model.services.ConfigurationApplicationService;
import org.proxectopuding.gui.model.services.DeviceManagerService;
import org.proxectopuding.gui.model.services.impl.ConfigurationApplicationServiceImpl;
import org.proxectopuding.gui.model.utils.ConfigurationManager;
import org.proxectopuding.gui.model.utils.I18nManager;
import org.proxectopuding.gui.model.utils.OperativeSystemManager;
import org.proxectopuding.gui.model.utils.PropertiesManager;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

public class ConfigurationApplicationServiceUnitTest {
	
	private static final String PRODUCT_ID = "PRODUCT_ID";
	private BagpipeConfigurationType CONFIGURATION_TYPE =
			BagpipeConfigurationType.SELECT;

	private PropertiesManager propertiesManager = new PropertiesManager();
	private ConfigurationManager configurationManager =
			new ConfigurationManager(propertiesManager);
	private OperativeSystemManager operativeSystemManager =
			new OperativeSystemManager();
	private I18nManager i18nManager = new I18nManager(configurationManager,
			operativeSystemManager, propertiesManager);
	private DeviceManagerService deviceManagerService =
			mock(DeviceManagerService.class);
	private ConfigurationApplicationService confAppService =
			new ConfigurationApplicationServiceImpl(i18nManager,
					deviceManagerService);

	@Before
	public void before() {
		
		reset(deviceManagerService);
	}
	
	@Test
	public void getSelectedBagpipeConfigurationType() {
	
		// Given
		BagpipeConfigurationType expectedConfigurationType = CONFIGURATION_TYPE;
		confAppService.setSelectedBagpipeConfigurationType(
				expectedConfigurationType);
		
		// When
		BagpipeConfigurationType configurationType =
				confAppService.getSelectedBagpipeConfigurationType();
		
		// Then
		assertEquals(expectedConfigurationType, configurationType);
	}
	
	@Test
	public void setSelectedBagpipeConfigurationType() {
		
		// Given
		BagpipeConfigurationType expectedConfigurationType = CONFIGURATION_TYPE;
		
		// When
		confAppService.setSelectedBagpipeConfigurationType(
				expectedConfigurationType);
		
		// Then
		BagpipeConfigurationType configurationType =
				confAppService.getSelectedBagpipeConfigurationType();
		assertEquals(expectedConfigurationType, configurationType);
	}
	
	@Test
	public void getCustomFingeringNumbers() {
		
		// Given
		List<FingeringOffset> fingerings = getFingeringOffsets();
		assertTrue(fingerings.size() > 0);
		FingeringOffset fingering = fingerings.iterator().next();
		int customFingeringNote = fingering.getOffset() % 12;
		int customFingeringOctave =
				(fingering.getOffset() - customFingeringNote) * 12;
		confAppService.setCustomFingeringOctave(customFingeringOctave);
		confAppService.setCustomFingeringNote(customFingeringNote);
		
		// When
		List<Integer> customFingeringNumbers =
				confAppService.getCustomFingeringNumbers(fingerings);
		
		// Then
		assertTrue(customFingeringNumbers.size() > 0);
	}

	@Test
	public void getCustomFingeringNumber() {
		
		// Given
		List<FingeringOffset> fingerings = getFingeringOffsets();
		assertTrue(fingerings.size() > 0);
		FingeringOffset fingering = fingerings.iterator().next();
		int customFingeringNote = fingering.getOffset() % 12;
		int customFingeringOctave =
				(fingering.getOffset() - customFingeringNote) * 12;
		confAppService.setCustomFingeringOctave(customFingeringOctave);
		confAppService.setCustomFingeringNote(customFingeringNote);
		List<Integer> customFingeringNumbers =
				confAppService.getCustomFingeringNumbers(fingerings);
		assertTrue(customFingeringNumbers.size() > 0);
		int expectedCustomFingeringNumber = customFingeringNumbers.get(0); 
		confAppService.setCustomFingeringNumber(expectedCustomFingeringNumber);
		
		// When
		int customFingeringNumber = confAppService.getCustomFingeringNumber();
		
		// Then
		assertEquals(expectedCustomFingeringNumber, customFingeringNumber);
	}

	@Test
	public void setCustomFingeringNumber() {
		
		// Given
		List<FingeringOffset> fingerings = getFingeringOffsets();
		assertTrue(fingerings.size() > 0);
		FingeringOffset fingering = fingerings.iterator().next();
		int customFingeringNote = fingering.getOffset() % 12;
		int customFingeringOctave =
				(fingering.getOffset() - customFingeringNote) * 12;
		confAppService.setCustomFingeringOctave(customFingeringOctave);
		confAppService.setCustomFingeringNote(customFingeringNote);
		List<Integer> customFingeringNumbers =
				confAppService.getCustomFingeringNumbers(fingerings);
		assertTrue(customFingeringNumbers.size() > 0);
		int expectedCustomFingeringNumber = customFingeringNumbers.get(0);  
		
		// When
		confAppService.setCustomFingeringNumber(expectedCustomFingeringNumber);
		
		// Then
		int customFingeringNumber = confAppService.getCustomFingeringNumber();
		assertEquals(expectedCustomFingeringNumber, customFingeringNumber);
	}

	@Test
	public void isCustomFingeringSensorSelected() {
		
		// Given
		List<FingeringOffset> fingerings = getFingeringOffsets();
		assertTrue(fingerings.size() > 0);
		FingeringOffset fingering = fingerings.iterator().next();
		int customFingeringNote = fingering.getOffset() % 12;
		int customFingeringOctave =
				(fingering.getOffset() - customFingeringNote) * 12;
		confAppService.setCustomFingeringOctave(customFingeringOctave);
		confAppService.setCustomFingeringNote(customFingeringNote);
		List<Integer> customFingeringNumbers =
				confAppService.getCustomFingeringNumbers(fingerings);
		assertTrue(customFingeringNumbers.size() > 0);
		int expectedCustomFingeringNumber = customFingeringNumbers.get(0); 
		confAppService.setCustomFingeringNumber(expectedCustomFingeringNumber);
		int sensor = 0;
		confAppService.setCustomFingeringSensor(sensor, true);
		
		// When
		boolean isCustomFingeringSensorSelected =
				confAppService.isCustomFingeringSensorSelected(sensor);
		
		// Then
		assertTrue(isCustomFingeringSensorSelected);
	}
	
	@Test
	public void setCustomFingeringSensor() {
		
		// Given
		List<FingeringOffset> fingerings = getFingeringOffsets();
		assertTrue(fingerings.size() > 0);
		FingeringOffset fingering = fingerings.iterator().next();
		int customFingeringNote = fingering.getOffset() % 12;
		int customFingeringOctave =
				(fingering.getOffset() - customFingeringNote) * 12;
		confAppService.setCustomFingeringOctave(customFingeringOctave);
		confAppService.setCustomFingeringNote(customFingeringNote);
		List<Integer> customFingeringNumbers =
				confAppService.getCustomFingeringNumbers(fingerings);
		assertTrue(customFingeringNumbers.size() > 0);
		int expectedCustomFingeringNumber = customFingeringNumbers.get(0); 
		confAppService.setCustomFingeringNumber(expectedCustomFingeringNumber);
		int sensor = 0;
		
		// When
		confAppService.setCustomFingeringSensor(sensor, true);
		
		// Then
		boolean isCustomFingeringSensorSelected =
				confAppService.isCustomFingeringSensorSelected(sensor);
		assertTrue(isCustomFingeringSensorSelected);
	}
	
	@Test
	public void getMidiServerConfiguration() {
		
		// Given
		when(deviceManagerService.getSelectedBagpipeDevice())
				.thenReturn(getDevice());
		when(deviceManagerService.getTuningTone(PRODUCT_ID))
				.thenReturn(getTuningConfigurationData().getTone());
		when(deviceManagerService.getTuningOctave(PRODUCT_ID))
				.thenReturn(getTuningConfigurationData().getOctave());
		
		// When
		MidiServerConfiguration midiServerConfiguration =
				confAppService.getMidiServerConfiguration();
		
		// Then
		verify(deviceManagerService, times(1)).getSelectedBagpipeDevice();
		verify(deviceManagerService, times(1)).getTuningTone(PRODUCT_ID);
		verify(deviceManagerService, times(1)).getTuningOctave(PRODUCT_ID);
		assertNotNull(midiServerConfiguration);
	}
	
	// Private
	
	private BagpipeDevice getDevice() {
		
		BagpipeDevice device = new BagpipeDevice();
		device.setProductId(PRODUCT_ID);
		device.setConfigurations(getConfigurations(PRODUCT_ID));
		return device;
	}
	
	private BagpipeConfiguration getConfiguration(String productId,
			BagpipeConfigurationType type) {
		
		BagpipeConfiguration configuration = new BagpipeConfiguration();
		
		configuration.setProductId(productId);
		configuration.setType(type.name());
		switch (type) {
			case START:
				configuration.setData(getStartConfigurationData());
				break;
			case SELECT:
				configuration.setData(getSelectionConfigurationData());
				break;
			case TUNING:
				configuration.setData(getTuningConfigurationData());
				break;
			case SENSIT:
				configuration.setData(getSensitivityConfigurationData());
				break;
			case FINGER:
				configuration.setData(getFingeringConfigurationData());
				break;
		}
		
		return configuration;
	}
	
	private Set<BagpipeConfiguration> getConfigurations(String productId) {
		
		return ImmutableSet.copyOf(BagpipeConfigurationType.values()).stream()
				.map(type -> getConfiguration(productId, type))
				.collect(ImmutableSet.toImmutableSet());
	}
	
	private StartConfiguration getStartConfigurationData() {
		
		return new StartConfiguration();
	}
	
	private SelectionConfiguration getSelectionConfigurationData() {
		
		SelectionConfiguration data = new SelectionConfiguration();

		data.setVolume(100);
		data.setBagEnabled(true);
		data.setDronesEnabled(ImmutableList.of(true, false, false));
		data.setFingeringTypes(ImmutableList.of(true, false, false));
		
		return data;
	}
	
	private TuningConfiguration getTuningConfigurationData() {
		
		TuningConfiguration data = new TuningConfiguration();
		
		data.setTone(0);
		data.setOctave(4);
		
		return data;
	}
	
	private SensitivityConfiguration getSensitivityConfigurationData() {
		
		SensitivityConfiguration data = new SensitivityConfiguration();
		
		data.setBagPressure(100);
		
		return data;
	}
	
	private FingeringConfiguration getFingeringConfigurationData() {
		
		FingeringConfiguration data = new FingeringConfiguration();
		
		data.setFingerings(getFingeringOffsets());
		
		return data;
	}
	
	private List<FingeringOffset> getFingeringOffsets() {
		
		ImmutableList.Builder<FingeringOffset> fingerings =
				ImmutableList.builder();
		
		for (int i = 0; i < 3; i++) {
			FingeringOffset fingeringOffset = new FingeringOffset();
			fingeringOffset.setFingering(i);
			fingeringOffset.setOffset(i);
			fingerings.add(fingeringOffset);
		}
		
		return fingerings.build();
	}
}
