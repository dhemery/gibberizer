package com.dhemery.gibberizer.widgets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import com.dhemery.gibberizer.core.Gibberizer;
import com.dhemery.gibberizer.ui.GibberizerWindow;

public class GibberizeButton extends JButton implements ActionListener {
	private static final long serialVersionUID = 1L;
	private final Gibberizer gibberizer;
	private final GibberizerWindow window;

	public GibberizeButton(String name, String toolTipText, Gibberizer gibberizer,
			GibberizerWindow window) {
		super(name);
		setName(name);
		setToolTipText(toolTipText);
		this.gibberizer = gibberizer;
		this.window = window;
		addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		window.setOutputText(gibberizer.gibberize(window.getInputText()));
	}
}
