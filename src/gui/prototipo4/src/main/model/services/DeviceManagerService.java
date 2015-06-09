package main.model.services;

import java.util.Set;

import main.model.entities.BagpipeConfiguration;
import main.model.entities.BagpipeDevice;

public interface DeviceManagerService {
	
	Set<BagpipeDevice> findBagpipeDevices();
	BagpipeConfiguration getBagpipeConfiguration(String productId, String type);
	BagpipeConfiguration getBagpipeConfiguration(BagpipeConfiguration configuration);
	void setBagpipeConfiguration(BagpipeConfiguration configuration);
	BagpipeConfiguration findBagpipeConfigurationByIdAndType(String productId, String type);
}
