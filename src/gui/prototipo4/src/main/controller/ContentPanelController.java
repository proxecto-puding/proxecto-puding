package main.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import main.model.entities.BagpipeConfiguration;
import main.model.entities.BagpipeConfigurationType;
import main.model.entities.BagpipeDevice;
import main.model.entities.MidiServerConfiguration;
import main.model.services.ConfigurationApplicationService;
import main.model.services.DeviceManagerService;
import main.model.services.I18nService;
import main.model.services.MidiService;
import main.model.services.impl.ConfigurationApplicationServiceImpl;
import main.model.services.impl.DeviceManagerServiceImpl;
import main.model.services.impl.I18nServiceImpl;
import main.model.services.impl.MidiServiceImpl;

public class ContentPanelController {
	
	private I18nService i18nService = new I18nServiceImpl();
	private DeviceManagerService deviceManagerService =
			new DeviceManagerServiceImpl();
	private ConfigurationApplicationService confAppService =
			new ConfigurationApplicationServiceImpl();
	private MidiService midiService = new MidiServiceImpl();
	
	public String getTranslationForApplyButtonText() {
		return i18nService.getTranslation("contentPanel.apply.button");
	}
	
	public ActionListener getActionListenerForApplyButton() {
		
		ActionListener actionListener = new ActionListener() {
			
			public void actionPerformed(ActionEvent event) {
				
				// Set device configuration.
				BagpipeDevice device = deviceManagerService.
						getSelectedBagpipeDevice();
				
				if (device != null) {
					
					BagpipeConfigurationType type = confAppService.
							getSelectedBagpipeConfigurationType();
					String productId = device.getProductId();
					BagpipeConfiguration bagpipeConfiguration =
							deviceManagerService.getBagpipeConfiguration(
									productId, type.toString());
					deviceManagerService.sendBagpipeConfiguration(
							bagpipeConfiguration);
				}
				
				// Set MIDI server configuration.
				MidiServerConfiguration midiServerConfiguration =
						confAppService.getMidiServerConfiguration();
				midiService.setConfiguration(midiServerConfiguration);
				midiService.restart();
			}
		};
		
		return actionListener;
	}
	
	public String getTranslationForUndoButtonText() {
		return i18nService.getTranslation("contentPanel.undo.button");
	}
	
	public ActionListener getActionListenerForUndoButton() {
		
		ActionListener actionListener = new ActionListener() {
			
			public void actionPerformed(ActionEvent event) {
				
				// TODO Implement.
			}
		};
		
		return actionListener;
	}
	
	public String getTranslationForDefaultButtonText() {
		return i18nService.getTranslation("contentPanel.default.button");
	}
	
	public ActionListener getActionListenerForDefaultButton() {
		
		ActionListener actionListener = new ActionListener() {
			
			public void actionPerformed(ActionEvent event) {
				
				// TODO Implement.
			}
		};
		
		return actionListener;
	}

}
