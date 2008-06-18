package com.dhemery.gibberizer.listeners;

import com.dhemery.gibberizer.core.Gibberizer;

public class PersistenceListener extends SpinnerListener {
	public PersistenceListener(Gibberizer gibberizer) {
		super(gibberizer);
	}

	@Override
	protected void execute(int selection) {
		getGibberizer().setPersistence(selection);
	}
}