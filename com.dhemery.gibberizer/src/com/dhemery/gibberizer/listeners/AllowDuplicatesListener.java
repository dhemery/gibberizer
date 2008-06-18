package com.dhemery.gibberizer.listeners;

import com.dhemery.gibberizer.core.Gibberizer;

public class AllowDuplicatesListener extends CheckBoxListener {

	public AllowDuplicatesListener(Gibberizer gibberizer) {
		super(gibberizer);
	}

	@Override
	protected void execute(boolean isSelected) {
		getGibberizer().setAllowDuplicates(isSelected);
	}
}
