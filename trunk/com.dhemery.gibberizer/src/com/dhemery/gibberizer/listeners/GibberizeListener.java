package com.dhemery.gibberizer.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.dhemery.gibberizer.core.Gibberizer;
import com.dhemery.gibberizer.ui.GibberizerWindow;

public class GibberizeListener implements ActionListener {
	private final Gibberizer gibberizer;
	private final GibberizerWindow window;

	public GibberizeListener(Gibberizer gibberizer,
			GibberizerWindow window) {
		this.gibberizer = gibberizer;
		this.window = window;
	}

	public void actionPerformed(ActionEvent e) {
		window.setOutputText(gibberizer.gibberize(window.getInputText()));
	}
}
