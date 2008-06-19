package com.dhemery.gibberizer.listeners;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JRadioButton;

import com.dhemery.gibberizer.core.Gibberizer;

public abstract class RadioButtonListener implements ItemListener {

	private final Gibberizer gibberizer;

	protected abstract void execute(Object data);
	
	public Gibberizer getGibberizer() {
		return gibberizer;
	}
	
	public RadioButtonListener(Gibberizer gibberizer) {
		this.gibberizer = gibberizer;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		JRadioButton button = (JRadioButton) e.getItem();
		execute(button.getClientProperty("style"));
	}
}