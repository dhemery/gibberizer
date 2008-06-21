package com.dhemery.gibberizer.widgets;

import com.dhemery.gibberizer.core.Gibberizer;

public class AllowInputEchoCheckBox extends GibberizerCheckBox {
	private static final long serialVersionUID = 1L;

	public AllowInputEchoCheckBox(String name, String toolTipText, Gibberizer gibberizer) {
		super(name, toolTipText, gibberizer, gibberizer.getAllowInputEcho());
	}
	
	@Override
	protected void execute() {
		getGibberizer().setAllowInputEcho(isSelected());
	}
}
