package main.model;

public class BagpipeDevice implements Comparable<BagpipeDevice> {
	
	private String productId;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	@Override
	public boolean equals(Object device) {
		return this.productId.equals(
				((BagpipeDevice) device).getProductId());
	}

	@Override
	public int compareTo(BagpipeDevice device) {
		return this.productId.compareTo(device.getProductId());
	}

}
