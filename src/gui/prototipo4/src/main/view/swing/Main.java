package main.view.swing;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.UIManager;
import java.awt.Toolkit;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JMenuBar;
import javax.swing.JTabbedPane;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JSeparator;

public class Main extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2067752108552203318L;
	
	private static final String TITLE = "Proxecto Puding";
	private static final String ICON_IMAGE_ICON_PATH =
			"/main/resources/images/proxecto-puding-logo.png";
	
	private static final MenuBarView menuBarView;
	private static final StartConfigurationView startConfigurationView;
	private static final SelectionConfigurationView selectionConfigurationView;
	private static final TuningConfigurationView tuningConfigurationView;
	private static final SensitivityConfigurationView sensitivityConfigurationView;
	private static final FingeringConfigurationView fingeringConfigurationView;
	
	private JPanel contentPanel;
	
	static {
		menuBarView = new MenuBarView();
		startConfigurationView = new StartConfigurationView();
		selectionConfigurationView = new SelectionConfigurationView();
		tuningConfigurationView = new TuningConfigurationView();
		sensitivityConfigurationView = new SensitivityConfigurationView();
		fingeringConfigurationView = new FingeringConfigurationView();
	};

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				Main.class.getResource(ICON_IMAGE_ICON_PATH)));
		setTitle(TITLE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 480);
		
		// Menu bar.
		JMenuBar menuBar = menuBarView.getMenuBar();
		setJMenuBar(menuBar);
		
		// Button bar.
		contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPanel);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		JButton btnApply = new JButton("Aplicar");
		
		JButton btnUndo = new JButton("Desfacer");
		
		JButton btnDefault = new JButton("Por defecto");
		
		JSeparator separator = new JSeparator();
		GroupLayout gl_contentPane = new GroupLayout(contentPanel);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(tabbedPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 604, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnApply)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnUndo)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnDefault))
						.addComponent(separator, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 604, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 4, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnDefault)
						.addComponent(btnUndo)
						.addComponent(btnApply))
					.addContainerGap())
		);
		
		// Start panel.
		JPanel panelStart = startConfigurationView.getStartPanel();
		tabbedPane.addTab("Inicio", null, panelStart, null);
		
		// Selection panel.
		JPanel panelSelect = selectionConfigurationView.getSelectionPanel();
		tabbedPane.addTab("Selección", null, panelSelect, null);
		
		// Tuning panel.
		JPanel panelTuning = tuningConfigurationView.getTuningPanel();
		tabbedPane.addTab("Afinación", null, panelTuning, null);
		
		// Sensitivity panel.
		JPanel panelSensit = sensitivityConfigurationView.getSensitivityPanel();
		tabbedPane.addTab("Sensibilidade", null, panelSensit, null);
		
		// Fingerings panel.
		JPanel panelFinger = fingeringConfigurationView.getFingeringPanel();
		tabbedPane.addTab("Dixitacións", null, panelFinger, null);
		
		
		
		contentPanel.setLayout(gl_contentPane);
	}
}
