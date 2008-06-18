package com.dhemery.gibberizer.listeners;

import com.dhemery.gibberizer.core.Gibberizer;
import com.dhemery.gibberizer.core.SplitStyle;

public class SplitStyleListener extends RadioButtonListener {

	public SplitStyleListener(Gibberizer gibberizer) {
		super(gibberizer);
	}

	@Override
	protected void execute(Object data) {
		getGibberizer().setSplitStyle((SplitStyle) data);
	}
}
