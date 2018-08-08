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
