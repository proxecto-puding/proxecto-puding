package org.proxectopuding.gui.controller;

import org.proxectopuding.gui.model.entities.BagpipeConfiguration;
import org.proxectopuding.gui.model.entities.BagpipeConfigurationAction;
import org.proxectopuding.gui.model.entities.BagpipeConfigurationType;
import org.proxectopuding.gui.model.entities.BagpipeDevice;
import org.proxectopuding.gui.model.entities.midiServer.MidiServerConfiguration;
import org.proxectopuding.gui.model.services.ConfigurationApplicationService;
import org.proxectopuding.gui.model.services.DeviceManagerService;
import org.proxectopuding.gui.model.services.I18nService;
import org.proxectopuding.gui.model.services.MidiService;
import org.proxectopuding.gui.model.services.NotificationService;
import org.proxectopuding.gui.model.utils.Notification;

import com.google.inject.Inject;

public class ContentPanelController extends Controller {
	
	private final DeviceManagerService deviceManagerService;
	private final ConfigurationApplicationService confAppService;
	private final MidiService midiService;
	
	@Inject
	public ContentPanelController(I18nService i18nService,
			DeviceManagerService deviceManagerService,
			ConfigurationApplicationService confAppService,
			MidiService midiService, NotificationService notificationService) {

		super(i18nService, notificationService);
		
		this.deviceManagerService = deviceManagerService;
		this.confAppService = confAppService;
		this.midiService = midiService;
		
		startMidiServer();
	}
	
	public String getApplyButtonLabel() {
		return getI18nService().getTranslation("contentPanel.apply.button");
	}
	
	public void onApply() {
		
		// Set device configuration
		BagpipeDevice device = deviceManagerService.getSelectedBagpipeDevice();
		
		if (device != null) {
			
			BagpipeConfigurationType type = confAppService
					.getSelectedBagpipeConfigurationType();
			String productId = device.getProductId();
			BagpipeConfiguration bagpipeConfiguration =
					deviceManagerService.getBagpipeConfiguration(
							productId, type.toString());
			bagpipeConfiguration.setAction(
					BagpipeConfigurationAction.NEW.toString());
			deviceManagerService.sendBagpipeConfiguration(bagpipeConfiguration);
		}
		
		// Set MIDI server configuration
		MidiServerConfiguration midiServerConfiguration =
				confAppService.getMidiServerConfiguration();
		midiService.setConfiguration(midiServerConfiguration);
		midiService.restart();
	}
	
	public String getUndoButtonLabel() {
		return getI18nService().getTranslation("contentPanel.undo.button");
	}
	
	public void onUndo() {
		
		// Set device configuration
		BagpipeDevice device = deviceManagerService.getSelectedBagpipeDevice();
		
		if (device != null) {
			
			// Ask device for previous configuration
			BagpipeConfigurationType type = confAppService
					.getSelectedBagpipeConfigurationType();
			String productId = device.getProductId();
			BagpipeConfiguration bagpipeConfiguration =
					deviceManagerService.getBagpipeConfiguration(
							productId, type.toString());
			bagpipeConfiguration.setAction(
					BagpipeConfigurationAction.RESET.toString());
			deviceManagerService.sendBagpipeConfiguration(bagpipeConfiguration);
			
			// Previous configuration from device
			deviceManagerService.findBagpipeConfiguration(productId, type.toString());
			getNotificationService().sendNotification(this,
					Notification.CHANTER_SELECTED, productId);
		}
		
		// Set MIDI server configuration
		MidiServerConfiguration midiServerConfiguration =
				confAppService.getMidiServerConfiguration();
		midiService.setConfiguration(midiServerConfiguration);
		midiService.restart();
	}
	
	public String getDefaultButtonLabel() {
		return getI18nService().getTranslation("contentPanel.default.button");
	}
	
	public void onDefault() {
		
		// Set device configuration
		BagpipeDevice device = deviceManagerService.getSelectedBagpipeDevice();
		
		if (device != null) {
			
			// Ask device for default configuration
			BagpipeConfigurationType type = confAppService
					.getSelectedBagpipeConfigurationType();
			String productId = device.getProductId();
			BagpipeConfiguration bagpipeConfiguration =
					deviceManagerService.getBagpipeConfiguration(
							productId, type.toString());
			bagpipeConfiguration.setAction(
					BagpipeConfigurationAction.DEFAULT.toString());
			deviceManagerService.sendBagpipeConfiguration(bagpipeConfiguration);
			
			// Default configuration from device
			deviceManagerService.findBagpipeConfiguration(productId, type.toString());
			getNotificationService().sendNotification(this,
					Notification.CHANTER_SELECTED, productId);
		}
		
		// Set MIDI server configuration
		MidiServerConfiguration midiServerConfiguration =
				confAppService.getMidiServerConfiguration();
		midiService.setConfiguration(midiServerConfiguration);
		midiService.restart();
	}
	
	// Private

	private void startMidiServer() {
		
		midiService.start();
	}
}
