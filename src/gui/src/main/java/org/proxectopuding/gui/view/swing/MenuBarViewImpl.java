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

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.proxectopuding.gui.controller.MenuBarController;
import org.proxectopuding.gui.view.MenuBarView;

import com.google.inject.Inject;

public class MenuBarViewImpl extends ViewImpl implements MenuBarView {
	
	private static final String FILE_MENU_ICON_PATH = 
			"icons/application.png";
	private static final String EXIT_MENU_ITEM_ICON_PATH =
			"icons/door_out.png";
	private static final String HELP_MENU_ICON_PATH =
			"icons/help.png";
	private static final String USER_MANUAL_MENU_ITEM_ICON_PATH =
			"icons/book_open.png";
	private static final String TECHNICAL_MANUAL_MENU_ITEM_ICON_PATH =
			"icons/book.png";
	private static final String CONF_APP_API_MENU_ITEM_ICON_PATH =
			"icons/page_white_cup.png";
	private static final String BAGPIPE_API_MENU_ITEM_ICON_PATH =
			"icons/page_white_cplusplus.png";
	private static final String ABOUT_MENU_ITEM_ICON_PATH =
			"icons/information.png";
	
	private final MenuBarController menuBarController;
	
	@Inject
	public MenuBarViewImpl(MenuBarController menuBarController) {
		
		this.menuBarController = menuBarController;
	}
	
	@Override
	public JMenuBar getMenuBar() {
		
		JMenuBar menuBar = new JMenuBar();
		
		JMenu mnFile = getFileMenu();
		menuBar.add(mnFile);
		JMenu mnHelp = getHelpMenu();
		menuBar.add(mnHelp);
		
		return menuBar;
	}
	
	private JMenu getFileMenu() {
		
		JMenu mnFile = new JMenu();
		
		mnFile.setText(menuBarController.getFileMenuLabel());
		mnFile.setIcon(new ImageIcon(this.getClass().getClassLoader()
				.getResource(FILE_MENU_ICON_PATH)));
		
		JMenuItem mntmExit = getExitMenuItem();
		mnFile.add(mntmExit);
		
		return mnFile;
	}
	
	private JMenuItem getExitMenuItem() {
		
		JMenuItem mntmExit = new JMenuItem();
		
		mntmExit.setText(menuBarController.getExitMenuItemLabel());
		mntmExit.setIcon(new ImageIcon(this.getClass().getClassLoader()
				.getResource(EXIT_MENU_ITEM_ICON_PATH)));
		
		mntmExit.addActionListener(event -> menuBarController.onExit());
		
		return mntmExit;
	}
	
	private JMenu getHelpMenu() {
		
		JMenu mnHelp = new JMenu();
		
		mnHelp.setText(menuBarController.getHelpMenuLabel());
		mnHelp.setIcon(new ImageIcon(this.getClass().getClassLoader()
				.getResource(HELP_MENU_ICON_PATH)));
		
		JMenuItem mntmUserManual = getUserManualMenuItem();
		mnHelp.add(mntmUserManual);
		JMenuItem mntmTechnicalManual = getTechnicalManualMenuItem();
		mnHelp.add(mntmTechnicalManual);
		JMenuItem mntmConfAppApi = getConfAppApiMenuItem();
		mnHelp.add(mntmConfAppApi);
		JMenuItem mntmBagpipeApi = getBagpipeApiMenuItem();
		mnHelp.add(mntmBagpipeApi);
		JMenuItem mntmAbout = getAboutMenuItem();
		mnHelp.add(mntmAbout);
		
		return mnHelp;
	}
	
	private JMenuItem getUserManualMenuItem() {
		
		JMenuItem mntmUserManual = new JMenuItem(); 
				
		mntmUserManual.setText(menuBarController.getUserManualMenuItemLabel());
		mntmUserManual.setIcon(new ImageIcon(this.getClass().getClassLoader()
				.getResource(USER_MANUAL_MENU_ITEM_ICON_PATH)));
		
		mntmUserManual.addActionListener(
				event -> menuBarController.onOpenUserManual());
		
		return mntmUserManual;
	}
	
	private JMenuItem getTechnicalManualMenuItem() {
		
		JMenuItem mntmTechnicalManual = new JMenuItem(); 
				
		mntmTechnicalManual.setText(
				menuBarController.getTechnicalManualMenuItemLabel());
		mntmTechnicalManual.setIcon(new ImageIcon(this.getClass().getClassLoader()
				.getResource(TECHNICAL_MANUAL_MENU_ITEM_ICON_PATH)));
		
		mntmTechnicalManual.addActionListener(
				event -> menuBarController.onOpenTechnicalManual());
		
		return mntmTechnicalManual;
	}
	
	private JMenuItem getConfAppApiMenuItem() {
		
		JMenuItem mntmConfAppApi = new JMenuItem();
		
		mntmConfAppApi.setText(menuBarController.getConfAppApiMenuItemLabel());
		mntmConfAppApi.setIcon(new ImageIcon(this.getClass().getClassLoader()
				.getResource(CONF_APP_API_MENU_ITEM_ICON_PATH)));
		
		mntmConfAppApi.addActionListener(
				event -> menuBarController.openConfAppApi());
		
		return mntmConfAppApi;
	}
	
	private JMenuItem getBagpipeApiMenuItem() {
		
		JMenuItem mntmBagpipeApi = new JMenuItem();
		
		mntmBagpipeApi.setText(menuBarController.getBagpipeApiMenuItemLabel());
		mntmBagpipeApi.setIcon(new ImageIcon(this.getClass().getClassLoader()
				.getResource(BAGPIPE_API_MENU_ITEM_ICON_PATH)));
		
		mntmBagpipeApi.addActionListener(
				event -> menuBarController.openBagpipeApi());
		
		return mntmBagpipeApi;
	}
	
	private JMenuItem getAboutMenuItem() {
		
		JMenuItem mntmAbout = new JMenuItem();
		
		mntmAbout.setText(menuBarController.getAboutMenuItemLabel());
		mntmAbout.setIcon(new ImageIcon(this.getClass().getClassLoader()
				.getResource(ABOUT_MENU_ITEM_ICON_PATH)));
		
		mntmAbout.addActionListener(event -> menuBarController.openAbout());
		
		return mntmAbout;
	}

}
