/*
 * Proxecto Puding
 * Copyright (C) 2011 Alejo Pacín <info@proxecto-puding.org>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.proxectopuding.gui.model.entities;

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
	
	public BagpipeConfiguration getConfigurationByType(String type) {
		
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
