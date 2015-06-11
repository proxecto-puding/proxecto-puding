package main.model.services;

import main.model.entities.MidiServerConfiguration;

public interface MidiService {
	
	Process start(MidiServerConfiguration configuration, boolean restart);
	void stop();
}
