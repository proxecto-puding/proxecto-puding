package main.model.services;

import java.util.List;

public interface ConfigurationApplicationService {
	
	public List<String> getReadingTones();
	
	public String getReadingTone();
	
	public void setReadingTone(String readingTone);

}
