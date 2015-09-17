package main.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import main.model.services.BrowserService;
import main.model.services.impl.BrowserServiceImpl;

public class MenuBarController {
	
	private BrowserService browserService = new BrowserServiceImpl();
	
	public ActionListener getActionListenerForExitMenuItem() {
		
		ActionListener actionListener = new ActionListener() {
			
			public void actionPerformed(ActionEvent event) {
				
				System.exit(0);
			}
		};
		
		return actionListener;
	}
	
	public ActionListener getActionListenerForUserManualMenuItem() {
		
		ActionListener actionListener = new ActionListener() {
			
			public void actionPerformed(ActionEvent event) {
				
				browserService.openUserManualUrl();
			}
		};
		
		return actionListener;
	}
	
	public ActionListener getActionListenerForConfAppApiMenuItem() {
		
		ActionListener actionListener = new ActionListener() {
			
			public void actionPerformed(ActionEvent event) {
				
				browserService.openConfAppApiUrl();
			}
		};
		
		return actionListener;
	}
	
	public ActionListener getActionListenerForBagpipeApiMenuItem() {
		
		ActionListener actionListener = new ActionListener() {
			
			public void actionPerformed(ActionEvent event) {
				
				browserService.openBagpipeApiUrl();
			}
		};
		
		return actionListener;
	}
	
	public ActionListener getActionListenerForAboutMenuItem() {
		
		ActionListener actionListener = new ActionListener() {
			
			public void actionPerformed(ActionEvent event) {
				
				browserService.openAboutUrl();
			}
		};
		
		return actionListener;
	}
	
}
