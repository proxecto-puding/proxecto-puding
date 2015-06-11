package main.model.entities;

import java.util.Set;
import java.util.TreeSet;

public class MidiServerConfiguration {
	
	private boolean useRealSamples;
	private int tuningFrequency;
	private boolean useNaturalTuningMode;
	private boolean useContinuousVibrato;
	private Set<PreciseTuning> preciseTunings;
	
	public MidiServerConfiguration() {
		preciseTunings = new TreeSet<PreciseTuning>();
	}
	
	public boolean useRealSamples() {
		return useRealSamples;
	}
	
	public void setUseRealSamples(boolean useRealSamples) {
		this.useRealSamples = useRealSamples;
	}
	
	public int getTuningFrequency() {
		return tuningFrequency;
	}
	
	public void setTuningFrequency(int tuningFrequency) {
		this.tuningFrequency = tuningFrequency;
	}
	
	public boolean useNaturalTuningMode() {
		return useNaturalTuningMode;
	}
	
	public void setUseNaturalTuningMode(boolean useNaturalTuningMode) {
		this.useNaturalTuningMode = useNaturalTuningMode;
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
