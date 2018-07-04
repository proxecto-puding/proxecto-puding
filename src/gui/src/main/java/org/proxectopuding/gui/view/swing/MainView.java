package org.proxectopuding.gui.view.swing;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JMenuBar;

import org.proxectopuding.gui.controller.MainController;

public class MainView extends JFrame {

	private static final Logger LOGGER = Logger.getLogger(MainView.class.getName());
	
	private static final long serialVersionUID = 2067752108552203318L;
	
	private static final String TITLE = "Proxecto Puding";
	private static final String ICON_IMAGE_ICON_PATH =
			"images/proxecto-puding-logo.png";
	
	private static final int X = 100;
	private static final int Y = 100;
	private static final int WIDTH = 600;
	private static final int HEIGHT = 800;
	
	private MenuBarView menuBarView = new MenuBarView();
	private ContentPanelView contentPanelView = new ContentPanelView();
	private MainController mainController = new MainController();
	
	/**
	 * Create the frame.
	 */
	public MainView() {
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				MainView.class.getClassLoader().getResource(ICON_IMAGE_ICON_PATH)));
		setTitle(TITLE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(X, Y, WIDTH, HEIGHT);
		
		// Menu bar.
		JMenuBar menuBar = menuBarView.getMenuBar();
		setJMenuBar(menuBar);
		
		// Content panel.
		JPanel contentPanel = contentPanelView.getContentPanel();
		setContentPane(contentPanel);
		
		mainController.findChanters();
	}
	
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
					MainView frame = new MainView();
					frame.setVisible(true);
				} catch (Exception e) {
					LOGGER.log(Level.SEVERE, "Unable to show the main view", e);
				}
			}
		});
	}
}
