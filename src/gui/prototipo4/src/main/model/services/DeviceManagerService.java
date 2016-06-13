package main.model.services;

import java.util.List;
import java.util.Set;

import main.model.entities.BagpipeConfiguration;
import main.model.entities.BagpipeDevice;
import main.model.entities.FingeringOffset;

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
	public void sendBagpipeConfiguration(BagpipeConfiguration configuration) throws IllegalArgumentException;
	
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
	public List<Boolean> getFingeringTypesEnabled(String productId) throws IllegalArgumentException;
	
	/**
	 * Set the fingering types for a given device.
	 * @param productId Device id.
	 * @param fingeringTypes Device fingering types.
	 * @throws IllegalArgumentException If the provided device id is null.
	 */
	public void setFingeringTypesEnabled(String productId, List<Boolean> fingeringTypes) throws IllegalArgumentException;
	
	/**
	 * Check if the bag is enabled for a given device.
	 * @param productId Device id.
	 * @return A boolean indicating if the bag is enabled.
	 * @throws IllegalArgumentException If the provided device id is null.
	 */
	public Boolean isBagEnabled(String productId) throws IllegalArgumentException;
	
	/**
	 * Set the bag enabled/disabled for a given device.
	 * @param productId Device id.
	 * @param bagEnabled A boolean indicating if the bag is enabled/disabled.
	 * @throws IllegalArgumentException If the provided device id is null.
	 */
	public void setBagEnabled(String productId, boolean bagEnabled) throws IllegalArgumentException;
	
	/**
	 * Get the drones status for a given device.
	 * @param productId Device id.
	 * @return Device drones status.
	 * @throws IllegalArgumentException If the provided device id is null.
	 */
	public List<Boolean> getDronesEnabled(String productId) throws IllegalArgumentException;
	
	/**
	 * Set the drones status for a given device.
	 * @param productId Device id.
	 * @param drones Drones statuses.
	 * @throws IllegalArgumentException If the provided device id is null.
	 */
	public void setDronesEnabled(String productId, List<Boolean> drones) throws IllegalArgumentException;

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
	
	/**
	 * Get the list of available custom fingerings.
	 * @param productId Device id.
	 * @return A list of custom fingerings.
	 * @throws IllegalArgumentException If the provided device id is null.
	 */
	public List<FingeringOffset> getFingerings(String productId) throws IllegalArgumentException;

	/**
	 * Set the list of custom fingerings.
	 * @param productId Device id.
	 * @param fingerings A list of fingerings.
	 */
	public void setFingerings(String productId, List<FingeringOffset> fingerings);
	
}
