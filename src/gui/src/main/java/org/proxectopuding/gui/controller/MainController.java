package org.proxectopuding.gui.controller;

import org.proxectopuding.gui.model.services.NotificationService;

import com.google.inject.Inject;

public class MainController extends Controller {
	
	@Inject
	public MainController(NotificationService notificationService) {
	
		super(notificationService);
	}
}
