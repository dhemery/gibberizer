package com.dhemery.gibberizer.listeners;

import com.dhemery.gibberizer.core.Gibberizer;

public class SimilarityListener extends SpinnerListener {
	public SimilarityListener(Gibberizer gibberizer) {
		super(gibberizer);
	}

	@Override
	protected void execute(int selection) {
		getGibberizer().setSimilarity(selection);
	}
}
