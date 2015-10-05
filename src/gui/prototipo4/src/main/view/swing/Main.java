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
import javax.swing.JSlider;
import javax.swing.JCheckBox;
import javax.swing.JSpinner;

public class Main extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2067752108552203318L;
	
	private static final String ICON_IMAGE_ICON_PATH =
			"/main/resources/images/proxecto-puding-logo.png";
	
	private static final MenuBarView menuBarView;
	private static final StartConfigurationView startConfigurationView;
	
	private JPanel contentPane;
	
	static {
		menuBarView = new MenuBarView();
		startConfigurationView = new StartConfigurationView();
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
		setTitle("Proxecto Puding");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 480);
		
		// Menu bar.
		JMenuBar menuBar = menuBarView.getMenuBar();
		setJMenuBar(menuBar);
		
		// Button bar.
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		JButton btnApply = new JButton("Aplicar");
		
		JButton btnUndo = new JButton("Desfacer");
		
		JButton btnDefault = new JButton("Por defecto");
		
		JSeparator separator = new JSeparator();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
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
		JPanel panelSelect = new JPanel();
		tabbedPane.addTab("Selección", null, panelSelect, null);
		
		JLabel lblVolume = new JLabel("Volume");
		
		JSlider sliderVolume = new JSlider();
		
		JSeparator separator_1 = new JSeparator();
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setOrientation(SwingConstants.VERTICAL);
		
		JLabel lblTuningTone = new JLabel("Tonalidade");
		
		JComboBox comboBoxTuningTone = new JComboBox();
		
		JLabel lblTuningOctave = new JLabel("Oitava");
		
		JComboBox comboBoxTuningOctave = new JComboBox();
		
		JLabel lblSamples = new JLabel("Mostras");
		
		JComboBox comboBoxSamples = new JComboBox();
		
		JSeparator separator_3 = new JSeparator();
		
		JSeparator separator_4 = new JSeparator();
		
		JSeparator separator_5 = new JSeparator();
		separator_5.setOrientation(SwingConstants.VERTICAL);
		
		JLabel lblFingeringTypes = new JLabel("Dixitación");
		
		JCheckBox chckbxAberto = new JCheckBox("Aberto");
		
		JCheckBox chckbxPechado = new JCheckBox("Pechado");
		
		JCheckBox chckbxCustom = new JCheckBox("Personalizada");
		
		JSeparator separator_6 = new JSeparator();
		separator_6.setOrientation(SwingConstants.VERTICAL);
		
		JLabel lblComplements = new JLabel("Complementos");
		
		JCheckBox chckbxBag = new JCheckBox("Fol");
		
		JLabel lblDrones = new JLabel("Bordóns");
		
		JCheckBox chckbxBassDrone = new JCheckBox("Ronco");
		
		JCheckBox chckbxTenorDrone = new JCheckBox("Ronqueta");
		
		JCheckBox chckbxHighDrone = new JCheckBox("Chillón");
		
		JSeparator separator_7 = new JSeparator();
		separator_7.setOrientation(SwingConstants.VERTICAL);
		GroupLayout gl_panelSelect = new GroupLayout(panelSelect);
		gl_panelSelect.setHorizontalGroup(
			gl_panelSelect.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelSelect.createSequentialGroup()
					.addGroup(gl_panelSelect.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelSelect.createSequentialGroup()
							.addGap(65)
							.addComponent(separator_1, GroupLayout.PREFERRED_SIZE, 1, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelSelect.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panelSelect.createParallelGroup(Alignment.LEADING)
								.addComponent(sliderVolume, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblVolume))))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelSelect.createParallelGroup(Alignment.LEADING)
						.addComponent(separator_2, GroupLayout.PREFERRED_SIZE, 11, GroupLayout.PREFERRED_SIZE)
						.addComponent(separator_7, GroupLayout.PREFERRED_SIZE, 11, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelSelect.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelSelect.createSequentialGroup()
							.addGap(115)
							.addGroup(gl_panelSelect.createParallelGroup(Alignment.LEADING)
								.addComponent(separator_4, GroupLayout.PREFERRED_SIZE, 1, GroupLayout.PREFERRED_SIZE)
								.addComponent(separator_3, GroupLayout.PREFERRED_SIZE, 1, GroupLayout.PREFERRED_SIZE)))
						.addComponent(lblTuningTone)
						.addComponent(comboBoxTuningTone, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblTuningOctave)
						.addComponent(comboBoxTuningOctave, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblSamples)
						.addComponent(comboBoxSamples, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator_5, GroupLayout.PREFERRED_SIZE, 11, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelSelect.createParallelGroup(Alignment.LEADING)
						.addComponent(lblFingeringTypes)
						.addGroup(gl_panelSelect.createSequentialGroup()
							.addGap(12)
							.addGroup(gl_panelSelect.createParallelGroup(Alignment.LEADING)
								.addComponent(chckbxPechado)
								.addComponent(chckbxAberto)
								.addComponent(chckbxCustom))))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator_6, GroupLayout.PREFERRED_SIZE, 11, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelSelect.createParallelGroup(Alignment.LEADING)
						.addComponent(lblComplements)
						.addGroup(gl_panelSelect.createSequentialGroup()
							.addGap(12)
							.addGroup(gl_panelSelect.createParallelGroup(Alignment.LEADING)
								.addComponent(lblDrones)
								.addComponent(chckbxBag)
								.addGroup(gl_panelSelect.createSequentialGroup()
									.addGap(12)
									.addGroup(gl_panelSelect.createParallelGroup(Alignment.LEADING)
										.addComponent(chckbxTenorDrone)
										.addComponent(chckbxBassDrone)
										.addComponent(chckbxHighDrone))))))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_panelSelect.setVerticalGroup(
			gl_panelSelect.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelSelect.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelSelect.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelSelect.createSequentialGroup()
							.addComponent(lblComplements)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(chckbxBag)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblDrones)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(chckbxBassDrone)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(chckbxTenorDrone)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(chckbxHighDrone))
						.addComponent(separator_6, GroupLayout.PREFERRED_SIZE, 368, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panelSelect.createSequentialGroup()
							.addComponent(lblFingeringTypes)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(chckbxAberto)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(chckbxPechado)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(chckbxCustom))
						.addGroup(gl_panelSelect.createSequentialGroup()
							.addComponent(lblTuningTone)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(comboBoxTuningTone, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lblTuningOctave)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(comboBoxTuningOctave, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lblSamples)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(comboBoxSamples, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelSelect.createSequentialGroup()
							.addComponent(lblVolume)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(sliderVolume, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(48)
							.addComponent(separator_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelSelect.createSequentialGroup()
							.addGroup(gl_panelSelect.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(separator_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(separator_5, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 368, Short.MAX_VALUE)
								.addGroup(Alignment.LEADING, gl_panelSelect.createSequentialGroup()
									.addComponent(separator_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(separator_7, GroupLayout.PREFERRED_SIZE, 368, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)))
							.addGap(148)
							.addComponent(separator_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panelSelect.setLayout(gl_panelSelect);
		
		// Tuning panel.
		JPanel panelTuning = new JPanel();
		tabbedPane.addTab("Afinación", null, panelTuning, null);
		
		JLabel lblTuningFrequency = new JLabel("Frecuencia");
		
		JSpinner spinnerTuningFrequency = new JSpinner();
		
		JLabel lblTuningHz = new JLabel("Hz");
		
		JLabel lblTuningMode = new JLabel("Modo");
		
		JComboBox comboBoxTuningMode = new JComboBox();
		
		JSeparator separator_8 = new JSeparator();
		separator_8.setOrientation(SwingConstants.VERTICAL);
		
		JLabel lblPreciseTuningSettings = new JLabel("Axuste fino");
		
		JLabel lblPreciseTuningNote = new JLabel("Nota");
		
		JComboBox comboBoxPreciseTuningNote = new JComboBox();
		
		JLabel lblPreciseTuningOctave = new JLabel("Oitava");
		
		JComboBox comboBoxPreciseTuningOctave = new JComboBox();
		
		JLabel lblPreciseTuningCents = new JLabel("Cents");
		
		JSpinner spinnerPreciseTuningCents = new JSpinner();
		GroupLayout gl_panelTuning = new GroupLayout(panelTuning);
		gl_panelTuning.setHorizontalGroup(
			gl_panelTuning.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelTuning.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelTuning.createParallelGroup(Alignment.LEADING)
						.addComponent(lblTuningFrequency)
						.addGroup(gl_panelTuning.createSequentialGroup()
							.addComponent(spinnerTuningFrequency, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblTuningHz))
						.addComponent(lblTuningMode)
						.addComponent(comboBoxTuningMode, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator_8, GroupLayout.PREFERRED_SIZE, 11, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelTuning.createParallelGroup(Alignment.LEADING)
						.addComponent(lblPreciseTuningSettings)
						.addGroup(gl_panelTuning.createSequentialGroup()
							.addGap(12)
							.addGroup(gl_panelTuning.createParallelGroup(Alignment.LEADING)
								.addComponent(comboBoxPreciseTuningNote, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblPreciseTuningNote)
								.addComponent(lblPreciseTuningOctave)
								.addComponent(comboBoxPreciseTuningOctave, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblPreciseTuningCents)
								.addComponent(spinnerPreciseTuningCents, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(391, Short.MAX_VALUE))
		);
		gl_panelTuning.setVerticalGroup(
			gl_panelTuning.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelTuning.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelTuning.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelTuning.createSequentialGroup()
							.addComponent(lblPreciseTuningSettings)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblPreciseTuningNote)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(comboBoxPreciseTuningNote, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lblPreciseTuningOctave)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(comboBoxPreciseTuningOctave, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lblPreciseTuningCents)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(spinnerPreciseTuningCents, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(separator_8, GroupLayout.PREFERRED_SIZE, 368, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panelTuning.createSequentialGroup()
							.addComponent(lblTuningFrequency)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panelTuning.createParallelGroup(Alignment.BASELINE)
								.addComponent(spinnerTuningFrequency, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblTuningHz))
							.addGap(18)
							.addComponent(lblTuningMode)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(comboBoxTuningMode, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(168, Short.MAX_VALUE))
		);
		panelTuning.setLayout(gl_panelTuning);
		
		// Sensitivity panel.
		JPanel panelSensit = new JPanel();
		tabbedPane.addTab("Sensibilidade", null, panelSensit, null);
		
		JLabel lblBagPressure = new JLabel("Presión do fol");
		
		JSlider sliderBagPressure = new JSlider();
		GroupLayout gl_panelSensit = new GroupLayout(panelSensit);
		gl_panelSensit.setHorizontalGroup(
			gl_panelSensit.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelSensit.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelSensit.createParallelGroup(Alignment.LEADING)
						.addComponent(lblBagPressure)
						.addComponent(sliderBagPressure, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(385, Short.MAX_VALUE))
		);
		gl_panelSensit.setVerticalGroup(
			gl_panelSensit.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelSensit.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblBagPressure)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(sliderBagPressure, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(499, Short.MAX_VALUE))
		);
		panelSensit.setLayout(gl_panelSensit);
		
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
		contentPane.setLayout(gl_contentPane);
	}
}
