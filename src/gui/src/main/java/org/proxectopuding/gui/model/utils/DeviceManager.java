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

package org.proxectopuding.gui.model.utils;

import java.util.Set;
import java.util.TreeSet;

import org.proxectopuding.gui.model.entities.BagpipeConfiguration;
import org.proxectopuding.gui.model.entities.BagpipeDevice;

public class DeviceManager {
	
	private Set<BagpipeDevice> devices;
	private BagpipeDevice selectedDevice;
	
	public DeviceManager() {
		
		devices = new TreeSet<BagpipeDevice>();
	}

	/**
	 * Get the list of registered devices.
	 * @return The list of registered devices.
	 */
	public Set<BagpipeDevice> getDevices() {
		return devices;
	}

	/**
	 * Set the list of registered devices.
	 * @param devices The list of devices to register.
	 */
	public void setDevices(Set<BagpipeDevice> devices) {
		this.devices = devices;
	}
	
	/**
	 * Get a registered device by id.
	 * @param productId The device id to search for.
	 * @return A registered device if found. Null otherwise.
	 */
	public BagpipeDevice getDevice(String productId) {
		
		BagpipeDevice device = null;
		
		for (BagpipeDevice d : devices) {
			if (d.getProductId().equalsIgnoreCase(productId)) {
				device = d;
				break;
			}
		}
		
		return device;
	}
	
	/**
	 * Add a default configured device to the list of registered devices.
	 * @param productId The device id to register.
	 */
	public void addDevice (String productId) {
		BagpipeDevice device = new BagpipeDevice();
		device.setProductId(productId);
		addDevice(device);
	}
	
	/**
	 * Add a provided device to the list of registered devices.
	 * @param device Device to register.
	 */
	public void addDevice(BagpipeDevice device) {
		devices.add(device);
	}
	
	/**
	 * Remove a device from the list of registered devices.
	 * @param productId Id of the device to remove from the list.
	 */
	public void removeDevice(String productId) {
		BagpipeDevice device = new BagpipeDevice();
		device.setProductId(productId);
		removeDevice(device);
	}
	
	/**
	 * Remove a device from the list of registered devices.
	 * @param device Device to remove from the list.
	 */
	public void removeDevice(BagpipeDevice device) {
		devices.remove(device);
	}
	
	/**
	 * Get the current device in use.
	 * @return A device.
	 */
	public BagpipeDevice getSelectedDevice() {
		return selectedDevice;
	}
	
	/**
	 * Set the current device in use.
	 * @param productId Device id.
	 */
	public void setSelectedDevice(String productId) {
		selectedDevice = getDevice(productId);
	}
	
	/**
	 * Get all the device configurations given a device id.
	 * @param productId Device id.
	 * @return The requested configurations.
	 */
	public Set<BagpipeConfiguration> getConfigurations(
			String productId) {
		
		Set<BagpipeConfiguration> configurations = null;
		
		BagpipeDevice device = getDevice(productId);
		if (device != null) {
			configurations = device.getConfigurations();
		}
		
		return configurations;
	}
	
	/**
	 * Get a device configuration by id and type.
	 * @param productId Device id.
	 * @param type Device type.
	 * @return The requested configuration if found. Null otherwise.
	 */
	public BagpipeConfiguration getConfiguration(
			String productId, String type) {
		
		BagpipeConfiguration configuration = null;
		
		BagpipeDevice device = getDevice(productId);
		if (device != null) {
			configuration = device.getConfigurationByType(type);
		}
		
		return configuration;
	}
	
	/**
	 * Add a configuration to a registered device.
	 * @param productId Device id.
	 * @param configuration Configuration to add.
	 */
	public void addConfiguration(String productId,
			BagpipeConfiguration configuration) {
		
		BagpipeDevice device = getDevice(productId);
		if (device != null) {
			device.addConfiguration(configuration);
		}
	}

}
