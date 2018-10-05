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
				.thenReturn(getTuningConfiguration(PRODUCT_ID,
						BagpipeConfigurationType.TUNING).getTone());
		when(deviceManagerService.getTuningOctave(PRODUCT_ID))
				.thenReturn(getTuningConfiguration(PRODUCT_ID,
						BagpipeConfigurationType.TUNING).getOctave());
		
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
		
		BagpipeConfiguration configuration = null;
		
		switch (type) {
			case START:
				configuration = getStartConfiguration(productId, type);
				break;
			case SELECT:
				configuration = getSelectionConfiguration(productId, type);
				break;
			case TUNING:
				configuration = getTuningConfiguration(productId, type);
				break;
			case SENSIT:
				configuration = getSensitivityConfiguration(productId, type);
				break;
			case FINGER:
				configuration = getFingeringConfiguration(productId, type);
				break;
		}
		
		return configuration;
	}
	
	private Set<BagpipeConfiguration> getConfigurations(String productId) {
		
		return ImmutableSet.copyOf(BagpipeConfigurationType.values()).stream()
				.map(type -> getConfiguration(productId, type))
				.collect(ImmutableSet.toImmutableSet());
	}
	
	private StartConfiguration getStartConfiguration(String productId,
			BagpipeConfigurationType type) {
		
		StartConfiguration configuration = new StartConfiguration();
		
		configuration.setProductId(productId);
		configuration.setType(type.name());
		
		return configuration;
	}
	
	private SelectionConfiguration getSelectionConfiguration(String productId,
			BagpipeConfigurationType type) {
		
		SelectionConfiguration configuration = new SelectionConfiguration();
		
		configuration.setProductId(productId);
		configuration.setType(type.name());

		configuration.setVolume(100);
		configuration.setBagEnabled(true);
		configuration.setDronesEnabled(ImmutableList.of(true, false, false));
		configuration.setFingeringTypes(ImmutableList.of(true, false, false));
		
		return configuration;
	}
	
	private TuningConfiguration getTuningConfiguration(String productId,
			BagpipeConfigurationType type) {
		
		TuningConfiguration configuration = new TuningConfiguration();
		
		configuration.setProductId(productId);
		configuration.setType(type.name());
		
		configuration.setTone(0);
		configuration.setOctave(4);
		
		return configuration;
	}
	
	private SensitivityConfiguration getSensitivityConfiguration(
			String productId, BagpipeConfigurationType type) {
		
		SensitivityConfiguration configuration = new SensitivityConfiguration();
		
		configuration.setProductId(productId);
		configuration.setType(type.name());
		
		configuration.setBagPressure(100);
		
		return configuration;
	}
	
	private FingeringConfiguration getFingeringConfiguration(String productId,
			BagpipeConfigurationType type) {
		
		FingeringConfiguration configuration = new FingeringConfiguration();
		
		configuration.setProductId(productId);
		configuration.setType(type.name());
		
		configuration.setFingerings(getFingeringOffsets());
		
		return configuration;
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
