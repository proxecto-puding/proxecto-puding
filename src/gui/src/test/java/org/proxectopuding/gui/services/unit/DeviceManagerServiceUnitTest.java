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
import org.proxectopuding.gui.model.entities.FingeringOffset;
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
		
		String productId = null;
		String type = null;
	}
	
	@Test
	public void findBagpipeConfigurationByPreInitializedConfig() {
		
		BagpipeConfiguration configuration = null;
	}
	
	@Test
	public void sendBagpipeConfiguration() {
		try {
			BagpipeConfiguration configuration = null;
		} catch (IllegalArgumentException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void getBagpipeConfiguration() {
		
		String productId = null;
		String type = null;
	}
	
	@Test
	public void getVolume() {
		try {
			String productId = null;
			
		} catch(IllegalArgumentException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void setVolume() {
		try {
			String productId = null;
			int volume = -1;
			
		} catch (IllegalArgumentException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void getTuningTone() {
		try {
			String productId = null;
			
		} catch (IllegalArgumentException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void setTuningTone() {
		try {
			String productId = null;
			int tuningTone = -1;
			
		} catch (IllegalArgumentException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void getTuningOctave() {
		try {
			String productId = null;
			
		} catch (IllegalArgumentException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void setTuningOctave() {
		try {
			String productId = null;
			int tuningOctave = -1;
			
		} catch (IllegalArgumentException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void getFingeringTypesEnabled() {
		try {
			String productId = null;
			
		} catch (IllegalArgumentException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void setFingeringTypesEnabled() {
		try {
			String productId = null;
			List<Boolean> fingeringTypes = ImmutableList.of();
			
		} catch (IllegalArgumentException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void isBagEnabled() {
		try {
			String productId = null;
			
		} catch (IllegalArgumentException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void setBagEnabled() {
		try {
			String productId = null;
			boolean bagEnabled = false;
			
		} catch (IllegalArgumentException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void getDronesEnabled() {
		try {
			String productId = null;
			
		} catch (IllegalArgumentException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void setDronesEnabled() {
		try {
			String productId = null;
			List<Boolean> drones = ImmutableList.of();
			
		} catch (IllegalArgumentException e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void getBagPressure() {
		try {
			String productId = null;
			
		} catch (IllegalArgumentException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void setBagPressure() {
		try {
			String productId = null;
			int bagPressure = -1;
			
		} catch (IllegalArgumentException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void getFingerings() {
		try {
			String productId = null;
			
		} catch (IllegalArgumentException e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void setFingerings() {
		
		String productId = null;
		List<FingeringOffset> fingerings = ImmutableList.of();
	}
	
	// Private
	
	private BagpipeDevice getDevice() {
		
		BagpipeDevice device = new BagpipeDevice();
		device.setProductId(PRODUCT_ID);
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
		
		return configuration;
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
}
