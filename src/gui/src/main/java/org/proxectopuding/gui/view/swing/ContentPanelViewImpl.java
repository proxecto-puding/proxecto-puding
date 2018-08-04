package org.proxectopuding.gui.view.swing;

import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import org.proxectopuding.gui.controller.ContentPanelController;
import org.proxectopuding.gui.model.utils.Notification;
import org.proxectopuding.gui.view.ContentPanelView;

import com.google.inject.Inject;

public class ContentPanelViewImpl extends ViewImpl implements ContentPanelView {
	
	private final TabbedPaneViewImpl tabbedPaneView;
	
	private final ContentPanelController contentPanelController;
	
	@Inject
	public ContentPanelViewImpl(TabbedPaneViewImpl tabbedPaneView,
			ContentPanelController contentPanelController) {
		
		this.tabbedPaneView = tabbedPaneView;
		this.contentPanelController = contentPanelController;
	}
	
	public JPanel getContentPanel() {
		
		JPanel contentPanel = new JPanel();
		
		JTabbedPane tabbedPane = getTabbedPane();
		
		JButton btnApply = getApplyButton();
		JButton btnUndo = getUndoButton();
		JButton btnDefault = getDefaultButton();
		
		JSeparator separator = getHorizontalSeparator();
		
		GroupLayout gl_contentPane = getGroupLayout(contentPanel,
				tabbedPane, btnApply, btnUndo, btnDefault, separator);
		contentPanel.setLayout(gl_contentPane);
		
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		return contentPanel;
	}

	private JTabbedPane getTabbedPane() {
		 return tabbedPaneView.getTabbedPane();
	}
	
	private JButton getApplyButton() {
		
		JButton btnApply = new JButton();
		
		String text = contentPanelController.getApplyButtonLabel();
		btnApply.setText(text);
		
		// On selection
		ActionListener actionListener = event -> {
			contentPanelController.onApply();
		};
		btnApply.addActionListener(actionListener);
		
		// On chanter selected
		btnApply.setEnabled(false);
		PropertyChangeListener propertyChangeListener = event -> {
			btnApply.setEnabled(true);
		};
		contentPanelController.subscribe(Notification.CHANTER_SELECTED,
				propertyChangeListener);
		
		return btnApply;
	}
	
	private JButton getUndoButton() {
		
		JButton btnUndo = new JButton();
		
		String text = contentPanelController.getUndoButtonLabel();
		btnUndo.setText(text);
		
		// On selection
		ActionListener actionListener = event -> {
			contentPanelController.onUndo();
		};
		btnUndo.addActionListener(actionListener);
		
		btnUndo.setEnabled(false);
		PropertyChangeListener propertyChangeListener = event -> {
			// TODO Enable
		};
		contentPanelController.subscribe(Notification.CHANTER_SELECTED,
				propertyChangeListener);
		
		return btnUndo;
	}
	
	private JButton getDefaultButton() {
		
		JButton btnDefault = new JButton();
		
		String text = contentPanelController.getDefaultButtonLabel();
		btnDefault.setText(text);
		
		// On selection
		ActionListener actionListener = event -> {
			contentPanelController.onDefault();
		};
		btnDefault.addActionListener(actionListener);
		
		btnDefault.setEnabled(false);
		PropertyChangeListener propertyChangeListener = event -> {
			// TODO Enable
		};
		contentPanelController.subscribe(Notification.CHANTER_SELECTED,
				propertyChangeListener);
		
		return btnDefault;
	}
	
	private GroupLayout getGroupLayout(JPanel contentPanel,
			JTabbedPane tabbedPane, JButton btnApply, JButton btnUndo,
			JButton btnDefault, JSeparator separator) {
		
		GroupLayout gl_contentPane = new GroupLayout(contentPanel);
		
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(tabbedPane,
								Alignment.LEADING,
								GroupLayout.DEFAULT_SIZE, 604,
								Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnApply)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnUndo)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnDefault))
						.addComponent(separator,
								Alignment.LEADING,
								GroupLayout.DEFAULT_SIZE, 604,
								Short.MAX_VALUE))
					.addContainerGap())
		);
		
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(tabbedPane,
							GroupLayout.DEFAULT_SIZE, 333,
							Short.MAX_VALUE)
					.addGap(18)
					.addComponent(separator,
							GroupLayout.PREFERRED_SIZE, 4,
							GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnApply)
						.addComponent(btnUndo)
						.addComponent(btnDefault))
					.addContainerGap())
		);
		
		return gl_contentPane;
	}

}
