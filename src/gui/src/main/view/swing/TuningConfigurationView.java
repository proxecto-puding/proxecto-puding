package main.view.swing;

import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeListener;

import main.controller.TuningConfigurationController;

public class TuningConfigurationView extends View {
	
	private final int MIN_TUNING_FREQ = 1;
	private final int MAX_TUNING_FREQ = 1000;
	private final int STEP_TUNING_FREQ = 1;
	private final int MIN_PRECISE_TUNING_CENTS = -99;
	private final int MAX_PRECISE_TUNING_CENTS = 99;
	private final int STEP_PRECISE_TUNING_CENTS = 1;
	
	private TuningConfigurationController tuningConfigurationController =
			new TuningConfigurationController();
	
	public JPanel getTuningPanel() {
		
		JPanel panelTuning = new JPanel();
		
		JLabel lblTuningFrequency = getTuningFrequencyLabel();
		JSpinner spinnerTuningFrequency = getTuningFrequencySpinner();
		JLabel lblTuningHz = getTuningHzLabel();
		
		JLabel lblTuningMode = getTuningModeLabel();
		JComboBox<String> comboBoxTuningMode = getTuningModeComboBox();
		
		JSeparator separator = getVerticalSeparator();
		
		JLabel lblPreciseTuningSettings = getPreciseTuningSettingsLabel();
		
		JLabel lblPreciseTuningNote = getPreciseTuningNoteLabel();
		JComboBox<String> comboBoxPreciseTuningNote =
				getPreciseTuningNoteComboBox();
		
		JLabel lblPreciseTuningOctave = getPreciseTuningOctaveLabel();
		JComboBox<Integer> comboBoxPreciseTuningOctave =
				getPreciseTuningOctaveComboBox();
		
		JLabel lblPreciseTuningCents = getPreciseTuningCentsLabel();
		JSpinner spinnerPreciseTuningCents = getPreciseTuningCentsSpinner();
		
		GroupLayout gl_panelTuning = getGroupLayout(panelTuning,
				lblTuningFrequency, spinnerTuningFrequency, lblTuningHz,
				lblTuningMode, comboBoxTuningMode,
				lblPreciseTuningSettings,
				lblPreciseTuningNote, comboBoxPreciseTuningNote,
				lblPreciseTuningOctave, comboBoxPreciseTuningOctave, 
				lblPreciseTuningCents, spinnerPreciseTuningCents,
				separator);
		panelTuning.setLayout(gl_panelTuning);
		
		return panelTuning;
	}
	
	private JLabel getTuningFrequencyLabel() {
		
		JLabel lblTuningFrequency = new JLabel();
		
		String text = tuningConfigurationController.
				getTranslationForTuningFrequencyLabel();
		lblTuningFrequency.setText(text);
		
		return lblTuningFrequency;
	}
	
	private JSpinner getTuningFrequencySpinner() {
		
		JSpinner spinnerTuningFrequency = new JSpinner();
		
		int tuningFrequency =
				tuningConfigurationController.getTuningFrequency();
		SpinnerModel tuningFrequencyModel =
				new SpinnerNumberModel(tuningFrequency, MIN_TUNING_FREQ,
						MAX_TUNING_FREQ, STEP_TUNING_FREQ);
		spinnerTuningFrequency.setModel(tuningFrequencyModel);
		
		ChangeListener changeListener = tuningConfigurationController.
				getChangeListenerForTuningFrequencySpinner();
		spinnerTuningFrequency.addChangeListener(changeListener);
		
		return spinnerTuningFrequency;
	}
	
	private JLabel getTuningHzLabel() {
		
		JLabel lblTuningHz = new JLabel();
		
		String text = tuningConfigurationController.
				getTranslationForTuningHzLabel();
		lblTuningHz.setText(text);
		
		return lblTuningHz;
	}
	
	private JLabel getTuningModeLabel() {
		
		JLabel lblTuningMode = new JLabel();
		
		String text = tuningConfigurationController.
				getTranslationForTuningModeLabel();
		lblTuningMode.setText(text);
		
		return lblTuningMode;
	}
	
	private JComboBox<String> getTuningModeComboBox() {
		
		JComboBox<String> comboBoxTuningMode = new JComboBox<String>();
		
		String[] tuningModes =
				tuningConfigurationController.getTuningModes();
		ComboBoxModel<String> tuningModeModel =
				new DefaultComboBoxModel<String>(tuningModes);
		comboBoxTuningMode.setModel(tuningModeModel);
		String tuningMode = tuningConfigurationController.getTuningMode();
		comboBoxTuningMode.setSelectedItem(tuningMode);
		
		ActionListener actionListener = tuningConfigurationController.
				getActionListenerForTuningModeComboBox();
		comboBoxTuningMode.addActionListener(actionListener);
		
		return comboBoxTuningMode;
	}
	
	private JLabel getPreciseTuningSettingsLabel() {
		
		JLabel lblPreciseTuningSettings = new JLabel();
		
		String text = tuningConfigurationController.
				getTranslationForPreciseTuningSettingsLabel();
		lblPreciseTuningSettings.setText(text);
		
		return lblPreciseTuningSettings;
	}
	
	private JLabel getPreciseTuningNoteLabel() {
		
		JLabel lblPreciseTuningNote = new JLabel();
		
		String text = tuningConfigurationController.
				getTranslationForPreciseTuningNoteLabel();
		lblPreciseTuningNote.setText(text);
		
		return lblPreciseTuningNote;
	}
	
	private JComboBox<String> getPreciseTuningNoteComboBox() {
		
		JComboBox<String> comboBoxPreciseTuningNote = new JComboBox<String>();
		
		String[] preciseTuningNotes =
				tuningConfigurationController.getPreciseTuningNotes();
		ComboBoxModel<String> preciseTuningNoteModel =
				new DefaultComboBoxModel<String>(preciseTuningNotes);
		comboBoxPreciseTuningNote.setModel(preciseTuningNoteModel);
		String preciseTuningNote =
				tuningConfigurationController.getPreciseTuningNote();
		comboBoxPreciseTuningNote.setSelectedItem(preciseTuningNote);
		
		ActionListener actionListener = tuningConfigurationController.
				getActionListenerForPreciseTuningNoteComboBox();
		comboBoxPreciseTuningNote.addActionListener(actionListener);
		
		// TODO Test this because of the final modifier.
		PropertyChangeListener propertyChangeListener = 
				tuningConfigurationController.
						getPropertyChangeListenerForPreciseTuningNoteComboBox(
								comboBoxPreciseTuningNote);
		comboBoxPreciseTuningNote.addPropertyChangeListener(
				propertyChangeListener);
		
		return comboBoxPreciseTuningNote;
	}
	
	private JLabel getPreciseTuningOctaveLabel() {
		
		JLabel lblPreciseTuningOctave = new JLabel();
		
		String text = tuningConfigurationController.
				getTranslationForPreciseTuningOctaveLabel();
		lblPreciseTuningOctave.setText(text);
		
		return lblPreciseTuningOctave;
	}
	
	private JComboBox<Integer> getPreciseTuningOctaveComboBox() {
		
		JComboBox<Integer> comboBoxPreciseTuningOctave =
				new JComboBox<Integer>();
		
		Integer[] preciseTuningOctaves =
				tuningConfigurationController.getPreciseTuningOctaves();
		ComboBoxModel<Integer> preciseTuningOctaveModel =
				new DefaultComboBoxModel<Integer>(preciseTuningOctaves);
		comboBoxPreciseTuningOctave.setModel(preciseTuningOctaveModel);
		int preciseTuningOctave =
				tuningConfigurationController.getPreciseTuningOctave();
		comboBoxPreciseTuningOctave.setSelectedItem(preciseTuningOctave);
		
		ActionListener actionListener = tuningConfigurationController.
				getActionListenerForPreciseTuningOctaveComboBox();
		comboBoxPreciseTuningOctave.addActionListener(actionListener);
		
		return comboBoxPreciseTuningOctave;
	}
	
	private JLabel getPreciseTuningCentsLabel() {
		
		JLabel lblPreciseTuningCents = new JLabel();
		
		String text = tuningConfigurationController.
				getTranslationForPreciseTuningCentsLabel();
		lblPreciseTuningCents.setText(text);
		
		return lblPreciseTuningCents;
	}
	
	private JSpinner getPreciseTuningCentsSpinner() {
		
		JSpinner spinnerPreciseTuningCents = new JSpinner();
		
		int preciseTuningCents =
				tuningConfigurationController.getPreciseTuningCents();
		SpinnerModel preciseTuningCentsModel =
				new SpinnerNumberModel(preciseTuningCents,
						MIN_PRECISE_TUNING_CENTS,
						MAX_PRECISE_TUNING_CENTS,
						STEP_PRECISE_TUNING_CENTS);
		spinnerPreciseTuningCents.setModel(preciseTuningCentsModel);
		
		ChangeListener changeListener = tuningConfigurationController.
				getChangeListenerForPreciseTuningCentsSpinner();
		spinnerPreciseTuningCents.addChangeListener(changeListener);
		
		// TODO Test this because of the final modifier.
		PropertyChangeListener propertyChangeListener = 
				tuningConfigurationController.
						getPropertyChangeListenerForPreciseTuningCentsSpinner(
								spinnerPreciseTuningCents);
		spinnerPreciseTuningCents.addPropertyChangeListener(
				propertyChangeListener);
		
		return spinnerPreciseTuningCents;
	}
	
	private GroupLayout getGroupLayout(JPanel panelTuning,
			JLabel lblTuningFrequency,
			JSpinner spinnerTuningFrequency,
			JLabel lblTuningHz,
			JLabel lblTuningMode,
			JComboBox<String> comboBoxTuningMode,
			JLabel lblPreciseTuningSettings,
			JLabel lblPreciseTuningNote,
			JComboBox<String> comboBoxPreciseTuningNote,
			JLabel lblPreciseTuningOctave,
			JComboBox<Integer> comboBoxPreciseTuningOctave,
			JLabel lblPreciseTuningCents,
			JSpinner spinnerPreciseTuningCents,
			JSeparator separator) {
		
		GroupLayout gl_panelTuning = new GroupLayout(panelTuning);
		
		gl_panelTuning.setHorizontalGroup(
				gl_panelTuning.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panelTuning.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_panelTuning.createParallelGroup(Alignment.LEADING)
							.addComponent(lblTuningFrequency)
							.addGroup(gl_panelTuning.createSequentialGroup()
								.addComponent(spinnerTuningFrequency,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(lblTuningHz))
							.addComponent(lblTuningMode)
							.addComponent(comboBoxTuningMode,
									GroupLayout.PREFERRED_SIZE,
									GroupLayout.DEFAULT_SIZE,
									GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(separator,
								GroupLayout.PREFERRED_SIZE, 11,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_panelTuning.createParallelGroup(Alignment.LEADING)
							.addComponent(lblPreciseTuningSettings)
							.addGroup(gl_panelTuning.createSequentialGroup()
								.addGap(12)
								.addGroup(gl_panelTuning.createParallelGroup(Alignment.LEADING)
									.addComponent(comboBoxPreciseTuningNote,
											GroupLayout.PREFERRED_SIZE,
											GroupLayout.DEFAULT_SIZE,
											GroupLayout.PREFERRED_SIZE)
									.addComponent(lblPreciseTuningNote)
									.addComponent(lblPreciseTuningOctave)
									.addComponent(comboBoxPreciseTuningOctave,
											GroupLayout.PREFERRED_SIZE,
											GroupLayout.DEFAULT_SIZE,
											GroupLayout.PREFERRED_SIZE)
									.addComponent(lblPreciseTuningCents)
									.addComponent(spinnerPreciseTuningCents,
											GroupLayout.PREFERRED_SIZE,
											GroupLayout.DEFAULT_SIZE,
											GroupLayout.PREFERRED_SIZE))))
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
								.addComponent(comboBoxPreciseTuningNote,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addGap(18)
								.addComponent(lblPreciseTuningOctave)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(comboBoxPreciseTuningOctave,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addGap(18)
								.addComponent(lblPreciseTuningCents)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(spinnerPreciseTuningCents,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
							.addComponent(separator,
									GroupLayout.PREFERRED_SIZE, 368,
									GroupLayout.PREFERRED_SIZE)
							.addGroup(gl_panelTuning.createSequentialGroup()
								.addComponent(lblTuningFrequency)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_panelTuning.createParallelGroup(Alignment.BASELINE)
									.addComponent(spinnerTuningFrequency,
											GroupLayout.PREFERRED_SIZE,
											GroupLayout.DEFAULT_SIZE,
											GroupLayout.PREFERRED_SIZE)
									.addComponent(lblTuningHz))
								.addGap(18)
								.addComponent(lblTuningMode)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(comboBoxTuningMode,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)))
						.addContainerGap(168, Short.MAX_VALUE))
		);
		
		return gl_panelTuning;
	}

}
