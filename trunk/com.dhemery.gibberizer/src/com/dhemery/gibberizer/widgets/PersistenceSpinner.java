package com.dhemery.gibberizer.widgets;

import com.dhemery.gibberizer.core.Gibberizer;

public class PersistenceSpinner extends GibberizerSpinner {
	private static final long serialVersionUID = 1L;

	public PersistenceSpinner(String name, String toolTipText, Gibberizer gibberizer) {
		super(name, toolTipText, gibberizer, Gibberizer.MAX_PERSISTENCE, gibberizer.getPersistence());
	}

	@Override
	protected void setSelection(int selection) {
		getGibberizer().setPersistence(selection);
	}
}
