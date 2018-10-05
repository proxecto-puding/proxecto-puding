/*
 * Proxecto Puding
 * Copyright (C) 2011 Alejo Pacín <info@proxecto-puding.org>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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
