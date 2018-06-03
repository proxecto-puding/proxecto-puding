package main.model.entities;

public class Note {
	
	private String id;
	private int value;
	private String translationId;
	private String translationText;
	
	public Note() {
		
	}
	
	public Note(String id, int value, String translationId,
			String translationText) {
		
		this.id = id;
		this.value = value;
		this.translationId = translationId;
		this.translationText = translationText;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
		this.value = value;
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
