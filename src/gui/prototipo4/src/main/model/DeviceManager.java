package main.model;

import java.util.Set;
import java.util.TreeSet;

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
	
	public static void addDevice(BagpipeDevice device) {
		devices.add(device);
	}
	
	public static void removeDevice(BagpipeDevice device) {
		devices.remove(device);
	}

}
