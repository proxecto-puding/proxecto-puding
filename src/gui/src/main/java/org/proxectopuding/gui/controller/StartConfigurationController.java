package org.proxectopuding.gui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JComboBox;

import org.proxectopuding.gui.model.services.ConfigurationApplicationService;
import org.proxectopuding.gui.model.services.DeviceManagerService;
import org.proxectopuding.gui.model.services.I18nService;
import org.proxectopuding.gui.model.services.NotificationService;
import org.proxectopuding.gui.model.services.impl.ConfigurationApplicationServiceImpl;
import org.proxectopuding.gui.model.services.impl.DeviceManagerServiceImpl;
import org.proxectopuding.gui.model.services.impl.I18nServiceImpl;
import org.proxectopuding.gui.model.services.impl.NotificationServiceImpl;
import org.proxectopuding.gui.model.utils.Notification;

public class StartConfigurationController {
	
	private I18nService i18nService = new I18nServiceImpl();
	private DeviceManagerService deviceManagerService =
			new DeviceManagerServiceImpl();
	private ConfigurationApplicationService confAppService =
			new ConfigurationApplicationServiceImpl();
	private NotificationService notificationService =
			new NotificationServiceImpl();

	public String getTranslationForChanterSelectionLabel() {
		return i18nService.getTranslation("startConfiguration.chanterSelection.label");
	}
	
	public String getTranslationForReadingToneLabel() {
		return i18nService.getTranslation("startConfiguration.readingTone.label");
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