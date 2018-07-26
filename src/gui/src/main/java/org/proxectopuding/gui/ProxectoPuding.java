package org.proxectopuding.gui;

import java.awt.EventQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.UIManager;

import org.proxectopuding.gui.injection.InjectionModule;
import org.proxectopuding.gui.view.swing.MainViewImpl;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class ProxectoPuding {
	
	private static final Logger LOGGER = Logger.getLogger(ProxectoPuding.class.getName());
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		LOGGER.log(Level.INFO, "Application started");
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			LOGGER.log(Level.SEVERE, "Unable to set application look and feel", e);
		}
		
		EventQueue.invokeLater(new Runnable() {
			
			public void run() {
				try {
					Injector injector = Guice.createInjector(new InjectionModule());
					MainViewImpl frame = injector.getInstance(MainViewImpl.class);
					frame.setVisible(true);
				} catch (Exception e) {
					LOGGER.log(Level.SEVERE, "Unable to show the main view", e);
				}
			}
		});
	}
}
