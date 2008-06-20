package com.dhemery.gibberizer.listeners;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.dhemery.gibberizer.core.Gibberizer;

public abstract class SpinnerListener implements ChangeListener {

	private final Gibberizer gibberizer;

	protected abstract void execute(int selection);

	public SpinnerListener(Gibberizer gibberizer) {
		this.gibberizer = gibberizer;
	}

	public void stateChanged(ChangeEvent e) {
		JSpinner spinner = (JSpinner)e.getSource();
		SpinnerNumberModel model = (SpinnerNumberModel)spinner.getModel();
		execute(model.getNumber().intValue());
	}

	public Gibberizer getGibberizer() {
		return gibberizer;
	}
}