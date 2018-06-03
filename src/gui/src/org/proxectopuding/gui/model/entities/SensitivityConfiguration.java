package org.proxectopuding.gui.model.entities;

public class SensitivityConfiguration extends ConfigurationData {
	
	// From 1 to 100.
	private int bagPressure;

	public int getBagPressure() {
		return bagPressure;
	}

	public void setBagPressure(int bagPressure) {
		
		bagPressure = (bagPressure % 100) + 1;
		if (bagPressure <= 0) {
			bagPressure += 100;
		}
		
		this.bagPressure = bagPressure;
	}

}
