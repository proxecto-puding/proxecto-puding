package main.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import main.model.services.ConfigurationApplicationService;
import main.model.services.DeviceManagerService;
import main.model.services.impl.ConfigurationApplicationServiceImpl;
import main.model.services.impl.DeviceManagerServiceImpl;

public class StartConfigurationController {
	
	private static final DeviceManagerService deviceManagerService;
	private static final ConfigurationApplicationService confAppService;
	
	static {
		deviceManagerService = new DeviceManagerServiceImpl();
		confAppService = new ConfigurationApplicationServiceImpl();
	};

	public String[] getChanters() {
		
		String[] chanters = {};
		
		chanters = (String[]) deviceManagerService.
				getBagpipeDeviceIds().toArray();
		
		return chanters;
	}
	
	public ActionListener getActionListenerForChanterSelectionComboBox() {
		
		ActionListener actionListener = new ActionListener() {
			
			public void actionPerformed(ActionEvent event) {
				
				// TODO Implement.
				// Load the config for the selected device, notifying all the
				// related view elements.
			}
		};
		
		return actionListener;
	}
	
	public String[] getReadingTones() {
		
		String[] readingTones = {};
		
		readingTones = (String[]) confAppService.getReadingTones().toArray();
		
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
				// TODO Notify all the related view elements.
				confAppService.setReadingTone(readingTone);
			}
		};
		
		return actionListener;
	}

}
