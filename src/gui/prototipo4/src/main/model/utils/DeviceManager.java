package main.model.utils;

import java.util.Set;
import java.util.TreeSet;

import main.model.entities.BagpipeConfiguration;
import main.model.entities.BagpipeDevice;

public class DeviceManager {
	
	private static Set<BagpipeDevice> devices;
	
	static {
		devices = new TreeSet<BagpipeDevice>();
	};

	/**
	 * Get the list of registered devices.
	 * @return The list of registered devices.
	 */
	public static Set<BagpipeDevice> getDevices() {
		return devices;
	}

	/**
	 * Set the list of registered devices.
	 * @param devices The list of devices to register.
	 */
	public static void setDevices(Set<BagpipeDevice> devices) {
		DeviceManager.devices = devices;
	}
	
	/**
	 * Find a registered device by id.
	 * @param productId The device id to search for.
	 * @return A registered device if found. Null otherwise.
	 */
	public static BagpipeDevice findDeviceById(String productId) {
		
		BagpipeDevice device = null;
		
		for (BagpipeDevice d : devices) {
			if (d.getProductId().equalsIgnoreCase(productId)) {
				device = d;
				break;
			}
		}
		
		return device;
	}
	
	/**
	 * Add a default configured device to the list of registered devices.
	 * @param productId The device id to register.
	 */
	public static void addDevice (String productId) {
		BagpipeDevice device = new BagpipeDevice();
		device.setProductId(productId);
		addDevice(device);
	}
	
	/**
	 * Add a provided device to the list of registered devices.
	 * @param device Device to register.
	 */
	public static void addDevice(BagpipeDevice device) {
		devices.add(device);
	}
	
	/**
	 * Remove a device from the list of registered devices.
	 * @param productId Id of the device to remove from the list.
	 */
	public static void removeDevice(String productId) {
		BagpipeDevice device = new BagpipeDevice();
		device.setProductId(productId);
		removeDevice(device);
	}
	
	/**
	 * Remove a device from the list of registered devices.
	 * @param device Device to remove from the list.
	 */
	public static void removeDevice(BagpipeDevice device) {
		devices.remove(device);
	}
	
	/**
	 * Find a device configuration by id and type.
	 * @param productId Device id.
	 * @param type Device type.
	 * @return The requested configuration if found. Null otherwise.
	 */
	public static BagpipeConfiguration findConfigurationByIdAndType(
			String productId, String type) {
		
		BagpipeConfiguration configuration = null;
		
		BagpipeDevice device = findDeviceById(productId);
		if (device != null) {
			configuration = device.findConfigurationByType(type);
		}
		
		return configuration;
	}

}
