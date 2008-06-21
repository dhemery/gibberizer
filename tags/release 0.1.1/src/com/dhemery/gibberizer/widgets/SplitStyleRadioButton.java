package com.dhemery.gibberizer.widgets;

import com.dhemery.gibberizer.core.Gibberizer;
import com.dhemery.gibberizer.core.SplitStyle;

public class SplitStyleRadioButton extends GibberizerRadioButton {
	private static final long serialVersionUID = 1L;
	private final SplitStyle style;

	public SplitStyleRadioButton(String name, String toolTipText, Gibberizer gibberizer, SplitStyle style) {
		super(name, toolTipText, gibberizer, gibberizer.getSplitStyle() == style);
		this.style = style;
	}

	@Override
	protected void select() {
		getGibberizer().setSplitStyle(style);
	}
}
