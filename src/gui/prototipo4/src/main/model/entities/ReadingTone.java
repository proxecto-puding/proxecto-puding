package main.model.entities;

public class ReadingTone {

	private String id;
	private String translationId;
	private String translationText;
	
	public ReadingTone() {
		
	}
	
	public ReadingTone(String id, String translationId,
			String translationText) {
		
		this.id = id;
		this.translationId = translationId;
		this.translationText = translationText;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTranslationId() {
		return translationId;
	}

	public void setTranslationId(String translationId) {
		this.translationId = translationId;
	}

	public String getTranslationText() {
		return translationText;
	}

	public void setTranslationText(String translationText) {
		this.translationText = translationText;
	}
	
}
