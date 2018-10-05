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

public class BagpipeConfiguration implements Comparable<BagpipeConfiguration> {
	
	private String productId;
	private String action;
	private String type;

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
