package main.model.entities;

import java.util.List;

public class MidiServerWindows extends MidiServer {
	
	static {
		// TODO Ensure this path is correct.
		path = "c:\\timidity\\timw32g.exe";
	}

	@Override
	public List<String> getCommand() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	};

}
