package com.dhemery.gibberizer.listeners;

import com.dhemery.gibberizer.core.Gibberizer;
import com.dhemery.gibberizer.core.JoinStyle;

public class JoinStyleListener extends RadioButtonListener {

	public JoinStyleListener(Gibberizer gibberizer) {
		super(gibberizer);
	}

	@Override
	protected void execute(Object data) {
		getGibberizer().setJoinStyle((JoinStyle) data);
	}

}
