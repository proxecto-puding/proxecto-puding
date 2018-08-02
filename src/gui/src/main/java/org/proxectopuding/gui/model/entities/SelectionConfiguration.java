package org.proxectopuding.gui.model.entities;

import java.util.List;

public class SelectionConfiguration extends BagpipeConfiguration {
	
	private int volume;
	private List<Boolean> fingeringTypes;
	private boolean bagEnabled;
	private List<Boolean> dronesEnabled;
	
	public int getVolume() {
		return volume;
	}
	
	public void setVolume(int volume) {
		this.volume = volume;
	}
	
	public List<Boolean> getFingeringTypes() {
		return fingeringTypes;
	}
	
	public void setFingeringTypes(List<Boolean> fingeringTypes) {
		this.fingeringTypes = fingeringTypes;
	}
	
	public boolean isBagEnabled() {
		return bagEnabled;
	}
	
	public void setBagEnabled(boolean bagEnabled) {
		this.bagEnabled = bagEnabled;
	}
	
	public List<Boolean> getDronesEnabled() {
		return dronesEnabled;
	}
	
	public void setDronesEnabled(List<Boolean> dronesEnabled) {
		this.dronesEnabled = dronesEnabled;
	}

}
