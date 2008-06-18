package com.dhemery.gibberizer.listeners;

import com.dhemery.gibberizer.core.Gibberizer;

public class BatchSizeListener extends SpinnerListener {
	public BatchSizeListener(Gibberizer gibberizer) {
		super(gibberizer);
	}

	@Override
	protected void execute(int selection) {
		getGibberizer().setBatchSize(selection);
	}
}
