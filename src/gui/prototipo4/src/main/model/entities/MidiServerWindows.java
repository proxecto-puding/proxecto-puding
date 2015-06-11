package main.model.entities;

import java.util.List;

public class MidiServerWindows extends MidiServer {
	
	static {
		// TODO Assure this path is correct.
		path = "c:\\timidity\\timw32g.exe";
	}

	@Override
	public List<String> getCommand(MidiServerConfiguration configuration) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	};

}
