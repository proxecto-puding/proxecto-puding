package main.model.entities;

import java.util.ArrayList;

public class FingeringConfiguration extends ConfigurationData {
	
	private ArrayList<FingeringOffset> fingerings;

	public ArrayList<FingeringOffset> getFingerings() {
		return fingerings;
	}

	public void setFingerings(ArrayList<FingeringOffset> fingerings) {
		this.fingerings = fingerings;
	}
	
}
