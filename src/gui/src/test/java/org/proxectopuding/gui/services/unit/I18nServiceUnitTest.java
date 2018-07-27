package org.proxectopuding.gui.services.unit;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.proxectopuding.gui.model.services.I18nService;
import org.proxectopuding.gui.model.services.impl.I18nServiceImpl;
import org.proxectopuding.gui.model.utils.I18nManager;

public class I18nServiceUnitTest {
	
	private static String TRANSLATION_ID = "TRANSLATION_ID";
	private static String TRANSLATION = "TRANSLATION";
	
	private I18nManager i18nManager = mock(I18nManager.class);
	private I18nService i18nService = new I18nServiceImpl(i18nManager);
	
	@Before
	public void before() {
		
		reset(i18nManager);
	}

	@Test
	public void getTranslation() {
		
		// Given
		when(i18nManager.getTranslation(TRANSLATION_ID)).thenReturn(TRANSLATION);
		
		// When
		String translation = i18nService.getTranslation(TRANSLATION_ID);
		
		// Then
		verify(i18nManager, times(1)).getTranslation(TRANSLATION_ID);
		assertEquals(TRANSLATION, translation);
	}
}
