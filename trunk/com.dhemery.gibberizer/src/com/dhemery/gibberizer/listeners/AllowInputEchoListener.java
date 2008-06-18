package com.dhemery.gibberizer.listeners;

import com.dhemery.gibberizer.core.Gibberizer;

public class AllowInputEchoListener extends CheckBoxListener {

	public AllowInputEchoListener(Gibberizer gibberizer) {
		super(gibberizer);
	}
	
	@Override
	protected void execute(boolean isSelected) {
		getGibberizer().setAllowInputEcho(isSelected);
	}
}
