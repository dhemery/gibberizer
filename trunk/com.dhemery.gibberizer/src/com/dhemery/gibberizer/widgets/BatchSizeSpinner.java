package com.dhemery.gibberizer.widgets;

import com.dhemery.gibberizer.core.Gibberizer;

public class BatchSizeSpinner extends GibberizerSpinner {
	private static final long serialVersionUID = 1L;

	public BatchSizeSpinner(String name, String toolTipText, Gibberizer gibberizer) {
		super(name, toolTipText, gibberizer, Gibberizer.MAX_BATCH_SIZE, gibberizer.getBatchSize());
	}

	@Override
	protected void setSelection(int selection) {
		getGibberizer().setBatchSize(selection);
	}
}
