package org.proxectopuding.gui.model.entities;

import java.util.List;

public class FingeringConfiguration extends BagpipeConfiguration {
	
	private List<FingeringOffset> fingerings;

	public List<FingeringOffset> getFingerings() {
		return fingerings;
	}

	public void setFingerings(List<FingeringOffset> fingerings) {
		this.fingerings = fingerings;
	}
	
}
