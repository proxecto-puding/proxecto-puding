package main.view.swing;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import main.controller.MenuBarController;

public class MenuBarView {
	
	private static final MenuBarController menuBarController;
	
	static {
		menuBarController = new MenuBarController();
	};
	
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
		
		mnFile.setText("File");
		mnFile.setIcon(new ImageIcon(this.getClass().
				getResource("/main/resources/icons/application.png")));
		JMenuItem mntmExit = getExitMenuItem();
		mnFile.add(mntmExit);
		
		return mnFile;
	}
	
	private JMenuItem getExitMenuItem() {
		
		JMenuItem mntmExit = menuBarController.getExitMenuItem();
		
		mntmExit.setText("Exit");
		mntmExit.setIcon(new ImageIcon(this.getClass().
				getResource("/main/resources/icons/door_out.png")));
		
		return mntmExit;
	}
	
	private JMenu getHelpMenu() {
		
		JMenu mnHelp = new JMenu();
				
		mnHelp.setText("Help");
		mnHelp.setIcon(new ImageIcon(this.getClass().
				getResource("/main/resources/icons/help.png")));
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
		
		JMenuItem mntmUserManual = menuBarController.getUserManualMenuItem();
		
		mntmUserManual.setText("User manual");
		mntmUserManual.setIcon(new ImageIcon(this.getClass().
				getResource("/main/resources/icons/book_open.png")));
		
		return mntmUserManual;
	}
	
	private JMenuItem getConfAppApiMenuItem() {
		
		JMenuItem mntmConfAppApi = menuBarController.getConfAppApiMenuItem();
		
		mntmConfAppApi.setText("Conf App API");
		mntmConfAppApi.setIcon(new ImageIcon(this.getClass().
				getResource("/main/resources/icons/page_white_cup.png")));
		
		return mntmConfAppApi;
	}
	
	private JMenuItem getBagpipeApiMenuItem() {
		
		JMenuItem mntmBagpipeApi = menuBarController.getBagpipeApiMenuItem();
		
		mntmBagpipeApi.setText("Bagpipe API");
		mntmBagpipeApi.setIcon(new ImageIcon(this.getClass().
				getResource("/main/resources/icons/page_white_cplusplus.png")));
		
		return mntmBagpipeApi;
	}
	
	private JMenuItem getAboutMenuItem() {
		
		JMenuItem mntmAbout = menuBarController.getAboutMenuItem();
		
		mntmAbout.setText("About");
		mntmAbout.setIcon(new ImageIcon(this.getClass().
				getResource("/main/resources/icons/information.png")));
		
		return mntmAbout;
	}

}
