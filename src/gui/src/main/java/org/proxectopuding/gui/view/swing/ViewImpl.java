package org.proxectopuding.gui.view.swing;

import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import org.proxectopuding.gui.view.View;

public class ViewImpl implements View {
	
	protected JSeparator getHorizontalSeparator() {
		return getSeparator(false);
	}
	
	protected JSeparator getVerticalSeparator() {
		return getSeparator(true);
	}
	
	private JSeparator getSeparator(boolean verticalOrientation) {
		
		JSeparator separator = new JSeparator();
		if (verticalOrientation) {
			separator.setOrientation(SwingConstants.VERTICAL);
		}
				
		return separator;
	}
	
}
