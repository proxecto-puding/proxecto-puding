package main.model;

public class BagpipeConfiguration {
	
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
	
}
