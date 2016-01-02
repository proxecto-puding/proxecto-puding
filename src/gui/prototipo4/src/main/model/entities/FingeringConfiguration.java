package main.model.entities;

import java.util.List;

public class FingeringConfiguration extends ConfigurationData {
	
	private List<FingeringOffset> fingerings;

	public List<FingeringOffset> getFingerings() {
		return fingerings;
	}

	public void setFingerings(List<FingeringOffset> fingerings) {
		this.fingerings = fingerings;
	}
	
}
