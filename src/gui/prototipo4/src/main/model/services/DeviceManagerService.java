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
	 * Get the current bagpipe device in use.
	 * @return A bagpipe device.
	 */
	public BagpipeDevice getSelectedBagpipeDevice();
	
	/**
	 * Set the current bagpipe device in use.
	 */
	public void setSelectedBagpipeDevice(String productId);
	
	/**
	 * Find all the device configurations given device id.
	 * @param productId Device id.
	 * @return The requested configurations.
	 */
	public Set<BagpipeConfiguration> findBagpipeConfigurations(String productId);
	
	/**
	 * Find a device configuration given a device id and type.
	 * @param productId Device id.
	 * @param type Device type.
	 * @return A device configuration if found. Null otherwise.
	 */
	public BagpipeConfiguration findBagpipeConfiguration(String productId, String type);
	
	/**
	 * Find a device configuration given a preinitialized one. 
	 * @param configuration Device configuration containing at least id and type.
	 * @return A device configuration if found. Null otherwise.
	 */
	public BagpipeConfiguration findBagpipeConfiguration(BagpipeConfiguration configuration);
	
	/**
	 * Set the configuration for a given device.
	 * @param configuration Device configuration containing at least id and type.
	 * @throws IllegalArgumentException If the provided configuration is null.
	 */
	public void setBagpipeConfiguration(BagpipeConfiguration configuration) throws IllegalArgumentException;
	
	/**
	 * Get a device configuration by id and type.
	 * @param productId Device id.
	 * @param type Device type.
	 * @return The requested configuration if found. Null otherwise.
	 */
	public BagpipeConfiguration getBagpipeConfiguration(String productId, String type);
	
	/**
	 * Get the volume for a given device.
	 * @param productId Device id.
	 * @return A volume value.
	 * @throws IllegalArgumentException If the provided device id is null.
	 */
	public int getVolume(String productId) throws IllegalArgumentException;
	
	/**
	 * Set the volume for a given device.
	 * @param productId Device Id.
	 * @param volume Volume.
	 * @throws IllegalArgumentException If the provided device id is null.
	 */
	public void setVolume(String productId, int volume) throws IllegalArgumentException;
	
	/**
	 * Get the tuning tone for a given device.
	 * @param productId Device id.
	 * @return A tuning tone.
	 * @throws IllegalArgumentException If the provided device id is null.
	 */
	public int getTuningTone(String productId) throws IllegalArgumentException;
	
	/**
	 * Set the tuning tone for a given device.
	 * @param productId Device id.
	 * @param tuningTone Tuning tone.
	 * @throws IllegalArgumentException If the provided device id is null.
	 */
	public void setTuningTone(String productId, int tuningTone) throws IllegalArgumentException;
	
	/**
	 * Get the tuning octave for a given device.
	 * @param productId Device id.
	 * @return A tuning octave.
	 * @throws IllegalArgumentException If the provided device id is null or
	 * the device is not found.
	 */
	public int getTuningOctave(String productId) throws IllegalArgumentException;
	
	/**
	 * Set the tuning octave for a given device.
	 * @param productId Device id.
	 * @param tuningOctave Tuning octave.
	 * @throws IllegalArgumentException If the provided device id is null.
	 */
	public void setTuningOctave(String productId, int tuningOctave) throws IllegalArgumentException;
	
	/**
	 * Get the fingering types for a given device.
	 * @param productId Device id.
	 * @return Device fingering types.
	 * @throws IllegalArgumentException If the provided device id is null.
	 */
	public List<Boolean> getFingeringTypes(String productId) throws IllegalArgumentException;
	
	/**
	 * Set the fingering types for a given device.
	 * @param productId Device id.
	 * @param fingeringTypes Device fingering types.
	 * @throws IllegalArgumentException If the provided device id is null.
	 */
	public void setFingeringTypes(String productId, List<Boolean> fingeringTypes) throws IllegalArgumentException;

	/**
	 * Get the bag pressure limit for a given device.
	 * @param productId Device id.
	 * @return A bag pressure limit.
	 * @throws IllegalArgumentException If the provided device id is null.
	 */
	public int getBagPressure(String productId) throws IllegalArgumentException;
	
	/**
	 * Set the bag pressure limit for a given device.
	 * @param productId Device id.
	 * @param bagPressure Bag pressure limit.
	 * @throws IllegalArgumentException If the provided device id is null.
	 */
	public void setBagPressure(String productId, int bagPressure) throws IllegalArgumentException;
	
}
