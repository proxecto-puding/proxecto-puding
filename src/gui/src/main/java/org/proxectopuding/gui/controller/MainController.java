package org.proxectopuding.gui.controller;

import org.proxectopuding.gui.model.services.DeviceManagerService;
import org.proxectopuding.gui.model.services.impl.DeviceManagerServiceImpl;

public class MainController {
	
	private DeviceManagerService deviceManagerService =
			new DeviceManagerServiceImpl();

	public void findChanters() {
		
		deviceManagerService.findBagpipeDevices();
	}
}
