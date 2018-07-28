package org.proxectopuding.gui.services.acceptance;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.proxectopuding.gui.model.entities.BagpipeConfiguration;
import org.proxectopuding.gui.model.entities.FingeringOffset;

import com.google.common.collect.ImmutableList;

public class DeviceManagerServiceAcceptanceTest {

	@Test
	public void findBagpipeDevices() {
		
	}
	
	@Test
	public void getBagpipeDeviceIds() {
		
	}
	
	@Test
	public void getSelectedBagpipeDevice() {
		
	}
	
	@Test
	public void setSelectedBagpipeDevice() {
		
		String productId = null;
	}
	
	@Test
	public void findBagpipeConfigurations() {
		
		String productId = null;
	}
	
	@Test
	public void findBagpipeConfigurationByProductIdAndType() {
		
		String productId = null;
		String type = null;
	}
	
	@Test
	public void findBagpipeConfigurationByPreInitializedConfig() {
		
		BagpipeConfiguration configuration = null;
	}
	
	@Test
	public void sendBagpipeConfiguration() {
		try {
			BagpipeConfiguration configuration = null;
		} catch (IllegalArgumentException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void getBagpipeConfiguration() {
		
		String productId = null;
		String type = null;
	}
	
	@Test
	public void getVolume() {
		try {
			String productId = null;
			
		} catch(IllegalArgumentException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void setVolume() {
		try {
			String productId = null;
			int volume = -1;
			
		} catch (IllegalArgumentException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void getTuningTone() {
		try {
			String productId = null;
			
		} catch (IllegalArgumentException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void setTuningTone() {
		try {
			String productId = null;
			int tuningTone = -1;
			
		} catch (IllegalArgumentException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void getTuningOctave() {
		try {
			String productId = null;
			
		} catch (IllegalArgumentException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void setTuningOctave() {
		try {
			String productId = null;
			int tuningOctave = -1;
			
		} catch (IllegalArgumentException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void getFingeringTypesEnabled() {
		try {
			String productId = null;
			
		} catch (IllegalArgumentException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void setFingeringTypesEnabled() {
		try {
			String productId = null;
			List<Boolean> fingeringTypes = ImmutableList.of();
			
		} catch (IllegalArgumentException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void isBagEnabled() {
		try {
			String productId = null;
			
		} catch (IllegalArgumentException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void setBagEnabled() {
		try {
			String productId = null;
			boolean bagEnabled = false;
			
		} catch (IllegalArgumentException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void getDronesEnabled() {
		try {
			String productId = null;
			
		} catch (IllegalArgumentException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void setDronesEnabled() {
		try {
			String productId = null;
			List<Boolean> drones = ImmutableList.of();
			
		} catch (IllegalArgumentException e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void getBagPressure() {
		try {
			String productId = null;
			
		} catch (IllegalArgumentException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void setBagPressure() {
		try {
			String productId = null;
			int bagPressure = -1;
			
		} catch (IllegalArgumentException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void getFingerings() {
		try {
			String productId = null;
			
		} catch (IllegalArgumentException e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void setFingerings() {
		
		String productId = null;
		List<FingeringOffset> fingerings = ImmutableList.of();
	}
}
