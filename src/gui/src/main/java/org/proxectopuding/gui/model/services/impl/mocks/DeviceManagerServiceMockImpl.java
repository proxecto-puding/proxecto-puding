package org.proxectopuding.gui.model.services.impl.mocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

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
import org.proxectopuding.gui.model.utils.DeviceManager;
import org.proxectopuding.gui.model.utils.connection.ConnectionManager;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;

public class DeviceManagerServiceMockImpl implements DeviceManagerService {
	
	private static final Logger LOGGER = Logger.getLogger(DeviceManagerServiceMockImpl.class.getName());
	
	private static final String PRODUCT_ID = "PRODUCT_ID";
	private static final int MAX_ATTEMPTS = 10;
	
	private DeviceManager deviceManager;
	private Gson gson;
	
	@Inject
	public DeviceManagerServiceMockImpl(ConnectionManager connectionManager,
			DeviceManager deviceManager) {
		try {
			LOGGER.log(Level.INFO, "Loading connection manager");
			this.deviceManager = deviceManager;
			this.gson = new GsonBuilder().setPrettyPrinting().create();			
		} catch(Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
	}
	
	@Override
	public Set<BagpipeDevice> findBagpipeDevices() {
		
		LOGGER.log(Level.INFO, "Looking for connected devices");
		
		sendDiscoveryBeacon();
		
		String response = null;
		BagpipeDevice device = null;
		for (int i = 0; i < MAX_ATTEMPTS; i++) {
			LOGGER.log(Level.INFO, "Attempts: {0}", i + 1);
			try {
				response = getDeviceAsJson();
				LOGGER.log(Level.INFO, "Response: {0}", response);
				device = gson.fromJson(response, BagpipeDevice.class);
				if (device != null) {
					deviceManager.addDevice(device);
					sendAck(device.getProductId());
					break;
				} else {
					LOGGER.log(Level.SEVERE, "Unable to add a new device to the list of known devices. Json: {0}", response);
				}
			} catch (Exception e) {
				LOGGER.log(Level.SEVERE, "Unable to add a new device to the list of known devices", e);
			}
		}
		
		if (deviceManager.getDevices().size() == 0) {
			LOGGER.log(Level.SEVERE, "No devices found");
		}
		LOGGER.log(Level.INFO, "connectionManager.disconnect()");
		
		return deviceManager.getDevices();
	}
	
	@Override
	public List<String> getBagpipeDeviceIds() {
		
		List<String> ids = new ArrayList<String>();
		
		Set<BagpipeDevice> devices = deviceManager.getDevices();
		for (BagpipeDevice device : devices) {
			ids.add(device.getProductId());
		}
		
		return ids;
	}
	
	@Override
	public BagpipeDevice getSelectedBagpipeDevice() {
		return deviceManager.getSelectedDevice();
	}
	
	@Override
	public void setSelectedBagpipeDevice(String productId) {
		deviceManager.setSelectedDevice(productId);
	}
	
	@Override
	public Set<BagpipeConfiguration> findBagpipeConfigurations(
			String productId) {
		
		BagpipeConfigurationType[] types = BagpipeConfigurationType.values();
		for (BagpipeConfigurationType type : types) {
			findBagpipeConfiguration(productId, type.toString());
		}
		
		return deviceManager.getConfigurations(productId);
	}

	@Override
	public BagpipeConfiguration findBagpipeConfiguration(
			BagpipeConfiguration configuration) {
		
		String productId = configuration.getProductId();
		String type = configuration.getType();
		
		return findBagpipeConfiguration(productId, type);
	}
	
	@Override
	public BagpipeConfiguration findBagpipeConfiguration(
			String productId, String type) {
		
		BagpipeConfiguration configuration = null;
		
		BagpipeDevice device = deviceManager.getDevice(productId);
		if (device == null) {
			LOGGER.log(Level.SEVERE, "Unable to find the configuration for productId: {0}. Device not found", productId);
			return configuration;
		}
		
		BagpipeConfiguration config = new BagpipeConfiguration();
		config.setProductId(productId);
		config.setType(type);
		String request = gson.toJson(config, BagpipeConfiguration.class);
		String response = null;
		for (int i = 0; i < MAX_ATTEMPTS; i++) {
			LOGGER.log(Level.INFO, "Attempts: {0}", i + 1);
			try {
				LOGGER.log(Level.INFO, "Request: {0}", request);
				response = getConfigurationAsJson(config.getProductId(),
						BagpipeConfigurationType.from(config.getType()));
				LOGGER.log(Level.INFO, "Response: {0}", response);
				configuration = gson.fromJson(response, BagpipeConfiguration.class);
				if (configuration != null &&
						productId.equalsIgnoreCase(configuration.getProductId()) &&
						type.equalsIgnoreCase(configuration.getType())) {
					sendAck(device.getProductId());
					deviceManager.addConfiguration(productId, configuration);
					break;
				}
			} catch (Exception e) {
				// Wrong configuration supplied.
				String configurationType = configuration == null ? "unknown" : configuration.getType(); 
				LOGGER.log(Level.SEVERE, "Unable to find the configuration for productId: {0}, type: {1}", new String[]{productId, configurationType});
				LOGGER.log(Level.SEVERE, "Unable to find the configuration", e);
			}
		}
		LOGGER.log(Level.INFO, "connectionManager.disconnect()");
		
		return configuration;
	}

	@Override
	public void sendBagpipeConfiguration(BagpipeConfiguration configuration)
			throws IllegalArgumentException {
		
		if (configuration != null) {
			String productId = configuration.getProductId();
			try {
				String json = gson.toJson(configuration);
				LOGGER.log(Level.INFO, "Request: {0}", json);
				deviceManager.addConfiguration(productId, configuration);
			} catch (Exception e) {
				LOGGER.log(Level.SEVERE, "Unable to send the configuration for productId: {0}, type: {1}", new String[]{productId, configuration.getType()});
				LOGGER.log(Level.SEVERE, "Unable to send the configuration", e);
			}
		} else {
			throw new IllegalArgumentException("Bagpipe configuration cannot be null");
		}
	}
	
	@Override
	public BagpipeConfiguration getBagpipeConfiguration(
			String productId, String type) {
		
		return deviceManager.getConfiguration(productId, type);
	}
	
	@Override
	public int getVolume(String productId) throws IllegalArgumentException {
		
		int volume = -1;
		
		if (productId != null) {
			BagpipeDevice device = deviceManager.getDevice(productId);
			if (device != null) {
				BagpipeConfiguration configuration = 
						device.getConfigurationByType(
								BagpipeConfigurationType.SELECT.toString());
				SelectionConfiguration selectionConfiguration =
						(SelectionConfiguration) configuration.getData();
				volume = selectionConfiguration.getVolume();
			} else {
				LOGGER.log(Level.SEVERE, "Unable to get the volume for productId: {0}. Device not found", productId);
			}
		} else {
			throw new IllegalArgumentException("ProductId cannot be null");
		}
		
		return volume;
	}
	
	@Override
	public void setVolume(String productId, int volume)
			throws IllegalArgumentException {
	
		if (productId != null) {
			BagpipeDevice device = deviceManager.getDevice(productId);
			if (device != null) {
				BagpipeConfiguration configuration = 
						device.getConfigurationByType(
								BagpipeConfigurationType.SELECT.toString());
				SelectionConfiguration selectionConfiguration =
						(SelectionConfiguration) configuration.getData();
				selectionConfiguration.setVolume(volume);
			} else {
				LOGGER.log(Level.SEVERE, "Unable to set the volume for productId: {0}. Device not found", productId);
			}
		} else {
			throw new IllegalArgumentException("ProductId cannot be null");
		}
	}
	
	@Override
	public int getTuningTone(String productId)
			throws IllegalArgumentException {
		
		int tuningTone = -1;
		
		if (productId != null) {
			BagpipeDevice device = deviceManager.getDevice(productId);
			if (device != null) {
				BagpipeConfiguration configuration = 
						device.getConfigurationByType(
								BagpipeConfigurationType.TUNING.toString());
				TuningConfiguration tuningConfiguration =
						(TuningConfiguration) configuration.getData();
				tuningTone = tuningConfiguration.getTone();
			} else {
				LOGGER.log(Level.SEVERE, "Unable to get the tuning tone for productId: {0}. Device not found", productId);
			}
		} else {
			throw new IllegalArgumentException("ProductId cannot be null");
		}
		
		return tuningTone;
	}
	
	@Override
	public void setTuningTone(String productId, int tuningTone)
			throws IllegalArgumentException {
		
		if (productId != null) {
			BagpipeDevice device = deviceManager.getDevice(productId);
			if (device != null) {
				BagpipeConfiguration configuration = 
						device.getConfigurationByType(
								BagpipeConfigurationType.TUNING.toString());
				TuningConfiguration tuningConfiguration =
						(TuningConfiguration) configuration.getData();
				tuningConfiguration.setTone(tuningTone);
			} else {
				LOGGER.log(Level.SEVERE, "Unable to set the tuning tone for productId: {0}. Device not found", productId);
			}
		} else {
			throw new IllegalArgumentException("ProductId cannot be null");
		}
	}
	
	@Override
	public int getTuningOctave(String productId)
			throws IllegalArgumentException {
		
		int tuningOctave = -1;
		
		if (productId != null) {
			BagpipeDevice device = deviceManager.getDevice(productId);
			if (device != null) {
				BagpipeConfiguration configuration = 
						device.getConfigurationByType(
								BagpipeConfigurationType.TUNING.toString());
				TuningConfiguration tuningConfiguration =
						(TuningConfiguration) configuration.getData();
				tuningOctave = tuningConfiguration.getOctave();
			} else {
				LOGGER.log(Level.SEVERE, "Unable to get the tuning octave for productId: {0}. Device not found", productId);
			}
		} else {
			throw new IllegalArgumentException("ProductId cannot be null");
		}
		
		return tuningOctave;
	}
	
	@Override
	public void setTuningOctave(String productId, int tuningOctave)
			throws IllegalArgumentException {
		
		if (productId != null) {
			BagpipeDevice device = deviceManager.getDevice(productId);
			if (device != null) {
				BagpipeConfiguration configuration = 
						device.getConfigurationByType(
								BagpipeConfigurationType.TUNING.toString());
				TuningConfiguration tuningConfiguration =
						(TuningConfiguration) configuration.getData();
				tuningConfiguration.setOctave(tuningOctave);
			} else {
				LOGGER.log(Level.SEVERE, "Unable to set the tuning octave for productId: {0}. Device not found", productId);
			}
		} else {
			throw new IllegalArgumentException("ProductId cannot be null");
		}
	}
	
	@Override
	public List<Boolean> getFingeringTypesEnabled(String productId)
			throws IllegalArgumentException {
		
		List<Boolean> fingeringTypes = new ArrayList<Boolean>();
		
		if (productId != null) {
			BagpipeDevice device = deviceManager.getDevice(productId);
			if (device != null) {
				BagpipeConfiguration configuration = 
						device.getConfigurationByType(
								BagpipeConfigurationType.SELECT.toString());
				SelectionConfiguration selectionConfiguration =
						(SelectionConfiguration) configuration.getData();
				fingeringTypes = selectionConfiguration.getFingeringTypes();
			} else {
				LOGGER.log(Level.SEVERE, "Unable to get the fingering types for productId: {0}. Device not found", productId);
			}
		} else {
			throw new IllegalArgumentException("ProductId cannot be null");
		}
		
		return fingeringTypes;
	}
	
	@Override
	public void setFingeringTypesEnabled(String productId,
			List<Boolean> fingeringTypes) throws IllegalArgumentException {
		
		if (productId != null) {
			BagpipeDevice device = deviceManager.getDevice(productId);
			if (device != null) {
				BagpipeConfiguration configuration = 
						device.getConfigurationByType(
								BagpipeConfigurationType.SELECT.toString());
				SelectionConfiguration selectionConfiguration =
						(SelectionConfiguration) configuration.getData();
				selectionConfiguration.setFingeringTypes(fingeringTypes);
			} else {
				LOGGER.log(Level.SEVERE, "Unable to set the fingering types for productId: {0}. Device not found", productId);
			}
		} else {
			throw new IllegalArgumentException("ProductId cannot be null");
		}
	}
	
	@Override
	public Boolean isBagEnabled(String productId)
			throws IllegalArgumentException {
		
		Boolean isBagEnabled = null;
		
		if (productId != null) {
			BagpipeDevice device = deviceManager.getDevice(productId);
			if (device != null) {
				BagpipeConfiguration configuration = 
						device.getConfigurationByType(
								BagpipeConfigurationType.SELECT.toString());
				SelectionConfiguration selectionConfiguration =
						(SelectionConfiguration) configuration.getData();
				isBagEnabled = selectionConfiguration.isBagEnabled();
			} else {
				LOGGER.log(Level.SEVERE, "Unable to get the bag for productId: {0}. Device not found", productId);
			}
		} else {
			throw new IllegalArgumentException("ProductId cannot be null");
		}
		
		return isBagEnabled;
	}
	
	@Override
	public void setBagEnabled(String productId, boolean bagEnabled)
			throws IllegalArgumentException {
		
		if (productId != null) {
			BagpipeDevice device = deviceManager.getDevice(productId);
			if (device != null) {
				BagpipeConfiguration configuration = 
						device.getConfigurationByType(
								BagpipeConfigurationType.SELECT.toString());
				SelectionConfiguration selectionConfiguration =
						(SelectionConfiguration) configuration.getData();
				selectionConfiguration.setBagEnabled(bagEnabled);
			} else {
				LOGGER.log(Level.SEVERE, "Unable to set the bag for productId: {0}. Device not found", productId);
			}
		} else {
			throw new IllegalArgumentException("ProductId cannot be null");
		}
	}
	
	@Override
	public List<Boolean> getDronesEnabled(String productId)
			throws IllegalArgumentException {
		
		List<Boolean> drones = new ArrayList<Boolean>();
		
		if (productId != null) {
			BagpipeDevice device = deviceManager.getDevice(productId);
			if (device != null) {
				BagpipeConfiguration configuration = 
						device.getConfigurationByType(
								BagpipeConfigurationType.SELECT.toString());
				SelectionConfiguration selectionConfiguration =
						(SelectionConfiguration) configuration.getData();
				drones = selectionConfiguration.getDronesEnabled();
			} else {
				LOGGER.log(Level.SEVERE, "Unable to get the drones for productId: {0}. Device not found", productId);
			}
		} else {
			throw new IllegalArgumentException("ProductId cannot be null");
		}
		
		return drones;
	}
	
	@Override
	public void setDronesEnabled(String productId, List<Boolean> drones)
			throws IllegalArgumentException {
		
		if (productId != null) {
			BagpipeDevice device = deviceManager.getDevice(productId);
			if (device != null) {
				BagpipeConfiguration configuration = 
						device.getConfigurationByType(
								BagpipeConfigurationType.SELECT.toString());
				SelectionConfiguration selectionConfiguration =
						(SelectionConfiguration) configuration.getData();
				selectionConfiguration.setDronesEnabled(drones);
			} else {
				LOGGER.log(Level.SEVERE, "Unable to set the drones for productId: {0}. Device not found", productId);
			}
		} else {
			throw new IllegalArgumentException("ProductId cannot be null");
		}
	}
	
	@Override
	public int getBagPressure(String productId)
			throws IllegalArgumentException {
		
		int bagPressure = -1;
		
		if (productId != null) {
			BagpipeDevice device = deviceManager.getDevice(productId);
			if (device != null) {
				BagpipeConfiguration configuration = 
						device.getConfigurationByType(
								BagpipeConfigurationType.SENSIT.toString());
				SensitivityConfiguration sensitivityConfiguration =
						(SensitivityConfiguration) configuration.getData();
				bagPressure = sensitivityConfiguration.getBagPressure();
			} else {
				LOGGER.log(Level.SEVERE, "Unable to get the bag pressure for productId: {0}. Device not found", productId);
			}
		} else {
			throw new IllegalArgumentException("ProductId cannot be null");
		}
		
		return bagPressure;
	}
	
	@Override
	public void setBagPressure(String productId, int bagPressure)
			throws IllegalArgumentException {
		
		if (productId != null) {
			BagpipeDevice device = deviceManager.getDevice(productId);
			if (device != null) {
				BagpipeConfiguration configuration = 
						device.getConfigurationByType(
								BagpipeConfigurationType.SENSIT.toString());
				SensitivityConfiguration sensitivityConfiguration =
						(SensitivityConfiguration) configuration.getData();
				sensitivityConfiguration.setBagPressure(bagPressure);
			} else {
				LOGGER.log(Level.SEVERE, "Unable to set the bag pressure for productId: {0}. Device not found", productId);
			}
		} else {
			throw new IllegalArgumentException("ProductId cannot be null");
		}
	}
	
	@Override
	public List<FingeringOffset> getFingerings(String productId)
			throws IllegalArgumentException {
		
		List<FingeringOffset> fingerings = new ArrayList<FingeringOffset>();
		
		if (productId != null) {
			BagpipeDevice device = deviceManager.getDevice(productId);
			if (device != null) {
				BagpipeConfiguration configuration = 
						device.getConfigurationByType(
								BagpipeConfigurationType.FINGER.toString());
				FingeringConfiguration fingeringConfiguration =
						(FingeringConfiguration) configuration.getData();
				fingerings = fingeringConfiguration.getFingerings();
			} else {
				LOGGER.log(Level.SEVERE, "Unable to get the fingerings for productId: {0}. Device not found", productId);
			}
		} else {
			throw new IllegalArgumentException("ProductId cannot be null");
		}
		
		return fingerings;
	}
	
	@Override
	public void setFingerings(String productId, List<FingeringOffset> fingerings) {
		
		if (productId != null) {
			BagpipeDevice device = deviceManager.getDevice(productId);
			if (device != null) {
				BagpipeConfiguration configuration = 
						device.getConfigurationByType(
								BagpipeConfigurationType.FINGER.toString());
				FingeringConfiguration fingeringConfiguration =
						(FingeringConfiguration) configuration.getData();
				fingeringConfiguration.setFingerings(fingerings);
			} else {
				LOGGER.log(Level.SEVERE, "Unable to set the fingerings for productId: {0}. Device not found", productId);
			}
		} else {
			throw new IllegalArgumentException("ProductId cannot be null");
		}
	}
	
	private void sendDiscoveryBeacon() {
		
		LOGGER.log(Level.INFO, "Sending discovery beacon");
	}
	
	private void sendAck(String productId) throws IllegalArgumentException {
		if (productId != null) {
			try {
				BagpipeDevice device = new BagpipeDevice();
				device.setProductId(productId);
				String json = gson.toJson(device);
				LOGGER.log(Level.INFO, "Request: {0}", json);
			} catch (Exception e) {
				LOGGER.log(Level.SEVERE, "Unable to send the ACK for productId: {0}", productId);
				LOGGER.log(Level.SEVERE, "Unable to send the ACK", e);
			}
		} else {
			throw new IllegalArgumentException("ProductId cannot be null");
		}
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
