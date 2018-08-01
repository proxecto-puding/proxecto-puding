package org.proxectopuding.gui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Set;

import javax.swing.JComboBox;

import org.proxectopuding.gui.model.entities.BagpipeDevice;
import org.proxectopuding.gui.model.services.ConfigurationApplicationService;
import org.proxectopuding.gui.model.services.DeviceManagerService;
import org.proxectopuding.gui.model.services.I18nService;
import org.proxectopuding.gui.model.services.NotificationService;
import org.proxectopuding.gui.model.utils.Notification;

import com.google.inject.Inject;

public class StartConfigurationController {
	
	private final I18nService i18nService;
	private final DeviceManagerService deviceManagerService;
	private final ConfigurationApplicationService confAppService;
	private final NotificationService notificationService;
	
	@Inject
	public StartConfigurationController(I18nService i18nService,
			DeviceManagerService deviceManagerService,
			ConfigurationApplicationService confAppService,
			NotificationService notificationService) {

		this.i18nService = i18nService;
		this.deviceManagerService = deviceManagerService;
		this.confAppService = confAppService;
		this.notificationService = notificationService;
	}

	public String getTranslationForChanterSelectionLabel() {
		return i18nService.getTranslation("startConfiguration.chanterSelection.label");
	}
	
	public String getTranslationForSearchButtonText() {
		return i18nService.getTranslation("startConfiguration.search.label");
	}
	
	public String getTranslationForReadingToneLabel() {
		return i18nService.getTranslation("startConfiguration.readingTone.label");
	}
	
	public void findChanters() {
		
		Set<BagpipeDevice> devices =
				deviceManagerService.findBagpipeDevices();
		if (devices.size() > 0) {
			notificationService.sendNotification(this,
					Notification.CHANTER_FOUND, devices);
		}
	}
	
	public String[] getChanters() {
		
		String[] chanters = {};
		
		List<String> list = deviceManagerService.getBagpipeDeviceIds();
		chanters = list.toArray(new String[list.size()]);
		
		return chanters;
	}
	
	public ActionListener getActionListenerForChanterSelectionComboBox() {
		
		ActionListener actionListener = new ActionListener() {
			
			public void actionPerformed(ActionEvent event) {
				
				@SuppressWarnings("unchecked")
				JComboBox<String> comboBoxChanterSelection =
						(JComboBox<String>) event.getSource();
				String productId = 
						(String) comboBoxChanterSelection.getSelectedItem();
				deviceManagerService.setSelectedBagpipeDevice(productId);
				deviceManagerService.findBagpipeConfigurations(productId);
				notificationService.sendNotification(comboBoxChanterSelection,
						Notification.CHANTER_SELECTED, productId);
			}
		};
		
		return actionListener;
	}
	
	public ActionListener getActionListenerForSearchButton() {
		
		ActionListener actionListener = new ActionListener() {
			
			public void actionPerformed(ActionEvent event) {
				
				Set<BagpipeDevice> devices =
						deviceManagerService.findBagpipeDevices();
				if (devices.size() > 0) {
					notificationService.sendNotification(event.getSource(),
							Notification.CHANTER_FOUND, devices);
				}
			}
		};
		
		return actionListener;
	}
	
	public String[] getReadingTones() {
		
		String[] readingTones = {};
		
		List<String> list = confAppService.getReadingTones();
		readingTones = list.toArray(new String[list.size()]);
		
		return readingTones;
	}
	
	public ActionListener getActionListenerForReadingToneComboBox() {
		
		ActionListener actionListener = new ActionListener() {
			
			public void actionPerformed(ActionEvent event) {
				
				@SuppressWarnings("unchecked")
				JComboBox<String> comboBoxReadingTone =
						(JComboBox<String>) event.getSource();
				String readingTone = 
						(String) comboBoxReadingTone.getSelectedItem();
				confAppService.setReadingTone(readingTone);
				notificationService.sendNotification(comboBoxReadingTone,
						Notification.READING_TONE_SELECTED, readingTone);
			}
		};
		
		return actionListener;
	}
}
