package com.dhemery.gibberizer.widgets;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JRadioButton;

import com.dhemery.gibberizer.core.Gibberizer;

/**
 * @author Owner
 *
 */
public abstract class GibberizerRadioButton extends JRadioButton implements ItemListener {
	private static final long serialVersionUID = 1L;
	private final Gibberizer gibberizer;

	public GibberizerRadioButton(String name, String toolTipText, Gibberizer gibberizer, boolean isSelected) {
		super(name, isSelected);
		setName(name);
		setToolTipText(toolTipText);
		this.gibberizer = gibberizer;
		addItemListener(this);
	}
	
	public Gibberizer getGibberizer() {
		return gibberizer;
	}
	
	public void itemStateChanged(ItemEvent e) {
		if(isSelected()) select();
	}

	protected abstract void select();
}