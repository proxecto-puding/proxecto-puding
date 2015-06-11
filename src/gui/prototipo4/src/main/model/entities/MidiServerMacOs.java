package main.model.entities;

import java.util.List;

public class MidiServerMacOs extends MidiServer {

	static {
		// TODO Assure this path is correct.
		path = "/usr/bin/timidity";
	};

	@Override
	public List<String> getCommand(MidiServerConfiguration configuration) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

}
