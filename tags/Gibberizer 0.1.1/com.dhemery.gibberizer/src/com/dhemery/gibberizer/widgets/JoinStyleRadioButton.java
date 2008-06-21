package com.dhemery.gibberizer.widgets;

import com.dhemery.gibberizer.core.Gibberizer;
import com.dhemery.gibberizer.core.JoinStyle;

public class JoinStyleRadioButton extends GibberizerRadioButton {
	private static final long serialVersionUID = 1L;
	private final JoinStyle style;

	public JoinStyleRadioButton(String name, String toolTipText, Gibberizer gibberizer, JoinStyle style) {
		super(name, toolTipText, gibberizer, gibberizer.getJoinStyle() == style);
		this.style = style;
	}

	@Override
	protected void select() {
		getGibberizer().setJoinStyle(style);
	}

}
