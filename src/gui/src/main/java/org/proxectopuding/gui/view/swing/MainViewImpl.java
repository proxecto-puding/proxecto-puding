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

package org.proxectopuding.gui.view.swing;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Toolkit;

import javax.swing.JMenuBar;

import org.proxectopuding.gui.controller.MainController;
import org.proxectopuding.gui.view.ContentPanelView;
import org.proxectopuding.gui.view.MainView;
import org.proxectopuding.gui.view.MenuBarView;

import com.google.inject.Inject;

public class MainViewImpl extends JFrame implements MainView {

	private static final long serialVersionUID = 2067752108552203318L;
	
	private static final String TITLE = "Proxecto Puding";
	private static final String ICON_IMAGE_ICON_PATH =
			"images/proxecto-puding-logo.png";
	
	private static final int X = 100;
	private static final int Y = 100;
	private static final int WIDTH = 600;
	private static final int HEIGHT = 768;
	
	/**
	 * Create the frame.
	 */
	@Inject
	public MainViewImpl(MenuBarView menuBarView,
			ContentPanelView contentPanelView,
			MainController mainController) {
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				MainViewImpl.class.getClassLoader().getResource(ICON_IMAGE_ICON_PATH)));
		setTitle(TITLE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(X, Y, WIDTH, HEIGHT);
		
		// Menu bar.
		JMenuBar menuBar = menuBarView.getMenuBar();
		setJMenuBar(menuBar);
		
		// Content panel.
		JPanel contentPanel = contentPanelView.getContentPanel();
		setContentPane(contentPanel);
	}
}
