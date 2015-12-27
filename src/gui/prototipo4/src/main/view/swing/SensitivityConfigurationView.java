package main.view.swing;

import java.beans.PropertyChangeListener;
import java.util.Dictionary;
import java.util.Hashtable;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ChangeListener;

import main.controller.SensitivityConfigurationController;

public class SensitivityConfigurationView {
	
	private final int MIN_BAG_PRESSURE = 1;
	private final int MAX_BAG_PRESSURE = 100;

	private SensitivityConfigurationController sensitivityConfigurationController =
			new SensitivityConfigurationController();
	
	public JPanel getSensitivityPanel() {
		
		JPanel panelSensit = new JPanel();
		
		JLabel lblBagPressure = getBagPressureLabel();
		panelSensit.add(lblBagPressure);
		
		JSlider sliderBagPressure = getBagPressureSlider();
		panelSensit.add(sliderBagPressure);
		
		GroupLayout gl_panelSensit = getGroupLayout(panelSensit,
				lblBagPressure, sliderBagPressure);
		panelSensit.setLayout(gl_panelSensit);
				
		return panelSensit;
	}
	
	private JLabel getBagPressureLabel() {
		
		JLabel lblBagPressure = new JLabel();
		
		String text = sensitivityConfigurationController.
				getTranslationForBagPressureLabel();
		lblBagPressure.setText(text);
		
		return lblBagPressure;
	}
	
	private JSlider getBagPressureSlider() {
		
		JSlider sliderBagPressure = new JSlider();
		
		sliderBagPressure.setMinimum(MIN_BAG_PRESSURE);
		sliderBagPressure.setMaximum(MAX_BAG_PRESSURE);
		Dictionary<Integer,JLabel> labels = new Hashtable<Integer,JLabel>();
		labels.put(MIN_BAG_PRESSURE, new JLabel(Integer.toString(MIN_BAG_PRESSURE)));
		labels.put(MAX_BAG_PRESSURE, new JLabel(Integer.toString(MAX_BAG_PRESSURE)));
		sliderBagPressure.setLabelTable(labels);
		sliderBagPressure.setPaintLabels(true);
		int bagPressure = sensitivityConfigurationController.getBagPressure();
		sliderBagPressure.setValue(bagPressure);
		ChangeListener changeListener = sensitivityConfigurationController.
				getChangeListenerForBagPressureSlider();
		sliderBagPressure.addChangeListener(changeListener);
		// TODO Test this because of the final modifier.
		PropertyChangeListener propertyChangeListener = 
				sensitivityConfigurationController.
						getPropertyChangeListenerForBagPressureSlider(
								sliderBagPressure);
		sliderBagPressure.addPropertyChangeListener(propertyChangeListener);
		
		return sliderBagPressure;
	}
	
	private GroupLayout getGroupLayout(JPanel panelSensit,
			JLabel lblBagPressure,
			JSlider sliderBagPressure) {
		
		GroupLayout gl_panelSensit = new GroupLayout(panelSensit);
		
		gl_panelSensit.setHorizontalGroup(
				gl_panelSensit.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panelSensit.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_panelSensit.createParallelGroup(Alignment.LEADING)
							.addComponent(lblBagPressure)
							.addComponent(sliderBagPressure,
									GroupLayout.PREFERRED_SIZE,
									GroupLayout.DEFAULT_SIZE,
									GroupLayout.PREFERRED_SIZE))
						.addContainerGap(385, Short.MAX_VALUE))
		);
		
		gl_panelSensit.setVerticalGroup(
				gl_panelSensit.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panelSensit.createSequentialGroup()
						.addContainerGap()
						.addComponent(lblBagPressure)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(sliderBagPressure,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addContainerGap(499, Short.MAX_VALUE))
		);
		
		return gl_panelSensit;
	}
	
}
