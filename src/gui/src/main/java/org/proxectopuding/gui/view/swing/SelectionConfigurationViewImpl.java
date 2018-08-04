package org.proxectopuding.gui.view.swing;

import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.util.Dictionary;
import java.util.Hashtable;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ChangeListener;

import org.proxectopuding.gui.controller.SelectionConfigurationController;
import org.proxectopuding.gui.model.utils.Notification;
import org.proxectopuding.gui.view.SelectionConfigurationView;

import com.google.inject.Inject;

public class SelectionConfigurationViewImpl extends ViewImpl implements SelectionConfigurationView {

	private final int MIN_VOLUME = 1;
	private final int MAX_VOLUME = 100;
	private final int FINGERING_TYPE_ABERTO = 0;
	private final int FINGERING_TYPE_PECHADO = 1;
	private final int FINGERING_TYPE_CUSTOM = 2;
	private final int DRONE_TYPE_BASS = 0;
	private final int DRONE_TYPE_TENOR = 1;
	private final int DRONE_TYPE_HIGH = 2;
	
	private final SelectionConfigurationController selectionConfigurationController;
	
	private int oldVolume = -1;
	
	@Inject
	public SelectionConfigurationViewImpl(
			SelectionConfigurationController selectionConfigurationController) {
		
		this.selectionConfigurationController = selectionConfigurationController;
	}
	
	public JPanel getSelectionPanel() {
		
		JPanel panelSelect = new JPanel();
		JLabel lblVolume = getVolumeLabel();
		
		JSlider sliderVolume = getVolumeSlider();
		
		JLabel lblTuningTone = getTuningToneLabel();
		JComboBox<String> comboBoxTuningTone = getTuningToneComboBox();
		
		JLabel lblTuningOctave = getTuningOctaveLabel();
		JComboBox<Integer> comboBoxTuningOctave = getTuningOctaveComboBox();
		
		JLabel lblSamples = getSamplesLabel();
		JComboBox<String> comboBoxSamples = getSamplesComboBox();
		
		JLabel lblFingeringTypes = getFingeringTypesLabel();
		JCheckBox chckbxAberto = getFingeringTypesAbertoCheckBox();
		JCheckBox chckbxPechado = getFingeringTypesPechadoCheckBox();
		JCheckBox chckbxCustom = getFingeringTypesCustomCheckBox();
		
		JLabel lblComplements = getComplementsLabel();
		
		JCheckBox chckbxBag = getComplementsBagCheckBox();
		
		JLabel lblDrones = getComplementsDronesLabel();
		JCheckBox chckbxBassDrone = getComplementsDronesBassDroneCheckBox();
		JCheckBox chckbxTenorDrone = getComplementsDronesTenorDroneCheckBox();
		JCheckBox chckbxHighDrone = getComplementsDronesHighDroneCheckBox();
		
		JSeparator separator1 = getVerticalSeparator();
		JSeparator separator2 = getVerticalSeparator();
		JSeparator separator3 = getVerticalSeparator();
		JSeparator separator4 = getVerticalSeparator();
		JSeparator separator5 = getVerticalSeparator();
		JSeparator separator6 = getVerticalSeparator();
		JSeparator separator7 = getVerticalSeparator();
		
		GroupLayout gl_panelSelect = getGroupLayout(panelSelect,
				lblVolume, sliderVolume,
				lblTuningTone, comboBoxTuningTone,
				lblTuningOctave, comboBoxTuningOctave,
				lblSamples, comboBoxSamples, 
				lblFingeringTypes, chckbxAberto, chckbxPechado, chckbxCustom,
				lblComplements, chckbxBag,
				lblDrones, chckbxBassDrone, chckbxTenorDrone, chckbxHighDrone,
				separator1, separator2, separator3, separator4, separator5,
				separator6, separator7);
		panelSelect.setLayout(gl_panelSelect);
				
		return panelSelect;
	}
	
	private JLabel getVolumeLabel() {
		
		JLabel lblVolume = new JLabel();
		
		String text = selectionConfigurationController.getVolumeLabel();
		lblVolume.setText(text);
		
		return lblVolume;
	}
	
	private JSlider getVolumeSlider() {
		
		JSlider sliderVolume = new JSlider();
		
		sliderVolume.setMinimum(MIN_VOLUME);
		sliderVolume.setMaximum(MAX_VOLUME);
		Dictionary<Integer,JLabel> labels = new Hashtable<Integer,JLabel>();
		labels.put(MIN_VOLUME, new JLabel(Integer.toString(MIN_VOLUME)));
		labels.put(MAX_VOLUME, new JLabel(Integer.toString(MAX_VOLUME)));
		sliderVolume.setLabelTable(labels);
		sliderVolume.setPaintLabels(true);
		int volume = selectionConfigurationController.getVolume();
		sliderVolume.setValue(volume);
		
		// On selection
		ChangeListener changeListener = event -> {
			
			int newVolume = sliderVolume.getValue();
			
			// Set current volume label
			if (oldVolume != -1 &&
					oldVolume != sliderVolume.getMinimum() &&
					oldVolume != sliderVolume.getMaximum()) {
				
				labels.remove(oldVolume);
			}
			labels.put(newVolume, new JLabel(Integer.toString(newVolume)));
			sliderVolume.setLabelTable(labels);
			oldVolume = newVolume;

			// Set new device volume
			if (!sliderVolume.getValueIsAdjusting()) {
				selectionConfigurationController.onVolumeSelected(newVolume);
			}
		};
		sliderVolume.addChangeListener(changeListener);
		
		// On chanter selected
		PropertyChangeListener propertyChangeListener = event -> {
			
			int newVolume = selectionConfigurationController.getVolume();
			sliderVolume.setValue(newVolume);
		};
		selectionConfigurationController.subscribe(
				Notification.CHANTER_SELECTED, propertyChangeListener);
		
		return sliderVolume;
	}
	
	private JLabel getTuningToneLabel() {
		
		JLabel lblTuningTone = new JLabel();
		
		String text = selectionConfigurationController.getTuningToneLabel();
		lblTuningTone.setText(text);
		
		return lblTuningTone;
	}
	
	private JComboBox<String> getTuningToneComboBox() {
		
		JComboBox<String> comboBoxTuningTone = new JComboBox<String>();
		
		String[] tuningTones =
				selectionConfigurationController.getTuningTones();
		ComboBoxModel<String> tuningToneModel =
				new DefaultComboBoxModel<String>(tuningTones);
		comboBoxTuningTone.setModel(tuningToneModel);
		String tuningTone = selectionConfigurationController.getTuningTone();
		comboBoxTuningTone.setSelectedItem(tuningTone);
		
		// On selection
		ActionListener actionListener = event -> {
			
			String newTuningTone = 
					(String) comboBoxTuningTone.getSelectedItem();
			selectionConfigurationController.onTuningToneSelected(newTuningTone);
		};
		comboBoxTuningTone.addActionListener(actionListener);
		
		// On chanter selected
		PropertyChangeListener propertyChangeListener = event -> {
			
			String newTuningTone = selectionConfigurationController.getTuningTone();
			comboBoxTuningTone.setSelectedItem(newTuningTone);
		};
		selectionConfigurationController.subscribe(
				Notification.CHANTER_SELECTED, propertyChangeListener);
		
		return comboBoxTuningTone;
	}
	
	private JLabel getTuningOctaveLabel() {
		
		JLabel lblTuningOctave = new JLabel();
		
		String text = selectionConfigurationController.getTuningOctaveLabel();
		lblTuningOctave.setText(text);
		
		return lblTuningOctave;
	}
	
	private JComboBox<Integer> getTuningOctaveComboBox() {
		
		JComboBox<Integer> comboBoxTuningOctave = new JComboBox<Integer>();
		
		Integer[] tuningOctaves =
				selectionConfigurationController.getTuningOctaves();
		ComboBoxModel<Integer> tuningOctaveModel =
				new DefaultComboBoxModel<Integer>(tuningOctaves);
		comboBoxTuningOctave.setModel(tuningOctaveModel);
		int tuningOctave = selectionConfigurationController.getTuningOctave();
		comboBoxTuningOctave.setSelectedItem(tuningOctave);
		
		// On selection
		ActionListener actionListener = event -> {
			
			Integer newTuningOctave = 
					(Integer) comboBoxTuningOctave.getSelectedItem();
			selectionConfigurationController.onTuningOctaveSelected(newTuningOctave);
		};
		comboBoxTuningOctave.addActionListener(actionListener);
		
		// On chanter selected
		PropertyChangeListener propertyChangeListener = event -> {
			
			Integer newTuningOctave =
					selectionConfigurationController.getTuningOctave();
			comboBoxTuningOctave.setSelectedItem(newTuningOctave);
		};
		selectionConfigurationController.subscribe(
				Notification.CHANTER_SELECTED, propertyChangeListener);
		
		return comboBoxTuningOctave;
	}
	
	private JLabel getSamplesLabel() {
		
		JLabel lblSamples = new JLabel();
		
		String text = selectionConfigurationController.getSamplesLabel();
		lblSamples.setText(text);
		
		return lblSamples;
	}
	
	private JComboBox<String> getSamplesComboBox() {
		
		JComboBox<String> comboBoxSamples = new JComboBox<String>();
		
		String[] samples = selectionConfigurationController.getSamples();
		ComboBoxModel<String> samplesModel =
				new DefaultComboBoxModel<String>(samples);
		comboBoxSamples.setModel(samplesModel);
		String sample = selectionConfigurationController.getSample();
		comboBoxSamples.setSelectedItem(sample);
		
		// On selection
		ActionListener actionListener = event -> {
			
			String newSample = (String) comboBoxSamples.getSelectedItem();
			selectionConfigurationController.onSampleSelected(newSample);
		};
		comboBoxSamples.addActionListener(actionListener);
		
		return comboBoxSamples;
	}
	
	private JLabel getFingeringTypesLabel() {
		
		JLabel lblFingeringTypes = new JLabel();
		
		String text = selectionConfigurationController.getFingeringTypesLabel();
		lblFingeringTypes.setText(text);
		
		return lblFingeringTypes;
	}
	
	private JCheckBox getFingeringTypesAbertoCheckBox() {
		
		JCheckBox chckbxAberto = new JCheckBox();
		
		String text = selectionConfigurationController.
				getFingeringTypesAbertoLabel();
		chckbxAberto.setText(text);
		
		boolean enabled = selectionConfigurationController
				.isFingeringTypeEnabled(FINGERING_TYPE_ABERTO);
		chckbxAberto.setSelected(enabled);
		
		// On selection
		ActionListener actionListener = event -> {
			
			boolean selected = chckbxAberto.isSelected();
			selectionConfigurationController
					.onFingeringTypeSelected(FINGERING_TYPE_ABERTO, selected);
		};
		chckbxAberto.addActionListener(actionListener);

		// On chanter selected
		PropertyChangeListener propertyChangeListener = event -> {
			
			boolean isEnabled = selectionConfigurationController
					.isFingeringTypeEnabled(FINGERING_TYPE_ABERTO);
			chckbxAberto.setSelected(isEnabled);
		};
		selectionConfigurationController.subscribe(
				Notification.CHANTER_SELECTED, propertyChangeListener);
		
		return chckbxAberto;
	}
	
	private JCheckBox getFingeringTypesPechadoCheckBox() {
		
		JCheckBox chckbxPechado = new JCheckBox();
		
		String text = selectionConfigurationController.
				getFingeringTypesPechadoLabel();
		chckbxPechado.setText(text);
		
		boolean enabled = selectionConfigurationController
				.isFingeringTypeEnabled(FINGERING_TYPE_PECHADO);
		chckbxPechado.setSelected(enabled);
		
		// On selection
		ActionListener actionListener = event -> {
			
			boolean selected = chckbxPechado.isSelected();
			selectionConfigurationController
					.onFingeringTypeSelected(FINGERING_TYPE_PECHADO, selected);
		};
		chckbxPechado.addActionListener(actionListener);

		// On chanter selected
		PropertyChangeListener propertyChangeListener = event -> {
			
			boolean isEnabled = selectionConfigurationController
					.isFingeringTypeEnabled(FINGERING_TYPE_PECHADO);
			chckbxPechado.setSelected(isEnabled);
		};
		selectionConfigurationController.subscribe(
				Notification.CHANTER_SELECTED, propertyChangeListener);
		
		return chckbxPechado;
	}
	
	private JCheckBox getFingeringTypesCustomCheckBox() {
		
		JCheckBox chckbxCustom = new JCheckBox();
		
		String text = selectionConfigurationController.
				getFingeringTypesCustomCheckLabel();
		chckbxCustom.setText(text);
		
		boolean enabled = selectionConfigurationController
				.isFingeringTypeEnabled(FINGERING_TYPE_CUSTOM);
		chckbxCustom.setSelected(enabled);
		
		// On selection
		ActionListener actionListener = event -> {
			
			boolean selected = chckbxCustom.isSelected();
			selectionConfigurationController
					.onFingeringTypeSelected(FINGERING_TYPE_CUSTOM, selected);
		};
		chckbxCustom.addActionListener(actionListener);

		// On chanter selected
		PropertyChangeListener propertyChangeListener = event -> {
			
			boolean isEnabled = selectionConfigurationController
					.isFingeringTypeEnabled(FINGERING_TYPE_CUSTOM);
			chckbxCustom.setSelected(isEnabled);
		};
		selectionConfigurationController.subscribe(
				Notification.CHANTER_SELECTED, propertyChangeListener);
		
		return chckbxCustom;
	}
	
	private JLabel getComplementsLabel() {
		
		JLabel lblComplements = new JLabel();
		
		String text = selectionConfigurationController.getComplementsLabel();
		lblComplements.setText(text);
		
		return lblComplements;
	}
	
	private JCheckBox getComplementsBagCheckBox() {
		
		JCheckBox chckbxBag = new JCheckBox();
		
		String text = selectionConfigurationController.getComplementsBagLabel();
		chckbxBag.setText(text);
		
		boolean enabled = selectionConfigurationController.isBagEnabled();
		chckbxBag.setSelected(enabled);
		
		// On selection
		ActionListener actionListener = event -> {
			
			boolean selected = chckbxBag.isSelected();
			selectionConfigurationController.onBagSelected(selected);
		};
		chckbxBag.addActionListener(actionListener);
		
		// On chanter selected
		PropertyChangeListener propertyChangeListener = event -> {
			
			boolean selected = selectionConfigurationController.isBagEnabled();
			chckbxBag.setSelected(selected);
		};
		selectionConfigurationController.subscribe(
				Notification.CHANTER_SELECTED, propertyChangeListener);
		
		return chckbxBag;
	}
	
	private JLabel getComplementsDronesLabel() {
		
		JLabel lblDrones = new JLabel();
		
		String text = selectionConfigurationController.
				getComplementsDronesLabel();
		lblDrones.setText(text);
		
		return lblDrones;
	}
	
	private JCheckBox getComplementsDronesBassDroneCheckBox() {
		
		JCheckBox chckbxBassDrone = new JCheckBox();
		
		String text = selectionConfigurationController.
				getComplementsDronesBassDroneLabel();
		chckbxBassDrone.setText(text);
		
		boolean enabled = selectionConfigurationController
				.isDroneEnabled(DRONE_TYPE_BASS);
		chckbxBassDrone.setSelected(enabled);
		
		// On selection
		ActionListener actionListener = event -> {
			
			boolean selected = chckbxBassDrone.isSelected();
			selectionConfigurationController
					.onDroneSelected(DRONE_TYPE_BASS, selected);
		};
		chckbxBassDrone.addActionListener(actionListener);
		
		// On chanter selected
		PropertyChangeListener propertyChangeListener = event -> {
			
			boolean selected = selectionConfigurationController
					.isDroneEnabled(DRONE_TYPE_BASS);
			chckbxBassDrone.setSelected(selected);
		};
		selectionConfigurationController.subscribe(
				Notification.CHANTER_SELECTED, propertyChangeListener);
		
		return chckbxBassDrone;
	}
	
	private JCheckBox getComplementsDronesTenorDroneCheckBox() {
		
		JCheckBox chckbxTenorDrone = new JCheckBox();
		
		String text = selectionConfigurationController.
				getComplementsDronesTenorDroneLabel();
		chckbxTenorDrone.setText(text);
		
		boolean enabled = selectionConfigurationController
				.isDroneEnabled(DRONE_TYPE_TENOR);
		chckbxTenorDrone.setSelected(enabled);
		
		// On selection
		ActionListener actionListener = event -> {
			
			boolean selected = chckbxTenorDrone.isSelected();
			selectionConfigurationController
					.onDroneSelected(DRONE_TYPE_TENOR, selected);
		};
		chckbxTenorDrone.addActionListener(actionListener);
		
		// On chanter selected
		PropertyChangeListener propertyChangeListener = event -> {
			
			boolean selected = selectionConfigurationController
					.isDroneEnabled(DRONE_TYPE_TENOR);
			chckbxTenorDrone.setSelected(selected);
		};
		selectionConfigurationController.subscribe(
				Notification.CHANTER_SELECTED, propertyChangeListener);
		
		return chckbxTenorDrone;
	}

	private JCheckBox getComplementsDronesHighDroneCheckBox() {
	
		JCheckBox chckbxHighDrone = new JCheckBox();
		
		String text = selectionConfigurationController.
				getComplementsDronesHighDroneLabel();
		chckbxHighDrone.setText(text);
		
		boolean enabled = selectionConfigurationController
				.isDroneEnabled(DRONE_TYPE_HIGH);
		chckbxHighDrone.setSelected(enabled);
		
		// On selection
		ActionListener actionListener = event -> {
			
			boolean selected = chckbxHighDrone.isSelected();
			selectionConfigurationController
					.onDroneSelected(DRONE_TYPE_HIGH, selected);
		};
		chckbxHighDrone.addActionListener(actionListener);
		
		// On chanter selected
		PropertyChangeListener propertyChangeListener = event -> {
			
			boolean selected = selectionConfigurationController
					.isDroneEnabled(DRONE_TYPE_HIGH);
			chckbxHighDrone.setSelected(selected);
		};
		selectionConfigurationController.subscribe(
				Notification.CHANTER_SELECTED, propertyChangeListener);
		
		return chckbxHighDrone;
	}
	
	private GroupLayout getGroupLayout(JPanel panelSelect,
			JLabel lblVolume, JSlider sliderVolume,
			JLabel lblTuningTone,
			JComboBox<String> comboBoxTuningTone,
			JLabel lblTuningOctave,
			JComboBox<Integer> comboBoxTuningOctave,
			JLabel lblSamples,
			JComboBox<String> comboBoxSamples,
			JLabel lblFingeringTypes,
			JCheckBox chckbxAberto,
			JCheckBox chckbxPechado,
			JCheckBox chckbxCustom,
			JLabel lblComplements,
			JCheckBox chckbxBag,
			JLabel lblDrones,
			JCheckBox chckbxBassDrone,
			JCheckBox chckbxTenorDrone,
			JCheckBox chckbxHighDrone,
			JSeparator separator1,
			JSeparator separator2,
			JSeparator separator3,
			JSeparator separator4,
			JSeparator separator5,
			JSeparator separator6,
			JSeparator separator7) {
		
		GroupLayout gl_panelSelect = new GroupLayout(panelSelect);
		
		gl_panelSelect.setHorizontalGroup(
				gl_panelSelect.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panelSelect.createSequentialGroup()
						.addGroup(gl_panelSelect.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panelSelect.createSequentialGroup()
								.addGap(65)
								.addComponent(separator1,
										GroupLayout.PREFERRED_SIZE, 1,
										GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_panelSelect.createSequentialGroup()
								.addContainerGap()
								.addGroup(gl_panelSelect.createParallelGroup(Alignment.LEADING)
									.addComponent(sliderVolume,
											GroupLayout.PREFERRED_SIZE, 90,
											GroupLayout.PREFERRED_SIZE)
									.addComponent(lblVolume))))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_panelSelect.createParallelGroup(Alignment.LEADING)
							.addComponent(separator2,
									GroupLayout.PREFERRED_SIZE, 11,
									GroupLayout.PREFERRED_SIZE)
							.addComponent(separator7,
									GroupLayout.PREFERRED_SIZE, 11,
									GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_panelSelect.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panelSelect.createSequentialGroup()
								.addGap(115)
								.addGroup(gl_panelSelect.createParallelGroup(Alignment.LEADING)
									.addComponent(separator4,
											GroupLayout.PREFERRED_SIZE, 1,
											GroupLayout.PREFERRED_SIZE)
									.addComponent(separator3,
											GroupLayout.PREFERRED_SIZE, 1,
											GroupLayout.PREFERRED_SIZE)))
							.addComponent(lblTuningTone)
							.addComponent(comboBoxTuningTone,
									GroupLayout.PREFERRED_SIZE,
									GroupLayout.DEFAULT_SIZE,
									GroupLayout.PREFERRED_SIZE)
							.addComponent(lblTuningOctave)
							.addComponent(comboBoxTuningOctave,
									GroupLayout.PREFERRED_SIZE,
									GroupLayout.DEFAULT_SIZE,
									GroupLayout.PREFERRED_SIZE)
							.addComponent(lblSamples)
							.addComponent(comboBoxSamples,
									GroupLayout.PREFERRED_SIZE,
									GroupLayout.DEFAULT_SIZE,
									GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(separator5,
								GroupLayout.PREFERRED_SIZE, 11,
								GroupLayout.PREFERRED_SIZE)
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
						.addComponent(separator6,
								GroupLayout.PREFERRED_SIZE, 11,
								GroupLayout.PREFERRED_SIZE)
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
							.addComponent(separator6,
									GroupLayout.PREFERRED_SIZE, 368,
									GroupLayout.PREFERRED_SIZE)
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
								.addComponent(comboBoxTuningTone,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addGap(18)
								.addComponent(lblTuningOctave)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(comboBoxTuningOctave,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addGap(18)
								.addComponent(lblSamples)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(comboBoxSamples,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_panelSelect.createSequentialGroup()
								.addComponent(lblVolume)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(sliderVolume,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addGap(48)
								.addComponent(separator1,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_panelSelect.createSequentialGroup()
								.addGroup(gl_panelSelect.createParallelGroup(Alignment.TRAILING, false)
									.addComponent(separator3,
											GroupLayout.PREFERRED_SIZE,
											GroupLayout.DEFAULT_SIZE,
											GroupLayout.PREFERRED_SIZE)
									.addComponent(separator5,
											Alignment.LEADING,
											GroupLayout.PREFERRED_SIZE, 368,
											Short.MAX_VALUE)
									.addGroup(Alignment.LEADING, gl_panelSelect.createSequentialGroup()
										.addComponent(separator2,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												ComponentPlacement.RELATED,
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addComponent(separator7,
												GroupLayout.PREFERRED_SIZE, 368,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)))
								.addGap(148)
								.addComponent(separator4,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
			
		return gl_panelSelect;
	}
	
}
