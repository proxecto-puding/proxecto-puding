package org.proxectopuding.gui.services.acceptance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.proxectopuding.gui.model.entities.BagpipeConfiguration;
import org.proxectopuding.gui.model.entities.BagpipeConfigurationType;
import org.proxectopuding.gui.model.entities.BagpipeDevice;
import org.proxectopuding.gui.model.entities.FingeringOffset;
import org.proxectopuding.gui.model.entities.midiServer.MidiServer;
import org.proxectopuding.gui.model.entities.midiServer.MidiServerConfiguration;
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

public class ConfigurationApplicationServiceAcceptanceTest {
	
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

	private Set<BagpipeDevice> devices;
	private BagpipeDevice device;
	private String productId;
	private Set<BagpipeConfiguration> configurations;
	private BagpipeConfiguration configuration;
	private BagpipeConfigurationType configurationType;
	
	@Before
	public void before() {
		
		// Given
		devices = deviceManagerService.findBagpipeDevices();
		assertTrue(devices.size() > 0);
		device = devices.iterator().next();
		productId = device.getProductId();
		deviceManagerService.setSelectedBagpipeDevice(productId);
		configurations = deviceManagerService
				.findBagpipeConfigurations(productId);
		assertEquals(BagpipeConfigurationType.values().length,
				configurations.size());
		configurations.forEach(configuration ->
				assertEquals(productId, configuration.getProductId()));
		configuration = configurations.iterator().next();
		configurationType =
				BagpipeConfigurationType.from(configuration.getType());
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
