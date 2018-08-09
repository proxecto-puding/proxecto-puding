package org.proxectopuding.gui.controller;

import org.proxectopuding.gui.model.services.I18nService;
import org.proxectopuding.gui.model.services.NotificationService;

import com.google.inject.Inject;

public class MainController extends Controller {
	
	@Inject
	public MainController(I18nService i18nService,
			NotificationService notificationService) {
	
		super(i18nService, notificationService);
	}
}
