package main.view.swing;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import main.controller.MenuBarController;

public class MenuBarView extends View {
	
	private static final String FILE_MENU_ICON_PATH = 
			"/main/resources/icons/application.png";
	private static final String EXIT_MENU_ITEM_ICON_PATH =
			"/main/resources/icons/door_out.png";
	private static final String HELP_MENU_ICON_PATH =
			"/main/resources/icons/help.png";
	private static final String USER_MANUAL_MENU_ITEM_ICON_PATH =
			"/main/resources/icons/book_open.png";
	private static final String CONF_APP_API_MENU_ITEM_ICON_PATH =
			"/main/resources/icons/page_white_cup.png";
	private static final String BAGPIPE_API_MENU_ITEM_ICON_PATH =
			"/main/resources/icons/page_white_cplusplus.png";
	private static final String ABOUT_MENU_ITEM_ICON_PATH =
			"/main/resources/icons/information.png";
	
	private MenuBarController menuBarController = new MenuBarController();
	
	public JMenuBar getMenuBar() {
		
		JMenuBar menuBar =new JMenuBar();
		
		JMenu mnFile = getFileMenu();
		menuBar.add(mnFile);
		JMenu mnHelp = getHelpMenu();
		menuBar.add(mnHelp);
		
		return menuBar;
	}
	
	private JMenu getFileMenu() {
		
		JMenu mnFile = new JMenu();
		
		mnFile.setText(menuBarController.getTranslationForFileMenu());
		mnFile.setIcon(new ImageIcon(this.getClass().
				getResource(FILE_MENU_ICON_PATH)));
		
		JMenuItem mntmExit = getExitMenuItem();
		mnFile.add(mntmExit);
		
		return mnFile;
	}
	
	private JMenuItem getExitMenuItem() {
		
		JMenuItem mntmExit = new JMenuItem();
		
		mntmExit.addActionListener(
				menuBarController.getActionListenerForExitMenuItem());
		
		mntmExit.setText(menuBarController.getTranslationForExitMenuItem());
		mntmExit.setIcon(new ImageIcon(this.getClass().
				getResource(EXIT_MENU_ITEM_ICON_PATH)));
		
		return mntmExit;
	}
	
	private JMenu getHelpMenu() {
		
		JMenu mnHelp = new JMenu();
		
		mnHelp.setText(menuBarController.getTranslationForHelpMenu());
		mnHelp.setIcon(new ImageIcon(this.getClass().
				getResource(HELP_MENU_ICON_PATH)));
		
		JMenuItem mntmUserManual = getUserManualMenuItem();
		mnHelp.add(mntmUserManual);
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
				
		mntmUserManual.addActionListener(
				menuBarController.getActionListenerForUserManualMenuItem());
		
		mntmUserManual.setText(menuBarController.getTranslationForUserManualMenuItem());
		mntmUserManual.setIcon(new ImageIcon(this.getClass().
				getResource(USER_MANUAL_MENU_ITEM_ICON_PATH)));
		
		return mntmUserManual;
	}
	
	private JMenuItem getConfAppApiMenuItem() {
		
		JMenuItem mntmConfAppApi = new JMenuItem();
		
		mntmConfAppApi.addActionListener(
				menuBarController.getActionListenerForConfAppApiMenuItem());
		
		mntmConfAppApi.setText(menuBarController.getTranslationForConfAppApiMenuItem());
		mntmConfAppApi.setIcon(new ImageIcon(this.getClass().
				getResource(CONF_APP_API_MENU_ITEM_ICON_PATH)));
		
		return mntmConfAppApi;
	}
	
	private JMenuItem getBagpipeApiMenuItem() {
		
		JMenuItem mntmBagpipeApi = new JMenuItem();
		
		mntmBagpipeApi.addActionListener(
				menuBarController.getActionListenerForBagpipeApiMenuItem());
		
		mntmBagpipeApi.setText(menuBarController.getTranslationForBagpipeApiMenuItem());
		mntmBagpipeApi.setIcon(new ImageIcon(this.getClass().
				getResource(BAGPIPE_API_MENU_ITEM_ICON_PATH)));
		
		return mntmBagpipeApi;
	}
	
	private JMenuItem getAboutMenuItem() {
		
		JMenuItem mntmAbout = new JMenuItem();
		
		mntmAbout.addActionListener(
				menuBarController.getActionListenerForAboutMenuItem());
		
		mntmAbout.setText(menuBarController.getTranslationForAboutMenuItem());
		mntmAbout.setIcon(new ImageIcon(this.getClass().
				getResource(ABOUT_MENU_ITEM_ICON_PATH)));
		
		return mntmAbout;
	}

}
