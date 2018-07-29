package org.proxectopuding.gui.services.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.proxectopuding.gui.model.entities.BagpipeConfiguration;
import org.proxectopuding.gui.model.entities.BagpipeConfigurationType;
import org.proxectopuding.gui.model.entities.BagpipeDevice;
import org.proxectopuding.gui.model.entities.FingeringConfiguration;
import org.proxectopuding.gui.model.entities.FingeringOffset;
import org.proxectopuding.gui.model.entities.SelectionConfiguration;
import org.proxectopuding.gui.model.entities.SensitivityConfiguration;
import org.proxectopuding.gui.model.entities.TuningConfiguration;
import org.proxectopuding.gui.model.entities.midiServer.MidiServer;
import org.proxectopuding.gui.model.entities.midiServer.MidiServerGeneral;
import org.proxectopuding.gui.model.entities.midiServer.MidiServerUnix;
import org.proxectopuding.gui.model.services.DeviceManagerService;
import org.proxectopuding.gui.model.services.MidiService;
import org.proxectopuding.gui.model.services.impl.DeviceManagerServiceImpl;
import org.proxectopuding.gui.model.services.impl.MidiServiceImpl;
import org.proxectopuding.gui.model.utils.DeviceManager;
import org.proxectopuding.gui.model.utils.FileDownload;
import org.proxectopuding.gui.model.utils.FileDownloader;
import org.proxectopuding.gui.model.utils.FileUtils;
import org.proxectopuding.gui.model.utils.MidiUtils;
import org.proxectopuding.gui.model.utils.OperativeSystemManager;
import org.proxectopuding.gui.model.utils.SoundFontManager;
import org.proxectopuding.gui.model.utils.connection.ConnectionManager;
import org.proxectopuding.gui.model.utils.connection.ConnectionManagerJsscImpl;

public class DeviceManagerServiceIntegrationTest {
	
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

	@Test
	public void findBagpipeDevices() {
		
		// When
		Set<BagpipeDevice> devices = deviceManagerService.findBagpipeDevices();
		
		// Then
		assertTrue(devices.size() > 0);
	}
	
	@Test
	public void getBagpipeDeviceIds() {
		
		// Given
		Set<BagpipeDevice> devices = deviceManagerService.findBagpipeDevices();
		assertTrue(devices.size() > 0);
		
		// When
		List<String> deviceIds = deviceManagerService.getBagpipeDeviceIds();
		
		// Then
		assertTrue(deviceIds.size() > 0);
	}
	
	@Test
	public void getSelectedBagpipeDevice() {
		
		// Given
		Set<BagpipeDevice> devices = deviceManagerService.findBagpipeDevices();
		assertTrue(devices.size() > 0);
		String expectedProductId = devices.iterator().next().getProductId();
		deviceManagerService.setSelectedBagpipeDevice(expectedProductId);
	
		// When
		BagpipeDevice selectedDevice =
				deviceManagerService.getSelectedBagpipeDevice();
		
		// Then
		assertEquals(expectedProductId, selectedDevice.getProductId());
	}
	
	@Test
	public void setSelectedBagpipeDevice() {
		
		// Given
		Set<BagpipeDevice> devices = deviceManagerService.findBagpipeDevices();
		assertTrue(devices.size() > 0);
		String expectedProductId = devices.iterator().next().getProductId();
		
		// When
		deviceManagerService.setSelectedBagpipeDevice(expectedProductId);
		
		// Then
		BagpipeDevice selectedDevice =
				deviceManagerService.getSelectedBagpipeDevice();
		assertEquals(expectedProductId, selectedDevice.getProductId());
	}
	
	@Test
	public void findBagpipeConfigurations() {
		
		// Given
		Set<BagpipeDevice> devices = deviceManagerService.findBagpipeDevices();
		assertTrue(devices.size() > 0);
		String expectedProductId = devices.iterator().next().getProductId();
		
		// When
		Set<BagpipeConfiguration> configurations =
				deviceManagerService.findBagpipeConfigurations(expectedProductId);
		
		// Then
		assertEquals(BagpipeConfigurationType.values().length,
				configurations.size());
		configurations.forEach(configuration ->
				assertEquals(expectedProductId, configuration.getProductId()));
	}
	
	@Test
	public void findBagpipeConfigurationByProductIdAndType() {
		
		// Given
		Set<BagpipeDevice> devices = deviceManagerService.findBagpipeDevices();
		assertTrue(devices.size() > 0);
		String expectedProductId = devices.iterator().next().getProductId();
		BagpipeConfigurationType expectedConfigurationType =
				BagpipeConfigurationType.START;
		
		// When
		BagpipeConfiguration configuration =
				deviceManagerService.findBagpipeConfiguration(expectedProductId,
						expectedConfigurationType.name());
		
		// Then
		assertEquals(expectedProductId, configuration.getProductId());
		assertEquals(expectedConfigurationType.name(), configuration.getType());
	}
	
	@Test
	public void findBagpipeConfigurationByPreInitializedConfig() {
		
		// Given
		Set<BagpipeDevice> devices = deviceManagerService.findBagpipeDevices();
		assertTrue(devices.size() > 0);
		String expectedProductId = devices.iterator().next().getProductId();
		BagpipeConfigurationType expectedConfigurationType =
				BagpipeConfigurationType.START;
		BagpipeConfiguration expectedConfiguration = new BagpipeConfiguration();
		expectedConfiguration.setProductId(expectedProductId);
		expectedConfiguration.setType(expectedConfigurationType.name());
		
		// When
		BagpipeConfiguration configuration = deviceManagerService
				.findBagpipeConfiguration(expectedConfiguration);
		
		// Then
		assertEquals(expectedProductId, configuration.getProductId());
		assertEquals(expectedConfigurationType.name(), configuration.getType());
	}
	
	@Test
	public void sendBagpipeConfiguration() {
		try {
			// Given
			Set<BagpipeDevice> devices = deviceManagerService.findBagpipeDevices();
			assertTrue(devices.size() > 0);
			String expectedProductId = devices.iterator().next().getProductId();
			Set<BagpipeConfiguration> configurations = deviceManagerService
					.findBagpipeConfigurations(expectedProductId);
			assertTrue(configurations.size() > 0);
			BagpipeConfiguration expectedConfiguration =
					configurations.iterator().next();
			
			// When
			deviceManagerService.sendBagpipeConfiguration(expectedConfiguration);
			
			// Then
			BagpipeConfiguration configuration = deviceManagerService.
					findBagpipeConfiguration(expectedConfiguration);
			assertEquals(expectedConfiguration.getProductId(),
					configuration.getProductId());
			assertEquals(expectedConfiguration.getType(),
					configuration.getType());
			
		} catch (IllegalArgumentException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void getBagpipeConfiguration() {
		
		// Given
		Set<BagpipeDevice> devices = deviceManagerService.findBagpipeDevices();
		assertTrue(devices.size() > 0);
		String expectedProductId = devices.iterator().next().getProductId();
		Set<BagpipeConfiguration> configurations = deviceManagerService
				.findBagpipeConfigurations(expectedProductId);
		assertTrue(configurations.size() > 0);
		String expectedConfigurationType =
				configurations.iterator().next().getType();
		
		// When
		BagpipeConfiguration configuration = deviceManagerService
				.getBagpipeConfiguration(expectedProductId,
						expectedConfigurationType);
		
		// Then
		assertEquals(expectedProductId, configuration.getProductId());
		assertEquals(expectedConfigurationType, configuration.getType());
	}
	
	@Test
	public void getVolume() {
		try {
			// Given
			Set<BagpipeDevice> devices = deviceManagerService.findBagpipeDevices();
			assertTrue(devices.size() > 0);
			String expectedProductId = devices.iterator().next().getProductId();
			Set<BagpipeConfiguration> configurations = deviceManagerService
					.findBagpipeConfigurations(expectedProductId);
			assertTrue(configurations.size() > 0);
			String expectedConfigurationType =
					BagpipeConfigurationType.SELECT.name();
			int expectedVolume = ((SelectionConfiguration) deviceManagerService
					.getBagpipeConfiguration(expectedProductId,
							expectedConfigurationType).getData()).getVolume();
			
			// When
			int volume = deviceManagerService.getVolume(expectedProductId);
			
			// Then
			assertEquals(expectedVolume, volume);
			
		} catch(IllegalArgumentException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void setVolume() {
		try {
			// Given
			Set<BagpipeDevice> devices = deviceManagerService.findBagpipeDevices();
			assertTrue(devices.size() > 0);
			String expectedProductId = devices.iterator().next().getProductId();
			Set<BagpipeConfiguration> configurations = deviceManagerService
					.findBagpipeConfigurations(expectedProductId);
			assertTrue(configurations.size() > 0);
			String expectedConfigurationType =
					BagpipeConfigurationType.SELECT.name();
			int expectedVolume = ((SelectionConfiguration) deviceManagerService
					.getBagpipeConfiguration(expectedProductId,
							expectedConfigurationType).getData()).getVolume();
			
			// When
			deviceManagerService.setVolume(expectedProductId, expectedVolume);
			
			// Then
			int volume = deviceManagerService.getVolume(expectedProductId);
			assertEquals(expectedVolume, volume);
			
		} catch (IllegalArgumentException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void getTuningTone() {
		try {
			// Given
			Set<BagpipeDevice> devices = deviceManagerService.findBagpipeDevices();
			assertTrue(devices.size() > 0);
			String expectedProductId = devices.iterator().next().getProductId();
			Set<BagpipeConfiguration> configurations = deviceManagerService
					.findBagpipeConfigurations(expectedProductId);
			assertTrue(configurations.size() > 0);
			String expectedConfigurationType =
					BagpipeConfigurationType.TUNING.name();
			int expectedTuningTone = ((TuningConfiguration) deviceManagerService
					.getBagpipeConfiguration(expectedProductId,
							expectedConfigurationType).getData()).getTone(); 
			
			// When
			int tuningTone = deviceManagerService.getTuningTone(expectedProductId);
			
			// Then
			assertEquals(expectedTuningTone, tuningTone);
			
		} catch (IllegalArgumentException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void setTuningTone() {
		try {
			// Given
			Set<BagpipeDevice> devices = deviceManagerService.findBagpipeDevices();
			assertTrue(devices.size() > 0);
			String expectedProductId = devices.iterator().next().getProductId();
			Set<BagpipeConfiguration> configurations = deviceManagerService
					.findBagpipeConfigurations(expectedProductId);
			assertTrue(configurations.size() > 0);
			String expectedConfigurationType =
					BagpipeConfigurationType.TUNING.name();
			int expectedTuningTone = ((TuningConfiguration) deviceManagerService
					.getBagpipeConfiguration(expectedProductId,
							expectedConfigurationType).getData()).getTone(); 
			
			// When
			deviceManagerService.setTuningTone(expectedProductId, expectedTuningTone);
			
			// Then
			int tuningTone = deviceManagerService.getTuningTone(expectedProductId);
			assertEquals(expectedTuningTone, tuningTone);
			
		} catch (IllegalArgumentException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void getTuningOctave() {
		try {
			// Given
			Set<BagpipeDevice> devices = deviceManagerService.findBagpipeDevices();
			assertTrue(devices.size() > 0);
			String expectedProductId = devices.iterator().next().getProductId();
			Set<BagpipeConfiguration> configurations = deviceManagerService
					.findBagpipeConfigurations(expectedProductId);
			assertTrue(configurations.size() > 0);
			String expectedConfigurationType =
					BagpipeConfigurationType.TUNING.name();
			int expectedTuningOctave = ((TuningConfiguration) deviceManagerService
					.getBagpipeConfiguration(expectedProductId,
							expectedConfigurationType).getData()).getOctave(); 
			
			// When
			int tuningOctave =
					deviceManagerService.getTuningOctave(expectedProductId);
			
			// Then
			assertEquals(expectedTuningOctave, tuningOctave);
			
		} catch (IllegalArgumentException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void setTuningOctave() {
		try {
			// Given
			Set<BagpipeDevice> devices = deviceManagerService.findBagpipeDevices();
			assertTrue(devices.size() > 0);
			String expectedProductId = devices.iterator().next().getProductId();
			Set<BagpipeConfiguration> configurations = deviceManagerService
					.findBagpipeConfigurations(expectedProductId);
			assertTrue(configurations.size() > 0);
			String expectedConfigurationType =
					BagpipeConfigurationType.TUNING.name();
			int expectedTuningOctave = ((TuningConfiguration) deviceManagerService
					.getBagpipeConfiguration(expectedProductId,
							expectedConfigurationType).getData()).getOctave();
			
			// When
			deviceManagerService.setTuningOctave(expectedProductId,
					expectedTuningOctave);
			
			// Then
			int tuningOctave =
					deviceManagerService.getTuningOctave(expectedProductId);
			assertEquals(expectedTuningOctave, tuningOctave);
			
		} catch (IllegalArgumentException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void getFingeringTypesEnabled() {
		try {
			// Given
			Set<BagpipeDevice> devices = deviceManagerService.findBagpipeDevices();
			assertTrue(devices.size() > 0);
			String expectedProductId = devices.iterator().next().getProductId();
			Set<BagpipeConfiguration> configurations = deviceManagerService
					.findBagpipeConfigurations(expectedProductId);
			assertTrue(configurations.size() > 0);
			String expectedConfigurationType =
					BagpipeConfigurationType.SELECT.name();
			List<Boolean> expectedFingeringTypesEnabled =
					((SelectionConfiguration) deviceManagerService
							.getBagpipeConfiguration(expectedProductId,
									expectedConfigurationType).getData())
									.getFingeringTypes();
			
			// When
			List<Boolean> fingeringTypesEnabled = deviceManagerService
					.getFingeringTypesEnabled(expectedProductId);
			
			// Then
			assertEquals(expectedFingeringTypesEnabled, fingeringTypesEnabled);
			
		} catch (IllegalArgumentException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void setFingeringTypesEnabled() {
		try {
			// Given
			Set<BagpipeDevice> devices = deviceManagerService.findBagpipeDevices();
			assertTrue(devices.size() > 0);
			String expectedProductId = devices.iterator().next().getProductId();
			Set<BagpipeConfiguration> configurations = deviceManagerService
					.findBagpipeConfigurations(expectedProductId);
			assertTrue(configurations.size() > 0);
			String expectedConfigurationType =
					BagpipeConfigurationType.SELECT.name();
			List<Boolean> expectedFingeringTypesEnabled =
					((SelectionConfiguration) deviceManagerService
							.getBagpipeConfiguration(expectedProductId,
									expectedConfigurationType).getData())
									.getFingeringTypes();
			
			// When
			deviceManagerService.setFingeringTypesEnabled(expectedProductId,
					expectedFingeringTypesEnabled);
			
			// Then
			List<Boolean> fingeringTypesEnabled = deviceManagerService
					.getFingeringTypesEnabled(expectedProductId);
			assertEquals(expectedFingeringTypesEnabled, fingeringTypesEnabled);
			
		} catch (IllegalArgumentException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void isBagEnabled() {
		try {
			// Given
			Set<BagpipeDevice> devices = deviceManagerService.findBagpipeDevices();
			assertTrue(devices.size() > 0);
			String expectedProductId = devices.iterator().next().getProductId();
			Set<BagpipeConfiguration> configurations = deviceManagerService
					.findBagpipeConfigurations(expectedProductId);
			assertTrue(configurations.size() > 0);
			String expectedConfigurationType =
					BagpipeConfigurationType.SELECT.name();
			boolean expectedBagEnabled =
					((SelectionConfiguration) deviceManagerService
					.getBagpipeConfiguration(expectedProductId,
							expectedConfigurationType).getData()).isBagEnabled(); 
			
			// When
			boolean bagEnabled = deviceManagerService
					.isBagEnabled(expectedProductId);
			
			// Then
			assertEquals(expectedBagEnabled, bagEnabled);
			
		} catch (IllegalArgumentException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void setBagEnabled() {
		try {
			// Given
			Set<BagpipeDevice> devices = deviceManagerService.findBagpipeDevices();
			assertTrue(devices.size() > 0);
			String expectedProductId = devices.iterator().next().getProductId();
			Set<BagpipeConfiguration> configurations = deviceManagerService
					.findBagpipeConfigurations(expectedProductId);
			assertTrue(configurations.size() > 0);
			String expectedConfigurationType =
					BagpipeConfigurationType.SELECT.name();
			boolean expectedBagEnabled =
					((SelectionConfiguration) deviceManagerService
					.getBagpipeConfiguration(expectedProductId,
							expectedConfigurationType).getData()).isBagEnabled(); 
			
			// When
			deviceManagerService.setBagEnabled(expectedProductId,
					expectedBagEnabled);
			
			// Then
			boolean bagEnabled =
					deviceManagerService.isBagEnabled(expectedProductId);
			assertEquals(expectedBagEnabled, bagEnabled);
			
		} catch (IllegalArgumentException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void getDronesEnabled() {
		try {
			// Given
			Set<BagpipeDevice> devices = deviceManagerService.findBagpipeDevices();
			assertTrue(devices.size() > 0);
			String expectedProductId = devices.iterator().next().getProductId();
			Set<BagpipeConfiguration> configurations = deviceManagerService
					.findBagpipeConfigurations(expectedProductId);
			assertTrue(configurations.size() > 0);
			String expectedConfigurationType =
					BagpipeConfigurationType.SELECT.name();
			List<Boolean> expectedDronesEnabled =
					((SelectionConfiguration) deviceManagerService
							.getBagpipeConfiguration(expectedProductId,
									expectedConfigurationType).getData())
									.getDronesEnabled(); 
			
			// When
			List<Boolean> dronesEnabled =
					deviceManagerService.getDronesEnabled(expectedProductId);
			
			// Then
			assertEquals(expectedDronesEnabled, dronesEnabled);
			
		} catch (IllegalArgumentException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void setDronesEnabled() {
		try {
			// Given
			Set<BagpipeDevice> devices = deviceManagerService.findBagpipeDevices();
			assertTrue(devices.size() > 0);
			String expectedProductId = devices.iterator().next().getProductId();
			Set<BagpipeConfiguration> configurations = deviceManagerService
					.findBagpipeConfigurations(expectedProductId);
			assertTrue(configurations.size() > 0);
			String expectedConfigurationType =
					BagpipeConfigurationType.SELECT.name();
			List<Boolean> expectedDronesEnabled =
					((SelectionConfiguration) deviceManagerService
							.getBagpipeConfiguration(expectedProductId,
									expectedConfigurationType).getData())
									.getDronesEnabled(); 
			
			// When
			deviceManagerService.setDronesEnabled(expectedProductId,
					expectedDronesEnabled);
			
			// Then
			List<Boolean> dronesEnabled =
					deviceManagerService.getDronesEnabled(expectedProductId);
			assertEquals(expectedDronesEnabled, dronesEnabled);
			
		} catch (IllegalArgumentException e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void getBagPressure() {
		try {
			// Given
			Set<BagpipeDevice> devices = deviceManagerService.findBagpipeDevices();
			assertTrue(devices.size() > 0);
			String expectedProductId = devices.iterator().next().getProductId();
			Set<BagpipeConfiguration> configurations = deviceManagerService
					.findBagpipeConfigurations(expectedProductId);
			assertTrue(configurations.size() > 0);
			String expectedConfigurationType =
					BagpipeConfigurationType.SENSIT.name();
			int expectedBagPressure =
					((SensitivityConfiguration) deviceManagerService
					.getBagpipeConfiguration(expectedProductId,
							expectedConfigurationType).getData())
							.getBagPressure();
			
			// When
			int bagPressure = deviceManagerService.getBagPressure(expectedProductId);
			
			// Then
			assertEquals(expectedBagPressure, bagPressure);
			
		} catch (IllegalArgumentException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void setBagPressure() {
		try {
			// Given
			Set<BagpipeDevice> devices = deviceManagerService.findBagpipeDevices();
			assertTrue(devices.size() > 0);
			String expectedProductId = devices.iterator().next().getProductId();
			Set<BagpipeConfiguration> configurations = deviceManagerService
					.findBagpipeConfigurations(expectedProductId);
			assertTrue(configurations.size() > 0);
			String expectedConfigurationType =
					BagpipeConfigurationType.SENSIT.name();
			int expectedBagPressure =
					((SensitivityConfiguration) deviceManagerService
					.getBagpipeConfiguration(expectedProductId,
							expectedConfigurationType).getData())
							.getBagPressure();
			
			// When
			deviceManagerService.setBagPressure(expectedProductId,
					expectedBagPressure);
			
			// Then
			int bagPressure =
					deviceManagerService.getBagPressure(expectedProductId);
			assertEquals(expectedBagPressure, bagPressure);
			
		} catch (IllegalArgumentException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void getFingerings() {
		try {
			// Given
			Set<BagpipeDevice> devices = deviceManagerService.findBagpipeDevices();
			assertTrue(devices.size() > 0);
			String expectedProductId = devices.iterator().next().getProductId();
			Set<BagpipeConfiguration> configurations = deviceManagerService
					.findBagpipeConfigurations(expectedProductId);
			assertTrue(configurations.size() > 0);
			String expectedConfigurationType =
					BagpipeConfigurationType.FINGER.name();
			List<FingeringOffset> expectedFingerings =
					((FingeringConfiguration) deviceManagerService
							.getBagpipeConfiguration(expectedProductId,
									expectedConfigurationType).getData())
									.getFingerings(); 
			
			// When
			List<FingeringOffset> fingerings =
					deviceManagerService.getFingerings(expectedProductId);
			
			// Then
			assertEquals(expectedFingerings, fingerings);
			
		} catch (IllegalArgumentException e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void setFingerings() {
		
		// Given
		Set<BagpipeDevice> devices = deviceManagerService.findBagpipeDevices();
		assertTrue(devices.size() > 0);
		String expectedProductId = devices.iterator().next().getProductId();
		Set<BagpipeConfiguration> configurations = deviceManagerService
				.findBagpipeConfigurations(expectedProductId);
		assertTrue(configurations.size() > 0);
		String expectedConfigurationType =
				BagpipeConfigurationType.FINGER.name();
		List<FingeringOffset> expectedFingerings =
				((FingeringConfiguration) deviceManagerService
						.getBagpipeConfiguration(expectedProductId,
								expectedConfigurationType).getData())
								.getFingerings(); 
		
		// When
		deviceManagerService.setFingerings(expectedProductId, expectedFingerings);
		
		// Then
		List<FingeringOffset> fingerings =
				deviceManagerService.getFingerings(expectedProductId);
		assertEquals(expectedFingerings, fingerings);
	}
}
