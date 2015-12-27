package main.model.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import main.model.entities.BagpipeConfiguration;
import main.model.entities.BagpipeConfigurationType;
import main.model.entities.BagpipeDevice;
import main.model.entities.SelectionConfiguration;
import main.model.entities.SensitivityConfiguration;
import main.model.services.DeviceManagerService;
import main.model.utils.ConnectionManager;
import main.model.utils.DeviceManager;

public class DeviceManagerServiceImpl implements DeviceManagerService {
	
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
					System.err.println(
							"Error while adding a new device to the list of known devices." +
							" Message: " + json);
				}
			} catch (Exception e) {
				System.err.println(
						"Error while adding a new device to the list of known devices." +
						" Message: " + e.getMessage());
				e.printStackTrace();
			}
			connection.delay(MAX_READING_DELAY);
			json = connection.readData();
			attempts++;
		}
		
		if (DeviceManager.getDevices().size() == 0) {
			System.err.println("Error: No devices found!");
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
			System.err.println("Error while getting the configuration for:" +
					" ProductId: " + productId +
					" Message: Device not found.");
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
				System.err.println("Error while getting the configuration for:" +
						" ProductId: " + productId +
						" Type: " + configuration == null ? "unknow" : configuration.getType() +
						" Message: " + e.getMessage());
				e.printStackTrace();
				configuration = null;
			}
		}
		
		return configuration;
	}

	@Override
	public void setBagpipeConfiguration(BagpipeConfiguration configuration)
			throws IllegalArgumentException {
		
		if (configuration != null) {
			String productId = configuration.getProductId();
			try {
				String json = gson.toJson(configuration);
				connection.writeData(json);
				DeviceManager.addConfiguration(productId, configuration);
			} catch (Exception e) {
				System.err.println("Error while setting the configuration for: " +
						" ProductId: " + productId +
						" Type: " + configuration.getType() +
						" Message: " + e.getMessage());
				e.printStackTrace();
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
				System.err.println("Error while getting the volume for:" +
						" ProductId: " + productId +
						" Message: Device not found.");
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
				System.err.println("Error while setting the volume for:" +
						" ProductId: " + productId +
						" Message: Device not found.");
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
				System.err.println("Error while getting the bag pressure for:" +
						" ProductId: " + productId +
						" Message: Device not found.");
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
				System.err.println("Error while setting the bag pressure for:" +
						" ProductId: " + productId +
						" Message: Device not found.");
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
				System.err.println("Error while sending ACK for: " +
						" ProductId: " + productId +
						" Message: " + e.getMessage());
				e.printStackTrace();
			}
		} else {
			throw new IllegalArgumentException("ProductId cannot be null");
		}
	}

}
