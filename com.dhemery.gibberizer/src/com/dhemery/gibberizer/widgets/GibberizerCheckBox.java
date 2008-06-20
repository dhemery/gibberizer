package com.dhemery.gibberizer.widgets;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;

import com.dhemery.gibberizer.core.Gibberizer;

public abstract class GibberizerCheckBox extends JCheckBox implements ItemListener {
	private static final long serialVersionUID = 1L;
	private final Gibberizer gibberizer;

	protected abstract void execute();
	
	public Gibberizer getGibberizer() {
		return gibberizer;
	}

	public GibberizerCheckBox(String name, String toolTipText, Gibberizer gibberizer, boolean isSelected) {
		super(name, isSelected);
		setName(name);
		setToolTipText(toolTipText);
		this.gibberizer = gibberizer;
		addItemListener(this);
	}

	public void itemStateChanged(ItemEvent e) {
		execute();
	}
}