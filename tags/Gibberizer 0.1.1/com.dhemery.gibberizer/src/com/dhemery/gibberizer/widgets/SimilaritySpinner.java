package com.dhemery.gibberizer.widgets;

import com.dhemery.gibberizer.core.Gibberizer;

public class SimilaritySpinner extends GibberizerSpinner {
	private static final long serialVersionUID = 1L;

	public SimilaritySpinner(String name, String toolTipText, Gibberizer gibberizer) {
		super(name, toolTipText, gibberizer, Gibberizer.MAX_SIMILARITY, gibberizer.getSimilarity());
	}

	@Override
	protected void setSelection(int selection) {
		getGibberizer().setSimilarity(selection);
	}
}
