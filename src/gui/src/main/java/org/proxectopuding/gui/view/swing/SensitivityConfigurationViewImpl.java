package org.proxectopuding.gui.view.swing;

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

import org.proxectopuding.gui.controller.SensitivityConfigurationController;
import org.proxectopuding.gui.model.utils.Notification;
import org.proxectopuding.gui.view.SensitivityConfigurationView;

import com.google.inject.Inject;

public class SensitivityConfigurationViewImpl extends ViewImpl implements SensitivityConfigurationView {
	
	private final int MIN_BAG_PRESSURE = 1;
	private final int MAX_BAG_PRESSURE = 100;

	private final SensitivityConfigurationController sensitivityConfigurationController;
	
	private int oldBagPressure = -1;
	
	@Inject
	public SensitivityConfigurationViewImpl(
			SensitivityConfigurationController sensitivityConfigurationController) {
		
		this.sensitivityConfigurationController = sensitivityConfigurationController;
	}
	
	@Override
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
		
		String text = sensitivityConfigurationController.getBagPressureLabel();
		lblBagPressure.setText(text);
		
		return lblBagPressure;
	}
	
	private JSlider getBagPressureSlider() {
		
		JSlider sliderBagPressure = new JSlider();
		
		sliderBagPressure.setMinimum(MIN_BAG_PRESSURE);
		sliderBagPressure.setMaximum(MAX_BAG_PRESSURE);
		Dictionary<Integer,JLabel> labels = new Hashtable<Integer,JLabel>();
		labels.put(MIN_BAG_PRESSURE,
				new JLabel(Integer.toString(MIN_BAG_PRESSURE)));
		labels.put(MAX_BAG_PRESSURE,
				new JLabel(Integer.toString(MAX_BAG_PRESSURE)));
		sliderBagPressure.setLabelTable(labels);
		sliderBagPressure.setPaintLabels(true);
		int bagPressure = sensitivityConfigurationController.getBagPressure();
		sliderBagPressure.setValue(bagPressure);
		
		// On action
		ChangeListener changeListener = event -> {

			int newBagPressure = sliderBagPressure.getValue();
			
			// Set current bag pressure label
			if (oldBagPressure != -1 &&
					oldBagPressure != sliderBagPressure.getMinimum() &&
					oldBagPressure != sliderBagPressure.getMaximum()) {
				
				labels.remove(oldBagPressure);
			}
			labels.put(newBagPressure,
					new JLabel(Integer.toString(newBagPressure)));
			sliderBagPressure.setLabelTable(labels);
			oldBagPressure = newBagPressure;
			
			// Set new device bag pressure
			if (!sliderBagPressure.getValueIsAdjusting()) {
				sensitivityConfigurationController.onBagPressureSelected(
					newBagPressure);
			}
		};
		sliderBagPressure.addChangeListener(changeListener);
		
		// On chanter selected
		PropertyChangeListener propertyChangeListener = event -> {
			
			int newVolume = sensitivityConfigurationController.getBagPressure();
			sliderBagPressure.setValue(newVolume);
		};
		sensitivityConfigurationController.subscribe(
				Notification.CHANTER_SELECTED, propertyChangeListener);
		
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
