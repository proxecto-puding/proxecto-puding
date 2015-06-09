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
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.ImageIcon;
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
	private JPanel contentPane;

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
		setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/main/resources/proxecto-puding-logo.png")));
		setTitle("Proxecto Puding");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 480);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		mnFile.setIcon(new ImageIcon(Main.class.getResource("/main/resources/icons/application.png")));
		menuBar.add(mnFile);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.setIcon(new ImageIcon(Main.class.getResource("/main/resources/icons/door_out.png")));
		mnFile.add(mntmExit);
		
		JMenu mnHelp = new JMenu("Help");
		mnHelp.setIcon(new ImageIcon(Main.class.getResource("/main/resources/icons/help.png")));
		menuBar.add(mnHelp);
		
		JMenuItem mntmUserManual = new JMenuItem("User manual");
		mntmUserManual.setIcon(new ImageIcon(Main.class.getResource("/main/resources/icons/book_open.png")));
		mnHelp.add(mntmUserManual);
		
		JMenuItem mntmConfAppApi = new JMenuItem("Conf App API");
		mntmConfAppApi.setIcon(new ImageIcon(Main.class.getResource("/main/resources/icons/page_white_cup.png")));
		mnHelp.add(mntmConfAppApi);
		
		JMenuItem mntmBagpipeApi = new JMenuItem("Bagpipe API");
		mntmBagpipeApi.setIcon(new ImageIcon(Main.class.getResource("/main/resources/icons/page_white_cplusplus.png")));
		mnHelp.add(mntmBagpipeApi);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.setIcon(new ImageIcon(Main.class.getResource("/main/resources/icons/information.png")));
		mnHelp.add(mntmAbout);
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
		
		JPanel panelStart = new JPanel();
		tabbedPane.addTab("Inicio", null, panelStart, null);
		
		JLabel lblChanterSelect = new JLabel("Seleccione o punteiro a configurar:");
		
		JComboBox comboBoxChanterSelect = new JComboBox();
		
		JLabel lblReadTone = new JLabel("Seleccione o tipo de lectura a empregar:");
		
		JComboBox comboBoxReadTone = new JComboBox();
		GroupLayout gl_panelStart = new GroupLayout(panelStart);
		gl_panelStart.setHorizontalGroup(
			gl_panelStart.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelStart.createSequentialGroup()
					.addGap(269)
					.addComponent(comboBoxChanterSelect, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(299, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_panelStart.createSequentialGroup()
					.addContainerGap(214, Short.MAX_VALUE)
					.addGroup(gl_panelStart.createParallelGroup(Alignment.LEADING)
						.addComponent(lblReadTone)
						.addComponent(lblChanterSelect))
					.addGap(181))
				.addGroup(gl_panelStart.createSequentialGroup()
					.addGap(274)
					.addComponent(comboBoxReadTone, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(294, Short.MAX_VALUE))
		);
		gl_panelStart.setVerticalGroup(
			gl_panelStart.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelStart.createSequentialGroup()
					.addGap(70)
					.addComponent(lblChanterSelect)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(comboBoxChanterSelect, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lblReadTone)
					.addGap(18)
					.addComponent(comboBoxReadTone, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(106, Short.MAX_VALUE))
		);
		panelStart.setLayout(gl_panelStart);
		
		JPanel panelSelect = new JPanel();
		tabbedPane.addTab("Selección", null, panelSelect, null);
		
		JLabel lblVolume = new JLabel("Volume");
		
		JSlider slider = new JSlider();
		
		JSeparator separator_1 = new JSeparator();
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setOrientation(SwingConstants.VERTICAL);
		
		JLabel lblTone = new JLabel("Tonalidade");
		
		JComboBox comboBoxTone = new JComboBox();
		
		JLabel lblOitava = new JLabel("Oitava");
		
		JComboBox comboBox = new JComboBox();
		
		JLabel lblMostras = new JLabel("Mostras");
		
		JComboBox comboBox_1 = new JComboBox();
		
		JSeparator separator_3 = new JSeparator();
		
		JSeparator separator_4 = new JSeparator();
		
		JSeparator separator_5 = new JSeparator();
		separator_5.setOrientation(SwingConstants.VERTICAL);
		
		JLabel lblDixitacin = new JLabel("Dixitación");
		
		JCheckBox chckbxAberta = new JCheckBox("Aberta");
		
		JCheckBox chckbxPechada = new JCheckBox("Pechada");
		
		JCheckBox chckbxPersonalizada = new JCheckBox("Personalizada");
		
		JSeparator separator_6 = new JSeparator();
		separator_6.setOrientation(SwingConstants.VERTICAL);
		
		JLabel lblComplementos = new JLabel("Complementos");
		
		JCheckBox chckbxFol = new JCheckBox("Fol");
		
		JLabel lblBordns = new JLabel("Bordóns");
		
		JCheckBox chckbxRonco = new JCheckBox("Ronco");
		
		JCheckBox chckbxRonqueta = new JCheckBox("Ronqueta");
		
		JCheckBox chckbxChilln = new JCheckBox("Chillón");
		
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
								.addComponent(slider, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
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
						.addComponent(lblTone)
						.addComponent(comboBoxTone, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblOitava)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblMostras)
						.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator_5, GroupLayout.PREFERRED_SIZE, 11, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelSelect.createParallelGroup(Alignment.LEADING)
						.addComponent(lblDixitacin)
						.addGroup(gl_panelSelect.createSequentialGroup()
							.addGap(12)
							.addGroup(gl_panelSelect.createParallelGroup(Alignment.LEADING)
								.addComponent(chckbxPechada)
								.addComponent(chckbxAberta)
								.addComponent(chckbxPersonalizada))))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator_6, GroupLayout.PREFERRED_SIZE, 11, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelSelect.createParallelGroup(Alignment.LEADING)
						.addComponent(lblComplementos)
						.addGroup(gl_panelSelect.createSequentialGroup()
							.addGap(12)
							.addGroup(gl_panelSelect.createParallelGroup(Alignment.LEADING)
								.addComponent(lblBordns)
								.addComponent(chckbxFol)
								.addGroup(gl_panelSelect.createSequentialGroup()
									.addGap(12)
									.addGroup(gl_panelSelect.createParallelGroup(Alignment.LEADING)
										.addComponent(chckbxRonqueta)
										.addComponent(chckbxRonco)
										.addComponent(chckbxChilln))))))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_panelSelect.setVerticalGroup(
			gl_panelSelect.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelSelect.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelSelect.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelSelect.createSequentialGroup()
							.addComponent(lblComplementos)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(chckbxFol)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblBordns)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(chckbxRonco)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(chckbxRonqueta)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(chckbxChilln))
						.addComponent(separator_6, GroupLayout.PREFERRED_SIZE, 368, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panelSelect.createSequentialGroup()
							.addComponent(lblDixitacin)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(chckbxAberta)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(chckbxPechada)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(chckbxPersonalizada))
						.addGroup(gl_panelSelect.createSequentialGroup()
							.addComponent(lblTone)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(comboBoxTone, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lblOitava)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lblMostras)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelSelect.createSequentialGroup()
							.addComponent(lblVolume)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(slider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
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
		
		JPanel panelTuning = new JPanel();
		tabbedPane.addTab("Afinación", null, panelTuning, null);
		
		JLabel lblFrecuencia = new JLabel("Frecuencia");
		
		JSpinner spinner = new JSpinner();
		
		JLabel lblHz = new JLabel("Hz");
		
		JLabel lblModo = new JLabel("Modo");
		
		JComboBox comboBox_2 = new JComboBox();
		
		JSeparator separator_8 = new JSeparator();
		separator_8.setOrientation(SwingConstants.VERTICAL);
		
		JLabel lblAxusteFino = new JLabel("Axuste fino");
		
		JLabel lblNota = new JLabel("Nota");
		
		JComboBox comboBox_3 = new JComboBox();
		
		JLabel lblOitava_1 = new JLabel("Oitava");
		
		JComboBox comboBox_4 = new JComboBox();
		
		JLabel lblCents = new JLabel("Cents");
		
		JSpinner spinner_1 = new JSpinner();
		GroupLayout gl_panelTuning = new GroupLayout(panelTuning);
		gl_panelTuning.setHorizontalGroup(
			gl_panelTuning.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelTuning.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelTuning.createParallelGroup(Alignment.LEADING)
						.addComponent(lblFrecuencia)
						.addGroup(gl_panelTuning.createSequentialGroup()
							.addComponent(spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblHz))
						.addComponent(lblModo)
						.addComponent(comboBox_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator_8, GroupLayout.PREFERRED_SIZE, 11, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelTuning.createParallelGroup(Alignment.LEADING)
						.addComponent(lblAxusteFino)
						.addGroup(gl_panelTuning.createSequentialGroup()
							.addGap(12)
							.addGroup(gl_panelTuning.createParallelGroup(Alignment.LEADING)
								.addComponent(comboBox_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNota)
								.addComponent(lblOitava_1)
								.addComponent(comboBox_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblCents)
								.addComponent(spinner_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(391, Short.MAX_VALUE))
		);
		gl_panelTuning.setVerticalGroup(
			gl_panelTuning.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelTuning.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelTuning.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelTuning.createSequentialGroup()
							.addComponent(lblAxusteFino)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblNota)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(comboBox_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lblOitava_1)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(comboBox_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lblCents)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(spinner_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(separator_8, GroupLayout.PREFERRED_SIZE, 368, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panelTuning.createSequentialGroup()
							.addComponent(lblFrecuencia)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panelTuning.createParallelGroup(Alignment.BASELINE)
								.addComponent(spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblHz))
							.addGap(18)
							.addComponent(lblModo)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(comboBox_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(168, Short.MAX_VALUE))
		);
		panelTuning.setLayout(gl_panelTuning);
		
		JPanel panelSensit = new JPanel();
		tabbedPane.addTab("Sensibilidade", null, panelSensit, null);
		
		JLabel lblPresinDoFol = new JLabel("Presión do fol");
		
		JSlider slider_1 = new JSlider();
		GroupLayout gl_panelSensit = new GroupLayout(panelSensit);
		gl_panelSensit.setHorizontalGroup(
			gl_panelSensit.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelSensit.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelSensit.createParallelGroup(Alignment.LEADING)
						.addComponent(lblPresinDoFol)
						.addComponent(slider_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(385, Short.MAX_VALUE))
		);
		gl_panelSensit.setVerticalGroup(
			gl_panelSensit.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelSensit.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblPresinDoFol)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(slider_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(499, Short.MAX_VALUE))
		);
		panelSensit.setLayout(gl_panelSensit);
		
		JPanel panelFinger = new JPanel();
		tabbedPane.addTab("Dixitacións", null, panelFinger, null);
		
		JLabel lblNota_1 = new JLabel("Nota");
		
		JComboBox comboBox_5 = new JComboBox();
		
		JLabel lblOitava_2 = new JLabel("Oitava");
		
		JComboBox comboBox_6 = new JComboBox();
		
		JLabel lblDixitacin_1 = new JLabel("Dixitación");
		
		JComboBox comboBox_7 = new JComboBox();
		
		JButton btnNova = new JButton("Nova");
		
		JButton btnEliminar = new JButton("Eliminar");
		
		JSeparator separator_9 = new JSeparator();
		separator_9.setOrientation(SwingConstants.VERTICAL);
		
		JLabel lblImaxeDoPunteiro = new JLabel("Situación dos sensores");
		
		JSeparator separator_10 = new JSeparator();
		separator_10.setOrientation(SwingConstants.VERTICAL);
		
		JLabel lblSensores = new JLabel("Sensores");
		
		JCheckBox checkBox = new JCheckBox("1");
		
		JCheckBox checkBox_1 = new JCheckBox("2");
		
		JCheckBox checkBox_2 = new JCheckBox("3");
		
		JCheckBox checkBox_3 = new JCheckBox("4");
		
		JCheckBox checkBox_4 = new JCheckBox("5");
		
		JCheckBox checkBox_5 = new JCheckBox("6");
		
		JLabel lblManEsquerda = new JLabel("Man esquerda");
		
		JLabel lblManDereita = new JLabel("Man dereita");
		
		JCheckBox checkBox_6 = new JCheckBox("7");
		
		JCheckBox checkBox_7 = new JCheckBox("8");
		
		JCheckBox checkBox_8 = new JCheckBox("9");
		GroupLayout gl_panelFinger = new GroupLayout(panelFinger);
		gl_panelFinger.setHorizontalGroup(
			gl_panelFinger.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelFinger.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelFinger.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNota_1)
						.addComponent(comboBox_5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblOitava_2)
						.addComponent(comboBox_6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblDixitacin_1)
						.addComponent(comboBox_7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNova)
						.addComponent(btnEliminar))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator_9, GroupLayout.PREFERRED_SIZE, 11, GroupLayout.PREFERRED_SIZE)
					.addGap(76)
					.addComponent(lblImaxeDoPunteiro)
					.addPreferredGap(ComponentPlacement.RELATED, 78, Short.MAX_VALUE)
					.addComponent(separator_10, GroupLayout.PREFERRED_SIZE, 11, GroupLayout.PREFERRED_SIZE)
					.addGroup(gl_panelFinger.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelFinger.createSequentialGroup()
							.addGap(33)
							.addGroup(gl_panelFinger.createParallelGroup(Alignment.LEADING)
								.addComponent(checkBox)
								.addGroup(gl_panelFinger.createSequentialGroup()
									.addGap(21)
									.addGroup(gl_panelFinger.createParallelGroup(Alignment.LEADING)
										.addComponent(checkBox_2)
										.addComponent(checkBox_1)
										.addComponent(checkBox_3)))
								.addComponent(lblManEsquerda)
								.addComponent(lblManDereita)
								.addGroup(gl_panelFinger.createSequentialGroup()
									.addGap(21)
									.addComponent(checkBox_4))
								.addComponent(checkBox_5)
								.addGroup(gl_panelFinger.createSequentialGroup()
									.addGap(21)
									.addGroup(gl_panelFinger.createParallelGroup(Alignment.LEADING)
										.addComponent(checkBox_7)
										.addComponent(checkBox_6)
										.addComponent(checkBox_8)))))
						.addGroup(gl_panelFinger.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblSensores)))
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
									.addComponent(lblSensores)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblManEsquerda)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(checkBox)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(checkBox_1)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(checkBox_2)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(checkBox_3)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblManDereita)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(checkBox_4)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(checkBox_5)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(checkBox_6)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(checkBox_7)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(checkBox_8))
								.addComponent(separator_9, GroupLayout.PREFERRED_SIZE, 368, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panelFinger.createSequentialGroup()
									.addComponent(lblNota_1)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(comboBox_5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(lblOitava_2)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(comboBox_6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(lblDixitacin_1)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(comboBox_7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(btnNova)
									.addGap(18)
									.addComponent(btnEliminar))
								.addComponent(separator_10, GroupLayout.PREFERRED_SIZE, 368, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_panelFinger.createSequentialGroup()
							.addGap(172)
							.addComponent(lblImaxeDoPunteiro)))
					.addContainerGap(168, Short.MAX_VALUE))
		);
		panelFinger.setLayout(gl_panelFinger);
		contentPane.setLayout(gl_contentPane);
	}
}
