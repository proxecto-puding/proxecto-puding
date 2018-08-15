package org.proxectopuding.gui.model.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.proxectopuding.gui.model.entities.BagpipeConfiguration;
import org.proxectopuding.gui.model.entities.BagpipeConfigurationAction;
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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;

public class DeviceManagerServiceImpl implements DeviceManagerService {
	
	private static final Logger LOGGER = Logger.getLogger(DeviceManagerServiceImpl.class.getName());
	
	private static final String DISCOVERY_BEACON = "DBEACON";
	private static final int MAX_ATTEMPTS = 10;
	
	private ConnectionManager connectionManager;
	private DeviceManager deviceManager;
	private Gson gson;
	
	@Inject
	public DeviceManagerServiceImpl(ConnectionManager connectionManager,
			DeviceManager deviceManager) {
		try {
			this.connectionManager = connectionManager;
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
				response = connectionManager.readData(false);
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
		connectionManager.disconnect();
		
		return deviceManager.getDevices();
	}
	
	@Override
	public List<String> getBagpipeDeviceIds() {
		
		LOGGER.log(Level.INFO, "Getting bagpipe device ids");
		List<String> ids = new ArrayList<String>();
		
		Set<BagpipeDevice> devices = deviceManager.getDevices();
		for (BagpipeDevice device : devices) {
			ids.add(device.getProductId());
		}
		
		return ids;
	}
	
	@Override
	public BagpipeDevice getSelectedBagpipeDevice() {
		LOGGER.log(Level.INFO, "Getting selected bagpipe device");
		return deviceManager.getSelectedDevice();
	}
	
	@Override
	public void setSelectedBagpipeDevice(String productId) {
		LOGGER.log(Level.INFO, "Setting selected bagpipe device");
		deviceManager.setSelectedDevice(productId);
	}
	
	@Override
	public Set<BagpipeConfiguration> findBagpipeConfigurations(
			String productId) {
		
		LOGGER.log(Level.INFO, "Finding bagpipe configurations for: {0}", productId);
		BagpipeConfigurationType[] types = BagpipeConfigurationType.values();
		for (BagpipeConfigurationType type : types) {
			findBagpipeConfiguration(productId, type.toString());
		}
		
		return deviceManager.getConfigurations(productId);
	}

	@Override
	public BagpipeConfiguration findBagpipeConfiguration(
			BagpipeConfiguration configuration) {
		
		LOGGER.log(Level.INFO, "Finding bagpipe configurations for: {0}", configuration);
		String productId = configuration.getProductId();
		String type = configuration.getType();
		
		return findBagpipeConfiguration(productId, type);
	}
	
	@Override
	public BagpipeConfiguration findBagpipeConfiguration(
			String productId, String type) {
		
		LOGGER.log(Level.INFO, "Finding bagpipe configuration for: {0} {1}", new String[]{productId, type});
		BagpipeConfiguration configuration = null;
		
		BagpipeDevice device = deviceManager.getDevice(productId);
		if (device == null) {
			LOGGER.log(Level.SEVERE, "Unable to find the configuration for productId: {0}. Device not found", productId);
			return configuration;
		}
		
		BagpipeConfiguration config = new BagpipeConfiguration();
		config.setProductId(productId);
		config.setAction(BagpipeConfigurationAction.CURRENT.toString());
		config.setType(type);
		String request = gson.toJson(config, BagpipeConfiguration.class);
		String response = null;
		for (int i = 0; i < MAX_ATTEMPTS; i++) {
			LOGGER.log(Level.INFO, "Attempts: {0}", i + 1);
			try {
				connectionManager.writeData(request, false);
				response = connectionManager.readData(false);
				configuration = parseConfiguration(response, type);
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
		connectionManager.disconnect();
		
		return configuration;
	}

	@Override
	public void sendBagpipeConfiguration(BagpipeConfiguration configuration)
			throws IllegalArgumentException {
		
		LOGGER.log(Level.INFO, "Sending bagpipe configuration: {0}", configuration);
		if (configuration != null) {
			String productId = configuration.getProductId();
			try {
				String json = gson.toJson(configuration);
				connectionManager.writeData(json);
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
		
		LOGGER.log(Level.INFO, "Getting bagpipe configuration for: {0} {1}", new String[]{productId, type});
		return deviceManager.getConfiguration(productId, type);
	}
	
	@Override
	public int getVolume(String productId) throws IllegalArgumentException {
		
		LOGGER.log(Level.INFO, "Getting volume for: {0}", productId);
		int volume = -1;
		
		if (productId != null) {
			BagpipeDevice device = deviceManager.getDevice(productId);
			if (device != null) {
				BagpipeConfiguration configuration = 
						device.getConfigurationByType(
								BagpipeConfigurationType.SELECT.toString());
				SelectionConfiguration selectionConfiguration =
						(SelectionConfiguration) configuration;
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
	
		LOGGER.log(Level.INFO, "Setting volume for: {0}", productId);
		if (productId != null) {
			BagpipeDevice device = deviceManager.getDevice(productId);
			if (device != null) {
				BagpipeConfiguration configuration = 
						device.getConfigurationByType(
								BagpipeConfigurationType.SELECT.toString());
				SelectionConfiguration selectionConfiguration =
						(SelectionConfiguration) configuration;
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
		
		LOGGER.log(Level.INFO, "Getting tuning tone for: {0}", productId);
		int tuningTone = -1;
		
		if (productId != null) {
			BagpipeDevice device = deviceManager.getDevice(productId);
			if (device != null) {
				BagpipeConfiguration configuration = 
						device.getConfigurationByType(
								BagpipeConfigurationType.TUNING.toString());
				TuningConfiguration tuningConfiguration =
						(TuningConfiguration) configuration;
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
		
		LOGGER.log(Level.INFO, "Setting tuning tone for: {0}", productId);
		if (productId != null) {
			BagpipeDevice device = deviceManager.getDevice(productId);
			if (device != null) {
				BagpipeConfiguration configuration = 
						device.getConfigurationByType(
								BagpipeConfigurationType.TUNING.toString());
				TuningConfiguration tuningConfiguration =
						(TuningConfiguration) configuration;
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
		
		LOGGER.log(Level.INFO, "Getting tuning octave for: {0}", productId);
		int tuningOctave = -1;
		
		if (productId != null) {
			BagpipeDevice device = deviceManager.getDevice(productId);
			if (device != null) {
				BagpipeConfiguration configuration = 
						device.getConfigurationByType(
								BagpipeConfigurationType.TUNING.toString());
				TuningConfiguration tuningConfiguration =
						(TuningConfiguration) configuration;
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
		
		LOGGER.log(Level.INFO, "Setting tuning octave for: {0}", productId);
		if (productId != null) {
			BagpipeDevice device = deviceManager.getDevice(productId);
			if (device != null) {
				BagpipeConfiguration configuration = 
						device.getConfigurationByType(
								BagpipeConfigurationType.TUNING.toString());
				TuningConfiguration tuningConfiguration =
						(TuningConfiguration) configuration;
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
		
		LOGGER.log(Level.INFO, "Getting fingering types enabled for: {0}", productId);
		List<Boolean> fingeringTypes = new ArrayList<Boolean>();
		
		if (productId != null) {
			BagpipeDevice device = deviceManager.getDevice(productId);
			if (device != null) {
				BagpipeConfiguration configuration = 
						device.getConfigurationByType(
								BagpipeConfigurationType.SELECT.toString());
				SelectionConfiguration selectionConfiguration =
						(SelectionConfiguration) configuration;
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
		
		LOGGER.log(Level.INFO, "Setting fingering types enabled for: {0}", productId);
		if (productId != null) {
			BagpipeDevice device = deviceManager.getDevice(productId);
			if (device != null) {
				BagpipeConfiguration configuration = 
						device.getConfigurationByType(
								BagpipeConfigurationType.SELECT.toString());
				SelectionConfiguration selectionConfiguration =
						(SelectionConfiguration) configuration;
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
		
		LOGGER.log(Level.INFO, "Is bag enabled for: {0}", productId);
		Boolean isBagEnabled = null;
		
		if (productId != null) {
			BagpipeDevice device = deviceManager.getDevice(productId);
			if (device != null) {
				BagpipeConfiguration configuration = 
						device.getConfigurationByType(
								BagpipeConfigurationType.SELECT.toString());
				SelectionConfiguration selectionConfiguration =
						(SelectionConfiguration) configuration;
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
		
		LOGGER.log(Level.INFO, "Setting bag enabled for: {0} {1}", new Object[]{productId, bagEnabled});
		if (productId != null) {
			BagpipeDevice device = deviceManager.getDevice(productId);
			if (device != null) {
				BagpipeConfiguration configuration = 
						device.getConfigurationByType(
								BagpipeConfigurationType.SELECT.toString());
				SelectionConfiguration selectionConfiguration =
						(SelectionConfiguration) configuration;
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
		
		LOGGER.log(Level.INFO, "Getting drones enabled for: {0}", productId);
		List<Boolean> drones = new ArrayList<Boolean>();
		
		if (productId != null) {
			BagpipeDevice device = deviceManager.getDevice(productId);
			if (device != null) {
				BagpipeConfiguration configuration = 
						device.getConfigurationByType(
								BagpipeConfigurationType.SELECT.toString());
				SelectionConfiguration selectionConfiguration =
						(SelectionConfiguration) configuration;
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
		
		LOGGER.log(Level.INFO, "Setting drones enabled for: {0}", new Object[]{productId, drones});
		if (productId != null) {
			BagpipeDevice device = deviceManager.getDevice(productId);
			if (device != null) {
				BagpipeConfiguration configuration = 
						device.getConfigurationByType(
								BagpipeConfigurationType.SELECT.toString());
				SelectionConfiguration selectionConfiguration =
						(SelectionConfiguration) configuration;
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
		
		LOGGER.log(Level.INFO, "Getting bag pressure for: {0}", productId);
		int bagPressure = -1;
		
		if (productId != null) {
			BagpipeDevice device = deviceManager.getDevice(productId);
			if (device != null) {
				BagpipeConfiguration configuration = 
						device.getConfigurationByType(
								BagpipeConfigurationType.SENSIT.toString());
				SensitivityConfiguration sensitivityConfiguration =
						(SensitivityConfiguration) configuration;
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
		
		LOGGER.log(Level.INFO, "Setting bag pressure for: {0}", productId);
		if (productId != null) {
			BagpipeDevice device = deviceManager.getDevice(productId);
			if (device != null) {
				BagpipeConfiguration configuration = 
						device.getConfigurationByType(
								BagpipeConfigurationType.SENSIT.toString());
				SensitivityConfiguration sensitivityConfiguration =
						(SensitivityConfiguration) configuration;
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
		
		LOGGER.log(Level.INFO, "Getting fingerings for: {0}", productId);
		List<FingeringOffset> fingerings = new ArrayList<FingeringOffset>();
		
		if (productId != null) {
			BagpipeDevice device = deviceManager.getDevice(productId);
			if (device != null) {
				BagpipeConfiguration configuration = 
						device.getConfigurationByType(
								BagpipeConfigurationType.FINGER.toString());
				FingeringConfiguration fingeringConfiguration =
						(FingeringConfiguration) configuration;
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
		
		LOGGER.log(Level.INFO, "Setting fingerings for: {0}", productId);
		if (productId != null) {
			BagpipeDevice device = deviceManager.getDevice(productId);
			if (device != null) {
				BagpipeConfiguration configuration = 
						device.getConfigurationByType(
								BagpipeConfigurationType.FINGER.toString());
				FingeringConfiguration fingeringConfiguration =
						(FingeringConfiguration) configuration;
				fingeringConfiguration.setFingerings(fingerings);
			} else {
				LOGGER.log(Level.SEVERE, "Unable to set the fingerings for productId: {0}. Device not found", productId);
			}
		} else {
			throw new IllegalArgumentException("ProductId cannot be null");
		}
	}
	
	// Private
	
	/**
	 * Send a discovery beacon for finding serial devices.
	 */
	private void sendDiscoveryBeacon() {
		
		LOGGER.log(Level.INFO, "Sending discovery beacon");
		connectionManager.writeData(DISCOVERY_BEACON, false);
	}
	
	/**
	 * Send an ACK message to the target device.
	 * @param device Target device.
	 */
	private void sendAck(String productId) throws IllegalArgumentException {
		
		LOGGER.log(Level.INFO, "Sending ack to: {0}", productId);
		if (productId != null) {
			try {
				BagpipeDevice device = new BagpipeDevice();
				device.setProductId(productId);
				String json = gson.toJson(device);
				connectionManager.writeData(json, false);
			} catch (Exception e) {
				LOGGER.log(Level.SEVERE, "Unable to send the ACK for productId: {0}", productId);
				LOGGER.log(Level.SEVERE, "Unable to send the ACK", e);
			}
		} else {
			throw new IllegalArgumentException("ProductId cannot be null");
		}
	}
	
	private BagpipeConfiguration parseConfiguration(String response,
			String type) {
		
		BagpipeConfiguration configuration = null;
		
		BagpipeConfigurationType configurationType =
				BagpipeConfigurationType.from(type);
		switch (configurationType) {
			case START:
				configuration = gson.fromJson(response, StartConfiguration.class);
				break;
			case SELECT:
				configuration = gson.fromJson(response, SelectionConfiguration.class);
				break;
			case TUNING:
				configuration = gson.fromJson(response, TuningConfiguration.class);
				break;
			case SENSIT:
				configuration = gson.fromJson(response, SensitivityConfiguration.class);
				break;
			case FINGER:
				configuration = gson.fromJson(response, FingeringConfiguration.class);
				break;
			}
		
		return configuration;
	}

}
