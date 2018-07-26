package org.proxectopuding.gui.controller;

import org.proxectopuding.gui.model.services.DeviceManagerService;

import com.google.inject.Inject;

public class MainController {
	
	private final DeviceManagerService deviceManagerService;
	
	@Inject
	public MainController(DeviceManagerService deviceManagerService) {
	
		this.deviceManagerService = deviceManagerService;
	}

	public void findChanters() {
		
		deviceManagerService.findBagpipeDevices();
	}
}
