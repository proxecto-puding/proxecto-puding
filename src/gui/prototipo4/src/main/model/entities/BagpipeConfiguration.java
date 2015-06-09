package main.model.entities;

public class BagpipeConfiguration implements Comparable<BagpipeConfiguration> {
	
	private String productId;
	private String action;
	private String type;
	private ConfigurationData data;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ConfigurationData getData() {
		return data;
	}

	public void setData(ConfigurationData data) {
		this.data = data;
	}
	
	@Override
	public boolean equals(Object configuration) {
		BagpipeConfiguration config = (BagpipeConfiguration) configuration;
		return productId.equalsIgnoreCase(config.getProductId()) &&
				type.equalsIgnoreCase(config.getType());
	}

	@Override
	public int compareTo(BagpipeConfiguration configuration) {
		
		int compareByProductId = 
				productId.compareTo(configuration.getProductId());
		if (compareByProductId != 0) {
			return compareByProductId;
		}
				
		int compareByType = type.compareTo(configuration.getType());
		
		return compareByType;
	}
	
}
