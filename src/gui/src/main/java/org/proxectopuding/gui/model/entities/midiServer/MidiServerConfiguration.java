package org.proxectopuding.gui.model.entities.midiServer;

import java.util.Set;
import java.util.TreeSet;

import org.proxectopuding.gui.model.entities.PreciseTuning;

public class MidiServerConfiguration {
	
	private boolean useRealSamples;
	private int tuningTone;
	private int tuningOctave;
	private int tuningFrequency;
	private boolean usePureIntonationMode;
	private boolean useContinuousVibrato;
	private Set<PreciseTuning> preciseTunings;
	
	public MidiServerConfiguration() {
		
		useRealSamples = false;
		tuningTone = 0;
		tuningOctave = 4;
		tuningFrequency = 440;
		usePureIntonationMode = false;
		useContinuousVibrato = false;
		preciseTunings = new TreeSet<PreciseTuning>();
	}
	
	public boolean useRealSamples() {
		return useRealSamples;
	}
	
	public void setUseRealSamples(boolean useRealSamples) {
		this.useRealSamples = useRealSamples;
	}
	
	public int getTuningTone() {
		return tuningTone;
	}

	public void setTuningTone(int tuningTone) {
		this.tuningTone = tuningTone;
	}
	
	public int getTuningOctave() {
		return tuningOctave;
	}

	public void setTuningOctave(int tuningOctave) {
		this.tuningOctave = tuningOctave;
	}

	public int getTuningFrequency() {
		return tuningFrequency;
	}
	
	public void setTuningFrequency(int tuningFrequency) {
		this.tuningFrequency = tuningFrequency;
	}
	
	public boolean usePureIntonationMode() {
		return usePureIntonationMode;
	}
	
	public void setUsePureIntonationMode(boolean usePureIntonationMode) {
		this.usePureIntonationMode = usePureIntonationMode;
	}

	public Set<PreciseTuning> getPreciseTunings() {
		return preciseTunings;
	}
	
	public boolean useContinuousVibrato() {
		return useContinuousVibrato;
	}

	public void setUseContinuousVibrato(boolean useContinuousVibrato) {
		this.useContinuousVibrato = useContinuousVibrato;
	}

	public void setPreciseTunings(Set<PreciseTuning> preciseTunings) {
		this.preciseTunings = preciseTunings;
	}
	
	public void addPreciseTuning(PreciseTuning preciseTuning) {
		if (preciseTunings.contains(preciseTuning)) {
			preciseTunings.remove(preciseTuning);
		}
		preciseTunings.add(preciseTuning);
	}

	public void removePreciseTuning(PreciseTuning preciseTuning) {
		preciseTunings.remove(preciseTuning);
	}
}
