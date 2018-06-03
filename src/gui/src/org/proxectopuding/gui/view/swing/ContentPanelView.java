package org.proxectopuding.gui.view.swing;

import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import org.proxectopuding.gui.controller.ContentPanelController;

public class ContentPanelView extends View {
	
	private TabbedPaneView tabbedPaneView =
			new TabbedPaneView();
	
	private ContentPanelController contentPanelController =
			new ContentPanelController();
	
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
		
		String text = contentPanelController.
				getTranslationForApplyButtonText();
		btnApply.setText(text);
		
		ActionListener actionListener = contentPanelController.
				getActionListenerForApplyButton();
		btnApply.addActionListener(actionListener);
		
		return btnApply;
	}
	
	private JButton getUndoButton() {
		
		JButton btnUndo = new JButton();
		
		String text = contentPanelController.
				getTranslationForUndoButtonText();
		btnUndo.setText(text);
		
		ActionListener actionListener = contentPanelController.
				getActionListenerForUndoButton();
		btnUndo.addActionListener(actionListener);
		
		return btnUndo;
	}
	
	private JButton getDefaultButton() {
		
		JButton btnDefault = new JButton();
		
		String text = contentPanelController.
				getTranslationForDefaultButtonText();
		btnDefault.setText(text);
		
		ActionListener actionListener = contentPanelController.
				getActionListenerForDefaultButton();
		btnDefault.addActionListener(actionListener);
		
		return btnDefault;
	}
	
	private GroupLayout getGroupLayout(JPanel contentPanel,
			JTabbedPane tabbedPane, JButton btnApply, JButton btnUndo,
			JButton btnDefault, JSeparator separator) {
		
		// TODO Fix buttons vertical alignment.
		
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
						.addComponent(btnDefault)
						.addComponent(btnUndo)
						.addComponent(btnApply))
					.addContainerGap())
		);
		
		return gl_contentPane;
	}

}
