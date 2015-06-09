package main.model.entities;

import java.util.ArrayList;

public class SelectionConfiguration extends ConfigurationData {
	
	private int volume;
	private ArrayList<Boolean> fingeringTypes;
	private boolean bagEnabled;
	private ArrayList<Boolean> dronesEnabled;
	
	public int getVolume() {
		return volume;
	}
	
	public void setVolume(int volume) {
		this.volume = volume;
	}
	
	public ArrayList<Boolean> getFingeringTypes() {
		return fingeringTypes;
	}
	
	public void setFingeringTypes(ArrayList<Boolean> fingeringTypes) {
		this.fingeringTypes = fingeringTypes;
	}
	
	public boolean isBagEnabled() {
		return bagEnabled;
	}
	
	public void setBagEnabled(boolean bagEnabled) {
		this.bagEnabled = bagEnabled;
	}
	
	public ArrayList<Boolean> getDronesEnabled() {
		return dronesEnabled;
	}
	
	public void setDronesEnabled(ArrayList<Boolean> dronesEnabled) {
		this.dronesEnabled = dronesEnabled;
	}

}
