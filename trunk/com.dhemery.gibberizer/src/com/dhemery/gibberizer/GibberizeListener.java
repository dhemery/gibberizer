package com.dhemery.gibberizer;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

public class GibberizeListener implements SelectionListener {
	private final Gibberizer gibberizer;
	private final GibberizerWindow window;

	public GibberizeListener(Gibberizer gibberizer,
			GibberizerWindow window) {
		this.gibberizer = gibberizer;
		this.window = window;
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		widgetSelected(e);
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		window.setOutputText(gibberizer.gibberize(window.getInputText()));
	}
}
