package org.proxectopuding.gui.model.entities;

public enum BagpipeConfigurationAction {
	
	CURRENT("current"),
	NEW("new"),
	RESET("reset"),
	DEFAULT("default");
	
	private final String action;
	
	private BagpipeConfigurationAction(final String action) {
        this.action = action;
    }
	
	@Override
    public String toString() {
        return action;
    }

	public static BagpipeConfigurationAction from(String action) {
		
		return action != null ?
				BagpipeConfigurationAction.valueOf(action.toUpperCase()) : null;
	}
}
