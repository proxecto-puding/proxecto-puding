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
					.addComponent(separator_2, GroupLayout.PREFERRED_SIZE, 11, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelSelect.createParallelGroup(Alignment.LEADING)
						.addComponent(lblTone)
						.addComponent(comboBoxTone, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(402, Short.MAX_VALUE))
		);
		gl_panelSelect.setVerticalGroup(
			gl_panelSelect.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelSelect.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelSelect.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelSelect.createSequentialGroup()
							.addComponent(lblTone)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(comboBoxTone, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(separator_2, GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
						.addGroup(gl_panelSelect.createSequentialGroup()
							.addComponent(lblVolume)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(slider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(48)
							.addComponent(separator_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		panelSelect.setLayout(gl_panelSelect);
		
		JPanel panelTuning = new JPanel();
		tabbedPane.addTab("Afinación", null, panelTuning, null);
		GroupLayout gl_panelTuning = new GroupLayout(panelTuning);
		gl_panelTuning.setHorizontalGroup(
			gl_panelTuning.createParallelGroup(Alignment.LEADING)
				.addGap(0, 599, Short.MAX_VALUE)
		);
		gl_panelTuning.setVerticalGroup(
			gl_panelTuning.createParallelGroup(Alignment.LEADING)
				.addGap(0, 304, Short.MAX_VALUE)
		);
		panelTuning.setLayout(gl_panelTuning);
		
		JPanel panelSensit = new JPanel();
		tabbedPane.addTab("Sensibilidade", null, panelSensit, null);
		GroupLayout gl_panelSensit = new GroupLayout(panelSensit);
		gl_panelSensit.setHorizontalGroup(
			gl_panelSensit.createParallelGroup(Alignment.LEADING)
				.addGap(0, 599, Short.MAX_VALUE)
		);
		gl_panelSensit.setVerticalGroup(
			gl_panelSensit.createParallelGroup(Alignment.LEADING)
				.addGap(0, 304, Short.MAX_VALUE)
		);
		panelSensit.setLayout(gl_panelSensit);
		
		JPanel panelFinger = new JPanel();
		tabbedPane.addTab("Dixitacións", null, panelFinger, null);
		GroupLayout gl_panelFinger = new GroupLayout(panelFinger);
		gl_panelFinger.setHorizontalGroup(
			gl_panelFinger.createParallelGroup(Alignment.LEADING)
				.addGap(0, 599, Short.MAX_VALUE)
		);
		gl_panelFinger.setVerticalGroup(
			gl_panelFinger.createParallelGroup(Alignment.LEADING)
				.addGap(0, 304, Short.MAX_VALUE)
		);
		panelFinger.setLayout(gl_panelFinger);
		contentPane.setLayout(gl_contentPane);
	}
}
