package main.model.entities;

public class SensitivityConfiguration extends ConfigurationData {
	
	private int bagPressure;

	public int getBagPressure() {
		return bagPressure;
	}

	public void setBagPressure(int bagPressure) {
		
		// Bag pressure should be between 1 and 100.
		bagPressure = (bagPressure % 100) + 1;
		if (bagPressure <= 0) {
			bagPressure += 100;
		}
		
		this.bagPressure = bagPressure;
	}

}
