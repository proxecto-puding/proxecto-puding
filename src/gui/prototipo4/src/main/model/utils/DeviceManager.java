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

	public static Set<BagpipeDevice> getDevices() {
		return devices;
	}

	public static void setDevices(Set<BagpipeDevice> devices) {
		DeviceManager.devices = devices;
	}
	
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
	
	public static void addDevice (String productId) {
		BagpipeDevice device = new BagpipeDevice();
		device.setProductId(productId);
		addDevice(device);
	}
	
	public static void addDevice(BagpipeDevice device) {
		devices.add(device);
	}
	
	public static void removeDevice(String productId) {
		BagpipeDevice device = new BagpipeDevice();
		device.setProductId(productId);
		removeDevice(device);
	}
	
	public static void removeDevice(BagpipeDevice device) {
		devices.remove(device);
	}
	
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
