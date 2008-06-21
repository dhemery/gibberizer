package com.dhemery.gibberizer.widgets;

import com.dhemery.gibberizer.core.Gibberizer;

public class AllowDuplicatesCheckBox extends GibberizerCheckBox {
	private static final long serialVersionUID = 1L;

	public AllowDuplicatesCheckBox(String name, String toolTipText, Gibberizer gibberizer) {
		super(name, toolTipText, gibberizer, gibberizer.getAllowDuplicates());
	}

	@Override
	protected void execute() {
		getGibberizer().setAllowDuplicates(isSelected());
	}
}
