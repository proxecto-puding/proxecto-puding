package main.model.services;

import java.util.List;
import java.util.Set;

import main.model.entities.BagpipeConfiguration;
import main.model.entities.BagpipeDevice;

public interface DeviceManagerService {
	
	/**
	 * Find all the available bagpipe devices.
	 * @return A set containing all the devices found.
	 */
	public Set<BagpipeDevice> findBagpipeDevices();
	
	/**
	 * Get the ids of all the registered bagpipe devices.
	 * @return A list of bagpipe device ids.
	 */
	public List<String> getBagpipeDeviceIds();
	
	/**
	 * Get all the device configurations given device id.
	 * @param productId Device id.
	 * @return The requested configurations.
	 */
	public Set<BagpipeConfiguration> getBagpipeConfigurations(String productId);
	
	/**
	 * Get a device configuration given a device id and type.
	 * @param productId Device id.
	 * @param type Device type.
	 * @return A device configuration if found. Null otherwise.
	 */
	public BagpipeConfiguration getBagpipeConfiguration(String productId, String type);
	
	/**
	 * Get a device configuration given a preinitialized one. 
	 * @param configuration Device configuration containing at least id and type.
	 * @return A device configuration if found. Null otherwise.
	 */
	public BagpipeConfiguration getBagpipeConfiguration(BagpipeConfiguration configuration);
	
	/**
	 * Set the configuration for a given device.
	 * @param configuration Device configuration containing at least id and type. 
	 */
	public void setBagpipeConfiguration(BagpipeConfiguration configuration);
	
	/**
	 * Find a device configuration by id and type.
	 * @param productId Device id.
	 * @param type Device type.
	 * @return The requested configuration if found. Null otherwise.
	 */
	public BagpipeConfiguration findBagpipeConfiguration(String productId, String type);
}
