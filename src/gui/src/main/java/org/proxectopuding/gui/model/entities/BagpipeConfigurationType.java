package org.proxectopuding.gui.model.entities;

public enum BagpipeConfigurationType {
	
	START("start"),
	SELECT("select"),
	TUNING("tuning"),
	SENSIT("sensit"),
	FINGER("finger");
	
	private final String type;
	
	private BagpipeConfigurationType(final String type) {
        this.type = type;
    }
	
	@Override
    public String toString() {
        return type;
    }

	public static BagpipeConfigurationType from(String type) {
		
		return type != null ?
				BagpipeConfigurationType.valueOf(type.toUpperCase()) : null;
	}
}
