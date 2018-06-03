package main.view.swing;

import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.LayoutStyle.ComponentPlacement;

import main.controller.FingeringConfigurationController;

public class FingeringConfigurationView extends View {

	private FingeringConfigurationController fingeringConfigurationController =
			new FingeringConfigurationController();
	
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
		
		JLabel lblChanterImage = getChangerImageLabel();
		
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
				getTranslationForCustomFingeringNoteLabel();
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
		
		ActionListener actionListener = fingeringConfigurationController.
				getActionListenerForCustomFingeringNoteComboBox();
		comboBoxCustomFingeringNote.addActionListener(actionListener);
		
		// TODO Test this because of the final modifier.
		PropertyChangeListener propertyChangeListener = 
				fingeringConfigurationController.
					getPropertyChangeListenerForCustomFingeringNoteComboBox(
							comboBoxCustomFingeringNote);
		comboBoxCustomFingeringNote.addPropertyChangeListener(
				propertyChangeListener);
		
		return comboBoxCustomFingeringNote;
	}
	
	private JLabel getCustomFingeringOctaveLabel() {
		
		JLabel lblCustomFingeringOctave = new JLabel();
		
		String text = fingeringConfigurationController.
				getTranslationForCustomFingeringOctaveLabel();
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
		
		ActionListener actionListener = fingeringConfigurationController.
				getActionListenerForCustomFingeringOctaveComboBox();
		comboBoxCustomFingeringOctave.addActionListener(actionListener);
		
		return comboBoxCustomFingeringOctave;
	}
	
	private JLabel getCustomFingeringNumberLabel() {
		
		JLabel lblCustomFingeringNumber = new JLabel();
		
		String text = fingeringConfigurationController.
				getTranslationForCustomFingeringNumberLabel();
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
		
		ActionListener actionListener = fingeringConfigurationController.
				getActionListenerForCustomFingeringNumberComboBox();
		comboBoxCustomFingeringNumber.addActionListener(actionListener);
		
		// TODO Test this because of the final modifier.
		PropertyChangeListener propertyChangeListener = 
				fingeringConfigurationController.
					getPropertyChangeListenerForCustomFingeringNumberComboBox(
							comboBoxCustomFingeringNumber);
		comboBoxCustomFingeringNumber.addPropertyChangeListener(
				propertyChangeListener);
		
		return comboBoxCustomFingeringNumber;
	}
	
	private JButton getCustomFingeringNewButton() {
		
		JButton btnCustomFingeringNew = new JButton();
		
		String text = fingeringConfigurationController.
				getTranslationForCustomFingeringNewButtonText();
		btnCustomFingeringNew.setText(text);
		
		ActionListener actionListener = fingeringConfigurationController.
				getActionListenerForCustomFingeringNewButton();
		btnCustomFingeringNew.addActionListener(actionListener);
		
		return btnCustomFingeringNew;
	}
	
	private JButton getCustomFingeringRemoveButton() {
		
		JButton btnCustomFingeringRemove = new JButton();
		
		String text = fingeringConfigurationController.
				getTranslationForCustomFingeringRemoveButton();
		btnCustomFingeringRemove.setText(text);
		
		ActionListener actionListener = fingeringConfigurationController.
				getActionListenerForCustomFingeringRemoveButton();
		btnCustomFingeringRemove.addActionListener(actionListener);
		
		return btnCustomFingeringRemove;
	}
	
	private JLabel getChangerImageLabel() {
		
		JLabel lblChanterImage = new JLabel();
		
		String text = fingeringConfigurationController.
				getTranslationForChanterImageLabel();
		lblChanterImage.setText(text);
		
		return lblChanterImage;
	}
	
	private JLabel getSensorsLabel() {
		
		JLabel lblSensors = new JLabel();
		
		String text = fingeringConfigurationController.
				getTranslationForSensorsLabel();
		lblSensors.setText(text);
		
		return lblSensors;
	}
	
	private JLabel getLeftHandSensorsLabel() {
		
		JLabel lblLeftHandSensors = new JLabel();
		
		String text = fingeringConfigurationController.
				getTranslationForLeftHandSensorsLabel();
		lblLeftHandSensors.setText(text);
		
		return lblLeftHandSensors;
	}
	
	private JLabel getRightHandSensorsLabel() {
		
		JLabel lblRightHandSensors = new JLabel();
		
		String text = fingeringConfigurationController.
				getTranslationForRightHandSensorsLabel();
		lblRightHandSensors.setText(text);
		
		return lblRightHandSensors;
	}
	
	private JCheckBox getSensorCheckBox(int sensor) {
		
		JCheckBox checkBoxSensor = new JCheckBox();
		
		checkBoxSensor.setText(String.valueOf(sensor+1));
		
		ActionListener actionListener = fingeringConfigurationController.
				getActionListenerForCustomFingeringSensorCheckBox(sensor);
		checkBoxSensor.addActionListener(actionListener);
		
		// TODO Test this because of the final modifier.
		PropertyChangeListener propertyChangeListener = 
				fingeringConfigurationController.
					getPropertyChangeListenerForCustomFingeringSensorCheckBox(
							sensor, checkBoxSensor);
		checkBoxSensor.addPropertyChangeListener(propertyChangeListener);
		
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
					.addGap(76)
					.addComponent(lblChanterImage)
					.addPreferredGap(ComponentPlacement.RELATED, 78,
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
							.addGap(172)
							.addComponent(lblChanterImage)))
					.addContainerGap(168, Short.MAX_VALUE))
		);
		
		return gl_panelFinger;
	}
	
}
