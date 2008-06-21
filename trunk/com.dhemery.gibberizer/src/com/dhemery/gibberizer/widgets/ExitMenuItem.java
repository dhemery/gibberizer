/**
 * 
 */
package com.dhemery.gibberizer.widgets;

import com.dhemery.gibberizer.ui.GibberizerWindow;

public class ExitMenuItem extends GibberizerMenuItem {
	private static final long serialVersionUID = 1L;

	public ExitMenuItem(String name, GibberizerWindow window) {
		super(name, window);
	}

	@Override
	public void execute(GibberizerWindow window) {
		window.close();
	}
}