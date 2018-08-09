package org.proxectopuding.gui.controller;

import org.proxectopuding.gui.model.entities.BagpipeDevice;
import org.proxectopuding.gui.model.services.DeviceManagerService;
import org.proxectopuding.gui.model.services.I18nService;
import org.proxectopuding.gui.model.services.NotificationService;

import com.google.inject.Inject;

public class SensitivityConfigurationController extends Controller {
	
	private final DeviceManagerService deviceManagerService;
	
	@Inject
	public SensitivityConfigurationController(I18nService i18nService,
			DeviceManagerService deviceManagerService,
			NotificationService notificationService) {
		
		super(i18nService, notificationService);
		
		this.deviceManagerService = deviceManagerService;
	}
	
	public String getBagPressureLabel() {
		return getI18nService().getTranslation("sensitivityConfiguration.bagPressure.label");
	}
	
	public int getBagPressure() {
		
		int bagPressure = -1;
		
		BagpipeDevice selectedDevice = 
				deviceManagerService.getSelectedBagpipeDevice();
		if (selectedDevice != null) {
			String productId = selectedDevice.getProductId();
			bagPressure = deviceManagerService.getBagPressure(productId);
		}
		
		return bagPressure;
	}
	
	public void onBagPressureSelected(int bagPressure) {
		
		BagpipeDevice selectedDevice = 
				deviceManagerService.getSelectedBagpipeDevice();
		if (selectedDevice != null) {
			String productId = selectedDevice.getProductId();
			deviceManagerService.setBagPressure(productId, bagPressure);
		}
	}
}
