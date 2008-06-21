/**
 * 
 */
package com.dhemery.gibberizer.widgets;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import com.dhemery.gibberizer.ui.GibberizerWindow;

public class AboutMenuItem extends GibberizerMenuItem {
	private static final long serialVersionUID = 1L;
	private static final String aboutMessage = 
		"For more information about Gibberizer, " +
		"visit the Gibberizer Project Page at " +
		"http://code.google.com/p/gibberizer/";
	private final JTextArea messageBox;

	public AboutMenuItem(String name, GibberizerWindow window) {
		super(name, window);
		messageBox = new JTextArea(aboutMessage, 3, 38);
		messageBox.setEditable(false);
		messageBox.setLineWrap(true);
		messageBox.setWrapStyleWord(true);
	}

	@Override
	public void execute(GibberizerWindow window) {
		JOptionPane.showMessageDialog(window,
				messageBox,
			    getText(),
			    JOptionPane.PLAIN_MESSAGE);
	}
}