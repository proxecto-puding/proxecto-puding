package org.proxectopuding.gui.services.unit;

import static org.junit.Assert.assertEquals;
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
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.stubbing.OngoingStubbing;
import org.proxectopuding.gui.model.entities.BagpipeConfiguration;
import org.proxectopuding.gui.model.entities.BagpipeConfigurationType;
import org.proxectopuding.gui.model.entities.BagpipeDevice;
import org.proxectopuding.gui.model.entities.FingeringConfiguration;
import org.proxectopuding.gui.model.entities.FingeringOffset;
import org.proxectopuding.gui.model.entities.SelectionConfiguration;
import org.proxectopuding.gui.model.entities.SensitivityConfiguration;
import org.proxectopuding.gui.model.entities.StartConfiguration;
import org.proxectopuding.gui.model.entities.TuningConfiguration;
import org.proxectopuding.gui.model.services.DeviceManagerService;
import org.proxectopuding.gui.model.services.impl.DeviceManagerServiceImpl;
import org.proxectopuding.gui.model.utils.DeviceManager;
import org.proxectopuding.gui.model.utils.connection.ConnectionManager;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class DeviceManagerServiceUnitTest {
	
	private static final String PRODUCT_ID = "PRODUCT_ID";

	private Gson gson = new GsonBuilder().setPrettyPrinting().create();
	private ConnectionManager connectionManager = mock(ConnectionManager.class);
	private DeviceManager deviceManager = mock(DeviceManager.class);
	private DeviceManagerService deviceManagerService =
			new DeviceManagerServiceImpl(connectionManager, deviceManager);

	@Before
	public void before() {
		
		reset(connectionManager, deviceManager);
	}
	
	@Test
	public void findBagpipeDevices() {
		
		// Given
		doNothing().when(connectionManager).writeData(anyString());
		when(connectionManager.readData()).thenReturn(getDeviceAsJson(), (String) null);
		doNothing().when(deviceManager).addDevice(getDevice());
		when(deviceManager.getDevices()).thenReturn(getDevices());
		
		// When
		Set<BagpipeDevice> devices = deviceManagerService.findBagpipeDevices();
		
		// Then
		verify(connectionManager, times(2)).writeData(anyString());
		verify(connectionManager, times(1)).readData();
		verify(deviceManager, times(1)).addDevice(getDevice());
		verify(deviceManager, times(2)).getDevices();
		assertEquals(getDevices(), devices);
	}
	
	@Test
	public void getBagpipeDeviceIds() {
		
		// Given
		when(deviceManager.getDevices()).thenReturn(getDevices());
		
		// When
		List<String> deviceIds = deviceManagerService.getBagpipeDeviceIds();
		
		// Then
		verify(deviceManager, times(1)).getDevices();
		assertEquals(getDeviceIds(), deviceIds);
	}
	
	@Test
	public void getSelectedBagpipeDevice() {
	
		// Given
		when(deviceManager.getSelectedDevice()).thenReturn(getDevice());
		
		// When
		BagpipeDevice selectedDevice =
				deviceManagerService.getSelectedBagpipeDevice();
		
		// Then
		verify(deviceManager, times(1)).getSelectedDevice();
		assertEquals(getDevice(), selectedDevice);
	}
	
	@Test
	public void setSelectedBagpipeDevice() {
		
		// Given
		doNothing().when(deviceManager).setSelectedDevice(PRODUCT_ID);
		
		// When
		deviceManagerService.setSelectedBagpipeDevice(PRODUCT_ID);
		
		// Then
		verify(deviceManager, times(1)).setSelectedDevice(PRODUCT_ID);
	}
	
	@Test
	public void findBagpipeConfigurations() {
		
		// Given
		when(deviceManager.getDevice(PRODUCT_ID)).thenReturn(getDevice());
		doNothing().when(connectionManager).writeData(anyString());
		OngoingStubbing<String> stubbing = when(connectionManager.readData());
		for (String configuration : getConfigurationsAsJson(PRODUCT_ID)) {
			stubbing = stubbing.thenReturn(configuration);
		}
		doNothing().when(deviceManager).addConfiguration(eq(PRODUCT_ID), any());
		Set<BagpipeConfiguration> expectedConfigurations =
				getConfigurations(PRODUCT_ID);
		when(deviceManager.getConfigurations(PRODUCT_ID))
				.thenReturn(expectedConfigurations);
		
		// When
		Set<BagpipeConfiguration> configurations =
				deviceManagerService.findBagpipeConfigurations(PRODUCT_ID);
		
		// Then
		verify(connectionManager, times(10)).writeData(anyString());
		verify(connectionManager, times(5)).readData();
		verify(deviceManager, times(5)).addConfiguration(eq(PRODUCT_ID), any());
		verify(deviceManager, times(1)).getConfigurations(PRODUCT_ID);
		assertEquals(expectedConfigurations, configurations);
	}
	
	@Test
	public void findBagpipeConfigurationByProductIdAndType() {
		
		// Given
		when(deviceManager.getDevice(PRODUCT_ID)).thenReturn(getDevice());
		doNothing().when(connectionManager).writeData(anyString());
		BagpipeConfigurationType type = BagpipeConfigurationType.START;
		when(connectionManager.readData()).thenReturn(
				getConfigurationAsJson(PRODUCT_ID, type));
		BagpipeConfiguration expectedConfiguration =
				getConfiguration(PRODUCT_ID, type);
		doNothing().when(deviceManager)
				.addConfiguration(PRODUCT_ID, expectedConfiguration);
		
		// When
		BagpipeConfiguration configuration =
				deviceManagerService.findBagpipeConfiguration(PRODUCT_ID,
						BagpipeConfigurationType.START.name());
		
		// Then
		verify(connectionManager, times(2)).writeData(anyString());
		verify(connectionManager, times(1)).readData();
		verify(deviceManager, times(1))
				.addConfiguration(PRODUCT_ID, expectedConfiguration);
		assertEquals(expectedConfiguration, configuration);
	}
	
	@Test
	public void findBagpipeConfigurationByPreInitializedConfig() {
		
		// Given
		when(deviceManager.getDevice(PRODUCT_ID)).thenReturn(getDevice());
		doNothing().when(connectionManager).writeData(anyString());
		BagpipeConfigurationType type = BagpipeConfigurationType.START;
		when(connectionManager.readData()).thenReturn(
				getConfigurationAsJson(PRODUCT_ID, type));
		BagpipeConfiguration expectedConfiguration =
				getConfiguration(PRODUCT_ID, type);
		doNothing().when(deviceManager)
				.addConfiguration(PRODUCT_ID, expectedConfiguration);
		
		// When
		BagpipeConfiguration configuration = deviceManagerService
				.findBagpipeConfiguration(expectedConfiguration);
		
		// Then
		verify(connectionManager, times(2)).writeData(anyString());
		verify(connectionManager, times(1)).readData();
		verify(deviceManager, times(1))
				.addConfiguration(PRODUCT_ID, expectedConfiguration);
		assertEquals(expectedConfiguration, configuration);
	}
	
	@Test
	public void sendBagpipeConfiguration() {
		try {
			// Given
			BagpipeConfigurationType type = BagpipeConfigurationType.START;
			BagpipeConfiguration configuration =
					getConfiguration(PRODUCT_ID, type);
			
			// When
			deviceManagerService.sendBagpipeConfiguration(configuration);
			
			// Then
			verify(connectionManager, times(1))
					.writeData(getConfigurationAsJson(PRODUCT_ID, type));
			verify(deviceManager, times(1))
					.addConfiguration(PRODUCT_ID, configuration);
			
		} catch (IllegalArgumentException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void getBagpipeConfiguration() {
		
		// Given
		BagpipeConfigurationType type = BagpipeConfigurationType.START;
		BagpipeConfiguration expectedConfiguration =
				getConfiguration(PRODUCT_ID, type);
		when(deviceManager.getConfiguration(PRODUCT_ID, type.name()))
				.thenReturn(expectedConfiguration);
		
		// When
		BagpipeConfiguration configuration = deviceManagerService
				.getBagpipeConfiguration(PRODUCT_ID, type.name());
		
		// Then
		verify(deviceManager, times(1))
				.getConfiguration(PRODUCT_ID, type.name());
		assertEquals(expectedConfiguration, configuration);
	}
	
	@Test
	public void getVolume() {
		try {
			// Given
			BagpipeDevice expectedDevice = getDevice();
			when(deviceManager.getDevice(PRODUCT_ID)).thenReturn(expectedDevice);
			int expectedVolume = ((SelectionConfiguration) expectedDevice
					.getConfigurationByType(
							BagpipeConfigurationType.SELECT.name())
							.getData()).getVolume(); 
			
			// When
			int volume = deviceManagerService.getVolume(PRODUCT_ID);
			
			// Then
			verify(deviceManager, times(1)).getDevice(PRODUCT_ID);
			assertEquals(expectedVolume, volume);
			
		} catch(IllegalArgumentException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void setVolume() {
		try {
			// Given
			when(deviceManager.getDevice(PRODUCT_ID)).thenReturn(getDevice());
			int expectedVolume = 50;
			
			// When
			deviceManagerService.setVolume(PRODUCT_ID, expectedVolume);
			
			// Then
			int volume = deviceManagerService.getVolume(PRODUCT_ID);
			assertEquals(expectedVolume, volume);
			
		} catch (IllegalArgumentException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void getTuningTone() {
		try {
			// Given
			BagpipeDevice expectedDevice = getDevice();
			when(deviceManager.getDevice(PRODUCT_ID)).thenReturn(expectedDevice);
			int expectedTuningTone = ((TuningConfiguration) expectedDevice
					.getConfigurationByType(
							BagpipeConfigurationType.TUNING.name())
							.getData()).getTone(); 
			
			// When
			int tuningTone = deviceManagerService.getTuningTone(PRODUCT_ID);
			
			// Then
			verify(deviceManager, times(1)).getDevice(PRODUCT_ID);
			assertEquals(expectedTuningTone, tuningTone);
			
		} catch (IllegalArgumentException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void setTuningTone() {
		try {
			// Given
			when(deviceManager.getDevice(PRODUCT_ID)).thenReturn(getDevice());
			int expectedTuningTone = 1;
			
			// When
			deviceManagerService.setTuningTone(PRODUCT_ID, expectedTuningTone);
			
			// Then
			int tuningTone = deviceManagerService.getTuningTone(PRODUCT_ID);
			assertEquals(expectedTuningTone, tuningTone);
			
		} catch (IllegalArgumentException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void getTuningOctave() {
		try {
			// Given
			BagpipeDevice expectedDevice = getDevice();
			when(deviceManager.getDevice(PRODUCT_ID)).thenReturn(expectedDevice);
			int expectedTuningOctave = ((TuningConfiguration) expectedDevice
					.getConfigurationByType(
							BagpipeConfigurationType.TUNING.name())
							.getData()).getOctave(); 
			
			// When
			int tuningOctave = deviceManagerService.getTuningOctave(PRODUCT_ID);
			
			// Then
			verify(deviceManager, times(1)).getDevice(PRODUCT_ID);
			assertEquals(expectedTuningOctave, tuningOctave);
			
		} catch (IllegalArgumentException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void setTuningOctave() {
		try {
			// Given
			when(deviceManager.getDevice(PRODUCT_ID)).thenReturn(getDevice());
			int expectedTuningOctave = 5;
			
			// When
			deviceManagerService.setTuningOctave(PRODUCT_ID, expectedTuningOctave);
			
			// Then
			int tuningOctave = deviceManagerService.getTuningOctave(PRODUCT_ID);
			assertEquals(expectedTuningOctave, tuningOctave);
			
		} catch (IllegalArgumentException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void getFingeringTypesEnabled() {
		try {
			// Given
			BagpipeDevice expectedDevice = getDevice();
			when(deviceManager.getDevice(PRODUCT_ID)).thenReturn(expectedDevice);
			List<Boolean> expectedFingeringTypesEnabled =
					((SelectionConfiguration) expectedDevice
							.getConfigurationByType(
									BagpipeConfigurationType.SELECT.name())
									.getData()).getFingeringTypes(); 
			
			// When
			List<Boolean> fingeringTypesEnabled =
					deviceManagerService.getFingeringTypesEnabled(PRODUCT_ID);
			
			// Then
			verify(deviceManager, times(1)).getDevice(PRODUCT_ID);
			assertEquals(expectedFingeringTypesEnabled, fingeringTypesEnabled);
			
		} catch (IllegalArgumentException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void setFingeringTypesEnabled() {
		try {
			// Given
			when(deviceManager.getDevice(PRODUCT_ID)).thenReturn(getDevice());
			List<Boolean> expectedFingeringTypesEnabled =
					ImmutableList.of(true, true, true);
			
			// When
			deviceManagerService.setFingeringTypesEnabled(PRODUCT_ID,
					expectedFingeringTypesEnabled);
			
			// Then
			List<Boolean> fingeringTypesEnabled =
					deviceManagerService.getFingeringTypesEnabled(PRODUCT_ID);
			assertEquals(expectedFingeringTypesEnabled, fingeringTypesEnabled);
			
		} catch (IllegalArgumentException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void isBagEnabled() {
		try {
			// Given
			BagpipeDevice expectedDevice = getDevice();
			when(deviceManager.getDevice(PRODUCT_ID)).thenReturn(expectedDevice);
			boolean expectedBagEnabled = ((SelectionConfiguration) expectedDevice
					.getConfigurationByType(
							BagpipeConfigurationType.SELECT.name())
							.getData()).isBagEnabled(); 
			
			// When
			boolean bagEnabled = deviceManagerService.isBagEnabled(PRODUCT_ID);
			
			// Then
			verify(deviceManager, times(1)).getDevice(PRODUCT_ID);
			assertEquals(expectedBagEnabled, bagEnabled);
			
		} catch (IllegalArgumentException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void setBagEnabled() {
		try {
			// Given
			when(deviceManager.getDevice(PRODUCT_ID)).thenReturn(getDevice());
			boolean expectedBagEnabled = false;
			
			// When
			deviceManagerService.setBagEnabled(PRODUCT_ID, expectedBagEnabled);
			
			// Then
			boolean bagEnabled = deviceManagerService.isBagEnabled(PRODUCT_ID);
			assertEquals(expectedBagEnabled, bagEnabled);
			
		} catch (IllegalArgumentException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void getDronesEnabled() {
		try {
			// Given
			BagpipeDevice expectedDevice = getDevice();
			when(deviceManager.getDevice(PRODUCT_ID)).thenReturn(expectedDevice);
			List<Boolean> expectedDronesEnabled =
					((SelectionConfiguration) expectedDevice
							.getConfigurationByType(
									BagpipeConfigurationType.SELECT.name())
									.getData()).getDronesEnabled(); 
			
			// When
			List<Boolean> dronesEnabled =
					deviceManagerService.getDronesEnabled(PRODUCT_ID);
			
			// Then
			verify(deviceManager, times(1)).getDevice(PRODUCT_ID);
			assertEquals(expectedDronesEnabled, dronesEnabled);
			
		} catch (IllegalArgumentException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void setDronesEnabled() {
		try {
			// Given
			when(deviceManager.getDevice(PRODUCT_ID)).thenReturn(getDevice());
			List<Boolean> expectedDronesEnabled =
					ImmutableList.of(true, true, true);
			
			// When
			deviceManagerService.setDronesEnabled(PRODUCT_ID,
					expectedDronesEnabled);
			
			// Then
			List<Boolean> dronesEnabled =
					deviceManagerService.getDronesEnabled(PRODUCT_ID);
			assertEquals(expectedDronesEnabled, dronesEnabled);
			
		} catch (IllegalArgumentException e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void getBagPressure() {
		try {
			// Given
			BagpipeDevice expectedDevice = getDevice();
			when(deviceManager.getDevice(PRODUCT_ID)).thenReturn(expectedDevice);
			int expectedBagPressure = ((SensitivityConfiguration) expectedDevice
					.getConfigurationByType(
							BagpipeConfigurationType.SENSIT.name())
							.getData()).getBagPressure();
			
			// When
			int bagPressure = deviceManagerService.getBagPressure(PRODUCT_ID);
			
			// Then
			verify(deviceManager, times(1)).getDevice(PRODUCT_ID);
			assertEquals(expectedBagPressure, bagPressure);
			
		} catch (IllegalArgumentException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void setBagPressure() {
		try {
			// Given
			when(deviceManager.getDevice(PRODUCT_ID)).thenReturn(getDevice());
			int expectedBagPressure = 50;
			
			// When
			deviceManagerService.setBagPressure(PRODUCT_ID, expectedBagPressure);
			
			// Then
			int bagPressure = deviceManagerService.getBagPressure(PRODUCT_ID);
			assertEquals(expectedBagPressure, bagPressure);
			
		} catch (IllegalArgumentException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void getFingerings() {
		try {
			// Given
			BagpipeDevice expectedDevice = getDevice();
			when(deviceManager.getDevice(PRODUCT_ID)).thenReturn(expectedDevice);
			List<FingeringOffset> expectedFingerings =
					((FingeringConfiguration) expectedDevice
							.getConfigurationByType(
									BagpipeConfigurationType.FINGER.name())
									.getData()).getFingerings(); 
			
			// When
			List<FingeringOffset> fingerings =
					deviceManagerService.getFingerings(PRODUCT_ID);
			
			// Then
			verify(deviceManager, times(1)).getDevice(PRODUCT_ID);
			assertEquals(expectedFingerings, fingerings);
			
		} catch (IllegalArgumentException e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void setFingerings() {
		
		// Given
		when(deviceManager.getDevice(PRODUCT_ID)).thenReturn(getDevice());
		List<FingeringOffset> expectedFingerings =
				getFingeringOffsets().stream().map(fo -> {
					fo.setOffset(fo.getOffset()+1);	return fo;
				}).collect(ImmutableList.toImmutableList());
		
		// When
		deviceManagerService.setFingerings(PRODUCT_ID, expectedFingerings);
		
		// Then
		List<FingeringOffset> fingerings =
				deviceManagerService.getFingerings(PRODUCT_ID);
		assertEquals(expectedFingerings, fingerings);
	}
	
	// Private
	
	private BagpipeDevice getDevice() {
		
		BagpipeDevice device = new BagpipeDevice();
		device.setProductId(PRODUCT_ID);
		device.setConfigurations(getConfigurations(PRODUCT_ID));
		return device;
	}
	
	private String getDeviceAsJson() {
	
		return gson.toJson(getDevice());
	}
	
	private Set<BagpipeDevice> getDevices() {
		
		return ImmutableSet.of(getDevice());
	}
	
	private List<String> getDeviceIds() {
		
		return getDevices().stream().map(device -> device.getProductId())
				.collect(ImmutableList.toImmutableList());
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
	
	private String getConfigurationAsJson(String productId,
			BagpipeConfigurationType type) {
		
		return gson.toJson(getConfiguration(productId, type));
	}
	
	private Set<BagpipeConfiguration> getConfigurations(String productId) {
		
		return ImmutableSet.copyOf(BagpipeConfigurationType.values()).stream()
				.map(type -> getConfiguration(productId, type))
				.collect(ImmutableSet.toImmutableSet());
	}
	
	private Set<String> getConfigurationsAsJson(String productId) {
		
		return getConfigurations(productId).stream()
				.map(configuration -> gson.toJson(configuration))
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
