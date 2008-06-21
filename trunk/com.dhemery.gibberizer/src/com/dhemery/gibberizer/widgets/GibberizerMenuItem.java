/**
 * 
 */
package com.dhemery.gibberizer.widgets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import com.dhemery.gibberizer.ui.GibberizerWindow;

public abstract class GibberizerMenuItem extends JMenuItem implements ActionListener {
	private static final long serialVersionUID = 1L;
	private final GibberizerWindow window;

	public GibberizerMenuItem(String name, GibberizerWindow window) {
		super(name);
		this.window = window;
		addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		execute(window);
	}

	protected abstract void execute(GibberizerWindow window);
}