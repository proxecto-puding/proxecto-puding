package main.view.swing;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.LayoutStyle.ComponentPlacement;

import main.controller.TuningConfigurationController;

public class TuningConfigurationView extends View {
	
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
		
		// TODO Implement.
		
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
		
		// TODO Implement.
		
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
		
		// TODO Implement.
		
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
		
		// TODO Implement.
		
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
		
		// TODO Implement.
		
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
