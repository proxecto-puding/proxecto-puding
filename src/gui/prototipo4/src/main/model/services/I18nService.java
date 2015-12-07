package main.model.services;

public interface I18nService {

	/**
	 * Get the text for a provided id located to the OS language.
	 * @param translationId Key that identifies the text to be retrieved.
	 * @return A i18n text.
	 */
	public String getTranslation(String translationId);
	
}
