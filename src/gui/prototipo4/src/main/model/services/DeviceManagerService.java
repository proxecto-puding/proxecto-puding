package main.model.services;

import java.util.Set;

import main.model.BagpipeConfiguration;
import main.model.BagpipeDevice;

public interface DeviceManagerService {
	
	Set<BagpipeDevice> findBagpipeDevices();
	BagpipeDevice getBagpipeDeviceConfig(String productId);
	void setBagpipeDeviceConfig(BagpipeConfiguration configuration);

}
