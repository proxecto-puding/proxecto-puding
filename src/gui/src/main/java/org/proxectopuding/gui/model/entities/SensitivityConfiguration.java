package org.proxectopuding.gui.model.entities;

public class SensitivityConfiguration extends ConfigurationData {
	
	// From 1 to 100.
	private int bagPressure;

	public int getBagPressure() {
		return bagPressure;
	}

	public void setBagPressure(int bagPressure) {
		
		int pressure = bagPressure % 100;
		pressure = pressure <= 0 ? 1 : pressure;
		
		this.bagPressure = bagPressure;
	}

}
