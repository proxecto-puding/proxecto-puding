package main.model.entities;

import java.util.Set;
import java.util.TreeSet;

public class BagpipeDevice implements Comparable<BagpipeDevice> {
	
	private String productId;
	private Set<BagpipeConfiguration> configurations;
	
	public BagpipeDevice() {
		configurations = new TreeSet<BagpipeConfiguration>();
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	public Set<BagpipeConfiguration> getConfigurations() {
		return configurations;
	}
	
	public void setConfigurations(Set<BagpipeConfiguration> configurations) {
		this.configurations = configurations;
	}
	
	public BagpipeConfiguration findConfigurationByType(String type) {
		
		BagpipeConfiguration configuration = null;
		
		for (BagpipeConfiguration c : configurations) {
			if (type.equalsIgnoreCase(c.getType())) {
				configuration = c;
				break;
			}
		}
		
		return configuration;
	}
	
	public void addConfiguration(BagpipeConfiguration configuration) {
		// If it exist a previous configuration for the same type,
		// it is removed.
		if (configurations.contains(configuration)) {
			configurations.remove(configuration);
		}
		configurations.add(configuration);
	}
	
	public void removeConfiguration(String type) {
		BagpipeConfiguration configuration = new BagpipeConfiguration();
		configuration.setProductId(productId);
		configuration.setType(type);
		removeConfiguration(configuration);
	}
	
	public void removeConfiguration(BagpipeConfiguration configuration) {
		configurations.remove(configuration);
	}
	
	@Override
	public boolean equals(Object device) {
		return productId.equalsIgnoreCase(
				((BagpipeDevice) device).getProductId());
	}

	@Override
	public int compareTo(BagpipeDevice device) {
		return productId.compareTo(device.getProductId());
	}

}
