package com.dhemery.gibberizer.listeners;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;

import com.dhemery.gibberizer.core.Gibberizer;

public abstract class CheckBoxListener implements ItemListener {

	private final Gibberizer gibberizer;

	protected abstract void execute(boolean isSelected);
	
	public Gibberizer getGibberizer() {
		return gibberizer;
	}
	
	public CheckBoxListener(Gibberizer gibberizer) {
		this.gibberizer = gibberizer;
	}

	public void itemStateChanged(ItemEvent e) {
		JCheckBox button = (JCheckBox) e.getItem();
		execute(button.isSelected());
	}
}