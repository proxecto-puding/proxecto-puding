package main.model.entities;

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

}
