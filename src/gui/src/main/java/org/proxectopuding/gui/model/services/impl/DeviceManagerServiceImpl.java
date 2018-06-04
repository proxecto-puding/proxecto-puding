package org.proxectopuding.gui.model.services.impl;

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
import org.proxectopuding.gui.model.entities.TuningConfiguration;
import org.proxectopuding.gui.model.services.DeviceManagerService;
import org.proxectopuding.gui.model.utils.ConnectionManager;
import org.proxectopuding.gui.model.utils.DeviceManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class DeviceManagerServiceImpl implements DeviceManagerService {
	
	private static final Logger LOGGER = Logger.getLogger(DeviceManagerServiceImpl.class.getName());
	
	private static final int MAX_ATTEMPTS = 30;
	private static final long MAX_DISCOVERING_DELAY = 10000; // 10 sec.
	private static final long MAX_READING_DELAY = 1000; // 1 sec.
	
	private static ConnectionManager connection;
	private static Gson gson;
	
	static {
		connection = ConnectionManager.getInstance();
		gson = new GsonBuilder().setPrettyPrinting().create();
	};
	
	@Override
	public Set<BagpipeDevice> findBagpipeDevices() {
		
		connection.sendDiscoveryBeacon();
		connection.delay(MAX_DISCOVERING_DELAY);
		
		String json = connection.readData();
		int attempts = 0;
		BagpipeDevice device = null;
		while (json != null && !json.isEmpty() && attempts < MAX_ATTEMPTS) {
			try {
				device = gson.fromJson(json, BagpipeDevice.class);
				if (device != null) {
					DeviceManager.addDevice(device);
					sendAck(device.getProductId());
				} else {
					LOGGER.log(Level.SEVERE, "Unable to add a new device to the list of known devices. Json: {0}", json);
				}
			} catch (Exception e) {
				LOGGER.log(Level.SEVERE, "Unable to add a new device to the list of known devices", e);
			}
			connection.delay(MAX_READING_DELAY);
			json = connection.readData();
			attempts++;
		}
		
		if (DeviceManager.getDevices().size() == 0) {
			LOGGER.log(Level.SEVERE, "No devices found");
		}
		
		return DeviceManager.getDevices();
	}
	
	@Override
	public List<String> getBagpipeDeviceIds() {
		
		List<String> ids = new ArrayList<String>();
		
		Set<BagpipeDevice> devices = DeviceManager.getDevices();
		for (BagpipeDevice device : devices) {
			ids.add(device.getProductId());
		}
		
		return ids;
	}
	
	@Override
	public BagpipeDevice getSelectedBagpipeDevice() {
		return DeviceManager.getSelectedDevice();
	}
	
	@Override
	public void setSelectedBagpipeDevice(String productId) {
		DeviceManager.setSelectedDevice(productId);
	}
	
	@Override
	public Set<BagpipeConfiguration> findBagpipeConfigurations(
			String productId) {
		
		BagpipeConfigurationType[] types = BagpipeConfigurationType.values();
		for (BagpipeConfigurationType type : types) {
			findBagpipeConfiguration(productId, type.toString());
		}
		
		return DeviceManager.getConfigurations(productId);
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
		
		BagpipeDevice device = DeviceManager.getDevice(productId);
		if (device == null) {
			LOGGER.log(Level.SEVERE, "Unable to find the configuration for productId: {0}. Device not found", productId);
			return configuration;
		}
		
		BagpipeConfiguration config = new BagpipeConfiguration();
		config.setProductId(productId);
		config.setType(type);
		String request = gson.toJson(config, BagpipeConfiguration.class);
		String response = null; 
		int attempts = 0;
		while (response != null && !response.isEmpty() && attempts < MAX_ATTEMPTS) {
			try {
				attempts++;
				connection.writeData(request);
				connection.delay(MAX_READING_DELAY);
				response = connection.readData();
				configuration = gson.fromJson(response, BagpipeConfiguration.class);
				if (configuration != null &&
						productId.equalsIgnoreCase(configuration.getProductId()) &&
						type.equalsIgnoreCase(configuration.getType())) {
					sendAck(device.getProductId());
					DeviceManager.addConfiguration(productId, configuration);
				} else {
					// Wrong configuration supplied.
					response = null;
					configuration = null;
				}
			} catch (Exception e) {
				// Wrong configuration supplied.
				response = null;
				String configurationType = configuration == null ? "unknown" : configuration.getType(); 
				LOGGER.log(Level.SEVERE, "Unable to find the configuration for productId: {0}, type: {1}", new String[]{productId, configurationType});
				LOGGER.log(Level.SEVERE, "Unable to find the configuration", e);
				configuration = null;
			}
		}
		
		return configuration;
	}

	@Override
	public void sendBagpipeConfiguration(BagpipeConfiguration configuration)
			throws IllegalArgumentException {
		
		if (configuration != null) {
			String productId = configuration.getProductId();
			try {
				String json = gson.toJson(configuration);
				connection.writeData(json);
				DeviceManager.addConfiguration(productId, configuration);
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
		
		return DeviceManager.getConfiguration(productId, type);
	}
	
	@Override
	public int getVolume(String productId) throws IllegalArgumentException {
		
		int volume = -1;
		
		if (productId != null) {
			BagpipeDevice device = DeviceManager.getDevice(productId);
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
			BagpipeDevice device = DeviceManager.getDevice(productId);
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
			BagpipeDevice device = DeviceManager.getDevice(productId);
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
			BagpipeDevice device = DeviceManager.getDevice(productId);
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
			BagpipeDevice device = DeviceManager.getDevice(productId);
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
			BagpipeDevice device = DeviceManager.getDevice(productId);
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
			BagpipeDevice device = DeviceManager.getDevice(productId);
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
			BagpipeDevice device = DeviceManager.getDevice(productId);
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
			BagpipeDevice device = DeviceManager.getDevice(productId);
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
			BagpipeDevice device = DeviceManager.getDevice(productId);
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
			BagpipeDevice device = DeviceManager.getDevice(productId);
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
			BagpipeDevice device = DeviceManager.getDevice(productId);
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
			BagpipeDevice device = DeviceManager.getDevice(productId);
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
			BagpipeDevice device = DeviceManager.getDevice(productId);
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
			BagpipeDevice device = DeviceManager.getDevice(productId);
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
			BagpipeDevice device = DeviceManager.getDevice(productId);
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
	
	/**
	 * Send an ACK message to the target device.
	 * @param device Target device.
	 */
	private void sendAck(String productId) throws IllegalArgumentException {
		if (productId != null) {
			try {
				BagpipeDevice device = new BagpipeDevice();
				device.setProductId(productId);
				String json = gson.toJson(device);
				connection.writeData(json);
			} catch (Exception e) {
				LOGGER.log(Level.SEVERE, "Unable to send the ACK for productId: {0}", productId);
				LOGGER.log(Level.SEVERE, "Unable to send the ACK", e);
			}
		} else {
			throw new IllegalArgumentException("ProductId cannot be null");
		}
	}

}