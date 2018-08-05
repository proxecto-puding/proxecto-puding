package org.proxectopuding.gui.view.swing;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.proxectopuding.gui.controller.FingeringConfigurationController;
import org.proxectopuding.gui.model.utils.Notification;
import org.proxectopuding.gui.view.FingeringConfigurationView;

import com.google.inject.Inject;

public class FingeringConfigurationViewImpl extends ViewImpl implements FingeringConfigurationView {
	
	private static final Logger LOGGER = Logger.getLogger(FingeringConfigurationViewImpl.class.getName());
	
	private static final String SENSORS_IMAGE_ICON_PATH = "images/sensors.png";

	private final FingeringConfigurationController fingeringConfigurationController;
	
	@Inject
	public FingeringConfigurationViewImpl(
			FingeringConfigurationController fingeringConfigurationController) {
		
		this.fingeringConfigurationController = fingeringConfigurationController;
	}
	
	@Override
	public JPanel getFingeringPanel() {
		
		JPanel panelFinger = new JPanel();
		
		JLabel lblCustomFingeringNote = getCustomFingeringNoteLabel();
		JComboBox<String> comboBoxCustomFingeringNote =
				getCustomFingeringNoteComboBox();
		
		JLabel lblCustomFingeringOctave = getCustomFingeringOctaveLabel();
		JComboBox<Integer> comboBoxCustomFingeringOctave =
				getCustomFingeringOctaveComboBox();
		
		JLabel lblCustomFingeringNumber = getCustomFingeringNumberLabel();
		JComboBox<Integer> comboBoxCustomFingeringNumber =
				getCustomFingeringNumberComboBox();
		
		JButton btnCustomFingeringNew = getCustomFingeringNewButton();
		JButton btnCustomFingeringRemove = getCustomFingeringRemoveButton();
		
		JSeparator separator1 = getVerticalSeparator();
		
		JLabel lblChanterImage = getChanterImageLabel();
		
		JSeparator separator2 = getVerticalSeparator();
		
		JLabel lblSensors = getSensorsLabel();
		
		JLabel lblLeftHandSensors = getLeftHandSensorsLabel();
		JCheckBox checkBoxSensor1 = getSensorCheckBox(0);
		JCheckBox checkBoxSensor2 = getSensorCheckBox(1);
		JCheckBox checkBoxSensor3 = getSensorCheckBox(2);
		JCheckBox checkBoxSensor4 = getSensorCheckBox(3);
		
		JLabel lblRightHandSensors = getRightHandSensorsLabel();
		JCheckBox checkBoxSensor5 = getSensorCheckBox(4);
		JCheckBox checkBoxSensor6 = getSensorCheckBox(5);
		JCheckBox checkBoxSensor7 = getSensorCheckBox(6);
		JCheckBox checkBoxSensor8 = getSensorCheckBox(7);
		JCheckBox checkBoxSensor9 = getSensorCheckBox(8);
		
		GroupLayout gl_panelFinger = getGroupLayout(panelFinger,
				lblCustomFingeringNote, comboBoxCustomFingeringNote,
				lblCustomFingeringOctave, comboBoxCustomFingeringOctave,
				lblCustomFingeringNumber, comboBoxCustomFingeringNumber,
				btnCustomFingeringNew, btnCustomFingeringRemove,
				lblChanterImage,
				lblSensors,
				lblLeftHandSensors,
				checkBoxSensor1, checkBoxSensor2,
				checkBoxSensor3, checkBoxSensor4,
				lblRightHandSensors,
				checkBoxSensor5, checkBoxSensor6,
				checkBoxSensor7, checkBoxSensor8,
				checkBoxSensor9,
				separator1, separator2);
		
		panelFinger.setLayout(gl_panelFinger);
		
		return panelFinger;
	}
	
	private JLabel getCustomFingeringNoteLabel() {
		
		JLabel lblCustomFingeringNote = new JLabel();
		
		String text = fingeringConfigurationController.
				getCustomFingeringNoteLabel();
		lblCustomFingeringNote.setText(text);
		
		return lblCustomFingeringNote;
	}
	
	private JComboBox<String> getCustomFingeringNoteComboBox() {
		
		JComboBox<String> comboBoxCustomFingeringNote =
				new JComboBox<String>();
		
		String[] customFingeringNotes =
				fingeringConfigurationController.getCustomFingeringNotes();
		ComboBoxModel<String> customFingeringNoteModel =
				new DefaultComboBoxModel<String>(customFingeringNotes);
		comboBoxCustomFingeringNote.setModel(customFingeringNoteModel);
		String customFingeringNote =
				fingeringConfigurationController.getCustomFingeringNote();
		comboBoxCustomFingeringNote.setSelectedItem(customFingeringNote);
		
		// On selection
		ActionListener actionListener = event -> {
			
			String newCustomFingeringNote = 
					(String) comboBoxCustomFingeringNote.getSelectedItem();
			fingeringConfigurationController.onCustomFingeringNoteSelected(
					newCustomFingeringNote);
		};
		comboBoxCustomFingeringNote.addActionListener(actionListener);
		
		// On reading tone selected
		PropertyChangeListener propertyChangeListener = event -> {
			
			String[] newCustomFingeringNotes =
					fingeringConfigurationController.getCustomFingeringNotes();
			ComboBoxModel<String> newCustomFingeringNoteModel =
					new DefaultComboBoxModel<String>(newCustomFingeringNotes);
			comboBoxCustomFingeringNote.setModel(newCustomFingeringNoteModel);
			String newCustomFingeringNote =
					fingeringConfigurationController.getCustomFingeringNote();
			comboBoxCustomFingeringNote.setSelectedItem(newCustomFingeringNote);
		};
		fingeringConfigurationController.subscribe(
				Notification.READING_TONE_SELECTED, propertyChangeListener);
		
		return comboBoxCustomFingeringNote;
	}
	
	private JLabel getCustomFingeringOctaveLabel() {
		
		JLabel lblCustomFingeringOctave = new JLabel();
		
		String text = fingeringConfigurationController.
				getCustomFingeringOctaveLabel();
		lblCustomFingeringOctave.setText(text);
		
		return lblCustomFingeringOctave;
	}
	
	private JComboBox<Integer> getCustomFingeringOctaveComboBox() {
		
		JComboBox<Integer> comboBoxCustomFingeringOctave =
				new JComboBox<Integer>();
		
		Integer[] customFingeringOctaves =
				fingeringConfigurationController.getCustomFingeringOctaves();
		ComboBoxModel<Integer> customFingeringOctaveModel =
				new DefaultComboBoxModel<Integer>(customFingeringOctaves);
		comboBoxCustomFingeringOctave.setModel(customFingeringOctaveModel);
		int customFingeringOctave =
				fingeringConfigurationController.getCustomFingeringOctave();
		comboBoxCustomFingeringOctave.setSelectedItem(customFingeringOctave);
		
		// On selection
		ActionListener actionListener = event -> {
			
			Integer newCustomFingeringOctave = (Integer)
					comboBoxCustomFingeringOctave.getSelectedItem();
			fingeringConfigurationController.onCustomFingeringOctaveSelected(
					newCustomFingeringOctave);
		};
		comboBoxCustomFingeringOctave.addActionListener(actionListener);
		
		return comboBoxCustomFingeringOctave;
	}
	
	private JLabel getCustomFingeringNumberLabel() {
		
		JLabel lblCustomFingeringNumber = new JLabel();
		
		String text = fingeringConfigurationController.
				getCustomFingeringNumberLabel();
		lblCustomFingeringNumber.setText(text);
		
		return lblCustomFingeringNumber;
	}
	
	private JComboBox<Integer> getCustomFingeringNumberComboBox() {
		
		JComboBox<Integer> comboBoxCustomFingeringNumber =
				new JComboBox<Integer>();
		
		Integer[] customFingeringNumbers =
				fingeringConfigurationController.getCustomFingeringNumbers();
		ComboBoxModel<Integer> customFingeringNumberModel =
				new DefaultComboBoxModel<Integer>(customFingeringNumbers);
		comboBoxCustomFingeringNumber.setModel(customFingeringNumberModel);
		int customFingeringNumber =
				fingeringConfigurationController.getCustomFingeringNumber();
		comboBoxCustomFingeringNumber.setSelectedItem(customFingeringNumber);
		
		// On selection
		ActionListener actionListener = event -> {
			
			Integer newCustomFingeringNumber = (Integer)
					comboBoxCustomFingeringNumber.getSelectedItem();
			fingeringConfigurationController.onCustomFingeringNumberSelected(
					newCustomFingeringNumber);
		};
		comboBoxCustomFingeringNumber.addActionListener(actionListener);
		
		// On changer, fingering note or fingering octave selected
		// or on fingering number added or removed
		PropertyChangeListener propertyChangeListener = event -> {
			
			Integer[] newCustomFingeringNumbers =
					fingeringConfigurationController.getCustomFingeringNumbers();
			ComboBoxModel<Integer> newCustomFingeringNumberModel =
					new DefaultComboBoxModel<Integer>(newCustomFingeringNumbers);
			comboBoxCustomFingeringNumber.setModel(newCustomFingeringNumberModel);
			Integer newCustomFingeringNumber =
					fingeringConfigurationController.getCustomFingeringNumber();
			comboBoxCustomFingeringNumber.setSelectedItem(
					newCustomFingeringNumber);	
		};
		fingeringConfigurationController.subscribe(
				Notification.CHANTER_SELECTED, propertyChangeListener);
		fingeringConfigurationController.subscribe(
				Notification.FINGERING_NOTE_SELECTED, propertyChangeListener);
		fingeringConfigurationController.subscribe(
				Notification.FINGERING_OCTAVE_SELECTED, propertyChangeListener);
		fingeringConfigurationController.subscribe(
				Notification.FINGERING_NUMBER_ADDED, propertyChangeListener);
		fingeringConfigurationController.subscribe(
				Notification.FINGERING_NUMBER_REMOVED, propertyChangeListener);
		
		return comboBoxCustomFingeringNumber;
	}
	
	private JButton getCustomFingeringNewButton() {
		
		JButton btnCustomFingeringNew = new JButton();
		
		String text = fingeringConfigurationController.
				getCustomFingeringNewButtonLabel();
		btnCustomFingeringNew.setText(text);
		
		// On selection
		ActionListener actionListener = event -> {
			fingeringConfigurationController.onAddCustomFingering();
		};
		btnCustomFingeringNew.addActionListener(actionListener);
		
		return btnCustomFingeringNew;
	}
	
	private JButton getCustomFingeringRemoveButton() {
		
		JButton btnCustomFingeringRemove = new JButton();
		
		String text = fingeringConfigurationController.
				getCustomFingeringRemoveButtonLabel();
		btnCustomFingeringRemove.setText(text);
		
		// On selection
		ActionListener actionListener = event -> {
			fingeringConfigurationController.onRemoveCustomFingering();
		};
		btnCustomFingeringRemove.addActionListener(actionListener);
		
		return btnCustomFingeringRemove;
	}
	
	private JLabel getChanterImageLabel() {
		
		JLabel lblChanterImage = new JLabel();
		
		try {
	         BufferedImage image = ImageIO.read(
	        		 FingeringConfigurationViewImpl.class.getClassLoader().
	        		 		getResource(SENSORS_IMAGE_ICON_PATH));
	         ImageIcon icon = new ImageIcon(image);
	         lblChanterImage.setIcon(icon);
	      } catch (IOException e) {
	         LOGGER.log(Level.SEVERE, "Sensors image not found", e);
	      }
		
		return lblChanterImage;
	}
	
	private JLabel getSensorsLabel() {
		
		JLabel lblSensors = new JLabel();
		
		String text = fingeringConfigurationController.getSensorsLabel();
		lblSensors.setText(text);
		
		return lblSensors;
	}
	
	private JLabel getLeftHandSensorsLabel() {
		
		JLabel lblLeftHandSensors = new JLabel();
		
		String text = fingeringConfigurationController
				.getLeftHandSensorsLabel();
		lblLeftHandSensors.setText(text);
		
		return lblLeftHandSensors;
	}
	
	private JLabel getRightHandSensorsLabel() {
		
		JLabel lblRightHandSensors = new JLabel();
		
		String text = fingeringConfigurationController
				.getRightHandSensorsLabel();
		lblRightHandSensors.setText(text);
		
		return lblRightHandSensors;
	}
	
	private JCheckBox getSensorCheckBox(int sensor) {
		
		JCheckBox checkBoxSensor = new JCheckBox();
		
		checkBoxSensor.setText(String.valueOf(sensor+1));
		boolean isSelected = fingeringConfigurationController.
				isCustomFingeringSensorSelected(sensor);
		checkBoxSensor.setSelected(isSelected); 
		
		// On selection
		ActionListener actionListener = event -> {
			
			boolean selected = checkBoxSensor.isSelected();
			fingeringConfigurationController.onCustomFingeringSensorSelected(
					sensor, selected);
		};
		checkBoxSensor.addActionListener(actionListener);
		
		// On changer, fingering note or fingering octave selected
		// or on fingering number added or removed
		PropertyChangeListener propertyChangeListener = event -> {
			
			boolean selected = fingeringConfigurationController.
					isCustomFingeringSensorSelected(sensor);
			checkBoxSensor.setSelected(selected); 
		};
		fingeringConfigurationController.subscribe(
				Notification.CHANTER_SELECTED, propertyChangeListener);
		fingeringConfigurationController.subscribe(
				Notification.FINGERING_NOTE_SELECTED, propertyChangeListener);
		fingeringConfigurationController.subscribe(
				Notification.FINGERING_OCTAVE_SELECTED, propertyChangeListener);
		fingeringConfigurationController.subscribe(
				Notification.FINGERING_NUMBER_ADDED, propertyChangeListener);
		fingeringConfigurationController.subscribe(
				Notification.FINGERING_NUMBER_REMOVED, propertyChangeListener);
		
		return checkBoxSensor;
	}
	
	private GroupLayout getGroupLayout(JPanel panelFinger,
			JLabel lblCustomFingeringNote,
			JComboBox<String> comboBoxCustomFingeringNote,
			JLabel lblCustomFingeringOctave,
			JComboBox<Integer> comboBoxCustomFingeringOctave,
			JLabel lblCustomFingeringNumber,
			JComboBox<Integer> comboBoxCustomFingeringNumber,
			JButton btnCustomFingeringNew,
			JButton btnCustomFingeringRemove,
			JLabel lblChanterImage,
			JLabel lblSensors,
			JLabel lblLeftHandSensors,
			JCheckBox checkBoxSensor1,
			JCheckBox checkBoxSensor2,
			JCheckBox checkBoxSensor3,
			JCheckBox checkBoxSensor4,
			JLabel lblRightHandSensors,
			JCheckBox checkBoxSensor5,
			JCheckBox checkBoxSensor6,
			JCheckBox checkBoxSensor7,
			JCheckBox checkBoxSensor8,
			JCheckBox checkBoxSensor9,
			JSeparator separator1,
			JSeparator separator2) {
		
		GroupLayout gl_panelFinger = new GroupLayout(panelFinger);
		
		gl_panelFinger.setHorizontalGroup(
			gl_panelFinger.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelFinger.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelFinger.createParallelGroup(Alignment.LEADING)
						.addComponent(lblCustomFingeringNote)
						.addComponent(comboBoxCustomFingeringNote,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(lblCustomFingeringOctave)
						.addComponent(comboBoxCustomFingeringOctave,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(lblCustomFingeringNumber)
						.addComponent(comboBoxCustomFingeringNumber,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(btnCustomFingeringNew)
						.addComponent(btnCustomFingeringRemove))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator1,
							GroupLayout.PREFERRED_SIZE, 11,
							GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(lblChanterImage)
					.addPreferredGap(ComponentPlacement.RELATED, 10,
							Short.MAX_VALUE)
					.addComponent(separator2,
							GroupLayout.PREFERRED_SIZE, 11,
							GroupLayout.PREFERRED_SIZE)
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
								.addComponent(separator1,
										GroupLayout.PREFERRED_SIZE, 368,
										GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panelFinger.createSequentialGroup()
									.addComponent(lblCustomFingeringNote)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(comboBoxCustomFingeringNote,
											GroupLayout.PREFERRED_SIZE,
											GroupLayout.DEFAULT_SIZE,
											GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(lblCustomFingeringOctave)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(comboBoxCustomFingeringOctave,
											GroupLayout.PREFERRED_SIZE,
											GroupLayout.DEFAULT_SIZE,
											GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(lblCustomFingeringNumber)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(comboBoxCustomFingeringNumber,
											GroupLayout.PREFERRED_SIZE,
											GroupLayout.DEFAULT_SIZE,
											GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(btnCustomFingeringNew)
									.addGap(18)
									.addComponent(btnCustomFingeringRemove))
								.addComponent(separator2,
										GroupLayout.PREFERRED_SIZE, 368,
										GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_panelFinger.createSequentialGroup()
							.addGap(20)
							.addComponent(lblChanterImage)))
					.addContainerGap(168, Short.MAX_VALUE))
		);
		
		return gl_panelFinger;
	}
	
}
