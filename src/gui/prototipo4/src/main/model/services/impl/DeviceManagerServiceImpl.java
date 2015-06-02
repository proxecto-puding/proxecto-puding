package main.model.services.impl;

import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import main.model.BagpipeConfiguration;
import main.model.BagpipeDevice;
import main.model.DeviceManager;
import main.model.services.DeviceManagerService;
import main.model.services.connection.ConnectionManager;

public class DeviceManagerServiceImpl implements DeviceManagerService {
	
	private static final int MAX_ATTEMPTS = 1024;
	private static final long MAX_DISCOVERING_DELAY = 10000;
	private static final long MAX_READING_DELAY = 1000;
	
	private static Gson gson;
	
	static {
		gson = new GsonBuilder().setPrettyPrinting().create();
	};
	
	@Override
	public Set<BagpipeDevice> findBagpipeDevices() {
		
		ConnectionManager.sendDiscoveryBeacon();
		ConnectionManager.delay(MAX_DISCOVERING_DELAY);
		
		String json = ConnectionManager.readData();
		int attempts = 0;
		while (json != null && !json.isEmpty() && attempts < MAX_ATTEMPTS) {
			try {
				BagpipeDevice device = gson.fromJson(json, BagpipeDevice.class);
				DeviceManager.addDevice(device);
				sendAck(device);
			} catch (Exception e) {
				e.printStackTrace();
			}
			ConnectionManager.delay(MAX_READING_DELAY);
			json = ConnectionManager.readData();
			attempts++;
		}
		
		if (DeviceManager.getDevices().size() == 0) {
			System.err.println("No devices found");
		}
		
		return DeviceManager.getDevices();
	}

	@Override
	public BagpipeDevice getBagpipeDeviceConfig(String productId) {
		
		// TODO Rework all this method.
		// For each config type:
		//  Send productId and config for this type.
		//  Send Ack.
		
		try {
			String json = ConnectionManager.readData();
			BagpipeConfiguration configuration =
					gson.fromJson(json, BagpipeConfiguration.class);
			// TODO Review and reimplement. 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setBagpipeDeviceConfig(BagpipeConfiguration configuration) {
		try {
			String json = gson.toJson(configuration);
			ConnectionManager.writeData(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void sendAck(BagpipeDevice device) {
		try {
			String json = gson.toJson(device);
			ConnectionManager.writeData(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
