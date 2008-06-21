/**
 * 
 */
package com.dhemery.gibberizer.widgets;

import javax.swing.JOptionPane;

import com.dhemery.gibberizer.ui.GibberizerWindow;

public class AboutMenuItem extends GibberizerMenuItem {
	private static final long serialVersionUID = 1L;
	private static final String aboutMessage = 
		"<html>" +
			"Learn more about " +
			GibberizerWindow.APPLICATION_BASE_NAME +
			" at:" +
			"<ul>" +
				"<li>" +
					"The " +
					"<a href='http://code.google.com/p/gibberizer/'>" +
						GibberizerWindow.APPLICATION_BASE_NAME + " Web Site" +
					"</a>" +
				"</li>" +
				"<li>" +
					"The " +
					"<a href='http://code.google.com/p/gibberizer/wiki/UserGuide'>" +
						GibberizerWindow.APPLICATION_BASE_NAME + " User Guide" +
					"</a>" +
				"</li>" +
				"<li>" +
					"The " +
					"<a href='http://code.google.com/p/gibberizer/w/list'>" +
						GibberizerWindow.APPLICATION_BASE_NAME + " Wiki" +
					"</a>" +
				"</li>" +
			"</ul>" +
		"</html>";
	public AboutMenuItem(String name, GibberizerWindow window) {
		super(name, window);
	}

	@Override
	public void execute(GibberizerWindow window) {
		JOptionPane.showMessageDialog(window,
			    aboutMessage,
			    getText(),
			    JOptionPane.PLAIN_MESSAGE);
	}
}