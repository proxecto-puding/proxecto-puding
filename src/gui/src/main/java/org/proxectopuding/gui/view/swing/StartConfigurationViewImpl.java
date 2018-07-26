package org.proxectopuding.gui.view.swing;

import java.awt.event.ActionListener;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.proxectopuding.gui.controller.StartConfigurationController;
import org.proxectopuding.gui.view.StartConfigurationView;

import com.google.inject.Inject;

public class StartConfigurationViewImpl extends ViewImpl implements StartConfigurationView {

	private final StartConfigurationController startConfigurationController;
	
	@Inject
	public StartConfigurationViewImpl(
			StartConfigurationController startConfigurationController) {
	
		this.startConfigurationController = startConfigurationController;
	}
	
	public JPanel getStartPanel() {
		
		JPanel panelStart = new JPanel();
		
		JLabel lblChanterSelection = getChanterSelectionLabel();
		panelStart.add(lblChanterSelection);
		JComboBox<String> comboBoxChanterSelection =
				getChanterSelectionComboBox();
		panelStart.add(comboBoxChanterSelection);
		
		JLabel lblReadingTone = getReadingToneLabel();
		panelStart.add(lblReadingTone);
		JComboBox<String> comboBoxReadingTone =
				getReadingToneComboBox();
		panelStart.add(comboBoxReadingTone);
		
		GroupLayout gl_panelStart = getGroupLayout(panelStart,
				lblChanterSelection, comboBoxChanterSelection, lblReadingTone,
				comboBoxReadingTone);
		panelStart.setLayout(gl_panelStart);
		
		return panelStart;
	}
	
	private JLabel getChanterSelectionLabel() {
		
		JLabel lblChanterSelection = new JLabel();
		
		String text = startConfigurationController.
				getTranslationForChanterSelectionLabel();
		lblChanterSelection.setText(text);
		
		return lblChanterSelection;
	}
	
	private JComboBox<String> getChanterSelectionComboBox() {
		
		JComboBox<String> comboBoxChanterSelection = new JComboBox<String>();
		
		String[] chanters = startConfigurationController.getChanters();
		ComboBoxModel<String> chanterSelectionModel =
				new DefaultComboBoxModel<String>(chanters);
		comboBoxChanterSelection.setModel(chanterSelectionModel);
		ActionListener actionListener = startConfigurationController.
				getActionListenerForChanterSelectionComboBox();
		comboBoxChanterSelection.addActionListener(actionListener);
		
		return comboBoxChanterSelection;
	}
	
	private JLabel getReadingToneLabel() {
		
		JLabel lblReadingTone = new JLabel();
		
		String text = startConfigurationController.
				getTranslationForReadingToneLabel();
		lblReadingTone.setText(text);
		
		return lblReadingTone;
	}
	
	private JComboBox<String> getReadingToneComboBox() {
		
		JComboBox<String> comboBoxReadingTone = new JComboBox<String>();
		
		String[] readingTones = startConfigurationController.getReadingTones();
		ComboBoxModel<String> readingToneModel =
				new DefaultComboBoxModel<String>(readingTones);
		comboBoxReadingTone.setModel(readingToneModel);
		ActionListener actionListener = 
				startConfigurationController.
				getActionListenerForReadingToneComboBox();
		comboBoxReadingTone.addActionListener(actionListener);
					
		return comboBoxReadingTone;
	}
	
	private GroupLayout getGroupLayout(JPanel panelStart,
			JLabel lblChanterSelection,
			JComboBox<String> comboBoxChanterSelection,
			JLabel lblReadingTone,
			JComboBox<String> comboBoxReadingTone) {
		
		GroupLayout gl_panelStart = new GroupLayout(panelStart);
		
		gl_panelStart.setHorizontalGroup(
			gl_panelStart.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelStart.createSequentialGroup()
					.addGroup(gl_panelStart.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelStart.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panelStart.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panelStart.createSequentialGroup()
									.addGap(12)
									.addComponent(comboBoxChanterSelection,
											GroupLayout.PREFERRED_SIZE,
											GroupLayout.DEFAULT_SIZE,
											GroupLayout.PREFERRED_SIZE))
								.addComponent(lblChanterSelection)))
						.addGroup(gl_panelStart.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panelStart.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panelStart.createSequentialGroup()
									.addGap(12)
									.addComponent(comboBoxReadingTone,
											GroupLayout.PREFERRED_SIZE,
											GroupLayout.DEFAULT_SIZE,
											GroupLayout.PREFERRED_SIZE))
								.addComponent(lblReadingTone))))
					.addContainerGap(291, Short.MAX_VALUE))
		);
		
		gl_panelStart.setVerticalGroup(
			gl_panelStart.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelStart.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblChanterSelection)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(comboBoxChanterSelection,
							GroupLayout.PREFERRED_SIZE,
							GroupLayout.DEFAULT_SIZE,
							GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lblReadingTone)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(comboBoxReadingTone,
							GroupLayout.PREFERRED_SIZE,
							GroupLayout.DEFAULT_SIZE,
							GroupLayout.PREFERRED_SIZE)
					.addContainerGap(428, Short.MAX_VALUE))
		);
		
		return gl_panelStart;
	}
	
}
