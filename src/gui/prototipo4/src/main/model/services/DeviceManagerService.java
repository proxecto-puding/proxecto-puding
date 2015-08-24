package main.model.services;

import java.util.Set;

import main.model.entities.BagpipeConfiguration;
import main.model.entities.BagpipeDevice;

public interface DeviceManagerService {
	
	/**
	 * Find all the available bagpipe devices.
	 * @return A set containing all the devices found.
	 */
	Set<BagpipeDevice> findBagpipeDevices();
	/**
	 * Get a device configuration given a device id and type.
	 * @param productId Device id.
	 * @param type Device type.
	 * @return A device configuration if found. Null otherwise.
	 */
	BagpipeConfiguration getBagpipeConfiguration(String productId, String type);
	/**
	 * Get a device configuration given a preinitialized one. 
	 * @param configuration Device configuration containing at least id and type.
	 * @return A device configuration if found. Null otherwise.
	 */
	BagpipeConfiguration getBagpipeConfiguration(BagpipeConfiguration configuration);
	/**
	 * Set the configuration for a given device.
	 * @param configuration Device configuration containing at least id and type. 
	 */
	void setBagpipeConfiguration(BagpipeConfiguration configuration);
	/**
	 * Find a device configuration by id and type.
	 * @param productId Device id.
	 * @param type Device type.
	 * @return The requested configuration if found. Null otherwise.
	 */
	BagpipeConfiguration findBagpipeConfigurationByIdAndType(String productId, String type);
}
