package org.proxectopuding.gui.model.entities;

import java.util.List;

public class MidiServerMacOs extends MidiServer {

	static {
		// TODO Ensure this path is correct.
		path = "/usr/bin/timidity";
	};

	@Override
	public List<String> getCommand() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

}
