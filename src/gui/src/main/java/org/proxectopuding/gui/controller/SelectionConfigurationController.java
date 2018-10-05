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

package org.proxectopuding.gui.controller;

import java.util.ArrayList;
import java.util.List;

import org.proxectopuding.gui.model.entities.BagpipeDevice;
import org.proxectopuding.gui.model.services.ConfigurationApplicationService;
import org.proxectopuding.gui.model.services.DeviceManagerService;
import org.proxectopuding.gui.model.services.I18nService;
import org.proxectopuding.gui.model.services.NotificationService;

import com.google.inject.Inject;

public class SelectionConfigurationController extends Controller {
	
	private final DeviceManagerService deviceManagerService;
	private final ConfigurationApplicationService confAppService;
	
	@Inject
	public SelectionConfigurationController(I18nService i18nService,
			DeviceManagerService deviceManagerService,
			ConfigurationApplicationService confAppService,
			NotificationService notificationService) {
		
		super(i18nService, notificationService);
		
		this.deviceManagerService = deviceManagerService;
		this.confAppService = confAppService;
	}
	
	public String getVolumeLabel() {
		return getI18nService().getTranslation("selectionConfiguration.volume.label");
	}
	
	public int getVolume() {
		
		int volume = -1;
		
		BagpipeDevice selectedDevice = 
				deviceManagerService.getSelectedBagpipeDevice();
		if (selectedDevice != null) {
			String productId = selectedDevice.getProductId();
			volume = deviceManagerService.getVolume(productId);
		}
		
		return volume;
	}
	
	public void onVolumeSelected(int volume) {
		
		BagpipeDevice selectedDevice = 
				deviceManagerService.getSelectedBagpipeDevice();
		if (selectedDevice != null) {
			String productId = selectedDevice.getProductId();
			deviceManagerService.setVolume(productId, volume);
		}
	}
	
	public String getTuningToneLabel() {
		return getI18nService().getTranslation("selectionConfiguration.tuningTone.label");
	}
	
	public String[] getTuningTones() {
		
		String[] tuningTones = {};
		
		List<String> list = confAppService.getTuningTones();
		tuningTones = list.toArray(new String[list.size()]);
		
		return tuningTones;
	}
	
	public String getTuningTone() {
		
		String tuningTone = null;
		
		BagpipeDevice selectedDevice = 
				deviceManagerService.getSelectedBagpipeDevice();
		if (selectedDevice != null) {
			String productId = selectedDevice.getProductId();
			int tone = deviceManagerService.getTuningTone(productId);
			tuningTone = confAppService.getTuningTone(tone);
		}
		
		if (tuningTone == null) {
			tuningTone = confAppService.getDefaultTuningTone();
		}
		
		return tuningTone;
	}
	
	public void onTuningToneSelected(String tuningTone) {
		
		BagpipeDevice selectedDevice = 
				deviceManagerService.getSelectedBagpipeDevice();
		if (selectedDevice != null) {
			String productId = selectedDevice.getProductId();
			int tone = confAppService.getTuningTone(tuningTone);
			deviceManagerService.setTuningTone(productId, tone);
		}
	}
	
	public String getTuningOctaveLabel() {
		return getI18nService().getTranslation("selectionConfiguration.tuningOctave.label");
	}
	
	public Integer[] getTuningOctaves() {
		
		Integer[] tuningOctaves = {};
		
		List<Integer> list = confAppService.getTuningOctaves();
		tuningOctaves = list.toArray(new Integer[list.size()]);
		
		return tuningOctaves;
	}
	
	public int getTuningOctave() {
	
		int tuningOctave = Integer.MIN_VALUE;
		
		BagpipeDevice selectedDevice = 
				deviceManagerService.getSelectedBagpipeDevice();
		if (selectedDevice != null) {
			String productId = selectedDevice.getProductId();
			try {
				tuningOctave = deviceManagerService.getTuningOctave(productId);
			} catch(IllegalArgumentException e) {
				// Skip.
			}
		}
		
		if (tuningOctave == Integer.MIN_VALUE) {
			tuningOctave = confAppService.getDefaultTuningOctave();
		}
		
		return tuningOctave;
	}
	
	public void onTuningOctaveSelected(int tuningOctave) {
		
		BagpipeDevice selectedDevice = 
				deviceManagerService.getSelectedBagpipeDevice();
		if (selectedDevice != null) {
			String productId = selectedDevice.getProductId();
			deviceManagerService.setTuningOctave(productId, tuningOctave);
		}
	}
	
	public String getSamplesLabel() {
		return getI18nService().getTranslation("selectionConfiguration.samples.label");
	}
	
	public String[] getSamples() {
		
		String[] samples = {};
		
		List<String> list = confAppService.getSamples();
		samples = list.toArray(new String[list.size()]);
		
		return samples;
	}
	
	public String getSample() {
		return confAppService.getSample();
	}
	
	public void onSampleSelected(String sample) {
		confAppService.setSample(sample);
	}
	
	public String getFingeringTypesLabel() {
		return getI18nService().getTranslation("selectionConfiguration.fingeringTypes.label");
	}
	
	public String getFingeringTypesAbertoLabel() {
		return getI18nService().getTranslation("selectionConfiguration.fingeringTypes.aberto.checkbox");
	}
		
	public String getFingeringTypesPechadoLabel() {
		return getI18nService().getTranslation("selectionConfiguration.fingeringTypes.pechado.checkbox");
	}
	
	public String getFingeringTypesCustomCheckLabel() {
		return getI18nService().getTranslation("selectionConfiguration.fingeringTypes.custom.checkbox");
	}
	
	public boolean isFingeringTypeEnabled(int fingeringType) {
		
		boolean isEnabled = false;
		
		List<Boolean> fingeringTypes = new ArrayList<Boolean>();
		BagpipeDevice selectedDevice = 
				deviceManagerService.getSelectedBagpipeDevice();
		if (selectedDevice != null) {
			String productId = selectedDevice.getProductId();
			fingeringTypes = deviceManagerService.
					getFingeringTypesEnabled(productId);
		}
		
		if (fingeringTypes.isEmpty()) {
			fingeringTypes = confAppService.getDefaultFingeringTypesEnabled();
		}
		
		isEnabled = fingeringTypes.get(fingeringType);
		
		return isEnabled;
	}
	
	public void onFingeringTypeSelected(int fingeringType,
			boolean selected) {
		
		BagpipeDevice selectedDevice = 
				deviceManagerService.getSelectedBagpipeDevice();
		if (selectedDevice != null) {
			String productId = selectedDevice.getProductId();
			List<Boolean> fingeringTypesEnabled = deviceManagerService.
					getFingeringTypesEnabled(productId);
			fingeringTypesEnabled.set(fingeringType, selected);
			deviceManagerService.setFingeringTypesEnabled(
					productId, fingeringTypesEnabled);
		}
	}
	
	public String getComplementsLabel() {
		return getI18nService().getTranslation("selectionConfiguration.complements.label");
	}
	
	public String getComplementsBagLabel() {
		return getI18nService().getTranslation("selectionConfiguration.complements.bag.checkbox");
	}
		
	public boolean isBagEnabled() {
		
		Boolean isBagEnabled = null;
		
		BagpipeDevice selectedDevice = 
				deviceManagerService.getSelectedBagpipeDevice();
		if (selectedDevice != null) {
			String productId = selectedDevice.getProductId();
			isBagEnabled = deviceManagerService.isBagEnabled(productId);
		}
		
		if (isBagEnabled == null) {
			isBagEnabled = confAppService.isDefaultBagEnabled();
		}
		
		return isBagEnabled;
	}
	
	public void onBagSelected(boolean selected) {
		
		BagpipeDevice selectedDevice = 
				deviceManagerService.getSelectedBagpipeDevice();
		if (selectedDevice != null) {
			String productId = selectedDevice.getProductId();
			deviceManagerService.setBagEnabled(productId, selected);
		}
	}
	
	public String getComplementsDronesLabel() {
		return getI18nService().getTranslation("selectionConfiguration.complements.drones.label");
	}
	
	public String getComplementsDronesBassDroneLabel() {
		return getI18nService().getTranslation("selectionConfiguration.complements.drones.bassDrone.checkbox");
	}
	
	public String getComplementsDronesTenorDroneLabel() {
		return getI18nService().getTranslation("selectionConfiguration.complements.drones.tenorDrone.checkbox");
	}
	
	public String getComplementsDronesHighDroneLabel() {
		return getI18nService().getTranslation("selectionConfiguration.complements.drones.highDrone.checkbox");
	}
	
	public boolean isDroneEnabled(int drone) {
		
		boolean isEnabled = false;
		
		List<Boolean> drones = new ArrayList<Boolean>();
		BagpipeDevice selectedDevice = 
				deviceManagerService.getSelectedBagpipeDevice();
		if (selectedDevice != null) {
			String productId = selectedDevice.getProductId();
			drones = deviceManagerService.getDronesEnabled(productId);
		}
		
		if (drones.isEmpty()) {
			drones = confAppService.getDefaultDronesEnabled();
		}
		
		isEnabled = drones.get(drone);
		
		return isEnabled;
	}
	
	public void onDroneSelected(int drone, boolean selected) {
		
		BagpipeDevice selectedDevice = 
				deviceManagerService.getSelectedBagpipeDevice();
		if (selectedDevice != null) {
			String productId = selectedDevice.getProductId();
			List<Boolean> drones =
					deviceManagerService.getDronesEnabled(productId);
			drones.set(drone, selected);
			deviceManagerService.setDronesEnabled(productId, drones);
		}
	}
}
