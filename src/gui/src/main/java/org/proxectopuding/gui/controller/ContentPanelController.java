package org.proxectopuding.gui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.proxectopuding.gui.model.entities.BagpipeConfiguration;
import org.proxectopuding.gui.model.entities.BagpipeConfigurationType;
import org.proxectopuding.gui.model.entities.BagpipeDevice;
import org.proxectopuding.gui.model.entities.midiServer.MidiServerConfiguration;
import org.proxectopuding.gui.model.services.ConfigurationApplicationService;
import org.proxectopuding.gui.model.services.DeviceManagerService;
import org.proxectopuding.gui.model.services.I18nService;
import org.proxectopuding.gui.model.services.MidiService;

import com.google.inject.Inject;

public class ContentPanelController {
	
	private final I18nService i18nService;
	private final DeviceManagerService deviceManagerService;
	private final ConfigurationApplicationService confAppService;
	private final MidiService midiService;
	
	@Inject
	public ContentPanelController(I18nService i18nService,
			DeviceManagerService deviceManagerService,
			ConfigurationApplicationService confAppService,
			MidiService midiService) {
		
		this.i18nService = i18nService;
		this.deviceManagerService = deviceManagerService;
		this.confAppService = confAppService;
		this.midiService = midiService;
	}
	
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
