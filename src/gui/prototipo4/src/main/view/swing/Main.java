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
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;

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
	
	private JPanel contentPanel;
	
	static {
		menuBarView = new MenuBarView();
		startConfigurationView = new StartConfigurationView();
		selectionConfigurationView = new SelectionConfigurationView();
		tuningConfigurationView = new TuningConfigurationView();
		sensitivityConfigurationView = new SensitivityConfigurationView();
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
		JPanel panelFinger = new JPanel();
		tabbedPane.addTab("Dixitacións", null, panelFinger, null);
		
		JLabel lblCustomFingeringNote = new JLabel("Nota");
		
		JComboBox comboBoxCustomFingeringNote = new JComboBox();
		
		JLabel lblCustomFingeringOctave = new JLabel("Oitava");
		
		JComboBox comboBoxCustomFingeringOctave = new JComboBox();
		
		JLabel lblCustomFingeringNumber = new JLabel("Dixitación");
		
		JComboBox comboBoxCustomFingeringNumber = new JComboBox();
		
		JButton btnCustomFingeringNew = new JButton("Nova");
		
		JButton btnCustomFingeringRemove = new JButton("Eliminar");
		
		JSeparator separator_9 = new JSeparator();
		separator_9.setOrientation(SwingConstants.VERTICAL);
		
		JLabel lblChanterImage = new JLabel("Situación dos sensores");
		
		JSeparator separator_10 = new JSeparator();
		separator_10.setOrientation(SwingConstants.VERTICAL);
		
		JLabel lblSensors = new JLabel("Sensores");
		
		JCheckBox checkBoxSensor1 = new JCheckBox("1");
		
		JCheckBox checkBoxSensor2 = new JCheckBox("2");
		
		JCheckBox checkBoxSensor3 = new JCheckBox("3");
		
		JCheckBox checkBoxSensor4 = new JCheckBox("4");
		
		JCheckBox checkBoxSensor5 = new JCheckBox("5");
		
		JCheckBox checkBoxSensor6 = new JCheckBox("6");
		
		JLabel lblLeftHandSensors = new JLabel("Man esquerda");
		
		JLabel lblRightHandSensors = new JLabel("Man dereita");
		
		JCheckBox checkBoxSensor7 = new JCheckBox("7");
		
		JCheckBox checkBoxSensor8 = new JCheckBox("8");
		
		JCheckBox checkBoxSensor9 = new JCheckBox("9");
		GroupLayout gl_panelFinger = new GroupLayout(panelFinger);
		gl_panelFinger.setHorizontalGroup(
			gl_panelFinger.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelFinger.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelFinger.createParallelGroup(Alignment.LEADING)
						.addComponent(lblCustomFingeringNote)
						.addComponent(comboBoxCustomFingeringNote, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblCustomFingeringOctave)
						.addComponent(comboBoxCustomFingeringOctave, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblCustomFingeringNumber)
						.addComponent(comboBoxCustomFingeringNumber, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnCustomFingeringNew)
						.addComponent(btnCustomFingeringRemove))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator_9, GroupLayout.PREFERRED_SIZE, 11, GroupLayout.PREFERRED_SIZE)
					.addGap(76)
					.addComponent(lblChanterImage)
					.addPreferredGap(ComponentPlacement.RELATED, 78, Short.MAX_VALUE)
					.addComponent(separator_10, GroupLayout.PREFERRED_SIZE, 11, GroupLayout.PREFERRED_SIZE)
					.addGroup(gl_panelFinger.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelFinger.createSequentialGroup()
							.addGap(33)
							.addGroup(gl_panelFinger.createParallelGroup(Alignment.LEADING)
								.addComponent(checkBoxSensor1)
								.addGroup(gl_panelFinger.createSequentialGroup()
									.addGap(21)
									.addGroup(gl_panelFinger.createParallelGroup(Alignment.LEADING)
										.addComponent(checkBoxSensor3)
										.addComponent(checkBoxSensor2)
										.addComponent(checkBoxSensor4)))
								.addComponent(lblLeftHandSensors)
								.addComponent(lblRightHandSensors)
								.addGroup(gl_panelFinger.createSequentialGroup()
									.addGap(21)
									.addComponent(checkBoxSensor5))
								.addComponent(checkBoxSensor6)
								.addGroup(gl_panelFinger.createSequentialGroup()
									.addGap(21)
									.addGroup(gl_panelFinger.createParallelGroup(Alignment.LEADING)
										.addComponent(checkBoxSensor8)
										.addComponent(checkBoxSensor7)
										.addComponent(checkBoxSensor9)))))
						.addGroup(gl_panelFinger.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblSensors)))
					.addContainerGap())
		);
		gl_panelFinger.setVerticalGroup(
			gl_panelFinger.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelFinger.createSequentialGroup()
					.addGroup(gl_panelFinger.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelFinger.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panelFinger.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panelFinger.createSequentialGroup()
									.addComponent(lblSensors)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblLeftHandSensors)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(checkBoxSensor1)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(checkBoxSensor2)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(checkBoxSensor3)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(checkBoxSensor4)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblRightHandSensors)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(checkBoxSensor5)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(checkBoxSensor6)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(checkBoxSensor7)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(checkBoxSensor8)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(checkBoxSensor9))
								.addComponent(separator_9, GroupLayout.PREFERRED_SIZE, 368, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panelFinger.createSequentialGroup()
									.addComponent(lblCustomFingeringNote)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(comboBoxCustomFingeringNote, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(lblCustomFingeringOctave)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(comboBoxCustomFingeringOctave, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(lblCustomFingeringNumber)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(comboBoxCustomFingeringNumber, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(btnCustomFingeringNew)
									.addGap(18)
									.addComponent(btnCustomFingeringRemove))
								.addComponent(separator_10, GroupLayout.PREFERRED_SIZE, 368, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_panelFinger.createSequentialGroup()
							.addGap(172)
							.addComponent(lblChanterImage)))
					.addContainerGap(168, Short.MAX_VALUE))
		);
		panelFinger.setLayout(gl_panelFinger);
		contentPanel.setLayout(gl_contentPane);
	}
}
