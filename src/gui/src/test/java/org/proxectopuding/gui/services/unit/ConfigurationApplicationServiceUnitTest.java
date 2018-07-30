package org.proxectopuding.gui.services.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
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
import org.proxectopuding.gui.model.entities.midiServer.MidiServerConfiguration;
import org.proxectopuding.gui.model.services.ConfigurationApplicationService;
import org.proxectopuding.gui.model.services.DeviceManagerService;
import org.proxectopuding.gui.model.services.impl.ConfigurationApplicationServiceImpl;
import org.proxectopuding.gui.model.utils.I18nManager;

public class ConfigurationApplicationServiceUnitTest {

	private I18nManager i18nManager = mock(I18nManager.class);
	private DeviceManagerService deviceManagerService =
			mock(DeviceManagerService.class);
	private ConfigurationApplicationService confAppService =
			new ConfigurationApplicationServiceImpl(i18nManager,
					deviceManagerService);

	// TODO Implement.
	private String productId;
	private BagpipeConfigurationType configurationType;
	
	@Before
	public void before() {
		
		reset(i18nManager, deviceManagerService);
	}
	
	@Test
	public void getSelectedBagpipeConfigurationType() {
	
		// Given
		BagpipeConfigurationType expectedConfigurationType = configurationType;
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
		BagpipeConfigurationType expectedConfigurationType = configurationType;
		
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
		List<FingeringOffset> fingerings =
				deviceManagerService.getFingerings(productId);
		assertTrue(fingerings.size() > 0);
		
		// When
		List<Integer> customFingeringNumbers =
				confAppService.getCustomFingeringNumbers(fingerings);
		
		// Then
		assertTrue(customFingeringNumbers.size() > 0);
	}

	@Test
	public void getCustomFingeringNumber() {
		
		// Given
		List<FingeringOffset> fingerings =
				deviceManagerService.getFingerings(productId);
		assertTrue(fingerings.size() > 0);
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
		List<FingeringOffset> fingerings =
				deviceManagerService.getFingerings(productId);
		assertTrue(fingerings.size() > 0);
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
		List<FingeringOffset> fingerings =
				deviceManagerService.getFingerings(productId);
		assertTrue(fingerings.size() > 0);
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
		List<FingeringOffset> fingerings =
				deviceManagerService.getFingerings(productId);
		assertTrue(fingerings.size() > 0);
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
		
		// When
		MidiServerConfiguration midiServerConfiguration =
				confAppService.getMidiServerConfiguration();
		
		// Then
		assertNotNull(midiServerConfiguration);
	}
}
